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
with(imp) {
    System.out.println("/******************************  CCCW12 消息记录开始   **********************************/");
    var socketMsg = methodCtx.getParamValue(0); //接收报文 
    //socketMsg = '0203CCCW0420190429190857CCCWDlzytest201904301723 C01   2R                   ';
    var billID = methodCtx.getParamValue(1); // 业务单元ID  
    var result = methodCtx.getParamValue(2); //成功失败  true:false  必须有返回值  methodCtx.getParam(2).setValue("true") 
    var sendMsg = methodCtx.getParamValue(3); // 发送报文   如果需要发送电文,将电文拼好放到这里返回  methodCtx.getParam(3).setValue("")
    var errMsg = methodCtx.getParamValue(4); //错误消息 methodCtx.getParam(4).setValue("")  
    var ctx = pluginCtx.getContext(); //服务端上下文     
    // 是否能够生成单据标识
    var isSucess = true;
    // 错误消息
    var errMsg = "";
    System.out.println("/*************  CCCW12 获取接口参数开始   ****************/");
    // 电文号
    var dwh = PublicBaseUtil.substringByte(socketMsg, "GBK", 5, 20); //电文号  从MES获取
    System.out.println("电文号-dwh:" + dwh);
    if (dwh != null) {
        dwh = dwh.trim();
    } else {
        dwh = "";
    }
    if ("".equals(dwh)) {
        isSucess = false;
        System.out.println("Error-电文号不能为空,无法进行,中断");
        errMsg = errMsg + "电文号不能为空\r\n";
    }
    // 电文头
    var dwt = PublicBaseUtil.substringByte(socketMsg, "GBK", 1, 29); //电文头  从MES获取
    System.out.println("电文头-dwt:" + dwt);
    if (dwt != null) {
        dwt = dwt.trim();
    } else {
        dwt = "";
    }
    if ("".equals(dwt)) {
        isSucess = false;
        System.out.println("Error-电文头不能为空,无法进行,中断");
        errMsg = errMsg + "电文头不能为空\r\n";
    }
    // 业务日期
    var ywrq = PublicBaseUtil.substringByte(socketMsg, "GBK", 11, 8);
    // 卷号
    var jh = PublicBaseUtil.substringByte(socketMsg, "GBK", 30, 20); //  从MES获取
    System.out.println("卷号-jh:" + jh);
    if (jh != null) {
        jh = jh.trim();
    } else {
        jh = "";
    }
    if ("".equals(jh)) {
        isSucess = false;
        System.out.println("Error-卷号不能为空,无法进行,中断");
        errMsg = errMsg + "卷号不能为空\r\n";
    }
    //源库区号
    var ykqh = PublicBaseUtil.substringByte(socketMsg, "GBK", 50, 3); //  从MES获取
    System.out.println("源库区号-ykqh:" + ykqh);
    if (ykqh != null) {
        ykqh = ykqh.trim();
    } else {
        ykqh = "";
    }
    // 目标库区号
    var mbkqh = PublicBaseUtil.substringByte(socketMsg, "GBK", 53, 3); //  从MES获取
    System.out.println("目标库区号-mbkqh:" + mbkqh);
    if (mbkqh != null) {
        mbkqh = mbkqh.trim();
    } else {
        mbkqh = "";
    }
    // 库位操作类型
    var kwczlx = PublicBaseUtil.substringByte(socketMsg, "GBK", 56, 20); //  从MES获取
    System.out.println("库位操作类型-kwczlx:" + kwczlx);
    if (kwczlx != null) {
        kwczlx = kwczlx.trim();
    } else {
        isSucess = false;
        System.out.println("Error-操作类型不能为空,中断");
        errMsg = errMsg + "操作类型不能为空\r\n";
    }
    // 业务时间
    var ywDate = PublicBaseUtil.substringByte(socketMsg, "GBK", 11, 8);
    System.out.println("业务时间-ywDate:" + ywDate);
    System.out.println("/*************  CCCW12 获取接口参数结束   ****************/");
    System.out.println("/*************  CCCW12 全局变量定义开始   ****************/");
    // 数量
    var qty = 0;
    // 物料编码
    var wlbm = '';
    // 物料库存状态
    var kczt = '';
    // 从即时库存中根据卷号获取数量
    var getQtySql = "select inv.FCURSTOREQTY FCURSTOREQTY,Material.fnumber MaterialNumber,store.FNUMBER storeNumber from T_IM_Inventory inv left join T_BD_Material Material on Material.fid=inv.FMaterialID left join T_IM_STORESTATE store on inv.FSTORESTATUSID = store.FID  where flot='" + jh + "' and FCURSTOREQTY <>0";
    var qtyList = DbUtil.executeQuery(ctx, getQtySql);
    if (qtyList.next()) {
        qty = qtyList.getBigDecimal("FCURSTOREQTY");
        wlbm = qtyList.getString("MaterialNumber");
        kczt = qtyList.getString('storeNumber');
    }
    System.out.println("Msg-qty:" + qty);
    System.out.println("Msg-wlbm:" + wlbm);
    System.out.println("/*************  CCCW12 全局变量定义结束   ****************/");
    System.out.println("/*************  CCCW12 前置操作开始   ****************/");
    // 设置返回结果
    methodCtx.getParam(2).setValue("false");
    System.out.println("/*************  CCCW12 前置操作结束   ****************/");
    System.out.println("/*************  CCCW12 条件分支开启   ****************/");
    // 生成库存调拨单并自动生成调拨出库单和调拨入库单 通了
    if (!ykqh.equals('') && !mbkqh.equals('') && kwczlx.equals('30')) {
        System.out.println("/******  CCCW12 生成库存调拨单并自动生成调拨出库单和调拨入库单 开始   *******/");
        var strCreatorNo = "user"; //创建人编码
        var strCUNo = "01"; // 管理单元编码
        var strBizDate = ywrq; //"20190417";//业务日期  从MES获取
        var strrecDate = strBizDate; //接收日期 
        var billStatus = BillBaseStatusEnum.ADD; //单据状态
        var strBizTypeNo = "331"; //业务类型编码
        var strBillTypeNo = "140"; //单据类型编码
        var strCurrencyNo = "BB01"; //币别编码
        var bgExchangeRate = BigDecimal.ONE; // 汇率
        var bgTotalQty = BigDecimal.ZERO; // 数量
        var cu = PublicBaseUtil.getCU(ctx, strCUNo);
        if (cu == null) {
            isSucess = false;
            errMsg = errMsg + "管理单元不能为空\r\n";
            System.out.println("Error-管理单元不能为空");
        }
        var bizType = PublicBaseUtil.getBizTypeByNumber(ctx, strBizTypeNo);
        if (bizType == null) {
            isSucess = false;
            errMsg = errMsg + "业务类型不能为空\r\n";
            System.out.println("Error-业务类型不能为空");
        }
        var billType = PublicBaseUtil.getBillTypeByNumber(ctx, strBillTypeNo);
        if (billType == null) {
            isSucess = false;
            errMsg = errMsg + "单据类型不能为空\r\n";
            System.out.println("Error-单据类型不能为空");
        }
        //库存组织
        var storeInfo = PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strCUNo);
        if (storeInfo == null) {
            isSucess = false;
            errMsg = errMsg + "库存组织不能为空\r\n";
            System.out.println("Error-库存组织不能为空");
        }
        //币别
        var currency = PublicBaseUtil.getCurrencyInfo(ctx, strCurrencyNo);
        if (currency == null) {
            isSucess = false;
            errMsg = errMsg + "币别不能为空\r\n";
            System.out.println("Error-币别不能为空");
        }
        // 财务组织
        var company = PublicBaseUtil.getCompany(ctx, cu.getNumber());
        if (company == null) {
            isSucess = false;
            errMsg = errMsg + "财务组织不能为空\r\n";
            System.out.println("Error-财务组织不能为空");
        }
        var strMaterialNo = wlbm;
        var strUnit = "吨";
        var strStockNo = ykqh //"001";//调出仓库编码  从MES获取
        var recstrStockNo = mbkqh //"003";//调入仓库编码  从MES获取
        var bgQty = qty; //new BigDecimal("0");
        var rjh = jh //"201904168";//批次号  从MES获取
        rjh = rjh.trim();
        if ("".equals(rjh)) {
            isSucess = false;
            errMsg = errMsg + "卷号不能为空\r\n";
            System.out.println("Error-卷号不能为空");
        }
        var strLot = rjh;
        // var strSQLInv = "select inv.FCURSTOREQTY FCURSTOREQTY,Material.fnumber MaterialNumber from T_IM_Inventory inv left join T_BD_Material Material on Material.fid=inv.FMaterialID where flot='" + strLot + "'";
        // System.out.println("销售出库接口" + strSQLInv);
        // var rsInv = DbUtil.executeQuery(ctx, strSQLInv);
        // if (rsInv.next()) {
        //     bgQty = rsInv.getBigDecimal("FCURSTOREQTY");
        //     strMaterialNo = rsInv.getString("MaterialNumber");
        // }
        var material = PublicBaseUtil.getMaterialInfoByNumber(ctx, cu.getId().toString(), strMaterialNo);
        if (material == null) {
            isSucess = false;
            errMsg = errMsg + "物料不存在\r\n";
            System.out.println("Error-物料不存在,处理停止");
        }
        var storOrgUnit = PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
        if (storOrgUnit == null) {
            isSucess = false;
            errMsg = errMsg + "库存组织不能为空\r\n";
            System.out.println("Error-库存组织不能为空,处理停止");
        }
        var stock = PublicBaseUtil.getWarehouseInfoByNumber(ctx, strStockNo);
        if (stock == null) {
            isSucess = false;
            errMsg = errMsg + "调出仓库不能为空\r\n";
            System.out.println("Error-调出仓库不能为空,处理停止");
        }
        var recstock = PublicBaseUtil.getWarehouseInfoByNumber(ctx, recstrStockNo);
        if (recstock == null) {
            isSucess = false;
            errMsg = errMsg + "调入仓库不能为空\r\n";
            System.out.println("Error-调入仓库不能为空,处理停止");
        }
        if (isSucess) {
            //新增库存调拨
            var info = new StockTransferBillInfo();
            info.put("bxNumber", "库区变更电文CCCW12");
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
                System.out.println("Error-管理单元不能为空,处理停止");
            }
            info.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
            var bizDate;
            var sdf = new SimpleDateFormat("yyyyMMdd");
            bizDate = sdf.parse(strBizDate);
            info.setBizDate(bizDate);
            var c = Calendar.getInstance();
            c.setTime(bizDate);
            var year = c.get(Calendar.YEAR); //年度
            var period = c.get(Calendar.MONTH) + 1;
            var month = c.get(Calendar.YEAR) * 100 + c.get(Calendar.MONTH) + 1; //期间
            var iday = (c.get(Calendar.YEAR) * 100 + c.get(Calendar.MONTH) + 1) * 100 + c.get(Calendar.MONDAY);
            info.setYear(year);
            info.setPeriod(period);
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
            //库存组织
            if (storeInfo != null) {
                info.setIssueStorageOrgUnit(storeInfo);
                info.setReceiptStorageOrgUnit(storeInfo);
            }
            //币别
            if (currency != null) {
                info.setCurrency(currency);
                info.setExchangeRate(bgExchangeRate);
            }
            // 财务组织
            if (company != null) {
                info.setReceiptCompanyOrgUnit(company);
                info.setIssueCompanyOrgUnit(company);
            }
            info.setIsShipment(true); //是否发运
            info.setIsShipment(false); //是否含税
            info.setIsInTax(false); //是否初始化单
            //新增分录实体
            var entry = new StockTransferBillEntryInfo();
            //分录行号
            entry.setSeq(1);
            if (material != null) {
                entry.setMaterial(material);
                entry.setBaseUnit(material.getBaseUnit());
                //单位
                var unit = PublicBaseUtil.getMeasureUnitInfoByName(ctx, material.getBaseUnit().getMeasureUnitGroup().getNumber(), strUnit);
                if (unit != null) {
                    entry.setUnit(unit);
                    entry.setBaseUnit(unit);
                } else {
                    isSucess = false;
                    errMsg = errMsg + "单位不能为空\r\n";
                }
            }

            entry.setBaseStatus(EntryBaseStatusEnum.ADD); //
            entry.setAssCoefficient(BigDecimal.ZERO);
            //库存组织
            if (storOrgUnit != null) {
                entry.setIssueStorageOrgUnit(storOrgUnit);
                entry.setReceiveStorageOrgUnit(storOrgUnit);

            }

            //财务组织
            entry.setIssueCompanyOrgUnit(company);
            entry.setReceiveCompanyOrgUnit(company);
            //调出仓库
            if (stock != null) {
                entry.setIssueWarehouse(stock);
            }
            if (recstock != null) {
                entry.setReceiptWarehouse(recstock);
            }
            entry.setQty(bgQty);
            entry.setBaseQty(bgQty);
            entry.setUnIssueBaseQty(bgQty);
            entry.setLot(strLot);
            entry.setBizDate(bizDate);
            var recDate = sdf.parse(strrecDate);
            entry.setReceiptPlanDate(recDate);
            entry.setIssuePlanDate(recDate);
            entry.setApAssociateBaseQty(bgQty);
            entry.setArAssociateBaseQty(bgQty);
            var storeTypeInfo = new com.kingdee.eas.basedata.scm.im.inv.StoreTypeInfo();
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

            if (storeTypeCollection != null &&
                storeTypeCollection.size() != 0) {
                storeTypeInfo = storeTypeCollection.get(0);
                entry.setStoreType(storeTypeInfo);
            }

            var storeStateInfo = new com.kingdee.eas.basedata.scm.im.inv.StoreStateInfo();
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

            if (storeStateCollection != null &&
                storeStateCollection.size() != 0) {
                storeStateInfo = storeStateCollection.get(0);
                entry.setStoreState(storeStateInfo);
            }
            var Entrys = new StockTransferBillEntryCollection();
            Entrys.add(entry);
            info.getEntries().addCollection(Entrys);
        }
        if (isSucess) {
            var pk = StockTransferBillFactory.getLocalInstance(ctx).save(info); //保存
            System.out.println("Msg-pk:" + pk.toString());
            if (pk != null) {
                var tempIno = StockTransferBillFactory.getLocalInstance(ctx).getStockTransferBillInfo(pk);
                var infoid = tempIno.getId().toString();
                if (tempIno.getBaseStatus().equals(BillBaseStatusEnum.TEMPORARILYSAVED)) {
                    StockTransferBillFactory.getLocalInstance(ctx).submit(tempIno);
                }
                tempIno = StockTransferBillFactory.getLocalInstance(ctx).getStockTransferBillInfo(pk);
                if (tempIno.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
                    StockTransferBillFactory.getLocalInstance(ctx).audit(pk);
                }
                var destType = BOSObjectType.create("71D272F1");
                var sourceType = BOSObjectType.create("2239F30A");
                var botpId = BOSUuid.read("709becee-0108-1000-e000-2b10c0a812fd045122C4");

                var view = new EntityViewInfo();
                var sic = view.getSelector();
                sic.add("*");

                sic.add("entries.*");

                var filter = new FilterInfo();
                var fic = filter.getFilterItems();
                fic.add(new FilterItemInfo("id", infoid));
                System.out.println("调拨出库接口开始" + infoid);
                view.setFilter(filter);
                var sourceColl = StockTransferBillFactory.getLocalInstance(ctx).getStockTransferBillCollection(view);
                var newcoll = sourceColl.cast(new CoreBillBaseCollection().getClass());
                System.out.println("调拨出库接口:" + newcoll.get(0).get("number"));
                var destinfo = PublicBaseUtil.botp(ctx, sourceType, destType, newcoll, botpId);
                System.out.println("调拨出库接口" + destinfo.get(0).get("number"));
                var id = destinfo.get(0).get("id");
                if (id != null) {

                    var moveoutinfo = MoveIssueBillFactory.getLocalInstance(ctx).getMoveIssueBillInfo(new ObjectUuidPK(id));
                    System.out.println("调拨出库接口");

                    var sdfDate = new SimpleDateFormat("yyyyMMdd");
                    var bizdate = sdfDate.parse(strBizDate);
                    moveoutinfo.setBizDate(bizdate);

                    var sic2 = new SelectorItemCollection();
                    sic2.add("id");
                    sic2.add("bizDate");
                    MoveIssueBillFactory.getLocalInstance(ctx).updatePartial(moveoutinfo, sic2);
                    moveoutinfo = MoveIssueBillFactory.getLocalInstance(ctx).getMoveIssueBillInfo(new ObjectUuidPK(id));

                    if (moveoutinfo != null) {
                        if (moveoutinfo.getBaseStatus().equals(com.kingdee.eas.scm.common.BillBaseStatusEnum.TEMPORARILYSAVED)) {
                            MoveIssueBillFactory.getLocalInstance(ctx).submit(moveoutinfo);
                        }
                        moveoutinfo = MoveIssueBillFactory.getLocalInstance(ctx).getMoveIssueBillInfo(new ObjectUuidPK(id));
                        if (moveoutinfo.getBaseStatus().equals(com.kingdee.eas.scm.common.BillBaseStatusEnum.SUBMITED)) {
                            MoveIssueBillFactory.getLocalInstance(ctx).audit(new ObjectUuidPK(id));
                        }

                    }
                }
                destType = BOSObjectType.create("E3DAFF63");
                sourceType = BOSObjectType.create("2239F30A");
                botpId = BOSUuid.read("709becee-0108-1000-e000-2980c0a812fd045122C4");

                view = new EntityViewInfo();
                sic = view.getSelector();
                sic.add("*");

                sic.add("entries.*");

                filter = new FilterInfo();
                fic = filter.getFilterItems();
                fic.add(new FilterItemInfo("id", infoid));
                System.out.println("调拨入库接口开始" + infoid);
                view.setFilter(filter);
                sourceColl = StockTransferBillFactory.getLocalInstance(ctx).getStockTransferBillCollection(view);
                newcoll = sourceColl.cast(new CoreBillBaseCollection().getClass());
                System.out.println("调拨入库接口:" + newcoll.get(0).get("number"));
                destinfo = PublicBaseUtil.botp(ctx, sourceType, destType, newcoll, botpId);
                System.out.println("调拨入库接口" + destinfo.get(0).get("number"));
                id = destinfo.get(0).get("id");
                if (id != null) {

                    var moveininfo = MoveInWarehsBillFactory.getLocalInstance(ctx).getMoveInWarehsBillInfo(new ObjectUuidPK(id));
                    System.out.println("调拨出库接口");

                    var sdfDate = new SimpleDateFormat("yyyyMMdd");
                    var bizdate = sdfDate.parse(strBizDate);
                    moveininfo.setBizDate(bizdate);

                    var sic2 = new SelectorItemCollection();
                    sic2.add("id");
                    sic2.add("bizDate");
                    MoveInWarehsBillFactory.getLocalInstance(ctx).updatePartial(moveininfo, sic2);
                    moveininfo = MoveInWarehsBillFactory.getLocalInstance(ctx).getMoveInWarehsBillInfo(new ObjectUuidPK(id));

                    if (moveininfo != null) {
                        if (moveininfo.getBaseStatus().equals(com.kingdee.eas.scm.common.BillBaseStatusEnum.TEMPORARILYSAVED)) {
                            MoveInWarehsBillFactory.getLocalInstance(ctx).submit(moveininfo);
                        }
                        moveininfo = MoveInWarehsBillFactory.getLocalInstance(ctx).getMoveInWarehsBillInfo(new ObjectUuidPK(id));
                        if (moveininfo.getBaseStatus().equals(com.kingdee.eas.scm.common.BillBaseStatusEnum.SUBMITED)) {
                            MoveIssueBillFactory.getLocalInstance(ctx).audit(new ObjectUuidPK(id));
                        }

                    }
                }
                methodCtx.getParam(2).setValue("true");
                methodCtx.getParam(4).setValue("新增成功");

            }
        } else {
            methodCtx.getParam(2).setValue("false");
            methodCtx.getParam(4).setValue(errMsg);
        }
        System.out.println("/******  CCCW12 生成库存调拨单并自动生成调拨出库单和调拨入库单 结束  *******/");
    }
    // 生成库存调拨单并自动生成调拨出库单 通了
    else if (!ykqh.equals('')  && kwczlx.equals('2G')) {
        System.out.println("/******  CCCW12 生成库存调拨单并自动生成调拨出库单 开始   *******/");
        var strCreatorNo = "user"; //创建人编码
        var strCUNo = "01"; // 管理单元编码
        var strBizDate = ywrq; //"20190417";//业务日期  从MES获取
        var strrecDate = strBizDate; //接收日期 
        var billStatus = BillBaseStatusEnum.ADD; //单据状态
        var strBizTypeNo = "331"; //业务类型编码
        var strBillTypeNo = "140"; //单据类型编码
        var strCurrencyNo = "BB01"; //币别编码

        var bgExchangeRate = BigDecimal.ONE; // 汇率
        var bgTotalQty = BigDecimal.ZERO; // 数量
        var cu = PublicBaseUtil.getCU(ctx, strCUNo);
        if (cu == null) {
            isSucess = false;
            errMsg = errMsg + "管理单元不能为空\r\n";
            System.out.println("Error-管理单元不能为空,处理停止");
        }
        var bizType = PublicBaseUtil.getBizTypeByNumber(ctx, strBizTypeNo);
        if (bizType == null) {
            isSucess = false;
            errMsg = errMsg + "业务类型不能为空\r\n";
            System.out.println("Error-业务类型不能为空,处理停止");
        }
        var billType = PublicBaseUtil.getBillTypeByNumber(ctx, strBillTypeNo);
        if (billType == null) {
            isSucess = false;
            errMsg = errMsg + "单据类型不能为空\r\n";
            System.out.println("Error-单据类型不能为空,处理停止");
        }
        //库存组织
        var storeInfo = PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strCUNo);
        if (storeInfo == null) {
            isSucess = false;
            errMsg = errMsg + "库存组织不能为空\r\n";
            System.out.println("Error-库存组织不能为空,处理停止");
        }
        //币别
        var currency = PublicBaseUtil.getCurrencyInfo(ctx, strCurrencyNo);
        if (currency == null) {
            isSucess = false;
            errMsg = errMsg + "币别不能为空\r\n";
            System.out.println("Error-币别不能为空,处理停止");
        }
        // 财务组织
        var company = PublicBaseUtil.getCompany(ctx, cu.getNumber());
        if (company == null) {
            isSucess = false;
            errMsg = errMsg + "财务组织不能为空\r\n";
            System.out.println("Error-财务组织不能为空,处理停止");
        }
        var strMaterialNo = "";
        var strUnit = "吨";

        var strStockNo = ykqh //"001";//调出仓库编码  从MES获取
        var recstrStockNo = "99"; //调入仓库编码  从MES获取 // 
        var bgQty = qty; //new BigDecimal("0");
        var rjh = jh; //"201904168";//批次号  从MES获取
        rjh = rjh.trim();
        if ("".equals(rjh)) {
            isSucess = false;
            errMsg = errMsg + "卷号不能为空\r\n";
            System.out.println("Error-卷号不能为空,处理停止");
        }
        var strLot = rjh;
        var strSQLInv = "select inv.FCURSTOREQTY FCURSTOREQTY,Material.fnumber MaterialNumber from T_IM_Inventory inv left join T_BD_Material Material on Material.fid=inv.FMaterialID where flot='" + strLot + "'";
        System.out.println("销售出库接口" + strSQLInv);
        var rsInv = DbUtil.executeQuery(ctx, strSQLInv);
        if (rsInv.next()) {
            bgQty = rsInv.getBigDecimal("FCURSTOREQTY");
            strMaterialNo = rsInv.getString("MaterialNumber");
        }
        var material = PublicBaseUtil.getMaterialInfoByNumber(ctx, cu.getId().toString(), strMaterialNo);
        if (material == null) {
            isSucess = false;
            errMsg = errMsg + "物料不存在\r\n";
            System.out.println("Error-物料不存在,处理停止");
        } else {

        }
        var storOrgUnit = PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
        if (storOrgUnit == null) {
            isSucess = false;
            errMsg = errMsg + "库存组织不能为空\r\n";
            System.out.println("Error-库存组织不能为空,处理停止");
        } else {

        }
        var stock = PublicBaseUtil.getWarehouseInfoByNumber(ctx, strStockNo);
        if (stock == null) {
            isSucess = false;
            errMsg = errMsg + "调出仓库不能为空\r\n";
            System.out.println("Error-调出仓库不能为空,处理停止");
        } else {

        }
        var recstock = PublicBaseUtil.getWarehouseInfoByNumber(ctx, recstrStockNo);
        if (recstock == null) {
            isSucess = false;
            errMsg = errMsg + "调入仓库不能为空\r\n";
        }
        if (isSucess) {
            //新增库存调拨
            var info = new StockTransferBillInfo();
            info.put("bxNumber", "库区变更电文CCCW12");
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
            info.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
            var bizDate;
            var sdf = new SimpleDateFormat("yyyyMMdd");
            bizDate = sdf.parse(strBizDate);
            info.setBizDate(bizDate);
            var c = Calendar.getInstance();
            c.setTime(bizDate);
            var year = c.get(Calendar.YEAR); //年度
            var period = c.get(Calendar.MONTH) + 1;
            var month = c.get(Calendar.YEAR) * 100 + c.get(Calendar.MONTH) + 1; //期间
            var iday = (c.get(Calendar.YEAR) * 100 + c.get(Calendar.MONTH) + 1) * 100 + c.get(Calendar.MONDAY);
            info.setYear(year);
            info.setPeriod(period);
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
            //库存组织
            if (storeInfo != null) {
                info.setIssueStorageOrgUnit(storeInfo);
                info.setReceiptStorageOrgUnit(storeInfo);
            }
            //币别
            if (currency != null) {
                info.setCurrency(currency);
                info.setExchangeRate(bgExchangeRate);
            }
            // 财务组织
            if (company != null) {
                info.setReceiptCompanyOrgUnit(company);
                info.setIssueCompanyOrgUnit(company);
            }
            info.setIsShipment(true); //是否发运
            info.setIsShipment(false); //是否含税
            info.setIsInTax(false); //是否初始化单

            //新增分录实体
            var entry = new StockTransferBillEntryInfo();
            //分录行号
            entry.setSeq(1);

            if (material != null) {
                entry.setMaterial(material);
                entry.setBaseUnit(material.getBaseUnit());
                //单位
                var unit = PublicBaseUtil.getMeasureUnitInfoByName(ctx, material.getBaseUnit().getMeasureUnitGroup().getNumber(), strUnit);
                if (unit != null) {
                    entry.setUnit(unit);
                    entry.setBaseUnit(unit);
                } else {
                    isSucess = false;
                    errMsg = errMsg + "单位不能为空\r\n";
                }
            }

            entry.setBaseStatus(EntryBaseStatusEnum.ADD); //
            entry.setAssCoefficient(BigDecimal.ZERO);
            //库存组织
            if (storOrgUnit != null) {
                entry.setIssueStorageOrgUnit(storOrgUnit);
                entry.setReceiveStorageOrgUnit(storOrgUnit);

            }

            //财务组织
            entry.setIssueCompanyOrgUnit(company);
            entry.setReceiveCompanyOrgUnit(company);
            //调出仓库
            if (stock != null) {
                entry.setIssueWarehouse(stock);
            }
            if (recstock != null) {
                entry.setReceiptWarehouse(recstock);
            }
            entry.setQty(bgQty);
            entry.setBaseQty(bgQty);
            entry.setUnIssueBaseQty(bgQty);
            entry.setLot(strLot);
            entry.setBizDate(bizDate);
            var recDate = sdf.parse(strrecDate);
            entry.setReceiptPlanDate(recDate);
            entry.setIssuePlanDate(recDate);
            entry.setApAssociateBaseQty(bgQty);
            entry.setArAssociateBaseQty(bgQty);
            var storeTypeInfo = new com.kingdee.eas.basedata.scm.im.inv.StoreTypeInfo();
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

            if (storeTypeCollection != null &&
                storeTypeCollection.size() != 0) {
                storeTypeInfo = storeTypeCollection.get(0);
                entry.setStoreType(storeTypeInfo);
            }

            var storeStateInfo = new com.kingdee.eas.basedata.scm.im.inv.StoreStateInfo();
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

            if (storeStateCollection != null &&
                storeStateCollection.size() != 0) {
                storeStateInfo = storeStateCollection.get(0);
                entry.setStoreState(storeStateInfo);
            }
            var Entrys = new StockTransferBillEntryCollection();
            Entrys.add(entry);
            info.getEntries().addCollection(Entrys);
        }
        if (isSucess) {
            var pk = StockTransferBillFactory.getLocalInstance(ctx).save(info); //保存
            if (pk != null) {
                var tempIno = StockTransferBillFactory.getLocalInstance(ctx).getStockTransferBillInfo(pk);
                var infoid = tempIno.getId().toString();
                if (tempIno.getBaseStatus().equals(BillBaseStatusEnum.TEMPORARILYSAVED)) {
                    StockTransferBillFactory.getLocalInstance(ctx).submit(tempIno);
                }
                tempIno = StockTransferBillFactory.getLocalInstance(ctx).getStockTransferBillInfo(pk);
                if (tempIno.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
                    StockTransferBillFactory.getLocalInstance(ctx).audit(pk);
                }
                var destType = BOSObjectType.create("71D272F1");
                var sourceType = BOSObjectType.create("2239F30A");
                var botpId = BOSUuid.read("709becee-0108-1000-e000-2b10c0a812fd045122C4");

                var view = new EntityViewInfo();
                var sic = view.getSelector();
                sic.add("*");

                sic.add("entries.*");

                var filter = new FilterInfo();
                var fic = filter.getFilterItems();
                fic.add(new FilterItemInfo("id", infoid));
                System.out.println("调拨出库接口开始" + infoid);
                view.setFilter(filter);
                var sourceColl = StockTransferBillFactory.getLocalInstance(ctx).getStockTransferBillCollection(view);
                var newcoll = sourceColl.cast(new CoreBillBaseCollection().getClass());
                System.out.println("调拨出库接口:" + newcoll.get(0).get("number"));
                var destinfo = PublicBaseUtil.botp(ctx, sourceType, destType, newcoll, botpId);
                System.out.println("调拨出库接口" + destinfo.get(0).get("number"));
                var id = destinfo.get(0).get("id");
                if (id != null) {

                    var moveoutinfo = MoveIssueBillFactory.getLocalInstance(ctx).getMoveIssueBillInfo(new ObjectUuidPK(id));
                    System.out.println("调拨出库接口");

                    var sdfDate = new SimpleDateFormat("yyyyMMdd");
                    var bizdate = sdfDate.parse(strBizDate);
                    moveoutinfo.setBizDate(bizdate);

                    var sic2 = new SelectorItemCollection();
                    sic2.add("id");
                    sic2.add("bizDate");
                    MoveIssueBillFactory.getLocalInstance(ctx).updatePartial(moveoutinfo, sic2);
                    moveoutinfo = MoveIssueBillFactory.getLocalInstance(ctx).getMoveIssueBillInfo(new ObjectUuidPK(id));

                    if (moveoutinfo != null) {
                        if (moveoutinfo.getBaseStatus().equals(com.kingdee.eas.scm.common.BillBaseStatusEnum.TEMPORARILYSAVED)) {
                            MoveIssueBillFactory.getLocalInstance(ctx).submit(moveoutinfo);
                        } else {
                            // isSucess = false;
                        }
                        moveoutinfo = MoveIssueBillFactory.getLocalInstance(ctx).getMoveIssueBillInfo(new ObjectUuidPK(id));
                        if (moveoutinfo.getBaseStatus().equals(com.kingdee.eas.scm.common.BillBaseStatusEnum.SUBMITED)) {
                            MoveIssueBillFactory.getLocalInstance(ctx).audit(new ObjectUuidPK(id));
                        } else {
                            // isSucess = false;
                        }

                    } else {

                    }
                } else {

                }
                methodCtx.getParam(2).setValue("true");
                methodCtx.getParam(4).setValue("新增成功");

            } else {

            }
        } else {
            methodCtx.getParam(2).setValue("false");
            methodCtx.getParam(4).setValue(errMsg);
        }
        System.out.println("/******  CCCW12 生成库存调拨单并自动生成调拨出库单 结束  *******/");
    }
    // 将该卷号做其他出库单出库 事务类型为030 通了
    else if (!ykqh.equals('') && kwczlx.equals('2R')) {
        System.out.println("/******  CCCW12  将该卷号做其他出库单出库 事务类型为30 开始 *******/");
        if ('304'.equals(kczt)) {

            var strBizDate = ywrq; //PublicBaseUtil.substringByte(socketMsg,"GBK" ,11,8) ; //业务日期  从MES获取
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
            var strLot = jh; //PublicBaseUtil.substringByte(socketMsg, "GBK", 30, 20);//批次号  从MES获取
            var bgQty = qty; //new BigDecimal("0");

            // errMsg="";
            strCreatorNo = "user"; //创建人编码
            strCUNo = "01"; // 管理单元编码
            //strBizDate="20190418" ; //业务日期  从MES获取
            billStatus = BillBaseStatusEnum.ADD; //单据状态
            purchaseType = PurchaseTypeEnum.PURCHASE;
            strBizTypeNo = "510"; //业务类型编码
            strBillTypeNo = "108"; //单据类型编码
            strTranstionType = "030"; //事务类型
            strNeedStockOrgNo = "01"; //需方库存组织
            isInTax = true;
            sdfDate = new SimpleDateFormat("yyyyMMdd");
            if (strLot != null) {
                strLot = strLot.trim();
            }
            // var zl=PublicBaseUtil.substringByte(socketMsg,"GBK" ,155,10);//重量  从MES获取
            // if(zl!=null){
            // 	zl=zl.trim();
            // }else
            // {
            // 	zl="0";
            // }
            // try
            // {
            // 	bgQty=new BigDecimal(zl).divide(new BigDecimal("1000"));
            // }catch(e){

            // }
            // if (bgQty.compareTo(BigDecimal.ZERO)==0){
            // 	isSucess=false;
            // 	errMsg=errMsg + "重量不能为零\r\n";
            // }
            // var strSQLInv = "select * from T_IM_Inventory where flot='"+strLot+"'";
            // System.out.println("销售出库接口"+strSQLInv);
            // var rsInv = DbUtil.executeQuery(ctx, strSQLInv);
            // if (rsInv.next()) {
            // 	var curStoreQty=rsInv.getBigDecimal("FCURSTOREQTY");
            // 	if(bgQty.compareTo(curStoreQty)!=0){
            // 		isSucess=false;
            // 		errMsg=errMsg + "与库存重量不一致\r\n";
            // 	}
            // }
            var cu = PublicBaseUtil.getCU(ctx, strCUNo);
            if (cu == null) {
                isSucess = false;
                errMsg = errMsg + "管理单元不能为空\r\n";
            }
            System.out.println("isSucess3" + isSucess);
            // 业务类型
            var bizType = PublicBaseUtil.getBizTypeByNumber(ctx, strBizTypeNo);
            if (bizType == null) {
                isSucess = false;
                errMsg = errMsg + "业务类型不能为空\r\n";
            }
            System.out.println("isSucess4" + isSucess);
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
            var storageInfo = PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strCUNo);
            if (storageInfo == null) {
                isSucess = false;
                errMsg = errMsg + "主业务组织不能为空\r\n";
            }
            var strMaterialNo = "";
            var strUnit = "吨"; //单位默认吨

            //var sqlLot = "SELECT * FROM T_IM_Inventory where FLot ='"+strLot+"'";
            //System.out.println("sqlLot"+sqlLot);
            var strStockNo = ykqh;
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


            //var strStockNo="003";//仓库编码  从MES获取
            System.out.println("仓库编码" + strStockNo);
            if (strStockNo == null) {
                isSucess = false;
                errMsg = errMsg + "仓库编码不能为空\r\n";
            }

            var bgPrice = new BigDecimal("10");
            var bgTaxRate = new BigDecimal("0");
            var bgAmount = new BigDecimal("1000");

            var bgTaxPrice = bgPrice.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
            var bgTaxAmount = bgAmount.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
            var bgTax = bgAmount.multiply(bgTaxRate).setScale(2, BigDecimal.ROUND_HALF_UP);
            var bgKd = new BigDecimal("0");
            var bgHd = new BigDecimal("0");
            /*
            var kd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,150,5);//宽度  从MES获取
            if(kd!=null){
                kd=kd.trim();
            }else
            {
                kd="0";
            }
            try
            {
                bgKd=new BigDecimal(kd).divide(new BigDecimal("10"));;
                //bgKd=new BigDecimal("1200");
            }catch(e){
            	
            }
            var hd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,144,6);//厚度  从MES获取
            if(hd!=null){
                hd=hd.trim();
            }else
            {
                hd="0";
            }
            try
            {
                bgHd=new BigDecimal(hd).divide(new BigDecimal("1000"));
                //bgHd=new BigDecimal("0.80");
            }catch(e){
            	
            }
            if (bgKd.compareTo(BigDecimal.ZERO)==0){
                isSucess=false;
                errMsg=errMsg + "宽度不能为零\r\n";
            }
            if (bgHd.compareTo(BigDecimal.ZERO)==0){
                isSucess=false;
                errMsg=errMsg + "厚度不能为零\r\n";
            }
            System.out.println("重量"+zl);
            System.out.println("厚度"+hd);
            System.out.println("宽度"+kd);
            */
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
            /*
            var phmc=PublicBaseUtil.substringByte(socketMsg,"GBK",94,50); //牌号名称  从MES获取
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
            }else{
                ph=phmc;
            }
            System.out.println("牌号名称"+phmc);
            System.out.println("牌号"+ph);
            */
            var strUnit = "吨"; //单位  默认吨
            //var materialGroupNum="06";//物料组别  从MES获取
            /*
            var materialGroupNum = PublicBaseUtil.substringByte(socketMsg,"GBK" ,90,4);
                materialGroupNum = materialGroupNum.trim();
            if("L101".equals(materialGroupNum)){
                materialGroupNum="09";
            }else if("L701".equals(materialGroupNum)){
                materialGroupNum="19";
            }
            System.out.println("物料组别"+materialGroupNum);
            */
            // var material= SocketFacadeFactory.getLocalInstance(ctx).getMaterialInfo("01", "5", materialGroupNum, ph, phmc, bgKd, bgHd, strUnit, "", true);
            // 物料编码
            var strMaterialNo = wlbm;
            // var strSQLInv = "SELECT FNUMBER  FROM T_BD_Material where fid = '" + wlid + "'";
            // System.out.println("获取物料编码SQL" + strSQLInv);
            // var rsInv = DbUtil.executeQuery(ctx, strSQLInv);
            // System.out.println("1");
            // if (rsInv.next()) {
            //     // bgQty=rsInv.getBigDecimal("FCURSTOREQTY");
            //     // // 临时测试
            //     // bgQty = 1;
            //     //临时代码结束
            //     strMaterialNo = rsInv.getString("FNUMBER");
            // }
            var material = PublicBaseUtil.getMaterialInfoByNumber(ctx, cu.getId().toString(), strMaterialNo);
            if (material == null) {
                isSucess = false;
                errMsg = errMsg + "物料不能为空\r\n";
            }
            var storOrgUnit = PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
            if (storOrgUnit == null) {
                isSucess = false;
                errMsg = errMsg + "库存组织不能为空\r\n";
            }
            var stock = PublicBaseUtil.getWarehouseInfoByNumber(ctx, strStockNo);
            if (stock == null) {
                isSucess = false;
                errMsg = errMsg + "仓库不能为空\r\n";
            }
            var invUpType = PublicBaseUtil.getInvUpdateTypeInfoByNumber(ctx, "008");
            if (invUpType == null) {
                isSucess = false;
                errMsg = errMsg + "更新类型不能为空\r\n";
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
                //事务类型 
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

                //InvUpdateTypeInfo invInfo = InvUpdateTypeFactory.getLocalInstance(ctx).getInvUpdateTypeInfo(new ObjectUuidPK("8r0AAAAEaOnC73rf"));

                entry.setInvUpdateType(invUpType);
                info.getEntries().addObject(entry);
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

                }
            } else {
                methodCtx.getParam(2).setValue("false");
                methodCtx.getParam(4).setValue(errMsg);
            }
        } else {
            isSucess = false;
            errMsg = errMsg + "库存类型不是报废冻结状态，无法进行报废出库\r\n";
            System.out.println("END_MSG-库存类型不是报废冻结状态，无法进行报废出库");
        }
        System.out.println("/******  CCCW12  将该卷号做其他出库单出库 事务类型为30 结束 *******/");
    }
    // 根据操作类型为1G生成的库存调拨单关联生成调拨入库单 通了
    else if ( !mbkqh.equals('') && kwczlx.equals('1G')) {
        System.out.println("/******  CCCW12  根据操作类型为1G生成的调拨出库单关联生成调拨入库单  *******/");
        // 根据卷号，和单据状态为保存状态 获取 库存调拨单，单据头id
        var infoid = '';
        var getKcdbdIdFormJHSql = "select a.FID  infoid from T_IM_StockTransferBill a left join T_IM_StockTransferBillEntry b on a.FID  = b.FPARENTID where b.FLOT ='" + jh + "' and a.FBASESTATUS = 4";
        var KcdboIdList = DbUtil.executeQuery(ctx, getKcdbdIdFormJHSql);
        if (KcdboIdList.next()) {
            infoid = KcdboIdList.getString("infoid");
        }
        // 根据这个id获取库存调拨单实体
        // var tempIno=StockTransferBillFactory.getLocalInstance(ctx).getStockTransferBillInfo(infoid);
        // 判断这个实体的单据状态是否是审核，若是审核则反审核
        // 根据仓库编码获取仓库id
        var sotckId = '';
        var getStockIdSql = "SELECT FID id FROM T_DB_WAREHOUSE where FNUMBER ='" + mbkqh + "' and FWhState=1 and FTransState = 1";
        var getStockIdList = DbUtil.executeQuery(ctx, getStockIdSql);
        if (getStockIdList.next()) {
            sotckId = getStockIdList.getString("id");
        }
        // 根据获取得到的仓库据id更新库存调拨单的目标库
        var updateMbqkSql = "update T_IM_StockTransferBillEntry set FRECEIPTWAREHOUSEID = '" + sotckId + "' where FPARENTID='" + infoid + "'";
        try {
            DbUtil.executeQuery(ctx, updateMbqkSql);
        } catch (error) {
            System.out.println("Error-根据获取得到的仓库据id更新库存调拨单的目标库-执行失败");
        }

        var strBizDate = ywDate; //PublicBaseUtil.substringByte(socketMsg, "GBK", 11, 8); //业务日期  从MES获取
        if (strBizDate == null) {
            isSucess = false;
            errMsg = errMsg + "业务日期不能为空\r\n";
            System.out.println("Error-业务日期不能为空-strBizDate");
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
        destType = BOSObjectType.create("E3DAFF63");
        sourceType = BOSObjectType.create("2239F30A");
        botpId = BOSUuid.read("709becee-0108-1000-e000-2980c0a812fd045122C4");

        view = new EntityViewInfo();
        sic = view.getSelector();
        sic.add("*");

        sic.add("entries.*");

        filter = new FilterInfo();
        fic = filter.getFilterItems();
        fic.add(new FilterItemInfo("id", infoid));
        System.out.println("调拨入库接口开始" + infoid);
        view.setFilter(filter);
        sourceColl = StockTransferBillFactory.getLocalInstance(ctx).getStockTransferBillCollection(view);
        newcoll = sourceColl.cast(new CoreBillBaseCollection().getClass());
        System.out.println("调拨入库接口:" + newcoll.get(0).get("number"));
        destinfo = PublicBaseUtil.botp(ctx, sourceType, destType, newcoll, botpId);
        System.out.println("调拨入库接口" + destinfo.get(0).get("number"));
        id = destinfo.get(0).get("id");
        if (id != null) {

            var moveininfo = MoveInWarehsBillFactory.getLocalInstance(ctx).getMoveInWarehsBillInfo(new ObjectUuidPK(id));
            System.out.println("调拨出库接口");

            var sdfDate = new SimpleDateFormat("yyyyMMdd");
            var bizdate = sdfDate.parse(strBizDate);
            moveininfo.setBizDate(bizdate);

            var sic2 = new SelectorItemCollection();
            sic2.add("id");
            sic2.add("bizDate");
            MoveInWarehsBillFactory.getLocalInstance(ctx).updatePartial(moveininfo, sic2);
            moveininfo = MoveInWarehsBillFactory.getLocalInstance(ctx).getMoveInWarehsBillInfo(new ObjectUuidPK(id));

            if (moveininfo != null) {
                if (moveininfo.getBaseStatus().equals(com.kingdee.eas.scm.common.BillBaseStatusEnum.TEMPORARILYSAVED)) {
                    MoveInWarehsBillFactory.getLocalInstance(ctx).submit(moveininfo);
                }
                moveininfo = MoveInWarehsBillFactory.getLocalInstance(ctx).getMoveInWarehsBillInfo(new ObjectUuidPK(id));
                if (moveininfo.getBaseStatus().equals(com.kingdee.eas.scm.common.BillBaseStatusEnum.SUBMITED)) {
                    MoveIssueBillFactory.getLocalInstance(ctx).audit(new ObjectUuidPK(id));
                }

            }
        }
        methodCtx.getParam(2).setValue("true");
        methodCtx.getParam(4).setValue("新增成功");
        System.out.println("/******  CCCW12  根据操作类型为1G生成的调拨出库单关联生成调拨入库单  *******/");
    } else {
        System.out.println("没有对应的操作");
        isSucess = false;
        errMsg = errMsg + "没有对应的操作\r\n";
        System.out.println("Error-没有对应的操作-" + kwczlx);
    }
    System.out.println("/*************  CCCW12 条件分支结束   ****************/");
    if (isSucess) {
        var strMsg = "0110" + dwh + "CWCC";
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
    System.out.println("/******************************  CCCW12 消息记录结束   **********************************/");
}