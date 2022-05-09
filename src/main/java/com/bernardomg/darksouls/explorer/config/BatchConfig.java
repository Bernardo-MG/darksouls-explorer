
package com.bernardomg.darksouls.explorer.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.bernardomg.darksouls.explorer.batch.DBLogProcessor;
import com.bernardomg.darksouls.explorer.batch.item.armor.ArmorItemReader;
import com.bernardomg.darksouls.explorer.batch.item.armor.ArmorItemWriter;
import com.bernardomg.darksouls.explorer.item.armor.domain.DtoArmor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private DataSource         datasource;

    @Value("classPath:/data/armors.csv")
    private Resource           inputResource;

    @Autowired
    private JobBuilderFactory  jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    public BatchConfig() {
        super();
    }

    @Bean
    public Job readCSVFileJob() {
        return jobBuilderFactory.get("readCSVFileJob")
            .incrementer(new RunIdIncrementer())
            .start(step())
            .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
            .<DtoArmor, DtoArmor> chunk(5)
            .reader(new ArmorItemReader(inputResource))
            .processor(new DBLogProcessor<>())
            .writer(new ArmorItemWriter(datasource))
            .build();
    }

}
