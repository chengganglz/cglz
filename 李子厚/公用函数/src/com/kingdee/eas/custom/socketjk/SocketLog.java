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

public class SocketLog extends CoreBillBase implements ISocketLog
{
    public SocketLog()
    {
        super();
        registerInterface(ISocketLog.class, this);
    }
    public SocketLog(Context ctx)
    {
        super(ctx);
        registerInterface(ISocketLog.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E2AB04A2");
    }
    private SocketLogController getController() throws BOSException
    {
        return (SocketLogController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SocketLogCollection getSocketLogCollection() throws BOSException
    {
        try {
            return getController().getSocketLogCollection(getContext());
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
    public SocketLogCollection getSocketLogCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSocketLogCollection(getContext(), view);
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
    public SocketLogCollection getSocketLogCollection(String oql) throws BOSException
    {
        try {
            return getController().getSocketLogCollection(getContext(), oql);
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
    public SocketLogInfo getSocketLogInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSocketLogInfo(getContext(), pk);
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
    public SocketLogInfo getSocketLogInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSocketLogInfo(getContext(), pk, selector);
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
    public SocketLogInfo getSocketLogInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSocketLogInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}