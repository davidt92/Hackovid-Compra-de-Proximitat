package org.hackovid.compradeproximitat.product;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

import org.hackovid.CompraProximitatDto.dto.ProductBoughtDto;
import org.hackovid.CompraProximitatDto.dto.ProductDto;
import org.hackovid.compradeproximitat.GlobalVariables.GlobalVariables;
import org.hackovid.compradeproximitat.R;
import org.hackovid.compradeproximitat.Utilities;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;


public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.StoreViewHolder>
{
    private RequestQueue requestQueue;

    private List<ProductDto> productDtoList;
    private static String username;

    static Activity activity;

    Gson gson = new Gson();


    public class StoreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public ImageView image;
        public TextView product;
        public TextView description;

        public StoreViewHolder(@NonNull View itemView)
        {
            super(itemView);
            image = itemView.findViewById(R.id.imageView1);
            product = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setPositiveButton("Obtenir", (dialog, id) ->
            {
                ProductDto productDto = productDtoList.stream().filter(t->t.getDescription().equals(description.getText().toString())).findFirst().orElse(null);

                if (productDto != null)
                {
                    String url = GlobalVariables.server + "/buyproduct/" + username + "/" +productDto.getId() + "/" + productDto.getProductName();

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            response ->
                            {
                                ProductBoughtDto productBoughtDto = gson.fromJson(response, ProductBoughtDto.class);
                                System.out.println(response);
                                Toast.makeText(activity, "Producte adquirit correctament, \nid compra: "+productBoughtDto.getGeneratedCode(), Toast.LENGTH_SHORT).show();

                            },
                            error -> {System.out.println("That didn't work!"+error);
                                Toast.makeText(activity, "Hi ha hagut un error en comprar el producte, torna-ho a intentar mes tard", Toast.LENGTH_SHORT).show();});
                    // Add the request to the RequestQueue.

                    requestQueue.add(stringRequest);

                }

            });
            builder.setNegativeButton("Cancel·lar", (dialog, id) ->
            {
                // User cancelled the dialog
            });
            builder.setMessage("Obtenir aquests producte/servei")
                    .setTitle("Obtenir "+product.getText().toString());

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }



    public ClientAdapter(List<ProductDto> productDtoList, Activity _activity, String username)
    {
        this.productDtoList = productDtoList;
        this.username = username;
        activity = _activity;
        requestQueue = Volley.newRequestQueue(activity);
    }


    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        System.out.println("Create_");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false);
        StoreViewHolder storeViewHolder = new StoreViewHolder(v);
        return storeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position)
    {
        System.out.println("Blind_");
        ProductDto currentItem = this.productDtoList.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(currentItem.getImage(), 0, currentItem.getImage().length);
        holder.image.setImageBitmap(bitmap);
        holder.product.setText(currentItem.getProductName());
        holder.description.setText(currentItem.getDescription());
    }

    @Override
    public int getItemCount()
    {
        return this.productDtoList.size();
    }


}
