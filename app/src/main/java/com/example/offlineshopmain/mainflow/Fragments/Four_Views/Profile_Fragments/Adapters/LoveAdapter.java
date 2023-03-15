package com.example.offlineshopmain.mainflow.Fragments.Four_Views.Profile_Fragments.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.BackEndServer.LovedBackEnd.FromServerLoved;
import com.example.offlineshopmain.backend.BackEndServer.LovedBackEnd.ServerMethodLoved;
import com.example.offlineshopmain.backend.UsedClass.Product;
import com.example.offlineshopmain.mainflow.Interface.toProduct;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class LoveAdapter extends RecyclerView.Adapter<LoveAdapter.ViewHolder> {

    ArrayList<Product> products = new ArrayList<>();
    toProduct context;
    ServerMethodLoved fromServer = FromServerLoved.fromServer;

    public LoveAdapter(toProduct context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((Context) context).inflate(R.layout.card_love, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int i = position;
        Product product = products.get(i);
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.toProduct(products.get(i).getId());
            }
        });
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromServer.removeLovedProduct(products.get(i).getId());
                products.remove(i);
                notifyDataSetChanged();
            }
        });
        holder.likes.setText(String.valueOf(product.getLikedusersids().size()));

        Glide.with((Context) context).load(product.getImageURL())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView close;
        ConstraintLayout parent;
        ImageView imageView;
        TextView likes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            close = itemView.findViewById(R.id.CLclosebutton);
            imageView = itemView.findViewById(R.id.R2Cimage);
            likes=itemView.findViewById(R.id.R2Clikes);
        }
    }
}
