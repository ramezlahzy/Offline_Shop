package com.example.offlineshopmain.ShopTrace.Fragments.Four_Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.ShopTrace.Fragments.ShopProduct_Fragments.ShopProuduct_Fragment;
import com.example.offlineshopmain.mainflow.Fragments.Four_Views.Home_Fragments.Home_Fragment;
import com.example.offlineshopmain.mainflow.Fragments.Four_Views.Profile_Fragments.Profile_Fragment;
import com.example.offlineshopmain.mainflow.Fragments.Four_Views.Search_Fragments.Search_Fragment;
import com.example.offlineshopmain.mainflow.Fragments.Four_Views.Shops_Fragments.Shops_Fragment;
import com.example.offlineshopmain.mainflow.Interface.toProduct;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class Four_Fragment_ViewShopp extends Fragment {


    private BottomNavigationView bottomnavigationView;
    FrameLayout frameLayout;
    FirebaseAuth mAuth;
    toProduct tproduct;
    private Menu menu;
    View view;

    public Four_Fragment_ViewShopp(toProduct tproduct) {
        this.tproduct = tproduct;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.main_view_shop, container, false);
            bottomnavigationView = view.findViewById(R.id.Main_Navigation_Bottom);
            bottomnavigationView = view.findViewById(R.id.Main_Navigation_Bottom);
            frameLayout = view.findViewById(R.id.AMPVcontainer);
            mAuth = FirebaseAuth.getInstance();
            setBottomnavigation();
            sethome();
        }
        return view;
    }


    private void sethome() {
        FragmentTransaction Transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Home_Fragment fragment = new Home_Fragment(tproduct, bottomnavigationView.getMenu());
        Transaction.replace(R.id.AMPVcontainer, fragment);
        Transaction.commit();
//        Fragment_Product_In_Rows fragment1 = new Fragment_Product_In_Rows(tproduct);
//        FragmentTransaction Transaction1 = ((FragmentActivity) tproduct).getSupportFragmentManager().beginTransaction();
//        Transaction1.replace(R.id.containerhome2, fragment1);
//        Transaction1.commit();
    }

    private void setadd() {
        ShopProuduct_Fragment fragment = new ShopProuduct_Fragment(bottomnavigationView.getMenu());
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null);
        transaction.replace(R.id.AMPVcontainer, fragment);
        transaction.commit();
    }

    private void setsearch() {
        Search_Fragment fragmentsearch = new Search_Fragment(bottomnavigationView.getMenu());
        FragmentTransaction Transactionsearch = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null);
        Transactionsearch.replace(R.id.AMPVcontainer, fragmentsearch);
        Transactionsearch.commit();
    }

    private void setLisked() {
        Profile_Fragment fragmentsearch = new Profile_Fragment(bottomnavigationView.getMenu());
        FragmentTransaction Transactionsearch = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null);
        Transactionsearch.replace(R.id.AMPVcontainer, fragmentsearch).addToBackStack(null);
        Transactionsearch.commit();
    }

    public void setShops() {
        Shops_Fragment fragment = new Shops_Fragment(bottomnavigationView.getMenu());
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null);
        transaction.replace(R.id.AMPVcontainer, fragment);
        transaction.commit();
    }

    public void setBottomnavigation() {
        bottomnavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_main_shop:
                        sethome();
                        break;
                    case R.id.search_main_shop:
                        setsearch();
                        break;
                    case R.id.categories_main_shop:
                        setadd();
                        break;
                    case R.id.liked_Main_Navigation:
                        setLisked();
                        break;
                    case R.id.profile_main_shop:
                        setShops();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    }


}


