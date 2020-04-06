package org.hackovid.compraProximitatBackEnd.database;

import org.hackovid.compraProximitatBackEnd.Test.Billionaires;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class H2Access
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Billionaires> findAllBillionaires()
    {
        return jdbcTemplate.query("Select * from BILLIONAIRES", new BillionairesRowMapper());
    }

    class BillionairesRowMapper implements RowMapper<Billionaires>
    {
        @Override
        public Billionaires mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            Billionaires billionaires = new Billionaires();
            billionaires.setId(rs.getInt("id"));
            billionaires.setFirstName(rs.getString("first_name"));
            billionaires.setLastName(rs.getString("last_name"));
            billionaires.setCareer(rs.getString("career"));

            return billionaires;
        }
    }
}
