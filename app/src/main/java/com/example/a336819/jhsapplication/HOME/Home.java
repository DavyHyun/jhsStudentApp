package com.example.a336819.jhsapplication.HOME;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a336819.jhsapplication.Credit;
import com.example.a336819.jhsapplication.InformationStore.UserInformation;
import com.example.a336819.jhsapplication.Links;
import com.example.a336819.jhsapplication.R;
import com.example.a336819.jhsapplication.SMS.Chat;
import com.example.a336819.jhsapplication.SignUp_SIgnIn.MainActivity;
import com.example.a336819.jhsapplication.TODO_ACTIVITY.ToDo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Home extends AppCompatActivity {
    private static final String TAG = "Home";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private Button SignOut;
    private DatabaseReference myRef;
    private  String userID;
    TextView weatherIcon, detailsField, currentTemperatureField;
    Typeface weatherFont;
    String OPEN_WEATHER_MAP_API = "e300b8578d72229e84c6294b505228b3";
    String city = "Mill Creek, US";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layoutt);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        detailsField = (TextView) findViewById(R.id.details_field);
        currentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
        weatherIcon = (TextView) findViewById(R.id.weather_icon);
        weatherFont = Typeface.createFromAsset(getAssets(), "fonts/weathericons-regular-webfont.ttf");
        weatherIcon.setTypeface(weatherFont);


        ImageButton SignOut = (ImageButton) findViewById(R.id.imageView2);
        ImageView TODO = (ImageView) findViewById(R.id.TODO);
        ImageView CREDIT = (ImageView) findViewById(R.id.CREDIT);
        ImageView LINK = (ImageView) findViewById(R.id.LINKS);
        ImageView CHAT = (ImageView) findViewById(R.id.CHAT);
        final TextView nametext = (TextView) findViewById(R.id.textView7);

        Calendar calendar = Calendar.getInstance();
        final String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        final DataSnapshot dataSnapshot;

        taskLoadUp(city);



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                showData(dataSnapshot, nametext, date);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        TODO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tm = new Intent(Home.this, ToDo.class);
                startActivity(tm);
            }
        });

        CREDIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent crm = new Intent(Home.this, Credit.class);
                startActivity(crm);
            }
        });

        LINK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lm = new Intent(Home.this, Links.class);
                startActivity(lm);
            }
        });

        CHAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chm = new Intent(Home.this, Chat.class);
                startActivity(chm);
            }
        });


        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                toastMessage("Signing Out...");
                Intent i = new Intent(Home.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    public void taskLoadUp(String query) {
        if (Function.isNetworkAvailable(getApplicationContext())) {
            DownloadWeather task = new DownloadWeather();
            task.execute(query);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void showData(DataSnapshot datasnapshot, TextView nt, String date) {
        for (DataSnapshot ds : datasnapshot.getChildren()) {
            UserInformation uInfo = new UserInformation();
            uInfo.setstudentName(ds.child(userID).getValue(UserInformation.class).getstudentName());
            uInfo.setstudentGrade(ds.child(userID).getValue(UserInformation.class).getstudentGrade());
            String[] nameSeperate = uInfo.getstudentName().split(" ");
            String firstname = nameSeperate[0];

            nt.setText("Hello, "+ firstname+"!\nToday's Date is, "+date);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


    class DownloadWeather extends AsyncTask< String, Void, String > {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }
        protected String doInBackground(String...args) {
            String xml = Function.excuteGet("http://api.openweathermap.org/data/2.5/weather?q=" + args[0] +
                    "&units=metric&appid=" + OPEN_WEATHER_MAP_API);
            return xml;
        }
        @Override
        protected void onPostExecute(String xml) {

            try {
                JSONObject json = new JSONObject(xml);
                if (json != null) {
                    JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                    JSONObject main = json.getJSONObject("main");
                    DateFormat df = DateFormat.getDateTimeInstance();


                    detailsField.setText(details.getString("description").toUpperCase(Locale.US));
                    currentTemperatureField.setText(String.format("%.2f", main.getDouble("temp")) + "°");
                    weatherIcon.setText(Html.fromHtml(Function.setWeatherIcon(details.getInt("id"),
                            json.getJSONObject("sys").getLong("sunrise") * 1000,
                            json.getJSONObject("sys").getLong("sunset") * 1000)));



                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Error, Check City", Toast.LENGTH_SHORT).show();
            }


        }



    }



}
