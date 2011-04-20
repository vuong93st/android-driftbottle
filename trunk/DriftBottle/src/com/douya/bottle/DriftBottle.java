package com.douya.bottle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

public class DriftBottle extends Activity {
	private TextView titleTextView = null;
	private TextView contentTextView = null;
	private TextView contentSubTextView = null;
	private TextView emailTextview = null;
	private TextView passwordTextview = null;
	private CheckBox saveuserinfoCheckbox = null;
	private ImageButton loginButton = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
    }
}