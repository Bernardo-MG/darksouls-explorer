CREATE (i:Item {name: 'Item name', description: 'Description'});
CREATE (m:Merchant {name: 'Merchant'});
MATCH (n {name: 'Item name'}), (m {name: 'Merchant'}) MERGE (m)-[:SELLS {price: 10}]->(n);