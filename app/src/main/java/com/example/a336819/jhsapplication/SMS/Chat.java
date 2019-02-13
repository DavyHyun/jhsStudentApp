package com.example.a336819.jhsapplication.SMS;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.a336819.jhsapplication.Home;
import com.example.a336819.jhsapplication.R;
import com.example.a336819.jhsapplication.InformationStore.UserInformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {

    private static final String TAG = "Chat";

    private List<UserInformation> mUsers;
    private List<String> userNum;
    private List<String> userName;
    private ArrayAdapter adapter;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        mUsers = new ArrayList<>();
        userName = new ArrayList<>();
        userNum = new ArrayList<>();
        EditText nameEnter = (EditText) findViewById(R.id.textname);
        final ListView list = (ListView) findViewById(R.id.Names);

        //userNum.add("one");
        //userNum.add("tne");
        //userNum.add("yne");
        //userNum.add("une");
        //userNum.add("ine");
        //userNum.add("pne");




        final DatabaseReference firebaseUser = FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference = firebaseUser.child("students");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("studentName").getValue(String.class);
                    String phonenum = ds.child("studentPhoneNum").getValue(String.class);
                    userNum.add(phonenum);
                    userName.add(name);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addListenerForSingleValueEvent(eventListener);



        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, userName);
        list.setAdapter(adapter);

        nameEnter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (Chat.this).adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = list.getItemAtPosition(position).toString();
                int pos = userName.indexOf(name);
                String phoneNum = userNum.get(pos);
                String [] t = name.split(" ");
                String finalName = t[0];

                Intent intent = new Intent(Chat.this, sendSMS.class);
                intent.putExtra("name", finalName);
                intent.putExtra("phoneNum", phoneNum);
                startActivity(intent);
            }
        });






        Button chback = (Button) findViewById(R.id.chat_back);

        chback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Chat.this, Home.class);
                startActivity(i);
            }
        });
    }
}
