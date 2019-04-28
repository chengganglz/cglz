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
        	//获取本地服务列表
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
    			urlconfigs = UrlConfigFactory.getLocalInstance(ctx).getUrlConfigCollection(view); //获取url 参数配置结果集
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
	    	      //获取远程服务列表
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
	    				urlconfigs = UrlConfigFactory.getLocalInstance(ctx).getUrlConfigCollection(view); //获取url 参数配置结果集
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
    	//获取本地服务列表
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
			urlconfigs = UrlConfigFactory.getLocalInstance(ctx).getUrlConfigCollection(view); //获取url 参数配置结果集
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
		        									statusInfo.setIsUseHeartbeatMsg(isUseHeartbeatMsg==true?"是":"否");
		        									statusInfo.setHeartbeatMsgTime(heartbeatMsgTime);
		        									statusInfo.setIsWirte(isWirte==true?"是":"否");
		        									statusInfo.setLocalIP(strverIP);
		        									statusInfo.setLocalPort(port);
		        									statusInfo.setServerStatus("启动");
		        									statusInfo.setRemoteIP(Servchannel.getClientChannal().remoteAddress().getHostName());
		        									statusInfo.setRemotePort(Servchannel.getClientChannal().remoteAddress().getPort());
		        									if (Servchannel.getClientChannal().isActive()){
		        										statusInfo.setChannelStatus("激活");
		        									}else
		        									{
		        										statusInfo.setChannelStatus("失效");
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
        									statusInfo.setIsUseHeartbeatMsg(isUseHeartbeatMsg==true?"是":"否");
        									statusInfo.setHeartbeatMsgTime(heartbeatMsgTime);
        									statusInfo.setIsWirte(isWirte==true?"是":"否");
        									statusInfo.setLocalIP(strverIP);
        									statusInfo.setLocalPort(port);
        									statusInfo.setServerStatus("启动");
        									statusInfo.setRemoteIP("");
        									statusInfo.setRemotePort(0);
        									statusInfo.setChannelStatus("等待连接");
        									
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
						statusInfo.setIsUseHeartbeatMsg(isUseHeartbeatMsg==true?"是":"否");
						statusInfo.setHeartbeatMsgTime(heartbeatMsgTime);
						statusInfo.setIsWirte(isWirte==true?"是":"否");
						statusInfo.setLocalIP(strverIP);
						statusInfo.setLocalPort(port);
						statusInfo.setServerStatus("停止");
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
      //获取远程服务列表
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
			urlconfigs = UrlConfigFactory.getLocalInstance(ctx).getUrlConfigCollection(view); //获取url 参数配置结果集
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
	        										statusInfo.setIsUseHeartbeatMsg(isUseHeartbeatMsg==true?"是":"否");
	        										statusInfo.setHeartbeatMsgTime(heartbeatMsgTime);
	        										statusInfo.setIsWirte(isWirte==true?"是":"否");
	        										statusInfo.setLocalIP(channel.localAddress().getHostName());
	        										statusInfo.setLocalPort(channel.localAddress().getPort());
	        										statusInfo.setServerStatus("启动");
	        										statusInfo.setRemoteIP(channel.remoteAddress().getHostName());
	        										statusInfo.setRemotePort(channel.remoteAddress().getPort());
	        										if (channel.isActive()){
	        											statusInfo.setChannelStatus("激活");
		        									}else
		        									{
		        										statusInfo.setChannelStatus("失效");
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
        									statusInfo.setIsUseHeartbeatMsg(isUseHeartbeatMsg==true?"是":"否");
        									statusInfo.setHeartbeatMsgTime(heartbeatMsgTime);
        									statusInfo.setIsWirte(isWirte==true?"是":"否");
        									statusInfo.setLocalIP("");
        									statusInfo.setLocalPort(0);
        									statusInfo.setServerStatus("停止");
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
						statusInfo.setIsUseHeartbeatMsg(isUseHeartbeatMsg==true?"是":"否");
						statusInfo.setHeartbeatMsgTime(heartbeatMsgTime);
						statusInfo.setIsWirte(isWirte==true?"是":"否");
						statusInfo.setLocalIP("");
						statusInfo.setLocalPort(0);
						statusInfo.setServerStatus("停止");
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
     * 插入日志
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
 * 发送电文功能
 * @param socketMsgSetNo 电文配置单号
 * @param billId 单据编号
 */
    protected SocketResult _sendSocketMsg(Context ctx, String socketMsgSetNo, String billId) throws BOSException
    {    	
    	SocketResult re=new SocketResult();
    	re.setResult(false);
    	re.setErrMsg("未知错误");
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
                		re.setErrMsg("报文配置参数中通道未设置");
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
	        					SocketFacadeFactory.getLocalInstance(ctx).InsSocketLog(configInfo, "执行脚本错误",e.getMessage());
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
	        					SocketFacadeFactory.getLocalInstance(ctx).InsSocketLog(configInfo,  "执行脚本错误",e.getMessage());
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
	        					SocketFacadeFactory.getLocalInstance(ctx).InsSocketLog(configInfo,  "执行脚本错误","错误："+strErr);
	        					re.setResult(false);
	                    		re.setErrMsg("执行脚本错误："+strErr);
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
											SocketFacadeFactory.getLocalInstance(ctx).InsSocketLog(configInfo, "连接远程地址错误", e.getMessage());
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
	                        		re.setErrMsg("连接超时");
	                        		re.setReturnSocketMsg("");
	                        	}
		                		if(socketMsgSetInfo.isIsLog()){
		        					SocketFacadeFactory.getLocalInstance(ctx).InsSocketLog(configInfo,  "发送电文："+sendMsg.getValue().toString(),send.getReturnMsg());
		        				}
		                		if (configInfo.isIsWrite()){
				            		SocketFacadeFactory.getLocalInstance(ctx).InsSocketLog(configInfo, "发送消息", sendMsg.getValue().toString());
				                }
		                		remoteClient.getSendMsgList().remove(send);
	        				}else
	        				{
	        					re.setResult(false);
                        		re.setErrMsg("通道未连接");
                        		re.setReturnSocketMsg("");
	        				}
	                	}else
	                	{
	                		re.setResult(false);
                    		re.setErrMsg("发送电文生成失败");
                    		re.setReturnSocketMsg("");
	                	}
	        			
	        		}else
	        		{
	        			re.setResult(false);
                		re.setErrMsg("未找到配置脚本");
                		re.setReturnSocketMsg("");
	        		}
	        		
	            	
	        	}else
	        	{
	        		re.setResult(false);
            		re.setErrMsg("未找到配置脚本");
            		re.setReturnSocketMsg("");
	        	}
	
	        }  else
        	{
        		re.setResult(false);
        		re.setErrMsg("未找到配置脚本");
        		re.setReturnSocketMsg("");
        	}
  
    	}else
    	{
    		re.setResult(false);
    		re.setErrMsg("电文配置单号不能为空");
    		re.setReturnSocketMsg("");
    	}

        return re;
    }
    
    /**
     * 根据物料属性获取物料info
     * @param String ctx
     * @param String zzbm 组织编码 
     * @param  String bs 物料标识码，1位字节，以5标识成品。
     * @param String fl 物料分类码，2位字节，09代表酸洗板，10代表酸洗开平板，17代表轧制板，18代表轧制开平板，19代表镀锌板，20代表镀锌开平板。
     * @param String ph 牌号码，4位字节，详细以冷轧MES中牌号流水号为准，如冷轧MES中牌号流水号不足4位则前置0补足。
     * @param String phmc 牌号名称，详细以冷轧MES中牌号名称为准，不限制字节长度；
     * @param BigDecimal kd  宽度
     * @param BigDecimal hd 厚度
     * @param String dwmc 单位名称
     * @param String dwzbm 单位组编码  可以传空 ,如果单位在不同组有相同名称 需要传
     * @param  boolean isBatchNo
     * @return MaterialInfo 物料对象
     */
    protected IObjectValue _getMaterialInfo(Context ctx, String zzbm, String bs, String fl, String ph, String phmc, BigDecimal kd, BigDecimal hd, String dwmc, String dwzbm, boolean isBatchNo) throws BOSException, EASBizException
     {    	
    	
//    	二、物料编码规范
//    	物料编码包括物料标识码、物料分类码、牌号码、规格码四部分共计14位组成，无间隔标志，详细说明如下：
//    		第一段：物料标识码，1位字节，以5标识成品。
//    		第二段：物料分类码，2位字节，09代表酸洗板，10代表酸洗开平板，17代表轧制板，18代表轧制开平板，19代表镀锌板，20代表镀锌开平板。
//    		第三段：牌号码，4位字节，详细以冷轧MES中牌号流水号为准，如冷轧MES中牌号流水号不足4位则前置0补足。
//    		第四段：规格码，7位字节，以物料厚度+宽度标识，其中物料厚度3位（物料厚度*100），物料宽度4位，不足相应位数的前置0补足。
//    		例如：50900010801200代表冷轧MES牌号流水号为0001，规格型号为0.80*1200的酸洗板。
//    	三、物料命名规范
//    		物料名称包括分类、牌号、规格三部分，以“_”间隔，详细说明如下：
//    	第一段：分类名称，包括酸洗板、酸洗开平板、轧制板、轧制开平板、镀锌板、镀锌开平板六类，不限制字节长度；
//    	第二段：牌号名称，详细以冷轧MES中牌号名称为准，不限制字节长度；
//    	第三段：规格，以物料厚度*宽度标识，其中物料厚度3位（物料厚度*100），物料宽度4位，不足相应位数的前置0补足，8位字节。
//    	例如：酸洗板_C600QK-CM_0.80*1200代表牌号为C600QK-CM，规格型号为0.80*1200的酸洗板。
    	MaterialInfo info=null;
    	String strNumber="";
    	String strName="";
    	String strModel="";
    	String strWLFL=fl;
    	String weightUnitName="吨";
    	String LengthUnitName="毫米";
    	String volumnUnitName="立方米";
    	if (bs.length()!=1){
    		throw new EASBizException(new NumericExceptionSubItem("003","物料标识长度必须为1"));
    	}else
    	{
    		strNumber=bs;
    	}
    	CompanyOrgUnitInfo companyInfo=PublicBaseUtil.getCompany(ctx, zzbm);
    	if (companyInfo==null){
    		throw new EASBizException(new NumericExceptionSubItem("003","组织不能为空"));
    	}
    	MaterialGroupInfo materialGroup=PublicBaseUtil.getMaterialGroupInfoByNumber(ctx, companyInfo.getId().toString(), fl);
		if (materialGroup==null){
			throw new EASBizException(new NumericExceptionSubItem("003","物料分类标准不能为空"));
		}else{
//			info.setMaterialGroup(materialGroup); //物料分类
			strNumber=strNumber+fl;
			strName=strName+materialGroup.getName();
		}
		
//    	if (fl.length()!=2){
//    		throw new EASBizException(new NumericExceptionSubItem("003","物料分类长度必须为2"));
//    	}else
//    	{
//    		strNumber=strNumber+fl;
////    		09	酸洗卷板
////    		10	酸洗开平卷板
////    		17	轧制卷板
////    		18	轧制开平卷板
////    		19	镀锌卷板
////    		20	镀锌开平卷板
//    		if(fl.equals("09")){
//    			strName=strName+"酸洗卷板";
//    		}else if (fl.equals("10")){
//    			strName=strName+"酸洗开平卷板";
//    		}else if (fl.equals("17")){
//    			strName=strName+"轧制卷板";
//    		}
//    		else if (fl.equals("18")){
//    			strName=strName+"轧制开平卷板";
//    		}
//    		else if (fl.equals("19")){
//    			strName=strName+"镀锌卷板";
//    		}else if (fl.equals("20")){
//    			strName=strName+"镀锌开平卷板";
//    		}else
//    		{
//    			throw new EASBizException(new NumericExceptionSubItem("003","物料分类有误，EAS不能识别"));
//    		}
//    	}
    	if(ph.length()<=4 && ph.length()>0){
    		String phTemp= "00000"+ph;
    		strNumber=strNumber+phTemp.substring(phTemp.length()-4, phTemp.length());
    		if(phmc.length()<1){
    			throw new EASBizException(new NumericExceptionSubItem("003","牌号名称不能为空"));
    		}else
    		{
    			strName=strName+"_"+phmc;
    		}
    	}else
    	{
    		throw new EASBizException(new NumericExceptionSubItem("003","牌号长度必须小于等于4并且大于0"));
    	}
    	
    	if(hd.compareTo(BigDecimal.ZERO)==1){
    		String hdTemp="00000"+(hd.setScale(2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
    		strNumber=strNumber+hdTemp.substring(hdTemp.length()-3, hdTemp.length());
    		strName=strName+"_"+hd.setScale(2,BigDecimal.ROUND_HALF_UP).toString()+"*";
    		strModel=strModel+hd.setScale(2,BigDecimal.ROUND_HALF_UP).toString()+"*";
    	}else
    	{
    		throw new EASBizException(new NumericExceptionSubItem("003","厚度必须大于0"));
    	}
    	
    	if(kd.compareTo(BigDecimal.ZERO)==1){
    		String kdTemp="00000"+kd.setScale(0,BigDecimal.ROUND_HALF_UP).toString();
    		strNumber=strNumber+kdTemp.substring(kdTemp.length()-4, kdTemp.length());
    		strName=strName+kdTemp.substring(kdTemp.length()-4, kdTemp.length());
    		strModel=strModel+kd.setScale(0,BigDecimal.ROUND_HALF_UP).toString();
    	}else
    	{
    		throw new EASBizException(new NumericExceptionSubItem("003","宽度必须大于0"));
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
    			throw new EASBizException(new NumericExceptionSubItem("003","组织不能为空"));
    		}
    		FullOrgUnitInfo fullOrg=PublicBaseUtil.getFullOrgUnitInfoByID(ctx, cu.getId().toString());
    		if(fullOrg!=null){
	    		
    		}else
    		{
    			throw new EASBizException(new NumericExceptionSubItem("003","组织不能为空"));
    		}
    		UserInfo user=PublicBaseUtil.getUserByNumber(ctx, "user");
    		info.setCreator(user);
    		//创建时间
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
    			throw new EASBizException(new NumericExceptionSubItem("003","重量单位不能为空"));
    		}else{
    			info.setWeightUnit(weightUnitInfo);
    		}
    		MeasureUnitInfo  lengthUnitInfo =PublicBaseUtil.getMeasureUnitInfoByName(ctx, "", LengthUnitName);
    		if (lengthUnitInfo==null){
    			throw new EASBizException(new NumericExceptionSubItem("003","长度单位不能为空"));
    		}else{
    			info.setLengthUnit(lengthUnitInfo);
    		}
    		
    		MeasureUnitInfo  volumnUnitInfo =PublicBaseUtil.getMeasureUnitInfoByName(ctx, "", volumnUnitName);
    		if (volumnUnitInfo==null){
    			throw new EASBizException(new NumericExceptionSubItem("003","体积单位不能为空"));
    		}else
    		{
    			info.setVolumnUnit(volumnUnitInfo);
    		}
//    		
//    		MaterialGroupInfo materialGroup=PublicBaseUtil.getMaterialGroupInfoByNumber(ctx, companyInfo.getId().toString(), strWLFL);
//    		if (materialGroup==null){
//    			throw new EASBizException(new NumericExceptionSubItem("003","物料分类标准不能为空"));
//    		}else{
    			info.setMaterialGroup(materialGroup); //物料分类
//    			
//    		}
    		info.setPricePrecision(6); //单价精度
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
    			throw new EASBizException(new NumericExceptionSubItem("003","物料基本分类不能为空"));
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
    					
    				  //分组信息
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
		                   
 
    					 
						
						
						
    					//财务组织
	    				MaterialCompanyInfoInfo materialCompany=new MaterialCompanyInfoInfo();
	    				materialCompany.setMateial(info);
	    				materialCompany.setCompany(companyInfo);
	    				materialCompany.setCreator(user);
	    				//创建时间
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
	    				
	    				//成本资料
	    				MaterialCostInfo materialCost=new MaterialCostInfo();
	    				materialCost.setMaterial(tempIno);
	    				materialCost.setCreator(user);
	    				materialCost.setCU(cu);
	    				materialCost.setOrgUnit(fullOrg);
	    				materialCost.setStatus(UsedStatusEnum.UNAPPROVE);
	    				materialCost.setEffectedStatus(2);
	    				materialCost.setStoreType(StoreType.SOURCE_MATERIAL);
	    				materialCost.setIsParticipateReduct(false);
	    				//创建时间
	    	        	try {
	    	        		materialCost.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
	    	    		} catch (EASBizException e) {
	    	    			// TODO Auto-generated catch block
	    	    			e.printStackTrace();
	    	    		}
	    	    		IObjectPK pkCost= MaterialCostFactory.getLocalInstance(ctx).submit(materialCost);
	    				
	    				//采购信息
	    				MaterialPurchasingInfo materialPur=new MaterialPurchasingInfo();
	    				materialPur.setMaterial(tempIno);
	    				materialPur.setOrgUnit(fullOrg);
	    				materialPur.setCreator(user);
	    				
	    				//创建时间
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
	    	    		//销售信息
	    	    		
	    				MaterialSalesInfo materialSale=new MaterialSalesInfo();
	    				materialSale.setMaterial(tempIno);
	    				materialSale.setOrgUnit(fullOrg);
	    				materialSale.setCreator(user);
	    				materialSale.setIsReturn(true);
	    				//创建时间
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
	    				
	    				//库存属性
	    	    		
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
	    				
	    				//创建时间
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
    		throw new EASBizException(new NumericExceptionSubItem("003","公司未找到"));
    	}
    	
        return info;
    }
}