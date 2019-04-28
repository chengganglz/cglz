package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.custom.socketjk.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBillBase;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class SocketServerStatus extends CoreBillBase implements ISocketServerStatus
{
    public SocketServerStatus()
    {
        super();
        registerInterface(ISocketServerStatus.class, this);
    }
    public SocketServerStatus(Context ctx)
    {
        super(ctx);
        registerInterface(ISocketServerStatus.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("04227577");
    }
    private SocketServerStatusController getController() throws BOSException
    {
        return (SocketServerStatusController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SocketServerStatusCollection getSocketServerStatusCollection() throws BOSException
    {
        try {
            return getController().getSocketServerStatusCollection(getContext());
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
    public SocketServerStatusCollection getSocketServerStatusCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSocketServerStatusCollection(getContext(), view);
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
    public SocketServerStatusCollection getSocketServerStatusCollection(String oql) throws BOSException
    {
        try {
            return getController().getSocketServerStatusCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SocketServerStatusInfo getSocketServerStatusInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSocketServerStatusInfo(getContext(), pk);
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
    public SocketServerStatusInfo getSocketServerStatusInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSocketServerStatusInfo(getContext(), pk, selector);
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
    public SocketServerStatusInfo getSocketServerStatusInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSocketServerStatusInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}