package com.douya.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

	/**数据库存储的时间格式串，如yyyymmdd 或yyyymmddHHMiSS*/
    public static final int DB_STORE_DATE = 1;
    /**用连字符-分隔的时间时间格式串，如yyyy-mm-dd 或yyyy-mm-dd HH:Mi:SS*/
    public static final int HYPHEN_DISPLAY_DATE = 2;
    /**用连字符.分隔的时间时间格式串，如yyyy.mm.dd 或yyyy.mm.dd HH:Mi:SS*/
    public static final int DOT_DISPLAY_DATE = 3;
    /**用中文字符分隔的时间格式串，如yyyy年mm月dd 或yyyy年mm月dd HH:Mi:SS*/
    public static final int CN_DISPLAY_DATE = 4;
    /*****数据库存储时间格式串,如yyyymmddHHMissSS**/
    public static final int DB_LONG_DATE = 5;
    
    private static final String DEFAULT_FORMAT = "yyyy-MM-dd kk.mm.ss";
	private static final int DAY_SECOND = 24 * 60 * 60;
	private static final int HOUR_SECOND = 60 * 60;

    /**
     * 得到精确到秒的格式化当前时间串
     * @param formatType 时间格式的类型{@link #DB_STORE_DATE},{@link #EN_HTML_DISPLAY_DATE},{@link #CN_HTML_DISPLAY_DATE}
     * @return 当前时间格式化时间串
     */
    public static String getCurrTimeStr(int formatType) {
        return getTimeStr(new Date(), formatType);
    }

    /**
     * 得到精确到秒的格式化时间串
     * @param date 指定时间
     * @param formatType 时间格式的类型{@link #DB_STORE_DATE},{@link #EN_HTML_DISPLAY_DATE},{@link #CN_HTML_DISPLAY_DATE}
     * @return 指定时间的格式化时间串
     */
    public static String getTimeStr(Date date, int formatType) {
        if (formatType < DB_STORE_DATE || formatType > DB_LONG_DATE) {
            throw new IllegalArgumentException("时间格式化类型不是合法的值。");
        } else {
            String formatStr = null;
            switch (formatType) {
            case DB_STORE_DATE:
                formatStr = "yyyyMMddHHmmss";
                break;
            case HYPHEN_DISPLAY_DATE:
                formatStr = "yyyy'-'MM'-'dd HH:mm:ss";
                break;
            case DOT_DISPLAY_DATE:
                formatStr = "yyyy.MM.dd HH:mm:ss";
                break;
            case CN_DISPLAY_DATE:
                formatStr = "yyyy'年'MM'月'dd'日' HH:mm:ss";
                break;
            case DB_LONG_DATE:
                formatStr = "yyyyMMddHHmmssSS";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            return sdf.format(date);
        }
    }

    /**
     * 得到精确到天的当前格式化日期串
     * @param formatType 时间格式的类型{@link #DB_STORE_DATE},{@link #EN_HTML_DISPLAY_DATE},{@link #CN_HTML_DISPLAY_DATE}
     * @return
     */
    public static String getCurrDateStr(int formatType) {
        return getDateStr(new Date(), formatType);
    }

    /**
     * 得到精确到天的指定时间格式化日期串
     * @param date 指定时间
     * @param formatType 时间格式的类型{@link #DB_STORE_DATE},{@link #EN_HTML_DISPLAY_DATE},{@link #CN_HTML_DISPLAY_DATE}
     * @return 指定时间格式化日期串
     */
    public static String getDateStr(Date date, int formatType) {

        if (formatType < DB_STORE_DATE || formatType > CN_DISPLAY_DATE) {
            throw new IllegalArgumentException("时间格式化类型不是合法的值。");
        } else {
            String formatStr = null;
            switch (formatType) {
            case DB_STORE_DATE:
                formatStr = "yyyyMMdd";
                break;
            case HYPHEN_DISPLAY_DATE:
                formatStr = "yyyy-MM-dd";
                break;
            case DOT_DISPLAY_DATE:
                formatStr = "yyyy.MM.dd";
                break;
            case CN_DISPLAY_DATE:
                formatStr = "yyyy'年'MM'月'dd'日'";
                break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            return sdf.format(date);
        }
    }

    /**
     * 得到精确到月的当前时间格式化年月串
     * @param formatType 时间格式的类型{@link #DB_STORE_DATE},{@link #EN_HTML_DISPLAY_DATE},{@link #CN_HTML_DISPLAY_DATE}
     * @return 精确到月当前时间格式化年月串
     */
    public static String getYearMonthStr(int formatType) {
        return getYearMonthStr(new Date(), formatType);
    }

    /**
     * 得到精确到月的指定时间格式化年月串
     * @param date 指定的时间
     * @param formatType 时间格式的类型{@link #DB_STORE_DATE},{@link #EN_HTML_DISPLAY_DATE},{@link #CN_HTML_DISPLAY_DATE}
     * @return 精确到月当前时间格式化年月串
     */
    public static String getYearMonthStr(Date date, int formatType) {
        if (formatType < DB_STORE_DATE || formatType > CN_DISPLAY_DATE) {
            throw new IllegalArgumentException("时间格式化类型不是合法的值。");
        } else {
            String formatStr = null;
            switch (formatType) {
            case DB_STORE_DATE:
                formatStr = "yyyyMM";
                break;
            case HYPHEN_DISPLAY_DATE:
                formatStr = "yyyy-MM";
                break;
            case DOT_DISPLAY_DATE:
                formatStr = "yyyy.MM";
                break;
            case CN_DISPLAY_DATE:
                formatStr = "yyyy'年'MM'月'";
                break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            return sdf.format(date);
        }
    }

    /**
     * 将数据库存储的日期格式串转换为各种显示的格式
     * @param dateStr 最小6位，最大14位的数据库存储格式时间串如:20041212
     * @param formatType 时间格式的类型{@link #DB_STORE_DATE},{@link #EN_HTML_DISPLAY_DATE},{@link #CN_HTML_DISPLAY_DATE}
     * @return 格式化的时间串
     */
    public static String toDisplayStr(String dateStr, int formatType) {
        if (formatType < DB_STORE_DATE || formatType > CN_DISPLAY_DATE) {
            throw new IllegalArgumentException("时间格式化类型不是合法的值。");
        }
        if (dateStr == null || dateStr.length() < 6 || dateStr.length() > 14 ||
            formatType == DB_STORE_DATE) {
            return StringUtils.toVisualString(dateStr);
        } else {
            char[] charArr = null;
            switch (formatType) {
            case HYPHEN_DISPLAY_DATE:
                charArr = new char[] {
                          '-', '-', ' ', ':', ':'};
                break;
            case DOT_DISPLAY_DATE:
                charArr = new char[] {
                          '.', '.', ' ', ':', ':'};
                break;
            case CN_DISPLAY_DATE:
                charArr = new char[] {'年','月',' ', ':', ':'};
                break;
            default:
                charArr = new char[] {
                          '-', '-', ' ', ':', ':'};
            }
            try {
                SimpleDateFormat sdf_1 = null;
                SimpleDateFormat sdf_2 = null;
                switch (dateStr.length()) {
                case 6:
                    sdf_1 = new SimpleDateFormat("yyyyMM");
                    sdf_2 = new SimpleDateFormat("yyyy'" + charArr[0] +
                                                 "'MM");
                    break;
                case 8:
                    sdf_1 = new SimpleDateFormat("yyyyMMdd");
                    sdf_2 = new SimpleDateFormat("yyyy'" + charArr[0] +
                                                 "'MM'" + charArr[1] + "'dd");
                    break;
                case 10:
                    sdf_1 = new SimpleDateFormat("yyyyMMddHH");
                    sdf_2 = new SimpleDateFormat("yyyy'" + charArr[0] +
                                                 "'MM'" + charArr[1] + "'dd'" +
                                                 "+charArr[2]" +
                                                 "'HH");
                    break;
                case 12:
                    sdf_1 = new SimpleDateFormat("yyyyMMddHHmm");
                    sdf_2 = new SimpleDateFormat("yyyy'" + charArr[0] +
                                                 "'MM'" + charArr[1] + "'dd'" +
                                                 charArr[2] +
                                                 "'HH'" + charArr[3] + "'mm");
                    break;
                case 14:
                    sdf_1 = new SimpleDateFormat("yyyyMMddHHmmss");
                    sdf_2 = new SimpleDateFormat(
                            "yyyy'" + charArr[0] + "'MM'" + charArr[1] +
                            "'dd'" + charArr[2] + "'HH'" + charArr[3] +
                            "'mm'" + charArr[4] + "'ss");
                    break;
                default:
                    return dateStr;
                }
                return sdf_2.format(sdf_1.parse(dateStr));
            } catch (ParseException ex) {
                return dateStr;
            }
        }
    }

    /**
     * 将显示格式的时间字符串转换为数据库存储的类型
     * @param dateStr 最小4位，最大19位。显示的时间格式时间串如:2004-12-12
     * @return 数据库存储的时间字符串
     */
    public static String toStoreStr(String dateStr) {
        if (dateStr == null || dateStr.trim().equals("")) {
            return "";
        }
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < dateStr.length(); i++) {
            if (dateStr.charAt(i) >= '0' && dateStr.charAt(i) <= '9') {
                strBuf.append(dateStr.charAt(i));
            }
        }
        return strBuf.toString();
    }


    /**
     * <b>功能描述：</b>
     *    把时间转换成14位的字符串，不足位数补充 0 或者 9
     * @param dateStr String
     * @param key int
     * @return String
     */
    public static String toStoreStr14(String dateStr, boolean bMax) {
        if (dateStr == null || dateStr.trim().equals("")) {
            return "";
        }
        StringBuffer strBuf = new StringBuffer(toStoreStr(dateStr));
        String retStr = strBuf.toString();
        if (bMax) {
            retStr = StringUtils.pad(retStr, 14, '9', true);
        } else {
            retStr = StringUtils.pad(retStr, 14, '0', true);
        }
        return retStr;
    }


    /**
     * 将生日存储的时间格式转化为年龄（周岁，小数点后不计）
     * @param birthdayStr 生日字段  "yyyymmdd"
     * @return 年龄
     */
    public static String birthdayToAge(String birthdayStr) {
        if (birthdayStr == null || birthdayStr.length() < 6) {
            return "";
        } else {
            int birthYear = Integer.parseInt(birthdayStr.substring(0, 4));
            int birthMonth = Integer.parseInt(birthdayStr.substring(4, 6));
            Calendar cal = new GregorianCalendar();
            int currYear = cal.get(Calendar.YEAR);
            int currMonth = cal.get(Calendar.MONTH);
            int age = currYear - birthYear;
            age -= (currMonth < birthMonth) ? 1 : 0;
            return "" + age;
        }
    }

    /**
     * 功能描述:
     *    将年龄转换为生日
     * @param age int
     * @return String
     */
    public static String ageToBirthday(int age) {

        String currDateStr = DateUtils.getYearMonthStr(DB_STORE_DATE);
        int currDateInt = Integer.parseInt(currDateStr);
        int ageDateInt = currDateInt - age * 100;
        return String.valueOf(ageDateInt);
    }


    /**
     * 功能描述:
     *    增加一年
     * @param age int
     * @return String
     */
    public static String nextAgeDate(String birDate) {

        int currDateInt = Integer.parseInt(birDate);
        int ageDateInt = currDateInt + 100;
        return String.valueOf(ageDateInt);
    }





    /**
     *
     * @param dateTimeStr String 格式化的时间串
     * @param formatType 数据格式类型 {@link #DB_STORE_DATE},{@link #EN_HTML_DISPLAY_DATE},{@link #CN_HTML_DISPLAY_DATE}
     * @param detal int  增加或减少的时间
     * @param field int 参见Calendar中关于时间字段属性的定义
     * @return String 返回的
     */
    public static String add(String dateTimeStr, int formatType, int detal,
                             int field) {
        if (dateTimeStr == null || dateTimeStr.length() < 6) {
            return dateTimeStr;
        } else {
            try {
                String formatStr = null;
                switch (formatType) {
                case DB_STORE_DATE:
                    formatStr = "yyyyMMddHHmmss";
                    break;
                case HYPHEN_DISPLAY_DATE:
                    formatStr = "yyyy-MM-dd HH:mm:ss";
                    break;
                case DOT_DISPLAY_DATE:
                    formatStr = "yyyy.MM.dd HH:mm:ss";
                    break;
                case CN_DISPLAY_DATE:
                    formatStr = "yyyy'年'MM'月' HH：mm：ss";
                    break;
                default:
                    formatStr = "yyyyMMddHHmmss";
                    break;
                }

                formatStr = formatStr.substring(0, dateTimeStr.length());
                SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
                Date d = sdf.parse(dateTimeStr);
                GregorianCalendar g = new GregorianCalendar();
                g.setTime(d);
                g.add(field, detal);
                d = g.getTime();
                return sdf.format(d);
            } catch (ParseException ex) {
                ex.printStackTrace();
                return dateTimeStr;
            }
        }
    }

//    /**
//     * @param date Date 时间
//     * @param detal int 增加的时间
//     * @param field int 参见Calendar中关于时间字段属性的定义
//     * @return Date
//     */
//    public static Date add(Date date, int detal, int field)
//    {
//        Calendar g = new GregorianCalendar();
//        g.setTime(date);
//        g.add(field, detal);
//        return g.getTime();
//    }
    /**
     * 日期、时间格式化
     * @param date Date
     * 将要被格式化的日期对象
     * @param outFmt String
     * 返回样式，参照类说明，如：yyyy年MM月dd日
     * @return String
     * 格式化后的日期、时间字符串，data为null时返回null，outFmt非法时返回yyyyMMdd格式
     */
    public static String getDateFormat(Date date, String outFmt) {
        if (null == date) {
            return null;
        }
        if (null == outFmt || "".equals(outFmt.trim())) { //outFmt非法
            outFmt = "yyyyMMdd";
        }

        String retu = null;
        SimpleDateFormat dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat(outFmt);
        } catch (IllegalArgumentException iaex) { //outFmt非法
            dateFormat = new SimpleDateFormat("yyyyMMdd");
        }
        retu = dateFormat.format(date);

        dateFormat = null;

        return retu;
    }

    /**
     * 把日期时间对象格式化为yyyyMMdd样式
     * @param date Date
     * 将要被格式化的日期对象
     * @return String
     * 格式化后的日期、时间字符串，如：20041001
     */
    public static String getDateFormat(Date date) {
        return getDateFormat(date, "yyyyMMdd");
    }

    /**
     * 把系统当前日期时间格式化为指定的样式
     * @param outFmt String
     * 返回样式，参照类说明，如：yyyy年MM月dd日
     * @return String
     * 格式化后的日期、时间字符串，如：2004年10月01日
     */
    public static String getDateFormat(String outFmt) {
        return getDateFormat(new Date(), outFmt);
    }

    /**
     * 把系统当前日期时间格式化为默认样式yyyyMMdd
     * @return String
     * 格式化后的日期、时间字符串，如：20041001
     */
    public static String getDateFormat() {
        return getDateFormat(new Date(), "yyyyMMdd");
    }

    /**
     * 日期、时间格式化
     * @param millis long
     * the number of milliseconds（毫秒） since January 1, 1970, 00:00:00 GMT.
     * @param outFmt String
     * 返回样式，参照类说明，如：yyyy年MM月dd日
     * @return String
     * 格式化后的日期、时间字符串
     */
    public static String getDateFormat(long millis, String outFmt) {
        Date d = new Date(millis);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        String retu = getDateFormat(calendar.getTime(), outFmt);
        calendar = null;
        return retu;
    }

    /**
     * 日期、时间格式化
     * @param datestr String
     * 存在一定格式的日期、时间字符串，如：20041001、200410011503
     * @param inFmt String
     * 对datestr参数格式说明，参照类说明，如：yyyyMMdd、yyyyMMddHHmm
     * @param outFmt String
     * 返回样式，参照类说明，如：yyyy年MM月dd日
     * @throws ParseException 当datestr不能格式化为inFmt格式时抛出此异常
     * @return String
     * 格式化后的日期、时间字符串，如：2004年10月01日、2004年10月01日
     * <BR>输出样式outFmt非法时，使用yyyyMMdd格式输出
     */
    public static String getDateFormat(String datestr, String inFmt,
                                       String outFmt) throws ParseException {
        if (null == datestr || "".equals(datestr.trim())) {
            return datestr;
        }

        if (null == inFmt || "".equals(inFmt.trim())) {
            return datestr;
        }

        if (null == outFmt || "".equals(outFmt.trim())) { //输出样式非法
            outFmt = "yyyyMMdd";
        }

        java.util.Date inDate = getDate(datestr, inFmt);

        if (null == inDate) { //根据inFmt分析datestr时抛出异常
            return datestr;
        }

        String retu = getDateFormat(inDate, outFmt);
        inDate = null;
        return retu;
    }

    /**
     * 把日期时间字符串，按inFmt样式转化为日期对象，然后格式化为默认样式yyyyMMdd
     * @param datestr String
     * 存在一定格式的日期、时间字符串，如：20041001、200410011503
     * @param inFmt String
     * 对datestr参数格式说明，参照类说明，如：yyyyMMdd、yyyyMMddHHmm
     * @throws ParseException 当datestr不能格式化为inFmt格式时抛出此异常
     * @return String
     * 格式化后的日期、时间字符串，如：20041001、20041001
     */
    public static String getDateFormat(String datestr, String inFmt) throws
            ParseException {
        return getDateFormat(datestr, inFmt, "yyyyMMdd");
    }

    /**
     * 根据inFmt的样式，日期时间字符串转化为日期时间对象
     * @param datestr String
     * 日期时间字符串，如：20041001、2004年10月01日 15:03
     * @param format String
     * 对datestr参数格式说明，参照类说明，如yyyyMMdd、yyyy年MM月dd日 HH:mm
     * @throws ParseException 当datestr不能格式化为inFmt格式时抛出此异常
     * @return Date
     * 日期时间对象，格式inFmt非法时，使用yyyyMMdd格式
     */
    public static Date getDate(String datestr, String format){
        if (StringUtils.isEmpty(datestr)) {
            return null;
        }
        if (StringUtils.isEmpty(format)) { //inFmt非法
            format = "yyyyMMdd";
        }

        try {
//依据inFmt格式把日期字符串转化为日期对象
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.setLenient(true);
            return dateFormat.parse(datestr);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 对日期时间对象进行调整，实现如昨天是几号，去年的今天星期几等.
     * <BR>例子：
     * <pre>
     * <blockquote>
     * 计算去年今天星期几
     * Date date = DateUtils.addDate(new Date(),Calendar.YEAR,-1);
     * System.out.println(DateUtils.getDateFormat(date,"E"));
     * 打印60天后是什么日期，并显示为 yyyy-MM-dd 星期
     * Date date = DateUtils.addDate(new Date(),Calendar.DATE,60);
     * System.out.println(DateUtils.getDateFormat(date,"yyyy-MM-dd E"));
     * </blockquote>
     * </pre>
     * @param date Date
     * 需要调整的日期时间对象
     * @param CALENDARFIELD int
     * 对日期时间对象以什么单位进行调整：
     * <pre>
     * <blockquote>
     * 年 Calendar.YEAR
     * 月 Calendar.MONTH
     * 日 Calendar.DATE
     * 时 Calendar.HOUR
     * 分 Calendar.MINUTE
     * 秒 Calendar.SECOND
     * </blockquote>
     * </pre>
     * @param amount int
     * 调整数量，>0表向后调整（明天，明年），<0表向前调整（昨天，去年）
     * @return Date
     * 调整后的日期时间对象
     */
    public static Date addDate(Date date, int CALENDARFIELD, int amount) {
        if (null == date) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(CALENDARFIELD, amount);
        return calendar.getTime();
    }

    /**
     * 对日期时间对象进行调整.
     * @param datestr String
     * 需要调整的日期时间字符串，它的格式为yyyyMMdd
     * @param CALENDARFIELD int
     * 对日期时间对象以什么单位进行调整
     * @param amount int
     * 调整数量
     * @throws ParseException 当datestr不能格式化为yyyyMMdd格式时抛出此异常
     * @return Date
     * 调整后的日期时间对象
     * @see #addDate(java.util.Date, int, int)
     */
    public static Date addDate(String datestr, int CALENDARFIELD, int amount) throws
            ParseException {
        return addDate(getDate(datestr, "yyyyMMdd"), CALENDARFIELD, amount);
    }

    /**
     * 根据出生日期，计算出在某一个日期的年龄
     * @param birthday Date
     * 出生日期时间对象
     * @param date2 Date
     * 计算日期对象
     * @return int
     * 返回date2那一天出生日期为birthday的年龄，如果birthday大于date2则返回-1
     */
    public static int getAge(Date birthday, Date date2) {
        if (null == birthday || null == date2) {
            return -1;
        }

        if (birthday.after(date2)) { //birthday大于date2
            return -1;
        }

        int ibdYear = StringUtils.getInt(getDateFormat(birthday, "yyyy"), -1);
        int idate2Year = StringUtils.getInt(getDateFormat(date2, "yyyy"), -1);

        if (ibdYear < 0 || idate2Year < 0) {
            return -1;
        }
        if (ibdYear > idate2Year) {
            return -1;
        }

        return idate2Year - ibdYear + 1;
    }

    /**
     * 根据出生日期，计算出当前的年龄
     * @param birthday Date
     * 出生日期时间对象
     * @return int
     * 返回出生日期为birthday的年龄，如果birthday大于当前系统日期则返回-1
     */
    public static int getAge(Date birthday) {
        return getAge(birthday, new Date());
    }

    /**
     * 根据出生日期，计算出当前的年龄
     * @param birthdaystr String
     * 出生日期时间字符串，其格式一定为yyyyMMdd
     * @throws ParseException 当datestr不能格式化为yyyyMMdd格式时抛出此异常
     * @return int
     * 返回出生日期为birthday的年龄，如果birthday大于当前系统日期则返回-1
     */
    public static int getAge(String birthdaystr) throws ParseException {
        return getAge(getDate(birthdaystr, "yyyyMMdd"));
    }

    /**
     * 时间转换
     * @param timestamp
     * @return
     */
    public static String converTime(long timestamp) {
		long currentSeconds = System.currentTimeMillis() / 1000;
		// 与现在时间相差秒数
		long timeGap = currentSeconds - timestamp;
		String timeStr = null;
		if (timeGap > DAY_SECOND) {
			// 1天以上
			timeStr = timeGap / DAY_SECOND + "天前";
		} else if (timeGap > HOUR_SECOND) {
			// 1小时-24小时
			timeStr = timeGap / HOUR_SECOND + "小时前";
		} else if (timeGap > 60) {
			// 1分钟-59分钟
			timeStr = timeGap / 60 + "分钟前";
		} else {
			// 1秒钟-59秒钟
			timeStr = "刚刚";
		}
		return timeStr;
	}
}
