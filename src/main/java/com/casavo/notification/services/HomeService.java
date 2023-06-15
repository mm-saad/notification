package com.casavo.notification.services;

import com.casavo.notification.entities.Home;
import com.casavo.notification.repositories.HomeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

@Service
public class HomeService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private final HomeRepository homeRepository;

    public HomeService(HomeRepository homeRepository) {

        this.homeRepository = homeRepository;
    }

    public Stream<Home> findByDate(LocalDate localDate) {

        return homeRepository.findByDate(localDate.format(DATE_TIME_FORMATTER));
    }
}
