package com.bankaya.challenge.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AuditInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ip;
    private LocalDateTime dateTime;
    private String method;

}
