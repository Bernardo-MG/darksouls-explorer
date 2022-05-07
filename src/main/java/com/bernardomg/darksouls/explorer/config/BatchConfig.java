
package com.bernardomg.darksouls.explorer.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.bernardomg.darksouls.explorer.item.armor.domain.DtoArmor;
import com.bernardomg.darksouls.explorer.loader.DBLogProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Value("classPath:/data/armors.csv")
    private Resource           inputResource;

    @Autowired
    private JobBuilderFactory  jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource         datasource;

    public BatchConfig() {
        super();
    }

    @Bean
    public LineMapper<DtoArmor> lineMapper() {
        DefaultLineMapper<DtoArmor> lineMapper = new DefaultLineMapper<DtoArmor>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] { "name" });
        lineTokenizer.setIncludedFields(new int[] { 0 });
        BeanWrapperFieldSetMapper<DtoArmor> fieldSetMapper = new BeanWrapperFieldSetMapper<DtoArmor>();
        fieldSetMapper.setTargetType(DtoArmor.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public ItemProcessor<DtoArmor, DtoArmor> processor() {
        return new DBLogProcessor();
    }

    @Bean
    public Job readCSVFileJob() {
        return jobBuilderFactory.get("readCSVFileJob")
            .incrementer(new RunIdIncrementer())
            .start(step())
            .build();
    }

    @Bean
    public FlatFileItemReader<DtoArmor> reader() {
        FlatFileItemReader<DtoArmor> itemReader = new FlatFileItemReader<DtoArmor>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(inputResource);
        return itemReader;
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
            .<DtoArmor, DtoArmor> chunk(5)
            .reader(reader())
            .processor(processor())
            .writer(writer())
            .build();
    }

    @Bean
    public JdbcBatchItemWriter<DtoArmor> writer() {
        JdbcBatchItemWriter<DtoArmor> itemWriter = new JdbcBatchItemWriter<DtoArmor>();
        itemWriter.setDataSource(datasource);
        itemWriter.setSql("INSERT INTO armors (name) VALUES (:name)");
        itemWriter.setItemSqlParameterSourceProvider(
            new BeanPropertyItemSqlParameterSourceProvider<DtoArmor>());
        return itemWriter;
    }

}
