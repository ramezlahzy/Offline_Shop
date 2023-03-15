package com.example.offlineshopmain.mainflow.LoginFragment;

import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginFragment extends Fragment {


    private static final String TAG = "LoginFragment";
    View view;
    public FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    EditText email, password;
    TextView wrongtext;
    ProgressBar bar;
    ImageButton login,guestlogin;
    FirebaseFirestore fs=FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_fragment, container, false);
        setin(view);
        return view;
    }

    private void setin(View view) {
        email = view.findViewById(R.id.loginemail);
        password = view.findViewById(R.id.loginpassword);
        login = view.findViewById(R.id.loginbutton);
        wrongtext = view.findViewById(R.id.loginwrongtext);
        bar = view.findViewById(R.id.lfprogressbar);
        guestlogin=view.findViewById(R.id.loginguestbutton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log();
            }
        });
        guestlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signInWithEmailAndPassword("banatel@gmail.com", "12345678")
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                bar.setVisibility(View.VISIBLE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    wrongtext.setVisibility(View.INVISIBLE);
                                    login();
                                }
                            }
                        });
            }
        });

    }

    private void log() {
        String emailstring = email.getText().toString();
        String pass = password.getText().toString();
        if (email.equals("") || pass.equals("")) {
            wrongtext.setVisibility(View.VISIBLE);
        } else {
            bar.setVisibility(View.VISIBLE);
            firebaseAuth.signInWithEmailAndPassword(emailstring, pass)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            bar.setVisibility(View.VISIBLE);
                            if (!task.isSuccessful()) {
//                                wrongtext.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                wrongtext.setVisibility(View.INVISIBLE);
                                login();

                            }
                        }
                    });
        }

    }

    private void login() {
        String id=firebaseAuth.getUid();
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        DocumentReference docIdRef = rootRef.collection("Shops").document(id);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                        Log.d(TAG, "login Shop: "+fs.collection("Shops").document(id));
                        ((MainActivity)getActivity()).loginasshop();

                    } else {
                        ((MainActivity)getActivity()).loginascustomer();
                        Log.d(TAG, "login Customer: "+fs.collection("Current_Users").document(id));
                        Log.d(TAG, "Document does not exist!");
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });

           }


}
