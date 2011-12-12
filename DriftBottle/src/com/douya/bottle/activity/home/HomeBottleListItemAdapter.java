package com.douya.bottle.activity.home;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.douya.android.R;
import com.douya.bottle.model.BottleItem;

public class HomeBottleListItemAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<BottleItem> list;

	public HomeBottleListItemAdapter(LayoutInflater inflater, List<BottleItem> list) {
		super();
		this.inflater = inflater;
		this.list = list;
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		if (position < list.size()) {
			return list.get(position);
		} else {
			return null;
		}
	}

	public long getItemId(int position) {
		if (position < list.size()) {
			return list.get(position).getId();
		} else {
			return -1;
		}
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		BottleItem item = list.get(position);
		View view;
		if (convertView != null && convertView.getId() == R.id.lvItems) {
			view = convertView;
		} else {
			view = inflater.inflate(R.layout.bottle_list_item, parent, false);
		}

		ItemViewHolder holder = (ItemViewHolder) view.getTag();
		if (holder == null) {
			holder = new ItemViewHolder();
			holder.ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
			holder.tvcaption = (TextView) view.findViewById(R.id.tvCaption);
			holder.tvContentTextView = (TextView) view.findViewById(R.id.tvContent);
			holder.tvContentDateTime = (TextView) view.findViewById(R.id.tvContentDateTime);
			view.setTag(holder);
		}

		if (item != null) {
			holder.ivIcon.setImageResource(item.getIcon());
			holder.tvcaption.setText(item.getCaption());
			holder.tvContentTextView.setText(item.getContent());
			holder.tvContentDateTime.setText(item.getContentDateTime());
		}
		return view;
	}

	protected class ItemViewHolder {
		public ImageView ivIcon;
		public TextView tvcaption;
		public TextView tvContentTextView;
		public TextView tvContentDateTime;
	}

}
