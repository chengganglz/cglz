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
with(imp){ 
	var socketMsg = methodCtx.getParamValue(0);  //接收报文 
	//socketMsg="0231CCCW1620190501224543CCCWDL11940006300        C0290041912C0290430001 TESTSHIHUI2019042501X190425002              DD11                                              00350015040000532399000000000010000022000C02     201904302201400 ";
	var billID = methodCtx.getParamValue(1); // 业务单元ID  
	var result = methodCtx.getParamValue(2); //成功失败  true:false  必须有返回值  methodCtx.getParam(2).setValue("true") 
	var sendMsg = methodCtx.getParamValue(3); // 发送报文   如果需要发送电文,将电文拼好放到这里返回  methodCtx.getParam(3).setValue("")
  	var errMsg = methodCtx.getParamValue(4); //错误消息 methodCtx.getParam(4).setValue("")  
	var ctx = pluginCtx.getContext(); //服务端上下文 
	var isSucess=true;
	errMsg="";
	var destType=BOSObjectType.create("CC3E933B");
   	var sourceType=BOSObjectType.create("C48A423A");
	var botpId=BOSUuid.read("6a7669e6-0108-1000-e000-2136c0a812fd045122C4");
	System.out.println("/**************************销售出库接口开始************************/");
	
	var hetonghao =PublicBaseUtil.substringByte(socketMsg,"GBK" ,93,20);//合同号  从MES获取
	if(hetonghao==null){
		isSucess=false;
		errMsg=errMsg + "合同号不能为空\r\n";
	}else{
		hetonghao=hetonghao.replace(" ", "");
	}
	if("".equals(hetonghao)){
		isSucess=false;
		errMsg=errMsg + "合同号不能为空\r\n";
	}
	System.out.println("合同号"+hetonghao);
	var strSQL1 = "select ord.fid orderid from T_SD_SaleOrder ord left join T_SD_SaleOrderEntry entry on entry.fparentid=ord.fid left join T_BD_Material material on material.fid=entry.fmaterialid where ord.fnumber='"+hetonghao+"' and  ord.FBASESTATUS <> 7";
	System.out.println("销售出库接口"+strSQL1);
	var dwh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,5,20);//电文号  从MES获取
    System.out.println("销售出库电文号"+dwh);
    var dwt=PublicBaseUtil.substringByte(socketMsg,"GBK" ,1,29);//电文头  从MES获取
	//dwt="0524CCCW2020190224040527CCCWD11203778";
	var rs = DbUtil.executeQuery(ctx, strSQL1);
	var orderid="";
	var lot=PublicBaseUtil.substringByte(socketMsg,"GBK" ,30,20);//批次号  从MES获取
	if(lot==null){
		isSucess=false;
		errMsg=errMsg + "卷号不能为空\r\n";
	}else{
		lot=lot.replace(" ", "");
	}
	if("".equals(lot)){
		isSucess=false;
		errMsg=errMsg + "卷号不能为空\r\n";
	}
	System.out.println("卷号"+lot);
	while (rs.next()) {
		orderid=rs.getString("orderid");
	}
	var strMsg="0110"+dwh+"CWCC";
	methodCtx.getParam(2).setValue("true");
		var strData=String.format("%-81s", "A")+"\n" ;
		methodCtx.getParam(3).setValue(strMsg+strData);
	var bgQty=BigDecimal.ONE; //数量  
	var zl=PublicBaseUtil.substringByte(socketMsg,"GBK" ,198,10);//重量  从MES获取
	if(zl!=null){
		zl=zl.trim();
	}else
	{
		zl="0";
	}
	try
	{
		bgQty=new BigDecimal(zl).divide(new BigDecimal("1000"));
		//bgQty=new BigDecimal(zl);
	}catch(e){
		
	}
	if (bgQty.compareTo(BigDecimal.ZERO)==0){
		isSucess=false;
		errMsg=errMsg + "重量不能为零\r\n";
	}
	System.out.println("重量"+zl);
	var sdfstr=PublicBaseUtil.substringByte(socketMsg,"GBK" ,216,8);//业务日期  从MES获取
	if(sdfstr==null){
		isSucess=false;
		errMsg=errMsg + "业务日期不能为空\r\n";
	}else{
		sdfstr=sdfstr.replace(" ", "");
	}
	if("".equals(sdfstr)){
		isSucess=false;
		errMsg=errMsg + "业务日期不能为空\r\n";
	}else if(sdfstr!=null){
		var isdate=PublicBaseUtil.valiDateTimeWithLongFormat(sdfstr);
		if(!isdate){
			isSucess=false;
			errMsg=errMsg + "业务日期格式不合法\r\n";
		}
	}
	System.out.println("业务日期"+sdfstr);
	var strSQLInv = "select * from T_IM_Inventory where flot='"+lot+"' and FCURSTOREQTY <> 0";
	System.out.println("销售出库接口"+strSQLInv);
	var rsInv = DbUtil.executeQuery(ctx, strSQLInv);
	if (rsInv.next()) {
		var curStoreQty=rsInv.getBigDecimal("FCURSTOREQTY");
		System.out.println("库存重量"+curStoreQty.toString());
		System.out.println("出库重量"+bgQty.toString());
		if(bgQty.compareTo(curStoreQty)!=0){
			isSucess=false;
			errMsg=errMsg + "与库存重量不一致\r\n";
		}
		
	}
	var strStockNo=PublicBaseUtil.substringByte(socketMsg,"GBK" ,208,8) ;//仓库编码  从MES获取
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
	
	System.out.println("库区号"+strStockNo);
	if(isSucess){
		if(orderid!=null&&!"".equals(orderid)){
			var view = new EntityViewInfo();
			var sic = view.getSelector();
			sic.add("*");
			sic.add("creator.*");
			sic.add("lastUpdateUser.*");
			sic.add("auditor.*");
			sic.add("modifier.*");
			sic.add("companyOrgUnit.*");
			sic.add("billType.*");
			sic.add("bizType.*");
			sic.add("sourceBillType.*");
			sic.add("paymentType.*");
			sic.add("adminOrgUnit.*");
			sic.add("settlementType.*");
			sic.add("saleOrgUnit.*");
			sic.add("orderCustomer.*");
			sic.add("entries.*");
			sic.add("entries.material.*");
			sic.add("entries.companyOrgUnit.*");
			sic.add("entries.assistProperty.*");
			sic.add("entries.assistProperty.basictype.id");
			sic.add("entries.assistProperty.compondingtype.id");
			sic.add("entries.baseUnit.*");
			sic.add("entries.unit.*");
			sic.add("entries.assistUnit.*");
			sic.add("deliveryType.*");
			sic.add("saleGroup.*");
			sic.add("salePerson.*");
			sic.add("salePerson.person.name");
			sic.add("currency.*");
			sic.add("entries.storageOrgUnit.*");
			sic.add("entries.warehouse.*");
			var filter = new FilterInfo();
			var fic = filter.getFilterItems();
			fic.add(new FilterItemInfo("id", orderid));
			view.setFilter(filter);
			var sourceColl=com.kingdee.eas.scm.sd.sale.SaleOrderFactory.getLocalInstance(ctx).getSaleOrderCollection(view);
			//var sourceColl = CoreBillBaseCustomFactory.getLocalInstance(ctx).getCoreBillBaseCollection(view);
			var e = null;
			var id="";
			try {
				var newcoll=sourceColl.cast(new CoreBillBaseCollection().getClass());
				System.out.println("销售出库接口:"+newcoll.get(0).get("number"));
				var destinfo=PublicBaseUtil.botp(ctx,sourceType,destType,newcoll,botpId);
				System.out.println("销售出库接口"+destinfo.get(0).get("number"));
				id=destinfo.get(0).get("id");
				if(id!=null){
					//var strSQL2 = "update  T_SD_SaleOrder set cfisxiatui =1 where fid='"+orderid+"'";
					//System.out.println("销售出库接口"+strSQL2);
					//DbUtil.execute(ctx, strSQL2);
					var saleisinfo=SaleIssueBillFactory.getLocalInstance(ctx).getSaleIssueBillInfo(new ObjectUuidPK(id));
					System.out.println("销售出库接口"+saleisinfo.getTransactionType().get("id"));
					
					var sdfDate = new SimpleDateFormat("yyyyMMdd");
					
					var bizdate = sdfDate.parse(sdfstr);
					saleisinfo.setBizDate(bizdate);
					var wah=PublicBaseUtil.getWarehouseInfoByNumber(ctx,strStockNo);
					
					var bgPrice=new BigDecimal("0"); //单价
					var bgTaxRate=new BigDecimal("0"); //税率
					var strSQLEntry = "select * from T_IM_SaleIssueEntry where FParentID='"+id+"'";
					System.out.println("销售出库接口"+strSQLEntry);
					var rsEntry = DbUtil.executeQuery(ctx, strSQLEntry);
					while (rsEntry.next()) {
						bgPrice=rsEntry.getBigDecimal("fPrice");
						bgTaxRate=rsEntry.getBigDecimal("fTaxRate");
					}
					var bgAmount =bgPrice.multiply(bgQty);// 金额
					var bgTaxPrice=bgPrice.multiply(bgTaxRate.divide(new BigDecimal("100"),8,BigDecimal.ROUND_HALF_UP).add(BigDecimal.ONE));
					var bgTaxAmount=bgTaxPrice.multiply(bgQty);
					var bgTax=bgTaxAmount.subtract(bgAmount); //税额
					var strSQL = "update T_IM_SaleIssueEntry set FWarehouseID='"+wah.getId()+"',flot='"+lot+"',fqty="+bgQty.toString()+",FUNDELIVERQTY="+bgQty.toString()+",FUNDELIVERBASEQTY="+bgQty.toString()+",FUNINQTY="+bgQty.toString()+",FASSOCIATEBASEQTY="+bgQty.toString()+",FUNINBASEQTY="+bgQty.toString()+",FUNWRITEOFFQTY="+bgQty.toString()+",FUNWRITEOFFBASEQTY="+bgQty.toString()+",FUNRETURNEDBASEQTY="+bgQty.toString()+",fbaseqty="+bgQty.toString()+",famount="+bgTaxAmount.toString()+",ftax="+bgTax.toString()+",FLOCALTAX="+bgTax.toString()+",FLOCALAMOUNT="+bgTaxAmount.toString()+",FNONTAXAMOUNT="+bgAmount.toString()+",FLOCALNONTAXAMOUNT="+bgAmount.toString()+" where FParentID='"+id+"'";
					System.out.println("销售出库接口"+strSQL);
					DbUtil.execute(ctx, strSQL);
					var strSQLsi = "update T_IM_SaleIssueBill set cfbxNumber='发货完成电文CCCW16' where fid='"+id+"'";
					System.out.println("销售出库接口"+strSQLsi);
					DbUtil.execute(ctx, strSQLsi);
					//saleisinfo.getEntries().getObject(0).put("warehouse", wah);
					//saleisinfo.getEntries().getObject(0).put("warehouse", wah);
					var sic2 = new SelectorItemCollection();
					sic2.add("id");
					sic2.add("bizDate");
					SaleIssueBillFactory.getLocalInstance(ctx).updatePartial(saleisinfo, sic2);
					saleisinfo=SaleIssueBillFactory.getLocalInstance(ctx).getSaleIssueBillInfo(new ObjectUuidPK(id));
					var transid=saleisinfo.getTransactionType().get("id");
					var view1 = new EntityViewInfo();
					var sic1 = view1.getSelector();
					sic1.add("id");
					sic1.add("riType.id");
					sic1.add("riType.bizDirection");
					var filter1 = new FilterInfo();
					var fic1 = filter1.getFilterItems();
					fic1.add(new FilterItemInfo("id", transid));
					view1.setFilter(filter1);
					var transinfo=TransactionTypeFactory.getLocalInstance(ctx).getTransactionTypeCollection(view1);
					saleisinfo.setTransactionType(transinfo.get(0));
					
					if(saleisinfo!=null){
						if(saleisinfo.getBaseStatus().equals(com.kingdee.eas.scm.common.BillBaseStatusEnum.TEMPORARILYSAVED)){
							SaleIssueBillFactory.getLocalInstance(ctx).submit(saleisinfo);
						}
						saleisinfo=SaleIssueBillFactory.getLocalInstance(ctx).getSaleIssueBillInfo(new ObjectUuidPK(id));
						if(saleisinfo.getBaseStatus().equals(com.kingdee.eas.scm.common.BillBaseStatusEnum.SUBMITED)){
							SaleIssueBillFactory.getLocalInstance(ctx).audit(new ObjectUuidPK(id));
						}
					}
				}
			} catch (e) {
				if (id != null) {
					var saleisinfo=SaleIssueBillFactory.getLocalInstance(ctx).getSaleIssueBillInfo(new ObjectUuidPK(id));
					SaleIssueBillFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(id));
				}
				isSucess = false;
				errMsg = errMsg + "单据生成失败\r\n" +e.toString().replace('\\s','').replace('\n','');
				System.out.println(errMsg);
			}
		}else{
			isSucess=false;
			errMsg="没有对应的销售订单！";
		}
	}
	var strMsg="0110"+dwh+"CWCC";
	// methodCtx.getParam(2).setValue("true");
	// 	var strData=String.format("%-81s", "A")+"\n" ;
	// 	methodCtx.getParam(3).setValue(strMsg+strData);
	if(isSucess){
		methodCtx.getParam(2).setValue("true");
		var strData=String.format("%-81s", "A")+"\n" ;
		methodCtx.getParam(3).setValue(strMsg+strData);
	}else
	{
		methodCtx.getParam(2).setValue("false");
		methodCtx.getParam(4).setValue(dwt+errMsg);
		var strMsg="0110"+dwh+"CWCCB";
		var newerrmsg=PublicBaseUtil.substringByte(errMsg, "GBK", 1, 80);
		if(newerrmsg!=null){
			errMsg=newerrmsg;
		}
		var endmsg="\n";
		methodCtx.getParam(3).setValue(strMsg+errMsg+endmsg);
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