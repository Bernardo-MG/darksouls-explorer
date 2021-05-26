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

package com.bernardomg.darksouls.explorer.persistence.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bernardomg.darksouls.explorer.model.Relationship;
import com.bernardomg.darksouls.explorer.model.RelationshipResult;

/**
 * People repository.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Repository
public class MentionRepositoryImpl implements MentionRepository {

    private final Driver driver;

    @Autowired
    protected MentionRepositoryImpl(final Driver drv) {
        super();

        driver = drv;
    }

    @Override
    public final List<Relationship> findAllMentions() {
        final Result rows;
        final List<Relationship> result;

        try (Session session = driver.session()) {
            rows = session.run(
                    "MATCH (m)-[:MENTIONS]->(p:Person) RETURN p.name AS mentioned, ID(p) AS mentionedId, m.name AS mentioner, ID(m) AS mentionerId, m.description AS mention");
            result = rows.stream().map(this::toMention)
                    .collect(Collectors.toList());
        }

        return result;
    }

    private final Relationship toMention(final Record row) {
        final Relationship mention;

        mention = new RelationshipResult();
        mention.setTarget(String.valueOf(row.get("mentioned")));
        mention.setTargetId(Long.valueOf(String.valueOf(row.get("mentionedId"))));
        mention.setSource(String.valueOf(row.get("mentioner")));
        mention.setSourceId(
                Long.valueOf(String.valueOf(row.get("mentionerId"))));
        mention.setType("mention");
        // mention.setMention(String.valueOf(row.get("mention")));

        return mention;
    }

}
