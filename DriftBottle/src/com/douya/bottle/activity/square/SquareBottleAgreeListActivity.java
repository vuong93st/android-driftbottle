package com.douya.bottle.activity.square;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.douya.android.R;

public class SquareBottleAgreeListActivity extends Activity{
	RelativeLayout relativeLayout5 = null;
	Button askBtn  = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bottleagreelist);
		TextView titleTextView = (TextView)findViewById(R.id.titleTextView);
		askBtn = (Button)findViewById(R.id.askBtn);
		relativeLayout5 = (RelativeLayout)findViewById(R.id.relativeLayout5);
		ListView ls1 = (ListView)findViewById(R.id.listView1);
		ListView ls2 = (ListView)findViewById(R.id.listView2);
		 //创建一个 SpannableString对象   
        SpannableString sp = new SpannableString("王者归来的漂流瓶从深圳市起程,开始了漫长的征程.     深圳市  2011-05-14");  
        //设置高亮  
        sp.setSpan(new ForegroundColorSpan(Color.CYAN),0,8,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(Color.CYAN),9,12,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        titleTextView.setText(sp);   
        //创建一个 SpannableString对象   
        SpannableString sp1 = new SpannableString(getString(R.string.pl_content_textview));  
        //设置高亮  
        sp1.setSpan(new ForegroundColorSpan(Color.CYAN),0,6,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //ListView Start
        ArrayList<HashMap<String, String>> list1 = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();
		map1.put("plContentTextView", sp1.toString());
		map1.put("plHfBtn", getString(R.string.huifu));
		map2.put("plContentTextView",sp1.toString());
		map2.put("plHfBtn", getString(R.string.huifu));
		list1.add(map1);
		list1.add(map2);
        SimpleAdapter sap1 = new SimpleAdapter(this, list1, R.layout.bottleagreelistitem1,
				new String[] { "plContentTextView", "plHfBtn"}, new int[] {
						R.id.plContentTextView, R.id.plHfBtn});
		ls1.setAdapter(sap1);
        ArrayList<HashMap<String, Object>> list2 = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map3 = new HashMap<String, Object>();
		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map3.put("titTextView1", "被  幸福瓶子  捡到");
		map3.put("titTextView2", "上海市 两天前");
		map3.put("headImage1", R.drawable.head_01);
		map3.put("contentTextView1", "那一年，冠希还没有相机。 那一年，李刚还没有儿子。 那一年，师太不会和和尚抢老道...");
		map3.put("askBtn1", "评论(4)");
		map4.put("titTextView1", "被  幸福瓶子  捡到");
		map4.put("titTextView2", "北京市 两天前");
		map4.put("headImage1", R.drawable.head_01);
		map4.put("contentTextView1", "那一年，冠希还没有相机。 那一年，李刚还没有儿子...");
		map4.put("askBtn1", "评论(4)");
		list2.add(map3);
		list2.add(map4);
		SimpleAdapter sap2 = new SimpleAdapter(this, list2, R.layout.bottleagreelistitem2,
				new String[] { "titTextView1", "titTextView2", "headImage1", "contentTextView1","askBtn1"}, new int[] {
						R.id.titTextView1, R.id.titTextView2,R.id.headImage1,R.id.contentTextView1,R.id.askBtn1});
		ls2.setAdapter(sap2);
		//ListView End
		askBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				System.out.println(relativeLayout5.getVisibility());
				if(relativeLayout5.getVisibility()==android.view.View.GONE){
					relativeLayout5.setVisibility(android.view.View.VISIBLE);
					askBtn.setText("收起评论(4)");
				}
				else if(relativeLayout5.getVisibility()==android.view.View.VISIBLE){
					relativeLayout5.setVisibility(android.view.View.GONE);
					askBtn.setText("评论(4)");
				}
			}
		});
	}
}
