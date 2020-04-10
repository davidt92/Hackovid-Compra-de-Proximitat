package org.hackovid.compradeproximitat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import org.hackovid.CompraProximitatDto.dto.GlobalVariablesDto;
import org.hackovid.CompraProximitatDto.dto.UserDto;
import org.hackovid.compradeproximitat.GlobalVariables.GlobalVariables;
import org.hackovid.compradeproximitat.client.ClientActivity;
import org.hackovid.compradeproximitat.location.MyLocation;
import org.hackovid.compradeproximitat.store.StoreActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity
{
    Gson gson = new Gson();
    // Global variables
    Button loginButton;
    Button registerButton;
    EditText userName;
    EditText password;

    RequestQueue requestQueue;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyLocation myLocation = MyLocation.getSingletonLocation(this);

        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.regist_button);

        userName = findViewById(R.id.user_name_text);
        password = findViewById(R.id.password_text);

        requestQueue = Volley.newRequestQueue(this);



        // Add the request to the RequestQueue.

        loginButton.setOnClickListener(v ->
        {
            System.out.println(userName.getText().toString());
            System.out.println(password.getText().toString());
            this.checkPassword(this);

        });

        registerButton.setOnClickListener(v ->
        {
            this.openRegisterActivity();
        });


    }

    private void checkPassword(MainActivity activity)
    {
        String url = GlobalVariables.server + "/checkUserPassword/" + userName.getText().toString() + "/" + Utilities.MD5(password.getText().toString());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response ->
                {
                    System.out.println(response);
                    activity.onCheckPassword(response);
                },
                error -> System.out.println("That didn't work!"+error));
        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);


    }

    private void onCheckPassword(String checkPasswordResult)
    {
        if (checkPasswordResult.equals(GlobalVariablesDto.USERNAME_DOES_NOT_EXIST))
        {
            Utilities.alertDialogBuilder(this, "L'usuari no existeix", "El usuari introduit no existeix");
        }
        else if (checkPasswordResult.equals(GlobalVariablesDto.WRONG_PASSWORD))
        {
            Utilities.alertDialogBuilder(this, "Password incorrecte", "El password introduit es incorrecte");
        }
        else
        {
            UserDto userDto = gson.fromJson(checkPasswordResult, UserDto.class);

            if (userDto.getUserType() == GlobalVariables.ClientType)
            {
                this.openClientActivity(userDto);
            }
            else if(userDto.getUserType() == GlobalVariables.StoreType)
            {
                this.openStoreActivity(userDto);
            }
        }
    }


    private void openRegisterActivity()
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void openClientActivity(UserDto userDto)
    {
        Intent intent = new Intent(this, ClientActivity.class);
        intent.putExtra(GlobalVariables.USERNAME, userDto.getUserName());
        intent.putExtra(GlobalVariables.POSTAL_CODE, userDto.getPostalCode());
        intent.putExtra(GlobalVariables.CITY, userDto.getCity());
        startActivity(intent);
    }

    private void openStoreActivity(UserDto userDto)
    {
        Intent intent = new Intent(this, StoreActivity.class);
        intent.putExtra(GlobalVariables.USERNAME, userDto.getUserName());
        intent.putExtra(GlobalVariables.POSTAL_CODE, userDto.getPostalCode());
        intent.putExtra(GlobalVariables.CITY, userDto.getCity());
        startActivity(intent);
    }
}
