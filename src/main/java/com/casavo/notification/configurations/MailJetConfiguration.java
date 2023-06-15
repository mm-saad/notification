package com.casavo.notification.configurations;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailJetConfiguration {

    @Bean
    public MailjetClient mailJetClient(
            @Value("${mailJet.apiKey}") String apiKey,
            @Value("${mailJet.apiSecret}") String apiSecret) {

        ClientOptions options = ClientOptions.builder()
                .apiKey(apiKey)
                .apiSecretKey(apiSecret)
                .build();

        return new MailjetClient(options);
    }
}
