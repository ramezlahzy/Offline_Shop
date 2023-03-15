package com.example.offlineshopmain.mainflow.Fragments.OneShop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.UsedClass.Category_For_User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Abstract_categories extends FirestoreRecyclerAdapter<Category_For_User, Abstract_categories.ViewHolder> {


    Navigate_Category navigate;

    public interface Navigate_Category {
        public void navegateto(String id_Category, String id_Shop);
    }

    Context context;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Abstract_categories(@NonNull FirestoreRecyclerOptions<Category_For_User> options, Navigate_Category navigate) {
        super(options);
        this.navigate = navigate;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Category_For_User model) {
        Glide.with(context).load(model.getImageURL()).centerCrop().into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate.navegateto(model.getId(), model.getId_Owner_Shop());
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.abstract_categories_onshop, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ACO_imageView);
            cardView = itemView.findViewById(R.id.ACO_card);
        }
    }
}
