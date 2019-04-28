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
public class UrlType extends StringEnum
{
    public static final String SERVER_VALUE = "Server";//alias=服务端
    public static final String CLIENT_VALUE = "Client";//alias=客户端

    public static final UrlType Server = new UrlType("Server", SERVER_VALUE);
    public static final UrlType Client = new UrlType("Client", CLIENT_VALUE);

    /**
     * construct function
     * @param String urlType
     */
    private UrlType(String name, String urlType)
    {
        super(name, urlType);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static UrlType getEnum(String urlType)
    {
        return (UrlType)getEnum(UrlType.class, urlType);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(UrlType.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(UrlType.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(UrlType.class);
    }
}