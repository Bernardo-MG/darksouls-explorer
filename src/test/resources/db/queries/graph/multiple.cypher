CREATE ({name: 'Source'});
CREATE ({name: 'Target'});
CREATE ({name: 'Another'});
MATCH (n {name: 'Source'}), (m {name: 'Target'}) MERGE (n)-[:RELATIONSHIP]->(m);
MATCH (n {name: 'Source'}), (m {name: 'Another'}) MERGE (n)-[:RELATIONSHIP]->(m);