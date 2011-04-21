package com.douya.bottle.activity;

import com.douya.bottle.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class RegsiterActivity extends Activity{

	private TextView emailTextview = null;
	private TextView passwordTextview = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		emailTextview = (TextView)findViewById(R.id.loginmain_email_textview);
        emailTextview.setText(R.string.loginmain_email);
        
        passwordTextview = (TextView)findViewById(R.id.loginmain_password_textview);
        passwordTextview.setText(R.string.loginmain_password);
	}

	
}
