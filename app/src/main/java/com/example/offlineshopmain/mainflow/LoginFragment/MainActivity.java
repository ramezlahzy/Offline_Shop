package com.example.offlineshopmain.mainflow.LoginFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.ShopTrace.Fragments.SignUpFragmentsShop.SignUpFragment1Shop;
import com.example.offlineshopmain.ShopTrace.Main_Branch_ActivityShop;
import com.example.offlineshopmain.mainflow.LoginFragment.ForgotPassward.ForgotFragment;
import com.example.offlineshopmain.mainflow.LoginFragment.SignUpFramgents.Sign0Fragment;
import com.example.offlineshopmain.mainflow.Main_Branch_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements Sign0Fragment.tosign1 {

    private static final String TAG = "MainActivity";
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private TextView logintext, signuptext, havetext, donttext;
    private RelativeLayout rigthpanel;
    private RelativeLayout leftpanel;
    private FrameLayout frameLayout;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        if (currentUser != null)
//            login();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent=new Intent(this, UpLoad_Activty.class);
//        startActivity(intent);
        setContentView(R.layout.newsignin);
        setin();
        tologin(null);
        addone(null);
    }


    public void addone(View view) {
        Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
        firebaseAuth.createUserWithEmailAndPassword("ramen@gmail.com", "newssword")
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    public void setin() {
        frameLayout = findViewById(R.id.loginFrameLayout);
        leftpanel = findViewById(R.id.newsigninleftpanel);
        rigthpanel = findViewById(R.id.newsigninrightpanel);
        havetext = findViewById(R.id.nsihavetext);
        donttext = findViewById(R.id.nsidonttext);
        signuptext = findViewById(R.id.nsisigntext);
        logintext = findViewById(R.id.nsilogintext);

    }

    public void tologin(View view) {
        leftpanel.setVisibility(View.VISIBLE);
        signuptext.setVisibility(View.VISIBLE);
        donttext.setVisibility(View.VISIBLE);
        rigthpanel.setVisibility(View.INVISIBLE);
        havetext.setVisibility(View.GONE);
        logintext.setVisibility(View.GONE);
        LoginFragment loginFragment = new LoginFragment();
        FragmentTransaction Transactionsearch = getSupportFragmentManager().beginTransaction();
        Transactionsearch.replace(R.id.loginFrameLayout, loginFragment);
        Transactionsearch.commit();
    }

    public void tosignup(View view) {
        leftpanel.setVisibility(View.INVISIBLE);
        signuptext.setVisibility(View.GONE);
        donttext.setVisibility(View.GONE);
        rigthpanel.setVisibility(View.VISIBLE);
        havetext.setVisibility(View.VISIBLE);
        logintext.setVisibility(View.VISIBLE);
        Sign0Fragment loginFragment = new Sign0Fragment();
        FragmentTransaction Transactionsearch = getSupportFragmentManager().beginTransaction();
        Transactionsearch.replace(R.id.loginFrameLayout, loginFragment);
        Transactionsearch.commit();


    }

    public void loginascustomer() {
        Intent intent = new Intent(MainActivity.this, Main_Branch_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void loginasshop() {
        Intent intent = new Intent(MainActivity.this, Main_Branch_ActivityShop.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void toforgotpasswordfragment(View view) {
        ForgotFragment fragment = new ForgotFragment();
        FragmentTransaction Transactionsearch = getSupportFragmentManager().beginTransaction();
        Transactionsearch.replace(R.id.loginFrameLayout, fragment);
        Transactionsearch.commit();
    }

    public int getwidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return width;
    }

    @Override
    public void tofirstFragmentshop() {
        SignUpFragment1Shop loginFragment = new SignUpFragment1Shop();
        FragmentTransaction Transactionsearch = getSupportFragmentManager().beginTransaction();
        Transactionsearch.replace(R.id.loginFrameLayout, loginFragment);
        Transactionsearch.commit();
    }

    @Override
    public void tofirstFragment() {
        SignUpFragment1 loginFragment = new SignUpFragment1();
        FragmentTransaction Transactionsearch = getSupportFragmentManager().beginTransaction();
        Transactionsearch.replace(R.id.loginFrameLayout, loginFragment);
        Transactionsearch.commit();
    }
    private void login() {
        String id=firebaseAuth.getUid();
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        DocumentReference docIdRef = rootRef.collection("Shops").document(id);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: "+e);
                    }
                });
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        loginasshop();

                    } else {
                        loginascustomer();
                        Log.d(TAG, "Document does not exist!");
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });

    }
    public static Boolean isShop(){
        String id= FirebaseAuth.getInstance().getUid();
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        DocumentReference docIdRef = rootRef.collection("Shops").document(id);
        final Boolean[] flag = {null};
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                            flag[0] =true;
                    } else {

                             flag[0]=false;
                        Log.d(TAG, "Document does not exist!");
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });

        return flag[0];
    }

}