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
imp.importPackage(Packages.com.kingdee.bos.dao.ormapping);  
imp.importPackage(Packages.com.kingdee.eas.framework); 


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
	var dwh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,5,20);
	var isSucess=true;
	methodCtx.getParam(2).setValue("false") ;
    	var errMsg="";
    	var strCreatorNo="user"; //创建人编码
    	var strCUNo="01";// 管理单元编码
    	var strBizDate=PublicBaseUtil.substringByte(socketMsg,"GBK" ,11,8) ; //业务日期
    	var billStatus=BillBaseStatusEnum.ADD; //单据状态
    	var strBizTypeNo="110";//业务类型编码
    	var strBillTypeNo="103";//单据类型编码
    	var strTranstionType="001";//事务类型
    	var strSupNo="1000"; //供应商编码
    	var strCurrencyNo ="BB01"; //币别编码
    	var strPaymentType="004";// 付款方式
    	var bgExchangeRate=BigDecimal.ONE;// 汇率
    	var bgTotalQty =BigDecimal.ZERO;// 数量
    	var bgTotalAmount =BigDecimal.ZERO;// 金额
    	var bgTotalActualCost =BigDecimal.ZERO;// 实际成本
    	var bgTotalLocalAmount =BigDecimal.ZERO;// 本位币金额
    	var  isInTax=true;
    	var sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    	
    	
    	//新增采购退货
    //制单人
		var creator=PublicBaseUtil.getUserByNumber(ctx, strCreatorNo);
		if(creator!=null){
			
		}else{
			isSucess=false;
			errMsg=errMsg + "制单人未找到\r\n";
		}
    	var sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    	//业务日期
    	var bizDate=null;
		try
		{
			
			var sdf = new SimpleDateFormat("yyyyMMdd");
			bizDate=sdf.parse(strBizDate);
			
		}catch( e){
			isSucess=false;
			errMsg=errMsg + "日期格式不正确\r\n";
			//throw new BOSException("日期格式不正确");
			
		}
		
    	//获取管理单元
    	var cu=PublicBaseUtil.getCU(ctx, strCUNo); 
    	if(cu!=null){
    		
    	}else
    	{
    		isSucess=false;
			errMsg=errMsg + "管理单元不能为空\r\n";
    	}
    	var rjh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,30,20);
    	rjh=rjh.trim();
    	
    	var strMaterialNo="";
    	var strUnit="公斤（千克）";
    	var strLot=rjh;
    	var strStockNo="001";
    	var bgQty=new BigDecimal("0");
    	var bgPrice=new BigDecimal("0");
    	var bgTaxRate=new BigDecimal("0");
    	var bgAmount=new BigDecimal("0");
    	var  bgKd=new BigDecimal("0");
    	var bgHd=new BigDecimal("0");
    	
	if("".equals(rjh))
	{
		isSucess=false;
		errMsg=errMsg + "热卷号不能为空\r\n";
	}
	var ph=PublicBaseUtil.substringByte(socketMsg,"GBK" ,100,50);
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
	var kd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,156,5);
	if(kd!=null){
		kd=kd.trim();
	}else
	{
		kd="0";
	}
	try
	{
		bgKd=new BigDecimal(kd).divide(new BigDecimal("10"));;
	}catch(  e){
		
	}
	var hd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,150,6);
	
		hd=hd.trim();
	
	try
	{
		bgHd=new BigDecimal(hd).divide(new BigDecimal("1000"));
	}catch( e){
		
	}
	var zl=PublicBaseUtil.substringByte(socketMsg,"GBK" ,161,5);
	if(zl!=null){
		zl=zl.trim();
	}else
	{
		zl="0";
	}
	try
	{
		bgQty=new BigDecimal(zl);
	}catch( e){
		
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
     	if(isSucess){
    		var material= SocketFacadeFactory.getLocalInstance(ctx).getMaterialInfo("01", "5", "09", ph, ph, bgKd, bgHd, strUnit, "", true);
	    	if (material!=null){
	    		var purBills=PurInWarehsEntryFactory.getLocalInstance(ctx).getPurInWarehsEntryCollection(" select * where parent.baseStatus='4' and   parent.bizType='d8e80652-0106-1000-e000-04c5c0a812202407435C'   and  material.id='"+material.getId().toString()+"' and  lot='"+strLot+"' and parent.CU.id='" +cu.getId().toString()+"'");
	    		if (purBills!=null){
	    			if(purBills.size()>0){
			    		//采购退货
	    				var entry=purBills.get(0);
	    				if(entry!=null){
				        	var info=PurInWarehsBillFactory.getLocalInstance(ctx).getPurInWarehsBillInfo(new ObjectUuidPK(entry.getParent().getId().toString()));
				        	var sour= new PurInWarehsBillCollection();
				        	sour.add(info);
				        	var source=new CoreBillBaseCollection();
				        	var newcoll=sour.cast(new CoreBillBaseCollection().getClass());
				        	var infos= PublicBaseUtil.botp(ctx, info.getBOSType(),  info.getBOSType(), newcoll, "GuKLmgEUEADgABTHwKgShQRRIsQ=");
				        	if(infos!=null){
				        		if(infos.size()>0){
				        			var id=infos.get(0).getId().toString();
				        			if(id!=null){
				    	    			var tempIno=PurInWarehsBillFactory.getLocalInstance(ctx).getPurInWarehsBillInfo(new ObjectUuidPK(id));
				    	    			if(tempIno!=null){
				    	    				tempIno.setBizDate(bizDate);
				    	    				tempIno.setCreator(creator);
				    	    				
					    	    			if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.TEMPORARILYSAVED)) {
					    						PurInWarehsBillFactory.getLocalInstance(ctx).submit(tempIno);
					    						
					    					}
					    					tempIno=PurInWarehsBillFactory.getLocalInstance(ctx).getPurInWarehsBillInfo(new ObjectUuidPK(id));
					    					if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
					    						PurInWarehsBillFactory.getLocalInstance(ctx).audit(new ObjectUuidPK(id));
					    					}
					    					methodCtx.getParam(2).setValue("true") ;
										methodCtx.getParam(4).setValue("新增成功");
				    	    			}
				        			}
				        		}
				        	}
	    				}
	    			}
	    		}
	    	}
    	}else
    	{
    		methodCtx.getParam(2).setValue("false") ;
		methodCtx.getParam(4).setValue(errMsg);

    		//throw new BOSException(errMsg);
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
	
}