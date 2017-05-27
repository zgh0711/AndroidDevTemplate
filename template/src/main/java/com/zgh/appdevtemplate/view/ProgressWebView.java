package com.zgh.appdevtemplate.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.zgh.appdevtemplate.R;
import com.zgh.appdevtemplate.view.dialog.LoadingDialogUtils;


/**
 * Created by ZGH on 2016/7/19.
 * 顶部带进度条的 webview
 */
public class ProgressWebView extends WebView {
    private ProgressBar mProgressBar;

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 8);
        mProgressBar.setLayoutParams(layoutParams);

        Drawable drawable = context.getResources().getDrawable(R.drawable.web_progress_bar_states);
        mProgressBar.setProgressDrawable(drawable);
        addView(mProgressBar);
        webViewSettings();
        // TODO: 下面的二个方法都是让 WebView 显示加载进度的，实际使用时选用一个即可，也可以二个都不用
        setWebViewClient(new WebViewClient());
        setWebChromeClient(new WebChromeClient());
    }

    private void webViewSettings() {
        WebSettings settings = this.getSettings();
        //使 WebView 能够与 JS 交互，不需要的话应注释钓
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        //自适应屏幕,通过设置这两个值，能够保证对 PC 等宽的页面也能良好显示。
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //缓存策略,建议在实际开发的时候，使用LOAD_NO_CACHE，来避免一些缓存相关的bug。
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //Webiew保留缩放功能但是隐藏缩放控件(这是一招必杀技)
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        //解决app中部分页面非https导致的问题
        //设置当一个安全站点企图加载来自一个不安全站点资源时WebView的行为,
        //在这种模式下,WebView将允许一个安全的起源从其他来源加载内容，即使那是不安全的.
        //如果app需要安全性比较高，不应该设置此模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    /**
     * 在开始加载网页时显示一个带遮罩层的加载动画，加载完成关闭
     */
    private class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            LoadingDialogUtils.showLoadingDialog();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            LoadingDialogUtils.cancelLoadingDialog();
            // 关闭图片加载阻塞
            view.getSettings().setBlockNetworkImage(false);
            // TODO: 调用 JS 代码，这是一个例子，根据需要修改
            view.evaluateJavascript(
                    "document.getElementsByTagName('header')[0].style.display ='none'",
                    new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                        }
                    });
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();// 接受所有网站的证书,解决 HTTPS 的证书问题
        }
    }

    /**
     * 判断页面加载过程，onProgressChanged 是在 WebChromeClient 类中的方法，需要手动重写
     * 加载时在页面顶部以一条直线的形式显示加载进度
     */
    private class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(android.webkit.WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressBar.setVisibility(GONE);
            } else {
                if (mProgressBar.getVisibility() == GONE) {
                    mProgressBar.setVisibility(VISIBLE);
                }
                mProgressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) mProgressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        mProgressBar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
