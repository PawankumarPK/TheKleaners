package com.example.hp.thekleaners;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private String urlSearch = "http://thekleaners.com/index.html";
    WebView webView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        WebView webView = (WebView)findViewById(R.id.webView);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.setCancelable(false);

        //loading webview
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(urlSearch);
        webView.setHorizontalScrollBarEnabled(true);

        //for Zoom in of webpage
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);

        //for the progress dialog to appear
        webView.setWebChromeClient(new WebChromeClient(){

            public void onProgressChanged(WebView View, int progress){

                progressDialog.setProgress(progress);
                if (progress == 100){
                    progressDialog.dismiss();
                }else {
                    progressDialog.show();
                }
            }
        });

    }
}

