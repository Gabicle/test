package com.example.demosimba.batch;

import org.springframework.batch.item.ItemReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class MergersRestReader implements ItemReader<MergerDto> {

    private final RestTemplate restTemplate;

    private int nextIndex;
    private List<MergerDto> issuersUnderOpa;

    public MergersRestReader() {
        this.restTemplate = new RestTemplate();
        nextIndex = 0;
    }

    @Override
    public MergerDto read() throws Exception {
        if (isDataInitialialized()){
            issuersUnderOpa = fetchDataFromApi();
        }

        MergerDto merger = null;
        if (nextIndex < issuersUnderOpa.size()) {
            merger = issuersUnderOpa.get(nextIndex);
            nextIndex++;
        } else {
            nextIndex = 0;
            issuersUnderOpa = null;
        }

        return merger;
    }

    private List<MergerDto> fetchDataFromApi() {
        String apiUrl = "http://localhost:8081/opa";
        ResponseEntity<MergerDto[]> response =
                restTemplate.getForEntity(apiUrl, MergerDto[].class);
        MergerDto[] opaData = response.getBody();
        return Arrays.asList(opaData);
    }

    private boolean isDataInitialialized() {
        return this.issuersUnderOpa == null;
    }
}
