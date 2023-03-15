package com.example.offlineshopmain.mainflow.Fragments.OneProduct.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.UsedClass.Reply;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Replies_Adapter extends FirestoreRecyclerAdapter<Reply,Replies_Adapter.ViewHolder> {

    Context context;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Replies_Adapter(@NonNull FirestoreRecyclerOptions<Reply> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Reply model) {
        holder.OwnerName.setText(model.getOwnerName());
        holder.replyBody.setText(model.getTextbody());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_reply, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView replyBody;
        TextView OwnerName;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        replyBody=itemView.findViewById(R.id.ORE_relpyBody);
        OwnerName=itemView.findViewById(R.id.ORE_ownername);
    }
}

}
