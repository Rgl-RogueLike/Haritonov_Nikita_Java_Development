package com.haritonov.vacation_pay.service.strategy;

import com.haritonov.vacation_pay.dto.VacationPayRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit-тесты для класса {@link SimplePayCalculator}.
 * Проверяют простую формулу расчета, округление копеек и проверку входных данных.
 */
public class SimplePayCalculatorTest {

    private SimplePayCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new SimplePayCalculator();
    }

    @Test
    void whenValidDataEnteredReturnsCorrectAmount() {
        // Сценарий: Корректные данные (29300 / 29.3 = 1000 руб/день). 10 дней отпуска.
        VacationPayRequest request = new VacationPayRequest();
        request.setAverageSalary(new BigDecimal("29300.00"));
        request.setVacationDays(10);

        BigDecimal expected = new BigDecimal("10000.00");
        BigDecimal result = calculator.calculate(request);

        assertEquals(expected, result);
    }

    @Test
    void whenRoundingNeededRoundsCorrectly() {
        // Сценарий: Проверка округления.
        // 10000 / 29.3 = 341.296 -> Должно стать 341.30
        VacationPayRequest request = new VacationPayRequest();
        request.setAverageSalary(new BigDecimal("10000.00"));
        request.setVacationDays(1);

        BigDecimal expected = new BigDecimal("341.30");
        BigDecimal result = calculator.calculate(request);

        assertEquals(expected, result);
    }

    @Test
    void whenDaysIsNullThrowsException() {
        // Сценарий: Количество дней не указано.
        VacationPayRequest request = new VacationPayRequest();
        request.setAverageSalary(new BigDecimal("50000.00"));
        request.setVacationDays(null);

        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculate(request);
        });
    }

    @Test
    void whenZeroDaysReturnsZero() {
        // Сценарий: 0 дней отпуска.
        VacationPayRequest request = new VacationPayRequest();
        request.setAverageSalary(new BigDecimal("50000.00"));
        request.setVacationDays(0);

        BigDecimal result = calculator.calculate(request);

        // Ожидаем именно 0.00
        assertEquals(new BigDecimal("0.00"), result);
    }

    @Test
    void whenAverageSalaryIsNullThrowsException() {
        // Сценарий: Средняя зарплата не указана (null).
        VacationPayRequest request = new VacationPayRequest();
        request.setVacationDays(10);

        assertThrows(NullPointerException.class, () -> {
            calculator.calculate(request);
        });
    }
}
