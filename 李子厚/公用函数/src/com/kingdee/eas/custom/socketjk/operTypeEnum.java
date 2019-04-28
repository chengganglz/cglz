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
public class operTypeEnum extends StringEnum
{
    public static final String SEND_VALUE = "Send";//alias=∑¢ÀÕ
    public static final String RECEIVE_VALUE = "Receive";//alias=Ω” ’

    public static final operTypeEnum Send = new operTypeEnum("Send", SEND_VALUE);
    public static final operTypeEnum Receive = new operTypeEnum("Receive", RECEIVE_VALUE);

    /**
     * construct function
     * @param String operTypeEnum
     */
    private operTypeEnum(String name, String operTypeEnum)
    {
        super(name, operTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static operTypeEnum getEnum(String operTypeEnum)
    {
        return (operTypeEnum)getEnum(operTypeEnum.class, operTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(operTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(operTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(operTypeEnum.class);
    }
}