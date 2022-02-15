package com.bankaya.challenge.database;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuditInformationServiceTest {

    @Mock
    private static AuditInformationRepository auditInformationRepository;

    private static AuditInformationService auditInformationService;

    private static LocalDateTime localDateTime;

    @BeforeAll
    static void setUp() {
        localDateTime = LocalDateTime.now();
        auditInformationRepository = mock(AuditInformationRepository.class);

        auditInformationService = new AuditInformationService(auditInformationRepository);
    }

    @Test
    void testSave() {
        AuditInformation expectedAuditInformation = createAuditInformationTest();

        when(auditInformationRepository.save(any(AuditInformation.class)))
                .thenReturn(expectedAuditInformation);
        AuditInformation actualAuditInformation = auditInformationService.save(expectedAuditInformation);

        assertEquals(expectedAuditInformation.toString(), actualAuditInformation.toString());
    }

    @Test
    void testFindAll() {
        int expectedElements = 5;
        List<AuditInformation> expectedAuditInformationList = createAuditInformationTest(expectedElements);

        when(auditInformationRepository.findAll()).thenReturn(expectedAuditInformationList);
        List<AuditInformation> actualAuditInformationList = auditInformationService.findAll();

        assertEquals(expectedElements, actualAuditInformationList.size());
        assertArrayEquals(expectedAuditInformationList.toArray(), actualAuditInformationList.toArray());
    }

    private AuditInformation createAuditInformationTest() {
        return AuditInformation.builder()
                .ip("127.0.0.1")
                .dateTime(localDateTime)
                .method("method")
                .build();
    }

    private List<AuditInformation> createAuditInformationTest(int elements) {
        return IntStream.range(0, elements)
                .mapToObj(i -> createAuditInformationTest())
                .collect(Collectors.toList());
    }

}