package com.douya.android.bottle;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.douya.android.R;
import com.douya.android.bottle.activity.RegsiterActivity;
import com.douya.android.bottle.model.Account;
import com.douya.android.bottle.service.DateDeserializer;
import com.douya.android.bottle.service.JsonDataGetApi;
import com.douya.android.core.activity.LocationActivity;
import com.eoemobile.api.EnhancedAgent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

	
	public void getJsonData() {
		JsonDataGetApi api = new JsonDataGetApi();
		JSONArray jArr;
		JSONObject jobj;
		try {
			//调用GetAccountData方法
			jArr = api.getArray("GetAccountData");
			//从返回的Account Array中取出第一个数据
			jobj = jArr.getJSONObject(0);
			
			GsonBuilder gsonb = new GsonBuilder();
			//Json中的日期表达方式没有办法直接转换成我们的Date类型, 因此需要单独注册一个Date的反序列化类.
			DateDeserializer ds = new DateDeserializer();
			//给GsonBuilder方法单独指定Date类型的反序列化方法
			gsonb.registerTypeAdapter(Date.class, ds);
			
			Gson gson = gsonb.create();

			Account account = gson.fromJson(jobj.toString(), Account.class);

			Log.d("LOG_CAT", jobj.toString());
			/*((TextView) findViewById(R.id.Name)).setText(account.Name);
			((TextView) findViewById(R.id.Age)).setText(String.valueOf(account.Age));
			((TextView) findViewById(R.id.Birthday)).setText(account.Birthday
					.toGMTString());
			((TextView) findViewById(R.id.Address)).setText(account.Address);*/

		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), e.getMessage(),
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}
}