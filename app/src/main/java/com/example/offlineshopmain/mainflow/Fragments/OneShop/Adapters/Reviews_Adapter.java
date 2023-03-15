package com.example.offlineshopmain.mainflow.Fragments.OneShop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.BackEndServer.ShopsBackEnd.FromServerShops;
import com.example.offlineshopmain.backend.BackEndServer.ShopsBackEnd.ServerMethodsShops;
import com.example.offlineshopmain.backend.UsedClass.Review;
import com.example.offlineshopmain.backend.UsedClass.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

public class Reviews_Adapter extends FirestoreRecyclerAdapter<Review, Reviews_Adapter.ViewHolder> implements FromServerShops.LoadUserViewHolder {

    Context context;

    ServerMethodsShops fromServer = FromServerShops.fromServer;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Reviews_Adapter(@NonNull FirestoreRecyclerOptions<Review> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Review model) {
        holder.reviewbody.setText(model.getText_Review());
        holder.date.setText(model.getDate());
        holder.numrate.setText(String.valueOf(model.getLikes()));

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_review, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void setLoadedUser(User user, ViewHolder holder) {
        holder.username.setText(user.getName());
        Picasso.with(context).load(user.getImageURL())
                .fit().centerCrop().into(holder.imageView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username, reviewbody, numrate, date;
        ImageView ringheart, redheart;
        RoundedImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.SR_OwnerName);
            reviewbody = itemView.findViewById(R.id.SR_reviewBody);
            numrate = itemView.findViewById(R.id.SR_numRate);
            date = itemView.findViewById(R.id.SR_dateReview);
            ringheart = itemView.findViewById(R.id.SR_ringheart);
            redheart = itemView.findViewById(R.id.SR_redheart);
            imageView = itemView.findViewById(R.id.SR_Image);
        }
    }
}
