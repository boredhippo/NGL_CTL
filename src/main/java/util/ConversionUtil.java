package util;

import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class ConversionUtil {

    public static BigDecimal fahrenheitToKelvin(BigDecimal fahrenheit) {
        return (fahrenheit.add(BigDecimal.valueOf(459.67))).divide(BigDecimal.valueOf(1.8), 4, RoundingMode.HALF_DOWN);
    }

    public static Double powDecimal(Double a, Double b) {
        return BigDecimalMath.pow(BigDecimal.valueOf(a), BigDecimal.valueOf(b), new MathContext(4)).doubleValue();
    }
}
