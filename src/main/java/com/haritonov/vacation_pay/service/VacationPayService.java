package com.haritonov.vacation_pay.service;

import com.haritonov.vacation_pay.dto.VacationPayRequest;
import com.haritonov.vacation_pay.dto.VacationPayResponse;

/**
 * Сервисный интерфейс для расчета отпускных.
 * Является точкой входа бизнес-логики.
 */
public interface VacationPayService {

    /**
     * Рассчитывает отпускные на основе входного запроса.
     * @param request объект с параметрами расчета (валидированный)
     * @return объект {@link VacationPayResponse} с рассчитанной суммой
     */
    VacationPayResponse calculate(VacationPayRequest request);

}
