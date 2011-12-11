package com.douya.base.xml.handler;

import org.xml.sax.helpers.DefaultHandler;

public abstract class BaseHandler extends DefaultHandler{

	public abstract Object getParseContent();

}
