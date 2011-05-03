package com.douya.android.bottle.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.douya.android.R;

public class HomeBottleActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bottle);
		TextView tv = (TextView)findViewById(R.id.home_bottle_content_tv);
		/*Typeface face = Typeface.createFromAsset (getAssets() , "fonts/msyh.ttf");
		tv.setTypeface (face);*/
	}

}
