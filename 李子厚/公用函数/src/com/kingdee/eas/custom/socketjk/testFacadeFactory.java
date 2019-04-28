package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class testFacadeFactory
{
    private testFacadeFactory()
    {
    }
    public static com.kingdee.eas.custom.socketjk.ItestFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ItestFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("49C3F7FB") ,com.kingdee.eas.custom.socketjk.ItestFacade.class);
    }
    
    public static com.kingdee.eas.custom.socketjk.ItestFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ItestFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("49C3F7FB") ,com.kingdee.eas.custom.socketjk.ItestFacade.class, objectCtx);
    }
    public static com.kingdee.eas.custom.socketjk.ItestFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ItestFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("49C3F7FB"));
    }
    public static com.kingdee.eas.custom.socketjk.ItestFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.ItestFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("49C3F7FB"));
    }
}