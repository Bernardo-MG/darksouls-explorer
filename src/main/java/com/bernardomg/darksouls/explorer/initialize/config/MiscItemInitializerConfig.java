
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
@ConditionalOnProperty(prefix = "initialize.db.source", name = "miscItem", havingValue = "true")
public class MiscItemInitializerConfig {

    @Value("classPath:/data/misc_items.csv")
    private Resource           data;

    @Autowired
    private DataSource         datasource;

    @Autowired
    private JobBuilderFactory  jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    public MiscItemInitializerConfig() {
        super();
    }

    @Bean("miscItemItemReader")
    public ItemReader<ItemBatchData>
            getMiscItemItemReader(@Qualifier("miscItemLineMapper") final LineMapper<ItemBatchData> lineMapper) {
        return new FlatFileItemReaderBuilder<ItemBatchData>().name("miscItemItemReader")
            .resource(data)
            .delimited()
            .names("name", "description")
            .linesToSkip(1)
            .lineMapper(lineMapper)
            .build();
    }

    @Bean("miscItemItemWriter")
    public ItemWriter<ItemBatchData> getMiscItemItemWriter() {
        return new JdbcBatchItemWriterBuilder<ItemBatchData>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<ItemBatchData>())
            .sql("INSERT INTO items (name, description, type) VALUES (:name, :description, 'Misc')")
            .dataSource(datasource)
            .build();
    }

    @Bean("miscItemLineMapper")
    public LineMapper<ItemBatchData> getMiscItemLineMapper() {
        final DelimitedLineTokenizer                   lineTokenizer;
        final BeanWrapperFieldSetMapper<ItemBatchData> fieldSetMapper;
        final DefaultLineMapper<ItemBatchData>         lineMapper;

        lineMapper = new DefaultLineMapper<>();

        lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("name", "description");
        fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(ItemBatchData.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean("miscItemLoaderJob")
    public Job getMiscItemLoaderJob(@Qualifier("miscItemLoaderStep") final Step armorLoaderStep) {
        return jobBuilderFactory.get("miscItemLoaderJob")
            .incrementer(new RunIdIncrementer())
            .start(armorLoaderStep)
            .build();
    }

    @Bean("miscItemLoaderStep")
    public Step getMiscItemLoaderStep(@Qualifier("miscItemItemReader") final ItemReader<ItemBatchData> reader,
            @Qualifier("miscItemItemWriter") final ItemWriter<ItemBatchData> writer) {
        return stepBuilderFactory.get("miscItemLoaderStep")
            .<ItemBatchData, ItemBatchData> chunk(5)
            .reader(reader)
            .processor(new DBLogProcessor<>())
            .writer(writer)
            .build();
    }

}
