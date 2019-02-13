package com.example.a336819.jhsapplication.SMS;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a336819.jhsapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class sendSMS extends Activity {
    private static final String TAG = "Chat";

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS=1;
    String phoneNum;
    String message;
    EditText messag;
    String namee;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendsms_layout);
        TextView textView = (TextView) findViewById(R.id.textView3);
        Intent intent = getIntent();
        final String Name = intent.getStringExtra("name");
        phoneNum = intent.getStringExtra("phoneNum");
        messag = (EditText) findViewById(R.id.editttt);
        textView.setText("Message to: " + Name);
        Log.d(TAG, phoneNum);


        Button sendBtn = (Button) findViewById(R.id.send2);
        Button back = (Button) findViewById(R.id.back);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("students").child(uid);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                namee = dataSnapshot.child("studentName").getValue(String.class);
                Log.d("TAG", namee);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        uidRef.addListenerForSingleValueEvent(eventListener);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(sendSMS.this, Chat.class);
                startActivity(i);

            }
        });

        sendBtn.setEnabled(false);
        if (checkPermission(Manifest.permission.SEND_SMS)) {
            Log.d(TAG, "1");
            sendBtn.setEnabled(true);
            Log.d(TAG, "2");
        } else {
            Log.d(TAG, "3");
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            Log.d(TAG, "4");
        }


    }

    public void onSend(View v) {
        message = "MESSAGE FROM " + namee + " THROUGH JHSApplication: " + messag.getText().toString();
        Log.d(TAG, "8");
        if(phoneNum==null || phoneNum.length()==0 || message == null || message.length()==0) {
            Log.d(TAG, "7");
            return;
        }

        if (checkPermission(Manifest.permission.SEND_SMS)){
            Log.d(TAG, "9");
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNum, null, message, null, null);
            Log.d(TAG, "11");
            Toast.makeText(this, "Message Sent!", Toast.LENGTH_SHORT).show();
        }else{
            Log.d(TAG, "10");
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkPermission(String permission) {
        Log.d(TAG, "5");
        int check = ContextCompat.checkSelfPermission(this, permission);
        Log.d(TAG, "6");
        return (check==PackageManager.PERMISSION_GRANTED);
    }





    }

