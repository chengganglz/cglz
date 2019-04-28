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
	System.out.println("/******************************  CCCW14 消息记录开始   **********************************/");
	var socketMsg = methodCtx.getParamValue(0);  //接收报文 
	var billID = methodCtx.getParamValue(1); // 业务单元ID  
	var result = methodCtx.getParamValue(2); //成功失败  true:false  必须有返回值  methodCtx.getParam(2).setValue("true") 
	var sendMsg = methodCtx.getParamValue(3); // 发送报文   如果需要发送电文,将电文拼好放到这里返回  methodCtx.getParam(3).setValue("")
  	var errMsg = methodCtx.getParamValue(4); //错误消息 methodCtx.getParam(4).setValue("")  
	var ctx = pluginCtx.getContext(); //服务端上下文 
	var isSucess=true;
	errMsg="";
	// 电文号
	var dwh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,5,20);//电文号  从MES获取
	System.out.println("电文号-dwh:"+dwh);
	// dwh="CCCW2020190224040527CCCWD";
	// 电文头
	var dwt=PublicBaseUtil.substringByte(socketMsg,"GBK" ,1,29);//电文头  从MES获取
	System.out.println("电文头-dwt:"+dwt);
	// dwt="0524CCCW2020190224040527CCCWD11203778";
	// var lot="111";//批次号  从MES获取
	// 卷号
	var lot="";//批次号  从MES获取
	var rjh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,30,20);//批次号  从MES获取
	// rjh="201904186";
	System.out.println("卷号-rjh:"+rjh);
	rjh=rjh.trim();
	// rjh="201904185";
	if("".equals(rjh))
	{
		isSucess=false;
		System.out.println("Error-卷号为空,无法进行,中断");
		errMsg=errMsg + "卷号不能为空\r\n";
	}
	lot = rjh;
	// 合同号
	// 合同号
	var hth=PublicBaseUtil.substringByte(socketMsg,"GBK" ,70,10);//  从MES获取
	System.out.println("合同号-hth:"+hth);
	
	// 合约号
	var hyh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,80,20);//  从MES获取
	System.out.println("合约号-hyh:"+hyh);
	// 牌号开始******************************************
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
	// 牌号结束*****************************************
	
	
	// 厚度开始
	var bgHd=new BigDecimal("0");
	var hd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,130,6);//厚度  从MES获取
	if(hd!=null){
		hd=hd.trim();
	}else
	{
		hd="0";
	}
	try
	{
		bgHd=new BigDecimal(hd).divide(new BigDecimal("1000"));
		// bgHd=new BigDecimal("0.80");
	}catch(e){
		System.out.println("ex-厚度转换失败");
	}
	if (bgHd.compareTo(BigDecimal.ZERO)==0){
		isSucess=false;
		System.out.println("error-厚度不能为零,处理终止");
		errMsg=errMsg + "厚度不能为零\r\n";
	}
	// 厚度结束
	
	
	// 宽度开始
	var bgKd=new BigDecimal("0"); 
	var kd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,136,5);//宽度  从MES获取
	System.out.println("宽度-kd:"+kd);
	if(kd!=null){
		kd=kd.trim();
	}else
	{
		kd="0";
	}
	try
	{
		bgKd=new BigDecimal(kd).divide(new BigDecimal("10"));;
		// bgKd=new BigDecimal("1200");
	}catch(e){
		System.out.println("ex-宽度转换失败");
	}
	if (bgKd.compareTo(BigDecimal.ZERO)==0){
		isSucess=false;
		System.out.println("error-宽度不能为零,处理终止");
		errMsg=errMsg + "宽度不能为零\r\n";
	}
	// 宽度结束
	
	
	
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
	// 重量处理开始
	var zl=PublicBaseUtil.substringByte(socketMsg,"GBK" ,141,10);//重量  从MES获取
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
		System.out.println("ex-重量转换失败");
	}
	if (bgQty.compareTo(BigDecimal.ZERO)==0){
		isSucess=false;
		System.out.println("error-重量不能为零,处理终止");
		errMsg=errMsg + "重量不能为零\r\n";
	}
	// 重量处理结束
	var strUnit="吨"; //单位  默认千克
	// 物料类别处理开始
	// var materialGroupNum="06";//物料组别  从MES获取
	// 问题? 物料组别不存在
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
		// 物料类别对应关系存储
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
	// 物料类别处理结束
	// 获取物料开始
	// var material= SocketFacadeFactory.getLocalInstance(ctx).getMaterialInfo("01", "5", materialGroupNum, ph, phmc, bgKd, bgHd, strUnit, "", true);
	// var mnumber="";
	// if (material==null){
	// 	isSucess=false;
	// 	errMsg=errMsg + "物料不能为空\r\n";
	// }else{
	// 	mnumber=material.getNumber();
	// }
	// 获取物料结束
	// 获取业务时间开始
	var strBizDate = PublicBaseUtil.substringByte(socketMsg,"GBK" ,,11,8) ;;//业务日期  从MES获取
	System.out.println("业务日期-strBizDate:"+strBizDate);
	// 业务时间结束

	// 获取退货价格开始
	var thjg = new BigDecimal('0');
	var temp_thjg=PublicBaseUtil.substringByte(socketMsg,"GBK" ,151,12);//  从MES获取
	if(temp_thjg!=null){
		temp_thjg=temp_thjg.trim();
	}else
	{
		temp_thjg="0";
	}
	try
	{
		thjg=new BigDecimal(temp_thjg).divide(new BigDecimal('10'));
	}catch(e){
		System.out.println("ex-退货价格转换失败");
	}
	// if (thjg.compareTo(BigDecimal.ZERO)==0){
	// 	isSucess=false;
	// 	System.out.println("error-退货价格不能为零,处理终止");
	// 	errMsg=errMsg + "退货价格不能为零\r\n";
	// }

	// 获取退货价格结束
	/****************************************************************************/
	/**
	 * 时间:2019-4-24 19:22
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
				// 字段名称 唯一标识,编码,名称,创建id,创建时间,控制单元,出口卷号,合同号,合约号,机组号,重量,生产时间,物料id,是否入库,电文号,电文类型,退货价格
				// 值 id,rjh,hth,'256c221a-0106-1000-e000-10d7c0a813f413B7DE7F',fcreatetimestr,'00000000-0000-0000-0000-000000000000CCE7AED4',rjh,hth,hyh,cbzxNo,bgQty,scsj,wlid,0,dwh,4,thjg
				sql = "insert into CT_CUS_JZSCCCJL(fid,FNUMBER,FNAME_L2,fcreatorid,fcreatetime,fcontrolunitid,CFCKJH,CFHTH,CFHYH,CFJZH,CFZL,CFSCSJ,CFWLID,CFSFRK,CFDWH,CFDWLX) values('"+id+"','"+fnumber+"','"+fnumber+"','256c221a-0106-1000-e000-10d7c0a813f413B7DE7F',to_date('"+fcreatetimestr+"'),'00000000-0000-0000-0000-000000000000CCE7AED4','"+rjh+"','"+hth+"','"+hyh+"','"+cbzxNo+"','"+bgQty+"',to_date('"+scsj+"'),'"+wlid+"','0','"+dwh+"',4,'"+thjg+"')"
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
	/*
	var destType=BOSObjectType.create("CC3E933B");
   	var sourceType=BOSObjectType.create("CC3E933B");
	var botpId=BOSUuid.read("/4CzhgEPEADgAARkwKgSOgRRIsQ=");
	System.out.println("销售退货接口开始");
	var strSQL1 = "select sale.fid saleid from T_IM_SaleIssueBill sale left join T_SCM_TransactionType trans on sale.FTRANSACTIONTYPEID=trans.fid  left join T_IM_SaleIssueEntry entry on entry.fparentid=sale.fid left join T_BD_Material material on material.fid=entry.fmaterialid where material.fnumber='"+mnumber+"' and entry.flot='"+lot+"' and (sale.cfisxiatui=0 or sale.cfisxiatui is null) and trans.fnumber='010'";
	System.out.println("销售退货接口"+strSQL1);
	var rs = DbUtil.executeQuery(ctx, strSQL1);
	var saleid="";
	while (rs.next()) {
		saleid=rs.getString("saleid");
	}
	*/
	
    // System.out.println("电文号"+dwh);
    /*
	if(saleid!=null&&!"".equals(saleid)){
		var view = new EntityViewInfo();
		var sic = view.getSelector();
		sic.add("*");
		
		sic.add("entries.*");
		
		var filter = new FilterInfo();
		var fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("id", saleid));
		view.setFilter(filter);
		var sourceColl=SaleIssueBillFactory.getLocalInstance(ctx).getSaleIssueBillCollection(view);
		//var sourceColl = CoreBillBaseCustomFactory.getLocalInstance(ctx).getCoreBillBaseCollection(view);
		var newcoll=sourceColl.cast(new CoreBillBaseCollection().getClass());
		System.out.println("销售退货接口:"+newcoll.get(0).get("number"));
		var destinfo=PublicBaseUtil.botp(ctx,sourceType,destType,newcoll,botpId);
		System.out.println("销售退货接口"+destinfo.get(0).get("number"));
		var id=destinfo.get(0).get("id");
		if(id!=null){
			var strSQL2 = "update  T_IM_SaleIssueBill set cfisxiatui =1 where fid='"+saleid+"'";
			System.out.println("销售退货接口"+strSQL2);
			DbUtil.execute(ctx, strSQL2);
			var saleisinfo=SaleIssueBillFactory.getLocalInstance(ctx).getSaleIssueBillInfo(new ObjectUuidPK(id));
			System.out.println("销售退货接口"+saleisinfo.getTransactionType().get("id"));
			var strSQLsi = "update T_IM_SaleIssueBill set cfbxNumber='销售退货电文CCCW14' where fid='"+id+"'";
			System.out.println("销售出库接口"+strSQLsi);
			DbUtil.execute(ctx, strSQLsi);
			var sdfDate = new SimpleDateFormat("yyyyMMdd");
			
			var bizdate = sdfDate.parse(sdfstr);
			saleisinfo.setBizDate(bizdate);
			var wah=PublicBaseUtil.getWarehouseInfoByNumber(ctx,"003");
			var strSQL = "update T_IM_SaleIssueEntry set FWarehouseID='"+wah.getId()+"' where FParentID='"+id+"'";
			 System.out.println("销售退货接口"+strSQL);
			 DbUtil.execute(ctx, strSQL);
			var strSQLsi = "update T_IM_SaleIssueBill set cfbxNumber='销售退货电文CCCW14' where fid='"+id+"'";
			System.out.println("销售退货接口"+strSQLsi);
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
				// if(saleisinfo.getBaseStatus().equals(com.kingdee.eas.scm.common.BillBaseStatusEnum.SUBMITED)){
				// 	SaleIssueBillFactory.getLocalInstance(ctx).audit(new ObjectUuidPK(id));
				// }
			}
		}
	}else{
		isSucess=false;
		errMsg="没有对应的销售出库单！";
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
		var endmsg="\n";
		methodCtx.getParam(3).setValue(strMsg+errMsg+endmsg);
	}
	System.out.println("/******************************  CCCW14 消息记录结束   **********************************/");
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