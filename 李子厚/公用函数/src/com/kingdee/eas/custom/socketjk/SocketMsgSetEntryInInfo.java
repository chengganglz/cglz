package com.kingdee.eas.custom.socketjk;

import java.io.Serializable;

public class SocketMsgSetEntryInInfo extends AbstractSocketMsgSetEntryInInfo implements Serializable 
{
    public SocketMsgSetEntryInInfo()
    {
        super();
    }
    protected SocketMsgSetEntryInInfo(String pkField)
    {
        super(pkField);
    }
}