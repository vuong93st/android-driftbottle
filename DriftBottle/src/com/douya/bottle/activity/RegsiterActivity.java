package com.douya.bottle.activity;

import com.douya.bottle.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class RegsiterActivity extends Activity{

	private TextView emailTextview = null;
	private TextView passwordTextview = null;
	//private TextView confirmPasswordTextview = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		emailTextview = (TextView)findViewById(R.id.loginmain_email_textview);
        emailTextview.setText(R.string.loginmain_email);
        
        passwordTextview = (TextView)findViewById(R.id.loginmain_password_textview);
        passwordTextview.setText(R.string.loginmain_password);
        
        /*confirmPasswordTextview = (TextView)findViewById(R.id.confirm_password_textview);
        confirmPasswordTextview.setText(R.string.confirm_password);*/
	}

	
}
