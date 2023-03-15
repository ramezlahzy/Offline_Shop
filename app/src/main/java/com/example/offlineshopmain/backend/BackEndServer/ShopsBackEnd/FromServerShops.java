package com.example.offlineshopmain.backend.BackEndServer.ShopsBackEnd;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.offlineshopmain.backend.UsedClass.Category_For_User;
import com.example.offlineshopmain.backend.UsedClass.Current_User;
import com.example.offlineshopmain.backend.UsedClass.Review;
import com.example.offlineshopmain.backend.UsedClass.Shop;
import com.example.offlineshopmain.backend.UsedClass.User;
import com.example.offlineshopmain.mainflow.Fragments.Four_Views.Shops_Fragments.Adapters.AdapterShopsFireStore;
import com.example.offlineshopmain.mainflow.Fragments.OneShop.Adapters.Reviews_Adapter;
import com.example.offlineshopmain.mainflow.Interface.toProduct;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class FromServerShops implements ServerMethodsShops {
    FirebaseFirestore fs = FirebaseFirestore.getInstance();
    public static ServerMethodsShops fromServer = new FromServerShops();
    FirebaseAuth auth=FirebaseAuth.getInstance();
    private static final String TAG = "FromServerShops";
    public interface Shops {
        void setRecyclerView(AdapterShopsFireStore shops);
    }

    public interface LoadUserViewHolder {
        void setLoadedUser(User user, Reviews_Adapter.ViewHolder position);
    }
    public interface LoadUser {
        void setLoadedUser(User user);
    }
    public interface LoadShop {
        void setLoadedShopInfo(Shop shop);
    }
    public interface OneShop extends LoadShop{
        void getShopCategory(ArrayList<String> productsIds,String CategoryName);
        void setUserImage(String imageURL);
        void completeAddReview();
    }

    @Override
    public void loadShops(Shops shops, Fragment fragment) {
        CollectionReference productsreference = fs.collection("Shops");
        Query query = productsreference.orderBy("id", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Shop> options = new FirestoreRecyclerOptions.Builder<Shop>()
                .setQuery(query, Shop.class)
                .setLifecycleOwner(fragment)
                .build();
        AdapterShopsFireStore shopsadatper = new AdapterShopsFireStore(options, (toProduct) fragment.getActivity());
        shops.setRecyclerView(shopsadatper);
    }

    @Override
    public void loadShopInfo(String id, LoadShop shopInfo) {
        fs.collection("Shops").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                    if (task.getResult().exists())
                        shopInfo.setLoadedShopInfo(task.getResult().toObject(Shop.class));
                    else
                        shopInfo.setLoadedShopInfo(null);
                else
                    shopInfo.setLoadedShopInfo(null);
            }
        });
    }

    @Override
    public void loadShopCategory(String id_Shop, String id_Category,toProduct toProduct,OneShop oneShop) {
        fs.collection("Shops").document(id_Shop).collection("Category_For_Users").document(id_Category).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Category_For_User category = task.getResult().toObject(Category_For_User.class);
                    ArrayList<String> arr = (ArrayList<String>) category.getProductsId();
                    oneShop.getShopCategory(arr,(String) task.getResult().get("id"));

                }
            }
        });
    }

    @Override
    public String getUserId() {
        return auth.getUid();
    }

    public void addLove(String id_Shop) {
        String id_User=auth.getUid();
        fs.collection("Shops").document(id_Shop).update("liked_Users_Id", FieldValue.arrayUnion(id_User));
    }

    public void removeLove(String id_Product) {
        String id_User=auth.getUid();
        fs.collection("Shops").document(id_Product).update("liked_Users_Id", FieldValue.arrayRemove(id_User));

    }

    public void addDislike(String id_Product) {
        String id_User=auth.getUid();
        fs.collection("Shops").document(id_Product).update("disliked_Users_Id", FieldValue.arrayUnion(id_User));
    }

    public void removeDislike(String id_Product) {
        String id_User=auth.getUid();
        fs.collection("Shops").document(id_Product).update("disliked_Users_Id", FieldValue.arrayRemove(id_User));
    }


    public void loadUser(LoadUserViewHolder loadUserViewHolder, Reviews_Adapter.ViewHolder position){
        fs.collection("Shops").document(getUserId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        loadUserViewHolder.setLoadedUser(task.getResult().toObject(Shop.class),position);
                    }
                    else
                        fs.collection("Current_Users").document(getUserId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful())
                                    if (task.getResult().exists())
                                        loadUserViewHolder.setLoadedUser(task.getResult().toObject(Current_User.class),position);
                            }
                        });
                }
            }
        });
    }
    public void loadUser(LoadUser loadUser){
        Log.d(TAG, "loadUser: ");
        fs.collection("Shops").document(getUserId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        loadUser.setLoadedUser(task.getResult().toObject(User.class));
                    }
                    else
                        fs.collection("Current_Users").document(getUserId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful())
                                    if (task.getResult().exists())
                                        loadUser.setLoadedUser(task.getResult().toObject(User.class));
                            }
                        });
                }
            }
        });
    }

    @Override
    public void doFollowing(String shop_ID) {
        addToFav(shop_ID,true);
    }

    @Override
    public void removeFollowing(String shop_ID) {
        addToFav(shop_ID,false);
    }
    public void addToFav(String shop_ID, boolean add) {
        String id_User = auth.getUid();
        DocumentReference docIdRef = fs.collection("Shops").document(id_User);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (add)
                            fs.collection("Shops").document(shop_ID).update("followers_ids",FieldValue.arrayUnion(id_User));
                        else
                            fs.collection("Shops").document(shop_ID).update("followers_ids",FieldValue.arrayRemove(id_User));
                        if (add) {
                            fs.collection("Shops").document(id_User).update("fav_Shops_ids", FieldValue.arrayUnion(shop_ID));
                        }else
                            fs.collection("Shops").document(id_User).update("fav_Shops_ids", FieldValue.arrayRemove(shop_ID));

                    } else {
                        if (add)
                            fs.collection("Current_Users").document(id_User).update("fav_Shops_ids", FieldValue.arrayUnion(shop_ID));
                        else
                            fs.collection("Current_Users").document(id_User).update("fav_Shops_ids", FieldValue.arrayRemove(shop_ID));

                    }

                }
            }
        });
    }


    @Override
    public void getUserImage(OneShop oneShop) {
        fs.collection("Shops").document(getUserId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                         oneShop.setUserImage(task.getResult().toObject(Shop.class).getImageURL());
                    }
                    else
                        fs.collection("Current_Users").document(getUserId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful())
                                    if (task.getResult().exists())
                                        oneShop.setUserImage(task.getResult().toObject(Current_User.class).getImageURL());
                            }
                        });
                }
            }
        });
    }

    @Override
    public void addReview(Review review, String shop_Id,OneShop oneShop) {
        fs.collection("Shops").document(shop_Id).collection("Shop_Reviews")
                .add(review).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        oneShop.completeAddReview();
                    }
                });

    }

}
