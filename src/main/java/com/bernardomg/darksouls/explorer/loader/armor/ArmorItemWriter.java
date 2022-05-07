
package com.bernardomg.darksouls.explorer.loader.armor;

import javax.sql.DataSource;

import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import com.bernardomg.darksouls.explorer.item.armor.domain.DtoArmor;

public class ArmorItemWriter extends JdbcBatchItemWriter<DtoArmor> {

    public ArmorItemWriter(final DataSource datasource) {
        super();

        setDataSource(datasource);
        setSql("INSERT INTO armors (name) VALUES (:name)");
        setItemSqlParameterSourceProvider(
            new BeanPropertyItemSqlParameterSourceProvider<DtoArmor>());
    }

}
