CREATE ({name: 'Source'});
CREATE ({name: 'Target', description: 'line1|line2'});
MATCH (n {name: 'Source'}), (m {name: 'Target'}) MERGE (n)-[:RELATIONSHIP]->(m);