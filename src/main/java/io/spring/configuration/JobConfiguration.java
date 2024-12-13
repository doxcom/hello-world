package io.spring.configuration;


import io.spring.batch.CustomProcessor;
import io.spring.batch.CustomReader;
import io.spring.batch.CustomWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
public class JobConfiguration {

    @Bean
    Job createJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("job", jobRepository)
                .flow(createStep(jobRepository,transactionManager)).end().build();
    }

    @Bean
    Step createStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step", jobRepository)
                .<String, String> chunk(3,transactionManager)
                .allowStartIfComplete(true)
                .reader(new CustomReader())
                .processor(new CustomProcessor())
                .writer(new CustomWriter())
                .build();
    }



   }


