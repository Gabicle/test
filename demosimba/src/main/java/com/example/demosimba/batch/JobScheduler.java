package com.example.demosimba.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobScheduler {
    @Autowired
    private Job job;

    @Autowired
    private JobLauncher jobLauncher;

    @Scheduled(fixedDelay = 8000)
    public void runBath() {
        JobParameters params = new JobParametersBuilder()
                .toJobParameters();

        try{
            jobLauncher.run(job, params);
        } catch (JobInstanceAlreadyCompleteException | JobParametersInvalidException | JobRestartException | JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        }
    }
}
