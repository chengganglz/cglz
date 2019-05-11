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

with(imp) {
	var socketMsg = methodCtx.getParamValue(0); //接收报文 
	// socketMsg ="          20190429           TEST0002                                                    L101DD11                                              0025401510400000109201C021M                    ";
	var billID = methodCtx.getParamValue(1); // 业务单元ID  
	var result = methodCtx.getParamValue(2); //成功失败  true:false  必须有返回值  methodCtx.getParam(2).setValue("true") 
	var sendMsg = methodCtx.getParamValue(3); // 发送报文   如果需要发送电文,将电文拼好放到这里返回  methodCtx.getParam(3).setValue("")
	var errMsg = methodCtx.getParamValue(4); //错误消息 methodCtx.getParam(4).setValue("")  
	var ctx = pluginCtx.getContext(); //服务端上下文 
	var isSucess = true;
	var errMsg = "";
	var strCreatorNo = "user"; //创建人编码
	var strCUNo = "01"; // 管理单元编码
	//var strBizDate="20190418" ; //业务日期  从MES获取
	var billStatus = BillBaseStatusEnum.ADD; //单据状态
	var purchaseType = PurchaseTypeEnum.PURCHASE;
	var strBizTypeNo = "501"; //业务类型编码
	var strBillTypeNo = "108"; //单据类型编码
	var strTranstionType = "032"; //事务类型
	var strNeedStockOrgNo = "01"; //需方库存组织
	var isInTax = true;
	var sdfDate = new SimpleDateFormat("yyyy-MM-dd");
	var dwh = PublicBaseUtil.substringByte(socketMsg, "GBK", 5, 20); //电文号  从MES获取
	//dwh="CCCW0120190418110558";
	System.out.println("电文号" + dwh);
	var dwt = PublicBaseUtil.substringByte(socketMsg, "GBK", 1, 29); //电文头  从MES获取
	//dwt="0524CCCW2020190224040527CCCWD11203778";
	System.out.println("CCCW11" + dwt);
	var flag = PublicBaseUtil.substringByte(socketMsg, "GBK", 165, 1); //操作标志  从MES获取
	if (flag != null) {
		flag = flag.trim();
	}
	System.out.println("操作标志" + flag);
	if ("1".equals(flag)) {
		var strBizDate = PublicBaseUtil.substringByte(socketMsg, "GBK", 11, 8); //业务日期  从MES获取
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
		var cu = PublicBaseUtil.getCU(ctx, strCUNo);
		if (cu == null) {
			isSucess = false;
			errMsg = errMsg + "管理单元不能为空\r\n";
		}
		// 业务类型
		var bizType = PublicBaseUtil.getBizTypeByNumber(ctx, strBizTypeNo);
		if (bizType == null) {
			isSucess = false;
			errMsg = errMsg + "业务类型不能为空\r\n";
		}
		// 单据类型
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
		//成本中心
		var costCenter = PublicBaseUtil.getCostCenterOrgUnitInfoById(ctx, cu.getId().toString());
		if (costCenter == null) {
			isSucess = false;
			errMsg = errMsg + "成本中心不能为空\r\n";
		}
		//主业务组织
		var storageInfo = PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strCUNo);
		if (storageInfo == null) {
			isSucess = false;
			errMsg = errMsg + "主业务组织不能为空\r\n";
		}
		var strMaterialNo = "";
		var strUnit = "吨"; //单位默认吨
		var strLot = PublicBaseUtil.substringByte(socketMsg, "GBK", 30, 20); //批次号  从MES获取
		var strStockNo = PublicBaseUtil.substringByte(socketMsg, "GBK", 166, 3); //"001";//仓库编码  从MES获取
		var bgQty = new BigDecimal("0");
		var bgPrice = new BigDecimal("10");
		var bgTaxRate = new BigDecimal("0");
		var bgAmount = new BigDecimal("1000");

		var bgTaxPrice = bgPrice.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
		var bgTaxAmount = bgAmount.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
		var bgTax = bgAmount.multiply(bgTaxRate).setScale(2, BigDecimal.ROUND_HALF_UP);
		if (strLot != null) {
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
		var ph = "";
		var sql = "SELECT * FROM CT_CUS_Paihao where fname_l2 ='" + phmc + "'";
		var rows = DbUtil.executeQuery(ctx, sql);
		if (rows != null && rows.next()) {
			ph = rows.getString("fnumber");
		} else {
			ph = phmc;
		}
		System.out.println("牌号名称" + phmc);
		System.out.println("牌号" + ph);

		var kd = PublicBaseUtil.substringByte(socketMsg, "GBK", 150, 5); //宽度  从MES获取
		if (kd != null) {
			kd = kd.trim();
		} else {
			kd = "0";
		}
		try {
			bgKd = new BigDecimal(kd).divide(new BigDecimal("10"));;
			//bgKd=BigDecimal.valueOf(4160);
		} catch (e) {

		}
		var hd = PublicBaseUtil.substringByte(socketMsg, "GBK", 144, 6); //厚度  从MES获取
		if (hd != null) {
			hd = hd.trim();
		} else {
			hd = "0";
		}
		try {
			bgHd = new BigDecimal(hd).divide(new BigDecimal("1000"));
			//bgHd=BigDecimal.valueOf(0.81);
		} catch (e) {

		}
		var zl = PublicBaseUtil.substringByte(socketMsg, "GBK", 155, 10); //重量  从MES获取
		if (zl != null) {
			zl = zl.trim();
		} else {
			zl = "0";
		}
		//zl="20";
		try {
			bgQty = new BigDecimal(zl).divide(new BigDecimal("1000"));

		} catch (e) {

		}
		if (bgKd.compareTo(BigDecimal.ZERO) == 0) {
			isSucess = false;
			errMsg = errMsg + "宽度不能为零\r\n";
		}
		if (bgHd.compareTo(BigDecimal.ZERO) == 0) {
			isSucess = false;
			errMsg = errMsg + "厚度不能为零\r\n";
		}
		if (bgQty.compareTo(BigDecimal.ZERO) == 0) {
			isSucess = false;
			errMsg = errMsg + "重量不能为零\r\n";
		}
		System.out.println("重量" + zl);
		System.out.println("厚度" + hd);
		System.out.println("宽度" + kd);
		//var materialGroupNum="09";//物料组别  从MES获取
		var materialGroupNum = PublicBaseUtil.substringByte(socketMsg, "GBK", 90, 4);
		if (materialGroupNum != null) {
			materialGroupNum = materialGroupNum.trim();
		} else {
			materialGroupNum = "";
		}
		if ("".equals(materialGroupNum)) {
			isSucess = false;
			errMsg = errMsg + "机组号不能为空\r\n";
		}
		if ('L101'.equals(materialGroupNum)) {
			materialGroupNum = '09'
		} else if ('L701'.equals(materialGroupNum)) {
			materialGroupNum = '19'
		} else if ('L201'.equals(materialGroupNum)) {
			materialGroupNum = '17'
		}
		var material = null;
		System.out.println("物料组别" + materialGroupNum);
		if (isSucess) {
			material = SocketFacadeFactory.getLocalInstance(ctx).getMaterialInfo("01", "5", materialGroupNum, ph, phmc, bgKd, bgHd, strUnit, "", true);
			if (material == null) {
				isSucess = false;
				errMsg = errMsg + "物料不能为空\r\n";
			}
		}
		var storOrgUnit = PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
		if (storOrgUnit == null) {
			isSucess = false;
			errMsg = errMsg + "库存组织不能为空\r\n";
		}
		// var sqlLot = "SELECT * FROM T_IM_Inventory where FLot ='"+strLot+"'";
		// System.out.println("sqlLot"+sqlLot);
		// var strStockNo="";
		// var rows = DbUtil.executeQuery(ctx, sqlLot);
		// 	if(rows!= null && rows.next()) {
		// 		var FID=rows.getString("FWarehouseID");

		// 		var strStockNosql = "SELECT * FROM T_DB_WAREHOUSE WHERE FID='"+FID+"'";
		// 		var row1 = DbUtil.executeQuery(ctx, strStockNosql);
		// 		if(row1!=null &&row1.next()){
		// 			strStockNo = row1.getString("FNUMBER");
		// 		}
		// 	}else{
		// 		isSucess=false;
		// 		errMsg=errMsg + "仓库内没有此物料\r\n";
		// 	}
		var stock = PublicBaseUtil.getWarehouseInfoByNumber(ctx, strStockNo);
		if (stock == null) {
			isSucess = false;
			errMsg = errMsg + "仓库不能为空\r\n";
		}
		var invUpType = PublicBaseUtil.getInvUpdateTypeInfoByNumber(ctx, "001");
		if (invUpType == null) {
			isSucess = false;
			errMsg = errMsg + "更新类型不能为空\r\n";
		}
		if (isSucess) {
			//新增委外领料
			var info = new OtherInWarehsBillInfo();
			info.put("bxNumber", "盘盈电文CCCW11");
			//制单人
			var creator = PublicBaseUtil.getUserByNumber(ctx, strCreatorNo);

			if (creator != null) {
				info.setCreator(creator);
			} else {
				info.setCreator(ContextUtil.getCurrentUserInfo(ctx));
			}
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
				var year = c.get(Calendar.YEAR); //年度
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
			//info.setPurchaseType(purchaseType);
			// 业务类型
			if (bizType != null) {
				info.setBizType(bizType);
			}
			// 单据类型
			if (billType != null) {
				info.setBillType(billType);
			}
			if (trantypeInfo != null) {
				info.setTransactionType(trantypeInfo);

			}

			//成本中心
			if (costCenter != null) {
				info.setCostCenterOrgUnit(costCenter);
			}
			// 财务组织
			var company = PublicBaseUtil.getCompany(ctx, cu.getNumber());
			//主业务组织
			if (storageInfo != null) {
				info.setStorageOrgUnit(storageInfo);
			}
			//盘盈单分录 

			//新增分录实体
			var entry = new OtherInWarehsBillEntryInfo();
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

			entry.setBaseStatus(EntryBaseStatusEnum.ADD); //
			//
			entry.setAssCoefficient(BigDecimal.ZERO);
			//库存组织
			if (storOrgUnit != null) {
				entry.setStorageOrgUnit(storOrgUnit);
				//entry.setReceiveStorageOrgUnit(storOrgUnit);

			}
			//财务组织
			entry.setCompanyOrgUnit(company);
			//仓库
			if (stock != null) {
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
		if (isSucess) {

			//MaterialReqBillInfo info1 = MaterialReqBillFactory.getLocalInstance(ctx).getMaterialReqBillInfo(new ObjectUuidPK("23sE0JysRKmP7TP55oLn7VAKt14="));

			var pk = OtherInWarehsBillFactory.getLocalInstance(ctx).save(info); //保存
			if (pk != null) {
				var tempIno = OtherInWarehsBillFactory.getLocalInstance(ctx).getOtherInWarehsBillInfo(pk);
				if (tempIno.getBaseStatus().equals(BillBaseStatusEnum.TEMPORARILYSAVED)) {

					OtherInWarehsBillFactory.getLocalInstance(ctx).submit(tempIno);
				}
				tempIno = OtherInWarehsBillFactory.getLocalInstance(ctx).getOtherInWarehsBillInfo(pk);
				if (tempIno.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
					OtherInWarehsBillFactory.getLocalInstance(ctx).audit(pk);
				}
				methodCtx.getParam(2).setValue("true");
				methodCtx.getParam(4).setValue("新增成功");

			}
		} else {
			methodCtx.getParam(2).setValue("false");
			methodCtx.getParam(4).setValue(errMsg);
		}
	} else if ("2".equals(flag)) {

		System.out.println("isSucess1" + isSucess);
		var strBizDate = PublicBaseUtil.substringByte(socketMsg, "GBK", 11, 8); //业务日期  从MES获取
		if (strBizDate == null) {
			isSucess = false;
			errMsg = errMsg + "业务日期不能为空\r\n";
			System.out.println("Error-业务日期不能为空-strBizDate" + strBizDate);
		} else {
			strBizDate = strBizDate.replace(" ", "");
		}
		if ("".equals(strBizDate)) {
			isSucess = false;
			errMsg = errMsg + "业务日期不能为空\r\n";
			System.out.println("Error-业务日期不能为空-strBizDate" + strBizDate);
		} else if (strBizDate != null) {
			var isdate = PublicBaseUtil.valiDateTimeWithLongFormat(strBizDate);
			if (!isdate) {
				isSucess = false;
				errMsg = errMsg + "业务日期格式不合法\r\n";
				System.out.println("Error-业务日期格式不合法-isdate" + isdate);
			}
		}

		System.out.println("业务日期" + strBizDate);
		var strLot = PublicBaseUtil.substringByte(socketMsg, "GBK", 30, 20); //批次号  从MES获取
		var bgQty = new BigDecimal("0");

		errMsg = "";
		strCreatorNo = "user"; //创建人编码
		strCUNo = "01"; // 管理单元编码
		//strBizDate="20190418" ; //业务日期  从MES获取
		billStatus = BillBaseStatusEnum.ADD; //单据状态
		purchaseType = PurchaseTypeEnum.PURCHASE;
		strBizTypeNo = "511"; //业务类型编码
		strBillTypeNo = "108"; //单据类型编码
		strTranstionType = "029"; //事务类型
		strNeedStockOrgNo = "01"; //需方库存组织
		isInTax = true;
		sdfDate = new SimpleDateFormat("yyyyMMdd");
		if (strLot != null) {
			strLot = strLot.trim();
		}
		var zl = PublicBaseUtil.substringByte(socketMsg, "GBK", 155, 10); //重量  从MES获取
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
			System.out.println("Error-重量不能为零-bgQty");
		}
		var strSQLInv = "select * from T_IM_Inventory where flot='" + strLot + "'";
		System.out.println("销售出库接口" + strSQLInv);
		var rsInv = DbUtil.executeQuery(ctx, strSQLInv);
		if (rsInv.next()) {
			var curStoreQty = rsInv.getBigDecimal("FCURSTOREQTY");
			if (bgQty.compareTo(curStoreQty) != 0) {
				isSucess = false;
				errMsg = errMsg + "与库存重量不一致\r\n";
				System.out.println("Error-与库存重量不一致-bgQty" + curStoreQty + '-' + bgQty);
			}
		}
		var cu = PublicBaseUtil.getCU(ctx, strCUNo);
		if (cu == null) {
			isSucess = false;
			errMsg = errMsg + "管理单元不能为空\r\n";
			System.out.println("Error-管理单元-cu" + cu);
		} else {

		}
		System.out.println("isSucess3" + isSucess);
		// 业务类型
		var bizType = PublicBaseUtil.getBizTypeByNumber(ctx, strBizTypeNo);
		if (bizType == null) {
			isSucess = false;
			errMsg = errMsg + "业务类型不能为空\r\n";
			System.out.println("Error-业务类型-bizType" + bizType);
		} else {

		}
		System.out.println("isSucess4" + isSucess);
		// 单据类型
		var billType = PublicBaseUtil.getBillTypeByNumber(ctx, strBillTypeNo);
		if (billType == null) {
			isSucess = false;
			errMsg = errMsg + "单据类型不能为空\r\n";
			System.out.println("Error-单据类型-billType" + billType);
		} else {

		}
		var trantypeInfo = PublicBaseUtil.getTransactionTypeInfoByNumber(ctx, strTranstionType);
		if (trantypeInfo == null) {
			isSucess = false;
			errMsg = errMsg + "事务类型不能为空\r\n";
			System.out.println("Error-事务类型-trantypeInfo" + trantypeInfo);
		} else {

		}
		//成本中心
		var costCenter = PublicBaseUtil.getCostCenterOrgUnitInfoById(ctx, cu.getId().toString());
		if (costCenter == null) {
			isSucess = false;
			errMsg = errMsg + "成本中心不能为空\r\n";
			System.out.println("Error-成本中心-costCenter" + costCenter);
		} else {

		}
		var storageInfo = PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strCUNo);
		if (storageInfo == null) {
			isSucess = false;
			errMsg = errMsg + "主业务组织不能为空\r\n";
			System.out.println("Error-主业务组织-storageInfo" + storageInfo);
		} else {

		}
		var strMaterialNo = "";
		var strUnit = "吨"; //单位默认吨

		var sqlLot = "SELECT * FROM T_IM_Inventory where FLot ='" + strLot + "'";
		System.out.println("sqlLot" + sqlLot);
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
			System.out.println("Error-仓库内没有此物料-strStockNo"+strStockNo);
		}


		//var strStockNo="003";//仓库编码  从MES获取
		System.out.println("仓库编码" + strStockNo);
		if (strStockNo == null) {
			isSucess = false;
			errMsg = errMsg + "仓库编码不能为空\r\n";
			System.out.println("Error-仓库编码-strStockNo" + strStockNo);
		} else {

		}

		var bgPrice = new BigDecimal("10");
		var bgTaxRate = new BigDecimal("0");
		var bgAmount = new BigDecimal("1000");

		var bgTaxPrice = bgPrice.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
		var bgTaxAmount = bgAmount.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
		var bgTax = bgAmount.multiply(bgTaxRate).setScale(2, BigDecimal.ROUND_HALF_UP);
		var bgKd = new BigDecimal("0");
		var bgHd = new BigDecimal("0");
		var kd = PublicBaseUtil.substringByte(socketMsg, "GBK", 150, 5); //宽度  从MES获取
		if (kd != null) {
			kd = kd.trim();
		} else {
			kd = "0";
		}
		try {
			bgKd = new BigDecimal(kd).divide(new BigDecimal("10"));;
			//bgKd=new BigDecimal("1200");
		} catch (e) {
			System.out.println("Error-宽度-bgKd" + bgKd);
		}
		var hd = PublicBaseUtil.substringByte(socketMsg, "GBK", 144, 6); //厚度  从MES获取
		if (hd != null) {
			hd = hd.trim();
		} else {
			hd = "0";
		}
		try {
			bgHd = new BigDecimal(hd).divide(new BigDecimal("1000"));
			//bgHd=new BigDecimal("0.80");
		} catch (e) {
			System.out.println("Error-厚度-bgHd" + bgHd);
		}
		if (bgKd.compareTo(BigDecimal.ZERO) == 0) {
			isSucess = false;
			errMsg = errMsg + "宽度不能为零\r\n";
			System.out.println("Error-宽度-bgKd" + bgKd);
		} else {

		}
		if (bgHd.compareTo(BigDecimal.ZERO) == 0) {
			isSucess = false;
			errMsg = errMsg + "厚度不能为零\r\n";
			System.out.println("Error-厚度-bgHd" + bgHd);
		} else {

		}
		System.out.println("重量" + zl);
		System.out.println("厚度" + hd);
		System.out.println("宽度" + kd);
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
		var phmc = PublicBaseUtil.substringByte(socketMsg, "GBK", 94, 50); //牌号名称  从MES获取
		if (phmc != null) {
			phmc = phmc.trim();
		} else {
			phmc = "";
		}
		if ("".equals(phmc)) {
			isSucess = false;
			errMsg = errMsg + "牌号名称不能为空\r\n";
			System.out.println("Error-牌号名称-phmc" + phmc);
		} else {

		}
		System.out.println("牌号名称" + phmc);
		var ph = "";
		var sql = "SELECT * FROM CT_CUS_Paihao where fname_l2 ='" + phmc + "'";
		var rows = DbUtil.executeQuery(ctx, sql);
		if (rows != null && rows.next()) {
			ph = rows.getString("fnumber");
		} else {
			ph = phmc;
		}
		System.out.println("牌号名称" + phmc);
		System.out.println("牌号" + ph);
		var strUnit = "吨"; //单位  默认吨
		//var materialGroupNum="06";//物料组别  从MES获取
		
		var material = null;
		var sqlmat = "SELECT mat.FNUMBER  mnumber FROM T_IM_Inventory inv left join T_BD_Material mat on mat.fid=inv.FMaterialID where FLOT ='"+strLot+"' and FCURSTOREQTY <> 0 ";
		var rowsmat = DbUtil.executeQuery(ctx, sqlmat);
		if (rowsmat != null && rowsmat.next()) {
			var mnumber = rowsmat.getString("mnumber");
			material = PublicBaseUtil.getMaterialInfoByNumber(ctx, cu.getId().toString(), mnumber);
		}else{
			isSucess = false;
			errMsg = errMsg + "该物料批次不存在\r\n";
		}
		if (material == null) {
			isSucess = false;
			errMsg = errMsg + "物料不能为空\r\n";
			System.out.println("Error-物料" + material);
		} else {

		}
		var storOrgUnit = PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
		if (storOrgUnit == null) {
			isSucess = false;
			errMsg = errMsg + "库存组织不能为空\r\n";
			System.out.println("Error-库存组织" + storOrgUnit);
		} else {

		}
		var stock = PublicBaseUtil.getWarehouseInfoByNumber(ctx, strStockNo);
		if (stock == null) {
			isSucess = false;
			errMsg = errMsg + "仓库不能为空\r\n";
			System.out.println("Error-仓库" + stock);
		} else {

		}
		var invUpType = PublicBaseUtil.getInvUpdateTypeInfoByNumber(ctx, "002");
		if (invUpType == null) {
			isSucess = false;
			errMsg = errMsg + "更新类型不能为空\r\n";
			System.out.println("Error-更新类型" + invUpType);
		} else {

		}

		System.out.println("isSucess" + isSucess);
		if (isSucess) {
			//新增盘亏
			var info = new OtherIssueBillInfo();
			info.put("bxNumber", "盘亏电文CCCW11");
			//制单人
			var creator = PublicBaseUtil.getUserByNumber(ctx, strCreatorNo);

			if (creator != null) {
				info.setCreator(creator);
			} else {
				info.setCreator(ContextUtil.getCurrentUserInfo(ctx));
			}
			//获取管理单元
			if (cu != null) {
				info.setCU(cu); //控制单元
			} else {
				System.out.println("控制单元" + cu);
			}
			//创建时间
			try {
				info.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
			} catch (e) {
				System.out.println("Error-创建时间异常");
			}
			//业务日期
			try {
				var bizDate;
				var sdf = new SimpleDateFormat("yyyyMMdd");
				bizDate = sdf.parse(strBizDate);
				info.setBizDate(bizDate);
				var c = Calendar.getInstance();
				c.setTime(bizDate);
				var year = c.get(Calendar.YEAR); //年度
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
				System.out.println("Error-日期格式不正确");

			}
			//单据状态
			info.setBaseStatus(billStatus);
			//info.setPurchaseType(purchaseType);
			// 业务类型
			if (bizType != null) {
				info.setBizType(bizType);
			} else {
				System.out.println("Error-业务类型-bizType，为空");
			}
			// 单据类型
			if (billType != null) {
				info.setBillType(billType);
			}
			//事务类型 
			if (trantypeInfo != null) {
				info.setTransactionType(trantypeInfo);
			} else {
				System.out.println("Error-事务类型-trantypeInfo,为空");
			}
			//成本中心
			if (costCenter != null) {
				System.out.println("成本中心" + costCenter);
				info.setCostCenterOrgUnit(costCenter);
			} else {
				System.out.println("Error-成本中心-costCenter,为空");
			}
			// 财务组织
			var company = PublicBaseUtil.getCompany(ctx, cu.getNumber());
			//主业务组织
			if (storageInfo != null) {
				info.setStorageOrgUnit(storageInfo);
			} else {
				System.out.println("Error-主业务组织-storageInfo,为空");
			}

			//盘亏单分录 
			//新增分录实体
			var entry = new OtherIssueBillEntryInfo();
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

			} else {
				System.out.println("Error-物料-material,为空");
			}

			entry.setBaseStatus(EntryBaseStatusEnum.ADD); //
			//
			entry.setAssCoefficient(BigDecimal.ZERO);
			//库存组织
			if (storOrgUnit != null) {
				entry.setStorageOrgUnit(storOrgUnit);
				//entry.setReceiveStorageOrgUnit(storOrgUnit);

			} else {
				System.out.println("Error-库存组织-storOrgUnit,为空");
			}
			//财务组织
			entry.setCompanyOrgUnit(company);
			//仓库
			if (stock != null) {
				entry.setWarehouse(stock);
				//entry.setOutWarehouse(stock);
			} else {
				System.out.println("Error-仓库-stock,为空");
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
		} else {
			System.out.println("Error-前置条件不满足，无法生成其他出库单实体");
		}
		//entry.getInvUpdateType().getStoreTypePre().getStoreFlag();

		if (isSucess) {

			//MaterialReqBillInfo info1 = MaterialReqBillFactory.getLocalInstance(ctx).getMaterialReqBillInfo(new ObjectUuidPK("23sE0JysRKmP7TP55oLn7VAKt14="));
			System.out.println("生成单子");
			var pk = OtherIssueBillFactory.getLocalInstance(ctx).save(info); //保存
			if (pk != null) {
				var tempIno = OtherIssueBillFactory.getLocalInstance(ctx).getOtherIssueBillInfo(pk);
				if (tempIno.getBaseStatus().equals(BillBaseStatusEnum.TEMPORARILYSAVED)) {

					OtherIssueBillFactory.getLocalInstance(ctx).submit(tempIno);
				}
				tempIno = OtherIssueBillFactory.getLocalInstance(ctx).getOtherIssueBillInfo(pk);
				if (tempIno.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
					OtherIssueBillFactory.getLocalInstance(ctx).audit(pk);
				}
				methodCtx.getParam(2).setValue("true");
				methodCtx.getParam(4).setValue("新增成功");
				System.out.println("生成单子");
			}
		} else {
			methodCtx.getParam(2).setValue("false");
			methodCtx.getParam(4).setValue(errMsg);
		}
	}
	// var strMsg = "0110" + dwh + "CWCC";
	// methodCtx.getParam(2).setValue("true");
	// var strData=String.format("%-81s", "A")+"\n" ;
	// System.out.println("应答报文："+strMsg+strData);
	// methodCtx.getParam(3).setValue(strMsg+strData);
	if (isSucess) {
		var strMsg = "0110" + dwh + "CWCC";
		methodCtx.getParam(2).setValue("true");
		var strData = String.format("%-81s", "A") + "\n";
		System.out.println("应答报文：" + strMsg + strData);
		methodCtx.getParam(3).setValue(strMsg + strData);
	} else {
		methodCtx.getParam(2).setValue("false");
		methodCtx.getParam(4).setValue(dwt + errMsg);
		var strMsg = "0110" + dwh + "CWCCB";

		var strData = String.format("%-80s", errMsg) + "\n"
		System.out.println("应答报文：" + strMsg + strData);
		methodCtx.getParam(3).setValue(strMsg + strData);
	}
}