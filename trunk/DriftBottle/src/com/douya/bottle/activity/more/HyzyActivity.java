package com.douya.bottle.activity.more;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.SingleLineTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

import com.douya.android.R;
import com.douya.bottle.activity.component.AlwaysMarqueeTextView;
import com.douya.bottle.model.Weather;

public class HyzyActivity extends TabActivity {
	
	private AlwaysMarqueeTextView weatherTextView = null;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hyzy);
		final TabHost tabHost = this.getTabHost();
		final TabWidget tabWidget = tabHost.getTabWidget();
		Intent intent = new Intent();
		createTabs(tabHost, intent, HyzyUserInfoActivity.class, "user_info", composeLinearLayout("漠漠然", R.drawable.face4));
		intent = new Intent();
		createTabs(tabHost, intent, HyzyLabelActivity.class, "label_number", composeLayout(getResources().getString(R.string.label_number), R.drawable.face_bg));
		intent = new Intent();
		createTabs(tabHost, intent, HyzyFriendActivity.class, "friend_number", composeLayout(getResources().getString(R.string.friend_number), R.drawable.face_bg));
		intent = new Intent();
		createTabs(tabHost, intent, HyzyBottleActivity.class, "bottle_number", composeLayout(getResources().getString(R.string.bottle_number), R.drawable.face_bg));

		tabHost.setCurrentTab(1);
        for(int i = 1; i < tabWidget.getChildCount(); i++)   
        {
            View v = tabWidget.getChildAt(i);   
            if (tabHost.getCurrentTab() == i) {
                v.setBackgroundDrawable(getResources().getDrawable(R.drawable.home_tab_bg));   
            } else {     
                v.setBackgroundDrawable(getResources().getDrawable(R.drawable.home_tab_null_bg));  
            }   
        }
		// 设置Tab变换时的监听事件
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			public void onTabChanged(String tabId) {
				if(tabHost.getCurrentTab()==0)tabHost.setCurrentTab(1);
				// 当点击tab选项卡的时候，更改当前的背景
				for (int i = 0; i < tabWidget.getChildCount(); i++) {
					View v = tabWidget.getChildAt(i);
					if (tabHost.getCurrentTab() == i) {
						v.setBackgroundDrawable(getResources().getDrawable(R.drawable.home_tab_bg));
					} else {
						v.setBackgroundDrawable(getResources().getDrawable(R.drawable.home_tab_null_bg));
					}
				}
				handler.post(updateUIThread);
			}
		});
		handler.post(updateUIThread);
	}
	Handler handler = new Handler();

	/**
	 * 更新天气界面UI
	 */
	Runnable updateUIThread = new Runnable() {

		public void run() {
			weatherTextView = (AlwaysMarqueeTextView) findViewById(R.id.app_weather_content);
			weatherTextView.setText(Weather.Current);
			weatherTextView.setTransformationMethod(SingleLineTransformationMethod.getInstance());
			weatherTextView.setFocusable(true);
			handler.postDelayed(updateUIThread, 1000*60*5);
		}
	};

	/**
	 * 这个设置Tab标签本身的布局，需要TextView和ImageView不能重合
	 * s:是文本显示的内容
	 * i:是ImageView的图片位置 将它设置到setIndicator(composeLayout("首页", R.drawable.coke))中
	 */
	public View composeLayout(String s, int i) {
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundResource(R.drawable.home_tab_null_bg);
		TextView iv = new TextView(this);
		iv.setBackgroundResource(i);
		iv.setText(s);
		iv.setTextColor(Color.WHITE);
		iv.setGravity(Gravity.CENTER);
		layout.addView(iv, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

		return layout;
	}

	public View composeLinearLayout(String s, int i){  
        LinearLayout layout = new LinearLayout(this);   
        layout.setOrientation(LinearLayout.VERTICAL);   
        ImageView iv = new ImageView(this);   
        iv.setImageResource(i);   
        layout.addView(iv,    
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)); 
        TextView tv = new TextView(this);   
        tv.setGravity(Gravity.CENTER);   
        tv.setSingleLine(true);   
        tv.setText(s);   
        tv.setTextColor(Color.WHITE);   
       layout.addView(tv,    
               new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));   
             
        return layout;   
    }
	/**
	 * 创建Tab页
	 * 
	 * @param tabHost
	 * @param intent
	 * @param cls
	 * @param name
	 * @param view
	 */
	public void createTabs(TabHost tabHost, Intent intent, Class<?> cls, String name, View view) {
		intent.setClass(this, cls);
		TabHost.TabSpec spec = tabHost.newTabSpec(name);
		spec.setIndicator(view);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}
}
