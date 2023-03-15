package com.example.offlineshopmain.mainflow.LoginFragment.ForgotPassward;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.offlineshopmain.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;


public class Forgot1Fragment extends Fragment {

    public interface tosecond{
        public void settosecond();
    }
    private String mVerificationId;

    public interface toSecondFragment {
        public void setToSecondFragment();
    }

    FrameLayout frameLayout;
    EditText number;
    ImageButton button;
    FirebaseAuth mAuth;
    View view;
    private static final String TAG = "ForgotPasswordFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forgot1fragment, container, false);
        Log.d(TAG, "onCreateView: " + getFragmentManager().getFragments().get(getFragmentManager().getFragments().size() - 2));
        setIn(view);
        return view;
    }
    public void sendOTP(String phoneNumber) {
        FirebaseFunctions mFunctions = FirebaseFunctions.getInstance();

        // Create the OTP
        int otp = (int) (Math.random() * 1000000);

        // Create the data to send to the function
        Map<String, Object> data = new HashMap<>();
        data.put("phoneNumber", phoneNumber);
        data.put("otp", otp);

        // Call the function and send the OTP
        mFunctions.getHttpsCallable("sendOTP")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        String result = (String) task.getResult().getData();
                        Log.d(TAG, "then: "+result);
                        return result;
                    }
                });
    }

    private void setIn(View view) {
        number = view.findViewById(R.id.f1fnumber);
        button = view.findViewById(R.id.f1fbutton);
        Log.d(TAG, "setIn: ");
        mAuth = FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+"+20"+number.getText().toString());
//                verifyPhoneNumber("+20"+number.getText().toString());
                verifyPhoneNumber2("+201275817179");


            }
        });
    }

    public interface APIService {
        @POST("verify-phone")
//        @POST("verify-code")
        Call<ResponseBody> verifyPhoneNumber(@Body VerifyPhoneNumberRequest request);
    }
    public class VerifyPhoneNumberRequest {
        @SerializedName("phoneNumber")
        private String phoneNumber;

        public VerifyPhoneNumberRequest(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }
    private void verifyPhoneNumber2(String phoneNumber){
        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://offlineshop.onrender.com/")
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);
        Call<ResponseBody> call = apiService.verifyPhoneNumber(new VerifyPhoneNumberRequest(phoneNumber));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: "+call.request().body());
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: "+response.code());
                    Log.d(TAG, "onResponse: "+response.body().toString());
                    // Handle successful response
                } else {
                    Log.d(TAG, "onResponse: "+response.body());
                    Log.d(TAG, "onResponse: "+response.isSuccessful());
                    Log.d(TAG, "onResponse: "+response.code());
                    try {
                        Log.d(TAG, "onResponse: "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle failure
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }




    private void verifyPhoneNumber(String phoneNumber){

        String url = "https://offlineshop.onrender.com/verify-phone";

        RequestBody requestBody = new FormBody.Builder()
                .add("phoneNumber", phoneNumber)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
                Log.d(TAG, "onResponse: "+response.body().string());
                Log.d(TAG, "onResponse: "+response.isSuccessful());
            }
        });
//        client.newCall(request).enqueue(new Callback() {
//
//
//            @Override
//            public void onResponse(Call call, Response response) {
//
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//
//            }
//        });
    }



}

//package com.example.offlineshopmain.mainflow.LoginFragment.ForgotPassward;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.ImageButton;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//
//import com.example.offlineshopmain.R;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthOptions;
//import com.google.firebase.auth.PhoneAuthProvider;
//
//import java.util.concurrent.TimeUnit;
//
//public class Forgot1Fragment extends Fragment {
//
//    private String mVerificationId;
//
//    public interface tosecond{
//        public void settosecond();
//    }
//    FrameLayout frameLayout;
//    EditText number;
//    ImageButton button;
//    FirebaseAuth mAuth;
//    View view;
//    private static final String TAG = "Forgot1Fragment";
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.forgot1fragment, container, false);
//        Log.d(TAG, "onCreateView: "+getFragmentManager().getFragments().get(getFragmentManager().getFragments().size()-2));
//        setin(view);
//        return view;
//    }
//
//    private void setin(View view) {
//        number=view.findViewById(R.id.f1fnumber);
//        button=view.findViewById(R.id.f1fbutton);
//
//        mAuth=FirebaseAuth.getInstance();
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "onClick: "+getParentFragment());
//
//                PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//                    @Override
//                    public void onVerificationCompleted(PhoneAuthCredential credential) {
//                        String code=credential.getSmsCode();
//                        Log.d(TAG, "onVerificationCompleted: "+code);
//                        if (code!=null){
//                            verifyCode(code);
//                        }
//                    }
//
//                    @Override
//                    public void onVerificationFailed(FirebaseException e) {
//
//                    }
//
//                    @Override
//                    public void onCodeSent(@NonNull String verificationId,
//                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
//                          super.onCodeSent(verificationId,token);
//                         mVerificationId = verificationId;
////                        mResendToken = token;
//                    }
//                };
////                PhoneAuthProvider.getInstance().verifyPhoneNumber(
////                        "+201275817179",
//////                        "+201275817179"
//////                        ,
////                        6,
////                        TimeUnit.SECONDS,
////                        getActivity(),
////                        mCallbacks
////                );
//                PhoneAuthOptions options =
//                        PhoneAuthOptions.newBuilder(mAuth)
//                                .setPhoneNumber("+201275817179")       // Phone number to verify
//                                .setTimeout(6L, TimeUnit.SECONDS) // Timeout and unit
//                                .setActivity(getActivity())                 // Activity (for callback binding)
//                                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
//                                .build();
//                PhoneAuthProvider.verifyPhoneNumber(options);
//                Log.d(TAG, "onClick: ");
//
//
//
//                mAuth.setLanguageCode("fr ");
//
//            }
//        });
//    }
//
//    private void verifyCode(String code) {
//        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerificationId,code);
//        signInTheUserByCredentials(credential);
//    }
//    private void signInTheUserByCredentials(PhoneAuthCredential credential){
//        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(
//                new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Toast.makeText(getActivity(), "DONNNNNNNNNNNN", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//    }
//
//    public void gonext(PhoneAuthCredential credential){
//        signInWithPhoneAuthCredential(credential);
//        ForgotFragment parent= (ForgotFragment) getFragmentManager().getFragments().get(getFragmentManager().getFragments().size()-2);
//        ((tosecond)parent).settosecond();
//    }
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//
//                            FirebaseUser user = task.getResult().getUser();
//                            // Update UI
//                        } else {
//                            // Sign in failed, display a message and update the UI
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                // The verification code entered was invalid
//                            }
//                        }
//                    }
//                });
//    }
//
//}





//                PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//                    private void verifyCode(String code) {
//                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
//                        signInWithPhoneAuthCredential(credential);
//                    }
//
//                    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//                        mAuth.signInWithCredential(credential)
//                                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                        if (task.isSuccessful()) {
//                                            // Sign in success, update UI with the signed-in user's information
//                                            Log.d(TAG, "signInWithCredential:success");
//
//                                            FirebaseUser user = task.getResult().getUser();
//                                            toSecondFragment activity = (toSecondFragment) getActivity();
//                                            activity.setToSecondFragment();
//                                        } else {
//                                            // Sign in failed, display a message and update the UI
//                                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                                // The verification code entered was invalid
//                                                Toast.makeText(getActivity(), "Invalid code.", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    }
//                                });
//                    }
//
//                    @Override
//                    public void onVerificationCompleted(PhoneAuthCredential credential) {
//                        String code = credential.getSmsCode();
//                        Log.d(TAG, "onVerificationCompleted: " + code);
//                        if (code != null) {
//                            verifyCode(code);
//                        }
//                    }
//
//                    @Override
//                    public void onVerificationFailed(FirebaseException e) {
//                        Log.d(TAG, "onVerificationFailed: "+e.getMessage());
//                    }
//
//                    @Override
//                    public void onCodeSent(@NonNull String verificationId,
//                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
//                        super.onCodeSent(verificationId, token);
//                        mVerificationId = verificationId;
//                    }
//                };
//
//                String phoneNumber = "+20" + number.getText().toString();
//                PhoneAuthOptions options =
//                        PhoneAuthOptions.newBuilder(mAuth)
//                                .setPhoneNumber(phoneNumber)       // Phone number to verify
//                                .setTimeout(6L, TimeUnit.SECONDS) // Timeout and unit
//                                .setActivity(getActivity())                 // Activity (for callback binding)
//                                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
//                                .build();
//
//                PhoneAuthProvider.verifyPhoneNumber(options);