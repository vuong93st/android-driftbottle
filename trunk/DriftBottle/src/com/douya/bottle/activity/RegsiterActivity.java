package com.douya.bottle.activity;

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
                this, R.array.city1, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        
        sp2 = (Spinner) findViewById(R.id.city);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(
                this, R.array.city2, android.R.layout.simple_spinner_item);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adapter);
        
        /*adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, city); 
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        sp2 = (Spinner) findViewById(R.id.city);
        sp2.setAdapter(adapter2);*/

        sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				/*int pos = sp.getSelectedItemPosition();
                adapter2 = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, pandc[pos]);
                sp2.setAdapter(adapter2);	*/			
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        	
		});

	}

	
	
	/**
	 * 显示提示信息
	 * @param str
	 */
	public void DisplayToast(String str)   
	{   
        Toast toast = Toast.makeText(this, str, Toast.LENGTH_LONG);   
        //设置toast显示的位置   
        toast.setGravity(Gravity.TOP, 0, 220);   
        //显示该Toast   
        toast.show();   
    }   

}
