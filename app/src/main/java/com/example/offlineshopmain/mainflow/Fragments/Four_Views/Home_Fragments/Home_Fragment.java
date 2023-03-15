package com.example.offlineshopmain.mainflow.Fragments.Four_Views.Home_Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.mainflow.Interface.toProduct;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Home_Fragment extends Fragment {
//    private DrawerLayout drawerLayout;
//    private NavigationView navigationView;
//    private Toolbar toolbar;
    View view;
    private static final String TAG = "Home_Fragment";
    toProduct tproduct;



    Menu menu;
    public Home_Fragment(toProduct context, Menu menuView) {
        this.menu = menuView;
        menuView.getItem(0).setChecked(true);
        this.tproduct = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);
//        setin();
        menu.getItem(0).setChecked(true);
        Fragment_Product_In_Rows fragment1 = new Fragment_Product_In_Rows(tproduct);
        FragmentTransaction Transaction1 = ((FragmentActivity) tproduct).getSupportFragmentManager().beginTransaction();
        Transaction1.replace(R.id.containerhome2, fragment1);
        Transaction1.commit();

        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView adView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        return view;
    }


    private void setin() {
//        drawerLayout = view.findViewById(R.id.drawer);
//        navigationView = view.findViewById(R.id.navigationView);
//        toolbar = view.findViewById(R.id.toolbar);
//        setnavigation();
    }

    @Override
    public void onStart() {
        super.onStart();
//        Fragment_Product_In_Rows fragment = new Fragment_Product_In_Rows(tproduct);
//        FragmentTransaction Transaction = ((FragmentActivity) tproduct).getSupportFragmentManager().beginTransaction();
//        Transaction.replace(R.id.containerhome2, fragment);
//        Transaction.commit();
    }
//
//
//    private void setnavigation() {
////        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.draweropen, R.string.drawerclosed);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//        Log.d(TAG, "setnavigation: " + navigationView);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case (R.id.cart):
//                        Toast.makeText(getActivity(), "You select cart", Toast.LENGTH_SHORT).show();
//                        break;
//                    case (R.id.terms):
//                        Toast.makeText(getActivity(), "You select terms", Toast.LENGTH_SHORT).show();
//                        break;
//                    case (R.id.categories):
//                        Toast.makeText(getActivity(), "You select categories", Toast.LENGTH_SHORT).show();
//                        break;
//                    case (R.id.aboutus):
//                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                        builder.setTitle("OFFLINE SHOPE");
//                        builder.setMessage("this app developed by brilliant GUCIAN studnt \n RAMEZ NASHAAT LAHZY \n Do you need visit his website");
//                        builder.setPositiveButton("VISITE", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Intent intent = new Intent(getActivity(), mywebsite.class);
//                                intent.putExtra("URL", "https://www.facebook.com/ramez.nashaat.58");
//                                startActivity(intent);
//                            }
//                        });
//                        builder.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        });
//                        builder.show();
//                        break;
//                    case (R.id.licences):
//                        Toast.makeText(getActivity(), "You select licences", Toast.LENGTH_SHORT).show();
//                        break;
//                    case (R.id.cash):
////                        Cash_Fragment_1 fragment=new Cash_Fragment_1();
////                        FragmentTransaction Transaction = getActivity().getSupportFragmentManager().beginTransaction();
////                        Transaction.replace(R.id.containerhome, fragment, null).addToBackStack(null);
////                        Transaction.commit();
//                }
//                return true;
//            }
//        });
//    }


}
