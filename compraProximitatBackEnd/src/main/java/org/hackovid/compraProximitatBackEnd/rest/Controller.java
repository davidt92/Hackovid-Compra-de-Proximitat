package org.hackovid.compraProximitatBackEnd.rest;

import com.google.gson.Gson;
import org.hackovid.CompraProximitatDto.dto.UserDto;
import org.hackovid.compraProximitatBackEnd.database.UserAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller
{
    @Autowired
    private UserAccess dbAccess;

    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public String getAllUsers()
    {
        Gson gson = new Gson();
        List<UserDto> list = dbAccess.getAllUsers();
        return gson.toJson(list);
    }

    @PostMapping("/adduser")
    public UserDto addUser(@RequestBody UserDto userDto)
    {
        dbAccess.addUser(userDto);
        return userDto;
    }


    @RequestMapping(value = "/checkUserPassword/{username}/{passwordHash}", method = RequestMethod.GET)
    public String checkUserPassword(@PathVariable String username, @PathVariable String passwordHash)
    {
        return dbAccess.checkPassword(username, passwordHash);
    }
}
