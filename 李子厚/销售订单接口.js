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
with(imp){ 
	var socketMsg = methodCtx.getParamValue(0);  //接收报文 
	//socketMsg="1961CCCW0120190423142349CCCWDX190423003          I TEST001             0000000000000020000000000000004700000000DD11                                              00200012500013XC热轧酸洗卷                    A3015                                             20190423201905232019043013309008    宁波浙金钢材有限公司                                                                                                                                                                                    13309008    宁波浙金钢材有限公司                                                                                                                                                                                    13309008    宁波浙金钢材有限公司                                                                                                                                                                                                                                                                                                                                                                                                        B一票到站                                214 汽水联运                                                                                                                                                                                                        20190423                                                                N00000                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ";
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
	
	var dwh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,5,20);
	System.out.println("销售订单电文号"+dwh);
	var dwt=PublicBaseUtil.substringByte(socketMsg,"GBK" ,1,29);
	var isSucess=true;
	var errMsg="";
	var strCreatorNo="user"; //创建人编码
	var strCUNo="01";// 管理单元编码
	var iszp=false;
	var  htxz =PublicBaseUtil.substringByte(socketMsg,"GBK" ,1461, 3); //是否赠品  从MES获取
	if(htxz==null){
		isSucess=false;
		errMsg=errMsg + "合同性质不能为空\r\n";
	}else{
		htxz=htxz.replace(" ", "");
	}
	if("".equals(htxz)){
		isSucess=false;
		errMsg=errMsg + "合同性质不能为空\r\n";
	}
	if("QSC".equals(htxz)){
		iszp=true;
	}
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
	var billNum=PublicBaseUtil.substringByte(socketMsg,"GBK" ,30, 20);//合同流水号  从MES获取
	System.out.println("合同号"+billNum);
	if(billNum==null){
		isSucess=false;
		errMsg=errMsg + "合同号不能为空\r\n";
	}else{
		billNum=billNum.replace(" ", "");
	}
	if("".equals(billNum)){
		isSucess=false;
		errMsg=errMsg + "合同号不能为空\r\n";
	}
	var flag=PublicBaseUtil.substringByte(socketMsg,"GBK" ,50, 1);//操作类型  从MES获取
	if(flag==null){
		isSucess=false;
		errMsg=errMsg + "操作类型不能为空\r\n";
	}else{
		flag=flag.replace(" ", "");
	}
	if("".equals(flag)){
		isSucess=false;
		errMsg=errMsg + "操作类型不能为空\r\n";
	}
	System.out.println("操作类型"+flag);
	if("I".equals(flag)){
		var conNum=PublicBaseUtil.substringByte(socketMsg,"GBK" ,52, 20);//合约号  从MES获取
		if(conNum==null){
			isSucess=false;
			errMsg=errMsg + "合约号不能为空\r\n";
		}else{
			conNum=conNum.replace(" ", "");
		}
		if("".equals(conNum)){
			isSucess=false;
			errMsg=errMsg + "合约号不能为空\r\n";
		}
		System.out.println("合约号"+conNum);
		var strBizDate=PublicBaseUtil.substringByte(socketMsg,"GBK" ,258, 8); //业务日期  从MES获取
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
		var strBizTypeNo="210";//业务类型编码  
		var strBillTypeNo="310";//单据类型编码
		var  isInnerSale=false; // 内部销售
		var  custNo =PublicBaseUtil.substringByte(socketMsg,"GBK" ,282, 12); //订单客户编码  从MES获取
		System.out.println("订单客户编码"+custNo);
		if(custNo==null){
			isSucess=false;
			errMsg=errMsg + "订单客户编码不能为空\r\n";
		}else{
			custNo=custNo.replace(" ", "");
		}
		if("".equals(custNo)){
			isSucess=false;
			errMsg=errMsg + "订单客户编码不能为空\r\n";
		}
		
		var  yushoustr =PublicBaseUtil.substringByte(socketMsg,"GBK" ,1455, 1); //是否预收  从MES获取
		System.out.println("是否预收"+yushoustr);
		if(yushoustr==null){
			isSucess=false;
			errMsg=errMsg + "是否预收不能为空\r\n";
		}else{
			yushoustr=yushoustr.replace(" ", "");
		}
		var isyushou=false;
		if("Y".equals(yushoustr)){
			isyushou=true;
		}else if("N".equals(yushoustr)){
			isyushou=false;
		}
		var yushoubili=BigDecimal.ZERO;//预售比例  从MES获取
		if(isyushou){
			var ysblstr=PublicBaseUtil.substringByte(socketMsg,"GBK" ,1456,3);//预售比例  从MES获取
			System.out.println("预售比例"+ysblstr);
			if(ysblstr!=null){
				ysblstr=ysblstr.trim();
			}else
			{
				ysblstr="0";
			}
			try
			{
				yushoubili=new BigDecimal(ysblstr);
			}catch(e){
				
			}
			if (yushoubili.compareTo(BigDecimal.ZERO)==0){
				isSucess=false;
				errMsg=errMsg + "预收比例不能为零\r\n";
			}
		}
		var  strDeliveryTypeNo="SEND"; //交货方式编码
		var strCurrencyNo ="BB01"; //币别编码
		var bigExchangeRate=BigDecimal.ONE;// 汇率
		var strPaymentTypeNo="002";// 付款方式编码
		var bgPrePayment=BigDecimal.ZERO;//预收金额
		var bgPrePaymentRate=BigDecimal.ZERO;//预收比率
		var strSaleOrgUnitNo="01"; // 销售组织编码
		var strSaleGroupNo="01"; // 销售组编码
		var strSalePersonNo="22713"; // 销售员编码
		//var strAdminOrgUnitNo="100"; // 部门编码
		var strStorageOrgUnitNo="01"; //库存组织
		
		var bgTotalAmount=new BigDecimal("0");//金额
		var bgTotalTax=new BigDecimal("0");//税额
		var bgTotalTaxAmount=new BigDecimal("0");//价税合计
		var bgPreRecevied=BigDecimal.ZERO;//已收应收款
		var bgUnPreReceviedAmout=BigDecimal.ZERO;//未预收款金额
		
		var strSendAddress ="地址"; // 送货地址
		var isSysBill=false;  //是否为系统订单
		var convertMode=ConvertModeEnum.DIRECTEXCHANGERATE;// 折算方式  默认直接汇率
		
		var bgLocalTotalAmount=new BigDecimal("0");//金额本位币
		var bgLocalTotalTaxAmount=new BigDecimal("0");//价税合计本位币
		var strCompanyNo="01" ; // 财务组织编码
		
		var isInTax=true; //是否含税
		var strCustomerPhone =""; //联系电话
		var strLinkMan ="" ;// 联系人
		var isCentralBalance=false; //集中结算
		
		var sdfDate = new SimpleDateFormat("yyyyMMdd");
		
		var cust=PublicBaseUtil.getCustomer(ctx, custNo);
		if(cust==null){
			isSucess=false;
			errMsg=errMsg + "客户不能为空\r\n";
		}
		var deliveryTypeInfo=PublicBaseUtil.geDeliveryTypeByNumber(ctx, strDeliveryTypeNo);
		if(deliveryTypeInfo==null){
			isSucess=false;
			errMsg=errMsg + "交付方式不能为空\r\n";
		}
		var currency=PublicBaseUtil.getCurrencyInfo(ctx, strCurrencyNo);
		if(currency==null){
			isSucess=false;
			errMsg=errMsg + "币别不能为空\r\n";
		}
		var paymentType=PublicBaseUtil.getPaymentTypeInfoByNumber(ctx, strPaymentTypeNo);
		if(paymentType==null){
			isSucess=false;
			errMsg=errMsg + "付款方式不能为空\r\n";
		}
		var saleOrgUnit=PublicBaseUtil.getSaleOrgUnitInfoByNumber(ctx, strSaleOrgUnitNo);
		if(saleOrgUnit==null){
			isSucess=false;
			errMsg=errMsg + "销售组织不能为空\r\n";
		}
		var saleGroup=PublicBaseUtil.getSaleGroupInfoByNumber(ctx, strSaleGroupNo);
		if(saleGroup==null){
			isSucess=false;
			errMsg=errMsg + "销售组不能为空\r\n";
		}
		var salePerson=PublicBaseUtil.getPersonInfoByNumber(ctx, strSalePersonNo);
		if(salePerson==null){
			isSucess=false;
			errMsg=errMsg + "销售员不能为空\r\n";
		}
		/*var adminOrg=PublicBaseUtil.getAdminOrgUnitInfoByNumber(ctx, strAdminOrgUnitNo);
		if(adminOrg==null){
			isSucess=false;
			errMsg=errMsg + "部门不能为空\r\n";
		}*/
		var strUnit="吨"; //单位  默认吨
		var bgAssociateQty=BigDecimal.ZERO; //未关联数量  
		var bgBaseQty=new BigDecimal("0"); //基本单位数量 
		var bgQty=new BigDecimal("0"); //数量  从MES获取
		var bgKd=new BigDecimal("0"); 
		var bgHd=new BigDecimal("0");
		var kd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,168,5);//宽度  从MES获取
		System.out.println("宽度"+kd);
		if(kd!=null){
			kd=kd.trim();
		}else
		{
			kd="0";
		}
		try
		{
			bgKd=new BigDecimal(kd).divide(new BigDecimal("10"));;
			//bgKd=new BigDecimal("4160");
		}catch(e){
			
		}
		var hd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,162,6);//厚度  从MES获取
		System.out.println("厚度"+hd);
		if(hd!=null){
			hd=hd.trim();
		}else
		{
			hd="0";
		}
		try
		{
			bgHd=new BigDecimal(hd).divide(new BigDecimal("1000"));
			//bgHd=new BigDecimal("0.816");
		}catch(e){
			
		}
		var zl=PublicBaseUtil.substringByte(socketMsg,"GBK" ,82,10);//重量  从MES获取
		System.out.println("重量"+zl);
		if(zl!=null){
			zl=zl.trim();
		}else
		{
			zl="0";
		}
		try
		{
			bgQty=new BigDecimal(zl).divide(new BigDecimal("1000"));
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
		if (bgQty.compareTo(BigDecimal.ZERO)==0){
			isSucess=false;
			errMsg=errMsg + "重量不能为零\r\n";
		}
		var bgPrice=new BigDecimal("0"); //单价   从MES获取
		var pro=PublicBaseUtil.substringByte(socketMsg,"GBK" ,92,20);//单价  从MES获取
		System.out.println("单价"+pro);
		if(pro!=null){
			pro=pro.trim();
		}else
		{
			pro="0";
		}
		try
		{
			bgPrice=new BigDecimal(pro).divide(new BigDecimal("1000000"));
		}catch(e){
			
		}
		if(!iszp){
			if (bgPrice.compareTo(BigDecimal.ZERO)==0){
				isSucess=false;
				errMsg=errMsg + "单价不能为零\r\n";
			}
		}
		var bgTaxRate=new BigDecimal("0"); //税率  从MES获取
		var shuilv=PublicBaseUtil.substringByte(socketMsg,"GBK" ,173,3);//税率  从MES获取
		System.out.println("税率"+shuilv);
		if(shuilv!=null){
			shuilv=shuilv.trim();
		}else
		{
			shuilv="0";
		}
		try
		{
			bgTaxRate=new BigDecimal(shuilv);
		}catch(e){
			
		}
		if (bgTaxRate.compareTo(BigDecimal.ZERO)==0){
			isSucess=false;
			errMsg=errMsg + "税率不能为零\r\n";
		}
		var bgTaxPrice=bgPrice.multiply(bgTaxRate.divide(new BigDecimal("100"),8,BigDecimal.ROUND_HALF_UP).add(BigDecimal.ONE));
		var bgAmount =bgPrice.multiply(bgQty);// 金额
		bgTotalAmount=bgAmount;
		bgLocalTotalAmount=bgTotalAmount.multiply(bigExchangeRate);
		bgAssociateQty=bgQty; //未关联数量
		bgBaseQty=bgQty; //基本单位数量
		var bgLocalAmount =bgAmount.multiply(bigExchangeRate);
		
		var bgActualPrice=bgPrice ;// 实际单价
		var bgActualTaxPrice=bgTaxPrice ;//实际含税单价
		var bgTaxAmount=bgTaxPrice.multiply(bgQty);
		bgTotalTaxAmount=bgTaxAmount;
		bgLocalTotalTaxAmount=bgTotalTaxAmount.multiply(bigExchangeRate);
		var bgTax=bgTaxAmount.subtract(bgAmount); //税额
		bgTotalTax=bgTax;
		var bgLocalTax=bgTax.multiply(bigExchangeRate); //本位币税额
		var bgLocalTaxAmount=bgTaxAmount.multiply(bigExchangeRate); // 本位币价税合计
		var phmc=PublicBaseUtil.substringByte(socketMsg,"GBK" ,112,50); //牌号名称  从MES获取
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
		var ph=""; //牌号 从MES获取
		var sql = "SELECT * FROM CT_CUS_Paihao where fname_l2 ='"+phmc+"'";
		var rows = DbUtil.executeQuery(ctx, sql);
		if(rows!= null&&rows.next() ) {
			ph=rows.getString("fnumber");
		}else{
			ph=phmc;
		}
		System.out.println("牌号名称"+phmc);
		System.out.println("牌号"+ph);
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
		var company=PublicBaseUtil.getCompany(ctx, strCompanyNo);
		if(company==null){
			isSucess=false;
			errMsg=errMsg + "财务组织不能为空\r\n";
		}
		var sendDateStr="";
		sendDateStr=PublicBaseUtil.substringByte(socketMsg,"GBK" ,266, 8); //交货日期  从MES获取
		if(sendDateStr==null){
			isSucess=false;
			errMsg=errMsg + "交货日期不能为空\r\n";
		}else{
			sendDateStr=sendDateStr.replace(" ", "");
		}
		if("".equals(sendDateStr)){
			isSucess=false;
			errMsg=errMsg + "交货日期不能为空\r\n";
		}else if(sendDateStr!=null){
			var isdate=PublicBaseUtil.valiDateTimeWithLongFormat(sendDateStr);
			if(!isdate){
				isSucess=false;
				errMsg=errMsg + "交货日期格式不合法\r\n";
			}
		}
		System.out.println("交货日期"+sendDateStr);
		var dtSendDate = null;
		try {
			dtSendDate = sdfDate.parse(sendDateStr);//发货日期  从MES获取
		} catch ( e) {
			
		}
		var dtDeliveryDate = null;
		try {
			dtDeliveryDate = sdfDate.parse(sendDateStr);//交货日期  从MES获取
		} catch ( e) {
			
		}
		var materialGroupNum=PublicBaseUtil.substringByte(socketMsg,"GBK" ,176, 1);//物料组别  从MES获取
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
			errMsg=errMsg + "品名代码不能为空\r\n";
		}
		System.out.println("品名代码"+materialGroupNum);
		if("X".equals(materialGroupNum)){
			materialGroupNum="09";
		}else if("M".equals(materialGroupNum)){
			materialGroupNum="19";
		}
		var material= null;
		var unit=null;
		if(isSucess){
			material= SocketFacadeFactory.getLocalInstance(ctx).getMaterialInfo("01", "5", materialGroupNum, ph, phmc, bgKd, bgHd, strUnit, "", true);
			
			if (material==null){
				isSucess=false;
				errMsg=errMsg + "物料不能为空\r\n";
			}else{
				unit=PublicBaseUtil.getMeasureUnitInfoByName(ctx, material.getBaseUnit().getMeasureUnitGroup().getNumber(), strUnit);
				if (unit==null){
					isSucess=false;
					errMsg=errMsg + "单位不能为空\r\n";
				}
			}
		}
		var storOrgUnit=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strStorageOrgUnitNo);
		if(storOrgUnit==null){
			isSucess=false;
			errMsg=errMsg + "库存组织不能为空\r\n";
		}
		
		if(isSucess){
			//新增销售订单
			var info=new SaleOrderInfo();
			info.setNumber(billNum);
			info.put("heyuehao", conNum);
			info.put("bxNumber", "订单信息电文CCCW01");
			info.put("isyushou", isyushou);
			info.put("yushoubili", yushoubili);
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
			//创建时间
			try {
				info.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
			} catch ( e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				var period =c.get(Calendar.YEAR)*100+c.get(Calendar.MONTH)+1 ; //期间
				info.setYear(year);
				info.setPeriod(period);
			}catch( ex){
				isSucess=false;
				errMsg=errMsg +ex.message + "日期格式不正确\r\n";
				//throw new BOSException("日期格式不正确");
				
			}
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
			// 内部销售
			info.setIsInnerSale(isInnerSale);  
			//订货客户
			if(cust!=null){
				info.setOrderCustomer(cust);
				var custCompanyinfo=PublicBaseUtil.getCustomerCompanyInfo(ctx, cust.getId().toString());
				if (custCompanyinfo!=null){
					strCustomerPhone=custCompanyinfo.getPhone()!=null?custCompanyinfo.getPhone().toString():"";
					strLinkMan=custCompanyinfo.getContactPerson()!=null?custCompanyinfo.getContactPerson():"";
				}
			}
			// 交货方式
			if(deliveryTypeInfo!=null){
				info.setDeliveryType(deliveryTypeInfo);
			}
			//币别
			if(currency!=null){
				info.setCurrency(currency);
				info.setExchangeRate(bigExchangeRate);
			}
			
			//付款方式
			if(paymentType!=null){
				info.setPaymentType(paymentType);
				
			}
			//预收金额
			info.setPrepayment(bgPrePayment);
			//预收比率
			info.setPrepaymentRate(bgPrePaymentRate);
			//销售组织
			if(saleOrgUnit!=null){
				info.setSaleOrgUnit(saleOrgUnit);
			}
			//销售组
			if(saleGroup!=null){
				info.setSaleGroup(saleGroup);
			}
			//销售员
			if(salePerson!=null){
				info.setSalePerson(salePerson);
			}
			
			//部门
			//if(adminOrg!=null){
			//	info.setAdminOrgUnit(adminOrg);
			//}
			//金额
			info.setTotalAmount(bgTotalAmount);
			//税额
			info.setTotalTax(bgTotalTax);
			//价税合计
			info.setTotalTaxAmount(bgTotalTaxAmount);
			//已收应收款
			info.setPreReceived(bgPreRecevied);
			//未预收款金额
			info.setUnPrereceivedAmount(bgUnPreReceviedAmout);
			// 送货地址
			info.setSendAddress(strSendAddress);
			//是否为系统订单
			info.setIsSysBill(isSysBill);
			// 折算方式  默认直接汇率
			info.setConvertMode(convertMode);
			//金额本位币
			info.setLocalTotalAmount(bgLocalTotalAmount);
			//价税合计本位币
			info.setLocalTotalTaxAmount(bgLocalTotalTaxAmount);
			// 财务组织
			if(company!=null){
				info.setCompanyOrgUnit(company);
			
			}
			//是否含税
			info.setIsInTax(isInTax);
			//联系电话
			info.setCustomerPhone(strCustomerPhone);
			// 联系人
			info.setLinkMan(strLinkMan);
			//集中结算
			info.setIsCentralBalance(isCentralBalance);

			//销售订单分录 
			
			
			// String strUnitGroup=""; //单位组
			
			
			//  String strCompanyNo=""; //财务组织
			
			var entry=new SaleOrderEntryInfo();
			//分录行号
			entry.setSeq(1);
			entry.setIsPresent(iszp);
			entry.put("htxz",htxz);
			entry.setSourceBillEntrySeq(0);
			entry.setDiscountCondition(DiscountConditionEnum.NULL);
			//物料
			if (material!=null){
				entry.setMaterial(material);
				entry.setBaseUnit(material.getBaseUnit());
				entry.setMaterialGroup(material.getMaterialGroup());
				//单位
				if (unit!=null){
					entry.setUnit(unit);
				}
			}
			
			entry.setBaseStatus(EntryBaseStatusEnum.ADD);//单据状态 新增
			//
			entry.setAssCoefficient(BigDecimal.ZERO);
			//未关联数量
			entry.setAssociateQty(bgAssociateQty);
			//基本单位数量
			entry.setBaseQty(bgQty);
			//数量
			entry.setQty(bgQty);
			//单价
			entry.setPrice(bgPrice);
			//含税单价
			entry.setTaxPrice(bgTaxPrice);
			// 金额
			entry.setAmount(bgAmount);
			// 本位币金额
			entry.setLocalAmount(bgLocalAmount);
			// 实际单价
			entry.setActualPrice(bgActualPrice);
			//实际含税单价
			entry.setActualTaxPrice(bgActualTaxPrice);
			//税率
			entry.setTaxRate(bgTaxRate);
			//税额
			entry.setTax(bgTax);
			// 价税合计
			entry.setTaxAmount(bgTaxAmount);
			//发货日期
			entry.setSendDate(dtSendDate);
			//交货日期
			entry.setDeliveryDate(dtDeliveryDate);
			//库存组织
			if(storOrgUnit!=null){
				entry.setStorageOrgUnit(storOrgUnit);
				entry.setProStorageOrgUnit(storOrgUnit);
			}
			//财务组织
			entry.setCompanyOrgUnit(company);
			//本位币税额
			entry.setLocalTax(bgLocalTax);
			// 本位币价税合计
			entry.setLocalTaxAmount(bgLocalTaxAmount);
			//发货客户
			entry.setDeliveryCustomer(cust);
			entry.setReceiveCustomer(cust);
			entry.setPaymentCustomer(cust);
			
			
			info.getEntries().add(entry);
		}
		if(isSucess){
			var pk=SaleOrderFactory.getLocalInstance(ctx).save(info);  //保存
			if(pk!=null){
				//	Context ctxTemp=ctx.
				var tempIno=SaleOrderFactory.getLocalInstance(ctx).getSaleOrderInfo(pk);
				if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.TEMPORARILYSAVED)) {
					SaleOrderFactory.getLocalInstance(ctx).submit(info);
				}
				if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
					SaleOrderFactory.getLocalInstance(ctx).audit(pk);
				}
			
			}
			methodCtx.getParam(2).setValue("true") ;
		}else
		{
			methodCtx.getParam(2).setValue("false") ;
			methodCtx.getParam(4).setValue(errMsg) ;
			
		}
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
		// var strMsg="0110"+dwh+"CWCC";
		// 	methodCtx.getParam(2).setValue("true");
		// 	var strData=String.format("%-81s", "A")+"\n" ;
		// 	methodCtx.getParam(3).setValue(strMsg+strData);
		
	}
}