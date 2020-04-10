package org.hackovid.compradeproximitat.product;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.hackovid.compradeproximitat.R;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder>
{

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

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return 0;
    }


}
