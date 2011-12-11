/**
 * @(#)com.douya.base.util.StrUtils.java
 * 版权声明 山东益信通科贸有限公司, 版权所有 违者必究
 *
 *<br> Copyright:Copyright (c) 2010-2011
 *<br> Company： 山东益信
 *<br> Author： 葛云杰(geyj@douya.cn)
 *<br> Date：2011-09-17
 *<br> Version：1.0
 */
package com.douya.base.util;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
/**
 * 字符串工具类，可以对字符串进行操作，如字符串替换，检查一个字符串是否为空格等
 */
public class StrUtils {

	
	private StringBuffer sb = null;
	private String sysLineSeparator = null;

	/**
	 * 构造函数
	 *
	 * 初始化sb对象 及 sysLineSeparator对象
	 */
	public StrUtils() {
		if (sb != null) {
			sb = null;
		} else {
			sb = new StringBuffer();
		}
		sysLineSeparator = System.getProperty("line.separator");
	}

	public static String requote(String str) {
		return (str == null) ? "" : str;
	}

	public static String GBKToISO(String s) throws Exception //存值
	{
		if (s != null) {
			return new String(s.getBytes("GBK"), "ISO8859_1");
		} else {
			return "";
		}
	}

	public static String ISOToGBK(String s) throws Exception { //取值
		if (s != null) {
			return new String(s.getBytes("ISO8859_1"), "GBK");
		} else {
			return "";
		}
	}

	public static String gbToUtf8(String src) {
		byte[] b = src.getBytes(); //it will be getBytes("ISO8859_1");
		char[] c = new char[b.length];
		for (int i = 0; i < b.length; i++) {
			c[i] = (char) (b[i] & 0x00FF);
		}
		return new String(c);
	}

	/**
	 * 如果为null或"",转换为"0";如果不为null,除去两边的空格
	 * 
	 * @param obj
	 *           Object
	 * @return String
	 */
	public static String null2Zero(Object obj) {

		if ((obj == null) || (obj.equals("")) || (obj.equals("null"))) {
			return "0";
		} else {
			return obj.toString().trim();
		}
	}

	/**
	 * 如果为null，转换为"";如果不为null，除去两边的空格
	 * 
	 * @param obj
	 *           Object
	 * @return String
	 */
	public static String null2Str(Object obj) {

		if ((obj == null) || (obj.equals("null")) || (obj.equals(""))) {
			return "";
		} else {
			return obj.toString().trim();
		}
	}

	/**
	 * 将一个字符串按给定分割字符串分割成数组
	 *
	 * @param str 待分割的字符串
	 * @param seperator 分隔符
	 * @return 字符串数组
	 */
	public static String[] split(String source, char useChar) {
		List list = new ArrayList();
		String sub;
		String[] result;

		if (source.charAt(0) == useChar)
			source = source.substring(1, source.length());
		if (source.charAt(source.length() - 1) == useChar)
			source = source.substring(0, source.length() - 1);

		int start = 0;
		int end = source.indexOf(useChar);
		while (end > 0) {
			sub = source.substring(start, end);
			list.add(sub);
			start = end + 1;
			end = source.indexOf(useChar, start);
		}

		sub = source.substring(start, source.length());
		list.add(sub);

		result = new String[list.size()];

		Iterator iter = list.iterator();
		int i = 0;
		while (iter.hasNext()) {
			result[i++] = (String) iter.next();
		}
		return result;
	}
	
	/**
	 * 将一个字符串按给定分割字符串分割成数组
	 *
	 * @param str 待分割的字符串
	 * @param seperator 分隔符
	 * @return 字符串数组
	 */
	public static List splitList(String source, char useChar) {
		List list = new ArrayList();
		String sub;

		if (source.charAt(0) == useChar)
			source = source.substring(1, source.length());
		if (source.charAt(source.length() - 1) == useChar)
			source = source.substring(0, source.length() - 1);

		int start = 0;
		int end = source.indexOf(useChar);
		while (end > 0) {
			sub = source.substring(start, end);
			list.add(sub);
			start = end + 1;
			end = source.indexOf(useChar, start);
		}

		sub = source.substring(start, source.length());
		list.add(sub);

		
		return list;
	}

	/**
	 * 判断给定子字符串<code>subStr</code>是否在大字符串<code>str</code>
	 *
	 * @param subStr 子字符串
	 * @param str 大字符串
	 * @return 如果存在则返回true,否则返回false
	 */
	public static boolean isIn(String subStr, String str) {
		if (subStr == null || str == null) {
			return false;
		}
		if (str.indexOf(subStr) == -1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 将数组中的每一项按默认连接符(,)连接起来
	 *
	 * @param str[] 待连接的字符串数组
	 * @return 用默认字符串（，）将str[]中的每一项串起来的字符串
	 */
	public static String join(String[] str) {
		return join(str, ",");
	}

	/**
	 * 将数组中的每一项按给定连接符连接
	 *
	 * @param str[] 待连接的字符串数组
	 * @param join  字符串连接符
	 * @return 用给定字符串将str[]中的每一项串起来的字符串
	 */
	public static String join(String[] str, String join) {
		if (str == null || join == null) {
			return "";
		}
		String rtnStr = "";
		for (int i = 0; i < str.length; i++) {
			rtnStr += join + str[i];
		}
		if (rtnStr.indexOf(join) != -1) {
			rtnStr = rtnStr.substring(1);
		}
		return rtnStr;
	}

	/**
	 * 字符串替换
	 * @param line 是源字符串
	 * @param oldString  是将要被替换掉的子字符串
	 * @param newString  是将要用来替换旧子字符串的字符串
	 * @return 返回替换后的结果字符串
	 */
	public static final String replace(
		String line,
		String oldString,
		String newString) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * 字符串替换，同时在替换的过程中newString中与oldString中相匹配的子字符串忽略，不替换。
	 * @param line  是源字符串
	 * @param oldString  是将要被替换掉的字符串
	 * @param newString  是将要用来替换旧字符串的字符串
	 * @return 返回替换后的结果字符串
	 */
	public static final String replaceIgnoreCase(
		String line,
		String oldString,
		String newString) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * 字符串替换，并且统计替换的个数
	 * @param line  是源字符串
	 * @param oldString  是将要被替换掉的字符串
	 * @param newString  是将要用来替换旧字符串的字符串
	 * @return 返回替换后的结果字符串
	 */
	public static final String replace(
		String line,
		String oldString,
		String newString,
		int[] count) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			int counter = 0;
			counter++;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}

	/** 检查一个字符串是否全部为空格
	 *  @param input  是要检查的字符串
	 *  @return 如果字符串为空格则返回false,否则返回strTemp.length()!
	 */
	public static boolean checkDataValid(String input) {
		String strTemp = new String(input);
		if (strTemp == null || strTemp.length() == 0) {
			return false;
		}
		strTemp = strTemp.trim();
		return strTemp.length() != 0;
	}

	/** 替换输入字符串中的非法字符
	 *  @param input  是要替换的字符串
	 *  @return 如果字符串为空则返回input,否则返回buf.toString()替换后的字符串
	 */
	public static String escapeHTML(String input) {

		if (input == null || input.length() == 0) {
			return input;
		}
		input = input.trim();
		StringBuffer buf = new StringBuffer();
		char ch = ' ';
		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
			if (ch == '<') {
				buf.append("&lt;");
			} else if (ch == '>') {
				buf.append("&gt;");
			} else if (ch == '\n') {
				buf.append("<br/>");
			} else if (ch == ' ') {
				buf.append("&nbsp;");
			} else if (ch == '\'') {
				buf.append("''");
			} else {
				buf.append(ch);
			}
		} //end for loop
		return buf.toString();
	}

	/**
	 * 转换一个包含有HTML标志(ie, &lt;b&gt;,&lt;table&gt;, etc)的字符串，将里面的'>'和'<'转换成'&lt'' and '&gt;'
	 * @param input 一篇用于转换的text
	 * @return 返回转换后的字符串
	 */
	public static final String escapeHTMLTags(String input) {
		/** 检查是否输入的字符串为空或者长度为零，如果是，则返回输入的字符串返回 */
		if (input == null || input.length() == 0) {
			return input;
		}
		/** 在这里使用StringBuffer型，是非常有效的途径 */
		StringBuffer buf = new StringBuffer(input.length());
		char ch = ' ';
		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
			if (ch == '<') {
				buf.append("&lt;");
			} else if (ch == '>') {
				buf.append("&gt;");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	/**
	 * 追加字符串到字符串缓冲器
	 *
	 * @param str 要追加的字符串
	 */
	public void append(String str) {
		sb.append(str);
	}

	/**
	 * 追加字符串到字符串缓冲器 并追加一个缺省的换行符\n
	 *
	 * @param str 要追加的字符串
	 */
	public void appendln(String str) {
		appendln(str, false);
	}

	/**
	 * 追加字符串到字符串缓冲器 并根据参数<code>isSysLineSeparator</code>不同追加缺省的换行符\n或系统换行符
	 *
	 * @param str 要追加的字符串
	 * @param useSysLineSeparator 是否追加系统换行符表识
	 */
	public void appendln(String str, boolean useSysLineSeparator) {
		if (useSysLineSeparator) {
			sb.append(str);
			sb.append(this.sysLineSeparator);
		} else {
			sb.append(str);
			sb.append("\n");
		}
	}

	/**
	 * 转换字符串缓冲器中的数据为字符串表示
	 * @ return 表示字符串缓冲器中的数据的字符串
	 */
	public String toStr() {
		return sb.toString();
	}

	/**
	 * 把一篇文档分割成小写的，以 , .\r\n:/\+为分割符的数组。
	 * 将来，这种方法应该是被改变后用于BreakIterator.wordInstance()方法。
	 * 这种class提供了更多的灵活性。
	 * @param text  要被分割成数组的文档
	 * @return 返回分割成的数组
	 */
	public static final String[] toLowerCaseWordArray(String text) {
		if (text == null || text.length() == 0) {
			return new String[0];
		}
		StringTokenizer tokens = new StringTokenizer(text, " ,\r\n.:/\\+");
		String[] words = new String[tokens.countTokens()];
		for (int i = 0; i < words.length; i++) {
			words[i] = tokens.nextToken().toLowerCase();
		}
		return words;
	}

	public static String[] objectArray2StringArray(Object[] objs) {
		if (objs == null)
			return null;
		String[] s = new String[objs.length];
		System.arraycopy(objs, 0, s, 0, s.length);
		return s;
	}

	//加密
	public static final String encrypt(String str) {

		String string_in = "YN8K1JOZVURB3MDETS5GPL27AXWIHQ94C6F0#$_";
		String string_out = "_$#ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String outpass = "";
		try {
			if(str!=null)
			{
				int offset = 0;
				Calendar calendar = Calendar.getInstance();
				int ss = calendar.get(Calendar.SECOND);
				offset = ss % 39;
				/*ResultSet rs=dataconn.executeQuery("select MOD(to_number(to_char(sysdate,'ss')),39) from dual");
				 if(rs.next()) offset=rs.getInt(1);
				 rs.close();
				 dataconn.close();*/
				if (offset > 0)
					offset = offset - 1;
				outpass = string_in.substring(offset, offset + 1);
				string_in = string_in + string_in;
				string_in = string_in.substring(offset, offset + 40);
				outpass = outpass + translate(str, string_in, string_out);
				return outpass;
			}else{
				System.out.println(" Sorry, vo.getPwd() is null!");
				return "";
			}
		} catch (Exception e) {
			System.out.println(e);
			return "";
		}
	}

	//解密
	public static final String disencrypt(String str) {

		String string_in = "YN8K1JOZVURB3MDETS5GPL27AXWIHQ94C6F0#$_";
		String string_out = "_$#ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		try {
			int offset = 0;
			char c = str.charAt(0);
			offset = string_in.indexOf(c);
			string_in = string_in + string_in;
			string_in = string_in.substring(offset, offset + 39);
			String s = str.substring(1);
			s = s.toUpperCase();
			String inpass = translate(s, string_out, string_in);
			return inpass;
		} catch (Exception e) {
			System.out.println(e);
			return "";
		}
	}

	public static final String translate(
		String str,
		String string_in,
		String string_out) {

		//String string_in = "YN8K1JOZVURB3MDETS5GPL27AXWIHQ94C6F0#$_";
		//String string_out = "_$#ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		
//		System.err.println("str = "+str);
//		
//		System.err.println("string_in = "+string_in);
//		
//		System.err.println("string_out = "+string_out);		
		
		String s = str.toUpperCase();
//		String s = str;
		
		char[] outc = new char[s.length()];
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int j = string_in.indexOf(c);
			outc[i] = string_out.charAt(j);

		}
		String outs = new String(outc);
		
		
		
		return outs;
	}

	/**
	* 对字符串进行编码,目的是解决数据库中文问题
	* 
	* @param inputStr
	*           String
	* @return String
	*/
	public static String encode(String inputStr) {

		String tempStr = "";
		try {
			tempStr = new String(inputStr.getBytes("ISO-8859-1"));
		} catch (Exception ex) {
			System.out.print("encode() error: " + ex.toString());
		}
		return tempStr;
	}
	
	public static String urlEncode(String url) {
        if (url == null || "".equals(url))
            return "";
        try {
            return java.net.URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static String urlDecode(String encodedUrl) {
        if (encodedUrl == null || "".equals(encodedUrl))
            return "";
        try {
            return java.net.URLDecoder.decode(encodedUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
    
    public static boolean isEmpty(String str) {
        if(str==null||"".equals(str)||"null".equalsIgnoreCase(str)) return true;
        return false;
    }
    
    /**
     * 将浮点型小数转换为百分数，小数点后第三位四舍五入
     * @param f
     * @return
     */
    public static String getRoundPercent(double f) {
        double r = f * 10000;
        String round = String.valueOf(r);
        if(round.indexOf(".")>0){
	        String strIntValue = round.substring(0,round.indexOf("."));
	        int intValue = Integer.parseInt(strIntValue);
	        if(r-intValue >= 0.5) r += 1;
        }
        round = String.valueOf(r/100);
        if(round.length() - round.indexOf(".")>2) {
            round = round.substring(0,round.indexOf(".")+3);
        }
        return round;
    }
}