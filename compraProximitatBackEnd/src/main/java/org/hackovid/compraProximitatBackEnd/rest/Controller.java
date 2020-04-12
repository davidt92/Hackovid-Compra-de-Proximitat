package org.hackovid.compraProximitatBackEnd.rest;

import com.google.gson.Gson;
import org.hackovid.CompraProximitatDto.dto.ProductBoughtDto;
import org.hackovid.CompraProximitatDto.dto.ProductDto;
import org.hackovid.CompraProximitatDto.dto.UserDto;
import org.hackovid.CompraProximitatDto.dto.Utilities;
import org.hackovid.compraProximitatBackEnd.database.ProductsBoughtDB;
import org.hackovid.compraProximitatBackEnd.database.ProductsDB;
import org.hackovid.compraProximitatBackEnd.database.UserAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

@RestController
public class Controller
{
    @Autowired
    private UserAccess usersDB;

    @Autowired
    private ProductsDB productsDB;

    @Autowired
    private ProductsBoughtDB productsBoughtDB;


    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public String getAllUsers()
    {
        Gson gson = new Gson();
        List<UserDto> list = usersDB.getAllUsers();
        return gson.toJson(list);
    }

    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public UserDto addUser(@RequestBody UserDto userDto)
    {
        try
        {
            usersDB.addUser(userDto);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return userDto;
    }

    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    public ProductDto addProduct(@RequestBody ProductDto productDto)
    {
        try
        {
            productsDB.addProduct(productDto);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return productDto;
    }

    @RequestMapping(value = "/image/{id}", method = RequestMethod.GET, produces = "image/jpg")
    public @ResponseBody byte[] getImage(@PathVariable String id) throws IOException
    {
        System.out.println("Get Image: " + id);
        return productsDB.getImageFromId(id);
    }

    @RequestMapping(value = "/getproductspostalcode/{postalcode}", method = RequestMethod.GET)
    public List<ProductDto> getProductsPostalCode(@PathVariable String postalcode)
    {
        List<ProductDto> productDtoList = null;

        try
        {
            productDtoList = productsDB.getAllProductsFromPostalCode(postalcode);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return productDtoList;
    }

    @RequestMapping(value = "/getproductsusername/{username}", method = RequestMethod.GET)
    public List<ProductDto> getProductsUserName(@PathVariable String username)
    {
        List<ProductDto> productDtoList = null;
        try
        {
            productDtoList = productsDB.getAllProductsFromUsername(username);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return productDtoList;
    }



    @RequestMapping(value = "/checkUserPassword/{username}/{passwordHash}", method = RequestMethod.GET)
    public String checkUserPassword(@PathVariable String username, @PathVariable String passwordHash)
    {
        return usersDB.checkPassword(username, passwordHash);
    }

    @RequestMapping(value = "/buyproduct/{username}/{productId}/{productName}", method = RequestMethod.GET)
    public ProductBoughtDto buyProduct(@PathVariable String username, @PathVariable int productId, @PathVariable String productName)
    {
        ProductBoughtDto productBoughtDto = new ProductBoughtDto();
        productBoughtDto.setUsername(username);
        productBoughtDto.setProductId(productId);
        productBoughtDto.setProductName(productName);
        productBoughtDto.setGeneratedCode(Utilities.MD5(String.valueOf(Math.random())));

        productsBoughtDB.buyProduct(productBoughtDto);

        return productBoughtDto;
    }

    @RequestMapping(value = "/checkproductbought/{generatedCode}", method = RequestMethod.GET)
    public ProductBoughtDto checkProductBought(@PathVariable String generatedCode)
    {
        System.out.println("HOLA");
        return productsBoughtDB.getProductByGeneratedCode(generatedCode);
    }

    @RequestMapping(value ="/productsboughtbyuser/{username}", method = RequestMethod.GET)
    public List<ProductBoughtDto> listOfProductsBoughtByUser(@PathVariable String username)
    {
        return productsBoughtDB.getProductBoughtByUsername(username);
    }
}
