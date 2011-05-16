package com.douya.android.bottle.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.douya.android.R;

public class HomeFriendActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend);
		TextView tv = (TextView)findViewById(R.id.home_find_friend_tv);
		
		 //创建一个 SpannableString对象   
        SpannableString sp = new SpannableString(getResources().getString(R.string.home_find_friend));  
        //设置高亮  
        sp.setSpan(new ForegroundColorSpan(Color.CYAN),2,8,Spannable.SPAN_EXCLUSIVE_INCLUSIVE); 
     
        tv.setText(sp);    
        //设置TextView可点击   
        tv.setMovementMethod(LinkMovementMethod.getInstance()); 
	}

}
