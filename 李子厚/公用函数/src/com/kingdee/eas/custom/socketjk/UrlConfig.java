package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.custom.socketjk.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class UrlConfig extends DataBase implements IUrlConfig
{
    public UrlConfig()
    {
        super();
        registerInterface(IUrlConfig.class, this);
    }
    public UrlConfig(Context ctx)
    {
        super(ctx);
        registerInterface(IUrlConfig.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DB383762");
    }
    private UrlConfigController getController() throws BOSException
    {
        return (UrlConfigController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public UrlConfigInfo getUrlConfigInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getUrlConfigInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public UrlConfigInfo getUrlConfigInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getUrlConfigInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public UrlConfigInfo getUrlConfigInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getUrlConfigInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public UrlConfigCollection getUrlConfigCollection() throws BOSException
    {
        try {
            return getController().getUrlConfigCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public UrlConfigCollection getUrlConfigCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getUrlConfigCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public UrlConfigCollection getUrlConfigCollection(String oql) throws BOSException
    {
        try {
            return getController().getUrlConfigCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}