package org.hackovid.compradeproximitat.product;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import org.hackovid.CompraProximitatDto.dto.ProductDto;
import org.hackovid.compradeproximitat.R;

import java.util.ArrayList;
import java.util.List;


public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder>
{

    private List<ProductDto> productDtoList;
    private List<Bitmap> bitmapList;

    static StoreAdapter storeAdapter;
    static Activity activity;


    public static class StoreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public ImageView image;
        public TextView product;
        public TextView description;
        public ProductDto productDto;

        public StoreViewHolder(@NonNull View itemView)
        {
            super(itemView);
            image = itemView.findViewById(R.id.imageView1);
            product = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
        }

        @Override
        public void onClick(View v)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage("Comprar aquesta promoció:")
                    .setTitle("Comprar"+product);
        }
    }

    public static StoreAdapter getStoreAdapter()
    {
        return storeAdapter;
    }

    public static StoreAdapter singletone(List<ProductDto> productDtoList)
    {
        if (storeAdapter == null)
        {
            storeAdapter = new StoreAdapter(productDtoList);
        }

        return storeAdapter;
    }



    private StoreAdapter(List<ProductDto> productDtoList)
    {
        this.productDtoList = productDtoList;
        this.bitmapList = new ArrayList<>();

        for(ProductDto productDto : this.productDtoList)
        {
            this.bitmapList.add(BitmapFactory.decodeByteArray(productDto.getImage(), 0, productDto.getImage().length));
        }
    }

    public void addProduct(ProductDto productDto)
    {
        this.productDtoList.add(productDto);
        this.bitmapList.add(BitmapFactory.decodeByteArray(productDto.getImage(), 0, productDto.getImage().length));
        notifyDataSetChanged();
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
        //Bitmap bitmap = BitmapFactory.decodeByteArray(currentItem.getImage(), 0, currentItem.getImage().length);
        holder.image.setImageBitmap(this.bitmapList.get(position));
        holder.product.setText(currentItem.getProductName());
        holder.description.setText(currentItem.getDescription());
    }

    @Override
    public int getItemCount()
    {
        return this.productDtoList.size();
    }


}
