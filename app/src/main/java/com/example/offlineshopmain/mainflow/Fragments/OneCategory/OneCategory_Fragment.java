package com.example.offlineshopmain.mainflow.Fragments.OneCategory;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.BackEndServer.OneCategoryBackEnd.FromServerOneCategory;
import com.example.offlineshopmain.backend.BackEndServer.OneCategoryBackEnd.ServerMethodOneCategory;
import com.example.offlineshopmain.backend.UsedClass.MetaProduct;
import com.example.offlineshopmain.mainflow.Fragments.Four_Views.Home_Fragments.Adapter.Adapter2Rows;

import java.util.ArrayList;

public class OneCategory_Fragment extends Fragment implements FromServerOneCategory.Category, Adapter2Rows.NearToEnd {
    View view;
    Adapter2Rows adapter;
    RecyclerView recyclerView;
    TextView textView;
    ServerMethodOneCategory FromServer = FromServerOneCategory.fromServer;
    String Category_Name ;
    public static String Categeroy_Name_KEY = "Category_Name_KEY";
    Activity activity;
    public OneCategory_Fragment(Activity activity) {
        this.activity = activity;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.onecategory_fragment, container, false);
        activity = getActivity();
        Category_Name = getArguments().getString(Categeroy_Name_KEY);
        FromServer.benull();
        setin();
        return view;
    }
    public void setin() {
        textView = view.findViewById(R.id.OCF_categoryName);
        textView.setText(Category_Name);
        recyclerView = view.findViewById(R.id.OCF_recycler);
        adapter = new Adapter2Rows(activity, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        NearToEnd();
    }
    @Override
    public void setProducts(ArrayList<MetaProduct> products) {
        for (MetaProduct product : products)
            adapter.setProducts(product);
    }
    @Override
    public void NearToEnd() {
        FromServer.getProducts(Category_Name, this);
    }
}
