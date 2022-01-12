CREATE (i:Item:Weapon {name: 'Sword'});
CREATE (p:WeaponUpgradePath {name: 'Physical'});
CREATE (l:WeaponLevel {name: 'Sword Physical', weapon: 'Sword', path: 'Physical', level: 0, physicalDamage: 10});
CREATE (l:WeaponLevel {name: 'Sword Physical 1', weapon: 'Sword', path: 'Physical', level: 1, physicalDamage: 20});
CREATE (l:WeaponLevel {name: 'Sword Physical 2', weapon: 'Sword', path: 'Physical', level: 2, physicalDamage: 30});
CREATE (l:WeaponLevel {name: 'Sword Physical 3', weapon: 'Sword', path: 'Physical', level: 3, physicalDamage: 40});
CREATE (l:WeaponLevel {name: 'Sword Physical 4', weapon: 'Sword', path: 'Physical', level: 4, physicalDamage: 50});