package com.douya.bottle.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

import com.douya.bottle.R;

public class MainActivity extends TabActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		final TabHost tabHost = this.getTabHost();   
        final TabWidget tabWidget = tabHost.getTabWidget(); 
		Resources res = getResources();
		
		Intent homeIntent = new Intent();
		homeIntent.setClass(this, HomeActivity.class);
		TabHost.TabSpec spec = tabHost.newTabSpec("home");
		spec.setIndicator(composeLayout("首页",R.drawable.bottle_home));
		spec.setContent(homeIntent);
		tabHost.addTab(spec);
		
		Intent nearbyIntent = new Intent();
		nearbyIntent.setClass(this, NearbyActivity.class);
		TabHost.TabSpec nearbySpec = tabHost.newTabSpec("nearby");
		nearbySpec.setIndicator(composeLayout("附近",R.drawable.bottle_nearby));
		nearbySpec.setContent(nearbyIntent);
		tabHost.addTab(nearbySpec);
		
		Intent squareIntent = new Intent();
		squareIntent.setClass(this, SquareActivity.class);
		TabHost.TabSpec squareSpec = tabHost.newTabSpec("square");
		squareSpec.setIndicator(composeLayout("广场",R.drawable.bottle_square));
		squareSpec.setContent(squareIntent);
		tabHost.addTab(squareSpec);
		
		Intent moreIntent = new Intent();
		moreIntent.setClass(this, MoreActivity.class);
		TabHost.TabSpec moreSpec = tabHost.newTabSpec("more");
		moreSpec.setIndicator(composeLayout("更多",R.drawable.bottle_more));
		moreSpec.setContent(moreIntent);
		tabHost.addTab(moreSpec);
		
		//这是对Tab标签本身的设置   
        int width =45;   
        int height =48;   
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
                //v.setBackgroundColor(Color.GREEN);   
                //在这里最好自己设置一个图片作为背景更好   
                v.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_icons));   
            } else {   
                v.setBackgroundColor(Color.GRAY);   
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
						// 这里最后需要和上面的设置保持一致,也可以用图片作为背景最好
						v.setBackgroundColor(Color.GRAY);
					}
				}
			}
		});  
	}

	/**   
     * 这个设置Tab标签本身的布局，需要TextView和ImageView不能重合   
     * s:是文本显示的内容   
     * i:是ImageView的图片位置   
     * 将它设置到setIndicator(composeLayout("开心", R.drawable.coke))中   
     */   
    public View composeLayout(String s, int i){   
        Log.e("Error", "composeLayout");   
        LinearLayout layout = new LinearLayout(this);   
        layout.setOrientation(LinearLayout.VERTICAL);   

        TextView tv = new TextView(this);   
        tv.setGravity(Gravity.CENTER);   
        tv.setSingleLine(true);   
        tv.setText(s);   
        tv.setTextColor(Color.RED);   
       layout.addView(tv,    
               new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));   
           
        ImageView iv = new ImageView(this);   
        iv.setImageResource(i);   
        layout.addView(iv,    
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));   
        return layout;   
    }  

}
