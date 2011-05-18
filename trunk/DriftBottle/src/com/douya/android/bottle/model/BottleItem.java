package com.douya.android.bottle.model;

public class BottleItem {

	private int id;
	private String caption;
	private String content;
	private int icon;
	

	public BottleItem(int id, String caption,String content, int icon) {
		this.setCaption(caption);
		this.setContent(content);
		this.setIcon(icon);
		this.setId(id);
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getCaption() {
		return caption;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getIcon() {
		return icon;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
