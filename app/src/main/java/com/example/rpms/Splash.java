package com.example.rpms;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {

    /**initialising parameters*/
    private EditText mloginemail,mloginpassword;
    private  TextView mgotoforgotpassword;
    private Button mgotosignup,mlogin;

    private FirebaseAuth firebaseAuth;

    ProgressBar mprogressbarofmainactivity;

    /**Hiding the actionBar,
     *  setting the XML IDs to java code,
     *  Starting new activity*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        getSupportActionBar().hide();


        mloginemail=findViewById(R.id.loginemail);
        mloginpassword=findViewById(R.id.loginpassword);
        mlogin=findViewById(R.id.login);
        mgotoforgotpassword=findViewById(R.id.gotoforgotpassword);
        mgotosignup=findViewById(R.id.gotosignup);
        mprogressbarofmainactivity=findViewById(R.id.progressbarofmainactivity);

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        if(firebaseUser!=null)
        {
            finish();

            startActivity(new Intent(Splash.this,Home.class));
        }



        mgotosignup.setOnClickListener(new View.OnClickListener() {
            /**setting onClickListener on GotoSignUp button to redirect the user
             *  from splash activity to SignUp Activity*/
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Splash.this,SignUp.class));
            }
        });
        mgotoforgotpassword.setOnClickListener(new View.OnClickListener() {
            /**setting onClickListener on GotoForgetPassword button to redirect
             *  the user from splash activity to ForgetPassword Activity*/
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Splash.this,ForgetPassword.class));
            }
        });

        mlogin.setOnClickListener(new View.OnClickListener() {
            /**setting onClickListener on Login button and validating to avoid empty email and password*/
            @Override
            public void onClick(View v) {
                String mail=mloginemail.getText().toString().trim();
                String password=mloginpassword.getText().toString().trim();

                if(mail.isEmpty()|| password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"All Field Are Required",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    mprogressbarofmainactivity.setVisibility(View.VISIBLE);

                    firebaseAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        /** login the user*/
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if(task.isSuccessful())
                            {
                                checkmailverfication();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Account Doesn't Exist",Toast.LENGTH_SHORT).show();
                                mprogressbarofmainactivity.setVisibility(View.INVISIBLE);
                            }


                        }
                    });




                }
            }
        });

    }

 /**checking if the user has verified the email or note*/
    private void checkmailverfication()
    {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        if(firebaseUser.isEmailVerified()==true)
        {
            //Toast.makeText(getApplicationContext(),"Logged In",Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(Splash.this,Home.class));
        }
        else
        {
            mprogressbarofmainactivity.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),"Verify your mail first",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }




}