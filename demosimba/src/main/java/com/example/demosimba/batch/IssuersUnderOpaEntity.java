package com.example.demosimba.batch;

import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "jobs")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssuersUnderOpaEntity {

    @Id
    String ticker;

    LocalDate startDate;

    LocalDate endDate;

}
