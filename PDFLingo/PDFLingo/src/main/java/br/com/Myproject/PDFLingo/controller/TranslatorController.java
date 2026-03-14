package br.com.Myproject.PDFLingo.controller;

import br.com.Myproject.PDFLingo.model.TextRequest;
import br.com.Myproject.PDFLingo.service.AIExerciseService;
import br.com.Myproject.PDFLingo.translationClasses.TranslationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("translate")
@CrossOrigin(origins = "*")
public class TranslatorController {
    @Autowired
    private AIExerciseService service;

    @PostMapping
    public ResponseEntity<TranslationResponse> translate(@RequestBody TextRequest request) {
        TranslationResponse response = service.translating(request);
        return ResponseEntity.ok(response);
    }
}
