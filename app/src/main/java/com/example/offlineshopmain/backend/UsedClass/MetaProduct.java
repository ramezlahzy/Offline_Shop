package com.example.offlineshopmain.backend.UsedClass;

import android.graphics.Bitmap;

public class MetaProduct {
    Product product;
    Bitmap bitmap;

    public MetaProduct( Bitmap bitmap,Product product) {
        this.product = product;
        this.bitmap = bitmap;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
