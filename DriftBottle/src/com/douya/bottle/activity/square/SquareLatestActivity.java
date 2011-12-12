package com.douya.bottle.activity.square;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.douya.android.R;

public class SquareLatestActivity extends ListActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.squarelatest);
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
