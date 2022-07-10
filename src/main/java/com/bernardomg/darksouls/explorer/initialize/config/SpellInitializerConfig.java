
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
import com.bernardomg.darksouls.explorer.initialize.model.SpellBatchData;

@Configuration
@ConditionalOnProperty(prefix = "initialize.db.source", name = "spell", havingValue = "true")
public class SpellInitializerConfig {

    @Value("classPath:/data/spells.csv")
    private Resource           data;

    @Autowired
    private DataSource         datasource;

    @Autowired
    private JobBuilderFactory  jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    public SpellInitializerConfig() {
        super();
    }

    @Bean("spellItemReader")
    public ItemReader<SpellBatchData> getWeaponItemReader(final LineMapper<SpellBatchData> lineMapper) {
        return new FlatFileItemReaderBuilder<SpellBatchData>().name("spellItemReader")
            .resource(data)
            .delimited()
            .names("name", "school", "description", "intelligence", "faith", "slots", "uses")
            .linesToSkip(1)
            .lineMapper(lineMapper)
            .build();
    }

    @Bean("spellItemWriter")
    public ItemWriter<SpellBatchData> getWeaponItemWriter() {
        return new JdbcBatchItemWriterBuilder<SpellBatchData>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<SpellBatchData>())
            .sql(
                "INSERT INTO spells (name, description, school, intelligence, faith, slots, uses) VALUES (:name, :description, :school, :intelligence, :faith, :slots, :uses)")
            .dataSource(datasource)
            .build();
    }

    @Bean("spellLineMapper")
    public LineMapper<SpellBatchData> getWeaponLineMapper() {
        final DelimitedLineTokenizer                    lineTokenizer;
        final BeanWrapperFieldSetMapper<SpellBatchData> fieldSetMapper;
        final DefaultLineMapper<SpellBatchData>         lineMapper;

        lineMapper = new DefaultLineMapper<>();

        lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("name", "school", "description", "intelligence", "faith", "slots", "uses");
        fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(SpellBatchData.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean("spellLoaderJob")
    public Job getWeaponLoaderJob(@Qualifier("spellLoaderStep") final Step armorLoaderStep) {
        return jobBuilderFactory.get("spellLoaderJob")
            .incrementer(new RunIdIncrementer())
            .start(armorLoaderStep)
            .build();
    }

    @Bean("spellLoaderStep")
    public Step getWeaponLoaderStep(final ItemReader<SpellBatchData> reader, final ItemWriter<SpellBatchData> writer) {
        return stepBuilderFactory.get("spellLoaderStep")
            .<SpellBatchData, SpellBatchData> chunk(5)
            .reader(reader)
            .processor(new DBLogProcessor<>())
            .writer(writer)
            .build();
    }

}
