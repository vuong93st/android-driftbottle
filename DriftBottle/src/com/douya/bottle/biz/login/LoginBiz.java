
package com.douya.bottle.biz.login;

import java.util.Map;

import com.douya.base.util.WebUtils;

/**
 * 登录
 */
public class LoginBiz {
	private static final String TAG = "LoginBiz";
	private static LoginBiz loginBiz;

	private LoginBiz() {
		
	}

	public static LoginBiz getInstance() {
		if (null == loginBiz) {
			loginBiz = new LoginBiz();
		}
		return loginBiz;
	}

	/**
	 * 
	 * TODO: 验证用户名、密码是否正确.
	 * 
	 * @param loginName
	 * @param loginPwd
	 * @return
	 */
	public String validate(String actionUrl, Map<String, String> fields) {
		return WebUtils.httpPost(actionUrl, fields);
	}
}
