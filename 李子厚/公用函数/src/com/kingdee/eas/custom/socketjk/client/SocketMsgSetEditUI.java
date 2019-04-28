/**
 * output package name
 */
package com.kingdee.eas.custom.socketjk.client;



import java.awt.event.*;

import javax.swing.KeyStroke;

import org.apache.log4j.Logger;
import org.fife.ep.ui.autocomplete.AutoCompletion;
import org.fife.ep.ui.autocomplete.CompletionProvider;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.ep.client.ScriptArea;
import com.kingdee.eas.ep.client.ScriptPanel;
import com.kingdee.eas.ep.client.script.ClassCompletionProvider;
import com.kingdee.eas.ep.client.script.MethodCompletionProvider;
import com.kingdee.eas.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.base.uict.client.SelectBizUI;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;

/**
 * output class name
 */
public class SocketMsgSetEditUI extends AbstractSocketMsgSetEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SocketMsgSetEditUI.class);
    private ScriptArea txtScript = null;
    private ScriptPanel pScript = null;
    /**
     * output class constructor
     */
    public SocketMsgSetEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
        
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
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	if(this.txtScript.getText()!=null){
    		this.editData.setScript(this.txtScript.getText());
    	}
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(this.txtScript.getText()!=null){
    		this.editData.setScript(this.txtScript.getText());
    	}
        super.actionSubmit_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.socketjk.SocketMsgSetFactory.getRemoteInstance();
    }

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject) 
    {
        super.setDataObject(dataObject);
        if(STATUS_ADDNEW.equals(getOprtState())) {
            editData.put("treeid",(com.kingdee.eas.custom.socketjk.SocketMsgSetTreeInfo)getUIContext().get(UIContext.PARENTNODE));
        }
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.custom.socketjk.SocketMsgSetInfo objectValue = new com.kingdee.eas.custom.socketjk.SocketMsgSetInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        String script="var imp = JavaImporter();  \r\n"+
        "imp.importPackage(Packages.com.kingdee.eas.util.app);  \r\n"+
        "imp.importPackage(Packages.com.kingdee.bos); \r\n"+
        "imp.importPackage(Packages.java.text);  \r\n"+
        "imp.importPackage(Packages.java.lang);  \r\n"+
        "imp.importPackage(Packages.com.kingdee.eas.custom.socketjk);   \r\n"+
        "imp.importPackage(Packages.java.sql);   \r\n"+       
        "with(imp){ \r\n"+
        "	var socketMsg = methodCtx.getParamValue(0);  //接收报文 \r\n" +
        "	var billID = methodCtx.getParamValue(1); // 业务单元ID  \r\n" +
        "	var result = methodCtx.getParamValue(2); //成功失败  true:false  必须有返回值  methodCtx.getParam(2).setValue(\"true\") \r\n" +
        "	var sendMsg = methodCtx.getParamValue(3); // 发送报文   如果需要发送电文,将电文拼好放到这里返回  methodCtx.getParam(3).setValue(\"\")\r\n  "  +
        "	var errMsg = methodCtx.getParamValue(4); //错误消息 methodCtx.getParam(4).setValue(\"\")  \r\n" +
        "	var ctx = pluginCtx.getContext(); //服务端上下文 \r\n"+
        "	/* //发送电文示例\r\n " +
        "	methodCtx.getParam(2).setValue(\"true\") ;\r\n"+
        "	methodCtx.getParam(3).setValue(\"\")\r\n"+
        "	var sdf=new SimpleDateFormat(\"yyyyMMddHHmmss\");\r\n"+
        "	var strDate=sdf.format( new Date());\r\n"+
        "	var strMsg=\"0205CCCW01\"+strDate+\"CWCCD\";\r\n"+
        "	var strData=\"0102\"+ String.format(\"%-20s\", \"CG0001\")+ String.format(\"%-100s\",\"测试\")+ String.format(\"%-20s\", \"XSDD00001\") + String.format(\"%-20s\", \"002\")+\"000000020000\"+\"0\\n\" ;\r\n"+
        "	methodCtx.getParam(3).setValue(strMsg+strData);\r\n"+
        "	var a1=PublicBaseUtil.substringByte(strMsg,\"GBK\" ,30, 12); //按字节截取字符 \r\n"+
        "	*/\r\n"+
        "	\r\n"+
        "}";
        objectValue.setScript(script);
        return objectValue;
    }
    
    public void onLoad() throws Exception {
        {
        	super.onLoad();
        	this.txtScript = new ScriptArea();
            this.pScript = new ScriptPanel(this.txtScript);
            int mask = 8;
            CompletionProvider cp = new ClassCompletionProvider();
            CompletionProvider mp = new MethodCompletionProvider(0);
            mp.setParameterizedCompletionParams('(', ",", ')');
            
            AutoCompletion ac = new AutoCompletion(new CompletionProvider[] { cp, mp });
            
            ac.install(this.txtScript);
            ac.setTriggerKey(KeyStroke.getKeyStroke(47, mask));
            ac.setParameterAssistanceEnabled(true);
            this.kDTabbedPane1.addTab("执行脚本", this.pScript);
            if(this.editData.getScript()!=null){
        		this.txtScript.setText(this.editData.getScript());
        	}
//            dataBinder.registerBinding("Script", String.class, this.txtScript, "text");	
        }
        }
    /**
     * output selectEASBiz_actionPerformed method
     */
    public void selectEASBiz_actionPerformed(ActionEvent e) throws Exception
    {
    	IUIWindow window = null;
        UIContext uiCtx = new UIContext(this);
        uiCtx.put("selectBizType","basedata");
        window = UIFactory.createUIFactory(UIFactoryName.MODEL).create((com.kingdee.eas.custom.socketjk.client.NewSelectBizUI.class).getName(), uiCtx, null);
        window.show();
        NewSelectBizUI pickerUI = (NewSelectBizUI)window.getUIObject();
        if(pickerUI.CONFIRM_EXIT.equals("confirm")){
        	if(pickerUI.getItme()!=null){
        		if (pickerUI.getItme().getDataSource()!=null){
        			if(pickerUI.getItme().getDataSource().getBizUnitPK()!=null){
        				this.txtEASBizType.setText(pickerUI.getItme().getDataSource().getBizUnitPK().toString());
        			}
        			if(pickerUI.getItme().getDataSource().getAlias()!=null){
        				this.txtEASBizTypeName.setText(pickerUI.getItme().getDataSource().getAlias());
        			}
        		}
        	}
        }
    }
    
    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = super.getSelectors();
        if(!sic.containsKey("Script")){
        	sic.add(new SelectorItemInfo("Script"));
        }
        return sic;
    }  
}