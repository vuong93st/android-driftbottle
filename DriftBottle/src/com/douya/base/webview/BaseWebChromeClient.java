package com.douya.base.webview;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class BaseWebChromeClient extends WebChromeClient {
	private Activity activity;
	private Handler handler;

	public BaseWebChromeClient() {
	}

	public BaseWebChromeClient(Activity ctx, Handler handler) {
		super();
		this.activity = ctx;
		this.handler = handler;
	}

	/**
	 *  处理javascript中的alert
	 */
	@Override
	public boolean onJsAlert(WebView view, String url, String message,
			final JsResult result) {
		// 构建一个Builder来显示网页中的对话框
		Builder builder = new Builder(activity);
		builder.setTitle(message);
		builder.setPositiveButton(android.R.string.ok,
				new AlertDialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 点击网页中的确定按钮之后,继续执行网页中的操作
						result.confirm();
					}
				});
		builder.setNegativeButton(android.R.string.cancel,
				new AlertDialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 点击网页中的"取消"按钮之后,取消执行网页中的操作
						result.cancel();
					}
				});
		builder.setCancelable(false);
		builder.create();
		builder.show();
		return true;
	}

	/**
	 *  处理javascript中的confirm
	 */
	@Override
	public boolean onJsConfirm(WebView view, String url, String message,
			final JsResult result) {
		Builder builder = new Builder(activity);
		builder.setTitle("操作提示");
		builder.setMessage(message);
		builder.setPositiveButton(android.R.string.ok,
				new AlertDialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						result.confirm();
					}

				});
		builder.setNegativeButton(android.R.string.cancel,
				new AlertDialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						result.cancel();
					}

				});
		builder.setCancelable(false);
		builder.create();
		builder.show();
		return true;
	}

	// 处理javascript中的prompt
	// message为网页中对话框的提示内容
	// defaultValue为没有输入时默认显示的内容
	@Override
	public boolean onJsPrompt(WebView view, String url, String message,
			String defaultValue, final JsPromptResult result) {
		return true;
	}

	/**
	 *  设置网页加载的进度条
	 */
	@Override
	public void onProgressChanged(WebView view, int newProgress) {
		Log.i("onProgressChanged", "newProgress=" + newProgress);
		super.onProgressChanged(view, newProgress);
	}

	/**
	 *  设置应用程序的标题title
	 */
	@Override
	public void onReceivedTitle(WebView view, String title) {
		activity.setTitle(title);
		super.onReceivedTitle(view, title);
	}
}
