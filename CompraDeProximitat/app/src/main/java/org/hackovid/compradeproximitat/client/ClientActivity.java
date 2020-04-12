package org.hackovid.compradeproximitat.client;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.hackovid.CompraProximitatDto.dto.ProductDto;
import org.hackovid.compradeproximitat.GlobalVariables.GlobalVariables;
import org.hackovid.compradeproximitat.R;
import org.hackovid.compradeproximitat.Utilities;
import org.hackovid.compradeproximitat.product.ClientAdapter;
import org.hackovid.compradeproximitat.product.StoreAdapter;

import java.util.Arrays;
import java.util.List;

public class ClientActivity extends AppCompatActivity
{

    private RequestQueue requestQueue;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private FloatingActionButton boughtProducts;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        requestQueue = Volley.newRequestQueue(this);

        recyclerView = findViewById(R.id.recyclerViewClient);
        boughtProducts = findViewById(R.id.boughtProducts);

        //recyclerView.setHasFixedSize(true); // Increase performance
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        String postalCode = intent.getStringExtra(GlobalVariables.POSTAL_CODE);
        String city = intent.getStringExtra(GlobalVariables.CITY);
        userName = intent.getStringExtra(GlobalVariables.USERNAME);

        boughtProducts.setOnClickListener(v->
        {
            System.out.println("Bought Products");
            Intent boughtIntent = new Intent(this, BoughtProductsActivity.class);
            boughtIntent.putExtra(GlobalVariables.USERNAME, userName);
            startActivity(boughtIntent);
        });

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar); // setting toolbar is important before calling getSupportActionBar()
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Productes/Serveis oferts a " + city);

        this.getProductsFromPostalCode(postalCode);

    }

    private void getProductsFromPostalCode(String postalCode)
    {

        ProgressDialog progress = ProgressDialog.show(this, "Carregant Productes/Serveis",
                "Sisplau esperi", true);

        ObjectMapper mapper = new ObjectMapper();
        String url = GlobalVariables.server + "/getproductspostalcode/" + postalCode;
        //String url = GlobalVariables.server + "/image";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response ->
                {
                    response = Utilities.convertUTF8ToString(response);
                    try
                    {
                        ProductDto[] productDtoArray = mapper.readValue(response,ProductDto[].class);
                        this.addProductsToScreen(Arrays.asList(productDtoArray));
                        progress.dismiss();
                    }
                    catch (JsonProcessingException e)
                    {
                        e.printStackTrace();
                    }
                },
                error -> {System.out.println("That didn't work!"+error);
                    Toast.makeText(this, "Hi ha hagut un error en obtenir els productes/serveis del servidor, torna-ho a intentar mes tard", Toast.LENGTH_SHORT).show();});
        // Add the request to the RequestQueue.

        requestQueue.add(stringRequest);

    }


    private void addProductsToScreen(List<ProductDto> productDtoList)
    {
        System.out.println("Add products to screen" + productDtoList.size());
        if (productDtoList != null)
        {
            recyclerViewAdapter = new ClientAdapter(productDtoList, this, userName);
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    }
}
