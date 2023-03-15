package com.example.offlineshopmain.ShopTrace.Fragments.SignUpFragmentsShop;

import static com.example.offlineshopmain.ShopTrace.Fragments.SignUpFragmentsShop.Sign1FragmentShop.KEY_NAME_SHOP;
import static com.example.offlineshopmain.ShopTrace.Fragments.SignUpFragmentsShop.Sign1FragmentShop.KEY_PHONE_SHOP;
import static com.example.offlineshopmain.ShopTrace.Fragments.SignUpFragmentsShop.Sign2FragmentShop.KEY_EMAIL_SHOP;
import static com.example.offlineshopmain.ShopTrace.Fragments.SignUpFragmentsShop.Sign2FragmentShop.KEY_PASSWORD_SHOP;
import static com.example.offlineshopmain.ShopTrace.Fragments.SignUpFragmentsShop.Sign3FragmentShop.KEY_URL_SHOP;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.UsedClass.Category_For_User;
import com.example.offlineshopmain.backend.UsedClass.Shop;
import com.example.offlineshopmain.mainflow.LoginFragment.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Sign4FragmentShop extends Fragment {
    View view;
    FirebaseAuth mAuth;
    ImageButton continuebtn;
    ProgressBar bar;
    public static FirebaseFirestore fs=FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sign4fragmentshop, container, false);
        setin(view);
        return view;
    }

    private void setin(View view) {
        //TODO set map
        mAuth=FirebaseAuth.getInstance();
        continuebtn = view.findViewById(R.id.s4fcontinuebtn);
        bar = view.findViewById(R.id.s4fprogressbarall);
        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                press();
            }
        });
    }

    private void press() {
        //TODO get location
        saveUserInformation(0, 0);
    }

    private void saveUserInformation(double latitude, double langitiude) {
        Bundle bundle = getArguments();
        String email = (String) bundle.get(KEY_EMAIL_SHOP);
        String password = (String) bundle.get(KEY_PASSWORD_SHOP);
        String shop_phone = (String) bundle.get(KEY_PHONE_SHOP);
        String shop_name = (String) bundle.get(KEY_NAME_SHOP);
        String Url = (String) bundle.get(KEY_URL_SHOP);
        bar.setVisibility(View.VISIBLE);
        FirebaseUser user = mAuth.getCurrentUser();
        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
        builder.setDisplayName(shop_name);
        builder.setPhotoUri(Uri.parse(Url.toString()));


        if (user != null) {
            UserProfileChangeRequest profile = builder.build();
            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Profile complete", Toast.LENGTH_SHORT).show();
                                String id = user.getUid();
                                Shop shop=new Shop("",shop_name,"","","","",shop_phone,new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>()
                                        ,Url,latitude,langitiude,email,password,new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),2.5);
                                shop.setId(id);
                                fs.collection("Shops").document(id).set(shop)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                               if (task.isSuccessful()) {
                                                   bar.setVisibility(View.GONE);
                                                   ((MainActivity)getActivity()).loginasshop();

                                                   Category_For_User category1 = new Category_For_User("id_Abstract_Category", new ArrayList<>(), "add", "https://firebasestorage.googleapis.com/v0/b/offline-shop-4f02a.appspot.com/o/uploads%2Faddgreen.png?alt=media&token=1eeb3a1c-f7d8-489c-9e0a-205283a3119b","add");
                                                   category1.setId("zzzzzzzzzzzzzzzzzzzzzzzzzzz");
                                                   fs.collection("Shops").document(id).collection("Category_For_Users").document("zzzzzzzzzzzzzzzzzzzzzzzzzzz").set(category1)
                                                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                               @Override
                                                               public void onComplete(@NonNull Task<Void> task) {
                                                                   Toast.makeText(getActivity(), task.isSuccessful() + "", Toast.LENGTH_SHORT).show();
                                                               }
                                                           });



                                               }
                                               else{
                                                   Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                               }
                                            }
                                        });





                            } else
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


        }
    }

}
