package com.kingdee.eas.custom.socketjk.app;

import org.apache.log4j.Logger;
import com.kingdee.bos.*;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.bot.app.BOTMappingControllerBean;
import com.kingdee.bos.util.BOSUuid;

import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.socketjk.PublicBaseUtil;
import com.kingdee.eas.custom.socketjk.SocketFacadeFactory;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.scm.common.BillBaseStatusEnum;
import com.kingdee.eas.scm.im.inv.PurInWarehsBillCollection;
import com.kingdee.eas.scm.im.inv.PurInWarehsBillFactory;
import com.kingdee.eas.scm.im.inv.PurInWarehsBillInfo;
import com.kingdee.eas.scm.im.inv.PurInWarehsEntryCollection;
import com.kingdee.eas.scm.im.inv.PurInWarehsEntryFactory;
import com.kingdee.eas.scm.im.inv.PurInWarehsEntryInfo;
import com.kingdee.eas.scm.im.inv.client.PurInWarehsBillEditUI;
import com.kingdee.eas.scm.im.inv.client.PurInWarehsBillListUI;


import java.lang.String;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class testFacadeControllerBean extends AbstracttestFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.custom.socketjk.app.testFacadeControllerBean");
    protected void _CreateBill(Context ctx, String strMsg) throws BOSException, EASBizException
    {   
    	String socketMsg="0524CCCW0220190308091015CCCWD1234567890          ab                  ac        01                  0123                                              0000802000003000\n";
    	
    	boolean isSucess=true;
    	
    	String errMsg="";
    	String strCreatorNo="user"; //创建人编码
    	String strCUNo="01";// 管理单元编码
    	String strBizDate=PublicBaseUtil.substringByte(socketMsg,"GBK" ,11,8) ; //业务日期
    	BillBaseStatusEnum billStatus=BillBaseStatusEnum.ADD; //单据状态
    	String strBizTypeNo="110";//业务类型编码
    	String strBillTypeNo="103";//单据类型编码
    	String strTranstionType="001";//事务类型
    	String strSupNo="1000"; //供应商编码
    	String strCurrencyNo ="BB01"; //币别编码
    	String strPaymentType="004";// 付款方式
    	BigDecimal bgExchangeRate=BigDecimal.ONE;// 汇率
    	BigDecimal bgTotalQty =BigDecimal.ZERO;// 数量
    	BigDecimal bgTotalAmount =BigDecimal.ZERO;// 金额
    	BigDecimal bgTotalActualCost =BigDecimal.ZERO;// 实际成本
    	BigDecimal bgTotalLocalAmount =BigDecimal.ZERO;// 本位币金额
    	boolean  isInTax=true;
    	
    	//制单人
		UserInfo creator=PublicBaseUtil.getUserByNumber(ctx, strCreatorNo);
		if(creator!=null){
			
		}else{
			isSucess=false;
			errMsg=errMsg + "制单人未找到\r\n";
		}
    	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    	//业务日期
    	Date bizDate=null;
		try
		{
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			bizDate=sdf.parse(strBizDate);
			
		}catch(Exception e){
			isSucess=false;
			errMsg=errMsg + "日期格式不正确\r\n";
			//throw new BOSException("日期格式不正确");
			
		}
		
    	//获取管理单元
    	CtrlUnitInfo cu=PublicBaseUtil.getCU(ctx, strCUNo); 
    	if(cu!=null){
    		
    	}else
    	{
    		isSucess=false;
			errMsg=errMsg + "管理单元不能为空\r\n";
    	}
    	String rjh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,30,20);
    	rjh=rjh.trim();
    	
    	String strMaterialNo="";
    	String strUnit="公斤（千克）";
    	String strLot=rjh;
    	String strStockNo="001";
    	BigDecimal bgQty=new BigDecimal("0");
    	BigDecimal bgPrice=new BigDecimal("0");
    	BigDecimal bgTaxRate=new BigDecimal("0");
    	BigDecimal bgAmount=new BigDecimal("0");
    	BigDecimal  bgKd=new BigDecimal("0");
    	BigDecimal bgHd=new BigDecimal("0");
    	
	if("".equals(rjh))
	{
		isSucess=false;
		errMsg=errMsg + "热卷号不能为空\r\n";
	}
	String ph=PublicBaseUtil.substringByte(socketMsg,"GBK" ,100,50);
	if(ph!=null){
		ph=ph.trim();
	}
	else
	{
		ph="";
	}
	if("".equals(ph))
	{
		isSucess=false;
		errMsg=errMsg + "牌号不能为空\r\n";
	}
	String kd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,156,5);
	if(kd!=null){
		kd=kd.trim();
	}else
	{
		kd="0";
	}
	try
	{
		bgKd=new BigDecimal(kd).divide(new BigDecimal("10"));;
	}catch( Exception e){
		
	}
	String hd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,150,6);
	
		hd=hd.trim();
	
	try
	{
		bgHd=new BigDecimal(hd).divide(new BigDecimal("1000"));
	}catch(Exception e){
		
	}
	String zl=PublicBaseUtil.substringByte(socketMsg,"GBK" ,161,5);
	if(zl!=null){
		zl=zl.trim();
	}else
	{
		zl="0";
	}
	try
	{
		bgQty=new BigDecimal(zl);
	}catch(Exception e){
		
	}
	if (bgKd.compareTo(BigDecimal.ZERO)==0){
    		isSucess=false;
		errMsg=errMsg + "宽度不能为零\r\n";
    	}
    	if (bgHd.compareTo(BigDecimal.ZERO)==0){
    		isSucess=false;
		errMsg=errMsg + "厚度不能为零\r\n";
    	}
    	if (bgQty.compareTo(BigDecimal.ZERO)==0){
    		isSucess=false;
		errMsg=errMsg + "重量不能为零\r\n";
    	}
     	if(isSucess){
    		MaterialInfo material= SocketFacadeFactory.getLocalInstance(ctx).getMaterialInfo("01", "5", "09", ph, ph, bgKd, bgHd, strUnit, "", true);
	    	if (material!=null){
	    		PurInWarehsEntryCollection purBills=PurInWarehsEntryFactory.getLocalInstance(ctx).getPurInWarehsEntryCollection(" select * where parent.baseStatus='4' and   parent.bizType='d8e80652-0106-1000-e000-04c5c0a812202407435C'   and  material.id='"+material.getId().toString()+"' and  lot='"+strLot+"' and parent.CU.id='" +cu.getId().toString()+"'");
	    		if (purBills!=null){
	    			if(purBills.size()>0){
			    		//采购退货
	    				PurInWarehsEntryInfo entry=purBills.get(0);
	    				if(entry!=null){
				        	PurInWarehsBillInfo info=PurInWarehsBillFactory.getLocalInstance(ctx).getPurInWarehsBillInfo(new ObjectUuidPK(entry.getParent().getId().toString()));
				        	PurInWarehsBillCollection sour= new PurInWarehsBillCollection();
				        	sour.add(info);
				        	CoreBillBaseCollection source=new CoreBillBaseCollection();
				        	IObjectCollection newcoll=sour.cast(new CoreBillBaseCollection().getClass());
				        	CoreBillBaseCollection infos= PublicBaseUtil.botp(ctx, info.getBOSType(),  info.getBOSType(), newcoll, "GuKLmgEUEADgABTHwKgShQRRIsQ=");
				        	if(infos!=null){
				        		if(infos.size()>0){
				        			String id=infos.get(0).getId().toString();
				        			if(id!=null){
				    	    			PurInWarehsBillInfo tempIno=PurInWarehsBillFactory.getLocalInstance(ctx).getPurInWarehsBillInfo(new ObjectUuidPK(id));
				    	    			if(tempIno!=null){
				    	    				tempIno.setBizDate(bizDate);
				    	    				tempIno.setCreator(creator);
				    	    				
					    	    			if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.TEMPORARILYSAVED)) {
					    						PurInWarehsBillFactory.getLocalInstance(ctx).submit(tempIno);
					    						
					    					}
					    					tempIno=PurInWarehsBillFactory.getLocalInstance(ctx).getPurInWarehsBillInfo(new ObjectUuidPK(id));
					    					if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
					    						PurInWarehsBillFactory.getLocalInstance(ctx).audit(new ObjectUuidPK(id));
					    					}
				    	    			}
				        			}
				        		}
				        	}
	    				}
	    			}
	    		}
	    	}
    	}else
    	{
    		
    	}
    	
    	
    	
    	
    	return;
    	
    	
//    	
//    	//采购入库
//    	
//    	
//    	
//    	boolean isSucess=true;
//    	String errMsg="";
//    	String strCreatorNo="user"; //创建人编码
//    	String strCUNo="01";// 管理单元编码
//    	String strBizDate="20190306" ; //业务日期
//    	BillBaseStatusEnum billStatus=BillBaseStatusEnum.ADD; //单据状态
//    	String strBizTypeNo="110";//业务类型编码
//    	String strBillTypeNo="103";//单据类型编码
//    	String strTranstionType="001";//事务类型
//    	String strSupNo="1000"; //供应商编码
//    	String strCurrencyNo ="BB01"; //币别编码
//    	String strPaymentType="004";// 付款方式
//    	BigDecimal bgExchangeRate=BigDecimal.ONE;// 汇率
//    	BigDecimal bgTotalQty =BigDecimal.ZERO;// 数量
//    	BigDecimal bgTotalAmount =BigDecimal.ZERO;// 金额
//    	BigDecimal bgTotalActualCost =BigDecimal.ZERO;// 实际成本
//    	BigDecimal bgTotalLocalAmount =BigDecimal.ZERO;// 本位币金额
//    	boolean  isInTax=true;
//    	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
//    	
//    	
//    	//新增采购入库
//    	PurInWarehsBillInfo info=new PurInWarehsBillInfo();
//    	//制单人
//		UserInfo creator=PublicBaseUtil.getUserByNumber(ctx, strCreatorNo);
//		if(creator!=null){
//			info.setCreator(creator);
//		}else{
//			info.setCreator(ContextUtil.getCurrentUserInfo(ctx));
//		}
//		//获取管理单元
//    	CtrlUnitInfo cu=PublicBaseUtil.getCU(ctx, strCUNo); 
//    	if(cu!=null){
//    		info.setCU(cu); //控制单元
//    	}else
//    	{
//    		isSucess=false;
//			errMsg=errMsg + "管理单元不能为空\r\n";
//    	}
//    	//创建时间
//    	try {
//			info.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
//		} catch (EASBizException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//业务日期
//		try
//		{
//			Date bizDate;
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//			bizDate=sdf.parse(strBizDate);
//			info.setBizDate(bizDate);
//			Calendar c = Calendar.getInstance();
//			c.setTime(bizDate);
//			int year=c.get(Calendar.YEAR);   //年度
//			int period=c.get(Calendar.MONTH)+1;
//	    	int month =c.get(Calendar.YEAR)*100+c.get(Calendar.MONTH)+1 ; //期间
//	    	int iday=(c.get(Calendar.YEAR)*100+c.get(Calendar.MONTH)+1)*100+c.get(Calendar.MONDAY);
//	    	info.setYear(year);
//	    	info.setPeriod(period);
//	    	info.setMonth(month);
//	    	info.setDay(iday);
//		}catch(Exception ex){
//			isSucess=false;
//			errMsg=errMsg + "日期格式不正确\r\n";
//			//throw new BOSException("日期格式不正确");
//			
//		}
//		//单据状态
//		info.setBaseStatus(billStatus);
//		// 业务类型
//		BizTypeInfo bizType=PublicBaseUtil.getBizTypeByNumber(ctx, strBizTypeNo);
//		if(bizType!=null){
//			info.setBizType(bizType);
//		}else
//		{
//			isSucess=false;
//			errMsg=errMsg + "业务类型不能为空\r\n";
//		}
//		// 单据类型
//		BillTypeInfo billType=PublicBaseUtil.getBillTypeByNumber(ctx, strBillTypeNo);
//		if(billType!=null){
//			info.setBillType(billType);
//		}else
//		{
//			isSucess=false;
//			errMsg=errMsg + "单据类型不能为空\r\n";
//		}
//		TransactionTypeInfo trantypeInfo=PublicBaseUtil.getTransactionTypeInfoByNumber(ctx, strTranstionType);
//		if(trantypeInfo!=null){
//			info.setTransactionType(trantypeInfo);
//		}else
//		{
//			isSucess=false;
//			errMsg=errMsg + "事务类型不能为空\r\n";
//		}
//		
//		SupplierInfo sup=PublicBaseUtil.getSupplier(ctx, strSupNo);
//		if(sup!=null){
//			info.setSupplier(sup);
//		}else
//		{
//			isSucess=false;
//			errMsg=errMsg + "供应商不能为空\r\n";
//		}
//		//库存组织
//		StorageOrgUnitInfo storeInfo=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strCUNo);
//		if(storeInfo!=null){
//			info.setStorageOrgUnit(storeInfo);
//		}else
//		{
//			isSucess=false;
//			errMsg=errMsg + "库存组织不能为空\r\n";
//		}
//		//币别
//		CurrencyInfo currency=PublicBaseUtil.getCurrencyInfo(ctx, strCurrencyNo);
//		if(currency!=null){
//			info.setCurrency(currency);
//			info.setExchangeRate(bgExchangeRate);
//		}else
//		{
//			isSucess=false;
//			errMsg=errMsg + "币别不能为空\r\n";
//		}
//		
//		// 付款方式
//		PaymentTypeInfo paymentInfo=PublicBaseUtil.getPaymentTypeInfoByNumber(ctx, strPaymentType);
//		
//		if(paymentInfo!=null){
//			info.setPaymentType(paymentInfo);
//		}else
//		{
//			isSucess=false;
//			errMsg=errMsg + "付款方式不能为空\r\n";
//		}
//		// 财务组织
//    	CompanyOrgUnitInfo company=PublicBaseUtil.getCompany(ctx, cu.getNumber());
//    	if(company!=null){
//			
//			
//		}else
//		{
//			isSucess=false;
//			errMsg=errMsg + "财务组织不能为空\r\n";
//		}
//    	//是否含税
//    	info.setIsInTax(isInTax);
//		
//
//    	//采购入库单分录 
//		String strMaterialNo="10912340902000";
//		String strUnit="吨";
//		String strLot="123456789";
//		String strStockNo="001";
//    	BigDecimal bgQty=new BigDecimal("100");
//    	BigDecimal bgPrice=new BigDecimal("10");
//    	BigDecimal bgTaxRate=new BigDecimal("0");
//    	BigDecimal bgAmount=new BigDecimal("1000");
//    	
//    	BigDecimal bgTaxPrice=bgPrice.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
//    	BigDecimal bgTaxAmount=bgAmount.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
//    	BigDecimal bgTax=bgAmount.multiply(bgTaxRate).setScale(2, BigDecimal.ROUND_HALF_UP);
//    	BigDecimal bgLocalTax=bgTax.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
//    	BigDecimal bgLocalPrice =bgPrice.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
//    	BigDecimal bgLocalAmount =bgAmount.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
//    	
//    	BigDecimal bgLocalTaxPrice =bgTaxPrice.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
//    	BigDecimal bgLocalTaxAmount =bgTaxAmount.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
//    	
//    	
//    	//big
//    	//新增分录实体
//    	PurInWarehsEntryInfo entry=new PurInWarehsEntryInfo();
//    	//分录行号
//    	entry.setSeq(1);
//    	entry.setSourceBillEntrySeq(0);
//    
//    	//物料
//    	MaterialInfo material=PublicBaseUtil.getMaterialInfoByNumber(ctx, info.getCU().getId().toString(), strMaterialNo);
//    	if (material!=null){
//    		entry.setMaterial(material);
//    		entry.setBaseUnit(material.getBaseUnit());
//    		
//    		
//    	}else
//    	{
//    		isSucess=false;
//			errMsg=errMsg + "物料不能为空\r\n";
//    	}
//    	//单位
//    	MeasureUnitInfo unit=PublicBaseUtil.getMeasureUnitInfoByName(ctx, material.getBaseUnit().getMeasureUnitGroup().getNumber(), strUnit);
//    	if (unit!=null){
//    		entry.setUnit(unit);
//    	}else
//    	{
//    		isSucess=false;
//			errMsg=errMsg + "单位不能为空\r\n";
//    	}
//    	entry.setBaseStatus(EntryBaseStatusEnum.NULL);//
//    	//
//    	entry.setAssCoefficient(BigDecimal.ZERO);
//    	//库存组织
//    	StorageOrgUnitInfo storOrgUnit=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
//    	if(storOrgUnit!=null){
//    		entry.setStorageOrgUnit(storOrgUnit);
//    		entry.setReceiveStorageOrgUnit(storOrgUnit);
//    		
//    	}else
//    	{
//    		isSucess=false;
//			errMsg=errMsg + "库存组织不能为空\r\n";
//    	}
//    	//财务组织
//    	entry.setCompanyOrgUnit(company);
//    	entry.setBalanceSupplier(sup);
//    	
//    	//仓库
//    	WarehouseInfo stock=PublicBaseUtil.getWarehouseInfoByNumber(ctx, strStockNo);
//    	if(stock!=null){
//    		entry.setWarehouse(stock);
//    		entry.setOutWarehouse(stock);
//    	}else
//    	{
//    		isSucess=false;
//			errMsg=errMsg + "仓库不能为空\r\n";
//    	}
//    	
//    	entry.setQty(bgQty);
//    	entry.setBaseQty(bgQty);
//    	bgTotalQty=bgTotalQty.add(bgQty);
//    	entry.setPrice(bgPrice);
//    	entry.setTaxAmount(bgAmount);
//    	entry.setTaxPrice(bgTaxPrice);
//    	entry.setTaxRate(bgTaxRate);
//    	entry.setAmount(bgAmount);
//    	entry.setLocalAmount(bgLocalAmount);
//    	entry.setLocalPrice(bgLocalPrice);
//    	entry.setLocalTax(bgLocalTax);
//    	entry.setLocalTaxAmount(bgLocalTaxAmount);
//    	entry.setLot(strLot);
//    	bgTotalAmount=bgTotalAmount.add(bgAmount);
//    	bgTotalLocalAmount=bgTotalLocalAmount.add(bgLocalAmount);
//    	bgTotalActualCost=bgTotalActualCost.add(bgLocalAmount);
//    	InvUpdateTypeInfo invUpType=PublicBaseUtil.getInvUpdateTypeInfoByNumber(ctx, "001");
//    	entry.setInvUpdateType(invUpType);
//    	entry.setDosingType(DosingTypeEnum.MANUAL);
//        entry.setUnWriteOffQty(bgQty);
//        entry.setUnWriteOffBaseQty(bgQty);
//        entry.setUnWriteOffAmount(bgAmount); //未核销金额
//    	entry.setUnReturnedBaseQty(bgQty);
//    	
//    	entry.setUnitActualCost(bgPrice); //单位实际成本
//    	entry.setActualCost(bgAmount); //实际成本 
//    	entry.setPurOrderEntrySeq(0);
//    	entry.setActualPrice(bgPrice); //实际单价
//    	entry.setActualTaxPrice(bgTaxPrice); //实际含税单价
//    	entry.setPurchaseCost(bgAmount); //采购成本
//    	entry.setUnitPurchaseCost(bgPrice); //单位采购成本
//    	
//    	info.getEntries().addObject(entry);
//    	
//    	
//    	info.setTotalQty(bgTotalQty);
//    	info.setTotalAmount(bgTotalAmount);
//    	info.setTotalActualCost(bgTotalActualCost);
//    	info.setTotalLocalAmount(bgTotalLocalAmount);
//  
//    	
//    	
//    	if(isSucess){
//    		IObjectPK pk=PurInWarehsBillFactory.getLocalInstance(ctx).save(info);  //保存
//			if(pk!=null){
////		    					Context ctxTemp=ctx.
//				PurInWarehsBillInfo tempIno=PurInWarehsBillFactory.getLocalInstance(ctx).getPurInWarehsBillInfo(pk);
//				if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.TEMPORARILYSAVED)) {
//					PurInWarehsBillFactory.getLocalInstance(ctx).submit(tempIno);
//				}
//				tempIno=PurInWarehsBillFactory.getLocalInstance(ctx).getPurInWarehsBillInfo(pk);
//				if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
//					PurInWarehsBillFactory.getLocalInstance(ctx).audit(pk);
//				}
//				
//				
//			}
//    	}else
//    	{
//    		throw new BOSException(errMsg);
//    	}
//    	
//        return;
    	
    	
    	/* 销售订单
    	strMsg="0524CCCW2020190225091015CCCWD11202188    天津金丰亨达钢铁贸易有限公司                                                                        河北省文安县新镇芦阜庄钢材市场                                                                      15645625432                                       1234567896@qq.com                                 154687923644                                                                    中国建设银行                                      25487952125489566118878                           I0\n";
    	String a1=PublicBaseUtil.substringByte(strMsg,"GBK" ,30, 12);
    	String b2=PublicBaseUtil.substringByte(strMsg,"GBK" , 42, 100);
    	String c3=PublicBaseUtil.substringByte(strMsg, "GBK" ,142, 100);
    	String e4=PublicBaseUtil.substringByte(strMsg,"GBK" , 242, 50);
    	String f5=PublicBaseUtil.substringByte(strMsg,"GBK" , 292, 50);
    	String g6=PublicBaseUtil.substringByte(strMsg,"GBK" , 342, 80);
    	String g8=PublicBaseUtil.substringByte(strMsg,"GBK" , 422, 50);
    	String g9=PublicBaseUtil.substringByte(strMsg, "GBK" ,472, 50);
    	String g10=PublicBaseUtil.substringByte(strMsg,"GBK" , 522, 1);
    	String g11=PublicBaseUtil.substringByte(strMsg,"GBK" , 523, 1);
    	boolean isSucess=true;
    	String errMsg="";
    	String strCreatorNo="user"; //创建人编码
    	String strCUNo="100000";// 管理单元编码
    	String strBizDate="20190225" ; //业务日期
    	BillBaseStatusEnum billStatus=BillBaseStatusEnum.ADD; //单据状态
    	String strBizTypeNo="210";//业务类型编码
    	String strBillTypeNo="310";//单据类型编码
    	boolean  isInnerSale=false; // 内部销售
    	String  custNo ="1.02.005"; //订单客户编码
    	String  strDeliveryTypeNo="SEND"; //交货方式编码
    	String strCurrencyNo ="BB01"; //币别编码
    	BigDecimal bigExchangeRate=BigDecimal.ONE;// 汇率
    	String strPaymentTypeNo="002";// 付款方式编码
    	BigDecimal bgPrePayment=BigDecimal.ZERO;//预收金额
    	BigDecimal bgPrePaymentRate=BigDecimal.ZERO;//预收比率
    	String strSaleOrgUnitNo="102000"; // 销售组织编码
    	String strSaleGroupNo="SZ001"; // 销售组编码
    	String strSalePersonNo="hq000002"; // 销售员编码
    	String strAdminOrgUnitNo="100000"; // 部门编码
    	
    	BigDecimal bgTotalAmount=BigDecimal.valueOf(10000);//金额
    	BigDecimal bgTotalTax=BigDecimal.valueOf(1700);//税额
    	BigDecimal bgTotalTaxAmount=BigDecimal.valueOf(11700);//价税合计
    	BigDecimal bgPreRecevied=BigDecimal.ZERO;//已收应收款
    	BigDecimal bgUnPreReceviedAmout=BigDecimal.ZERO;//未预收款金额
    	
    	String  strSendAddress ="测试地址"; // 送货地址
    	boolean isSysBill=false;  //是否为系统订单
    	ConvertModeEnum convertMode=ConvertModeEnum.DIRECTEXCHANGERATE;// 折算方式  默认直接汇率
    	
    	BigDecimal bgLocalTotalAmount=BigDecimal.valueOf(10000);//金额本位币
    	BigDecimal bgLocalTotalTaxAmount=BigDecimal.valueOf(11700);//价税合计本位币
    	String  strCompanyNo="102000" ; // 财务组织编码
    	
    	boolean  isInTax=true; //是否含税
    	String strCustomerPhone =""; //联系电话
    	String strLinkMan ="" ;// 联系人
    	boolean  isCentralBalance=false; //集中结算
    	
    	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    	
    	
    	//新增销售订
    	SaleOrderInfo info=new SaleOrderInfo();
    	//制单人
		UserInfo creator=PublicBaseUtil.getUserByNumber(ctx, strCreatorNo);
		if(creator!=null){
			info.setCreator(creator);
		}else{
			info.setCreator(ContextUtil.getCurrentUserInfo(ctx));
		}
		//获取管理单元
    	CtrlUnitInfo cu=PublicBaseUtil.getCU(ctx, strCUNo); 
    	if(cu!=null){
    		info.setCU(cu); //控制单元
    	}else
    	{
    		isSucess=false;
			errMsg=errMsg + "管理单元不能为空\r\n";
    	}
    	//创建时间
    	try {
			info.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//业务日期
		try
		{
			Date bizDate;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			bizDate=sdf.parse(strBizDate);
			info.setBizDate(bizDate);
			Calendar c = Calendar.getInstance();
			c.setTime(bizDate);
			int year=c.get(Calendar.YEAR);   //年度
	    	int period =c.get(Calendar.YEAR)*100+c.get(Calendar.MONTH)+1 ; //期间
	    	info.setYear(year);
	    	info.setPeriod(period);
		}catch(Exception ex){
			isSucess=false;
			errMsg=errMsg + "日期格式不正确\r\n";
			//throw new BOSException("日期格式不正确");
			
		}
		//单据状态
		info.setBaseStatus(billStatus);
		// 业务类型
		BizTypeInfo bizType=PublicBaseUtil.getBizTypeByNumber(ctx, strBizTypeNo);
		if(bizType!=null){
			info.setBizType(bizType);
		}else
		{
			isSucess=false;
			errMsg=errMsg + "业务类型不能为空\r\n";
		}
		// 单据类型
		BillTypeInfo billType=PublicBaseUtil.getBillTypeByNumber(ctx, strBillTypeNo);
		if(billType!=null){
			info.setBillType(billType);
		}else
		{
			isSucess=false;
			errMsg=errMsg + "单据类型不能为空\r\n";
		}
		// 内部销售
		info.setIsInnerSale(isInnerSale);  
    	//订货客户
    	CustomerInfo cust=PublicBaseUtil.getCustomer(ctx, custNo);
    	if(cust!=null){
    		info.setOrderCustomer(cust);
    		CustomerCompanyInfoInfo custCompanyinfo=PublicBaseUtil.getCustomerCompanyInfo(ctx, cust.getId().toString());
    		if (custCompanyinfo!=null){
    			strCustomerPhone=custCompanyinfo.getPhone()!=null?custCompanyinfo.getPhone().toString():"";
    			strLinkMan=custCompanyinfo.getContactPerson()!=null?custCompanyinfo.getContactPerson():"";
    		}
    	}else
    	{
    		isSucess=false;
			errMsg=errMsg + "客户不能为空\r\n";
    	}
    	// 交货方式
    	DeliveryTypeInfo deliveryTypeInfo=PublicBaseUtil.geDeliveryTypeByNumber(ctx, strDeliveryTypeNo);
		if(deliveryTypeInfo!=null){
			info.setDeliveryType(deliveryTypeInfo);
		}else
		{
			isSucess=false;
			errMsg=errMsg + "交付方式不能为空\r\n";
		}
    	//币别
		CurrencyInfo currency=PublicBaseUtil.getCurrencyInfo(ctx, strCurrencyNo);
		if(currency!=null){
			info.setCurrency(currency);
			info.setExchangeRate(bigExchangeRate);
		}else
		{
			isSucess=false;
			errMsg=errMsg + "币别不能为空\r\n";
		}
		
		//付款方式
		PaymentTypeInfo paymentType=PublicBaseUtil.getPaymentTypeInfoByNumber(ctx, strPaymentTypeNo);
		if(paymentType!=null){
			info.setPaymentType(paymentType);
			
		}else
		{
//			isSucess=false;
//			errMsg=errMsg + "付款方式不能为空\r\n";
		}
		//预收金额
		info.setPrepayment(bgPrePayment);
		//预收比率
		info.setPrepaymentRate(bgPrePaymentRate);
		//销售组织
		SaleOrgUnitInfo saleOrgUnit=PublicBaseUtil.getSaleOrgUnitInfoByNumber(ctx, strSaleOrgUnitNo);
		if(saleOrgUnit!=null){
			info.setSaleOrgUnit(saleOrgUnit);
		}else
		{
			isSucess=false;
			errMsg=errMsg + "销售组织不能为空\r\n";
		}
		//销售组
		SaleGroupInfo saleGroup=PublicBaseUtil.getSaleGroupInfoByNumber(ctx, strSaleGroupNo);
		if(saleGroup!=null){
			info.setSaleGroup(saleGroup);
		}else
		{
//			isSucess=false;
//			errMsg=errMsg + "销售组不能为空\r\n";
		}
		//销售员
		PersonInfo salePerson=PublicBaseUtil.getPersonInfoByNumber(ctx, strSalePersonNo);
		if(salePerson!=null){
			info.setSalePerson(salePerson);
		}else
		{
			isSucess=false;
			errMsg=errMsg + "销售员不能为空\r\n";
		}
		
		//部门
		AdminOrgUnitInfo adminOrg=PublicBaseUtil.getAdminOrgUnitInfoByNumber(ctx, strAdminOrgUnitNo);
		if(adminOrg!=null){
			info.setAdminOrgUnit(adminOrg);
		}else
		{
//			isSucess=false;
//			errMsg=errMsg + "部门不能为空\r\n";
		}
		//金额
		info.setTotalAmount(bgTotalAmount);
		//税额
		info.setTotalTax(bgTotalTax);
		//价税合计
		info.setTotalTaxAmount(bgTotalTaxAmount);
		//已收应收款
		info.setPreReceived(bgPreRecevied);
		//未预收款金额
		info.setUnPrereceivedAmount(bgUnPreReceviedAmout);
		// 送货地址
    	info.setSendAddress(strSendAddress);
    	//是否为系统订单
    	info.setIsSysBill(isSysBill);
    	// 折算方式  默认直接汇率
    	info.setConvertMode(convertMode);
    	//金额本位币
    	info.setLocalTotalAmount(bgLocalTotalAmount);
    	//价税合计本位币
    	info.setLocalTotalTaxAmount(bgLocalTotalTaxAmount);
    	// 财务组织
    	CompanyOrgUnitInfo company=PublicBaseUtil.getCompany(ctx, strCompanyNo);
    	if(company!=null){
			info.setCompanyOrgUnit(company);
			
		}else
		{
			isSucess=false;
			errMsg=errMsg + "财务组织不能为空\r\n";
		}
    	//是否含税
    	info.setIsInTax(isInTax);
    	//联系电话
    	info.setCustomerPhone(strCustomerPhone);
    	// 联系人
    	info.setLinkMan(strLinkMan);
    	//集中结算
    	info.setIsCentralBalance(isCentralBalance);

    	//销售订单分录 
    	String strMaterialNo="1.101.002";//物料编码
//    	String strUnitGroup=""; //单位组
    	String strUnit="件"; //单位
    	BigDecimal bgAssociateQty=BigDecimal.ONE; //未关联数量
    	BigDecimal bgBaseQty=BigDecimal.ONE; //基本单位数量
    	BigDecimal bgQty=BigDecimal.ONE; //数量
    	BigDecimal bgPrice=BigDecimal.valueOf(10000); //单价
    	BigDecimal bgTaxPrice=BigDecimal.valueOf(11700); //单价
    	BigDecimal bgAmount =BigDecimal.valueOf(10000) ;// 金额
    	BigDecimal bgLocalAmount =BigDecimal.valueOf(10000);// 本位币金额
    	BigDecimal bgActualPrice=BigDecimal.valueOf(10000) ;// 实际单价
    	BigDecimal bgActualTaxPrice=BigDecimal.valueOf(11700) ;//实际含税单价
    	BigDecimal bgTaxRate=BigDecimal.valueOf(17) ; //税率
    	BigDecimal bgTax=BigDecimal.valueOf(1700) ; //税额
    	BigDecimal bgTaxAmount=BigDecimal.valueOf(11700) ; // 价税合计
    	
    	
    	
    	Date dtSendDate = null;
		try {
			dtSendDate = sdfDate.parse("2019-02-25");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//发货日期
    	Date dtDeliveryDate = null;
		try {
			dtDeliveryDate = sdfDate.parse("2019-02-25");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 交货日期
    	String strStorageOrgUnitNo="102000"; //库存组织
//    	String strCompanyNo=""; //财务组织
    	BigDecimal bgLocalTax=BigDecimal.valueOf(1700) ; //本位币税额
    	BigDecimal bgLocalTaxAmount=BigDecimal.valueOf(11700) ; // 本位币价税合计
    	
    	//big
    	//新增分录实体
    	SaleOrderEntryInfo entry=new SaleOrderEntryInfo();
    	//分录行号
    	entry.setSeq(1);
    	entry.setSourceBillEntrySeq(0);
    	entry.setDiscountCondition(DiscountConditionEnum.NULL);
    	//物料
    	MaterialInfo material=PublicBaseUtil.getMaterialInfoByNumber(ctx, info.getCU().getId().toString(), strMaterialNo);
    	if (material!=null){
    		entry.setMaterial(material);
    		entry.setBaseUnit(material.getBaseUnit());
    		entry.setMaterialGroup(material.getMaterialGroup());
    		
    	}else
    	{
    		isSucess=false;
			errMsg=errMsg + "物料不能为空\r\n";
    	}
    	//单位
    	MeasureUnitInfo unit=PublicBaseUtil.getMeasureUnitInfoByName(ctx, material.getBaseUnit().getMeasureUnitGroup().getNumber(), strUnit);
    	if (unit!=null){
    		entry.setUnit(unit);
    	}else
    	{
    		isSucess=false;
			errMsg=errMsg + "单位不能为空\r\n";
    	}
    	entry.setBaseStatus(EntryBaseStatusEnum.ADD);//单据状态 新增
    	//
    	entry.setAssCoefficient(BigDecimal.ZERO);
    	//未关联数量
    	entry.setAssociateQty(bgAssociateQty);
    	//基本单位数量
    	entry.setBaseQty(bgBaseQty);
    	//数量
    	entry.setQty(bgQty);
    	//单价
    	entry.setPrice(bgPrice);
    	//含税单价
    	entry.setTaxPrice(bgTaxPrice);
    	// 金额
    	entry.setAmount(bgAmount);
    	// 本位币金额
    	entry.setLocalAmount(bgLocalAmount);
    	// 实际单价
    	entry.setActualPrice(bgActualPrice);
    	//实际含税单价
    	entry.setActualTaxPrice(bgActualTaxPrice);
    	//税率
    	entry.setTaxRate(bgTaxRate);
    	 //税额
    	entry.setTax(bgTax);
    	// 价税合计
    	entry.setTaxAmount(bgTaxAmount);
    	//发货日期
    	entry.setSendDate(dtSendDate);
    	//交货日期
    	entry.setDeliveryDate(dtDeliveryDate);
    	//库存组织
    	StorageOrgUnitInfo storOrgUnit=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strStorageOrgUnitNo);
    	if(storOrgUnit!=null){
    		entry.setStorageOrgUnit(storOrgUnit);
    		entry.setProStorageOrgUnit(storOrgUnit);
    	}else
    	{
    		isSucess=false;
			errMsg=errMsg + "库存组织不能为空\r\n";
    	}
    	//财务组织
    	entry.setCompanyOrgUnit(company);
    	//本位币税额
    	entry.setLocalTax(bgLocalTax);
    	// 本位币价税合计
    	entry.setLocalTaxAmount(bgLocalTaxAmount);
    	//发货客户
    	entry.setDeliveryCustomer(cust);
    	entry.setReceiveCustomer(cust);
    	entry.setPaymentCustomer(cust);
    	
    	
    	info.getEntries().add(entry);
    	
    	if(isSucess){
    		IObjectPK pk=SaleOrderFactory.getLocalInstance(ctx).save(info);  //保存
			if(pk!=null){
//		    					Context ctxTemp=ctx.
				SaleOrderInfo tempIno=SaleOrderFactory.getLocalInstance(ctx).getSaleOrderInfo(pk);
				if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.TEMPORARILYSAVED)) {
					SaleOrderFactory.getLocalInstance(ctx).submit(info);
				}
				if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
					SaleOrderFactory.getLocalInstance(ctx).audit(pk);
				}
				
				
			}
    	}else
    	{
    		throw new BOSException(errMsg);
    	}
    	
        return;
        */
    }
    
}