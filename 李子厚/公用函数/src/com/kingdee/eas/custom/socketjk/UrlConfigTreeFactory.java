package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class UrlConfigTreeFactory
{
    private UrlConfigTreeFactory()
    {
    }
    public static com.kingdee.eas.custom.socketjk.IUrlConfigTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.IUrlConfigTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8BD19E20") ,com.kingdee.eas.custom.socketjk.IUrlConfigTree.class);
    }
    
    public static com.kingdee.eas.custom.socketjk.IUrlConfigTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.IUrlConfigTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8BD19E20") ,com.kingdee.eas.custom.socketjk.IUrlConfigTree.class, objectCtx);
    }
    public static com.kingdee.eas.custom.socketjk.IUrlConfigTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.IUrlConfigTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8BD19E20"));
    }
    public static com.kingdee.eas.custom.socketjk.IUrlConfigTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.socketjk.IUrlConfigTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8BD19E20"));
    }
}