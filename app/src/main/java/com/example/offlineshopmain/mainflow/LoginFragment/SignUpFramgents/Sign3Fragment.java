package com.example.offlineshopmain.mainflow.LoginFragment.SignUpFramgents;

import static android.app.Activity.RESULT_OK;
import static com.example.offlineshopmain.backend.NewAllData.fs;
import static com.example.offlineshopmain.mainflow.LoginFragment.SignUpFramgents.Sign1Fragment.KEY_FIRST_NAME;
import static com.example.offlineshopmain.mainflow.LoginFragment.SignUpFramgents.Sign1Fragment.KEY_LAST_NAME;
import static com.example.offlineshopmain.mainflow.LoginFragment.SignUpFramgents.Sign1Fragment.KEY_PHONE;
import static com.example.offlineshopmain.mainflow.LoginFragment.SignUpFramgents.Sign2Fragment.KEY_EMAIL;
import static com.example.offlineshopmain.mainflow.LoginFragment.SignUpFramgents.Sign2Fragment.KEY_PASSWORD;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.offlineshopmain.R;
import com.example.offlineshopmain.backend.UsedClass.Current_User;
import com.example.offlineshopmain.mainflow.LoginFragment.MainActivity;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Sign3Fragment extends Fragment {

    private static final String TAG = "Sign3Fragment";
    private static final int CHOOSE_IMAGE = 101;
    View view;
    ImageButton continuebtn;
    ImageView imageView;
    RadioGroup radioGroup;
    RadioButton maleradio, femaleradio;
    ProgressBar bar;
    ProgressBar barall;
    Spinner spinner;
    TextView photowarning;
    Uri uri;
    Uri downLoadUri;
    FirebaseAuth mAuth;
    int LOCATION_REFRESH_TIME = 1500; // 1500 seconds to update
    int LOCATION_REFRESH_DISTANCE = 500; // 500 meters to update
    private LocationRequest locationRequest;


    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            setlatitude(location.getLatitude(),location.getLongitude());
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        locationRequest=LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        view = inflater.inflate(R.layout.sign3fragment, container, false);
        setin(view);
        return view;
    }



    private void setin(View view) {
        mAuth = FirebaseAuth.getInstance();
        imageView = view.findViewById(R.id.s3fimageview);
        continuebtn = view.findViewById(R.id.s3fcontinuebtn);
        radioGroup = view.findViewById(R.id.s3fradiogroub);
        maleradio = view.findViewById(R.id.s3fradiomale);
        femaleradio = view.findViewById(R.id.s3fradiofemale);
        bar = view.findViewById(R.id.s3fprogressbar);
        barall = view.findViewById(R.id.s3fprogressbarall);
        spinner = view.findViewById(R.id.s3fspinneryears);
        spinner.setSelection(53);
        photowarning = view.findViewById(R.id.s3fphotowarning);
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
                                                    photowarning.setVisibility(View.INVISIBLE);
                                                    downLoadUri=task.getResult();
                                                    Picasso.with(getActivity()).load(downLoadUri)
                                                            .fit()
                                                            .centerCrop()
                                                            .into(imageView);
                                                } else
                                                    Toast.makeText(getActivity(), task.getException().toString(), Toast.LENGTH_SHORT).show();
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
        String email = (String) bundle.get(KEY_EMAIL);
        String password = (String) bundle.get(KEY_PASSWORD);
        String phone_Number = (String) bundle.get(KEY_PHONE);
        String first_Name = (String) bundle.get(KEY_FIRST_NAME);
        String last_Name = (String) bundle.get(KEY_LAST_NAME);
        String age = getage();
        boolean isfemale = getfemale();
        if (uri == null) {
            photowarning.setVisibility(View.VISIBLE);
            return;
        }


        Log.d(TAG, "saveUserInformation: " + age);
        Log.d(TAG, "saveUserInformation: " + isfemale);
        photowarning.setVisibility(View.INVISIBLE);
        barall.setVisibility(View.VISIBLE);
        FirebaseUser user = mAuth.getCurrentUser();
        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
        builder.setDisplayName(first_Name + " " + last_Name);
        if (uri != null)
            builder.setPhotoUri(Uri.parse(uri.toString()));

        if (user != null) {
            UserProfileChangeRequest profile = builder.build();
            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            barall.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                ((MainActivity) getActivity()).loginascustomer();
                                Toast.makeText(getActivity(), "Profile complete", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            String id = user.getUid();
            Current_User user1 = new Current_User(first_Name, last_Name, age, null, new ArrayList<>(), downLoadUri.toString(), isfemale, 0, 0,password,email,phone_Number
            ,new ArrayList<>(),new ArrayList<>());
            user1.setId(id);
//            user1.addToFireStore(getActivity(), id);
//            LocationManager mLocationManager = (LocationManager) requireActivity().getSystemService(LOCATION_SERVICE);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//
//                    if (isGPSEnabled()) {
//                        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, mLocationListener);
//
////                        LocationServices.getFusedLocationProviderClient(getActivity())
////                                .requestLocationUpdates(locationRequest, new LocationCallback() {
////                                    @Override
////                                    public void onLocationResult(@NonNull LocationResult locationResult) {
////                                        super.onLocationResult(locationResult);
////
////                                        LocationServices.getFusedLocationProviderClient(getActivity())
////                                                .removeLocationUpdates(this);
////
////                                        if (locationResult != null && locationResult.getLocations().size() >0){
////
////                                            int index = locationResult.getLocations().size() - 1;
////                                            double latitude = locationResult.getLocations().get(index).getLatitude();
////                                            double longitude = locationResult.getLocations().get(index).getLongitude();
////
////                                            setlatitude(latitude,longitude);
////
////                                        }
////                                    }
////                                }, Looper.getMainLooper());
//
//                    } else {
//                        turnOnGPS();
//                    }
//                } else
//                    getActivity().requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1 );
//
//
//            }
        }
    }

    private void setlatitude(double latitude, double longitude) {
        fs.collection("Current_Users").document(mAuth.getCurrentUser().getUid()).update("latitude",latitude);
        fs.collection("Current_Users").document(mAuth.getCurrentUser().getUid()).update("longitude",longitude);
        Log.d(TAG, "setlatitude: "+latitude);
        Log.d(TAG, "setlatitude: "+longitude);
    }


    private void turnOnGPS() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getActivity())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(getActivity(), "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(getActivity(), 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {

            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }
    private String getage() {
        return spinner.getSelectedItem().toString() + "";
    }

    private boolean getfemale() {
        if (radioGroup.getCheckedRadioButtonId() == femaleradio.getId())
            return true;
        return false;
    }
}
