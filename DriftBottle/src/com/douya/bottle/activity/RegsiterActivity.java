package com.douya.bottle.activity;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.douya.bottle.R;

public class RegsiterActivity extends Activity{

	private TextView emailTextview = null;
	private TextView passwordTextview = null;
	private TextView confirmPasswordTextview = null;
	private TextView genderTextView = null;
	private RadioButton genderBoyButton = null;
	private RadioButton genderGirlButton = null;
	private TextView cityTextView = null;
	
	private Context context = null;
	private Spinner sp = null;
    private Spinner sp2 =null;
    ArrayAdapter<String> adapter ;
    ArrayAdapter<String> adapter2; 

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
        
        sp = (Spinner) findViewById(R.id.province);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, R.array.city, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				int pos = sp.getSelectedItemPosition();
				int variable = Integer.parseInt(getClassVariableByName("city"+pos).toString());
				sp2 = (Spinner) findViewById(R.id.city);
				if(variable!=0){
			        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(
			        		RegsiterActivity.this, variable, android.R.layout.simple_spinner_item);
	
			        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			        sp2.setAdapter(adapter2);
			        sp2.setVisibility(View.VISIBLE);
				}else{
					sp2.setVisibility(View.GONE);
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
        	
		});
	}
	/**
	 * 根据变量名称，得到R.array类中的变量
	 * @param varName
	 * @return
	 */
	public String getClassVariableByName(String varName){
    	// 获取f对象对应类中的所有属性域
		Field[] fields = R.array.class.getDeclaredFields();
		for(int i = 0 , len = fields.length; i < len; i++) {
			// 对于每个属性，获取属性名
			try {
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
	 * @param str
	 */
	public void DisplayToast(String str)   
	{   
        Toast toast = Toast.makeText(this, str, Toast.LENGTH_LONG);   
        toast.setGravity(Gravity.TOP, 0, 220);   
        toast.show();   
    }   

}
