package com.example.offlineshopmain.mainflow.Fragments.Four_Views.Home_Fragments.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.NewAllData;
import com.example.offlineshopmain.backend.UsedClass.Product;
import com.example.offlineshopmain.mainflow.Interface.toProduct;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class Adapter2RowsFireStore extends FirestoreRecyclerAdapter<Product, Adapter2RowsFireStore.ViewHolder> {

    private static final String TAG = "Adapter2RowsFireStore";
    private Context context;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    String id_User =FirebaseAuth.getInstance().getUid();;
    Boolean isShop=null;
    FirebaseFirestore fs=FirebaseFirestore.getInstance();
    FragmentActivity activityCompat;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     * @param activityCompat
     */
    public Adapter2RowsFireStore(@NonNull FirestoreRecyclerOptions<Product> options, FragmentActivity activityCompat) {
        super(options);
        this.activityCompat=activityCompat;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Product product) {
        holder.price.setText(product.getPrice() + " L.E");
        Log.d(TAG, "onBindViewHolder: " + product.getImageURL());
        Glide.with(holder.context).load(product.getImageURL())
                .into(holder.imageView);
        holder.id_For_Product=product.getId();
        Log.d(TAG, "onBindViewHolder: "+product.getImageURL());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((toProduct)activityCompat).toProduct(product.getId());
            }
        });
    }
    public void deleteItem(int position){
      getSnapshots().getSnapshot(position).getReference().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    NewAllData.deleteProduct(task.getResult().toObject(Product.class));
                }
            }
        });
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rows2card, parent, false);
        context=parent.getContext();
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public CardView parent;
        public ImageButton send;
        public TextView price;
        public Context context;
        public int id;
        public String id_For_Product;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.R2Cimage);
            parent = itemView.findViewById(R.id.R2Ccard);
            send = itemView.findViewById(R.id.R2Csend);
//            price = itemView.findViewById(R.id.R2Cprice);
            id = itemView.getId();
            context = itemView.getContext();
        }

    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
//        fs.collection("Products")
//                        .document(holder.id_For_Product).update("watched_Users_id",FieldValue.arrayUnion(id_User));
        getIfShop(holder.id_For_Product);
    }

    private void getIfShop(String Id_Product) {
        if (isShop!=null) {
            addtouserwatched(isShop,Id_Product);
            return;
        }
        DocumentReference docIdRef = fs.collection("Shops").document(id_User);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        addtouserwatched(true,Id_Product);
                        isShop=true;
                    } else {
                        addtouserwatched(false,Id_Product);
                        isShop=false;
                        Log.d(TAG, "Document does not exist!");
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });



    }

    private void addtouserwatched(boolean isShop,String Id_Proudct) {
        if (isShop) {
            fs.collection("Shops").document(id_User).update("watched_list_ids", FieldValue.arrayUnion(Id_Proudct));
        } else {
            fs.collection("Current_Users").document(id_User).update("watched_list_ids", FieldValue.arrayUnion(Id_Proudct));
        }
    }
}
