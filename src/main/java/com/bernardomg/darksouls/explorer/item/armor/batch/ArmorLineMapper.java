
package com.bernardomg.darksouls.explorer.item.armor.batch;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

import com.bernardomg.darksouls.explorer.item.armor.domain.DtoArmor;

public final class ArmorLineMapper extends DefaultLineMapper<DtoArmor> {

    public ArmorLineMapper() {
        super();

        final DelimitedLineTokenizer lineTokenizer;
        final BeanWrapperFieldSetMapper<DtoArmor> fieldSetMapper;

        lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] { "name" });
        lineTokenizer.setIncludedFields(new int[] { 0 });
        fieldSetMapper = new BeanWrapperFieldSetMapper<DtoArmor>();
        fieldSetMapper.setTargetType(DtoArmor.class);

        setLineTokenizer(lineTokenizer);
        setFieldSetMapper(fieldSetMapper);
    }

}
