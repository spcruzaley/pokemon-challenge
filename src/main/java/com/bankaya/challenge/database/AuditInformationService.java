package com.bankaya.challenge.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditInformationService {

    private final AuditInformationRepository auditInformationRepository;

    @Autowired
    public AuditInformationService(AuditInformationRepository auditInformationRepository) {
        this.auditInformationRepository = auditInformationRepository;
    }

    public AuditInformation save(AuditInformation auditInformation) {
        return auditInformationRepository.save(auditInformation);
    }

    public List<AuditInformation> findAll() {
        return (List<AuditInformation>) auditInformationRepository.findAll();
    }

}
