package com.example.fcmtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.fcmtest.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    WebView webView;
    WebSettings webViewSetting;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        webView = binding.activityMainFcm;
        webViewSetting = webView.getSettings();

        webViewSetting.setJavaScriptEnabled(true);
        webViewSetting.setLoadWithOverviewMode(true);
        webViewSetting = webView.getSettings();
        webViewSetting.setJavaScriptEnabled(true);
        webViewSetting.setLoadWithOverviewMode(true);
        webViewSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSetting.setUseWideViewPort(true);
        webViewSetting.setLoadsImagesAutomatically(true);
        webViewSetting.setUseWideViewPort(true);
        webViewSetting.setSupportZoom(true);
        webViewSetting.setAllowFileAccess(true);
        webViewSetting.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient(){});
        webView.setWebChromeClient(new WebChromeClient());

        webView.addJavascriptInterface(this, "Android");
        webView.loadUrl("file:///android_asset/fcmTest.html");

        Intent intent = getIntent();
        if (intent != null) {//푸시알림을 선택해서 실행한것이 아닌경우 예외처리
            String notificationData = intent.getStringExtra("test");
            if (notificationData != null)
                Log.d("FCM_TEST", notificationData);

        }
    }
    @JavascriptInterface
    public void MoveApp() {

        boolean isExist = false;
        String urlScheme = "scheme://host";

        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> mApps;
        Intent mIntent = new Intent(Intent.ACTION_MAIN, null);
        mApps = packageManager.queryIntentActivities(mIntent, 0);

        try {
            for (int i = 0; i < mApps.size(); i++) {
                if(mApps.get(i).activityInfo.packageName.startsWith("com.example.cameratest")){
                    isExist = true;
                    break;
                }
            }
        } catch (Exception e) {
            isExist = false;
        }

        // 설치되어 있으면
        if(isExist){
            mIntent.setAction(Intent.ACTION_VIEW);
            mIntent.addCategory(Intent.CATEGORY_BROWSABLE);
            mIntent.addCategory(Intent.CATEGORY_DEFAULT);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mIntent.setData(Uri.parse(urlScheme));
            Log.d("mIntent_url?", toString().valueOf(mIntent));
            startActivity(mIntent);
        }else{
            Intent marketLaunch = new Intent(Intent.ACTION_VIEW);
            marketLaunch.setData(Uri.parse("market://details?id=com.example.cameratest"));
            startActivity(marketLaunch);
        }

    }
}