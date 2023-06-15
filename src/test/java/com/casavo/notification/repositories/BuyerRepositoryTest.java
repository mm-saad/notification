package com.casavo.notification.repositories;

import com.casavo.notification.CommonTestConfiguration;
import com.casavo.notification.entities.Buyer;
import org.assertj.core.api.Assertions;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;
import java.util.stream.Collectors;

@EnableMongoRepositories(
        value = {
                "com.casavo.notification.repositories",
        },
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BuyerRepository.class})
        }
)
@DataMongoTest
class BuyerRepositoryTest extends CommonTestConfiguration {

    @Autowired
    private BuyerRepository buyerRepository;

    @AfterEach
    public void tearDown() {

        buyerRepository.deleteAll();
    }

    @Test
    void findAllByZipCode_nominalCase() {

        //Given
        Buyer oliver = new Buyer(
                "oliver",
                "Oliver",
                "oliver@gmail.com",
                "1234567890",
                "75000"
        );
        Buyer max = new Buyer(
                "max",
                "max",
                "max@gmail.com",
                "0123456789",
                "60000"
        );

        buyerRepository.save(oliver);
        buyerRepository.save(max);

        //When
        List<Buyer> collect = buyerRepository.findAllByZipCode("60000").collect(Collectors.toList());

        //Then
        Assertions.assertThat(collect).hasSize(1);
        Assertions.assertThat(collect.get(0).getId()).isEqualTo(max.getId()).isEqualTo("max");
        Assertions.assertThat(collect.get(0).getName()).isEqualTo(max.getName()).isEqualTo("max");
        Assertions.assertThat(collect.get(0).getEmail()).isEqualTo(max.getEmail()).isEqualTo("max@gmail.com");
        Assertions.assertThat(collect.get(0).getTel()).isEqualTo(max.getTel()).isEqualTo("0123456789");
        Assertions.assertThat(collect.get(0).getZipCode()).isEqualTo(max.getZipCode()).isEqualTo("60000");
    }
}