package com.example.a336819.jhsapplication.AddClasses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.a336819.jhsapplication.Home;
import com.example.a336819.jhsapplication.R;
import com.example.a336819.jhsapplication.InformationStore.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class AddClassesSE extends AppCompatActivity {
    private static final String TAG = "AddClassesSE";
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    private String grade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addclassse_layout);

        ArrayList<String> listt = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        myRef = mFirebaseDatabase.getReference("students");
        final String userID = user.getUid();
        final String G;

        final AutoCompleteTextView c1 = (AutoCompleteTextView) findViewById(R.id.c1);
        final AutoCompleteTextView c2 = (AutoCompleteTextView) findViewById(R.id.c2);
        final AutoCompleteTextView c3 = (AutoCompleteTextView) findViewById(R.id.c3);
        final AutoCompleteTextView c4 = (AutoCompleteTextView) findViewById(R.id.c4);
        final AutoCompleteTextView c5 = (AutoCompleteTextView) findViewById(R.id.c5);
        final AutoCompleteTextView c6 = (AutoCompleteTextView) findViewById(R.id.c6);
        Button cont = (Button) findViewById(R.id.cont);

        InputStream is = getResources().openRawResource(R.raw.coursecatalog);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line;
        int test = 0;
        try {
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                String name = values[1];
                listt.add(name);

            }
        } catch (IOException e) {

        }



        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, listt);
        c1.setAdapter(adapter);
        c2.setAdapter(adapter);
        c3.setAdapter(adapter);
        c4.setAdapter(adapter);
        c5.setAdapter(adapter);
        c6.setAdapter(adapter);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                UserInformation uInfo = new UserInformation();
                uInfo.setstudentGrade(dataSnapshot.child(userID).getValue(UserInformation.class).getstudentGrade());
                grade = uInfo.getstudentGrade();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());

            }
        });


        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p1 = c1.getText().toString().trim();
                String p2 = c2.getText().toString().trim();
                String p3 = c3.getText().toString().trim();
                String p4 = c4.getText().toString().trim();
                String p5 = c5.getText().toString().trim();
                String p6 = c6.getText().toString().trim();

                FirebaseUser user = mAuth.getCurrentUser();
                String useID = user.getUid();
                ClassesInformation CI = new ClassesInformation(p1, p2, p3, p4, p5, p6);
                myRef.child(useID).child("Senior classes").setValue(CI);

                    Intent i = new Intent(AddClassesSE.this, Home.class);
                    startActivity(i);


            }
        });
    }

}