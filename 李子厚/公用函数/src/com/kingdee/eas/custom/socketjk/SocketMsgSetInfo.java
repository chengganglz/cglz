package com.kingdee.eas.custom.socketjk;

import java.io.Serializable;

public class SocketMsgSetInfo extends AbstractSocketMsgSetInfo implements Serializable 
{
    public SocketMsgSetInfo()
    {
        super();
    }
    protected SocketMsgSetInfo(String pkField)
    {
        super(pkField);
    }
}