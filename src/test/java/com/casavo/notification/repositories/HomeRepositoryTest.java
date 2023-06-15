package com.casavo.notification.repositories;

import com.casavo.notification.CommonTestConfiguration;
import com.casavo.notification.entities.Buyer;
import com.casavo.notification.entities.Home;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@EnableMongoRepositories(
        value = {
                "com.casavo.notification.repositories",
        },
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {HomeRepository.class})
        }
)
@DataMongoTest
class HomeRepositoryTest extends CommonTestConfiguration {

    @Autowired
    private HomeRepository homeRepository;

    @AfterEach
    public void tearDown() {

        homeRepository.deleteAll();
    }

    @Test
    void findAllByZipCode_nominalCase() {

        //Given
        String date = LocalDate.of(2023, 6, 15).format(DateTimeFormatter.ISO_LOCAL_DATE);
        Home paris = new Home(
                "paris",
                "home in paris",
                "75000",
                BigDecimal.valueOf(300000),
                "2023-06-14"
        );

        Home creil = new Home(
                "creil",
                "home in creil",
                "60100",
                BigDecimal.valueOf(200000),
                "2023-06-15"
        );

        homeRepository.save(paris);
        homeRepository.save(creil);

        //When
        List<Home> collect = homeRepository.findByDate(date).collect(Collectors.toList());

        //Then
        Assertions.assertThat(collect).hasSize(1);
        Assertions.assertThat(collect.get(0).getId()).isEqualTo(creil.getId()).isEqualTo("creil");
        Assertions.assertThat(collect.get(0).getDescription()).isEqualTo(creil.getDescription()).isEqualTo("home in creil");
        Assertions.assertThat(collect.get(0).getZipCode()).isEqualTo(creil.getZipCode()).isEqualTo("60100");
        Assertions.assertThat(collect.get(0).getPrice()).isEqualTo(creil.getPrice()).isEqualTo(BigDecimal.valueOf(200000));
        Assertions.assertThat(collect.get(0).getDate()).isEqualTo(creil.getDate()).isEqualTo(date);
    }
}