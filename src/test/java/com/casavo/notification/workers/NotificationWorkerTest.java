package com.casavo.notification.workers;

import com.casavo.notification.CommonTestConfiguration;
import com.casavo.notification.entities.Buyer;
import com.casavo.notification.entities.Home;
import com.casavo.notification.helpers.DateHelper;
import com.casavo.notification.services.BuyerService;
import com.casavo.notification.services.HomeService;
import com.casavo.notification.services.NotificationService;
import com.mailjet.client.MailjetRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(classes = NotificationWorker.class)
class NotificationWorkerTest extends CommonTestConfiguration {

    @Autowired
    private NotificationWorker notificationWorker;

    @MockBean
    private BuyerService buyerService;
    @MockBean
    private HomeService homeService;
    @MockBean
    private NotificationService notificationService;
    @MockBean
    private DateHelper dateHelper;

    @Captor
    ArgumentCaptor<Buyer> buyerArgumentCaptor;
    @Captor
    ArgumentCaptor<List<Home>> housesArgumentCaptor;

    @Test
    void notifyBuyers_nominalCase() {

        //Given
        LocalDate now = LocalDate.of(2023, 6, 15);
        Home paris = new Home("paris", "home in paris", "75000", BigDecimal.valueOf(300000), "2023-06-14");
        Buyer oliver = new Buyer("oliver", "Oliver", "oliver@gmail.com", "1234567890", "75000");

        doReturn(now).when(dateHelper).getNow();
        doReturn(Stream.of(paris)).when(homeService).findByDate(now);
        doReturn(Stream.of(oliver)).when(buyerService).findAllByZipCode(paris.getZipCode());
        doNothing().when(notificationService).sendSms(buyerArgumentCaptor.capture(), housesArgumentCaptor.capture());

        //When
        notificationWorker.notifyBuyers();

        //Then
        Buyer buyerNotified = buyerArgumentCaptor.getValue();
        assertThat(buyerNotified.getId()).isEqualTo(oliver.getId());

        List<Home> houses = housesArgumentCaptor.getValue();
        assertThat(houses).hasSize(1);
        assertThat(houses.get(0).getId()).isEqualTo(paris.getId());
    }
}