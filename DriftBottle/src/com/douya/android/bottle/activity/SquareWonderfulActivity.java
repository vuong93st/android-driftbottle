package com.douya.android.bottle.activity;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.douya.android.R;

public class SquareWonderfulActivity extends ListActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.squarewonderful);
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("squareWonderfulLogo", R.drawable.new_bottle);
		map1.put("squareWonderfulTitle", "我的瓶子");
		map1.put("squareWonderfulCounts", "共被9人赞过");
		map1.put("squareWonderfulContent", "你好！大家好！你们大家都好！");
		list.add(map1);
		SimpleAdapter sap = new SimpleAdapter(this, list, R.layout.squarewonderfulitem,
				new String[] { "squareWonderfulLogo", "squareWonderfulTitle", "squareWonderfulCounts","squareWonderfulContent" }, new int[] {
						R.id.squareWonderfulLogo, R.id.squareWonderfulTitle, R.id.squareWonderfulCounts,R.id.squareWonderfulContent });
		setListAdapter(sap);
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
