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

public interface IUrlConfig extends IDataBase
{
    public UrlConfigInfo getUrlConfigInfo(IObjectPK pk) throws BOSException, EASBizException;
    public UrlConfigInfo getUrlConfigInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public UrlConfigInfo getUrlConfigInfo(String oql) throws BOSException, EASBizException;
    public UrlConfigCollection getUrlConfigCollection() throws BOSException;
    public UrlConfigCollection getUrlConfigCollection(EntityViewInfo view) throws BOSException;
    public UrlConfigCollection getUrlConfigCollection(String oql) throws BOSException;
}