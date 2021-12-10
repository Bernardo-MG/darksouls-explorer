CREATE (i:Item {name: 'Item name', description: 'Description'});
CREATE (m:Merchant {name: 'Merchant'});
CREATE (l:Location {name: 'Location'});
MATCH (n {name: 'Item name'}), (m {name: 'Merchant'}) MERGE (m)-[:SELLS {price: 10}]->(n);
MATCH (m {name: 'Merchant'}), (l {name: 'Location'}) MERGE (m)-[:LOCATED_IN]->(l);