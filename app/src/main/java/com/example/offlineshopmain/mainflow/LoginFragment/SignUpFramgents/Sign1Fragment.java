package com.example.offlineshopmain.mainflow.LoginFragment.SignUpFramgents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.mainflow.LoginFragment.SignUpFragment1;

public class Sign1Fragment extends Fragment {

    View view;
    EditText firstName, lastName, phoneNumber;
    TextView firstwrong, lastwrong, phonewrong;
    ImageButton continuebtn;
    public interface tosign2{
        public void tosecondFragment(Bundle bundle);
    }

    public static final String KEY_FIRST_NAME="KEY_FIRST_NAME";
    public static final String KEY_LAST_NAME="KEY_LAST_NAME";
    public static final String KEY_PHONE="KEY_PHONE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sign1fragment, container, false);
        setin(view);
        return view;
    }

    private void setin(View view) {
        firstName = view.findViewById(R.id.s1ffisrtname);
        lastName = view.findViewById(R.id.s1flastname);
        phoneNumber = view.findViewById(R.id.s1fphonenumber);
        continuebtn = view.findViewById(R.id.s1fcontinuebtn);
        firstwrong = view.findViewById(R.id.s1fwrotefirstname);
        lastwrong = view.findViewById(R.id.s1fwrotelastname);
        phonewrong = view.findViewById(R.id.s1fwrotephone);
        firstwrong.setVisibility(View.GONE);
        lastwrong.setVisibility(View.GONE);
        phonewrong.setVisibility(View.GONE);
        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnpressed();
            }
        });
    }

    private void btnpressed() {
        if (checkall()) {
            Bundle bundle=new Bundle();
            bundle.putString(KEY_FIRST_NAME,firstName.getText().toString());
            bundle.putString(KEY_LAST_NAME,lastName.getText().toString());
            bundle.putString(KEY_PHONE,phoneNumber.getText().toString());
            SignUpFragment1 parent= (SignUpFragment1) getFragmentManager().getFragments().get(getFragmentManager().getFragments().size()-2);
            ((tosign2)parent).tosecondFragment(bundle);
        }
    }

    private boolean checkall() {
        boolean flag = true;
        if (firstName.getText().toString().equals("")) {
            flag = false;
            firstName.setError("mustn't be empty");
            firstwrong.setVisibility(View.VISIBLE);
            firstName.setError("");
        } else
            firstwrong.setVisibility(View.GONE);
        if (lastName.getText().toString().equals("")) {
            flag = false;
            lastwrong.setVisibility(View.VISIBLE);
            lastName.setError("");
        } else
            lastwrong.setVisibility(View.GONE);
        try {
            String phone = phoneNumber.getText().toString();
            if (!phone.substring(0, 2).equals("01") || phone.length() != 11)
                throw new Exception();
            Long.parseLong(phone.substring(1));
            phonewrong.setVisibility(View.GONE);
        } catch (Exception e) {
            phonewrong.setError("");
            flag = false;
            phonewrong.setVisibility(View.VISIBLE);
        }
        return flag;
    }


}
