package com.haritonov.vacation_pay.util;

import java.math.BigDecimal;

/**
 * Утилитарный класс для хранения констант, используемых в расчетах.
 */
public class CalculationConstants {

    /**
     * Среднее количество календарных дней в месяце.
     */
    public static final BigDecimal AVG_DAYS_IN_MONTH = new BigDecimal("29.3");

    /**
     * Приватный конструктор для предотвращения создания экземпляров утилитарного класса.
     */
    private CalculationConstants() {}

}
