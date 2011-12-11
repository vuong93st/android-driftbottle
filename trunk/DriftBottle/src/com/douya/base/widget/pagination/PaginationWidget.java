/*
 * @version 1.0
 * @Date 2011-11-29
 * @author Anders.Zhang
 * Copyright Shandong douya Sci&Trading Co.,Ltd.
 */

// ~ Package Information
// ============================================================================

package com.douya.base.widget.pagination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.douya.android.R;
import com.douya.base.app.BaseApp;
import com.douya.base.biz.pagination.PaginationBiz;
import com.douya.base.entity.exception.ExceptionEntity;
import com.douya.base.entity.pagination.PageBean;
import com.douya.base.widget.pagination.PaginationTableAdapter.TableRow;

/**
 * 
 * @Title: 分页控件.
 * @Description: 分页控件分为主数据域与控制域两部分.<br/>
 *               1.主数据域分为标题栏,数据域两部分.标题栏可通过设置TableTitleVisibility控制是否可见(默认可见).<br/>
 *               2.设置actionUrl指定数据加载地址.<br/>
 *               3.在DefaultSharedPreferences中设置分页大小,key为PaginationWidget.
 *               PAGESIZE_KEY,默认每页20条数据.<br/>
 *               4.设置OnItemClickListener指定点击数据域时的事件处理.<br/>
 *               5.设置PaginationBiz实现数据加载实现.<br/>
 *               6.分页数据存储在PageBean中.<br/>
 *               7.setOnClickListener设置单元格单击事件.<br/>
 *               8.通过putQueryString设置查询参数
 * 
 * @author ZhangDq
 * @version 1.0
 * 
 */
public class PaginationWidget {
	private static final String TAG = "PaginationWidget";
	/**
	 * 每页的数据量在SharedPreferences中的Key
	 */
	public static final String PAGESIZE_KEY = "pageSize";
	/**
	 * 数据访问地址
	 */
	private String actionUrl;
	/**
	 * 分页控件所在Activity
	 */
	private Activity activity;
	/**
	 * 表格标题部分可见性
	 */
	private TableTitleVisibility tableTitleVisibility;
	/**
	 * 表格标题部分
	 */
	private ListView lv_page_title;
	/**
	 * 表格数据域适配器
	 */
	private PaginationTableAdapter tableTitleAdapter;
	/**
	 * 表格数据域部分
	 */
	private ListView lv_page_body;
	/**
	 * 表格数据域适配器
	 */
	private PaginationTableAdapter tableBodyAdapter;
	/**
	 * 分页按钮,首页,前一页,下一页,末页
	 */
	private Button btn_firstPage, btn_prevPage, btn_nextPage, btn_lastPage;
	/**
	 * 当前页,总页数,已查看记录数,总记录数
	 */
	private TextView txt_currentPage, txt_pageCount, txt_page_allCount;
	/**
	 * 当前页的数据实体
	 */
	private PageBean pageBean = null;
	/**
	 * 数据加载进度提示
	 */
	private ProgressDialog progressDialog;
	/**
	 * 数据获取业务逻辑实现
	 */
	private PaginationBiz paginationBiz;
	/**
	 * 表格数据域列表项点击事件处理监听器
	 */
	private OnItemClickListener pageBodyOnItemClickListener;

	/**
	 * @Title: 单元格类型.
	 */
	public enum TableTitleVisibility {
		/**
		 * 单元格可见性,可见
		 */
		VISIBLE(View.VISIBLE, "visible"),
		/**
		 * 单元格可见性,不可见,占用空间
		 */
		INSVISIBLE(View.INVISIBLE, "invisible"),
		/**
		 * 单元格可见性,不可见,不占用空间
		 */
		GONE(View.GONE, "gone");

		private int intValue;
		private String strValue;

		private TableTitleVisibility(int intValue, String strValue) {
			this.intValue = intValue;
			this.strValue = strValue;
		}

		public int getIntValue() {
			return intValue;
		}

		public String getStrValue() {
			return strValue;
		}
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String res = msg.obj.toString();
			Toast toast = null;
			switch (msg.what) {
			case R.id.exception:
				// 异常处理
				int start = res.indexOf("=") + 1;
				String code = res.substring(start);
				/*
				ExceptionEntity exception = BaseApp.getExceptions().get(code);
				String promptStr = null;
				if (null != exception) {
					promptStr = exception.getValue();
				} else {
					promptStr = activity.getResources().getString(
							R.string.unknownError);
				}
				toast = Toast.makeText(activity, promptStr, Toast.LENGTH_SHORT);
				*/
				break;
			}
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			progressDialog.dismiss();
		}

	};

	public PaginationWidget() {
		// 默认标题部分可见
		tableTitleVisibility = TableTitleVisibility.VISIBLE;
	}

	public PaginationWidget(Activity activity) {
		this();
		this.activity = activity;
	}

	public PaginationWidget init(Activity activity) {
		this.activity = activity;
		if (null == pageBean) {
			pageBean = new PageBean();
		}
		paginationBiz = new PaginationBiz();
		setUpViews();
		bindEvents();
		initData();
		return this;
	}

	public PaginationWidget init(Activity activity, String actionUrl) {
		this.actionUrl = actionUrl;
		return init(activity);
	}

	public PaginationWidget init(Activity activity, String actionUrl,
			int pageSize) {
		pageBean.setPageSize(pageSize);
		return init(activity, actionUrl);
	}

	public PaginationWidget init(Activity activity, String actionUrl,
			int pageSize, PaginationBiz paginationBiz) {
		this.paginationBiz = paginationBiz;
		return init(activity, actionUrl, pageSize);
	}

	public PaginationWidget init(Activity activity, String actionUrl,
			int pageSize, PaginationBiz paginationBiz,
			TableTitleVisibility tableTitleVisibility) {
		this.tableTitleVisibility = tableTitleVisibility;
		return init(activity, actionUrl, pageSize, paginationBiz);
	}

	private void setUpViews() {
		findPageTitle();
		lv_page_body = (ListView) activity
				.findViewById(R.id.lv_pagination_widget_data_body);
		btn_firstPage = (Button) activity.findViewById(R.id.btn_firstPage);
		btn_prevPage = (Button) activity.findViewById(R.id.btn_prevPage);
		btn_nextPage = (Button) activity.findViewById(R.id.btn_nextPage);
		btn_lastPage = (Button) activity.findViewById(R.id.btn_lastPage);

		txt_currentPage = (TextView) activity
				.findViewById(R.id.txt_currentPage);
		txt_pageCount = (TextView) activity.findViewById(R.id.txt_pageCount);
		txt_page_allCount = (TextView) activity
				.findViewById(R.id.txt_page_allCount);
	}

	private void bindEvents() {
		btn_firstPage.setOnClickListener(onClickListener);
		btn_prevPage.setOnClickListener(onClickListener);
		btn_nextPage.setOnClickListener(onClickListener);
		btn_lastPage.setOnClickListener(onClickListener);
		lv_page_body.setOnItemClickListener(new OnItemClickListener());
	}

	private void initData() {
		tableBodyAdapter = new PaginationTableAdapter(activity,
				new ArrayList<PaginationTableAdapter.TableRow>());
		lv_page_body.setAdapter(tableBodyAdapter);

		tableTitleAdapter = new PaginationTableAdapter(activity,
				new ArrayList<PaginationTableAdapter.TableRow>());
		lv_page_title.setAdapter(tableTitleAdapter);
		setTitleVisibility();
		SharedPreferences dsp = PreferenceManager
				.getDefaultSharedPreferences(activity);
		// 设置分页大小,默认每页20条数据
		pageBean.setPageSize(dsp.getInt(PAGESIZE_KEY, 20));
	}

	private void setTitleVisibility() {
		findPageTitle();
		// 设置是否可见
		lv_page_title.setVisibility(tableTitleVisibility.getIntValue());
	}

	void findPageTitle() {
		lv_page_title = (ListView) activity
				.findViewById(R.id.lv_pagination_widget_data_title);
	}

	public class OnItemClickListener implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			if (null != pageBodyOnItemClickListener) {
				pageBodyOnItemClickListener.onItemClick(arg0, arg1, position,
						arg3);
			}
		}
	}

	private CompoundButton.OnClickListener onClickListener = new CompoundButton.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_firstPage:
				loadFirstPage();
				break;
			case R.id.btn_prevPage:
				loadPrevPage();
				break;
			case R.id.btn_nextPage:
				loadNextPage();
				break;
			case R.id.btn_lastPage:
				loadLastPage();
				break;
			}
		}

	};

	private void loadFirstPage() {
		pageBean.setCurrentPage(1);
		loadPaginationData();
	}

	private void loadPrevPage() {
		if (pageBean.getCurrentPage() == 1) {
			showToast(R.string.isFirstPage);
			return;
		}
		pageBean.setCurrentPage(pageBean.getCurrentPage() - 1);
		loadPaginationData();
	}

	private void loadNextPage() {
		if (pageBean.getCurrentPage() == pageBean.getPageCount()) {
			showToast(R.string.isLastPage);
			return;
		}
		pageBean.setCurrentPage(pageBean.getCurrentPage() + 1);
		loadPaginationData();
	}

	private void loadLastPage() {
		pageBean.setCurrentPage(pageBean.getPageCount());
		loadPaginationData();
	}

	/**
	 * 
	 * TODO: 加载数据.
	 * 
	 */
	public void loadPaginationData() {
		Log.i(TAG, "loadPaginationData");
		showProgressDialog();
		new LoadPaginationDataTask().execute(new String[] {
				"" + pageBean.getCurrentPage(), "" + pageBean.getPageSize() });
	}

	private void showToast(int message) {
		Toast toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	private void showProgressDialog() {
		progressDialog = ProgressDialog.show(activity, activity.getResources()
				.getString(R.string.dataLoadingTitle), activity.getResources()
				.getString(R.string.dataLoadingMsg), true);
	}

	class LoadPaginationDataTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			Bundle query = pageBean.getQueryBundle();
			Map<String, String> fields = new HashMap<String, String>();
			// 设置查询参数
			for (String key : query.keySet()) {
				fields.put(key, query.getString(key));
			}
			return paginationBiz
					.loadPaginationData(actionUrl, fields, pageBean);
		}

		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();

			if (null != result) {
				if (result.contains("exceptionCode")) {
					// 处理异常
					handler.obtainMessage(R.id.exception, result)
							.sendToTarget();
				}
			}
			Log.i(TAG, "currentPage=" + pageBean.getCurrentPage());
			Log.i(TAG, "pageCount=" + pageBean.getPageCount());
			Log.i(TAG, "pageSize=" + pageBean.getPageSize());
			Log.i(TAG, "allCount=" + pageBean.getAllCount());

			txt_currentPage.setText("" + pageBean.getCurrentPage());
			txt_pageCount.setText("" + pageBean.getPageCount());

			txt_page_allCount.setText("" + pageBean.getAllCount());
			@SuppressWarnings("unchecked")
			List<TableRow> table = (List<TableRow>) pageBean.getPageDatas();
			if (table.size() > 0) {
				tableTitleAdapter.setTableDataSource(table.subList(0, 1));
				tableTitleAdapter.notifyDataSetChanged();
			}
			if (table.size() > 1) {
				tableBodyAdapter.setTableDataSource(table.subList(1,
						table.size()));
				tableBodyAdapter.notifyDataSetChanged();
			}
		}
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public void setPageSize(int pageSize) {
		pageBean.setPageSize(pageSize);
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	/**
	 * 
	 * TODO: 获取所有数据.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TableRow> getPageDatas() {
		return (List<TableRow>) pageBean.getPageDatas();
	}

	/**
	 * 
	 * TODO: 获取表格数据域数据.
	 * 
	 * @return
	 */
	public List<TableRow> getPageBodyDatas() {
		return tableBodyAdapter.getTableDataSource();
	}

	/**
	 * 
	 * TODO: 获取表格标题栏数据.
	 * 
	 * @return
	 */
	public List<TableRow> getPageTitleDatas() {
		return tableTitleAdapter.getTableDataSource();
	}

	/**
	 * 
	 * TODO: 表格数据域部分行点击事件监听器.
	 * 
	 * @param onItemClickListener
	 */
	public void setPageBodyOnItemClickListener(
			OnItemClickListener pageBodyOnItemClickListener) {
		this.pageBodyOnItemClickListener = pageBodyOnItemClickListener;
	}

	/**
	 * 
	 * TODO: 表格行单元格项点击事件监听器.
	 * 
	 * @param onClickListener
	 */
	public void setCellOnClickListener(OnClickListener cellOnClickListener) {
		tableTitleAdapter.setCellOnClickListener(cellOnClickListener);
		tableBodyAdapter.setCellOnClickListener(cellOnClickListener);
	}

	/**
	 * 
	 * TODO: 设置数据加载实现.
	 * 
	 * @param paginationBiz
	 */
	public void setPaginationBiz(PaginationBiz paginationBiz) {
		this.paginationBiz = paginationBiz;
	}

	/**
	 * 
	 * TODO: 设置表格标题栏可见性.
	 * 
	 * @param tableTitleVisibility
	 */
	public void setTableTitleVisibility(
			TableTitleVisibility tableTitleVisibility) {
		this.tableTitleVisibility = tableTitleVisibility;
		setTitleVisibility();
	}

	/**
	 * 
	 * TODO: 设置查询参数.
	 * 
	 * @param key
	 * @param value
	 */
	public void putQueryString(String key, String value) {
		pageBean.getQueryBundle().putString(key, value);
	}
}
