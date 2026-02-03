package com.haritonov.vacation_pay.service;

import com.haritonov.vacation_pay.dto.VacationPayRequest;
import com.haritonov.vacation_pay.dto.VacationPayResponse;

public interface VacationPayService {

    VacationPayResponse calculate(VacationPayRequest request);

}
