package com.casavo.notification.repositories;

import com.casavo.notification.entities.Buyer;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface BuyerRepository extends CrudRepository<Buyer, String> {

    Stream<Buyer> findAllByZipCode(String zipCode);
}
