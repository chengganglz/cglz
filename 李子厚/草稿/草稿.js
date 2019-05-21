var imp = JavaImporter();
imp.importPackage(Packages.com.kingdee.eas.util);
imp.importPackage(Packages.com.kingdee.eas.util.client);
imp.importPackage(Packages.com.kingdee.eas.common.client);
imp.importPackage(Packages.com.kingdee.bos.metadata.entity);
imp.importPackage(Packages.com.kingdee.bos.metadata.query.util);
imp.importPackage(Packages.com.kingdee.bos.dao.query);
imp.importPackage(Packages.net.sf.json.util);
imp.importPackage(Packages.java.util);
with(imp) {
    pluginCtx.getKDWorkButton("btnCopy").setVisible(false);
    pluginCtx.getKDWorkButton("btnAttachment").setVisible(false);
    pluginCtx.getKDWorkButton("btnPrint").setVisible(false);
    pluginCtx.getKDWorkButton("btnPrintPreview").setVisible(false);
    
}