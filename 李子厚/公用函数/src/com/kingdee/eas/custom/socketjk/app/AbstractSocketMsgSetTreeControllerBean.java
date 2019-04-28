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

import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.ObjectBaseCollection;
import java.lang.String;
import com.kingdee.eas.custom.socketjk.SocketMsgSetTreeInfo;
import com.kingdee.eas.framework.app.TreeBaseControllerBean;
import com.kingdee.eas.custom.socketjk.SocketMsgSetTreeCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;



public abstract class AbstractSocketMsgSetTreeControllerBean extends TreeBaseControllerBean implements SocketMsgSetTreeController
{
    protected AbstractSocketMsgSetTreeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("370B4941");
    }

    public SocketMsgSetTreeInfo getSocketMsgSetTreeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("abf90349-4a3b-4df1-982f-beb74c38bdc1"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            SocketMsgSetTreeInfo retValue = (SocketMsgSetTreeInfo)_getValue(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (SocketMsgSetTreeInfo)svcCtx.getMethodReturnValue();
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

    public SocketMsgSetTreeInfo getSocketMsgSetTreeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d9198f0c-67ff-4ba7-a1d5-9404d0da8cb7"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            SocketMsgSetTreeInfo retValue = (SocketMsgSetTreeInfo)_getValue(ctx, pk, selector);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (SocketMsgSetTreeInfo)svcCtx.getMethodReturnValue();
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

    public SocketMsgSetTreeInfo getSocketMsgSetTreeInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("91f80897-a03b-4d72-8a08-57593ec4d30d"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            SocketMsgSetTreeInfo retValue = (SocketMsgSetTreeInfo)_getValue(ctx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (SocketMsgSetTreeInfo)svcCtx.getMethodReturnValue();
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

    public SocketMsgSetTreeCollection getSocketMsgSetTreeCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("bc4bb0b3-6519-4153-9a12-6fbbc3d70b9c"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            SocketMsgSetTreeCollection retValue = (SocketMsgSetTreeCollection)_getCollection(ctx, svcCtx);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (SocketMsgSetTreeCollection)svcCtx.getMethodReturnValue();
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

    public SocketMsgSetTreeCollection getSocketMsgSetTreeCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("af7eb414-56e6-426d-bd59-3ae120dc656f"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            SocketMsgSetTreeCollection retValue = (SocketMsgSetTreeCollection)_getCollection(ctx, svcCtx, view);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (SocketMsgSetTreeCollection)svcCtx.getMethodReturnValue();
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

    public SocketMsgSetTreeCollection getSocketMsgSetTreeCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a090a6a1-8e88-4f1d-92cb-05f68651d5d3"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            SocketMsgSetTreeCollection retValue = (SocketMsgSetTreeCollection)_getCollection(ctx, svcCtx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (SocketMsgSetTreeCollection)svcCtx.getMethodReturnValue();
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

    public TreeBaseCollection getTreeBaseCollection (Context ctx) throws BOSException
    {
    	return (TreeBaseCollection)(getSocketMsgSetTreeCollection(ctx).cast(TreeBaseCollection.class));
    }
    public TreeBaseCollection getTreeBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (TreeBaseCollection)(getSocketMsgSetTreeCollection(ctx, view).cast(TreeBaseCollection.class));
    }
    public TreeBaseCollection getTreeBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (TreeBaseCollection)(getSocketMsgSetTreeCollection(ctx, oql).cast(TreeBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx) throws BOSException
    {
    	return (DataBaseCollection)(getSocketMsgSetTreeCollection(ctx).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (DataBaseCollection)(getSocketMsgSetTreeCollection(ctx, view).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (DataBaseCollection)(getSocketMsgSetTreeCollection(ctx, oql).cast(DataBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getSocketMsgSetTreeCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getSocketMsgSetTreeCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getSocketMsgSetTreeCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getSocketMsgSetTreeCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getSocketMsgSetTreeCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getSocketMsgSetTreeCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}