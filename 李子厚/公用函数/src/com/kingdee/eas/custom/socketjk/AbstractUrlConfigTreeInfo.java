package com.kingdee.eas.custom.socketjk;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractUrlConfigTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractUrlConfigTreeInfo()
    {
        this("id");
    }
    protected AbstractUrlConfigTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 传输参数配置组别 's 父节点 property 
     */
    public com.kingdee.eas.custom.socketjk.UrlConfigTreeInfo getParent()
    {
        return (com.kingdee.eas.custom.socketjk.UrlConfigTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.socketjk.UrlConfigTreeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8BD19E20");
    }
}