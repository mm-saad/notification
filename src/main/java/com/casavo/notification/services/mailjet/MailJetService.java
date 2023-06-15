package com.casavo.notification.services.mailjet;

import com.casavo.notification.entities.Buyer;
import com.casavo.notification.entities.Home;
import com.casavo.notification.services.NotificationService;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.sms.SmsSend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MailJetService implements NotificationService {

    private final MailjetClient mailjetClient;

    public MailJetService(MailjetClient mailjetClient) {

        this.mailjetClient = mailjetClient;
    }

    @Override
    public void sendSms(Buyer buyer, List<Home> houses) {

        MailjetRequest mailjetRequest = new MailjetRequest(SmsSend.resource)
                .property(SmsSend.FROM, "MJPilot")
                .property(SmsSend.TO, buyer.getTel())
                .property(SmsSend.TEXT, String.format("There are [%d] new houses for selling in your town, check our website !", houses.size()));

        try {
            // send the request
            mailjetClient.post(mailjetRequest);
        } catch (MailjetException e) {
            log.warn("Error sending sms to [{}] (name [{}])", buyer.getTel(), buyer.getName(), e);
        }
    }
}
