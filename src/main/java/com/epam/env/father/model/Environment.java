package com.epam.env.father.model;

import static java.text.MessageFormat.format;
import static java.util.Objects.isNull;

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
    private String country;
    private LocalDateTime reservationExpiration;

    @Override
    public String toString() {
        if (isNull(reservedBy))
            return format("Environment '{0}' is free", id);
        else
            return format("Environment '{0}' reserved by {1}, reservation expiration date {2}", id, reservedBy, reservationExpiration);
        
    }
}
