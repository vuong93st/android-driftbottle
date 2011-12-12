package com.douya.bottle.activity.more;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.douya.R;

public class YqljActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yqlj);
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map1.put("yqljItemLogo", R.drawable.head_01);
		map1.put("yqljItemTitle", "腾讯网");
		map2.put("yqljItemLogo", R.drawable.face4);
		map2.put("yqljItemTitle", "飞扬女子医院");
		list.add(map1);
		list.add(map2);
		SimpleAdapter sap = new SimpleAdapter(this, list, R.layout.yqljitem,
				new String[] { "yqljItemLogo", "yqljItemTitle" }, new int[] {
						R.id.yqljItemLogo, R.id.yqljItemTitle });
		setListAdapter(sap);
	}
	/**
	 * ListView单击事件方法
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		switch (position) {
		case 0: {
			Intent intent = new Intent();
			intent.setClass(YqljActivity.this, HyzyActivity.class);
			startActivity(intent);
			break;
		}
		}
	}
}
