package ua.lviv.polytech.f1.service;

import org.springframework.stereotype.Service;
import ua.lviv.polytech.f1.model.SentimentResult;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

@Service
public class SentimentAnalysisService {

    private final Random random = new Random();

    /**
     * Динамічний Mock-сервіс для демонстрації
     * Результат трохи відрізняється при кожному виклику
     */
    public SentimentResult analyze(String text, String language) {
        System.out.println("🔍 [SentimentAnalysisService] Аналіз тексту: " + text);

        String lowerText = text.toLowerCase().trim();

        // Підрахунок ключових слів
        int stressScore = 0;
        int positiveScore = 0;

        String[] stressWords = {"no", "can't", "gone", "crash", "problem", "tyre", "lose", "fuck", "shit", "oh no", "help", "danger", "slow", "late", "box box"};
        String[] positiveWords = {"good", "great", "yes", "podium", "win", "perfect", "nice", "excellent"};

        for (String word : stressWords) {
            if (lowerText.contains(word)) stressScore++;
        }
        for (String word : positiveWords) {
            if (lowerText.contains(word)) positiveScore++;
        }

        // Базовий рівень стресу
        double baseStress = Math.min(0.95, (stressScore * 0.25) - (positiveScore * 0.2));

        // Додаємо невелику випадковість (шум)
        double noise = (random.nextDouble() * 0.3) - 0.15; // від -0.15 до +0.15
        double stressLevel = Math.max(0.0, Math.min(1.0, baseStress + noise));

        // Визначаємо тональність
        String polarity;
        if (stressLevel > 0.68) {
            polarity = "NEGATIVE";
        } else if (stressLevel < 0.32) {
            polarity = "POSITIVE";
        } else {
            polarity = "NEUTRAL";
        }

        // Випадкова впевненість (85–94%)
        double confidence = 0.85 + (random.nextDouble() * 0.09);

        SentimentResult result = new SentimentResult();
        result.setId(UUID.randomUUID());
        result.setTimestamp(Instant.now());
        result.setPolarity(polarity);
        result.setStressLevel(Math.round(stressLevel * 100.0) / 100.0); // округлення до 2 знаків
        result.setConfidence(Math.round(confidence * 100.0) / 100.0);
        result.setEmotions("stress:" + String.format("%.2f", stressLevel));
        result.setPilotId("DEMO");

        System.out.println("✅ [SentimentAnalysisService] Результат: " + polarity +
                " | Стрес: " + String.format("%.2f", stressLevel) + " | Confidence: " + String.format("%.2f", confidence));

        return result;
    }
}