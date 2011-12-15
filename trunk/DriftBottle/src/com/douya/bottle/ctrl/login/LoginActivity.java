package com.douya.bottle.ctrl.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.douya.R;
import com.douya.base.entity.exception.ExceptionEntity;
import com.douya.bottle.activity.RegsiterActivity;
import com.douya.bottle.app.BottleApp;
import com.douya.bottle.biz.login.LoginBiz;
import com.waps.AppConnect;

public class LoginActivity extends Activity {
	private static final String TAG = "LoginActivity";

	private LoginBiz loginBiz = LoginBiz.getInstance();
	
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
	 * 密码框
	 */
	private EditText passwordEditText = null;
	/**
	 * 保存密码CheckBox
	 */
	private CheckBox cbRememberAccount = null;
	/**
	 * 立即注册按钮
	 */
	private ImageView regsiterButton = null;
	/**
	 * 登录按钮
	 */
	private ImageView loginButton = null;
	/**
	 * 进度条
	 */
	private ProgressDialog progressDialog;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Toast toast = null;
			String res = (String) msg.obj;
			switch (msg.what) {
			case R.id.exception:
				// 异常处理
				int start = res.indexOf("=") + 1;
				String code = res.substring(start);
				ExceptionEntity entity = BottleApp.getExceptions().get(code);
				String promptStr = null;
				if (null != entity) {
					promptStr = entity.getValue();
				} else {
					promptStr = getResources().getString(R.string.unknownError);
				}
				toast = Toast.makeText(LoginActivity.this, promptStr,
						Toast.LENGTH_SHORT);
				break;
			}
			if (null != toast) {
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			progressDialog.dismiss();
		}
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //EnhancedAgent.init(this);//市场服务
		
        titleTextView = (TextView)findViewById(R.id.loginmain_title);
        titleTextView.setText(R.string.loginmain_title);
        
        contentTextView = (TextView)findViewById(R.id.loginmain_content);
        contentTextView.setText(R.string.loginmain_content);
        
        contentSubTextView = (TextView)findViewById(R.id.loginmain_content_sub);
        contentSubTextView.setText(R.string.loginmain_content_sub);
        
        loginButton = (ImageView)findViewById(R.id.loginmain_loginButton);
        regsiterButton = (ImageView)findViewById(R.id.loginmain_registerButton);
        
        emailEditText = (EditText)findViewById(R.id.loginmain_email_edittext);
        passwordEditText = (EditText)findViewById(R.id.loginmain_passowrd_edittext);
        
        regsiterButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				RelativeLayout layout = (RelativeLayout)findViewById(R.id.login_layout);
				Intent intent =new Intent();
				intent.setClass(LoginActivity.this, RegsiterActivity.class);
				LoginActivity.this.startActivity(intent);
			}
		});
        
        loginButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
			}
		});
    }
    
    @Override
	protected void onPause() {
		super.onPause();
		//EnhancedAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		//EnhancedAgent.onResume(this);
	}

	@Override
	public void finish() {
		AppConnect.getInstance(this).finalize();
		super.finish();
	}
}