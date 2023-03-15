package com.example.offlineshopmain.ShopTrace.Dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.ShopTrace.Fragments.ShopProduct_Fragments.Adapters.Adapter_Abstract_Categories_FireStore;
import com.example.offlineshopmain.backend.UsedClass.Abstract_Category;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Bottom_Categories_Dialog extends BottomSheetDialog {

    private FirebaseFirestore fs = FirebaseFirestore.getInstance();
    private CollectionReference productsreference = fs.collection("Abstract_Categories");
    private RecyclerView recyclerView;
    public Bottom_Categories_Dialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void setContentView(View view) {
        View bottomSheetView = LayoutInflater.from(getContext()).inflate(R.layout.all_categories_drawer, findViewById(R.id.all_categories_drawer));
        setin(bottomSheetView);
        super.setContentView(bottomSheetView);
    }
    private void setin(View bottomSheetView) {
        Query query = productsreference.orderBy("id", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Abstract_Category> options = new FirestoreRecyclerOptions.Builder<Abstract_Category>()
                .setQuery(query, Abstract_Category.class)
                .setLifecycleOwner(this)
                .build();
        recyclerView=bottomSheetView.findViewById(R.id.ACDrecyclerview);
        Adapter_Abstract_Categories_FireStore adapter=new Adapter_Abstract_Categories_FireStore(options);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

    }

}
