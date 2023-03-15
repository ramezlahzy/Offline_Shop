package com.example.offlineshopmain.ShopTrace.Fragments.ShopProduct_Fragments.Adapters;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.UsedClass.Abstract_Category;
import com.example.offlineshopmain.backend.UsedClass.Category_For_User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_Abstract_Categories_FireStore extends FirestoreRecyclerAdapter<Abstract_Category, Adapter_Abstract_Categories_FireStore.ViewHolder> {


    private static final String TAG = "Adapter_Abstract_Catego";



    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public Adapter_Abstract_Categories_FireStore(@NonNull FirestoreRecyclerOptions<Abstract_Category> options) {
        super(options);
    }

    FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Abstract_Category model) {
        Picasso.with(holder.context).load(model.getPhotoURL())
                .fit()
                .centerInside()
//                .centerCrop()
                .into(holder.imageView);
        holder.name.setText(model.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    press(model.getId(), model.getName(), model.getPhotoURL(), holder.context);
            }
        });

    }

    private void press(String id_Abstract_Category, String name_Of, String ImageUrl, Context context) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore fs = FirebaseFirestore.getInstance();
        String id = mAuth.getUid();
        Category_For_User category = new Category_For_User(id_Abstract_Category, new ArrayList<>(), name_Of, ImageUrl,auth.getUid());


        DocumentReference docIdRef = fs.collection("Shops").document(id).collection("Category_For_Users").document(name_Of);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(context, "This Category is already exist", Toast.LENGTH_SHORT).show();
                    } else {

                        Category_For_User category1 = new Category_For_User(id_Abstract_Category, new ArrayList<>(), name_Of, ImageUrl,auth.getUid());
                        fs.collection("Shops").document(id).collection("Category_For_Users").document(category.getName_Of_Category()).set(category)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, task.isSuccessful() + "", Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_abstract_show, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Context context;
        TextView name;
        ImageView imageView;
        RelativeLayout cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            name = itemView.findViewById(R.id.CACprice);
            imageView = itemView.findViewById(R.id.CACimage);
            cardView = itemView.findViewById(R.id.onecategcard);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            Log.d(TAG, "ViewHolder: " + context);
        }
    }
}
