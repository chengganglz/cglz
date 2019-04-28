package com.kingdee.eas.custom.socketjk.app;

import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.framework.LineResult;
import com.kingdee.eas.framework.exception.EASMultiException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;



public abstract class AbstracttestFacadeControllerBean extends AbstractBizControllerBean implements testFacadeController
{
    protected AbstracttestFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("49C3F7FB");
    }

    public void CreateBill(Context ctx, String strMsg) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("83c8d661-f041-4b44-8b63-c072ac29498d"), new Object[]{ctx, strMsg});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _CreateBill(ctx, strMsg);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _CreateBill(Context ctx, String strMsg) throws BOSException, EASBizException
    {    	
        return;
    }

}