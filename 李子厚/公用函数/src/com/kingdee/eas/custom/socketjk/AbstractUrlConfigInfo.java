package com.kingdee.eas.custom.socketjk;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractUrlConfigInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractUrlConfigInfo()
    {
        this("id");
    }
    protected AbstractUrlConfigInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����������� 's ��� property 
     */
    public com.kingdee.eas.custom.socketjk.UrlConfigTreeInfo getTreeid()
    {
        return (com.kingdee.eas.custom.socketjk.UrlConfigTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.custom.socketjk.UrlConfigTreeInfo item)
    {
        put("treeid", item);
    }
    /**
     * Object: ����������� 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getFICompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("FICompany");
    }
    public void setFICompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("FICompany", item);
    }
    /**
     * Object:�����������'s ip��ַproperty 
     */
    public String getIp()
    {
        return getString("ip");
    }
    public void setIp(String item)
    {
        setString("ip", item);
    }
    /**
     * Object:�����������'s �˿�property 
     */
    public int getPort()
    {
        return getInt("port");
    }
    public void setPort(int item)
    {
        setInt("port", item);
    }
    /**
     * Object:�����������'s ����property 
     */
    public com.kingdee.eas.custom.socketjk.UrlType getUrlType()
    {
        return com.kingdee.eas.custom.socketjk.UrlType.getEnum(getString("urlType"));
    }
    public void setUrlType(com.kingdee.eas.custom.socketjk.UrlType item)
    {
		if (item != null) {
        setString("urlType", item.getValue());
		}
    }
    /**
     * Object:�����������'s �Ƿ�������������property 
     */
    public boolean isIsUseHeartbeatMsg()
    {
        return getBoolean("isUseHeartbeatMsg");
    }
    public void setIsUseHeartbeatMsg(boolean item)
    {
        setBoolean("isUseHeartbeatMsg", item);
    }
    /**
     * Object:�����������'s �������ķ��ͼ����ms��property 
     */
    public int getHeartbeatMsgTime()
    {
        return getInt("HeartbeatMsgTime");
    }
    public void setHeartbeatMsgTime(int item)
    {
        setInt("HeartbeatMsgTime", item);
    }
    /**
     * Object:�����������'s �Ƿ����ʵʱ��־property 
     */
    public boolean isIsWrite()
    {
        return getBoolean("isWrite");
    }
    public void setIsWrite(boolean item)
    {
        setBoolean("isWrite", item);
    }
    /**
     * Object:�����������'s ��ʱʱ�䣨ms��property 
     */
    public int getTimeOut()
    {
        return getInt("timeOut");
    }
    public void setTimeOut(int item)
    {
        setInt("timeOut", item);
    }
    /**
     * Object:�����������'s ����property 
     */
    public boolean isIsUse()
    {
        return getBoolean("IsUse");
    }
    public void setIsUse(boolean item)
    {
        setBoolean("IsUse", item);
    }
    /**
     * Object:�����������'s �ط�����property 
     */
    public int getReTranTimes()
    {
        return getInt("ReTranTimes");
    }
    public void setReTranTimes(int item)
    {
        setInt("ReTranTimes", item);
    }
    /**
     * Object:�����������'s �����ʽproperty 
     */
    public com.kingdee.eas.custom.socketjk.CharsetEnum getCharset()
    {
        return com.kingdee.eas.custom.socketjk.CharsetEnum.getEnum(getString("Charset"));
    }
    public void setCharset(com.kingdee.eas.custom.socketjk.CharsetEnum item)
    {
		if (item != null) {
        setString("Charset", item.getValue());
		}
    }
    /**
     * Object:�����������'s ����ͷ����property 
     */
    public int getHeadLength()
    {
        return getInt("headLength");
    }
    public void setHeadLength(int item)
    {
        setInt("headLength", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DB383762");
    }
}