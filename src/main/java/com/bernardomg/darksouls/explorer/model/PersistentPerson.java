/**
 * Copyright 2020 the original author or authors
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

package com.bernardomg.darksouls.explorer.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * Persistent planet. Prepared for Neo4j.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Node("Person")
public class PersistentPerson implements Person {

    /**
     * Planet name.
     */
    @Id
    private String name;

    public PersistentPerson() {
        super();
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param value
     *            the name
     */
    public void setName(final String value) {
        name = value;
    }

}
