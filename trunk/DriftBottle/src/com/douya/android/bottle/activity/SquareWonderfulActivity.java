package com.douya.android.bottle.activity;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.ListActivity;
import android.content.Intent;
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
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map1.put("squareWonderfulLogo", R.drawable.new_bottle);
		map1.put("squareWonderfulTitle", "王者归来的瓶子");
		map1.put("squareWonderfulCounts", "共被9人赞过");
		map1.put("squareWonderfulContent", "我很怀念小时候，那一年李刚还没...");
		map2.put("squareWonderfulLogo", R.drawable.new_bottle);
		map2.put("squareWonderfulTitle", "飞扬的瓶子");
		map2.put("squareWonderfulCounts", "共被8人赞过");
		map2.put("squareWonderfulContent", "每个人心里都有一段情，也许放不...");
		list.add(map1);
		list.add(map2);
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
		Intent intent = new Intent();
		intent.setClass(SquareWonderfulActivity.this, BottleAgreeListActivity.class);
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
	}
}
