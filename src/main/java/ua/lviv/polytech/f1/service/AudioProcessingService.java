package ua.lviv.polytech.f1.service;

import org.springframework.stereotype.Service;
import ua.lviv.polytech.f1.model.AudioStream;
import ua.lviv.polytech.f1.model.SentimentResult;

@Service
public class AudioProcessingService {

    private final SentimentAnalysisService sentimentService;

    public AudioProcessingService(SentimentAnalysisService sentimentService) {
        this.sentimentService = sentimentService;
    }

    public SentimentResult processSample(String pilotId, String language, String sampleText) {
        AudioStream stream = new AudioStream();
        stream.setPilotId(pilotId);
        stream.setLanguage(language);
        stream.setText(sampleText);

        return sentimentService.analyze(sampleText, language);
    }
}