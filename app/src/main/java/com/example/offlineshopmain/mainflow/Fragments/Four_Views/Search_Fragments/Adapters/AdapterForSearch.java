package com.example.offlineshopmain.mainflow.Fragments.Four_Views.Search_Fragments.Adapters;

import android.content.Context;
import android.util.Log;
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
import com.example.offlineshopmain.backend.UsedClass.Product;

import java.util.ArrayList;

public class AdapterForSearch extends RecyclerView.Adapter<AdapterForSearch.ViewHolder> {


    Context context;
    private static final String TAG = "AdapterForSearch";
    ArrayList<Product> searchItem=new ArrayList<>();
    public AdapterForSearch(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.new_card_search,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product=searchItem.get(position);
        holder.name.setText(product.getProduct_name());
        holder.price.setText("Price: "+product.getPrice()+" L.E");
        holder.describtion.setText(product.getDescribtion());
        Glide.with(context).asBitmap().load(product.getImageURL())
//                .fitCenter().
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(holder.imageView);
        holder.parentcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO add product navigation
            }
        });
//        Picasso.with(context).load(product.getImageURL())
//                .fit().centerInside().into(holder.imageView);
        Log.d(TAG, "onBindViewHolder: ");
    }

    @Override
    public int getItemCount() {
        return searchItem.size();
    }

    public ArrayList<Product> getSearchItem() {
        return searchItem;
    }

    public void setSearchItem(ArrayList<Product> searchItem) {
        this.searchItem = searchItem;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name,price,describtion;
        CardView parentcard;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.NCSRoundImage);
            parentcard=itemView.findViewById(R.id.NCScard);
            name=itemView.findViewById(R.id.NCSname);
            price=itemView.findViewById(R.id.NCSprice);
            describtion=itemView.findViewById(R.id.NCSdescribtion);
        }
    }
}
