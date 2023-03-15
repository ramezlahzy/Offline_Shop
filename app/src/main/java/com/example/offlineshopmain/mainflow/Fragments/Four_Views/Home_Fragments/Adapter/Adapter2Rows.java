package com.example.offlineshopmain.mainflow.Fragments.Four_Views.Home_Fragments.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
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
import com.example.offlineshopmain.backend.BackEndServer.HomeBackEnd.FromServerHome;
import com.example.offlineshopmain.backend.BackEndServer.HomeBackEnd.ServerMethodsHome;
import com.example.offlineshopmain.backend.UsedClass.MetaProduct;
import com.example.offlineshopmain.mainflow.Interface.toProduct;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Adapter2Rows extends RecyclerView.Adapter<Adapter2Rows.ViewHolder> {

    public interface NearToEnd {
        void NearToEnd();
    }

    private static final String TAG = "Adapter2Rows";

    Context activity;
    ArrayList<MetaProduct> products = new ArrayList<>();
    ServerMethodsHome fromServer = new FromServerHome();
    NearToEnd nearToEnd;

    public Adapter2Rows(Activity activity, NearToEnd end) {
        this.activity = activity;
        nearToEnd = end;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rows2card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position > getItemCount() - 10) {
            nearToEnd.NearToEnd();
        }
        MetaProduct product = products.get(position);
//        holder.likes.setText(String.valueOf(product.getProduct().getLikedusersids().size()));
        Glide.with(activity).asBitmap().fitCenter().load(product.getBitmap()).into(holder.imageView);
//        holder.imageView.setImageBitmap(product.getImage());
        holder.id_For_Product = product.getProduct().getId();
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((toProduct) activity).toProduct(product.getProduct().getId());
            }
        });

    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        fromServer.addToWatched(holder.id_For_Product,activity);
    }

    public void setProducts(MetaProduct metaProduct) {
        File ff = new File(activity.getExternalCacheDir().getAbsolutePath() + "/" + metaProduct.getProduct().getId());
        try {
            if (!ff.exists()) {
                FileOutputStream fileOutputStream = new FileOutputStream(ff);
                metaProduct.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.products.add(metaProduct);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void deleteItem(int position) {
        fromServer.deleteProduct(products.get(position).getProduct(),activity);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public CardView parent;
        public ImageButton send;
        public TextView likes;
        public Context context;
        public int id;
        public String id_For_Product;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.R2Cimage);
            parent = itemView.findViewById(R.id.R2Ccard);
            send = itemView.findViewById(R.id.R2Csend);
            likes = itemView.findViewById(R.id.R2Clikes);
            id = itemView.getId();
            context = itemView.getContext();
        }

    }


}
