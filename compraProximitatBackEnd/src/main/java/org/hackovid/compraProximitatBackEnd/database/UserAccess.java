package org.hackovid.compraProximitatBackEnd.database;

import com.google.gson.Gson;
import org.hackovid.CompraProximitatDto.dto.GlobalVariablesDto;
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
public class UserAccess
{
    private String tableName = "USERS_TABLE";
    private final static Logger LOGGER = Logger.getLogger(UserAccess.class.getName());

    private Gson gson = new Gson();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<UserDto> getAllUsers()
    {
        return jdbcTemplate.query("Select * from "+tableName, new UsersDtoRowMapper());
    }

    public String checkPassword(String username, String passwordHash)
    {
        List<UserDto> userDtoList = jdbcTemplate.query("Select * from "+tableName+" where username = '"+username+"'", new UsersDtoRowMapper());

        if (userDtoList.size() == 0)
        {
            return GlobalVariablesDto.USERNAME_DOES_NOT_EXIST;
        }
        else
        {
            if (userDtoList.get(0).getPasswordHash().equals(passwordHash))
            {
                return gson.toJson(userDtoList.get(0));
            }
            else
            {
                return GlobalVariablesDto.WRONG_PASSWORD;
            }
        }
    }

    public int addUser(UserDto userDto)
    {
        return jdbcTemplate.update("INSERT INTO " + tableName + " (user_type, username, first_name, last_name, direction, city, postal_code, password_hash, email)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);", userDto.getUserType(), userDto.getUserName(), userDto.getFirstName(), userDto.getLastName(), userDto.getDirection(), userDto.getCity(), userDto.getPostalCode(), userDto.getPasswordHash(), userDto.getEmail());
    }



    class UsersDtoRowMapper implements RowMapper<UserDto>
    {
        @Override
        public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            UserDto userDto = new UserDto();
            try
            {
                userDto.setId(rs.getInt("id"));
            }
            catch (Exception e)
            {
                LOGGER.info("id does not exist as column in DB response");
            }

            try
            {
                userDto.setUserType(rs.getInt("user_type"));
            }
            catch (Exception e)
            {
                LOGGER.info("user_type does not exist as column in DB response");
            }

            try
            {
                userDto.setFirstName(rs.getString("first_name"));
            }
            catch (Exception e)
            {
                LOGGER.info("first_name does not exist as column in DB response");
            }

            try
            {
                userDto.setLastName(rs.getString("last_name"));
            }
            catch (Exception e)
            {
                LOGGER.info("last_name does not exist as column in DB response");
            }

            try
            {
                userDto.setDirection(rs.getString("direction"));
            }
            catch (Exception e)
            {
                LOGGER.info("direction does not exist as column in DB response");
            }

            try
            {
                userDto.setCity(rs.getString("city"));
            }
            catch (Exception e)
            {
                LOGGER.info("city does not exist as column in DB response");
            }

            try
            {
                userDto.setPostalCode(rs.getString("postal_code"));
            }
            catch (Exception e)
            {
                LOGGER.info("postal_code does not exist as column in DB response");
            }

            try
            {
                userDto.setPasswordHash(rs.getString("password_hash"));
            }
            catch (Exception e)
            {
                LOGGER.info("password_hash does not exist as column in DB response");
            }

            try
            {
                userDto.setEmail(rs.getString("email"));
            }
            catch (Exception e)
            {
                LOGGER.info("email does not exist as column in DB response");
            }

            try
            {
                userDto.setUserName(rs.getString("username"));
            }
            catch (Exception e)
            {
                LOGGER.info("username does not exist as column in DB response");
            }

            return userDto;
        }
    }
}
