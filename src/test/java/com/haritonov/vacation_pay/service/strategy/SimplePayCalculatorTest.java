package com.haritonov.vacation_pay.service.strategy;

import com.haritonov.vacation_pay.dto.VacationPayRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class SimplePayCalculatorTest {

    private SimplePayCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new SimplePayCalculator();
    }

    @Test
    void whenValidDataEnteredReturnsCorrectAmount() {
        VacationPayRequest request = new VacationPayRequest();
        request.setAverageSalary(new BigDecimal("29300.00"));
        request.setVacationDays(10);
        BigDecimal expected = new BigDecimal("10000.00");
        BigDecimal result = calculator.calculate(request);
        assertEquals(expected, result);
    }

    @Test
    void whenRoundingNeededRoundsCorrectly() {
        VacationPayRequest request = new VacationPayRequest();
        request.setAverageSalary(new BigDecimal("10000.00"));
        request.setVacationDays(1);
        BigDecimal expected = new BigDecimal("341.30");
        BigDecimal result = calculator.calculate(request);
        assertEquals(expected, result);
    }

    @Test
    void whenDaysIsNullThrowsException() {
        VacationPayRequest request = new VacationPayRequest();
        request.setAverageSalary(new BigDecimal("50000.00"));
        request.setVacationDays(null);
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculate(request);
        });
    }

    @Test
    void whenZeroDaysReturnsZero() {
        VacationPayRequest request = new VacationPayRequest();
        request.setAverageSalary(new BigDecimal("50000.00"));
        request.setVacationDays(0);
        BigDecimal result = calculator.calculate(request);
        assertEquals(new BigDecimal("0.00"), result);
    }
}
