package com.example.offlineshopmain.mainflow.Fragments.OneShop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.BackEndServer.ShopsBackEnd.FromServerShops;
import com.example.offlineshopmain.backend.BackEndServer.ShopsBackEnd.ServerMethodsShops;
import com.example.offlineshopmain.backend.UsedClass.Category_For_User;
import com.example.offlineshopmain.backend.UsedClass.Review;
import com.example.offlineshopmain.backend.UsedClass.Shop;
import com.example.offlineshopmain.mainflow.Fragments.OneShop.Adapters.Abstract_categories;
import com.example.offlineshopmain.mainflow.Fragments.OneShop.Adapters.Adapter_Products;
import com.example.offlineshopmain.mainflow.Interface.toProduct;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OneShop_Fragment extends Fragment implements Abstract_categories.Navigate_Category, FromServerShops.OneShop, OnMapReadyCallback {
    View view;
    RecyclerView abstractCategories, onCategory;
    RoundedImageView imageView;
    TextView shop_name, onCategory_name, rate, numrates, sendReview, numFollowing, textFollowing, textLocation;
    CardView info, reviews;
    RelativeLayout followingCard, relativeLocation;
    EditText reviewsEdit;
    ImageButton homebtn, backbtn, redheart, ringheart, blackdislike, ringdislike;
    ImageView userImage;
    ProgressBar bar;
    MapView shopLocation;
    GoogleMap googleMap;
    ServerMethodsShops fromServer = FromServerShops.fromServer;
    public static final String KEY_SHOP_ID = "KEY_SHOP_ID";
    private static final String TAG = "OneShop_Fragment";
    public static final String MAPVIEW_BUNDLE_KEY = "MAPVIEW_BUNDLE_KEY", Lat_key = "Lat_key", Lon_Key = "Lon_key";
    toProduct activity;

    public OneShop_Fragment(toProduct toProduct) {
        this.activity = toProduct;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one_shop_, container, false);
        setin();
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;
    }


    private void setin() {
        abstractCategories = view.findViewById(R.id.FOS_AbstractCategories_RecyclerView);
        onCategory = view.findViewById(R.id.FOS_OneCategory_RecyclerView);
        imageView = view.findViewById(R.id.FOS_roundedImage);
        shop_name = view.findViewById(R.id.FOS_shopName);
        rate = view.findViewById(R.id.FOS_Rate);
        numrates = view.findViewById(R.id.FOS_NumRates);
        onCategory_name = view.findViewById(R.id.FOS_oneCategory_Name);
        info = view.findViewById(R.id.FOS_info);
        reviews = view.findViewById(R.id.FOS_reviews);
        homebtn = view.findViewById(R.id.FOS_homebtn);
        backbtn = view.findViewById(R.id.FOS_backbtn);
        blackdislike = view.findViewById(R.id.blackdislike);
        ringdislike = view.findViewById(R.id.ringdislike);
        redheart = view.findViewById(R.id.redheart);
        ringheart = view.findViewById(R.id.ringnheart);
        reviewsEdit = view.findViewById(R.id.FOS_reviewEdit);
        sendReview = view.findViewById(R.id.FOS_sendReview_TextView);
        userImage = view.findViewById(R.id.FOS_userImage);
        bar = view.findViewById(R.id.progress_bar);
        numFollowing = view.findViewById(R.id.FOS_numfollowing);
        followingCard = view.findViewById(R.id.FOS_followingCard);
        textFollowing = view.findViewById(R.id.FOS_textFollowing);
        shopLocation = view.findViewById(R.id.FOS_mapView);
        shopLocation.onCreate(null);
        shopLocation.getMapAsync(this);
        textLocation = view.findViewById(R.id.FOS_textLocation);
        relativeLocation = view.findViewById(R.id.FOS_relativeLocation);
        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.tohome();
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) activity).onBackPressed();
            }
        });
        Bundle bundle = getArguments();
        String newid = bundle.getString(KEY_SHOP_ID);
        followingCard.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (textFollowing.getText().toString().equals("Follow")) {
                    textFollowing.setText("Following");
                    followingCard.setBackground(new ColorDrawable(Color.GRAY));
                    fromServer.doFollowing(newid);
                } else {
                    textFollowing.setText("Follow");
                    followingCard.setBackground(new ColorDrawable(Color.RED));
                    fromServer.removeFollowing(newid);
                }
            }
        });
        setLikesBtns(newid);
        fromServer.loadShopInfo(newid, this);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info_BottomDialog dialog = new Info_BottomDialog((Activity) activity, R.style.BottomSheetDialogTheme, newid);
                dialog.setContentView(null);
                dialog.show();
            }
        });
        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reviews_BottomDialog dialog = new Reviews_BottomDialog((Activity) activity, R.style.BottomSheetDialogTheme, newid);
                dialog.setContentView(null);
                dialog.show();
            }
        });
        fromServer.getUserImage(this);

    }

    private void addReview(String id_Shop) {
        String str = reviewsEdit.getText().toString();
        if (str.equals(""))
            return;
        bar.setVisibility(View.VISIBLE);
        Date date = Calendar.getInstance().getTime();
        String stringdate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
        Review review = new Review(fromServer.getUserId(), str, new ArrayList<>(), stringdate, id_Shop, 0, new ArrayList<>());
        fromServer.addReview(review, getArguments().getString(KEY_SHOP_ID), this);
    }

    private void setLikesBtns(String id_Shop) {
        sendReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReview(id_Shop);
            }
        });
        redheart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromServer.removeLove(id_Shop);
                redheart.setVisibility(View.GONE);
                ringheart.setVisibility(View.VISIBLE);
            }
        });
        ringheart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromServer.addLove(id_Shop);
                fromServer.removeDislike(id_Shop);
                redheart.setVisibility(View.VISIBLE);
                ringheart.setVisibility(View.GONE);
                blackdislike.setVisibility(View.GONE);
                ringdislike.setVisibility(View.VISIBLE);
            }
        });
        blackdislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromServer.removeDislike(id_Shop);
                blackdislike.setVisibility(View.GONE);
                ringdislike.setVisibility(View.VISIBLE);
            }
        });
        ringdislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromServer.addDislike(id_Shop);
                fromServer.removeLove(id_Shop);
                redheart.setVisibility(View.GONE);
                ringheart.setVisibility(View.VISIBLE);
                blackdislike.setVisibility(View.VISIBLE);
                ringdislike.setVisibility(View.GONE);
            }
        });
    }

    private void setAll(Shop shop) {
        bar.setVisibility(View.GONE);
        shop_name.setText(shop.getName());
        Glide.with((Activity) activity).load(shop.getImageURL()).centerCrop().into(imageView);
        rate.setText(shop.getRate() + "/5");
        numrates.setText((shop.getLiked_Users_Id().size() + shop.getDisliked_Users_Id().size()) + " ratings");
        Query query = FirebaseFirestore.getInstance().collection("Shops")
                .document(shop.getId())
                .collection("Category_For_Users").whereNotEqualTo("name_Of_Category", "add");
        FirestoreRecyclerOptions<Category_For_User> options = new FirestoreRecyclerOptions.Builder<Category_For_User>()
                .setQuery(query, Category_For_User.class)
                .setLifecycleOwner(this)
                .build();
        Abstract_categories adapter = new Abstract_categories(options, this);
        abstractCategories.setAdapter(adapter);
        abstractCategories.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        ArrayList<String> likedusersids = (ArrayList<String>) shop.getLiked_Users_Id();
        ArrayList<String> dislikedusersids = (ArrayList<String>) shop.getDisliked_Users_Id();
        String id_User = fromServer.getUserId();

        if (likedusersids.contains(id_User)) {
            redheart.setVisibility(View.VISIBLE);
            ringheart.setVisibility(View.GONE);
        } else {
            redheart.setVisibility(View.GONE);
            ringheart.setVisibility(View.VISIBLE);
        }
        if (dislikedusersids.contains(id_User)) {
            blackdislike.setVisibility(View.VISIBLE);
            ringdislike.setVisibility(View.GONE);
        } else {
            blackdislike.setVisibility(View.GONE);
            ringdislike.setVisibility(View.VISIBLE);
        }

        int followers = 0;
        if (shop.getFollowers_ids() != null)
            followers = shop.getFollowers_ids().size();
        numFollowing.setText(followers + " Followers");
        if (shop.getFollowers_ids().contains(id_User)) {
            textFollowing.setText("Following");
            followingCard.setBackground(new ColorDrawable(Color.GRAY));
        }
        textLocation.setText(shop.getAddress());
        relativeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latitude = String.valueOf(shop.getLatitude());
                String longitude = String.valueOf(shop.getLongitude());
                String uri = "https://www.google.com.tw/maps/place/" + latitude + "," + longitude;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(shop.getLatitude(), shop.getLongitude()))
                .title("Marker"));

    }

    @Override
    public void navegateto(String id_Category, String id_Shop) {
        fromServer.loadShopCategory(id_Shop, id_Category, (toProduct) getActivity(), this);
    }

    @Override
    public void setLoadedShopInfo(Shop shop) {
        if (shop != null)
            setAll(shop);
        else
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getShopCategory(ArrayList<String> productsIds, String CategoryName) {
        Adapter_Products adapter = new Adapter_Products(getActivity(), (toProduct) getActivity());
        adapter.setArrayList(productsIds);
        onCategory.setAdapter(adapter);
        onCategory.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        onCategory.setVisibility(View.VISIBLE);
        onCategory_name.setText(CategoryName);
        onCategory_name.setVisibility(View.VISIBLE);
    }

    @Override
    public void setUserImage(String imageURL) {
        Log.d(TAG, "setUserImage: " + imageURL);
        Glide.with(getActivity()).load(imageURL).centerCrop().into(userImage);
    }

    @Override
    public void completeAddReview() {
        bar.setVisibility(View.GONE);
        Bundle bundle = getArguments();
        reviewsEdit.setText("");
        String newid = bundle.getString(KEY_SHOP_ID);
        Reviews_BottomDialog dialog = new Reviews_BottomDialog((Activity) activity, R.style.BottomSheetDialogTheme, newid);
        dialog.setContentView(null);
        dialog.show();
    }


    @Override
    public void onResume() {
        super.onResume();
        shopLocation.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        shopLocation.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        shopLocation.onStop();
    }


    @Override
    public void onPause() {
        shopLocation.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        shopLocation.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        shopLocation.onLowMemory();
    }

}