package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SocketMsgSetFactory
{
    private SocketMsgSetFactory()
    {
    }
    public static com.kingdee.eas.custom.socketjk.ISocketMsgSet getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketMsgSet)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9D7E9B03") ,com.kingdee.eas.custom.socketjk.ISocketMsgSet.class);
    }
    
    public static com.kingdee.eas.custom.socketjk.ISocketMsgSet getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketMsgSet)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9D7E9B03") ,com.kingdee.eas.custom.socketjk.ISocketMsgSet.class, objectCtx);
    }
    public static com.kingdee.eas.custom.socketjk.ISocketMsgSet getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketMsgSet)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9D7E9B03"));
    }
    public static com.kingdee.eas.custom.socketjk.ISocketMsgSet getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketMsgSet)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9D7E9B03"));
    }
}