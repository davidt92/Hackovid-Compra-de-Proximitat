package org.hackovid.compraProximitatBackEnd.rest;

import com.google.gson.Gson;
import org.hackovid.compraProximitatBackEnd.Test.Billionaires;
import org.hackovid.compraProximitatBackEnd.Test.TestObject;
import org.hackovid.compraProximitatBackEnd.database.H2Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller
{
    @Autowired
    private H2Access dbAccess;

    @RequestMapping(value = "/personas", method = RequestMethod.GET)
    public String index()
    {
        Gson gson = new Gson();
        TestObject test = new TestObject(3);

        List<Billionaires> list = dbAccess.findAllBillionaires();

        for (Billionaires bill : list)
        {
            System.out.println(bill.getFirstName()+" "+bill.getLastName());
        }

        return gson.toJson(test);
    }
}
