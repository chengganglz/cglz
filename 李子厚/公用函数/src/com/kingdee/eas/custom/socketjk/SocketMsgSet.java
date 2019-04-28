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

public class SocketMsgSet extends DataBase implements ISocketMsgSet
{
    public SocketMsgSet()
    {
        super();
        registerInterface(ISocketMsgSet.class, this);
    }
    public SocketMsgSet(Context ctx)
    {
        super(ctx);
        registerInterface(ISocketMsgSet.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9D7E9B03");
    }
    private SocketMsgSetController getController() throws BOSException
    {
        return (SocketMsgSetController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SocketMsgSetInfo getSocketMsgSetInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSocketMsgSetInfo(getContext(), pk);
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
    public SocketMsgSetInfo getSocketMsgSetInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSocketMsgSetInfo(getContext(), pk, selector);
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
    public SocketMsgSetInfo getSocketMsgSetInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSocketMsgSetInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SocketMsgSetCollection getSocketMsgSetCollection() throws BOSException
    {
        try {
            return getController().getSocketMsgSetCollection(getContext());
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
    public SocketMsgSetCollection getSocketMsgSetCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSocketMsgSetCollection(getContext(), view);
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
    public SocketMsgSetCollection getSocketMsgSetCollection(String oql) throws BOSException
    {
        try {
            return getController().getSocketMsgSetCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}