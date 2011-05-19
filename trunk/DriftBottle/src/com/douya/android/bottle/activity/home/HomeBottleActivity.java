package com.douya.android.bottle.activity.home;

import java.util.ArrayList;
import java.util.List;

import android.R.bool;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.douya.android.R;
import com.douya.android.bottle.model.BottleIcon;
import com.douya.android.bottle.model.BottleItem;
import com.douya.android.bottle.model.BottleType;

public class HomeBottleActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bottle);
		TextView tv = (TextView)findViewById(R.id.home_bottle_bottom_tv);
		ImageButton bottleNewBtn = (ImageButton)findViewById(R.id.start_new_bottle_btn);
		
		 //创建一个 SpannableString对象   
        SpannableString sp = new SpannableString(getResources().getString(R.string.home_bottle_bottom));  
        //设置高亮  
        sp.setSpan(new ForegroundColorSpan(Color.CYAN),4,10,Spannable.SPAN_EXCLUSIVE_INCLUSIVE); 
        //设置超链接   
        //sp.setSpan(new URLSpan("http://www.baidu.com"), 5, 7,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);    
        //设置高亮样式一   
        //sp.setSpan(new BackgroundColorSpan(Color.RED), 17 ,19,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
        //设置斜体   
        //sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 27, 29, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);         
        
        tv.setText(sp);    
        //设置TextView可点击   
        tv.setMovementMethod(LinkMovementMethod.getInstance());    

        bottleNewBtn.setOnClickListener(new NewBottle());
        
		RelativeLayout no_bottle_layout = (RelativeLayout)findViewById(R.id.no_bottle_layout);
		RelativeLayout have_bottle_layout = (RelativeLayout)findViewById(R.id.have_bottle_layout);
		
		int bottleCount = 1;//瓶子数
		
		if(bottleCount == 0){
			no_bottle_layout.setVisibility(View.VISIBLE);//无数据时显示
			have_bottle_layout.setVisibility(View.GONE);
		}else{
			no_bottle_layout.setVisibility(View.GONE);
			have_bottle_layout.setVisibility(View.VISIBLE);//无数据时不显示，有数据时显示
			ListView lvItems = (ListView)findViewById(R.id.lvItems);
			List<BottleItem> list = new ArrayList<BottleItem>();
			
			BottleItem myItem = new BottleItem(0, "Fiyo的标签瓶","装有3个标签", BottleIcon.TAG_BOTTLE_ICON,BottleType.TAG_BOTTLE,
					"今天");
			list.add(myItem);
			myItem = new BottleItem(1, "Fiyo的祝愿瓶","愿天下所有父母健康长寿", 
					BottleIcon.WISH_BOTTLE_ICON,BottleType.WISH_BOTTLE,
					"昨天");
			list.add(myItem);
			myItem = new BottleItem(2, "Fiyo的传递瓶","春妮你在哪里？", BottleIcon.TRANSFER_BOTTLE_ICON,BottleType.TRANSFER_BOTTLE,
					"前天");
			list.add(myItem);
			HomeBottleListItemAdapter myAdapter = new HomeBottleListItemAdapter(getLayoutInflater(), list);
			lvItems.setAdapter(myAdapter);
		}
	}

	class NewBottle implements OnClickListener{

		public void onClick(View v) {
			Intent intent =new Intent();
			intent.setClass(HomeBottleActivity.this, HomeBottleNewActivity.class);
			HomeBottleActivity.this.startActivity(intent);
		}
	}
}
