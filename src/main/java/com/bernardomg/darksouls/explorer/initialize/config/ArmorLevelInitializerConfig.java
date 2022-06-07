
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
import com.bernardomg.darksouls.explorer.initialize.model.ArmorLevelBatchData;

@Configuration
@ConditionalOnProperty(prefix = "initialize.db.source", name = "armorLevel",
        havingValue = "true")
public class ArmorLevelInitializerConfig {

    @Value("classPath:/data/armor_levels.csv")
    private Resource           data;

    @Autowired
    private DataSource         datasource;

    @Autowired
    private JobBuilderFactory  jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    public ArmorLevelInitializerConfig() {
        super();
    }

    @Bean("armorLevelItemReader")
    public ItemReader<ArmorLevelBatchData> getArmorLevelItemReader(
            final LineMapper<ArmorLevelBatchData> lineMapper) {
        return new FlatFileItemReaderBuilder<ArmorLevelBatchData>()
            .name("armorLevelItemReader")
            .resource(data)
            .delimited()
            .names(new String[] { "name", "level", "regular", "strike", "slash",
                    "thrust", "magic", "fire", "lightning", "bleed", "poison",
                    "curse", "poise" })
            .distanceLimit(0)
            .linesToSkip(1)
            .lineMapper(lineMapper)
            .build();
    }

    @Bean("armorLevelItemWriter")
    public ItemWriter<ArmorLevelBatchData> getArmorLevelItemWriter() {
        return new JdbcBatchItemWriterBuilder<ArmorLevelBatchData>()
            .itemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<ArmorLevelBatchData>())
            .sql(
                "INSERT INTO armor_levels (name, level, regular_protection, strike_protection, slash_protection, thrust_protection, magic_protection, fire_protection, lightning_protection, bleed_protection, poison_protection, curse_protection, poise) VALUES (:name, :level, :regular, :strike, :slash, :thrust, :magic, :fire, :lightning, :bleed, :poison, :curse, :poise)")
            .dataSource(datasource)
            .build();
    }

    @Bean("armorLevelLineMapper")
    public LineMapper<ArmorLevelBatchData> getArmorLevelLineMapper() {
        final DelimitedLineTokenizer lineTokenizer;
        final BeanWrapperFieldSetMapper<ArmorLevelBatchData> fieldSetMapper;
        final DefaultLineMapper<ArmorLevelBatchData> lineMapper;

        lineMapper = new DefaultLineMapper<ArmorLevelBatchData>();

        lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] { "name", "level", "regular",
                "strike", "slash", "thrust", "magic", "fire", "lightning",
                "bleed", "poison", "curse", "poise" });
        fieldSetMapper = new BeanWrapperFieldSetMapper<ArmorLevelBatchData>();
        fieldSetMapper.setTargetType(ArmorLevelBatchData.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean("armorLevelLoaderJob")
    public Job getArmorLevelLoaderJob(
            @Qualifier("armorLevelLoaderStep") final Step armorLoaderStep) {
        return jobBuilderFactory.get("armorLevelLoaderJob")
            .incrementer(new RunIdIncrementer())
            .start(armorLoaderStep)
            .build();
    }

    @Bean("armorLevelLoaderStep")
    public Step getArmorLevelLoaderStep(
            final ItemReader<ArmorLevelBatchData> reader,
            final ItemWriter<ArmorLevelBatchData> writer) {
        return stepBuilderFactory.get("armorLevelLoaderStep")
            .<ArmorLevelBatchData, ArmorLevelBatchData> chunk(5)
            .reader(reader)
            .processor(new DBLogProcessor<>())
            .writer(writer)
            .build();
    }

}
