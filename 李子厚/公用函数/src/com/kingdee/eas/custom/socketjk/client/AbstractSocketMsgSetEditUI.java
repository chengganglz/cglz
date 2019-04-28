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
public abstract class AbstractSocketMsgSetEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSocketMsgSetEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDButton kDButtonSelectBiz;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEASBizType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEASBizTypeName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMsgType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChannel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contheadLength;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdataLength;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contendCharLength;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contendChar;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmsgNo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmsgNoSta;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmsgNoEnd;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsLog;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSimpleName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEASBizType;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtEASBizTypeName;
    protected com.kingdee.bos.ctrl.swing.KDComboBox MsgType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtChannel;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtheadLength;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtdataLength;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtendCharLength;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtendChar;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtmsgNo;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtmsgNoSta;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtmsgNoEnd;
    protected com.kingdee.eas.custom.socketjk.SocketMsgSetInfo editData = null;
    protected SelectEASBiz selectEASBiz = null;
    /**
     * output class constructor
     */
    public AbstractSocketMsgSetEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSocketMsgSetEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //selectEASBiz
        this.selectEASBiz = new SelectEASBiz(this);
        getActionManager().registerAction("selectEASBiz", selectEASBiz);
         this.selectEASBiz.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDButtonSelectBiz = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contEASBizType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEASBizTypeName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMsgType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChannel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contheadLength = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contdataLength = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contendCharLength = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contendChar = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmsgNo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmsgNoSta = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmsgNoEnd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsLog = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtSimpleName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtEASBizType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtEASBizTypeName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.MsgType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtChannel = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtheadLength = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtdataLength = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtendCharLength = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtendChar = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtmsgNo = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtmsgNoSta = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtmsgNoEnd = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDPanel1.setName("kDPanel1");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDButtonSelectBiz.setName("kDButtonSelectBiz");
        this.contEASBizType.setName("contEASBizType");
        this.contEASBizTypeName.setName("contEASBizTypeName");
        this.contMsgType.setName("contMsgType");
        this.contChannel.setName("contChannel");
        this.contheadLength.setName("contheadLength");
        this.contdataLength.setName("contdataLength");
        this.contendCharLength.setName("contendCharLength");
        this.contendChar.setName("contendChar");
        this.contmsgNo.setName("contmsgNo");
        this.contmsgNoSta.setName("contmsgNoSta");
        this.contmsgNoEnd.setName("contmsgNoEnd");
        this.chkIsLog.setName("chkIsLog");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.txtSimpleName.setName("txtSimpleName");
        this.txtDescription.setName("txtDescription");
        this.txtEASBizType.setName("txtEASBizType");
        this.txtEASBizTypeName.setName("txtEASBizTypeName");
        this.MsgType.setName("MsgType");
        this.prmtChannel.setName("prmtChannel");
        this.txtheadLength.setName("txtheadLength");
        this.txtdataLength.setName("txtdataLength");
        this.txtendCharLength.setName("txtendCharLength");
        this.txtendChar.setName("txtendChar");
        this.txtmsgNo.setName("txtmsgNo");
        this.txtmsgNoSta.setName("txtmsgNoSta");
        this.txtmsgNoEnd.setName("txtmsgNoEnd");
        // CoreUI		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);
        // kDTabbedPane1
        // kDPanel1
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
        // kDButtonSelectBiz
        this.kDButtonSelectBiz.setAction((IItemAction)ActionProxyFactory.getProxy(selectEASBiz, new Class[] { IItemAction.class }, getServiceContext()));		
        this.kDButtonSelectBiz.setText(resHelper.getString("kDButtonSelectBiz.text"));		
        this.kDButtonSelectBiz.setVisible(false);
        // contEASBizType		
        this.contEASBizType.setBoundLabelText(resHelper.getString("contEASBizType.boundLabelText"));		
        this.contEASBizType.setBoundLabelLength(100);		
        this.contEASBizType.setBoundLabelUnderline(true);		
        this.contEASBizType.setVisible(false);
        // contEASBizTypeName		
        this.contEASBizTypeName.setBoundLabelText(resHelper.getString("contEASBizTypeName.boundLabelText"));		
        this.contEASBizTypeName.setBoundLabelLength(100);		
        this.contEASBizTypeName.setBoundLabelUnderline(true);		
        this.contEASBizTypeName.setVisible(false);
        // contMsgType		
        this.contMsgType.setBoundLabelText(resHelper.getString("contMsgType.boundLabelText"));		
        this.contMsgType.setBoundLabelLength(100);		
        this.contMsgType.setBoundLabelUnderline(true);		
        this.contMsgType.setVisible(true);
        // contChannel		
        this.contChannel.setBoundLabelText(resHelper.getString("contChannel.boundLabelText"));		
        this.contChannel.setBoundLabelLength(100);		
        this.contChannel.setBoundLabelUnderline(true);		
        this.contChannel.setVisible(true);
        // contheadLength		
        this.contheadLength.setBoundLabelText(resHelper.getString("contheadLength.boundLabelText"));		
        this.contheadLength.setBoundLabelLength(100);		
        this.contheadLength.setBoundLabelUnderline(true);		
        this.contheadLength.setVisible(false);
        // contdataLength		
        this.contdataLength.setBoundLabelText(resHelper.getString("contdataLength.boundLabelText"));		
        this.contdataLength.setBoundLabelLength(100);		
        this.contdataLength.setBoundLabelUnderline(true);		
        this.contdataLength.setVisible(false);
        // contendCharLength		
        this.contendCharLength.setBoundLabelText(resHelper.getString("contendCharLength.boundLabelText"));		
        this.contendCharLength.setBoundLabelLength(100);		
        this.contendCharLength.setBoundLabelUnderline(true);		
        this.contendCharLength.setVisible(false);
        // contendChar		
        this.contendChar.setBoundLabelText(resHelper.getString("contendChar.boundLabelText"));		
        this.contendChar.setBoundLabelLength(100);		
        this.contendChar.setBoundLabelUnderline(true);		
        this.contendChar.setVisible(false);
        // contmsgNo		
        this.contmsgNo.setBoundLabelText(resHelper.getString("contmsgNo.boundLabelText"));		
        this.contmsgNo.setBoundLabelLength(100);		
        this.contmsgNo.setBoundLabelUnderline(true);		
        this.contmsgNo.setVisible(true);
        // contmsgNoSta		
        this.contmsgNoSta.setBoundLabelText(resHelper.getString("contmsgNoSta.boundLabelText"));		
        this.contmsgNoSta.setBoundLabelLength(100);		
        this.contmsgNoSta.setBoundLabelUnderline(true);		
        this.contmsgNoSta.setVisible(true);
        // contmsgNoEnd		
        this.contmsgNoEnd.setBoundLabelText(resHelper.getString("contmsgNoEnd.boundLabelText"));		
        this.contmsgNoEnd.setBoundLabelLength(100);		
        this.contmsgNoEnd.setBoundLabelUnderline(true);		
        this.contmsgNoEnd.setVisible(true);
        // chkIsLog		
        this.chkIsLog.setText(resHelper.getString("chkIsLog.text"));		
        this.chkIsLog.setVisible(true);		
        this.chkIsLog.setHorizontalAlignment(2);
        // txtNumber		
        this.txtNumber.setMaxLength(80);
        // txtName		
        this.txtName.setRequired(true);
        // txtSimpleName		
        this.txtSimpleName.setMaxLength(80);
        // txtDescription
        // txtEASBizType		
        this.txtEASBizType.setVisible(false);		
        this.txtEASBizType.setHorizontalAlignment(2);		
        this.txtEASBizType.setMaxLength(255);		
        this.txtEASBizType.setRequired(false);		
        this.txtEASBizType.setEnabled(false);
        // txtEASBizTypeName		
        this.txtEASBizTypeName.setVisible(false);		
        this.txtEASBizTypeName.setHorizontalAlignment(2);		
        this.txtEASBizTypeName.setMaxLength(255);		
        this.txtEASBizTypeName.setRequired(false);		
        this.txtEASBizTypeName.setEnabled(false);
        // MsgType		
        this.MsgType.setVisible(true);		
        this.MsgType.addItems(EnumUtils.getEnumList("com.kingdee.eas.custom.socketjk.SocketMsgType").toArray());		
        this.MsgType.setRequired(true);
        // prmtChannel		
        this.prmtChannel.setQueryInfo("com.kingdee.eas.custom.socketjk.app.UrlConfigQuery");		
        this.prmtChannel.setVisible(true);		
        this.prmtChannel.setEditable(true);		
        this.prmtChannel.setDisplayFormat("$name$");		
        this.prmtChannel.setEditFormat("$number$");		
        this.prmtChannel.setCommitFormat("$number$");		
        this.prmtChannel.setRequired(true);
        		EntityViewInfo eviprmtChannel = new EntityViewInfo ();
		eviprmtChannel.setFilter(com.kingdee.eas.framework.FrameWorkUtils.getF7FilterInfoByAuthorizedOrg(com.kingdee.eas.basedata.org.OrgType.getEnum("Company"),"FICompany.id"));
		prmtChannel.setEntityViewInfo(eviprmtChannel);
					
        		prmtChannel.addSelectorListener(new SelectorListener() {
			com.kingdee.eas.custom.socketjk.client.UrlConfigListUI prmtChannel_F7ListUI = null;
			public void willShow(SelectorEvent e) {
				if (prmtChannel_F7ListUI == null) {
					try {
						prmtChannel_F7ListUI = new com.kingdee.eas.custom.socketjk.client.UrlConfigListUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap ctx = new HashMap();
					ctx.put("bizUIOwner",javax.swing.SwingUtilities.getWindowAncestor(prmtChannel_F7ListUI));
					prmtChannel_F7ListUI.setF7Use(true,ctx);
					prmtChannel.setSelector(prmtChannel_F7ListUI);
				}
			}
		});
					
        // txtheadLength		
        this.txtheadLength.setVisible(false);		
        this.txtheadLength.setHorizontalAlignment(2);		
        this.txtheadLength.setDataType(0);		
        this.txtheadLength.setSupportedEmpty(true);		
        this.txtheadLength.setRequired(false);
        // txtdataLength		
        this.txtdataLength.setVisible(false);		
        this.txtdataLength.setHorizontalAlignment(2);		
        this.txtdataLength.setDataType(0);		
        this.txtdataLength.setSupportedEmpty(true);		
        this.txtdataLength.setRequired(false);
        // txtendCharLength		
        this.txtendCharLength.setVisible(false);		
        this.txtendCharLength.setHorizontalAlignment(2);		
        this.txtendCharLength.setDataType(0);		
        this.txtendCharLength.setSupportedEmpty(true);		
        this.txtendCharLength.setRequired(false);
        // txtendChar		
        this.txtendChar.setVisible(false);		
        this.txtendChar.setHorizontalAlignment(2);		
        this.txtendChar.setMaxLength(100);		
        this.txtendChar.setRequired(false);
        // txtmsgNo		
        this.txtmsgNo.setVisible(true);		
        this.txtmsgNo.setHorizontalAlignment(2);		
        this.txtmsgNo.setMaxLength(100);		
        this.txtmsgNo.setRequired(true);
        // txtmsgNoSta		
        this.txtmsgNoSta.setVisible(true);		
        this.txtmsgNoSta.setHorizontalAlignment(2);		
        this.txtmsgNoSta.setDataType(0);		
        this.txtmsgNoSta.setSupportedEmpty(true);		
        this.txtmsgNoSta.setRequired(false);
        // txtmsgNoEnd		
        this.txtmsgNoEnd.setVisible(true);		
        this.txtmsgNoEnd.setHorizontalAlignment(2);		
        this.txtmsgNoEnd.setDataType(0);		
        this.txtmsgNoEnd.setSupportedEmpty(true);		
        this.txtmsgNoEnd.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {MsgType,prmtChannel,txtheadLength,txtendChar,txtmsgNo,txtmsgNoSta,chkIsLog}));
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
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 629));
        kDTabbedPane1.setBounds(new Rectangle(4, 4, 1003, 621));
        this.add(kDTabbedPane1, new KDLayout.Constraints(4, 4, 1003, 621, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1002, 588));        kDLabelContainer1.setBounds(new Rectangle(31, 22, 270, 19));
        kDPanel1.add(kDLabelContainer1, new KDLayout.Constraints(31, 22, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(329, 22, 270, 19));
        kDPanel1.add(kDLabelContainer2, new KDLayout.Constraints(329, 22, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(636, 22, 270, 19));
        kDPanel1.add(kDLabelContainer3, new KDLayout.Constraints(636, 22, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer4.setBounds(new Rectangle(636, 53, 270, 19));
        kDPanel1.add(kDLabelContainer4, new KDLayout.Constraints(636, 53, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDButtonSelectBiz.setBounds(new Rectangle(840, 338, 73, 21));
        kDPanel1.add(kDButtonSelectBiz, new KDLayout.Constraints(840, 338, 73, 21, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contEASBizType.setBounds(new Rectangle(48, 340, 470, 19));
        kDPanel1.add(contEASBizType, new KDLayout.Constraints(48, 340, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contEASBizTypeName.setBounds(new Rectangle(545, 340, 270, 19));
        kDPanel1.add(contEASBizTypeName, new KDLayout.Constraints(545, 340, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMsgType.setBounds(new Rectangle(31, 53, 270, 19));
        kDPanel1.add(contMsgType, new KDLayout.Constraints(31, 53, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contChannel.setBounds(new Rectangle(329, 53, 270, 19));
        kDPanel1.add(contChannel, new KDLayout.Constraints(329, 53, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contheadLength.setBounds(new Rectangle(48, 372, 270, 19));
        kDPanel1.add(contheadLength, new KDLayout.Constraints(48, 372, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contdataLength.setBounds(new Rectangle(346, 372, 270, 19));
        kDPanel1.add(contdataLength, new KDLayout.Constraints(346, 372, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contendCharLength.setBounds(new Rectangle(653, 372, 270, 19));
        kDPanel1.add(contendCharLength, new KDLayout.Constraints(653, 372, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contendChar.setBounds(new Rectangle(48, 402, 270, 19));
        kDPanel1.add(contendChar, new KDLayout.Constraints(48, 402, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contmsgNo.setBounds(new Rectangle(33, 98, 270, 19));
        kDPanel1.add(contmsgNo, new KDLayout.Constraints(33, 98, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contmsgNoSta.setBounds(new Rectangle(331, 98, 270, 19));
        kDPanel1.add(contmsgNoSta, new KDLayout.Constraints(331, 98, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contmsgNoEnd.setBounds(new Rectangle(638, 98, 270, 19));
        kDPanel1.add(contmsgNoEnd, new KDLayout.Constraints(638, 98, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkIsLog.setBounds(new Rectangle(36, 132, 270, 19));
        kDPanel1.add(chkIsLog, new KDLayout.Constraints(36, 132, 270, 19, 0));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtName);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtSimpleName);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtDescription);
        //contEASBizType
        contEASBizType.setBoundEditor(txtEASBizType);
        //contEASBizTypeName
        contEASBizTypeName.setBoundEditor(txtEASBizTypeName);
        //contMsgType
        contMsgType.setBoundEditor(MsgType);
        //contChannel
        contChannel.setBoundEditor(prmtChannel);
        //contheadLength
        contheadLength.setBoundEditor(txtheadLength);
        //contdataLength
        contdataLength.setBoundEditor(txtdataLength);
        //contendCharLength
        contendCharLength.setBoundEditor(txtendCharLength);
        //contendChar
        contendChar.setBoundEditor(txtendChar);
        //contmsgNo
        contmsgNo.setBoundEditor(txtmsgNo);
        //contmsgNoSta
        contmsgNoSta.setBoundEditor(txtmsgNoSta);
        //contmsgNoEnd
        contmsgNoEnd.setBoundEditor(txtmsgNoEnd);

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
		dataBinder.registerBinding("IsLog", boolean.class, this.chkIsLog, "selected");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("simpleName", String.class, this.txtSimpleName, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "_multiLangItem");
		dataBinder.registerBinding("EASBizType", String.class, this.txtEASBizType, "text");
		dataBinder.registerBinding("EASBizTypeName", String.class, this.txtEASBizTypeName, "text");
		dataBinder.registerBinding("MsgType", com.kingdee.eas.custom.socketjk.SocketMsgType.class, this.MsgType, "selectedItem");
		dataBinder.registerBinding("Channel", com.kingdee.eas.custom.socketjk.UrlConfigInfo.class, this.prmtChannel, "data");
		dataBinder.registerBinding("headLength", int.class, this.txtheadLength, "value");
		dataBinder.registerBinding("dataLength", int.class, this.txtdataLength, "value");
		dataBinder.registerBinding("endCharLength", int.class, this.txtendCharLength, "value");
		dataBinder.registerBinding("endChar", String.class, this.txtendChar, "text");
		dataBinder.registerBinding("msgNo", String.class, this.txtmsgNo, "text");
		dataBinder.registerBinding("msgNoSta", int.class, this.txtmsgNoSta, "value");
		dataBinder.registerBinding("msgNoEnd", int.class, this.txtmsgNoEnd, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.custom.socketjk.app.SocketMsgSetEditUIHandler";
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
        this.MsgType.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.custom.socketjk.SocketMsgSetInfo)ov;
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
	 * ????????У??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("IsLog", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("simpleName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EASBizType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("EASBizTypeName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("MsgType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Channel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("headLength", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dataLength", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("endCharLength", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("endChar", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("msgNo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("msgNoSta", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("msgNoEnd", ValidateHelper.ON_SAVE);    		
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
        sic.add(new SelectorItemInfo("IsLog"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("simpleName"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("EASBizType"));
        sic.add(new SelectorItemInfo("EASBizTypeName"));
        sic.add(new SelectorItemInfo("MsgType"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Channel.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("Channel.id"));
        	sic.add(new SelectorItemInfo("Channel.number"));
        	sic.add(new SelectorItemInfo("Channel.name"));
		}
        sic.add(new SelectorItemInfo("headLength"));
        sic.add(new SelectorItemInfo("dataLength"));
        sic.add(new SelectorItemInfo("endCharLength"));
        sic.add(new SelectorItemInfo("endChar"));
        sic.add(new SelectorItemInfo("msgNo"));
        sic.add(new SelectorItemInfo("msgNoSta"));
        sic.add(new SelectorItemInfo("msgNoEnd"));
        return sic;
    }        
    	

    /**
     * output selectEASBiz_actionPerformed method
     */
    public void selectEASBiz_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareSelectEASBiz(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareSelectEASBiz() {
    	return false;
    }

    /**
     * output SelectEASBiz class
     */     
    protected class SelectEASBiz extends ItemAction {     
    
        public SelectEASBiz()
        {
            this(null);
        }

        public SelectEASBiz(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("SelectEASBiz.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("SelectEASBiz.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("SelectEASBiz.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSocketMsgSetEditUI.this, "SelectEASBiz", "selectEASBiz_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.custom.socketjk.client", "SocketMsgSetEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.custom.socketjk.client.SocketMsgSetEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.socketjk.SocketMsgSetFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.custom.socketjk.SocketMsgSetInfo objectValue = new com.kingdee.eas.custom.socketjk.SocketMsgSetInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


        
					protected void beforeStoreFields(ActionEvent arg0) throws Exception {
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtName.getItemDataByLang(new LanguageInfo(com.kingdee.eas.common.client.SysContext.getSysContext().getLocale())))) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"名称"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(MsgType.getSelectedItem())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"报文类型"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtChannel.getData())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"使用通道"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtmsgNo.getText())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"电文号"});
		}
			super.beforeStoreFields(arg0);
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
		vo.put("MsgType","ACK");
        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}