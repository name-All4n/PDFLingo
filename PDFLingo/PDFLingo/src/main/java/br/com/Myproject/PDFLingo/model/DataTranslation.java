package br.com.Myproject.PDFLingo.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataTranslation(@JsonAlias("responseData")
                            DataResponse dataResponse) {
}
