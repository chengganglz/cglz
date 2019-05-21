var imp = JavaImporter();
imp.importPackage(Packages.com.kingdee.eas.util.app);
imp.importPackage(Packages.java.math);
imp.importPackage(Packages.java.util);
imp.importPackage(Packages.java.text);
with(imp) {
	pluginCtx.getKDCheckBox("chkiscdtx").setSelected(true);
	var pkjizhundate= pluginCtx.getKDDatePicker("pkjizhundate").getValue();
	if(pkjizhundate==null||"".equals(pkjizhundate)){
		com.kingdee.eas.util.client.MsgBox.showInfo("请先填写基准日，然后计算承兑贴息");
		com.kingdee.eas.util.SysUtil.abort();
	}
	var pkisenddate= pluginCtx.getKDDatePicker("pkisenddate").getValue();
	if(pkisenddate==null||"".equals(pkisenddate)){
		com.kingdee.eas.util.client.MsgBox.showInfo("请先填写到期日，然后计算承兑贴息");
		com.kingdee.eas.util.SysUtil.abort();
	}
	if(pkjizhundate.after(pkisenddate)){
		com.kingdee.eas.util.client.MsgBox.showInfo("基准日不能晚于到期日");
		com.kingdee.eas.util.SysUtil.abort();
	}
	var dateSub=pkisenddate.getTime()-pkjizhundate.getTime();
	var days=dateSub/(1000*60*60*24);
	var nowDate=new Date();
	var skjine=pluginCtx.getKDFormattedTextField("txtActRecAmt").getValue();
	if(skjine!=null){
		skjine=new BigDecimal(skjine);
	}else{
		com.kingdee.eas.util.client.MsgBox.showInfo("请先录入收款金额，然后再计算承兑贴息");
		com.kingdee.eas.util.SysUtil.abort();
	}
	var format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	var nowDateStr=format.format(nowDate);
	var sql = "SELECT * FROM CT_CUS_RTXL where cfshengxiaodate <= to_date('"+nowDateStr+"') and cfshixiaodate>=to_date('"+nowDateStr+"') ";
	//com.kingdee.eas.util.client.MsgBox.showInfo(sql);
	var row = null;
	row = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
	var cfrtxl =null;
	if (row.next()) {
		cfrtxl = row.getBigDecimal("cfrtxl");
		
	}
	if(days<=30){
		days=0;
	}else{
		days=days-30;
	}
	var daysBigDecimal=new BigDecimal(days);
	var cdtx=skjine.multiply(daysBigDecimal).multiply(cfrtxl);
	//com.kingdee.eas.util.client.MsgBox.showInfo("承兑贴息"+cdtx.toString());
	cdtx=cdtx.divide(new BigDecimal("100"),5,BigDecimal.ROUND_HALF_UP);
	//com.kingdee.eas.util.client.MsgBox.showInfo("承兑贴息"+cdtx.toString());
	pluginCtx.getKDFormattedTextField("txttiexinum").setValue(cdtx);

}