
package com.bernardomg.darksouls.explorer.test.configuration.annotation;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SpringJUnitConfig
@Transactional(propagation = Propagation.NEVER)
@Rollback
public @interface IntegrationTest {

}
