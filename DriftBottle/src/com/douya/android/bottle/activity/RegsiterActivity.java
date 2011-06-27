package com.douya.android.bottle.activity;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adview.AdViewLayout;
import com.douya.android.R;

public class RegsiterActivity extends Activity{

	private TextView emailTextview = null;//邮箱
	private TextView passwordTextview = null;//密码
	private TextView confirmPasswordTextview = null;//确认密码
	private TextView genderTextView = null;//性别
	private TextView cityTextView = null;//城市
	private Context context = null;
	/**
	 * 性别单选按钮：男
	 */
	private RadioButton genderBoyButton = null;
	/**
	 * 性别单选按钮：女
	 */
	private RadioButton genderGirlButton = null;
	/**
	 * 省、直辖市下拉列表
	 */
	private Spinner provinceSpinner = null;
	/**
	 * 地区下拉列表
	 */
    private Spinner prefecturalLevelCitySpinner =null;
    /**
     * 县级市下拉列表
     */
    private Spinner countyCitySpinner =null;
	/**
	 * 注册完成按钮
	 */
	private ImageButton registerButton = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		context = this;
		emailTextview = (TextView)findViewById(R.id.loginmain_email_textview);
        emailTextview.setText(R.string.loginmain_email);
        
        passwordTextview = (TextView)findViewById(R.id.loginmain_password_textview);
        passwordTextview.setText(R.string.loginmain_password);

        confirmPasswordTextview = (TextView)findViewById(R.id.confirm_password_textview);
        confirmPasswordTextview.setText(R.string.confirm_password);
        
        genderTextView = (TextView)findViewById(R.id.gender_textview);
        genderTextView.setText(R.string.gender);
        
        genderBoyButton = (RadioButton)findViewById(R.id.gender_boy);
        genderBoyButton.setText(R.string.gender_boy);
        genderGirlButton = (RadioButton)findViewById(R.id.gender_girl);
        genderGirlButton.setText(R.string.gender_girl);
        
        cityTextView = (TextView)findViewById(R.id.city_textview);
        cityTextView.setText(R.string.city);
        
        provinceSpinner = (Spinner) findViewById(R.id.province);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, R.array.city, R.layout.myspinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        provinceSpinner.setAdapter(adapter);

        provinceSpinner.setOnItemSelectedListener(new provinceSelectedListener());
        
        registerButton = (ImageButton)findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent =getIntent();
				intent.setClass(RegsiterActivity.this, MainActivity.class);
				RegsiterActivity.this.startActivity(intent);
			}
		});
      //广告
        LinearLayout layout = (LinearLayout)findViewById(R.id.adLayout);
        AdViewLayout adViewLayout = new AdViewLayout(this, "SDK20111224150629118q8ighe3eyste");
        RelativeLayout.LayoutParams adViewLayoutParams = new 
        RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        layout.addView(adViewLayout, adViewLayoutParams);
        layout.invalidate();
	}
	
	
	//选择省、直辖市，列出地级市
	class provinceSelectedListener implements OnItemSelectedListener{

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			int pos = provinceSpinner.getSelectedItemPosition();
			int variable = Integer.parseInt(getVariableByName("city"+pos).toString());
			prefecturalLevelCitySpinner = (Spinner) findViewById(R.id.city);
			if(variable!=0){
		        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(
		        		RegsiterActivity.this, variable, R.layout.myspinner);

		        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        prefecturalLevelCitySpinner.setAdapter(adapter2);
		        prefecturalLevelCitySpinner.setOnItemSelectedListener(new citySelectedListener());
		        prefecturalLevelCitySpinner.setVisibility(View.VISIBLE);
			}else{
				prefecturalLevelCitySpinner.setVisibility(View.GONE);
			}
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	//选择地区，列出县级市
	class citySelectedListener implements OnItemSelectedListener{

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			int pos = provinceSpinner.getSelectedItemPosition();
    		int pos_sub = prefecturalLevelCitySpinner.getSelectedItemPosition();
    		int variable_sub = Integer.parseInt(getVariableByName("city"+pos+"_"+pos_sub).toString());
    		countyCitySpinner = (Spinner) findViewById(R.id.city_sub);
			if(variable_sub!=0){
		        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(
		        		RegsiterActivity.this, variable_sub, R.layout.myspinner);

		        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        countyCitySpinner.setAdapter(adapter3);
		        countyCitySpinner.setVisibility(View.VISIBLE);
			}else{
				countyCitySpinner.setVisibility(View.GONE);
			}
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	/**
	 * 根据变量名称，得到R.array类中的变量
	 * @param varName
	 * @return
	 */
	public String getVariableByName(String varName){
    	// 获取对象对应类中的所有属性域
		Field[] fields = R.array.class.getDeclaredFields();
		for(int i = 0 , len = fields.length; i < len; i++) {
			try {
				// 比较获取的属性名是否也传入的一致
				if(varName.equalsIgnoreCase(fields[i].getName())){
					// 获取原来的访问控制权限
					boolean accessFlag = fields[i].isAccessible();
					// 修改访问控制权限
					fields[i].setAccessible(true);
					// 获取在对象中属性fields[i]对应的对象中的变量
					Object o = fields[i].get(R.array.class);
					// 恢复访问控制权限
					fields[i].setAccessible(accessFlag);
					return o.toString();
				}
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
		return "0";
    }
	
	/**
	 * 弹出提示消息
	 * @param context
	 * @param str
	 */
	public void displayToast(Context context,String str)   
	{   
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);   
        toast.setGravity(Gravity.TOP, 0, 220);   
        toast.show();   
    }   

}
