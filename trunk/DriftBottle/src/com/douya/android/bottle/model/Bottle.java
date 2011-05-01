package com.douya.android.bottle.model;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Convenience definitions for BottleProvider
 */
public final class Bottle {
    public static final String AUTHORITY = "com.douya.bottle";

    private Bottle() {}
    
    /**
     * 漂流瓶表
     */
    public static final class Bottles implements BaseColumns {
        private Bottles() {}

        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/bottles");

        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of notes.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.douya.bottle.bottle";

        /**
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single note.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.douya.bottle.bottle";

        /**
         * 今日天气
         * <P>Type: TEXT</P>
         */
        public static final String TODAY = "today";

        /**
         * 明日天气
         * <P>Type: TEXT</P>
         */
        public static final String TOMORROW = "tomorrow";

        /**
         * 后天天气
         * <P>Type: TEXT</P>
         */
        public static final String AFTERDAY = "afterday";

        /**
         * 当前天气
         * <P>Type: TEXT</P>
         */
        public static final String CURRENT = "current";
        /**
         * 当前日期
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String CURRENT_DATE = "current_date";
        
    }
}
