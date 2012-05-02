DROP TABLE Foos IF EXISTS;

CREATE TABLE Foos (
    id INTEGER,
    name VARCHAR(12),
    value VARCHAR(12)
);

DROP PROCEDURE GetFoosById IF EXISTS;

CREATE PROCEDURE GetFoosById(IN fooId INTEGER)
    READS SQL DATA
    LANGUAGE JAVA
    DYNAMIC RESULT SETS 1
    EXTERNAL NAME 'CLASSPATH:org.marccarre.spring.db.testing.StoredProcedures.getFooById';

-- GetFoosById would otherwise have been like the below.
-- but Spring splits the statement for each semi-colon it finds.
--
--CREATE PROCEDURE GetFoosById(IN fooId INTEGER)
--  READS SQL DATA
--  DYNAMIC RESULT SETS 1
--  BEGIN ATOMIC
--      DECLARE resultSet CURSOR WITHOUT HOLD WITH RETURN FOR SELECT name, value FROM Foos WHERE id = fooId;
--      OPEN resultSet
--  END;
