package com.haritonov.vacation_pay.service.strategy;

import com.haritonov.vacation_pay.config.HolidaysProperties;
import com.haritonov.vacation_pay.dto.VacationPayRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DateRangePayCalculatorTest {

    private HolidaysProperties holidaysProperties;
    private DateRangePayCalculator calculator;

    private VacationPayRequest request;

    @BeforeEach
    void setUp() {
        request = new VacationPayRequest();
        request.setAverageSalary(new BigDecimal("29300.00"));
    }

    private void setupCalculatorWithHolidays(List<String> dates) {
        holidaysProperties = new HolidaysProperties();
        holidaysProperties.setDates(dates);
        holidaysProperties.init();
        calculator = new DateRangePayCalculator(holidaysProperties);
    }

    @Test
    void whenNoHolidaysPaysAllDays() {
        setupCalculatorWithHolidays(List.of());
        request.setStartDate(LocalDate.of(2026, 3, 1));
        request.setEndDate(LocalDate.of(2026, 3, 5));
        BigDecimal expected = new BigDecimal("5000.00");
        BigDecimal result = calculator.calculate(request);
        assertEquals(expected, result);
    }

    @Test
    void whenOneHoliday() {
        setupCalculatorWithHolidays(List.of("2026-02-23"));
        request.setStartDate(LocalDate.of(2026, 2, 22));
        request.setEndDate(LocalDate.of(2026, 2, 24));
        BigDecimal expected = new BigDecimal("2000.00");
        BigDecimal result = calculator.calculate(request);
        assertEquals(expected, result);
    }

    @Test
    void whenAllHolidaysPaysZero() {
        setupCalculatorWithHolidays(List.of("2026-01-01", "2026-01-02", "2026-01-03"));
        request.setStartDate(LocalDate.of(2026, 1, 1));
        request.setEndDate(LocalDate.of(2026, 1, 3));
        BigDecimal result = calculator.calculate(request);
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void whenWeekendNotHolidaysPays() {
        setupCalculatorWithHolidays(List.of());
        request.setStartDate(LocalDate.of(2026, 1, 17));
        request.setEndDate(LocalDate.of(2026, 1, 18));
        BigDecimal expected = new BigDecimal("2000.00");
        BigDecimal result = calculator.calculate(request);
        assertEquals(expected, result);
    }

    @Test
    void whenStartDateIsNullThrowsException() {
        setupCalculatorWithHolidays(List.of());
        request.setStartDate(null);
        request.setEndDate(LocalDate.of(2024, 1, 5));
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculate(request);
        });
    }

    @Test
    void whenEndDateIsBeforeStartDateThrowsException() {
        setupCalculatorWithHolidays(List.of());
        request.setStartDate(LocalDate.of(2024, 1, 10));
        request.setEndDate(LocalDate.of(2024, 1, 1));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculate(request);
        });
        assertEquals("Дата конца отпуска не может быть раньше даты начала", exception.getMessage());
    }

    @Test
    void whenRequestObjectIsNullThrowsException() {
        setupCalculatorWithHolidays(List.of());
        assertThrows(NullPointerException.class, () -> {
            calculator.calculate(null);
        });
    }
}
