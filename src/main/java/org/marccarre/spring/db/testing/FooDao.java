package org.marccarre.spring.db.testing;

import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

public class FooDao {
    private JdbcTemplate jdbcTemplate;

    public FooDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Foo> getById(int id) {
        return jdbcTemplate.query("{call GetFoosById(?)}", new FooRowMapper(), id);
    }

    public List<Foo> getByIdCallableStatement(int id) {
        final int fooId = id;
        return jdbcTemplate.execute(
                new CallableStatementCreator() {
                    public CallableStatement createCallableStatement(Connection con) throws SQLException{
                        CallableStatement cs = con.prepareCall("{call GetFoosById(?)}");
                        cs.setInt(1, fooId);
                        return cs;
                    }
                },
                new CallableStatementCallback<List<Foo>>() {
                    public List<Foo> doInCallableStatement(CallableStatement cs) throws SQLException{
                        cs.execute();
                        List<Foo> foos = new ArrayList<Foo>();

                        if (cs.getMoreResults()) {
                            ResultSet rs = cs.getResultSet();
                            FooRowMapper mapper = new FooRowMapper();
                            int rowIndex = 0;
                            while (rs.next()) {
                                foos.add(mapper.mapRow(rs, rowIndex));
                                rowIndex++;
                            }
                        }

                        return foos;
                    }
                }
        );
    }
}