/**
 * output package name
 */
package com.kingdee.eas.custom.socketjk.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractSocketServerStatusEditUI extends com.kingdee.eas.framework.client.CoreBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSocketServerStatusEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompanyID;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompanyName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contServerType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contServerName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTimeOut;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsUseHeartbeatMsg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHeartbeatMsgTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIsWirte;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLocalIP;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLocalPort;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contServerStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRemoteIP;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChannelStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRemotePort;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDateLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompanyID;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompanyName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtServerType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtServerName;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTimeOut;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtIsUseHeartbeatMsg;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtHeartbeatMsgTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtIsWirte;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLocalIP;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLocalPort;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtServerStatus;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRemoteIP;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtChannelStatus;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtRemotePort;
    protected com.kingdee.eas.custom.socketjk.SocketServerStatusInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractSocketServerStatusEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractSocketServerStatusEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrint
        actionPrint.setEnabled(true);
        actionPrint.setDaemonRun(false);

        actionPrint.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
        _tempStr = resHelper.getString("ActionPrint.SHORT_DESCRIPTION");
        actionPrint.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.LONG_DESCRIPTION");
        actionPrint.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrint.NAME");
        actionPrint.putValue(ItemAction.NAME, _tempStr);
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrint.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        //actionPrintPreview
        actionPrintPreview.setEnabled(true);
        actionPrintPreview.setDaemonRun(false);

        actionPrintPreview.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl P"));
        _tempStr = resHelper.getString("ActionPrintPreview.SHORT_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.LONG_DESCRIPTION");
        actionPrintPreview.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionPrintPreview.NAME");
        actionPrintPreview.putValue(ItemAction.NAME, _tempStr);
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionPrintPreview.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCompanyID = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCompanyName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contServerType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contServerName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTimeOut = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIsUseHeartbeatMsg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHeartbeatMsgTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIsWirte = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLocalIP = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLocalPort = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contServerStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRemoteIP = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChannelStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRemotePort = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDDateLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtCompanyID = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtCompanyName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtServerType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtServerName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTimeOut = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtIsUseHeartbeatMsg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtHeartbeatMsgTime = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtIsWirte = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLocalIP = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLocalPort = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtServerStatus = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRemoteIP = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtChannelStatus = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRemotePort = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contNumber.setName("contNumber");
        this.contBizDate.setName("contBizDate");
        this.contDescription.setName("contDescription");
        this.contAuditor.setName("contAuditor");
        this.contCompanyID.setName("contCompanyID");
        this.contCompanyName.setName("contCompanyName");
        this.contServerType.setName("contServerType");
        this.contServerName.setName("contServerName");
        this.contTimeOut.setName("contTimeOut");
        this.contIsUseHeartbeatMsg.setName("contIsUseHeartbeatMsg");
        this.contHeartbeatMsgTime.setName("contHeartbeatMsgTime");
        this.contIsWirte.setName("contIsWirte");
        this.contLocalIP.setName("contLocalIP");
        this.contLocalPort.setName("contLocalPort");
        this.contServerStatus.setName("contServerStatus");
        this.contRemoteIP.setName("contRemoteIP");
        this.contChannelStatus.setName("contChannelStatus");
        this.contRemotePort.setName("contRemotePort");
        this.prmtCreator.setName("prmtCreator");
        this.kDDateCreateTime.setName("kDDateCreateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.kDDateLastUpdateTime.setName("kDDateLastUpdateTime");
        this.txtNumber.setName("txtNumber");
        this.pkBizDate.setName("pkBizDate");
        this.txtDescription.setName("txtDescription");
        this.prmtAuditor.setName("prmtAuditor");
        this.txtCompanyID.setName("txtCompanyID");
        this.txtCompanyName.setName("txtCompanyName");
        this.txtServerType.setName("txtServerType");
        this.txtServerName.setName("txtServerName");
        this.txtTimeOut.setName("txtTimeOut");
        this.txtIsUseHeartbeatMsg.setName("txtIsUseHeartbeatMsg");
        this.txtHeartbeatMsgTime.setName("txtHeartbeatMsgTime");
        this.txtIsWirte.setName("txtIsWirte");
        this.txtLocalIP.setName("txtLocalIP");
        this.txtLocalPort.setName("txtLocalPort");
        this.txtServerStatus.setName("txtServerStatus");
        this.txtRemoteIP.setName("txtRemoteIP");
        this.txtChannelStatus.setName("txtChannelStatus");
        this.txtRemotePort.setName("txtRemotePort");
        // CoreUI		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.btnCreateTo.setVisible(true);		
        this.btnAddLine.setVisible(false);		
        this.btnCopyLine.setVisible(false);		
        this.btnInsertLine.setVisible(false);		
        this.btnRemoveLine.setVisible(false);		
        this.btnAuditResult.setVisible(false);		
        this.separator1.setVisible(false);		
        this.menuItemCreateTo.setVisible(true);		
        this.separator3.setVisible(false);		
        this.menuItemTraceUp.setVisible(false);		
        this.menuItemTraceDown.setVisible(false);		
        this.menuTable1.setVisible(false);		
        this.menuItemAddLine.setVisible(false);		
        this.menuItemCopyLine.setVisible(false);		
        this.menuItemInsertLine.setVisible(false);		
        this.menuItemRemoveLine.setVisible(false);		
        this.menuItemViewSubmitProccess.setVisible(false);		
        this.menuItemViewDoProccess.setVisible(false);		
        this.menuItemAuditResult.setVisible(false);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);		
        this.contLastUpdateUser.setEnabled(false);		
        this.contLastUpdateUser.setVisible(false);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);		
        this.contLastUpdateTime.setEnabled(false);		
        this.contLastUpdateTime.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);		
        this.contBizDate.setBoundLabelAlignment(7);		
        this.contBizDate.setVisible(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contCompanyID		
        this.contCompanyID.setBoundLabelText(resHelper.getString("contCompanyID.boundLabelText"));		
        this.contCompanyID.setBoundLabelLength(100);		
        this.contCompanyID.setBoundLabelUnderline(true);		
        this.contCompanyID.setVisible(true);
        // contCompanyName		
        this.contCompanyName.setBoundLabelText(resHelper.getString("contCompanyName.boundLabelText"));		
        this.contCompanyName.setBoundLabelLength(100);		
        this.contCompanyName.setBoundLabelUnderline(true);		
        this.contCompanyName.setVisible(true);
        // contServerType		
        this.contServerType.setBoundLabelText(resHelper.getString("contServerType.boundLabelText"));		
        this.contServerType.setBoundLabelLength(100);		
        this.contServerType.setBoundLabelUnderline(true);		
        this.contServerType.setVisible(true);
        // contServerName		
        this.contServerName.setBoundLabelText(resHelper.getString("contServerName.boundLabelText"));		
        this.contServerName.setBoundLabelLength(100);		
        this.contServerName.setBoundLabelUnderline(true);		
        this.contServerName.setVisible(true);
        // contTimeOut		
        this.contTimeOut.setBoundLabelText(resHelper.getString("contTimeOut.boundLabelText"));		
        this.contTimeOut.setBoundLabelLength(100);		
        this.contTimeOut.setBoundLabelUnderline(true);		
        this.contTimeOut.setVisible(true);
        // contIsUseHeartbeatMsg		
        this.contIsUseHeartbeatMsg.setBoundLabelText(resHelper.getString("contIsUseHeartbeatMsg.boundLabelText"));		
        this.contIsUseHeartbeatMsg.setBoundLabelLength(100);		
        this.contIsUseHeartbeatMsg.setBoundLabelUnderline(true);		
        this.contIsUseHeartbeatMsg.setVisible(true);
        // contHeartbeatMsgTime		
        this.contHeartbeatMsgTime.setBoundLabelText(resHelper.getString("contHeartbeatMsgTime.boundLabelText"));		
        this.contHeartbeatMsgTime.setBoundLabelLength(100);		
        this.contHeartbeatMsgTime.setBoundLabelUnderline(true);		
        this.contHeartbeatMsgTime.setVisible(true);
        // contIsWirte		
        this.contIsWirte.setBoundLabelText(resHelper.getString("contIsWirte.boundLabelText"));		
        this.contIsWirte.setBoundLabelLength(100);		
        this.contIsWirte.setBoundLabelUnderline(true);		
        this.contIsWirte.setVisible(true);
        // contLocalIP		
        this.contLocalIP.setBoundLabelText(resHelper.getString("contLocalIP.boundLabelText"));		
        this.contLocalIP.setBoundLabelLength(100);		
        this.contLocalIP.setBoundLabelUnderline(true);		
        this.contLocalIP.setVisible(true);
        // contLocalPort		
        this.contLocalPort.setBoundLabelText(resHelper.getString("contLocalPort.boundLabelText"));		
        this.contLocalPort.setBoundLabelLength(100);		
        this.contLocalPort.setBoundLabelUnderline(true);		
        this.contLocalPort.setVisible(true);
        // contServerStatus		
        this.contServerStatus.setBoundLabelText(resHelper.getString("contServerStatus.boundLabelText"));		
        this.contServerStatus.setBoundLabelLength(100);		
        this.contServerStatus.setBoundLabelUnderline(true);		
        this.contServerStatus.setVisible(true);
        // contRemoteIP		
        this.contRemoteIP.setBoundLabelText(resHelper.getString("contRemoteIP.boundLabelText"));		
        this.contRemoteIP.setBoundLabelLength(100);		
        this.contRemoteIP.setBoundLabelUnderline(true);		
        this.contRemoteIP.setVisible(true);
        // contChannelStatus		
        this.contChannelStatus.setBoundLabelText(resHelper.getString("contChannelStatus.boundLabelText"));		
        this.contChannelStatus.setBoundLabelLength(100);		
        this.contChannelStatus.setBoundLabelUnderline(true);		
        this.contChannelStatus.setVisible(true);
        // contRemotePort		
        this.contRemotePort.setBoundLabelText(resHelper.getString("contRemotePort.boundLabelText"));		
        this.contRemotePort.setBoundLabelLength(100);		
        this.contRemotePort.setBoundLabelUnderline(true);		
        this.contRemotePort.setVisible(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // kDDateCreateTime		
        this.kDDateCreateTime.setTimeEnabled(true);		
        this.kDDateCreateTime.setEnabled(false);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);
        // kDDateLastUpdateTime		
        this.kDDateLastUpdateTime.setTimeEnabled(true);		
        this.kDDateLastUpdateTime.setEnabled(false);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // pkBizDate		
        this.pkBizDate.setVisible(true);		
        this.pkBizDate.setEnabled(true);
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // txtCompanyID		
        this.txtCompanyID.setVisible(true);		
        this.txtCompanyID.setHorizontalAlignment(2);		
        this.txtCompanyID.setMaxLength(100);		
        this.txtCompanyID.setRequired(false);
        // txtCompanyName		
        this.txtCompanyName.setVisible(true);		
        this.txtCompanyName.setHorizontalAlignment(2);		
        this.txtCompanyName.setMaxLength(200);		
        this.txtCompanyName.setRequired(false);
        // txtServerType		
        this.txtServerType.setVisible(true);		
        this.txtServerType.setHorizontalAlignment(2);		
        this.txtServerType.setMaxLength(100);		
        this.txtServerType.setRequired(false);
        // txtServerName		
        this.txtServerName.setVisible(true);		
        this.txtServerName.setHorizontalAlignment(2);		
        this.txtServerName.setMaxLength(100);		
        this.txtServerName.setRequired(false);
        // txtTimeOut		
        this.txtTimeOut.setVisible(true);		
        this.txtTimeOut.setHorizontalAlignment(2);		
        this.txtTimeOut.setDataType(0);		
        this.txtTimeOut.setSupportedEmpty(true);		
        this.txtTimeOut.setRequired(false);
        // txtIsUseHeartbeatMsg		
        this.txtIsUseHeartbeatMsg.setVisible(true);		
        this.txtIsUseHeartbeatMsg.setHorizontalAlignment(2);		
        this.txtIsUseHeartbeatMsg.setMaxLength(100);		
        this.txtIsUseHeartbeatMsg.setRequired(false);
        // txtHeartbeatMsgTime		
        this.txtHeartbeatMsgTime.setVisible(true);		
        this.txtHeartbeatMsgTime.setHorizontalAlignment(2);		
        this.txtHeartbeatMsgTime.setDataType(0);		
        this.txtHeartbeatMsgTime.setSupportedEmpty(true);		
        this.txtHeartbeatMsgTime.setRequired(false);
        // txtIsWirte		
        this.txtIsWirte.setVisible(true);		
        this.txtIsWirte.setHorizontalAlignment(2);		
        this.txtIsWirte.setMaxLength(100);		
        this.txtIsWirte.setRequired(false);
        // txtLocalIP		
        this.txtLocalIP.setVisible(true);		
        this.txtLocalIP.setHorizontalAlignment(2);		
        this.txtLocalIP.setMaxLength(100);		
        this.txtLocalIP.setRequired(false);
        // txtLocalPort		
        this.txtLocalPort.setVisible(true);		
        this.txtLocalPort.setHorizontalAlignment(2);		
        this.txtLocalPort.setDataType(0);		
        this.txtLocalPort.setSupportedEmpty(true);		
        this.txtLocalPort.setRequired(false);
        // txtServerStatus		
        this.txtServerStatus.setVisible(true);		
        this.txtServerStatus.setHorizontalAlignment(2);		
        this.txtServerStatus.setMaxLength(100);		
        this.txtServerStatus.setRequired(false);
        // txtRemoteIP		
        this.txtRemoteIP.setVisible(true);		
        this.txtRemoteIP.setHorizontalAlignment(2);		
        this.txtRemoteIP.setMaxLength(100);		
        this.txtRemoteIP.setRequired(false);
        // txtChannelStatus		
        this.txtChannelStatus.setVisible(true);		
        this.txtChannelStatus.setHorizontalAlignment(2);		
        this.txtChannelStatus.setMaxLength(100);		
        this.txtChannelStatus.setRequired(false);
        // txtRemotePort		
        this.txtRemotePort.setVisible(true);		
        this.txtRemotePort.setHorizontalAlignment(2);		
        this.txtRemotePort.setDataType(0);		
        this.txtRemotePort.setSupportedEmpty(true);		
        this.txtRemotePort.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtCompanyID,txtCompanyName,txtServerType,txtServerName,txtTimeOut,txtIsUseHeartbeatMsg,txtHeartbeatMsgTime,txtIsWirte,txtLocalIP,txtLocalPort,txtServerStatus,txtRemoteIP,txtChannelStatus,txtRemotePort}));
        this.setFocusCycleRoot(true);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(0, 0, 1013, 629));
        this.setLayout(null);
        contCreator.setBounds(new Rectangle(440, 524, 270, 19));
        this.add(contCreator, null);
        contCreateTime.setBounds(new Rectangle(730, 524, 270, 19));
        this.add(contCreateTime, null);
        contLastUpdateUser.setBounds(new Rectangle(440, 555, 270, 19));
        this.add(contLastUpdateUser, null);
        contLastUpdateTime.setBounds(new Rectangle(730, 555, 270, 19));
        this.add(contLastUpdateTime, null);
        contNumber.setBounds(new Rectangle(728, 26, 270, 19));
        this.add(contNumber, null);
        contBizDate.setBounds(new Rectangle(700, 112, 270, 19));
        this.add(contBizDate, null);
        contDescription.setBounds(new Rectangle(728, 66, 270, 19));
        this.add(contDescription, null);
        contAuditor.setBounds(new Rectangle(15, 528, 270, 19));
        this.add(contAuditor, null);
        contCompanyID.setBounds(new Rectangle(301, 15, 270, 19));
        this.add(contCompanyID, null);
        contCompanyName.setBounds(new Rectangle(301, 38, 270, 19));
        this.add(contCompanyName, null);
        contServerType.setBounds(new Rectangle(301, 76, 270, 19));
        this.add(contServerType, null);
        contServerName.setBounds(new Rectangle(301, 114, 270, 19));
        this.add(contServerName, null);
        contTimeOut.setBounds(new Rectangle(301, 152, 270, 19));
        this.add(contTimeOut, null);
        contIsUseHeartbeatMsg.setBounds(new Rectangle(301, 190, 270, 19));
        this.add(contIsUseHeartbeatMsg, null);
        contHeartbeatMsgTime.setBounds(new Rectangle(301, 228, 270, 19));
        this.add(contHeartbeatMsgTime, null);
        contIsWirte.setBounds(new Rectangle(301, 266, 270, 19));
        this.add(contIsWirte, null);
        contLocalIP.setBounds(new Rectangle(301, 304, 270, 19));
        this.add(contLocalIP, null);
        contLocalPort.setBounds(new Rectangle(301, 342, 270, 19));
        this.add(contLocalPort, null);
        contServerStatus.setBounds(new Rectangle(301, 380, 270, 19));
        this.add(contServerStatus, null);
        contRemoteIP.setBounds(new Rectangle(301, 418, 270, 19));
        this.add(contRemoteIP, null);
        contChannelStatus.setBounds(new Rectangle(301, 496, 270, 19));
        this.add(contChannelStatus, null);
        contRemotePort.setBounds(new Rectangle(0, 0, 270, 19));
        this.add(contRemotePort, null);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(kDDateCreateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(kDDateLastUpdateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contCompanyID
        contCompanyID.setBoundEditor(txtCompanyID);
        //contCompanyName
        contCompanyName.setBoundEditor(txtCompanyName);
        //contServerType
        contServerType.setBoundEditor(txtServerType);
        //contServerName
        contServerName.setBoundEditor(txtServerName);
        //contTimeOut
        contTimeOut.setBoundEditor(txtTimeOut);
        //contIsUseHeartbeatMsg
        contIsUseHeartbeatMsg.setBoundEditor(txtIsUseHeartbeatMsg);
        //contHeartbeatMsgTime
        contHeartbeatMsgTime.setBoundEditor(txtHeartbeatMsgTime);
        //contIsWirte
        contIsWirte.setBoundEditor(txtIsWirte);
        //contLocalIP
        contLocalIP.setBoundEditor(txtLocalIP);
        //contLocalPort
        contLocalPort.setBoundEditor(txtLocalPort);
        //contServerStatus
        contServerStatus.setBoundEditor(txtServerStatus);
        //contRemoteIP
        contRemoteIP.setBoundEditor(txtRemoteIP);
        //contChannelStatus
        contChannelStatus.setBoundEditor(txtChannelStatus);
        //contRemotePort
        contRemotePort.setBoundEditor(txtRemotePort);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator6);
        menuFile.add(menuItemSendMail);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(MenuItemPCVoucher);
        menuBiz.add(menuItemDelPCVoucher);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnSave);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnPCVoucher);
        this.toolBar.add(btnDelPCVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.kDDateLastUpdateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("CompanyID", String.class, this.txtCompanyID, "text");
		dataBinder.registerBinding("CompanyName", String.class, this.txtCompanyName, "text");
		dataBinder.registerBinding("ServerType", String.class, this.txtServerType, "text");
		dataBinder.registerBinding("ServerName", String.class, this.txtServerName, "text");
		dataBinder.registerBinding("TimeOut", int.class, this.txtTimeOut, "value");
		dataBinder.registerBinding("IsUseHeartbeatMsg", String.class, this.txtIsUseHeartbeatMsg, "text");
		dataBinder.registerBinding("HeartbeatMsgTime", int.class, this.txtHeartbeatMsgTime, "value");
		dataBinder.registerBinding("IsWirte", String.class, this.txtIsWirte, "text");
		dataBinder.registerBinding("LocalIP", String.class, this.txtLocalIP, "text");
		dataBinder.registerBinding("LocalPort", int.class, this.txtLocalPort, "value");
		dataBinder.registerBinding("ServerStatus", String.class, this.txtServerStatus, "text");
		dataBinder.registerBinding("RemoteIP", String.class, this.txtRemoteIP, "text");
		dataBinder.registerBinding("ChannelStatus", String.class, this.txtChannelStatus, "text");
		dataBinder.registerBinding("RemotePort", int.class, this.txtRemotePort, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.custom.socketjk.app.SocketServerStatusEditUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }


    /**
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.txtCompanyID.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.custom.socketjk.SocketServerStatusInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"NONE",editData.getString("number"));
    }
    
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
            	com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("NONE");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer oufip = new com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
		}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CompanyID", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CompanyName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ServerType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ServerName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("TimeOut", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("IsUseHeartbeatMsg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("HeartbeatMsgTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("IsWirte", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LocalIP", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("LocalPort", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ServerStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("RemoteIP", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ChannelStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("RemotePort", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lastUpdateUser.id"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.number"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		}
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
        sic.add(new SelectorItemInfo("CompanyID"));
        sic.add(new SelectorItemInfo("CompanyName"));
        sic.add(new SelectorItemInfo("ServerType"));
        sic.add(new SelectorItemInfo("ServerName"));
        sic.add(new SelectorItemInfo("TimeOut"));
        sic.add(new SelectorItemInfo("IsUseHeartbeatMsg"));
        sic.add(new SelectorItemInfo("HeartbeatMsgTime"));
        sic.add(new SelectorItemInfo("IsWirte"));
        sic.add(new SelectorItemInfo("LocalIP"));
        sic.add(new SelectorItemInfo("LocalPort"));
        sic.add(new SelectorItemInfo("ServerStatus"));
        sic.add(new SelectorItemInfo("RemoteIP"));
        sic.add(new SelectorItemInfo("ChannelStatus"));
        sic.add(new SelectorItemInfo("RemotePort"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
    	if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionPrint(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrint(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrint() {
    	return false;
    }
	public RequestContext prepareActionPrintPreview(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionPrintPreview(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrintPreview() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.custom.socketjk.client", "SocketServerStatusEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.custom.socketjk.client.SocketServerStatusEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.socketjk.SocketServerStatusFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.custom.socketjk.SocketServerStatusInfo objectValue = new com.kingdee.eas.custom.socketjk.SocketServerStatusInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/custom/socketjk/SocketServerStatus";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.custom.socketjk.app.SocketServerStatusQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {        
        return null;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}