package com.example.offlineshopmain.backend;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.offlineshopmain.backend.UsedClass.Abstract_Category;
import com.example.offlineshopmain.backend.UsedClass.Category_For_User;
import com.example.offlineshopmain.backend.UsedClass.Current_User;
import com.example.offlineshopmain.backend.UsedClass.Product;
import com.example.offlineshopmain.backend.UsedClass.Shop;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewAllData {

    public static FirebaseFirestore fs=FirebaseFirestore.getInstance();
    public static void addAbstract_Category(Abstract_Category categoryFire, Context context){
        categoryFire.setId(categoryFire.getName());
        fs.document("/Abstract_Categories/"+categoryFire.getName()).set(categoryFire);
    }
    public static void addCategory_For_User(Category_For_User categoryFire, Context context){
        fs.collection("Category_For_Users").add(categoryFire)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String id=documentReference.getId();
                        fs.collection("Category_For_Users").document(id)
                                .update("id",id);
                        Toast.makeText(context, "Added Correctly", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public static void addCurrent_User(Current_User User,Context context){
        fs.collection("Current_Users").add(User)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String id=documentReference.getId();
                        fs.collection("Current_Users").document(id)
                                .update("id",id);
                        Toast.makeText(context, "Added Correctly", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public static void addProduct(Product product, Context context){
        fs.collection("Products").add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                      String id= documentReference.getId();
                              fs.collection("Products").document(id)
                                      .update("id",id);
                              product.setId(id);
                        Toast.makeText(context, "Added Correctly", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    public static void addShop(Shop shopFire, Context context){
        fs.collection("Shops").add(shopFire)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String id=documentReference.getId();
                        fs.collection("Shops").document(id)
                                .update("id",id);
                        Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public static void addUser(Current_User user,Context context,String id){
        user.setId(id);
        fs.collection("Current_Users").document(id).set(user);
    }


    public static void deleteProduct(Product product){
        fs.collection("Products").document(product.getId()).delete();
        fs.collection("Shops").document(product.getId_Owner_Shop()).collection("Category_For_Users")
                .document(product.getProduct_Kind()).update("productsId", FieldValue.arrayRemove(product.getId()));
        fs.collection("Abstract_Categories").document(product.getProduct_Kind()).update("all_Products_Id",FieldValue.arrayRemove(product.getId()));
    }

}
