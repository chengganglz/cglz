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
with (imp) {
	System.out.println("/******************************  CCCW12 消息记录开始   **********************************/");
	var socketMsg = methodCtx.getParamValue(0);  //接收报文 
	var billID = methodCtx.getParamValue(1); // 业务单元ID  
	var result = methodCtx.getParamValue(2); //成功失败  true:false  必须有返回值  methodCtx.getParam(2).setValue("true") 
	var sendMsg = methodCtx.getParamValue(3); // 发送报文   如果需要发送电文,将电文拼好放到这里返回  methodCtx.getParam(3).setValue("")
	var errMsg = methodCtx.getParamValue(4); //错误消息 methodCtx.getParam(4).setValue("")  
	var ctx = pluginCtx.getContext(); //服务端上下文 

	socketMsg = '0076CCCW1220190425143655CCCWDL11940002400           C01                    ';
	/**********************************变量定义 *********************/
	// 约定电文长度
	var declareMsgLength = 76;
	// 逻辑允许运行标识
	var isSucess = true;
	// 物料未入库标识
	var isRK = true;
	// 电文类型
	var dwlx = 0;
	// 业务日期-默认值
	var ywrq = '19880101'
	// 机组号
	var jzh = '';
	// 物料id
	var wlid = '';
	// 重量
	var zl = new BigDecimal("0");
	// 物料实体
	var material = null;
	// 物料编码
	var strMaterialNo = '';	
	/**********************************变量定义结束 *********************/
	System.out.println("/*****************  电文内容获取   ******************/");
	// 电文号
	var dwh = PublicBaseUtil.substringByte(socketMsg, "GBK", 5, 20);//电文号  从MES获取
	System.out.println("电文号-dwh:" + dwh);
	if (dwh != null) {
		dwh = dwh.trim();
	}
	else {
		dwh = "";
	}
	if ("".equals(dwh)) {
		isSucess = false;
		System.out.println("Error-电文号不能为空,无法进行,中断");
		errMsg = errMsg + "电文号不能为空\r\n";
	}
	// 电文头
	var dwt = PublicBaseUtil.substringByte(socketMsg, "GBK", 1, 29);//电文头  从MES获取
	System.out.println("电文头-dwt:" + dwt);
	if (dwt != null) {
		dwt = dwt.trim();
	}
	else {
		dwt = "";
	}
	if ("".equals(dwt)) {
		isSucess = false;
		System.out.println("Error-电文头不能为空,无法进行,中断");
		errMsg = errMsg + "电文头不能为空\r\n";
	}

	// 卷号
	var jh = PublicBaseUtil.substringByte(socketMsg, "GBK", 30, 20);//  从MES获取
	System.out.println("卷号-jh:" + jh);
	if (jh != null) {
		jh = jh.trim();
	}
	else {
		jh = "";
	}
	if ("".equals(jh)) {
		isSucess = false;
		System.out.println("Error-卷号不能为空,无法进行,中断");
		errMsg = errMsg + "卷号不能为空\r\n";
	}

	//源库区号
	var ykqh = PublicBaseUtil.substringByte(socketMsg, "GBK", 50, 3);//  从MES获取
	System.out.println("源库区号-ykqh:" + ykqh);
	if (ykqh != null) {
		ykqh = ykqh.trim();
	}
	else {
		ykqh = "";
	}
	// if("".equals(ykqh))
	// {
	// 	// isSucess=false;
	// 	System.out.println("Error-源库区号不能为空,无法进行,中断");
	// 	errMsg=errMsg + "源库区号不能为空\r\n";
	// }
	// 目标库区号
	var mbkqh = PublicBaseUtil.substringByte(socketMsg, "GBK", 53, 3);//  从MES获取
	System.out.println("目标库区号-mbkqh:" + mbkqh);
	if (mbkqh != null) {
		mbkqh = mbkqh.trim();
	}
	else {
		mbkqh = "";
	}
	// if("".equals(ykqh))
	// {
	// 	// isSucess=false;
	// 	System.out.println("Error-源库区号不能为空,无法进行,中断");
	// 	errMsg=errMsg + "源库区号不能为空\r\n";
	// }

	// 预留字段
	var yl = PublicBaseUtil.substringByte(socketMsg, "GBK", 56, 20);//  从MES获取
	System.out.println("预留字段-yl:" + yl);
	if (yl != null) {
		yl = yl.trim();
	}
	else {
		yl = "";
	}
	// if("".equals(ykqh))
	// {
	// 	// isSucess=false;
	// 	System.out.println("Error-源库区号不能为空,无法进行,中断");
	// 	errMsg=errMsg + "源库区号不能为空\r\n";
	// }
	System.out.println("/*****************  电文内容获取   ******************/");
	// 获取长度
	// if(getlength(socketMsg)==declareMsgLength){
	/******************************************** 逻辑段处理 *******************************/
	if (isSucess) {
		// 根据卷号获取 单据类型，是否入库
		var getDjlxAndSfrkSql = "SELECT CFDWLX,CFSFRK,CFSCSJ,CFJZH,CFWLID,CFZL FROM  CT_CUS_JZSCCCJL where cfckjh = '" + jh + "'";
		var rows = DbUtil.executeQuery(ctx, getDjlxAndSfrkSql);
		if (rows != null && rows.next()) {
			dwlx = java.lang.Integer.parseInt(rows.getString("CFDWLX"));
			isRK = rows.getString("CFSFRK");
			ywrq = rows.getString('CFSCSJ');
			jzh = rows.getString('CFJZH');
			wlid = rows.getString('CFWLID');
			zl = new BigDecimal(rows.getString('CFZL'));
			var strBizDate = ywrq;
			System.out.println("电文类型-dwlx:" + dwlx);
			System.out.println("是否入库-isRK:" + isRK);
			System.out.println("业务日期-ywrq:" + ywrq);
			System.out.println("机组号-jzh:" + jzh);
			System.out.println("物料id-wlid:" + wlid);
			System.out.println("重量-zl:" + zl);
			// 根据机组号转成本中心编码
			var temp_cbzx = '';
			jzh = jzh.trim()
			/**
			 * 
			 * 机组号，成本中心编码 对应关系
			 * L101 酸洗 007
			 * L701 镀锌 008
			 * L201 虚拟轧机
			 * L801 卷材开平
			*/
			if ('L101'.equals(jzh)) {
				temp_cbzx = '007'
			}
			if ('L701'.equals(jzh)) {
				temp_cbzx = '008'
			}
			if ("".equals(temp_cbzx)) {
				isSucess = false;
				System.out.println("Error-当前机组号没有找到对应的成本中心,无法进行,中断");
				errMsg = errMsg + "当前机组号没有找到对应的成本中心\r\n";
			} else {
				costcenter = PublicBaseUtil.getCostCenterOrgUnitInfoByNumber(ctx, temp_cbzx);//成本中心编码 
				if (costcenter == null) {
					System.out.println("Error-成本中心为空,无法进行,中断");
					isSucess = false;
					errMsg = errMsg + "成本中心不能为空\r\n";
				}
			}
			// 获取物料实体
						
	
			// 判断该卷是否入库
			if (isRK==0) {
				/**
				 * 电文类型字典
				 * 名称 值
				 * 机组生产产出电文 1
				 * 盘盈电文 2
				 * 委外生产产出 3
				 * 销售退货 4
				 */
				// 源库区号是否为空字符串 且 目标库区号不为空
				if ("".equals(ykqh) && !"".equals(mbkqh)) {
					//若正确，则使用该电文类型，走对应逻辑
					// if(dwlx == '1'){

					// }else{
					// 	if(dwlx == 2){

					// 	}else{
					// 		if(dwlx == 3){

					// 		}else{
					// 			if(dwlx == '4'){

					// 			}else{

					// 			}
					// 		}
					// 	}
					// }
					switch (dwlx) {
						case 1: {
							// 走生产产出逻辑
							System.out.println("---->走生产产出逻辑 CCCW04");
							if (isSucess) {
								/********************** 前置变量开始 ****************/
								var strCreatorNo = "user"; //创建人编码
								var billStatus = BillBaseStatusEnum.ADD; //单据状态
								var strBizTypeNo = "390";//业务类型编码
								var strBillTypeNo = "105";//单据类型编码
								var strTranstionType = "024";//事务类型
								var strCUNo = "01";// 管理单元编码
								var strUnit = "吨";//单位默认吨
								// var strCostcenter = ''//成本中心编码  从MES获取 成本中心已获取实体
								var strStockNo = mbkqh;//仓库编码  从MES获取 问题?这里不确定是否需要进行转换
								var bgQty = new BigDecimal("0");// 重量
								bgQty = zl;
								var bgTotalQty = BigDecimal.ZERO;// 数量
								var strLot = jh;// 卷号 MES中获取
								/********************** 前置变量结束 ****************/
								/**********************  前置约束 *******************/
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
								// 事务类型
								var trantypeInfo = PublicBaseUtil.getTransactionTypeInfoByNumber(ctx, strTranstionType);
								if (trantypeInfo == null) {
									isSucess = false;
									errMsg = errMsg + "事务类型不能为空\r\n";
								}
								//库存组织
								var storeInfo = PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strCUNo);
								if (storeInfo == null) {
									isSucess = false;
									errMsg = errMsg + "库存组织不能为空\r\n";
								}
								var storOrgUnit=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
								// // 成本中心
								// 	var costcenter=PublicBaseUtil.getCostCenterOrgUnitInfoByNumber(ctx,strCostcenter);
								// 	if(costcenter==null){
								// 		isSucess=false;
								// 		errMsg=errMsg + "成本中心不能为空\r\n";
								// 	}
								// 仓库
								var stock = PublicBaseUtil.getWarehouseInfoByNumber(ctx, strStockNo);
								if (stock == null) {
									isSucess = false;
									errMsg = errMsg + "仓库不能为空\r\n";
									System.out.println("Error-仓库不能为空-处理终止");
								}
								// 更新类型
								var invUpType = PublicBaseUtil.getInvUpdateTypeInfoByNumber(ctx, "001");
								if (invUpType == null) {
									isSucess = false;
									errMsg = errMsg + "更新类型不能为空\r\n";
								}
								/**********************  前置约束 *******************/

								/****************** 代码逻辑开始  ****************/
								try {
									// var strSQLInv = "SELECT FNUMBER  FROM T_BD_Material where fid = '"+wlid+"'";
									var strSQLInv = "SELECT FNUMBER  FROM T_BD_Material where fid = '"+wlid+"'";
									System.out.println("获取物料编码SQL"+strSQLInv);
									var rsInv = DbUtil.executeQuery(ctx, strSQLInv);
									System.out.println("1");
									if (rsInv.next()) {
										// bgQty=rsInv.getBigDecimal("FCURSTOREQTY");
										// // 临时测试
										// bgQty = 1;
										//临时代码结束
										strMaterialNo=rsInv.getString("FNUMBER");
									}
									System.out.println("2");
									System.out.println("cu.getId().toString():"+cu.getId().toString());

									material= PublicBaseUtil.getMaterialInfoByNumber(ctx, cu.getId().toString(), strMaterialNo);
								} catch (e) {
									System.out.println("Error-获取物料实体时出现异常,无法进行,中断");
									isSucess = false;
									errMsg = errMsg + "获取物料实体时出现异常\r\n";
								}
								if (isSucess) {
									System.out.println("过程-新增生产入库-开始");
									//新增生产入库
									var info = new ManufactureRecBillInfo();
									info.put("bxNumber", "机组生产产出电文CCCW04");
									System.out.println("过程-新增生产入库-开始-1");
									//制单人
									var creator = PublicBaseUtil.getUserByNumber(ctx, strCreatorNo);
									if (creator != null) {
										info.setCreator(creator);
									} else {
										info.setCreator(ContextUtil.getCurrentUserInfo(ctx));
									}
									System.out.println("过程-新增生产入库-开始-2");
									//获取管理单元 
									if (cu != null) {
										info.setCU(cu); //控制单元
									}
									System.out.println("过程-新增生产入库-开始-3");
									//创建时间
									try {
										info.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
									} catch (e) {
										System.out.println("Error-创建时间时出现异常,无法进行,中断");
									}
									System.out.println("过程-新增生产入库-开始-4");
									//业务日期
									try {
										var bizDate;
										var sdf = new SimpleDateFormat("yyyyMMdd");
										bizDate = sdf.parse(strBizDate);
										info.setBizDate(bizDate);
										var c = Calendar.getInstance();
										c.setTime(bizDate);
										var year = c.get(Calendar.YEAR);   //年度
										var period = c.get(Calendar.MONTH) + 1;
										var month = c.get(Calendar.YEAR) * 100 + c.get(Calendar.MONTH) + 1; //期间
										var iday = (c.get(Calendar.YEAR) * 100 + c.get(Calendar.MONTH) + 1) * 100 + c.get(Calendar.MONDAY);
										info.setYear(year);
										info.setPeriod(period);
										info.setMonth(month);
										info.setDay(iday);
									} catch (e) {
										isSucess = false;
										errMsg = errMsg + "日期格式不正确\r\n";
										System.out.println("Error-日期格式不正确,无法进行,中断");
										//throw new BOSException("日期格式不正确");

									}
									System.out.println("过程-新增生产入库-开始-5");
									//单据状态
									info.setBaseStatus(billStatus);
									System.out.println("过程-新增生产入库-开始-6");
									// 业务类型
									if (bizType != null) {
										info.setBizType(bizType);
									}
									System.out.println("过程-新增生产入库-开始-7");
									// 单据类型
									if (billType != null) {
										info.setBillType(billType);
									}
									System.out.println("过程-新增生产入库-开始-8");
									// 事务类型
									if (trantypeInfo != null) {
										info.setTransactionType(trantypeInfo);
									}
									System.out.println("过程-新增生产入库-开始-9");
									//库存组织
									if (storeInfo != null) {
										info.setStorageOrgUnit(storeInfo);
										info.setProcessOrgUnit(storeInfo);
									}
									System.out.println("过程-新增生产入库-开始-10");
									var id = storeInfo.getId().toString();
									System.out.println("过程-新增生产入库-开始-11");
									// 成本中心
									if (costcenter != null) {
										info.setCostCenterOrgUnit(costcenter);

									}
									System.out.println("过程-新增生产入库-开始-12");
									// 倒冲标识
									info.setIsBackFlushSucceed(com.kingdee.eas.scm.im.inv.BackFlushSucceedEnum.NO_BACKFLUSH);
									System.out.println("过程-新增生产入库-开始-13");
									// 财务组织
									var company = PublicBaseUtil.getCompany(ctx, cu.getNumber());
									System.out.println("过程-新增生产入库-开始-14");
									//新增分录实体
									var entry = new ManufactureRecBillEntryInfo();
									System.out.println("过程-新增生产入库-开始-15");
									//分录行号
									entry.setSeq(1);
									entry.setSourceBillEntrySeq(0);
									System.out.println("过程-新增生产入库-开始-16");
									// 获取物料
									// System.out.println("牌号" + ph);
									// System.out.println("牌号名称" + phmc);
									// System.out.println("宽度" + bgKd.toString());
									// System.out.println("厚度" + bgHd.toString());
									// System.out.println("单位" + strUnit);

									// System.out.println("牌号" + ph);
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
											System.out.println("Error-单位不能为空,无法进行,中断");
										}
									}else{
										System.out.println("Error-物料不存在,无法进行,中断");
									}
									System.out.println("过程-新增生产入库-开始-17");
									entry.setBaseStatus(EntryBaseStatusEnum.NULL);//
									entry.setAssCoefficient(BigDecimal.ZERO);
									System.out.println("过程-新增生产入库-开始-18");
									//库存组织
									if (storOrgUnit != null) {
										entry.setStorageOrgUnit(storOrgUnit);
									}
									System.out.println("过程-新增生产入库-开始-19");
									//财务组织
									entry.setCompanyOrgUnit(company);
									System.out.println("过程-新增生产入库-开始-20");
									//成本中心
									entry.setCostCenterOrgUnit(costcenter);
									System.out.println("过程-新增生产入库-开始-21");
									//仓库
									if (stock != null) {
										entry.setWarehouse(stock);
									}
									System.out.println("过程-新增生产入库-开始-22");
									entry.setQty(bgQty);
									entry.setBaseQty(bgQty);
									System.out.println("过程-新增生产入库-开始-23");
									bgTotalQty = bgTotalQty.add(bgQty);
									info.setTotalQty(bgTotalQty);
									entry.setLot(strLot);
									System.out.println("过程-新增生产入库-开始-24");
									entry.setInvUpdateType(invUpType);
									entry.setAssociateQty(bgQty);
									System.out.println("过程-新增生产入库-开始-25");
									var manuEntrys = new ManufactureRecBillEntryCollection();
									manuEntrys.add(entry);
									System.out.println("过程-新增生产入库-开始-26");
									if (manuEntrys.size() > 0) {
										info.getEntries().addCollection(manuEntrys);
									}
									System.out.println("过程-新增生产入库-开始-27");
								}
								if (isSucess) {
									System.out.println("过程-新增生产入库-开始-28");
									var pk = ManufactureRecBillFactory.getLocalInstance(ctx).save(info);  //保存
									System.out.println("过程-新增生产入库-开始-29");
									if (pk != null) {
										System.out.println("过程-新增生产入库-开始-30");
										//	Context ctxTemp=ctx.
										var tempIno = ManufactureRecBillFactory.getLocalInstance(ctx).getManufactureRecBillInfo(pk);
										if (tempIno.getBaseStatus().equals(BillBaseStatusEnum.TEMPORARILYSAVED)) {
											ManufactureRecBillFactory.getLocalInstance(ctx).submit(tempIno);
										}
										System.out.println("过程-新增生产入库-开始-31");
										tempIno = ManufactureRecBillFactory.getLocalInstance(ctx).getManufactureRecBillInfo(pk);
										if (tempIno.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
											ManufactureRecBillFactory.getLocalInstance(ctx).audit(pk);
										}
										System.out.println("过程-新增生产入库-开始-32");
										methodCtx.getParam(2).setValue("true");
										methodCtx.getParam(4).setValue("新增成功");
										System.out.println("过程-新增生产入库-开始-33");
									}else{
										System.out.println("result-生产入库单-生成失败");
									}
								} else {
									System.out.println("过程-新增生产入库-失败");
									methodCtx.getParam(2).setValue("false");
									methodCtx.getParam(4).setValue(errMsg);
									//throw new BOSException(errMsg);
								}
							}
							/****************  代码逻辑结束 ****************/
						} break;
						case 2: {
							// 走盘盈逻辑
							System.out.println("---->走盘盈逻辑 CCCW11");

						} break;
						case 3: {
							// 走委外生产逻辑
							System.out.println("---->走委外生产逻辑 CCCW08");
						} break;
						case 4: {
							// 走销售退货逻辑
							System.out.println("---->走销售退货逻辑 CCCW14");
						} break;
						default: {
							isSucess = false;
							System.out.println("Error-未找到对应的单据类型，处理终止");
						}
							break;
					}
				} else {
					// 源库区号不为空 且 目标库区号不为空
					if (!"".equals(ykqh) && !"".equals(mbkqh)) {
						// 若正确 走库存调拨逻辑
						System.out.println("---->走库存调拨逻辑");
					} else {
						isSucess = false;
						System.out.println("Error-没有合适的业务逻辑，处理终止");
					}
				}
			} else {
				isSucess = false;
				System.out.println("Error-该卷已入库，处理终止");
			}

		} else {
			isSucess = false;
			System.out.println("Error-未找到该卷号，处理终止");
		}
	} else {
		isSucess = false;
		System.out.println("Error-前置条件不满足，处理终止");
	}


	/******************************************** 逻辑段处理结束 *******************************/
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
	/*
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
	*/
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
	// }else{
	// 	// 电文长度不符合约定
	// 		methodCtx.getParam(2).setValue("false");
	// 		methodCtx.getParam(4).setValue(dwt+errMsg);
	// 		var strMsg="0110"+dwh+"CWCCB";
	// 		var strData=String.format("%-80s", errMsg)+"\n" 
	// 		methodCtx.getParam(3).setValue(strMsg+strData);
	// }
	var strMsg = "0110" + dwh + "CWCC";

	methodCtx.getParam(2).setValue("true");
	var strData = String.format("%-81s", "A") + "\n";
	methodCtx.getParam(3).setValue(strMsg + strData);
	// if (isSucess) {
	// 	methodCtx.getParam(2).setValue("true");
	// 	var strData = String.format("%-81s", "A") + "\n";
	// 	methodCtx.getParam(3).setValue(strMsg + strData);
	// } else {
	// 	methodCtx.getParam(2).setValue("false");
	// 	methodCtx.getParam(4).setValue(dwt + errMsg);
	// 	var strMsg = "0110" + dwh + "CWCCB";
	// 	var strData = String.format("%-80s", errMsg) + "\n"
	// 	methodCtx.getParam(3).setValue(strMsg + strData);
	// }
	System.out.println("/******************************  CCCW12 消息记录结束   **********************************/");
}