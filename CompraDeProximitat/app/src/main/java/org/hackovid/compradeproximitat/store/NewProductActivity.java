package org.hackovid.compradeproximitat.store;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hackovid.CompraProximitatDto.dto.ProductDto;
import org.hackovid.compradeproximitat.GlobalVariables.GlobalVariables;
import org.hackovid.compradeproximitat.R;
import org.hackovid.compradeproximitat.Utilities;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewProductActivity extends AppCompatActivity
{
    private static Logger LOGGER = LogManager.getLogger(NewProductActivity.class);

    private Gson gson = new Gson();
    private RequestQueue requestQueue;

    private ExecutorService executorService = Executors.newFixedThreadPool(1);

    private Button captureButton;
    private Button addProduct;
    private EditText productName;
    private EditText description;


    private final int PERMISSION_CODE = 1000;
    private byte[] imageByteArray;

    private Timer timer = new Timer();

    Uri image_uri;
    private int IMAGE_CAPTURE_CODE = 1001;

    private boolean could_add_product = true;

    private String postalCode;
    private String city;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        captureButton = findViewById(R.id.capture_image_button);
        addProduct = findViewById(R.id.add_product_button);
        productName = findViewById(R.id.product_name);
        description = findViewById(R.id.description);


        requestQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        postalCode = intent.getStringExtra(GlobalVariables.POSTAL_CODE);
        city = intent.getStringExtra(GlobalVariables.CITY);
        userName = intent.getStringExtra(GlobalVariables.USERNAME);

        captureButton.setOnClickListener(v ->{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                {
                    String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                    requestPermissions(permission, PERMISSION_CODE);

                }
                else
                {
                    this.openCamera();
                }
            }
        });

        addProduct.setOnClickListener( v -> {
            this.addProduct();
        });


    }

    private void openCamera()
    {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");

        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        // Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case PERMISSION_CODE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    openCamera();
                }
                else
                {
                    Toast.makeText(this, "Permis denegat", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            could_add_product = false;
            System.out.println("Capture correct");
            captureButton.setText("Fotografia afegida correctament");
            //Toast.makeText(this, "El botó de afegir producte s'habilitara cuan s'haigi carregat la imatge", Toast.LENGTH_SHORT).show();
            executorService.submit(()->{
                try
                {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    imageByteArray = byteArrayOutputStream .toByteArray();
                    could_add_product = true;
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            });

        }
    }

    private void addProduct()
    {
        ProgressDialog progress = ProgressDialog.show(this, "Siusplau esperi",
                "Afegint producte", true);

        System.out.println("Adding product");
        while (could_add_product==false);


        String url = GlobalVariables.server + "/addproduct/";

        ProductDto productDto = new ProductDto(userName, productName.getText().toString(), description.getText().toString(), imageByteArray, GlobalVariables.PRODUCT_AVALIABLE, postalCode);

        try
        {
            JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url,new JSONObject(gson.toJson(productDto)),
                    response ->
                    {
                        System.out.println(response);
                        progress.dismiss();
                        finish();
                    },
                    error -> {System.out.println(error);} );

            // Add the request to the RequestQueue.
            requestQueue.add(jsonRequest);
        }
        catch (JSONException e)
        {
            LOGGER.error("Error parsing object to JSONObject", e);
            e.printStackTrace();
        }
    }
}
