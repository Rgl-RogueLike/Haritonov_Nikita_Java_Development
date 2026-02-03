package com.haritonov.vacation_pay.service.impl;

import com.haritonov.vacation_pay.dto.VacationPayRequest;
import com.haritonov.vacation_pay.dto.VacationPayResponse;
import com.haritonov.vacation_pay.service.VacationPayService;
import com.haritonov.vacation_pay.service.strategy.SimplePayCalculator;
import com.haritonov.vacation_pay.service.strategy.VacationPayStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class VacationPayServiceImpl implements VacationPayService {


    private final SimplePayCalculator simplePayCalculator;

    public VacationPayServiceImpl(SimplePayCalculator simplePayCalculator) {
        this.simplePayCalculator = simplePayCalculator;
    }

    @Override
    public VacationPayResponse calculate(VacationPayRequest request) {
        VacationPayStrategy strategy;
        if (request.getStart() != null && request.getEnd() != null) {
            //TODO dateRangePayCalculator
            strategy = simplePayCalculator;
        } else {
            strategy = simplePayCalculator;
        }
        BigDecimal calculatorResult = strategy.calculate(request);
        return new VacationPayResponse(calculatorResult);
    }
}
