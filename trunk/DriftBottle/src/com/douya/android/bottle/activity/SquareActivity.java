package com.douya.android.bottle.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.SingleLineTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

import com.douya.android.R;
import com.douya.android.bottle.activity.component.AlwaysMarqueeTextView;
import com.douya.android.core.dao.DatabaseHelper;

public class SquareActivity extends TabActivity{
	private AlwaysMarqueeTextView weatherTextView = null;
	String weatherCurrent = "";
	DatabaseHelper dbHelper; 
	SQLiteDatabase sqliteDatabase;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.square); 
		final TabHost tabHost = this.getTabHost();
		final TabWidget tabWidget = tabHost.getTabWidget();
		Intent squareWonderfulIntent = new Intent();
		createTabs(
				tabHost,
				squareWonderfulIntent,
				SquareWonderfulActivity.class,
				"wonderful",
				composeLayout("精彩",
						R.drawable.square_top_bg1));
		Intent squareLatestIntent = new Intent();
		createTabs(
				tabHost,
				squareLatestIntent,
				SquareLatestActivity.class,
				"latest",
				composeLayout("最新",
						R.drawable.square_top_bg2));	
		tabHost.setCurrentTab(0);//设置初始激活的Activity
        for(int i = 0; i < tabWidget.getChildCount(); i++)   
        { 
            View v = tabWidget.getChildAt(i);   
    		System.out.println(tabHost.getCurrentTab());
            if (tabHost.getCurrentTab() == i) {
                v.setBackgroundDrawable(getResources().getDrawable(R.drawable.square_top_bg1));   
            } else {     
                v.setBackgroundDrawable(getResources().getDrawable(R.drawable.square_top_bg2));  
            }   
        }
     // 设置Tab变换时的监听事件
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			public void onTabChanged(String tabId) {
				// 当点击tab选项卡的时候，更改当前的背景
				for (int i = 0; i < tabWidget.getChildCount(); i++) {
					View v = tabWidget.getChildAt(i);
					if (tabHost.getCurrentTab() == i) {
						v.setBackgroundDrawable(getResources().getDrawable(R.drawable.square_top_bg1));
					} else {
						v.setBackgroundDrawable(getResources().getDrawable(R.drawable.square_top_bg2));
					}
				}
				handler.post(updateUIThread);
			}
		});
		handler.post(updateUIThread);
	}
	
	/**   
     * 这个设置Tab标签本身的布局，需要TextView和ImageView不能重合   
     * s:是文本显示的内容   
     * i:是ImageView的图片位置   
     * 将它设置到setIndicator(composeLayout("首页", R.drawable.coke))中   
     */   
    public View composeLayout(String s, int i){  
    	LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
//		layout.setBackgroundResource(i);
		TextView iv = new TextView(this);
		//iv.setBackgroundResource(i);
		iv.setText(s);
		iv.setTextColor(Color.GRAY);
		iv.setGravity(Gravity.CENTER);
		layout.addView(iv, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 30));

		return layout;
    }
	 /**
     * 创建Tab页
     * @param tabHost
     * @param intent
     * @param cls
     * @param name
     * @param view
     */
    public void createTabs(TabHost tabHost,Intent intent,Class<?> cls,String name,View view){
    	intent.setClass(this, cls);
		TabHost.TabSpec spec = tabHost.newTabSpec(name);
		spec.setIndicator(view);
		spec.setContent(intent);
		tabHost.addTab(spec);
    }
	Handler handler = new Handler();
	/**
	 * 更新天气界面UI
	 */
	Runnable updateUIThread = new Runnable() {

		public void run() {
			weatherCurrent="";
			// 获取天气预报
			dbHelper = new DatabaseHelper(SquareActivity.this, "bottle_db"); 
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
