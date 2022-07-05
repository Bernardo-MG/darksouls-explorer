
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
import com.bernardomg.darksouls.explorer.initialize.model.TalismanAdjustmentBatchData;

@Configuration
@ConditionalOnProperty(prefix = "initialize.db.source", name = "talismanAdjustment", havingValue = "true")
public class TalismanAdjustmentInitializerConfig {

    @Value("classPath:/data/talisman_adjustments.csv")
    private Resource           data;

    @Autowired
    private DataSource         datasource;

    @Autowired
    private JobBuilderFactory  jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    public TalismanAdjustmentInitializerConfig() {
        super();
    }

    @Bean("talismanAdjustmentItemReader")
    public ItemReader<TalismanAdjustmentBatchData>
    getTalismanAdjustmentItemReader(final LineMapper<TalismanAdjustmentBatchData> lineMapper) {
        return new FlatFileItemReaderBuilder<TalismanAdjustmentBatchData>().name("talismanAdjustmentItemReader")
                .resource(data)
                .delimited()
                .names("name", "faith", "intelligence", "adjustment")
                .distanceLimit(0)
                .linesToSkip(1)
                .lineMapper(lineMapper)
                .build();
    }

    @Bean("talismanAdjustmentItemWriter")
    public ItemWriter<TalismanAdjustmentBatchData> getTalismanAdjustmentItemWriter() {
        return new JdbcBatchItemWriterBuilder<TalismanAdjustmentBatchData>()
                .itemSqlParameterSourceProvider(
                    new BeanPropertyItemSqlParameterSourceProvider<TalismanAdjustmentBatchData>())
                .sql(
                        "INSERT INTO weapon_adjustments (name, faith, intelligence, adjustment) VALUES (:name, :faith, :intelligence, :adjustment)")
                .dataSource(datasource)
                .build();
    }

    @Bean("talismanAdjustmentLineMapper")
    public LineMapper<TalismanAdjustmentBatchData> getTalismanAdjustmentLineMapper() {
        final DelimitedLineTokenizer                                 lineTokenizer;
        final BeanWrapperFieldSetMapper<TalismanAdjustmentBatchData> fieldSetMapper;
        final DefaultLineMapper<TalismanAdjustmentBatchData>         lineMapper;

        lineMapper = new DefaultLineMapper<>();

        lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("name", "faith", "intelligence", "adjustment");
        fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(TalismanAdjustmentBatchData.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean("talismanAdjustmentLoaderJob")
    public Job getTalismanAdjustmentLoaderJob(@Qualifier("talismanAdjustmentLoaderStep") final Step armorLoaderStep) {
        return jobBuilderFactory.get("talismanAdjustmentLoaderJob")
                .incrementer(new RunIdIncrementer())
                .start(armorLoaderStep)
                .build();
    }

    @Bean("talismanAdjustmentLoaderStep")
    public Step getTalismanAdjustmentLoaderStep(final ItemReader<TalismanAdjustmentBatchData> reader,
            final ItemWriter<TalismanAdjustmentBatchData> writer) {
        return stepBuilderFactory.get("talismanAdjustmentLoaderStep")
                .<TalismanAdjustmentBatchData, TalismanAdjustmentBatchData> chunk(5)
                .reader(reader)
                .processor(new DBLogProcessor<>())
                .writer(writer)
                .build();
    }

}
