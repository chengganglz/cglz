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
imp.importPackage(Packages.com.kingdee.bos.util);  
imp.importPackage(Packages.com.kingdee.bos.metadata.entity); 
imp.importPackage(Packages.com.kingdee.eas.framework); 
imp.importPackage(Packages.com.kingdee.bos.dao.ormapping); 
with(imp){ 
	var socketMsg = methodCtx.getParamValue(0);  //接收报文 
	//socketMsg="0217CCCW0920190424173240CCCWD192021030600        3DD11                                              00296015200007770000023000DD11                                              0029601520000777000002400020190424171759 ";
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
	//dwt="0524CCCW2020190224040527CCCWD11203778";
	System.out.println("/************************************重量修正电文号"+dwh+"*****************/");
	var isSucess=true;
	methodCtx.getParam(2).setValue("false") ;
	var errMsg="";
	var strCreatorNo="user"; //创建人编码
	var strCUNo="01";// 管理单元编码
	var strBizDate=PublicBaseUtil.substringByte(socketMsg,"GBK" ,203,8); //业务日期  从MES获取
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
	var billStatus=BillBaseStatusEnum.ADD; //单据状态
	var PROC_DIV=PublicBaseUtil.substringByte(socketMsg,"GBK" ,50,1);//操作类型  从MES获取
	if(PROC_DIV==null){
		isSucess=false;
		errMsg=errMsg + "操作类型不能为空\r\n";
	}else{
		PROC_DIV=PROC_DIV.replace(" ", "");
	}
	if("".equals(PROC_DIV)){
		isSucess=false;
		errMsg=errMsg + "操作类型不能为空\r\n";
	}
	System.out.println("操作类型"+PROC_DIV);
	if("3".equals(PROC_DIV)){
		var strBizTypeNo="390";//业务类型编码
		var strBillTypeNo="105";//单据类型编码
		var strTranstionType="026";//事务类型
		//var strCurrencyNo ="BB01"; //币别编码
		//var bgExchangeRate=BigDecimal.ONE;// 汇率
		var newqty=BigDecimal.ZERO;
		var bgTotalQty =BigDecimal.ZERO;// 数量
		var strLot=PublicBaseUtil.substringByte(socketMsg,"GBK" ,30,20);//批次号  从MES获取
		if(strLot==null){
			isSucess=false;
			errMsg=errMsg + "卷号不能为空\r\n";
		}else{
			strLot=strLot.replace(" ", "");
		}
		if("".equals(strLot)){
			isSucess=false;
			errMsg=errMsg + "卷号不能为空\r\n";
		}
		System.out.println("卷号"+strLot);
		var newzl=PublicBaseUtil.substringByte(socketMsg,"GBK" ,193,10);//新重量  从MES获取
		if(newzl!=null){
			newzl=newzl.trim();
		}else
		{
			newzl="0";
		}
		try
		{
			newqty=new BigDecimal(newzl).divide(new BigDecimal("1000"));
		}catch(e){
			
		}
		System.out.println("重量"+newzl);
		System.out.println("重量"+newqty.toString());
		if (newqty.compareTo(BigDecimal.ZERO)==0){
			isSucess=false;
			errMsg=errMsg + "修正后重量不能为零\r\n";
		}
		// var strSQLPiao = "select * from CT_CUS_JZSCCCJL where cfckjh='"+strLot+"' and (cfsfrk=0 or cfsfrk is null)";
		// System.out.println("重量修正接口"+strSQLPiao);
		// var rsPiao = DbUtil.executeQuery(ctx, strSQLPiao);
		// var isPiao=false;
		// while (rsPiao.next()) {
		// 	var piaoFid=rsPiao.getString("fid");
		// 	var strSQLUpdate = "update  CT_CUS_JZSCCCJL set cfzl ="+newqty.toString()+" where fid='"+piaoFid+"'";
		// 	System.out.println("销售退货接口"+strSQLUpdate);
		// 	DbUtil.execute(ctx, strSQLUpdate);
		// 	isPiao=true;
		// }
		// if(!isPiao){
		var xiuzhengleixing="";//判断时间顺序：1：生产入库重量修正  2：委外入库重量修正  3：采购入库单重量修正  4：销售退货重量修正  5：盘盈单重量修正
		var strSqlXzType = "select recbill.fid recfid,recbill.FCREATETIME recCreateTime from T_IM_ManufactureRecBill recbill left join T_ORG_BaseUnit  orgunit on recbill.fcostcenterorgunitid=orgunit.fid  left join T_SCM_TransactionType trans on recbill.FTransactionTypeID=trans.fid  left join T_IM_ManufactureRecBillEntry entry on recbill.fid = entry.fparentid left join T_BD_Material material on entry.fmaterialid=material.fid left join T_BD_MeasureUnit unit on unit.fid=material.FBASEUNIT  left join T_DB_WAREHOUSE ware on ware.fid=entry.FWAREHOUSEID where entry.flot='"+strLot+"' and (recbill.cfistuiku=0 or recbill.cfistuiku is null) and trans.fnumber='024'  and recbill.FBASESTATUS =4";
		System.out.println("判断修正类型"+strSqlXzType);
		var rsXzType = DbUtil.executeQuery(ctx, strSqlXzType);
		var xzDate=null;
		while (rsXzType.next()) {
			xzDate=rsXzType.getDate("recCreateTime");
			xiuzhengleixing="1";
		}
		strSqlXzType = "select purbill.fid purfid,purbill.FCREATETIME purCreateTime from T_IM_PurInWarehsBill purbill left join T_SCM_TransactionType trans on purbill.FTransactionTypeID=trans.fid left join T_SCM_BizType bizType on purbill.FBizTypeID=bizType.fid  left join T_IM_PurInWarehsEntry entry on purbill.fid = entry.fparentid left join T_BD_Supplier supplier on purbill.FSupplierID=supplier.fid left join T_BD_Material material on entry.fmaterialid=material.fid left join T_BD_MeasureUnit unit on unit.fid=material.FBASEUNIT  left join T_DB_WAREHOUSE ware on ware.fid=entry.FWAREHOUSEID where  entry.flot='"+strLot+"' and (purbill.cfistuiku=0 or purbill.cfistuiku is null) and trans.fnumber='001' and bizType.fnumber='130' and purbill.FBASESTATUS =4";
		System.out.println("判断修正类型"+strSqlXzType);
		rsXzType = DbUtil.executeQuery(ctx, strSqlXzType);
		while (rsXzType.next()) {
			var newDate=rsXzType.getDate("purCreateTime");
			if(xzDate==null){
				xzDate=newDate;
			}else{
				var result=newDate.compareTo(xzDate);
				if(result>0){
					xzDate=newDate;
					xiuzhengleixing="2";
				}
			}
			
		}
		strSqlXzType = "select purbill.fid purfid, purbill.FCREATETIME purCreateTime from T_IM_PurInWarehsBill purbill left join T_SCM_TransactionType trans on purbill.FTransactionTypeID=trans.fid left join T_SCM_BizType bizType on purbill.FBizTypeID=bizType.fid  left join T_IM_PurInWarehsEntry entry on purbill.fid = entry.fparentid left join T_BD_Supplier supplier on purbill.FSupplierID=supplier.fid left join T_BD_Material material on entry.fmaterialid=material.fid left join T_BD_MeasureUnit unit on unit.fid=material.FBASEUNIT  left join T_DB_WAREHOUSE ware on ware.fid=entry.FWAREHOUSEID where entry.flot='"+strLot+"' and (purbill.cfistuiku=0 or purbill.cfistuiku is null) and trans.fnumber='001' and bizType.fnumber='110' and purbill.FBASESTATUS =4";
		System.out.println("判断修正类型"+strSqlXzType);
		rsXzType = DbUtil.executeQuery(ctx, strSqlXzType);
		while (rsXzType.next()) {
			var newDate=rsXzType.getDate("purCreateTime");
			if(xzDate==null){
				xzDate=newDate;
			}else{
				var result=newDate.compareTo(xzDate);
				if(result>0){
					xzDate=newDate;
					xiuzhengleixing="3";
				}
			}
		}
		strSqlXzType = "select purbill.fid purfid, purbill.FCREATETIME purCreateTime from T_IM_PurInWarehsBill purbill left join T_SCM_TransactionType trans on purbill.FTransactionTypeID=trans.fid left join T_SCM_BizType bizType on purbill.FBizTypeID=bizType.fid  left join T_IM_PurInWarehsEntry entry on purbill.fid = entry.fparentid left join T_BD_Supplier supplier on purbill.FSupplierID=supplier.fid left join T_BD_Material material on entry.fmaterialid=material.fid left join T_BD_MeasureUnit unit on unit.fid=material.FBASEUNIT  left join T_DB_WAREHOUSE ware on ware.fid=entry.FWAREHOUSEID where entry.flot='"+strLot+"' and (purbill.cfistuiku=0 or purbill.cfistuiku is null) and trans.fnumber='001' and bizType.fnumber='110' and purbill.FBASESTATUS =4";
		System.out.println("判断修正类型"+strSqlXzType);
		rsXzType = DbUtil.executeQuery(ctx, strSqlXzType);
		while (rsXzType.next()) {
			var newDate=rsXzType.getDate("purCreateTime");
			if(xzDate==null){
				xzDate=newDate;
			}else{
				var result=newDate.compareTo(xzDate);
				if(result>0){
					xzDate=newDate;
					xiuzhengleixing="3";
				}
			}
		}

		var strSQL1 = "select recbill.fid recfid,entry.fqty entryqty,material.fnumber materialnum,unit.fname_l2 unitname,ware.fnumber warenum,orgunit.fnumber orgnum from T_IM_ManufactureRecBill recbill left join T_ORG_BaseUnit  orgunit on recbill.fcostcenterorgunitid=orgunit.fid  left join T_SCM_TransactionType trans on recbill.FTransactionTypeID=trans.fid  left join T_IM_ManufactureRecBillEntry entry on recbill.fid = entry.fparentid left join T_BD_Material material on entry.fmaterialid=material.fid left join T_BD_MeasureUnit unit on unit.fid=material.FBASEUNIT  left join T_DB_WAREHOUSE ware on ware.fid=entry.FWAREHOUSEID where entry.flot='"+strLot+"' and (recbill.cfistuiku=0 or recbill.cfistuiku is null) and trans.fnumber='024'  and recbill.FBASESTATUS =4";
		System.out.println("销售退货接口"+strSQL1);
		var rs = DbUtil.executeQuery(ctx, strSQL1);
		var recfid="";
		while (rs.next()) {
			recfid=rs.getString("recfid");
			var bgQty=rs.getBigDecimal("entryqty");
			var strMaterialNo=rs.getString("materialnum");
			var strUnit=rs.getString("unitname");
			var strStockNo=rs.getString("warenum");
			var orgnum=rs.getString("orgnum");
			
			var fushu=new BigDecimal("-1");
			var fubgQty=bgQty.multiply(fushu);
			
			if(newqty.compareTo(BigDecimal.ZERO)==0){
				isSucess=false;
				errMsg=errMsg + "重量不能为0\r\n";
				continue;
			}
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
			var costcenter=PublicBaseUtil.getCostCenterOrgUnitInfoByNumber(ctx,orgnum);//成本中心编码
			if(costcenter==null){
				isSucess=false;
				errMsg=errMsg + "成本中心不能为空\r\n";
			}
			var material=PublicBaseUtil.getMaterialInfoByNumber(ctx, cu.getId().toString(), strMaterialNo);
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
				//新增生产入库(红字)
				var info=new ManufactureRecBillInfo();
				info.put("bxNumber","重量修正电文CCCW09");
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
				entry.setQty(fubgQty);
				entry.setBaseQty(fubgQty);
				bgTotalQty=bgTotalQty.add(fubgQty);
				info.setTotalQty(bgTotalQty);
				entry.setLot(strLot);
				
				entry.setInvUpdateType(invUpType);
				entry.setAssociateQty(fubgQty);
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
			if(isSucess){
				var newinfo=new ManufactureRecBillInfo();
				newinfo.put("bxNumber","重量修正电文CCCW09");
				if(creator!=null){
					newinfo.setCreator(creator);
				}else{
					newinfo.setCreator(ContextUtil.getCurrentUserInfo(ctx));
				}
				if(cu!=null){
					newinfo.setCU(cu); //控制单元
				}
				try {
					newinfo.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
				} catch ( e) {
					
				}
				//业务日期
				try
				{
					var bizDate;
					var sdf = new SimpleDateFormat("yyyyMMdd");
					bizDate=sdf.parse(strBizDate);
					newinfo.setBizDate(bizDate);
					var c = Calendar.getInstance();
					c.setTime(bizDate);
					var year=c.get(Calendar.YEAR);   //年度
					var period=c.get(Calendar.MONTH)+1;
					var month =c.get(Calendar.YEAR)*100+c.get(Calendar.MONTH)+1 ; //期间
					var iday=(c.get(Calendar.YEAR)*100+c.get(Calendar.MONTH)+1)*100+c.get(Calendar.MONDAY);
					newinfo.setYear(year);
					newinfo.setPeriod(period);
					newinfo.setMonth(month);
					newinfo.setDay(iday);
				}catch(e){
					isSucess=false;
					errMsg=errMsg + "日期格式不正确\r\n";
					//throw new BOSException("日期格式不正确");
					
				}
				//单据状态
				newinfo.setBaseStatus(billStatus);
				// 业务类型
				if(bizType!=null){
					newinfo.setBizType(bizType);
				}
				// 单据类型
				if(billType!=null){
					newinfo.setBillType(billType);
				}
				strTranstionType="024";//事务类型
				trantypeInfo=PublicBaseUtil.getTransactionTypeInfoByNumber(ctx, strTranstionType);
				if(trantypeInfo!=null){
					newinfo.setTransactionType(trantypeInfo);
				}
				//库存组织
				if(storeInfo!=null){
					newinfo.setStorageOrgUnit(storeInfo);
					newinfo.setProcessOrgUnit(storeInfo);
				}
				//成本中心
				if(costcenter!=null){
					newinfo.setCostCenterOrgUnit(costcenter);
					
				}
				// 倒冲标识
				newinfo.setIsBackFlushSucceed(com.kingdee.eas.scm.im.inv.BackFlushSucceedEnum.NO_BACKFLUSH);
				//新增分录实体
				var newentry=new ManufactureRecBillEntryInfo();
				//分录行号
				newentry.setSeq(1);
				newentry.setSourceBillEntrySeq(0);
				// 获取物料
				if (material!=null){
					newentry.setMaterial(material);
					newentry.setBaseUnit(material.getBaseUnit());
				}
				//单位
				if (unit!=null){
					newentry.setUnit(unit);
				}
				newentry.setBaseStatus(EntryBaseStatusEnum.NULL);//
				newentry.setAssCoefficient(BigDecimal.ZERO);
				//库存组织
				if(storOrgUnit!=null){
					newentry.setStorageOrgUnit(storOrgUnit);
					
				}
				//财务组织
				newentry.setCompanyOrgUnit(company);
				//成本中心
				newentry.setCostCenterOrgUnit(costcenter);
				//仓库
				if(stock!=null){
					newentry.setWarehouse(stock);
				}
				newentry.setQty(newqty);
				newentry.setBaseQty(newqty);
				bgTotalQty=bgTotalQty.add(newqty);
				newinfo.setTotalQty(bgTotalQty);
				newentry.setLot(strLot);
				newentry.setInvUpdateType(invUpType);
				newentry.setAssociateQty(fubgQty);
				var newmanuEntrys = new ManufactureRecBillEntryCollection();
				newmanuEntrys.add(newentry);
				if (newmanuEntrys.size() > 0) {
					newinfo.getEntries().addCollection(newmanuEntrys);
				}
			}
			if(isSucess){
				var pk=ManufactureRecBillFactory.getLocalInstance(ctx).save(newinfo);  //保存
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
					var strSQL2 = "update  T_IM_ManufactureRecBill set cfistuiku =1 where fid='"+recfid+"'";
					System.out.println("销售退货接口"+strSQL2);
					DbUtil.execute(ctx, strSQL2);
				}
				
			}else
			{
				methodCtx.getParam(2).setValue("false") ;
				methodCtx.getParam(4).setValue(errMsg);
				//throw new BOSException(errMsg);
			}
		}
		var strSQLweiwai = "select purbill.fid purfid,supplier.fnumber suppnum,entry.fqty entryqty,material.fnumber materialnum,unit.fname_l2 unitname,ware.fnumber warenum from T_IM_PurInWarehsBill purbill left join T_SCM_TransactionType trans on purbill.FTransactionTypeID=trans.fid left join T_SCM_BizType bizType on purbill.FBizTypeID=bizType.fid  left join T_IM_PurInWarehsEntry entry on purbill.fid = entry.fparentid left join T_BD_Supplier supplier on purbill.FSupplierID=supplier.fid left join T_BD_Material material on entry.fmaterialid=material.fid left join T_BD_MeasureUnit unit on unit.fid=material.FBASEUNIT  left join T_DB_WAREHOUSE ware on ware.fid=entry.FWAREHOUSEID where entry.flot='"+strLot+"' and (purbill.cfistuiku=0 or purbill.cfistuiku is null) and trans.fnumber='001' and bizType.fnumber='130' and purbill.FBASESTATUS =4";
		System.out.println("委外退库接口"+strSQLweiwai);
		var rsweiwai = DbUtil.executeQuery(ctx, strSQLweiwai);
		var purfid="";
		strBizTypeNo="130";//业务类型编码
		strBillTypeNo="103";//单据类型编码
		strTranstionType="001";//事务类型
		var strSupNo="1000"; //供应商编码
		var strCurrencyNo ="BB01"; //币别编码
		var strPaymentType="004";// 付款方式
		var bgExchangeRate=BigDecimal.ONE;// 汇率
		bgTotalQty =BigDecimal.ZERO;// 数量
		var bgTotalAmount =BigDecimal.ZERO;// 金额
		var bgTotalActualCost =BigDecimal.ZERO;// 实际成本
		var bgTotalLocalAmount =BigDecimal.ZERO;// 本位币金额
		var isInTax=true;
		var sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		while (rsweiwai.next()) {
			purfid=rsweiwai.getString("purfid");
			if(newqty.compareTo(BigDecimal.ZERO)==0){
				isSucess=false;
				errMsg=errMsg + "重量不能为0\r\n";
				continue;
			}
			var strMaterialNo=rsweiwai.getString("materialnum");
			var strUnit=rsweiwai.getString("unitname");
			var strStockNo=rsweiwai.getString("warenum");
			strSupNo=rsweiwai.getString("suppnum");
			
			var destType=BOSObjectType.create("783061E3");
			var sourceType=BOSObjectType.create("783061E3");
			var botpId=BOSUuid.read("de7VqEIjQ+iK5nkGfrdeaARRIsQ=");
			System.out.println("委外退库接口开始");
			var view = new EntityViewInfo();
			var sic = view.getSelector();
			sic.add("*");
			
			sic.add("entries.*");
			
			var filter = new FilterInfo();
			var fic = filter.getFilterItems();
			fic.add(new FilterItemInfo("id", purfid));
			view.setFilter(filter);
			var sourceColl=PurInWarehsBillFactory.getLocalInstance(ctx).getPurInWarehsBillCollection(view);
			var newcoll=sourceColl.cast(new CoreBillBaseCollection().getClass());
			System.out.println("委外退货接口:"+newcoll.get(0).get("number"));
			var destinfo=PublicBaseUtil.botp(ctx,sourceType,destType,newcoll,botpId);
			System.out.println("委外退货接口"+destinfo.get(0).get("number"));
			var id=destinfo.get(0).get("id");
			System.out.println("委外退货接口"+id);
			if(id!=null){
				var purisinfo=PurInWarehsBillFactory.getLocalInstance(ctx).getPurInWarehsBillInfo(new ObjectUuidPK(id));
				System.out.println("委外退货接口"+purisinfo.getTransactionType().get("id"));
				var sdfDate = new SimpleDateFormat("yyyyMMdd");
				var bizdate = sdfDate.parse(strBizDate);
				purisinfo.setBizDate(bizdate);
				var wah=PublicBaseUtil.getWarehouseInfoByNumber(ctx,strStockNo);
				var sic2 = new SelectorItemCollection();
				sic2.add("id");
				sic2.add("bizDate");
				PurInWarehsBillFactory.getLocalInstance(ctx).updatePartial(purisinfo, sic2);
				var strSQLsi = "update T_IM_PurInWarehsBill set cfbxNumber='重量修正电文CCCW09' where fid='"+id+"'";
				System.out.println("委外退货接口"+strSQLsi);
				DbUtil.execute(ctx, strSQLsi);
				purisinfo=PurInWarehsBillFactory.getLocalInstance(ctx).getPurInWarehsBillInfo(new ObjectUuidPK(id));
				if(purisinfo!=null){
					if(purisinfo.getBaseStatus().equals(com.kingdee.eas.scm.common.BillBaseStatusEnum.TEMPORARILYSAVED)){
						PurInWarehsBillFactory.getLocalInstance(ctx).submit(purisinfo);
					}
					purisinfo=PurInWarehsBillFactory.getLocalInstance(ctx).getPurInWarehsBillInfo(new ObjectUuidPK(id));
					if(purisinfo.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
						PurInWarehsBillFactory.getLocalInstance(ctx).audit(new ObjectUuidPK(id));
					}
					
				}
				
				var strSQL2 = "update  T_IM_PurInWarehsBill set cfistuiku =1 where fid='"+purfid+"'";
				System.out.println("委外退货接口"+strSQL2);
				DbUtil.execute(ctx, strSQL2);
				
			}
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
			// 供应商
			var sup=PublicBaseUtil.getSupplier(ctx, strSupNo);
			if(sup==null){
				isSucess=false;
				errMsg=errMsg + "供应商不能为空\r\n";
			}
			//库存组织
			var storeInfo=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strCUNo);
			if(storeInfo==null){
				isSucess=false;
				errMsg=errMsg + "库存组织不能为空\r\n";
			}
			//币别
			var currency=PublicBaseUtil.getCurrencyInfo(ctx, strCurrencyNo);
			if(currency==null){
				isSucess=false;
				errMsg=errMsg + "币别不能为空\r\n";
			}
			// 付款方式
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
			var bgQty=new BigDecimal("0");
			var bgPrice=new BigDecimal("0");
			var bgTaxRate=new BigDecimal("0");
			var bgAmount=new BigDecimal("0");
			
			var material= PublicBaseUtil.getMaterialInfoByNumber(ctx, cu.getId().toString(), strMaterialNo);
			if(material==null){
				isSucess=false;
				errMsg=errMsg + "物料不能为空\r\n";
			}
			
			var bgTaxPrice=bgPrice.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
			var bgTaxAmount=bgAmount.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
			var bgTax=bgAmount.multiply(bgTaxRate).setScale(2, BigDecimal.ROUND_HALF_UP);
			var bgLocalTax=bgTax.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
			var bgLocalPrice =bgPrice.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
			var bgLocalAmount =bgAmount.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
			
			var bgLocalTaxPrice =bgTaxPrice.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
			var bgLocalTaxAmount =bgTaxAmount.multiply(bgExchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
			//库存组织
			var storOrgUnit=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
			if(storOrgUnit==null){
				isSucess=false;
				errMsg=errMsg + "库存组织不能为空\r\n";
			}
			//仓库
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
				info.put("bxNumber","重量修正电文CCCW09");
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
				// 事务类型
				if(trantypeInfo!=null){
					info.setTransactionType(trantypeInfo);
				}
				// 供应商
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
			
				//物料
				//var material=PublicBaseUtil.getMaterialInfoByNumber(ctx, info.getCU().getId().toString(), strMaterialNo);
				
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
				entry.setQty(newqty);
				entry.setBaseQty(newqty);
				bgTotalQty=bgTotalQty.add(newqty);
				entry.setPrice(bgPrice);
				entry.setTaxAmount(bgAmount);
				entry.setTaxPrice(bgTaxPrice);
				entry.setTaxRate(bgTaxRate);
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
				entry.setUnWriteOffQty(newqty);
				entry.setUnWriteOffBaseQty(newqty);
				entry.setUnWriteOffAmount(bgAmount); //未核销金额
				entry.setUnReturnedBaseQty(newqty);
				
				entry.setUnitActualCost(bgPrice); //单位实际成本
				entry.setActualCost(bgAmount); //实际成本 
				entry.setPurOrderEntrySeq(0);
				entry.setActualPrice(bgPrice); //实际单价
				entry.setActualTaxPrice(bgTaxPrice); //实际含税单价
				entry.setPurchaseCost(bgAmount); //采购成本
				entry.setUnitPurchaseCost(bgPrice); //单位采购成本
				var purEntrys = new PurInWarehsEntryCollection();
				purEntrys.add(entry);
				if (purEntrys.size() > 0) {
					info.getEntries().addCollection(purEntrys);
				}
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
		}
		var strSQLCaiGou = "select purbill.fid purfid,supplier.fnumber suppnum,entry.fqty entryqty,material.fnumber materialnum,entry.fprice purprice,unit.fname_l2 unitname,ware.fnumber warenum from T_IM_PurInWarehsBill purbill left join T_SCM_TransactionType trans on purbill.FTransactionTypeID=trans.fid left join T_SCM_BizType bizType on purbill.FBizTypeID=bizType.fid  left join T_IM_PurInWarehsEntry entry on purbill.fid = entry.fparentid left join T_BD_Supplier supplier on purbill.FSupplierID=supplier.fid left join T_BD_Material material on entry.fmaterialid=material.fid left join T_BD_MeasureUnit unit on unit.fid=material.FBASEUNIT  left join T_DB_WAREHOUSE ware on ware.fid=entry.FWAREHOUSEID where entry.flot='"+strLot+"' and (purbill.cfistuiku=0 or purbill.cfistuiku is null) and trans.fnumber='001' and bizType.fnumber='110'  and purbill.FBASESTATUS =4";
		System.out.println("采购退库接口"+strSQLCaiGou);
		var rsCaiGou = DbUtil.executeQuery(ctx, strSQLCaiGou);
		var purfid="";
		strBizTypeNo="110";//业务类型编码
		strBillTypeNo="103";//单据类型编码
		strTranstionType="001";//事务类型
		var strSupNo=""; //供应商编码
		var strCurrencyNo ="BB01"; //币别编码
		var strPaymentType="004";// 付款方式
		var bgExchangeRate=BigDecimal.ONE;// 汇率
		bgTotalQty =BigDecimal.ZERO;// 数量
		var bgTotalAmount =BigDecimal.ZERO;// 金额
		var bgTotalActualCost =BigDecimal.ZERO;// 实际成本
		var bgTotalLocalAmount =BigDecimal.ZERO;// 本位币金额
		var isInTax=true;
		var sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		while (rsCaiGou.next()) {
			purfid=rsCaiGou.getString("purfid");
			var newqty=new BigDecimal(newzl);
			if(newqty.compareTo(BigDecimal.ZERO)==0){
				isSucess=false;
				errMsg=errMsg + "重量不能为0\r\n";
				continue;
			}
			var purprice=rsCaiGou.getBigDecimal("purprice");
			var strMaterialNo=rsCaiGou.getString("materialnum");
			var strUnit=rsCaiGou.getString("unitname");
			var strStockNo=rsCaiGou.getString("warenum");
			strSupNo=rsCaiGou.getString("suppnum");
			var bgTaxRate=new BigDecimal("0");
			bgTaxRate=new BigDecimal("13");
			
			var destType=BOSObjectType.create("783061E3");
			var sourceType=BOSObjectType.create("783061E3");
			var botpId=BOSUuid.read("GuKLmgEUEADgABTHwKgShQRRIsQ=");
			System.out.println("采购退库接口开始");
			var view = new EntityViewInfo();
			var sic = view.getSelector();
			sic.add("*");
			
			sic.add("entries.*");
			
			var filter = new FilterInfo();
			var fic = filter.getFilterItems();
			fic.add(new FilterItemInfo("id", purfid));
			view.setFilter(filter);
			var sourceColl=PurInWarehsBillFactory.getLocalInstance(ctx).getPurInWarehsBillCollection(view);
			var newcoll=sourceColl.cast(new CoreBillBaseCollection().getClass());
			System.out.println("采购退货接口:"+newcoll.get(0).get("number"));
			var destinfo=PublicBaseUtil.botp(ctx,sourceType,destType,newcoll,botpId);
			System.out.println("采购退货接口"+destinfo.get(0).get("number"));
			var id=destinfo.get(0).get("id");
			System.out.println("采购退货接口"+id);
			if(id!=null){
				var purisinfo=PurInWarehsBillFactory.getLocalInstance(ctx).getPurInWarehsBillInfo(new ObjectUuidPK(id));
				System.out.println("采购退货接口"+purisinfo.getTransactionType().get("id"));
				var strSQLsi = "update T_IM_PurInWarehsBill set cfbxNumber='重量修正电文CCCW09' where fid='"+id+"'";
				System.out.println("委外退货接口"+strSQLsi);
				DbUtil.execute(ctx, strSQLsi);
				var sdfDate = new SimpleDateFormat("yyyyMMdd");
				var bizdate = sdfDate.parse(strBizDate);
				purisinfo.setBizDate(bizdate);
				var wah=PublicBaseUtil.getWarehouseInfoByNumber(ctx,strStockNo);
				var sic2 = new SelectorItemCollection();
				sic2.add("id");
				sic2.add("bizDate");
				PurInWarehsBillFactory.getLocalInstance(ctx).updatePartial(purisinfo, sic2);
				purisinfo=PurInWarehsBillFactory.getLocalInstance(ctx).getPurInWarehsBillInfo(new ObjectUuidPK(id));
				if(purisinfo!=null){
					if(purisinfo.getBaseStatus().equals(com.kingdee.eas.scm.common.BillBaseStatusEnum.TEMPORARILYSAVED)){
						PurInWarehsBillFactory.getLocalInstance(ctx).submit(purisinfo);
					}
					purisinfo=PurInWarehsBillFactory.getLocalInstance(ctx).getPurInWarehsBillInfo(new ObjectUuidPK(id));
					if(purisinfo.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
						PurInWarehsBillFactory.getLocalInstance(ctx).audit(new ObjectUuidPK(id));
					}
					
				}
				
				var strSQL2 = "update  T_IM_PurInWarehsBill set cfistuiku =1 where fid='"+purfid+"'";
				System.out.println("采购退货接口"+strSQL2);
				DbUtil.execute(ctx, strSQL2);
				
			}
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
			// 供应商
			var sup=PublicBaseUtil.getSupplier(ctx, strSupNo);
			if(sup==null){
				isSucess=false;
				errMsg=errMsg + "供应商不能为空\r\n";
			}
			//库存组织
			var storeInfo=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strCUNo);
			if(storeInfo==null){
				isSucess=false;
				errMsg=errMsg + "库存组织不能为空\r\n";
			}
			//币别
			var currency=PublicBaseUtil.getCurrencyInfo(ctx, strCurrencyNo);
			if(currency==null){
				isSucess=false;
				errMsg=errMsg + "币别不能为空\r\n";
			}
			// 付款方式
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
			var material=PublicBaseUtil.getMaterialInfoByNumber(ctx, cu.getId().toString(), strMaterialNo);
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
				info.put("bxNumber","重量修正电文CCCW09");
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
				info.setPurchaseType(com.kingdee.eas.scm.common.PurchaseTypeEnum.PURCHASE);//采购类别
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
				// 事务类型
				if(trantypeInfo!=null){
					info.setTransactionType(trantypeInfo);
				}
				// 供应商
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
				
				
				var bgQty=new BigDecimal("0");
				var bgPrice=new BigDecimal("0");
				if(purprice!=null){
					bgPrice=purprice;
				}
				
				var bgTaxRate=new BigDecimal("0");
				var bgAmount=new BigDecimal("0");
				var bgKd=new BigDecimal("0");
				var bgHd=new BigDecimal("0");
				var ph="0001";
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
				bgKd=BigDecimal.valueOf(1200);
				try
				{
					//bgKd=new BigDecimal(kd).divide(new BigDecimal("10"));;
					bgKd=BigDecimal.valueOf(1200);
				}catch(e){
					
				}
				bgHd=BigDecimal.valueOf(0.8);
				try
				{
					//bgHd=new BigDecimal(hd).divide(new BigDecimal("1000"));
					bgHd=BigDecimal.valueOf(0.8);
				}catch(e){
					
				}
				if (bgKd==0){
					isSucess=false;
					errMsg=errMsg + "宽度不能为零\r\n";
				}
				if (bgHd==0){
					isSucess=false;
					errMsg=errMsg + "厚度不能为零\r\n";
				}
				var bgTaxPrice=bgPrice.multiply(bgTaxRate.divide(new BigDecimal("100"),8,BigDecimal.ROUND_HALF_UP).add(BigDecimal.ONE));
				System.out.println("单价："+bgPrice.toString());
				bgAmount=bgPrice.multiply(newqty);
				var bgTaxAmount=bgTaxPrice.multiply(newqty);
				var bgTax=bgTaxAmount.subtract(bgAmount);
				var bgLocalTax=bgTax.multiply(bgExchangeRate);
				var bgLocalPrice =bgPrice.multiply(bgExchangeRate);
				var bgLocalAmount =bgAmount.multiply(bgExchangeRate);
				
				var bgLocalTaxPrice =bgTaxPrice.multiply(bgExchangeRate);
				var bgLocalTaxAmount =bgTaxAmount.multiply(bgExchangeRate);
				
				
				//big
				//新增分录实体
				var entry=new PurInWarehsEntryInfo();
				//分录行号
				entry.setSeq(1);
				entry.setSourceBillEntrySeq(0);
			
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
				entry.setQty(newqty);
				entry.setBaseQty(newqty);
				bgTotalQty=bgTotalQty.add(newqty);
				entry.setPrice(bgPrice);
				entry.setTaxAmount(bgTaxAmount);
				entry.setTaxPrice(bgTaxPrice);
				entry.setTaxRate(bgTaxRate);
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
				entry.setUnWriteOffQty(newqty);
				entry.setUnWriteOffBaseQty(newqty);
				entry.setUnWriteOffAmount(bgAmount); //未核销金额
				entry.setUnReturnedBaseQty(newqty);
				
				entry.setUnitActualCost(bgPrice); //单位实际成本
				entry.setActualCost(bgAmount); //实际成本 
				entry.setPurOrderEntrySeq(0);
				entry.setActualPrice(bgPrice); //实际单价
				entry.setActualTaxPrice(bgTaxPrice); //实际含税单价
				entry.setPurchaseCost(bgAmount); //采购成本
				entry.setUnitPurchaseCost(bgPrice); //单位采购成本
				var purEntrys = new PurInWarehsEntryCollection();
				purEntrys.add(entry);
				if (purEntrys.size() > 0) {
					info.getEntries().addCollection(purEntrys);
				}
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
		}
		
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
	}/*else{
		var strBizTypeNo="520";//业务类型编码
		var strBillTypeNo="121";//单据类型编码
		var bgTotalQty =BigDecimal.ZERO;// 数量
		var strLotqian="111";
		var strLothou="20190402";
		var newzhq="400";
		var newzhhou="400";
		var bgQty=new BigDecimal(newzhq);
		var zhuanhhouQty=new BigDecimal(newzhhou);
		var strMaterialNozhq="50901230082000";
		var strMaterialNozhhou="50900010002200";
		var strStockNo="001";
		var info =new MatAttrChangeBillInfo();
		System.out.println("形态转换接口："+strMaterialNozhq);
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
		info.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
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
		//库存组织
		var storeInfo=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strCUNo);
		if(storeInfo!=null){
			info.setStorageOrgUnit(storeInfo);
		}else
		{
			isSucess=false;
			errMsg=errMsg + "库存组织不能为空\r\n";
		}
		//新增分录实体
		var entryzhq=new MatAttrChangeBeforeEntryInfo();
		//分录行号
		entryzhq.setSeq(1);
		// 获取物料
		
		var materialzhq=PublicBaseUtil.getMaterialInfoByNumber(ctx, info.getCU().getId().toString(), strMaterialNozhq);
		var materialzhhou=PublicBaseUtil.getMaterialInfoByNumber(ctx, info.getCU().getId().toString(), strMaterialNozhhou);
		if (materialzhq!=null){
			entryzhq.setMaterial(materialzhq);
			entryzhq.setBaseUnit(materialzhq.getBaseUnit());
			entryzhq.setUnit(materialzhq.getBaseUnit());
		}else
		{
			isSucess=false;
			errMsg=errMsg + "转换前物料不能为空\r\n";
		}
		entryzhq.setBaseStatus(EntryBaseStatusEnum.ADD);//
		entryzhq.setAssCoefficient(BigDecimal.ZERO);
		//库存组织
		var storOrgUnit=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
		if(storOrgUnit!=null){
			entryzhq.setStorageOrgUnit(storOrgUnit);
			
		}else
		{
			isSucess=false;
			errMsg=errMsg + "库存组织不能为空\r\n";
		}
		// 财务组织
		var company=PublicBaseUtil.getCompany(ctx, cu.getNumber());
		if(company!=null){
			entryzhq.setCompanyOrgUnit(company);
		}else{
			isSucess=false;
			errMsg=errMsg + "财务组织不能为空\r\n";
		}
		//仓库
		var stock=PublicBaseUtil.getWarehouseInfoByNumber(ctx, strStockNo);
		if(stock!=null){
			entryzhq.setWarehouse(stock);
		}else
		{
			isSucess=false;
			errMsg=errMsg + "仓库不能为空\r\n";
		}
		entryzhq.setQty(bgQty);
		entryzhq.setBaseQty(bgQty);
		entryzhq.setAssociateQty(bgQty);
		var storeTypeInfo=new com.kingdee.eas.basedata.scm.im.inv.StoreTypeInfo();
		var view4 = new EntityViewInfo();
		var sic4 = view4.getSelector();
		sic4.add("*");
		var filter4 = new FilterInfo();
		var fic4 = filter4.getFilterItems();
		fic4.add(new FilterItemInfo("number", "G"));
		view4.setFilter(filter4);
		var storeTypeCollection = new com.kingdee.eas.basedata.scm.im.inv.StoreTypeCollection();

		if (ctx != null) {
			storeTypeCollection = com.kingdee.eas.basedata.scm.im.inv.StoreTypeFactory.getLocalInstance(ctx).getStoreTypeCollection(view4);
			
		}

		if (storeTypeCollection != null
				&& storeTypeCollection.size() != 0) {
			storeTypeInfo = storeTypeCollection.get(0);
			entryzhq.setStoreType(storeTypeInfo);
		}
		var storeStateInfo=new com.kingdee.eas.basedata.scm.im.inv.StoreStateInfo();
		var view = new EntityViewInfo();
		var sic = view.getSelector();
		sic.add("*");
		var filter = new FilterInfo();
		var fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("number", "1"));
		view.setFilter(filter);
		var storeStateCollection = new com.kingdee.eas.basedata.scm.im.inv.StoreStateCollection();

		if (ctx != null) {
			storeStateCollection = com.kingdee.eas.basedata.scm.im.inv.StoreStateFactory.getLocalInstance(ctx).getStoreStateCollection(view);
			
		}

		if (storeStateCollection != null
				&& storeStateCollection.size() != 0) {
			storeStateInfo = storeStateCollection.get(0);
			entryzhq.setStoreStatus(storeStateInfo);
		}
		entryzhq.setLot(strLotqian);
		var entryzhhou=new MatAttrChangeAfterEntryInfo();
		entryzhhou.setSeq(1);
		if (materialzhhou!=null){
			entryzhhou.setMaterial(materialzhhou);
			entryzhhou.setBaseUnit(materialzhhou.getBaseUnit());
			entryzhhou.setUnit(materialzhhou.getBaseUnit());
		}else
		{
			isSucess=false;
			errMsg=errMsg + "转换后物料不能为空\r\n";
		}
		entryzhhou.setBaseStatus(EntryBaseStatusEnum.ADD);//
		entryzhhou.setAssCoefficient(BigDecimal.ZERO);
		//库存组织
		if(storOrgUnit!=null){
			entryzhhou.setStorageOrgUnit(storOrgUnit);
			
		}else
		{
			isSucess=false;
			errMsg=errMsg + "库存组织不能为空\r\n";
		}
		// 财务组织
		if(company!=null){
			entryzhhou.setCompanyOrgUnit(company);
		}else{
			isSucess=false;
			errMsg=errMsg + "财务组织不能为空\r\n";
		}
		//仓库
		if(stock!=null){
			entryzhhou.setWarehouse(stock);
		}else
		{
			isSucess=false;
			errMsg=errMsg + "仓库不能为空\r\n";
		}
		entryzhhou.setQty(zhuanhhouQty);
		entryzhhou.setBaseQty(zhuanhhouQty);
		entryzhhou.setAssociateQty(zhuanhhouQty);
		entryzhhou.setStoreType(storeTypeInfo);
		entryzhhou.setStoreStatus(storeStateInfo);
		entryzhhou.setLot(strLothou);
		var entryhoucollection = new MatAttrChangeAfterEntryCollection();
		entryhoucollection.add(entryzhhou);
		entryzhq.getEntry1().addCollection(entryhoucollection);
		var entryqiancollection = new MatAttrChangeBeforeEntryCollection();
		entryqiancollection.add(entryzhq);
		info.getEntry().addCollection(entryqiancollection);
		if(isSucess){
			var pk=MatAttrChangeBillFactory.getLocalInstance(ctx).save(info);  //保存
			if(pk!=null){
				var tempIno=MatAttrChangeBillFactory.getLocalInstance(ctx).getMatAttrChangeBillInfo(pk);
				if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.TEMPORARILYSAVED)) {
					MatAttrChangeBillFactory.getLocalInstance(ctx).submit(tempIno);
				}
				tempIno=MatAttrChangeBillFactory.getLocalInstance(ctx).getMatAttrChangeBillInfo(pk);
				if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
					MatAttrChangeBillFactory.getLocalInstance(ctx).audit(pk);
				}
				
				methodCtx.getParam(2).setValue("true") ;
				methodCtx.getParam(4).setValue("新增成功");
			}
		}else
		{
			methodCtx.getParam(2).setValue("false") ;
			methodCtx.getParam(4).setValue(errMsg);
		}
		strMsg="0110"+dwh+"CWCCD";
		
		if(isSucess){
			methodCtx.getParam(2).setValue("true");
			var strData=String.format("%-81s", "A")+"\n" ;
			methodCtx.getParam(3).setValue(strMsg+strData);
		}else
		{
			methodCtx.getParam(2).setValue("false");
			methodCtx.getParam(4).setValue(errMsg);
			var strMsg="0110"+dwh+"CWCCDB";
			var strData=String.format("%-80s", errMsg)+"\n" 
			methodCtx.getParam(3).setValue(strMsg+strData);
		}
	}*/
	
}