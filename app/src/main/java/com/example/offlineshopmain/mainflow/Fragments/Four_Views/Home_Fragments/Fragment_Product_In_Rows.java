package com.example.offlineshopmain.mainflow.Fragments.Four_Views.Home_Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.BackEndServer.HomeBackEnd.FromServerHome;
import com.example.offlineshopmain.backend.BackEndServer.HomeBackEnd.ServerMethodsHome;
import com.example.offlineshopmain.backend.UsedClass.MetaProduct;
import com.example.offlineshopmain.mainflow.Fragments.Four_Views.Home_Fragments.Adapter.Adapter2Rows;
import com.example.offlineshopmain.mainflow.Interface.toProduct;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Fragment_Product_In_Rows extends Fragment implements FromServerHome.Home, Adapter2Rows.NearToEnd {
    toProduct toProduct;
    RecyclerView recyclerViewNotWatched;
    RecyclerView recyclerViewwatched;
    ServerMethodsHome fromServer = FromServerHome.fromServer;
    Adapter2Rows watchedAdapter, notWatchedAdapter;
    private static final String TAG = "Fragment_Product_In_Row";
    View view;
    static int i = 0;

    public Fragment_Product_In_Rows(toProduct context) {
        this.toProduct = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from((Context) toProduct).inflate(R.layout.fragment_2__rows, container, false);
        recyclerViewNotWatched = view.findViewById(R.id.rows2rightrecycleview);
        recyclerViewwatched = view.findViewById(R.id.F2RwatchedRecyclerView);
        watchedAdapter = new Adapter2Rows(getActivity(), this);
        notWatchedAdapter = new Adapter2Rows(getActivity(), this);
        recyclerViewNotWatched.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        recyclerViewwatched.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        recyclerViewwatched.setAdapter(watchedAdapter);
        recyclerViewNotWatched.setAdapter(notWatchedAdapter);
        fromServer.loadwatchedProducts(this, getContext());
        fromServer.loadnotWatchedProducts(this, getContext());
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                watchedAdapter.deleteItem(viewHolder.getAbsoluteAdapterPosition());
            }
        }).attachToRecyclerView(recyclerViewwatched);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                notWatchedAdapter.deleteItem(viewHolder.getAbsoluteAdapterPosition());
            }
        }).attachToRecyclerView(recyclerViewwatched);
        return view;
    }


    @Override
    public void LoadedNotWatched(MetaProduct product) {
        File file = new File(getContext().getExternalCacheDir().getAbsolutePath() + "/" + product.getProduct().getId());
        if (!file.exists()) {
            FileOutputStream outputStream;
            try {
                outputStream = new FileOutputStream(file);
                product.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            } catch (FileNotFoundException e) {
                Log.d(TAG, "saveImage: Exception  " + e.getMessage());
            }
        }
        notWatchedAdapter.setProducts(product);
    }

    @Override
    public void LoadedWatched(MetaProduct metaProduct) {

        File file = new File(getContext().getExternalCacheDir().getAbsolutePath() + "/" + metaProduct.getProduct().getId());
        if (!file.exists()) {
            FileOutputStream outputStream;
            try {
                outputStream = new FileOutputStream(file);
                metaProduct.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            } catch (FileNotFoundException e) {
                Log.d(TAG, "saveImage: Exception  " + e.getMessage());
            }
        }
        watchedAdapter.setProducts(metaProduct);
    }

    @Override
    public void NearToEnd() {

    }
}

