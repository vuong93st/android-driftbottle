package com.douya.util;

import android.text.method.ReplacementTransformationMethod;
/**
 * 大写转换
 *
 */
public class UpperCaseTransformationMethod
extends ReplacementTransformationMethod {
    private static char[] ORIGINAL = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g'
    	, 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q'
    	, 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static char[] REPLACEMENT = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G'
    	, 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q'
    	, 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     *替换字符 \n 和 \r.
     */
    protected char[] getOriginal() {
        return ORIGINAL;
    }

    /**
     * 字符 \n 替换为空格;
     * 字符 \r 替换为 FEFF (zero width space).
     */
    protected char[] getReplacement() {
        return REPLACEMENT;
    }

    public static UpperCaseTransformationMethod getInstance() {
        if (sInstance != null)
            return sInstance;

        sInstance = new UpperCaseTransformationMethod();
        return sInstance;
    }

    private static UpperCaseTransformationMethod sInstance;
}
