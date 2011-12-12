package com.douya.bottle.activity.more;

import com.douya.android.R;
import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class WdplActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wdpl);
		TextView wdplTitle = (TextView)findViewById(R.id.wdplTitle);
		wdplTitle.setText(R.string.moreitem2);
		TextView wdpl2Title2 = (TextView)findViewById(R.id.wdpl2Title2);		
		 //创建一个 SpannableString对象   
       SpannableString sp = new SpannableString(getResources().getString(R.string.wdpl_2_title2));  
       //设置高亮  
       sp.setSpan(new ForegroundColorSpan(Color.CYAN),1,5,Spannable.SPAN_EXCLUSIVE_INCLUSIVE); 
		wdpl2Title2.setText(sp);    
       //设置TextView可点击   
		wdpl2Title2.setMovementMethod(LinkMovementMethod.getInstance()); 
	}

}
