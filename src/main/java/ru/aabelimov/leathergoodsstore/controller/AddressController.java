package ru.aabelimov.leathergoodsstore.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Value("${credentials.dadata.token}")
    private String token;

    @PostMapping
    public String checkAddress(@RequestBody String query) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Token " + token);
        HttpEntity<String> request = new HttpEntity<>(query, headers);
//        restTemplate.postForObject("http://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/address", request, String.class)
        return restTemplate.postForObject("http://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/address", request, String.class);
    }
}
