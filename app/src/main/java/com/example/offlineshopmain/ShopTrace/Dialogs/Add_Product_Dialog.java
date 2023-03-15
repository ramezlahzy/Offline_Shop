package com.example.offlineshopmain.ShopTrace.Dialogs;

import static android.app.Activity.RESULT_OK;
import static com.example.offlineshopmain.ShopTrace.Fragments.ShopProduct_Fragments.ShopProduct_Fragment2.KEY_CATEGORY_Name;
import static com.example.offlineshopmain.ShopTrace.Fragments.ShopProduct_Fragments.ShopProduct_Fragment2.Key_Abstract_Category_Id;
import static com.example.offlineshopmain.ShopTrace.Fragments.ShopProduct_Fragments.ShopProduct_Fragment2.Key_Id_User;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.BackEndServer.AddProductBackEnd.FromServerAddProduct;
import com.example.offlineshopmain.backend.BackEndServer.AddProductBackEnd.ServerMethodAddProduct;
import com.example.offlineshopmain.backend.UsedClass.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Add_Product_Dialog extends DialogFragment implements FromServerAddProduct.Dialog {
    private static final int CAMERA_REQUEST_CODE = 101;
    private static final int SETTING_INTENT_CODE = 102;
    private static final int PICK_IMAGE_REQUEST = 1;
    EditText price, describtion, name_of_product;
    TextView warnprice, warnphoto, warnName;
    CardView save;
    ImageView exit, mainphoto, gallary, camera;
    View view;
    Activity activity;
    ProgressBar bar;
    Uri uriUpload, uriCamera, urigallary;
    ServerMethodAddProduct fromServer = FromServerAddProduct.fromServer;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference("Product_Images");
    private static final String TAG = "Add_Product_Dialog";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.addnewproductdialog, null);
        setin();
        activity = getActivity();
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity).setView(view);
//        view.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return builder.create();
    }

    private void setin() {
        price = view.findViewById(R.id.ANPprice);
        describtion = view.findViewById(R.id.ANPdescribtion);
        name_of_product = view.findViewById(R.id.ANPname_product);
        warnprice = view.findViewById(R.id.ANPwarnprice);
        warnName = view.findViewById(R.id.ANPwarnname);
        warnphoto = view.findViewById(R.id.ANPwarnphoto);
        save = view.findViewById(R.id.ANPsave);
        exit = view.findViewById(R.id.ANPexit);
        mainphoto = view.findViewById(R.id.ANPphoto);
        gallary = view.findViewById(R.id.ANPgallary);
        camera = view.findViewById(R.id.ANPcamer);
        bar = view.findViewById(R.id.ANPbar);
//        openFileChooser();
        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlecamerapermission();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        setdone();
    }


    private void showSnackBar() {
        Snackbar.make(view, "application need camera permission", Snackbar.LENGTH_SHORT).setAction(
                "Grant permission", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + activity.getPackageName()));
                        startActivityForResult(intent, SETTING_INTENT_CODE);
                    }
                }
        ).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bar.setVisibility(View.VISIBLE);
        mainphoto.setImageBitmap(null);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    uriCamera = data.getData();
                    uploadFile();
                }
                break;
            case PICK_IMAGE_REQUEST:
                if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                    urigallary = data.getData();
                    uploadFile();
                }
            default:
                break;
        }

    }


    private void setdone() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkall()) {
                    String stringname = name_of_product.getText().toString();
                    String stringprice = price.getText().toString();
                    String stringdiscribtion = describtion.getText().toString();
                    Bundle bundle = getArguments();
                    String nameOfCategory = bundle.getString(KEY_CATEGORY_Name);
                    String id_user = bundle.getString(Key_Id_User);
                    String id_Abstract_Category = bundle.getString(Key_Abstract_Category_Id);
                    Product product = new Product(stringname, id_Abstract_Category, stringdiscribtion, id_Abstract_Category, 0, 0, Integer.parseInt(stringprice), uriUpload.toString(), new ArrayList<>(), new ArrayList<>(), id_user, new ArrayList<>());
                    fromServer.addProduct(product, nameOfCategory, Add_Product_Dialog.this);
                }
            }
        });
//        save.setEnabled(false);
    }


    private boolean checkall() {
        String stringname = name_of_product.getText().toString();
        String stringprice = price.getText().toString();
        boolean flag = true;
        if (stringname.equals("")) {
            flag = false;
            warnName.setVisibility(View.VISIBLE);
        } else
            warnName.setVisibility(View.INVISIBLE);
        try {
            warnprice.setVisibility(View.INVISIBLE);
            Integer.parseInt(stringprice);
        } catch (Exception e) {
            flag = false;
            warnprice.setVisibility(View.VISIBLE);
        }
        if (uriUpload == null) {
            warnphoto.setVisibility(View.VISIBLE);
            flag = false;
        }
        return flag;
    }

    private void handlecamerapermission() {
        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            opencamera();
        else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA))
                showSnackBar();
            else
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE); //CAMERA_PERMISSION_REQUEST_CODE);
        }
    }


    private void uploadFile() {

        if (urigallary != null || uriCamera != null) {
            Uri uri = uriCamera;
            if (uriCamera == null) uri = urigallary;
            Log.d(TAG, "uploadFile: " + uri);

            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
            fileReference.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri newuri) {
                                    Log.d(TAG, "onSuccess: " + newuri.toString());
                                    Picasso.with(getActivity()).load(newuri.toString())
                                            .fit()
                                            .centerCrop()
                                            .into(mainphoto);
                                    Add_Product_Dialog.this.uriUpload = newuri;
                                    bar.setVisibility(View.INVISIBLE);
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
////                            mProgressBar.setProgress((int) progress);
//                        }
//                    })
            urigallary = null;
            uriCamera = null;
        } else
            Toast.makeText(getActivity(), "No File Selected", Toast.LENGTH_SHORT).show();

    }

    private void opencamera() {
        Intent intent3 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent3, CAMERA_REQUEST_CODE);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void SetAdd(boolean b) {
        if (b)
            dismiss();
        else
            save.setEnabled(true);
    }
}
