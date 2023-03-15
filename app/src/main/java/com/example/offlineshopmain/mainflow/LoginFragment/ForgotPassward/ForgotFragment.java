package com.example.offlineshopmain.mainflow.LoginFragment.ForgotPassward;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.offlineshopmain.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotFragment extends Fragment implements Forgot1Fragment.tosecond,Forgot2Fragment.tofirst {


    View view;
    public FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FrameLayout frameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fortgotfragment, container, false);
        setin(view);
        return view;
    }

    private void setin(View view) {
        frameLayout=view.findViewById(R.id.forgotframgmentframelayout);
        tofirst();
    }

    public void tofirst() {
        Forgot1Fragment fragment=new Forgot1Fragment();
        FragmentTransaction Transactionsearch = getActivity().getSupportFragmentManager().beginTransaction();
        Transactionsearch.replace(R.id.forgotframgmentframelayout,fragment);
        Transactionsearch.commit();
    }
    public void settosecond() {
        Forgot2Fragment fragment=new Forgot2Fragment();
        FragmentTransaction Transactionsearch = getActivity().getSupportFragmentManager().beginTransaction();
        Transactionsearch.replace(R.id.forgotframgmentframelayout,fragment);
        Transactionsearch.commit();
    }
}
