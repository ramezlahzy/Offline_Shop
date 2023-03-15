package com.example.offlineshopmain.ShopTrace;

import static com.example.offlineshopmain.mainflow.Fragments.OneCategory.OneCategory_Fragment.Categeroy_Name_KEY;
import static com.example.offlineshopmain.mainflow.Fragments.OneProduct.OneProduct_Fragment.KEY_ONE_PRODUCT;
import static com.example.offlineshopmain.mainflow.Fragments.OneShop.OneShop_Fragment.KEY_SHOP_ID;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.ShopTrace.Fragments.Four_Views.Four_Fragment_ViewShopp;
import com.example.offlineshopmain.mainflow.Fragments.OneCategory.OneCategory_Fragment;
import com.example.offlineshopmain.mainflow.Fragments.OneProduct.OneProduct_Fragment;
import com.example.offlineshopmain.mainflow.Fragments.OneShop.OneShop_Fragment;
import com.example.offlineshopmain.mainflow.Interface.toProduct;

public class Main_Branch_ActivityShop extends AppCompatActivity implements toProduct {

    private static final String TAG = "Main_Branch_ActivitySho";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_main_branch_shop);
        Four_Fragment_ViewShopp fragmentsearch = new Four_Fragment_ViewShopp(this);
        FragmentTransaction Transactionsearch = getSupportFragmentManager().beginTransaction();
        Transactionsearch.replace(R.id.AMBS_frame, fragmentsearch);
        Transactionsearch.commit();
    }


    public void toProduct(String id) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ONE_PRODUCT, id);
        OneProduct_Fragment fragment=new OneProduct_Fragment(this);
        fragment.setArguments(bundle);
        FragmentTransaction Transactionsearch = getSupportFragmentManager().beginTransaction().addToBackStack(null);
        Transactionsearch.replace(R.id.AMBS_frame, fragment);
        Transactionsearch.commit();

    }
    @Override
    public void toShop(String id) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_SHOP_ID, id);
        OneShop_Fragment fragment=new OneShop_Fragment(this);
        fragment.setArguments(bundle);
        FragmentTransaction Transactionsearch = getSupportFragmentManager().beginTransaction().addToBackStack(null);
        Transactionsearch.replace(R.id.AMBS_frame, fragment);
        Transactionsearch.commit();
    }

    @Override
    public void tohome() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        Four_Fragment_ViewShopp fragmentsearch = new Four_Fragment_ViewShopp(this);
        FragmentTransaction Transactionsearch = getSupportFragmentManager().beginTransaction().addToBackStack(null);
        Transactionsearch.replace(R.id.AMBS_frame, fragmentsearch);
        Transactionsearch.commit();
    }
    @Override
    public void toCategory(String id) {
        Bundle bundle = new Bundle();
        bundle.putString(Categeroy_Name_KEY, id);
        OneCategory_Fragment fragment=new OneCategory_Fragment(this);
        fragment.setArguments(bundle);
        FragmentTransaction Transactionsearch = getSupportFragmentManager().beginTransaction().addToBackStack(null);
        Transactionsearch.replace(R.id.AMBS_frame, fragment).addToBackStack(null);
        Transactionsearch.commit();
    }

}