CREATE (i:Item {name: 'Item', description: 'Description'});
CREATE (m:Merchant {name: 'Merchant'});
MATCH (n {name: 'Item'}), (m {name: 'Merchant'}) MERGE (m)-[:SELLS {price: 10}]->(n);