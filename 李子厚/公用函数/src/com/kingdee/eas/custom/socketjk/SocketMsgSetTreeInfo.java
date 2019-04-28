package com.kingdee.eas.custom.socketjk;

import java.io.Serializable;

public class SocketMsgSetTreeInfo extends AbstractSocketMsgSetTreeInfo implements Serializable 
{
    public SocketMsgSetTreeInfo()
    {
        super();
    }
    protected SocketMsgSetTreeInfo(String pkField)
    {
        super(pkField);
    }
}