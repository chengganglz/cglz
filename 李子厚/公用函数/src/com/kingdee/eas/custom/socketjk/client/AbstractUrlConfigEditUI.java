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
public abstract class AbstractUrlConfigEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractUrlConfigEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFICompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contip;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contport;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conturlType;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkisUseHeartbeatMsg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHeartbeatMsgTime;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkisWrite;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conttimeOut;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsUse;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReTranTimes;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCharset;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contheadLength;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSimpleName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtFICompany;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtip;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtport;
    protected com.kingdee.bos.ctrl.swing.KDComboBox urlType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtHeartbeatMsgTime;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txttimeOut;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtReTranTimes;
    protected com.kingdee.bos.ctrl.swing.KDComboBox Charset;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtheadLength;
    protected com.kingdee.eas.custom.socketjk.UrlConfigInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractUrlConfigEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractUrlConfigEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFICompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contip = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contport = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conturlType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkisUseHeartbeatMsg = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contHeartbeatMsgTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkisWrite = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.conttimeOut = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsUse = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contReTranTimes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCharset = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contheadLength = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtSimpleName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.prmtFICompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtip = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtport = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.urlType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtHeartbeatMsgTime = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txttimeOut = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtReTranTimes = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.Charset = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtheadLength = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.contFICompany.setName("contFICompany");
        this.contip.setName("contip");
        this.contport.setName("contport");
        this.conturlType.setName("conturlType");
        this.chkisUseHeartbeatMsg.setName("chkisUseHeartbeatMsg");
        this.contHeartbeatMsgTime.setName("contHeartbeatMsgTime");
        this.chkisWrite.setName("chkisWrite");
        this.conttimeOut.setName("conttimeOut");
        this.chkIsUse.setName("chkIsUse");
        this.contReTranTimes.setName("contReTranTimes");
        this.contCharset.setName("contCharset");
        this.contheadLength.setName("contheadLength");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.txtSimpleName.setName("txtSimpleName");
        this.txtDescription.setName("txtDescription");
        this.prmtFICompany.setName("prmtFICompany");
        this.txtip.setName("txtip");
        this.txtport.setName("txtport");
        this.urlType.setName("urlType");
        this.txtHeartbeatMsgTime.setName("txtHeartbeatMsgTime");
        this.txttimeOut.setName("txttimeOut");
        this.txtReTranTimes.setName("txtReTranTimes");
        this.Charset.setName("Charset");
        this.txtheadLength.setName("txtheadLength");
        // CoreUI		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelAlignment(7);		
        this.kDLabelContainer4.setVisible(true);
        // contFICompany		
        this.contFICompany.setBoundLabelText(resHelper.getString("contFICompany.boundLabelText"));		
        this.contFICompany.setBoundLabelLength(100);		
        this.contFICompany.setBoundLabelUnderline(true);		
        this.contFICompany.setVisible(true);
        // contip		
        this.contip.setBoundLabelText(resHelper.getString("contip.boundLabelText"));		
        this.contip.setBoundLabelLength(100);		
        this.contip.setBoundLabelUnderline(true);		
        this.contip.setVisible(true);
        // contport		
        this.contport.setBoundLabelText(resHelper.getString("contport.boundLabelText"));		
        this.contport.setBoundLabelLength(100);		
        this.contport.setBoundLabelUnderline(true);		
        this.contport.setVisible(true);
        // conturlType		
        this.conturlType.setBoundLabelText(resHelper.getString("conturlType.boundLabelText"));		
        this.conturlType.setBoundLabelLength(100);		
        this.conturlType.setBoundLabelUnderline(true);		
        this.conturlType.setVisible(true);
        // chkisUseHeartbeatMsg		
        this.chkisUseHeartbeatMsg.setText(resHelper.getString("chkisUseHeartbeatMsg.text"));		
        this.chkisUseHeartbeatMsg.setVisible(true);		
        this.chkisUseHeartbeatMsg.setHorizontalAlignment(2);
        // contHeartbeatMsgTime		
        this.contHeartbeatMsgTime.setBoundLabelText(resHelper.getString("contHeartbeatMsgTime.boundLabelText"));		
        this.contHeartbeatMsgTime.setBoundLabelLength(100);		
        this.contHeartbeatMsgTime.setBoundLabelUnderline(true);		
        this.contHeartbeatMsgTime.setVisible(true);
        // chkisWrite		
        this.chkisWrite.setText(resHelper.getString("chkisWrite.text"));		
        this.chkisWrite.setVisible(true);		
        this.chkisWrite.setHorizontalAlignment(2);
        // conttimeOut		
        this.conttimeOut.setBoundLabelText(resHelper.getString("conttimeOut.boundLabelText"));		
        this.conttimeOut.setBoundLabelLength(100);		
        this.conttimeOut.setBoundLabelUnderline(true);		
        this.conttimeOut.setVisible(true);
        // chkIsUse		
        this.chkIsUse.setText(resHelper.getString("chkIsUse.text"));		
        this.chkIsUse.setVisible(true);		
        this.chkIsUse.setHorizontalAlignment(2);
        // contReTranTimes		
        this.contReTranTimes.setBoundLabelText(resHelper.getString("contReTranTimes.boundLabelText"));		
        this.contReTranTimes.setBoundLabelLength(100);		
        this.contReTranTimes.setBoundLabelUnderline(true);		
        this.contReTranTimes.setVisible(true);
        // contCharset		
        this.contCharset.setBoundLabelText(resHelper.getString("contCharset.boundLabelText"));		
        this.contCharset.setBoundLabelLength(100);		
        this.contCharset.setBoundLabelUnderline(true);		
        this.contCharset.setVisible(true);
        // contheadLength		
        this.contheadLength.setBoundLabelText(resHelper.getString("contheadLength.boundLabelText"));		
        this.contheadLength.setBoundLabelLength(100);		
        this.contheadLength.setBoundLabelUnderline(true);		
        this.contheadLength.setVisible(true);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // txtName
        // txtSimpleName		
        this.txtSimpleName.setMaxLength(80);
        // txtDescription
        // prmtFICompany		
        this.prmtFICompany.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery4AsstAcct");		
        this.prmtFICompany.setVisible(true);		
        this.prmtFICompany.setEditable(true);		
        this.prmtFICompany.setDisplayFormat("$name$");		
        this.prmtFICompany.setEditFormat("$number$");		
        this.prmtFICompany.setCommitFormat("$number$");		
        this.prmtFICompany.setRequired(true);
        		setOrgF7(prmtFICompany,com.kingdee.eas.basedata.org.OrgType.getEnum("Company"));
					
        // txtip		
        this.txtip.setVisible(true);		
        this.txtip.setHorizontalAlignment(2);		
        this.txtip.setMaxLength(100);		
        this.txtip.setRequired(false);
        // txtport		
        this.txtport.setVisible(true);		
        this.txtport.setHorizontalAlignment(2);		
        this.txtport.setDataType(0);		
        this.txtport.setSupportedEmpty(true);		
        this.txtport.setRequired(false);
        // urlType		
        this.urlType.setVisible(true);		
        this.urlType.addItems(EnumUtils.getEnumList("com.kingdee.eas.custom.socketjk.UrlType").toArray());		
        this.urlType.setRequired(false);
        // txtHeartbeatMsgTime		
        this.txtHeartbeatMsgTime.setVisible(true);		
        this.txtHeartbeatMsgTime.setHorizontalAlignment(2);		
        this.txtHeartbeatMsgTime.setDataType(0);		
        this.txtHeartbeatMsgTime.setSupportedEmpty(true);		
        this.txtHeartbeatMsgTime.setRequired(false);
        // txttimeOut		
        this.txttimeOut.setVisible(true);		
        this.txttimeOut.setHorizontalAlignment(2);		
        this.txttimeOut.setDataType(0);		
        this.txttimeOut.setSupportedEmpty(true);		
        this.txttimeOut.setRequired(false);
        // txtReTranTimes		
        this.txtReTranTimes.setVisible(true);		
        this.txtReTranTimes.setHorizontalAlignment(2);		
        this.txtReTranTimes.setDataType(0);		
        this.txtReTranTimes.setSupportedEmpty(true);		
        this.txtReTranTimes.setRequired(false);
        // Charset		
        this.Charset.setVisible(true);		
        this.Charset.addItems(EnumUtils.getEnumList("com.kingdee.eas.custom.socketjk.CharsetEnum").toArray());		
        this.Charset.setRequired(false);
        // txtheadLength		
        this.txtheadLength.setVisible(true);		
        this.txtheadLength.setHorizontalAlignment(2);		
        this.txtheadLength.setDataType(0);		
        this.txtheadLength.setSupportedEmpty(true);		
        this.txtheadLength.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtip,txtport,urlType,chkisUseHeartbeatMsg,txtHeartbeatMsgTime,txttimeOut,txtReTranTimes,Charset,txtheadLength}));
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
        kDLabelContainer1.setBounds(new Rectangle(81, 45, 270, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(404, 45, 270, 19));
        this.add(kDLabelContainer2, null);
        kDLabelContainer3.setBounds(new Rectangle(81, 109, 270, 19));
        this.add(kDLabelContainer3, null);
        kDLabelContainer4.setBounds(new Rectangle(404, 109, 270, 19));
        this.add(kDLabelContainer4, null);
        contFICompany.setBounds(new Rectangle(81, 436, 270, 19));
        this.add(contFICompany, null);
        contip.setBounds(new Rectangle(81, 172, 270, 19));
        this.add(contip, null);
        contport.setBounds(new Rectangle(404, 172, 270, 19));
        this.add(contport, null);
        conturlType.setBounds(new Rectangle(404, 235, 270, 19));
        this.add(conturlType, null);
        chkisUseHeartbeatMsg.setBounds(new Rectangle(81, 298, 270, 19));
        this.add(chkisUseHeartbeatMsg, null);
        contHeartbeatMsgTime.setBounds(new Rectangle(404, 298, 270, 19));
        this.add(contHeartbeatMsgTime, null);
        chkisWrite.setBounds(new Rectangle(81, 235, 270, 19));
        this.add(chkisWrite, null);
        conttimeOut.setBounds(new Rectangle(81, 346, 270, 19));
        this.add(conttimeOut, null);
        chkIsUse.setBounds(new Rectangle(408, 346, 270, 19));
        this.add(chkIsUse, null);
        contReTranTimes.setBounds(new Rectangle(80, 386, 270, 19));
        this.add(contReTranTimes, null);
        contCharset.setBounds(new Rectangle(407, 386, 270, 19));
        this.add(contCharset, null);
        contheadLength.setBounds(new Rectangle(406, 432, 270, 19));
        this.add(contheadLength, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtName);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtSimpleName);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtDescription);
        //contFICompany
        contFICompany.setBoundEditor(prmtFICompany);
        //contip
        contip.setBoundEditor(txtip);
        //contport
        contport.setBoundEditor(txtport);
        //conturlType
        conturlType.setBoundEditor(urlType);
        //contHeartbeatMsgTime
        contHeartbeatMsgTime.setBoundEditor(txtHeartbeatMsgTime);
        //conttimeOut
        conttimeOut.setBoundEditor(txttimeOut);
        //contReTranTimes
        contReTranTimes.setBoundEditor(txtReTranTimes);
        //contCharset
        contCharset.setBoundEditor(Charset);
        //contheadLength
        contheadLength.setBoundEditor(txtheadLength);

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
        this.menuBar.add(menuTool);
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
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnReset);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
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
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isUseHeartbeatMsg", boolean.class, this.chkisUseHeartbeatMsg, "selected");
		dataBinder.registerBinding("isWrite", boolean.class, this.chkisWrite, "selected");
		dataBinder.registerBinding("IsUse", boolean.class, this.chkIsUse, "selected");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("simpleName", String.class, this.txtSimpleName, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "_multiLangItem");
		dataBinder.registerBinding("FICompany", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.prmtFICompany, "data");
		dataBinder.registerBinding("ip", String.class, this.txtip, "text");
		dataBinder.registerBinding("port", int.class, this.txtport, "value");
		dataBinder.registerBinding("urlType", com.kingdee.eas.custom.socketjk.UrlType.class, this.urlType, "selectedItem");
		dataBinder.registerBinding("HeartbeatMsgTime", int.class, this.txtHeartbeatMsgTime, "value");
		dataBinder.registerBinding("timeOut", int.class, this.txttimeOut, "value");
		dataBinder.registerBinding("ReTranTimes", int.class, this.txtReTranTimes, "value");
		dataBinder.registerBinding("Charset", com.kingdee.eas.custom.socketjk.CharsetEnum.class, this.Charset, "selectedItem");
		dataBinder.registerBinding("headLength", int.class, this.txtheadLength, "value");		
	}
	//Regiester UI State
	private void registerUIState(){
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtDescription, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtNumber, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtSimpleName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtDescription, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtNumber, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtSimpleName, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtName, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtDescription, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtNumber, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtSimpleName, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.custom.socketjk.app.UrlConfigEditUIHandler";
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
        this.txtip.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.custom.socketjk.UrlConfigInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"Company",editData.getString("number"));
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
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Company");
		}

	protected KDBizPromptBox getMainBizOrg() {
		return prmtFICompany;
}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("Company");
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
		getValidateHelper().registerBindProperty("isUseHeartbeatMsg", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isWrite", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("IsUse", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("simpleName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("FICompany", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ip", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("port", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("urlType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("HeartbeatMsgTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("timeOut", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ReTranTimes", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Charset", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("headLength", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
		            this.txtName.setEnabled(true);
		            this.txtDescription.setEnabled(true);
		            this.txtNumber.setEnabled(true);
		            this.txtSimpleName.setEnabled(true);
        } else if (STATUS_EDIT.equals(this.oprtState)) {
		            this.txtName.setEnabled(true);
		            this.txtDescription.setEnabled(true);
		            this.txtNumber.setEnabled(true);
		            this.txtSimpleName.setEnabled(true);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
		            this.txtName.setEnabled(false);
		            this.txtDescription.setEnabled(false);
		            this.txtNumber.setEnabled(false);
		            this.txtSimpleName.setEnabled(false);
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
        sic.add(new SelectorItemInfo("isUseHeartbeatMsg"));
        sic.add(new SelectorItemInfo("isWrite"));
        sic.add(new SelectorItemInfo("IsUse"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("simpleName"));
        sic.add(new SelectorItemInfo("description"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("FICompany.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("FICompany.id"));
        	sic.add(new SelectorItemInfo("FICompany.number"));
        	sic.add(new SelectorItemInfo("FICompany.name"));
		}
        sic.add(new SelectorItemInfo("ip"));
        sic.add(new SelectorItemInfo("port"));
        sic.add(new SelectorItemInfo("urlType"));
        sic.add(new SelectorItemInfo("HeartbeatMsgTime"));
        sic.add(new SelectorItemInfo("timeOut"));
        sic.add(new SelectorItemInfo("ReTranTimes"));
        sic.add(new SelectorItemInfo("Charset"));
        sic.add(new SelectorItemInfo("headLength"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.custom.socketjk.client", "UrlConfigEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.custom.socketjk.client.UrlConfigEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.socketjk.UrlConfigFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.custom.socketjk.UrlConfigInfo objectValue = new com.kingdee.eas.custom.socketjk.UrlConfigInfo();
				if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("Company")) != null && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("Company")).getBoolean("isBizUnit"))
			objectValue.put("FICompany",com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum("Company")));
 
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
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
		vo.put("urlType","Client");
vo.put("Charset","UTF-8");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}