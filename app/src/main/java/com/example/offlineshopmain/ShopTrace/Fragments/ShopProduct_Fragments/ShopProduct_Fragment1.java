package com.example.offlineshopmain.ShopTrace.Fragments.ShopProduct_Fragments;

import static com.example.offlineshopmain.ShopTrace.Fragments.ShopProduct_Fragments.ShopProduct_Fragment2.KEY_CATEGORY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.ShopTrace.Fragments.ShopProduct_Fragments.Adapters.Adapter_Shop_Categories;
import com.example.offlineshopmain.ShopTrace.Dialogs.Bottom_Categories_Dialog;
import com.example.offlineshopmain.backend.UsedClass.Category_For_User;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ShopProduct_Fragment1 extends Fragment implements Adapter_Shop_Categories.ShowBottom {
    RecyclerView recyclerView;
    View view;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fs = FirebaseFirestore.getInstance();

    public interface tosecond_Fragment{
        public void toSecond_Fragment(Bundle bundle);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shopproduct_fragment1, container, false);
        setin(view);
        return view;
    }

    private void setin(View v) {
        recyclerView=v.findViewById(R.id.spf1recyclerView);
        mAuth=FirebaseAuth.getInstance();
        String id=mAuth.getUid();
        CollectionReference productsreference=fs.collection("Shops").document(id).collection("Category_For_Users");
        Query query = productsreference.orderBy("id", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Category_For_User> options = new FirestoreRecyclerOptions.Builder<Category_For_User>()
                .setQuery(query, Category_For_User.class)
                .setLifecycleOwner(this)
                .build();
        Adapter_Shop_Categories categories=new Adapter_Shop_Categories(options,this);
        recyclerView.setAdapter(categories);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
    }

    @Override
    public void showbottom() {
        Bottom_Categories_Dialog dialog=new Bottom_Categories_Dialog(getActivity(), R.style.BottomSheetDialogTheme);
        dialog.setContentView(null);
        dialog.show();
    }

    @Override
    public void gotosecond(String name_Of_Category) {
        ShopProuduct_Fragment parent= (ShopProuduct_Fragment) getFragmentManager().getFragments().get(getFragmentManager().getFragments().size()-2);
        Bundle bundle=new Bundle();
        bundle.putString(KEY_CATEGORY,name_Of_Category);
        ((tosecond_Fragment)parent).toSecond_Fragment(bundle);
    }

}


