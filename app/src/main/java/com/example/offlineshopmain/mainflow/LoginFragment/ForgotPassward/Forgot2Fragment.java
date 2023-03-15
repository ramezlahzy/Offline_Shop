package com.example.offlineshopmain.mainflow.LoginFragment.ForgotPassward;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.offlineshopmain.R;

public class Forgot2Fragment extends Fragment {
    public interface tofirst {
        public void tofirst();
    }


    private EditText inputCode1, inputCode2, inputCode3, inputCode4, inputCode5, inputCode6;
    private TextView resend;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forgot2fragment, container, false);
        setin(view);
        return view;
    }

    private void setin(View view) {
        inputCode1 = view.findViewById(R.id.inputcode1);
        inputCode2 = view.findViewById(R.id.inputcode2);
        inputCode3 = view.findViewById(R.id.inputcode3);
        inputCode4 = view.findViewById(R.id.inputcode4);
        inputCode5 = view.findViewById(R.id.inputcode5);
        inputCode6 = view.findViewById(R.id.inputcode6);
        resend = view.findViewById(R.id.textResendOTP);
        setresend();
        setupOTPInputs();
    }

    private void setresend() {
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotFragment parent = (ForgotFragment) getFragmentManager().getFragments().get(getFragmentManager().getFragments().size() - 2);
                ((tofirst) parent).tofirst();
            }
        });

    }

    private void setupOTPInputs() {
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                if (!s.toString().trim().isEmpty())
                    inputCode2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                if (!s.toString().trim().isEmpty())
                    inputCode3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                if (!s.toString().trim().isEmpty())
                    inputCode4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                if (!s.toString().trim().isEmpty())
                    inputCode5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                if (!s.toString().trim().isEmpty())
                    inputCode6.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                if (!s.toString().trim().isEmpty())
                    verify();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void verify() {
        //TODO
        Toast.makeText(getActivity(), "Verified", Toast.LENGTH_SHORT).show();
    }

}
