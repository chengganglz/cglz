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

public interface IUrlConfigTree extends ITreeBase
{
    public UrlConfigTreeInfo getUrlConfigTreeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public UrlConfigTreeInfo getUrlConfigTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public UrlConfigTreeInfo getUrlConfigTreeInfo(String oql) throws BOSException, EASBizException;
    public UrlConfigTreeCollection getUrlConfigTreeCollection() throws BOSException;
    public UrlConfigTreeCollection getUrlConfigTreeCollection(EntityViewInfo view) throws BOSException;
    public UrlConfigTreeCollection getUrlConfigTreeCollection(String oql) throws BOSException;
}