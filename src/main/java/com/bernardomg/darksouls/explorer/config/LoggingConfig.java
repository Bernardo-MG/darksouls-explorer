/**
 * Copyright 2021 the original author or authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.bernardomg.darksouls.explorer.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.darksouls.explorer.logging.aspect.ControllerLoggingAspect;
import com.bernardomg.darksouls.explorer.logging.aspect.ServiceLoggingAspect;

/**
 * Logging configuration.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
public class LoggingConfig {

    /**
     * Default constructor.
     */
    public LoggingConfig() {
        super();
    }

    @Bean
    @ConditionalOnProperty(name = "logging.controller", matchIfMissing = false,
            havingValue = "true")
    public ControllerLoggingAspect controllerLoggingAspect() {
        return new ControllerLoggingAspect();
    }

    @Bean
    @ConditionalOnProperty(name = "logging.service", matchIfMissing = false,
            havingValue = "true")
    public ServiceLoggingAspect serviceLoggingAspect() {
        return new ServiceLoggingAspect();
    }

}
