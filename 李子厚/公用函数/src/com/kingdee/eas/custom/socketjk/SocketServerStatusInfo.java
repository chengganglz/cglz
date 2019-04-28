package com.kingdee.eas.custom.socketjk;

import java.io.Serializable;

public class SocketServerStatusInfo extends AbstractSocketServerStatusInfo implements Serializable 
{
    public SocketServerStatusInfo()
    {
        super();
    }
    protected SocketServerStatusInfo(String pkField)
    {
        super(pkField);
    }
}