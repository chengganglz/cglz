package com.kingdee.eas.custom.socketjk;

import java.io.Serializable;

public class UrlConfigInfo extends AbstractUrlConfigInfo implements Serializable 
{
    public UrlConfigInfo()
    {
        super();
    }
    protected UrlConfigInfo(String pkField)
    {
        super(pkField);
    }
}