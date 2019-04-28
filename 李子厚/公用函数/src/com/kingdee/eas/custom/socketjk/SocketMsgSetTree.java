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

public class SocketMsgSetTree extends TreeBase implements ISocketMsgSetTree
{
    public SocketMsgSetTree()
    {
        super();
        registerInterface(ISocketMsgSetTree.class, this);
    }
    public SocketMsgSetTree(Context ctx)
    {
        super(ctx);
        registerInterface(ISocketMsgSetTree.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("370B4941");
    }
    private SocketMsgSetTreeController getController() throws BOSException
    {
        return (SocketMsgSetTreeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SocketMsgSetTreeInfo getSocketMsgSetTreeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSocketMsgSetTreeInfo(getContext(), pk);
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
    public SocketMsgSetTreeInfo getSocketMsgSetTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSocketMsgSetTreeInfo(getContext(), pk, selector);
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
    public SocketMsgSetTreeInfo getSocketMsgSetTreeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSocketMsgSetTreeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SocketMsgSetTreeCollection getSocketMsgSetTreeCollection() throws BOSException
    {
        try {
            return getController().getSocketMsgSetTreeCollection(getContext());
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
    public SocketMsgSetTreeCollection getSocketMsgSetTreeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSocketMsgSetTreeCollection(getContext(), view);
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
    public SocketMsgSetTreeCollection getSocketMsgSetTreeCollection(String oql) throws BOSException
    {
        try {
            return getController().getSocketMsgSetTreeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}