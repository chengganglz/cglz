var ctx = pluginCtx.getUIContext().get("sysContext");
var table = pluginCtx.getKDTable("tblMain");
var siz = table.getSelectManager().size();
//com.kingdee.eas.util.client.MsgBox.showInfo(siz);
if (siz > 0) {
    var selnum = table.getSelectManager().get(0).getBeginRow();
    var loanid = table.getRow(selnum).getCell("loanid").getValue();
    //com.kingdee.eas.util.client.MsgBox.showInfo(fid);
    ctx.setProperty("bbb", loanid);
    var uiContext = new com.kingdee.eas.common.client.UIContext(pluginCtx.getUI());
    uiContext.put(com.kingdee.eas.common.client.UIContext.ID, loanid);
    var uiName = "com.kingdee.eas.fm.fin.client.LoanEditUI";
    var uiWindow = com.kingdee.bos.ui.face.UIFactory.createUIFactory(com.kingdee.eas.common.client.UIFactoryName.MODEL).create(uiName, uiContext, null,
        com.kingdee.eas.common.client.OprtState.VIEW);
    uiWindow.show();
} else {
    com.kingdee.eas.util.client.MsgBox.showInfo("未选中行");
}


var easImporter = JavaImporter();
easImporter.importPackage(Packages.com.kingdee.bos.ctrl.extendcontrols);
easImporter.importPackage(Packages.com.kingdee.bos.metadata.entity);
easImporter.importPackage(Packages.com.kingdee.bos.metadata.query.util);
easImporter.importPackage(Packages.com.kingdee.bos.util);
with(easImporter) {
    var filterInfo = new com.kingdee.bos.metadata.entity.FilterInfo();
    var company = pluginCtx.getUIContext().get("sysContext").getCurrentCtrlUnit();
    var companyId = company.getId().toString();

    //var str = "'"+companyId+"'";
    /* while(companyId!=null){
        var sql="select * from T_ORG_CtrlUnit where fid = '"+companyId+"'";
        var sqlExecutor=new com.kingdee.bos.dao.query.SQLExecutor(sql);
        var rowSet=sqlExecutor.executeSQL();
        if(rowSet.next()){
            companyId = rowSet.getString("FPARENTID");
            if(companyId!=null){
                str += ","+"'"+companyId+"'";
            }
            }else{
                companyId = null;
                }
        } */
    var ctx = pluginCtx.getUIContext().get("sysContext");
    var aaa = ctx.getProperty("aaa");
    //com.kingdee.eas.util.client.MsgBox.showInfo(aaa);
    filterInfo.getFilterItems().add(new com.kingdee.bos.metadata.entity.FilterItemInfo("MainConLoan.id", aaa, com.kingdee.bos.metadata.query.util.CompareType.EQUALS));
    //com.kingdee.eas.util.client.MsgBox.showWarning(str);
    methodCtx.setResultValue(filterInfo);
}
//弹出单据界面
var ctx=pluginCtx.getUIContext().get("sysContext");
var table=pluginCtx.getKDTable("tblMain");
var siz=table.getSelectManager().size();
//com.kingdee.eas.util.client.MsgBox.showInfo(siz);
if(siz>0){
	var selnum=table.getSelectManager().get(0).getBeginRow();
	var fid=table.getRow(selnum).getCell("id").getValue();
	var uiContext = new com.kingdee.eas.common.client.UIContext(pluginCtx.getUI());
	uiContext.put(com.kingdee.eas.common.client.UIContext.ID, fid);
	var uiName = "com.kingdee.eas.fm.fin.client.LoanEditUI";
	var uiWindow = com.kingdee.bos.ui.face.UIFactory.createUIFactory(com.kingdee.eas.common.client.UIFactoryName.MODEL).create(uiName, uiContext, null,
                    com.kingdee.eas.common.client.OprtState.VIEW);
	uiWindow.show();
}else{
	com.kingdee.eas.util.client.MsgBox.showInfo("未选中行");
	}