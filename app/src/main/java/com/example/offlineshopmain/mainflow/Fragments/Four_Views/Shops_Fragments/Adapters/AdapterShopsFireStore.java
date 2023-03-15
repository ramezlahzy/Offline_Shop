package com.example.offlineshopmain.mainflow.Fragments.Four_Views.Shops_Fragments.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.UsedClass.Shop;
import com.example.offlineshopmain.mainflow.Interface.toProduct;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AdapterShopsFireStore extends FirestoreRecyclerAdapter<Shop, AdapterShopsFireStore.ViewHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private static final String TAG = "AdapterShopsFireStore";
    toProduct activity;

    public AdapterShopsFireStore(@NonNull FirestoreRecyclerOptions<Shop> options, toProduct context) {
        super(options);
        this.activity = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Shop model) {
        Glide.with((Activity) activity).load(model.getImageURL()).centerCrop().into(holder.image);
        holder.shopname.setText(model.getName());
        holder.address.setText(model.getAddress());
        holder.rate.setText("" + model.getRate());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.toShop(model.getId());
            }
        });


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_card, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout parent;
        TextView shopname, address, rate;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopname = itemView.findViewById(R.id.SC_shopName);
            parent = itemView.findViewById(R.id.Shop_cardparent);
            address = itemView.findViewById(R.id.SC_address);
            rate = itemView.findViewById(R.id.SC_rate);
            image = itemView.findViewById(R.id.SC_ShopImage);
        }
    }
}
