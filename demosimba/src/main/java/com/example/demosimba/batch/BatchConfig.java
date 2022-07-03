package com.example.demosimba.batch;

import com.fasterxml.jackson.databind.BeanProperty;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;


    @Bean
    public ItemReader<MergerDto> itemReader(){
        return new MergersRestReader();
    }

    @Bean
    public ItemProcessor<MergerDto, IssuersUnderOpaEntity> itemProcessor() {
        return new MergerProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<IssuersUnderOpaEntity> itemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<IssuersUnderOpaEntity>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO jobs ( ticker,  start_date, end_date ) VALUES ( " +
                        ":ticker, :startDate, :endDate )")
                .dataSource(dataSource).build();    }

    @Bean
    public Step mergerJobStep(ItemReader<MergerDto> itemReader,
                              ItemWriter<IssuersUnderOpaEntity> itemWriter,
                              ItemProcessor<MergerDto, IssuersUnderOpaEntity> itemProcessor) {
        return stepBuilderFactory.get("mergerJobStep")
                .<MergerDto, IssuersUnderOpaEntity>chunk(10)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    FieldSetMapper<IssuersUnderOpaEntity> fieldSetMapper() {
        BeanWrapperFieldSetMapper<IssuersUnderOpaEntity> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(IssuersUnderOpaEntity.class);
        fieldSetMapper.setConversionService(ApplicationConversionService.getSharedInstance());
        return fieldSetMapper;
    }

    @Bean
    public Job mergerJob(Step mergerJobStep) {
        return jobBuilderFactory.get("mergerJob")
                .incrementer(new RunIdIncrementer())
                .flow(mergerJobStep)
                .end().build();
    }
}