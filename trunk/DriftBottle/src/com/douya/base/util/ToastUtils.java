/**
 * @(#)com.douya.base.util.ToastUtils.java
 * 版权声明 山东益信通科贸有限公司, 版权所有 违者必究
 *
 *<br> Copyright:Copyright (c) 2010-2011
 *<br> Company： 山东益信
 *<br> Author： 葛云杰(geyj@douya.cn)
 *<br> Date：2011-09-17
 *<br> Version：1.0
 */
package com.douya.base.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Toast提示工具类
 * @author GeYunjie
 *
 */
public class ToastUtils {

	// 默认效果
	public static void defaultToast(Context context, String prompt) {
		Toast.makeText(context, prompt, Toast.LENGTH_SHORT).show();
	}

	// 自定义显示位置效果
	public static void gravityToast(Context context, int gravity, String prompt) {
		Toast toast = Toast.makeText(context, prompt, Toast.LENGTH_LONG);
		toast.setGravity(gravity, 0, 0);
		toast.show();
	}

	// 带图片的Toast
	public static void pictureToast(Context context, int gravity,
			String prompt, int picRes) {
		Toast toast = Toast.makeText(context, prompt, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		ImageView imageCodeProject = new ImageView(context);
		imageCodeProject.setImageResource(picRes);
		toastView.addView(imageCodeProject, 0);
		toast.show();
	}

	// 完全自动以Toast
	/*
	 * public static void entirUserDefined(Activity activity) { LayoutInflater
	 * inflater = activity.getLayoutInflater(); View layout =
	 * inflater.inflate(R.layout.custom, (ViewGroup)
	 * findViewById(R.id.llToast)); ImageView image = (ImageView)
	 * layout.findViewById(R.id.tvImageToast);
	 * image.setImageResource(R.drawable.icon); TextView title = (TextView)
	 * layout.findViewById(R.id.tvTitleToast); title.setText("Attention");
	 * TextView text = (TextView) layout.findViewById(R.id.tvTextToast);
	 * text.setText("完全自定义Toast"); Toast toast = new Toast(activity);
	 * toast.setGravity(Gravity.RIGHT | Gravity.TOP, 12, 40);
	 * toast.setDuration(Toast.LENGTH_LONG); toast.setView(layout);
	 * toast.show(); }
	 */

}
