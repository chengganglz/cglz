package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SocketLogFactory
{
    private SocketLogFactory()
    {
    }
    public static com.kingdee.eas.custom.socketjk.ISocketLog getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketLog)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E2AB04A2") ,com.kingdee.eas.custom.socketjk.ISocketLog.class);
    }
    
    public static com.kingdee.eas.custom.socketjk.ISocketLog getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketLog)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E2AB04A2") ,com.kingdee.eas.custom.socketjk.ISocketLog.class, objectCtx);
    }
    public static com.kingdee.eas.custom.socketjk.ISocketLog getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketLog)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E2AB04A2"));
    }
    public static com.kingdee.eas.custom.socketjk.ISocketLog getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketLog)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E2AB04A2"));
    }
}