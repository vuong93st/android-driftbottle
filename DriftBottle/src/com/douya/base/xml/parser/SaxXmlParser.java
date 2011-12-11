package com.douya.base.xml.parser;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;
import android.util.Xml;

import com.douya.base.xml.handler.DefaultSaxHandler;

/**
 * @Title: Android的sax解析器,本质上还是sax解析,用到android.sax包.
 * @Description: <br/>
 * 
 */
public class SaxXmlParser extends DefaultSaxXmlParser{
	
	// ~ Static Fields
	private static final String TAG = "SaxXmlParser";
	// ==========================================================================

	// ~ Fields
	// ==========================================================================

	// ~ Constructors
	// ==========================================================================

	// ~ Static Methods
	// ==========================================================================

	// ~ Methods
	/**
	 * TODO: xml解析方法的默认实现.
	 * 	TODO: 
	 * 
	 * @see com.douya.agro.xml.parser.BaseXmlParser#parse()
	 */
	@Override
	public void parse() {
		Log.i(TAG, "SaxXmlParser parse()");
		try {
			Xml.parse(getXml(), getHandler());
			setContent(getHandler().getParseContent());
			Log.i(TAG, "解析完毕");
		} catch (SAXException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Object parse2(){
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			if(null == getHandler()){
				setHandler(new DefaultSaxHandler());
			}
			parser.parse(new InputSource(new StringReader(getXml())), getHandler());
			return getHandler().getParseContent();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void parseInpuSource(){
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			if(null == getHandler()){
				setHandler(new DefaultSaxHandler());
			}
			parser.parse(getInputSource(), getHandler());
			setContent(getHandler().getParseContent());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	// ==========================================================================

	// ~ Getter Setter Methods
	// ==========================================================================

}
