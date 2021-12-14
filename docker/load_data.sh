#!/bin/bash

cypher-shell -a bolt://graph-db:7687 --format plain < 'MATCH(n) DELETE n';

cypher-shell -a bolt://graph-db:7687 --format plain < /ds/darksouls_1/import.cypher;

cypher-shell -a bolt://graph-db:7687 --format plain < /ds/darksouls_1/postprocess.cypher;