package org.hackovid.CompraProximitatDto.dto;

public class UserDto
{
    int id;
    int userType;
    String userName;
    String firstName;
    String lastName;
    String direction;
    String city;
    String postalCode;
    String passwordHash;
    String email;

    public UserDto()
    {
    }

    public UserDto(int userType, String userName, String firstName, String lastName, String direction, String city, String postalCode, String passwordHash, String email)
    {
        this.userType = userType;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.direction = direction;
        this.city = city;
        this.postalCode = postalCode;
        this.passwordHash = passwordHash;
        this.email = email;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getDirection()
    {
        return direction;
    }

    public void setDirection(String direction)
    {
        this.direction = direction;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getPostalCode()
    {
        return postalCode;
    }

    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    public String getPasswordHash()
    {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash)
    {
        this.passwordHash = passwordHash;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getUserType()
    {
        return userType;
    }

    public void setUserType(int userType)
    {
        this.userType = userType;
    }
}
