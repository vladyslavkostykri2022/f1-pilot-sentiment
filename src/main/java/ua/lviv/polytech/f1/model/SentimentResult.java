package ua.lviv.polytech.f1.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "sentiment_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SentimentResult {

    @Id
    private UUID id = UUID.randomUUID();

    private Instant timestamp = Instant.now();

    private String pilotId;

    @Column(length = 20)
    private String polarity;        // POSITIVE / NEGATIVE / NEUTRAL

    private double stressLevel;     // 0.0 - 1.0

    private double confidence;

    @Column(length = 500)
    private String emotions;
}