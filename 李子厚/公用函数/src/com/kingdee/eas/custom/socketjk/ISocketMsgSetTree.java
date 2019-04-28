package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import java.lang.String;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.*;

public interface ISocketMsgSetTree extends ITreeBase
{
    public SocketMsgSetTreeInfo getSocketMsgSetTreeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SocketMsgSetTreeInfo getSocketMsgSetTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SocketMsgSetTreeInfo getSocketMsgSetTreeInfo(String oql) throws BOSException, EASBizException;
    public SocketMsgSetTreeCollection getSocketMsgSetTreeCollection() throws BOSException;
    public SocketMsgSetTreeCollection getSocketMsgSetTreeCollection(EntityViewInfo view) throws BOSException;
    public SocketMsgSetTreeCollection getSocketMsgSetTreeCollection(String oql) throws BOSException;
}