package br.com.Myproject.PDFLingo.controller;

import br.com.Myproject.PDFLingo.model.TextRequest;
import br.com.Myproject.PDFLingo.model.TextResponse;
import br.com.Myproject.PDFLingo.service.ConsultMyMemory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("translate")
@CrossOrigin(origins = "*")
public class TranslatorController {
    @PostMapping
    public TextResponse translate(@RequestBody TextRequest request) {
        var originalPhearse = request.phrase();
        var translatedPhrase = ConsultMyMemory.getTranslation(originalPhearse);
        return new TextResponse(translatedPhrase);
    }


}
