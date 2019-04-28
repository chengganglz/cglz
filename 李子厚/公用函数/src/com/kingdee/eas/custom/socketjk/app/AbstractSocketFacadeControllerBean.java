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

import com.kingdee.eas.custom.socketjk.SocketServerStatusCollection;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.custom.socketjk.UrlConfigInfo;
import java.math.BigDecimal;
import java.lang.String;
import com.kingdee.eas.custom.socketjk.SocketResult;
import com.kingdee.eas.common.EASBizException;



public abstract class AbstractSocketFacadeControllerBean extends AbstractBizControllerBean implements SocketFacadeController
{
    protected AbstractSocketFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("908D6A7C");
    }

    public void RunService(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("656bb632-aebb-4d41-9d0f-1972d7e583e3"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _RunService(ctx);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _RunService(Context ctx) throws BOSException
    {    	
        return;
    }

    public void StopService(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ca1d7b47-a582-4d20-b2a4-07140efcec62"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _StopService(ctx);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _StopService(Context ctx) throws BOSException
    {    	
        return;
    }

    public SocketServerStatusCollection getServerStatusList(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("bfc44700-bc3f-44cc-ae53-3482d531774d"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            SocketServerStatusCollection retValue = (SocketServerStatusCollection)_getServerStatusList(ctx);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (SocketServerStatusCollection)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getServerStatusList(Context ctx) throws BOSException
    {    	
        return null;
    }

    public boolean executeScript(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("93a61b9f-6049-4aad-adb3-7c1a0e9a2c0b"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_executeScript(ctx);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
            }
            invokeServiceAfter(svcCtx);
            return ((Boolean)svcCtx.getMethodReturnValue()).booleanValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected boolean _executeScript(Context ctx) throws BOSException
    {    	
        return false;
    }

    public void channelRead(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("21034f2b-da0d-4a1a-855b-d8b9f145fbbf"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _channelRead(ctx);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _channelRead(Context ctx) throws BOSException
    {    	
        return;
    }

    public void InsSocketLog(Context ctx, UrlConfigInfo urlConfig, String socketMsg, String losMsg) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("345f92c3-0b24-4463-9bc1-ff6233c14bc3"), new Object[]{ctx, urlConfig, socketMsg, losMsg});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _InsSocketLog(ctx, urlConfig, socketMsg, losMsg);
            }
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _InsSocketLog(Context ctx, IObjectValue urlConfig, String socketMsg, String losMsg) throws BOSException
    {    	
        return;
    }

    public SocketResult sendSocketMsg(Context ctx, String socketMsgSetNo, String billId) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("58f2a1a4-5e76-43e2-a5da-bac615fa738a"), new Object[]{ctx, socketMsgSetNo, billId});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            SocketResult retValue = (SocketResult)_sendSocketMsg(ctx, socketMsgSetNo, billId);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (SocketResult)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected SocketResult _sendSocketMsg(Context ctx, String socketMsgSetNo, String billId) throws BOSException
    {    	
        return null;
    }

    public MaterialInfo getMaterialInfo(Context ctx, String zzbm, String bs, String fl, String ph, String phmc, BigDecimal kd, BigDecimal hd, String dwmc, String dwzbm, boolean isBatchNo) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e8c849c3-cc1e-4843-bba5-98bf38c1918f"), new Object[]{ctx, zzbm, bs, fl, ph, phmc, kd, hd, dwmc, dwzbm, new Boolean(isBatchNo)});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            MaterialInfo retValue = (MaterialInfo)_getMaterialInfo(ctx, zzbm, bs, fl, ph, phmc, kd, hd, dwmc, dwzbm, isBatchNo);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (MaterialInfo)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getMaterialInfo(Context ctx, String zzbm, String bs, String fl, String ph, String phmc, BigDecimal kd, BigDecimal hd, String dwmc, String dwzbm, boolean isBatchNo) throws BOSException, EASBizException
    {    	
        return null;
    }

}