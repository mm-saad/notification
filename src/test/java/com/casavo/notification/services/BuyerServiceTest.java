package com.casavo.notification.services;

import com.casavo.notification.CommonTestConfiguration;
import com.casavo.notification.entities.Buyer;
import com.casavo.notification.repositories.BuyerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.doReturn;

@SpringBootTest(classes = BuyerService.class)
class BuyerServiceTest extends CommonTestConfiguration {

    @Autowired
    private BuyerService buyerService;

    @MockBean
    private BuyerRepository buyerRepository;

    @Test
    void findAllByZipCode_nominalCase() {

        //Given
        String zipCode = "75000";
        Buyer buyer = Buyer.builder()
                .id("1")
                .name("buyer 1")
                .build();

        doReturn(Stream.of(buyer)).when(buyerRepository).findAllByZipCode(zipCode);

        //When
        Stream<Buyer> allByZipCode = buyerService.findAllByZipCode(zipCode);

        //Then
        List<Buyer> houses = allByZipCode.collect(Collectors.toList());
        Assertions.assertThat(houses).hasSize(1);
        Assertions.assertThat(houses.get(0)).isEqualTo(buyer);
    }

}