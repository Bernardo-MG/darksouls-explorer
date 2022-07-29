CREATE (p:UpgradePath {name: 'Magic'});
MATCH (p:UpgradePath {name: 'Magic'}), (l:Level {path: 'Magic'}) MERGE (p)-[:HAS_LEVEL]->(l);