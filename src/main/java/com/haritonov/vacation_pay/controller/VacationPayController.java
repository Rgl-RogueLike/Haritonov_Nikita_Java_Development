package com.haritonov.vacation_pay.controller;

import com.haritonov.vacation_pay.dto.VacationPayRequest;
import com.haritonov.vacation_pay.dto.VacationPayResponse;
import com.haritonov.vacation_pay.service.VacationPayService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
public class VacationPayController {

    private final VacationPayService vacationPayService;

    public VacationPayController(VacationPayService vacationPayService) {
        this.vacationPayService = vacationPayService;
    }

    @GetMapping("/calculate")
    public ResponseEntity<VacationPayResponse> calculate(@Valid VacationPayRequest request) {
        VacationPayResponse response = vacationPayService.calculate(request);
        return ResponseEntity.ok(response);
    }
}
