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
	System.out.println("生产入库电文号"+dwh);
	var isSucess=true;
	methodCtx.getParam(2).setValue("false") ;
	var errMsg="";
	var strCreatorNo="user"; //创建人编码
	var strCUNo="01";// 管理单元编码
	var strBizDate=PublicBaseUtil.substringByte(socketMsg,"GBK" ,11,8); //业务日期
	strBizDate="20190417";
	var billStatus=BillBaseStatusEnum.ADD; //单据状态
	var strBizTypeNo="390";//业务类型编码
	var strBillTypeNo="105";//单据类型编码
	var strTranstionType="024";//事务类型
	//var strCurrencyNo ="BB01"; //币别编码
	//var bgExchangeRate=BigDecimal.ONE;// 汇率
	var bgTotalQty =BigDecimal.ZERO;// 数量
	
	//新增生产入库
    var info=new ManufactureRecBillInfo();
	info.put("bxNumber","机组生产产出电文CCCW04");
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
	//创建时间
	try {
		info.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
	} catch ( e) {
		
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
	// 事务类型
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
		info.setStorageOrgUnit(storeInfo);
		info.setProcessOrgUnit(storeInfo);
	}else
	{
		isSucess=false;
		errMsg=errMsg + "库存组织不能为空\r\n";
	}
	var id=storeInfo.getId().toString();
	// 成本中心
	var costcenter=PublicBaseUtil.getCostCenterOrgUnitInfoById(ctx,id);
	if(costcenter!=null){
		info.setCostCenterOrgUnit(costcenter);
		
	}else
	{
		isSucess=false;
		errMsg=errMsg + "成本中心不能为空\r\n";
	}
	// 倒冲标识
	info.setIsBackFlushSucceed(com.kingdee.eas.scm.im.inv.BackFlushSucceedEnum.NO_BACKFLUSH);
	// 财务组织
    var company=PublicBaseUtil.getCompany(ctx, cu.getNumber());
	var strMaterialNo="50901230082000";
	var strUnit="公斤（千克）";
	
	var strStockNo="001";
	var bgQty=new BigDecimal("0");
	var  bgKd=new BigDecimal("0");
	var bgHd=new BigDecimal("0");
    var rjh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,30,20);
    rjh=rjh.trim();
	rjh="201904176";
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
	var phmc="Appio";
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
	var kd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,156,5);
	if(kd!=null){
		kd=kd.trim();
	}else
	{
		kd="0";
	}
	try
	{
		//bgKd=new BigDecimal(kd).divide(new BigDecimal("10"));;
		bgKd=new BigDecimal("12020").divide(new BigDecimal("10"));
	}catch(e){
		
	}
	var hd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,150,6);
	
		hd=hd.trim();
	
	try
	{
		//bgHd=new BigDecimal(hd).divide(new BigDecimal("1000"));
		bgHd=new BigDecimal("082").divide(new BigDecimal("100"));
	}catch(e){
		
	}
	var zl=PublicBaseUtil.substringByte(socketMsg,"GBK" ,161,5);
	zl="20";
	if(zl!=null){
		zl=zl.trim();
	}else
	{
		zl="0";
	}
	try
	{
		bgQty=new BigDecimal(zl);
	}catch(e){
		
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
		//新增分录实体
		var entry=new ManufactureRecBillEntryInfo();
		//分录行号
		entry.setSeq(1);
		entry.setSourceBillEntrySeq(0);
		// 获取物料
		System.out.println("牌号"+ph);
		System.out.println("牌号名称"+phmc);
		System.out.println("宽度"+bgKd.toString());
		System.out.println("厚度"+bgHd.toString());
		System.out.println("单位"+strUnit);
		var material=SocketFacadeFactory.getLocalInstance(ctx).getMaterialInfo("01", "5", "09", ph, phmc, bgKd, bgHd, strUnit, "", true);
		System.out.println("牌号"+ph);
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
		entry.setAssCoefficient(BigDecimal.ZERO);
		//库存组织
		var storOrgUnit=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
		if(storOrgUnit!=null){
			entry.setStorageOrgUnit(storOrgUnit);
			
		}else
		{
			isSucess=false;
			errMsg=errMsg + "库存组织不能为空\r\n";
		}
		//财务组织
		entry.setCompanyOrgUnit(company);
		//成本中心
		entry.setCostCenterOrgUnit(costcenter);
		//仓库
		var stock=PublicBaseUtil.getWarehouseInfoByNumber(ctx, strStockNo);
		if(stock!=null){
			entry.setWarehouse(stock);
		}else
		{
			isSucess=false;
			errMsg=errMsg + "仓库不能为空\r\n";
		}
		entry.setQty(bgQty);
		entry.setBaseQty(bgQty);
		
		bgTotalQty=bgTotalQty.add(bgQty);
		info.setTotalQty(bgTotalQty);
		entry.setLot(strLot);
		var invUpType=PublicBaseUtil.getInvUpdateTypeInfoByNumber(ctx, "001");
		entry.setInvUpdateType(invUpType);
		entry.setAssociateQty(bgQty);
		var manuEntrys = new ManufactureRecBillEntryCollection();
		manuEntrys.add(entry);
		if (manuEntrys.size() > 0) {
			info.getEntries().addCollection(manuEntrys);
		}
	}
	if(isSucess){
		var pk=ManufactureRecBillFactory.getLocalInstance(ctx).save(info);  //保存
		if(pk!=null){
			//	Context ctxTemp=ctx.
			var tempIno=ManufactureRecBillFactory.getLocalInstance(ctx).getManufactureRecBillInfo(pk);
			if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.TEMPORARILYSAVED)) {
				ManufactureRecBillFactory.getLocalInstance(ctx).submit(tempIno);
			}
			tempIno=ManufactureRecBillFactory.getLocalInstance(ctx).getManufactureRecBillInfo(pk);
			if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
				ManufactureRecBillFactory.getLocalInstance(ctx).audit(pk);
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
	}else{
		methodCtx.getParam(2).setValue("false");
		methodCtx.getParam(4).setValue(dwt+errMsg);
		strMsg="0110"+dwh+"CWCCB";
		var strData=String.format("%-80s", errMsg)+"\n" 
		methodCtx.getParam(3).setValue(strMsg+strData);
	}
	
}