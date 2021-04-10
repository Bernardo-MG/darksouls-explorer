LOAD CSV WITH HEADERS FROM 'file:///people.csv' AS row
FIELDTERMINATOR ';'
WITH row WHERE row.name IS NOT NULL
MERGE (n:Person {name: row.name});

LOAD CSV WITH HEADERS FROM 'file:///organizations.csv' AS row
FIELDTERMINATOR ';'
WITH row WHERE row.name IS NOT NULL
MERGE (n:Organization {name: row.name});

LOAD CSV WITH HEADERS FROM 'file:///locations.csv' AS row
FIELDTERMINATOR ';'
WITH row WHERE row.name IS NOT NULL
MERGE (n:Location {name: row.name});

LOAD CSV WITH HEADERS FROM 'file:///covenants.csv' AS row
FIELDTERMINATOR ';'
WITH row WHERE row.name IS NOT NULL
MERGE (n:Covenant {name: row.name});



// Load ammunition
LOAD CSV WITH HEADERS FROM 'file:///ammunition.csv' AS row
WITH row WHERE row.name IS NOT NULL
MERGE (n:Item:Ammunition {name: row.name, description: COALESCE(row.description, '')});

// Load armors
LOAD CSV WITH HEADERS FROM 'file:///armors.csv' AS row
WITH row WHERE row.name IS NOT NULL
MERGE (n:Item:Armor {name: row.name, description: COALESCE(row.description, '')});

// Load catalysts
LOAD CSV WITH HEADERS FROM 'file:///catalysts.csv' AS row
WITH row WHERE row.name IS NOT NULL
MERGE (n:Item:Catalyst {name: row.name, description: COALESCE(row.description, '')});

// Load embers
LOAD CSV WITH HEADERS FROM 'file:///embers.csv' AS row
WITH row WHERE row.name IS NOT NULL
MERGE (n:Item:Ember {name: row.name, description: COALESCE(row.description, '')});

// Load key items
LOAD CSV WITH HEADERS FROM 'file:///key_items.csv' AS row
WITH row WHERE row.name IS NOT NULL
MERGE (n:Item:KeyItem {name: row.name, description: COALESCE(row.description, '')});

// Load miscelaneous items
LOAD CSV WITH HEADERS FROM 'file:///misc_items.csv' AS row
WITH row WHERE row.name IS NOT NULL
MERGE (n:Item {name: row.name, description: COALESCE(row.description, '')});

// Load rings
LOAD CSV WITH HEADERS FROM 'file:///rings.csv' AS row
WITH row WHERE row.name IS NOT NULL
MERGE (n:Item:Ring {name: row.name, description: COALESCE(row.description, '')});

// Load shields
LOAD CSV WITH HEADERS FROM 'file:///shields.csv' AS row
WITH row WHERE row.name IS NOT NULL
MERGE (n:Item:Shield {name: row.name, description: COALESCE(row.description, '')});

// Load souls
LOAD CSV WITH HEADERS FROM 'file:///souls.csv' AS row
WITH row WHERE row.name IS NOT NULL
MERGE (n:Item:Soul {name: row.name, description: COALESCE(row.description, '')});

// Load spells
LOAD CSV WITH HEADERS FROM 'file:///spells.csv' AS row
WITH row WHERE row.name IS NOT NULL
MERGE (n:Item:Spell {name: row.name, description: COALESCE(row.description, '')});

// Load talismans
LOAD CSV WITH HEADERS FROM 'file:///talismans.csv' AS row
WITH row WHERE row.name IS NOT NULL
MERGE (n:Item:Talisman {name: row.name, description: COALESCE(row.description, '')});

// Load upgrade materials
LOAD CSV WITH HEADERS FROM 'file:///upgrade_materials.csv' AS row
WITH row WHERE row.name IS NOT NULL
MERGE (n:Item:UpgradeMaterial {name: row.name, description: COALESCE(row.description, '')});

// Load weapons
LOAD CSV WITH HEADERS FROM 'file:///weapons.csv' AS row
WITH row WHERE row.name IS NOT NULL
MERGE (n:Item:Weapon {name: row.name, description: COALESCE(row.description, '')});



// Armor sets
LOAD CSV WITH HEADERS FROM 'file:///armor_sets.csv' AS row
WITH row WHERE row.name IS NOT NULL
MERGE (n:ArmorSet {name: row.name});

// Magic schools
LOAD CSV WITH HEADERS FROM 'file:///magic_schools.csv' AS row
WITH row WHERE row.name IS NOT NULL
MERGE (n:MagicSchool {name: row.name});

// Shield types
LOAD CSV WITH HEADERS FROM 'file:///shield_types.csv' AS row
WITH row WHERE row.name IS NOT NULL
MERGE (n:ShieldType {name: row.name});

// Weapon types
LOAD CSV WITH HEADERS FROM 'file:///weapon_types.csv' AS row
WITH row WHERE row.name IS NOT NULL
MERGE (n:WeaponType {name: row.name});



// Armor set relationships
LOAD CSV WITH HEADERS FROM 'file:///armors_armor_sets.csv' AS row
MATCH (a:Armor {name: row.armor})
MATCH (s:ArmorSet {name: row.set})
MERGE (a)-[:OF]->(s);

// Shield type relationships
LOAD CSV WITH HEADERS FROM 'file:///weapons_weapon_types.csv' AS row
MATCH (w:Weapon {name: row.weapon})
MATCH (t:WeaponType {name: row.type})
MERGE (w)-[:OF]->(t);

// Spell school relationships
LOAD CSV WITH HEADERS FROM 'file:///spells_magic_schools.csv' AS row
MATCH (s:Spell {name: row.spell})
MATCH (m:MagicSchool {name: row.school})
MERGE (s)-[:OF]->(m);

// Weapon type relationships
LOAD CSV WITH HEADERS FROM 'file:///shields_shield_types.csv' AS row
MATCH (s:Shield {name: row.weapon})
MATCH (t:ShieldType {name: row.type})
MERGE (s)-[:OF]->(t);



// Concepts
LOAD CSV WITH HEADERS FROM 'file:///concepts.csv' AS row
WITH row WHERE row.name IS NOT NULL
MERGE (n:Concept {name: row.name, description: COALESCE(row.description, '')});



// People origins
MATCH (p:Person), (l:Location)
WHERE toLower(p.name) ENDS WITH toLower('of ' + l.name)
MERGE (p)-[:OF]->(l);

// Alias
LOAD CSV WITH HEADERS FROM 'file:///alias.csv' AS row
MATCH (n {name: row.name})
MATCH (a {name: row.alias})
MERGE (n)-[:ALIAS]->(a);


// Mentions
LOAD CSV WITH HEADERS FROM 'file:///mentions.csv' AS row
FIELDTERMINATOR ';'
MATCH (s {name: row.source})
MATCH (d {name: row.destination})
MERGE (s)-[:MENTIONS]->(d);

MATCH (n:Item), (m)
WHERE
	NOT m:ArmorSet
	AND NOT m:MagicSchool
    AND toLower(n.description) CONTAINS toLower(m.name)
MERGE (n)-[:MENTIONS]->(m);


// Cut content
LOAD CSV WITH HEADERS FROM 'file:///cut_content.csv' AS row
MATCH (c {name: row.name})
set c:CutContent
return c;


// Games
CREATE (n:Game {name: 'Dark Souls'});
CREATE (n:Game {name: 'Dark Souls 2'});
CREATE (n:Game {name: 'Dark Souls 3'});

// Marks the data set source
// MATCH
//    (n),
//    (g)
// WHERE
//    NOT n:Game
//    AND g.name = 'Dark Souls'
// MERGE (n)-[:IN]->(g);