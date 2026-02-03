package com.haritonov.vacation_pay.service.strategy;

import com.haritonov.vacation_pay.config.HolidaysProperties;
import com.haritonov.vacation_pay.dto.VacationPayRequest;
import com.haritonov.vacation_pay.util.CalculationConstants;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Стратегия расчета отпускных по датам (с учетом праздничных дней).
 * Реализует интерфейс {@link VacationPayStrategy}.
 */
@Component
public class DateRangePayCalculator implements VacationPayStrategy {

    /**
     * Объект настроек, содержащий список официальных праздничных дней в 2026 году.
     */
    private final HolidaysProperties holidaysProperties;

    /**
     * Конструктор для внедрения зависимости (Dependency Injection).
     * @param holidaysProperties бин с настройками праздников
     */
    public DateRangePayCalculator(HolidaysProperties holidaysProperties) {
        this.holidaysProperties = holidaysProperties;
    }

    /**
     * Выполняет расчет суммы отпускных за указанный период.
     *
     * @param "request" запрос, содержащий даты начала и конца отпуска, а также среднюю зарплату
     * @return рассчитанная сумма отпускных. Может вернуть {@link BigDecimal#ZERO}, если весь период состоит из праздников
     * @throws IllegalArgumentException если даты не указаны или дата конца раньше даты начала
     */
    @Override
    public BigDecimal calculate(VacationPayRequest request) {
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Необходимы даты начала и конца отпуска");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Дата конца отпуска не может быть раньше даты начала");
        }
        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        int paidDays = 0;
        for (int i = 0; i < totalDays; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            if (!holidaysProperties.isHolidays(currentDate)) {
                paidDays++;
            }
        }
        if (paidDays == 0) {
            return  BigDecimal.ZERO;
        }
        BigDecimal averageSalary = request.getAverageSalary();
        BigDecimal averageDailySalary = averageSalary.divide(CalculationConstants.AVG_DAYS_IN_MONTH, 2, RoundingMode.HALF_UP);

        return averageDailySalary.multiply(BigDecimal.valueOf(paidDays));
    }
}
