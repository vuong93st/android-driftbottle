package com.douya.bottle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adview.AdViewLayout;
import com.douya.R;
import com.douya.base.map.LocationActivity;
import com.douya.bottle.activity.RegsiterActivity;
import com.waps.AppConnect;

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
	 * 密码框
	 */
	private EditText passwordEditText = null;
	
	/**
	 * 保存密码CheckBox
	 */
	private CheckBox saveuserinfoCheckbox = null;
	/**
	 * 立即注册按钮
	 */
	private ImageView regsiterButton = null;
	/**
	 * 登录按钮
	 */
	private ImageView loginButton = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //EnhancedAgent.init(this);//市场服务
        initLocation();//初始化位置服务
		
        titleTextView = (TextView)findViewById(R.id.loginmain_title);
        titleTextView.setText(R.string.loginmain_title);
        
        contentTextView = (TextView)findViewById(R.id.loginmain_content);
        contentTextView.setText(R.string.loginmain_content);
        
        contentSubTextView = (TextView)findViewById(R.id.loginmain_content_sub);
        contentSubTextView.setText(R.string.loginmain_content_sub);
        
        emailTextview = (TextView)findViewById(R.id.loginmain_email_textview);
        emailTextview.setText(R.string.login_name_title);
        
        passwordTextview = (TextView)findViewById(R.id.loginmain_password_textview);
        passwordTextview.setText(R.string.login_pwd_title);
        
        saveuserinfoCheckbox = (CheckBox)findViewById(R.id.loginmain_saveuserinfo_checkbox);
        saveuserinfoCheckbox.setText(R.string.opt_remember_account);
        
        loginButton = (ImageView)findViewById(R.id.loginmain_loginButton);
        regsiterButton = (ImageView)findViewById(R.id.loginmain_registerButton);
        
        emailEditText = (EditText)findViewById(R.id.loginmain_email_edittext);
        passwordEditText = (EditText)findViewById(R.id.loginmain_passowrd_edittext);
        
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
        
        loginButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				//Login(emailEditText.getText().toString(),passwordEditText.getText().toString());

			}
		});
        
        //广告
        LinearLayout layout = (LinearLayout)findViewById(R.id.adLayout);
        /* 下面两行仅仅用于调试，发布前注释掉*/
        //AdViewTargeting.setTestMode(true);
        //AdViewManager.setConfigExpireTimeout(-1);

        AdViewLayout adViewLayout = new AdViewLayout(this, "SDK20111224150629118q8ighe3eyste");
        RelativeLayout.LayoutParams adViewLayoutParams = new 
        RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, 50);
        layout.addView(adViewLayout, adViewLayoutParams);
        layout.invalidate();
     // 连接服务器. 应用启动时调用.
        //AppConnect.getInstance(this);
        
      //AdGroupTargeting.setTestMode(true);// 该行仅用于调试，发布前去掉
    	//LinearLayout container = (LinearLayout)findViewById(R.id.AdLinearLayout);
    	//container.addView(new AdGroupLayout (this));//调用代码仅这行和互动广告不同

      //显示多家平台广告 (兼容AdMob等广告平台)
       /*LinearLayout container =(LinearLayout)findViewById(R.id.AdLinearLayout);
       container.addView(new AdGroupLayout(this));*/


        //获取用户IP所在地区
//        String area=AppConnect.getInstance(this).getArea();
//        Toast.makeText(this, "您所在地区:" + area, Toast.LENGTH_SHORT).show();
    }
    
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//EnhancedAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//EnhancedAgent.onResume(this);
	}

	@Override
	public void finish() {
		AppConnect.getInstance(this).finalize();
		super.finish();
	}
}