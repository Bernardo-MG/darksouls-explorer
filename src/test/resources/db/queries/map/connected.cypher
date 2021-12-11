CREATE (m:Map {name: 'Map 1'});
CREATE (m:Map {name: 'Map 2'});
MATCH (m:Map {name: 'Map 1'}), (n:Map {name: 'Map 2'}) MERGE (m)-[:CONNECTS_TO]->(n) MERGE (n)-[:CONNECTS_TO]->(m);