package com.example.offlineshopmain.mainflow.Fragments.Four_Views.Search_Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.UsedClass.Product;
import com.example.offlineshopmain.mainflow.Fragments.Four_Views.Search_Fragments.Adapters.AdapterForSearch;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Search_Fragment extends Fragment {

    EditText search;
    RecyclerView recyclerView;
    Toolbar toolbar;
    AdapterForSearch adapter;
    View view;
    FrameLayout frameLayout;
    private static final String TAG = "SearchActivity";

    Menu menu;
    public Search_Fragment(Menu menu) {
    this.menu=menu;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_search, container, false);
        setin();
        menu.getItem(1).setChecked(true);
        return view;
    }

    private void setin() {
        search = view.findViewById(R.id.edittxtsearch);
        frameLayout = view.findViewById(R.id.SFframeLayout);
        recyclerView = view.findViewById(R.id.SFrecyclerView);
        Fragment_All_Categories fram = new Fragment_All_Categories();
        FragmentTransaction Transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Transaction.replace(R.id.SFframeLayout, fram);
        Transaction.commit();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!search.getText().toString().equals("")) {
                    setsearch(search.getText().toString());
                    frameLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    frameLayout.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        adapter = new AdapterForSearch(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    }

    private void setsearch(String s) {

       FirebaseFirestore.getInstance().collection("Products")
               .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                   @Override
                   public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                       ArrayList<Product>all_Product=new ArrayList<>();
                       for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                           all_Product.add(queryDocumentSnapshot.toObject(Product.class));
                       }
                       getSimilarProduct(s,all_Product);
                   }
               });
    }

    public void getSimilarProduct(String s, ArrayList<Product> allProducts) {
        ArrayList<Product> similars = new ArrayList<>();
        for (Product product : allProducts) {
            if (similar(product.getProduct_name(), s)) {
                similars.add(product);
            }
        }
        adapter.setSearchItem(similars);
    }

    private boolean similar(String productname, String s) {
        for (int i = 0; i <= productname.length(); i++) {
            for (int j = i + 1; j < productname.length(); j++) {
                String temp = productname.substring(i, j);
                if (temp.equalsIgnoreCase(s))
                    return true;
            }
        }
        if (productname.equals(s))
            return true;


        return false;
    }


}