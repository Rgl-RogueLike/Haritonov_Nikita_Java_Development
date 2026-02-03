package com.haritonov.vacation_pay.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public class VacationPayRequest {

    @NotNull(message = "Средняя зарплата не может быть пустой")
    @Positive(message = "Средняя зарплата должна быть больше нуля")
    private BigDecimal averageSalary;

    private Integer vacationDays;
    private LocalDate startDate;
    private LocalDate endDate;

    public VacationPayRequest() {}

    public BigDecimal getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(BigDecimal averageSalary) {
        this.averageSalary = averageSalary;
    }

    public Integer getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(Integer vacationDays) {
        this.vacationDays = vacationDays;
    }

    public LocalDate getStart() {
        return startDate;
    }

    public void setStart(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEnd() {
        return endDate;
    }

    public void setEnd(LocalDate endDate) {
        this.endDate = endDate;
    }
}
