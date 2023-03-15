package com.example.offlineshopmain.backend.BackEndServer.AddProductBackEnd;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.offlineshopmain.backend.UsedClass.Category_For_User;
import com.example.offlineshopmain.backend.UsedClass.MetaProduct;
import com.example.offlineshopmain.backend.UsedClass.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FromServerAddProduct implements ServerMethodAddProduct {
    static FirebaseFirestore fs = FirebaseFirestore.getInstance();
    static FirebaseAuth auth = FirebaseAuth.getInstance();
    public static ServerMethodAddProduct fromServer = new FromServerAddProduct();
    public static String id_User = auth.getUid();

    public interface AddProduct {
        void SetMetaProduct(MetaProduct metaProduct);
    }

    public interface Dialog {
        void SetAdd(boolean b);
    }

    public void getMetaProduct(String abstractClassID, AddProduct fram) {
        fs.collection("Shops").document(id_User).collection("Category_For_Users")
                .document(abstractClassID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Category_For_User category = documentSnapshot.toObject(Category_For_User.class);
                            ArrayList<String> products = (ArrayList<String>) category.getProductsId();
                            for (String product : products) {
                                fs.collection("Products").document(product).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if (documentSnapshot.exists()) {
                                            Product product1 = documentSnapshot.toObject(Product.class);
                                            Asynproduct asyn = new Asynproduct(product1, fram);
                                            asyn.execute(product1.getImageURL());
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
    }

    public void addProduct(Product product, String nameOfCategory, Dialog fram) {
        fs.collection("Products").add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String id = documentReference.getId();
                        fs.collection("Products").document(id)
                                .update("id", id).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        fs.collection("Shops").document(id_User).collection("Category_For_Users")
                                                .document(nameOfCategory).update("productsId", FieldValue.arrayUnion(product.getId()))
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            fram.SetAdd(true);
                                                        } else {
                                                            fram.SetAdd(false);
                                                        }
                                                    }
                                                });
                                        fs.collection("Abstract_Categories").document(nameOfCategory)
                                                .update("all_Products_Id", FieldValue.arrayUnion(id));


                                    }
                                });
                        product.setId(id);
                    }
                });
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show();
//                    }
//                });

    }


    class Asynproduct extends AsyncTask<String, Bitmap, Bitmap> {
        Product product;
        AddProduct fram;

        public Asynproduct(Product product, AddProduct fram) {
            this.product = product;
            this.fram = fram;
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
            fram.SetMetaProduct(metaProduct);
        }
    }

    public void deleteProduct(Product product) {
        fs.collection("Products").document(product.getId()).delete();
        fs.collection("Shops").document(product.getId_Owner_Shop()).collection("Category_For_Users")
                .document(product.getProduct_Kind()).update("productsId", FieldValue.arrayRemove(product.getId()));
        fs.collection("Abstract_Categories").document(product.getProduct_Kind()).update("all_Products_Id", FieldValue.arrayRemove(product.getId()));
    }
}
