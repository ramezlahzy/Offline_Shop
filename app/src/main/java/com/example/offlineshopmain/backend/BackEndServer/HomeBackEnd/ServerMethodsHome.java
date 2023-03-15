package com.example.offlineshopmain.backend.BackEndServer.HomeBackEnd;

import android.content.Context;

import com.example.offlineshopmain.backend.UsedClass.Product;

public interface ServerMethodsHome {
    void loadwatchedProducts(FromServerHome.Home fram, Context context);
    void loadnotWatchedProducts(FromServerHome.Home fram,Context context);
    void addToWatched(String productId,Context context);
    void deleteProduct(Product product,Context context);
}
