package com.douya.android.bottle.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
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
		
		
		 //创建一个 SpannableString对象   
        SpannableString sp = new SpannableString(getResources().getString(R.string.home_bottle_bottom));    
        //设置超链接   
        //sp.setSpan(new URLSpan("http://www.baidu.com"), 5, 7,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);    
        //设置高亮样式一   
        //sp.setSpan(new BackgroundColorSpan(Color.RED), 17 ,19,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);    
        //设置高亮样式二   
        sp.setSpan(new ForegroundColorSpan(Color.CYAN),4,10,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);      
        //设置斜体   
        //sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 27, 29, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);    
 
        tv.setText(sp);    
        //设置TextView可点击   
        tv.setMovementMethod(LinkMovementMethod.getInstance());    


	}

}
