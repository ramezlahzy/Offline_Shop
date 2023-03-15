package com.example.offlineshopmain.backend.BackEndServer.LovedBackEnd;

public interface ServerMethodLoved {
    void getLovedProducts(FromServerLoved.Profile fram);
    void getLovedShops(FromServerLoved.Profile fram);
    void removeLovedProduct(String productId);
}
