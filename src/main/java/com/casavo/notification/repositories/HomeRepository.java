package com.casavo.notification.repositories;

import com.casavo.notification.entities.Home;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface HomeRepository extends CrudRepository<Home, String> {

    Stream<Home> findByDate(String date);
}
