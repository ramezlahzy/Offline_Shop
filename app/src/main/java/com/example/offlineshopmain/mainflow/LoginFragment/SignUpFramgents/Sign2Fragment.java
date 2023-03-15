package com.example.offlineshopmain.mainflow.LoginFragment.SignUpFramgents;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.mainflow.LoginFragment.ForgotPassward.Forgot2Fragment;
import com.example.offlineshopmain.mainflow.LoginFragment.ForgotPassward.ForgotFragment;
import com.google.firebase.auth.FirebaseAuth;

public class Sign2Fragment extends Fragment {

    public interface tosign3 {
        public void tothirdFragment(Bundle bundle);
    }


    public static final String KEY_EMAIL = "KEY_EMAIL";
    public static final String KEY_PASSWORD = "KEY_PASSWORD";
    EditText password, email;
    TextView passwordwrong, emailwrong;
    ImageButton continuebtn;
    ProgressBar bar;
    View view;



    private EditText inputCode1, inputCode2, inputCode3, inputCode4, inputCode5, inputCode6;
    private TextView resend;

    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sign2fragment, container, false);
//        setin(view);
//        new BeginSignInRequest.Builder().setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                .setSupported(true)
//                .setServerClientId("686038531771-53db9f3b4rk5a3v21v3s2cu6dhbo4m0a.apps.googleusercontent.com")
//                .setFilterByAuthorizedAccounts(true)
//                .build());

        inputCode1 = view.findViewById(R.id.inputcode1);
        inputCode2 = view.findViewById(R.id.inputcode2);
        inputCode3 = view.findViewById(R.id.inputcode3);
        inputCode4 = view.findViewById(R.id.inputcode4);
        inputCode5 = view.findViewById(R.id.inputcode5);
        inputCode6 = view.findViewById(R.id.inputcode6);
        resend = view.findViewById(R.id.textResendOTP);
        setresend();
        setupOTPInputs();
        return view;
    }

    private void setresend() {
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotFragment parent = (ForgotFragment) getFragmentManager().getFragments().get(getFragmentManager().getFragments().size() - 2);
                ((Forgot2Fragment.tofirst) parent).tofirst();
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

//    private void setin(View view) {
//        mAuth = FirebaseAuth.getInstance();
//        password = view.findViewById(R.id.s2fpassword);
//        email = view.findViewById(R.id.s2femail);
//        passwordwrong = view.findViewById(R.id.s2fwrongpassword);
//        emailwrong = view.findViewById(R.id.s2fwrongemail);
//        continuebtn = view.findViewById(R.id.s2fcontinuebtn);
//        bar = view.findViewById(R.id.s2fprogressbar);
//        continuebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pressed();
//            }
//        });
//    }
//
//    private void pressed() {
//        if (checkall()) {
//
//            bar.setVisibility(View.VISIBLE);
//            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
//                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            bar.setVisibility(View.GONE);
//                            if (task.isSuccessful()) {
//                                SignUpFragment1 parent = (SignUpFragment1) getFragmentManager().getFragments().get(getFragmentManager().getFragments().size() - 2);
//                                Bundle bundle=getArguments();
//                                bundle.putString(KEY_EMAIL,email.getText().toString());
//                                bundle.putString(KEY_PASSWORD,password.getText().toString());
//                                ((Sign2Fragment.tosign3) parent).tothirdFragment(bundle);
//                                Toast.makeText(getActivity(), "User SignIn Successfuly", Toast.LENGTH_SHORT).show();
//                            } else {
//                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
//                                    Toast.makeText(getActivity(), "You are already registered", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }
//                    });
//        }
//    }
//
//    private boolean checkall() {
//        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
//            emailwrong.setVisibility(View.VISIBLE);
//            email.setError("wrong email");
//            return false;
//        } else {
//            email.setError(null);
//            emailwrong.setVisibility(View.INVISIBLE);
//        }
//        if (password.getText().toString().trim().length() < 8) {
//            passwordwrong.setVisibility(View.VISIBLE);
//            password.setError("wrong password");
//            return false;
//        } else {
//            password.setError(null);
//            passwordwrong.setVisibility(View.INVISIBLE);
//        }
//        return true;
//    }
