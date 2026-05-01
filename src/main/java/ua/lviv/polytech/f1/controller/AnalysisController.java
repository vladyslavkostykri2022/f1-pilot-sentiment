package ua.lviv.polytech.f1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.lviv.polytech.f1.model.SentimentResult;
import ua.lviv.polytech.f1.repository.SentimentResultRepository;
import ua.lviv.polytech.f1.service.AudioProcessingService;
import ua.lviv.polytech.f1.service.VoskSpeechToTextService;

import java.util.List;

@Controller
public class AnalysisController {

    private final AudioProcessingService processingService;
    private final VoskSpeechToTextService voskService;
    private final SentimentResultRepository resultRepository;

    public AnalysisController(AudioProcessingService processingService,
                              VoskSpeechToTextService voskService,
                              SentimentResultRepository resultRepository) {
        this.processingService = processingService;
        this.voskService = voskService;
        this.resultRepository = resultRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    // === НОВИЙ МЕТОД ДЛЯ ЗАВАНТАЖЕННЯ АУДІО ===
    @PostMapping("/analyze-audio")
    public String analyzeAudio(@RequestParam("file") MultipartFile file,
                               @RequestParam String pilotId,
                               Model model) throws Exception {

        if (file.isEmpty()) {
            model.addAttribute("error", "Файл не вибрано!");
            return "index";
        }

        // 1. Конвертуємо аудіо у текст за допомогою Vosk
        byte[] audioBytes = file.getBytes();
        String transcribedText = voskService.transcribe(audioBytes);

        // 2. Аналізуємо отриманий текст
        SentimentResult result = processingService.processSample(pilotId, "en", transcribedText);
        result.setPilotId(pilotId);
        resultRepository.save(result);

        model.addAttribute("result", result);
        model.addAttribute("text", transcribedText);
        model.addAttribute("filename", file.getOriginalFilename());

        return "result";
    }

    @GetMapping("/history")
    public String history(Model model) {
        List<SentimentResult> results = resultRepository.findAll();
        model.addAttribute("results", results);
        return "history";
    }
}