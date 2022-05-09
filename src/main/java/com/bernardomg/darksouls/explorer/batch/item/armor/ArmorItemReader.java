
package com.bernardomg.darksouls.explorer.batch.item.armor;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.Resource;

import com.bernardomg.darksouls.explorer.item.armor.domain.DtoArmor;

public class ArmorItemReader extends FlatFileItemReader<DtoArmor> {

    public ArmorItemReader(final Resource inputResource) {
        super();

        setLineMapper(new ArmorLineMapper());
        setLinesToSkip(1);
        setResource(inputResource);
    }

}
