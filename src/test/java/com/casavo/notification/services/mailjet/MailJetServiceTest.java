package com.casavo.notification.services.mailjet;

import com.casavo.notification.CommonTestConfiguration;
import com.casavo.notification.entities.Buyer;
import com.casavo.notification.entities.Home;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.errors.MailjetException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MailJetService.class)
class MailJetServiceTest extends CommonTestConfiguration {

    @Autowired
    private MailJetService mailJetService;

    @MockBean
    private MailjetClient mailjetClient;

    @Captor
    ArgumentCaptor<MailjetRequest> mailjetRequestCaptor;

    @Test
    void sendSms_nominalCase() throws MailjetException {

        //Given
        Buyer buyer = Buyer.builder()
                .id("buyer")
                .name("buyer 1")
                .tel("1234567890")
                .build();
        Home home = Home.builder()
                .id("home")
                .description("home 1")
                .build();

        doReturn(null).when(mailjetClient).post(mailjetRequestCaptor.capture());

        //When
        mailJetService.sendSms(buyer, singletonList(home));

        //Then
        MailjetRequest request = mailjetRequestCaptor.getValue();
        assertThat(request.getBodyJSON().get("Text")).isEqualTo("There are [1] new houses for selling in your town, check our website !");
        assertThat(request.getBodyJSON().get("From")).isEqualTo("MJPilot");
        assertThat(request.getBodyJSON().get("To")).isEqualTo(buyer.getTel());
    }

    @Test
    void sendSms_shouldSoftlyHandleMailJetException() throws MailjetException {

        //Given
        Buyer buyer = Buyer.builder()
                .id("buyer")
                .name("buyer 1")
                .tel("1234567890")
                .build();
        Home home = Home.builder()
                .id("home")
                .description("home 1")
                .build();

        doThrow(new MailjetException("error")).when(mailjetClient).post(any());

        //When
        mailJetService.sendSms(buyer, singletonList(home));

        //Then
        verify(mailjetClient, times(1)).post(any());
    }
}