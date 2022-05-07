
package com.bernardomg.darksouls.explorer.loader;

import org.springframework.batch.item.ItemProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DBLogProcessor<I> implements ItemProcessor<I, I> {

    @Override
    public final I process(final I data) throws Exception {
        log.debug("Inserting data: {}", data);
        return data;
    }

}
