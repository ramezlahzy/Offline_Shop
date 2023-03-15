package com.example.offlineshopmain;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.offlineshopmain.backend.UsedClass.Abstract_Category;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UpLoad_Activty extends AppCompatActivity {



        private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private Button mButtonShow;
    private TextView mTexViewFilename;
    private EditText mEditTextFilename;
    private ImageView mImgageView;
    private ProgressBar mProgressBar;
    private static final String TAG = "Fragment_All_Categories";
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaserRef;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fs = FirebaseFirestore.getInstance();
    private CollectionReference productsreference = FirebaseFirestore.getInstance().collection("Abstract_Categories");
    private StorageTask mUploadTask;

//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_product);
        setin();
    }

    private void setin() {
    mButtonChooseImage=findViewById(R.id.button_choose_image);
    mButtonUpload=findViewById(R.id.button_upload);
    mImgageView=findViewById(R.id.image_view);
    mEditTextFilename=findViewById(R.id.edit_text_file_name);
    mProgressBar=findViewById(R.id.progress_bar);
    mStorageRef= FirebaseStorage.getInstance().getReference("/uploads");
    mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openFileChooser();
        }
    });
    mButtonUpload.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            uploadFile();
        }
    });
    }

    private void uploadFile() {
        if (mImageUri != null) {
            Log.d(TAG, "uploadFile: "+System.currentTimeMillis());
            Log.d(TAG, "uploadFile: "+mImageUri);
            Log.d(TAG, "uploadFile: "+getFileExtension(mImageUri));
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(UpLoad_Activty.this, "Upload successful", Toast.LENGTH_SHORT).show();
                                    Abstract_Category category = new Abstract_Category(mEditTextFilename.getText().toString(), new ArrayList<>(), uri.toString());
                                    category.addToFireStore(UpLoad_Activty.this);
                                }
                            });
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpLoad_Activty.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
//                            mProgressBar.setProgress((int) progress);
//                        }
//                    })
            ;
        } else
            Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(mImgageView);

        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}