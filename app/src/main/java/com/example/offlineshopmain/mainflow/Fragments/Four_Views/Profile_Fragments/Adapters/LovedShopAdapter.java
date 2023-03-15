package com.example.offlineshopmain.mainflow.Fragments.Four_Views.Profile_Fragments.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.UsedClass.Shop;
import com.example.offlineshopmain.mainflow.Interface.toProduct;

import java.util.ArrayList;

public class LovedShopAdapter extends RecyclerView.Adapter<LovedShopAdapter.ViewHolder> {
    ArrayList<Shop> list = new ArrayList<>();
    Activity activity;

    public LovedShopAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_shop_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Shop shop = list.get(position);
        holder.textView.setText(shop.getName());
        Glide.with((Activity) activity).load(shop.getImageURL()).fitCenter().into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((toProduct)activity).toShop(shop.getId());
            }
        });
    }
    public void add(Shop shop){
        list.add(shop);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.FSC_shopCard);
            imageView = itemView.findViewById(R.id.FSC_shopimage);
            textView = itemView.findViewById(R.id.FSC_shopname);
        }
    }
}
