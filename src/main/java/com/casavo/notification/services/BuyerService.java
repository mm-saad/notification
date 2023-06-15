package com.casavo.notification.services;

import com.casavo.notification.entities.Buyer;
import com.casavo.notification.repositories.BuyerRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class BuyerService {

    private final BuyerRepository buyerRepository;

    public BuyerService(BuyerRepository buyerRepository) {

        this.buyerRepository = buyerRepository;
    }

    public Stream<Buyer> findAllByZipCode(String zipCode) {

        return buyerRepository.findAllByZipCode(zipCode);
    }
}
