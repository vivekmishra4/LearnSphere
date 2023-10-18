package com.example.sdc_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignInActivity extends AppCompatActivity {
    EditText username,password;
    Button signIn,SignUpPage,HomePage;
    Boolean signedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        username=findViewById(R.id.sign_in_username);
        password=findViewById(R.id.sign_in_password);
        signIn=findViewById(R.id.sign_in_btn);
        SignUpPage=findViewById(R.id.sign_up_page);
        HomePage=findViewById(R.id.home_page_btn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        SignUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),SignUpActivity.class);
                startActivity(intent);

            }
        });
        HomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

}