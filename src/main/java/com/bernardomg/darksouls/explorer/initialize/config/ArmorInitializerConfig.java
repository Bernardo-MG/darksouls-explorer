
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
import com.bernardomg.darksouls.explorer.initialize.model.ArmorBatchData;

@Configuration
@ConditionalOnProperty(prefix = "initialize.db.source", name = "armor",
        havingValue = "true")
public class ArmorInitializerConfig {

    @Value("classPath:/data/armors.csv")
    private Resource           data;

    @Autowired
    private DataSource         datasource;

    @Autowired
    private JobBuilderFactory  jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    public ArmorInitializerConfig() {
        super();
    }

    @Bean("armorItemReader")
    public ItemReader<ArmorBatchData>
            getArmorItemReader(final LineMapper<ArmorBatchData> lineMapper) {
        return new FlatFileItemReaderBuilder<ArmorBatchData>()
            .name("armorItemReader")
            .resource(data)
            .delimited()
            .names(
                new String[] { "name", "description", "weight", "durability" })
            .linesToSkip(1)
            .lineMapper(lineMapper)
            .build();
    }

    @Bean("armorItemWriter")
    public ItemWriter<ArmorBatchData> getArmorItemWriter() {
        return new JdbcBatchItemWriterBuilder<ArmorBatchData>()
            .itemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<ArmorBatchData>())
            .sql(
                "INSERT INTO armors (name, description, weight, durability) VALUES (:name, :description, :weight, :durability)")
            .dataSource(datasource)
            .build();
    }

    @Bean("armorLineMapper")
    public LineMapper<ArmorBatchData> getArmorLineMapper() {
        final DelimitedLineTokenizer lineTokenizer;
        final BeanWrapperFieldSetMapper<ArmorBatchData> fieldSetMapper;
        final DefaultLineMapper<ArmorBatchData> lineMapper;

        lineMapper = new DefaultLineMapper<ArmorBatchData>();

        lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(
            new String[] { "name", "description", "weight", "durability" });
        lineTokenizer.setIncludedFields(new int[] { 0, 2, 3, 4 });
        fieldSetMapper = new BeanWrapperFieldSetMapper<ArmorBatchData>();
        fieldSetMapper.setTargetType(ArmorBatchData.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean("armorLoaderJob")
    public Job getArmorLoaderJob(
            @Qualifier("armorLoaderStep") final Step armorLoaderStep) {
        return jobBuilderFactory.get("armorLoaderJob")
            .incrementer(new RunIdIncrementer())
            .start(armorLoaderStep)
            .build();
    }

    @Bean("armorLoaderStep")
    public Step getArmorLoaderStep(final ItemReader<ArmorBatchData> reader,
            final ItemWriter<ArmorBatchData> writer) {
        return stepBuilderFactory.get("armorLoaderStep")
            .<ArmorBatchData, ArmorBatchData> chunk(5)
            .reader(reader)
            .processor(new DBLogProcessor<>())
            .writer(writer)
            .build();
    }

}
