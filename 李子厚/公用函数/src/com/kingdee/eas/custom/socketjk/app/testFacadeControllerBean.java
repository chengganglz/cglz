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
    	String strCreatorNo="user"; //�����˱���
    	String strCUNo="01";// ����Ԫ����
    	String strBizDate=PublicBaseUtil.substringByte(socketMsg,"GBK" ,11,8) ; //ҵ������
    	BillBaseStatusEnum billStatus=BillBaseStatusEnum.ADD; //����״̬
    	String strBizTypeNo="110";//ҵ�����ͱ���
    	String strBillTypeNo="103";//�������ͱ���
    	String strTranstionType="001";//��������
    	String strSupNo="1000"; //��Ӧ�̱���
    	String strCurrencyNo ="BB01"; //�ұ����
    	String strPaymentType="004";// ���ʽ
    	BigDecimal bgExchangeRate=BigDecimal.ONE;// ����
    	BigDecimal bgTotalQty =BigDecimal.ZERO;// ����
    	BigDecimal bgTotalAmount =BigDecimal.ZERO;// ���
    	BigDecimal bgTotalActualCost =BigDecimal.ZERO;// ʵ�ʳɱ�
    	BigDecimal bgTotalLocalAmount =BigDecimal.ZERO;// ��λ�ҽ��
    	boolean  isInTax=true;
    	
    	//�Ƶ���
		UserInfo creator=PublicBaseUtil.getUserByNumber(ctx, strCreatorNo);
		if(creator!=null){
			
		}else{
			isSucess=false;
			errMsg=errMsg + "�Ƶ���δ�ҵ�\r\n";
		}
    	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    	//ҵ������
    	Date bizDate=null;
		try
		{
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			bizDate=sdf.parse(strBizDate);
			
		}catch(Exception e){
			isSucess=false;
			errMsg=errMsg + "���ڸ�ʽ����ȷ\r\n";
			//throw new BOSException("���ڸ�ʽ����ȷ");
			
		}
		
    	//��ȡ����Ԫ
    	CtrlUnitInfo cu=PublicBaseUtil.getCU(ctx, strCUNo); 
    	if(cu!=null){
    		
    	}else
    	{
    		isSucess=false;
			errMsg=errMsg + "����Ԫ����Ϊ��\r\n";
    	}
    	String rjh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,30,20);
    	rjh=rjh.trim();
    	
    	String strMaterialNo="";
    	String strUnit="���ǧ�ˣ�";
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
		errMsg=errMsg + "�Ⱦ�Ų���Ϊ��\r\n";
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
		errMsg=errMsg + "�ƺŲ���Ϊ��\r\n";
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
		errMsg=errMsg + "��Ȳ���Ϊ��\r\n";
    	}
    	if (bgHd.compareTo(BigDecimal.ZERO)==0){
    		isSucess=false;
		errMsg=errMsg + "��Ȳ���Ϊ��\r\n";
    	}
    	if (bgQty.compareTo(BigDecimal.ZERO)==0){
    		isSucess=false;
		errMsg=errMsg + "��������Ϊ��\r\n";
    	}
     	if(isSucess){
    		MaterialInfo material= SocketFacadeFactory.getLocalInstance(ctx).getMaterialInfo("01", "5", "09", ph, ph, bgKd, bgHd, strUnit, "", true);
	    	if (material!=null){
	    		PurInWarehsEntryCollection purBills=PurInWarehsEntryFactory.getLocalInstance(ctx).getPurInWarehsEntryCollection(" select * where parent.baseStatus='4' and   parent.bizType='d8e80652-0106-1000-e000-04c5c0a812202407435C'   and  material.id='"+material.getId().toString()+"' and  lot='"+strLot+"' and parent.CU.id='" +cu.getId().toString()+"'");
	    		if (purBills!=null){
	    			if(purBills.size()>0){
			    		//�ɹ��˻�
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
//    	//�ɹ����
//    	
//    	
//    	
//    	boolean isSucess=true;
//    	String errMsg="";
//    	String strCreatorNo="user"; //�����˱���
//    	String strCUNo="01";// ����Ԫ����
//    	String strBizDate="20190306" ; //ҵ������
//    	BillBaseStatusEnum billStatus=BillBaseStatusEnum.ADD; //����״̬
//    	String strBizTypeNo="110";//ҵ�����ͱ���
//    	String strBillTypeNo="103";//�������ͱ���
//    	String strTranstionType="001";//��������
//    	String strSupNo="1000"; //��Ӧ�̱���
//    	String strCurrencyNo ="BB01"; //�ұ����
//    	String strPaymentType="004";// ���ʽ
//    	BigDecimal bgExchangeRate=BigDecimal.ONE;// ����
//    	BigDecimal bgTotalQty =BigDecimal.ZERO;// ����
//    	BigDecimal bgTotalAmount =BigDecimal.ZERO;// ���
//    	BigDecimal bgTotalActualCost =BigDecimal.ZERO;// ʵ�ʳɱ�
//    	BigDecimal bgTotalLocalAmount =BigDecimal.ZERO;// ��λ�ҽ��
//    	boolean  isInTax=true;
//    	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
//    	
//    	
//    	//�����ɹ����
//    	PurInWarehsBillInfo info=new PurInWarehsBillInfo();
//    	//�Ƶ���
//		UserInfo creator=PublicBaseUtil.getUserByNumber(ctx, strCreatorNo);
//		if(creator!=null){
//			info.setCreator(creator);
//		}else{
//			info.setCreator(ContextUtil.getCurrentUserInfo(ctx));
//		}
//		//��ȡ����Ԫ
//    	CtrlUnitInfo cu=PublicBaseUtil.getCU(ctx, strCUNo); 
//    	if(cu!=null){
//    		info.setCU(cu); //���Ƶ�Ԫ
//    	}else
//    	{
//    		isSucess=false;
//			errMsg=errMsg + "����Ԫ����Ϊ��\r\n";
//    	}
//    	//����ʱ��
//    	try {
//			info.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
//		} catch (EASBizException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//ҵ������
//		try
//		{
//			Date bizDate;
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//			bizDate=sdf.parse(strBizDate);
//			info.setBizDate(bizDate);
//			Calendar c = Calendar.getInstance();
//			c.setTime(bizDate);
//			int year=c.get(Calendar.YEAR);   //���
//			int period=c.get(Calendar.MONTH)+1;
//	    	int month =c.get(Calendar.YEAR)*100+c.get(Calendar.MONTH)+1 ; //�ڼ�
//	    	int iday=(c.get(Calendar.YEAR)*100+c.get(Calendar.MONTH)+1)*100+c.get(Calendar.MONDAY);
//	    	info.setYear(year);
//	    	info.setPeriod(period);
//	    	info.setMonth(month);
//	    	info.setDay(iday);
//		}catch(Exception ex){
//			isSucess=false;
//			errMsg=errMsg + "���ڸ�ʽ����ȷ\r\n";
//			//throw new BOSException("���ڸ�ʽ����ȷ");
//			
//		}
//		//����״̬
//		info.setBaseStatus(billStatus);
//		// ҵ������
//		BizTypeInfo bizType=PublicBaseUtil.getBizTypeByNumber(ctx, strBizTypeNo);
//		if(bizType!=null){
//			info.setBizType(bizType);
//		}else
//		{
//			isSucess=false;
//			errMsg=errMsg + "ҵ�����Ͳ���Ϊ��\r\n";
//		}
//		// ��������
//		BillTypeInfo billType=PublicBaseUtil.getBillTypeByNumber(ctx, strBillTypeNo);
//		if(billType!=null){
//			info.setBillType(billType);
//		}else
//		{
//			isSucess=false;
//			errMsg=errMsg + "�������Ͳ���Ϊ��\r\n";
//		}
//		TransactionTypeInfo trantypeInfo=PublicBaseUtil.getTransactionTypeInfoByNumber(ctx, strTranstionType);
//		if(trantypeInfo!=null){
//			info.setTransactionType(trantypeInfo);
//		}else
//		{
//			isSucess=false;
//			errMsg=errMsg + "�������Ͳ���Ϊ��\r\n";
//		}
//		
//		SupplierInfo sup=PublicBaseUtil.getSupplier(ctx, strSupNo);
//		if(sup!=null){
//			info.setSupplier(sup);
//		}else
//		{
//			isSucess=false;
//			errMsg=errMsg + "��Ӧ�̲���Ϊ��\r\n";
//		}
//		//�����֯
//		StorageOrgUnitInfo storeInfo=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strCUNo);
//		if(storeInfo!=null){
//			info.setStorageOrgUnit(storeInfo);
//		}else
//		{
//			isSucess=false;
//			errMsg=errMsg + "�����֯����Ϊ��\r\n";
//		}
//		//�ұ�
//		CurrencyInfo currency=PublicBaseUtil.getCurrencyInfo(ctx, strCurrencyNo);
//		if(currency!=null){
//			info.setCurrency(currency);
//			info.setExchangeRate(bgExchangeRate);
//		}else
//		{
//			isSucess=false;
//			errMsg=errMsg + "�ұ���Ϊ��\r\n";
//		}
//		
//		// ���ʽ
//		PaymentTypeInfo paymentInfo=PublicBaseUtil.getPaymentTypeInfoByNumber(ctx, strPaymentType);
//		
//		if(paymentInfo!=null){
//			info.setPaymentType(paymentInfo);
//		}else
//		{
//			isSucess=false;
//			errMsg=errMsg + "���ʽ����Ϊ��\r\n";
//		}
//		// ������֯
//    	CompanyOrgUnitInfo company=PublicBaseUtil.getCompany(ctx, cu.getNumber());
//    	if(company!=null){
//			
//			
//		}else
//		{
//			isSucess=false;
//			errMsg=errMsg + "������֯����Ϊ��\r\n";
//		}
//    	//�Ƿ�˰
//    	info.setIsInTax(isInTax);
//		
//
//    	//�ɹ���ⵥ��¼ 
//		String strMaterialNo="10912340902000";
//		String strUnit="��";
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
//    	//������¼ʵ��
//    	PurInWarehsEntryInfo entry=new PurInWarehsEntryInfo();
//    	//��¼�к�
//    	entry.setSeq(1);
//    	entry.setSourceBillEntrySeq(0);
//    
//    	//����
//    	MaterialInfo material=PublicBaseUtil.getMaterialInfoByNumber(ctx, info.getCU().getId().toString(), strMaterialNo);
//    	if (material!=null){
//    		entry.setMaterial(material);
//    		entry.setBaseUnit(material.getBaseUnit());
//    		
//    		
//    	}else
//    	{
//    		isSucess=false;
//			errMsg=errMsg + "���ϲ���Ϊ��\r\n";
//    	}
//    	//��λ
//    	MeasureUnitInfo unit=PublicBaseUtil.getMeasureUnitInfoByName(ctx, material.getBaseUnit().getMeasureUnitGroup().getNumber(), strUnit);
//    	if (unit!=null){
//    		entry.setUnit(unit);
//    	}else
//    	{
//    		isSucess=false;
//			errMsg=errMsg + "��λ����Ϊ��\r\n";
//    	}
//    	entry.setBaseStatus(EntryBaseStatusEnum.NULL);//
//    	//
//    	entry.setAssCoefficient(BigDecimal.ZERO);
//    	//�����֯
//    	StorageOrgUnitInfo storOrgUnit=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
//    	if(storOrgUnit!=null){
//    		entry.setStorageOrgUnit(storOrgUnit);
//    		entry.setReceiveStorageOrgUnit(storOrgUnit);
//    		
//    	}else
//    	{
//    		isSucess=false;
//			errMsg=errMsg + "�����֯����Ϊ��\r\n";
//    	}
//    	//������֯
//    	entry.setCompanyOrgUnit(company);
//    	entry.setBalanceSupplier(sup);
//    	
//    	//�ֿ�
//    	WarehouseInfo stock=PublicBaseUtil.getWarehouseInfoByNumber(ctx, strStockNo);
//    	if(stock!=null){
//    		entry.setWarehouse(stock);
//    		entry.setOutWarehouse(stock);
//    	}else
//    	{
//    		isSucess=false;
//			errMsg=errMsg + "�ֿⲻ��Ϊ��\r\n";
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
//        entry.setUnWriteOffAmount(bgAmount); //δ�������
//    	entry.setUnReturnedBaseQty(bgQty);
//    	
//    	entry.setUnitActualCost(bgPrice); //��λʵ�ʳɱ�
//    	entry.setActualCost(bgAmount); //ʵ�ʳɱ� 
//    	entry.setPurOrderEntrySeq(0);
//    	entry.setActualPrice(bgPrice); //ʵ�ʵ���
//    	entry.setActualTaxPrice(bgTaxPrice); //ʵ�ʺ�˰����
//    	entry.setPurchaseCost(bgAmount); //�ɹ��ɱ�
//    	entry.setUnitPurchaseCost(bgPrice); //��λ�ɹ��ɱ�
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
//    		IObjectPK pk=PurInWarehsBillFactory.getLocalInstance(ctx).save(info);  //����
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
    	
    	
    	/* ���۶���
    	strMsg="0524CCCW2020190225091015CCCWD11202188    ����������ó�����޹�˾                                                                        �ӱ�ʡ�İ�������«��ׯ�ֲ��г�                                                                      15645625432                                       1234567896@qq.com                                 154687923644                                                                    �й���������                                      25487952125489566118878                           I0\n";
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
    	String strCreatorNo="user"; //�����˱���
    	String strCUNo="100000";// ����Ԫ����
    	String strBizDate="20190225" ; //ҵ������
    	BillBaseStatusEnum billStatus=BillBaseStatusEnum.ADD; //����״̬
    	String strBizTypeNo="210";//ҵ�����ͱ���
    	String strBillTypeNo="310";//�������ͱ���
    	boolean  isInnerSale=false; // �ڲ�����
    	String  custNo ="1.02.005"; //�����ͻ�����
    	String  strDeliveryTypeNo="SEND"; //������ʽ����
    	String strCurrencyNo ="BB01"; //�ұ����
    	BigDecimal bigExchangeRate=BigDecimal.ONE;// ����
    	String strPaymentTypeNo="002";// ���ʽ����
    	BigDecimal bgPrePayment=BigDecimal.ZERO;//Ԥ�ս��
    	BigDecimal bgPrePaymentRate=BigDecimal.ZERO;//Ԥ�ձ���
    	String strSaleOrgUnitNo="102000"; // ������֯����
    	String strSaleGroupNo="SZ001"; // ���������
    	String strSalePersonNo="hq000002"; // ����Ա����
    	String strAdminOrgUnitNo="100000"; // ���ű���
    	
    	BigDecimal bgTotalAmount=BigDecimal.valueOf(10000);//���
    	BigDecimal bgTotalTax=BigDecimal.valueOf(1700);//˰��
    	BigDecimal bgTotalTaxAmount=BigDecimal.valueOf(11700);//��˰�ϼ�
    	BigDecimal bgPreRecevied=BigDecimal.ZERO;//����Ӧ�տ�
    	BigDecimal bgUnPreReceviedAmout=BigDecimal.ZERO;//δԤ�տ���
    	
    	String  strSendAddress ="���Ե�ַ"; // �ͻ���ַ
    	boolean isSysBill=false;  //�Ƿ�Ϊϵͳ����
    	ConvertModeEnum convertMode=ConvertModeEnum.DIRECTEXCHANGERATE;// ���㷽ʽ  Ĭ��ֱ�ӻ���
    	
    	BigDecimal bgLocalTotalAmount=BigDecimal.valueOf(10000);//��λ��
    	BigDecimal bgLocalTotalTaxAmount=BigDecimal.valueOf(11700);//��˰�ϼƱ�λ��
    	String  strCompanyNo="102000" ; // ������֯����
    	
    	boolean  isInTax=true; //�Ƿ�˰
    	String strCustomerPhone =""; //��ϵ�绰
    	String strLinkMan ="" ;// ��ϵ��
    	boolean  isCentralBalance=false; //���н���
    	
    	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    	
    	
    	//�������۶�
    	SaleOrderInfo info=new SaleOrderInfo();
    	//�Ƶ���
		UserInfo creator=PublicBaseUtil.getUserByNumber(ctx, strCreatorNo);
		if(creator!=null){
			info.setCreator(creator);
		}else{
			info.setCreator(ContextUtil.getCurrentUserInfo(ctx));
		}
		//��ȡ����Ԫ
    	CtrlUnitInfo cu=PublicBaseUtil.getCU(ctx, strCUNo); 
    	if(cu!=null){
    		info.setCU(cu); //���Ƶ�Ԫ
    	}else
    	{
    		isSucess=false;
			errMsg=errMsg + "����Ԫ����Ϊ��\r\n";
    	}
    	//����ʱ��
    	try {
			info.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ҵ������
		try
		{
			Date bizDate;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			bizDate=sdf.parse(strBizDate);
			info.setBizDate(bizDate);
			Calendar c = Calendar.getInstance();
			c.setTime(bizDate);
			int year=c.get(Calendar.YEAR);   //���
	    	int period =c.get(Calendar.YEAR)*100+c.get(Calendar.MONTH)+1 ; //�ڼ�
	    	info.setYear(year);
	    	info.setPeriod(period);
		}catch(Exception ex){
			isSucess=false;
			errMsg=errMsg + "���ڸ�ʽ����ȷ\r\n";
			//throw new BOSException("���ڸ�ʽ����ȷ");
			
		}
		//����״̬
		info.setBaseStatus(billStatus);
		// ҵ������
		BizTypeInfo bizType=PublicBaseUtil.getBizTypeByNumber(ctx, strBizTypeNo);
		if(bizType!=null){
			info.setBizType(bizType);
		}else
		{
			isSucess=false;
			errMsg=errMsg + "ҵ�����Ͳ���Ϊ��\r\n";
		}
		// ��������
		BillTypeInfo billType=PublicBaseUtil.getBillTypeByNumber(ctx, strBillTypeNo);
		if(billType!=null){
			info.setBillType(billType);
		}else
		{
			isSucess=false;
			errMsg=errMsg + "�������Ͳ���Ϊ��\r\n";
		}
		// �ڲ�����
		info.setIsInnerSale(isInnerSale);  
    	//�����ͻ�
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
			errMsg=errMsg + "�ͻ�����Ϊ��\r\n";
    	}
    	// ������ʽ
    	DeliveryTypeInfo deliveryTypeInfo=PublicBaseUtil.geDeliveryTypeByNumber(ctx, strDeliveryTypeNo);
		if(deliveryTypeInfo!=null){
			info.setDeliveryType(deliveryTypeInfo);
		}else
		{
			isSucess=false;
			errMsg=errMsg + "������ʽ����Ϊ��\r\n";
		}
    	//�ұ�
		CurrencyInfo currency=PublicBaseUtil.getCurrencyInfo(ctx, strCurrencyNo);
		if(currency!=null){
			info.setCurrency(currency);
			info.setExchangeRate(bigExchangeRate);
		}else
		{
			isSucess=false;
			errMsg=errMsg + "�ұ���Ϊ��\r\n";
		}
		
		//���ʽ
		PaymentTypeInfo paymentType=PublicBaseUtil.getPaymentTypeInfoByNumber(ctx, strPaymentTypeNo);
		if(paymentType!=null){
			info.setPaymentType(paymentType);
			
		}else
		{
//			isSucess=false;
//			errMsg=errMsg + "���ʽ����Ϊ��\r\n";
		}
		//Ԥ�ս��
		info.setPrepayment(bgPrePayment);
		//Ԥ�ձ���
		info.setPrepaymentRate(bgPrePaymentRate);
		//������֯
		SaleOrgUnitInfo saleOrgUnit=PublicBaseUtil.getSaleOrgUnitInfoByNumber(ctx, strSaleOrgUnitNo);
		if(saleOrgUnit!=null){
			info.setSaleOrgUnit(saleOrgUnit);
		}else
		{
			isSucess=false;
			errMsg=errMsg + "������֯����Ϊ��\r\n";
		}
		//������
		SaleGroupInfo saleGroup=PublicBaseUtil.getSaleGroupInfoByNumber(ctx, strSaleGroupNo);
		if(saleGroup!=null){
			info.setSaleGroup(saleGroup);
		}else
		{
//			isSucess=false;
//			errMsg=errMsg + "�����鲻��Ϊ��\r\n";
		}
		//����Ա
		PersonInfo salePerson=PublicBaseUtil.getPersonInfoByNumber(ctx, strSalePersonNo);
		if(salePerson!=null){
			info.setSalePerson(salePerson);
		}else
		{
			isSucess=false;
			errMsg=errMsg + "����Ա����Ϊ��\r\n";
		}
		
		//����
		AdminOrgUnitInfo adminOrg=PublicBaseUtil.getAdminOrgUnitInfoByNumber(ctx, strAdminOrgUnitNo);
		if(adminOrg!=null){
			info.setAdminOrgUnit(adminOrg);
		}else
		{
//			isSucess=false;
//			errMsg=errMsg + "���Ų���Ϊ��\r\n";
		}
		//���
		info.setTotalAmount(bgTotalAmount);
		//˰��
		info.setTotalTax(bgTotalTax);
		//��˰�ϼ�
		info.setTotalTaxAmount(bgTotalTaxAmount);
		//����Ӧ�տ�
		info.setPreReceived(bgPreRecevied);
		//δԤ�տ���
		info.setUnPrereceivedAmount(bgUnPreReceviedAmout);
		// �ͻ���ַ
    	info.setSendAddress(strSendAddress);
    	//�Ƿ�Ϊϵͳ����
    	info.setIsSysBill(isSysBill);
    	// ���㷽ʽ  Ĭ��ֱ�ӻ���
    	info.setConvertMode(convertMode);
    	//��λ��
    	info.setLocalTotalAmount(bgLocalTotalAmount);
    	//��˰�ϼƱ�λ��
    	info.setLocalTotalTaxAmount(bgLocalTotalTaxAmount);
    	// ������֯
    	CompanyOrgUnitInfo company=PublicBaseUtil.getCompany(ctx, strCompanyNo);
    	if(company!=null){
			info.setCompanyOrgUnit(company);
			
		}else
		{
			isSucess=false;
			errMsg=errMsg + "������֯����Ϊ��\r\n";
		}
    	//�Ƿ�˰
    	info.setIsInTax(isInTax);
    	//��ϵ�绰
    	info.setCustomerPhone(strCustomerPhone);
    	// ��ϵ��
    	info.setLinkMan(strLinkMan);
    	//���н���
    	info.setIsCentralBalance(isCentralBalance);

    	//���۶�����¼ 
    	String strMaterialNo="1.101.002";//���ϱ���
//    	String strUnitGroup=""; //��λ��
    	String strUnit="��"; //��λ
    	BigDecimal bgAssociateQty=BigDecimal.ONE; //δ��������
    	BigDecimal bgBaseQty=BigDecimal.ONE; //������λ����
    	BigDecimal bgQty=BigDecimal.ONE; //����
    	BigDecimal bgPrice=BigDecimal.valueOf(10000); //����
    	BigDecimal bgTaxPrice=BigDecimal.valueOf(11700); //����
    	BigDecimal bgAmount =BigDecimal.valueOf(10000) ;// ���
    	BigDecimal bgLocalAmount =BigDecimal.valueOf(10000);// ��λ�ҽ��
    	BigDecimal bgActualPrice=BigDecimal.valueOf(10000) ;// ʵ�ʵ���
    	BigDecimal bgActualTaxPrice=BigDecimal.valueOf(11700) ;//ʵ�ʺ�˰����
    	BigDecimal bgTaxRate=BigDecimal.valueOf(17) ; //˰��
    	BigDecimal bgTax=BigDecimal.valueOf(1700) ; //˰��
    	BigDecimal bgTaxAmount=BigDecimal.valueOf(11700) ; // ��˰�ϼ�
    	
    	
    	
    	Date dtSendDate = null;
		try {
			dtSendDate = sdfDate.parse("2019-02-25");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//��������
    	Date dtDeliveryDate = null;
		try {
			dtDeliveryDate = sdfDate.parse("2019-02-25");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// ��������
    	String strStorageOrgUnitNo="102000"; //�����֯
//    	String strCompanyNo=""; //������֯
    	BigDecimal bgLocalTax=BigDecimal.valueOf(1700) ; //��λ��˰��
    	BigDecimal bgLocalTaxAmount=BigDecimal.valueOf(11700) ; // ��λ�Ҽ�˰�ϼ�
    	
    	//big
    	//������¼ʵ��
    	SaleOrderEntryInfo entry=new SaleOrderEntryInfo();
    	//��¼�к�
    	entry.setSeq(1);
    	entry.setSourceBillEntrySeq(0);
    	entry.setDiscountCondition(DiscountConditionEnum.NULL);
    	//����
    	MaterialInfo material=PublicBaseUtil.getMaterialInfoByNumber(ctx, info.getCU().getId().toString(), strMaterialNo);
    	if (material!=null){
    		entry.setMaterial(material);
    		entry.setBaseUnit(material.getBaseUnit());
    		entry.setMaterialGroup(material.getMaterialGroup());
    		
    	}else
    	{
    		isSucess=false;
			errMsg=errMsg + "���ϲ���Ϊ��\r\n";
    	}
    	//��λ
    	MeasureUnitInfo unit=PublicBaseUtil.getMeasureUnitInfoByName(ctx, material.getBaseUnit().getMeasureUnitGroup().getNumber(), strUnit);
    	if (unit!=null){
    		entry.setUnit(unit);
    	}else
    	{
    		isSucess=false;
			errMsg=errMsg + "��λ����Ϊ��\r\n";
    	}
    	entry.setBaseStatus(EntryBaseStatusEnum.ADD);//����״̬ ����
    	//
    	entry.setAssCoefficient(BigDecimal.ZERO);
    	//δ��������
    	entry.setAssociateQty(bgAssociateQty);
    	//������λ����
    	entry.setBaseQty(bgBaseQty);
    	//����
    	entry.setQty(bgQty);
    	//����
    	entry.setPrice(bgPrice);
    	//��˰����
    	entry.setTaxPrice(bgTaxPrice);
    	// ���
    	entry.setAmount(bgAmount);
    	// ��λ�ҽ��
    	entry.setLocalAmount(bgLocalAmount);
    	// ʵ�ʵ���
    	entry.setActualPrice(bgActualPrice);
    	//ʵ�ʺ�˰����
    	entry.setActualTaxPrice(bgActualTaxPrice);
    	//˰��
    	entry.setTaxRate(bgTaxRate);
    	 //˰��
    	entry.setTax(bgTax);
    	// ��˰�ϼ�
    	entry.setTaxAmount(bgTaxAmount);
    	//��������
    	entry.setSendDate(dtSendDate);
    	//��������
    	entry.setDeliveryDate(dtDeliveryDate);
    	//�����֯
    	StorageOrgUnitInfo storOrgUnit=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strStorageOrgUnitNo);
    	if(storOrgUnit!=null){
    		entry.setStorageOrgUnit(storOrgUnit);
    		entry.setProStorageOrgUnit(storOrgUnit);
    	}else
    	{
    		isSucess=false;
			errMsg=errMsg + "�����֯����Ϊ��\r\n";
    	}
    	//������֯
    	entry.setCompanyOrgUnit(company);
    	//��λ��˰��
    	entry.setLocalTax(bgLocalTax);
    	// ��λ�Ҽ�˰�ϼ�
    	entry.setLocalTaxAmount(bgLocalTaxAmount);
    	//�����ͻ�
    	entry.setDeliveryCustomer(cust);
    	entry.setReceiveCustomer(cust);
    	entry.setPaymentCustomer(cust);
    	
    	
    	info.getEntries().add(entry);
    	
    	if(isSucess){
    		IObjectPK pk=SaleOrderFactory.getLocalInstance(ctx).save(info);  //����
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