package org.hackovid.compradeproximitat.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hackovid.CompraProximitatDto.dto.ProductBoughtDto;
import org.hackovid.CompraProximitatDto.dto.ProductDto;
import org.hackovid.compradeproximitat.GlobalVariables.GlobalVariables;
import org.hackovid.compradeproximitat.R;
import org.hackovid.compradeproximitat.Utilities;
import org.hackovid.compradeproximitat.product.ClientBoughtAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoughtProductsActivity extends AppCompatActivity
{
    private RequestQueue requestQueue;
    String userName;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bought_products);

        recyclerView = findViewById(R.id.recyclerViewClient_1);

        Intent intent = getIntent();
        userName = intent.getStringExtra(GlobalVariables.USERNAME);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        requestQueue = Volley.newRequestQueue(this);
        this.getProductsBoughtForUser(userName);
    }


    private void getProductsBoughtForUser(String username)
    {
        ProgressDialog progress = ProgressDialog.show(this, "Carregant Productes/Serveis Comprats",
                "Sisplau esperi", true);

        ObjectMapper mapper = new ObjectMapper();
        String url = GlobalVariables.server + "/productsboughtbyuser/" + username;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response ->
                {
                    response = Utilities.convertUTF8ToString(response);
                    try
                    {
                        ProductBoughtDto[] productBoughtDtos = mapper.readValue(response,ProductBoughtDto[].class);
                        List<ProductBoughtDto> productBoughtDtoList = new ArrayList<ProductBoughtDto>(Arrays.asList(productBoughtDtos));

                        System.out.println(productBoughtDtoList);

                        this.addProductBought(productBoughtDtoList);

                        progress.dismiss();
                    }
                    catch (JsonProcessingException e)
                    {
                        e.printStackTrace();
                    }
                },
                error -> {System.out.println("That didn't work!"+error);
                    progress.dismiss();
                    Toast.makeText(this, "Hi ha hagut un error en obtenir els productes/serveis del servidor, torna-ho a intentar més tard", Toast.LENGTH_SHORT).show();});
        // Add the request to the RequestQueue.

        requestQueue.add(stringRequest);

    }


    private void addProductBought(List<ProductBoughtDto> productBoughtDtoList)
    {
        ClientBoughtAdapter clientBoughtAdapter = new ClientBoughtAdapter(productBoughtDtoList, this, userName);
        recyclerView.setAdapter(clientBoughtAdapter);
    }

}
