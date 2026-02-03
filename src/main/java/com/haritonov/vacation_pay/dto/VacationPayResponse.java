package com.haritonov.vacation_pay.dto;

import java.math.BigDecimal;

/**
 * DTO для ответа сервера клиенту.
 * Содержит рассчитанную сумму отпускных.
 */
public class VacationPayResponse {

    /**
     * Итоговая сумма отпускных, подлежащая выплате сотруднику.
     */
    private BigDecimal calculationResult;

    /**
     * Конструктор по умолчанию.
     */
    public VacationPayResponse() {}

    /**
     * Конструктор для быстрого создания ответа с результатом.
     * @param calculationResult рассчитанная сумма.
     */
    public VacationPayResponse(BigDecimal calculationResult) {
        this.calculationResult = calculationResult;
    }

    public BigDecimal getCalculationResult() {
        return calculationResult;
    }

    public void setCalculationResult(BigDecimal calculationResult) {
        this.calculationResult = calculationResult;
    }
}
