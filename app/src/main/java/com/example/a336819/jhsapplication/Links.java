package com.example.a336819.jhsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Links extends AppCompatActivity {

    private Button go,gb,cvs,sw,gt;
    private WebView wb;
    private EditText search;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.links_layout);

        go=findViewById(R.id.go);
        gb=findViewById(R.id.gb);
        cvs=findViewById(R.id.cvs);
        sw=findViewById(R.id.sw);
        gt=findViewById(R.id.gt);
        Button back = findViewById(R.id.link_back);
        wb=(WebView)findViewById(R.id.webview);
        search = (EditText) findViewById(R.id.texturl);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Links.this, Home.class);
                startActivity(i);
            }
        });





        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wb.loadUrl("http://www.google.com/search?q=" + search.getText().toString().trim());
           }
        });

        gb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wb = (WebView) findViewById(R.id.webview);
                wb.setWebViewClient(new WebViewClient());

                WebSettings webSettings = wb.getSettings();
                webSettings.setJavaScriptEnabled(true);

                wb.loadUrl("https://gradebook.everettsd.org/Wazzle/Gradebook/Logon.aspx?ReturnUrl=%2FWazzle%2FGradebook%2F");
            }
        });
        cvs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wb = (WebView) findViewById(R.id.webview);
                wb.setWebViewClient(new WebViewClient());
                WebSettings webSettings = wb.getSettings();
                webSettings.setJavaScriptEnabled(true);

                wb.loadUrl("https://everettsd.instructure.com/");
            }
        });
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wb = (WebView) findViewById(R.id.webview);
                wb.setWebViewClient(new WebViewClient());
                WebSettings webSettings = wb.getSettings();
                webSettings.setJavaScriptEnabled(true);

                wb.loadUrl("https://www.everettsd.org/jacksonhigh");
            }
        });
        gt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wb = (WebView) findViewById(R.id.webview);
                wb.setWebViewClient(new WebViewClient());
                WebSettings webSettings = wb.getSettings();
                webSettings.setJavaScriptEnabled(true);

                wb.loadUrl("https://translate.google.com/");
            }
        });






    }
}
