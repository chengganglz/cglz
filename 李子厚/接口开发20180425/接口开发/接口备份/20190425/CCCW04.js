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
	System.out.println("/******************************  CCCW04消息记录开始   **********************************/");
	var socketMsg = methodCtx.getParamValue(0);  //接收报文 
	var billID = methodCtx.getParamValue(1); // 业务单元ID  
	var result = methodCtx.getParamValue(2); //成功失败  true:false  必须有返回值  methodCtx.getParam(2).setValue("true") 
	var sendMsg = methodCtx.getParamValue(3); // 发送报文   如果需要发送电文,将电文拼好放到这里返回  methodCtx.getParam(3).setValue("")
  	var errMsg = methodCtx.getParamValue(4); //错误消息 methodCtx.getParam(4).setValue("")  
	var ctx = pluginCtx.getContext(); //服务端上下文 
	// socketMsg = '0193CCCW0420190424104238CCCWDL11940001600        192015240200                  X190202018          L101DD11                                              00292015000000002700020190424094611   0 ';
	// socketMsg = "0193CCCW0420190424153923CCCWDL11940002400        192021020700                  X190326001          L101DD11                                              00350015000000002770020190424153003   0 "
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
	System.out.println("电文号-dwh:"+dwh);
	var dwt=PublicBaseUtil.substringByte(socketMsg,"GBK" ,1,29);//电文头  从MES获取
	System.out.println("电文头-dwt:"+dwt);
	// dwt="0524CCCW2020190224040527CCCWD11203778";
	System.out.println("生产入库电文号"+dwh);
	var isSucess=true;
	methodCtx.getParam(2).setValue("false") ;
	var errMsg="";
	var strCreatorNo="user"; //创建人编码
	var strCUNo="01";// 管理单元编码
	var strBizDate=PublicBaseUtil.substringByte(socketMsg,"GBK" ,11,8); //业务日期  从MES获取
	System.out.println("业务日期-strBizDate:"+strBizDate);
	// strBizDate="20190418";
	var billStatus=BillBaseStatusEnum.ADD; //单据状态
	var strBizTypeNo="390";//业务类型编码
	var strBillTypeNo="105";//单据类型编码
	var strTranstionType="024";//事务类型
	//var strCurrencyNo ="BB01"; //币别编码
	//var bgExchangeRate=BigDecimal.ONE;// 汇率
	var bgTotalQty =BigDecimal.ZERO;// 数量
	var cu=PublicBaseUtil.getCU(ctx, strCUNo); 
	if(cu==null){
		isSucess=false;
		errMsg=errMsg + "管理单元不能为空\r\n";
	}
	// 业务类型
	var bizType=PublicBaseUtil.getBizTypeByNumber(ctx, strBizTypeNo);
	if(bizType==null){
		isSucess=false;
		errMsg=errMsg + "业务类型不能为空\r\n";
	}
	// 单据类型
	var billType=PublicBaseUtil.getBillTypeByNumber(ctx, strBillTypeNo);
	if(billType==null){
		isSucess=false;
		errMsg=errMsg + "单据类型不能为空\r\n";
	}
	// 事务类型
	var trantypeInfo=PublicBaseUtil.getTransactionTypeInfoByNumber(ctx, strTranstionType);
	if(trantypeInfo==null){
		isSucess=false;
		errMsg=errMsg + "事务类型不能为空\r\n";
	}
	//库存组织
	var storeInfo=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strCUNo);
	if(storeInfo==null){
		isSucess=false;
		errMsg=errMsg + "库存组织不能为空\r\n";
	}
	// 成本中心编码
	var cbzxNo = PublicBaseUtil.substringByte(socketMsg,"GBK" ,100,4);
	// 成本中心实体
	var costcenter = null;
	System.out.println("成本中心编码-cbzxNo:"+cbzxNo);
	if("".equals(cbzxNo))
	{
		isSucess=false;
		errMsg=errMsg + "成本中心不能为空\r\n";
	}else{
		// 成本中心对应关系存储
		var temp_cbzx = '';
		cbzxNo = cbzxNo.trim()
		/**
		 * 
		 * 机组号，成本中心编码 对应关系
		 * L101 酸洗 007
		 * L701 镀锌 008
		 * L201 虚拟轧机
		 * L801 卷材开平
		*/
		if('L101'.equals(cbzxNo)){
			temp_cbzx = '007'
		}
		if('L701'.equals(cbzxNo)){
			temp_cbzx = '008'
		}
		if("".equals(temp_cbzx)){
			isSucess=false;
			System.out.println("Error-当前机组号没有找到对应的成本中心,无法进行,中断");
			errMsg=errMsg + "当前机组号没有找到对应的成本中心\r\n";
		}else{
			costcenter = PublicBaseUtil.getCostCenterOrgUnitInfoByNumber(ctx,temp_cbzx);//成本中心编码  从MES获取
			if(costcenter==null){
				System.out.println("Error-成本中心为空,无法进行,中断");
				isSucess=false;
				errMsg=errMsg + "成本中心不能为空\r\n";
			}
		}
	}
	var strMaterialNo="";
	var strUnit="吨";//单位默认千克
	
	// var strStockNo="001";//仓库编码  从MES获取
	var strStockNo=PublicBaseUtil.substringByte(socketMsg,"GBK" ,189,3);;//仓库编码  从MES获取	
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
	var  bgKd=new BigDecimal("0");
	var bgHd=new BigDecimal("0");
	var rjh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,30,20);//批次号  从MES获取
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
	var rkjh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,50,20);//批次号  从MES获取
	System.out.println("入口卷号-rkjh:"+rkjh);
	rkjh=rkjh.trim();
	// rjh="201904185";
	// if("".equals(rjh))
	// {
	// 	isSucess=false;
	// 	System.out.println("Error-卷号为空,无法进行,中断");
	// 	errMsg=errMsg + "卷号不能为空\r\n";
	// }
	// 合同号
	var hth=PublicBaseUtil.substringByte(socketMsg,"GBK" ,70,10);//  从MES获取
	System.out.println("合同号-hth:"+hth);
	// hth=hth.trim();	
	// if("".equals(hth))
	// {
	// 	isSucess=false;
	// 	System.out.println("Error-合同号为空,无法进行,中断");
	// 	errMsg=errMsg + "合同号不能为空\r\n";
	// }
	//
	// 合约号
	var hyh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,80,20);//  从MES获取
	System.out.println("合约号-hyh:"+hyh);
	hyh=hyh.trim();	
	if("".equals(hyh))
	{
		isSucess=false;
		System.out.println("Error-合约号为空,无法进行,中断");
		errMsg=errMsg + "合约号不能为空\r\n";
	}
    
	/*
	//var ph=PublicBaseUtil.substringByte(socketMsg,"GBK" ,100,50);
	var ph="0417";//牌号  从MES获取
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
	var phmc="Appio";//牌号名称  从MES获取
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
	*/
	var phmc=PublicBaseUtil.substringByte(socketMsg,"GBK" ,104,50); //牌号名称  从MES获取
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
	var kd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,160,5);//宽度  从MES获取
	System.out.println("宽度-kd:"+kd);
	if(kd!=null){
		kd=kd.trim();
	}else
	{
		kd="0";
	}
	try
	{
		//bgKd=new BigDecimal(kd).divide(new BigDecimal("10"));;
		bgKd=new BigDecimal(kd).divide(new BigDecimal("10"));
	}catch(e){
		
	}
	var hd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,154,6);//厚度  从MES获取
	System.out.println("厚度-hd:"+hd);
	if(hd!=null){
		hd=hd.trim();
	}else
	{
		hd="0";
	}
	try
	{
		bgHd=new BigDecimal(hd).divide(new BigDecimal("1000"));
		// bgHd=new BigDecimal(hd).divide(new BigDecimal("100"));
	}catch(e){
		
	}
	var zl=PublicBaseUtil.substringByte(socketMsg,"GBK" ,165,10);//重量  从MES获取
	System.out.println("重量-zl:"+zl);
	// zl="20";
	if(zl!=null){
		zl=zl.trim();
	}else
	{
		zl="0";
	}
	try
	{
		// 重量/1000 转吨
		bgQty=new BigDecimal(zl).divide(new BigDecimal(1000));
	}catch(e){
		
	}
	if (bgKd.compareTo(BigDecimal.ZERO)==0){
		isSucess=false;
		System.out.println("宽度不能为零-bgKd:"+bgKd);
		errMsg=errMsg + "宽度不能为零\r\n";
	}
	if (bgHd.compareTo(BigDecimal.ZERO)==0){
		isSucess=false;
		System.out.println("厚度不能为零-bgHd:"+bgHd);
		errMsg=errMsg + "厚度不能为零\r\n";
	}
	if (bgQty.compareTo(BigDecimal.ZERO)==0){
		isSucess=false;
		System.out.println("重量不能为零-bgQty:"+bgQty);
		errMsg=errMsg + "重量不能为零\r\n";
	}
	// var materialGroupNum="09";//物料组别  //从机组号中读取,取首字母
	// var materialGroupNum = PublicBaseUtil.substringByte(socketMsg,"GBK" ,100,4);
	// System.out.println("物料组别-materialGroupNum:"+materialGroupNum);
	
	var materialGroupNum=PublicBaseUtil.substringByte(socketMsg,"GBK" ,100, 4);//物料组别  从MES获取
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
	System.out.println("物料组别-materialGroupNum:"+materialGroupNum);
	// if("X".equals(materialGroupNum)){
	// 	materialGroupNum="09";
	// }else if("M".equals(materialGroupNum)){
	// 	materialGroupNum="19";
	// }
	/****************************************************************************/
	/**
	 * 时间:2019-4-23 20:07
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
				var sdf = new SimpleDateFormat("yyyyMMdd");
				var scsjDate=sdf.parse(strBizDate);
				var scsj = format.format(scsjDate); 
				// 编码
				var fnumber = new java.util.Date().getTime().toString();
				// 数据库字段 fid,FNUMBER,FNAME_L2,fcreatorid,fcreatetime,fcontrolunitid,CFCKJH,CFHTH,CFHYH,CFJZH,CFZL,CFSCSJ,CFWLID,CFSFRK
				// 字段名称 唯一标识,编码,名称,创建id,创建时间,控制单元,出口卷号,合同号,合约号,机组号,重量,生产时间,物料id,是否入库,电文号,电文类型,入口卷号
				// 值 id,rjh,hth,'256c221a-0106-1000-e000-10d7c0a813f413B7DE7F',fcreatetimestr,'00000000-0000-0000-0000-000000000000CCE7AED4',rjh,hth,hyh,cbzxNo,bgQty,scsj,wlid,0,dwh,1,rkjh
				sql = "insert into CT_CUS_JZSCCCJL(fid,FNUMBER,FNAME_L2,fcreatorid,fcreatetime,fcontrolunitid,CFCKJH,CFHTH,CFHYH,CFJZH,CFZL,CFSCSJ,CFWLID,CFSFRK,CFDWH,CFDWLX,CFRKJH) values('"+id+"','"+fnumber+"','"+fnumber+"','256c221a-0106-1000-e000-10d7c0a813f413B7DE7F',to_date('"+fcreatetimestr+"'),'00000000-0000-0000-0000-000000000000CCE7AED4','"+rjh+"','"+hth+"','"+hyh+"','"+cbzxNo+"','"+bgQty+"',to_date('"+scsj+"'),'"+wlid+"','0','"+dwh+"',1,'"+rkjh+"')"
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
	/****************************************************************************/
	/*
	//库存组织
	var storOrgUnit=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
	if(storOrgUnit==null){
		isSucess=false;
		System.out.println("Error-库存组织不能为空,无法进行,中断");
		errMsg=errMsg + "库存组织不能为空\r\n";
	}
	var stock=PublicBaseUtil.getWarehouseInfoByNumber(ctx, strStockNo);
	if(stock==null){
		isSucess=false;
		System.out.println("Error-仓库不能为空,无法进行,中断");
		errMsg=errMsg + "仓库不能为空\r\n";
	}
	var invUpType=PublicBaseUtil.getInvUpdateTypeInfoByNumber(ctx, "001");
	if(invUpType==null){
		isSucess=false;
		System.out.println("Error-更新类型不能为空,无法进行,中断");
		errMsg=errMsg + "更新类型不能为空\r\n";
	}
	
	if(isSucess){
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
		if(cu!=null){
			info.setCU(cu); //控制单元
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
		if(bizType!=null){
			info.setBizType(bizType);
		}
		// 单据类型
		if(billType!=null){
			info.setBillType(billType);
		}
		// 事务类型
		if(trantypeInfo!=null){
			info.setTransactionType(trantypeInfo);
		}
		//库存组织
		if(storeInfo!=null){
			info.setStorageOrgUnit(storeInfo);
			info.setProcessOrgUnit(storeInfo);
		}
		var id=storeInfo.getId().toString();
		// 成本中心
		if(costcenter!=null){
			info.setCostCenterOrgUnit(costcenter);
			
		}
		// 倒冲标识
		info.setIsBackFlushSucceed(com.kingdee.eas.scm.im.inv.BackFlushSucceedEnum.NO_BACKFLUSH);
		// 财务组织
		var company=PublicBaseUtil.getCompany(ctx, cu.getNumber());
		
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
		}
		
		entry.setBaseStatus(EntryBaseStatusEnum.NULL);//
		entry.setAssCoefficient(BigDecimal.ZERO);
		//库存组织
		if(storOrgUnit!=null){
			entry.setStorageOrgUnit(storOrgUnit);
			
		}
		//财务组织
		entry.setCompanyOrgUnit(company);
		//成本中心
		entry.setCostCenterOrgUnit(costcenter);
		//仓库
		if(stock!=null){
			entry.setWarehouse(stock);
		}
		entry.setQty(bgQty);
		entry.setBaseQty(bgQty);
		
		bgTotalQty=bgTotalQty.add(bgQty);
		info.setTotalQty(bgTotalQty);
		entry.setLot(strLot);
		
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
	*/
	System.out.println("/******************************  CCCW04消息记录结束   **********************************/");
	var strMsg="0110"+dwh+"CWCC";
	methodCtx.getParam(2).setValue("true");
	var strData=String.format("%-81s", "A")+"\n" ;
	methodCtx.getParam(3).setValue(strMsg+strData);
	/*if(isSucess){
		methodCtx.getParam(2).setValue("true");
		var strData=String.format("%-81s", "A")+"\n" ;
		methodCtx.getParam(3).setValue(strMsg+strData);
	}else{
		methodCtx.getParam(2).setValue("false");
		methodCtx.getParam(4).setValue(dwt+errMsg);
		strMsg="0110"+dwh+"CWCCB";
		var strData=String.format("%-80s", errMsg)+"\n" 
		methodCtx.getParam(3).setValue(strMsg+strData);
	}*/
	
}