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
public class objTypeEnum extends StringEnum
{
    public static final String CUSTOMER_VALUE = "Customer";//alias=�ͻ�
    public static final String SUPPLIER_VALUE = "Supplier";//alias=��Ӧ��

    public static final objTypeEnum Customer = new objTypeEnum("Customer", CUSTOMER_VALUE);
    public static final objTypeEnum Supplier = new objTypeEnum("Supplier", SUPPLIER_VALUE);

    /**
     * construct function
     * @param String objTypeEnum
     */
    private objTypeEnum(String name, String objTypeEnum)
    {
        super(name, objTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static objTypeEnum getEnum(String objTypeEnum)
    {
        return (objTypeEnum)getEnum(objTypeEnum.class, objTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(objTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(objTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(objTypeEnum.class);
    }
}