package com.douya.bottle.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.douya.bottle.R;
import com.douya.utils.AlwaysMarqueeTextView;
import com.douya.utils.DatabaseHelper;

public class MoreActivity extends ListActivity{
	private AlwaysMarqueeTextView weatherTextView = null;
	String weatherCurrent="";
	DatabaseHelper dbHelper; 
	SQLiteDatabase sqliteDatabase;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);
		dbHelper = new DatabaseHelper(MoreActivity.this, "bottle_db"); 
		sqliteDatabase = dbHelper.getReadableDatabase(); 
		
		handler.post(updateUIThread);
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		HashMap<String, Object> map3 = new HashMap<String, Object>();
		HashMap<String, Object> map4 = new HashMap<String, Object>();
		HashMap<String, Object> map5 = new HashMap<String, Object>();
		HashMap<String, Object> map6 = new HashMap<String, Object>();
		HashMap<String, Object> map7 = new HashMap<String, Object>();
		map1.put("moreLogo", R.drawable.face);
		map1.put("moreTitle", getString(R.string.moreitem1));
		map1.put("moreMore", R.drawable.bottle_arrow);
		map2.put("moreLogo", R.drawable.face1);
		map2.put("moreTitle", getString(R.string.moreitem2));
		map2.put("moreMore", R.drawable.bottle_arrow);
		map3.put("moreLogo", R.drawable.face2);
		map3.put("moreTitle", getString(R.string.moreitem3));
		map3.put("moreMore", R.drawable.bottle_arrow);
		map4.put("moreLogo", R.drawable.face3);
		map4.put("moreTitle", getString(R.string.moreitem4));
		map4.put("moreMore", R.drawable.bottle_arrow);
		map5.put("moreLogo", R.drawable.face4);
		map5.put("moreTitle", getString(R.string.moreitem5));
		map5.put("moreMore", R.drawable.bottle_arrow);
		map6.put("moreLogo", R.drawable.face);
		map6.put("moreTitle", getString(R.string.moreitem6));
		map6.put("moreMore", R.drawable.bottle_arrow);
		map7.put("moreLogo", R.drawable.face1);
		map7.put("moreTitle", getString(R.string.moreitem7));
		map7.put("moreMore", R.drawable.bottle_arrow);
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		list.add(map6);
		list.add(map7);
		SimpleAdapter sap = new SimpleAdapter(this, list, R.layout.moreitem,
				new String[] { "moreLogo", "moreTitle", "moreMore" }, new int[] {
						R.id.moreLogo, R.id.moreTitle, R.id.moreMore });
		setListAdapter(sap);
	}
	/**
	 * ListView单击事件方法
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
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
				weatherCurrent += cursor.getString(cursor.getColumnIndex("today")); 
				weatherCurrent += cursor.getString(cursor.getColumnIndex("tomorrow")); 
				weatherCurrent += cursor.getString(cursor.getColumnIndex("afterday")); 
            }   
			weatherTextView = (AlwaysMarqueeTextView) findViewById(R.id.app_weather_content);
			weatherTextView.setText(weatherCurrent);
			weatherTextView.setTransformationMethod(SingleLineTransformationMethod.getInstance());
			weatherTextView.setFocusable(true);
			handler.postDelayed(updateUIThread, 1000 * 60 * 5);
		}
	};

}
