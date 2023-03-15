package com.example.offlineshopmain.mainflow.Fragments.Four_Views.Search_Fragments.Adapters;

import android.app.Activity;
import android.content.Context;
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
import com.example.offlineshopmain.backend.UsedClass.Abstract_Category;
import com.example.offlineshopmain.mainflow.Interface.toProduct;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Adapter_Search_Category extends FirestoreRecyclerAdapter<Abstract_Category, Adapter_Search_Category.ViewHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Activity activity;

    public Adapter_Search_Category(@NonNull FirestoreRecyclerOptions<Abstract_Category> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull Adapter_Search_Category.ViewHolder holder, int position, @NonNull Abstract_Category model) {
        Glide.with(holder.context).load(model.getPhotoURL())
                .centerInside().into(holder.imageView);
        holder.name.setText(model.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((toProduct)activity).toCategory(model.getName());
            }
        });

    }

    @NonNull
    @Override
    public Adapter_Search_Category.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_abstract_categories_search, parent, false);
        return new Adapter_Search_Category.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Context context;
        TextView name;
        ImageView imageView;
        RelativeLayout cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            name = itemView.findViewById(R.id.CACprice);
            imageView = itemView.findViewById(R.id.CACimage);
            cardView = itemView.findViewById(R.id.onecategcard);

        }
    }
}
