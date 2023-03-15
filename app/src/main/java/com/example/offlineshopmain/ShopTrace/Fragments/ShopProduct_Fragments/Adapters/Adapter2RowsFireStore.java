package com.example.offlineshopmain.ShopTrace.Fragments.ShopProduct_Fragments.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.UsedClass.Product;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Adapter2RowsFireStore extends FirestoreRecyclerAdapter<Product, Adapter2RowsFireStore.ViewHolder> {

    private static final String TAG = "Adapter2RowsFireStore";

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Adapter2RowsFireStore(@NonNull FirestoreRecyclerOptions<Product> options) {
        super(options);
//        Log.d(TAG, "Adapter2RowsFireStore: " + options);

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Product product) {
        holder.price.setText(product.getPrice() + " L.E");
        Log.d(TAG, "onBindViewHolder: " + product);
//        Glide.with(context).asBitmap().load(product.getUrlofphoto()).into(holder.imageView);
//        holder.parent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(holder.context, OneoneproductActivity.class);
//                intent.putExtra(ONE_PRODUCT_KEY, product.getId());
//                holder.context.startActivity(intent);
//            }
//        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rows2card, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public CardView parent;
        public ImageButton send;
        public TextView price;
        public Context context;
        public int id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.R2Cimage);
            parent = itemView.findViewById(R.id.R2Ccard);
            send = itemView.findViewById(R.id.R2Csend);
            price = itemView.findViewById(R.id.R2Cprice);
            id = itemView.getId();
            context = itemView.getContext();
        }

    }

}
