
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
import com.bernardomg.darksouls.explorer.initialize.model.WeaponLevelBatchData;

@Configuration
@ConditionalOnProperty(prefix = "initialize.db.source", name = "weaponLevel", havingValue = "true")
public class WeaponLevelInitializerConfig {

    @Value("classPath:/data/weapon_levels.csv")
    private Resource           data;

    @Autowired
    private DataSource         datasource;

    @Autowired
    private JobBuilderFactory  jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    public WeaponLevelInitializerConfig() {
        super();
    }

    @Bean("weaponLevelItemReader")
    public ItemReader<WeaponLevelBatchData>
    getWeaponLevelItemReader(final LineMapper<WeaponLevelBatchData> lineMapper) {
        return new FlatFileItemReaderBuilder<WeaponLevelBatchData>().name("weaponLevelItemReader")
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

    @Bean("weaponLevelItemWriter")
    public ItemWriter<WeaponLevelBatchData> getWeaponLevelItemWriter() {
        return new JdbcBatchItemWriterBuilder<WeaponLevelBatchData>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<WeaponLevelBatchData>())
                .sql(
                        "INSERT INTO weapon_levels (name, path, level, physical_damage, magic_damage, fire_damage, lightning_damage, strength_bonus, dexterity_bonus, intelligence_bonus, faith_bonus, physical_reduction, magic_reduction, fire_reduction, lightning_reduction, critical_damage, stability) VALUES (:name, :path, :level, :physical, :magic, :fire, :lightning, :strength, :dexterity, :intelligence, :faith, :physical_reduction, :magic_reduction, :fire_reduction, :lightning_reduction, :critical, :stability)")
                .dataSource(datasource)
                .build();
    }

    @Bean("weaponLevelLineMapper")
    public LineMapper<WeaponLevelBatchData> getWeaponLevelLineMapper() {
        final DelimitedLineTokenizer                          lineTokenizer;
        final BeanWrapperFieldSetMapper<WeaponLevelBatchData> fieldSetMapper;
        final DefaultLineMapper<WeaponLevelBatchData>         lineMapper;

        lineMapper = new DefaultLineMapper<>();

        lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("name", "path", "level", "physical", "magic", "fire", "lightning", "strength",
            "dexterity", "intelligence", "faith", "physical_reduction", "magic_reduction", "fire_reduction",
            "lightning_reduction", "critical", "stability");
        fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(WeaponLevelBatchData.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean("weaponLevelLoaderJob")
    public Job getWeaponLevelLoaderJob(@Qualifier("weaponLevelLoaderStep") final Step armorLoaderStep) {
        return jobBuilderFactory.get("weaponLevelLoaderJob")
                .incrementer(new RunIdIncrementer())
                .start(armorLoaderStep)
                .build();
    }

    @Bean("weaponLevelLoaderStep")
    public Step getWeaponLevelLoaderStep(final ItemReader<WeaponLevelBatchData> reader,
            final ItemWriter<WeaponLevelBatchData> writer) {
        return stepBuilderFactory.get("weaponLevelLoaderStep")
                .<WeaponLevelBatchData, WeaponLevelBatchData> chunk(5)
                .reader(reader)
                .processor(new DBLogProcessor<>())
                .writer(writer)
                .build();
    }

}
