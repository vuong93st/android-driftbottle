package com.douya.android.bottle.activity.more;

import java.util.ArrayList;
import java.util.HashMap;

import com.douya.android.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class WzdpzActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wzdpz);
		TextView tv = (TextView)findViewById(R.id.wzdpzTitle);
		tv.setText(R.string.moreitem1);
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map1.put("wzdpzItemLogo", R.drawable.new_bottle);
		map1.put("wzdpzItemTitle", "娜姐V5瓶");
		map1.put("wzdpzItemContent", "当那么多人打破头挤进国企,当很多人");
		map1.put("wzdpzItemTimes", "3分钟前赞过");
		map2.put("wzdpzItemLogo", R.drawable.new_bottle);
		map2.put("wzdpzItemTitle", "飞扬NB瓶");
		map2.put("wzdpzItemContent", "飞扬心里有一段情，也许放不下,也许错过.");
		map2.put("wzdpzItemTimes", "5分钟前赞过");
		list.add(map1);
		list.add(map2);
		SimpleAdapter sap = new SimpleAdapter(this, list, R.layout.wzdpzitem,
				new String[] { "wzdpzItemLogo", "wzdpzItemTitle", "wzdpzItemContent","wzdpzItemTimes" }, new int[] {
						R.id.wzdpzItemLogo, R.id.wzdpzItemTitle, R.id.wzdpzItemContent,R.id.wzdpzItemTimes });
		setListAdapter(sap);
	}
	

}
