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
    private LocalDate start;
    private LocalDate end;

    public VacationPayRequest() {}

    public BigDecimal getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(BigDecimal averageSalary) {
        this.averageSalary = averageSalary;
    }

    public int getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(int vacationDays) {
        this.vacationDays = vacationDays;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }
}
