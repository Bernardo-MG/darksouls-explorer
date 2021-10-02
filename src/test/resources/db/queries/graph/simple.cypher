CREATE ({name: 'Source'});
CREATE ({name: 'Target'});
MATCH (n {name: 'Source'}), (m {name: 'Target'}) MERGE (n)-[:RELATIONSHIP]->(m);