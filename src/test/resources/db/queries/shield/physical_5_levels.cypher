CREATE (i:Item:Shield {name: 'Shield'});
CREATE (p:UpgradePath {name: 'Physical'});
CREATE (p:UpgradePath {name: 'Magic'});
CREATE (l:Level {name: 'Shield Physical', target: 'Shield', path: 'Physical', level: 0, pathLevel: 0});
CREATE (l:Level {name: 'Shield Physical 1', target: 'Shield', path: 'Physical', level: 1, pathLevel: 1});
CREATE (l:Level {name: 'Shield Physical 2', target: 'Shield', path: 'Physical', level: 2, pathLevel: 2});
CREATE (l:Level {name: 'Shield Physical 3', target: 'Shield', path: 'Physical', level: 3, pathLevel: 3});
CREATE (l:Level {name: 'Shield Physical 4', target: 'Shield', path: 'Physical', level: 4, pathLevel: 4});
CREATE (l:Level {name: 'Shield Magic', target: 'Shield', path: 'Magic', level: 0, pathLevel: 5});
MATCH (l:Level), (m:Level) WHERE l.path = m.path AND m.level = l.level + 1 MERGE (l)-[:NEXT]->(m);
MATCH (l:Level {path: 'Physical', level: 4}), (m:Level {path: 'Magic', level: 0}) MERGE (l)-[:NEXT]->(m);
MATCH (p:UpgradePath {name: 'Physical'}), (l:Level {path: 'Physical'}) MERGE (p)-[:HAS_LEVEL]->(l);
MATCH (p:UpgradePath {name: 'Magic'}), (l:Level {path: 'Magic'}) MERGE (p)-[:HAS_LEVEL]->(l);
MATCH (s:Shield), (l:Level) WHERE s.name = l.target MERGE (s)-[:HAS_LEVEL]->(l);