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
import com.kingdee.eas.custom.socketjk.UrlConfigTreeCollection;
import java.lang.String;
import com.kingdee.eas.framework.app.TreeBaseControllerBean;
import com.kingdee.eas.custom.socketjk.UrlConfigTreeInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;



public abstract class AbstractUrlConfigTreeControllerBean extends TreeBaseControllerBean implements UrlConfigTreeController
{
    protected AbstractUrlConfigTreeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("8BD19E20");
    }

    public UrlConfigTreeInfo getUrlConfigTreeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7ad531d4-71e7-423f-a848-8c0e5bda50e2"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            UrlConfigTreeInfo retValue = (UrlConfigTreeInfo)_getValue(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (UrlConfigTreeInfo)svcCtx.getMethodReturnValue();
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

    public UrlConfigTreeInfo getUrlConfigTreeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d5b90582-9bc1-4b26-9f65-c63a9e9892cd"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            UrlConfigTreeInfo retValue = (UrlConfigTreeInfo)_getValue(ctx, pk, selector);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (UrlConfigTreeInfo)svcCtx.getMethodReturnValue();
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

    public UrlConfigTreeInfo getUrlConfigTreeInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1ff6f847-1d48-434e-8155-c04e73ee286f"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            UrlConfigTreeInfo retValue = (UrlConfigTreeInfo)_getValue(ctx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (UrlConfigTreeInfo)svcCtx.getMethodReturnValue();
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

    public UrlConfigTreeCollection getUrlConfigTreeCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("98a3acd2-595e-44a1-b5bb-5027ba19b318"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            UrlConfigTreeCollection retValue = (UrlConfigTreeCollection)_getCollection(ctx, svcCtx);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (UrlConfigTreeCollection)svcCtx.getMethodReturnValue();
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

    public UrlConfigTreeCollection getUrlConfigTreeCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("bd0461da-7c00-4f9c-9186-350344384f44"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            UrlConfigTreeCollection retValue = (UrlConfigTreeCollection)_getCollection(ctx, svcCtx, view);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (UrlConfigTreeCollection)svcCtx.getMethodReturnValue();
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

    public UrlConfigTreeCollection getUrlConfigTreeCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4b195e9b-3a32-4275-abae-63d4101df214"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            UrlConfigTreeCollection retValue = (UrlConfigTreeCollection)_getCollection(ctx, svcCtx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (UrlConfigTreeCollection)svcCtx.getMethodReturnValue();
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
    	return (TreeBaseCollection)(getUrlConfigTreeCollection(ctx).cast(TreeBaseCollection.class));
    }
    public TreeBaseCollection getTreeBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (TreeBaseCollection)(getUrlConfigTreeCollection(ctx, view).cast(TreeBaseCollection.class));
    }
    public TreeBaseCollection getTreeBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (TreeBaseCollection)(getUrlConfigTreeCollection(ctx, oql).cast(TreeBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx) throws BOSException
    {
    	return (DataBaseCollection)(getUrlConfigTreeCollection(ctx).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (DataBaseCollection)(getUrlConfigTreeCollection(ctx, view).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (DataBaseCollection)(getUrlConfigTreeCollection(ctx, oql).cast(DataBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getUrlConfigTreeCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getUrlConfigTreeCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getUrlConfigTreeCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getUrlConfigTreeCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getUrlConfigTreeCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getUrlConfigTreeCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}