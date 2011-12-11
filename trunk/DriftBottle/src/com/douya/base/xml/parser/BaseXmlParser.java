package com.douya.base.xml.parser;

// ~ File Information
// ============================================================================

/**
 * @Title: xml文件解析接口.
 * @Description: 1.子类需提供parse()实现,以提供具体的解析方式.
 * 				 2.setContent(),getContent()具体的设置和获取解析完数据值.
 */
public interface BaseXmlParser {

	// ~ Static Fields
	public static final String DEBUG_TAG = "BaseSaxParser";
	// ==========================================================================

	// ~ Methods
	/**
	 * 解析方法.
	 */
	void parse();
	
	/**
	 * 
	 * TODO: ,返回parse()解析完成的object,客户端可按需求转型成需要的类型.
	 * 	TODO:
	 * 
	 * @return
	 */
	Object getContent();
	
	/**
	 * 
	 * TODO: 设置解析完成的数据.
	 * 	TODO:
	 * 
	 * @param content
	 */
	void setContent(Object content);
	
	/**
	 * 
	 * TODO: 待解析的xml数据.
	 * 	TODO:
	 * 
	 * @param xml
	 */
	void setXml(String xml);
	// ==========================================================================

}
