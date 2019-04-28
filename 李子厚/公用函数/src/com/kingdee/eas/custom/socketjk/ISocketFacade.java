package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import java.math.BigDecimal;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

public interface ISocketFacade extends IBizCtrl
{
    public void RunService() throws BOSException;
    public void StopService() throws BOSException;
    public SocketServerStatusCollection getServerStatusList() throws BOSException;
    public boolean executeScript() throws BOSException;
    public void channelRead() throws BOSException;
    public void InsSocketLog(UrlConfigInfo urlConfig, String socketMsg, String losMsg) throws BOSException;
    public SocketResult sendSocketMsg(String socketMsgSetNo, String billId) throws BOSException;
    public MaterialInfo getMaterialInfo(String zzbm, String bs, String fl, String ph, String phmc, BigDecimal kd, BigDecimal hd, String dwmc, String dwzbm, boolean isBatchNo) throws BOSException, EASBizException;
}