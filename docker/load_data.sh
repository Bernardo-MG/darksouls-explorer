#!/bin/bash

cypher-shell -a bolt://db:7687 --format plain < /ds/darksouls_1/import.cypher;

cypher-shell -a bolt://db:7687 --format plain < /ds/darksouls_1/postprocess.cypher;