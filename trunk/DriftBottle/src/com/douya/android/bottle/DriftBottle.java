package com.douya.android.bottle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.douya.android.R;
import com.douya.android.bottle.activity.RegsiterActivity;
import com.douya.android.bottle.service.WeatherService;
import com.eoemobile.api.EnhancedAgent;

public class DriftBottle extends Activity {
	private TextView titleTextView = null;
	private TextView contentTextView = null;
	private TextView contentSubTextView = null;
	private TextView emailTextview = null;
	private TextView passwordTextview = null;
	/**
	 * 邮箱编辑框
	 */
	private EditText emailEditText = null;
	/**
	 * 保存密码CheckBox
	 */
	private CheckBox saveuserinfoCheckbox = null;
	/**
	 * 立即注册按钮
	 */
	private ImageButton regsiterButton = null;
	/**
	 * 登录按钮
	 */
	private ImageButton loginButton = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        EnhancedAgent.init(this);


        Intent intent = new Intent();
        intent.setClass(DriftBottle.this, WeatherService.class);
        System.out.println("onCreate=========启动天气Service");
        startService(intent);
        titleTextView = (TextView)findViewById(R.id.loginmain_title);
        titleTextView.setText(R.string.loginmain_title);
        
        contentTextView = (TextView)findViewById(R.id.loginmain_content);
        contentTextView.setText(R.string.loginmain_content);
        
        contentSubTextView = (TextView)findViewById(R.id.loginmain_content_sub);
        contentSubTextView.setText(R.string.loginmain_content_sub);
        
        emailTextview = (TextView)findViewById(R.id.loginmain_email_textview);
        emailTextview.setText(R.string.loginmain_email);
        
        passwordTextview = (TextView)findViewById(R.id.loginmain_password_textview);
        passwordTextview.setText(R.string.loginmain_password);
        
        saveuserinfoCheckbox = (CheckBox)findViewById(R.id.loginmain_saveuserinfo_checkbox);
        saveuserinfoCheckbox.setText(R.string.loginmain_save_password);
        
        loginButton = (ImageButton)findViewById(R.id.loginmain_loginButton);
        regsiterButton = (ImageButton)findViewById(R.id.loginmain_registerButton);
        
        emailEditText = (EditText)findViewById(R.id.loginmain_email_edittext);

        regsiterButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent =new Intent();
				intent.setClass(DriftBottle.this, RegsiterActivity.class);
				DriftBottle.this.startActivity(intent);
			}
		});
        
    }
    
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		EnhancedAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		EnhancedAgent.onResume(this);
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		System.out.println("finish=========停止天气Service");
		Intent intent = new Intent();
		intent.setClass(DriftBottle.this, WeatherService.class);
		stopService(intent);
		super.finish();
	}
}