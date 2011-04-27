package com.douya.bottle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.douya.bottle.activity.RegsiterActivity;

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
    
    
}