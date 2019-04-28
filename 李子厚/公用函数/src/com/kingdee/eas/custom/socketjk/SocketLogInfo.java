package com.kingdee.eas.custom.socketjk;

import java.io.Serializable;

public class SocketLogInfo extends AbstractSocketLogInfo implements Serializable 
{
    public SocketLogInfo()
    {
        super();
    }
    protected SocketLogInfo(String pkField)
    {
        super(pkField);
    }
}