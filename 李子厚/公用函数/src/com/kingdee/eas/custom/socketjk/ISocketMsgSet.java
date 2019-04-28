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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.*;

public interface ISocketMsgSet extends IDataBase
{
    public SocketMsgSetInfo getSocketMsgSetInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SocketMsgSetInfo getSocketMsgSetInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SocketMsgSetInfo getSocketMsgSetInfo(String oql) throws BOSException, EASBizException;
    public SocketMsgSetCollection getSocketMsgSetCollection() throws BOSException;
    public SocketMsgSetCollection getSocketMsgSetCollection(EntityViewInfo view) throws BOSException;
    public SocketMsgSetCollection getSocketMsgSetCollection(String oql) throws BOSException;
}