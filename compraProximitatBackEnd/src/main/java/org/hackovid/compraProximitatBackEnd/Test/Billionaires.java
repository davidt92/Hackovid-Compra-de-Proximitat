package org.hackovid.compraProximitatBackEnd.Test;

public class Billionaires
{
    int id;
    String firstName;
    String lastName;
    String career;

    public Billionaires()
    {
    }

    public Billionaires(int id, String firstName, String lastName, String career)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.career = career;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
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

    public String getCareer()
    {
        return career;
    }

    public void setCareer(String career)
    {
        this.career = career;
    }
}
