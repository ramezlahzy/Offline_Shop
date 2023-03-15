package com.example.offlineshopmain.mainflow.Fragments.Four_Views.Shops_Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.BackEndServer.ShopsBackEnd.FromServerShops;
import com.example.offlineshopmain.backend.BackEndServer.ShopsBackEnd.ServerMethodsShops;
import com.example.offlineshopmain.mainflow.Fragments.Four_Views.Shops_Fragments.Adapters.AdapterShopsFireStore;

public class Shops_Fragment extends Fragment implements FromServerShops.Shops {

    View view;
    RecyclerView recyclerView;
    ServerMethodsShops fromServer=FromServerShops.fromServer;
    private static final String TAG = "Shops_Fragment";
    Menu menu;
    public Shops_Fragment(Menu menu) {
        this.menu=menu;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shop_fragment, container, false);
        recyclerView=view.findViewById(R.id.ShopFRecycerView);
        fromServer.loadShops(this,this);
        menu.getItem(3+(menu.size()==4?-1:0)).setChecked(true);
        return view;
    }

    public void setRecyclerView(AdapterShopsFireStore shops){
        recyclerView.setAdapter(shops);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
    }


}
