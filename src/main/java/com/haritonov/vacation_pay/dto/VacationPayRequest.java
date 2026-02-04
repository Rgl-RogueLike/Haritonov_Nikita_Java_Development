package com.haritonov.vacation_pay.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) для входящего запроса на расчет отпускных.
 * Содержит данные о зарплате и параметрах отпуска.
 * Валидация происходит через аннотации {@link NotNull} и {@link Positive}.
 */
public class VacationPayRequest {

    /**
     * Средняя заработная плата сотрудника за последние 12 месяцев.
     * Обязательное поле, должно быть положительным числом.
     */
    @NotNull(message = "Средняя зарплата не может быть пустой")
    @Positive(message = "Средняя зарплата должна быть больше нуля")
    private BigDecimal averageSalary;

    /**
     * Количество дней отпуска.
     * Используется для простого расчета (без учета праздничных дней).
     */
    @NotNull(message = "Количество дней не может быть пустым")
    @Positive(message = "Количество дней должно быть больше нуля")
    private Integer vacationDays;

    /**
     * Дата начала отпуска.
     * Используется для расчета с учетом праздничных дней.
     */
    private LocalDate vacationDate;

    /**
     * Конструктор по умолчанию.
     */
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

    public LocalDate getVacationDate() {
        return vacationDate;
    }

    public void setVacationDate(LocalDate vacationDate) {
        this.vacationDate = vacationDate;
    }
}
