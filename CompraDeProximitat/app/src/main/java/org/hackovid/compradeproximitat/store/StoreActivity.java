package org.hackovid.compradeproximitat.store;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hackovid.CompraProximitatDto.dto.ProductDto;
import org.hackovid.compradeproximitat.GlobalVariables.GlobalVariables;
import org.hackovid.compradeproximitat.R;
import org.hackovid.compradeproximitat.Utilities;
import org.hackovid.compradeproximitat.product.StoreAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreActivity extends AppCompatActivity
{
    private static Logger LOGGER = LogManager.getLogger(StoreActivity.class);

    private FloatingActionButton addProduct;
    private Button qrScanner;

    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private StoreAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private String postalCode;
    private String city;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        addProduct = findViewById(R.id.floatingActionButton3);
        recyclerView = findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true); // Increase performance
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        requestQueue = Volley.newRequestQueue(this);

        qrScanner = findViewById(R.id.toolbarbtn);

        addProduct.setOnClickListener(v->{
           this.addNewProduct();
        });

        qrScanner.setOnClickListener(v ->
        {
            this.startCamera();
        });

        Intent intent = getIntent();
        postalCode = intent.getStringExtra(GlobalVariables.POSTAL_CODE);
        city = intent.getStringExtra(GlobalVariables.CITY);
        userName = intent.getStringExtra(GlobalVariables.USERNAME);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar); // setting toolbar is important before calling getSupportActionBar()
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Botiga de "+userName);

        this.getProductsFromUser(userName);

    }

    private void startCamera()
    {
        try
        {

            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

            startActivityForResult(intent, 0);

        }
        catch (Exception e)
        {

            Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
            Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
            startActivity(marketIntent);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0)
        {

            if (resultCode == RESULT_OK)
            {
                String contents = data.getStringExtra("SCAN_RESULT");
                Toast.makeText(this, contents, Toast.LENGTH_SHORT).show();
            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }
        }
    }

    private void addNewProduct()
    {
        Intent intent = new Intent(this, NewProductActivity.class);
        intent.putExtra(GlobalVariables.USERNAME, userName);
        intent.putExtra(GlobalVariables.POSTAL_CODE, postalCode);
        intent.putExtra(GlobalVariables.CITY, city);
        startActivity(intent);
    }

    private void getProductsFromUser(String userName)
    {
        ProgressDialog progress = ProgressDialog.show(this, "Carregant Productes",
                "Siusplau esperi", true);

        ObjectMapper mapper = new ObjectMapper();
        String url = GlobalVariables.server + "/getproductsusername/" + userName;
        //String url = GlobalVariables.server + "/image";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response ->
                {
                    response = Utilities.convertUTF8ToString(response);
                    try
                    {
                        ProductDto[] productDtoArray = mapper.readValue(response,ProductDto[].class);
                        // Creating new ArrayList as Arrays.asList() does not support structural modifications
                        this.addProductsToScreen(new ArrayList<>(Arrays.asList(productDtoArray)));
                        progress.dismiss();
                    }
                    catch (JsonProcessingException e)
                    {
                        e.printStackTrace();
                    }
                },
                error -> {System.out.println("That didn't work!"+error);
                    Toast.makeText(this, "Hi ha hagut un error en obtenir els productes del servidor, torna-ho a intentar mes tard", Toast.LENGTH_SHORT).show();});
        // Add the request to the RequestQueue.

        requestQueue.add(stringRequest);

    }

    private void addProductsToScreen(List<ProductDto> productDtoList)
    {
        System.out.println("Add products to screen" + productDtoList.size());
        if (productDtoList != null)
        {
            // Creating new ArrayList as Arrays.asList() does not support structural modifications
            recyclerViewAdapter = StoreAdapter.singletone(productDtoList);
            recyclerView.setAdapter(recyclerViewAdapter);

        }
    }
}
