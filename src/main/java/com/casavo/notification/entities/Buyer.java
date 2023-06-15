package com.casavo.notification.entities;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Document
public class Buyer {

    @Id
    private final String id;
    private final String name;
    private final String email;
    private final String tel;
    @Indexed
    private final String zipCode;

    @Builder
    @PersistenceCreator
    public Buyer(String id, String name, String email, String tel, String zipCode) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Buyer buyer = (Buyer) o;

        return Objects.equals(id, buyer.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
