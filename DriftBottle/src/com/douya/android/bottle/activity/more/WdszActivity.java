package com.douya.android.bottle.activity.more;

import com.douya.android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class WdszActivity extends Activity {
	/**
	 * 省、直辖市下拉列表
	 */
	private Spinner citySpinner = null;
	/**
	 * 生日年份下拉列表
	 */
	private Spinner yearSpinner = null;
	/**
	 * 生日月份下拉列表
	 */
	private Spinner monthSpinner = null;
	/**
	 * 生日日下拉列表
	 */
	private Spinner daySpinner = null;
	/**
	 * 情感状态下拉列表
	 */
	private Spinner qgztSpinner = null;
	/**
	 * 保存修改按钮
	 */
	private Button bzxgBtn = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wdsz);
		citySpinner = (Spinner) findViewById(R.id.wdszCity);
        ArrayAdapter cityAdapter = ArrayAdapter.createFromResource(
                this, R.array.city, android.R.layout.simple_spinner_item);

        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);
        yearSpinner = (Spinner) findViewById(R.id.wdszSryear);
        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(
                this, R.array.year, android.R.layout.simple_spinner_item);

        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        monthSpinner = (Spinner) findViewById(R.id.wdszSrmonth);
        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(
                this, R.array.month, android.R.layout.simple_spinner_item);

        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);
        daySpinner = (Spinner) findViewById(R.id.wdszSrday);
        ArrayAdapter dayAdapter = ArrayAdapter.createFromResource(
                this, R.array.day, android.R.layout.simple_spinner_item);

        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);
        qgztSpinner = (Spinner) findViewById(R.id.wdszQgztSpinner);
        ArrayAdapter qgztAdapter = ArrayAdapter.createFromResource(
                this, R.array.qgzt, android.R.layout.simple_spinner_item);

        qgztAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qgztSpinner.setAdapter(qgztAdapter);
        bzxgBtn = (Button)findViewById(R.id.wdszBtn);
        bzxgBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
