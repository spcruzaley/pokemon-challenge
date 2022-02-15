package com.bankaya.challenge.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Component
public class RestApiClient {

    public static final String POKEMON_URL = "https://pokeapi.co/api/v2/pokemon/";

    private final RestTemplate restTemplate;

    private final ObjectMapper mapper;

    @Autowired
    public RestApiClient(RestTemplate restTemplate, ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }

    public String getProperty(String pokemon, String property) {
        String response = "";

        try {
            ResponseEntity<String> responseEntity = getPokemonInformation(pokemon);

            JsonNode node = mapper.readValue(responseEntity.getBody(), JsonNode.class);
            response = node.get(property).toString();
        } catch (IOException exception) {
            response = "Error mapping result for pokemon " + pokemon;
            log.error(exception.getMessage());
        } catch (RestClientException exception) {
            response = exception.getMessage();
            log.error(exception.getMessage());
        }

        return response;
    }

    private ResponseEntity<String> getPokemonInformation(String pokemon) throws RestClientException {
        String fullPokemonUrl = POKEMON_URL + pokemon;
        ResponseEntity<String> response;

        try {
            log.info("Calling url {}", fullPokemonUrl);
            System.setProperty("http.agent", "Chrome");
            response = restTemplate.getForEntity(fullPokemonUrl, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RestClientException("Error calling Pokemon API with response code " + response.getBody());
            }
        } catch (Exception e) {
            throw new RestClientException(e.getMessage());
        }

        return response;
    }

}
