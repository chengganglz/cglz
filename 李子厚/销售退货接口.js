var imp = JavaImporter();
imp.importPackage(Packages.com.kingdee.eas.util.app);
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
imp.importPackage(Packages.com.kingdee.eas.scm.im.inv);
imp.importPackage(Packages.com.kingdee.bos.util);
imp.importPackage(Packages.com.kingdee.bos.metadata.entity);
imp.importPackage(Packages.com.kingdee.eas.framework);
imp.importPackage(Packages.com.kingdee.eas.ep);
imp.importPackage(Packages.com.kingdee.bos.metadata.bot);
imp.importPackage(Packages.com.kingdee.eas.base.btp);
imp.importPackage(Packages.com.kingdee.bos.dao.ormapping);
imp.importPackage(Packages.com.kingdee.eas.basedata.scm.common);
imp.importPackage(Packages.com.kingdee.jdbc.rowset);
with(imp) {
	var socketMsg = methodCtx.getParamValue(0); //接收报文 
	// socketMsg = "          20190429           lzytest201904301344 TESTSHIHUI2019042501X190425002          DD11                                              003500150400000027800000000250000C02 ";
	var billID = methodCtx.getParamValue(1); // 业务单元ID  
	var result = methodCtx.getParamValue(2); //成功失败  true:false  必须有返回值  methodCtx.getParam(2).setValue("true") 
	var sendMsg = methodCtx.getParamValue(3); // 发送报文   如果需要发送电文,将电文拼好放到这里返回  methodCtx.getParam(3).setValue("")
	var errMsg = methodCtx.getParamValue(4); //错误消息 methodCtx.getParam(4).setValue("")  
	var ctx = pluginCtx.getContext(); //服务端上下文 
	var isSucess = true;
	errMsg = "";
	var lot = PublicBaseUtil.substringByte(socketMsg, "GBK", 30, 20); //"111";//批次号  从MES获取
	if (lot != null) {
		lot = lot.trim();
	} else {
		lot = "";
	}
	var bgKd = new BigDecimal("0");
	var bgHd = new BigDecimal("0");
	// 库区号
	// var kqh = '';
	var kqh = PublicBaseUtil.substringByte(socketMsg, "GBK", 173, 3); //库区号 003 从MES获取
	if (kqh != null) {
		kqh = kqh.trim();
	} else {
		kqh = "";
	}
	if ("".equals(kqh)) {
		isSucess = false;
		System.out.println("Error-库区号不能为空,无法进行,中断");
		errMsg = errMsg + "库区号不能为空\r\n";
	}
	var kd = PublicBaseUtil.substringByte(socketMsg, "GBK", 146, 5); //宽度  从MES获取
	if (kd != null) {
		kd = kd.trim();
	} else {
		kd = "0";
	}
	try {
		bgKd = new BigDecimal(kd).divide(new BigDecimal("10"));;
		// bgKd=new BigDecimal("1200");
	} catch (e) {

	}
	var hd = PublicBaseUtil.substringByte(socketMsg, "GBK", 140, 6); //厚度  从MES获取
	if (hd != null) {
		hd = hd.trim();
	} else {
		hd = "0";
	}
	try {
		bgHd = new BigDecimal(hd).divide(new BigDecimal("1000"));
		// bgHd=new BigDecimal("0.80");
	} catch (e) {

	}
	if (bgKd.compareTo(BigDecimal.ZERO) == 0) {
		isSucess = false;
		errMsg = errMsg + "宽度不能为零\r\n";
		System.out.println("Error-宽度不能为零,无法进行,中断");
	}
	if (bgHd.compareTo(BigDecimal.ZERO) == 0) {
		isSucess = false;
		errMsg = errMsg + "厚度不能为零\r\n";
		System.out.println("Error-厚度不能为零,无法进行,中断");
	}
	// var ph="0422"; //牌号 从MES获取
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
	// var phmc="0422"; //牌号名称  从MES获取
	// if(phmc!=null){
	// 	phmc=phmc.trim();
	// }else{
	// 	phmc="";
	// }
	// if("".equals(phmc))
	// {
	// 	isSucess=false;
	// 	errMsg=errMsg + "牌号名称不能为空\r\n";
	// }
	var phmc = PublicBaseUtil.substringByte(socketMsg, "GBK", 90, 50); //牌号名称  从MES获取
	if (phmc != null) {
		phmc = phmc.trim();
	} else {
		phmc = "";
	}
	if ("".equals(phmc)) {
		isSucess = false;
		System.out.println("Error-牌号名称不能为空,无法进行,中断");
		errMsg = errMsg + "牌号名称不能为空\r\n";
	}
	var ph = ""; //牌号 从MES获取
	var sql = "SELECT * FROM CT_CUS_Paihao where fname_l2 ='" + phmc + "'";
	var rows = DbUtil.executeQuery(ctx, sql);
	if (rows != null && rows.next()) {
		ph = rows.getString("fnumber");
	} else {
		ph = phmc;
	}
	System.out.println("牌号名称-phmc:" + phmc);
	System.out.println("牌号-ph:" + ph);
	if (ph != null) {
		ph = ph.trim();
	} else {
		ph = "";
	}
	if ("".equals(ph)) {
		isSucess = false;
		System.out.println("Error-牌号不能为空,无法进行,中断");
		errMsg = errMsg + "牌号不能为空\r\n";
	}
	// 从MES中获取 退货重量 EAS 数量
	var bgQty = new BigDecimal("0");
	var zl = PublicBaseUtil.substringByte(socketMsg, "GBK", 151, 10); //重量  从MES获取
	System.out.println("重量-zl:" + zl);
	// zl="20";
	if (zl != null) {
		zl = zl.trim();
	} else {
		zl = "0";
	}
	try {
		bgQty = new BigDecimal(zl).divide(new BigDecimal(1000));;
	} catch (e) {

	}
	// 退货价格
	var price = PublicBaseUtil.substringByte(socketMsg, "GBK", 161, 12); //	
	if (price != null) {
		price = price.trim();
	} else {
		price = "0";
	}
	try {
		price = new BigDecimal(price).divide(new BigDecimal("100"));
		// price=new BigDecimal("12020").divide(new BigDecimal("10"));
	} catch (e) {

	}
	System.out.println("退货价格-price:" + price);
	// var strUnit="吨"; //吨
	// var materialGroupNum="06";//物料组别  从MES获取
	// var material= SocketFacadeFactory.getLocalInstance(ctx).getMaterialInfo("01", "5", materialGroupNum, ph, phmc, bgKd, bgHd, strUnit, "", true);
	// var mnumber="";
	// if (material==null){
	// 	isSucess=false;
	// 	errMsg=errMsg + "物料不能为空\r\n";
	// }else{
	// 	mnumber=material.getNumber();
	// }
	var sdfstr = PublicBaseUtil.substringByte(socketMsg, "GBK", 11, 8); //"20190416";//业务日期  从MES获取
	var destType = BOSObjectType.create("CC3E933B");
	var sourceType = BOSObjectType.create("CC3E933B");
	var botpId = BOSUuid.read("/4CzhgEPEADgAARkwKgSOgRRIsQ=");
	System.out.println("销售退货接口开始");
	// var strSQL1 = "select sale.fid saleid from T_IM_SaleIssueBill sale left join T_SCM_TransactionType trans on sale.FTRANSACTIONTYPEID=trans.fid  left join T_IM_SaleIssueEntry entry on entry.fparentid=sale.fid left join T_BD_Material material on material.fid=entry.fmaterialid where material.fnumber='"+mnumber+"' and entry.flot='"+lot+"' and (sale.cfisxiatui=0 or sale.cfisxiatui is null) and trans.fnumber='010'";
	// 修改原因：一个批次号唯一对应一个物料，那么就不需要根据物料在做过滤条件了
	var strSQL1 = "select sale.fid saleid from T_IM_SaleIssueBill sale left join T_SCM_TransactionType trans on sale.FTRANSACTIONTYPEID=trans.fid  left join T_IM_SaleIssueEntry entry on entry.fparentid=sale.fid where entry.flot='" + lot + "' and (sale.cfisxiatui=0 or sale.cfisxiatui is null) and trans.fnumber='010'";
	System.out.println("销售退货接口" + strSQL1);
	var rs = DbUtil.executeQuery(ctx, strSQL1);
	var saleid = "";
	while (rs.next()) {
		saleid = rs.getString("saleid");
	}
	var dwh = PublicBaseUtil.substringByte(socketMsg, "GBK", 5, 20); //电文号  从MES获取
	// dwh="CCCW2020190224040527CCCWD";
	var dwt = PublicBaseUtil.substringByte(socketMsg, "GBK", 1, 29); //电文头  从MES获取
	// dwt="0524CCCW2020190224040527CCCWD11203778";
	System.out.println("电文号" + dwh);
	if (isSucess) {
		if (saleid != null && !"".equals(saleid)) {
			var view = new EntityViewInfo();
			var sic = view.getSelector();
			sic.add("*");

			sic.add("entries.*");

			var filter = new FilterInfo();
			var fic = filter.getFilterItems();
			fic.add(new FilterItemInfo("id", saleid));
			view.setFilter(filter);
			var sourceColl = SaleIssueBillFactory.getLocalInstance(ctx).getSaleIssueBillCollection(view);
			//var sourceColl = CoreBillBaseCustomFactory.getLocalInstance(ctx).getCoreBillBaseCollection(view);
			var newcoll = sourceColl.cast(new CoreBillBaseCollection().getClass());
			System.out.println("销售退货接口:" + newcoll.get(0).get("number"));
			var destinfo = PublicBaseUtil.botp(ctx, sourceType, destType, newcoll, botpId);
			System.out.println("销售退货接口" + destinfo.get(0).get("number"));
			var id = destinfo.get(0).get("id");
			if (id != null) {
				var strSQL2 = "update  T_IM_SaleIssueBill set cfisxiatui =1 where fid='" + saleid + "'";
				System.out.println("销售退货接口" + strSQL2);
				DbUtil.execute(ctx, strSQL2);
				var saleisinfo = SaleIssueBillFactory.getLocalInstance(ctx).getSaleIssueBillInfo(new ObjectUuidPK(id));
				System.out.println("销售退货接口" + saleisinfo.getTransactionType().get("id"));
				var strSQLsi = "update T_IM_SaleIssueBill set cfbxNumber='销售退货电文CCCW14' where fid='" + id + "'";
				System.out.println("销售出库接口" + strSQLsi);
				DbUtil.execute(ctx, strSQLsi);
				var sdfDate = new SimpleDateFormat("yyyyMMdd");

				var bizdate = sdfDate.parse(sdfstr);
				saleisinfo.setBizDate(bizdate);
				// saleisinfo.setQty(-bgQty);
				// saleisinfo.setBaseQty(-bgQty);
				// saleisinfo.setPrice(price);
				// 猜测是这里设置库区号，将003 修改为读取到的库区代码即可
				var wah = PublicBaseUtil.getWarehouseInfoByNumber(ctx, kqh);
				var strSQL = "update T_IM_SaleIssueEntry set FWarehouseID='" + wah.getId() + "',FQty=" + (-bgQty) + ",FBaseQty=" + (-bgQty) + ",FTaxPrice = " + price + " where FParentID='" + id + "'";
				// var strSQL = "update T_IM_SaleIssueEntry set FWarehouseID='" + wah.getId() + "' where FParentID='" + id + "'";
				System.out.println("销售退货接口" + strSQL);
				DbUtil.execute(ctx, strSQL);
				var strSQLsi = "update T_IM_SaleIssueBill set cfbxNumber='销售退货电文CCCW14' where fid='" + id + "'";
				System.out.println("销售退货接口" + strSQLsi);
				DbUtil.execute(ctx, strSQLsi);
				//saleisinfo.getEntries().getObject(0).put("warehouse", wah);
				//saleisinfo.getEntries().getObject(0).put("warehouse", wah);
				var sic2 = new SelectorItemCollection();
				sic2.add("id");
				sic2.add("bizDate");
				SaleIssueBillFactory.getLocalInstance(ctx).updatePartial(saleisinfo, sic2);
				saleisinfo = SaleIssueBillFactory.getLocalInstance(ctx).getSaleIssueBillInfo(new ObjectUuidPK(id));
				var transid = saleisinfo.getTransactionType().get("id");
				var view1 = new EntityViewInfo();
				var sic1 = view1.getSelector();
				sic1.add("id");
				sic1.add("riType.id");
				sic1.add("riType.bizDirection");
				var filter1 = new FilterInfo();
				var fic1 = filter1.getFilterItems();
				fic1.add(new FilterItemInfo("id", transid));
				view1.setFilter(filter1);
				var transinfo = TransactionTypeFactory.getLocalInstance(ctx).getTransactionTypeCollection(view1);
				saleisinfo.setTransactionType(transinfo.get(0));
				// 设置退货重量
				// saleisinfo.setQty(bgQty)
				// saleisinfo.setQTY(bgQty)
				// saleisinfo.setBASEQTY(bgQty)
				// saleisinfo.setRETURNSQTY(bgQty)
				// 设置价格
				// saleisinfo.setSalePrice(price)
				if (saleisinfo != null) {
					if (saleisinfo.getBaseStatus().equals(com.kingdee.eas.scm.common.BillBaseStatusEnum.TEMPORARILYSAVED)) {
						SaleIssueBillFactory.getLocalInstance(ctx).submit(saleisinfo);
					}
					saleisinfo = SaleIssueBillFactory.getLocalInstance(ctx).getOtherIssueBillInfo(new ObjectUuidPK(id));

					if (saleisinfo.getBaseStatus().equals(com.kingdee.eas.scm.common.BillBaseStatusEnum.SUBMITED)) {
						SaleIssueBillFactory.getLocalInstance(ctx).audit(new ObjectUuidPK(id));
					}

				}
			}
		} else {
			isSucess = false;
			errMsg = "没有对应的销售出库单！";
		}
	} else {
		System.out.println("前置条件不满足，无法下推");
	}

	if (isSucess) {
		var strMsg = "0110" + dwh + "CWCC";
		methodCtx.getParam(2).setValue("true");
		var strData = String.format("%-81s", "A") + "\n";
		methodCtx.getParam(3).setValue(strMsg + strData);
	} else {
		methodCtx.getParam(2).setValue("false");
		methodCtx.getParam(4).setValue(dwt + errMsg);
		var strMsg = "0110" + dwh + "CWCCB";
		var endmsg = "\n";
		methodCtx.getParam(3).setValue(strMsg + errMsg + endmsg);
	}

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

}