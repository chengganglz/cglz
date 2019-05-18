var imp = JavaImporter();
with (imp) {
    // 客户控件
    var khEle = pluginCtx.getKDBizPromptBox("prmtKH");
    // 判断点击时选择了客户
    var khDataObj = khEle.getValue();
    if (khDataObj != undefined && khDataObj != null) {
        // 客户id
        var khId = khDataObj.get("id");
        // 客户编码
        var khNumber = khDataObj.get("number");

        com.kingdee.eas.util.client.MsgBox.showInfo("客户id" + khId);
        com.kingdee.eas.util.client.MsgBox.showInfo("客户编码" + khNumber);

        var uiContext = new com.kingdee.eas.common.client.UIContext(pluginCtx.getUI());
        var uiName = "com.kingdee.eas.custom.client.JDXTZDListUI";
        
        var uiWindow = com.kingdee.bos.ui.face.UIFactory.createUIFactory(com.kingdee.eas.common.client.UIFactoryName.MODEL).create(uiName, uiContext);
        uiWindow.show();

    } else {
        com.kingdee.eas.util.client.MsgBox.showInfo("请先选择客户");
    }
}