package com.haritonov.vacation_pay.service.strategy;

import com.haritonov.vacation_pay.dto.VacationPayRequest;
import com.haritonov.vacation_pay.util.CalculationConstants;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SimplePayCalculator implements VacationPayStrategy {
    @Override
    public BigDecimal calculate(VacationPayRequest request) {
        BigDecimal averageSalary = request.getAverageSalary();
        Integer vacationDays = request.getVacationDays();
        if (vacationDays == null) {
            throw new IllegalArgumentException("Количество дней отпуска не указано для расчета");
        }
        BigDecimal averageDailySalary = averageSalary.divide(CalculationConstants.AVG_DAYS_IN_MONTH, 2, RoundingMode.HALF_UP);
        return averageDailySalary.multiply(BigDecimal.valueOf(vacationDays));
    }
}
