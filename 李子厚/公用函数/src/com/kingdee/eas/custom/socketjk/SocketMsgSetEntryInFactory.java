package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SocketMsgSetEntryInFactory
{
    private SocketMsgSetEntryInFactory()
    {
    }
    public static com.kingdee.eas.custom.socketjk.ISocketMsgSetEntryIn getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketMsgSetEntryIn)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6AEBE0B4") ,com.kingdee.eas.custom.socketjk.ISocketMsgSetEntryIn.class);
    }
    
    public static com.kingdee.eas.custom.socketjk.ISocketMsgSetEntryIn getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketMsgSetEntryIn)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6AEBE0B4") ,com.kingdee.eas.custom.socketjk.ISocketMsgSetEntryIn.class, objectCtx);
    }
    public static com.kingdee.eas.custom.socketjk.ISocketMsgSetEntryIn getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketMsgSetEntryIn)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6AEBE0B4"));
    }
    public static com.kingdee.eas.custom.socketjk.ISocketMsgSetEntryIn getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketMsgSetEntryIn)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6AEBE0B4"));
    }
}