package com.douya.bottle.activity;

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

import com.douya.bottle.R;
import com.douya.utils.AlwaysMarqueeTextView;
import com.douya.utils.WeatherUtils;

public class MainActivity extends TabActivity{
	private AlwaysMarqueeTextView weatherTextView = null;
	String weatherCurrent="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		handler.post(updateThread);
		
		final TabHost tabHost = this.getTabHost();   
        final TabWidget tabWidget = tabHost.getTabWidget(); 
		
        Intent homeIntent = new Intent();
		createTabs(tabHost,homeIntent,HomeActivity.class,"home",composeLayout(getResources().getString(R.string.home),R.drawable.bottle_home));
		Intent nearbyIntent = new Intent();
		createTabs(tabHost,nearbyIntent,NearbyActivity.class,"nearby",composeLayout(getResources().getString(R.string.nearby),R.drawable.bottle_nearby));
		Intent squareIntent = new Intent();
		createTabs(tabHost,squareIntent,SquareActivity.class,"square",composeLayout(getResources().getString(R.string.square),R.drawable.bottle_square));
		Intent moreIntent = new Intent();
		createTabs(tabHost,moreIntent,MoreActivity.class,"more",composeLayout(getResources().getString(R.string.more),R.drawable.bottle_more));
		
		//这是对Tab标签本身的设置   
        int width =45;   
        int height =50;   
        for(int i = 0; i < tabWidget.getChildCount(); i++)   
        {   
           //设置高度、宽度，不过宽度由于设置为fill_parent，在此对它没效果   
            tabWidget.getChildAt(i).getLayoutParams().height = height;   
            tabWidget.getChildAt(i).getLayoutParams().width = width;   
             /**   
             * 下面是设置Tab的背景，可以是颜色，背景图片等   
             */   
            View v = tabWidget.getChildAt(i);   
            if (tabHost.getCurrentTab() == i) {
                v.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_icons));   
            } else {     
                v.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab));  
            }   
        }
		 //设置Tab变换时的监听事件   
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			public void onTabChanged(String tabId) {
				// 当点击tab选项卡的时候，更改当前的背景
				for (int i = 0; i < tabWidget.getChildCount(); i++) {
					View v = tabWidget.getChildAt(i);
					if (tabHost.getCurrentTab() == i) {
						v.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_icons)); 
					} else {
						v.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab)); 
					}
					handler.post(updateUIThread);
				}
			}
		}); 
		
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

        TextView tv = new TextView(this);   
        tv.setGravity(Gravity.CENTER);   
        tv.setSingleLine(true);   
        tv.setText(s);   
        tv.setTextColor(Color.WHITE);   
       layout.addView(tv,    
               new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));   
           
        ImageView iv = new ImageView(this);   
        iv.setImageResource(i);   
        layout.addView(iv,    
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));   
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
		TabHost.TabSpec squareSpec = tabHost.newTabSpec(name);
		squareSpec.setIndicator(view);
		squareSpec.setContent(intent);
		tabHost.addTab(squareSpec);
    }
    
   Handler handler = new Handler();
   Runnable updateThread = new Runnable(){
	
		public void run() {
			//获取天气预报
			WeatherUtils weatherUtils = new WeatherUtils();
			weatherUtils.getWeather("济南");
			weatherCurrent+=weatherUtils.getWeatherCurrent()!=null?weatherUtils.getWeatherCurrent()+"      " :"无法连接到天气预报服务器，暂时无法获取天气预报！" ;
			weatherCurrent+=weatherUtils.getWeatherTomorrow()!=null?weatherUtils.getWeatherTomorrow()+"      " :"" ;
			weatherCurrent+=weatherUtils.getWeatherAfterday()!=null?weatherUtils.getWeatherAfterday()+"      " :"" ;
			handler.post(updateUIThread);
			handler.postDelayed(updateThread, 1000*60*60);
		}
	};

	Runnable updateUIThread = new Runnable(){
		
		public void run() {
			//获取天气预报
			weatherTextView = (AlwaysMarqueeTextView)findViewById(R.id.app_weather_content);
			weatherTextView.setText(weatherCurrent);
			weatherTextView.setTransformationMethod(SingleLineTransformationMethod.getInstance());
			weatherTextView.setFocusable(true);
			handler.postDelayed(updateThread, 1000*60*5);
		}
	};
}
