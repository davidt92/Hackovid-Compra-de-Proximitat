package org.hackovid.compradeproximitat.store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hackovid.compradeproximitat.GlobalVariables.GlobalVariables;
import org.hackovid.compradeproximitat.R;
import org.hackovid.compradeproximitat.client.ClientActivity;
import org.json.JSONException;
import org.json.JSONObject;

public class StoreActivity extends AppCompatActivity
{
    private static Logger LOGGER = LogManager.getLogger(StoreActivity.class);

    private FloatingActionButton addProduct;

    private RequestQueue requestQueue;

    private String postalCode;
    private String city;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        addProduct = findViewById(R.id.floatingActionButton3);

        requestQueue = Volley.newRequestQueue(this);

        addProduct.setOnClickListener(v->{
            this.addNewProduct();
        });

        Intent intent = getIntent();
        postalCode = intent.getStringExtra(GlobalVariables.POSTAL_CODE);
        city = intent.getStringExtra(GlobalVariables.CITY);
        userName = intent.getStringExtra(GlobalVariables.USERNAME);

    }

    private void addNewProduct()
    {
        Intent intent = new Intent(this, NewProductActivity.class);
        intent.putExtra(GlobalVariables.USERNAME, userName);
        intent.putExtra(GlobalVariables.POSTAL_CODE, postalCode);
        intent.putExtra(GlobalVariables.CITY, city);
        startActivity(intent);
    }
}
