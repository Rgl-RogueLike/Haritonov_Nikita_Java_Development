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

@Component
@ConfigurationProperties(prefix = "holidays")
public class HolidaysProperties {

    private List<String> dates;
    private Set<LocalDate> parsedDates;

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
