package com.example.offlineshopmain.ShopTrace.Fragments.SignUpFragmentsShop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.offlineshopmain.R;

public class SignUpFragment1Shop extends Fragment implements Sign1FragmentShop.tosign2shop, Sign2FragmentShop.tosign3shop,Sign3FragmentShop.tosign4shop {
    FrameLayout frameLayout;
    View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_fragment1, container, false);
        setin(view);
        return view;
    }

    private void setin(View view) {
        frameLayout=view.findViewById(R.id.signframgmentframelayout);
        tofirstFramgent();
    }

    private void tofirstFramgent() {
        Sign1FragmentShop fragment=new Sign1FragmentShop();
        FragmentTransaction Transactionsearch = getActivity().getSupportFragmentManager().beginTransaction();
        Transactionsearch.replace(R.id.signframgmentframelayout,fragment);
        Transactionsearch.commit();
    }

    @Override
    public void tosecondFragmentshop(Bundle bundle) {
        Sign2FragmentShop fragment=new Sign2FragmentShop();
        fragment.setArguments(bundle);
        FragmentTransaction Transactionsearch = getActivity().getSupportFragmentManager().beginTransaction();
        Transactionsearch.replace(R.id.signframgmentframelayout,fragment);
        Transactionsearch.commit();
    }

    @Override
    public void tothirdFragmentshop(Bundle bundle) {
        Sign3FragmentShop fragment=new Sign3FragmentShop();
        fragment.setArguments(bundle);
        FragmentTransaction Transactionsearch = getActivity().getSupportFragmentManager().beginTransaction();
        Transactionsearch.replace(R.id.signframgmentframelayout,fragment);
        Transactionsearch.commit();
    }

    @Override
    public void tofourthFragmentshop(Bundle bundle) {
        Sign4FragmentShop fragment=new Sign4FragmentShop();
        fragment.setArguments(bundle);
        FragmentTransaction Transactionsearch = getActivity().getSupportFragmentManager().beginTransaction();
        Transactionsearch.replace(R.id.signframgmentframelayout,fragment);
        Transactionsearch.commit();
    }
}
