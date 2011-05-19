package com.douya.android.bottle.model;

public class BottleItem {

	private int id;
	private String caption;
	private String content;
	private String contentDateTime;
	private String type;
	private int icon;
	

	public BottleItem(int id, String caption,String content, int icon,String type,String contentDateTime) {
		this.setCaption(caption);
		this.setContent(content);
		this.setIcon(icon);
		this.setId(id);
		this.setType(type);
		this.setContentDateTime(contentDateTime);
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContentDateTime() {
		return contentDateTime;
	}

	public void setContentDateTime(String contentDateTime) {
		this.contentDateTime = contentDateTime;
	}
	
}
