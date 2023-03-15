package com.example.offlineshopmain.mainflow.Fragments.OneProduct.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.UsedClass.Current_User;
import com.example.offlineshopmain.backend.UsedClass.Reply;
import com.example.offlineshopmain.backend.UsedClass.Review;
import com.example.offlineshopmain.backend.UsedClass.Shop;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

public class Reviews_Adapter extends FirestoreRecyclerAdapter<Review, Reviews_Adapter.ViewHolder> {


    Context context;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Reviews_Adapter(@NonNull FirestoreRecyclerOptions<Review> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Review review) {
        FirebaseFirestore fs=FirebaseFirestore.getInstance();
        fs.collection("Products").document(review.getId_Product()).collection("Reviews")
                .document(review.getId()).collection("Replies").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                       holder.repliesNumber.setText( queryDocumentSnapshots.getDocumentChanges().size() +" Replies");
                    }
                })      ;


        Query query = fs.collection("Products").document(review.getId_Product()).collection("Reviews")
                .document(review.getId()).collection("Replies");
        FirestoreRecyclerOptions<Reply> options = new FirestoreRecyclerOptions.Builder<Reply>()
                .setQuery(query, Reply.class)
//                .setLifecycleOwner((LifecycleOwner) context)
                .build();
        Replies_Adapter adapter=new Replies_Adapter(options);
        holder.replies_RecyclerView.setAdapter(adapter);
        holder.replies_RecyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter.startListening();


        holder.sentText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String reply= holder.reply_User.getText().toString();
               if (!reply.equals("")){
                   fs.collection("Shops").document(review.getId_Owner_User()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                       @Override
                       public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                           if (task.isSuccessful()){
                               if (task.getResult().exists()){
                                   Shop shop=task.getResult().toObject(Shop.class);
                                   fs.collection("Products").document(review.getId_Product()).collection("Reviews")
                                           .document(review.getId()).collection("Replies").add(new Reply(reply,shop.getName()));
                               }
                               else{
                                   fs.collection("Current_Users").document(review.getId_Owner_User()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                       @Override
                                       public void onSuccess(DocumentSnapshot documentSnapshot) {

                                           if (documentSnapshot.exists()){
                                               Current_User current_user=documentSnapshot.toObject(Current_User.class);
                                               fs.collection("Products").document(review.getId_Product()).collection("Reviews")
                                                       .document(review.getId()).collection("Replies").add(new Reply(reply,current_user.getFirst_name()));

                                           }
                                       }
                                   });
                                   }
                           }
                       }
                   });

               }
            }
        });

        String id = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        DocumentReference docIdRef = rootRef.collection("Shops").document(review.getId_Owner_User());
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Shop shop=document.toObject(Shop.class);
                        holder.userName.setText(shop.getName());
                        Picasso.with(context).load(shop.getImageURL())
                                .fit()
                                .centerCrop()
                                .into(holder.userImage);
                    } else {
                        fs.collection("Current_Users").document(review.getId_Owner_User()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()){
                                    if (task.getResult().exists()){
                                        Current_User c=task.getResult().toObject(Current_User.class);
                                        holder.userName.setText(c.getFirst_name());
                                        Picasso.with(context).load(c.getImageURL())
                                                .fit()
                                                .centerCrop()
                                                .into(holder.userImage);
                                    }
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.date.setText(review.getDate());
        holder.reviewBody.setText(review.getText_Review());
        holder.repliesNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.replies_RecyclerView.setVisibility(View.VISIBLE);
                holder.constraintLayout.setVisibility(View.VISIBLE);
                holder.repliesNumber.setVisibility(View.INVISIBLE);
                holder.repliesOnly.setVisibility(View.VISIBLE);
            }
        });
        holder.repliesOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.replies_RecyclerView.setVisibility(View.GONE);
                holder.constraintLayout.setVisibility(View.GONE);
                holder.repliesNumber.setVisibility(View.VISIBLE);
                holder.repliesOnly.setVisibility(View.INVISIBLE);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_review, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout constraintLayout;
        RoundedImageView userImage;
        TextView date, reviewBody, repliesNumber, userName, sentText, repliesOnly;
        EditText reply_User;
        RecyclerView replies_RecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.OR_RoundImageOwner);
            date = itemView.findViewById(R.id.OR_dateReview);
            reviewBody = itemView.findViewById(R.id.OR_reviewBody);
            repliesNumber = itemView.findViewById(R.id.OR_repliesNumber);
            userName = itemView.findViewById(R.id.OR_OwnerName);
            sentText = itemView.findViewById(R.id.OR_sendText);
            reply_User = itemView.findViewById(R.id.OR_userReply);
            replies_RecyclerView = itemView.findViewById(R.id.OR_repliesRecyclerView);
            repliesOnly = itemView.findViewById(R.id.OR_repliesonly);
            constraintLayout = itemView.findViewById(R.id.OR_container);
        }
    }
}
