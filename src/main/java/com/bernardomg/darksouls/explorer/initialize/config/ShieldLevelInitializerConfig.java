
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
import com.bernardomg.darksouls.explorer.initialize.model.ShieldLevelBatchData;

@Configuration
@ConditionalOnProperty(prefix = "initialize.db.source", name = "shieldLevel", havingValue = "true")
public class ShieldLevelInitializerConfig {

    @Value("classPath:/data/shield_levels.csv")
    private Resource           data;

    @Autowired
    private DataSource         datasource;

    @Autowired
    private JobBuilderFactory  jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    public ShieldLevelInitializerConfig() {
        super();
    }

    @Bean("shieldLevelItemReader")
    public ItemReader<ShieldLevelBatchData>
            getShieldLevelItemReader(final LineMapper<ShieldLevelBatchData> lineMapper) {
        return new FlatFileItemReaderBuilder<ShieldLevelBatchData>().name("weaponLevelItemReader")
            .resource(data)
            .delimited()
            .names("name", "path", "level", "physical", "magic", "fire", "lightning", "strength", "dexterity",
                "intelligence", "faith", "physical_reduction", "magic_reduction", "fire_reduction",
                "lightning_reduction", "critical", "stability")
            .distanceLimit(0)
            .linesToSkip(1)
            .lineMapper(lineMapper)
            .build();
    }

    @Bean("shieldLevelItemWriter")
    public ItemWriter<ShieldLevelBatchData> getShieldLevelItemWriter() {
        return new JdbcBatchItemWriterBuilder<ShieldLevelBatchData>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<ShieldLevelBatchData>())
            .sql(
                "INSERT INTO weapon_levels (name, path, level, physical_damage, magic_damage, fire_damage, lightning_damage, strength_bonus, dexterity_bonus, intelligence_bonus, faith_bonus, physical_reduction, magic_reduction, fire_reduction, lightning_reduction, critical_damage, stability) VALUES (:name, :path, :level, :physical, :magic, :fire, :lightning, :strength, :dexterity, :intelligence, :faith, :physical_reduction, :magic_reduction, :fire_reduction, :lightning_reduction, :critical, :stability)")
            .dataSource(datasource)
            .build();
    }

    @Bean("shieldLevelLineMapper")
    public LineMapper<ShieldLevelBatchData> getShieldLevelLineMapper() {
        final DelimitedLineTokenizer                          lineTokenizer;
        final BeanWrapperFieldSetMapper<ShieldLevelBatchData> fieldSetMapper;
        final DefaultLineMapper<ShieldLevelBatchData>         lineMapper;

        lineMapper = new DefaultLineMapper<>();

        lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("name", "path", "level", "physical", "magic", "fire", "lightning", "strength",
            "dexterity", "intelligence", "faith", "physical_reduction", "magic_reduction", "fire_reduction",
            "lightning_reduction", "critical", "stability");
        fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(ShieldLevelBatchData.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean("shieldLevelLoaderJob")
    public Job getShieldLevelLoaderJob(@Qualifier("shieldLevelLoaderStep") final Step armorLoaderStep) {
        return jobBuilderFactory.get("shieldLevelLoaderJob")
            .incrementer(new RunIdIncrementer())
            .start(armorLoaderStep)
            .build();
    }

    @Bean("shieldLevelLoaderStep")
    public Step getShieldLevelLoaderStep(final ItemReader<ShieldLevelBatchData> reader,
            final ItemWriter<ShieldLevelBatchData> writer) {
        return stepBuilderFactory.get("shieldLevelLoaderStep")
            .<ShieldLevelBatchData, ShieldLevelBatchData> chunk(5)
            .reader(reader)
            .processor(new DBLogProcessor<>())
            .writer(writer)
            .build();
    }

}
