package com.example.offlineshopmain.ShopTrace.Fragments.ShopProduct_Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.offlineshopmain.R;

public class ShopProuduct_Fragment extends Fragment implements ShopProduct_Fragment1.tosecond_Fragment{

    View view;
    FrameLayout frameLayout;
    Menu menu;
    public ShopProuduct_Fragment(Menu menu) {
    this.menu=menu;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shopproduct_fragment, container, false);
        setin(view);
        menu.getItem(2).setChecked(true);
        return view;
    }

    private void setin(View v) {
        frameLayout=v.findViewById(R.id.spfFragmentLayout);
        toFirstFragment();
    }
    public void toFirstFragment(){
        ShopProduct_Fragment1 fragment=new ShopProduct_Fragment1();
        FragmentTransaction Transactionsearch = getActivity().getSupportFragmentManager().beginTransaction();
        Transactionsearch.replace(R.id.spfFragmentLayout,fragment);
        Transactionsearch.commit();
    }

    @Override
    public void toSecond_Fragment(Bundle bundle) {
        ShopProduct_Fragment2 fragment=new ShopProduct_Fragment2();
        fragment.setArguments(bundle);
        FragmentTransaction Transactionsearch = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null);
        Transactionsearch.replace(R.id.spfFragmentLayout,fragment);
        Transactionsearch.commit();
    }


}


