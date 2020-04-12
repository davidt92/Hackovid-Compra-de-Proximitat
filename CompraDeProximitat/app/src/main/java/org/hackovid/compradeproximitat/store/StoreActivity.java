package org.hackovid.compradeproximitat.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hackovid.CompraProximitatDto.dto.ProductBoughtDto;
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

    Gson gson = new Gson();

    private FloatingActionButton addProduct;
    private FloatingActionButton qrScanner;

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
        qrScanner = findViewById(R.id.qrScanner_1);
        recyclerView = findViewById(R.id.recyclerView);

        //recyclerView.setHasFixedSize(true); // Increase performance
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        requestQueue = Volley.newRequestQueue(this);


        addProduct.setOnClickListener(v->{
           this.addNewProduct();
        });

        qrScanner.setOnClickListener(v->
        {
            this.startCamera();
        });

        Intent intent = getIntent();
        postalCode = intent.getStringExtra(GlobalVariables.POSTAL_CODE);
        city = intent.getStringExtra(GlobalVariables.CITY);
        userName = intent.getStringExtra(GlobalVariables.USERNAME);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
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

                this.validateCode(contents);
            }
            if (resultCode == RESULT_CANCELED)
            {
                //handle cancel
            }
        }
    }


    private void validateCode(String qrCode)
    {
        String url = GlobalVariables.server + "/checkproductbought/" + qrCode;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    if (response == null || response.length() == 0)
                    {
                        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);

// 2. Chain together various setter methods to set the dialog characteristics
                        builder.setMessage("El codi qr no correspon amb cap producte/servei")
                                .setTitle("Codi QR Incorrecte");

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    else
                    {
                        ProductBoughtDto productBoughtDto = gson.fromJson(response, ProductBoughtDto.class);

                        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setPositiveButton("Usar codi", (dialog, id) ->
                        {
                            this.useCode(qrCode);
                        });
                        builder.setNegativeButton("Cancel·lar", (dialog, id) ->
                        {
                            // User cancelled the dialog
                        });

// 2. Chain together various setter methods to set the dialog characteristics
                        builder.setMessage("A el client li correspont un "+productBoughtDto.getProductName())
                                .setTitle("Codi QR Correcte");

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                },
                error -> {});

        requestQueue.add(stringRequest);

             //   requestQueue

    }

    private void useCode(String qrCode)
    {
        String url = GlobalVariables.server + "/deleteboughtproduct/" + qrCode;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response ->
                {
                    System.out.println(response);
                    int intresponse = Integer.valueOf(response);
                    System.out.println(intresponse);
                    if (intresponse == 1)
                    {
                        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);

// 2. Chain together various setter methods to set the dialog characteristics
                        builder.setMessage("Producte/Servei usat correctament")
                                .setTitle("Producte/Servei usat correctament");

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    else
                    {
                        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);

// 2. Chain together various setter methods to set the dialog characteristics
                        builder.setMessage("Producte/Servei usat incorrectament")
                                .setTitle("Producte/Servei usat incorrectament");

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                },
                error -> {});

        requestQueue.add(stringRequest);
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
        ProgressDialog progress = ProgressDialog.show(this, "Carregant Productes/Serveis",
                "Sisplau esperi", true);

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
                    Toast.makeText(this, "Hi ha hagut un error en obtenir els productes/serveis del servidor, torna-ho a intentar més tard", Toast.LENGTH_SHORT).show();});
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
