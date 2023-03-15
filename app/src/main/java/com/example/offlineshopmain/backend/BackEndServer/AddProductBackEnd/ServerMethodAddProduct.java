package com.example.offlineshopmain.backend.BackEndServer.AddProductBackEnd;

import com.example.offlineshopmain.backend.UsedClass.Product;

public interface  ServerMethodAddProduct {
    void getMetaProduct(String abstractClassID, FromServerAddProduct.AddProduct fram);
    void addProduct(Product product, String nameOfCategory, FromServerAddProduct.Dialog fram);
    void deleteProduct(Product product);
}
