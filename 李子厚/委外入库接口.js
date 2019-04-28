var imp = JavaImporter();  
imp.importPackage(Packages.com.kingdee.eas.util.app);  
imp.importPackage(Packages.com.kingdee.bos); 
imp.importPackage(Packages.java.text);  
imp.importPackage(Packages.java.lang);  
imp.importPackage(Packages.com.kingdee.eas.custom.socketjk);   
imp.importPackage(Packages.java.sql);   
imp.importPackage(Packages.com.kingdee.bos);  
imp.importPackage(Packages.com.kingdee.bos.dao);  
imp.importPackage(Packages.com.kingdee.eas.base.permission);  
imp.importPackage(Packages.com.kingdee.eas.basedata.assistant);  
imp.importPackage(Packages.com.kingdee.eas.basedata.master.cssp);  
imp.importPackage(Packages.com.kingdee.eas.basedata.master.material);  
imp.importPackage(Packages.com.kingdee.eas.basedata.org);  
imp.importPackage(Packages.com.kingdee.eas.basedata.scm.common);  
imp.importPackage(Packages.com.kingdee.eas.basedata.scm.im.inv);  
imp.importPackage(Packages.com.kingdee.eas.common);  
imp.importPackage(Packages.com.kingdee.eas.scm.common);  
imp.importPackage(Packages.com.kingdee.eas.scm.im.inv);  
imp.importPackage(Packages.com.kingdee.eas.scm.sm.sc);  
imp.importPackage(Packages.com.kingdee.eas.util);  
imp.importPackage(Packages.java.math);  
imp.importPackage(Packages.java.util);  
with(imp){ 
	var socketMsg = methodCtx.getParamValue(0);  //接收报文 
	var billID = methodCtx.getParamValue(1); // 业务单元ID  
	var result = methodCtx.getParamValue(2); //成功失败  true:false  必须有返回值  methodCtx.getParam(2).setValue("true") 
	var sendMsg = methodCtx.getParamValue(3); // 发送报文   如果需要发送电文,将电文拼好放到这里返回  methodCtx.getParam(3).setValue("")
  	var errMsg = methodCtx.getParamValue(4); //错误消息 methodCtx.getParam(4).setValue("")  
	var ctx = pluginCtx.getContext(); //服务端上下文 
	/* //发送电文示例
 	methodCtx.getParam(2).setValue("true") ;
	methodCtx.getParam(3).setValue("")
	var sdf=new SimpleDateFormat("yyyyMMddHHmmss");
	var strDate=sdf.format( new Date());
	var strMsg="0205CCCW01"+strDate+"CWCCD";
	var strData="0102"+ String.format("%-20s", "CG0001")+ String.format("%-100s","测试")+ String.format("%-20s", "XSDD00001") + String.format("%-20s", "002")+"000000020000"+"0\n" ;
	methodCtx.getParam(3).setValue(strMsg+strData);
	var a1=PublicBaseUtil.substringByte(strMsg,"GBK" ,30, 12); //按字节截取字符 
	*/
	var dwh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,5,20);
	var dwt=PublicBaseUtil.substringByte(socketMsg,"GBK" ,0,29);
	dwt="0524CCCW2020190224040527CCCWD11203778";
	System.out.println("生产退料电文号"+dwh);
	var isSucess=true;
	methodCtx.getParam(2).setValue("false") ;
	var errMsg="";
	var strCreatorNo="user"; //创建人编码
	var strCUNo="01";// 管理单元编码
	var strBizDate=PublicBaseUtil.substringByte(socketMsg,"GBK" ,11,8) ; //业务日期
	var billStatus=BillBaseStatusEnum.ADD; //单据状态
	var strBizTypeNo="130";//业务类型编码
	var strBillTypeNo="103";//单据类型编码
	var strTranstionType="001";//事务类型
	var strSupNo="1000"; //供应商编码
	var strCurrencyNo ="BB01"; //币别编码
	var strPaymentType="004";// 付款方式
	var bgExchangeRate=BigDecimal.ONE;// 汇率
	var bgTotalQty =BigDecimal.ZERO;// 数量
	var bgTotalAmount =BigDecimal.ZERO;// 金额
	var bgTotalActualCost =BigDecimal.ZERO;// 实际成本
	var bgTotalLocalAmount =BigDecimal.ZERO;// 本位币金额
	var  isInTax=true;
	var sdfDate = new SimpleDateFormat("yyyy-MM-dd");
	
	
	//新增采购入库
	var info=new PurInWarehsBillInfo();
	//制单人
	var creator=PublicBaseUtil.getUserByNumber(ctx, strCreatorNo);
	if(creator!=null){
		info.setCreator(creator);
	}else{
		info.setCreator(ContextUtil.getCurrentUserInfo(ctx));
	}
	//获取管理单元
	var cu=PublicBaseUtil.getCU(ctx, strCUNo); 
	if(cu!=null){
		info.setCU(cu); //控制单元
	}else
	{
		isSucess=false;
		errMsg=errMsg + "管理单元不能为空\r\n";
	}
	info.setPurchaseType(com.kingdee.eas.scm.common.PurchaseTypeEnum.SUBCONTRACT);//采购类别委外
	//创建时间
	try {
		info.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
	} catch ( e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//业务日期
	strBizDate="20190417";
	try
	{
		var bizDate;
		var sdf = new SimpleDateFormat("yyyyMMdd");
		bizDate=sdf.parse(strBizDate);
		info.setBizDate(bizDate);
		var c = Calendar.getInstance();
		c.setTime(bizDate);
		var year=c.get(Calendar.YEAR);   //年度
		var period=c.get(Calendar.MONTH)+1;
		var month =c.get(Calendar.YEAR)*100+c.get(Calendar.MONTH)+1 ; //期间
		var iday=(c.get(Calendar.YEAR)*100+c.get(Calendar.MONTH)+1)*100+c.get(Calendar.MONDAY);
		info.setYear(year);
		info.setPeriod(period);
		info.setMonth(month);
		info.setDay(iday);
	}catch(e){
		isSucess=false;
		errMsg=errMsg + "日期格式不正确\r\n";
		//throw new BOSException("日期格式不正确");
		
	}
	//单据状态
	info.setBaseStatus(billStatus);
	// 业务类型
	var bizType=PublicBaseUtil.getBizTypeByNumber(ctx, strBizTypeNo);
	if(bizType!=null){
		info.setBizType(bizType);
	}else
	{
		isSucess=false;
		errMsg=errMsg + "业务类型不能为空\r\n";
	}
	// 单据类型
	var billType=PublicBaseUtil.getBillTypeByNumber(ctx, strBillTypeNo);
	if(billType!=null){
		info.setBillType(billType);
	}else
	{
		isSucess=false;
		errMsg=errMsg + "单据类型不能为空\r\n";
	}
	var trantypeInfo=PublicBaseUtil.getTransactionTypeInfoByNumber(ctx, strTranstionType);
	if(trantypeInfo!=null){
		info.setTransactionType(trantypeInfo);
	}else
	{
		isSucess=false;
		errMsg=errMsg + "事务类型不能为空\r\n";
	}
	
	var sup=PublicBaseUtil.getSupplier(ctx, strSupNo);
	if(sup!=null){
		info.setSupplier(sup);
	}else
	{
		isSucess=false;
		errMsg=errMsg + "供应商不能为空\r\n";
	}
	//库存组织
	var storeInfo=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strCUNo);
	if(storeInfo!=null){
		info.setStorageOrgUnit(storeInfo);
	}else
	{
		isSucess=false;
		errMsg=errMsg + "库存组织不能为空\r\n";
	}
	//币别
	var currency=PublicBaseUtil.getCurrencyInfo(ctx, strCurrencyNo);
	if(currency!=null){
		info.setCurrency(currency);
		info.setExchangeRate(bgExchangeRate);
	}else
	{
		isSucess=false;
		errMsg=errMsg + "币别不能为空\r\n";
	}
	
	// 付款方式
	var paymentInfo=PublicBaseUtil.getPaymentTypeInfoByNumber(ctx, strPaymentType);
	
	if(paymentInfo!=null){
		info.setPaymentType(paymentInfo);
	}else
	{
		isSucess=false;
		errMsg=errMsg + "付款方式不能为空\r\n";
	}
	// 财务组织
	var company=PublicBaseUtil.getCompany(ctx, cu.getNumber());
	if(company!=null){
		
		
	}else
	{
		isSucess=false;
		errMsg=errMsg + "财务组织不能为空\r\n";
	}
	//是否含税
	info.setIsInTax(isInTax);
	

    //采购入库单分录 
    
	var strMaterialNo="";
	var strUnit="公斤（千克）";
	
	var strStockNo="001";
	var bgQty=new BigDecimal("0");
	var bgPrice=new BigDecimal("0");
	var bgTaxRate=new BigDecimal("0");
	var bgAmount=new BigDecimal("0");
	var  bgKd=new BigDecimal("0");
	var bgHd=new BigDecimal("0");
	var rjh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,30,20);
	rjh="201904172";
	rjh=rjh.trim();
	if("".equals(rjh))
	{
		isSucess=false;
		errMsg=errMsg + "卷号不能为空\r\n";
	}
	var strLot=rjh;
	//var ph=PublicBaseUtil.substringByte(socketMsg,"GBK" ,100,50);
	var ph="0417";
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
	var phmc="Appprr";
	if(phmc!=null){
		phmc=phmc.trim();
	}
	else
	{
		phmc="";
	}
	if("".equals(phmc))
	{
		isSucess=false;
		errMsg=errMsg + "牌号名称不能为空\r\n";
	}
	/*var kd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,156,5);
	if(kd!=null){
		kd=kd.trim();
	}else
	{
		kd="0";
	}*/
	bgKd=BigDecimal.valueOf(1320);
	try
	{
		//bgKd=new BigDecimal(kd).divide(new BigDecimal("10"));;
		bgKd=BigDecimal.valueOf(1320);
	}catch(e){
		
	}
	//var hd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,150,6);
	//	hd=hd.trim();
	bgHd=BigDecimal.valueOf(0.87);
	try
	{
		//bgHd=new BigDecimal(hd).divide(new BigDecimal("1000"));
		bgHd=BigDecimal.valueOf(0.87);
	}catch(e){
		
	}
	var zl=PublicBaseUtil.substringByte(socketMsg,"GBK" ,161,5);
	if(zl!=null){
		zl=zl.trim();
	}else
	{
		zl="0";
	}
	zl="20";
	try
	{
		bgQty=new BigDecimal(zl);
	}catch(e){
		
	}
	if (bgKd==0){
		isSucess=false;
		errMsg=errMsg + "宽度不能为零\r\n";
	}
	if (bgHd==0){
		isSucess=false;
		errMsg=errMsg + "厚度不能为零\r\n";
	}
	if (bgQty==0){
		isSucess=false;
		errMsg=errMsg + "重量不能为零\r\n";
	}

	var bgTaxPrice=bgPrice.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
	var bgTaxAmount=bgAmount.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
	var bgTax=bgAmount.multiply(bgTaxRate).setScale(2, BigDecimal.ROUND_HALF_UP);
	var bgLocalTax=bgTax.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
	var bgLocalPrice =bgPrice.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
	var bgLocalAmount =bgAmount.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
	
	var bgLocalTaxPrice =bgTaxPrice.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
	var bgLocalTaxAmount =bgTaxAmount.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
	
	if(isSucess){
		//big
		//新增分录实体
		var entry=new PurInWarehsEntryInfo();
		//分录行号
		entry.setSeq(1);
		entry.setSourceBillEntrySeq(0);

		//物料
		//var material=PublicBaseUtil.getMaterialInfoByNumber(ctx, info.getCU().getId().toString(), strMaterialNo);
		System.out.println("牌号"+ph);
		System.out.println("宽度"+bgKd);
		System.out.println("厚度"+bgHd);
		var material= SocketFacadeFactory.getLocalInstance(ctx).getMaterialInfo("01", "5", "09", ph, phmc, bgKd, bgHd, strUnit, "", true);
		if (material!=null){
			entry.setMaterial(material);
			entry.setBaseUnit(material.getBaseUnit());
			
			
		}else
		{
			isSucess=false;
			errMsg=errMsg + "物料不能为空\r\n";
		}
		//单位
		var unit=PublicBaseUtil.getMeasureUnitInfoByName(ctx, material.getBaseUnit().getMeasureUnitGroup().getNumber(), strUnit);
		if (unit!=null){
			entry.setUnit(unit);
		}else
		{
			isSucess=false;
			errMsg=errMsg + "单位不能为空\r\n";
		}
		entry.setBaseStatus(EntryBaseStatusEnum.NULL);//
		//
		entry.setAssCoefficient(BigDecimal.ZERO);
		//库存组织
		var storOrgUnit=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
		if(storOrgUnit!=null){
			entry.setStorageOrgUnit(storOrgUnit);
			entry.setReceiveStorageOrgUnit(storOrgUnit);
			
		}else
		{
			isSucess=false;
			errMsg=errMsg + "库存组织不能为空\r\n";
		}
		//财务组织
		entry.setCompanyOrgUnit(company);
		entry.setBalanceSupplier(sup);
		
		//仓库
		var stock=PublicBaseUtil.getWarehouseInfoByNumber(ctx, strStockNo);
		if(stock!=null){
			entry.setWarehouse(stock);
			entry.setOutWarehouse(stock);
		}else
		{
			isSucess=false;
			errMsg=errMsg + "仓库不能为空\r\n";
		}
		
		entry.setQty(bgQty);
		entry.setBaseQty(bgQty);
		bgTotalQty=bgTotalQty.add(bgQty);
		//entry.setPrice(bgPrice);
		//entry.setTaxAmount(bgAmount);
		//entry.setTaxPrice(bgTaxPrice);
		//entry.setTaxRate(bgTaxRate);
		//entry.setAmount(bgAmount);
		//entry.setLocalAmount(bgLocalAmount);
		//entry.setLocalPrice(bgLocalPrice);
		//entry.setLocalTax(bgLocalTax);
		//entry.setLocalTaxAmount(bgLocalTaxAmount);
		entry.setLot(strLot);
		//bgTotalAmount=bgTotalAmount.add(bgAmount);
		//bgTotalLocalAmount=bgTotalLocalAmount.add(bgLocalAmount);
		//bgTotalActualCost=bgTotalActualCost.add(bgLocalAmount);
		var invUpType=PublicBaseUtil.getInvUpdateTypeInfoByNumber(ctx, "001");
		entry.setInvUpdateType(invUpType);
		entry.setDosingType(DosingTypeEnum.MANUAL);
		entry.setUnWriteOffQty(bgQty);
		entry.setUnWriteOffBaseQty(bgQty);
		//entry.setUnWriteOffAmount(bgAmount); //未核销金额
		entry.setUnReturnedBaseQty(bgQty);
		
		//entry.setUnitActualCost(bgPrice); //单位实际成本
		//entry.setActualCost(bgAmount); //实际成本 
		entry.setPurOrderEntrySeq(0);
		//entry.setActualPrice(bgPrice); //实际单价
		//entry.setActualTaxPrice(bgTaxPrice); //实际含税单价
		//entry.setPurchaseCost(bgAmount); //采购成本
		//entry.setUnitPurchaseCost(bgPrice); //单位采购成本
		
		info.getEntries().addObject(entry);
		
		
		info.setTotalQty(bgTotalQty);
		//info.setTotalAmount(bgTotalAmount);
		//info.setTotalActualCost(bgTotalActualCost);
		//info.setTotalLocalAmount(bgTotalLocalAmount);
	}
	
	
	if(isSucess){
		var pk=PurInWarehsBillFactory.getLocalInstance(ctx).save(info);  //保存
		if(pk!=null){
//		    					Context ctxTemp=ctx.
			var tempIno=PurInWarehsBillFactory.getLocalInstance(ctx).getPurInWarehsBillInfo(pk);
			if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.TEMPORARILYSAVED)) {
				PurInWarehsBillFactory.getLocalInstance(ctx).submit(tempIno);
			}
			tempIno=PurInWarehsBillFactory.getLocalInstance(ctx).getPurInWarehsBillInfo(pk);
			if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
				PurInWarehsBillFactory.getLocalInstance(ctx).audit(pk);
			}
			
			methodCtx.getParam(2).setValue("true") ;
			methodCtx.getParam(4).setValue("新增成功");

		}
	}else
	{
		methodCtx.getParam(2).setValue("false") ;
		methodCtx.getParam(4).setValue(errMsg);

		//throw new BOSException(errMsg);
	}


	var strMsg="0110"+dwh+"CWCC";
	if(isSucess){
		methodCtx.getParam(2).setValue("true");
		var strData=String.format("%-81s", "A")+"\n" ;
		methodCtx.getParam(3).setValue(strMsg+strData);
	}else
	{
		methodCtx.getParam(2).setValue("false");
		methodCtx.getParam(4).setValue(dwt+errMsg);
		var strMsg="0110"+dwh+"CWCCB";
		var strData=String.format("%-80s", errMsg)+"\n" 
		methodCtx.getParam(3).setValue(strMsg+strData);
	}
	
}