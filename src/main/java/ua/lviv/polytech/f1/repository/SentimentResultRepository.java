package ua.lviv.polytech.f1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lviv.polytech.f1.model.SentimentResult;

public interface SentimentResultRepository extends JpaRepository<SentimentResult, java.util.UUID> {
}