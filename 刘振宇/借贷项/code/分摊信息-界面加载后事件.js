/*
var imp = JavaImporter();
imp.importPackage(Packages.com.kingdee.eas.util);
imp.importPackage(Packages.com.kingdee.eas.util.client);
imp.importPackage(Packages.com.kingdee.eas.common.client);
imp.importPackage(Packages.com.kingdee.bos.metadata.entity);
imp.importPackage(Packages.com.kingdee.bos.metadata.query.util);
imp.importPackage(Packages.com.kingdee.bos.dao.query);
imp.importPackage(Packages.net.sf.json.util);
*/
// with (imp) {
/**
 * 运行时函数类
 */


Manager.setFLContHide("kdtEntrys");
Manager.setFLContHide("kdtAssEntrys");
// pluginCtx.getKDTable("kdtEntrys").getParent().getParent().getAddNewLineButton().setVisible(false);
// pluginCtx.getKDTable("kdtAssEntrys").getParent().getParent().getAddNewLineButton().setVisible(false);
// pluginCtx.getKDTable("kdtEntrys").getParent().getParent().getInsertLineButton().setVisible(false);
// pluginCtx.getKDTable("kdtAssEntrys").getParent().getParent().getInsertLineButton().setVisible(false);
// pluginCtx.getKDTable("kdtEntrys").getParent().getParent().getRemoveLinesButton().setVisible(false);
// pluginCtx.getKDTable("kdtAssEntrys").getParent().getParent().getRemoveLinesButton().setVisible(false);
pluginCtx.getKDBizPromptBox("prmtKH").addDataChangeListener(function (e) {

    var ss = Manager.getF7FieldDataAttr("prmtKH", 'id');
    Manager.alert(ss)
    Manager.alert(Manager.getF7FieldDataAttr("prmtKH", 'number'));

    // com.kingdee.eas.util.client.MsgBox.showInfo(ss);
    // var obj = pluginCtx.getKDBizPromptBox("prmtKH")
    // var kh = pluginCtx.getKDBizPromptBox("prmtKH").getValue();
    // // var string = JSONUtils.valueToString(obj)
    // com.kingdee.eas.util.client.MsgBox.showInfo(kh.get("number"));
    // com.kingdee.eas.util.client.MsgBox.showInfo(kh.get("id"));
});
// }