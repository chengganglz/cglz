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
	System.out.println("/******************************  CCCW08 消息记录开始   **********************************/");
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
	var dwh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,5,20);//电文号  从MES获取
	var dwt=PublicBaseUtil.substringByte(socketMsg,"GBK" ,1,29);//电文头  从MES获取
	// dwt="0524CCCW2020190224040527CCCWD11203778";
	System.out.println("CCCW08电文号："+dwh);
	var isSucess=true;
	methodCtx.getParam(2).setValue("false") ;
	var errMsg="";
	var strCreatorNo="user"; //创建人编码
	var strCUNo="01";// 管理单元编码
	var strBizDate=PublicBaseUtil.substringByte(socketMsg,"GBK" ,197,14) ; //业务日期  从MES获取
	// strBizDate="20190418";
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
	// var hetonghao="201904181619"; //合同号  从MES获取
	// var heyuehao="1619";//合约号  从MES获取
	// 合同号
	var hth=PublicBaseUtil.substringByte(socketMsg,"GBK" ,90,20);//  从MES获取
	System.out.println("合同号-hth:"+hth);
	// 合约号
	var hyh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,70,20);//  从MES获取
	System.out.println("合约号-hyh:"+hyh);
	var cu=PublicBaseUtil.getCU(ctx, strCUNo); 
	if(cu==null){
		isSucess=false;
		errMsg=errMsg + "管理单元不能为空\r\n";
	}
	var bizType=PublicBaseUtil.getBizTypeByNumber(ctx, strBizTypeNo);
	if(bizType==null){
		isSucess=false;
		errMsg=errMsg + "业务类型不能为空\r\n";
	}
	var billType=PublicBaseUtil.getBillTypeByNumber(ctx, strBillTypeNo);
	if(billType==null){
		isSucess=false;
		errMsg=errMsg + "单据类型不能为空\r\n";
	}
	var trantypeInfo=PublicBaseUtil.getTransactionTypeInfoByNumber(ctx, strTranstionType);
	if(trantypeInfo==null){
		isSucess=false;
		errMsg=errMsg + "事务类型不能为空\r\n";
	}
	var sup=PublicBaseUtil.getSupplier(ctx, strSupNo);
	if(sup==null){
		isSucess=false;
		errMsg=errMsg + "供应商不能为空\r\n";
	}
	var storeInfo=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strCUNo);
	if(storeInfo==null){
		isSucess=false;
		errMsg=errMsg + "库存组织不能为空\r\n";
	}
	var currency=PublicBaseUtil.getCurrencyInfo(ctx, strCurrencyNo);
	if(currency==null){
		isSucess=false;
		errMsg=errMsg + "币别不能为空\r\n";
	}
	var paymentInfo=PublicBaseUtil.getPaymentTypeInfoByNumber(ctx, strPaymentType);
	
	if(paymentInfo==null){
		isSucess=false;
		errMsg=errMsg + "付款方式不能为空\r\n";
	}
	// 财务组织
	var company=PublicBaseUtil.getCompany(ctx, cu.getNumber());
	if(company==null){
		isSucess=false;
		errMsg=errMsg + "财务组织不能为空\r\n";
	}
	var strMaterialNo="";
	var strUnit="吨";//单位默认千克
	
	// var strStockNo="001";//仓库编码  从MES获取
	var strStockNo=PublicBaseUtil.substringByte(socketMsg,"GBK" ,224,3);;//仓库编码  从MES获取	
	System.out.println("仓库编码-strStockNo:"+strStockNo);
	strStockNo=strStockNo.trim();
	// rjh="201904185";
	// if("".equals(strStockNo))
	// {
	// 	isSucess=false;
	// 	System.out.println("Error-仓库编码为空,无法进行,中断");
	// 	errMsg=errMsg + "仓库编码不能为空\r\n";
	// }
	var bgQty=new BigDecimal("0");
	var bgPrice=new BigDecimal("0");
	var bgTaxRate=new BigDecimal("0");
	var bgAmount=new BigDecimal("0");
	var  bgKd=new BigDecimal("0");
	var bgHd=new BigDecimal("0");
	var rjh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,30,20);//批次号  从MES获取
	// rjh="201904186";
	System.out.println("批次号/热卷号-rjh:"+rjh);
	rjh=rjh.trim();
	// rjh="201904185";
	if("".equals(rjh))
	{
		isSucess=false;
		System.out.println("Error-卷号为空,无法进行,中断");
		errMsg=errMsg + "卷号不能为空\r\n";
	}
	var strLot=rjh;
	// 入口卷号
	var rkjh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,50,20);//  从MES获取
	System.out.println("入口卷号-rkjh:"+rkjh);
	rkjh=rkjh.trim();
	//var ph=PublicBaseUtil.substringByte(socketMsg,"GBK" ,100,50);
	// var ph="0417";//牌号  从MES获取
	// if(ph!=null){
	// 	ph=ph.trim();
	// }
	// else
	// {
	// 	ph="";
	// }
	// if("".equals(ph))
	// {
	// 	isSucess=false;
	// 	errMsg=errMsg + "牌号不能为空\r\n";
	// }
	// // var phmc="Appprr";//牌号名称  从MES获取
	// if(phmc!=null){
	// 	phmc=phmc.trim();
	// }
	// else
	// {
	// 	phmc="";
	// }
	// if("".equals(phmc))
	// {
	// 	isSucess=false;
	// 	errMsg=errMsg + "牌号名称不能为空\r\n";
	// }
	var phmc=PublicBaseUtil.substringByte(socketMsg,"GBK" ,114,50); //牌号名称  从MES获取
	if(phmc!=null){
		phmc=phmc.trim();
	}else{
		phmc="";
	}
	if("".equals(phmc))
	{
		isSucess=false;
		System.out.println("Error-牌号名称不能为空,无法进行,中断");
		errMsg=errMsg + "牌号名称不能为空\r\n";
	}
	var ph=""; //牌号 从MES获取
	var sql = "SELECT * FROM CT_CUS_Paihao where fname_l2 ='"+phmc+"'";
	var rows = DbUtil.executeQuery(ctx, sql);
	if(rows!= null&&rows.next() ) {
		ph=rows.getString("fnumber");
	}else{
		ph=phmc;
	}
	System.out.println("牌号名称-phmc:"+phmc);
	System.out.println("牌号-ph:"+ph);
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
		System.out.println("Error-牌号不能为空,无法进行,中断");
		errMsg=errMsg + "牌号不能为空\r\n";
	}
	// 宽度
	var kd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,170,5);//宽度  从MES获取
	if(kd!=null){
		kd=kd.trim();
	}else
	{
		kd="0";
	}
	try
	{
		bgKd=new BigDecimal(kd).divide(new BigDecimal("10"));;
		// bgKd=BigDecimal.valueOf(1320);
	}catch(e){
		
	}
	// 厚度
	var hd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,164,6);//厚度  从MES获取
	if(hd!=null){
		hd=hd.trim();
	}else
	{
		hd="0";
	}
	try
	{
		bgHd=new BigDecimal(hd).divide(new BigDecimal("1000"));
		// bgHd=BigDecimal.valueOf(0.87);
	}catch(e){
		
	}
	// 重量
	var zl=PublicBaseUtil.substringByte(socketMsg,"GBK" ,187,10);//重量  从MES获取
	if(zl!=null){
		zl=zl.trim();
	}else
	{
		zl="0";
	}
	// zl="20";
	try
	{
		// 除1000 转吨
		bgQty=new BigDecimal(zl).divide(new BigDecimal('1000'));
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

	var bgTaxPrice=bgPrice.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
	var bgTaxAmount=bgAmount.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
	var bgTax=bgAmount.multiply(bgTaxRate).setScale(2, BigDecimal.ROUND_HALF_UP);
	var bgLocalTax=bgTax.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
	var bgLocalPrice =bgPrice.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
	var bgLocalAmount =bgAmount.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
	
	var bgLocalTaxPrice =bgTaxPrice.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
	var bgLocalTaxAmount =bgTaxAmount.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
	// var materialGroupNum="09";//物料组别  从MES获取
	var materialGroupNum=PublicBaseUtil.substringByte(socketMsg,"GBK" ,110, 4);//物料组别  从MES获取
	if(materialGroupNum!=null){
		materialGroupNum=materialGroupNum.trim();
	}
	else
	{
		materialGroupNum="";
	}
	if("".equals(materialGroupNum))
	{
		isSucess=false;
		System.out.println("Error-机组号不能为空,无法进行,中断");
		errMsg=errMsg + "机组号不能为空\r\n";
	}else{
		// 成本中心对应关系存储
		var temp_cbzx = '';
		cbzxNo = cbzxNo.trim()
		/**
		 * 
		 * mes机组号，mes名称，物料类别编码 对应关系
		 * L101 酸洗 09
		 * L701 镀锌 19
		 * L201 虚拟轧机
		 * L801 卷材开平
		*/
		if('L101'.equals(cbzxNo)){
			temp_cbzx = '09'
		}
		if('L701'.equals(cbzxNo)){
			temp_cbzx = '19'
		}
		if("".equals(temp_cbzx)){
			isSucess=false;
			System.out.println("Error-当前机组号没有找到对应的物料类别,无法进行,中断");
			errMsg=errMsg + "当前机组号没有找到对应的物料类别\r\n";
		}
		materialGroupNum = temp_cbzx;
	}
	
	// 作业计划号
	var zyjhh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,211,12);//  从MES获取
	System.out.println("作业计划号:"+zyjhh);
	// 委外方税号
	var wwfsh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,227,20);//  从MES获取
	System.out.println("委外方税号:"+wwfsh);
	/****************************************************************************/
	/**
	 * 时间:2019-4-24 15:34
	 * 修改人：刘振宇
	 * 修改内容：增加获取物料id
	 */
	// 物料id
	var wlid = '';
	// 物料实体
	var material = null 
	if(isSucess){
		System.out.println("MSG-允许获取物料");
		// 获取物料实体
		material =SocketFacadeFactory.getLocalInstance(ctx).getMaterialInfo("01", "5", materialGroupNum, ph, phmc, bgKd, bgHd, strUnit, "", true);
		if(material==null){
			System.out.println("Error-获取物料不存在，无法进行，中断");
			isSucess=false;
			errMsg=errMsg + "物料不能为空\r\n";
		}else{
			// 获取物料id
			wlid = material.getId().toString();
			// 在这里写插入
			if(isSucess){
				System.out.println("前置条件满足-允许插入生产产出记录");
				// 获取BOS类型				
				var bosid=com.kingdee.bos.util.BOSUuid.create("5E53BAD4");
				var id=bosid.toString();
				// 创建时间
				var format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				var fcreatetime = new java.util.Date();
				var fcreatetimestr=format.format(fcreatetime);
				// 生产时间格式化
				var sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				var scsjDate=sdf.parse(strBizDate);
				var scsj = format.format(scsjDate); 
				// 编码
				var fnumber = new java.util.Date().getTime().toString();
				// 数据库字段 fid,FNUMBER,FNAME_L2,fcreatorid,fcreatetime,fcontrolunitid,CFCKJH,CFHTH,CFHYH,CFJZH,CFZL,CFSCSJ,CFWLID,CFSFRK
				// 字段名称 唯一标识,编码,名称,创建id,创建时间,控制单元,出口卷号,合同号,合约号,机组号,重量,生产时间,物料id,是否入库,电文号,电文类型,入口卷号,作业计划号,委外方税号
				// 值 id,rjh,hth,'256c221a-0106-1000-e000-10d7c0a813f413B7DE7F',fcreatetimestr,'00000000-0000-0000-0000-000000000000CCE7AED4',rjh,hth,hyh,cbzxNo,bgQty,scsj,wlid,0,dwh,3,rkjh,zyjhh,wwfsh
				sql = "insert into CT_CUS_JZSCCCJL(fid,FNUMBER,FNAME_L2,fcreatorid,fcreatetime,fcontrolunitid,CFCKJH,CFHTH,CFHYH,CFJZH,CFZL,CFSCSJ,CFWLID,CFSFRK,CFDWH,CFDWLX) values('"+id+"','"+fnumber+"','"+fnumber+"','256c221a-0106-1000-e000-10d7c0a813f413B7DE7F',to_date('"+fcreatetimestr+"'),'00000000-0000-0000-0000-000000000000CCE7AED4','"+rjh+"','"+hth+"','"+hyh+"','"+cbzxNo+"','"+bgQty+"',to_date('"+scsj+"'),'"+wlid+"','0','"+dwh+"',3,'"+rkjh+"','"+zyjhh+"','"+wwfsh+"')"
				System.out.println("执行sql:"+sql);		
				try {
					DbUtil.execute(ctx, sql);
					System.out.println("操作成功-插入生产产出记录");
				} catch (error) {
					System.out.println("操作失败-插入生产产出记录失败");
				}		
				
			}
		}
	}
	/**
	 * 修改:不新增单据，仅入台账
	 * 时间：2019/4/24 14：48
	 * 刘振宇
	 */
	/*
	var material= SocketFacadeFactory.getLocalInstance(ctx).getMaterialInfo("01", "5", materialGroupNum, ph, phmc, bgKd, bgHd, strUnit, "", true);
	if(material==null){
		isSucess=false;
		errMsg=errMsg + "物料不能为空\r\n";
	}
	var storOrgUnit=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
	if(storOrgUnit==null){
		isSucess=false;
		errMsg=errMsg + "库存组织不能为空\r\n";
	}
	var stock=PublicBaseUtil.getWarehouseInfoByNumber(ctx, strStockNo);
	if(stock==null){
		isSucess=false;
		errMsg=errMsg + "仓库不能为空\r\n";
	}
	var invUpType=PublicBaseUtil.getInvUpdateTypeInfoByNumber(ctx, "001");
	if(invUpType==null){
		isSucess=false;
		errMsg=errMsg + "更新类型不能为空\r\n";
	}
	if(isSucess){
		//新增采购入库
		var info=new PurInWarehsBillInfo();
		info.put("bxNumber","委外生产产出电文CCC208");
		//制单人
		var creator=PublicBaseUtil.getUserByNumber(ctx, strCreatorNo);
		if(creator!=null){
			info.setCreator(creator);
		}else{
			info.setCreator(ContextUtil.getCurrentUserInfo(ctx));
		}
		//获取管理单元 
		if(cu!=null){
			info.setCU(cu); //控制单元
		}
		info.setPurchaseType(com.kingdee.eas.scm.common.PurchaseTypeEnum.SUBCONTRACT);//采购类别委外
		//创建时间
		try {
			info.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
		} catch ( e) {
			
		}
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
		if(bizType!=null){
			info.setBizType(bizType);
		}
		// 单据类型
		if(billType!=null){
			info.setBillType(billType);
		}
		//事务类型
		if(trantypeInfo!=null){
			info.setTransactionType(trantypeInfo);
		}
		//供应商
		if(sup!=null){
			info.setSupplier(sup);
		}
		//库存组织
		if(storeInfo!=null){
			info.setStorageOrgUnit(storeInfo);
		}
		//币别
		if(currency!=null){
			info.setCurrency(currency);
			info.setExchangeRate(bgExchangeRate);
		}
		
		// 付款方式
		if(paymentInfo!=null){
			info.setPaymentType(paymentInfo);
		}
		
		//是否含税
		info.setIsInTax(isInTax);
		//采购入库单分录 
    
		//big
		//新增分录实体
		var entry=new PurInWarehsEntryInfo();
		//分录行号
		entry.setSeq(1);
		entry.setSourceBillEntrySeq(0);
		entry.put("hetonghao",hetonghao);
		entry.put("heyuehao",heyuehao);
		
		//物料
		//var material=PublicBaseUtil.getMaterialInfoByNumber(ctx, info.getCU().getId().toString(), strMaterialNo);
		System.out.println("牌号"+ph);
		System.out.println("宽度"+bgKd);
		System.out.println("厚度"+bgHd);
		
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
		}
		
		entry.setBaseStatus(EntryBaseStatusEnum.NULL);//
		//
		entry.setAssCoefficient(BigDecimal.ZERO);
		//库存组织
		if(storOrgUnit!=null){
			entry.setStorageOrgUnit(storOrgUnit);
			entry.setReceiveStorageOrgUnit(storOrgUnit);
			
		}
		//财务组织
		entry.setCompanyOrgUnit(company);
		entry.setBalanceSupplier(sup);
		
		//仓库
		if(stock!=null){
			entry.setWarehouse(stock);
			entry.setOutWarehouse(stock);
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
	*/

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
	System.out.println("/******************************  CCCW08 消息记录结束   **********************************/");
}