package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SocketMsgSetTreeFactory
{
    private SocketMsgSetTreeFactory()
    {
    }
    public static com.kingdee.eas.custom.socketjk.ISocketMsgSetTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketMsgSetTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("370B4941") ,com.kingdee.eas.custom.socketjk.ISocketMsgSetTree.class);
    }
    
    public static com.kingdee.eas.custom.socketjk.ISocketMsgSetTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketMsgSetTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("370B4941") ,com.kingdee.eas.custom.socketjk.ISocketMsgSetTree.class, objectCtx);
    }
    public static com.kingdee.eas.custom.socketjk.ISocketMsgSetTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketMsgSetTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("370B4941"));
    }
    public static com.kingdee.eas.custom.socketjk.ISocketMsgSetTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketMsgSetTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("370B4941"));
    }
}