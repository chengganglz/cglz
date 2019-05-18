/*
var imp = JavaImporter();
imp.importPackage(Packages.com.kingdee.eas.util);
imp.importPackage(Packages.com.kingdee.eas.util.client);
imp.importPackage(Packages.com.kingdee.eas.common.client);
imp.importPackage(Packages.com.kingdee.bos.metadata.entity);
imp.importPackage(Packages.com.kingdee.bos.metadata.query.util);
imp.importPackage(Packages.com.kingdee.bos.dao.query);
imp.importPackage(Packages.net.sf.json.util);
with (imp) {
}
*/
/**
 * 公共接口管理器
 */
var Manager = {
    /**
    * 设置分录隐藏控制按钮
    * @param {string} name 分录控件名
    */
    setFLContHide: function (name) {
        // 设置添加新分录按钮隐藏
        pluginCtx.getKDTable(name).getParent().getParent().getAddNewLineButton().setVisible(false);
        // 设置插入新分录按钮隐藏
        pluginCtx.getKDTable(name).getParent().getParent().getInsertLineButton().setVisible(false);
        // 设置移除分录按钮隐藏
        pluginCtx.getKDTable(name).getParent().getParent().getRemoveLinesButton().setVisible(false);
    },
    /**
     * 弹出框提示
     * @param {string|number} value 
     */
    alert: function (value) {
        com.kingdee.eas.util.client.MsgBox.showInfo(value);
    },
    /**
     * 获取f7字段数据对象的属性
     * @param {string} name f7 控件名称
     * @param {string} attrName 要获取的属性名称
     */
    getF7FieldDataAttr: function (name, attrName) {
        return pluginCtx.getKDBizPromptBox(name).getValue().get(attrName);
    },
    /**
     * 设置指定表格下指定列不可编辑
     * @param {String} tableName 表格控件名称 
     * @param {String} columnName 列名称
     */
    setColumnReadOnly: function (tableName, columnName) {
        var tableEle = pluginCtx.getKDTable(tableName);
        tableEle.getColumn(columnName).getStyleAttributes().setLocked(true);
    },
    propSendParams:function(){
        var uiContext = new UIContext(ui); //上下文
        uiContext.put("billModel", coll.get(0));// 这个是在新的页面中设置过滤值的方式
        var win = null;
        var uiFactoryName = UIFactoryName.NEWWIN; //页签的方式打开UI，NEWWIN时打开新窗口
        var className = SplitBillDetailUI.class.getName(); //UI界面对应的类名，如：DemoListUI.class.getName()
    
        win = UIFactory.createUIFactory(uiFactoryName).create(className, uiContext);
    },
    /**
     * 事件监听
     */
    EventListener: {
        /**
         * 表格类
         */
        Table: {
            /**
             * 
             * @param {String} tableName 表格名称
             * @param {function(row,col,table)} Func 值更新后回调函数
             */
            valueChange: function (tableName,Func) {
                //表格增加值改变事件
                var table = pluginCtx.getKDTable("kdtEntrys");
                table.addKDTPropertyChangeListener(function (event, methodName) {
                    if (methodName == "propertyChange") {
                        var row = event.getRowIndex();
                        var col = event.getColIndex();
                        Func(row,col,table)
                        //当第五列的值改变时设置test列的值为00
                        // if (col == 4) {
                        //     table.getRow(row).getCell("test").setValue("00");
                        // }
                    }
                });
            }
        }
    }
}