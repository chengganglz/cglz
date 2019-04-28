package com.kingdee.eas.custom.socketjk;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSocketLogInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractSocketLogInfo()
    {
        this("id");
    }
    protected AbstractSocketLogInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������־'s �Ƿ�����ƾ֤property 
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
     * Object:������־'s ��������property 
     */
    public String getSocketData()
    {
        return getString("socketData");
    }
    public void setSocketData(String item)
    {
        setString("socketData", item);
    }
    /**
     * Object:������־'s ��Ϣproperty 
     */
    public String getErrMsg()
    {
        return getString("errMsg");
    }
    public void setErrMsg(String item)
    {
        setString("errMsg", item);
    }
    /**
     * Object: ������־ 's ������Դ property 
     */
    public com.kingdee.eas.custom.socketjk.UrlConfigInfo getDataType()
    {
        return (com.kingdee.eas.custom.socketjk.UrlConfigInfo)get("dataType");
    }
    public void setDataType(com.kingdee.eas.custom.socketjk.UrlConfigInfo item)
    {
        put("dataType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E2AB04A2");
    }
}