package com.haritonov.vacation_pay.dto;

import java.math.BigDecimal;

public class VacationPayResponse {

    private BigDecimal calculationResult;

    public VacationPayResponse() {}

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
