package com.example.offlineshopmain.mainflow.Fragments.OneShop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.BackEndServer.ShopsBackEnd.FromServerShops;
import com.example.offlineshopmain.backend.BackEndServer.ShopsBackEnd.ServerMethodsShops;
import com.example.offlineshopmain.backend.UsedClass.Review;
import com.example.offlineshopmain.mainflow.Fragments.OneShop.Adapters.Reviews_Adapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Reviews_BottomDialog extends BottomSheetDialog  {


    View view;
    String shopId;
    private static final String TAG = "Info_Fragment";
    public static final String KEY_SHOP_INFO = "KEY_SHOP_INFO";
    ServerMethodsShops fromServer = FromServerShops.fromServer;
    RecyclerView recyclerView;
    Context context;
    public Reviews_BottomDialog(@NonNull Context context, int Them, String shopId) {
        super(context, Them);
        this.shopId = shopId;
        this.context=context;
    }

    @Override
    public void setContentView(View view) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.reviews_shop_fragment, findViewById(R.id.reviews_Drawer));
        recyclerView=view.findViewById(R.id.RSF_reviews);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        Query query = FirebaseFirestore.getInstance().collection("Shops")
                .document(shopId)
                .collection("Shop_Reviews");
        FirestoreRecyclerOptions<Review> options = new FirestoreRecyclerOptions.Builder<Review>()
                .setQuery(query, Review.class)
                .setLifecycleOwner(this)
                .build();
        Reviews_Adapter adapter=new Reviews_Adapter(options,context);
        recyclerView.setAdapter(adapter);
        super.setContentView(view);

    }


}
