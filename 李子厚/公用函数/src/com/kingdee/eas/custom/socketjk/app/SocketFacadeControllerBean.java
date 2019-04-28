package com.kingdee.eas.custom.socketjk.app;

import org.apache.log4j.Logger;

import io.netty.buffer.Unpooled;
import io.netty.channel.socket.SocketChannel;

import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.kingdee.bos.*;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.framework.DataBaseDInfo;
import com.kingdee.eas.basedata.master.material.ABCEnum;
import com.kingdee.eas.basedata.master.material.AccountType;
import com.kingdee.eas.basedata.master.material.CalculateTypeEnum;
import com.kingdee.eas.basedata.master.material.EquipmentPropertyEnum;
import com.kingdee.eas.basedata.master.material.IssuePriorityEnum;
import com.kingdee.eas.basedata.master.material.MaterialCompanyInfo;
import com.kingdee.eas.basedata.master.material.MaterialCompanyInfoFactory;
import com.kingdee.eas.basedata.master.material.MaterialCompanyInfoInfo;
import com.kingdee.eas.basedata.master.material.MaterialCostFactory;
import com.kingdee.eas.basedata.master.material.MaterialCostInfo;
import com.kingdee.eas.basedata.master.material.MaterialFacade;
import com.kingdee.eas.basedata.master.material.MaterialFactory;
import com.kingdee.eas.basedata.master.material.MaterialGroup;
import com.kingdee.eas.basedata.master.material.MaterialGroupDetialCollection;
import com.kingdee.eas.basedata.master.material.MaterialGroupDetialFactory;
import com.kingdee.eas.basedata.master.material.MaterialGroupDetialInfo;
import com.kingdee.eas.basedata.master.material.MaterialGroupInfo;
import com.kingdee.eas.basedata.master.material.MaterialGroupStandardInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.master.material.MaterialInventory;
import com.kingdee.eas.basedata.master.material.MaterialInventoryFactory;
import com.kingdee.eas.basedata.master.material.MaterialInventoryInfo;
import com.kingdee.eas.basedata.master.material.MaterialPurchasingFactory;
import com.kingdee.eas.basedata.master.material.MaterialPurchasingInfo;
import com.kingdee.eas.basedata.master.material.MaterialSalesFactory;
import com.kingdee.eas.basedata.master.material.MaterialSalesInfo;
import com.kingdee.eas.basedata.master.material.QuotaPeriodEnum;
import com.kingdee.eas.basedata.master.material.QuotaPolicyInfo;
import com.kingdee.eas.basedata.master.material.StoreType;
import com.kingdee.eas.basedata.master.material.TimeUnitEnum;
import com.kingdee.eas.basedata.master.material.UsedStatusEnum;
import com.kingdee.eas.basedata.master.material.app.MaterialControllerBean;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.socketjk.LocalSocketServer;
import com.kingdee.eas.custom.socketjk.PublicBaseUtil;
import com.kingdee.eas.custom.socketjk.PublicUtil;
import com.kingdee.eas.custom.socketjk.RemoteSocketClient;
import com.kingdee.eas.custom.socketjk.SocketFacadeFactory;
import com.kingdee.eas.custom.socketjk.SocketLogFactory;
import com.kingdee.eas.custom.socketjk.SocketLogInfo;
import com.kingdee.eas.custom.socketjk.SocketMsg;
import com.kingdee.eas.custom.socketjk.SocketMsgSetCollection;
import com.kingdee.eas.custom.socketjk.SocketMsgSetFactory;
import com.kingdee.eas.custom.socketjk.SocketMsgSetInfo;
import com.kingdee.eas.custom.socketjk.SocketResult;
import com.kingdee.eas.custom.socketjk.SocketServerChannal;
import com.kingdee.eas.custom.socketjk.SocketServerStatusCollection;
import com.kingdee.eas.custom.socketjk.SocketServerStatusInfo;
import com.kingdee.eas.custom.socketjk.UrlConfigCollection;
import com.kingdee.eas.custom.socketjk.UrlConfigFactory;
import com.kingdee.eas.custom.socketjk.UrlConfigInfo;
import com.kingdee.eas.custom.socketjk.UrlType;
import com.kingdee.eas.ep.app.BeanParam;
import com.kingdee.eas.ep.plugin.ExtendMethodInfo;
import com.kingdee.eas.ep.plugin.MethodParam;
import com.kingdee.eas.ep.plugin.ScriptExecuteException;
import com.kingdee.eas.ep.plugin.ScriptExecutorFactotry;
import com.kingdee.eas.ep.plugin.ScriptInfo;
//import com.kingdee.eas.ep.app.BeanParam;
//import com.kingdee.eas.ep.plugin.ExtendMethodInfo;
//import com.kingdee.eas.ep.plugin.MethodParam;
//import com.kingdee.eas.ep.plugin.ScriptExecutorFactotry;
//import com.kingdee.eas.ep.plugin.ScriptInfo;
import com.kingdee.eas.scm.common.BillBaseStatusEnum;
import com.kingdee.eas.scm.sd.sale.SaleOrderFactory;
import com.kingdee.eas.scm.sd.sale.SaleOrderInfo;
import com.kingdee.eas.scm.sd.sale.client.SaleOrderEditUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;
//import com.kingdee.util.StringUtils;


public class SocketFacadeControllerBean extends AbstractSocketFacadeControllerBean
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2548989792652051633L;
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.custom.socketjk.app.SocketFacadeControllerBean");
    protected void _RunService(Context ctx) throws BOSException
    {    	
    	if(true){
    		
    	    	if(PublicUtil.LocalServerList.size()==0){
        	//��ȡ���ط����б�
        	EntityViewInfo view = new EntityViewInfo(); 
            SelectorItemCollection sic = view.getSelector();
            sic.add("*");
            sic.add("FICompany.*");
            FilterInfo filter = new FilterInfo();
            FilterItemCollection fic = filter.getFilterItems();
            fic.add(new FilterItemInfo("isUse", 1,CompareType.EQUALS));
            fic.add(new FilterItemInfo("urlType", UrlType.Server.getValue(),CompareType.EQUALS));
            view.setFilter(filter);
            UrlConfigCollection urlconfigs=null;
    		try {
    			urlconfigs = UrlConfigFactory.getLocalInstance(ctx).getUrlConfigCollection(view); //��ȡurl �������ý����
    		} catch (BOSException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            if (urlconfigs.size()>0){
            	
            	for(int i=0;i<urlconfigs.size();i++){
            		UrlConfigInfo info =urlconfigs.get(i);
            		if (info!=null){
            			LocalSocketServer localServer=new LocalSocketServer();
            			localServer.setConfigInfo(info);
            			localServer.setServerCtx(ctx);
            			try {
							localServer.start();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        		}
            	
            	}
            }
    	    	}
    		}
    	
    	if(true){
    		
    	    	if(PublicUtil.RemoteClientList.size()==0){
	    	      //��ȡԶ�̷����б�
	    	    	EntityViewInfo view = new EntityViewInfo(); 
	    	        SelectorItemCollection sic = view.getSelector();
	    	        sic.add("*");
	    	        sic.add("FICompany.*");
	    	        FilterInfo filter = new FilterInfo();
	    	        FilterItemCollection fic = filter.getFilterItems();
	    	        fic.add(new FilterItemInfo("isUse", 1,CompareType.EQUALS));
	    	        fic.add(new FilterItemInfo("urlType", UrlType.Client.getValue(),CompareType.EQUALS));
	    	        view.setFilter(filter);
	    	        UrlConfigCollection urlconfigs=null;
	    			try {
	    				urlconfigs = UrlConfigFactory.getLocalInstance(ctx).getUrlConfigCollection(view); //��ȡurl �������ý����
	    			} catch (BOSException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    	        if (urlconfigs.size()>0){
	    	        	for(int i=0;i<urlconfigs.size();i++){
	    	        		UrlConfigInfo info =urlconfigs.get(i);
	    	        		if (info!=null){
	    	        			RemoteSocketClient remoteClient=new RemoteSocketClient();
	    	        			remoteClient.setConfigInfo(info);
	    	        			remoteClient.setServerCtx(ctx);
	    	        			try {
									remoteClient.start();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	    	        		}
	    	        	}
	    	        }
    	    	}
    		
    	}
    	        		
        return;
    }
    
    protected void _StopService(Context ctx) throws BOSException
    {    	
    	
    	if(PublicUtil.LocalServerList.size()>0){
    		for(int i=PublicUtil.LocalServerList.size()-1;i>-1;i--)
    		{
    			LocalSocketServer localServer=PublicUtil.LocalServerList.get(i);
    			if(localServer!=null){
    				if(localServer.getCf()!=null){
//    					localServer.getCf().channel().closeFuture().sync();
    				}
    				if(localServer.getGroup()!=null){
    					try {
							localServer.getGroup().shutdownGracefully().sync();
							PublicUtil.LocalServerList.remove(localServer);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
    				}
    			}
    		}
    	}
    	
    	
    	if(PublicUtil.RemoteClientList.size()>0){
    		for(int i=PublicUtil.RemoteClientList.size()-1;i>-1;i--)
    		{
    			RemoteSocketClient remoteClient=PublicUtil.RemoteClientList.get(i);
    			if(remoteClient!=null){
    				if(remoteClient.getCf()!=null){
//    					localServer.getCf().channel().closeFuture().sync();
    				}
    				if(remoteClient.getGroup()!=null){
    					try {
							remoteClient.getGroup().shutdownGracefully().sync();
							PublicUtil.RemoteClientList.remove(remoteClient);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
    				}
    			}
    		}
    	
    	}
    }
    
    
    protected IObjectCollection _getServerStatusList(Context ctx) throws BOSException
    {    
    	
    	SocketServerStatusCollection statusColl=new SocketServerStatusCollection();

    	if(true){
    	//��ȡ���ط����б�
    	EntityViewInfo view = new EntityViewInfo(); 
        SelectorItemCollection sic = view.getSelector();
        sic.add("*");
        sic.add("FICompany.*");
        FilterInfo filter = new FilterInfo();
        FilterItemCollection fic = filter.getFilterItems();
        fic.add(new FilterItemInfo("isUse", 1,CompareType.EQUALS));
        fic.add(new FilterItemInfo("urlType", UrlType.Server.getValue(),CompareType.EQUALS));
        view.setFilter(filter);
        UrlConfigCollection urlconfigs=null;
		try {
			urlconfigs = UrlConfigFactory.getLocalInstance(ctx).getUrlConfigCollection(view); //��ȡurl �������ý����
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (urlconfigs.size()>0){
        	
        	for(int i=0;i<urlconfigs.size();i++){
        		UrlConfigInfo info =urlconfigs.get(i);
        		if (info!=null){
        			CompanyOrgUnitInfo company=info.getFICompany();
        			String compayid=company!=null?info.getFICompany().getId().toString():"";
        			String compayName=company!=null?info.getFICompany().getName():"";
        			String serverName=info.getName();
        			String serverType=info.getUrlType().getAlias();
        			String strverIP=info.getIp();
        			int port=info.getPort();
        			boolean isUseHeartbeatMsg=info.isIsUseHeartbeatMsg();
        			boolean isWirte=info.isIsWrite();
        			int heartbeatMsgTime=info.getHeartbeatMsgTime();
        			int timeOut=info.getTimeOut();
        			boolean isFind=false;
        			if(PublicUtil.LocalServerList.size()>0){
        				for (int j=0;j<PublicUtil.LocalServerList.size();j++)
        				{
        					LocalSocketServer localServer=PublicUtil.LocalServerList.get(j);
        					if(localServer!=null){
        						if (localServer.getConfigInfo()!=null){
        							if(localServer.getConfigInfo().getId().equals(info.getId())){
        								isFind=true;
        								boolean isFindClient=false;
        								if(localServer.getRemoteSocketChannelList().size()>0){
        									for(int m=0;m<localServer.getRemoteSocketChannelList().size();m++){
        										SocketServerChannal Servchannel=localServer.getRemoteSocketChannelList().get(m);
	        									if(Servchannel!=null){
	        										isFindClient=true;
		        									SocketServerStatusInfo statusInfo=new SocketServerStatusInfo();
		        									statusInfo.setCompanyID(compayid);
		        									statusInfo.setCompanyName(compayName);
		        									statusInfo.setServerType(serverType);
		        									statusInfo.setServerName(serverName);
		        									statusInfo.setTimeOut(timeOut);
		        									statusInfo.setIsUseHeartbeatMsg(isUseHeartbeatMsg==true?"��":"��");
		        									statusInfo.setHeartbeatMsgTime(heartbeatMsgTime);
		        									statusInfo.setIsWirte(isWirte==true?"��":"��");
		        									statusInfo.setLocalIP(strverIP);
		        									statusInfo.setLocalPort(port);
		        									statusInfo.setServerStatus("����");
		        									statusInfo.setRemoteIP(Servchannel.getClientChannal().remoteAddress().getHostName());
		        									statusInfo.setRemotePort(Servchannel.getClientChannal().remoteAddress().getPort());
		        									if (Servchannel.getClientChannal().isActive()){
		        										statusInfo.setChannelStatus("����");
		        									}else
		        									{
		        										statusInfo.setChannelStatus("ʧЧ");
		        									}
		        									statusColl.add(statusInfo);
        										}
        											
        									}
        								}
        								if(!isFindClient)
        								{
        									SocketServerStatusInfo statusInfo=new SocketServerStatusInfo();
        									statusInfo.setCompanyID(compayid);
        									statusInfo.setCompanyName(compayName);
        									statusInfo.setServerType(serverType);
        									statusInfo.setServerName(serverName);
        									statusInfo.setTimeOut(timeOut);
        									statusInfo.setIsUseHeartbeatMsg(isUseHeartbeatMsg==true?"��":"��");
        									statusInfo.setHeartbeatMsgTime(heartbeatMsgTime);
        									statusInfo.setIsWirte(isWirte==true?"��":"��");
        									statusInfo.setLocalIP(strverIP);
        									statusInfo.setLocalPort(port);
        									statusInfo.setServerStatus("����");
        									statusInfo.setRemoteIP("");
        									statusInfo.setRemotePort(0);
        									statusInfo.setChannelStatus("�ȴ�����");
        									
        									statusColl.add(statusInfo);
        									
        									
        									
        								}
        							}
        						}
        					}
        				}
        				
        			}else
        			{
        				SocketServerStatusInfo statusInfo=new SocketServerStatusInfo();
						statusInfo.setCompanyID(compayid);
						statusInfo.setCompanyName(compayName);
						statusInfo.setServerType(serverType);
						statusInfo.setServerName(serverName);
						statusInfo.setTimeOut(timeOut);
						statusInfo.setIsUseHeartbeatMsg(isUseHeartbeatMsg==true?"��":"��");
						statusInfo.setHeartbeatMsgTime(heartbeatMsgTime);
						statusInfo.setIsWirte(isWirte==true?"��":"��");
						statusInfo.setLocalIP(strverIP);
						statusInfo.setLocalPort(port);
						statusInfo.setServerStatus("ֹͣ");
						statusInfo.setRemoteIP("");
						statusInfo.setRemotePort(0);
						statusInfo.setChannelStatus("");
						
						statusColl.add(statusInfo);
						
        				
        			}
        			
        		}
        	}
        }
    	}
    	if(true){
      //��ȡԶ�̷����б�
    	EntityViewInfo view = new EntityViewInfo(); 
        SelectorItemCollection sic = view.getSelector();
        sic.add("*");
        sic.add("FICompany.*");
        FilterInfo filter = new FilterInfo();
        FilterItemCollection fic = filter.getFilterItems();
        fic.add(new FilterItemInfo("isUse", 1,CompareType.EQUALS));
        fic.add(new FilterItemInfo("urlType", UrlType.Client.getValue(),CompareType.EQUALS));
        view.setFilter(filter);
        UrlConfigCollection urlconfigs=null;
		try {
			urlconfigs = UrlConfigFactory.getLocalInstance(ctx).getUrlConfigCollection(view); //��ȡurl �������ý����
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (urlconfigs.size()>0){
        	for(int i=0;i<urlconfigs.size();i++){
        		UrlConfigInfo info =urlconfigs.get(i);
        		if (info!=null){
        			CompanyOrgUnitInfo company=info.getFICompany();
        			String compayid=company!=null?info.getFICompany().getId().toString():"";
        			String compayName=company!=null?info.getFICompany().getName():"";
        			String serverName=info.getName();
        			String serverType=info.getUrlType().getAlias();
        			String strverIP=info.getIp();
        			int port=info.getPort();
        			boolean isUseHeartbeatMsg=info.isIsUseHeartbeatMsg();
        			boolean isWirte=info.isIsWrite();
        			int heartbeatMsgTime=info.getHeartbeatMsgTime();
        			int timeOut=info.getTimeOut();
        			boolean isFind=false;
        			if(PublicUtil.RemoteClientList.size()>0){
        				for (int j=0;j<PublicUtil.RemoteClientList.size();j++)
        				{
        					RemoteSocketClient remoteClient=PublicUtil.RemoteClientList.get(j);
        					if(remoteClient!=null){
        						if (remoteClient.getConfigInfo()!=null){
        							if(remoteClient.getConfigInfo().getId().equals(info.getId())){
        								isFind=true;
        								boolean isFindClient=false;
        								if(remoteClient.getRomoteSocketChannel()!=null){
        									
        										SocketChannel channel=remoteClient.getRomoteSocketChannel();
	        									if(channel!=null){
	        										isFindClient=true;
	        										SocketServerStatusInfo statusInfo=new SocketServerStatusInfo();
	        										statusInfo.setCompanyID(compayid);
	        										statusInfo.setCompanyName(compayName);
	        										statusInfo.setServerType(serverType);
	        										statusInfo.setServerName(serverName);
	        										statusInfo.setTimeOut(timeOut);
	        										statusInfo.setIsUseHeartbeatMsg(isUseHeartbeatMsg==true?"��":"��");
	        										statusInfo.setHeartbeatMsgTime(heartbeatMsgTime);
	        										statusInfo.setIsWirte(isWirte==true?"��":"��");
	        										statusInfo.setLocalIP(channel.localAddress().getHostName());
	        										statusInfo.setLocalPort(channel.localAddress().getPort());
	        										statusInfo.setServerStatus("����");
	        										statusInfo.setRemoteIP(channel.remoteAddress().getHostName());
	        										statusInfo.setRemotePort(channel.remoteAddress().getPort());
	        										if (channel.isActive()){
	        											statusInfo.setChannelStatus("����");
		        									}else
		        									{
		        										statusInfo.setChannelStatus("ʧЧ");
		        									}
	        										
	        										statusColl.add(statusInfo);
        										}
        									
        								} else
        								{
        									SocketServerStatusInfo statusInfo=new SocketServerStatusInfo();
        									statusInfo.setCompanyID(compayid);
        									statusInfo.setCompanyName(compayName);
        									statusInfo.setServerType(serverType);
        									statusInfo.setServerName(serverName);
        									statusInfo.setTimeOut(timeOut);
        									statusInfo.setIsUseHeartbeatMsg(isUseHeartbeatMsg==true?"��":"��");
        									statusInfo.setHeartbeatMsgTime(heartbeatMsgTime);
        									statusInfo.setIsWirte(isWirte==true?"��":"��");
        									statusInfo.setLocalIP("");
        									statusInfo.setLocalPort(0);
        									statusInfo.setServerStatus("ֹͣ");
        									statusInfo.setRemoteIP(strverIP);
        									statusInfo.setRemotePort(port);
        									statusInfo.setChannelStatus("");
        									statusColl.add(statusInfo);
        									
        									
        									
        								}
        							}
        						}
        					}
        				}
        				
        			}else
        			{
        				SocketServerStatusInfo statusInfo=new SocketServerStatusInfo();
						statusInfo.setCompanyID(compayid);
						statusInfo.setCompanyName(compayName);
						statusInfo.setServerType(serverType);
						statusInfo.setServerName(serverName);
						statusInfo.setTimeOut(timeOut);
						statusInfo.setIsUseHeartbeatMsg(isUseHeartbeatMsg==true?"��":"��");
						statusInfo.setHeartbeatMsgTime(heartbeatMsgTime);
						statusInfo.setIsWirte(isWirte==true?"��":"��");
						statusInfo.setLocalIP("");
						statusInfo.setLocalPort(0);
						statusInfo.setServerStatus("ֹͣ");
						statusInfo.setRemoteIP(strverIP);
						statusInfo.setRemotePort(port);
						statusInfo.setChannelStatus("");
						statusColl.add(statusInfo);
						
						
        			}
        			
        		}
        	}
        }
    	}
    
        return statusColl ; 
    }
    
    
    protected boolean _executeScript(Context ctx,SocketMsgSetInfo config,String script,String socketMsg) throws BOSException
    {    	
////    	String script="  var imp = JavaImporter();  "+
////" imp.importPackage(Packages.com.kingdee.eas.util.app);  "+
////" imp.importPackage(Packages.com.kingdee.bos); "+
////
////" with(imp){ "+
////"  var a=1 ;  var billId = methodCtx.getParamValue(0);  var ctx = pluginCtx.getContext(); "+
////    			" var b=3 ;    throw new BOSException(ctx);	 }";
//    	BeanParam param = new BeanParam();
//		param.setContext(ctx);
//		ExtendMethodInfo method = new ExtendMethodInfo();
//		MethodParam socket=new MethodParam();
//		socket.setType("socketMsg");
//		socket.setValue(socketMsg);
//		method.addParam(socket);
//		
//		MethodParam billid=new MethodParam();
//		billid.setType("billID");
//		billid.setValue("");
//		method.addParam(billid);
//		
//		MethodParam returnMsg=new MethodParam();
//		returnMsg.setType("returnMsg");
//		returnMsg.setValue("");
//		method.addParam(returnMsg);
//		
//		MethodParam sendMsg=new MethodParam();
//		sendMsg.setType("sendMsg");
//		sendMsg.setValue("");
//		method.addParam(sendMsg);
//		
//		
//		MethodParam Err=new MethodParam();
//		Err.setType("Err");
//		Err.setValue("");
//		method.addParam(Err);
//		
//		
//    	if (!(StringUtils.isEmpty(script))) {
//			try {
//				ScriptExecutorFactotry.getServerInstance().execute(
//						param,
//						method,
//						new ScriptInfo(script, false, ""));
//			} catch (EASBizException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
    	
    	
        return false;
    }
    /**
     * ������־
     */
    protected void _InsSocketLog(Context ctx, IObjectValue urlConfig, String socketMsg, String losMsg) throws BOSException
    {    	
    	
    	SocketLogInfo logInfo=new SocketLogInfo();
		logInfo.setDataType((UrlConfigInfo)urlConfig);
		logInfo.setSocketData(socketMsg);
		logInfo.setErrMsg(losMsg);
		logInfo.setCU(PublicBaseUtil.getCU(ctx,""));
		try {
			logInfo.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
			logInfo.setBizDate(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logInfo.setCreator(ContextUtil.getCurrentUserInfo(ctx));
		try {
			SocketLogFactory.getLocalInstance(ctx).save(logInfo);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return;
    }
    
/**
 * ���͵��Ĺ���
 * @param socketMsgSetNo �������õ���
 * @param billId ���ݱ��
 */
    protected SocketResult _sendSocketMsg(Context ctx, String socketMsgSetNo, String billId) throws BOSException
    {    	
    	SocketResult re=new SocketResult();
    	re.setResult(false);
    	re.setErrMsg("δ֪����");
    	re.setReturnSocketMsg("");
    	if(!StringUtils.isEmpty(socketMsgSetNo)){
	    	EntityViewInfo view = new EntityViewInfo();
	        SelectorItemCollection sic = view.getSelector();
	        sic.add(new SelectorItemInfo("*"));
	        sic.add(new SelectorItemInfo("Channel.*"));
	        FilterInfo filter = new FilterInfo();
	        FilterItemCollection fic = filter.getFilterItems();
	        fic.add(new FilterItemInfo("number", socketMsgSetNo));
	        view.setFilter(filter);
	        
	        SocketMsgSetCollection socketMsgSetCollection = SocketMsgSetFactory.getLocalInstance(ctx).getSocketMsgSetCollection(view);
	        if(socketMsgSetCollection != null && socketMsgSetCollection.size() != 0){
	        	SocketMsgSetInfo socketMsgSetInfo = socketMsgSetCollection.get(0);
	        	if(socketMsgSetInfo!=null){
	        		
	        		UrlConfigInfo configInfo=socketMsgSetInfo.getChannel();
	        		if (configInfo==null){
	        			re.setResult(false);
                		re.setErrMsg("�������ò�����ͨ��δ����");
                		re.setReturnSocketMsg("");
                		return re ;
	        		}
	        		BeanParam param = new BeanParam();
	        		param.setContext(ctx);
	        		ExtendMethodInfo method = new ExtendMethodInfo();
	        		MethodParam socket=new MethodParam();
	        		socket.setType("socketMsg");
	        		socket.setValue("");
	        		method.addParam(socket);
	        		
	        		MethodParam billid=new MethodParam();
	        		billid.setType("billID");
	        		billid.setValue(billId);
	        		method.addParam(billid);
	        		
	        		MethodParam result=new MethodParam();
	        		result.setType("result");
	        		result.setValue("");
	        		method.addParam(result);
	        		
	        		MethodParam sendMsg=new MethodParam();
	        		sendMsg.setType("sendMsg");
	        		sendMsg.setValue("");
	        		method.addParam(sendMsg);
	        		
	        		
	        		MethodParam errMsg=new MethodParam();
	        		errMsg.setType("errMsg");
	        		errMsg.setValue("");
	        		method.addParam(errMsg);
	        		method.setResult("String", "");
	        		String script=socketMsgSetInfo.getScript();
	        		if (script!=null && (!script.equals(""))) {
	            		
	        			try {
	        				
	        				ScriptExecutorFactotry.getServerInstance().execute(
	        						param,
	        						method,
	        						new ScriptInfo(script, false, ""));
	        			} catch (EASBizException e) {
	        				// TODO Auto-generated catch block
	        				e.printStackTrace();
	        				try {
	        					SocketFacadeFactory.getLocalInstance(ctx).InsSocketLog(configInfo, "ִ�нű�����",e.getMessage());
	        				} catch (BOSException e1) {
	        					// TODO Auto-generated catch block
	        					e1.printStackTrace();
	        				}
	        				re.setResult(false);
                    		re.setErrMsg(e.getMessage());
                    		re.setReturnSocketMsg("");
                    		return re ;
	        			} catch (ScriptExecuteException e) {
	        				// TODO Auto-generated catch block
	        				e.printStackTrace();
	        				try {
	        					SocketFacadeFactory.getLocalInstance(ctx).InsSocketLog(configInfo,  "ִ�нű�����",e.getMessage());
	        				} catch (BOSException e1) {
	        					// TODO Auto-generated catch block
	        					e1.printStackTrace();
	        				}
	        				re.setResult(false);
                    		re.setErrMsg(e.getMessage());
                    		re.setReturnSocketMsg("");
                    		return re ;
	        			}
	        			if (result!=null)
	        			{
	        				if(result.getValue().toString().toUpperCase().equals("FALSE")){
	        					String strErr="";
	        					if(errMsg!=null){
	        						if(errMsg.getValue()!=null){
	        							strErr=errMsg.getValue().toString();
	        						}
	        					}
	        					SocketFacadeFactory.getLocalInstance(ctx).InsSocketLog(configInfo,  "ִ�нű�����","����"+strErr);
	        					re.setResult(false);
	                    		re.setErrMsg("ִ�нű�����"+strErr);
	                    		re.setReturnSocketMsg("");
	                    		return re ;
	        				}
	        			}
	        			if(!StringUtils.isEmpty( sendMsg.getValue().toString())){
	        				RemoteSocketClient remoteClient=null;
	        				for(int i=0;i<PublicUtil.RemoteClientList.size();i++){
	        					RemoteSocketClient remoteClientTemp=PublicUtil.RemoteClientList.get(i);
	        					if(remoteClientTemp.getConfigInfo().getId().equals(configInfo.getId())){
	        						remoteClient=remoteClientTemp;
	        						break;
	        					}
	        				}
	        				if (remoteClient!=null){
	        					SocketChannel ch= remoteClient.getRomoteSocketChannel();
	        					if(ch==null){
    	    	        			try {
    	    	        				remoteClient.start();
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										try {
											SocketFacadeFactory.getLocalInstance(ctx).InsSocketLog(configInfo, "����Զ�̵�ַ����", e.getMessage());
										} catch (BOSException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									
										re.setResult(false);
		                        		re.setErrMsg(e.getMessage());
		                        		re.setReturnSocketMsg("");
		                        		return re ;
									}
    	    	        		}
	        					for( int i=remoteClient.getSendMsgList().size()-1 ;i>-1;i--){
	        						SocketMsg sendMsgTemp= remoteClient.getSendMsgList().get(i);
	        						Calendar calTemp = Calendar.getInstance(); 
		                        	Date dateTemp = calTemp.getTime();
	        						if(dateTemp.getTime()-sendMsgTemp.getSendTime().getTime()>configInfo.getTimeOut()){
	        							remoteClient.getSendMsgList().remove(i);
	        						}
	        						
	        					}
	        					ch.writeAndFlush(Unpooled.copiedBuffer(sendMsg.getValue().toString(), Charset.forName(configInfo.getCharset().getValue().toString())));
	        					SocketMsg send= new SocketMsg(); 
	        					Calendar cal = Calendar.getInstance(); 
	                        	Date date = cal.getTime();
	                        	send.setSendMsg(sendMsg.getValue().toString());
	                        	send.setSendTime(date);
	                        	send.setSendTimes(1);
	                        	send.setReturnMsg("");
	                        	remoteClient.addsendMsgList(send);
	                        	
	                        	for (int j=0;j<configInfo.getTimeOut()/10;j++){
	                        		try {
										TimeUnit.MILLISECONDS.sleep(10);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										break;
									}
									boolean isReturn=false;
	                        		for(int k=0;k<remoteClient.getSendMsgList().size();k++){
	                        			SocketMsg sendTemp=remoteClient.getSendMsgList().get(k);
	                        			if (sendTemp.getSendMsg().equals(send.getSendMsg())){
	                        				send=sendTemp;
	                        				
	                        				if(!StringUtils.isEmpty( send.getReturnMsg())){
	                        					
	                        					isReturn=true;
	                        				}else
	                        				{
	                        					
	            	        					cal = Calendar.getInstance(); 
	            	                        	date = cal.getTime();
	            	                        	if(date.getTime()-send.getSendTime().getTime()>=configInfo.getTimeOut()){
	            	                        		if (send.getSendTimes()<configInfo.getReTranTimes()){
			            	                        	send.setSendTime(date);
			            	                        	
			            	                        	send.setSendTimes(send.getSendTimes()+1);
			            	                        	ch.writeAndFlush(Unpooled.copiedBuffer(sendMsg.getValue().toString(), Charset.forName(configInfo.getCharset().getValue().toString())));
	            	                        		}
	            	                        	}
	                        				}
	                        				
	                        				break;
	                        			}
	                        		}

	                        		if(isReturn){
	                        			break;
	                        		}
	                        	}
	                        	if(!StringUtils.isEmpty(send.getReturnMsg()))
	                        	{
	                        		re.setResult(true);
	                        		re.setErrMsg("");
	                        		re.setReturnSocketMsg(send.getReturnMsg());
	                        	
	                        	}else
	                        	{
	                        		re.setResult(false);
	                        		re.setErrMsg("���ӳ�ʱ");
	                        		re.setReturnSocketMsg("");
	                        	}
		                		if(socketMsgSetInfo.isIsLog()){
		        					SocketFacadeFactory.getLocalInstance(ctx).InsSocketLog(configInfo,  "���͵��ģ�"+sendMsg.getValue().toString(),send.getReturnMsg());
		        				}
		                		if (configInfo.isIsWrite()){
				            		SocketFacadeFactory.getLocalInstance(ctx).InsSocketLog(configInfo, "������Ϣ", sendMsg.getValue().toString());
				                }
		                		remoteClient.getSendMsgList().remove(send);
	        				}else
	        				{
	        					re.setResult(false);
                        		re.setErrMsg("ͨ��δ����");
                        		re.setReturnSocketMsg("");
	        				}
	                	}else
	                	{
	                		re.setResult(false);
                    		re.setErrMsg("���͵�������ʧ��");
                    		re.setReturnSocketMsg("");
	                	}
	        			
	        		}else
	        		{
	        			re.setResult(false);
                		re.setErrMsg("δ�ҵ����ýű�");
                		re.setReturnSocketMsg("");
	        		}
	        		
	            	
	        	}else
	        	{
	        		re.setResult(false);
            		re.setErrMsg("δ�ҵ����ýű�");
            		re.setReturnSocketMsg("");
	        	}
	
	        }  else
        	{
        		re.setResult(false);
        		re.setErrMsg("δ�ҵ����ýű�");
        		re.setReturnSocketMsg("");
        	}
  
    	}else
    	{
    		re.setResult(false);
    		re.setErrMsg("�������õ��Ų���Ϊ��");
    		re.setReturnSocketMsg("");
    	}

        return re;
    }
    
    /**
     * �����������Ի�ȡ����info
     * @param String ctx
     * @param String zzbm ��֯���� 
     * @param  String bs ���ϱ�ʶ�룬1λ�ֽڣ���5��ʶ��Ʒ��
     * @param String fl ���Ϸ����룬2λ�ֽڣ�09������ϴ�壬10������ϴ��ƽ�壬17�������ư壬18�������ƿ�ƽ�壬19�����п�壬20�����п��ƽ�塣
     * @param String ph �ƺ��룬4λ�ֽڣ���ϸ������MES���ƺ���ˮ��Ϊ׼��������MES���ƺ���ˮ�Ų���4λ��ǰ��0���㡣
     * @param String phmc �ƺ����ƣ���ϸ������MES���ƺ�����Ϊ׼���������ֽڳ��ȣ�
     * @param BigDecimal kd  ���
     * @param BigDecimal hd ���
     * @param String dwmc ��λ����
     * @param String dwzbm ��λ�����  ���Դ��� ,�����λ�ڲ�ͬ������ͬ���� ��Ҫ��
     * @param  boolean isBatchNo
     * @return MaterialInfo ���϶���
     */
    protected IObjectValue _getMaterialInfo(Context ctx, String zzbm, String bs, String fl, String ph, String phmc, BigDecimal kd, BigDecimal hd, String dwmc, String dwzbm, boolean isBatchNo) throws BOSException, EASBizException
     {    	
    	
//    	�������ϱ���淶
//    	���ϱ���������ϱ�ʶ�롢���Ϸ����롢�ƺ��롢������Ĳ��ֹ���14λ��ɣ��޼����־����ϸ˵�����£�
//    		��һ�Σ����ϱ�ʶ�룬1λ�ֽڣ���5��ʶ��Ʒ��
//    		�ڶ��Σ����Ϸ����룬2λ�ֽڣ�09������ϴ�壬10������ϴ��ƽ�壬17�������ư壬18�������ƿ�ƽ�壬19�����п�壬20�����п��ƽ�塣
//    		�����Σ��ƺ��룬4λ�ֽڣ���ϸ������MES���ƺ���ˮ��Ϊ׼��������MES���ƺ���ˮ�Ų���4λ��ǰ��0���㡣
//    		���ĶΣ�����룬7λ�ֽڣ������Ϻ��+��ȱ�ʶ���������Ϻ��3λ�����Ϻ��*100�������Ͽ��4λ��������Ӧλ����ǰ��0���㡣
//    		���磺50900010801200��������MES�ƺ���ˮ��Ϊ0001������ͺ�Ϊ0.80*1200����ϴ�塣
//    	�������������淶
//    		�������ư������ࡢ�ƺš���������֣��ԡ�_���������ϸ˵�����£�
//    	��һ�Σ��������ƣ�������ϴ�塢��ϴ��ƽ�塢���ư塢���ƿ�ƽ�塢��п�塢��п��ƽ�����࣬�������ֽڳ��ȣ�
//    	�ڶ��Σ��ƺ����ƣ���ϸ������MES���ƺ�����Ϊ׼���������ֽڳ��ȣ�
//    	�����Σ���������Ϻ��*��ȱ�ʶ���������Ϻ��3λ�����Ϻ��*100�������Ͽ��4λ��������Ӧλ����ǰ��0���㣬8λ�ֽڡ�
//    	���磺��ϴ��_C600QK-CM_0.80*1200�����ƺ�ΪC600QK-CM������ͺ�Ϊ0.80*1200����ϴ�塣
    	MaterialInfo info=null;
    	String strNumber="";
    	String strName="";
    	String strModel="";
    	String strWLFL=fl;
    	String weightUnitName="��";
    	String LengthUnitName="����";
    	String volumnUnitName="������";
    	if (bs.length()!=1){
    		throw new EASBizException(new NumericExceptionSubItem("003","���ϱ�ʶ���ȱ���Ϊ1"));
    	}else
    	{
    		strNumber=bs;
    	}
    	CompanyOrgUnitInfo companyInfo=PublicBaseUtil.getCompany(ctx, zzbm);
    	if (companyInfo==null){
    		throw new EASBizException(new NumericExceptionSubItem("003","��֯����Ϊ��"));
    	}
    	MaterialGroupInfo materialGroup=PublicBaseUtil.getMaterialGroupInfoByNumber(ctx, companyInfo.getId().toString(), fl);
		if (materialGroup==null){
			throw new EASBizException(new NumericExceptionSubItem("003","���Ϸ����׼����Ϊ��"));
		}else{
//			info.setMaterialGroup(materialGroup); //���Ϸ���
			strNumber=strNumber+fl;
			strName=strName+materialGroup.getName();
		}
		
//    	if (fl.length()!=2){
//    		throw new EASBizException(new NumericExceptionSubItem("003","���Ϸ��೤�ȱ���Ϊ2"));
//    	}else
//    	{
//    		strNumber=strNumber+fl;
////    		09	��ϴ���
////    		10	��ϴ��ƽ���
////    		17	���ƾ��
////    		18	���ƿ�ƽ���
////    		19	��п���
////    		20	��п��ƽ���
//    		if(fl.equals("09")){
//    			strName=strName+"��ϴ���";
//    		}else if (fl.equals("10")){
//    			strName=strName+"��ϴ��ƽ���";
//    		}else if (fl.equals("17")){
//    			strName=strName+"���ƾ��";
//    		}
//    		else if (fl.equals("18")){
//    			strName=strName+"���ƿ�ƽ���";
//    		}
//    		else if (fl.equals("19")){
//    			strName=strName+"��п���";
//    		}else if (fl.equals("20")){
//    			strName=strName+"��п��ƽ���";
//    		}else
//    		{
//    			throw new EASBizException(new NumericExceptionSubItem("003","���Ϸ�������EAS����ʶ��"));
//    		}
//    	}
    	if(ph.length()<=4 && ph.length()>0){
    		String phTemp= "00000"+ph;
    		strNumber=strNumber+phTemp.substring(phTemp.length()-4, phTemp.length());
    		if(phmc.length()<1){
    			throw new EASBizException(new NumericExceptionSubItem("003","�ƺ����Ʋ���Ϊ��"));
    		}else
    		{
    			strName=strName+"_"+phmc;
    		}
    	}else
    	{
    		throw new EASBizException(new NumericExceptionSubItem("003","�ƺų��ȱ���С�ڵ���4���Ҵ���0"));
    	}
    	
    	if(hd.compareTo(BigDecimal.ZERO)==1){
    		String hdTemp="00000"+(hd.setScale(2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
    		strNumber=strNumber+hdTemp.substring(hdTemp.length()-3, hdTemp.length());
    		strName=strName+"_"+hd.setScale(2,BigDecimal.ROUND_HALF_UP).toString()+"*";
    		strModel=strModel+hd.setScale(2,BigDecimal.ROUND_HALF_UP).toString()+"*";
    	}else
    	{
    		throw new EASBizException(new NumericExceptionSubItem("003","��ȱ������0"));
    	}
    	
    	if(kd.compareTo(BigDecimal.ZERO)==1){
    		String kdTemp="00000"+kd.setScale(0,BigDecimal.ROUND_HALF_UP).toString();
    		strNumber=strNumber+kdTemp.substring(kdTemp.length()-4, kdTemp.length());
    		strName=strName+kdTemp.substring(kdTemp.length()-4, kdTemp.length());
    		strModel=strModel+kd.setScale(0,BigDecimal.ROUND_HALF_UP).toString();
    	}else
    	{
    		throw new EASBizException(new NumericExceptionSubItem("003","��ȱ������0"));
    	}
    	
    	
    	if(companyInfo!=null){
    	info=PublicBaseUtil.getMaterialInfoByNumber(ctx, companyInfo.getId().toString(),strNumber) ;
    	if (info==null){
    		info=new MaterialInfo();
//    		BOSUuid id=BOSUuid.create(info.getBOSType());
//    		info.setId(id);
    		info.setNumber(strNumber);
    		info.setName(strName);   
    		info.setModel(strModel);
    		info.setIsOutsourcedPart(true);
    		info.put("paihao", phmc);
    		CtrlUnitInfo cu=PublicBaseUtil.getCU(ctx, zzbm);
    		if(cu!=null){
	    		info.setAdminCU(cu);
	    		info.setCU(cu);
    		}else
    		{
    			throw new EASBizException(new NumericExceptionSubItem("003","��֯����Ϊ��"));
    		}
    		FullOrgUnitInfo fullOrg=PublicBaseUtil.getFullOrgUnitInfoByID(ctx, cu.getId().toString());
    		if(fullOrg!=null){
	    		
    		}else
    		{
    			throw new EASBizException(new NumericExceptionSubItem("003","��֯����Ϊ��"));
    		}
    		UserInfo user=PublicBaseUtil.getUserByNumber(ctx, "user");
    		info.setCreator(user);
    		//����ʱ��
        	try {
    			info.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
    		} catch (EASBizException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    		MeasureUnitInfo  unitInfo =PublicBaseUtil.getMeasureUnitInfoByName(ctx, dwzbm, dwmc);
    		info.setBaseUnit(unitInfo);
    		
    		MeasureUnitInfo  weightUnitInfo =PublicBaseUtil.getMeasureUnitInfoByName(ctx, "", weightUnitName);
    		if (weightUnitInfo==null){
    			throw new EASBizException(new NumericExceptionSubItem("003","������λ����Ϊ��"));
    		}else{
    			info.setWeightUnit(weightUnitInfo);
    		}
    		MeasureUnitInfo  lengthUnitInfo =PublicBaseUtil.getMeasureUnitInfoByName(ctx, "", LengthUnitName);
    		if (lengthUnitInfo==null){
    			throw new EASBizException(new NumericExceptionSubItem("003","���ȵ�λ����Ϊ��"));
    		}else{
    			info.setLengthUnit(lengthUnitInfo);
    		}
    		
    		MeasureUnitInfo  volumnUnitInfo =PublicBaseUtil.getMeasureUnitInfoByName(ctx, "", volumnUnitName);
    		if (volumnUnitInfo==null){
    			throw new EASBizException(new NumericExceptionSubItem("003","�����λ����Ϊ��"));
    		}else
    		{
    			info.setVolumnUnit(volumnUnitInfo);
    		}
//    		
//    		MaterialGroupInfo materialGroup=PublicBaseUtil.getMaterialGroupInfoByNumber(ctx, companyInfo.getId().toString(), strWLFL);
//    		if (materialGroup==null){
//    			throw new EASBizException(new NumericExceptionSubItem("003","���Ϸ����׼����Ϊ��"));
//    		}else{
    			info.setMaterialGroup(materialGroup); //���Ϸ���
//    			
//    		}
    		info.setPricePrecision(6); //���۾���
    		info.setWidth(kd);
    		info.setHeight(hd);
    		info.setShortName(strName);
    		info.setLongNumber(strNumber);
    		info.setVersion(1);
    		info.setEffectedStatus(2);
    		info.setEquipProperty(EquipmentPropertyEnum.DEFAULT);
    		info.setIsSynochronous(1);
    		info.setIsGoods(false);
    		info.setStatus(UsedStatusEnum.UNAPPROVE);
    		info.setUseAsstAttrRelation(false);
    		MaterialGroupDetialCollection groupCols=new MaterialGroupDetialCollection();
    		MaterialGroupDetialInfo groupInfo=new MaterialGroupDetialInfo();
    		groupInfo.setMaterial(info);
    		MaterialGroupStandardInfo standinfo=PublicBaseUtil.getMaterialGroupStandardInfoByNumber(ctx, companyInfo.getId().toString(), "BaseGroupStandard");
    		if(standinfo!=null){
    			groupInfo.setMaterialGroupStandard(standinfo);
    		}else
    		{
    			throw new EASBizException(new NumericExceptionSubItem("003","���ϻ������಻��Ϊ��"));
    		}
    		groupInfo.setMaterialGroup(materialGroup);
    		groupCols.add(groupInfo);
//    		
//    		MaterialGroupDetialInfo groupInfo2=new MaterialGroupDetialInfo();
//    		groupInfo2.setMaterial(info);
//    		MaterialGroupStandardInfo standinfo2=PublicBaseUtil.getMaterialGroupStandardInfoByNumber(ctx, companyInfo.getId().toString(), "NetOrderStandardType");
//    		groupInfo2.setMaterialGroupStandard(standinfo2);
//    		groupCols.add(groupInfo2);
//    		info.getMaterialGroupDetails().addCollection(groupCols);
//    		MaterialControllerBean
//    		DataBaseDInfo dataBaseDInfo = (DataBaseDInfo) info;
//    		CompanyOrgUnitInfo company = ContextUtil.getCurrentFIUnit(ctx);
    		IObjectPK pk=MaterialFactory.getLocalInstance(ctx).submit(info);
    		
    		if(pk!=null){
    				MaterialInfo tempIno=MaterialFactory.getLocalInstance(ctx).getMaterialInfo(pk);
    				if(tempIno!=null){
    					
    				  //������Ϣ
//    					MaterialGroupDetialInfo groupDetail=new MaterialGroupDetialInfo();
//    					groupDetail.setMaterial(tempIno);
//    					groupDetail.setMaterialGroup(materialGroup);
//    					groupDetail.setMaterialGroupStandard(standinfo);
//    		                  MaterialGroupDetialInfo mgdInfo = new MaterialGroupDetialInfo();  
//    		                  mgdInfo.setMaterial(newMaterial);  
//    		                  mgdInfo.setMaterialGroup(mgInfo);  
//    		                  mgdInfo.setMaterialGroupStandard(mgsInfo);  
//    		                  IObjectPK mgdID = MaterialGroupDetialFactory.getRemoteInstance().  
		                    StringBuilder addSDetailInfo  = new StringBuilder("/*ditalect*/ insert into T_BD_MaterialGroupDetial(FID,FMaterialID,FMaterialGroupStandardID,FMaterialGroupID ) values(newbosid('B9B5052C'),'");  
		                    addSDetailInfo.append(tempIno.getId().toString());  
		                    addSDetailInfo.append("','");  
		                    addSDetailInfo.append(standinfo.getId().toString());  
		                    addSDetailInfo.append("','");  
		                    addSDetailInfo.append(materialGroup.getId().toString());  
		                    addSDetailInfo.append("')");  
		                    DbUtil.execute(ctx, addSDetailInfo.toString());
		                   
 
    					 
						
						
						
    					//������֯
	    				MaterialCompanyInfoInfo materialCompany=new MaterialCompanyInfoInfo();
	    				materialCompany.setMateial(info);
	    				materialCompany.setCompany(companyInfo);
	    				materialCompany.setCreator(user);
	    				//����ʱ��
	    	        	try {
	    	        		materialCompany.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
	    	    		} catch (EASBizException e) {
	    	    			// TODO Auto-generated catch block
	    	    			e.printStackTrace();
	    	    		}
	    				materialCompany.setCU(cu);
	    				materialCompany.setEffectedStatus(2);
	    				materialCompany.setAccountType(AccountType.MOVE_WEIGHTED_AVERAGE);
	    				materialCompany.setCalculateType(CalculateTypeEnum.SELFMANUFACTURE);
	    				materialCompany.setStandardCost(BigDecimal.ZERO);
	    				materialCompany.setStatus(UsedStatusEnum.UNAPPROVE);
	    				IObjectPK pkCompany=MaterialCompanyInfoFactory.getLocalInstance(ctx).submit(materialCompany);
//	    				MaterialCompanyInfoInfo tempmaterialCompany=MaterialCompanyInfoFactory.getLocalInstance(ctx).getMaterialCompanyInfoInfo(pkCompany);
//	    				if(tempmaterialCompany!=null){
//	    					
////							if(tempmaterialCompany.getStatus().equals(UsedStatusEnum.UNAPPROVE)) {
////								MaterialCompanyInfoFactory.getLocalInstance(ctx).submit(tempIno);
////							}
////							tempmaterialCompany=MaterialCompanyInfoFactory.getLocalInstance(ctx).getMaterialCompanyInfoInfo(pkCompany);
//							if(tempmaterialCompany.getStatus().equals(UsedStatusEnum.UNAPPROVE)) {
//								MaterialCompanyInfoFactory.getLocalInstance(ctx).approve(pk);
//							}
//	    				}
	    				
	    				//�ɱ�����
	    				MaterialCostInfo materialCost=new MaterialCostInfo();
	    				materialCost.setMaterial(tempIno);
	    				materialCost.setCreator(user);
	    				materialCost.setCU(cu);
	    				materialCost.setOrgUnit(fullOrg);
	    				materialCost.setStatus(UsedStatusEnum.UNAPPROVE);
	    				materialCost.setEffectedStatus(2);
	    				materialCost.setStoreType(StoreType.SOURCE_MATERIAL);
	    				materialCost.setIsParticipateReduct(false);
	    				//����ʱ��
	    	        	try {
	    	        		materialCost.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
	    	    		} catch (EASBizException e) {
	    	    			// TODO Auto-generated catch block
	    	    			e.printStackTrace();
	    	    		}
	    	    		IObjectPK pkCost= MaterialCostFactory.getLocalInstance(ctx).submit(materialCost);
	    				
	    				//�ɹ���Ϣ
	    				MaterialPurchasingInfo materialPur=new MaterialPurchasingInfo();
	    				materialPur.setMaterial(tempIno);
	    				materialPur.setOrgUnit(fullOrg);
	    				materialPur.setCreator(user);
	    				
	    				//����ʱ��
	    	        	try {
	    	        		materialPur.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
	    	    		} catch (EASBizException e) {
	    	    			// TODO Auto-generated catch block
	    	    			e.printStackTrace();
	    	    		}
	    	    		materialPur.setCU(cu);
	    	    		materialPur.setEffectedStatus(2);
	    	    		materialPur.setStatus(UsedStatusEnum.UNAPPROVE);
	    	    		materialPur.setUnit(unitInfo);
	    	    		QuotaPolicyInfo  quoInfo=PublicBaseUtil.getQuotaPolicyInfoByNumber(ctx, "005");
	    	    		if (quoInfo!=null){
	    	    			materialPur.setQuotaPolicy(quoInfo);
	    	    		}
	    	    		materialPur.setQuotaPeriod(QuotaPeriodEnum.season);
	    	    		materialPur.setIsReturn(true);
	    	    		IObjectPK pkPur= MaterialPurchasingFactory.getLocalInstance(ctx).submit(materialPur);
//	    	    		MaterialPurchasingInfo tempPur=MaterialPurchasingFactory.getLocalInstance(ctx).getMaterialPurchasingInfo(pkPur);
//	    				if(tempPur!=null){
//	    					
////							if(tempPur.getStatus().equals(UsedStatusEnum.UNAPPROVE)) {
////								MaterialPurchasingFactory.getLocalInstance(ctx).submit(tempIno);
////							}
////							tempPur=MaterialPurchasingFactory.getLocalInstance(ctx).getMaterialPurchasingInfo(pkPur);
//							if(tempPur.getStatus().equals(UsedStatusEnum.UNAPPROVE)) {
//								MaterialPurchasingFactory.getLocalInstance(ctx).approve(pk);
//							}
//	    				}
	    	    		//������Ϣ
	    	    		
	    				MaterialSalesInfo materialSale=new MaterialSalesInfo();
	    				materialSale.setMaterial(tempIno);
	    				materialSale.setOrgUnit(fullOrg);
	    				materialSale.setCreator(user);
	    				materialSale.setIsReturn(true);
	    				//����ʱ��
	    	        	try {
	    	        		materialSale.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
	    	    		} catch (EASBizException e) {
	    	    			// TODO Auto-generated catch block
	    	    			e.printStackTrace();
	    	    		}
	    	    		materialSale.setCU(cu);
	    	    		materialSale.setEffectedStatus(2);
	    	    		materialSale.setStatus(UsedStatusEnum.UNAPPROVE);
	    	    		materialSale.setUnit(unitInfo);
	    	    		IObjectPK pkSale= MaterialSalesFactory.getLocalInstance(ctx).submit(materialSale);
//	    	    		MaterialSalesInfo tempSale=MaterialSalesFactory.getLocalInstance(ctx).getMaterialSalesInfo(pkSale);
//	    				if(tempSale!=null){
//	    					
////							if(tempSale.getStatus().equals(UsedStatusEnum.UNAPPROVE)) {
////								MaterialSalesFactory.getLocalInstance(ctx).submit(tempIno);
////							}
////							tempSale=MaterialSalesFactory.getLocalInstance(ctx).getMaterialSalesInfo(pkSale);
//							if(tempSale.getStatus().equals(UsedStatusEnum.UNAPPROVE)) {
//								MaterialSalesFactory.getLocalInstance(ctx).approve(pk);
//							}
//	    				}
	    				
	    				//�������
	    	    		
	    				MaterialInventoryInfo materialInv=new MaterialInventoryInfo();
	    				materialInv.setIsControl(true);
	    				materialInv.setMaterial(tempIno);
	    				materialInv.setOrgUnit(fullOrg);
	    				materialInv.setCreator(user);
	    				materialInv.setDaysBottom(0);
	    				materialInv.setDaysTop(0);
	    				materialInv.setDaysTurnover(0);
	    				materialInv.setIsNegative(false);
	    				materialInv.setIsBatchNo(false);
	    				materialInv.setIsSequenceNo(false);
	    				materialInv.setIsBarcode(false);
	    				materialInv.setAbcType(ABCEnum.NOTSELECT);
	    				materialInv.setIsCompages(false);
	    				materialInv.setIssuePriorityMode(IssuePriorityEnum.NOTSELECT);
	    				materialInv.setIsPeriodValid(false);
	    				materialInv.setPeriodValid(1);
	    				materialInv.setPeriodValidUnit(TimeUnitEnum.DAY);
	    				materialInv.setInWarehsAhead(1);
	    				materialInv.setOutWarehsAhead(1);
	    				materialInv.setPrepWarnAhead(1);
	    				materialInv.setAheadUnit(TimeUnitEnum.DAY);
	    				materialInv.setReservationDate(0);
	    				
	    				//����ʱ��
	    	        	try {
	    	        		materialInv.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
	    	    		} catch (EASBizException e) {
	    	    			// TODO Auto-generated catch block
	    	    			e.printStackTrace();
	    	    		}
	    	    		materialInv.setCU(cu);
	    	    		materialInv.setEffectedStatus(2);
	    	    		materialInv.setStatus(UsedStatusEnum.UNAPPROVE);
	    	    		materialInv.setUnit(unitInfo);
	    	    		materialInv.setIsLotNumber(isBatchNo);
	    	    		IObjectPK pkInv= MaterialInventoryFactory.getLocalInstance(ctx).submit(materialInv);
//	    	    		MaterialInventoryInfo tempInv=MaterialInventoryFactory.getLocalInstance(ctx).getMaterialInventoryInfo(pkInv);
//	    				if(tempInv!=null){
//	    					
////							if(tempInv.getStatus().equals(UsedStatusEnum.UNAPPROVE)) {
////								MaterialInventoryFactory.getLocalInstance(ctx).submit(tempIno);
////							}
////							tempInv=MaterialInventoryFactory.getLocalInstance(ctx).getMaterialInventoryInfo(pkInv);
//							if(tempInv.getStatus().equals(UsedStatusEnum.UNAPPROVE)) {
//								MaterialInventoryFactory.getLocalInstance(ctx).approve(pk);
//							}
//	    				}
	    				
//	    	    		if(tempIno.getStatus().equals(UsedStatusEnum.UNAPPROVE)) {
//							MaterialFactory.getLocalInstance(ctx).submit(tempIno);
//						}
						tempIno=MaterialFactory.getLocalInstance(ctx).getMaterialInfo(pk);
						if(tempIno.getStatus().equals(UsedStatusEnum.UNAPPROVE)) {
							MaterialFactory.getLocalInstance(ctx).approve(pk);
						}
					
    				}
				}
    	
    		
    	}
    	}else
    	{
    		throw new EASBizException(new NumericExceptionSubItem("003","��˾δ�ҵ�"));
    	}
    	
        return info;
    }
}