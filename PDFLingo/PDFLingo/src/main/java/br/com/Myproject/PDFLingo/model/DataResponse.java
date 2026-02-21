package br.com.Myproject.PDFLingo.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataResponse(@JsonAlias("translatedText") String translatedText) {
}
