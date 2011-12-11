package com.douya.base.entity.pagination;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 * @Title: 数据分页实体类.
 * @version 1.0
 * 
 */
public class PageBean implements Parcelable {
	/**
	 * 总记录数
	 */
	private int allCount;
	/**
	 * 当前页,默认第一页
	 */
	private int currentPage = 1;

	/**
	 * 总页数,默认共一页
	 */
	private int pageCount = 1;

	/**
	 * 每页记录数,默认10条
	 */
	private int pageSize = 10;

	/**
	 * 当前页的查询条件
	 */
	private Bundle queryBundle;

	private Object pageDatas;

	public PageBean() {
		queryBundle = new Bundle();
		queryBundle.putString("page_goto", "" + currentPage);
		queryBundle.putString("page_size", "" + pageSize);
	}

	public static final Parcelable.Creator<PageBean> CREATOR = new Creator<PageBean>() {
		public PageBean createFromParcel(Parcel source) {
			PageBean mPageBean = new PageBean();

			mPageBean.allCount = source.readInt();
			mPageBean.currentPage = source.readInt();
			mPageBean.pageCount = source.readInt();
			mPageBean.pageSize = source.readInt();
			mPageBean.queryBundle = source.readBundle();
			return mPageBean;
		}

		public PageBean[] newArray(int size) {
			return new PageBean[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(allCount);
		parcel.writeInt(currentPage);
		parcel.writeInt(pageCount);
		parcel.writeInt(pageSize);
		parcel.writeBundle(queryBundle);
	}

	public int getAllCount() {
		return allCount;
	}

	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		if (currentPage > pageCount) {
			this.currentPage = pageCount;
		} else if (currentPage < 1) {
			this.currentPage = 1;
		} else {
			this.currentPage = currentPage;
		}
		queryBundle.putString("page_goto", "" + currentPage);
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		if (pageCount < 1) {
			this.pageCount = 1;
		} else {
			this.pageCount = pageCount;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		queryBundle.putString("page_size", "" + pageSize);
	}

	public Bundle getQueryBundle() {
		return queryBundle;
	}

	public void setQueryBundle(Bundle queryBundle) {
		this.queryBundle = queryBundle;
	}

	public Object getPageDatas() {
		return pageDatas;
	}

	public void setPageDatas(Object pageDatas) {
		this.pageDatas = pageDatas;
	}

}
