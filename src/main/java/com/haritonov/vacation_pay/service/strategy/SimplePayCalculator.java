package com.haritonov.vacation_pay.service.strategy;

import com.haritonov.vacation_pay.dto.VacationPayRequest;
import com.haritonov.vacation_pay.util.CalculationConstants;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * Стратегия для простого расчета отпускных.
 * Используется, когда пользователь указывает только количество дней отпуска.
 * Расчет производится по фиксированному среднему количеству дней в месяце (29.3),
 * независимо от того, выпадают они на праздники или выходные.
 */
@Component
public class SimplePayCalculator implements VacationPayStrategy {
    /**
     * Рассчитывает сумму отпускных на основе количества дней и средней зарплаты.
     * Формула: (Средняя зарплата / 29.3) * Количество дней.
     * @param request запрос, содержащий среднюю зарплату и количество дней отпуска
     * @return рассчитанная сумма отпускных
     * @throws IllegalArgumentException если количество дней не указано (null)
     */
    @Override
    public BigDecimal calculate(VacationPayRequest request) {
        if (request.getVacationDays() == null) {
            throw new IllegalArgumentException("Количество дней отпуска не указано для расчета");
        }
        BigDecimal averageSalary = request.getAverageSalary();
        Integer vacationDays = request.getVacationDays();
        BigDecimal averageDailySalary = averageSalary.divide(CalculationConstants.AVG_DAYS_IN_MONTH, 2, RoundingMode.HALF_UP);
        return averageDailySalary.multiply(BigDecimal.valueOf(vacationDays));
    }
}
