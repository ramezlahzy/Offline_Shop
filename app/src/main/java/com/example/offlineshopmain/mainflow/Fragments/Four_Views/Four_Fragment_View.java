package com.example.offlineshopmain.mainflow.Fragments.Four_Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.mainflow.Fragments.Four_Views.Home_Fragments.Home_Fragment;
import com.example.offlineshopmain.mainflow.Fragments.Four_Views.Profile_Fragments.Profile_Fragment;
import com.example.offlineshopmain.mainflow.Fragments.Four_Views.Search_Fragments.Search_Fragment;
import com.example.offlineshopmain.mainflow.Fragments.Four_Views.Shops_Fragments.Shops_Fragment;
import com.example.offlineshopmain.mainflow.Interface.toProduct;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Four_Fragment_View extends Fragment  {


    View view;
    private BottomNavigationView bottomnavigationView;
    FrameLayout frameLayout;
    toProduct tproduct;

    public Four_Fragment_View(toProduct tproduct) {
        this.tproduct = tproduct;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main_product_view, container, false);
        setin();

        return view;
    }

    private void setin() {
        bottomnavigationView = view.findViewById(R.id.Main_Navigation_Bottom);
        frameLayout = view.findViewById(R.id.AMPVcontainer);
        setBottomnavigation();
        sethome();
    }


    private void sethome() {
        FragmentTransaction Transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Home_Fragment fragment = new Home_Fragment(tproduct, bottomnavigationView.getMenu());
        Transaction.replace(R.id.AMPVcontainer, fragment);
        Transaction.commit();

    }

    private void setsearch() {
        Search_Fragment fragmentsearch = new Search_Fragment(bottomnavigationView.getMenu());
        FragmentTransaction Transactionsearch = getActivity().getSupportFragmentManager().beginTransaction();
        Transactionsearch.replace(R.id.AMPVcontainer, fragmentsearch).addToBackStack(null);
        Transactionsearch.commit();
    }
    private void setLisked() {
        Profile_Fragment fragmentsearch = new Profile_Fragment(bottomnavigationView.getMenu());
        FragmentTransaction Transactionsearch = getActivity().getSupportFragmentManager().beginTransaction();
        Transactionsearch.replace(R.id.AMPVcontainer, fragmentsearch).addToBackStack(null);
        Transactionsearch.commit();
    }
    public void setShops(){
        Shops_Fragment fragment = new Shops_Fragment(bottomnavigationView.getMenu());
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null);
        transaction.replace(R.id.AMPVcontainer, fragment);
        transaction.commit();
    }
    public void setBottomnavigation() {
        //TODO: 8/28/2022
        bottomnavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_Main_navigation:
                        sethome();
                        break;
                    case R.id.search_Main_Navigation:
                        setsearch();
                        break;
                    case R.id.liked_Main_Navigation:
                        setLisked();
                        break;
                    case R.id.shops_Main_Navigation:
                        setShops();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    }

    public void clearbackstack() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }





}