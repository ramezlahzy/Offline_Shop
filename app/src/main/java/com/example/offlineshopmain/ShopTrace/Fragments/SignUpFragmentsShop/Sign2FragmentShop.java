package com.example.offlineshopmain.ShopTrace.Fragments.SignUpFragmentsShop;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.offlineshopmain.R;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Sign2FragmentShop extends Fragment {

    public interface tosign3shop {
        public void tothirdFragmentshop(Bundle bundle);
    }


    public static final String KEY_EMAIL_SHOP = "KEY_EMAIL_SHOP";
    public static final String KEY_PASSWORD_SHOP = "KEY_PASSWORD_SHOP";
    EditText password, email;
    TextView passwordwrong, emailwrong;
    ImageButton continuebtn;
    ProgressBar bar;
    View view;

    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sign2fragment, container, false);
        setin(view);
        new BeginSignInRequest.Builder().setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId("686038531771-53db9f3b4rk5a3v21v3s2cu6dhbo4m0a.apps.googleusercontent.com")
                .setFilterByAuthorizedAccounts(true)
                .build());

        return view;
    }

    private void setin(View view) {
        mAuth = FirebaseAuth.getInstance();
        password = view.findViewById(R.id.s2fpassword);
        email = view.findViewById(R.id.s2femail);
        passwordwrong = view.findViewById(R.id.s2fwrongpassword);
        emailwrong = view.findViewById(R.id.s2fwrongemail);
        continuebtn = view.findViewById(R.id.s2fcontinuebtn);
        bar = view.findViewById(R.id.s2fprogressbar);
        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressed();
            }
        });
    }

    private void pressed() {
        if (checkall()) {

            bar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            bar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                SignUpFragment1Shop parent = (SignUpFragment1Shop) getFragmentManager().getFragments().get(getFragmentManager().getFragments().size() - 2);
                                Bundle bundle=getArguments();
                                bundle.putString(KEY_EMAIL_SHOP,email.getText().toString());
                                bundle.putString(KEY_PASSWORD_SHOP,password.getText().toString());
                                ((Sign2FragmentShop.tosign3shop) parent).tothirdFragmentshop(bundle);
                                Toast.makeText(getActivity(), "User SignIn Successfuly", Toast.LENGTH_SHORT).show();
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(getActivity(), "You are already registered", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }
    }

    private boolean checkall() {
        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            emailwrong.setVisibility(View.VISIBLE);
            email.setError("wrong email");
            return false;
        } else {
            email.setError(null);
            emailwrong.setVisibility(View.INVISIBLE);
        }
        if (password.getText().toString().trim().length() < 8) {
            passwordwrong.setVisibility(View.VISIBLE);
            password.setError("wrong password");
            return false;
        } else {
            password.setError(null);
            passwordwrong.setVisibility(View.INVISIBLE);
        }
        return true;
    }
}
