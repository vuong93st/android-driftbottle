package com.douya.base.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.douya.R;

public class BaseWebViewClient extends WebViewClient {
	private static String TAG = "RNWebViewClient";
	private Context context = null;
	private Handler handler = null;

	// 记录当前WebView中的页面

	public BaseWebViewClient() {
	}

	public BaseWebViewClient(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;
	}

	@Override
	public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
		System.out.println("shouldOverrideUrlLoading,url=" + url);
		view.loadUrl(url);
		return true;
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		super.onPageStarted(view, url, favicon);
		handler.sendEmptyMessage(R.id.start_loading);
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
		handler.sendEmptyMessage(R.id.completed);
	}
}
