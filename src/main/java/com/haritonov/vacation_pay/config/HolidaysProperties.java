package com.haritonov.vacation_pay.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Класс для чтения настроек праздничных дней из конфигурации (application.yml).
 * Связан с префиксом {@code holidays} в YAML-файле через {@link ConfigurationProperties}.
 * При инициализации преобразует строки дат в объекты {@link LocalDate} для быстрого поиска.
 */
@Component
@ConfigurationProperties(prefix = "holidays")
public class HolidaysProperties {

    /**
     * Список строковых дат, считанных из YAML (например, "2024-01-01").
     */
    private List<String> dates;

    /**
     * Кэш (Set) распарсенных объектов {@link LocalDate}.
     */
    private Set<LocalDate> parsedDates;

    /**
     * Метод, запускаемый после создания бина.
     * Преобразует поток строк {@link #dates} в {@link Set<LocalDate>}.
     * Отсеивает некорректные даты, выводя ошибку в System.err.
     */
    @PostConstruct
    public void init() {
        if (dates != null) {
            parsedDates = dates.stream()
                    .map(dateStr -> {
                        try {
                            return LocalDate.parse(dateStr);
                        } catch (DateTimeParseException exception) {
                            System.err.println("Ошибка парсинга даты: " + dateStr);
                            return null;
                        }
                    })
                    .filter(date -> date != null)
                    .collect(Collectors.toSet());
        } else {
            parsedDates = new HashSet<>();
        }
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public boolean isHolidays(LocalDate date) {
        return parsedDates.contains(date);
    }
}
