package org.hackovid.compradeproximitat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.hackovid.CompraProximitatDto.dto.UserDto;
import org.hackovid.compradeproximitat.GlobalVariables.GlobalVariables;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity
{
    // Global variables
    Button loginButton;
    Button registerButton;
    EditText userName;
    EditText password;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);

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
                    activity.onCheckPassword(Boolean.parseBoolean(response));
                },
                error -> System.out.println("That didn't work!"+error));
        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);


    }

    private void onCheckPassword(boolean checkPasswordResult)
    {
        System.out.println("Check Password result "+ checkPasswordResult);
    }


    private void openRegisterActivity()
    {

    }
}
