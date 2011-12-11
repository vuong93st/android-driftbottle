package com.douya.base.ctrl;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaseTabActivity extends TabActivity {
	private static final String TAG = "BaseTabActivity";

	/**
	 * 当前显示的Activity
	 */
	private Activity activity;

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	public Activity getActivity() {
		return activity;
	}

	public void exitConfirm(final Activity activity) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
		dialogBuilder.setTitle("退出");
		dialogBuilder.setMessage("确定退出应用程序吗?");
		dialogBuilder.setPositiveButton("是",
				new DialogInterface.OnClickListener() {// 退出按钮

					@Override
					public void onClick(DialogInterface dialog, int i) {
						// 退出应用方法
						activity.finish();
						System.exit(0);
						android.os.Process.killProcess(android.os.Process
								.myPid());
						ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
						am.restartPackage(getPackageName());
						am.killBackgroundProcesses(getPackageName());
					}
				});
		dialogBuilder.setNegativeButton("否",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int i) {

						// 不退出不用执行任何操作
					}
				});
		dialogBuilder.show();// 显示对话框
	}
	
	/**   
     * 这个设置Tab标签本身的布局，需要TextView和ImageView不能重合   
     * s:是文本显示的内容   
     * i:是ImageView的图片资源，如：R.drawable.tab_btn_home   
     * 将它设置到setIndicator(composeLayout("主页", R.drawable.home))中   
     */   
    public View composeLayout(String s, int i){  
        LinearLayout layout = new LinearLayout(this);   
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setWeightSum(1);
        layout.setGravity(Gravity.CENTER);
        ImageView imageView = new ImageView(this);
        imageView.setPadding(0, 5, 0, 0);
        imageView.setImageResource(i);   
        layout.addView(imageView,    
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        
        TextView textView = new TextView(this);   
        textView.setGravity(Gravity.CENTER);   
        textView.setSingleLine(true);   
        textView.setText(s);   
        textView.setTextColor(Color.WHITE);
       layout.addView(textView,    
               new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));   
        
        return layout;   
    }
}
