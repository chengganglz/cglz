package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public interface ISocketMsgSetEntryIn extends ICoreBillEntryBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public SocketMsgSetEntryInInfo getSocketMsgSetEntryInInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SocketMsgSetEntryInInfo getSocketMsgSetEntryInInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SocketMsgSetEntryInInfo getSocketMsgSetEntryInInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(SocketMsgSetEntryInInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, SocketMsgSetEntryInInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, SocketMsgSetEntryInInfo model) throws BOSException, EASBizException;
    public void updatePartial(SocketMsgSetEntryInInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, SocketMsgSetEntryInInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public SocketMsgSetEntryInCollection getSocketMsgSetEntryInCollection() throws BOSException;
    public SocketMsgSetEntryInCollection getSocketMsgSetEntryInCollection(EntityViewInfo view) throws BOSException;
    public SocketMsgSetEntryInCollection getSocketMsgSetEntryInCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
}