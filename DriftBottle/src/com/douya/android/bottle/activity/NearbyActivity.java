package com.douya.android.bottle.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.SingleLineTransformationMethod;

import com.douya.android.R;
import com.douya.android.bottle.activity.component.AlwaysMarqueeTextView;
import com.douya.android.core.dao.DatabaseHelper;

public class NearbyActivity extends Activity{
	private AlwaysMarqueeTextView weatherTextView = null;
	String weatherCurrent = "";
	DatabaseHelper dbHelper; 
	SQLiteDatabase sqliteDatabase;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nearby);
		
		handler.post(updateUIThread);
		
		/*Uri uri = Uri.parse("geo:38.899533,-77.036476"); 
		Intent intent = new Intent(Intent.ACTION_VIEW,uri); 
		startActivity(intent);
		
		MapView mapView=(MapView)this.findViewById(R.id.mapView);
        LinearLayout zoomLayout=(LinearLayout)this.findViewById(R.id.zoom);
        View zoomView=mapView.getZoomControls();
        zoomLayout.addView(zoomView);
        mapView.displayZoomControls(true);*/

	}


	Handler handler = new Handler();
	/**
	 * 更新天气界面UI
	 */
	Runnable updateUIThread = new Runnable() {

		public void run() {
			weatherCurrent="";
			// 获取天气预报
			dbHelper = new DatabaseHelper(NearbyActivity.this, "bottle_db"); 
			sqliteDatabase = dbHelper.getReadableDatabase(); 
			Cursor cursor = sqliteDatabase.query("weather", null, null, null, null, null, null);
			if (cursor.moveToNext()) {
				weatherCurrent += cursor.getString(cursor.getColumnIndex("current"));
            }
			cursor.close();
			sqliteDatabase.close();
			weatherTextView = (AlwaysMarqueeTextView) findViewById(R.id.app_weather_content);
			weatherTextView.setText(weatherCurrent);
			weatherTextView.setTransformationMethod(SingleLineTransformationMethod.getInstance());
			weatherTextView.setFocusable(true);
			handler.postDelayed(updateUIThread, 1000*60*5);
		}
	};
}
