package com.example.offlineshopmain.backend.BackEndServer.ShopsBackEnd;

import androidx.fragment.app.Fragment;

import com.example.offlineshopmain.backend.UsedClass.Review;
import com.example.offlineshopmain.mainflow.Fragments.OneShop.Adapters.Reviews_Adapter;
import com.example.offlineshopmain.mainflow.Interface.toProduct;

public interface ServerMethodsShops {
    void loadShops(FromServerShops.Shops shops, Fragment fragment);

    void loadShopInfo(String id, FromServerShops.LoadShop shopInfo);

    void addLove(String id_Product);

    void removeLove(String id_Product);

    void addDislike(String id_Product);

    void removeDislike(String id_Product);

    void loadShopCategory(String id_Shop, String id_Category, toProduct toProduct, FromServerShops.OneShop oneShop);

    String getUserId();

    void loadUser(FromServerShops.LoadUserViewHolder loadUserViewHolder, Reviews_Adapter.ViewHolder position);

    void getUserImage(FromServerShops.OneShop oneShop);

    void addReview(Review review, String shop_Id, FromServerShops.OneShop oneShop);

    void loadUser(FromServerShops.LoadUser loadUser);

    void doFollowing(String shop_ID);

    void removeFollowing(String shop_ID);
}
