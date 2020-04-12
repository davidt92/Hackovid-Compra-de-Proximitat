package org.hackovid.compradeproximitat.product;

import android.app.Activity;
import android.content.Intent;
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
import com.google.gson.Gson;

import org.hackovid.CompraProximitatDto.dto.ProductBoughtDto;
import org.hackovid.CompraProximitatDto.dto.ProductDto;
import org.hackovid.compradeproximitat.GlobalVariables.GlobalVariables;
import org.hackovid.compradeproximitat.R;
import org.hackovid.compradeproximitat.client.QRCodeActivity;

import java.util.List;

public class ClientBoughtAdapter extends RecyclerView.Adapter<ClientBoughtAdapter.StoreViewHolder>
{

    private RequestQueue requestQueue;
    private List<ProductBoughtDto> productBoughtDtoList;
    private static String username;

    static Activity activity;


    public class StoreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView product;
        public TextView generatedCode;

        public StoreViewHolder(@NonNull View itemView)
        {
            super(itemView);
            product = itemView.findViewById(R.id.title);
            generatedCode = itemView.findViewById(R.id.generatedcode);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            Intent qrCodeIntent = new Intent(activity, QRCodeActivity.class);
            qrCodeIntent.putExtra(GlobalVariables.QR_CODE, generatedCode.getText().toString());
            activity.startActivity(qrCodeIntent);
        }
    }


    public ClientBoughtAdapter(List<ProductBoughtDto> productBoughtDtos, Activity _activity, String username)
    {
        System.out.println("Generate");
        this.productBoughtDtoList = productBoughtDtos;
        this.username = username;
        activity = _activity;
        requestQueue = Volley.newRequestQueue(activity);
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        System.out.println("Create");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_no_image, parent, false);
        ClientBoughtAdapter.StoreViewHolder storeViewHolder = new ClientBoughtAdapter.StoreViewHolder(v);
        return storeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position)
    {
        System.out.println("Blind");
        ProductBoughtDto currentItem = this.productBoughtDtoList.get(position);
        holder.product.setText(currentItem.getProductName());
        holder.generatedCode.setText(currentItem.getGeneratedCode());
    }

    @Override
    public int getItemCount()
    {
        return this.productBoughtDtoList.size();
    }

}