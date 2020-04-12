package org.hackovid.compradeproximitat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.apache.logging.log4j.LogManager;
import org.hackovid.CompraProximitatDto.dto.UserDto;
import org.hackovid.compradeproximitat.GlobalVariables.GlobalVariables;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.logging.log4j.Logger;


public class RegisterActivity extends AppCompatActivity
{
    private static Logger LOGGER = LogManager.getLogger(RegisterActivity.class);

    private RadioGroup radioGroup;
    private RadioButton radioClient;
    private RadioButton radioStore;
    private Button registerButton;
    private RadioGroup userType;
    private EditText userName;
    private EditText password;
    private EditText passwordConfirmation;
    private EditText email;
    private EditText direction;
    private EditText name;
    private EditText surname;
    private EditText postalCode;
    private EditText city;


    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar); // setting toolbar is important before calling getSupportActionBar()
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.register_key));
        actionBar.setDisplayHomeAsUpEnabled(true);

        requestQueue = Volley.newRequestQueue(this);

        registerButton = findViewById(R.id.register_button);
        userType = findViewById(R.id.radioGroup);
        userName = findViewById(R.id.user_name_text);
        password = findViewById(R.id.password_text);
        passwordConfirmation = findViewById(R.id.password_1_text);
        email = findViewById(R.id.email_text);
        direction = findViewById(R.id.direction_text);
        name = findViewById(R.id.first_name);
        surname = findViewById(R.id.last_name_text);
        postalCode = findViewById(R.id.postal_code_text);
        city = findViewById(R.id.city_text);
        radioGroup = findViewById(R.id.radioGroup);
        radioClient = findViewById(R.id.radioButtonClient);
        radioStore = findViewById(R.id.radioButtonStore);

        registerButton.setOnClickListener(v->
        {
            System.out.println("Send Message");

            if (password.getText().toString().equals(passwordConfirmation.getText().toString()))
            {
                int userType;

                if (radioGroup.getCheckedRadioButtonId() == radioClient.getId())
                {
                    userType = GlobalVariables.ClientType;
                }
                else
                {
                    userType = GlobalVariables.StoreType;
                }

                UserDto userDto = new UserDto(userType,
                        userName.getText().toString(),
                        name.getText().toString(),
                        surname.getText().toString(),
                        direction.getText().toString(),
                        city.getText().toString(),
                        postalCode.getText().toString(),
                        Utilities.MD5(password.getText().toString()),
                        email.getText().toString());
                this.register(userDto);
            }
            else
            {
                Toast.makeText(this, "El usuari i la contrasenya no coincideixen", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        finish();
        return true;
    }

    private void register(UserDto userDto)
    {
        System.out.println("Send Message");
        Gson gson = new Gson();
        String url = GlobalVariables.server + "/adduser";

        try
        {
            JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url,new JSONObject(gson.toJson(userDto)),
                    response ->
                    {
                        Toast.makeText(this, "Usuari registrat correctament", Toast.LENGTH_SHORT).show();
                        System.out.println(response);
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
