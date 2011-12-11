package com.douya.base.entity.exception;

import com.douya.base.entity.BaseEntity;

/**
 * 异常实体类
 * @author GeYunjie
 *
 */
public class ExceptionEntity  extends BaseEntity{
	private String code;
	private String value;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
