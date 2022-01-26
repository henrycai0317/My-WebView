package com.example.my_webview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private WebView mWebView;
    private WebSettings mWebSettings;
    private TextView beginLoading,endLoading,loading,mtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.webView1);
        mWebView.loadUrl("https://www.eese.com/");

        beginLoading = (TextView) findViewById(R.id.text_beginLoading);
        endLoading = (TextView) findViewById(R.id.text_endLoading);
        loading = (TextView) findViewById(R.id.text_Loading);
        mtitle = (TextView) findViewById(R.id.title);
        mWebSettings = mWebView.getSettings();
        mWebSettings.setSupportMultipleWindows(true);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);



        mWebView.setWebViewClient(new WebViewClient(){
        //設置不用系統瀏覽器打開，直接顯示在當前WebView
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        //設置加載前的方法
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d(TAG, "onPageStarted: 開始加載");
                beginLoading.setText("開始加載....");
            }
        //設置加載後的方法
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG, "onPageFinished: 結束加載");
                endLoading.setText("結束加載 !!");
            }
        });

        //設置WebChromeClientf類
        mWebView.setWebChromeClient(new WebChromeClient(){

            //獲取網站標題
            @Override
            public void onReceivedTitle(WebView view, String title) {
                Log.d(TAG, "onReceivedTitle:  標題在這 "+title);
                mtitle.setText(title);
            }

            //獲取加載進度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress < 100){
                    String progress = newProgress + "%";
                    loading.setText(progress);
                } else if (newProgress == 100){
                    String progress = newProgress + "%";
                    loading.setText(progress);
                }
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
      if(keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()){
          mWebView.goBack();
          return true;
      }
      return false;
    }

    @Override
    protected void onDestroy() {
        if(mWebView != null){
            mWebView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}