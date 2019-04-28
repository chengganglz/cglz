package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SocketFacadeFactory
{
    private SocketFacadeFactory()
    {
    }
    public static com.kingdee.eas.custom.socketjk.ISocketFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("908D6A7C") ,com.kingdee.eas.custom.socketjk.ISocketFacade.class);
    }
    
    public static com.kingdee.eas.custom.socketjk.ISocketFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("908D6A7C") ,com.kingdee.eas.custom.socketjk.ISocketFacade.class, objectCtx);
    }
    public static com.kingdee.eas.custom.socketjk.ISocketFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("908D6A7C"));
    }
    public static com.kingdee.eas.custom.socketjk.ISocketFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ISocketFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("908D6A7C"));
    }
}