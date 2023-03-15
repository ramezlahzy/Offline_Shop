package com.example.offlineshopmain.backend.BackEndServer.LovedBackEnd;

import androidx.annotation.NonNull;

import com.example.offlineshopmain.backend.UsedClass.Current_User;
import com.example.offlineshopmain.backend.UsedClass.Product;
import com.example.offlineshopmain.backend.UsedClass.Shop;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FromServerLoved implements ServerMethodLoved {
    static FirebaseFirestore fs = FirebaseFirestore.getInstance();
    static FirebaseAuth auth = FirebaseAuth.getInstance();
    public static ServerMethodLoved fromServer = new FromServerLoved();
    static String id_User = auth.getUid();

    @Override
    public void getLovedProducts(Profile fram) {

        fs.collection("Shops").document(id_User).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        setAdapterShop(fram);
                    } else {
                        setAdapterCusmoter(fram);
                    }
                }
            }
        });
    }

    @Override
    public void getLovedShops(Profile fram) {
        fs.collection("Shops").document(id_User).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        showLovedShops(fram, snapshot.toObject(Shop.class).getFav_Shops_ids());
                    } else
                        fs.collection("Current_Users").document(id_User).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                showLovedShops(fram,documentSnapshot.toObject(Current_User.class).getFav_Shops_ids());
                            }
                        });
                }
            }
        });
    }

    public void showLovedShops(Profile fram, List<String> shopsID) {
        for (String s : shopsID) {
            fs.collection("Shops").document(s).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    fram.LoadedFavListShops(documentSnapshot.toObject(Shop.class));
                }
            });
        }
    }

    private void setAdapterShop(Profile fram) {
        fs.collection("Shops").document(id_User).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Shop shop = task.getResult().toObject(Shop.class);
                    setarray(shop.getFav_Products_ids(), fram);
                }
            }
        });
    }

    private void setAdapterCusmoter(Profile fram) {
        fs.collection("Current_Users").document(id_User).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Current_User shop = task.getResult().toObject(Current_User.class);
                    setarray(shop.getFav_Products_ids(), fram);
                }
            }
        });
    }

    private void setarray(List<String> fav_products_ids, Profile fram) {
        ArrayList<Product> products = new ArrayList<>();
        for (String id_Product : fav_products_ids) {
            fs.collection("Products").document(id_Product).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Product product = documentSnapshot.toObject(Product.class);
                    if (product != null) {
                        products.add(product);
                        fram.LoadedFavList(products);
                    }

                }
            });
        }

    }

    public void removeLovedProduct(String productId) {
        fs.collection("Shops").document(id_User).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot.exists())
                        fs.collection("Shops").document(id_User).update("fav_Products_ids", FieldValue.arrayRemove(productId));
                    else
                        fs.collection("Current_Users").document(id_User).update("fav_Products_ids", FieldValue.arrayRemove(productId));
                }
            }
        });
    }

    public interface Profile {
        void LoadedFavList(ArrayList<Product> products);

        void LoadedFavListShops(Shop shop);
    }


}
