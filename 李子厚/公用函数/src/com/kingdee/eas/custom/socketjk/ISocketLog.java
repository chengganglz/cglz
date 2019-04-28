package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import java.lang.String;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.*;

public interface ISocketLog extends ICoreBillBase
{
    public SocketLogCollection getSocketLogCollection() throws BOSException;
    public SocketLogCollection getSocketLogCollection(EntityViewInfo view) throws BOSException;
    public SocketLogCollection getSocketLogCollection(String oql) throws BOSException;
    public SocketLogInfo getSocketLogInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SocketLogInfo getSocketLogInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SocketLogInfo getSocketLogInfo(String oql) throws BOSException, EASBizException;
}