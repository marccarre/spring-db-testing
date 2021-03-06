[![Build Status](https://travis-ci.org/marccarre/spring-db-testing.svg?branch=master)](https://travis-ci.org/marccarre/spring-db-testing)
[![Coverage Status](https://coveralls.io/repos/github/marccarre/spring-db-testing/badge.svg?branch=master)](https://coveralls.io/github/marccarre/spring-db-testing?branch=master)

# spring.db.testing

Sample code to use Spring embedded database API and unit test against an in-memory HSQLDB.

Discussion: http://stackoverflow.com/questions/10338044/create-a-stored-procedure-in-hsqldb-with-spring-embedded-databases-api

Conclusion:
- SQL "call" statement isn't portable accross different RDMS, at least when using Spring's JdbcTemplate.
- Spring StoredProcedure class works fine though, at least on HSQLDB and Sybase, and is probably likely to have a better portability.