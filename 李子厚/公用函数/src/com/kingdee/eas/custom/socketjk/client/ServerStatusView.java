/**
 * output package name
 */
package com.kingdee.eas.custom.socketjk.client;


import io.netty.buffer.Unpooled;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.CharsetUtil;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.custom.socketjk.PublicUtil;
import com.kingdee.eas.custom.socketjk.RemoteSocketClient;
import com.kingdee.eas.custom.socketjk.SocketFacadeFactory;
import com.kingdee.eas.custom.socketjk.SocketServerStatusCollection;
import com.kingdee.eas.custom.socketjk.SocketServerStatusInfo;
import com.kingdee.eas.custom.socketjk.testFacadeFactory;


/**
 * output class name
 */
public class ServerStatusView extends AbstractServerStatusView
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4533849516887997355L;
	private static final Logger logger = CoreUIObject.getLogger(ServerStatusView.class);
    private Timer timer;
    /**
     * output class constructor
     */
    public ServerStatusView() throws Exception
    {
        super();
      
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionToolBarCustom_actionPerformed
     */
    public void actionToolBarCustom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToolBarCustom_actionPerformed(e);
    }

    /**
     * output actionCloudFeed_actionPerformed
     */
    public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudFeed_actionPerformed(e);
    }

    /**
     * output actionCloudShare_actionPerformed
     */
    public void actionCloudShare_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudShare_actionPerformed(e);
    }

    /**
     * output actionCloudScreen_actionPerformed
     */
    public void actionCloudScreen_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudScreen_actionPerformed(e);
    }

    /**
     * output actionXunTongFeed_actionPerformed
     */
    public void actionXunTongFeed_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionXunTongFeed_actionPerformed(e);
    }

    /**
     * output startServer_actionPerformed method
     */
    public void startServer_actionPerformed(ActionEvent e) throws Exception
    {
    	SocketFacadeFactory.getRemoteInstance().RunService();
    	ShowServerStatus();
//    	new LocalSocketServer(8888).start(); // 启动
    	
//    	this.kDTable1.addHeadRow();
//    	for(int i=0; i<100;i++){
//    		this.kDTable1.addRow();
//    	}
         
    }
    	

    /**
     * output stopServer_actionPerformed method
     */
    public void stopServer_actionPerformed(ActionEvent e) throws Exception
    {
    	SocketFacadeFactory.getRemoteInstance().StopService();
    	ShowServerStatus();
    }
    	

    /**
     * output clientTest_actionPerformed method
     */
    public void clientTest_actionPerformed(ActionEvent e) throws Exception
    {
    	if(PublicUtil.RemoteClientList.size()>0){
    		RemoteSocketClient a=PublicUtil.RemoteClientList.get(0);
    		if (a!=null){
    			SocketChannel  b=a.getRomoteSocketChannel();
//    			b.writeAndFlush("服务端我又来了");
    			  b.writeAndFlush(Unpooled.copiedBuffer("服务端我又来了", CharsetUtil.UTF_8));
    		}
    		
    	}
//    	SocketFacadeFactory.getRemoteInstance().executeScript();

    }
   
    /**
     * output connectServer_actionPerformed method
     */
    public void connectServer_actionPerformed(ActionEvent e) throws Exception
    {
    	
    }
    public void onLoad()
    throws Exception
  {
    	super.onLoad();
    	PublicUtil.addColumnToKDTable(this.kDTable1, "CompanyID", "组织ID", 0,false);
    	PublicUtil.addColumnToKDTable(this.kDTable1, "CompanyName", "组织", 20,true);
    	PublicUtil.addColumnToKDTable(this.kDTable1, "ServerType", "服务类型", 20,true);
    	PublicUtil.addColumnToKDTable(this.kDTable1, "ServerName", "服务名", 20,true);
    	PublicUtil.addColumnToKDTable(this.kDTable1, "TimeOut", "超时时间(ms)", 20,true);
    	PublicUtil.addColumnToKDTable(this.kDTable1, "IsUseHeartbeatMsg", "是否使用心跳电文", 20,true);
    	PublicUtil.addColumnToKDTable(this.kDTable1, "HeartbeatMsgTime", "心跳电文间隔(ms)", 20,true);
    	PublicUtil.addColumnToKDTable(this.kDTable1, "IsWirte", "是否输出实时日志", 20,true);
    	PublicUtil.addColumnToKDTable(this.kDTable1, "LocalIP", "本地IP", 20,true);
    	PublicUtil.addColumnToKDTable(this.kDTable1, "LocalPort", "本地端口号", 20,true);
    	PublicUtil.addColumnToKDTable(this.kDTable1, "ServerStatus", "服务状态", 20,true);
    	PublicUtil.addColumnToKDTable(this.kDTable1, "RemoteIP", "远程IP", 20,true);
    	PublicUtil.addColumnToKDTable(this.kDTable1, "RemotePort", "远程端口号", 20,true);
    	PublicUtil.addColumnToKDTable(this.kDTable1, "ChannelStatus", "通道状态", 20,true);
    	ShowServerStatus();
    	
    	timer = new Timer();
    	timer.schedule(new TimerTask() {
    	        public void run() {
    	        	ShowServerStatus();
    	        }
    	}, 2000 , 5000);
    	
  }
    public void  ShowServerStatus()
    {
    	
//    	this.kDScrollPane1.add(comp);
    	this.kDTable1.removeRows();
    	KDTable kt=this.kDTable1;
    	try {
		SocketServerStatusCollection statusCols=	SocketFacadeFactory.getRemoteInstance().getServerStatusList();
		for(int i=0;i<statusCols.size();i++){
			SocketServerStatusInfo info=statusCols.get(i);
			IRow row=this.kDTable1.addRow();
			row.getCell("CompanyID").setValue(info.getCompanyID());
			row.getCell("CompanyName").setValue(info.getCompanyName());
			row.getCell("ServerType").setValue(info.getServerType());
			row.getCell("ServerName").setValue(info.getServerName());
			row.getCell("TimeOut").setValue(info.getTimeOut());
			row.getCell("IsUseHeartbeatMsg").setValue(info.getIsUseHeartbeatMsg());
			row.getCell("HeartbeatMsgTime").setValue(info.getHeartbeatMsgTime());
			row.getCell("IsWirte").setValue(info.getIsWirte());
			row.getCell("LocalIP").setValue(info.getLocalIP());
			row.getCell("LocalPort").setValue(info.getLocalPort());
			row.getCell("ServerStatus").setValue(info.getServerStatus());
			if(info.getServerStatus().equals("停止")){
				row.getStyleAttributes().setBackground(Color.yellow);
			}
			if(info.getServerStatus().equals("启动")){
				row.getStyleAttributes().setBackground(Color.green);
			}
			row.getCell("RemoteIP").setValue(info.getRemoteIP());
			row.getCell("RemotePort").setValue(info.getRemotePort());
			row.getCell("ChannelStatus").setValue(info.getChannelStatus());
			if(info.getChannelStatus().equals("失效")){
				row.getStyleAttributes().setBackground(Color.gray);
			}
		}
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.kDTable1=kt;
		String[]  columns={"CompanyID","CompanyName","ServerType","ServerName","TimeOut","IsUseHeartbeatMsg","HeartbeatMsgTime","IsWirte","LocalIP","LocalPort","ServerStatus"};
		PublicUtil.setMergeColumn(this.kDTable1, columns);

    	
    }
    
    /**
     * output refresh_actionPerformed method
     */
    public void refresh_actionPerformed(ActionEvent e) throws Exception
    {
    	
    	testFacadeFactory.getRemoteInstance().CreateBill("");
//    	MaterialInfo  material= SocketFacadeFactory.getRemoteInstance().getMaterialInfo("01", "1", "09", "1233", "测试牌号", new BigDecimal("2000"), new BigDecimal("0.9"), "吨", "",true);
    	
//    	ShowServerStatus();
    }
    
    
    public boolean useScrollPane() {

    	return true;
    	}

    	public int getVerticalScrollPolicy() {
    	return 20;
    	}


    	public int getHorizontalScrollPolicy() {
    	return 30;
    	}

}