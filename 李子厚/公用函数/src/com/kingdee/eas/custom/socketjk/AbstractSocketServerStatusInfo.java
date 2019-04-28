package com.kingdee.eas.custom.socketjk;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSocketServerStatusInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractSocketServerStatusInfo()
    {
        this("id");
    }
    protected AbstractSocketServerStatusInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:����״̬'s �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:����״̬'s ��˾IDproperty 
     */
    public String getCompanyID()
    {
        return getString("CompanyID");
    }
    public void setCompanyID(String item)
    {
        setString("CompanyID", item);
    }
    /**
     * Object:����״̬'s ��˾����property 
     */
    public String getCompanyName()
    {
        return getString("CompanyName");
    }
    public void setCompanyName(String item)
    {
        setString("CompanyName", item);
    }
    /**
     * Object:����״̬'s ��������property 
     */
    public String getServerType()
    {
        return getString("ServerType");
    }
    public void setServerType(String item)
    {
        setString("ServerType", item);
    }
    /**
     * Object:����״̬'s ��������property 
     */
    public String getServerName()
    {
        return getString("ServerName");
    }
    public void setServerName(String item)
    {
        setString("ServerName", item);
    }
    /**
     * Object:����״̬'s ��ʱʱ��property 
     */
    public int getTimeOut()
    {
        return getInt("TimeOut");
    }
    public void setTimeOut(int item)
    {
        setInt("TimeOut", item);
    }
    /**
     * Object:����״̬'s �Ƿ�������������property 
     */
    public String getIsUseHeartbeatMsg()
    {
        return getString("IsUseHeartbeatMsg");
    }
    public void setIsUseHeartbeatMsg(String item)
    {
        setString("IsUseHeartbeatMsg", item);
    }
    /**
     * Object:����״̬'s �������ļ��ʱ��property 
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
     * Object:����״̬'s �Ƿ������־property 
     */
    public String getIsWirte()
    {
        return getString("IsWirte");
    }
    public void setIsWirte(String item)
    {
        setString("IsWirte", item);
    }
    /**
     * Object:����״̬'s ����IPproperty 
     */
    public String getLocalIP()
    {
        return getString("LocalIP");
    }
    public void setLocalIP(String item)
    {
        setString("LocalIP", item);
    }
    /**
     * Object:����״̬'s ���ض˿�property 
     */
    public int getLocalPort()
    {
        return getInt("LocalPort");
    }
    public void setLocalPort(int item)
    {
        setInt("LocalPort", item);
    }
    /**
     * Object:����״̬'s ����״̬property 
     */
    public String getServerStatus()
    {
        return getString("ServerStatus");
    }
    public void setServerStatus(String item)
    {
        setString("ServerStatus", item);
    }
    /**
     * Object:����״̬'s Զ��IPproperty 
     */
    public String getRemoteIP()
    {
        return getString("RemoteIP");
    }
    public void setRemoteIP(String item)
    {
        setString("RemoteIP", item);
    }
    /**
     * Object:����״̬'s ͨ��״̬property 
     */
    public String getChannelStatus()
    {
        return getString("ChannelStatus");
    }
    public void setChannelStatus(String item)
    {
        setString("ChannelStatus", item);
    }
    /**
     * Object:����״̬'s Զ�̶˿�property 
     */
    public int getRemotePort()
    {
        return getInt("RemotePort");
    }
    public void setRemotePort(int item)
    {
        setInt("RemotePort", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("04227577");
    }
}