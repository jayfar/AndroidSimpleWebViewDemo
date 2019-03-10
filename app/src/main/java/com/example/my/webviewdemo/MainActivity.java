package com.example.my.webviewdemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button mBtnGo;
    private WebView mWebView;
    private EditText etUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnGo = findViewById(R.id.btnGo);
        mBtnGo.setText("GO");
        mBtnGo.setOnClickListener(this);

        etUrl = findViewById(R.id.editTextUrl);
        etUrl.setText("https://www.whatsmybrowser.org/");
        etUrl.setText("https://appr.tc/r/186325883");



        this.requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, 1);

        mWebView = findViewById(R.id.webview);

        WebSettings settings = mWebView.getSettings();

        // Enable Javascript
        settings.setJavaScriptEnabled(true);

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB+
            settings.setDisplayZoomControls(false);
        }

        // Use WideViewport and Zoom out if there is no viewport defined
        //settings.setUseWideViewPort(true);
        //settings.setLoadWithOverviewMode(true);

        // Enable pinch to zoom without the zoom buttons
        //settings.setBuiltInZoomControls(true);

        // Allow use of Local Storage
        settings.setDomStorageEnabled(true);

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB+
            settings.setDisplayZoomControls(false);
        }

        // Enable remote debugging via chrome://inspect
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        mWebView.setWebViewClient(new WebViewClient());

        // AppRTC requires third party cookies to work
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptThirdPartyCookies(mWebView, true);

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Log.d("DEMO", "onPermissionRequest");
                MainActivity.this.runOnUiThread(new Runnable() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                            request.grant(request.getResources());
                    }
                });
            }

            // If this is needed, override the WebViewClient above and put this there
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
//
//                // this will ignore the Ssl error and will go forward to your site
//                handler.proceed();
//            }
        });
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.btnGo:
                mWebView.loadUrl(etUrl.getText().toString());
                break;

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(MainActivity.this, "Permission denied. You must allow these permissions", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }
}
