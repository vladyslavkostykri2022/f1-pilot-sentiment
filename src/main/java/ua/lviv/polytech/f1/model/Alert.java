package ua.lviv.polytech.f1.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter @Setter
public class Alert {
    @Id
    private UUID id = UUID.randomUUID();
    private Instant timestamp = Instant.now();
    private String severity;   // LOW / MEDIUM / HIGH
    private String message;
    private String pilotId;
}