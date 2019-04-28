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
	
	//var billID = methodCtx.getParamValue(1); // 业务单元ID  
	var result = methodCtx.getParamValue(2); // 成功失败  true:false  必须有返回值  methodCtx.getParam(2).setValue("true") 
	var sendMsg = methodCtx.getParamValue(3); // 发送报文   如果需要发送电文,将电文拼好放到这里返回  methodCtx.getParam(3).setValue("")
  	var errMsg = methodCtx.getParamValue(4); //错误消息 methodCtx.getParam(4).setValue("")  
	var ctx = pluginCtx.getContext(); //服务端上下文 
	var dwh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,5,20);
	System.out.println("电文号"+dwh);
	var dwt=PublicBaseUtil.substringByte(socketMsg,"GBK" ,1,29);
	dwt="0524CCCW2020190224040527CCCWD11203778";
	var isSucess=true;
	var easnumber=PublicBaseUtil.substringByte(socketMsg,"GBK" ,30,4);//客户编码  从MES获取
	if(easnumber!=null){
		easnumber=easnumber.replace(" ", "");
	}
	if("".equals(easnumber))
	{
		isSucess=false;
		errMsg=errMsg + "牌号流水不能为空\r\n";
	}
	var easname=PublicBaseUtil.substringByte(socketMsg,"GBK" ,34,50);//客户编码  从MES获取
	if(easname!=null){
		easname=easname.replace(" ", "");
	}
	if("".equals(easname))
	{
		isSucess=false;
		errMsg=errMsg + "牌号名称不能为空\r\n";
	}
	var format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	var fcreatetime = new java.util.Date();
	var fcreatetimestr=format.format(fcreatetime);
	
	if(isSucess){
		var sql = "SELECT * FROM CT_CUS_Paihao where fnumber='"+easnumber+"'";
		var rows = DbUtil.executeQuery(ctx, sql);
		if(rows!= null&&rows.next() ) {
			sql = "UPDATE CT_CUS_Paihao SET  fname_l2='"+easname+"' WHERE fnumber='"+easnumber+"' ";
			System.out.println("select T_ORG_Admin2:"+sql);
			DbUtil.execute(ctx, sql);
		}else{
			var bosid=com.kingdee.bos.util.BOSUuid.create("23013530");
			var id=bosid.toString();
			sql = "INSERT INTO CT_CUS_Paihao (Fid, fnumber, fname_l2, fcreatorid ,fcreatetime,fcontrolunitid ) VALUES ('"+id+"','"+easnumber+"','"+easname+"','256c221a-0106-1000-e000-10d7c0a813f413B7DE7F',to_date('"+fcreatetimestr+"'),'00000000-0000-0000-0000-000000000000CCE7AED4')";
			System.out.println("select T_ORG_Admin2:"+sql);
			DbUtil.execute(ctx, sql);
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
		var strData=String.format("%-80s", errMsg)+"\n";
		var strMsg="0110"+dwh+"CWCCB";
		var endmsg="\n";
		methodCtx.getParam(3).setValue(strMsg+strData+endmsg);
	}
}