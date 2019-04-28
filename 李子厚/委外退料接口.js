var imp = JavaImporter(); 
imp.importPackage(Packages.com.kingdee.eas.util);  
imp.importPackage(Packages.com.kingdee.bos); 
imp.importPackage(Packages.java.text);  
imp.importPackage(Packages.java.lang);  
imp.importPackage(Packages.com.kingdee.eas.custom.socketjk);   
imp.importPackage(Packages.java.sql);  
imp.importPackage(Packages.com.kingdee.bos.dao);  
imp.importPackage(Packages.com.kingdee.eas.base.permission);  
imp.importPackage(Packages.com.kingdee.eas.basedata.assistant);  
imp.importPackage(Packages.com.kingdee.eas.basedata.master.cssp);  
imp.importPackage(Packages.com.kingdee.eas.basedata.master.material); 
imp.importPackage(Packages.com.kingdee.eas.basedata.org);  
imp.importPackage(Packages.com.kingdee.eas.basedata.person);  
imp.importPackage(Packages.com.kingdee.eas.basedata.scm.common);  
imp.importPackage(Packages.com.kingdee.eas.basedata.scm.sd.sale);  
imp.importPackage(Packages.com.kingdee.eas.common);  
imp.importPackage(Packages.com.kingdee.eas.scm.common);  
imp.importPackage(Packages.com.kingdee.eas.scm.sd.sale);   
imp.importPackage(Packages.com.kingdee.eas.util);   
imp.importPackage(Packages.com.kingdee.eas.util.app);   
imp.importPackage(Packages.java.math); 
imp.importPackage(Packages.java.util);  
imp.importPackage(Packages.com.kingdee.bos.dao.query);  
imp.importPackage(Packages.com.kingdee.jdbc.rowset);
imp.importPackage(Packages.com.kingdee.bos.metadata.query.util);
imp.importPackage(Packages.com.kingdee.bos.util);
imp.importPackage(Packages.com.kingdee.eas.util.client);
imp.importPackage(Packages.com.kingdee.bos.ctrl.swing);
imp.importPackage(Packages.com.kingdee.bos.metadata.entity);
imp.importPackage(Packages.com.kingdee.eas.scm.im);
imp.importPackage(Packages.com.kingdee.eas.scm.im.inv);
imp.importPackage(Packages.com.kingdee.eas.basedata.scm.im.inv);
imp.importPackage(Packages.com.kingdee.eas.util);
imp.importPackage(Packages.com.kingdee.bos.dao.ormapping);
with(imp){
	var socketMsg = methodCtx.getParamValue(0);  //接收报文 
	//var billID = methodCtx.getParamValue(1); // 业务单元ID  
	var result = methodCtx.getParamValue(2); // 成功失败  true:false  必须有返回值  methodCtx.getParam(2).setValue("true") 
	var sendMsg = methodCtx.getParamValue(3); // 发送报文   如果需要发送电文,将电文拼好放到这里返回  methodCtx.getParam(3).setValue("")
  	var errMsg = methodCtx.getParamValue(4); //错误消息 methodCtx.getParam(4).setValue("")  
	var ctx = pluginCtx.getContext(); //服务端上下文 
	var isSucess=true;
	var errMsg="";
	var strCreatorNo="user"; //创建人编码
	var strCUNo="01";// 管理单元编码
	var strBizDate="20190417"; //业务日期
	var billStatus=BillBaseStatusEnum.ADD; //单据状态
	var purchaseType = PurchaseTypeEnum.SUBCONTRACT;
	var strBizTypeNo="131";//业务类型编码
	var strBillTypeNo="104";//单据类型编码
	var strTranstionType="023";//事务类型
	var strNeedStockOrgNo="01";//需方库存组织
	//String strSupNo="1000"; //供应商编码
	//String strCurrencyNo ="BB01"; //币别编码
	//String strPaymentType="004";// 付款方式
	//BigDecimal bgExchangeRate=BigDecimal.ONE;// 汇率
	//BigDecimal bgTotalQty =BigDecimal.ZERO;// 数量
	//BigDecimal bgTotalAmount =BigDecimal.ZERO;// 金额
	//BigDecimal bgTotalActualCost =BigDecimal.ZERO;// 实际成本
	//BigDecimal bgTotalLocalAmount =BigDecimal.ZERO;// 本位币金额
	var dwh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,5,20);
	var dwt=PublicBaseUtil.substringByte(socketMsg,"GBK" ,0,29);
	dwt="0524CCCW2020190224040527CCCWD11203778";
	System.out.println("生产退料电文号"+dwh);
	
	var  isInTax=true;
	var sdfDate = new SimpleDateFormat("yyyy-MM-dd");
	
	
	//新增委外领料
	var info=new com.kingdee.eas.scm.im.inv.MaterialReqBillInfo();
	//制单人
	var creator=PublicBaseUtil.getUserByNumber(ctx, strCreatorNo);
	if(creator!=null){
		info.setCreator(creator);
	}else{
		info.setCreator(ContextUtil.getCurrentUserInfo(ctx));
	}

	info.setPurchaseType(com.kingdee.eas.scm.common.PurchaseTypeEnum.SUBCONTRACT);
	//获取管理单元
	var cu=PublicBaseUtil.getCU(ctx, strCUNo); 
	if(cu!=null){
		info.setCU(cu); //控制单元
	}else
	{
		isSucess=false;
		errMsg=errMsg + "管理单元不能为空\r\n";
	}

	var supInfo = PublicBaseUtil.getSupplier(ctx, "1000");
	if(supInfo!=null){
		info.setSupplier(supInfo);
		
	}else
	{
		isSucess=false;
		errMsg=errMsg + "供应商不能为空\r\n";
	}
	//创建时间
	try {
		info.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
	} catch (e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//业务日期
	try
	{
		var bizDate;
		var sdf = new SimpleDateFormat("yyyyMMdd");
		bizDate=sdf.parse(strBizDate);
		info.setBizDate(bizDate);
		var c = Calendar.getInstance();
		c.setTime(bizDate);
		var year=c.get(Calendar.YEAR);   //年度
		var period=c.get(Calendar.MONTH);
		var month =c.get(Calendar.YEAR)*100+c.get(Calendar.MONTH) ; //期间
		var iday=(c.get(Calendar.YEAR)*100+c.get(Calendar.MONTH)+1)*100+c.get(Calendar.MONDAY);
		info.setYear(year);
		info.setPeriod(period);
		info.setMonth(month);
		info.setDay(iday);
	}catch(ex){
		isSucess=false;
		errMsg=errMsg + "日期格式不正确\r\n";
		//throw new BOSException("日期格式不正确");
		
	}
	//单据状态
	info.setBaseStatus(billStatus);
	info.setPurchaseType(purchaseType);
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
	
	//库存组织
	var storeInfo=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strCUNo);
	if(storeInfo!=null){
		info.setSupplyStoreOrgUnit(storeInfo);
	}else
	{
		isSucess=false;
		errMsg=errMsg + "供方库存组织不能为空\r\n";
	}
	
	var demandInfo=PublicBaseUtil.getCompany(ctx, strCUNo);
	if(storeInfo!=null){
		info.setDemandCompanyOrgUnit(demandInfo);
	}else
	{
		isSucess=false;
		errMsg=errMsg + "需方财务组织不能为空\r\n";
	}
	var supplyInfo=PublicBaseUtil.getCompany(ctx, strCUNo);
	if(storeInfo!=null){
		info.setSupplyCompanyOrgUnit(supplyInfo);
	}else
	{
		isSucess=false;
		errMsg=errMsg + "供方财务组织不能为空\r\n";
	}
	
	
	// 财务组织
	var company=PublicBaseUtil.getCompany(ctx, cu.getNumber());
	if(company!=null){
		
		
	}else
	{
		isSucess=false;
		errMsg=errMsg + "财务组织不能为空\r\n";
	}
	//主业务组织
	var  storageInfo=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strCUNo);
	if(storeInfo!=null){
		info.setStorageOrgUnit(storageInfo);
	}else
	{
		isSucess=false;
		errMsg=errMsg + "主业务组织不能为空\r\n";
	}

	var costCInfo=PublicBaseUtil.getCostCenterOrgUnitInfoById(ctx, cu.getId().toString());
	if(costCInfo!=null){
		info.setCostCenterOrgUnit(costCInfo);
		//entry.setReceiveStorageOrgUnit(storOrgUnit);
		
	}else
	{
		isSucess=false;
		errMsg=errMsg + "成本中心不能为空\r\n";
	}
	//是否含税
	//info.setIsInTax(isInTax);
	

	//委外领料单分录 
	var strMaterialNo="50604160811220";
	var strUnit="公斤（千克）";
	var strLot="201904171";
	var strStockNo="001";
	var bgQty=new BigDecimal("40");
	var fushu=new BigDecimal("-1");
	var fubgQty=bgQty.multiply(fushu);
	var bgPrice=new BigDecimal("10");
	var bgTaxRate=new BigDecimal("0");
	var bgAmount=new BigDecimal("100");
	
	var bgTaxPrice=bgPrice.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
	var bgTaxAmount=bgAmount.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
	var bgTax=bgAmount.multiply(bgTaxRate).setScale(2, BigDecimal.ROUND_HALF_UP);
	
	//big
	//新增分录实体
	var entry=new MaterialReqBillEntryInfo();
	//分录行号
	entry.setSeq(1);
	entry.setSourceBillEntrySeq(0);

	//物料
	var material=PublicBaseUtil.getMaterialInfoByNumber(ctx, info.getCU().getId().toString(), strMaterialNo);
	if (material!=null){
		entry.setMaterial(material);
		entry.setBaseUnit(material.getBaseUnit());
		//单位
		var unit=PublicBaseUtil.getMeasureUnitInfoByName(ctx, material.getBaseUnit().getMeasureUnitGroup().getNumber(), strUnit);
		if (unit!=null){
			entry.setUnit(unit);
		}else
		{
			isSucess=false;
			errMsg=errMsg + "单位不能为空\r\n";
		}
		
	}else
	{
		isSucess=false;
		errMsg=errMsg + "物料不能为空\r\n";
	}
	
	entry.setBaseStatus(EntryBaseStatusEnum.NULL);//
	//
	entry.setAssCoefficient(BigDecimal.ZERO);
	//库存组织
	var storOrgUnit=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
	if(storOrgUnit!=null){
		entry.setStorageOrgUnit(storOrgUnit);
		//entry.setReceiveStorageOrgUnit(storOrgUnit);
		
	}else
	{
		isSucess=false;
		errMsg=errMsg + "库存组织不能为空\r\n";
	}
	//财务组织
	entry.setCompanyOrgUnit(company);
	//entry.setBalanceSupplier(sup);
	var costCenterInfo=PublicBaseUtil.getCostCenterOrgUnitInfoById(ctx, cu.getId().toString());
	if(costCenterInfo!=null){
		entry.setCostCenterOrgUnit(costCenterInfo);
		//entry.setReceiveStorageOrgUnit(storOrgUnit);
		
	}else
	{
		isSucess=false;
		errMsg=errMsg + "成本中心不能为空\r\n";
	}

	
	
	//仓库
	var stock=PublicBaseUtil.getWarehouseInfoByNumber(ctx, strStockNo);
	if(stock!=null){
		entry.setWarehouse(stock);
		//entry.setOutWarehouse(stock);
	}else
	{
		isSucess=false;
		errMsg=errMsg + "仓库不能为空\r\n";
	}
	
	entry.setQty(fubgQty);
	entry.setBaseQty(fubgQty);
	entry.setAssociateQty(fubgQty);
	entry.setUnReturnedBaseQty(bgQty);
	entry.setSubUnWriteOffQty(fubgQty);
	entry.setSubUnWriteOffBaseQty(fubgQty);
	
	//entry.setPrice(bgPrice);
	//entry.setTaxPrice(bgTaxPrice);
	//entry.setTaxRate(bgTaxRate);
	//entry.setAmount(bgAmount);
	entry.setLot(strLot);
    var invInfo = null;
	var evi = new EntityViewInfo();
	var filter = new FilterInfo();
	filter.getFilterItems().add(new FilterItemInfo("id", "8r0AAAAEaOnC73rf"));
	evi.setFilter(filter);
	
	var sic = new SelectorItemCollection();
	sic.add("*");
	sic.add("storeTypePre.*");
	sic.add("storeTypePre.storeFlag.*");
	evi.setSelector(sic);
	
	var iinv = null;
	if(ctx != null){
		iinv = InvUpdateTypeFactory.getLocalInstance(ctx);    			
	}else{
		iinv = InvUpdateTypeFactory.getRemoteInstance();
	}
	
	var users = iinv.getInvUpdateTypeCollection(evi);
	if(users != null && users.size() != 0){
		invInfo = users.get(0);
	}
	
	
	invInfo.getStoreTypePre();
	System.out.println("委外领料接口1："+invInfo.getStoreTypePre().getId());
	System.out.println("委外领料接口2："+invInfo.getStoreTypePre().getStoreFlag());
	//var a=StoreFlagEnum.SUPPLIER;
	//var b=StoreFlagEnum.TOSUPPLIER;
	//var invUpType=PublicBaseUtil.getInvUpdateTypeInfoByNumber(ctx, "001");
	entry.setInvUpdateType(invInfo);
	
	var materialReqBillEntrys = new MaterialReqBillEntryCollection();
	materialReqBillEntrys.add(entry);
	System.out.println("委外领料接口3："+materialReqBillEntrys.get(0).getInvUpdateType().getStoreTypePre().getStoreFlag());
	System.out.println("委外领料接口31："+materialReqBillEntrys.size());
	info.getEntry().addCollection(materialReqBillEntrys);
	System.out.println("委外领料接口4："+info.getEntry().get(0).getInvUpdateType().getStoreTypePre().getStoreFlag());
	System.out.println("委外领料接口5："+info.getEntry().size());
	System.out.println("委外领料接口6："+info.getEntry().get(0).getInvUpdateType().getStoreTypePre().getStoreFlag());
	
	if(isSucess){
		var pk=MaterialReqBillFactory.getLocalInstance(ctx).save(info);  //保存
		if(pk!=null){
//		    					Context ctxTemp=ctx.
			var tempIno=MaterialReqBillFactory.getLocalInstance(ctx).getMaterialReqBillInfo(pk);
			tempIno.getEntry().get(0).setInvUpdateType(invInfo);
			System.out.println("委外领料接口7："+tempIno.getEntry().size());
			if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.TEMPORARILYSAVED)) {
				MaterialReqBillFactory.getLocalInstance(ctx).submit(tempIno);
			}
			tempIno=MaterialReqBillFactory.getLocalInstance(ctx).getMaterialReqBillInfo(pk);
			if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
				MaterialReqBillFactory.getLocalInstance(ctx).audit(pk);
			}
			
			
		}
	}else
	{
		methodCtx.getParam(2).setValue("false");
		methodCtx.getParam(4).setValue(errMsg);
	}
	
	var strMsg="0110"+dwh+"CWCC";
	if(isSucess){
		methodCtx.getParam(2).setValue("true");
		var strData=String.format("%-81s", "A")+"\n";
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