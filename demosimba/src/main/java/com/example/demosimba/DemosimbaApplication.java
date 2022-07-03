package com.example.demosimba;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class DemosimbaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemosimbaApplication.class, args);
    }

}
