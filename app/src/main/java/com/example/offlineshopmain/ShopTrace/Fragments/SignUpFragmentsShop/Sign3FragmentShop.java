package com.example.offlineshopmain.ShopTrace.Fragments.SignUpFragmentsShop;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.offlineshopmain.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Sign3FragmentShop extends Fragment {

    public interface tosign4shop{
        public void tofourthFragmentshop(Bundle bundle);
    }
    private static final String TAG = "Sign3Fragment";
    private static final int CHOOSE_IMAGE = 101;
    public static final String KEY_URL_SHOP="KEY_URL_SHOP";
    View view;
    ImageButton continuebtn;
    ImageView imageView;
    ProgressBar bar;
    ProgressBar barall;
    TextView photowarning,choosetext;
    Uri uri;
    Uri downloadUri;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sign3fragmentshop, container, false);
        setin(view);
        return view;
    }



    private void setin(View view) {
        mAuth = FirebaseAuth.getInstance();
        imageView = view.findViewById(R.id.s3fimageview);
        continuebtn = view.findViewById(R.id.s3fcontinuebtn);
        bar = view.findViewById(R.id.s3fprogressbar);
        barall = view.findViewById(R.id.s3fprogressbarall);
        photowarning = view.findViewById(R.id.s3fphotowarning);
        choosetext=view.findViewById(R.id.s3fchoosetext);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagechooser();
            }
        });
        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInformation();
            }
        });
    }

    private void showImagechooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            uri = data.getData();
            uploadImageToFirebaseStorage();

        }
    }

    private void uploadImageToFirebaseStorage() {
        StorageReference profileImageRef = FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");
        if (uri != null) {
            bar.setVisibility(View.VISIBLE);
            continuebtn.setClickable(false);
            profileImageRef.putFile(uri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                profileImageRef.getDownloadUrl()
                                        .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {
                                                bar.setVisibility(View.GONE);
                                                if (task.isSuccessful()) {
                                                    continuebtn.setClickable(true);

                                                    downloadUri=task.getResult();
                                                    photowarning.setVisibility(View.INVISIBLE);
                                                    Picasso.with(getActivity()).load(downloadUri)
                                                            .fit()
                                                            .centerCrop()
                                                            .into(imageView);
                                                }
//                                                else
//                                                    Toast.makeText(getActivity(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void saveUserInformation() {
        Bundle bundle = getArguments();
        if (uri == null) {
            photowarning.setVisibility(View.VISIBLE);
            return;
        }
        bundle.putString(KEY_URL_SHOP,downloadUri.toString());
        photowarning.setVisibility(View.INVISIBLE);
        SignUpFragment1Shop parent= (SignUpFragment1Shop) getFragmentManager().getFragments().get(getFragmentManager().getFragments().size()-2);
        ((Sign3FragmentShop.tosign4shop)parent).tofourthFragmentshop(bundle);

    }






}
