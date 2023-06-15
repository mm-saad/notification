package com.casavo.notification.services;

import com.casavo.notification.CommonTestConfiguration;
import com.casavo.notification.entities.Home;
import com.casavo.notification.repositories.HomeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.doReturn;

@SpringBootTest(classes = HomeService.class)
class HomeServiceTest extends CommonTestConfiguration {

    @Autowired
    private HomeService homeService;

    @MockBean
    private HomeRepository homeRepository;

    @Test
    void findByDate_nominalCase() {

        //Given
        LocalDate localDate = LocalDate.of(2023, 6, 15);
        Home home = Home.builder()
                .id("1")
                .description("home 1")
                .build();

        doReturn(Stream.of(home)).when(homeRepository).findByDate(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));

        //When
        Stream<Home> byDate = homeService.findByDate(localDate);

        //Then
        List<Home> houses = byDate.collect(Collectors.toList());
        Assertions.assertThat(houses).hasSize(1);
        Assertions.assertThat(houses.get(0)).isEqualTo(home);
    }
}