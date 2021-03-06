package com.example.loginpack;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class LoginPack {

    ConstraintLayout mainLayout;
    Button btnSubmitMobNo;
    Button btnSubmitOtp;
    EditText edtMob;
    EditText edtOtp;
    View mobNoView;
    View otpView;
    Resources resources;
    Activity activity;
    boolean isMobileValid=false;


    public interface LogCallback{
        void success();
        void failed();
    }

    LoginPack(String api, int layout, final int login, final int otp, final Activity activity, final LogCallback listener, final String packageName){
        activity.setContentView(layout);
        this.activity=activity;
        PackageManager manager = activity.getPackageManager();
        try {
            resources = manager.getResourcesForApplication(packageName);
            int resLayout = resources.getIdentifier("layout", "id", packageName);
            mainLayout = activity.findViewById(resLayout);
            mobNoView=activity.getLayoutInflater().inflate(login,null);
            mobNoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mainLayout.addView(mobNoView);
            int resMobEdt = resources.getIdentifier("edtMobNo","id",packageName);
            int resMobBtn = resources.getIdentifier("btnMobSubmit","id",packageName);
            edtMob=activity.findViewById(resMobEdt);
            btnSubmitMobNo=mobNoView.findViewById(resMobBtn);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }




        btnSubmitMobNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = edtMob.getText().toString();
                if(s.length()<10||s.length()>10){
                    Toast.makeText(activity, "Invalid mobile number", Toast.LENGTH_SHORT).show();
                }else{

                    mainLayout.removeView(mobNoView);
                    otpView=activity.getLayoutInflater().inflate(otp,null);
                    otpView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    mainLayout.addView(otpView);
                    int resEdtOtp = resources.getIdentifier("edtOtp","id",packageName);
                    int resBtnOtp = resources.getIdentifier("btnOtpSubmit","id",packageName);
                    btnSubmitOtp=activity.findViewById(resBtnOtp);
                    edtOtp = activity.findViewById(resEdtOtp);

                    btnSubmitOtp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s=edtOtp.getText().toString();
                            if("1234".equals(s))
                            {
                                listener.success();
                            }
                            else
                            {
                                listener.failed();
                            }

                        }
                    });

                }
            }
        });

    }


    public static class Builder{
        static String api;
        int layout;
        int login;
        int otp;
        Activity delegate;
        LogCallback listener;
        String packageName;
        public Builder(){

        }

        public Builder callback(LogCallback listener){
            this.listener = listener;
            return this;
        }

        public Builder api(String api){
            this.api = api;
            return this;
        }

        public Builder delegate(Activity delegate){
            this.delegate = delegate;
            return this;
        }

        public Builder layout(int layout) {
            this.layout = layout;
            return this;
        }

        public Builder login(int login)
        {
            this.login=login;
            return this;
        }

        public Builder otp(int otp)
        {
            this.otp=otp;
            return this;
        }

        public Builder packageName(String packageName)
        {
            this.packageName = packageName;
            return this;
        }

        public LoginPack build(){
            return new LoginPack(api, layout,login,otp, delegate, listener,packageName);
        }
    }

    public class OtpResponse
    {
        @SerializedName("mobile")
        String mobile;
        @SerializedName("otp")
        String otp;

        public OtpResponse(String mobile, String otp) {
            this.mobile = mobile;
            this.otp = otp;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getMobile() {
            return mobile;

        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }

    public class RetrofitInstance {

        private  Retrofit retrofit = null;
        private  String BASE_URL="";

        public  OtpResponse getService(){


            if(retrofit==null){

                retrofit=new Retrofit
                        .Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

            }

            return (OtpResponse) retrofit.create(OtpService.class);

        }

    }

    public interface OtpService {

        @GET
        Call<OtpResponse> getPopularMovies(@Query("mobile") String apiKey);

    }

}




