package com.example.gjy.misi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.KeyEvent;
import android.os.Handler;
import android.widget.LinearLayout;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private View view;
    private SwipeRefreshLayout swipeLayout;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        //状态蓝颜色
        back();
        //添加javaScript支持
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(0);
        WebSettings webSettings = webView.getSettings();
        webSettings.setAllowFileAccess(true);//支持访问文件
        webSettings.setBuiltInZoomControls(true);//支持缩放
        webView.loadUrl("http://115.159.209.152/");
        //安卓开发之设置webview的浏览器标识（User-Agent）
        String ua = webView.getSettings().getUserAgentString();
        webView.getSettings().setUserAgentString(ua.replace("Android", "HFWSH_USER Android"));
        //触摸焦点起作用
        webView.requestFocus();
        //取消滚动条
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        //刷新
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();  //刷新
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        swipeLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        //当网页被点击的时候
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverUrlLoading(final WebView view, final String url) {
                view.loadUrl(url);
                return true;
            }
        });
     //返回上个页面
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
    // Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //状态栏颜色
    public void back(){
        if (android.os.Build.VERSION.SDK_INT > 18) {

            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            // 创建状态栏的管理实例
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // 激活状态栏设置
            tintManager.setStatusBarTintEnabled(true);
            // 激活导航栏设置
            tintManager.setNavigationBarTintEnabled(true);
            // 设置一个颜色给系统栏
            tintManager.setTintColor(Color.parseColor("#000000"));
        }
    }
}
