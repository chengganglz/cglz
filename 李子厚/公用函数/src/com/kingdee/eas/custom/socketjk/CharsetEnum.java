/**
 * output package name
 */
package com.kingdee.eas.custom.socketjk;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class CharsetEnum extends StringEnum
{
    public static final String UTF_8_VALUE = "UTF-8";//alias=UTF_8
    public static final String UTF_16_VALUE = "UTF-16";//alias=UTF_16
    public static final String UTF_16BE_VALUE = "UTF-16BE";//alias=UTF_16BE
    public static final String UTF_16LE_VALUE = "UTF-16LE";//alias=UTF_16LE
    public static final String ISO_8859_1_VALUE = "ISO-8859-1";//alias=ISO_8859_1
    public static final String US_ASCII_VALUE = "US-ASCII";//alias=US_ASCII
    public static final String GBK_VALUE = "GBK";//alias=GBK
    public static final String ASCII_VALUE = "ASCII";//alias=ASCII

    public static final CharsetEnum UTF_8 = new CharsetEnum("UTF_8", UTF_8_VALUE);
    public static final CharsetEnum UTF_16 = new CharsetEnum("UTF_16", UTF_16_VALUE);
    public static final CharsetEnum UTF_16BE = new CharsetEnum("UTF_16BE", UTF_16BE_VALUE);
    public static final CharsetEnum UTF_16LE = new CharsetEnum("UTF_16LE", UTF_16LE_VALUE);
    public static final CharsetEnum ISO_8859_1 = new CharsetEnum("ISO_8859_1", ISO_8859_1_VALUE);
    public static final CharsetEnum US_ASCII = new CharsetEnum("US_ASCII", US_ASCII_VALUE);
    public static final CharsetEnum GBK = new CharsetEnum("GBK", GBK_VALUE);
    public static final CharsetEnum ASCII = new CharsetEnum("ASCII", ASCII_VALUE);

    /**
     * construct function
     * @param String charsetEnum
     */
    private CharsetEnum(String name, String charsetEnum)
    {
        super(name, charsetEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CharsetEnum getEnum(String charsetEnum)
    {
        return (CharsetEnum)getEnum(CharsetEnum.class, charsetEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CharsetEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CharsetEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CharsetEnum.class);
    }
}