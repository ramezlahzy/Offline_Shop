package com.example.offlineshopmain.mainflow.LoginFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.mainflow.LoginFragment.SignUpFramgents.Sign1Fragment;
import com.example.offlineshopmain.mainflow.LoginFragment.SignUpFramgents.Sign2Fragment;
import com.example.offlineshopmain.mainflow.LoginFragment.SignUpFramgents.Sign3Fragment;

public class SignUpFragment1 extends Fragment implements Sign1Fragment.tosign2,Sign2Fragment.tosign3{
    FrameLayout frameLayout;
    View view;
    @Override
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
        Sign1Fragment fragment=new Sign1Fragment();
        FragmentTransaction Transactionsearch = getActivity().getSupportFragmentManager().beginTransaction();
        Transactionsearch.replace(R.id.signframgmentframelayout,fragment);
        Transactionsearch.commit();
    }
    public void tosecondFragment(Bundle bundle){
        Sign2Fragment fragment=new Sign2Fragment();
        fragment.setArguments(bundle);
        FragmentTransaction Transactionsearch = getActivity().getSupportFragmentManager().beginTransaction();
        Transactionsearch.replace(R.id.signframgmentframelayout,fragment);
        Transactionsearch.commit();
    }

    @Override
    public void tothirdFragment(Bundle bundle) {
        Sign3Fragment fragment=new Sign3Fragment();
        fragment.setArguments(bundle);
        FragmentTransaction Transactionsearch = getActivity().getSupportFragmentManager().beginTransaction();
        Transactionsearch.replace(R.id.signframgmentframelayout,fragment);
        Transactionsearch.commit();
    }
}
