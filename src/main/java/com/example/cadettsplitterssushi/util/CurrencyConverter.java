package com.example.cadettsplitterssushi.util;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class CurrencyConverter {


    public double convertFromSEKToEUR(double priceSEK){
        RestClient restClient = RestClient.create();

        Map<String, Double> rates = (Map<String, Double>) restClient.get()
                .uri("https://api.frankfurter.dev/v1/latest?base=SEK&to=EUR")
                .retrieve()
                .body(Map.class).get("rates");
        double rate = rates.get("EUR");

        return priceSEK * rate;
    }
}
