package br.com.Myproject.PDFLingo.service;

import br.com.Myproject.PDFLingo.model.DataTranslation;
import tools.jackson.databind.ObjectMapper;

import java.net.URLEncoder;

public class ConsultMyMemory {
    public static String getTranslation (String text) {
        ObjectMapper mapper = new ObjectMapper();
        ConsumptionAPI consumo =  new ConsumptionAPI();

        String texto = URLEncoder.encode(text);
        String langpair = URLEncoder.encode("en|pt-br");

        String url = "https://api.mymemory.translated.net/get?q=" + texto + "&langpair=" + langpair;
        String json = consumo.getData(url);

        DataTranslation traducao;
        try {
            traducao = mapper.readValue(json, DataTranslation.class);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return traducao.dataResponse().translatedText();
    }
}
