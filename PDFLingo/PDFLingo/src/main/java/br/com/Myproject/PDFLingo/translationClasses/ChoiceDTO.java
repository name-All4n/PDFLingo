package br.com.Myproject.PDFLingo.translationClasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ChoiceDTO(MessageDTO message) {
}
