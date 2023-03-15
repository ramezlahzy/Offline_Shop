package com.example.offlineshopmain.mainflow.Fragments.Four_Views.Search_Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.UsedClass.Abstract_Category;
import com.example.offlineshopmain.mainflow.Fragments.Four_Views.Search_Fragments.Adapters.Adapter_Search_Category;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class Fragment_All_Categories extends Fragment {

    RecyclerView recyclerView;
    View view;
    private static final String TAG = "Fragment_All_Categories";
    FirebaseFirestore fs=FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment__all__categories, container, false);
        setin();
        return view;
    }

    private void setin() {
        recyclerView = view.findViewById(R.id.FACrecycleview);
        CollectionReference productsreference=fs.collection("Abstract_Categories");
        Query query = productsreference.orderBy("id", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Abstract_Category> options = new FirestoreRecyclerOptions.Builder<Abstract_Category>()
                .setQuery(query, Abstract_Category.class)
                .setLifecycleOwner(this)
                .build();
        Adapter_Search_Category categories=new Adapter_Search_Category(options,getActivity());
        recyclerView.setAdapter(categories);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));

    }


}