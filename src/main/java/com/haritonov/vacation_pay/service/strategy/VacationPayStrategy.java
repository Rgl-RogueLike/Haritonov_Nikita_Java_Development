package com.haritonov.vacation_pay.service.strategy;

import com.haritonov.vacation_pay.dto.VacationPayRequest;

import java.math.BigDecimal;

public interface VacationPayStrategy {
    BigDecimal calculate(VacationPayRequest request);
}
