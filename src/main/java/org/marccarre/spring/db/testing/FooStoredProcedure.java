package org.marccarre.spring.db.testing;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2012, Marc CARRE
 * Date: 27/04/12
 * Time: 07:27
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

public class FooStoredProcedure extends StoredProcedure {
    public static final String FOO_ID = "fooId";
    public static final String RESULTS = "results";
    private JdbcTemplate jdbcTemplate;

    public FooStoredProcedure(DataSource dataSource) {
        super(dataSource, "GetFoosById");
        declareParameter(new SqlParameter(FOO_ID, Types.INTEGER));
        declareParameter(new SqlReturnResultSet(RESULTS, new FooRowMapper()));
        compile();
    }

    protected Map<String, Object> executeRaw(int fooId) {
        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(FOO_ID, fooId);
        return super.execute(inputs);
    }

    public List<Foo> execute(int fooId) {
        Map<String, Object> results = executeRaw(fooId);
        return (List<Foo>)results.get(RESULTS);
    }
}
