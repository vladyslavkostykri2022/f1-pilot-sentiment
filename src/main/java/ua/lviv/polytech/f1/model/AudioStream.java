package ua.lviv.polytech.f1.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AudioStream {
    @Id
    private UUID id = UUID.randomUUID();
    private Instant timestamp = Instant.now();
    private String pilotId;
    private String language;
    @Column(length = 5000)
    private String text;           // транскрипція
}