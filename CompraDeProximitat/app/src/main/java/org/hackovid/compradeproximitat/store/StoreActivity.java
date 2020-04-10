package org.hackovid.compradeproximitat.store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hackovid.CompraProximitatDto.dto.ProductDto;
import org.hackovid.CompraProximitatDto.dto.UserDto;
import org.hackovid.compradeproximitat.GlobalVariables.GlobalVariables;
import org.hackovid.compradeproximitat.R;
import org.hackovid.compradeproximitat.Utilities;
import org.hackovid.compradeproximitat.client.ClientActivity;
import org.hackovid.compradeproximitat.product.Product;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends AppCompatActivity
{
    private static Logger LOGGER = LogManager.getLogger(StoreActivity.class);

    private FloatingActionButton addProduct;

    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private String postalCode;
    private String city;
    private String userName;

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        addProduct = findViewById(R.id.floatingActionButton3);
        recyclerView = findViewById(R.id.recyclerView);

        requestQueue = Volley.newRequestQueue(this);

        addProduct.setOnClickListener(v->{
            this.addNewProduct();
        });

        Intent intent = getIntent();
        postalCode = intent.getStringExtra(GlobalVariables.POSTAL_CODE);
        city = intent.getStringExtra(GlobalVariables.CITY);
        userName = intent.getStringExtra(GlobalVariables.USERNAME);

        List<Product> products = new ArrayList<>();


    }

    private void addNewProduct()
    {
        Intent intent = new Intent(this, NewProductActivity.class);
        intent.putExtra(GlobalVariables.USERNAME, userName);
        intent.putExtra(GlobalVariables.POSTAL_CODE, postalCode);
        intent.putExtra(GlobalVariables.CITY, city);
        startActivity(intent);
    }

    private void getProductsFromUser(StoreActivity activity, String userName)
    {
        String url = GlobalVariables.server + "/getproductsusername/" + userName;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response ->
                {
                    List<ProductDto> productDtoList = gson.fromJson(response, new TypeToken<List<ProductDto>>(){}.getType());
                    activity.addProductsToScreen(productDtoList);
                },
                error -> System.out.println("That didn't work!"+error));
        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }

    private void addProductsToScreen(List<ProductDto> productDtoList)
    {

    }
}
