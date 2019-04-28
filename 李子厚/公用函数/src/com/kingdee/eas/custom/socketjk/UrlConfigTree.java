package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.custom.socketjk.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.TreeBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class UrlConfigTree extends TreeBase implements IUrlConfigTree
{
    public UrlConfigTree()
    {
        super();
        registerInterface(IUrlConfigTree.class, this);
    }
    public UrlConfigTree(Context ctx)
    {
        super(ctx);
        registerInterface(IUrlConfigTree.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8BD19E20");
    }
    private UrlConfigTreeController getController() throws BOSException
    {
        return (UrlConfigTreeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public UrlConfigTreeInfo getUrlConfigTreeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getUrlConfigTreeInfo(getContext(), pk);
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
    public UrlConfigTreeInfo getUrlConfigTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getUrlConfigTreeInfo(getContext(), pk, selector);
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
    public UrlConfigTreeInfo getUrlConfigTreeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getUrlConfigTreeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public UrlConfigTreeCollection getUrlConfigTreeCollection() throws BOSException
    {
        try {
            return getController().getUrlConfigTreeCollection(getContext());
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
    public UrlConfigTreeCollection getUrlConfigTreeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getUrlConfigTreeCollection(getContext(), view);
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
    public UrlConfigTreeCollection getUrlConfigTreeCollection(String oql) throws BOSException
    {
        try {
            return getController().getUrlConfigTreeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}