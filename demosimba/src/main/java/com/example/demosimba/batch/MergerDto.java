package com.example.demosimba.batch;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MergerDto {
    String ticker;

    LocalDate startDate;

    LocalDate endDate;
}