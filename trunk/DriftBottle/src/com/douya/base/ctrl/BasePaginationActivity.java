package com.douya.base.ctrl;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.douya.R;
import com.douya.base.biz.pagination.PaginationBiz;
import com.douya.base.util.IpOptionUtils;
import com.douya.base.util.StringUtils;
import com.douya.base.widget.pagination.PaginationTableAdapter.TableCell;
import com.douya.base.widget.pagination.PaginationTableAdapter.TableRow;
import com.douya.base.widget.pagination.PaginationWidget;

public class BasePaginationActivity extends BaseActivity {
	private static final String TAG = "BasePaginationActivity";

	/**
	 * 分页控件
	 */
	PaginationWidget paginationWidget = null;
	/**
	 * 该页的查询条件
	 */
	private Map<String, String> queryVaule = new HashMap<String, String>();
	/**
	 * 分页数据获取业务逻辑实现
	 */
	private PaginationBiz paginationBiz = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*paginationBiz = new StockPaginationBiz();

		prevInit(R.layout.stock_list_pagination_widget,
				BasePaginationActivity.this, getIntent(), "", paginationBiz);

		initBottomWidget(BasePaginationActivity.this, 1);*/
	}

	/**
	 * 
	 * TODO: 初始化底部tab页,需要时可以在onCreate中调用.
	 * 
	 * @param activity 所在的Activity
	 * @param tabIndex tab索引
	 */
	public void initBottomWidget(Activity activity, int tabIndex) {
		// 创建页面底部选项卡控件
		//BottomTabWidget bottomTabWidget = new BottomTabWidget(activity);
		// 选中第二个选项卡
		//bottomTabWidget.setCurrentViews(tabIndex);
	}

	/**
	 * 
	 * TODO: 前期初始化,需要的地方在onCreate中调用.
	 * 
	 * @param layoutResID 布局资源id
	 * @param activity 所在的Activity
	 * @param intent 传递过来的参数
	 * @param actionUrl 分页加载url
	 * @param paginationBiz 分页数据获取实现
	 */
	public void prevInit(int layoutResID, Activity activity, Intent intent,
			String actionUrl, PaginationBiz paginationBiz) {
		setContentView(R.layout.list_pagination_widget);
		setUpView();
		bindEvents();
		initPaginationWidget(activity, intent, actionUrl, paginationBiz);
	}

	public void initPaginationWidget(Activity activity, Intent intent,
			String actionUrl, PaginationBiz paginationBiz) {
		prevInitPaginationWidget(activity, intent, actionUrl, paginationBiz);
		paginationWidget.loadPaginationData();
	}

	public void prevInitPaginationWidget(Activity activity, Intent intent,
			String actionUrl, PaginationBiz paginationBiz) {
		// 列表分页控件
		paginationWidget = new PaginationWidget().init(activity);
		paginationWidget.setActionUrl(IpOptionUtils.bulidActionUrl(activity)
				+ actionUrl);
		// 填充查询参数
		fillQueryConditions(intent, initQueryConditionKeys());
		paginationWidget.setPaginationBiz(paginationBiz);
		// 分页控件数据域点击时事件处理
		paginationWidget
				.setPageBodyOnItemClickListener(paginationWidget.new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						pageBodyOnItemClick(position);
					}

				});
	}

	/**
	 * 
	 * TODO: 初始化分页控件查询参数Key,用于fillQueryConditions.
	 * 
	 * @return
	 */
	public String[] initQueryConditionKeys() {
		String[] params = new String[] {};
		return params;
	}

	/**
	 * 
	 * TODO: 向分页控件中填充查询参数.
	 * 
	 * @param intent
	 * @param params
	 *            待填充的参数,来自initQueryConditionKeys().
	 */
	public void fillQueryConditions(Intent intent, String[] params) {
		if (null == params)
			return;
		for (int i = 0; i < params.length; i++) {
			if (StringUtils.isNotEmpty(intent.getStringExtra(params[i]))) {
				// 设置参数
				paginationWidget.putQueryString(params[i],
						intent.getStringExtra(params[i]));
			}
		}
	}

	/**
	 * 
	 * TODO: 初始化参数Key,用于填充参数值fillExtras.
	 * 
	 * @return
	 */
	public String[] initExtraKeys() {
		String[] params = new String[] {};
		return params;
	}

	/**
	 * 
	 * TODO: 根据initExtraKeys()Key,填充参数初始化值.点击表格数据域项时传递的参数.
	 * 
	 * @param tableRow
	 * @param intent
	 * @param params
	 *            传递的参数,来自initExtraKeys().
	 */
	public void fillExtras(TableRow tableRow, Intent intent, String[] params) {
		TableCell tableCell = null;
		Object value = null;
		for (int i = params.length - 1; i >= 0; i--) {
			tableCell = tableRow.getCellValue(params[i]);
			if (null != tableCell) {
				value = tableCell.getValue();
				if (null != value) {
					intent.putExtra(params[i], value.toString());
					Log.i(TAG, "" + params[i] + ":" + value.toString());
				}
			}
		}
	}

	/**
	 * 设置视图
	 */
	public void setUpView() {

	}

	/**
	 * 设置监听
	 */
	public void bindEvents() {

	}

	public void pageBodyOnItemClick(int position) {
		TableRow tableRow = paginationWidget.getPageBodyDatas().get(position);
		Intent intent = new Intent();
		// 填充参数
		fillExtras(tableRow, intent, initExtraKeys());
		// 设置点击数据项时打开的Activity
		pageWidgetItemClickStartActivity(BasePaginationActivity.this, intent);
		startActivity(intent);
	}

	/**
	 * 
	 * TODO: 设置分页控件表格数据域子项点击时打开的子Activity.
	 * 
	 * @param context
	 * @param intent
	 */
	public void pageWidgetItemClickStartActivity(Context context, Intent intent) {

	}

	public void setPaginationBiz(PaginationBiz paginationBiz) {
		this.paginationBiz = paginationBiz;
	}

	public PaginationWidget getPaginationWidget() {
		return paginationWidget;
	}

	public PaginationBiz getPaginationBiz() {
		return paginationBiz;
	}

}
