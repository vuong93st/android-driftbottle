package com.douya.base.biz.pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.douya.base.dao.pagination.PaginationService;
import com.douya.base.entity.pagination.PageBean;
import com.douya.base.widget.pagination.PaginationTableAdapter.TableCell;
import com.douya.base.widget.pagination.PaginationTableAdapter.TableRow;

public class PaginationBiz {
	private static final String TAG = "PaginationBiz";

	private PaginationService paginationService;

	public PaginationBiz() {
		paginationService = PaginationService.getInstance();
	}

	/**
	 * 
	 * TODO: 加载分页数据.
	 * 
	 * @param actionUrl
	 *            访问服务器地址
	 * @param fields
	 *            form表单字段,key字段名,value为字段值
	 * @param pageBean
	 *            分页实体
	 * @return
	 */
	public String loadPaginationData(String actionUrl,
			Map<String, String> fields, PageBean pageBean) {
		String res = paginationService.loadPaginationData(actionUrl, fields);
		createTableDataSource(res, pageBean);
		return res;
	}

	/**
	 * 
	 * TODO: 生成表格数据域部分.
	 * 
	 * @param jsonStr
	 *            服务器返回的JSON格式字符串
	 * @param pageBean
	 *            分页实体
	 */
	public void createTableDataSource(String jsonStr, PageBean pageBean) {
		List<TableRow> table = new ArrayList<TableRow>();
		// 生成表格title
		createTableTitle(table);
		try {
			JSONObject object = new JSONObject(jsonStr);
			JSONObject parentJO = (JSONObject) object.get("gridData");
			JSONObject childJO = (JSONObject) parentJO.get("table_list");

			JSONObject pageJO = (JSONObject) childJO.get("page");
			JSONArray tableRows = (JSONArray) childJO.get("data");

			pageBean.setAllCount(Integer.parseInt(pageJO
					.getString("page_allCount")));// 总记录数
			pageBean.setCurrentPage(Integer.parseInt(pageJO
					.getString("page_cur")));// 当前页
			pageBean.setPageCount(Integer.parseInt(pageJO
					.getString("page_allPage")));// 总页数

			//当前页的行号起始索引
			int rowStartIndex = (pageBean.getCurrentPage() -1 ) * pageBean.getPageSize() + 1;
			JSONObject tableRowJson = null;
			TableRow tableRow = null;
			TableCell[] cells = null;
			TableCell[] titles = table.get(0).getCells();
			int rowCount = tableRows.length();
			for (int i = 0; i < rowCount; i++) {
				// 生成行数据
				cells = new TableCell[titles.length];
				// 生成行
				tableRow = new TableRow(cells);
				tableRowJson = tableRows.getJSONObject(i);

				// 填充行数据
				fillTableCells(tableRowJson, rowStartIndex, cells, titles);
				// 添加行到表格
				table.add(tableRow);
				rowStartIndex = rowStartIndex + 1;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		pageBean.setPageDatas(table);
	}

	/**
	 * 
	 * TODO: 填充表格单元格.
	 * 
	 * @param tableRowJson 表格行JSON格式字符串
	 * @param rowStartIndex 该行在表格中的行索引
	 * @param cells 行单元格数组
	 * @param titles 表格标题栏
	 */
	public void fillTableCells(JSONObject tableRowJson, int rowStartIndex,
			TableCell[] cells, TableCell[] titles) {

	}

	/**
	 * 
	 * TODO: 生成表格title.
	 * 
	 * @param table
	 */
	public void createTableTitle(List<TableRow> table) {

	}

	/**
	 * 
	 * TODO: 填入方法概括.
	 * 
	 * @param tableRowJson
	 *            表格行JSON格式字符串
	 * @param jsonKey
	 *            JSON串中的key
	 * @return
	 * @throws JSONException
	 */
	public String getCellValue(JSONObject tableRowJson, String jsonKey)
			throws JSONException {
		if (!tableRowJson.isNull(jsonKey)) {
			return tableRowJson.getString(jsonKey);
		}
		return "";
	}
}
