package com.example.offlineshopmain.mainflow.Fragments.Four_Views.Profile_Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.BackEndServer.LovedBackEnd.FromServerLoved;
import com.example.offlineshopmain.backend.BackEndServer.LovedBackEnd.ServerMethodLoved;
import com.example.offlineshopmain.backend.BackEndServer.ShopsBackEnd.FromServerShops;
import com.example.offlineshopmain.backend.BackEndServer.ShopsBackEnd.ServerMethodsShops;
import com.example.offlineshopmain.backend.UsedClass.Product;
import com.example.offlineshopmain.backend.UsedClass.Shop;
import com.example.offlineshopmain.backend.UsedClass.User;
import com.example.offlineshopmain.mainflow.Fragments.Four_Views.Profile_Fragments.Adapters.LoveAdapter;
import com.example.offlineshopmain.mainflow.Fragments.Four_Views.Profile_Fragments.Adapters.LovedShopAdapter;
import com.example.offlineshopmain.mainflow.Interface.toProduct;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class Profile_Fragment extends Fragment implements FromServerLoved.Profile, FromServerShops.LoadUser {

    View view;
    RecyclerView recyclerViewProducts, recyclerViewShops;
    ServerMethodLoved fromServer = FromServerLoved.fromServer;
    ServerMethodsShops fromServerShops = FromServerShops.fromServer;
    View productline, shopsline;
    TextView user_name, email, products, shops;
    LovedShopAdapter lovedShopAdapter;
    RoundedImageView user_image;
    ImageView threedots;
    Menu menu;
    private static final String TAG = "Profile_Fragment";

    public Profile_Fragment(Menu menu) {
        this.menu = menu;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.loved_fragment, container, false);
        setin();
        menu.getItem(4 + ((menu.size() == 4) ? -1 : 0)).setChecked(true);
        return view;
    }

    private void setin() {
        user_image = view.findViewById(R.id.LFimageView);
        user_name = view.findViewById(R.id.LFuser_name);
        productline = view.findViewById(R.id.LFproductsline);
        shopsline = view.findViewById(R.id.LFshops_line);
        threedots = view.findViewById(R.id.LFthreedots);
        email = view.findViewById(R.id.LFuser_email);
        products = view.findViewById(R.id.LFproducts);
        shops = view.findViewById(R.id.LFshops);
        recyclerViewProducts = view.findViewById(R.id.LFrecyclerViewProducts);
        recyclerViewShops = view.findViewById(R.id.LFrecyclerViewShops);
        lovedShopAdapter = new LovedShopAdapter(getActivity());
        recyclerViewShops.setAdapter(lovedShopAdapter);
        recyclerViewShops.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        fromServer.getLovedProducts(this);
        fromServer.getLovedShops(this);
        fromServerShops.loadUser(this);
        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productline.setVisibility(View.VISIBLE);
                recyclerViewProducts.setVisibility(View.VISIBLE);
                shopsline.setVisibility(View.GONE);
                recyclerViewShops.setVisibility(View.GONE);
            }
        });

        shops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopsline.setVisibility(View.VISIBLE);
                recyclerViewShops.setVisibility(View.VISIBLE);
                productline.setVisibility(View.GONE);
                recyclerViewProducts.setVisibility(View.GONE);
            }
        });


    }

    public void LoadedFavList(ArrayList<Product> products) {
        LoveAdapter adapter = new LoveAdapter((toProduct) getActivity());
        recyclerViewProducts.setAdapter(adapter);
        recyclerViewProducts.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter.setProducts(products);
    }

    @Override
    public void LoadedFavListShops(Shop shop) {
        lovedShopAdapter.add(shop);
    }


    @Override
    public void setLoadedUser(User user) {
        Log.d(TAG, "setLoadedUser: ");
        Glide.with(getActivity()).load(user.getImageURL()).centerInside().into(user_image);
        user_name.setText(user.getName());
        email.setText(user.getEmail());
    }
}
