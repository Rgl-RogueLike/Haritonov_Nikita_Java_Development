package com.haritonov.vacation_pay.service.strategy;

import com.haritonov.vacation_pay.config.HolidaysProperties;
import com.haritonov.vacation_pay.dto.VacationPayRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit-тесты для класса {@link DateRangePayCalculator}.
 * Проверяют корректность расчета отпускных по датам с учетом праздников и валидацию входных данных.
 */
public class DateRangePayCalculatorTest {

    private HolidaysProperties holidaysProperties;
    private DateRangePayCalculator calculator;

    /**
     * Объект запроса, переиспользуемый в тестах.
     * Средняя зарплата фиксирована (29300) для упрощения расчетов (1000 руб/день).
     */
    private VacationPayRequest request;

    @BeforeEach
    void setUp() {
        request = new VacationPayRequest();
        request.setAverageSalary(new BigDecimal("29300.00"));
    }

    /**
     * Вспомогательный метод для инициализации калькулятора с заданным списком праздников.
     * Создает объект свойств, заполняет его строками и вызывает init() для парсинга.
     * @param dates список строк дат-праздников (например, "2026-01-01")
     */
    private void setupCalculatorWithHolidays(List<String> dates) {
        holidaysProperties = new HolidaysProperties();
        holidaysProperties.setDates(dates);
        holidaysProperties.init();
        calculator = new DateRangePayCalculator(holidaysProperties);
    }

    @Test
    void whenNoHolidaysPaysAllDays() {
        // Сценарий: Отпуск 5 дней, праздничных дней нет.
        setupCalculatorWithHolidays(List.of());
        request.setStartDate(LocalDate.of(2026, 3, 1));
        request.setEndDate(LocalDate.of(2026, 3, 5));

        // Ожидается: 5 дней * 1000 = 5000
        BigDecimal expected = new BigDecimal("5000.00");
        BigDecimal result = calculator.calculate(request);

        assertEquals(expected, result);
    }

    @Test
    void whenOneHoliday() {
        // Сценарий: Период 3 дня (22, 23, 24 февраля). 23 февраля — праздник.
        setupCalculatorWithHolidays(List.of("2026-02-23"));
        request.setStartDate(LocalDate.of(2026, 2, 22));
        request.setEndDate(LocalDate.of(2026, 2, 24));

        // Ожидается: оплатим только 22 и 24 февраля (2 дня)
        BigDecimal expected = new BigDecimal("2000.00");
        BigDecimal result = calculator.calculate(request);

        assertEquals(expected, result);
    }

    @Test
    void whenAllHolidaysPaysZero() {
        // Сценарий: Весь период состоит из праздников.
        setupCalculatorWithHolidays(List.of("2026-01-01", "2026-01-02", "2026-01-03"));
        request.setStartDate(LocalDate.of(2026, 1, 1));
        request.setEndDate(LocalDate.of(2026, 1, 3));

        BigDecimal result = calculator.calculate(request);

        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void whenWeekendNotHolidaysPays() {
        // Сценарий: Отпуск выпадает на субботу и воскресенье (выходные).
        // В ТК РФ выходные оплачиваются, если они не являются праздниками.
        setupCalculatorWithHolidays(List.of());
        request.setStartDate(LocalDate.of(2026, 1, 17));
        request.setEndDate(LocalDate.of(2026, 1, 18));

        // Ожидается: оплата за 2 дня
        BigDecimal expected = new BigDecimal("2000.00");
        BigDecimal result = calculator.calculate(request);

        assertEquals(expected, result);
    }

    @Test
    void whenStartDateIsNullThrowsException() {
        // Сценарий: Дата начала отпуска не указана.
        setupCalculatorWithHolidays(List.of());
        request.setStartDate(null);
        request.setEndDate(LocalDate.of(2024, 1, 5));

        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculate(request);
        });
    }

    @Test
    void whenEndDateIsBeforeStartDateThrowsException() {
        // Сценарий: Дата конца раньше даты начала (некорректный интервал).
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
        // Сценарий: Сам объект запроса null.
        setupCalculatorWithHolidays(List.of());
        assertThrows(NullPointerException.class, () -> {
            calculator.calculate(null);
        });
    }
}
