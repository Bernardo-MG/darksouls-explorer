
package com.bernardomg.darksouls.explorer.loader;

import org.springframework.batch.item.ItemProcessor;

import com.bernardomg.darksouls.explorer.item.armor.domain.DtoArmor;

public class DBLogProcessor implements ItemProcessor<DtoArmor, DtoArmor> {

    @Override
    public DtoArmor process(DtoArmor data) throws Exception {
        System.out.println("Inserting data: " + data);
        return data;
    }

}
