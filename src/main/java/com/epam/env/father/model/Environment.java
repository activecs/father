package com.epam.env.father.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(of = "id")
public class Environment {

    @Id
    public String id;
    @DBRef
    private Client reservedBy;
    private LocalDateTime reservationExpiration;

    @Override
    public String toString() {
        return "Environment id='" + id + "\', reserved by=" + reservedBy +
                ", reservation expiration date=" + reservationExpiration;
    }
}
