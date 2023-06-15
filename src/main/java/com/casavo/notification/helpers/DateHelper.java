package com.casavo.notification.helpers;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateHelper {

    public LocalDate getNow() {

        return LocalDate.now();
    }
}
