var imp = JavaImporter();
imp.importPackage(Packages.com.kingdee.eas.util);
imp.importPackage(Packages.com.kingdee.eas.util.client);
imp.importPackage(Packages.com.kingdee.eas.common.client);
imp.importPackage(Packages.com.kingdee.bos.metadata.entity);
imp.importPackage(Packages.com.kingdee.bos.metadata.query.util);
imp.importPackage(Packages.com.kingdee.bos.dao.query);
with (imp) {
    /***********************设置可分摊金额、已分摊金额 不可编辑 开始*************************/
    var tableEle = pluginCtx.getKDTable("kdtEntrys");
    tableEle.getColumn("KFTJE").getStyleAttributes().setLocked(true);
    tableEle.getColumn("YFTJE").getStyleAttributes().setLocked(true);
    /************************设置可分摊金额、已分摊金额 不可编辑 结束***********************/
    tableEle.addKDTPropertyChangeListener(function (event, methodName) {
        if (methodName == "propertyChange") {
            var row = event.getRowIndex();
            var col = event.getColIndex();
             
             com.kingdee.eas.util.client.MsgBox.showInfo(event.getValue());
            if(col == 2){
                tableEle.getRow(row).getCell("KFTJE").setValue("0");
                tableEle.getRow(row).getCell("YFTJE").setValue("0");
            }
        }
    });
}