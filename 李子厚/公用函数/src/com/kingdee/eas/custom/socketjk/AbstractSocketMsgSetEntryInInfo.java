package com.kingdee.eas.custom.socketjk;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSocketMsgSetEntryInInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSocketMsgSetEntryInInfo()
    {
        this("id");
    }
    protected AbstractSocketMsgSetEntryInInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:  ‰»Î≤Œ ˝ 's null property 
     */
    public com.kingdee.eas.custom.socketjk.SocketMsgSetInfo getParent()
    {
        return (com.kingdee.eas.custom.socketjk.SocketMsgSetInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.socketjk.SocketMsgSetInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6AEBE0B4");
    }
}