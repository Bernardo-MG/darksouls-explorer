
package com.bernardomg.darksouls.explorer.initialize.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.bernardomg.darksouls.explorer.initialize.DBLogProcessor;
import com.bernardomg.darksouls.explorer.initialize.model.ShieldBatchData;

@Configuration
@ConditionalOnProperty(prefix = "initialize.db.source", name = "shield", havingValue = "true")
public class ShieldInitializerConfig {

    @Value("classPath:/data/shields.csv")
    private Resource           data;

    @Autowired
    private DataSource         datasource;

    @Autowired
    private JobBuilderFactory  jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    public ShieldInitializerConfig() {
        super();
    }

    @Bean("shieldItemReader")
    public ItemReader<ShieldBatchData> getShieldItemReader(final LineMapper<ShieldBatchData> lineMapper) {
        return new FlatFileItemReaderBuilder<ShieldBatchData>().name("shieldItemReader")
                .resource(data)
                .delimited()
                .names("name", "type", "description", "weight", "durability", "attacks", "strength_requirement", "dexterity_requirement", "intelligence_requirement", "faith_requirement", "strength_bonus", "dexterity_bonus", "intelligence_bonus", "faith_bonus", "physical_damage", "magic_damage", "fire_damage", "lightning_damage", "critical_damage", "physical_reduction", "magic_reduction", "fire_reduction", "lightning_reduction", "stability")
                .linesToSkip(1)
                .lineMapper(lineMapper)
                .build();
    }

    @Bean("shieldItemWriter")
    public ItemWriter<ShieldBatchData> getShieldItemWriter() {
        return new JdbcBatchItemWriterBuilder<ShieldBatchData>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<ShieldBatchData>())
                .sql(
                        "INSERT INTO weapons (name, description, type, subtype, weight, durability, strength_requirement, dexterity_requirement, intelligence_requirement, faith_requirement, strength_bonus, dexterity_bonus, intelligence_bonus, faith_bonus, physical_damage, magic_damage, fire_damage, lightning_damage, critical_damage, physical_reduction, magic_reduction, fire_reduction, lightning_reduction, stability) VALUES (:name, :description, 'Shield', :type, :weight, :durability, :strength_requirement, :dexterity_requirement, :intelligence_requirement, :faith_requirement, :strength_bonus, :dexterity_bonus, :intelligence_bonus, :faith_bonus, :physical_damage, :magic_damage, :fire_damage, :lightning_damage, :critical_damage, :physical_reduction, :magic_reduction, :fire_reduction, :lightning_reduction, :stability)")
                .dataSource(datasource)
                .build();
    }

    @Bean("shieldLineMapper")
    public LineMapper<ShieldBatchData> getShieldLineMapper() {
        final DelimitedLineTokenizer                     lineTokenizer;
        final BeanWrapperFieldSetMapper<ShieldBatchData> fieldSetMapper;
        final DefaultLineMapper<ShieldBatchData>         lineMapper;

        lineMapper = new DefaultLineMapper<>();

        lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("name", "type", "description", "weight", "durability", "attacks", "strength_requirement", "dexterity_requirement", "intelligence_requirement", "faith_requirement", "strength_bonus", "dexterity_bonus", "intelligence_bonus", "faith_bonus", "physical_damage", "magic_damage", "fire_damage", "lightning_damage", "critical_damage", "physical_reduction", "magic_reduction", "fire_reduction", "lightning_reduction", "stability");
        fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(ShieldBatchData.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean("shieldLoaderJob")
    public Job getShieldLoaderJob(@Qualifier("shieldLoaderStep") final Step armorLoaderStep) {
        return jobBuilderFactory.get("shieldLoaderJob")
                .incrementer(new RunIdIncrementer())
                .start(armorLoaderStep)
                .build();
    }

    @Bean("shieldLoaderStep")
    public Step getShieldLoaderStep(final ItemReader<ShieldBatchData> reader,
            final ItemWriter<ShieldBatchData> writer) {
        return stepBuilderFactory.get("shieldLoaderStep")
                .<ShieldBatchData, ShieldBatchData> chunk(5)
                .reader(reader)
                .processor(new DBLogProcessor<>())
                .writer(writer)
                .build();
    }

}
