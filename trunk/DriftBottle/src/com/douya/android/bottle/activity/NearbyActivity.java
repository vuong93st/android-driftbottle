package com.douya.android.bottle.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.LinearLayout;

import com.douya.android.R;
import com.douya.android.bottle.activity.component.AlwaysMarqueeTextView;
import com.douya.android.core.dao.DatabaseHelper;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class NearbyActivity extends MapActivity{
	private AlwaysMarqueeTextView weatherTextView = null;
	String weatherCurrent = "";
	DatabaseHelper dbHelper; 
	SQLiteDatabase sqliteDatabase;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nearby);
		dbHelper = new DatabaseHelper(NearbyActivity.this, "bottle_db"); 
		sqliteDatabase = dbHelper.getReadableDatabase(); 
		
		handler.post(updateUIThread);
		
		Uri uri = Uri.parse("geo:38.899533,-77.036476"); 
		Intent intent = new Intent(Intent.ACTION_VIEW,uri); 
		startActivity(intent);
		
		MapView mapView=(MapView)this.findViewById(R.id.mapView);
        LinearLayout zoomLayout=(LinearLayout)this.findViewById(R.id.zoom);
        View zoomView=mapView.getZoomControls();
        zoomLayout.addView(zoomView);
        mapView.displayZoomControls(true);

	}

 
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}


	Handler handler = new Handler();
	/**
	 * 更新天气界面UI
	 */
	Runnable updateUIThread = new Runnable() {

		public void run() {
			weatherCurrent="";
			// 获取天气预报
			if(sqliteDatabase==null)return;
			Cursor cursor = sqliteDatabase.query("weather", null, null, null, null, null, null);
			if (cursor.moveToNext()) {
				weatherCurrent += cursor.getString(cursor.getColumnIndex("current"));
            }
			weatherTextView = (AlwaysMarqueeTextView) findViewById(R.id.app_weather_content);
			weatherTextView.setText(weatherCurrent);
			weatherTextView.setTransformationMethod(SingleLineTransformationMethod.getInstance());
			weatherTextView.setFocusable(true);
			handler.postDelayed(updateUIThread, 1000*60*5);
		}
	};
}
