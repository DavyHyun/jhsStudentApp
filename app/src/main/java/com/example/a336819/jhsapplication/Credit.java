package com.example.a336819.jhsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.a336819.jhsapplication.AddClasses.ClassesInformation;
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

public class Credit extends AppCompatActivity {

    private static final String TAG = "Credit";
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    private String p1, p2, p3, p4, p5, p6;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_layout);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        myRef = mFirebaseDatabase.getReference("students");
        final String userID = user.getUid();



        Button cback = (Button) findViewById(R.id.credit_back);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ClassesInformation cInfo = new ClassesInformation();
                cInfo.setP1(dataSnapshot.child(userID).getValue(ClassesInformation.class).getP1());
                p1 = cInfo.getP1();
                cInfo.setP2(dataSnapshot.child(userID).getValue(ClassesInformation.class).getP2());
                p2 = cInfo.getP2();
                cInfo.setP3(dataSnapshot.child(userID).getValue(ClassesInformation.class).getP3());
                p3 = cInfo.getP3();
                cInfo.setP4(dataSnapshot.child(userID).getValue(ClassesInformation.class).getP4());
                p4 = cInfo.getP4();
                cInfo.setP5(dataSnapshot.child(userID).getValue(ClassesInformation.class).getP5());
                p5 = cInfo.getP5();
                cInfo.setP6(dataSnapshot.child(userID).getValue(ClassesInformation.class).getP6());
                p6 = cInfo.getP6();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        InputStream is = getResources().openRawResource(R.raw.coursecatalog);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line;
        double p1d, p2d, p3d, p4d, p5d, p6d;
        String[] p1c, p2c, p3c, p4c, p5c, p6c;
        try{
            while((line = reader.readLine()) != null) {
                if (line.contains(p1)) {
                    p1c = line.split(",");
                    p1d = Double.parseDouble(p1c[2]);
                } else if (line.contains(p2)) {
                    p2c = line.split(",");
                    p2d = Double.parseDouble(p2c[2]);
                } else if (line.contains(p3)) {
                    p3c = line.split(",");
                    p3d = Double.parseDouble(p3c[2]);
                } else if (line.contains(p4)) {
                    p4c = line.split(",");
                    p4d = Double.parseDouble(p4c[2]);
                } else if (line.contains(p5)) {
                    p5c = line.split(",");
                    p5d = Double.parseDouble(p5c[2]);
                } else if (line.contains(p6)) {
                    p6c = line.split(",");
                    p6d = Double.parseDouble(p6c[2]);
                }
            }
        } catch (IOException e) {

        }

        for (int i =0; i<13; i ++ ) {

        }



        cback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Credit.this, Home.class);
                startActivity(i);
            }
        });
    }
}
