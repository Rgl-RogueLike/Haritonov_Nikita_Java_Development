package com.haritonov.vacation_pay.dto;

import java.math.BigDecimal;

public class VacationPayResponse {

    private BigDecimal calculationResult;

    public VacationPayResponse() {}

    public VacationPayResponse(BigDecimal calculationResult) {
        this.calculationResult = calculationResult;
    }

    public BigDecimal getCalculactionResult() {
        return calculationResult;
    }

    public void setCalculactionResult(BigDecimal calculationResult) {
        this.calculationResult = calculationResult;
    }
}
