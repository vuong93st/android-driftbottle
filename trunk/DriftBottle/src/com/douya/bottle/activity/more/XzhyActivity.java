package com.douya.bottle.activity.more;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.douya.R;

public class XzhyActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xzhy);
		TextView xzhyTitle = (TextView)findViewById(R.id.xzhyTitle);
		TextView xzhy2Title = (TextView)findViewById(R.id.xzhy2Titile);
		EditText xzhyEditText = (EditText)findViewById(R.id.xzhyEditText);
		Button xzhyBtn = (Button)findViewById(R.id.xzhyBtn);
		xzhyTitle.setText(R.string.moreitem5);
		xzhy2Title.setText(R.string.xzhy_2_title);
		xzhyEditText.setText(R.string.xzhy_edittext);
		xzhyBtn.setText(R.string.xzhy_btntext);
		
	}
	

}
