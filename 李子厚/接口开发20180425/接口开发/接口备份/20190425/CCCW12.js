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
imp.importPackage(Packages.com.kingdee.bos.metadata.entity);  
imp.importPackage(Packages.com.kingdee.bos.util);  
imp.importPackage(Packages.com.kingdee.eas.framework);  
imp.importPackage(Packages.com.kingdee.bos.dao.ormapping);
with(imp){ 
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
	//dwt="0524CCCW2020190224040527CCCWD11203778";
	System.out.println("生产退料电文号"+dwh);
	var isSucess=true;
	methodCtx.getParam(2).setValue("false") ;
	var errMsg="";
    var strCreatorNo="user"; //创建人编码
    var strCUNo="01";// 管理单元编码
	var strBizDate="20190417";//业务日期  从MES获取
	var strrecDate=strBizDate;//接收日期 
	var billStatus=BillBaseStatusEnum.ADD; //单据状态
	var strBizTypeNo="331";//业务类型编码
	var strBillTypeNo="140";//单据类型编码
    var strCurrencyNo ="BB01"; //币别编码
	
    var bgExchangeRate=BigDecimal.ONE;// 汇率
    var bgTotalQty =BigDecimal.ZERO;// 数量
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
	// 财务组织
	var company=PublicBaseUtil.getCompany(ctx, cu.getNumber());
	if(company==null){
		isSucess=false;
		errMsg=errMsg + "财务组织不能为空\r\n";
	}
	var strMaterialNo="";
	var strUnit="公斤（千克）";
	
	var strStockNo="001";//调出仓库编码  从MES获取
	var recstrStockNo="003";//调入仓库编码  从MES获取
	var bgQty=new BigDecimal("0");
	var rjh="201904168";//批次号  从MES获取
	rjh=rjh.trim();
	if("".equals(rjh))
	{
		isSucess=false;
		errMsg=errMsg + "卷号不能为空\r\n";
	}
	var strLot=rjh;
	var strSQLInv = "select inv.FCURSTOREQTY FCURSTOREQTY,Material.fnumber MaterialNumber from T_IM_Inventory inv left join T_BD_Material Material on Material.fid=inv.FMaterialID where flot='"+strLot+"'";
	System.out.println("销售出库接口"+strSQLInv);
	var rsInv = DbUtil.executeQuery(ctx, strSQLInv);
	if (rsInv.next()) {
		bgQty=rsInv.getBigDecimal("FCURSTOREQTY");
		strMaterialNo=rsInv.getString("MaterialNumber");
	}
	var material= PublicBaseUtil.getMaterialInfoByNumber(ctx, cu.getId().toString(), strMaterialNo);
	if(material==null){
		isSucess=false;
		errMsg=errMsg + "物料不存在\r\n";
		
	}
	var storOrgUnit=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
	if(storOrgUnit==null){
		isSucess=false;
		errMsg=errMsg + "库存组织不能为空\r\n";
	}
	var stock=PublicBaseUtil.getWarehouseInfoByNumber(ctx, strStockNo);
	if(stock==null){
		isSucess=false;
		errMsg=errMsg + "调出仓库不能为空\r\n";
	}
	var recstock=PublicBaseUtil.getWarehouseInfoByNumber(ctx, recstrStockNo);
	if(recstock==null){
		isSucess=false;
		errMsg=errMsg + "调入仓库不能为空\r\n";
	}
	/*if(isSucess){
		//新增库存调拨
		var info=new StockTransferBillInfo();
		info.put("bxNumber","库区变更电文CCCW12");
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
		if(bizType!=null){
			info.setBizType(bizType);
		}
		// 单据类型
		if(billType!=null){
			info.setBillType(billType);
		}
		//库存组织
		if(storeInfo!=null){
			info.setIssueStorageOrgUnit(storeInfo);
			info.setReceiptStorageOrgUnit(storeInfo);
		}
		//币别
		if(currency!=null){
			info.setCurrency(currency);
			info.setExchangeRate(bgExchangeRate);
		}
		// 财务组织
		if(company!=null){
			info.setReceiptCompanyOrgUnit(company);
			info.setIssueCompanyOrgUnit(company);
		}
		info.setIsShipment(true);//是否发运
		info.setIsShipment(false);//是否含税
		info.setIsInTax(false);//是否初始化单
	
		//新增分录实体
		var entry=new StockTransferBillEntryInfo();
		//分录行号
		entry.setSeq(1);
		
		if (material!=null){
			entry.setMaterial(material);
			entry.setBaseUnit(material.getBaseUnit());
			//单位
			var unit=PublicBaseUtil.getMeasureUnitInfoByName(ctx, material.getBaseUnit().getMeasureUnitGroup().getNumber(), strUnit);
			if (unit!=null){
				entry.setUnit(unit);
				entry.setBaseUnit(unit);
			}else
			{
				isSucess=false;
				errMsg=errMsg + "单位不能为空\r\n";
			}
		}
		
		entry.setBaseStatus(EntryBaseStatusEnum.ADD);//
		entry.setAssCoefficient(BigDecimal.ZERO);
		//库存组织
		if(storOrgUnit!=null){
			entry.setIssueStorageOrgUnit(storOrgUnit);
			entry.setReceiveStorageOrgUnit(storOrgUnit);
			
		}
		
		//财务组织
		entry.setIssueCompanyOrgUnit(company);
		entry.setReceiveCompanyOrgUnit(company);
		//调出仓库
		if(stock!=null){
			entry.setIssueWarehouse(stock);
		}
		if(recstock!=null){
			entry.setReceiptWarehouse(recstock);
		}
		entry.setQty(bgQty);
		entry.setBaseQty(bgQty);
		entry.setUnIssueBaseQty(bgQty);
		entry.setLot(strLot);
		entry.setBizDate(bizDate);
		var recDate=sdf.parse(strrecDate);
		entry.setReceiptPlanDate(recDate);
		entry.setIssuePlanDate(recDate);
		entry.setApAssociateBaseQty(bgQty);
		entry.setArAssociateBaseQty(bgQty);
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
			entry.setStoreType(storeTypeInfo);
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
			entry.setStoreState(storeStateInfo);
		}
		var Entrys = new StockTransferBillEntryCollection();
		Entrys.add(entry);
		info.getEntries().addCollection(Entrys);
	}
	if(isSucess){
		var pk=StockTransferBillFactory.getLocalInstance(ctx).save(info);  //保存
		if(pk!=null){
			var tempIno=StockTransferBillFactory.getLocalInstance(ctx).getStockTransferBillInfo(pk);
			var infoid=tempIno.getId().toString();
			if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.TEMPORARILYSAVED)) {
				StockTransferBillFactory.getLocalInstance(ctx).submit(tempIno);
			}
			tempIno=StockTransferBillFactory.getLocalInstance(ctx).getStockTransferBillInfo(pk);
			if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
				StockTransferBillFactory.getLocalInstance(ctx).audit(pk);
			}
			var destType=BOSObjectType.create("71D272F1");
			var sourceType=BOSObjectType.create("2239F30A");
			var botpId=BOSUuid.read("709becee-0108-1000-e000-2b10c0a812fd045122C4");
			
			var view = new EntityViewInfo();
			var sic = view.getSelector();
			sic.add("*");
			
			sic.add("entries.*");
			
			var filter = new FilterInfo();
			var fic = filter.getFilterItems();
			fic.add(new FilterItemInfo("id", infoid));
			System.out.println("调拨出库接口开始"+infoid);
			view.setFilter(filter);
			var sourceColl=StockTransferBillFactory.getLocalInstance(ctx).getStockTransferBillCollection(view);
			var newcoll=sourceColl.cast(new CoreBillBaseCollection().getClass());
			System.out.println("调拨出库接口:"+newcoll.get(0).get("number"));
			var destinfo=PublicBaseUtil.botp(ctx,sourceType,destType,newcoll,botpId);
			System.out.println("调拨出库接口"+destinfo.get(0).get("number"));
			var id=destinfo.get(0).get("id");
			if(id!=null){
				
				var moveoutinfo=MoveIssueBillFactory.getLocalInstance(ctx).getMoveIssueBillInfo(new ObjectUuidPK(id));
				System.out.println("调拨出库接口");
				
				var sdfDate = new SimpleDateFormat("yyyyMMdd");
				var bizdate = sdfDate.parse(strBizDate);
				moveoutinfo.setBizDate(bizdate);
				
				var sic2 = new SelectorItemCollection();
				sic2.add("id");
				sic2.add("bizDate");
				MoveIssueBillFactory.getLocalInstance(ctx).updatePartial(moveoutinfo, sic2);
				moveoutinfo=MoveIssueBillFactory.getLocalInstance(ctx).getMoveIssueBillInfo(new ObjectUuidPK(id));
				
				if(moveoutinfo!=null){
					if(moveoutinfo.getBaseStatus().equals(com.kingdee.eas.scm.common.BillBaseStatusEnum.TEMPORARILYSAVED)){
						MoveIssueBillFactory.getLocalInstance(ctx).submit(moveoutinfo);
					}
					moveoutinfo=MoveIssueBillFactory.getLocalInstance(ctx).getMoveIssueBillInfo(new ObjectUuidPK(id));
					if(moveoutinfo.getBaseStatus().equals(com.kingdee.eas.scm.common.BillBaseStatusEnum.SUBMITED)){
						MoveIssueBillFactory.getLocalInstance(ctx).audit(new ObjectUuidPK(id));
					}
					
				}
			}
			destType=BOSObjectType.create("E3DAFF63");
			sourceType=BOSObjectType.create("2239F30A");
			botpId=BOSUuid.read("709becee-0108-1000-e000-2980c0a812fd045122C4");
			
			view = new EntityViewInfo();
			sic = view.getSelector();
			sic.add("*");
			
			sic.add("entries.*");
			
			filter = new FilterInfo();
			fic = filter.getFilterItems();
			fic.add(new FilterItemInfo("id", infoid));
			System.out.println("调拨入库接口开始"+infoid);
			view.setFilter(filter);
			sourceColl=StockTransferBillFactory.getLocalInstance(ctx).getStockTransferBillCollection(view);
			newcoll=sourceColl.cast(new CoreBillBaseCollection().getClass());
			System.out.println("调拨入库接口:"+newcoll.get(0).get("number"));
			destinfo=PublicBaseUtil.botp(ctx,sourceType,destType,newcoll,botpId);
			System.out.println("调拨入库接口"+destinfo.get(0).get("number"));
			id=destinfo.get(0).get("id");
			if(id!=null){
				
				var moveininfo=MoveInWarehsBillFactory.getLocalInstance(ctx).getMoveInWarehsBillInfo(new ObjectUuidPK(id));
				System.out.println("调拨出库接口");
				
				var sdfDate = new SimpleDateFormat("yyyyMMdd");
				var bizdate = sdfDate.parse(strBizDate);
				moveininfo.setBizDate(bizdate);
				
				var sic2 = new SelectorItemCollection();
				sic2.add("id");
				sic2.add("bizDate");
				MoveInWarehsBillFactory.getLocalInstance(ctx).updatePartial(moveininfo, sic2);
				moveininfo=MoveInWarehsBillFactory.getLocalInstance(ctx).getMoveInWarehsBillInfo(new ObjectUuidPK(id));
				
				if(moveininfo!=null){
					if(moveininfo.getBaseStatus().equals(com.kingdee.eas.scm.common.BillBaseStatusEnum.TEMPORARILYSAVED)){
						MoveInWarehsBillFactory.getLocalInstance(ctx).submit(moveininfo);
					}
					moveininfo=MoveInWarehsBillFactory.getLocalInstance(ctx).getMoveInWarehsBillInfo(new ObjectUuidPK(id));
					if(moveininfo.getBaseStatus().equals(com.kingdee.eas.scm.common.BillBaseStatusEnum.SUBMITED)){
						MoveIssueBillFactory.getLocalInstance(ctx).audit(new ObjectUuidPK(id));
					}
					
				}
			}
			methodCtx.getParam(2).setValue("true");
			methodCtx.getParam(4).setValue("新增成功");
			
		}
	}else
	{
		methodCtx.getParam(2).setValue("false") ;
		methodCtx.getParam(4).setValue(errMsg);
	}*/
	
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
	
}