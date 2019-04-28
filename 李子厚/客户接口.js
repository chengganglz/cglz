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
imp.importPackage(Packages.com.kingdee.bos.dao.query);  
imp.importPackage(Packages.com.kingdee.jdbc.rowset);
imp.importPackage(Packages.com.kingdee.bos.metadata.query.util);
imp.importPackage(Packages.com.kingdee.bos.util);
imp.importPackage(Packages.com.kingdee.eas.util.client);
imp.importPackage(Packages.com.kingdee.bos.ctrl.swing);
imp.importPackage(Packages.com.kingdee.bos.metadata.entity);
with(imp){
	var socketMsg = methodCtx.getParamValue(0);  //接收报文 
	//var socketMsg="0523CCCW2020190423085652CCCWD11202195    天津钢刚好钢铁贸易有限公司                                                                          河北霸州市霸州镇营上村                                                                                                                                                                                  13244522                                                                                                                                                                            U";
	//var billID = methodCtx.getParamValue(1); // 业务单元ID  
	var result = methodCtx.getParamValue(2); // 成功失败  true:false  必须有返回值  methodCtx.getParam(2).setValue("true") 
	var sendMsg = methodCtx.getParamValue(3); // 发送报文   如果需要发送电文,将电文拼好放到这里返回  methodCtx.getParam(3).setValue("")
  	var errMsg = methodCtx.getParamValue(4); //错误消息 methodCtx.getParam(4).setValue("")  
	var ctx = pluginCtx.getContext(); //服务端上下文 
	var dwh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,5,20);
	System.out.println("电文号"+dwh);
	var dwt=PublicBaseUtil.substringByte(socketMsg,"GBK" ,1,29);
	//dwt="0524CCCW2020190224040527CCCWD11203778";
	var isSucess=true;
	var easnumber=PublicBaseUtil.substringByte(socketMsg,"GBK" ,30,12);//客户编码  从MES获取
	if(easnumber!=null){
		easnumber=easnumber.replace(" ", "");
	}else{
		isSucess=false;
		errMsg=errMsg + "客户编码不能为空\r\n";
	}
	System.out.println("编码"+easnumber);
	if("".equals(easnumber))
	{
		isSucess=false;
		errMsg=errMsg + "客户编码不能为空\r\n";
	}
	var name=PublicBaseUtil.substringByte(socketMsg,"GBK" ,42,100);//客户名称  从MES获取
	if(name!=null){
		name=name.replace(" ", "");
	}else{
		isSucess=false;
		errMsg=errMsg + "客户名称不能为空\r\n";
	}
	
	System.out.println("名称"+name);
	if("".equals(name))
	{
		isSucess=false;
		errMsg=errMsg + "客户名称不能为空\r\n";
	}
	var taxRegisterNo=PublicBaseUtil.substringByte(socketMsg,"GBK" ,342,30); //社会统一信用代码  从MES获取
	if(taxRegisterNo!=null){
		taxRegisterNo=taxRegisterNo.replace(" ", "");
	}else{
		System.out.println("社会统一信用代码"+taxRegisterNo);
		isSucess=false;
		errMsg=errMsg + "社会统一信用代码不能为空\r\n";
	}
	
	System.out.println("社会统一信用代码"+taxRegisterNo);
	if("".equals(taxRegisterNo))
	{
		isSucess=false;
		errMsg=errMsg + "社会统一信用代码不能为空\r\n";
	}
	var flag=PublicBaseUtil.substringByte(socketMsg,"GBK" ,522,1);// 操作标志 从MES获取
	
	
	var custominfo = new com.kingdee.eas.basedata.master.cssp.CustomerInfo();
	custominfo.put("bxNumber", "客户基本信息电文CCCW20");
	var view6 = new com.kingdee.bos.metadata.entity.EntityViewInfo();
	var sic6 = view6.getSelector();
	sic6.add("*");
	var filter6 = new com.kingdee.bos.metadata.entity.FilterInfo();
	var fic6 = filter6.getFilterItems();
	fic6.add(new FilterItemInfo("number", easnumber));
	view6.setFilter(filter6);
	var customerCollection =new com.kingdee.eas.basedata.master.cssp.CustomerCollection();
	if(isSucess){
		customerCollection = com.kingdee.eas.basedata.master.cssp.CustomerFactory.getLocalInstance(ctx).getCustomerCollection(view6);
		System.out.println("客户端接口参数解析完成");
	}
	
	if (isSucess&&customerCollection != null && customerCollection.size() != 0) {
		System.out.println("客户更新开始");
		custominfo = customerCollection.get(0);
		custominfo.setName(name);
		//custominfo.setString("isyushou", isyushou);
		//custominfo.setString("yushoubili", yushoubili);
		custominfo.setTaxRegisterNo(taxRegisterNo);
		var selector2 = new SelectorItemCollection();
		selector2.add("id");
		selector2.add("name");
		CustomerFactory.getLocalInstance(ctx).updatePartial(custominfo, selector2);
		custominfo.setTaxRegisterNo(taxRegisterNo);
		var selector3 = new SelectorItemCollection();
		selector3.add("id");
		selector3.add("txRegisterNo");
		CustomerFactory.getLocalInstance(ctx).updatePartial(custominfo, selector3);
		var sql = "UPDATE T_BD_Customer SET FNAME_l2='"+name+"',FTXREGISTERNO ='"+taxRegisterNo+"'  WHERE  FNUMBER ='"+easnumber+"'";
		System.out.println("select T_ORG_Admin2:"+sql);
		DbUtil.execute(ctx, sql);
	}else if(isSucess){
		custominfo.setTaxRegisterNo(taxRegisterNo);
		custominfo.setName(name);
		custominfo.setNumber(easnumber);
		var customerGroupDetailinfo=new com.kingdee.eas.basedata.master.cssp.CustomerGroupDetailInfo();
		var customerGroupDetailCollection = new com.kingdee.eas.basedata.master.cssp.CustomerGroupDetailCollection();
		//custominfo.setString("isyushou", isyushou);
		//custominfo.setString("yushoubili", yushoubili);
		var user = new com.kingdee.eas.base.permission.UserInfo();

		var view = new com.kingdee.bos.metadata.entity.EntityViewInfo();
		var sic = view.getSelector();
		sic.add("id");
		sic.add("number");
		sic.add("name");
		var filter = new com.kingdee.bos.metadata.entity.FilterInfo();
		var fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("number", "user"));
		view.setFilter(filter);
		var userCollection =new com.kingdee.eas.base.permission.UserCollection();
		
		userCollection = com.kingdee.eas.base.permission.UserFactory.getLocalInstance(ctx).getUserCollection(view);
		
		if (userCollection != null && userCollection.size() != 0) {
			user = userCollection.get(0);
		}
		if (user != null) {
			custominfo.setCreator(user);
		}
		var date = new java.util.Date();       
		var nousedate = new java.sql.Timestamp(date.getTime());
		custominfo.setCreateTime(nousedate);
		var ctrlUnitFI = new com.kingdee.eas.basedata.org.CtrlUnitInfo();

		var view1 = new com.kingdee.bos.metadata.entity.EntityViewInfo();
		var sic1 = view1.getSelector();
		sic1.add("id");
		sic1.add("number");
		sic1.add("name");
		var filter1 = new com.kingdee.bos.metadata.entity.FilterInfo();
		var fic1 = filter1.getFilterItems();
		fic1.add(new FilterItemInfo("number", "01"));
		view1.setFilter(filter1);
		var ctrlUnitCollection1 = new com.kingdee.eas.basedata.org.CtrlUnitCollection();

		if (ctx != null) {
			ctrlUnitCollection1 = com.kingdee.eas.basedata.org.CtrlUnitFactory.getLocalInstance(ctx).getCtrlUnitCollection(view1);
			
		}

		if (ctrlUnitCollection1 != null && ctrlUnitCollection1.size() != 0) {
			ctrlUnitFI = ctrlUnitCollection1.get(0);
		}
		com.kingdee.eas.util.app.ContextUtil.setCurrentCtrlUnit(ctx, ctrlUnitFI);
		custominfo.setCU(ctrlUnitFI);
		custominfo.setAdminCU(ctrlUnitFI);
		var comorg=new com.kingdee.eas.basedata.org.CompanyOrgUnitInfo();
		var view2 = new EntityViewInfo();
		var sic2 = view2.getSelector();
		sic2.add("id");
		sic2.add("number");
		sic2.add("name");
		var filter2 = new FilterInfo();
		var fic2 = filter2.getFilterItems();
		fic2.add(new FilterItemInfo("number", "01"));
		view2.setFilter(filter2);
		var companyOrgUnitCollection = new com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;

		if (ctx != null) {
				companyOrgUnitCollection = com.kingdee.eas.basedata.org.CompanyOrgUnitFactory
						.getLocalInstance(ctx).getCompanyOrgUnitCollection(view2);
			
		}

		if (companyOrgUnitCollection != null
				&& companyOrgUnitCollection.size() != 0) {
			comorg = companyOrgUnitCollection.get(0);
		}
		var custominfocompany = new com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoInfo();
		
		custominfocompany.setCompanyOrgUnit(comorg);
		var currencyFI =new com.kingdee.eas.basedata.assistant.CurrencyInfo();
		var view3 = new EntityViewInfo();
		var sic3 = view3.getSelector();
		sic3.add("id");
		sic3.add("number");
		sic3.add("name");
		var filter3 = new FilterInfo();
		var fic3 = filter3.getFilterItems();
		fic3.add(new FilterItemInfo("number", "BB01"));
		view3.setFilter(filter3);        
		var currencyCollection=new com.kingdee.eas.basedata.assistant.CurrencyCollection();
		
		currencyCollection = com.kingdee.eas.basedata.assistant.CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection(view3);
		
		
		if(currencyCollection != null && currencyCollection.size() != 0){
			currencyFI = currencyCollection.get(0);
		}
		custominfocompany.setSettlementCurrency(currencyFI);
		var gsspGroupInfo=new com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo();
		var view4 = new EntityViewInfo();
		var sic4 = view4.getSelector();
		sic4.add("id");
		sic4.add("number");
		sic4.add("name");
		var filter4 = new FilterInfo();
		var fic4 = filter4.getFilterItems();
		fic4.add(new FilterItemInfo("number", "01"));
		view4.setFilter(filter4);
		var groupCollection = new com.kingdee.eas.basedata.master.cssp.CSSPGroupCollection();

		if (ctx != null) {
			groupCollection = com.kingdee.eas.basedata.master.cssp.CSSPGroupFactory.getLocalInstance(ctx).getCSSPGroupCollection(view4);
			
		}

		if (groupCollection != null
				&& groupCollection.size() != 0) {
			gsspGroupInfo = groupCollection.get(0);
		}
		custominfo.setBrowseGroup(gsspGroupInfo);
		customerGroupDetailinfo.setCustomerGroup(gsspGroupInfo);
		var standardinfo=new com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardInfo();

		var view5 = new EntityViewInfo();
		var sic5 = view5.getSelector();
		sic5.add("id");
		sic5.add("number");
		sic5.add("name");
		var filter5 = new FilterInfo();
		var fic5 = filter5.getFilterItems();
		fic5.add(new FilterItemInfo("number", "customerGroupStandard"));
		view5.setFilter(filter5);
		var groupCollection = new com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardCollection();

		if (ctx != null) {
			groupCollection = com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardFactory.getLocalInstance(ctx).getCSSPGroupStandardCollection(view5);
			
		}

		if (groupCollection != null
				&& groupCollection.size() != 0) {
			standardinfo = groupCollection.get(0);
		}
		customerGroupDetailinfo.setCustomerGroupStandard(standardinfo);
		customerGroupDetailCollection.add(customerGroupDetailinfo);
		custominfo.getCustomerGroupDetails().addCollection(customerGroupDetailCollection);
		custominfo.setIsInternalCompany(false);
		custominfo.setUsedStatus(com.kingdee.eas.basedata.master.cssp.UsedStatusEnum.UNAPPROVE);
		var bosId=com.kingdee.bos.util.BOSUuid.create(custominfo.getBOSType());
		custominfo.setId(bosId);
		var pk=com.kingdee.eas.basedata.master.cssp.CustomerFactory.getLocalInstance(ctx).save(custominfo);
		com.kingdee.eas.basedata.master.cssp.CustomerFactory.getLocalInstance(ctx).approve(pk);
		custominfocompany.setCustomer(custominfo);
		var pk2=com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoFactory.getLocalInstance(ctx).save(custominfocompany);
		var customsaleinfo = new com.kingdee.eas.basedata.master.cssp.CustomerSaleInfoInfo();
		
		var saleorg=PublicBaseUtil.getSaleOrgUnitInfoByNumber(ctx,"01");
		customsaleinfo.setSaleOrgUnit(saleorg);
		customsaleinfo.setCustomer(custominfo);
		customsaleinfo.setSettlementOrgUnit(custominfo);
		customsaleinfo.setBillingOrgUnit(custominfo);
		customsaleinfo.setDeliverOrgUnit(custominfo);
		var pk3=CustomerSaleInfoFactory.getLocalInstance(ctx).save(customsaleinfo);
	}
	/*if("D".equals(flag)){
		custominfo.setUsedStatus(com.kingdee.eas.basedata.master.cssp.UsedStatusEnum.FREEZED);//禁用客户
		var selector1 = new SelectorItemCollection();
		selector1.add("id");
		selector1.add("usedStatus");
		CustomerFactory.getLocalInstance(ctx).updatePartial(custominfo, selector1);
	}*/
	var strMsg="0110"+dwh+"CWCC";
	if(isSucess){
		methodCtx.getParam(2).setValue("true");
		var strData=String.format("%-81s", "A")+"\n";
		methodCtx.getParam(3).setValue(strMsg+strData);
	}else
	{
		methodCtx.getParam(2).setValue("false");
		methodCtx.getParam(4).setValue(dwt+errMsg);
		var strMsg="0110"+dwh+"CWCCB";
		var strData=String.format("%-80s", errMsg)+"\n";
		var endmsg="\n";
		methodCtx.getParam(3).setValue(strMsg+strData+endmsg);
	}
}