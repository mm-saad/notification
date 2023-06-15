package com.casavo.notification.workers;

import com.casavo.notification.entities.Home;
import com.casavo.notification.helpers.DateHelper;
import com.casavo.notification.services.BuyerService;
import com.casavo.notification.services.HomeService;
import com.casavo.notification.services.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component("NotificationWorker")
public class NotificationWorker {

    private static final String CRON = "${notification.cron:0 0 12 * * *}"; //once a day at 12 o'clock

    private final HomeService homeService;
    private final BuyerService buyerService;
    private final NotificationService notificationService;
    private final DateHelper dateHelper;

    public NotificationWorker(
            HomeService homeService,
            BuyerService buyerService,
            NotificationService notificationService,
            DateHelper dateHelper) {

        this.homeService = homeService;
        this.buyerService = buyerService;
        this.notificationService = notificationService;
        this.dateHelper = dateHelper;
    }

    @Scheduled(cron = CRON, zone = "Europe/Paris")
    public void notifyBuyers() {

        LocalDate localDate = dateHelper.getNow();

        Map<String, List<Home>> homesByZipCode = new HashMap<>();
        //collect houses by zipcode
        homeService.findByDate(localDate).forEach(home -> {
            homesByZipCode.putIfAbsent(home.getZipCode(), new ArrayList<>());
            homesByZipCode.get(home.getZipCode()).add(home);
        });

        //Notify each buyer of new houses by zipcode
        for (Map.Entry<String, List<Home>> entry : homesByZipCode.entrySet()) {
            String zipcode = entry.getKey();
            List<Home> houses = entry.getValue();

            buyerService.findAllByZipCode(zipcode)
                    .forEach(buyer -> notificationService.sendSms(buyer, houses));
        }
    }
}
