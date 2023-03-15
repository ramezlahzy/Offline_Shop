package com.example.offlineshopmain.mainflow.Fragments.OneProduct;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.BackEndServer.OneProductBackEnd.FromServerOneProductOneProduct;
import com.example.offlineshopmain.backend.BackEndServer.OneProductBackEnd.ServerMethodsOneProduct;
import com.example.offlineshopmain.backend.UsedClass.Product;
import com.example.offlineshopmain.backend.UsedClass.Review;
import com.example.offlineshopmain.backend.UsedClass.Shop;
import com.example.offlineshopmain.mainflow.Fragments.Four_Views.Home_Fragments.Fragment_Product_In_Rows;
import com.example.offlineshopmain.mainflow.Fragments.OneProduct.Adapter.Reviews_Adapter;
import com.example.offlineshopmain.mainflow.Interface.toProduct;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OneProduct_Fragment extends Fragment implements FromServerOneProductOneProduct.OneProductInterFace {


    private static final String TAG = "OneProduct_Fragment";
    public static final String KEY_ONE_PRODUCT = "KEY_ONE_PRODUCT";
    View view;
    FrameLayout frameLayout;
    ImageView product_Image, add_favCorrect, imageComment, downLoad;
    RoundedImageView product_Shop;
    ImageButton blackdislike, ringdislike, redheart, ringheart, backbtn, homebtn, expandbtn, deexpandbtn;
    TextView product_Name, product_Price, product_Describtion, shop_Name, sentReview, textFollowing;
    EditText review_Edit;
    CardView add_favCard;
    ProgressBar bar;
    RecyclerView allReviews;
    View viewsplit, ShopCard;
    RelativeLayout following;
    String id_User = FirebaseAuth.getInstance().getUid();
    Reviews_Adapter adapter;
    ServerMethodsOneProduct fromServer = FromServerOneProductOneProduct.fromServer;
    toProduct tproduct;
    String productId;

    public OneProduct_Fragment(toProduct tproduct) {
        this.tproduct = tproduct;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.onproduct_fragment, container, false);
        setin();
        return view;
    }

    private void setin() {
        viewsplit = view.findViewById(R.id.OPF_View);
        frameLayout = view.findViewById(R.id.OPF_FrameLayout_remainProduct);
        product_Image = view.findViewById(R.id.OPF_imageView);
        product_Shop = view.findViewById(R.id.OPF_shopImage);
        blackdislike = view.findViewById(R.id.blackdislike);
        ringdislike = view.findViewById(R.id.ringdislike);
        redheart = view.findViewById(R.id.redheart);
        ringheart = view.findViewById(R.id.ringnheart);
        product_Name = view.findViewById(R.id.OPF_name);
        product_Price = view.findViewById(R.id.OPF_Price);
        product_Describtion = view.findViewById(R.id.OPF_describtion);
        shop_Name = view.findViewById(R.id.OPF_shopName);
        add_favCard = view.findViewById(R.id.OPF_Addtofav);
        homebtn = view.findViewById(R.id.OPF_homebtn);
        backbtn = view.findViewById(R.id.OPF_backbtn);
        bar = view.findViewById(R.id.OPF_progressbar);
        add_favCorrect = view.findViewById(R.id.OPF_correct);
        expandbtn = view.findViewById(R.id.OPF_expandImageBtn);
        deexpandbtn = view.findViewById(R.id.OPF_notexpandImageBtn);
        allReviews = view.findViewById(R.id.OPF_Reviews_RecyclerView);
        sentReview = view.findViewById(R.id.OPF_sendReview_TextView);
        review_Edit = view.findViewById(R.id.OPF_TypeReviewEditText);
        imageComment = view.findViewById(R.id.OPF_userImage);
        ShopCard = view.findViewById(R.id.OPF_Shop_card);
        following = view.findViewById(R.id.OPF_Following);
        textFollowing = view.findViewById(R.id.OPF_textFollowing);
        downLoad = view.findViewById(R.id.OPF_DownLoad);

        expandbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allReviews.setVisibility(View.VISIBLE);
                expandbtn.setVisibility(View.INVISIBLE);
                deexpandbtn.setVisibility(View.VISIBLE);
            }
        });
        deexpandbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allReviews.setVisibility(View.GONE);
                expandbtn.setVisibility(View.VISIBLE);
                deexpandbtn.setVisibility(View.INVISIBLE);
            }
        });

        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textFollowing.getText().toString().equals("Follow")) {
                    textFollowing.setText("Following");
                    following.setBackground(new ColorDrawable(Color.GRAY));
                    fromServer.addToFav(getArguments().getString(KEY_ONE_PRODUCT));
                } else {
                    textFollowing.setText("Follow");
                    following.setBackground(new ColorDrawable(Color.BLACK));
                    fromServer.RemoveFromFav(getArguments().getString(KEY_ONE_PRODUCT));
                }
            }
        });

        FromServerOneProductOneProduct.fromServer.getproduct(this, getArguments().getString(KEY_ONE_PRODUCT));
        setLikesBtns(getArguments().getString(KEY_ONE_PRODUCT));
        setreviewsAdapter(getArguments().getString(KEY_ONE_PRODUCT));
        productId = getArguments().getString(KEY_ONE_PRODUCT);
        File ff = new File(getContext().getExternalCacheDir().getAbsolutePath() + "/" + productId);
        if (ff.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(ff.getAbsolutePath());
            Glide.with(getContext()).load(myBitmap).fitCenter().into(product_Image);
        }
        Fragment_Product_In_Rows fragment = new Fragment_Product_In_Rows(tproduct);
        FragmentTransaction Transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Transaction.replace(R.id.OPF_FrameLayout_remainProduct, fragment);
        Transaction.commit();
    }


    private void setAll(Product product) {
        viewsplit.setVisibility(View.GONE);
        File ff = new File(getContext().getExternalCacheDir().getAbsolutePath() + "/" + productId);
        if (!ff.exists())
            Glide.with(getContext()).load(product.getImageURL()).fitCenter().into(product_Image);
        fromServer.setLikesOneProduct(this, product.getId());
        setTexts(product);
        FromServerOneProductOneProduct.fromServer.setUserImageOneProduct(product, this);
        sentReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!review_Edit.getText().toString().equals("")) {
                    addReview(review_Edit.getText().toString(), product.getId());
                    review_Edit.setText("");
                }
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onback();
            }
        });
        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tohome();
            }
        });
        add_favCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromServer.isShop_addFav(OneProduct_Fragment.this, product.getId());
            }
        });
        ShopCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((toProduct) getActivity()).toShop(product.getId_Owner_Shop());
            }
        });

    }

    public void following(boolean follow) {
        if (follow) {
            textFollowing.setText("Following");
            following.setBackground(new ColorDrawable(Color.GRAY));
        } else {
            textFollowing.setText("Follow");
            following.setBackground(new ColorDrawable(Color.BLACK));
        }
    }

    public void setImageComment(String ImageUrl) {
        Picasso.with(getContext()).load(ImageUrl)
                .fit()
                .centerCrop()
                .into(imageComment);

    }


    private void addReview(String TextReview, String id_Product) {
        Calendar.getInstance();
        Date date = Calendar.getInstance().getTime();
//        String stringdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        String stringdate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
        Review review = new Review(id_User, TextReview, new ArrayList<>(), stringdate, id_Product, 0, new ArrayList<>());
        review.setId(System.currentTimeMillis() + "");
        fromServer.setReview(id_Product, review.getId(), review);
        allReviews.setVisibility(View.VISIBLE);
        expandbtn.setVisibility(View.INVISIBLE);
        deexpandbtn.setVisibility(View.VISIBLE);
    }

    private void setLikesBtns(String id_Product) {
        redheart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromServer.removeLove(OneProduct_Fragment.this, id_Product);
                redheart.setVisibility(View.GONE);
                ringheart.setVisibility(View.VISIBLE);
            }
        });
        ringheart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromServer.addLove(OneProduct_Fragment.this, id_Product);
                fromServer.removeDislike(OneProduct_Fragment.this, id_Product);
                redheart.setVisibility(View.VISIBLE);
                ringheart.setVisibility(View.GONE);
                blackdislike.setVisibility(View.GONE);
                ringdislike.setVisibility(View.VISIBLE);
            }
        });
        blackdislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromServer.removeDislike(OneProduct_Fragment.this, id_Product);
                blackdislike.setVisibility(View.GONE);
                ringdislike.setVisibility(View.VISIBLE);
            }
        });
        ringdislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromServer.addDislike(OneProduct_Fragment.this, id_Product);
                fromServer.removeLove(OneProduct_Fragment.this, id_Product);
                redheart.setVisibility(View.GONE);
                ringheart.setVisibility(View.VISIBLE);
                blackdislike.setVisibility(View.VISIBLE);
                ringdislike.setVisibility(View.GONE);
            }
        });
    }


    public void LoadedsetShop(Shop shop) {
        if (shop != null) {
            shop_Name.setText(shop.getName());
            Picasso.with(getContext()).load(shop.getImageURL())
                    .fit()
                    .centerCrop()
                    .into(product_Shop);
        } else
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
    }


    public void LoadedIsShop_addFav() {
        add_favCorrect.setVisibility(View.VISIBLE);
    }

    private void setTexts(Product product) {
        product_Name.setText(product.getProduct_name());
        product_Price.setText(product.getPrice() + " L.E");
        product_Describtion.setText(product.getDescribtion());
        fromServer.setShop(this, product.getId_Owner_Shop());

    }


    public void LoadSetLikes(Product product) {
        bar.setVisibility(View.GONE);
        if (product != null)
            setLikes2((ArrayList<String>) product.getLikedusersids(), (ArrayList<String>) product.getDislikedusersids());
        else
            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
    }

    private void setLikes2(ArrayList<String> likedusersids, ArrayList<String> dislikedusersids) {
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
    }


    public void onback() {
        getActivity().onBackPressed();
    }

    public void tohome() {
        ((toProduct) getActivity()).tohome();
    }

    private void setreviewsAdapter(String id_product) {
        Query query = FirebaseFirestore.getInstance().collection("Products").document(id_product)
                .collection("Reviews");
        FirestoreRecyclerOptions<Review> options = new FirestoreRecyclerOptions.Builder<Review>()
                .setQuery(query, Review.class)
                .setLifecycleOwner(this)
                .build();
        adapter = new Reviews_Adapter(options);
        allReviews.setAdapter(adapter);
        allReviews.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.startListening();
    }

    OutputStream outputStream;

    private void saveImage() {
        File dir = new File(Environment.getExternalStorageDirectory(), "SaveImage");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        BitmapDrawable drawable = (BitmapDrawable) product_Image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        File file = new File(dir, System.currentTimeMillis() + ".jpg");
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        file = new File(path, System.currentTimeMillis() + ".jpg");
        path.mkdirs();
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            Log.d(TAG, "saveImage: Exception  " + e.getMessage());
        }
        File ff = new File(getContext().getExternalCacheDir().getAbsolutePath() + "/Ro7iSmKUMsmm6CT4Gms1");
        try {

            Bitmap myBitmap = BitmapFactory.decodeFile(ff.getAbsolutePath());
            Log.d(TAG, "saveImage: " + myBitmap);
//            Log.d(TAG, "saveImage: "+file1.getAbsolutePath());
//            Drawable d = Drawable.createFromPath(file1.getAbsolutePath());
            product_Image.setImageBitmap(myBitmap);
            FileOutputStream fileOutputStream = new FileOutputStream(ff);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        Toast.makeText(getActivity(), "Successfuly Saved", Toast.LENGTH_SHORT).show();
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            File imgFile = new File(path, "1670276157784.jpg");

//            Log.d(TAG, "saveImage: " + file.exists() + " " + file.getAbsolutePath());
//        FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
//        String data = userInput.getText().toString();
//        fos.write(data.getBytes());
//        fos.flush();
//        fos.close();
            if (file.exists()) {

//                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                product_Image.setImageBitmap(myBitmap);
            }
        } else
            askPermission2();


    }

    @Override
    public void LoadProduct(Product product) {
        bar.setVisibility(View.GONE);
        if (product == null)
            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
        else {
            setAll(product);
            downLoad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                        saveImage();
                    else
                        askPermission();
                }
            });
        }
    }

    public static int REQUEST_CODE = 101;

    private void askPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

    private void askPermission2() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 22);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveImage();
            } else
                Toast.makeText(getActivity(), "Please provide the requist permissions", Toast.LENGTH_SHORT).show();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

