package org.hackovid.compraProximitatBackEnd.database;

import com.google.gson.Gson;
import org.hackovid.CompraProximitatDto.dto.ProductBoughtDto;
import org.hackovid.CompraProximitatDto.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class ProductsBoughtDB
{
    private String tableName = "PRODUCTS_BOUGHT";
    private final static Logger LOGGER = Logger.getLogger(ProductsBoughtDB.class.getName());

    private Gson gson = new Gson();

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public int buyProduct(ProductBoughtDto productBoughtDto)
    {
        return jdbcTemplate.update("INSERT INTO " + tableName + " (generated_code, product_id, product_name, username)"
                + "VALUES (?, ?, ?, ?);", productBoughtDto.getGeneratedCode(), productBoughtDto.getProductId(), productBoughtDto.getProductName(), productBoughtDto.getUsername());
    }

    public List<ProductBoughtDto> getProductBoughtByUsername(String username)
    {
        return jdbcTemplate.query("Select * from " + tableName + " where username = '"+username+"'", new ProductBoughtRowMapper());
    }

    public ProductBoughtDto getProductByGeneratedCode(String generatedCode)
    {
        List<ProductBoughtDto> productBoughtDtoList = jdbcTemplate.query("Select * from " + tableName + " where generated_code = '" + generatedCode + "'", new ProductBoughtRowMapper());

        if (productBoughtDtoList.size() == 0)
        {
            return null;
        }
        else
        {
            return productBoughtDtoList.get(0);
        }
    }


    class ProductBoughtRowMapper implements RowMapper<ProductBoughtDto>
    {
        @Override
        public ProductBoughtDto mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            ProductBoughtDto productBoughtDto = new ProductBoughtDto();
            try
            {
                productBoughtDto.setGeneratedCode(rs.getString("generated_code"));
            }
            catch (Exception e)
            {
                LOGGER.info("generated_code does not exist as column in DB response");
            }

            try
            {
                productBoughtDto.setProductId(rs.getInt("product_id"));
            }
            catch (Exception e)
            {
                LOGGER.info("product_id does not exist as column in DB response");
            }

            try
            {
                productBoughtDto.setUsername(rs.getString("username"));
            }
            catch (Exception e)
            {
                LOGGER.info("username does not exist as column in DB response");
            }

            try
            {
                productBoughtDto.setProductName(rs.getString("product_name"));
            }
            catch (Exception e)
            {
                LOGGER.info("product_name does not exist as column in DB response");
            }


            return productBoughtDto;
        }
    }
}
