package com.casavo.notification.services;

import com.casavo.notification.entities.Buyer;
import com.casavo.notification.entities.Home;

import java.util.List;

public interface NotificationService {
    void sendSms(Buyer buyer, List<Home> houses);
}
