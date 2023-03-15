package com.example.offlineshopmain.ShopTrace.Fragments.ShopProduct_Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.ShopTrace.Dialogs.Add_Product_Dialog;
import com.example.offlineshopmain.ShopTrace.Fragments.ShopProduct_Fragments.Adapters.AdapterFor2Rows;
import com.example.offlineshopmain.backend.BackEndServer.AddProductBackEnd.FromServerAddProduct;
import com.example.offlineshopmain.backend.BackEndServer.AddProductBackEnd.ServerMethodAddProduct;
import com.example.offlineshopmain.backend.UsedClass.MetaProduct;
import com.example.offlineshopmain.mainflow.Interface.toProduct;

public class ShopProduct_Fragment2 extends Fragment implements FromServerAddProduct.AddProduct {
    public static final String KEY_CATEGORY = "KEY_CATEGORY";
    private static final String TAG = "ShopProduct_Fragment2";
    private RecyclerView recyclerView;
    private View view;
    private ImageView addbtn, backbtn;
    private String nameOfCategory;
    ServerMethodAddProduct fromServer = FromServerAddProduct.fromServer;
    String id = FromServerAddProduct.id_User;
    AdapterFor2Rows adapter;
    public static final String KEY_CATEGORY_Name = "KEY_CATEGORY_Name";
    public static final String Key_Id_User = "Key_Id_User";
    public static final String Key_Abstract_Category_Id = "Key_Abstract_Category_Id";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shopproduct_fragment2, container, false);
        setin(view);
        return view;
    }

    private void setin(View v) {
        recyclerView = v.findViewById(R.id.SPF2recyclerView);
        backbtn = v.findViewById(R.id.spf2backbtn);
        addbtn = v.findViewById(R.id.spf2addproduct);
        Bundle bundle = getArguments();
        nameOfCategory = bundle.getString(KEY_CATEGORY);
        adapter = new AdapterFor2Rows(getContext(), (toProduct) getContext());
        recyclerView.setAdapter(adapter);
        fromServer.getMetaProduct(nameOfCategory,this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addbtnpressed();
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAbsoluteAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
    }


    private void addbtnpressed() {
        Add_Product_Dialog dialogfornewproduct = new Add_Product_Dialog();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CATEGORY_Name, nameOfCategory);
        bundle.putString(Key_Id_User, id);
        bundle.putString(Key_Abstract_Category_Id, nameOfCategory);
        dialogfornewproduct.setArguments(bundle);
        dialogfornewproduct.show(getActivity().getSupportFragmentManager(), "aldfjlksd;jfjkdjf;");
//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void SetMetaProduct(MetaProduct metaProduct) {
        adapter.addItems(metaProduct);
    }
}