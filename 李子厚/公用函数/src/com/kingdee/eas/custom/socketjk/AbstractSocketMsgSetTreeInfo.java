package com.kingdee.eas.custom.socketjk;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSocketMsgSetTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractSocketMsgSetTreeInfo()
    {
        this("id");
    }
    protected AbstractSocketMsgSetTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 报文配置组别 's 父节点 property 
     */
    public com.kingdee.eas.custom.socketjk.SocketMsgSetTreeInfo getParent()
    {
        return (com.kingdee.eas.custom.socketjk.SocketMsgSetTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.socketjk.SocketMsgSetTreeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("370B4941");
    }
}