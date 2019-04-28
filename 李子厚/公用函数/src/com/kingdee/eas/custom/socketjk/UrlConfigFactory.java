package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class UrlConfigFactory
{
    private UrlConfigFactory()
    {
    }
    public static com.kingdee.eas.custom.socketjk.IUrlConfig getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.IUrlConfig)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DB383762") ,com.kingdee.eas.custom.socketjk.IUrlConfig.class);
    }
    
    public static com.kingdee.eas.custom.socketjk.IUrlConfig getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.IUrlConfig)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DB383762") ,com.kingdee.eas.custom.socketjk.IUrlConfig.class, objectCtx);
    }
    public static com.kingdee.eas.custom.socketjk.IUrlConfig getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.IUrlConfig)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DB383762"));
    }
    public static com.kingdee.eas.custom.socketjk.IUrlConfig getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.IUrlConfig)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DB383762"));
    }
}