package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SocketServerStatusFactory
{
    private SocketServerStatusFactory()
    {
    }
    public static com.kingdee.eas.custom.socketjk.ISocketServerStatus getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketServerStatus)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("04227577") ,com.kingdee.eas.custom.socketjk.ISocketServerStatus.class);
    }
    
    public static com.kingdee.eas.custom.socketjk.ISocketServerStatus getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketServerStatus)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("04227577") ,com.kingdee.eas.custom.socketjk.ISocketServerStatus.class, objectCtx);
    }
    public static com.kingdee.eas.custom.socketjk.ISocketServerStatus getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketServerStatus)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("04227577"));
    }
    public static com.kingdee.eas.custom.socketjk.ISocketServerStatus getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketServerStatus)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("04227577"));
    }
}