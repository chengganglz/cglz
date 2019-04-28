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
imp.importPackage(Packages.com.kingdee.eas.util);
with (imp) {
	var socketMsg = methodCtx.getParamValue(0);  //接收报文 
	//socketMsg="0194CCCW0320190425143659CCCWD192021021200        TESTSHIHUI2019042501X190425002          L101DD11                                              00350015000000002800020190425140901C01003500150400 ";
	//var billID = methodCtx.getParamValue(1); // 业务单元ID  
	var result = methodCtx.getParamValue(2); // 成功失败  true:false  必须有返回值  methodCtx.getParam(2).setValue("true") 
	var sendMsg = methodCtx.getParamValue(3); // 发送报文   如果需要发送电文,将电文拼好放到这里返回  methodCtx.getParam(3).setValue("")
	var errMsg = methodCtx.getParamValue(4); //错误消息 methodCtx.getParam(4).setValue("")  
	var ctx = pluginCtx.getContext(); //服务端上下文 
	var isSucess = true;
	var errMsg = "";
	var strCreatorNo = "user"; //创建人编码
	var strCUNo = "01";// 管理单元编码
	System.out.println("/**********************生产领料电文开始**************************************/");
	var strBizDate = PublicBaseUtil.substringByte(socketMsg, "GBK", 165, 8); //业务日期  从MES获取
	if (strBizDate == null) {
		isSucess = false;
		errMsg = errMsg + "业务日期不能为空\r\n";
	} else {
		strBizDate = strBizDate.replace(" ", "");
	}
	if ("".equals(strBizDate)) {
		isSucess = false;
		errMsg = errMsg + "业务日期不能为空\r\n";
	} else if (strBizDate != null) {
		var isdate = PublicBaseUtil.valiDateTimeWithLongFormat(strBizDate);
		if (!isdate) {
			isSucess = false;
			errMsg = errMsg + "业务日期格式不合法\r\n";
		}
	}
	System.out.println("业务日期" + strBizDate);
	var billStatus = BillBaseStatusEnum.ADD; //单据状态
	var strBizTypeNo = "340";//业务类型编码
	var strBillTypeNo = "104";//单据类型编码
	var strTranstionType = "020";//事务类型
	var strNeedStockOrgNo = "01";//需方库存组织
	//String strSupNo="1000"; //供应商编码
	//String strCurrencyNo ="BB01"; //币别编码
	//String strPaymentType="004";// 付款方式
	//BigDecimal bgExchangeRate=BigDecimal.ONE;// 汇率
	//BigDecimal bgTotalQty =BigDecimal.ZERO;// 数量
	//BigDecimal bgTotalAmount =BigDecimal.ZERO;// 金额
	//BigDecimal bgTotalActualCost =BigDecimal.ZERO;// 实际成本
	//BigDecimal bgTotalLocalAmount =BigDecimal.ZERO;// 本位币金额
	var dwh = PublicBaseUtil.substringByte(socketMsg, "GBK", 5, 20);//电文号  从MES获取
	var dwt = PublicBaseUtil.substringByte(socketMsg, "GBK", 1, 29);//电文头  从MES获取
	System.out.println("生产领料单电文号" + dwh);
	var strLot = PublicBaseUtil.substringByte(socketMsg, "GBK", 30, 20);//批次号  从MES获取
	if (strLot == null) {
		isSucess = false;
		errMsg = errMsg + "卷号不能为空\r\n";
	} else {
		strLot = strLot.replace(" ", "");
	}
	if ("".equals(strLot)) {
		isSucess = false;
		errMsg = errMsg + "卷号不能为空\r\n";
	}
	System.out.println("卷号" + strLot);
	var isInTax = true;
	var sdfDate = new SimpleDateFormat("yyyyMMdd");
	var bgQty = new BigDecimal("0");
	var zl = PublicBaseUtil.substringByte(socketMsg, "GBK", 155, 10);//重量  从MES获取
	if (zl != null) {
		zl = zl.trim();
	} else {
		zl = "0";
	}
	try {
		bgQty = new BigDecimal(zl).divide(new BigDecimal("1000"));
	} catch (e) {

	}
	if (bgQty.compareTo(BigDecimal.ZERO) == 0) {
		isSucess = false;
		errMsg = errMsg + "重量不能为零\r\n";
	}
	System.out.println("重量" + bgQty.toString());
	var strSQLInv = "select * from T_IM_Inventory where flot='" + strLot + "'  and FCURSTOREQTY <> 0";
	System.out.println("销售出库接口" + strSQLInv);
	var rsInv = DbUtil.executeQuery(ctx, strSQLInv);
	if (rsInv.next()) {
		var curStoreQty = rsInv.getBigDecimal("FCURSTOREQTY");
		if (bgQty.compareTo(curStoreQty) != 0) {
			isSucess = false;
			errMsg = errMsg + "与库存重量不一致\r\n";
		}
	}
	var cu = PublicBaseUtil.getCU(ctx, strCUNo);
	if (cu == null) {
		isSucess = false;
		errMsg = errMsg + "管理单元不能为空\r\n";
	}
	var bizType = PublicBaseUtil.getBizTypeByNumber(ctx, strBizTypeNo);
	if (bizType == null) {
		isSucess = false;
		errMsg = errMsg + "业务类型不能为空\r\n";
	}
	var billType = PublicBaseUtil.getBillTypeByNumber(ctx, strBillTypeNo);
	if (billType == null) {
		isSucess = false;
		errMsg = errMsg + "单据类型不能为空\r\n";
	}
	var trantypeInfo = PublicBaseUtil.getTransactionTypeInfoByNumber(ctx, strTranstionType);
	if (trantypeInfo == null) {
		isSucess = false;
		errMsg = errMsg + "事务类型不能为空\r\n";
	}
	var storeInfo = PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strCUNo);
	if (storeInfo == null) {
		isSucess = false;
		errMsg = errMsg + "供方库存组织不能为空\r\n";
	}
	var demandInfo = PublicBaseUtil.getCompany(ctx, strCUNo);
	if (storeInfo == null) {
		isSucess = false;
		errMsg = errMsg + "需方财务组织不能为空\r\n";
	}
	var supplyInfo = PublicBaseUtil.getCompany(ctx, strCUNo);
	if (storeInfo == null) {
		isSucess = false;
		errMsg = errMsg + "供方财务组织不能为空\r\n";
	}
	// 财务组织
	var company = PublicBaseUtil.getCompany(ctx, cu.getNumber());
	if (company == null) {
		isSucess = false;
		errMsg = errMsg + "财务组织不能为空\r\n";
	}
	var costNumber = PublicBaseUtil.substringByte(socketMsg, "GBK", 90, 4);//机组号  从MES获取
	if (costNumber == null) {
		isSucess = false;
		errMsg = errMsg + "机组号不能为空\r\n";
	} else {
		costNumber = costNumber.replace(" ", "");
	}
	var costCInfo = null;
	if ("".equals(costNumber)) {
		isSucess = false;
		errMsg = errMsg + "机组号不能为空\r\n";
	} else {
		if ("L101".equals(costNumber)) {
			costNumber = "007";
		} else if ("L701".equals(costNumber)) {
			costNumber = "008";
		}
		costCInfo = PublicBaseUtil.getCostCenterOrgUnitInfoByNumber(ctx, costNumber);//成本中心编码  从MES获取
		if (costCInfo == null) {
			isSucess = false;
			errMsg = errMsg + "成本中心不能为空\r\n";
		}
	}
	System.out.println("机组号" + costNumber);

	var bgKd = new BigDecimal("0");
	var bgHd = new BigDecimal("0");
	var kd = PublicBaseUtil.substringByte(socketMsg, "GBK", 150, 5);//宽度  从MES获取
	if (kd != null) {
		kd = kd.trim();
	} else {
		kd = "0";
	}
	try {
		bgKd = new BigDecimal(kd).divide(new BigDecimal("10"));;
		//bgKd=new BigDecimal("1200");
	} catch (e) {

	}
	System.out.println("宽度" + kd);
	var hd = PublicBaseUtil.substringByte(socketMsg, "GBK", 144, 6);//厚度  从MES获取
	if (hd != null) {
		hd = hd.trim();
	} else {
		hd = "0";
	}
	try {
		bgHd = new BigDecimal(hd).divide(new BigDecimal("1000"));
		//bgHd=new BigDecimal("0.80");
	} catch (e) {

	}
	System.out.println("厚度" + hd);
	if (bgKd.compareTo(BigDecimal.ZERO) == 0) {
		isSucess = false;
		errMsg = errMsg + "宽度不能为零\r\n";
	}
	if (bgHd.compareTo(BigDecimal.ZERO) == 0) {
		isSucess = false;
		errMsg = errMsg + "厚度不能为零\r\n";
	}
	var phmc = PublicBaseUtil.substringByte(socketMsg, "GBK", 94, 50); //牌号名称  从MES获取
	if (phmc != null) {
		phmc = phmc.trim();
	} else {
		phmc = "";
	}
	if ("".equals(phmc)) {
		isSucess = false;
		errMsg = errMsg + "牌号名称不能为空\r\n";
	}
	System.out.println("牌号名称" + phmc);
	var ph = "";
	var sql = "SELECT * FROM CT_CUS_Paihao where fname_l2 ='" + phmc + "'";
	var rows = DbUtil.executeQuery(ctx, sql);
	if (rows != null && rows.next()) {
		ph = rows.getString("fnumber");
	}
	System.out.println("牌号名称" + phmc);
	System.out.println("牌号" + ph);
	var strUnit = "吨"; //单位  默认吨
	var materialGroupNum = PublicBaseUtil.substringByte(socketMsg, "GBK", 90, 4);//物料组别  从MES获取
	if (materialGroupNum != null) {
		materialGroupNum = materialGroupNum.trim();
	}
	else {
		materialGroupNum = "";
	}
	if ("".equals(materialGroupNum)) {
		isSucess = false;
		errMsg = errMsg + "品名代码不能为空\r\n";
	}
	System.out.println("品名代码" + materialGroupNum);
	if ("L101".equals(materialGroupNum)) {
		materialGroupNum = "09";
		if ("".equals(ph)) {
			isSucess = false;
			errMsg = errMsg + "牌号流水不存在\r\n";
		}
	} else if ("L701".equals(materialGroupNum)) {
		materialGroupNum = "19";
		if ("".equals(ph)) {
			isSucess = false;
			errMsg = errMsg + "牌号流水不存在\r\n";
		}
	} else {
		materialGroupNum = "06";
		if ("".equals(ph) && phmc != null) {
			var sql = "SELECT * FROM CT_CUS_Rezhapaihao where fname_l2 ='" + phmc + "'";
			var rows = DbUtil.executeQuery(ctx, sql);
			if (rows != null && rows.next()) {
				ph = rows.getString("fnumber");
			} else {
				do {
					var random = new Random();
					var i = random.nextInt(1000);
					i++;
					while (i >= 1000) {
						i--;
					}
					var easnumber = String.format("%04d", i);
					System.out.println("热轧流水" + easnumber);
					var sqlrz = "SELECT * FROM CT_CUS_Rezhapaihao where fnumber ='" + easnumber + "'";
					var rowsrz = DbUtil.executeQuery(ctx, sqlrz);
				} while (rowsrz != null && rowsrz.next())
				var format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				var fcreatetime = new java.util.Date();
				var fcreatetimestr = format.format(fcreatetime);
				var bosid = com.kingdee.bos.util.BOSUuid.create("33181FEC");
				var id = bosid.toString();
				sql = "INSERT INTO CT_CUS_Rezhapaihao (Fid, fnumber, fname_l2, fcreatorid ,fcreatetime,fcontrolunitid ) VALUES ('" + id + "','" + easnumber + "','" + phmc + "','256c221a-0106-1000-e000-10d7c0a813f413B7DE7F',to_date('" + fcreatetimestr + "'),'00000000-0000-0000-0000-000000000000CCE7AED4')";
				System.out.println("select T_ORG_Admin2:" + sql);
				DbUtil.execute(ctx, sql);
				ph = easnumber;
			}
		}
	}
	var material = SocketFacadeFactory.getLocalInstance(ctx).getMaterialInfo("01", "5", materialGroupNum, ph, phmc, bgKd, bgHd, strUnit, "", true);
	if (material == null) {
		isSucess = false;
		errMsg = errMsg + "物料不能为空\r\n";
	}

	var strStockNo = PublicBaseUtil.substringByte(socketMsg, "GBK", 179, 3);//仓库编码  从MES获取
	if (strStockNo == null) {
		isSucess = false;
		errMsg = errMsg + "仓库编码不能为空\r\n";
	} else {
		strStockNo = strStockNo.replace(" ", "");
	}
	if ("".equals(strStockNo)) {
		isSucess = false;
		errMsg = errMsg + "仓库编码不能为空\r\n";
	}
	System.out.println("仓库编码" + strStockNo);
	var bgPrice = new BigDecimal("10");
	var bgTaxRate = new BigDecimal("0");
	var bgAmount = new BigDecimal("1000");

	var bgTaxPrice = bgPrice.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
	var bgTaxAmount = bgAmount.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
	var bgTax = bgAmount.multiply(bgTaxRate).setScale(2, BigDecimal.ROUND_HALF_UP);
	//BigDecimal bgLocalTax=bgTax.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
	//BigDecimal bgLocalPrice =bgPrice.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
	//BigDecimal bgLocalAmount =bgAmount.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);

	//BigDecimal bgLocalTaxPrice =bgTaxPrice.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
	//BigDecimal bgLocalTaxAmount =bgTaxAmount.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);

	if (bgQty.compareTo(BigDecimal.ZERO) == 0) {
		isSucess = false;
		errMsg = errMsg + "重量不能为零\r\n";
	}
	var storOrgUnit = PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
	if (storOrgUnit == null) {
		isSucess = false;
		errMsg = errMsg + "库存组织不能为空\r\n";
	}
	var costObjectInfo = PublicBaseUtil.getCostObjectInfoByNumber(ctx, material.getNumber());
	if (costObjectInfo == null) {
		isSucess = false;
		errMsg = errMsg + "成本对象不能为空\r\n";
	}
	var costCenterInfo = PublicBaseUtil.getCostCenterOrgUnitInfoByNumber(ctx, costNumber);//成本中心编码  从MES获取
	if (costCenterInfo == null) {
		isSucess = false;
		errMsg = errMsg + "成本中心不能为空\r\n";
	}
	var stock = PublicBaseUtil.getWarehouseInfoByNumber(ctx, strStockNo);
	if (stock == null) {
		isSucess = false;
		errMsg = errMsg + "仓库不能为空\r\n";
	}
	var invUpType = PublicBaseUtil.getInvUpdateTypeInfoByNumber(ctx, "002");
	if (invUpType == null) {
		isSucess = false;
		errMsg = errMsg + "更新类型不能为空\r\n";
	}
	if (isSucess) {
		//新增生产领料
		var info = new com.kingdee.eas.scm.im.inv.MaterialReqBillInfo();
		//制单人
		var creator = PublicBaseUtil.getUserByNumber(ctx, strCreatorNo);
		if (creator != null) {
			info.setCreator(creator);
		} else {
			info.setCreator(ContextUtil.getCurrentUserInfo(ctx));
		}
		info.put("bxNumber", "机组生产投料电文CCCW03");
		//获取管理单元
		if (cu != null) {
			info.setCU(cu); //控制单元
		}
		//创建时间
		try {
			info.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
		} catch (e) {

		}
		//业务日期
		try {
			var bizDate;
			var sdf = new SimpleDateFormat("yyyyMMdd");
			bizDate = sdf.parse(strBizDate);
			info.setBizDate(bizDate);
			var c = Calendar.getInstance();
			c.setTime(bizDate);
			var year = c.get(Calendar.YEAR);   //年度
			var period = c.get(Calendar.MONTH);
			var month = c.get(Calendar.YEAR) * 100 + c.get(Calendar.MONTH); //期间
			var iday = (c.get(Calendar.YEAR) * 100 + c.get(Calendar.MONTH) + 1) * 100 + c.get(Calendar.MONDAY);
			info.setYear(year);
			info.setPeriod(period);
			info.setMonth(month);
			info.setDay(iday);
		} catch (ex) {
			isSucess = false;
			errMsg = errMsg + "日期格式不正确\r\n";
			//throw new BOSException("日期格式不正确");

		}
		//单据状态
		info.setBaseStatus(billStatus);
		// 业务类型
		if (bizType != null) {
			info.setBizType(bizType);
		}
		// 单据类型
		if (billType != null) {
			info.setBillType(billType);
		}
		//事务类型
		if (trantypeInfo != null) {
			info.setTransactionType(trantypeInfo);
		}

		//库存组织
		if (storeInfo != null) {
			info.setSupplyStoreOrgUnit(storeInfo);
			info.setStorageOrgUnit(storeInfo);
		}
		if (storeInfo != null) {
			info.setDemandCompanyOrgUnit(demandInfo);
		}
		if (storeInfo != null) {
			info.setSupplyCompanyOrgUnit(supplyInfo);
		}
		//成本中心
		if (costCInfo != null) {
			info.setCostCenterOrgUnit(costCInfo);
			//entry.setReceiveStorageOrgUnit(storOrgUnit);
		}
		//是否含税
		//info.setIsInTax(isInTax);


		//生产领料单分录 

		//big
		//新增分录实体
		var entry = new MaterialReqBillEntryInfo();
		//分录行号
		entry.setSeq(1);
		entry.setSourceBillEntrySeq(0);

		//物料
		if (material != null) {
			entry.setMaterial(material);
			entry.setBaseUnit(material.getBaseUnit());
			//单位
			var unit = PublicBaseUtil.getMeasureUnitInfoByName(ctx, material.getBaseUnit().getMeasureUnitGroup().getNumber(), strUnit);
			if (unit != null) {
				entry.setUnit(unit);
			} else {
				isSucess = false;
				errMsg = errMsg + "单位不能为空\r\n";
			}

		}

		entry.setBaseStatus(EntryBaseStatusEnum.NULL);//
		//
		entry.setAssCoefficient(BigDecimal.ZERO);
		//库存组织
		if (storOrgUnit != null) {
			entry.setStorageOrgUnit(storOrgUnit);
			//entry.setReceiveStorageOrgUnit(storOrgUnit);

		}
		//成本对象
		if (costObjectInfo != null) {
			entry.setCostObject(costObjectInfo);
		}
		//财务组织
		entry.setCompanyOrgUnit(company);
		//entry.setBalanceSupplier(sup);
		//成本中心
		if (costCenterInfo != null) {
			entry.setCostCenterOrgUnit(costCenterInfo);
			//entry.setReceiveStorageOrgUnit(storOrgUnit);

		}
		//仓库
		if (stock != null) {
			entry.setWarehouse(stock);
			//entry.setOutWarehouse(stock);
		}

		entry.setQty(bgQty);
		entry.setBaseQty(bgQty);
		//bgTotalQty=bgTotalQty.add(bgQty);
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
		//entry.setDosingType(DosingTypeEnum.MANUAL);
		//entry.setUnWriteOffQty(bgQty);
		//entry.setUnWriteOffBaseQty(bgQty);
		//entry.setUnWriteOffAmount(bgAmount); //未核销金额
		//entry.setUnReturnedBaseQty(bgQty);

		//entry.setUnitActualCost(bgPrice); //单位实际成本
		//entry.setActualCost(bgAmount); //实际成本 
		//entry.setPurOrderEntrySeq(0);
		//entry.setActualPrice(bgPrice); //实际单价
		//entry.setActualTaxPrice(bgTaxPrice); //实际含税单价
		//entry.setPurchaseCost(bgAmount); //采购成本
		//entry.setUnitPurchaseCost(bgPrice); //单位采购成本

		info.getEntries().addObject(entry);


		//info.setTotalQty(bgTotalQty);
		//info.setTotalAmount(bgTotalAmount);
		//info.setTotalActualCost(bgTotalActualCost);
		//info.setTotalLocalAmount(bgTotalLocalAmount);
	}


	if (isSucess) {
		var pk = MaterialReqBillFactory.getLocalInstance(ctx).save(info);  //保存
		if (pk != null) {
			//		    					Context ctxTemp=ctx.
			var tempIno = MaterialReqBillFactory.getLocalInstance(ctx).getMaterialReqBillInfo(pk);
			if (tempIno.getBaseStatus().equals(BillBaseStatusEnum.TEMPORARILYSAVED)) {
				MaterialReqBillFactory.getLocalInstance(ctx).submit(tempIno);
			}
			tempIno = MaterialReqBillFactory.getLocalInstance(ctx).getMaterialReqBillInfo(pk);
			if (tempIno.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
				MaterialReqBillFactory.getLocalInstance(ctx).audit(pk);
			}


		}
	} else {
		methodCtx.getParam(2).setValue("false");
		methodCtx.getParam(4).setValue(errMsg);
	}
	var strMsg = "0110" + dwh + "CWCC";
	//methodCtx.getParam(2).setValue("true");
	//var strData=String.format("%-81s", "A")+"\n" ;
	//methodCtx.getParam(3).setValue(strMsg+strData);
	if (isSucess) {
		methodCtx.getParam(2).setValue("true");
		var strData = String.format("%-81s", "A") + "\n";
		methodCtx.getParam(3).setValue(strMsg + strData);
	} else {
		methodCtx.getParam(2).setValue("false");
		methodCtx.getParam(4).setValue(dwt + errMsg);
		var strMsg = "0110" + dwh + "CWCCB";
		var strData = String.format("%-80s", errMsg) + "\n"
		methodCtx.getParam(3).setValue(strMsg + strData);
	}


}