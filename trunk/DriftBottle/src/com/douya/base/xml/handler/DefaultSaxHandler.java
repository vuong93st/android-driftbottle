package com.douya.base.xml.handler;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.douya.base.entity.exception.ExceptionEntity;


/**
 * @Title: sax 具体解析方式默认实现.
 * @Description: 这里默认实现的是异常值的解析(参考xml文档结构).<br/>
 *               <?xml version="1.0" encoding="utf-8"?> <exceptoins> <exception>
 *               <code>0001</code> <value>连接超时</value> </exception> <exception>
 *               <code>0002</code> <value>服务器异常</value> </exception>
 *               </exceptoins> <br/>
 *               <!--
 * 
 *               Modification History： Date Author Version Description
 *               ----------
 *               ------------------------------------------------------
 *               ------------ 2011-3-19 Anders.Zhang 1.0 Build File
 * 
 *               -->
 * 
 * @author Anders.Zhang,mailto:<a
 *         href="mailto:zhangdq@douya.cn">Anders.Zhang</a>
 * @version 1.0
 * 
 */
public class DefaultSaxHandler extends BaseHandler {

	// ~ Static Fields
	private static final String TAG = "DefaultSaxHandler";
	// ==========================================================================

	// ~ Fields
	private Map<String, ExceptionEntity> exceptions;
	private ExceptionEntity exception;
	private StringBuilder builder;

	// ==========================================================================

	// ~ Constructors
	// ==========================================================================

	// ~ Static Methods
	// ==========================================================================

	// ~ Methods
	/**
	 * 遇到文本节点中的字符数据时便会调用此方法
	 */
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		builder.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		super.endElement(uri, localName, name);
		if (null != exception) {
			if (localName.equalsIgnoreCase("code")) {
				exception.setCode(trim(builder.toString()));
			} else if (localName.equalsIgnoreCase("value")) {
				exception.setValue(trim(builder.toString()));
			} else if (localName.equalsIgnoreCase("exception")) {
				exceptions.put(exception.getCode(), exception);
			}
			builder.setLength(0);
		}
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		if (null == exceptions) {
			exceptions = new HashMap<String, ExceptionEntity>();
		} else {
			exceptions.clear();
		}
		builder = new StringBuilder();
	}

	

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);
		if ("exception".equalsIgnoreCase(localName)) {
			this.exception = new ExceptionEntity();
		}
	}

	private String trim(String strToTrim) {
		if(null == strToTrim){
			return null;
		}
		return strToTrim.trim();
	}
	// ==========================================================================

	// ~ Getter Setter Methods
	@Override
	public Object getParseContent() {
		return exceptions;
	}

	// ==========================================================================

}
