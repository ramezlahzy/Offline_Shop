package com.example.offlineshopmain.ShopTrace.Fragments.SignUpFragmentsShop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.offlineshopmain.R;

public class Sign1FragmentShop extends Fragment {
    public interface tosign2shop{
        public void tosecondFragmentshop(Bundle bundle);
    }
    public static final String KEY_PHONE_SHOP = "KEY_PHONE_SHOP";
    public static final String KEY_NAME_SHOP = "KEY_SHOP_NAME";
    ImageButton continuebtn;
    EditText phone,name;
    TextView wrongphone,wrongname;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sign1fragmentshop, container, false);
        setin(view);
        return view;
    }

    private void setin(View view) {
           continuebtn=view.findViewById(R.id.s1fscontinuebtn);
           phone=view.findViewById(R.id.s1fsphonenumber);
           name=view.findViewById(R.id.s1fsshopname);
           wrongname=view.findViewById(R.id.s1fswrongshopname);
           wrongphone=view.findViewById(R.id.s1fswrongphone);
           continuebtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   press();
               }
           });

    }

    private void press() {
        if (checkall()){
            Bundle bundle=new Bundle();
            bundle.putString(KEY_NAME_SHOP,name.getText().toString());
            bundle.putString(KEY_PHONE_SHOP,phone.getText().toString());
            SignUpFragment1Shop parent= (SignUpFragment1Shop) getFragmentManager().getFragments().get(getFragmentManager().getFragments().size()-2);
            ((Sign1FragmentShop.tosign2shop)parent).tosecondFragmentshop(bundle);
        }
    }

    private boolean checkall() {
        boolean flag = true;
        if (name.getText().toString().equals("")) {
            flag = false;
            wrongname.setVisibility(View.VISIBLE);
        } else
            wrongname.setVisibility(View.GONE);
        try {
            String phonenumber = phone.getText().toString();
            if (!phonenumber.substring(0, 2).equals("01") || phonenumber.length() != 11)
                throw new Exception();
            Long.parseLong(phonenumber.substring(1));
            wrongphone.setVisibility(View.GONE);
        } catch (Exception e) {
            wrongname.setError("");
            flag = false;
            wrongphone.setVisibility(View.VISIBLE);
        }
        return flag;
    }


}
