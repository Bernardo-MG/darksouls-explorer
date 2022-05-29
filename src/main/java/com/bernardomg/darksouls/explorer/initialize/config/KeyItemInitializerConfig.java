
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
import com.bernardomg.darksouls.explorer.initialize.model.ItemBatchData;

@Configuration
@ConditionalOnProperty(prefix = "initialize.db.source", name = "keyItem",
        havingValue = "true")
public class KeyItemInitializerConfig {

    @Value("classPath:/data/key_items.csv")
    private Resource           data;

    @Autowired
    private DataSource         datasource;

    @Autowired
    private JobBuilderFactory  jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    public KeyItemInitializerConfig() {
        super();
    }

    @Bean("keyItemItemReader")
    public ItemReader<ItemBatchData> getKeyItemItemReader(
            @Qualifier("keyItemLineMapper") final LineMapper<ItemBatchData> lineMapper) {
        return new FlatFileItemReaderBuilder<ItemBatchData>()
            .name("keyItemItemReader")
            .resource(data)
            .delimited()
            .names(new String[] { "name", "description" })
            .linesToSkip(1)
            .lineMapper(lineMapper)
            .build();
    }

    @Bean("keyItemItemWriter")
    public ItemWriter<ItemBatchData> getKeyItemItemWriter() {
        return new JdbcBatchItemWriterBuilder<ItemBatchData>()
            .itemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<ItemBatchData>())
            .sql(
                "INSERT INTO key_items (type, name, description) VALUES (\"keyItem\", :name, :description)")
            .dataSource(datasource)
            .build();
    }

    @Bean("keyItemLineMapper")
    public LineMapper<ItemBatchData> getKeyItemLineMapper() {
        final DelimitedLineTokenizer lineTokenizer;
        final BeanWrapperFieldSetMapper<ItemBatchData> fieldSetMapper;
        final DefaultLineMapper<ItemBatchData> lineMapper;

        lineMapper = new DefaultLineMapper<ItemBatchData>();

        lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] { "name", "description" });
        fieldSetMapper = new BeanWrapperFieldSetMapper<ItemBatchData>();
        fieldSetMapper.setTargetType(ItemBatchData.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean("keyItemLoaderJob")
    public Job getKeyItemLoaderJob(
            @Qualifier("keyItemLoaderStep") final Step armorLoaderStep) {
        return jobBuilderFactory.get("keyItemLoaderJob")
            .incrementer(new RunIdIncrementer())
            .start(armorLoaderStep)
            .build();
    }

    @Bean("keyItemLoaderStep")
    public Step getKeyItemLoaderStep(
            @Qualifier("keyItemItemReader") final ItemReader<ItemBatchData> reader,
            @Qualifier("keyItemItemWriter") final ItemWriter<ItemBatchData> writer) {
        return stepBuilderFactory.get("keyItemLoaderStep")
            .<ItemBatchData, ItemBatchData> chunk(5)
            .reader(reader)
            .processor(new DBLogProcessor<>())
            .writer(writer)
            .build();
    }

}
