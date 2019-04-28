package com.kingdee.eas.custom.socketjk;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSocketMsgSetInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractSocketMsgSetInfo()
    {
        this("id");
    }
    protected AbstractSocketMsgSetInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 报文配置 's 组别 property 
     */
    public com.kingdee.eas.custom.socketjk.SocketMsgSetTreeInfo getTreeid()
    {
        return (com.kingdee.eas.custom.socketjk.SocketMsgSetTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.custom.socketjk.SocketMsgSetTreeInfo item)
    {
        put("treeid", item);
    }
    /**
     * Object:报文配置's 业务单元property 
     */
    public String getEASBizType()
    {
        return getString("EASBizType");
    }
    public void setEASBizType(String item)
    {
        setString("EASBizType", item);
    }
    /**
     * Object:报文配置's 业务单元名称property 
     */
    public String getEASBizTypeName()
    {
        return getString("EASBizTypeName");
    }
    public void setEASBizTypeName(String item)
    {
        setString("EASBizTypeName", item);
    }
    /**
     * Object:报文配置's 报文类型property 
     */
    public com.kingdee.eas.custom.socketjk.SocketMsgType getMsgType()
    {
        return com.kingdee.eas.custom.socketjk.SocketMsgType.getEnum(getString("MsgType"));
    }
    public void setMsgType(com.kingdee.eas.custom.socketjk.SocketMsgType item)
    {
		if (item != null) {
        setString("MsgType", item.getValue());
		}
    }
    /**
     * Object: 报文配置 's 使用通道 property 
     */
    public com.kingdee.eas.custom.socketjk.UrlConfigInfo getChannel()
    {
        return (com.kingdee.eas.custom.socketjk.UrlConfigInfo)get("Channel");
    }
    public void setChannel(com.kingdee.eas.custom.socketjk.UrlConfigInfo item)
    {
        put("Channel", item);
    }
    /**
     * Object:报文配置's 执行脚本property 
     */
    public String getScript()
    {
        return getString("Script");
    }
    public void setScript(String item)
    {
        setString("Script", item);
    }
    /**
     * Object:报文配置's 电文头长度property 
     */
    public int getHeadLength()
    {
        return getInt("headLength");
    }
    public void setHeadLength(int item)
    {
        setInt("headLength", item);
    }
    /**
     * Object:报文配置's 数据长度property 
     */
    public int getDataLength()
    {
        return getInt("dataLength");
    }
    public void setDataLength(int item)
    {
        setInt("dataLength", item);
    }
    /**
     * Object:报文配置's 结束符长度property 
     */
    public int getEndCharLength()
    {
        return getInt("endCharLength");
    }
    public void setEndCharLength(int item)
    {
        setInt("endCharLength", item);
    }
    /**
     * Object:报文配置's 结束符property 
     */
    public String getEndChar()
    {
        return getString("endChar");
    }
    public void setEndChar(String item)
    {
        setString("endChar", item);
    }
    /**
     * Object:报文配置's 电文号property 
     */
    public String getMsgNo()
    {
        return getString("msgNo");
    }
    public void setMsgNo(String item)
    {
        setString("msgNo", item);
    }
    /**
     * Object:报文配置's 电文号开始位置property 
     */
    public int getMsgNoSta()
    {
        return getInt("msgNoSta");
    }
    public void setMsgNoSta(int item)
    {
        setInt("msgNoSta", item);
    }
    /**
     * Object:报文配置's 电文号截止位置property 
     */
    public int getMsgNoEnd()
    {
        return getInt("msgNoEnd");
    }
    public void setMsgNoEnd(int item)
    {
        setInt("msgNoEnd", item);
    }
    /**
     * Object:报文配置's 是否记录日志property 
     */
    public boolean isIsLog()
    {
        return getBoolean("IsLog");
    }
    public void setIsLog(boolean item)
    {
        setBoolean("IsLog", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9D7E9B03");
    }
}