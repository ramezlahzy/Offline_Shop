package com.example.offlineshopmain.mainflow.Fragments.OneShop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.BackEndServer.ShopsBackEnd.FromServerShops;
import com.example.offlineshopmain.backend.BackEndServer.ShopsBackEnd.ServerMethodsShops;
import com.example.offlineshopmain.backend.UsedClass.Shop;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Info_BottomDialog extends BottomSheetDialog implements FromServerShops.LoadShop {


    View view;
    String shopId;
    private static final String TAG = "Info_Fragment";
    public static final String KEY_SHOP_INFO = "KEY_SHOP_INFO";
    ServerMethodsShops fromServer = FromServerShops.fromServer;

    public Info_BottomDialog(@NonNull Context context, int Them, String shopId) {
        super(context, Them);
        Log.d(TAG, "Info_Fragment: " + shopId);
        this.shopId = shopId;
    }

    @Override
    public void setContentView(View view) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.info_fragment, findViewById(R.id.infoDrawer));
        Log.d(TAG, "setContentView: " + shopId);
        fromServer.loadShopInfo(shopId, this);
        super.setContentView(view);

    }

    @Override
    public void setLoadedShopInfo(Shop shop) {
        //TODO 10/6/22
    }
}
