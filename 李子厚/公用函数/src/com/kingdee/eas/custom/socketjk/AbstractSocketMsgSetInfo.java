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
     * Object: �������� 's ��� property 
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
     * Object:��������'s ҵ��Ԫproperty 
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
     * Object:��������'s ҵ��Ԫ����property 
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
     * Object:��������'s ��������property 
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
     * Object: �������� 's ʹ��ͨ�� property 
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
     * Object:��������'s ִ�нű�property 
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
     * Object:��������'s ����ͷ����property 
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
     * Object:��������'s ���ݳ���property 
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
     * Object:��������'s ����������property 
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
     * Object:��������'s ������property 
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
     * Object:��������'s ���ĺ�property 
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
     * Object:��������'s ���ĺſ�ʼλ��property 
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
     * Object:��������'s ���ĺŽ�ֹλ��property 
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
     * Object:��������'s �Ƿ��¼��־property 
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