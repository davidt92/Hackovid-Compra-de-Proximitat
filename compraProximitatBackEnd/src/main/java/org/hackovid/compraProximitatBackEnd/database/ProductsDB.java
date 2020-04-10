package org.hackovid.compraProximitatBackEnd.database;

import com.google.gson.Gson;
import org.hackovid.CompraProximitatDto.dto.ProductDto;
import org.hackovid.CompraProximitatDto.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class ProductsDB
{
    private String tableName = "PRODUCTS";
    private final static Logger LOGGER = Logger.getLogger(ProductsDB.class.getName());

    private Gson gson = new Gson();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int addProduct(ProductDto productDto)
    {
        return jdbcTemplate.update("INSERT INTO " + tableName + " (seller_username, product_name, description, image, state, postal_code)"
                + "VALUES (?, ?, ?, ?, ?, ?);", productDto.getSellerUsername(), productDto.getProductName(), productDto.getDescription(), productDto.getImage(), productDto.getState(), productDto.getPostal_code());
    }

    public List<ProductDto> getAllProductsFromPostalCode(String postalCode)
    {
        return jdbcTemplate.query("Select * from " + tableName + "where postal_code = "+postalCode, new ProductDtoRowMapper());
    }

    public List<ProductDto> getAllProductsFromUsername(String userName)
    {
        return jdbcTemplate.query("Select * from " + tableName + "where seller_username = "+userName, new ProductDtoRowMapper());
    }

    class ProductDtoRowMapper implements RowMapper<ProductDto>
    {
        @Override
        public ProductDto mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            ProductDto productDto = new ProductDto();
            try
            {
                productDto.setId(rs.getInt("product_id"));
            }
            catch (Exception e)
            {
                LOGGER.info("id does not exist as column in DB response");
            }

            try
            {
                productDto.setSellerUsername(rs.getString("seller_username"));
            }
            catch (Exception e)
            {
                LOGGER.info("seller_username does not exist as column in DB response");
            }

            try
            {
                productDto.setProductName(rs.getString("product_name"));
            }
            catch (Exception e)
            {
                LOGGER.info("product_name does not exist as column in DB response");
            }

            try
            {
                productDto.setDescription(rs.getString("description"));
            }
            catch (Exception e)
            {
                LOGGER.info("description does not exist as column in DB response");
            }

            try
            {
                productDto.setImage(rs.getBytes("image"));
            }
            catch (Exception e)
            {
                LOGGER.info("image does not exist as column in DB response");
            }

            try
            {
                productDto.setState(rs.getInt("state"));
            }
            catch (Exception e)
            {
                LOGGER.info("state does not exist as column in DB response");
            }

            try
            {
                productDto.setPostal_code(rs.getString("postal_code"));
            }
            catch (Exception e)
            {
                LOGGER.info("postal_code does not exist as column in DB response");
            }

            return productDto;
        }
    }
}
