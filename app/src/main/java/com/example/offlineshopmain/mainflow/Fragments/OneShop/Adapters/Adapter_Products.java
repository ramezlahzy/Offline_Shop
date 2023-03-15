package com.example.offlineshopmain.mainflow.Fragments.OneShop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.mainflow.Interface.toProduct;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_Products extends RecyclerView.Adapter<Adapter_Products.ViewHolder> {


    Context context;
    ArrayList<String>arrayList=new ArrayList<>();
    FirebaseFirestore fs=FirebaseFirestore.getInstance();
    CardView cardView;
    toProduct activity;
    public Adapter_Products(Context context, toProduct toProduct) {
        this.context = context;
        activity=toProduct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_product_shop,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     holder.bar.setVisibility(View.VISIBLE);
     String id=arrayList.get(position);
     holder.cardView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             activity.toProduct(id);
         }
     });
        fs.collection("Products").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String imageUrl= (String) documentSnapshot.get("imageURL");
                String price=(documentSnapshot.get("price").toString())+" L.E";
                holder.bar.setVisibility(View.GONE);
                holder.textView.setText(price);
                Picasso.with(context).load(imageUrl)
                        .fit()
                        .centerCrop()
                        .into(holder.imageView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        ProgressBar bar;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.CPS_image);
            textView=itemView.findViewById(R.id.CPS_price);
            bar=itemView.findViewById(R.id.CPS_progressBar);
            cardView=itemView.findViewById(R.id.CPS_card);
        }
    }
}
