package service;

import exception.ParameterOutOfBoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class CorrectionCalculationTest {
    Calculator calculator = new Calculator();

    @Test
    void calcualteCTL_1() throws ParameterOutOfBoundException {
        BigDecimal result = calculator.calculateCTLCorrection(BigDecimal.valueOf(.45), BigDecimal.valueOf(0));
        Assertions.assertEquals(1.1201, result.doubleValue());

        result = calculator.calculateCTLCorrection(BigDecimal.valueOf(.45), BigDecimal.valueOf(3));
        Assertions.assertEquals(1.1147, result.doubleValue());

        result = calculator.calculateCTLCorrection(BigDecimal.valueOf(.45), BigDecimal.valueOf(80));
        Assertions.assertEquals(0.9516, result.doubleValue());

        result = calculator.calculateCTLCorrection(BigDecimal.valueOf(.67), BigDecimal.valueOf(64));
        Assertions.assertEquals(0.9971, result.doubleValue());
    }

}