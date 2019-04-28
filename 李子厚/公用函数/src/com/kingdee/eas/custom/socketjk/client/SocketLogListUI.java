/**
 * output package name
 */
package com.kingdee.eas.custom.socketjk.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.kingdee.bos.Context;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.master.material.EquipmentPropertyEnum;
import com.kingdee.eas.basedata.master.material.MaterialFactory;
import com.kingdee.eas.basedata.master.material.MaterialGroupDetialCollection;
import com.kingdee.eas.basedata.master.material.MaterialGroupDetialInfo;
import com.kingdee.eas.basedata.master.material.MaterialGroupInfo;
import com.kingdee.eas.basedata.master.material.MaterialGroupStandardInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.master.material.UsedStatusEnum;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.socketjk.PublicBaseUtil;
import com.kingdee.eas.custom.socketjk.SocketFacadeFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class SocketLogListUI extends AbstractSocketLogListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SocketLogListUI.class);
    
    /**
     * output class constructor
     */
    public SocketLogListUI() throws Exception
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
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output menuItemPCVoucher_actionPerformed method
     */
    protected void menuItemPCVoucher_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemPCVoucher_actionPerformed(e);
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
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
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
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
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
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
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
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionCopyTo_actionPerformed
     */
    public void actionCopyTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyTo_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewDoProccess_actionPerformed(e);
    }

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionSendSmsMessage_actionPerformed
     */
    public void actionSendSmsMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendSmsMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    /**
     * output actionWorkflowList_actionPerformed
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }

    /**
     * output actoinViewSignature_actionPerformed
     */
    public void actoinViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actoinViewSignature_actionPerformed(e);
    }

    /**
     * output actionNumberSign_actionPerformed
     */
    public void actionNumberSign_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNumberSign_actionPerformed(e);
    }

    /**
     * output actionPCVoucher_actionPerformed
     */
    public void actionPCVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPCVoucher_actionPerformed(e);
    }

    /**
     * output actionDelPCVoucher_actionPerformed
     */
    public void actionDelPCVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelPCVoucher_actionPerformed(e);
    }

    /**
     * output actionTDPrint_actionPerformed
     */
    public void actionTDPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTDPrint_actionPerformed(e);
    }

    /**
     * output actionTDPrintPreview_actionPerformed
     */
    public void actionTDPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTDPrintPreview_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.socketjk.SocketLogFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.custom.socketjk.SocketLogInfo objectValue = new com.kingdee.eas.custom.socketjk.SocketLogInfo();
		
        return objectValue;
    }
    /**
     * output actionStartServer_actionPerformed method
     */
    public void actionStartServer_actionPerformed(ActionEvent e) throws Exception
    {
//    	Context ctx=null; 
//    	String zzbm="010000";
//    	String bs="1";
//    	String fl="09";
//    	String ph="1234";
//    	String phmc="测试牌号";
//    	BigDecimal kd=new BigDecimal("2000");
//    	BigDecimal hd=new BigDecimal("0.8");
//    	String dwmc="吨";
//    	String dwzbm="";
////    	MaterialInfo  material= SocketFacadeFactory.getRemoteInstance().getMaterialNumber("010000", "1", "09", "1234", "测试牌号", new BigDecimal("2000"), new BigDecimal("0.8"), "吨", "");
//    	
////    	二、物料编码规范
////    	物料编码包括物料标识码、物料分类码、牌号码、规格码四部分共计14位组成，无间隔标志，详细说明如下：
////    		第一段：物料标识码，1位字节，以5标识成品。
////    		第二段：物料分类码，2位字节，09代表酸洗板，10代表酸洗开平板，17代表轧制板，18代表轧制开平板，19代表镀锌板，20代表镀锌开平板。
////    		第三段：牌号码，4位字节，详细以冷轧MES中牌号流水号为准，如冷轧MES中牌号流水号不足4位则前置0补足。
////    		第四段：规格码，7位字节，以物料厚度+宽度标识，其中物料厚度3位（物料厚度*100），物料宽度4位，不足相应位数的前置0补足。
////    		例如：50900010801200代表冷轧MES牌号流水号为0001，规格型号为0.80*1200的酸洗板。
////    	三、物料命名规范
////    		物料名称包括分类、牌号、规格三部分，以“_”间隔，详细说明如下：
////    	第一段：分类名称，包括酸洗板、酸洗开平板、轧制板、轧制开平板、镀锌板、镀锌开平板六类，不限制字节长度；
////    	第二段：牌号名称，详细以冷轧MES中牌号名称为准，不限制字节长度；
////    	第三段：规格，以物料厚度*宽度标识，其中物料厚度3位（物料厚度*100），物料宽度4位，不足相应位数的前置0补足，8位字节。
////    	例如：酸洗板_C600QK-CM_0.80*1200代表牌号为C600QK-CM，规格型号为0.80*1200的酸洗板。
//    	MaterialInfo info=null;
//    	String strNumber="";
//    	String strName="";
//    	String strModel="";
//    	String strWLFL="1";
//    	String weightUnitName="吨";
//    	String LengthUnitName="毫米";
//    	String volumnUnitName="立方米";
//    	if (bs.length()!=1){
//    		throw new EASBizException(new NumericExceptionSubItem("003","物料标识长度必须为1"));
//    	}else
//    	{
//    		strNumber=bs;
//    	}
//    	
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
//    	if(ph.length()<=4 && ph.length()>0){
//    		String phTemp= "00000"+ph;
//    		strNumber=strNumber+phTemp.substring(phTemp.length()-4, phTemp.length());
//    		if(phmc.length()<1){
//    			throw new EASBizException(new NumericExceptionSubItem("003","牌号名称不能为空"));
//    		}else
//    		{
//    			strName=strName+"_"+phmc;
//    		}
//    	}else
//    	{
//    		throw new EASBizException(new NumericExceptionSubItem("003","牌号长度必须小于等于4并且大于0"));
//    	}
//    	
//    	if(hd.compareTo(BigDecimal.ZERO)==1){
//    		String hdTemp="00000"+(hd.setScale(2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
//    		strNumber=strNumber+hdTemp.substring(hdTemp.length()-3, hdTemp.length());
//    		strName=strName+"_"+hd.setScale(2,BigDecimal.ROUND_HALF_UP).toString()+"*";
//    		strModel=strModel+hd.setScale(2,BigDecimal.ROUND_HALF_UP).toString()+"*";
//    	}else
//    	{
//    		throw new EASBizException(new NumericExceptionSubItem("003","厚度必须大于0"));
//    	}
//    	
//    	if(kd.compareTo(BigDecimal.ZERO)==1){
//    		String kdTemp=kd.setScale(0,BigDecimal.ROUND_HALF_UP).toString();
//    		strNumber=strNumber+kdTemp.substring(kdTemp.length()-4, kdTemp.length());
//    		strName=strName+kdTemp.substring(kdTemp.length()-4, kdTemp.length());
//    		strModel=strModel+kd.setScale(0,BigDecimal.ROUND_HALF_UP).toString();
//    	}else
//    	{
//    		throw new EASBizException(new NumericExceptionSubItem("003","宽度必须大于0"));
//    	}
//    	CompanyOrgUnitInfo companyInfo=PublicBaseUtil.getCompany(ctx, zzbm);
//    	
//    	if(companyInfo!=null){
//    	info=PublicBaseUtil.getMaterialInfoByNumber(ctx, companyInfo.getId().toString(),strNumber) ;
//    	if (info==null){
//    		info=new MaterialInfo();
////    		BOSUuid id=BOSUuid.create(info.getBOSType());
////    		info.setId(id);
//    		info.setNumber(strNumber);
//    		info.setName(strName);   
//    		info.setModel(strModel);
//    		CtrlUnitInfo cu=PublicBaseUtil.getCU(ctx, zzbm);
//    		if(cu!=null){
//	    		info.setAdminCU(cu);
//	    		info.setCU(cu);
//    		}else
//    		{
//    			throw new EASBizException(new NumericExceptionSubItem("003","组织不能为空"));
//    		}
//    		UserInfo user=PublicBaseUtil.getUserByNumber(ctx, "user");
//    		info.setCreator(user);
//    		//创建时间
//        	try {
//    			info.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
//    		} catch (EASBizException ex) {
//    			// TODO Auto-generated catch block
//    			ex.printStackTrace();
//    		}
//    		
//    		MeasureUnitInfo  unitInfo =PublicBaseUtil.getMeasureUnitInfoByName(ctx, dwzbm, dwmc);
//    		info.setBaseUnit(unitInfo);
//    		
//    		MeasureUnitInfo  weightUnitInfo =PublicBaseUtil.getMeasureUnitInfoByName(ctx, "", weightUnitName);
//    		if (weightUnitInfo==null){
//    			throw new EASBizException(new NumericExceptionSubItem("003","重量单位不能为空"));
//    		}else{
//    			info.setWeightUnit(weightUnitInfo);
//    		}
//    		MeasureUnitInfo  lengthUnitInfo =PublicBaseUtil.getMeasureUnitInfoByName(ctx, "", LengthUnitName);
//    		if (lengthUnitInfo==null){
//    			throw new EASBizException(new NumericExceptionSubItem("003","长度单位不能为空"));
//    		}else{
//    			info.setLengthUnit(lengthUnitInfo);
//    		}
//    		
//    		MeasureUnitInfo  volumnUnitInfo =PublicBaseUtil.getMeasureUnitInfoByName(ctx, "", volumnUnitName);
//    		if (volumnUnitInfo==null){
//    			throw new EASBizException(new NumericExceptionSubItem("003","体积单位不能为空"));
//    		}else
//    		{
//    			info.setVolumnUnit(volumnUnitInfo);
//    		}
////    		
//    		MaterialGroupInfo materialGroup=PublicBaseUtil.getMaterialGroupInfoByNumber(ctx, companyInfo.getId().toString(), strWLFL);
//    		if (materialGroup==null){
//    			throw new EASBizException(new NumericExceptionSubItem("003","物料分类标准不能为空"));
//    		}else{
//    			info.setMaterialGroup(materialGroup); //物料分类
//    		}
//    		info.setPricePrecision(6); //单价精度
//    		info.setWidth(kd);
//    		info.setHeight(hd);
//    		info.setShortName(strName);
//    		info.setLongNumber(strNumber);
//    		info.setVersion(1);
//    		info.setEffectedStatus(1);
//    		info.setEquipProperty(EquipmentPropertyEnum.DEFAULT);
//    		info.setIsSynochronous(1);
//    		info.setIsGoods(false);
//    		info.setStatus(UsedStatusEnum.UNAPPROVE);
//    		info.setUseAsstAttrRelation(false);
//    		MaterialGroupDetialCollection groupCols=new MaterialGroupDetialCollection();
//    		MaterialGroupDetialInfo groupInfo=new MaterialGroupDetialInfo();
//    		groupInfo.setMaterial(info);
//    		MaterialGroupStandardInfo standinfo=PublicBaseUtil.getMaterialGroupStandardInfoByNumber(ctx, companyInfo.getId().toString(), "BaseGroupStandard");
//    		if(standinfo!=null){
//    			groupInfo.setMaterialGroupStandard(standinfo);
//    		}else
//    		{
//    			throw new EASBizException(new NumericExceptionSubItem("003","物料基本分类不能为空"));
//    		}
//    		groupInfo.setMaterialGroup(materialGroup);
//    		groupCols.add(groupInfo);
////    		
////    		MaterialGroupDetialInfo groupInfo2=new MaterialGroupDetialInfo();
////    		groupInfo2.setMaterial(info);
////    		MaterialGroupStandardInfo standinfo2=PublicBaseUtil.getMaterialGroupStandardInfoByNumber(ctx, companyInfo.getId().toString(), "NetOrderStandardType");
////    		groupInfo2.setMaterialGroupStandard(standinfo2);
////    		groupCols.add(groupInfo2);
////    		info.getMaterialGroupDetails().addCollection(groupCols);
////    		MaterialControllerBean
////    		DataBaseDInfo dataBaseDInfo = (DataBaseDInfo) info;
////    		CompanyOrgUnitInfo company = ContextUtil.getCurrentFIUnit(ctx);
//    		IObjectPK pk=MaterialFactory.getRemoteInstance().save(info);
//    		
//    		if(pk!=null){
//					//				Context ctxTemp=ctx.
////					MaterialInfo tempIno=MaterialFactory.getLocalInstance(ctx).getMaterialInfo(pk);
////					if(tempIno.getStatus().equals(UsedStatusEnum.UNAPPROVE)) {
////						MaterialFactory.getLocalInstance(ctx).approve(pk);
////					}
//					
//					
//				}
//    	
//    		
//    	}else
//    	{
//    		throw new EASBizException(new NumericExceptionSubItem("003","公司未找到"));
//    	}
//    	}
    	
    }
}