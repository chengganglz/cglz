/********************** 前置变量开始 ****************/
var strCreatorNo="user"; //创建人编码
var billStatus=BillBaseStatusEnum.ADD; //单据状态
var strBizTypeNo="390";//业务类型编码
var strBillTypeNo="105";//单据类型编码
var strTranstionType="024";//事务类型
var strCUNo="01";// 管理单元编码
var strUnit="吨";//单位默认吨
var strCostcenter = ''//成本中心编码  从MES获取
var strStockNo="001";//仓库编码  从MES获取
var bgQty=new BigDecimal("0");// 重量
var bgTotalQty =BigDecimal.ZERO;// 数量
var strLot=rjh;// 卷号
/********************** 前置变量结束 ****************/
/**********************  前置约束 *******************/
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
// 成本中心
	var costcenter=PublicBaseUtil.getCostCenterOrgUnitInfoByNumber(ctx,strCostcenter);
	if(costcenter==null){
		isSucess=false;
		errMsg=errMsg + "成本中心不能为空\r\n";
	}
// 仓库
var stock=PublicBaseUtil.getWarehouseInfoByNumber(ctx, strStockNo);
	if(stock==null){
		isSucess=false;
		errMsg=errMsg + "仓库不能为空\r\n";
	}
// 更新类型
var invUpType=PublicBaseUtil.getInvUpdateTypeInfoByNumber(ctx, "001");
	if(invUpType==null){
		isSucess=false;
		errMsg=errMsg + "更新类型不能为空\r\n";
	}
/**********************  前置约束 *******************/

/****************** 代码逻辑开始  ****************/
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
/****************  代码逻辑结束 ****************/