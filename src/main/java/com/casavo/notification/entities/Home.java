package com.casavo.notification.entities;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Document
public class Home {

    @Id
    private final String id;
    private final String description;
    private final String zipCode;
    private final BigDecimal price;
    @Indexed
    private final String date; //For simplification let's imagine the date is with format yyyy-mm-dd. We should use LocalDate for this field

    @Builder
    @PersistenceCreator
    public Home(String id, String description, String zipCode, BigDecimal price, String date) {

        this.id = id;
        this.description = description;
        this.zipCode = zipCode;
        this.price = price;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Home home = (Home) o;

        return Objects.equals(id, home.id);
    }

    @Override
    public int hashCode() {

        return id != null ? id.hashCode() : 0;
    }
}
