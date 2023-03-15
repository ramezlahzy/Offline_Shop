package com.example.offlineshopmain.backend.BackEndServer.OneProductBackEnd;

import com.example.offlineshopmain.backend.UsedClass.Product;
import com.example.offlineshopmain.backend.UsedClass.Review;

public interface ServerMethodsOneProduct {
     void getproduct(FromServerOneProductOneProduct.OneProductInterFace fram, String string);
     void setUserImageOneProduct(Product product, FromServerOneProductOneProduct.OneProductInterFace oneproduct);
     void setReview(String id_Product, String reviewId, Review review);
     String getUserId();
     void setLikesOneProduct(FromServerOneProductOneProduct.OneProductInterFace fram, String Product_id);
     void isShop_addFav(FromServerOneProductOneProduct.OneProductInterFace fram, String id_Product);
     void addLove(FromServerOneProductOneProduct.OneProductInterFace fram, String id_Product);
     void removeLove(FromServerOneProductOneProduct.OneProductInterFace fram, String id_Product);
     void addDislike(FromServerOneProductOneProduct.OneProductInterFace fram, String id_Product);
     void removeDislike(FromServerOneProductOneProduct.OneProductInterFace fram, String id_Product);
     void setShop(FromServerOneProductOneProduct.OneProductInterFace fram, String id_OwnerShop);
     void addToFav(String shop_ID);
     void RemoveFromFav(String product_ID);
}
