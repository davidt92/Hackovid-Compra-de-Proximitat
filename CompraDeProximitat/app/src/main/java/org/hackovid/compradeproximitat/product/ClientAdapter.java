package org.hackovid.compradeproximitat.product;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.hackovid.CompraProximitatDto.dto.ProductDto;
import org.hackovid.compradeproximitat.R;

import java.util.List;


public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.StoreViewHolder>
{

    private List<ProductDto> productDtoList;


    public static class StoreViewHolder extends RecyclerView.ViewHolder
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
        }
    }



    public ClientAdapter(List<ProductDto> productDtoList)
    {
        this.productDtoList = productDtoList;
    }


    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false);
        StoreViewHolder storeViewHolder = new StoreViewHolder(v);
        return storeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position)
    {
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
