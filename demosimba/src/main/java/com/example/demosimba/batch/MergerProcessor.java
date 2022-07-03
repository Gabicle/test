package com.example.demosimba.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

public class MergerProcessor implements ItemProcessor<MergerDto, IssuersUnderOpaEntity> {
    @Override
    public IssuersUnderOpaEntity process(MergerDto mergerDto) {
        return IssuersUnderOpaEntity.builder()
                .ticker(mergerDto.getTicker())
                .startDate(mergerDto.getStartDate()).endDate(mergerDto.getEndDate()).build();
    }
}
