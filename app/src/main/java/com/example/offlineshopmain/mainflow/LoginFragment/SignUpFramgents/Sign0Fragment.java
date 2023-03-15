package com.example.offlineshopmain.mainflow.LoginFragment.SignUpFramgents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.offlineshopmain.R;

public class Sign0Fragment  extends Fragment {

    public interface tosign1{
        public void tofirstFragmentshop();
        public void tofirstFragment();
    }


    View view;
    ImageButton continuebtn;
    RadioGroup radioGroup;
    RadioButton customer,seller;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sign0fragment, container, false);
        setin(view);
        return view;
    }

    private void setin(View view) {
        continuebtn=view.findViewById(R.id.s0fcontinuebtn);
        radioGroup=view.findViewById(R.id.s0fradiogroub);
        customer=view.findViewById(R.id.s0fcustomerradio);
        seller=view.findViewById(R.id.s0fsellerradio);
        radioGroup.check(R.id.s0fcustomerradio);
        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                press();
            }
        });
    }

    private void press() {
        if(radioGroup.getCheckedRadioButtonId()==customer.getId()){
            ((tosign1)getActivity()).tofirstFragment();
        }else{
            ((tosign1)getActivity()).tofirstFragmentshop();
        }
    }


}