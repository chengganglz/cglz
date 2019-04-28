package com.kingdee.eas.custom.socketjk.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.custom.socketjk.SocketServerStatusCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.custom.socketjk.SocketServerStatusInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SocketServerStatusController extends CoreBillBaseController
{
    public SocketServerStatusCollection getSocketServerStatusCollection(Context ctx) throws BOSException, RemoteException;
    public SocketServerStatusCollection getSocketServerStatusCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SocketServerStatusCollection getSocketServerStatusCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public SocketServerStatusInfo getSocketServerStatusInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SocketServerStatusInfo getSocketServerStatusInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SocketServerStatusInfo getSocketServerStatusInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
}