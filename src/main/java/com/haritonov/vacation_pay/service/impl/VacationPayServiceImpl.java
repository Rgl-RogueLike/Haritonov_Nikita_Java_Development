package com.haritonov.vacation_pay.service.impl;

import com.haritonov.vacation_pay.dto.VacationPayRequest;
import com.haritonov.vacation_pay.dto.VacationPayResponse;
import com.haritonov.vacation_pay.service.VacationPayService;
import com.haritonov.vacation_pay.service.strategy.DateRangePayCalculator;
import com.haritonov.vacation_pay.service.strategy.SimplePayCalculator;
import com.haritonov.vacation_pay.service.strategy.VacationPayStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Реализация сервиса расчета отпускных выплат.
 * Использует Strategy паттерн для выбора алгоритма расчета:
 * - простой расчет по количеству дней (vacationDays)
 * - точный расчет по периоду (startDate-endDate) с исключением праздников
 */
@Service
public class VacationPayServiceImpl implements VacationPayService {

    /**
     * Калькулятор простого расчета (по vacationDays).
     */
    private final SimplePayCalculator simplePayCalculator;

    /**
     * Калькулятор по периоду дат с учетом праздничных дней.
     */
    private final DateRangePayCalculator dateRangePayCalculator;

    /**
     * Конструктор с внедрением зависимостей (Spring DI).
     * @param simplePayCalculator простой калькулятор
     * @param dateRangePayCalculator калькулятор по диапазону дат
     */
    public VacationPayServiceImpl(SimplePayCalculator simplePayCalculator, DateRangePayCalculator dateRangePayCalculator) {
        this.simplePayCalculator = simplePayCalculator;
        this.dateRangePayCalculator = dateRangePayCalculator;
    }

    /**
     * Автоматически выбирает стратегию расчета на основе наличия дат отпуска:
     * - если есть startDate и endDate → DateRangePayCalculator
     * - иначе → SimplePayCalculator (по vacationDays)
     *
     * @param request параметры расчета отпускных
     * @return результат расчета в VacationPayResponse
     */
    @Override
    public VacationPayResponse calculate(VacationPayRequest request) {
        VacationPayStrategy strategy;
        if (request.getVacationDate() != null) {
            strategy = dateRangePayCalculator;
        } else {
            strategy = simplePayCalculator;
        }
        BigDecimal calculatorResult = strategy.calculate(request);
        return new VacationPayResponse(calculatorResult);
    }
}
