package service;

import domain.NGLData;
import exception.ParameterOutOfBoundException;
import util.ConversionUtil;
import util.GlobalConstants;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Calculator {

    public BigDecimal calculateCTLCorrection(BigDecimal specificGravity, BigDecimal observedTemperature) throws ParameterOutOfBoundException {
        //round up values to 4 decimal points
        specificGravity = specificGravity.setScale(4, RoundingMode.HALF_DOWN);
        observedTemperature = observedTemperature.setScale(4, RoundingMode.HALF_DOWN);

        //convert to Kelvin
        BigDecimal observedTemperatureKelvin = ConversionUtil.fahrenheitToKelvin(observedTemperature);

        //validate min and max boundaries
        boolean process = false;
        if (GlobalConstants.minimumKelvin <= observedTemperatureKelvin.doubleValue()
                && observedTemperatureKelvin.doubleValue() <= GlobalConstants.maximumKelvin
            && GlobalConstants.minimumGravity <= specificGravity.doubleValue()
            && specificGravity.doubleValue() <= GlobalConstants.maximumGravity) {
            process = true;
        }

        if (!process) {
            throw new ParameterOutOfBoundException("Out of bound Exception");
        }

        //find where the input density falls on
        NGLData predecessorNGL = null;
        NGLData successorNGL = null;

        Map<String, NGLData> sortedNewMap = GlobalConstants.nglTable.entrySet().stream().sorted(Comparator.comparing(e -> e.getValue().getY60()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        for (Map.Entry<String, NGLData> entry : sortedNewMap.entrySet()) {
            if (specificGravity.doubleValue() >= entry.getValue().getY60()) {
                predecessorNGL = entry.getValue();
            }
            if (specificGravity.doubleValue() <= entry.getValue().getY60()) {
                successorNGL = entry.getValue();
                break;
            }
        }

        if (predecessorNGL == null || successorNGL == null) {
            throw new ParameterOutOfBoundException("No reference fluids found");
        }

        System.out.println("predecessor NGL :" + predecessorNGL.getFluidName());
        System.out.println("successor NGL :" + successorNGL.getFluidName());


        Double interpolationValue = (specificGravity.doubleValue()- predecessorNGL.getY60())/(successorNGL.getY60()- predecessorNGL.getY60());
        System.out.println("Interpolation value :"+interpolationValue);


        Double criticalTemperature = predecessorNGL.gettC() + interpolationValue * (successorNGL.gettC() - predecessorNGL.gettC());
        System.out.println("Critical Temperature :"+criticalTemperature);

        Double reducedObservedTemperature = observedTemperatureKelvin.doubleValue()/criticalTemperature;
        System.out.println("Reduced Temperature :"+reducedObservedTemperature);

        Double reducedObservedTemperatureAt60 = 519.67/(1.8 * criticalTemperature);
        System.out.println("Reduced Temperature @ 60 :"+reducedObservedTemperatureAt60);

        Double scalingFactor = (predecessorNGL.getzC()*predecessorNGL.getPc())/(successorNGL.getzC()*successorNGL.getPc());
        System.out.println("Scaling factor :"+scalingFactor);

        Double tau = Math.abs(1 - reducedObservedTemperatureAt60);
        System.out.println("Tau :"+tau);

        Double rosat1 = predecessorNGL.getPc()*(1 + ((predecessorNGL.getK1()*ConversionUtil.powDecimal(tau,0.35))+(predecessorNGL.getK3()*ConversionUtil.powDecimal(tau,2d))+(predecessorNGL.getK4()*ConversionUtil.powDecimal(tau,3d)))/(1 + (predecessorNGL.getK2()*ConversionUtil.powDecimal(tau, 0.65))));
        Double rosat2 = successorNGL.getPc()*(1 + ((successorNGL.getK1()*ConversionUtil.powDecimal(tau,0.35))+(successorNGL.getK3()*ConversionUtil.powDecimal(tau,2d))+(successorNGL.getK4()*ConversionUtil.powDecimal(tau,3d)))/(1 + (successorNGL.getK2()*ConversionUtil.powDecimal(tau, 0.65))));
        System.out.println("Rosat 1 :"+rosat1);
        System.out.println("Rosat 2 :"+rosat2);

        Double interpolationFactor = rosat1/(1 + interpolationValue*((rosat1/(scalingFactor*rosat2))-1));
        System.out.println("Interpolation factor :"+interpolationFactor);

        Double tauX = Math.abs(1 - reducedObservedTemperature);
        System.out.println("TauX :"+tauX);

        Double rosat1X = predecessorNGL.getPc()*(1 + ((predecessorNGL.getK1()*ConversionUtil.powDecimal(tauX,0.35))+(predecessorNGL.getK3()*ConversionUtil.powDecimal(tauX,2d))+(predecessorNGL.getK4()*ConversionUtil.powDecimal(tauX,3d)))/(1 + (predecessorNGL.getK2()*ConversionUtil.powDecimal(tauX, 0.65))));
        Double rosat2X = successorNGL.getPc()*(1 + ((successorNGL.getK1()*ConversionUtil.powDecimal(tauX,0.35))+(successorNGL.getK3()*ConversionUtil.powDecimal(tauX,2d))+(successorNGL.getK4()*ConversionUtil.powDecimal(tauX,3d)))/(1 + (successorNGL.getK2()*ConversionUtil.powDecimal(tauX, 0.65))));
        System.out.println("RosatX 1 :"+rosat1X);
        System.out.println("RosatX 2 :"+rosat2X);

        Double ctl = rosat1X/(interpolationFactor * (1 + interpolationValue * ((rosat1X/(scalingFactor * rosat2X)) -1 )));
        System.out.println("CTL :"+ctl);

        BigDecimal roundedCtl = BigDecimal.valueOf(ctl).setScale(4,RoundingMode.HALF_DOWN);
        System.out.println("Rounded CTL :"+roundedCtl);

        return roundedCtl;
    }
}
