
package com.bernardomg.darksouls.explorer.batch.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.bernardomg.darksouls.explorer.batch.model.ArmorBatchData;

@Configuration
public class ArmorBatchConfig
        extends AbstractBatchLoaderConfig<ArmorBatchData> {

    @Value("classPath:/data/armors.csv")
    private Resource armorsData;

    public ArmorBatchConfig(
            @Value("classPath:/data/armors.csv") final Resource armorsData) {
        super(armorsData, ArmorBatchData.class,
            "INSERT INTO armors (name, description, weight, durability) VALUES (:name, :description, :weight, :durability)",
            Arrays.asList("name", "description", "weight", "durability"),
            Arrays.asList(0, 2, 3, 4));
    }

}
