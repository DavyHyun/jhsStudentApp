package com.example.a336819.jhsapplication.SignUp_SIgnIn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.a336819.jhsapplication.Home;
import com.example.a336819.jhsapplication.InformationStore.Student;
import com.example.a336819.jhsapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpData extends AppCompatActivity {

    private static final String TAG = "AddToDatabase";

    private FirebaseAuth mAuth;
    private Button Cont,back;
    private RadioButton F,S,J,SE;
    private EditText NameInput, PhoneNum;
    private RadioGroup rg;
    private RadioButton rb;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference myRef;





    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signupdata);

        Cont = (Button) findViewById(R.id.btn_cont);
        back = (Button) findViewById(R.id.btn_back);
        F = (RadioButton) findViewById(R.id.radioButton7);
        S = (RadioButton) findViewById(R.id.radioButton6);
        J = (RadioButton) findViewById(R.id.radioButton8);
        SE = (RadioButton) findViewById(R.id.radioButton9);
        NameInput = (EditText) findViewById(R.id.editText2);
        PhoneNum = (EditText) findViewById(R.id.editText3);
        rg = (RadioGroup) findViewById(R.id.radioGroup);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("students");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object value = dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpData.this, SignUp.class);
                startActivity(i);
            }
        });

        Cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage("working...");
                int radiobuttonid = rg.getCheckedRadioButtonId();
                rb = (RadioButton) findViewById(radiobuttonid);
                String choice = rb.getText().toString().trim();
                String Name = NameInput.getText().toString();
                String Phone = PhoneNum.getText().toString();
                if(!Name.equals("")) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid();
                    Student student = new Student(Name, choice, userID, Phone);
                    myRef.child(userID).setValue(student);
                    Intent yo = new Intent(SignUpData.this, Home.class);
                    startActivity(yo);

                }

            }
        });

    }

    public void rbclick(View v) {
        int radiobuttonid = rg.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(radiobuttonid);
        String choice = rb.getText().toString().trim();
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show
                ();
    }
}
