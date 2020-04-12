package org.hackovid.CompraProximitatDto.dto;

public class ProductBoughtDto
{
    int productId;
    String productName;
    String username;
    String generatedCode;
    String storeAddress;

    public ProductBoughtDto()
    {
    }

    public ProductBoughtDto(int productId, String username, String generatedCode, String storeAddress)
    {
        this.productId = productId;
        this.username = username;
        this.generatedCode = generatedCode;
    }

    public int getProductId()
    {
        return productId;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getGeneratedCode()
    {
        return generatedCode;
    }

    public void setGeneratedCode(String generatedCode)
    {
        this.generatedCode = generatedCode;
    }

    public String getStoreAddress()
    {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress)
    {
        this.storeAddress = storeAddress;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }
}

