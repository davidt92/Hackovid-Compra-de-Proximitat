package org.hackovid.CompraProximitatDto.dto;

import java.io.FileInputStream;

public class ProductDto
{
    private int id;
    private String sellerUsername;
    private String productName;
    private String description;
    private byte[] image;
    private int State;
    private String postal_code;

    public ProductDto()
    {
    }

    public ProductDto(String sellerUsername, String productName, String description, byte[] image, int state, String postal_code)
    {
        this.sellerUsername = sellerUsername;
        this.productName = productName;
        this.description = description;
        this.image = image;
        State = state;
        this.postal_code = postal_code;
    }

    public String getSellerUsername()
    {
        return sellerUsername;
    }

    public void setSellerUsername(String sellerUsername)
    {
        this.sellerUsername = sellerUsername;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public byte[] getImage()
    {
        return image;
    }

    public void setImage(byte[] image)
    {
        this.image = image;
    }

    public int getState()
    {
        return State;
    }

    public void setState(int state)
    {
        State = state;
    }

    public String getPostal_code()
    {
        return postal_code;
    }

    public void setPostal_code(String postal_code)
    {
        this.postal_code = postal_code;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
