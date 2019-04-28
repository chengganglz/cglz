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
public class ValueTypeEnum extends StringEnum
{
    public static final String FIXED_VALUE = "Fixed";//alias=固定值
    public static final String LINK_VALUE = "Link";//alias=关联属性

    public static final ValueTypeEnum Fixed = new ValueTypeEnum("Fixed", FIXED_VALUE);
    public static final ValueTypeEnum Link = new ValueTypeEnum("Link", LINK_VALUE);

    /**
     * construct function
     * @param String valueTypeEnum
     */
    private ValueTypeEnum(String name, String valueTypeEnum)
    {
        super(name, valueTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ValueTypeEnum getEnum(String valueTypeEnum)
    {
        return (ValueTypeEnum)getEnum(ValueTypeEnum.class, valueTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ValueTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ValueTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ValueTypeEnum.class);
    }
}