package com.haritonov.vacation_pay.service.strategy;

import com.haritonov.vacation_pay.dto.VacationPayRequest;

import java.math.BigDecimal;

/**
 * Интерфейс стратегии (Strategy Pattern) для расчета отпускных.
 * <p>
 * Определяет контракт для различных алгоритмов расчета:
 * <ul>
 *   <li>{@link SimplePayCalculator} — расчет по количеству дней.</li>
 *   <li>{@link DateRangePayCalculator} — расчет по датам с учетом праздников.</li>
 * </ul>
 */
public interface VacationPayStrategy {
    BigDecimal calculate(VacationPayRequest request);
}
