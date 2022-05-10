
package com.bernardomg.darksouls.explorer.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.bernardomg.darksouls.explorer.batch.DBLogProcessor;
import com.bernardomg.darksouls.explorer.item.armor.domain.DtoArmor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Value("classPath:/data/armors.csv")
    private Resource           armorsData;

    @Autowired
    private DataSource         datasource;

    @Autowired
    private JobBuilderFactory  jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    public BatchConfig() {
        super();
    }

    @Bean("armorLoaderJob")
    public Job armorLoaderJob(
            @Qualifier("armorLoaderStep") final Step armorLoaderStep) {
        return jobBuilderFactory.get("armorLoaderJob")
            .incrementer(new RunIdIncrementer())
            .start(armorLoaderStep)
            .build();
    }

    @Bean("armorItemReader")
    public ItemReader<DtoArmor>
            getArmorItemReader(final LineMapper<DtoArmor> lineMapper) {
        final FlatFileItemReader<DtoArmor> itemReader;

        itemReader = new FlatFileItemReader<DtoArmor>();

        itemReader.setLineMapper(lineMapper);
        itemReader.setLinesToSkip(1);
        itemReader.setResource(armorsData);

        return itemReader;
    }

    @Bean("armorItemWriter")
    public ItemWriter<DtoArmor> getArmorItemWriter() {
        final JdbcBatchItemWriter<DtoArmor> itemWriter;

        itemWriter = new JdbcBatchItemWriter<DtoArmor>();

        itemWriter.setDataSource(datasource);
        itemWriter.setSql(
            "INSERT INTO armors (name, description, weight, durability) VALUES (:name, :description, :weight, :durability)");
        itemWriter.setItemSqlParameterSourceProvider(
            new BeanPropertyItemSqlParameterSourceProvider<DtoArmor>());

        return itemWriter;
    }

    @Bean("armorLineMapper")
    public LineMapper<DtoArmor> getArmorLineMapper() {
        final DelimitedLineTokenizer lineTokenizer;
        final BeanWrapperFieldSetMapper<DtoArmor> fieldSetMapper;
        final DefaultLineMapper<DtoArmor> lineMapper;

        lineMapper = new DefaultLineMapper<DtoArmor>();

        lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] { "name" });
        lineTokenizer.setIncludedFields(new int[] { 0 });
        fieldSetMapper = new BeanWrapperFieldSetMapper<DtoArmor>();
        fieldSetMapper.setTargetType(DtoArmor.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean("armorLoaderStep")
    public Step getArmorLoaderStep(final ItemReader<DtoArmor> reader,
            final ItemWriter<DtoArmor> writer) {
        return stepBuilderFactory.get("step")
            .<DtoArmor, DtoArmor> chunk(5)
            .reader(reader)
            .processor(new DBLogProcessor<>())
            .writer(writer)
            .build();
    }

}
