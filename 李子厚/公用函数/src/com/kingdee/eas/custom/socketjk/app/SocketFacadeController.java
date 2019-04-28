package com.kingdee.eas.custom.socketjk.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.eas.custom.socketjk.SocketServerStatusCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.custom.socketjk.UrlConfigInfo;
import java.math.BigDecimal;
import java.lang.String;
import com.kingdee.eas.custom.socketjk.SocketResult;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SocketFacadeController extends BizController
{
    public void RunService(Context ctx) throws BOSException, RemoteException;
    public void StopService(Context ctx) throws BOSException, RemoteException;
    public SocketServerStatusCollection getServerStatusList(Context ctx) throws BOSException, RemoteException;
    public boolean executeScript(Context ctx) throws BOSException, RemoteException;
    public void channelRead(Context ctx) throws BOSException, RemoteException;
    public void InsSocketLog(Context ctx, UrlConfigInfo urlConfig, String socketMsg, String losMsg) throws BOSException, RemoteException;
    public SocketResult sendSocketMsg(Context ctx, String socketMsgSetNo, String billId) throws BOSException, RemoteException;
    public MaterialInfo getMaterialInfo(Context ctx, String zzbm, String bs, String fl, String ph, String phmc, BigDecimal kd, BigDecimal hd, String dwmc, String dwzbm, boolean isBatchNo) throws BOSException, EASBizException, RemoteException;
}