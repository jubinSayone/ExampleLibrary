package com.example.package_login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity /*implements LoginPack.LogCallback*/{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //setContentView(R.layout.activity_main);

      /*  new LoginPack.Builder()
                .api("")
                .layout(R.layout.activity_main)
                .login(R.layout.login_layout)
                .otp(R.layout.otp_verify)
                .delegate(this)
                .callback(this)
                .packageName("com.example.package_login")
                .build();*/

    }

   /* @Override
    public void success() {
        Toast.makeText(this, "Login sucessfull", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void failed() {
        Toast.makeText(this, "Otp invalid", Toast.LENGTH_SHORT).show();

    }*/
}
