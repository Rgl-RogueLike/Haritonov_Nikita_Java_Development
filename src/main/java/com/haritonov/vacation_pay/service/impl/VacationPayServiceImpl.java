package com.haritonov.vacation_pay.service.impl;

import com.haritonov.vacation_pay.dto.VacationPayRequest;
import com.haritonov.vacation_pay.dto.VacationPayResponse;
import com.haritonov.vacation_pay.service.VacationPayService;
import com.haritonov.vacation_pay.service.strategy.DateRangePayCalculator;
import com.haritonov.vacation_pay.service.strategy.SimplePayCalculator;
import com.haritonov.vacation_pay.service.strategy.VacationPayStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class VacationPayServiceImpl implements VacationPayService {


    private final SimplePayCalculator simplePayCalculator;
    private final DateRangePayCalculator dateRangePayCalculator;

    public VacationPayServiceImpl(SimplePayCalculator simplePayCalculator, DateRangePayCalculator dateRangePayCalculator) {
        this.simplePayCalculator = simplePayCalculator;
        this.dateRangePayCalculator = dateRangePayCalculator;
    }

    @Override
    public VacationPayResponse calculate(VacationPayRequest request) {
        VacationPayStrategy strategy;
        if (request.getStartDate() != null && request.getEndDate() != null) {
            strategy = dateRangePayCalculator;
        } else {
            strategy = simplePayCalculator;
        }
        BigDecimal calculatorResult = strategy.calculate(request);
        return new VacationPayResponse(calculatorResult);
    }
}
