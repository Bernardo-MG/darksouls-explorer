
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
import com.bernardomg.darksouls.explorer.initialize.model.WeaponBatchData;

@Configuration
@ConditionalOnProperty(prefix = "initialize.db.source", name = "weapon", havingValue = "true")
public class WeaponInitializerConfig {

    @Value("classPath:/data/weapons.csv")
    private Resource           data;

    @Autowired
    private DataSource         datasource;

    @Autowired
    private JobBuilderFactory  jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    public WeaponInitializerConfig() {
        super();
    }

    @Bean("weaponItemReader")
    public ItemReader<WeaponBatchData> getWeaponItemReader(final LineMapper<WeaponBatchData> lineMapper) {
        return new FlatFileItemReaderBuilder<WeaponBatchData>().name("weaponItemReader")
            .resource(data)
            .delimited()
            .names("name", "type", "description", "weight", "durability", "attacks", "strength_requirement",
                "dexterity_requirement", "intelligence_requirement", "faith_requirement", "strength_bonus",
                "dexterity_bonus", "intelligence_bonus", "faith_bonus", "physical_damage", "magic_damage",
                "fire_damage", "lightning_damage", "critical_damage", "physical_reduction", "magic_reduction",
                "fire_reduction", "lightning_reduction", "stability")
            .linesToSkip(1)
            .lineMapper(lineMapper)
            .build();
    }

    @Bean("weaponItemWriter")
    public ItemWriter<WeaponBatchData> getWeaponItemWriter() {
        return new JdbcBatchItemWriterBuilder<WeaponBatchData>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<WeaponBatchData>())
            .sql(
                "INSERT INTO weapons (name, description, type, subtype, weight, durability, strength_requirement, dexterity_requirement, intelligence_requirement, faith_requirement, strength_bonus, dexterity_bonus, intelligence_bonus, faith_bonus, physical_damage, magic_damage, fire_damage, lightning_damage, critical_damage, physical_reduction, magic_reduction, fire_reduction, lightning_reduction, stability) VALUES (:name, :description, 'Weapon', :type, :weight, :durability, :strength_requirement, :dexterity_requirement, :intelligence_requirement, :faith_requirement, :strength_bonus, :dexterity_bonus, :intelligence_bonus, :faith_bonus, :physical_damage, :magic_damage, :fire_damage, :lightning_damage, :critical_damage, :physical_reduction, :magic_reduction, :fire_reduction, :lightning_reduction, :stability)")
            .dataSource(datasource)
            .build();
    }

    @Bean("weaponLineMapper")
    public LineMapper<WeaponBatchData> getWeaponLineMapper() {
        final DelimitedLineTokenizer                     lineTokenizer;
        final BeanWrapperFieldSetMapper<WeaponBatchData> fieldSetMapper;
        final DefaultLineMapper<WeaponBatchData>         lineMapper;

        lineMapper = new DefaultLineMapper<>();

        lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("name", "type", "description", "weight", "durability", "attacks", "strength_requirement",
            "dexterity_requirement", "intelligence_requirement", "faith_requirement", "strength_bonus",
            "dexterity_bonus", "intelligence_bonus", "faith_bonus", "physical_damage", "magic_damage", "fire_damage",
            "lightning_damage", "critical_damage", "physical_reduction", "magic_reduction", "fire_reduction",
            "lightning_reduction", "stability");
        fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(WeaponBatchData.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean("weaponLoaderJob")
    public Job getWeaponLoaderJob(@Qualifier("weaponLoaderStep") final Step armorLoaderStep) {
        return jobBuilderFactory.get("weaponLoaderJob")
            .incrementer(new RunIdIncrementer())
            .start(armorLoaderStep)
            .build();
    }

    @Bean("weaponLoaderStep")
    public Step getWeaponLoaderStep(final ItemReader<WeaponBatchData> reader,
            final ItemWriter<WeaponBatchData> writer) {
        return stepBuilderFactory.get("weaponLoaderStep")
            .<WeaponBatchData, WeaponBatchData> chunk(5)
            .reader(reader)
            .processor(new DBLogProcessor<>())
            .writer(writer)
            .build();
    }

}
