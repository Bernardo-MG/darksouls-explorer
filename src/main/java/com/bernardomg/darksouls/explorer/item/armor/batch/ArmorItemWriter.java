
package com.bernardomg.darksouls.explorer.item.armor.batch;

import javax.sql.DataSource;

import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import com.bernardomg.darksouls.explorer.item.armor.domain.DtoArmor;

public class ArmorItemWriter extends JdbcBatchItemWriter<DtoArmor> {

    public ArmorItemWriter(final DataSource datasource) {
        super();

        setDataSource(datasource);
        setSql(
            "INSERT INTO armors (name, description, weight, durability) VALUES (:name, :description, :weight, :durability)");
        setItemSqlParameterSourceProvider(
            new BeanPropertyItemSqlParameterSourceProvider<DtoArmor>());
    }

}
