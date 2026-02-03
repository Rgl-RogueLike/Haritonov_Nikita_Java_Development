package com.haritonov.vacation_pay.controller;

import com.haritonov.vacation_pay.dto.VacationPayRequest;
import com.haritonov.vacation_pay.dto.VacationPayResponse;
import com.haritonov.vacation_pay.service.VacationPayService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * REST-контроллер для расчета отпускных.
 * Предоставляет единственную точку входа: {@code GET /calculate}.
 * Принимает параметры через GET-запрос, валидирует их и делегирует расчет сервису.
 * Ошибки валидации обрабатываются {@link com.haritonov.vacation_pay.exception.GlobalExceptionHandler}.
 */
@RestController
@Validated
public class VacationPayController {

    /**
     * Сервис для бизнес-логики расчета отпускных.
     * Внедряется через конструктор.
     */
    private final VacationPayService vacationPayService;

    /**
     * Конструктор контроллера.
     * @param vacationPayService сервис расчета отпускных
     */
    public VacationPayController(VacationPayService vacationPayService) {
        this.vacationPayService = vacationPayService;
    }

    /**
     * Обрабатывает GET-запрос на расчет отпускных.
     * Параметры запроса автоматически мапятся в объект {@link VacationPayRequest}.
     * Валидация параметров происходит благодаря аннотации {@link Valid}.
     *
     * @param request объект с параметрами расчета
     * @return {@link ResponseEntity} с {@link VacationPayResponse}
     */
    @GetMapping("/calculate")
    public ResponseEntity<VacationPayResponse> calculate(@Valid VacationPayRequest request) {
        VacationPayResponse response = vacationPayService.calculate(request);
        return ResponseEntity.ok(response);
    }
}
