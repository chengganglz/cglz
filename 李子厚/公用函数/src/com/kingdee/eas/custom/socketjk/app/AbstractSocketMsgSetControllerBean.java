package com.kingdee.eas.custom.socketjk.app;

import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.framework.LineResult;
import com.kingdee.eas.framework.exception.EASMultiException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;

import com.kingdee.eas.custom.socketjk.SocketMsgSetInfo;
import com.kingdee.eas.framework.app.DataBaseControllerBean;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.custom.socketjk.SocketMsgSetCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import java.lang.String;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;



public abstract class AbstractSocketMsgSetControllerBean extends DataBaseControllerBean implements SocketMsgSetController
{
    protected AbstractSocketMsgSetControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("9D7E9B03");
    }

    public SocketMsgSetInfo getSocketMsgSetInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1b0e1f7d-8b86-439f-88de-601f3fade291"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            SocketMsgSetInfo retValue = (SocketMsgSetInfo)_getValue(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (SocketMsgSetInfo)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        return super._getValue(ctx, pk);
    }

    public SocketMsgSetInfo getSocketMsgSetInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("eff2475e-6e64-402d-85bf-8a1ecc2e8d42"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            SocketMsgSetInfo retValue = (SocketMsgSetInfo)_getValue(ctx, pk, selector);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (SocketMsgSetInfo)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        return super._getValue(ctx, pk, selector);
    }

    public SocketMsgSetInfo getSocketMsgSetInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d9c48b76-bfc3-44df-b1cd-ddc22fee914e"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            SocketMsgSetInfo retValue = (SocketMsgSetInfo)_getValue(ctx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (SocketMsgSetInfo)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, String oql) throws BOSException, EASBizException
    {
        return super._getValue(ctx, oql);
    }

    public SocketMsgSetCollection getSocketMsgSetCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("552b4c51-df34-413a-b9a2-4bb876add81e"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            SocketMsgSetCollection retValue = (SocketMsgSetCollection)_getCollection(ctx, svcCtx);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (SocketMsgSetCollection)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getCollection(Context ctx, IServiceContext svcCtx) throws BOSException
    {
        return super._getCollection(ctx, svcCtx);
    }

    public SocketMsgSetCollection getSocketMsgSetCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7c72b069-a7a7-4c8d-9ceb-223e0d74b997"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            SocketMsgSetCollection retValue = (SocketMsgSetCollection)_getCollection(ctx, svcCtx, view);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (SocketMsgSetCollection)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getCollection(Context ctx, IServiceContext svcCtx, EntityViewInfo view) throws BOSException
    {
        return super._getCollection(ctx, svcCtx, view);
    }

    public SocketMsgSetCollection getSocketMsgSetCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a8fc0986-b189-4f47-af03-7b8d39e6f4b4"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            SocketMsgSetCollection retValue = (SocketMsgSetCollection)_getCollection(ctx, svcCtx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (SocketMsgSetCollection)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getCollection(Context ctx, IServiceContext svcCtx, String oql) throws BOSException
    {
        return super._getCollection(ctx, svcCtx, oql);
    }

    public DataBaseCollection getDataBaseCollection (Context ctx) throws BOSException
    {
    	return (DataBaseCollection)(getSocketMsgSetCollection(ctx).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (DataBaseCollection)(getSocketMsgSetCollection(ctx, view).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (DataBaseCollection)(getSocketMsgSetCollection(ctx, oql).cast(DataBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getSocketMsgSetCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getSocketMsgSetCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getSocketMsgSetCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getSocketMsgSetCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getSocketMsgSetCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getSocketMsgSetCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}