package com.example.offlineshopmain.mainflow;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.offlineshopmain.R;

public class mywebsite extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywebsite);
        Intent intent=getIntent();
        webView=findViewById(R.id.mywebviewURL);

        if(intent!=null){
            String URL=intent.getStringExtra("URL");
        webView.loadUrl(URL);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);}
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack())
            webView.goBack();
        else
        super.onBackPressed();
    }
}