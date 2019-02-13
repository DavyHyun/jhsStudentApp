package com.example.a336819.jhsapplication.SignUp_SIgnIn;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a336819.jhsapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "SignUp";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText Emaill, Passwordd;
    private Button SignUp;
    private TextView back;
    private EditText ConPass;

    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup);


        back = (TextView) findViewById(R.id.link_login);
        Emaill = (EditText) findViewById(R.id.input_name);
        Passwordd = (EditText) findViewById(R.id.input_email1);
        ConPass = (EditText) findViewById(R.id.input_password1);
        SignUp = (Button) findViewById(R.id.btn_signup);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage("Registering...");
                resgisteruser();
            }
        });




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
            }
        });




    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }




    private void updateUI(FirebaseUser user) {

    }

    private void resgisteruser() {
            String email = Emaill.getText().toString().trim();
            String pass = Passwordd.getText().toString().trim();
            String con = ConPass.getText().toString().trim();

            if (email.isEmpty()) {
                Emaill.setError("Email is required");
                Emaill.requestFocus();
                return;
            }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Emaill.setError("Please enter a valid email");
            Emaill.requestFocus();
            return;
        }

        if (pass.isEmpty()) {
            Passwordd.setError("Password is required");
            Passwordd.requestFocus();
            return;
        }

        if (!pass.equals(con)) {
            ConPass.setError("Password does not match");
            ConPass.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Intent i = new Intent(SignUp.this, SignUpData.class);
                    startActivity(i);
                    finish();

                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }




}
