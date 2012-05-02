spring.db.testing
=================

Sample code to use Spring embedded database API and unit test against an in-memory HSQLDB.

Discussion: http://stackoverflow.com/questions/10338044/create-a-stored-procedure-in-hsqldb-with-spring-embedded-databases-api

Conclusion:
- Spring JdbcTemplate "call" statement isn't portable accross different RDMS.
- Spring StoredProcedure class works at least on HSQLDB and Sybase, and is likely to have a better portability.