package com.douya.base.xml.parser;

import org.xml.sax.InputSource;

/**
 * @Title: Write a overview of the file here.
 * @Description: Write a detailed description of the file here.
 *  
 * 
 */
public abstract class DefaultXmlParser implements BaseXmlParser {

	

	// ~ Static Fields
	// ==========================================================================

	// ~ Fields
	//解析完成后数据放入content中
	private Object content;
	
	//待解析的xml数据
	private String xml;
	private InputSource inputSource;
	// ==========================================================================

	// ~ Constructors
	public DefaultXmlParser(){}
	public DefaultXmlParser(String xml){
		this.xml = xml;
	}
	// ==========================================================================

	// ~ Static Methods
	// ==========================================================================

	// ~ Methods
	// ==========================================================================

	// ~ Getter Setter Methods
	/**
	 * TODO: 填入方法概括.
	 * 	TODO: 填入方法说明.
	 * 
	 * @see com.douya.sjxl.xml.parser.BaseXmlParser#getContent()
	 */
	public Object getContent(){
		return this.content;
	}

	/**
	 * TODO: 填入方法概括.
	 * 	TODO: 填入方法说明.
	 * 
	 * @see com.douya.sjxl.xml.parser.BaseXmlParser#setContent(java.lang.Object)
	 */
	public void setContent(Object content){
		this.content = content;
	}
	
	public String getXml() {
		return xml;
	}

	@Override
	public void setXml(String xml) {
		this.xml = xml;
	}
	// ==========================================================================
	public InputSource getInputSource() {
		return inputSource;
	}
	public void setInputSource(InputSource inputSource) {
		this.inputSource = inputSource;
	}

}
