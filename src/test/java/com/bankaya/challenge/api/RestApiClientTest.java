package com.bankaya.challenge.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestApiClientTest {

    private static final String POKEMON_API = "https://pokeapi.co/api/v2/pokemon/";

    public static RestTemplate restTemplate;

    @InjectMocks
    public static RestApiClient restApiClient;

	@Spy
	private static ObjectMapper objectMapper;

    @BeforeAll
    static void setUp() {
        restTemplate = mock(RestTemplate.class);
        objectMapper = new ObjectMapper();

        RestTemplateBuilder mockBuilder = mock(RestTemplateBuilder.class);
        when(mockBuilder.build()).thenReturn(restTemplate);

        restApiClient = new RestApiClient(restTemplate, objectMapper);
    }

    @Test
    void testGetProperty() {
        String pokemon = "pikachu";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

        when(restTemplate.getForEntity(POKEMON_API + pokemon, String.class))
                .thenReturn(responseEntity);
        String actualAbilities = restApiClient.getProperty("pikachu", "abilities");

        assertEquals(expectedAbilities, actualAbilities);
    }

    private static String response = "{" +
            "  \"abilities\": [" +
            "    {" +
            "      \"ability\": {" +
            "        \"name\": \"static\"," +
            "        \"url\": \"https://pokeapi.co/api/v2/ability/9/\"" +
            "      }," +
            "      \"is_hidden\": false," +
            "      \"slot\": 1" +
            "    }," +
            "    {" +
            "      \"ability\": {" +
            "        \"name\": \"lightning-rod\"," +
            "        \"url\": \"https://pokeapi.co/api/v2/ability/31/\"" +
            "      }," +
            "      \"is_hidden\": true," +
            "      \"slot\": 3" +
            "    }" +
            "  ]" +
            "}";

    private static String expectedAbilities = "[{\"ability\":{\"name\":\"static\"," +
            "\"url\":\"https://pokeapi.co/api/v2/ability/9/\"},\"is_hidden\":false,\"slot\":1}," +
            "{\"ability\":{\"name\":\"lightning-rod\",\"url\":\"https://pokeapi.co/api/v2/ability/31/\"}," +
            "\"is_hidden\":true,\"slot\":3}]";

}