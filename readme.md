# Dark Souls Explorer Backend

No-SQL experiment using Dark Souls data taken from the wiki.

Currently the data is not included in the project.

[![Release docs](https://img.shields.io/badge/docs-release-blue.svg)][site-release]
[![Development docs](https://img.shields.io/badge/docs-develop-blue.svg)][site-develop]

[![Release javadocs](https://img.shields.io/badge/javadocs-release-blue.svg)][javadoc-release]
[![Development javadocs](https://img.shields.io/badge/javadocs-develop-blue.svg)][javadoc-develop]

## Features

Several technologies are combined to make this work:

- [Spring MVC](https://spring.io/)
- [Neo4j](https://neo4j.com/)

## Documentation

Documentation is always generated for the latest release, kept in the 'master' branch:

- The [latest release documentation page][site-release].
- The [latest release Javadoc site][javadoc-release].

Documentation is also generated from the latest snapshot, taken from the 'develop' branch:

- The [the latest snapshot documentation page][site-develop].
- The [latest snapshot Javadoc site][javadoc-develop].

The documentation site is actually a Maven site, and its sources are included in the project. If required it can be generated by using the following Maven command:

```
mvn verify site -P development
```

The verify phase is required, otherwise some of the reports won't be generated.

## Usage

The application is coded in Java, using Maven to manage the project.

### Profiles

Maven profiles are included for setting up the database.

| Profile     | Server                   |
|-------------|--------------------------|
| development | Development settings     |
| production  | Production settings      |

### Database

Before running, start a local Neo4j database. This will be populated with the dataset.

The database should accept the username neo4j with password secret.

### Running

To run the project locally use the following Maven command:

```
mvn spring-boot:run -P development
```

It will be accessible at [http://localhost:8080/](http://localhost:8080/).

### Docker image

Alternatively, a Docker compose file is included.

```
docker-compose -f docker/docker-compose.yml up
```

This will start a Neo4j database, with the dashboard available at [http://localhost:7474/](http://localhost:7474/). And the backend, available at http://localhost:8080.

### Running the tests

The project requires a database and a server for being able to run the integration tests.

Just like running the project, an embedded server with an in-memory database can be used:

```
mvn verify -P development
```

## Collaborate

Any kind of help with the project will be well received, and there are two main ways to give such help:

- Reporting errors and asking for extensions through the issues management
- or forking the repository and extending the project

### Issues management

Issues are managed at the GitHub [project issues tracker][issues], where any Github user may report bugs or ask for new features.

### Getting the code

If you wish to fork or modify the code, visit the [GitHub project page][scm], where the latest versions are always kept. Check the 'master' branch for the latest release, and the 'develop' for the current, and stable, development version.

## License

The project has been released under the [MIT License][license].

[issues]: https://github.com/bernardo-mg/darksouls-explorer-backend/issues
[javadoc-develop]: https://docs.bernardomg.com/development/maven/darksouls-explorer-backend/apidocs
[javadoc-release]: https://docs.bernardomg.com/maven/darksouls-explorer-backend/apidocs
[license]: https://www.opensource.org/licenses/mit-license.php
[scm]: https://github.com/bernardo-mg/darksouls-explorer-backend
[site-develop]: https://docs.bernardomg.com/development/maven/darksouls-explorer-backend
[site-release]: https://docs.bernardomg.com/maven/darksouls-explorer-backend
