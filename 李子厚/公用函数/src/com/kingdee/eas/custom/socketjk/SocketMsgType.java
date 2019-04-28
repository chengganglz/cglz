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
public class SocketMsgType extends StringEnum
{
    public static final String ACK_VALUE = "ACK";//alias=应答电文
    public static final String ORDINARY_VALUE = "Ordinary";//alias=普通电文
    public static final String HEARTBEAT_VALUE = "HeartBeat";//alias=心跳电文

    public static final SocketMsgType ACK = new SocketMsgType("ACK", ACK_VALUE);
    public static final SocketMsgType Ordinary = new SocketMsgType("Ordinary", ORDINARY_VALUE);
    public static final SocketMsgType HeartBeat = new SocketMsgType("HeartBeat", HEARTBEAT_VALUE);

    /**
     * construct function
     * @param String socketMsgType
     */
    private SocketMsgType(String name, String socketMsgType)
    {
        super(name, socketMsgType);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SocketMsgType getEnum(String socketMsgType)
    {
        return (SocketMsgType)getEnum(SocketMsgType.class, socketMsgType);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SocketMsgType.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SocketMsgType.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SocketMsgType.class);
    }
}