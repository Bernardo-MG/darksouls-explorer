
package com.bernardomg.darksouls.explorer.batch.config;

import java.util.Collection;

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
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import com.bernardomg.darksouls.explorer.batch.DBLogProcessor;

public abstract class AbstractBatchLoaderConfig<T> {

    private final Resource            armorsData;

    private final Collection<Integer> columns;

    @Autowired
    private DataSource                datasource;

    private final Collection<String>  fields;

    private final String              insert;

    @Autowired
    private JobBuilderFactory         jobBuilderFactory;

    @Autowired
    private StepBuilderFactory        stepBuilderFactory;

    private final Class<T>            target;

    public AbstractBatchLoaderConfig(final Resource armorsData,
            final Class<T> target, final String inst,
            final Collection<String> fields,
            final Collection<Integer> columns) {
        super();

        this.armorsData = armorsData;
        this.target = target;
        insert = inst;
        this.fields = fields;
        this.columns = columns;
    }

    @Bean("armorLoaderJob")
    public Job armorLoaderJob(
            @Qualifier("armorLoaderStep") final Step armorLoaderStep) {
        return jobBuilderFactory.get("armorLoaderJob")
            .incrementer(new RunIdIncrementer())
            .start(armorLoaderStep)
            .build();
    }

    @Bean("armorItemReader")
    public ItemReader<T> getArmorItemReader(final LineMapper<T> lineMapper) {
        return new FlatFileItemReaderBuilder<T>().name("armorItemReader")
            .resource(armorsData)
            .delimited()
            .names(fields.toArray(new String[fields.size()]))
            .linesToSkip(1)
            .lineMapper(lineMapper)
            .build();
    }

    @Bean("armorItemWriter")
    public ItemWriter<T> getArmorItemWriter() {
        return new JdbcBatchItemWriterBuilder<T>()
            .itemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<T>())
            .sql(insert)
            .dataSource(datasource)
            .build();
    }

    @Bean("armorLineMapper")
    public LineMapper<T> getArmorLineMapper() {
        final DelimitedLineTokenizer lineTokenizer;
        final BeanWrapperFieldSetMapper<T> fieldSetMapper;
        final DefaultLineMapper<T> lineMapper;

        lineMapper = new DefaultLineMapper<T>();

        lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(fields.toArray(new String[fields.size()]));
        lineTokenizer.setIncludedFields(columns.stream()
            .mapToInt(c -> c)
            .toArray());
        fieldSetMapper = new BeanWrapperFieldSetMapper<T>();
        fieldSetMapper.setTargetType(target);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean("armorLoaderStep")
    public Step getArmorLoaderStep(final ItemReader<T> reader,
            final ItemWriter<T> writer) {
        return stepBuilderFactory.get("armorLoaderStep")
            .<T, T> chunk(5)
            .reader(reader)
            .processor(new DBLogProcessor<>())
            .writer(writer)
            .build();
    }

}
