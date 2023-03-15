package com.example.offlineshopmain.backend.BackEndServer.OneProductBackEnd;

import androidx.annotation.NonNull;

import com.example.offlineshopmain.backend.UsedClass.Current_User;
import com.example.offlineshopmain.backend.UsedClass.Product;
import com.example.offlineshopmain.backend.UsedClass.Review;
import com.example.offlineshopmain.backend.UsedClass.Shop;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class FromServerOneProductOneProduct implements ServerMethodsOneProduct {
    static FirebaseFirestore fs = FirebaseFirestore.getInstance();
    static FirebaseAuth auth = FirebaseAuth.getInstance();
    public static ServerMethodsOneProduct fromServer = new FromServerOneProductOneProduct();

    public interface OneProductInterFace {
        void LoadProduct(Product product);

        void setImageComment(String ImageUrl);

        void LoadSetLikes(Product product);

        void LoadedIsShop_addFav();

        void LoadedsetShop(Shop shop);

        void following(boolean follow);

    }

    public String getUserId() {
        return auth.getUid();
    }

    @Override
    public void setLikesOneProduct(OneProductInterFace fram, String id_Product) {

        fs.collection("Products").document(id_Product).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Product product = task.getResult().toObject(Product.class);
                    fram.LoadSetLikes(product);
                } else
                    fram.LoadSetLikes(null);
            }
        });
    }

    @Override
    public void isShop_addFav(OneProductInterFace fram, String id_Product) {
        fram.LoadedIsShop_addFav();
        String id_User = fromServer.getUserId();
        DocumentReference docIdRef = fs.collection("Shops").document(id_User);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        fs.collection("Shops").document(id_User).update("fav_Products_ids", FieldValue.arrayUnion(id_Product));

                    } else {
                        fs.collection("Current_Users").document(id_User).update("fav_Products_ids", FieldValue.arrayUnion(id_Product));
                    }
                }
            }
        });
    }

    public void getproduct(OneProductInterFace fram, String string) {
        fs.collection("Products").document(string).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Product product = task.getResult().toObject(Product.class);
                    fram.LoadProduct(product);
                    askFollowing(fram, product.getId_Owner_Shop());
                } else
                    fram.LoadProduct(null);
            }
        });
    }

    public void askFollowing(OneProductInterFace fram, String shop_ID) {
        String id_User = auth.getUid();
        DocumentReference docIdRef = fs.collection("Shops").document(id_User);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        fs.collection("Shops").document(id_User).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Shop shop = document.toObject(Shop.class);
                                if (shop.getFav_Shops_ids().contains(shop_ID))
                                    fram.following(true);
                                else
                                    fram.following(false);
                            }
                        });
                    } else {
                        fs.collection("Current_Users").document(id_User).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Current_User user = documentSnapshot.toObject(Current_User.class);
                                if (user.getFav_Shops_ids().contains(shop_ID))
                                    fram.following(true);
                                else
                                    fram.following(false);
                            }
                        });
                    }

                }
            }
        });
    }

    @Override
    public void setUserImageOneProduct(Product product, OneProductInterFace oneproduct) {
        String id_User = auth.getUid();
        DocumentReference docIdRef = fs.collection("Shops").document(id_User);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        fs.collection("Shops").document(id_User).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Shop shop = document.toObject(Shop.class);
                                if (shop.getFav_Products_ids().contains(product.getId()))
                                    oneproduct.LoadedIsShop_addFav();
                                oneproduct.setImageComment(shop.getImageURL());
                            }
                        });
                    } else {
                        fs.collection("Current_Users").document(id_User).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Current_User user = documentSnapshot.toObject(Current_User.class);
                                if (user.getFav_Products_ids().contains(product.getId()))
                                    oneproduct.LoadedIsShop_addFav();
                                oneproduct.setImageComment(user.getImageURL());
                            }
                        });
                    }

                }
            }
        });
    }

    @Override
    public void setReview(String id_Product, String reviewId, Review review) {
        fs.collection("Products").document(id_Product).collection("Reviews").document(review.getId()).set(review);
    }

    public void addLove(FromServerOneProductOneProduct.OneProductInterFace fram, String id_Product) {
        String id_User = auth.getUid();
        fs.collection("Products").document(id_Product).update("likedusersids", FieldValue.arrayUnion(id_User))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            fromServer.setLikesOneProduct(fram, id_Product);

                    }
                });
    }

    public void removeLove(FromServerOneProductOneProduct.OneProductInterFace fram, String id_Product) {
        String id_User = auth.getUid();

        fs.collection("Products").document(id_Product).update("likedusersids", FieldValue.arrayRemove(id_User))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            fromServer.setLikesOneProduct(fram, id_Product);
                    }
                });

    }

    public void addDislike(OneProductInterFace fram, String id_Product) {
        String id_User = auth.getUid();
        fs.collection("Products").document(id_Product).update("dislikedusersids", FieldValue.arrayUnion(id_User))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            fromServer.setLikesOneProduct(fram, id_Product);
                    }
                });
    }

    public void removeDislike(FromServerOneProductOneProduct.OneProductInterFace fram, String id_Product) {
        String id_User = auth.getUid();
        fs.collection("Products").document(id_Product).update("dislikedusersids", FieldValue.arrayRemove(id_User))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            fromServer.setLikesOneProduct(fram, id_Product);
                    }
                });
    }

    @Override
    public void setShop(OneProductInterFace fram, String id_OwnerShop) {
        fs.collection("Shops").document(id_OwnerShop).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Shop shop = task.getResult().toObject(Shop.class);
                    fram.LoadedsetShop(shop);
                } else
                    fram.LoadedsetShop(null);

            }
        });
    }

    public void addToFav(String product_ID) {
        fs.collection("Products").document(product_ID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Product product = documentSnapshot.toObject(Product.class);
                    addToFav(product.getId_Owner_Shop(), true);
                }
            }
        });
    }

    public void RemoveFromFav(String product_ID) {
        fs.collection("Products").document(product_ID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Product product = documentSnapshot.toObject(Product.class);
                    addToFav(product.getId_Owner_Shop(), false);
                }
            }
        });
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


}
