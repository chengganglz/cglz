var imp = JavaImporter();  
imp.importPackage(Packages.com.kingdee.eas.util.app);  
imp.importPackage(Packages.org.apache.log4j); 
imp.importPackage(Packages.com.kingdee.bos); 
imp.importPackage(Packages.com.kingdee.bos.dao); 
imp.importPackage(Packages.com.kingdee.bos.dao.ormapping);
imp.importPackage(Packages.com.kingdee.eas.base.permission); 
imp.importPackage(Packages.com.kingdee.eas.basedata.assistant); 
imp.importPackage(Packages.com.kingdee.eas.basedata.master.material); 
imp.importPackage(Packages.com.kingdee.eas.basedata.org); 
imp.importPackage(Packages.com.kingdee.eas.basedata.scm.common); 
imp.importPackage(Packages.com.kingdee.eas.basedata.scm.im.inv); 
imp.importPackage(Packages.com.kingdee.eas.common); 
imp.importPackage(Packages.com.kingdee.eas.custom.socketjk); 
imp.importPackage(Packages.com.kingdee.eas.scm.common); 
imp.importPackage(Packages.com.kingdee.eas.scm.im.inv); 
imp.importPackage(Packages.com.kingdee.eas.util); 
imp.importPackage(Packages.java.lang); 
imp.importPackage(Packages.java.math); 
imp.importPackage(Packages.java.sql); 
imp.importPackage(Packages.java.text); 
imp.importPackage(Packages.java.util); 

with(imp){ 
	System.out.println("/******************************  CCCW11 消息记录开始   **********************************/");
	var socketMsg = methodCtx.getParamValue(0);  //接收报文 
	// var socketMsg ="0156CCCW1120190424091613CCCWDL1613CCCWDL11920009300                                      L101                                                  0020201250000000000001 ";
	var billID = methodCtx.getParamValue(1); // 业务单元ID  
	var result = methodCtx.getParamValue(2); //成功失败  true:false  必须有返回值  methodCtx.getParam(2).setValue("true") 
	var sendMsg = methodCtx.getParamValue(3); // 发送报文   如果需要发送电文,将电文拼好放到这里返回  methodCtx.getParam(3).setValue("")
  	var errMsg = methodCtx.getParamValue(4); //错误消息 methodCtx.getParam(4).setValue("")  
	var ctx = pluginCtx.getContext(); //服务端上下文 
	var isSucess=true;
	var errMsg="";
	var strCreatorNo="user"; //创建人编码
	var strCUNo="01";// 管理单元编码
	//var strBizDate="20190418" ; //业务日期  从MES获取
	var billStatus=BillBaseStatusEnum.ADD; //单据状态
	var purchaseType = PurchaseTypeEnum.PURCHASE;
	var strBizTypeNo="501";//业务类型编码
	var strBillTypeNo="108";//单据类型编码
	var strTranstionType="032";//事务类型
	var strNeedStockOrgNo="01";//需方库存组织
	var  isInTax=true;
	var sdfDate = new SimpleDateFormat("yyyy-MM-dd");
	var dwh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,5,20);//电文号  从MES获取
	//dwh="CCCW0120190418110558";
	System.out.println("电文号"+dwh);
	var dwt=PublicBaseUtil.substringByte(socketMsg,"GBK" ,1,29);//电文头  从MES获取
	//dwt="0524CCCW2020190224040527CCCWD11203778";
	System.out.println("生产退料电文号"+dwt);
	var flag=PublicBaseUtil.substringByte(socketMsg,"GBK",165,1);//操作标志  从MES获取
	if(flag!=null){
		flag = flag.trim();
	}
	System.out.println("操作标志"+flag);
	// 1是盘盈，2是盘亏
	if("1".equals(flag)){
		System.out.println("/******************************  CCCW11-盘盈记录开始   **********************************/");
		var strBizDate=PublicBaseUtil.substringByte(socketMsg,"GBK" ,11,8) ; //业务日期  从MES获取
		if(strBizDate==null){
			isSucess=false;
			errMsg=errMsg + "业务日期不能为空\r\n";
		}else{
			strBizDate=strBizDate.replace(" ", "");
		}
		if("".equals(strBizDate)){
			isSucess=false;
			errMsg=errMsg + "业务日期不能为空\r\n";
		}else if(strBizDate!=null){
			var isdate=PublicBaseUtil.valiDateTimeWithLongFormat(strBizDate);
			if(!isdate){
				isSucess=false;
				errMsg=errMsg + "业务日期格式不合法\r\n";
			}
		}
		
		System.out.println("业务日期"+strBizDate);
		var  cu=PublicBaseUtil.getCU(ctx, strCUNo); 
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
  		var  billType=PublicBaseUtil.getBillTypeByNumber(ctx, strBillTypeNo);
  		if(billType==null){
  			isSucess=false;
  			errMsg=errMsg + "单据类型不能为空\r\n";
  		}
  		var  trantypeInfo=PublicBaseUtil.getTransactionTypeInfoByNumber(ctx, strTranstionType);
  		if(trantypeInfo==null){
  			isSucess=false;
  			errMsg=errMsg + "事务类型不能为空\r\n";
  		}
  		//成本中心
		var costCenter=PublicBaseUtil.getCostCenterOrgUnitInfoById(ctx,cu.getId().toString());
		if(costCenter==null){
			isSucess=false;
			errMsg=errMsg + "成本中心不能为空\r\n";
		}
  		//主业务组织
  		var  storageInfo=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strCUNo);
  		if(storageInfo==null){
  			isSucess=false;
  			errMsg=errMsg + "主业务组织不能为空\r\n";
  		}
		var strMaterialNo="";
  		var strUnit="吨";//单位默认吨
		var strLot=PublicBaseUtil.substringByte(socketMsg,"GBK" ,30,20);//批次号  从MES获取
		// 合同号
		var hth=PublicBaseUtil.substringByte(socketMsg,"GBK" ,70,20);//  从MES获取
		System.out.println("合同号-hth:"+hth);
		// 合约号
		var hyh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,50,20);//  从MES获取
		System.out.println("合约号-hyh:"+hyh);
  		var strStockNo="001";//仓库编码  从MES获取
  		var bgQty=new BigDecimal("0");
  		var bgPrice=new BigDecimal("10");
  		var bgTaxRate=new BigDecimal("0");
  		var bgAmount=new BigDecimal("1000");
      	
  		var bgTaxPrice=bgPrice.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
  		var bgTaxAmount=bgAmount.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
  		var bgTax=bgAmount.multiply(bgTaxRate).setScale(2, BigDecimal.ROUND_HALF_UP);
		if(strLot!=null){
			strLot = strLot.trim();
		}
		/*var ph="0418";//牌号  从MES获取
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
		var phmc="ABABA";//牌号名称  从MES获取
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
		}*/
		var phmc=PublicBaseUtil.substringByte(socketMsg,"GBK",94,50); //牌号名称  从MES获取
		if(phmc!=null){
			phmc=phmc.trim();
		}else{
			phmc="";
		}
		if("".equals(phmc))
		{
			isSucess=false;
			errMsg=errMsg + "牌号名称不能为空\r\n";
		}
		var ph="";
		var sql = "SELECT * FROM CT_CUS_Paihao where fname_l2 ='"+phmc+"'";
		var rows = DbUtil.executeQuery(ctx, sql);
		if(rows!= null&&rows.next() ) {
			ph=rows.getString("fnumber");
		}else{
			ph=phmc;
		}
		System.out.println("牌号名称"+phmc);
		System.out.println("牌号"+ph);
		
		var kd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,150,5);//宽度  从MES获取
		if(kd!=null){
			kd=kd.trim();
		}else
		{
			kd="0";
		}
		try
		{
			bgKd=new BigDecimal(kd).divide(new BigDecimal("10"));;
			//bgKd=BigDecimal.valueOf(4160);
		}catch(e){
			
		}
		var hd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,144,6);//厚度  从MES获取
		if(hd!=null){
			hd=hd.trim();
		}else
		{
			hd="0";
		}
		try
		{
			bgHd=new BigDecimal(hd).divide(new BigDecimal("1000"));
			//bgHd=BigDecimal.valueOf(0.81);
		}catch(e){
			
		}
		var zl=PublicBaseUtil.substringByte(socketMsg,"GBK" ,155,10);//重量  从MES获取
		if(zl!=null){
			zl=zl.trim();
		}else
		{
			zl="0";
		}
		//zl="20";		
		try{
			bgQty=new BigDecimal(zl).divide(new BigDecimal("1000"));
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
		System.out.println("重量"+zl);
		System.out.println("厚度"+hd);
		System.out.println("宽度"+kd);
		//var materialGroupNum="09";//物料组别  从MES获取
		// 临时变量记录结果,判断是否存在对应值,记录日志
		var temp_materialGroupNum = '';
		var materialGroupNum = PublicBaseUtil.substringByte(socketMsg,"GBK" ,90,4);
			materialGroupNum = materialGroupNum.trim();
		if("L101".equals(materialGroupNum)){
			materialGroupNum="09";
		}else if("L701".equals(materialGroupNum)){
			materialGroupNum="19";
		}
		temp_materialGroupNum = materialGroupNum;
		if("".equals(temp_materialGroupNum)){
			System.out.println("exception-传入机组号没有找到对应的物料类别");
		}
		System.out.println("物料组别"+materialGroupNum);		
		var  material= SocketFacadeFactory.getLocalInstance(ctx).getMaterialInfo("01", "5", materialGroupNum, ph, phmc, bgKd, bgHd, strUnit, "", true);
		if(material==null){
			isSucess=false;
			errMsg=errMsg + "物料不能为空\r\n";
		}
		// 成本中心开始
		// 成本中心编码
		var cbzxNo = PublicBaseUtil.substringByte(socketMsg,"GBK" ,90,4);
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
		// 成本中心结束
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
					var sdf = new SimpleDateFormat("yyyyMMdd");
					var scsjDate=sdf.parse(strBizDate);
					var scsj = format.format(scsjDate); 
					// 编码
					var fnumber = new java.util.Date().getTime().toString();
					// 数据库字段 fid,FNUMBER,FNAME_L2,fcreatorid,fcreatetime,fcontrolunitid,CFCKJH,CFHTH,CFHYH,CFJZH,CFZL,CFSCSJ,CFWLID,CFSFRK
					// 字段名称 唯一标识,编码,名称,创建id,创建时间,控制单元,出口卷号,合同号,合约号,机组号,重量,生产时间,物料id,是否入库,电文号,电文类型
					// 值 id,rjh,hth,'256c221a-0106-1000-e000-10d7c0a813f413B7DE7F',fcreatetimestr,'00000000-0000-0000-0000-000000000000CCE7AED4',strLot,hth,hyh,cbzxNo,bgQty,scsj,wlid,0,dwh,2
					sql = "insert into CT_CUS_JZSCCCJL(fid,FNUMBER,FNAME_L2,fcreatorid,fcreatetime,fcontrolunitid,CFCKJH,CFHTH,CFHYH,CFJZH,CFZL,CFSCSJ,CFWLID,CFSFRK,CFDWH,CFDWLX) values('"+id+"','"+fnumber+"','"+fnumber+"','256c221a-0106-1000-e000-10d7c0a813f413B7DE7F',to_date('"+fcreatetimestr+"'),'00000000-0000-0000-0000-000000000000CCE7AED4','"+strLot+"','"+hth+"','"+hyh+"','"+cbzxNo+"','"+bgQty+"',to_date('"+scsj+"'),'"+wlid+"','0','"+dwh+"',2)"
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
		System.out.println("/******************************  CCCW11-盘盈记录结束   **********************************/");
		/**
		 * 修改:不生成单据，直接写入台账
		 * 时间：2019-04-24 20:00
		 * 修改人：刘振宇
		 */		
		/*
		var  storOrgUnit=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
		if(storOrgUnit==null){
			isSucess=false;
			errMsg=errMsg + "库存组织不能为空\r\n";
		}
		var  stock=PublicBaseUtil.getWarehouseInfoByNumber(ctx, strStockNo);
		if(stock==null){
			isSucess=false;
			errMsg=errMsg + "仓库不能为空\r\n";
		}
		var  invUpType=PublicBaseUtil.getInvUpdateTypeInfoByNumber(ctx, "001");
		if(invUpType==null){
			isSucess=false;
			errMsg=errMsg + "更新类型不能为空\r\n";
		}
		*/
		/*
		if(isSucess){
			//新增委外领料
			var info=new OtherInWarehsBillInfo();
			info.put("bxNumber","盘盈电文CCCW11");
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
			} catch (e) {
				
			}
			//业务日期
			try
			{
				var bizDate;
				var  sdf = new SimpleDateFormat("yyyyMMdd");
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
			//info.setPurchaseType(purchaseType);
			// 业务类型
			if(bizType!=null){
				info.setBizType(bizType);
			}
			// 单据类型
			if(billType!=null){
				info.setBillType(billType);
			}
			if(trantypeInfo!=null){
				info.setTransactionType(trantypeInfo);
				
			}
			
			//成本中心
			if(costCenter!=null){
				info.setCostCenterOrgUnit(costCenter);
			}
			// 财务组织
			var  company=PublicBaseUtil.getCompany(ctx, cu.getNumber());
			//主业务组织
			if(storageInfo!=null){
				info.setStorageOrgUnit(storageInfo);
			}
			//盘盈单分录 
			
			//新增分录实体
			var entry=new OtherInWarehsBillEntryInfo();
			//分录行号
			entry.setSeq(1);
			entry.setSourceBillEntrySeq(0);
			//物料
			
			if (material!=null){
				entry.setMaterial(material);
				entry.setBaseUnit(material.getBaseUnit());
				//单位
				var  unit=PublicBaseUtil.getMeasureUnitInfoByName(ctx, material.getBaseUnit().getMeasureUnitGroup().getNumber(), strUnit);
				if (unit!=null){
					entry.setUnit(unit);
				}else
				{
					isSucess=false;
					errMsg=errMsg + "单位不能为空\r\n";
				}
				
			}
			
			entry.setBaseStatus(EntryBaseStatusEnum.ADD);//
			//
			entry.setAssCoefficient(BigDecimal.ZERO);
			//库存组织
			if(storOrgUnit!=null){
				entry.setStorageOrgUnit(storOrgUnit);
				//entry.setReceiveStorageOrgUnit(storOrgUnit);
				
			}
			//财务组织
			entry.setCompanyOrgUnit(company);
			//仓库
			if(stock!=null){
				entry.setWarehouse(stock);
				//entry.setOutWarehouse(stock);
			}
			
			entry.setQty(bgQty);
			entry.setBaseQty(bgQty);
			//entry.setPrice(bgPrice);
			//entry.setTaxPrice(bgTaxPrice);
			//entry.setTaxRate(bgTaxRate);
			//entry.setAmount(bgAmount);
			entry.setLot(strLot);
			
			//var invInfo = InvUpdateTypeFactory.getLocalInstance(ctx).getInvUpdateTypeInfo(new ObjectUuidPK("8r0AAAAEaOnC73rf"));
			
			entry.setInvUpdateType(invUpType);
			info.getEntries().addObject(entry);
      	}
      	//entry.getInvUpdateType().getStoreTypePre().getStoreFlag();
      	if(isSucess){
      		
      		//MaterialReqBillInfo info1 = MaterialReqBillFactory.getLocalInstance(ctx).getMaterialReqBillInfo(new ObjectUuidPK("23sE0JysRKmP7TP55oLn7VAKt14="));
      		
      		var  pk=OtherInWarehsBillFactory.getLocalInstance(ctx).save(info);  //保存
  			if(pk!=null){
  				var  tempIno=OtherInWarehsBillFactory.getLocalInstance(ctx).getOtherInWarehsBillInfo(pk);
  				if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.TEMPORARILYSAVED)) {
  					
  					OtherInWarehsBillFactory.getLocalInstance(ctx).submit(tempIno);
  				}
  				tempIno=OtherInWarehsBillFactory.getLocalInstance(ctx).getOtherInWarehsBillInfo(pk);
  				if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
  					OtherInWarehsBillFactory.getLocalInstance(ctx).audit(pk);
  				}
				methodCtx.getParam(2).setValue("true") ;
				methodCtx.getParam(4).setValue("新增成功");
  				
  			}
      	}else
      	{
      		methodCtx.getParam(2).setValue("false") ;
			methodCtx.getParam(4).setValue(errMsg);
		  }
		  */
	}
	
	else if("2".equals(flag)){
		
				System.out.println("isSucess1"+isSucess);
		var strBizDate=PublicBaseUtil.substringByte(socketMsg,"GBK" ,11,8) ; //业务日期  从MES获取
			if(strBizDate==null){
				isSucess=false;
				errMsg=errMsg + "业务日期不能为空\r\n";
			}else{
				strBizDate=strBizDate.replace(" ", "");
			}
			if("".equals(strBizDate)){
				isSucess=false;
				errMsg=errMsg + "业务日期不能为空\r\n";
			}else if(strBizDate!=null){
				var isdate=PublicBaseUtil.valiDateTimeWithLongFormat(strBizDate);
				if(!isdate){
					isSucess=false;
					errMsg=errMsg + "业务日期格式不合法\r\n";
				}
			}
			
			System.out.println("业务日期"+strBizDate);
		var strLot=PublicBaseUtil.substringByte(socketMsg,"GBK" ,30,20);//批次号  从MES获取
		var bgQty=new BigDecimal("0");
		
		errMsg="";
      	strCreatorNo="user"; //创建人编码
      	strCUNo="01";// 管理单元编码
      	//strBizDate="20190418" ; //业务日期  从MES获取
      	billStatus=BillBaseStatusEnum.ADD; //单据状态
      	purchaseType = PurchaseTypeEnum.PURCHASE;
      	strBizTypeNo="511";//业务类型编码
		strBillTypeNo="108";//单据类型编码
		strTranstionType="029";//事务类型
      	strNeedStockOrgNo="01";//需方库存组织
      	isInTax=true;
      	sdfDate = new SimpleDateFormat("yyyyMMdd");
		if(strLot!=null){
			strLot = strLot.trim();
		}
		var zl=PublicBaseUtil.substringByte(socketMsg,"GBK" ,155,10);//重量  从MES获取
		if(zl!=null){
			zl=zl.trim();
		}else
		{
			zl="0";
		}
		try
		{
			bgQty=new BigDecimal(zl).divide(new BigDecimal("1000"));
		}catch(e){
			
		}
		if (bgQty.compareTo(BigDecimal.ZERO)==0){
    		isSucess=false;
			errMsg=errMsg + "重量不能为零\r\n";
    	}
		var strSQLInv = "select * from T_IM_Inventory where flot='"+strLot+"'  and FCURSTOREQTY <> 0";
		System.out.println("销售出库接口"+strSQLInv);
		var rsInv = DbUtil.executeQuery(ctx, strSQLInv);
		if (rsInv.next()) {
			var curStoreQty=rsInv.getBigDecimal("FCURSTOREQTY");
			if(bgQty.compareTo(curStoreQty)!=0){
				isSucess=false;
				errMsg=errMsg + "与库存重量不一致\r\n";
			}
		}
		var  cu=PublicBaseUtil.getCU(ctx, strCUNo); 
		if(cu==null){
			isSucess=false;
			errMsg=errMsg + "管理单元不能为空\r\n";
		}
				System.out.println("isSucess3"+isSucess);
		// 业务类型
		var bizType=PublicBaseUtil.getBizTypeByNumber(ctx, strBizTypeNo);
		if(bizType==null){
			isSucess=false;
			errMsg=errMsg + "业务类型不能为空\r\n";
		}
				System.out.println("isSucess4"+isSucess);
		// 单据类型
		var  billType=PublicBaseUtil.getBillTypeByNumber(ctx, strBillTypeNo);
		if(billType==null){
			isSucess=false;
			errMsg=errMsg + "单据类型不能为空\r\n";
		}
		var  trantypeInfo=PublicBaseUtil.getTransactionTypeInfoByNumber(ctx, strTranstionType);
		if(trantypeInfo==null){
			isSucess=false;
			errMsg=errMsg + "事务类型不能为空\r\n";
		}
		//成本中心
		var costCenter=PublicBaseUtil.getCostCenterOrgUnitInfoById(ctx,cu.getId().toString());
		if(costCenter==null){
			isSucess=false;
			errMsg=errMsg + "成本中心不能为空\r\n";
		}
		var  storageInfo=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strCUNo);
		if(storageInfo==null){
			isSucess=false;
			errMsg=errMsg + "主业务组织不能为空\r\n";
		}
		var strMaterialNo="";
		var strUnit="吨";//单位默认吨

		var sqlLot = "SELECT * FROM T_IM_Inventory where FLot ='"+strLot+"'";
		System.out.println("sqlLot"+sqlLot);
		var strStockNo="";
		var rows = DbUtil.executeQuery(ctx, sqlLot);
			if(rows!= null && rows.next()) {
				var FID=rows.getString("FWarehouseID");
				
				var strStockNosql = "SELECT * FROM T_DB_WAREHOUSE WHERE FID='"+FID+"'";
				var row1 = DbUtil.executeQuery(ctx, strStockNosql);
				if(row1!=null &&row1.next()){
					strStockNo = row1.getString("FNUMBER");
				}
			}else{
				isSucess=false;
				errMsg=errMsg + "仓库内没有此物料\r\n";
			}
			
		
		//var strStockNo="003";//仓库编码  从MES获取
		System.out.println("仓库编码"+strStockNo);
		if(strStockNo==null){
				isSucess=false;
				errMsg=errMsg + "仓库编码不能为空\r\n";
		}

		var bgPrice=new BigDecimal("10");
		var bgTaxRate=new BigDecimal("0");
		var bgAmount=new BigDecimal("1000");
		
		var bgTaxPrice=bgPrice.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
		var bgTaxAmount=bgAmount.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
		var bgTax=bgAmount.multiply(bgTaxRate).setScale(2, BigDecimal.ROUND_HALF_UP);
		var bgKd=new BigDecimal("0"); 
		var bgHd=new BigDecimal("0");
		var kd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,150,5);//宽度  从MES获取
		if(kd!=null){
			kd=kd.trim();
		}else
		{
			kd="0";
		}
		try
		{
			bgKd=new BigDecimal(kd).divide(new BigDecimal("10"));;
			//bgKd=new BigDecimal("1200");
		}catch(e){
			
		}
		var hd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,144,6);//厚度  从MES获取
		if(hd!=null){
			hd=hd.trim();
		}else
		{
			hd="0";
		}
		try
		{
			bgHd=new BigDecimal(hd).divide(new BigDecimal("1000"));
			//bgHd=new BigDecimal("0.80");
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
		System.out.println("重量"+zl);
		System.out.println("厚度"+hd);
		System.out.println("宽度"+kd);
		/*var ph="0422"; //牌号 从MES获取
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
		var phmc="0422"; //牌号名称  从MES获取
		if(phmc!=null){
			phmc=phmc.trim();
		}else{
			phmc="";
		}
		if("".equals(phmc))
		{
			isSucess=false;
			errMsg=errMsg + "牌号名称不能为空\r\n";
		}*/
			var phmc=PublicBaseUtil.substringByte(socketMsg,"GBK",94,50); //牌号名称  从MES获取
			if(phmc!=null){
				phmc=phmc.trim();
			}else{
				phmc="";
			}
			if("".equals(phmc))
			{
				isSucess=false;
				errMsg=errMsg + "牌号名称不能为空\r\n";
			}
			System.out.println("牌号名称"+phmc);
			var ph="";
			var sql = "SELECT * FROM CT_CUS_Paihao where fname_l2 ='"+phmc+"'";
			var rows = DbUtil.executeQuery(ctx, sql);
			if(rows!= null&&rows.next() ) {
				ph=rows.getString("fnumber");
			}else{
				ph=phmc;
			}
			System.out.println("牌号名称"+phmc);
			System.out.println("牌号"+ph);
		var strUnit="吨"; //单位  默认吨
		//var materialGroupNum="06";//物料组别  从MES获取
		var materialGroupNum = PublicBaseUtil.substringByte(socketMsg,"GBK" ,90,4);
			materialGroupNum = materialGroupNum.trim();
		if("L101".equals(materialGroupNum)){
			materialGroupNum="09";
		}else if("L701".equals(materialGroupNum)){
			materialGroupNum="19";
		}
		System.out.println("物料组别"+materialGroupNum);
		var material= SocketFacadeFactory.getLocalInstance(ctx).getMaterialInfo("01", "5", materialGroupNum, ph, phmc, bgKd, bgHd, strUnit, "", true);
		if(material==null){
			isSucess=false;
			errMsg=errMsg + "物料不能为空\r\n";
		}
		var  storOrgUnit=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
		if(storOrgUnit==null){
			isSucess=false;
			errMsg=errMsg + "库存组织不能为空\r\n";
		}
		var  stock=PublicBaseUtil.getWarehouseInfoByNumber(ctx, strStockNo);
		if(stock==null){
			isSucess=false;
			errMsg=errMsg + "仓库不能为空\r\n";
		}
		var  invUpType=PublicBaseUtil.getInvUpdateTypeInfoByNumber(ctx, "002");
		if(invUpType==null){
			isSucess=false;
			errMsg=errMsg + "更新类型不能为空\r\n";
		}
		
		System.out.println("isSucess"+isSucess);
		if(isSucess){
			//新增盘亏
			var info=new OtherIssueBillInfo();
			info.put("bxNumber","盘亏电文CCCW11");
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
			} catch (e) {
				
			}
			//业务日期
			try
			{
				var bizDate;
				var  sdf = new SimpleDateFormat("yyyyMMdd");
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
			//info.setPurchaseType(purchaseType);
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
			//成本中心
			if(costCenter!=null){
				info.setCostCenterOrgUnit(costCenter);
			}
			// 财务组织
			var  company=PublicBaseUtil.getCompany(ctx, cu.getNumber());
			//主业务组织
			if(storageInfo!=null){
				info.setStorageOrgUnit(storageInfo);
			}
			
			//盘亏单分录 
			//新增分录实体
			var entry=new OtherIssueBillEntryInfo();
			//分录行号
			entry.setSeq(1);
			entry.setSourceBillEntrySeq(0);
		  
			//物料
			if (material!=null){
				entry.setMaterial(material);
				entry.setBaseUnit(material.getBaseUnit());
				//单位
				var  unit=PublicBaseUtil.getMeasureUnitInfoByName(ctx, material.getBaseUnit().getMeasureUnitGroup().getNumber(), strUnit);
				if (unit!=null){
					entry.setUnit(unit);
				}else
				{
					isSucess=false;
					errMsg=errMsg + "单位不能为空\r\n";
				}
				
			}
			
			entry.setBaseStatus(EntryBaseStatusEnum.ADD);//
			//
			entry.setAssCoefficient(BigDecimal.ZERO);
			//库存组织
			if(storOrgUnit!=null){
				entry.setStorageOrgUnit(storOrgUnit);
				//entry.setReceiveStorageOrgUnit(storOrgUnit);
				
			}
			//财务组织
			entry.setCompanyOrgUnit(company);
			//仓库
			if(stock!=null){
				entry.setWarehouse(stock);
				//entry.setOutWarehouse(stock);
			}
			
			entry.setQty(bgQty);
			entry.setBaseQty(bgQty);
			//entry.setPrice(bgPrice);
			//entry.setTaxPrice(bgTaxPrice);
			//entry.setTaxRate(bgTaxRate);
			//entry.setAmount(bgAmount);
			entry.setLot(strLot);
			
			//InvUpdateTypeInfo invInfo = InvUpdateTypeFactory.getLocalInstance(ctx).getInvUpdateTypeInfo(new ObjectUuidPK("8r0AAAAEaOnC73rf"));
			
			entry.setInvUpdateType(invUpType);
			info.getEntries().addObject(entry);
		}
		//entry.getInvUpdateType().getStoreTypePre().getStoreFlag();

		if(isSucess){
			
			//MaterialReqBillInfo info1 = MaterialReqBillFactory.getLocalInstance(ctx).getMaterialReqBillInfo(new ObjectUuidPK("23sE0JysRKmP7TP55oLn7VAKt14="));
			System.out.println("生成单子");
			var  pk=OtherIssueBillFactory.getLocalInstance(ctx).save(info);  //保存
			if(pk!=null){
				var  tempIno=OtherIssueBillFactory.getLocalInstance(ctx).getOtherIssueBillInfo(pk);
				if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.TEMPORARILYSAVED)) {
					
					OtherIssueBillFactory.getLocalInstance(ctx).submit(tempIno);
				}
				tempIno=OtherIssueBillFactory.getLocalInstance(ctx).getOtherIssueBillInfo(pk);
				if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
					OtherIssueBillFactory.getLocalInstance(ctx).audit(pk);
				}
				methodCtx.getParam(2).setValue("true") ;
				methodCtx.getParam(4).setValue("新增成功");
				
			}
		}else
		{
			methodCtx.getParam(2).setValue("false") ;
			methodCtx.getParam(4).setValue(errMsg);
		}
	}
	var strMsg="0110"+dwh+"CWCC";
	if(isSucess){
		methodCtx.getParam(2).setValue("true");
		var strData=String.format("%-81s", "A")+"\n" ;
		System.out.println("应答报文："+strMsg+strData);
		methodCtx.getParam(3).setValue(strMsg+strData);
	}else
	{
		methodCtx.getParam(2).setValue("false");
		methodCtx.getParam(4).setValue(dwt+errMsg);
		var strMsg="0110"+dwh+"CWCCB";
		
		var strData=String.format("%-80s", errMsg)+"\n" 
		System.out.println("应答报文："+strMsg+strData);
		methodCtx.getParam(3).setValue(strMsg+strData);
	}
	System.out.println("/******************************  CCCW11 消息记录结束   **********************************/");
}