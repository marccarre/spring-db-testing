package org.marccarre.spring.db.testing;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Copyright (C) 2012, Marc CARRE
 * Date: 27/04/12
 * Time: 08:29
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

public class FooDaoTest {
    private FooDao dao;
    private FooStoredProcedure sp;

    @Before
    public void setUp() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .build();
        dao = new FooDao(db);
        sp = new FooStoredProcedure(db);
    }

    @Test
    public void testGetFooUsingStoredProcedure() {
        // Using Spring's StoredProcedure seems the more portable approach
        List<Foo> foos = sp.execute(123);
        assertEquals(2, foos.size());
        assertEquals("Anakin", foos.get(0).name);
        assertEquals("Skywalker", foos.get(0).value);
        assertEquals("Homer", foos.get(1).name);
        assertEquals("Simpsons", foos.get(1).value);
    }

    @Test
    @Ignore("Fails as classic jdbcTemplate.execute doesn't work on {call GetFooById(?)}")
    public void testGetFooById() {
        // Classic JdbcTemplate's execute call isn't portable. Some RDMS will like the parenthesis. Some won't.
        List<Foo> foos = dao.getById(123);
        assertEquals(2, foos.size());
        assertEquals("Anakin", foos.get(0).name);
        assertEquals("Skywalker", foos.get(0).value);
        assertEquals("Homer", foos.get(1).name);
        assertEquals("Simpsons", foos.get(1).value);
    }

    @Test
    public void testGetFooByIdUsingCallableStatement() {
        // Using CallableStatement satisfies HSQLDB, but production database doesn't like the syntax with parenthesis.
        List<Foo> foos = dao.getByIdCallableStatement(123);
        assertEquals(2, foos.size());
        assertEquals("Anakin", foos.get(0).name);
        assertEquals("Skywalker", foos.get(0).value);
        assertEquals("Homer", foos.get(1).name);
        assertEquals("Simpsons", foos.get(1).value);
    }
}
