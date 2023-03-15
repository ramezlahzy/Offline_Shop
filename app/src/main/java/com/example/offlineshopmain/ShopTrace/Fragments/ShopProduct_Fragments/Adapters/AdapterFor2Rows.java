package com.example.offlineshopmain.ShopTrace.Fragments.ShopProduct_Fragments.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.BackEndServer.AddProductBackEnd.FromServerAddProduct;
import com.example.offlineshopmain.backend.BackEndServer.AddProductBackEnd.ServerMethodAddProduct;
import com.example.offlineshopmain.backend.UsedClass.MetaProduct;
import com.example.offlineshopmain.mainflow.Interface.toProduct;

import java.util.ArrayList;

public class AdapterFor2Rows extends RecyclerView.Adapter<AdapterFor2Rows.ViewHolder> {

    ArrayList<MetaProduct> items = new ArrayList<>();
    Context context;
    toProduct activity;
    ServerMethodAddProduct fromServer = FromServerAddProduct.fromServer;
    private static final String TAG = "AdapterFor2Rows";

    public AdapterFor2Rows(Context context, toProduct a) {
        this.context = context;
        activity = a;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rows2card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MetaProduct product = items.get(position);
        holder.likes.setText(product.getProduct().getLikedusersids().size()+"");
        Glide.with(context).asBitmap().load(product.getBitmap()).into(holder.imageView);
        holder.id_Product=product.getProduct().getId();

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.toProduct(product.getProduct().getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<MetaProduct> arr) {
        items = arr;
        notifyDataSetChanged();
    }
    public void addItems(MetaProduct product) {
        items.add(product);
        notifyDataSetChanged();
    }
    public void deleteItem(int position) {
        fromServer.deleteProduct(items.get(position).getProduct());
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        CardView parent;
        ImageButton send;
        TextView likes;

        int id;
        public String id_Product;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.R2Cimage);
            parent = itemView.findViewById(R.id.R2Ccard);
            send = itemView.findViewById(R.id.R2Csend);
            likes = itemView.findViewById(R.id.R2Clikes);
            id = itemView.getId();
        }
    }
}
