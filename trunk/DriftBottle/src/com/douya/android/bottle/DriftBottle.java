package com.douya.android.bottle;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.douya.android.R;
import com.douya.android.bottle.activity.RegsiterActivity;
import com.douya.android.core.activity.LocationActivity;
import com.douya.android.core.dao.DatabaseHelper;
import com.eoemobile.api.EnhancedAgent;

public class DriftBottle extends LocationActivity {
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
        EnhancedAgent.init(this);//市场服务
        initLocation();//初始化位置服务
        

		
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
				RelativeLayout layout = (RelativeLayout)findViewById(R.id.login_layout);
				Animation animation = AnimationUtils.loadAnimation(DriftBottle.this, R.anim.rotate);
				layout.startAnimation(animation);
				Intent intent =new Intent();
				intent.setClass(DriftBottle.this, RegsiterActivity.class);
				DriftBottle.this.startActivity(intent);
			}
		});
        
        /*Thread thread = new Thread(update);// getWeather("济南");	
		thread.start();*/
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

}