package com.bankaya.challenge.webservice;

import com.bankaya.challenge.api.RestApiClient;
import com.bankaya.challenge.database.AuditInformation;
import com.bankaya.challenge.database.AuditInformationService;
import com.bankaya.challenge.webservice.gen.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Endpoint
public class PokemonEndpoint {

    private static final String NAMESPACE_URI = "http://challenge.bankaya.com/webservice/gen";

    private final GenericResponse genericResponse;

    private final StatusComponent statusComponent;

    private final RestApiClient restApiClient;

    private final HttpServletRequest servletRequest;

    //private AuditInformationRepository auditInformationRepository;

    private final AuditInformationService auditInformationService;

    @Autowired
    public PokemonEndpoint(StatusComponent statusComponent, RestApiClient restApiClient,
                           HttpServletRequest servletRequest,
                           //AuditInformationRepository auditInformationRepository,
                           AuditInformationService auditInformationService) {
        this.restApiClient = restApiClient;
        this.statusComponent = statusComponent;
        this.servletRequest = servletRequest;
        this.auditInformationService = auditInformationService;
        //this.auditInformationRepository = auditInformationRepository;

        this.genericResponse = new GenericResponse();
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "abilitiesRequest")
    @ResponsePayload
    public GenericResponse getAbilities(@RequestPayload AbilitiesRequest request) {
        String auth = servletRequest.getHeader("Authorization");

        String pokemon = request.getPokemon();
        log.info("Setting abilities for {}", pokemon);

        String abilities = restApiClient.getProperty(pokemon, "abilities");
        this.genericResponse.setStatus(statusComponent.setDefaultStatus(abilities));

        //Save in DB
        saveInformationToDB("abilities");

        return genericResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "held_itemsRequest")
    @ResponsePayload
    public GenericResponse getHeldItems(@RequestPayload HeldItemsRequest request) {
        log.debug("Calling getHeldItems");

        String pokemon = request.getPokemon();
        log.info("Setting held items for {}", pokemon);

        String held_items = restApiClient.getProperty(pokemon, "held_items");
        this.genericResponse.setStatus(statusComponent.setDefaultStatus(held_items));

        //Save in DB
        saveInformationToDB("held_items");

        return genericResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "idRequest")
    @ResponsePayload
    public GenericResponse getId(@RequestPayload IdRequest request) {
        log.debug("Calling getId");

        String pokemon = request.getPokemon();
        log.info("Setting id for {}", pokemon);

        String id = restApiClient.getProperty(pokemon, "id");
        this.genericResponse.setStatus(statusComponent.setDefaultStatus(id));

        //Save in DB
        saveInformationToDB("id");

        return genericResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "location_area_encountersRequest")
    @ResponsePayload
    public GenericResponse getLocationAreaEncounters(@RequestPayload LocationAreaEncountersRequest request) {
        log.debug("Calling getLocationAreaEncounters");

        String pokemon = request.getPokemon();
        log.info("Setting location area encounters for {}", pokemon);

        String location_area_encounters = restApiClient.getProperty(pokemon, "location_area_encounters");
        this.genericResponse.setStatus(statusComponent.setDefaultStatus(location_area_encounters));

        //Save in DB
        saveInformationToDB("location_area_encounters");

        return genericResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "nameRequest")
    @ResponsePayload
    public GenericResponse getName(@RequestPayload NameRequest request) {
        log.debug("Calling getName");

        String pokemon = request.getPokemon();
        log.info("Setting name for {}", pokemon);

        String name = restApiClient.getProperty(pokemon, "name");
        this.genericResponse.setStatus(statusComponent.setDefaultStatus(name));

        //Save in DB
        saveInformationToDB("name");

        return genericResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "base_experienceRequest")
    @ResponsePayload
    public GenericResponse getBaseExperience(@RequestPayload BaseExperienceRequest request) {
        log.debug("Calling getBaseExperience");

        String pokemon = request.getPokemon();
        log.info("Setting base experience for {}", pokemon);

        String base_experience = restApiClient.getProperty(pokemon, "base_experience");
        this.genericResponse.setStatus(statusComponent.setDefaultStatus(base_experience));

        //Save in DB
        saveInformationToDB("base_experience");

        return genericResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "recordsRequest")
    @ResponsePayload
    public GenericResponse getRecords(@RequestPayload RecordsRequest request) {
        log.debug("Calling getRecords");

        List<AuditInformation> auditInformationList = auditInformationService.findAll();
        genericResponse.setStatus(statusComponent.setDefaultStatus(auditInformationList.toString()));

        return genericResponse;
    }

    private void saveInformationToDB(String method) {
        AuditInformation auditInformation = auditInformationService
                .save(AuditInformation.builder()
                        .ip(servletRequest.getRemoteAddr())
                        .dateTime(LocalDateTime.now())
                        .method(method)
                        .build());

        log.info("Information saved {}", auditInformation);
    }
}
