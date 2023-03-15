package com.example.offlineshopmain.ShopTrace.Fragments.ShopProduct_Fragments.Adapters;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.UsedClass.Category_For_User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class Adapter_Shop_Categories extends FirestoreRecyclerAdapter<Category_For_User,Adapter_Shop_Categories.ViewHolder> {


    private static final String TAG = "Adapter_Abstract_Catego";
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ShowBottom showBottom;

    public interface ShowBottom {
         void showbottom();
         void gotosecond(String name_Of_Category);
    }
    public Adapter_Shop_Categories(@NonNull FirestoreRecyclerOptions<Category_For_User> options, ShowBottom showbottom) {
        super(options);
        this.showBottom = showbottom;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Category_For_User model) {
        Picasso.with(holder.context).load(model.getImageURL())
                .fit()
                .centerInside()
//                .centerCrop()
                .into(holder.imageView);
        holder.name.setText(model.getName_Of_Category());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getName_Of_Category().equals("add")){
                    showBottom.showbottom();
                }
                else{
                    showBottom.gotosecond(model.getName_Of_Category());
                }
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_abstract_cat_add, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Context context;
        TextView name;
        ImageView imageView;
        RelativeLayout cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            name=itemView.findViewById(R.id.CACprice);
            imageView=itemView.findViewById(R.id.CACimage);
            cardView=itemView.findViewById(R.id.onecategcard);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            Log.d(TAG, "ViewHolder: "+context);
//            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//            int height = displayMetrics.heightPixels;
//            int width = displayMetrics.widthPixels;
//            cardView.getLayoutParams().width= width;
        }
    }
}
