package com.douya.android.bottle.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.douya.android.R;

public class HomeBottleActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bottle);
		TextView tv = (TextView)findViewById(R.id.home_bottle_bottom_tv);
		tv.setText(Html.fromHtml(getResources().getString(R.string.home_bottle_bottom)));
		

	}

}
