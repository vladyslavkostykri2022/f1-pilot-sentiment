package ua.lviv.polytech.f1.service;

import org.springframework.stereotype.Service;
import org.vosk.LibVosk;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;

@Service
public class VoskSpeechToTextService {

    private final Model model;

    public VoskSpeechToTextService() throws IOException {
        //LibVosk.setLogLevel(0); // вимикаємо логи Vosk
        this.model = new Model("src/main/resources/vosk-model-small-en"); // шлях до моделі
    }

    public String transcribe(byte[] audioBytes) throws Exception {
        try (Recognizer recognizer = new Recognizer(model, 16000)) {
            recognizer.setWords(true);

            // Vosk працює тільки з 16kHz mono PCM
            AudioInputStream ais = AudioSystem.getAudioInputStream(new ByteArrayInputStream(audioBytes));
            // Якщо потрібно — тут можна конвертувати, але для простоти припустимо, що файл вже правильний

            byte[] buffer = new byte[4096];
            int nbytes;
            while ((nbytes = ais.read(buffer)) >= 0) {
                recognizer.acceptWaveForm(buffer, nbytes);
            }

            String result = recognizer.getFinalResult();
            // Парсимо JSON і беремо тільки текст
            return result.contains("\"text\"")
                    ? result.split("\"text\" : \"")[1].split("\"")[0]
                    : "Не вдалося розпізнати";
        }
    }
}