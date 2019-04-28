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
     * Object:服务状态's 是否生成凭证property 
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
     * Object:服务状态's 公司IDproperty 
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
     * Object:服务状态's 公司名称property 
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
     * Object:服务状态's 服务类型property 
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
     * Object:服务状态's 服务名称property 
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
     * Object:服务状态's 超时时间property 
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
     * Object:服务状态's 是否启用心跳电文property 
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
     * Object:服务状态's 心跳电文间隔时间property 
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
     * Object:服务状态's 是否输出日志property 
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
     * Object:服务状态's 本地IPproperty 
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
     * Object:服务状态's 本地端口property 
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
     * Object:服务状态's 服务状态property 
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
     * Object:服务状态's 远程IPproperty 
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
     * Object:服务状态's 通道状态property 
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
     * Object:服务状态's 远程端口property 
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