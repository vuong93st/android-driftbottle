package com.douya.android.bottle.activity.square;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.douya.android.R;

public class SquareBottleAgreeListActivity extends ListActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bottleagreelist);
		TextView tv = (TextView)findViewById(R.id.titleTextView);
		 //创建一个 SpannableString对象   
        SpannableString sp = new SpannableString("王者归来的漂流瓶从深圳市起程,开始了漫长的征程.     深圳市  2011-05-14");  
        //设置高亮  
        sp.setSpan(new ForegroundColorSpan(Color.CYAN),0,8,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(Color.CYAN),9,12,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(sp);   
        //ListView Start
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		HashMap<String, Object> map3 = new HashMap<String, Object>();
		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map1.put("squareLatestLogo", R.drawable.moreicon_4);
		map1.put("squareLatestTitle", getString(R.string.moreitem1));
		map2.put("squareLatestLogo", R.drawable.moreicon_4);
		map2.put("squareLatestTitle", getString(R.string.moreitem1));
		map3.put("squareLatestLogo", R.drawable.moreicon_4);
		map3.put("squareLatestTitle", getString(R.string.moreitem1));
		map4.put("squareLatestLogo", R.drawable.moreicon_4);
		map4.put("squareLatestTitle", getString(R.string.moreitem1));
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		SimpleAdapter sap = new SimpleAdapter(this, list, R.layout.squarelatestitem,
				new String[] { "squareLatestLogo", "squareLatestTitle"}, new int[] {
						R.id.squareLatestLogo, R.id.squareLatestTitle});
		setListAdapter(sap);
		//ListView End
	}
	/**
	 * ListView单击事件方法
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}
}
