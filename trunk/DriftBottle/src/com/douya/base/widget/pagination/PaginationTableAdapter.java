package com.douya.base.widget.pagination;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.douya.R;

/**
 * @Title: 分页动态表格数据适配器.
 * 
 * @author ZhangDq
 * 
 */
public class PaginationTableAdapter extends BaseAdapter {
	private static final String TAG = "PaginationTableAdapter";

	private Context context;
	/**
	 * 数据Adapter所关联的ListView
	 */
	private ListView listView;
	/**
	 * 表格数据源
	 */
	private List<TableRow> tableDataSource;
	/**
	 * 数据源在ListView中的所有项的选中状态
	 */
	private Map<Integer, Boolean> checkedStat;
	/**
	 * 上一个选中状态的单选按钮的索引,-1表示没有选中状态的单选按钮
	 */
	private int prevChecdedRadio = -1;

	private OnClickListener cellOnClickListener;

	public PaginationTableAdapter(Context context, List<TableRow> table) {
		this.context = context;
		this.tableDataSource = table;
		checkedStat = new HashMap<Integer, Boolean>();
		checkedAll(false);
	}

	public PaginationTableAdapter(Context context, List<TableRow> table,
			ListView listView) {
		this(context, table);
		this.listView = listView;
	}

	/**
	 * 
	 * TODO: 同步更改所有项选中状态.
	 * 
	 * @param checked
	 */
	public void checkedAll(Boolean checked) {
		for (int i = tableDataSource.size() - 1; i >= 0; i--) {
			setChecked(i, checked, false);
		}
		this.notifyDataSetChanged();
	}

	/**
	 * 
	 * TODO: 设置指定项的选中状态.
	 * 
	 * @param position
	 * @param checked
	 * @param notifyDataSetChanged
	 *            是否通知数据变化
	 */
	public void setChecked(int position, Boolean checked,
			boolean notifyDataSetChanged) {
		checkedStat.put(position, checked);
		if (notifyDataSetChanged)
			this.notifyDataSetChanged();
	}

	/**
	 * 
	 * TODO: 反选指定索引项.
	 * 
	 * @param position
	 * @param notifyDataSetChanged
	 *            是否通知数据变化
	 */
	public void toggle(int position, boolean notifyDataSetChanged) {
		setChecked(position, !checkedStat.get(position), notifyDataSetChanged);
	}

	/**
	 * 
	 * TODO: 全部反选.
	 * 
	 */
	public void toggleAll() {
		for (int i = tableDataSource.size() - 1; i >= 0; i--) {
			toggle(i, false);
		}
		this.notifyDataSetChanged();
	}

	public void removeAll() {
		tableDataSource.clear();
		this.notifyDataSetChanged();
	}

	/**
	 * 
	 * TODO: 删除指定的所有项.
	 * 
	 * @param deletedIndexs
	 *            待删除的索引列表
	 */
	public void remove(List<Integer> deletedIndexs) {
		if (null == deletedIndexs || deletedIndexs.size() < 1) {
			return;
		}
		// 按升序排序
		Collections.sort(deletedIndexs);
		int len = deletedIndexs.size();
		// 按照索引由大到小的顺序删除
		for (int i = len - 1; i >= 0; i--) {
			remove(deletedIndexs.get(i), false);
		}
		this.notifyDataSetChanged();
	}

	/**
	 * 
	 * TODO: 删除指定索引项.
	 * 
	 * @param index
	 *            指定索引
	 * @param notifyDataSetChanged
	 *            是否通知数据变化
	 */
	public void remove(int index, boolean notifyDataSetChanged) {
		// 当前集合最后一项索引
		int lastIndex = tableDataSource.size() - 1;
		if (index < 0 || index > lastIndex)
			return;
		if (index < lastIndex) {
			// 不是最后一项,则调整选中状态记录表,顺序后移
			for (int i = index; i < lastIndex; i++) {
				checkedStat.put(i, checkedStat.get(i + 1));
			}
		} else {
			// 最后一项,直接删除
			checkedStat.remove(index);
		}
		tableDataSource.remove(index);
		if (notifyDataSetChanged)
			this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return tableDataSource.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public TableRow getItem(int position) {
		return tableDataSource.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return createTableRowView(position, convertView, parent);
	}

	/**
	 * 
	 * TODO: 生成表格行布局.
	 */
	private View createTableRowView(int position, View convertView,
			ViewGroup parent) {
		TableRow tableRow = tableDataSource.get(position);
		RelativeLayout layout = new RelativeLayout(this.context);
		// 实例化表格行
		TableRowView tableRowView = new TableRowView(this.context, tableRow,
				position);
		// 表格行自适应大小
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		// 设置表格行对齐方式
		params.addRule(tableRow.getTableRowLayoutRule().getIntValue());
		layout.addView(tableRowView, params);

		// 填充满父布局
		AbsListView.LayoutParams param = new AbsListView.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		layout.setLayoutParams(param);

		if (tableRow.getTableRowType() == TableRow.TableRowType.TITLE) {// 该行为标题栏行
			// 设置背景
			layout.setBackgroundResource(tableRow.getBackgroundResource());
		} else if (tableRow.getTableRowType() == TableRow.TableRowType.BODY) {// 该行为数据域行
		}
		return layout;
	}

	/**
	 * TableRowView,实现表格行的样式
	 */
	private class TableRowView extends LinearLayout {

		public TableRowView(Context context, TableRow tableRow, int position) {
			super(context);
			this.setOrientation(LinearLayout.HORIZONTAL);

			// 表格单元格
			TableCell tableCell = null;
			// 单元格布局参数
			LinearLayout.LayoutParams layoutParams = null;
			// 文本单元格
			TextView textCell = null;
			// 图片单元格
			ImageView imgCell = null;
			int len = tableRow.getSize();
			for (int i = 0; i < len; i++) {
				// 逐个格单元添加到行
				tableCell = tableRow.getCellValue(i);
				if (null == tableCell)
					continue;
				// 按照格单元指定的大小设置空间
				layoutParams = new LinearLayout.LayoutParams(
						tableCell.getWidth(), tableCell.getHeight());
				// 设置单元格的margin值
				layoutParams.setMargins(tableCell.getLeftMargin(),
						tableCell.getToptMargin(), tableCell.getRighttMargin(),
						tableCell.getBottomtMargin());
				if (tableCell.getTableCellType() == TableCell.TableCellType.STRING) {// 如果格单元是文本内容
					textCell = new TextView(context);
					textCell.setGravity(Gravity.CENTER);
					if (tableCell.getBackGroundColor() < 0) {
						textCell.setBackgroundColor(tableCell
								.getBackGroundColor());
					}
					textCell.setTextColor(tableCell.getTextColor());
					textCell.setTextSize(tableCell.getTextSize());
					textCell.setText(String.valueOf(tableCell.getValue()));

					/* 设置单元格是否可见 */
					textCell.setVisibility(tableCell.getTableCellVisibility()
							.getIntValue());
					addView(textCell, layoutParams);
				} else if (tableCell.getTableCellType() == TableCell.TableCellType.IMAGE) {// 如果单元格是图像内容
					imgCell = new ImageView(context);
					// tag格式为"所在行_所在列"
					imgCell.setTag(position + "_" + i);
					// 背景黑色
					// imgCell.setBackgroundColor(tableCell.getBackGroundColor());
					imgCell.setImageResource((Integer) tableCell.getValue());
					if (null != cellOnClickListener) {
						imgCell.setOnClickListener(cellOnClickListener);
					}
					addView(imgCell, layoutParams);
				} else if (tableCell.getTableCellType() == TableCell.TableCellType.RADIO) {// 如果单元格是单选
					RadioButton radioButton = new RadioButton(context);
					// tag记录RadioButton的位置信息
					radioButton.setTag(position);
					radioButton
							.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

								@Override
								public void onCheckedChanged(
										CompoundButton buttonView,
										boolean isChecked) {
									if (!isChecked)
										return;
									if (prevChecdedRadio != -1) {
										// 这段代码来实现单选功能,将之前选中的RadioButton设为未选中
										RadioButton tempButton = (RadioButton) listView
												.findViewWithTag(prevChecdedRadio);
										if (null != tempButton) {
											tempButton.setChecked(false);
										}
									}
									// 记录当前选中的RadioButton的索引
									prevChecdedRadio = (Integer) ((RadioButton) buttonView)
											.getTag();
								}
							});
					// temp记录单选按钮状态,解决了单选框移出屏幕范围未选中状态
					if (prevChecdedRadio == position) {
						radioButton.setChecked(true);
					} else {
						radioButton.setChecked(false);
					}
					addView(radioButton, layoutParams);
				} else if (tableCell.getTableCellType() == TableCell.TableCellType.CHECK) {// 如果单元格是复选
					final CheckBox checkBox = new CheckBox(context);
					checkBox.setTag(position);
					checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							Integer pos = (Integer) checkBox.getTag();
							checkedStat.put(pos, isChecked);
						}
					});
					checkBox.setChecked(checkedStat.get(position));
					addView(checkBox, layoutParams);
				}
			}
			// 表格行背景设置,已移至父布局设置背景
			/*
			 * if (tableRow.getTableRowType() == TableRow.TableRowType.TITLE) {
			 * TableRowView.this.setBackgroundResource(tableRow
			 * .getBackgroundResource()); } else if (tableRow.getTableRowType()
			 * == TableRow.TableRowType.BODY) { //
			 * TableRowView.this.setBackgroundColor(tableRow //
			 * .getBackGroundColor()); //
			 * TableRowView.this.setBackgroundColor(0xff000000); }
			 */
		}
	}

	/**
	 * TableRow 实现表格的行
	 */
	public static class TableRow {
		private TableCell[] cells;

		/**
		 * 默认表格行背景色,白色
		 */
		private int backGroundColor = 0xffffffff;
		private int backgroundResource = R.drawable.page_header_bg;

		/**
		 * @Title: 表格行类型.
		 */
		public enum TableRowType {
			/**
			 * 表格行类型,标题部分
			 */
			TITLE(0, "title"),
			/**
			 * 表格行类型,数据域部分
			 */
			BODY(1, "body");

			private int intValue;
			private String strValue;

			private TableRowType(int intValue, String strValue) {
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

		/**
		 * 表格行类型
		 */
		private TableRowType tableRowType = TableRowType.BODY;

		/**
		 * @Title: 表格行相对于父布局对齐方式.
		 */
		public enum TableRowLayoutRule {
			/**
			 * 相对于父布局居左
			 */
			ALIGN_PARENT_LEFT(RelativeLayout.ALIGN_PARENT_LEFT,
					"ALIGN_PARENT_LEFT"),
			/**
			 * 相对于父布局居上
			 */
			ALIGN_PARENT_TOP(RelativeLayout.ALIGN_PARENT_TOP,
					"ALIGN_PARENT_TOP"),
			/**
			 * 相对于父布局居右
			 */
			ALIGN_PARENT_RIGHT(RelativeLayout.ALIGN_PARENT_RIGHT,
					"ALIGN_PARENT_RIGHT"),
			/**
			 * 相对于父布局居下
			 */
			ALIGN_PARENT_BOTTOM(RelativeLayout.ALIGN_PARENT_BOTTOM,
					"ALIGN_PARENT_BOTTOM"),
			/**
			 * 水平、垂直方向居中
			 */
			CENTER_IN_PARENT(RelativeLayout.CENTER_IN_PARENT,
					"CENTER_IN_PARENT"),
			/**
			 * 水平方向居中
			 */
			CENTER_HORIZONTAL(RelativeLayout.CENTER_HORIZONTAL,
					"CENTER_HORIZONTAL"),
			/**
			 * 垂直方向居中
			 */
			CENTER_VERTICAL(RelativeLayout.CENTER_VERTICAL, "CENTER_VERTICAL");

			private int intValue;
			private String strValue;

			private TableRowLayoutRule(int intValue, String strValue) {
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

		/**
		 * 表格行相对于父布局对齐方式,默认水平、垂直方向居中.
		 */
		private TableRowLayoutRule tableRowLayoutRule = TableRowLayoutRule.CENTER_IN_PARENT;

		public TableRow(TableCell[] cells) {
			this.cells = cells;
		}

		public TableRow(TableCell[] cells, TableRowType tableRowType) {
			this(cells);
			this.tableRowType = tableRowType;
		}

		public int getSize() {
			return cells.length;
		}

		public TableCell getCellValue(int index) {
			if (index >= cells.length)
				return null;
			return cells[index];
		}

		public TableCell getCellValue(String cellTag) {
			if (null == cells || null == cellTag)
				return null;
			String tagTmp = null;
			for (int i = cells.length - 1; i >= 0; i--) {
				tagTmp = cells[i].getCellUniqueTag();
				if (null != tagTmp && tagTmp.equals(cellTag))
					return cells[i];
			}
			return null;
		}

		public TableCell[] getCells() {
			return cells;
		}

		public void setCells(TableCell[] cells) {
			this.cells = cells;
		}

		public int getBackGroundColor() {
			return backGroundColor;
		}

		public void setBackGroundColor(int backGroundColor) {
			this.backGroundColor = backGroundColor;
		}

		public TableRowType getTableRowType() {
			return tableRowType;
		}

		public void setTableRowType(TableRowType tableRowType) {
			this.tableRowType = tableRowType;
		}

		public int getBackgroundResource() {
			return backgroundResource;
		}

		public void setBackgroundResource(int backgroundResource) {
			this.backgroundResource = backgroundResource;
		}

		public TableRowLayoutRule getTableRowLayoutRule() {
			return tableRowLayoutRule;
		}

		public void setTableRowLayoutRule(TableRowLayoutRule tableRowLayoutRule) {
			this.tableRowLayoutRule = tableRowLayoutRule;
		}
	}

	/**
	 * TableCell,实现表格的格单元
	 */
	public static class TableCell {

		/**
		 * @Title: 单元格类型.
		 */
		public enum TableCellType {
			/**
			 * 单元格类型,文本
			 */
			STRING(0, "text"),
			/**
			 * 单元格类型,图片
			 */
			IMAGE(1, "image"),
			/**
			 * 单元格类型,单选
			 */
			RADIO(1, "radio"),
			/**
			 * 单元格类型,复选
			 */
			CHECK(1, "check");

			private int intValue;
			private String strValue;

			private TableCellType(int intValue, String strValue) {
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

		/**
		 * @Title: 单元格类型.
		 */
		public enum TableCellVisibility {
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

			private TableCellVisibility(int intValue, String strValue) {
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

		/**
		 * 单元格可见性
		 */
		private TableCellVisibility tableCellVisibility;
		/**
		 * 单元格类型
		 */
		private TableCellType tableCellType;
		/**
		 * 单元格内容
		 */
		private Object value;
		/**
		 * 单元格唯一标识,
		 */
		private String cellUniqueTag;
		/**
		 * 单元格宽度
		 */
		private int width;
		/**
		 * 单元格高度
		 */
		private int height;
		/**
		 * 上下左右margin值
		 */
		private int leftMargin, toptMargin, righttMargin, bottomtMargin;
		/**
		 * 默认背景色,白色
		 */
		private int backGroundColor;
		/**
		 * 文本框时,文本颜色值
		 */
		private int textColor;
		private float textSize;

		public TableCell() {
			this.tableCellType = TableCellType.STRING;
			this.tableCellVisibility = TableCellVisibility.VISIBLE;
			this.textSize = 15;
			// 默认黑色
			this.textColor = 0xff000000;
		}

		public TableCell(Object value, int width, int height) {
			this();
			this.value = value;
			this.width = width;
			this.height = height;
		}

		public TableCell(int width, int height, int backGroundColor) {
			this.width = width;
			this.height = height;
			this.backGroundColor = backGroundColor;
		}

		public TableCell(Object value, int width, int height, float textSize) {
			this(value, width, height);
			this.textSize = textSize;
		}

		public TableCell(Object value, int width, int height, float textSize,
				String cellUniqueTag) {
			this(value, width, height, textSize);
			this.cellUniqueTag = cellUniqueTag;
		}

		public TableCell(Object value, int width, int height,
				TableCellVisibility tableCellVisibility) {
			this(value, width, height);
			this.tableCellVisibility = tableCellVisibility;
		}

		public TableCell(Object value, int width, int height, float textSize,
				TableCellVisibility tableCellVisibility, String cellUniqueTag) {
			this(value, width, height, textSize, tableCellVisibility);
			this.cellUniqueTag = cellUniqueTag;
		}

		public TableCell(Object value, int width, int height,
				TableCellVisibility tableCellVisibility, String cellUniqueTag) {
			this(value, width, height, tableCellVisibility);
			this.cellUniqueTag = cellUniqueTag;
		}

		public TableCell(Object value, int width, int height,
				String cellUniqueTag) {
			this(value, width, height);
			this.cellUniqueTag = cellUniqueTag;
		}

		public TableCell(Object value, int width, int height,
				TableCellType tableCellType) {
			this(value, width, height);
			this.tableCellType = tableCellType;
		}

		public TableCell(Object value, int width, int height, float textSize,
				TableCellType tableCellType) {
			this(value, width, height, textSize);
			this.tableCellType = tableCellType;
		}

		public TableCell(Object value, int width, int height, float textSize,
				TableCellVisibility tableCellVisibility) {
			this(value, width, height, textSize);
			this.tableCellVisibility = tableCellVisibility;
		}

		public TableCell(Object value, int width, int height,
				TableCellType tableCellType,
				TableCellVisibility tableCellVisibility) {
			this(value, width, height, tableCellType);
			this.tableCellVisibility = tableCellVisibility;
		}

		public TableCell(Object value, int width, int height, float textSize,
				TableCellType tableCellType,
				TableCellVisibility tableCellVisibility) {
			this(value, width, height, textSize, tableCellType);
			this.tableCellVisibility = tableCellVisibility;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public TableCellType getTableCellType() {
			return tableCellType;
		}

		public void setTableCellType(TableCellType tableCellType) {
			this.tableCellType = tableCellType;
		}

		public int getTextColor() {
			return textColor;
		}

		public void setTextColor(int textColor) {
			this.textColor = textColor;
		}

		public int getBackGroundColor() {
			return backGroundColor;
		}

		public void setBackGroundColor(int backGroundColor) {
			this.backGroundColor = backGroundColor;
		}

		public float getTextSize() {
			return textSize;
		}

		public void setTextSize(float textSize) {
			this.textSize = textSize;
		}

		public TableCellVisibility getTableCellVisibility() {
			return tableCellVisibility;
		}

		public void setTableCellVisibility(
				TableCellVisibility tableCellVisibility) {
			this.tableCellVisibility = tableCellVisibility;
		}

		public int getLeftMargin() {
			return leftMargin;
		}

		public void setLeftMargin(int leftMargin) {
			this.leftMargin = leftMargin;
		}

		public int getToptMargin() {
			return toptMargin;
		}

		public void setToptMargin(int toptMargin) {
			this.toptMargin = toptMargin;
		}

		public int getRighttMargin() {
			return righttMargin;
		}

		public void setRighttMargin(int righttMargin) {
			this.righttMargin = righttMargin;
		}

		public int getBottomtMargin() {
			return bottomtMargin;
		}

		public void setBottomtMargin(int bottomtMargin) {
			this.bottomtMargin = bottomtMargin;
		}

		public TableCell setMargins(int left, int top, int right, int bottom) {
			this.leftMargin = left;
			this.toptMargin = top;
			this.righttMargin = right;
			this.bottomtMargin = bottom;
			return this;
		}

		public String getCellUniqueTag() {
			return cellUniqueTag;
		}

		public TableCell setCellUniqueTag(String cellUniqueTag) {
			this.cellUniqueTag = cellUniqueTag;
			return this;
		}

	}

	public List<TableRow> getTableDataSource() {
		return tableDataSource;
	}

	public void setTableDataSource(List<TableRow> tableDataSource) {
		this.tableDataSource = tableDataSource;
	}

	public Map<Integer, Boolean> getCheckedStat() {
		return checkedStat;
	}

	public int getPrevRadioIndex() {
		return prevChecdedRadio;
	}

	public ListView getListView() {
		return listView;
	}

	public void setListView(ListView listView) {
		this.listView = listView;
	}

	public void setCellOnClickListener(OnClickListener cellOnClickListener) {
		this.cellOnClickListener = cellOnClickListener;
	}

}
