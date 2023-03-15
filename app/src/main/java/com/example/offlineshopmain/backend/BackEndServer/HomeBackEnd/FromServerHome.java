package com.example.offlineshopmain.backend.BackEndServer.HomeBackEnd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.offlineshopmain.backend.UsedClass.Current_User;
import com.example.offlineshopmain.backend.UsedClass.MetaProduct;
import com.example.offlineshopmain.backend.UsedClass.Product;
import com.example.offlineshopmain.backend.UsedClass.Shop;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FromServerHome implements ServerMethodsHome {
    static FirebaseFirestore fs = FirebaseFirestore.getInstance();
    static FirebaseAuth auth = FirebaseAuth.getInstance();
    public Context context;
    public static ServerMethodsHome fromServer = new FromServerHome();
    static String id_User = auth.getUid();
    private static final String TAG = "FromServerHome";

    public interface Home {
        void LoadedNotWatched(MetaProduct products);
        void LoadedWatched(MetaProduct metaProduct);
    }

    @Override
    public void loadwatchedProducts(Home fram, Context context) {
        this.context = context;
        fs.collection("Shops").document(id_User).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Shop shop = documentSnapshot.toObject(Shop.class);
                    ArrayList<String> productsIds = (ArrayList<String>) shop.getWatched_list_ids();
                    for (String id : productsIds) {
                        fs.collection("Products").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    File ff = new File(context.getExternalCacheDir().getAbsolutePath() + "/" + documentSnapshot.getId());
                                    if (ff.exists()) {
                                        Product product = documentSnapshot.toObject(Product.class);
                                        Bitmap myBitmap = BitmapFactory.decodeFile(ff.getAbsolutePath());
                                        MetaProduct metaProduct = new MetaProduct(myBitmap, product);
                                        fram.LoadedWatched(metaProduct);
                                    } else
                                    {
                                        Asyn asyn = new Asyn(documentSnapshot.toObject(Product.class), fram);
                                        asyn.execute(documentSnapshot.toObject(Product.class).getImageURL());

                                    }
                                }
                            }
                        });
                    }
                } else {

                    fs.collection("Current_Users").document(id_User).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                Current_User user = documentSnapshot.toObject(Current_User.class);
                                ArrayList<Product> products = new ArrayList<>();
                                ArrayList<String> productsIds = (ArrayList<String>) user.getWatched_list_ids();
                                for (String id : productsIds) {
                                    fs.collection("Products").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if (documentSnapshot.exists()) {

                                                File ff = new File(context.getExternalCacheDir().getAbsolutePath() + "/" + documentSnapshot.getId());
                                                if (ff.exists()) {
                                                    Product product = documentSnapshot.toObject(Product.class);
                                                    Bitmap myBitmap = BitmapFactory.decodeFile(ff.getAbsolutePath());
                                                    Log.d(TAG, "doInBackground: findit");
                                                    MetaProduct metaProduct = new MetaProduct(myBitmap, product);
                                                    fram.LoadedWatched(metaProduct);
                                                } else {
                                                    Asyn asyn = new Asyn(documentSnapshot.toObject(Product.class), fram);
                                                    asyn.execute(documentSnapshot.toObject(Product.class).getImageURL());

                                                }

                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });


                }
            }
        });
    }

    @Override
    public void loadnotWatchedProducts(Home fram, Context context) {
        this.context = context;
        fs.collection("Shops").document(id_User).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Shop shop = documentSnapshot.toObject(Shop.class);
                    ArrayList<Product> products = new ArrayList<>();
                    ArrayList<String> productsIds = (ArrayList<String>) shop.getWatched_list_ids();
                    fs.collection("Products").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                                if (queryDocumentSnapshot.exists() && !productsIds.contains(queryDocumentSnapshot.getId())) {
                                    File ff = new File(context.getExternalCacheDir().getAbsolutePath() + "/" + documentSnapshot.getId());
                                    if (ff.exists()) {
                                        Product product = documentSnapshot.toObject(Product.class);
                                        Bitmap myBitmap = BitmapFactory.decodeFile(ff.getAbsolutePath());
                                        Log.d(TAG, "doInBackground: findit");
                                        MetaProduct metaProduct = new MetaProduct(myBitmap, product);
                                        fram.LoadedNotWatched(metaProduct);
                                    } else

                                    {
                                        Asynnotwatched asyn = new Asynnotwatched(documentSnapshot.toObject(Product.class), fram);
                                        asyn.execute(documentSnapshot.toObject(Product.class).getImageURL());

                                    }


                                }
                            }
                        }
                    });
                } else {

                    fs.collection("Current_Users").document(id_User).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                Current_User user = documentSnapshot.toObject(Current_User.class);
                                ArrayList<Product> products = new ArrayList<>();
                                ArrayList<String> productsIds = (ArrayList<String>) user.getWatched_list_ids();
                                fs.collection("Products").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                                            if (queryDocumentSnapshot.exists() && !productsIds.contains(queryDocumentSnapshot.getId())) {

                                                File ff = new File(context.getExternalCacheDir().getAbsolutePath() + "/" + documentSnapshot.getId());
                                                if (ff.exists()) {
                                                    Product product = documentSnapshot.toObject(Product.class);
                                                    Bitmap myBitmap = BitmapFactory.decodeFile(ff.getAbsolutePath());
                                                    Log.d(TAG, "doInBackground: findit");
                                                    MetaProduct metaProduct = new MetaProduct(myBitmap, product);
                                                    fram.LoadedNotWatched(metaProduct);
                                                } else {
                                                    Asynnotwatched asyn = new Asynnotwatched(documentSnapshot.toObject(Product.class), fram);
                                                    asyn.execute(documentSnapshot.toObject(Product.class).getImageURL());

                                                }


                                            }
                                        }
                                    }
                                });
                            }
                        }
                    });


                }
            }
        });
    }

    @Override
    public void addToWatched(String productId, Context context) {
        this.context = context;
        fs.collection("Shops").document(id_User).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    fs.collection("Shops").document(id_User).update("watched_list_ids", FieldValue.arrayUnion(productId));
                } else
                    fs.collection("Current_Users").document(id_User).update("watched_list_ids", FieldValue.arrayUnion(productId));

            }
        });
    }

    public void deleteProduct(Product product, Context context) {
        this.context = context;
        fs.collection("Products").document(product.getId()).delete();
        fs.collection("Shops").document(product.getId_Owner_Shop()).collection("Category_For_Users")
                .document(product.getProduct_Kind()).update("productsId", FieldValue.arrayRemove(product.getId()));
        fs.collection("Abstract_Categories").document(product.getProduct_Kind()).update("all_Products_Id", FieldValue.arrayRemove(product.getId()));
    }

    class Asyn extends AsyncTask<String, Bitmap, Bitmap> {
        Product product;
        Home fram;

        public Asyn(Product product, Home fram) {
            this.product = product;
            this.fram = fram;
        }

        @SuppressLint("WrongThread")
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap myBitmap = null;
            {
                Log.d(TAG, "doInBackground: didn't find it");
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    myBitmap = BitmapFactory.decodeStream(input);
                } catch (IOException e) {
                }
            }
            return myBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            MetaProduct metaProduct = new MetaProduct(bitmap, product);
            fram.LoadedWatched(metaProduct);
        }
    }

    class Asynnotwatched extends AsyncTask<String, Bitmap, Bitmap> {
        Product product;
        Home fram;

        public Asynnotwatched(Product product, Home fram) {
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
            fram.LoadedNotWatched(metaProduct);
        }
    }

//public class MetaProduct {
//        private Bitmap image;
//        private Product product;
//
//    public MetaProduct(Bitmap image, Product product) {
//        this.image = image;
//        this.product = product;
//    }
//
//    public Bitmap getImage() {
//        return image;
//    }
//
//    public void setImage(Bitmap image) {
//        this.image = image;
//    }
//
//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
//}
}
