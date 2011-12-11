package com.douya.base.xml.parser;

import com.douya.base.xml.handler.BaseHandler;

/**
 * @Title: 默认的xml解析器实现.
 * @Description:
 * 
 */
public abstract class DefaultSaxXmlParser extends DefaultXmlParser {

	// ~ Static Fields
	// ==========================================================================

	// ~ Fields
	
	//sax xml解析器
	private BaseHandler baseHandler;
	
	// ==========================================================================

	// ~ Constructors
	// ==========================================================================

	// ~ Static Methods
	// ==========================================================================

	// ~ Methods
	// ==========================================================================

	// ~ Getter Setter Methods
	
	public BaseHandler getHandler() {
		return baseHandler;
	}

	public void setHandler(BaseHandler handler) {
		this.baseHandler = handler;
	}

	@Override
	public Object getContent() {
		return getHandler().getParseContent();
	}
	
	// ==========================================================================

}
