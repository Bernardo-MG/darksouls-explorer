
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
import com.bernardomg.darksouls.explorer.initialize.model.CatalystAdjustmentBatchData;

@Configuration
@ConditionalOnProperty(prefix = "initialize.db.source", name = "catalystAdjustment", havingValue = "true")
public class CatalystAdjustmentInitializerConfig {

    @Value("classPath:/data/catalyst_adjustments.csv")
    private Resource           data;

    @Autowired
    private DataSource         datasource;

    @Autowired
    private JobBuilderFactory  jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    public CatalystAdjustmentInitializerConfig() {
        super();
    }

    @Bean("catalystAdjustmentItemReader")
    public ItemReader<CatalystAdjustmentBatchData>
            getCatalystAdjustmentItemReader(final LineMapper<CatalystAdjustmentBatchData> lineMapper) {
        return new FlatFileItemReaderBuilder<CatalystAdjustmentBatchData>().name("catalystAdjustmentItemReader")
            .resource(data)
            .delimited()
            .names("name", "faith", "intelligence", "adjustment")
            .distanceLimit(0)
            .linesToSkip(1)
            .lineMapper(lineMapper)
            .build();
    }

    @Bean("catalystAdjustmentItemWriter")
    public ItemWriter<CatalystAdjustmentBatchData> getCatalystAdjustmentItemWriter() {
        return new JdbcBatchItemWriterBuilder<CatalystAdjustmentBatchData>()
            .itemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<CatalystAdjustmentBatchData>())
            .sql(
                "INSERT INTO weapon_adjustments (name, faith, intelligence, adjustment) VALUES (:name, :faith, :intelligence, :adjustment)")
            .dataSource(datasource)
            .build();
    }

    @Bean("catalystAdjustmentLineMapper")
    public LineMapper<CatalystAdjustmentBatchData> getCatalystAdjustmentLineMapper() {
        final DelimitedLineTokenizer                                 lineTokenizer;
        final BeanWrapperFieldSetMapper<CatalystAdjustmentBatchData> fieldSetMapper;
        final DefaultLineMapper<CatalystAdjustmentBatchData>         lineMapper;

        lineMapper = new DefaultLineMapper<>();

        lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("name", "faith", "intelligence", "adjustment");
        fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(CatalystAdjustmentBatchData.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean("catalystAdjustmentLoaderJob")
    public Job getCatalystAdjustmentLoaderJob(@Qualifier("catalystAdjustmentLoaderStep") final Step armorLoaderStep) {
        return jobBuilderFactory.get("catalystAdjustmentLoaderJob")
            .incrementer(new RunIdIncrementer())
            .start(armorLoaderStep)
            .build();
    }

    @Bean("catalystAdjustmentLoaderStep")
    public Step getCatalystAdjustmentLoaderStep(final ItemReader<CatalystAdjustmentBatchData> reader,
            final ItemWriter<CatalystAdjustmentBatchData> writer) {
        return stepBuilderFactory.get("catalystAdjustmentLoaderStep")
            .<CatalystAdjustmentBatchData, CatalystAdjustmentBatchData> chunk(5)
            .reader(reader)
            .processor(new DBLogProcessor<>())
            .writer(writer)
            .build();
    }

}
