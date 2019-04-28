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
imp.importPackage(Packages.com.kingdee.bos.dao.ormapping);
imp.importPackage(Packages.com.kingdee.eas.framework);
with(imp){ 
	var socketMsg = methodCtx.getParamValue(0);  //接收报文 
	//socketMsg="0255CCCW0220190424111504CCCWDTESTSHIHUI002       2                                                  DD11                                              00350015000000002800000000000C0111202195            192021030700                  X                     ";
	System.out.println("/**********************采购入库采购退货电文开始**************************************/");
	var billID = methodCtx.getParamValue(1); // 业务单元ID  
	var result = methodCtx.getParamValue(2); //成功失败  true:false  必须有返回值  	methodCtx.getParam(2).setValue("true") 
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
	//dwt="0524CCCW2020190224040527CCCWD11203778";
	System.out.println("采购入库单电文号"+dwh);
	var isSucess=true;
	methodCtx.getParam(2).setValue("false") ;
	var errMsg="";
	var strCreatorNo="user"; //创建人编码
	var strCUNo="01";// 管理单元编码
	
	var billStatus=BillBaseStatusEnum.ADD; //单据状态
	var strBizTypeNo="110";//业务类型编码
	var strBillTypeNo="103";//单据类型编码
	var strTranstionType="001";//事务类型
	var strSupNo=""; //供应商编码  从MES获取
	var strSupPici=PublicBaseUtil.substringByte(socketMsg,"GBK" ,213,30); //供应商编码  从MES获取
	if(strSupPici==null){
		isSucess=false;
		errMsg=errMsg + "供应商统一信用代码不能为空\r\n";
	}else{
		strSupPici=strSupPici.replace(" ", "");
	}
	if("".equals(strSupPici)){
		isSucess=false;
		errMsg=errMsg + "供应商统一信用代码不能为空\r\n";
	}else if(strSupPici!=null){
		var sql = "select * from T_BD_Supplier where FTAXREGISTERNO ='"+strSupPici+"'";
		var rows = DbUtil.executeQuery(ctx, sql);
		if(rows!= null&&rows.next() ) {
			strSupNo=rows.getString("fnumber");
		}
		if("".equals(strSupNo)){
			isSucess=false;
			errMsg=errMsg + "供应商统一信用代码不存在\r\n";
		}
	}
	System.out.println("税号"+strSupPici);
	
	var strCurrencyNo ="BB01"; //币别编码
	var strPaymentType="004";// 付款方式
	var bgExchangeRate=BigDecimal.ONE;// 汇率
	var bgTotalQty =BigDecimal.ZERO;// 数量
	var bgTotalAmount =BigDecimal.ZERO;// 金额
	var bgTotalActualCost =BigDecimal.ZERO;// 实际成本
	var bgTotalLocalAmount =BigDecimal.ZERO;// 本位币金额
	var  isInTax=true;
	var sdfDate = new SimpleDateFormat("yyyyMMdd");
    var flag=PublicBaseUtil.substringByte(socketMsg,"GBK" ,50,1);//操作类型  从MES获取
	if(flag==null||"".equals(flag)){
		isSucess=false;
		errMsg=errMsg+"操作类型不能为空\r\n";
	}
	System.out.println("操作类型"+flag);
	if(isSucess){
		if("1".equals(flag)){
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
			var cu=PublicBaseUtil.getCU(ctx, strCUNo); 
			if(cu==null){
				isSucess=false;
				errMsg=errMsg + "管理单元不能为空\r\n";
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
			var strUnit="吨";//默认吨
			
			var strStockNo=PublicBaseUtil.substringByte(socketMsg,"GBK" ,190,3) ;//仓库编码  从MES获取
			if(strStockNo==null){
				isSucess=false;
				errMsg=errMsg + "库区号不能为空\r\n";
			}else{
				strStockNo=strStockNo.replace(" ", "");
			}
			if("".equals(strStockNo)){
				isSucess=false;
				errMsg=errMsg + "库区号不能为空\r\n";
			}
			/*if("C01".equals(strStockNo)){
				strStockNo="001";
			}else if("C02".equals(strStockNo)){
				strStockNo="002";
			}else if("C03".equals(strStockNo)){
				strStockNo="003";
			}else if("K01".equals(strStockNo)||"R01".equals(strStockNo)){
				strStockNo="004";
			}*/
			System.out.println("库区号"+strStockNo);
			var bgQty=new BigDecimal("0");
			var bgPrice=new BigDecimal("0");//单价  从MES获取
			var pro=PublicBaseUtil.substringByte(socketMsg,"GBK" ,182,8);//单价  从MES获取
			System.out.println("单价"+pro);
			if(pro!=null){
				pro=pro.trim();
			}else
			{
				pro="0";
			}
			try
			{
				bgPrice=new BigDecimal(pro).divide(new BigDecimal("100"));
			}catch(e){
				
			}
			if (bgPrice.compareTo(BigDecimal.ZERO)==0){
				//isSucess=false;
				errMsg=errMsg + "单价不能为零\r\n";
			}
			var bgTaxRate=new BigDecimal("0");
			bgTaxRate=new BigDecimal("13");
			var bgAmount=new BigDecimal("0");
			var  bgKd=new BigDecimal("0");
			var bgHd=new BigDecimal("0");
			var rjh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,30,20);//批次号  从MES获取
			if(rjh!=null){
				rjh=rjh.trim();
			}else
			{
				rjh="";
			}
			if("".equals(rjh))
			{
				isSucess=false;
				errMsg=errMsg + "热卷号不能为空\r\n";
			}
			System.out.println("批次号"+rjh);
			var strLot=rjh;
			var phmc=PublicBaseUtil.substringByte(socketMsg,"GBK" ,111,50); //牌号名称  从MES获取
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
			}
			System.out.println("牌号名称"+phmc);
			System.out.println("牌号"+ph);
			var kd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,167,5);//宽度 从MES获取
			if(kd!=null){
				kd=kd.trim();
			}else
			{
				kd="0";
			}
			try
			{
				bgKd=new BigDecimal(kd).divide(new BigDecimal("10"));
			}catch(e){
				
			}
			System.out.println("宽度"+kd);
			var hd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,161,6);//厚度  从MES获取
			if(hd!=null){
				hd=hd.trim();
			}else
			{
				hd="0";
			}
			try
			{
				bgHd=new BigDecimal(hd).divide(new BigDecimal("1000"));
			}catch(e){
				
			}
			System.out.println("厚度"+hd);
			var zl=PublicBaseUtil.substringByte(socketMsg,"GBK" ,172,10);//重量  从MES获取
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
			System.out.println("重量"+zl);
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
			var bgTaxPrice=bgPrice.multiply(bgTaxRate.divide(new BigDecimal("100"),8,BigDecimal.ROUND_HALF_UP).add(BigDecimal.ONE));
			System.out.println("单价："+bgPrice.toString());
			bgAmount=bgPrice.multiply(bgQty);
			var bgTaxAmount=bgTaxPrice.multiply(bgQty);
			var bgTax=bgTaxAmount.subtract(bgAmount);
			var bgLocalTax=bgTax.multiply(bgExchangeRate);
			var bgLocalPrice =bgPrice.multiply(bgExchangeRate);
			var bgLocalAmount =bgAmount.multiply(bgExchangeRate);
			
			var bgLocalTaxPrice =bgTaxPrice.multiply(bgExchangeRate);
			var bgLocalTaxAmount =bgTaxAmount.multiply(bgExchangeRate);
			var hetonghao=PublicBaseUtil.substringByte(socketMsg,"GBK" ,91,20);//合同号  从MES获取
			System.out.println("合同号"+hetonghao);
			if(hetonghao==null){
				isSucess=false;
				errMsg=errMsg + "合同号不能为空\r\n";
			}else{
				hetonghao=hetonghao.replace(" ", "");
			}
			
			var heyuehao=PublicBaseUtil.substringByte(socketMsg,"GBK" ,71,20);//合约号  从MES获取
			System.out.println("合约号"+heyuehao);
			if(heyuehao==null){
				isSucess=false;
				errMsg=errMsg + "合约号不能为空\r\n";
			}else{
				heyuehao=heyuehao.replace(" ", "");
			}
			
			var materialGroupNum=PublicBaseUtil.substringByte(socketMsg,"GBK" ,243,1);//物料组别  从MES获取
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
				errMsg=errMsg + "品名代码不能为空\r\n";
			}
			System.out.println("品名代码"+materialGroupNum);
			if("X".equals(materialGroupNum)){
				materialGroupNum="09";
				if("".equals(ph)){
					isSucess=false;
					errMsg=errMsg + "牌号流水不存在\r\n";
				}
			}else if("M".equals(materialGroupNum)){
				materialGroupNum="19";
				if("".equals(ph)){
					isSucess=false;
					errMsg=errMsg + "牌号流水不存在\r\n";
				}
			}else{
				materialGroupNum="06";
				if("".equals(ph)&&phmc!=null){
					var sql = "SELECT * FROM CT_CUS_Rezhapaihao where fname_l2 ='"+phmc+"'";
					var rows = DbUtil.executeQuery(ctx, sql);
					if(rows!= null&&rows.next() ) {
						ph=rows.getString("fnumber");
					}else{
						do{
							var random = new Random();
							var i = random.nextInt(1000);
							i++;
							while(i>=1000){
								i--;
							}
							var easnumber = String.format("%04d", i);
							System.out.println("热轧流水"+easnumber);
							var sqlrz = "SELECT * FROM CT_CUS_Rezhapaihao where fnumber ='"+easnumber+"'";
							var rowsrz = DbUtil.executeQuery(ctx, sqlrz);
						}while(rowsrz!= null&&rowsrz.next())
						var format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						var fcreatetime = new java.util.Date();
						var fcreatetimestr=format.format(fcreatetime);
						var bosid=com.kingdee.bos.util.BOSUuid.create("33181FEC");
						var id=bosid.toString();
						sql = "INSERT INTO CT_CUS_Rezhapaihao (Fid, fnumber, fname_l2, fcreatorid ,fcreatetime,fcontrolunitid ) VALUES ('"+id+"','"+easnumber+"','"+phmc+"','256c221a-0106-1000-e000-10d7c0a813f413B7DE7F',to_date('"+fcreatetimestr+"'),'00000000-0000-0000-0000-000000000000CCE7AED4')";
						System.out.println("select T_ORG_Admin2:"+sql);
						DbUtil.execute(ctx, sql);
						ph=easnumber;
					}
				}
			}
			var material= null;
			var unit=null;
			if(isSucess){
				material= SocketFacadeFactory.getLocalInstance(ctx).getMaterialInfo("01", "5", materialGroupNum, ph, phmc, bgKd, bgHd, strUnit, "", true);
				
				if (material==null){
					isSucess=false;
					errMsg=errMsg + "物料不能为空\r\n";
				}else{
					unit=PublicBaseUtil.getMeasureUnitInfoByName(ctx, material.getBaseUnit().getMeasureUnitGroup().getNumber(), strUnit);
					if (unit==null){
						isSucess=false;
						errMsg=errMsg + "单位不能为空\r\n";
					}
				}
			}
			var storOrgUnit=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
			if(storOrgUnit==null){
				isSucess=false;
				errMsg=errMsg + "库存组织不能为空\r\n";
			}
			var purchaseUnit=PublicBaseUtil.getPurchaseOrgUnitInfoByNumber(ctx, cu.getNumber());
			if(purchaseUnit==null){
				isSucess=false;
				errMsg=errMsg + "采购组织不能为空\r\n";
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
				info.put("bxNumber","外购卷入库电文CCCW02");
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
				//var ph=PublicBaseUtil.substringByte(socketMsg,"GBK" ,100,50);
				
				//big
				//新增分录实体
				var entry=new PurInWarehsEntryInfo();
				//分录行号
				entry.setSeq(1);
				entry.setSourceBillEntrySeq(0);
				entry.put("hetonghao", hetonghao);
				entry.put("heyuehao", heyuehao);
				//物料
				//var material=PublicBaseUtil.getMaterialInfoByNumber(ctx, info.getCU().getId().toString(), strMaterialNo);
				if (material!=null){
					entry.setMaterial(material);
					entry.setBaseUnit(material.getBaseUnit());
					//单位
					if (unit!=null){
						entry.setUnit(unit);
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
				//采购组织
				
				entry.setPurchaseOrgUnit(purchaseUnit);
				//仓库
				if(stock!=null){
					entry.setWarehouse(stock);
					entry.setOutWarehouse(stock);
				}
				
				entry.setQty(bgQty);
				entry.setBaseQty(bgQty);
				bgTotalQty=bgTotalQty.add(bgQty);
				entry.setPrice(bgPrice);
				entry.setTaxAmount(bgTaxAmount);
				entry.setTaxPrice(bgTaxPrice);
				entry.setTaxRate(bgTaxRate);
				entry.setTax(bgTax);
				entry.setAmount(bgAmount);
				entry.setLocalAmount(bgLocalAmount);
				entry.setLocalPrice(bgLocalPrice);
				entry.setLocalTax(bgLocalTax);
				entry.setLocalTaxAmount(bgLocalTaxAmount);
				entry.setLot(strLot);
				bgTotalAmount=bgTotalAmount.add(bgAmount);
				bgTotalLocalAmount=bgTotalLocalAmount.add(bgLocalAmount);
				bgTotalActualCost=bgTotalActualCost.add(bgLocalAmount);
				
				entry.setInvUpdateType(invUpType);
				entry.setDosingType(DosingTypeEnum.MANUAL);
				entry.setUnWriteOffQty(bgQty);
				entry.setUnWriteOffBaseQty(bgQty);
				entry.setUnWriteOffAmount(bgAmount); //未核销金额
				entry.setUnReturnedBaseQty(bgQty);
				
				entry.setUnitActualCost(bgPrice); //单位实际成本
				entry.setActualCost(bgAmount); //实际成本 
				entry.setPurOrderEntrySeq(0);
				entry.setActualPrice(bgPrice); //实际单价
				entry.setActualTaxPrice(bgTaxPrice); //实际含税单价
				entry.setPurchaseCost(bgAmount); //采购成本
				entry.setUnitPurchaseCost(bgPrice); //单位采购成本
				
				info.getEntries().addObject(entry);
				
				
				info.setTotalQty(bgTotalQty);
				info.setTotalAmount(bgTotalAmount);
				info.setTotalActualCost(bgTotalActualCost);
				info.setTotalLocalAmount(bgTotalLocalAmount);
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
			
		}else if("2".equals(flag)){
			errMsg="";
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
			strCreatorNo="user"; //创建人编码
			strCUNo="01";// 管理单元编码
			billStatus=BillBaseStatusEnum.ADD; //单据状态
			strBizTypeNo="110";//业务类型编码
			strBillTypeNo="103";//单据类型编码
			strTranstionType="001";//事务类型
			strCurrencyNo ="BB01"; //币别编码
			strPaymentType="004";// 付款方式
			bgExchangeRate=BigDecimal.ONE;// 汇率
			bgTotalQty =BigDecimal.ZERO;// 数量
			bgTotalAmount =BigDecimal.ZERO;// 金额
			bgTotalActualCost =BigDecimal.ZERO;// 实际成本
			bgTotalLocalAmount =BigDecimal.ZERO;// 本位币金额
			isInTax=true;
			sdfDate = new SimpleDateFormat("yyyyMMdd");
			
			//获取管理单元
			var cu=PublicBaseUtil.getCU(ctx, strCUNo); 
			if(cu==null){
				isSucess=false;
				errMsg=errMsg + "管理单元不能为空\r\n";
			}
			var rjh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,30,20);//批次号  从MES获取
			if(rjh!=null){
				rjh=rjh.trim();
			}else
			{
				rjh="";
			}
			if("".equals(rjh))
			{
				isSucess=false;
				errMsg=errMsg + "热卷号不能为空\r\n";
			}
			System.out.println("批次号"+rjh);
			
			var strMaterialNo="";
			var strUnit="吨";//单位默认吨
			var strLot=rjh;
			
			var strStockNo=PublicBaseUtil.substringByte(socketMsg,"GBK" ,190,3) ;//仓库编码 从MES获取
			if(strStockNo==null){
				isSucess=false;
				errMsg=errMsg + "库区号不能为空\r\n";
			}else{
				strStockNo=strStockNo.replace(" ", "");
			}
			if("".equals(strStockNo)){
				isSucess=false;
				errMsg=errMsg + "库区号不能为空\r\n";
			}
			/*if("C01".equals(strStockNo)){
				strStockNo="001";
			}else if("C02".equals(strStockNo)){
				strStockNo="002";
			}else if("C03".equals(strStockNo)){
				strStockNo="003";
			}else if("K01".equals(strStockNo)||"R01".equals(strStockNo)){
				strStockNo="004";
			}*/
			System.out.println("库区号"+strStockNo);
			var bgQty=new BigDecimal("0");
			var bgPrice=new BigDecimal("0");
			var bgTaxRate=new BigDecimal("0");
			var bgAmount=new BigDecimal("0");
			var  bgKd=new BigDecimal("0");
			var bgHd=new BigDecimal("0");
			var phmc=PublicBaseUtil.substringByte(socketMsg,"GBK" ,111,50); //牌号名称  从MES获取
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
			}
			System.out.println("牌号名称"+phmc);
			System.out.println("牌号"+ph);
			var kd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,167,5);//宽度 从MES获取
			if(kd!=null){
				kd=kd.trim();
			}else
			{
				kd="0";
			}
			try
			{
				bgKd=new BigDecimal(kd).divide(new BigDecimal("10"));
			}catch(e){
				
			}
			System.out.println("宽度"+kd);
			var hd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,161,6);//厚度  从MES获取
			if(hd!=null){
				hd=hd.trim();
			}else
			{
				hd="0";
			}
			try
			{
				bgHd=new BigDecimal(hd).divide(new BigDecimal("1000"));
			}catch(e){
				
			}
			System.out.println("厚度"+hd);
			var zl=PublicBaseUtil.substringByte(socketMsg,"GBK" ,172,10);//重量  从MES获取
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
			System.out.println("重量"+zl);
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
			var materialGroupNum=PublicBaseUtil.substringByte(socketMsg,"GBK" ,243,1);//物料组别  从MES获取
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
				errMsg=errMsg + "品名代码不能为空\r\n";
			}
			System.out.println("品名代码"+materialGroupNum);
			if("X".equals(materialGroupNum)){
				materialGroupNum="09";
				if("".equals(ph)){
					isSucess=false;
					errMsg=errMsg + "牌号流水不存在\r\n";
				}
			}else if("M".equals(materialGroupNum)){
				materialGroupNum="19";
				if("".equals(ph)){
					isSucess=false;
					errMsg=errMsg + "牌号流水不存在\r\n";
				}
			}else{
				materialGroupNum="06";
				if("".equals(ph)&&phmc!=null){
					var sql = "SELECT * FROM CT_CUS_Rezhapaihao where fname_l2 ='"+phmc+"'";
					var rows = DbUtil.executeQuery(ctx, sql);
					if(rows!= null&&rows.next() ) {
						ph=rows.getString("fnumber");
					}else{
						do{
							var random = new Random();
							var i = random.nextInt(1000);
							i++;
							while(i>=1000){
								i--;
							}
							var easnumber = String.format("%04d", i);
							System.out.println("热轧流水"+easnumber);
							var sqlrz = "SELECT * FROM CT_CUS_Rezhapaihao where fnumber ='"+easnumber+"'";
							var rowsrz = DbUtil.executeQuery(ctx, sqlrz);
						}while(rowsrz!= null&&rowsrz.next())
						var format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						var fcreatetime = new java.util.Date();
						var fcreatetimestr=format.format(fcreatetime);
						var bosid=com.kingdee.bos.util.BOSUuid.create("33181FEC");
						var id=bosid.toString();
						sql = "INSERT INTO CT_CUS_Rezhapaihao (Fid, fnumber, fname_l2, fcreatorid ,fcreatetime,fcontrolunitid ) VALUES ('"+id+"','"+easnumber+"','"+phmc+"','256c221a-0106-1000-e000-10d7c0a813f413B7DE7F',to_date('"+fcreatetimestr+"'),'00000000-0000-0000-0000-000000000000CCE7AED4')";
						System.out.println("select T_ORG_Admin2:"+sql);
						DbUtil.execute(ctx, sql);
						ph=easnumber;
					}
				}
			}
			System.out.println("品名代码"+materialGroupNum);
			var material= null;
			if(isSucess){
				material= SocketFacadeFactory.getLocalInstance(ctx).getMaterialInfo("01", "5", materialGroupNum, ph, phmc, bgKd, bgHd, strUnit, "", true);
				if(material==null){
					isSucess=false;
					errMsg=errMsg + "获取物料失败\r\n";
				}
			}
			if(isSucess){
				//新增采购退货
				//制单人
				var creator=PublicBaseUtil.getUserByNumber(ctx, strCreatorNo);
				if(creator!=null){
					
				}else{
					isSucess=false;
					errMsg=errMsg + "制单人未找到\r\n";
				}
				var sdfDate = new SimpleDateFormat("yyyyMMdd");
				//业务日期
				var bizDate=null;
				try
				{
					
					var sdf = new SimpleDateFormat("yyyyMMdd");
					bizDate=sdf.parse(strBizDate);
					
				}catch( e){
					isSucess=false;
					errMsg=errMsg + "日期格式不正确\r\n";
					//throw new BOSException("日期格式不正确");
					
				}
			}
			if(isSucess){
				
				if (material!=null){
					var purBills=PurInWarehsEntryFactory.getLocalInstance(ctx).getPurInWarehsEntryCollection(" select * where parent.baseStatus='4' and   parent.bizType='d8e80652-0106-1000-e000-04c5c0a812202407435C'   and  material.id='"+material.getId().toString()+"' and  lot='"+strLot+"' and parent.CU.id='" +cu.getId().toString()+"'");
					if (purBills!=null){
						if(purBills.size()>0){
							//采购退货
							var entry=purBills.get(0);
							if(entry!=null){
								var info=PurInWarehsBillFactory.getLocalInstance(ctx).getPurInWarehsBillInfo(new ObjectUuidPK(entry.getParent().getId().toString()));
								var sour= new PurInWarehsBillCollection();
								sour.add(info);
								var source=new CoreBillBaseCollection();
								var newcoll=sour.cast(new CoreBillBaseCollection().getClass());
								var infos= PublicBaseUtil.botp(ctx, info.getBOSType(),  info.getBOSType(), newcoll, "GuKLmgEUEADgABTHwKgShQRRIsQ=");
								if(infos!=null){
									if(infos.size()>0){
										var id=infos.get(0).getId().toString();
										if(id!=null){
											var tempIno=PurInWarehsBillFactory.getLocalInstance(ctx).getPurInWarehsBillInfo(new ObjectUuidPK(id));
											var strSQLsi = "update T_IM_PurInWarehsBill set cfbxNumber='采购退货电文CCCW02' where fid='"+id+"'";
											System.out.println("销售出库接口"+strSQLsi);
											DbUtil.execute(ctx, strSQLsi);
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
												methodCtx.getParam(2).setValue("true") ;
												methodCtx.getParam(4).setValue("新增成功");
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
				methodCtx.getParam(2).setValue("false") ;
				methodCtx.getParam(4).setValue(errMsg);
				//throw new BOSException(errMsg);
			}
		}
	}
	var strMsg="0110"+dwh+"CWCC";
	//methodCtx.getParam(2).setValue("true");
	//var strData=String.format("%-81s", "A")+"\n" ;
	//methodCtx.getParam(3).setValue(strMsg+strData);
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