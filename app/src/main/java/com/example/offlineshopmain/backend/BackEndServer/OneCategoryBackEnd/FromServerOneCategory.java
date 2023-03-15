package com.example.offlineshopmain.backend.BackEndServer.OneCategoryBackEnd;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.offlineshopmain.backend.UsedClass.Abstract_Category;
import com.example.offlineshopmain.backend.UsedClass.MetaProduct;
import com.example.offlineshopmain.backend.UsedClass.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FromServerOneCategory implements ServerMethodOneCategory {
    static FirebaseFirestore fs = FirebaseFirestore.getInstance();
    static FirebaseAuth auth = FirebaseAuth.getInstance();
    public static ServerMethodOneCategory fromServer = new FromServerOneCategory();
    Category fram;
    private static final String TAG = "FromServerOneCategory";
    public interface Category {
        void setProducts(ArrayList<MetaProduct> products);
    }

    ArrayList<String> projectsID;
    int i = 0;

    @Override
    public void getProducts(String category_Name, Category fram) {
        this.fram = fram;
        if (projectsID == null) {
            i = 0;
            fs.collection("Abstract_Categories").document(category_Name).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        Abstract_Category abstract_category = documentSnapshot.toObject(Abstract_Category.class);
                        projectsID = abstract_category.getAll_Products_Id();
                        setProducts();
                    }
                }
            });
        } else
            setProducts();
    }

    @Override
    public void benull() {
        projectsID=null;
    }

    int count,cou;

    public void setProducts() {
        int limit = Math.min(i + 10, projectsID.size() - 1);
        count = limit - i + 1;
        cou=0;
        returnproduct = new ArrayList<>();
        for (; i <= limit; i++) {
            String productId = projectsID.get(i);
            fs.collection("Products").document(productId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Product product = documentSnapshot.toObject(Product.class);
                    AsynTask asynTask = new AsynTask(product);
                    asynTask.execute(product.getImageURL());
                }
            });
        }
    }

    ArrayList<MetaProduct> returnproduct;
    ;
    public void addProduct(MetaProduct product) {
        returnproduct.add(product);
        cou++;
        Log.d(TAG, "addProduct: "+cou+"    "+count);
        if (cou == count){
            fram.setProducts(returnproduct);
        }
    }


    class AsynTask extends AsyncTask<String, Bitmap, Bitmap> {
        Product product;

        public AsynTask(Product product) {
            this.product = product;
        }

        @SuppressLint("WrongThread")
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap myBitmap = null;

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
            }

            return myBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            MetaProduct metaProduct = new MetaProduct(bitmap, product);
            addProduct(metaProduct);
        }
    }
}
