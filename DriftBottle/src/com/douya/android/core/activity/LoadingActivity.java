package com.douya.android.core.activity;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.douya.android.R;
import com.douya.android.bottle.DriftBottle;

public class LoadingActivity extends Activity{
	
	private TextView loadingTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		
		loadingTextView = (TextView)findViewById(R.id.loading_info_tv);
		Thread thread = new Thread(update);
		thread.start();
	}
	
	Runnable update = new Runnable() {
		
		public void run() {
			try{
				Thread.sleep(2000);
				Intent intent = new Intent();
				intent.setClass(LoadingActivity.this, DriftBottle.class);
				startActivity(intent);
				LoadingActivity.this.finish();
			}catch(Exception e){
				
			}
		}
	};
}
