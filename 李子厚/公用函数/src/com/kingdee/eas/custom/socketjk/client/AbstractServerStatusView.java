/**
 * output package name
 */
package com.kingdee.eas.custom.socketjk.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractServerStatusView extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractServerStatusView.class);
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnStartServer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnStopServer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClientTest;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnClient;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRefresh;
    protected StartServer startServer = null;
    protected StopServer stopServer = null;
    protected ClientTest clientTest = null;
    protected ConnectServer connectServer = null;
    protected Refresh refresh = null;
    /**
     * output class constructor
     */
    public AbstractServerStatusView() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractServerStatusView.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //startServer
        this.startServer = new StartServer(this);
        getActionManager().registerAction("startServer", startServer);
         this.startServer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //stopServer
        this.stopServer = new StopServer(this);
        getActionManager().registerAction("stopServer", stopServer);
         this.stopServer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //clientTest
        this.clientTest = new ClientTest(this);
        getActionManager().registerAction("clientTest", clientTest);
         this.clientTest.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //connectServer
        this.connectServer = new ConnectServer(this);
        getActionManager().registerAction("connectServer", connectServer);
         this.connectServer.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //refresh
        this.refresh = new Refresh(this);
        getActionManager().registerAction("refresh", refresh);
         this.refresh.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnStartServer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnStopServer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnClientTest = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnClient = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRefresh = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDTable1.setName("kDTable1");
        this.btnStartServer.setName("btnStartServer");
        this.btnStopServer.setName("btnStopServer");
        this.btnClientTest.setName("btnClientTest");
        this.btnClient.setName("btnClient");
        this.btnRefresh.setName("btnRefresh");
        // CoreUI		
        this.btnPageSetup.setVisible(false);		
        this.btnCloud.setVisible(false);		
        this.btnXunTong.setVisible(false);		
        this.kDSeparatorCloud.setVisible(false);		
        this.menuItemPageSetup.setVisible(false);		
        this.menuItemCloudFeed.setVisible(false);		
        this.menuItemCloudScreen.setEnabled(false);		
        this.menuItemCloudScreen.setVisible(false);		
        this.menuItemCloudShare.setVisible(false);		
        this.kdSeparatorFWFile1.setVisible(false);
        // kDTable1

        

        // btnStartServer
        this.btnStartServer.setAction((IItemAction)ActionProxyFactory.getProxy(startServer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnStartServer.setText(resHelper.getString("btnStartServer.text"));		
        this.btnStartServer.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_startupserver"));
        // btnStopServer
        this.btnStopServer.setAction((IItemAction)ActionProxyFactory.getProxy(stopServer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnStopServer.setText(resHelper.getString("btnStopServer.text"));		
        this.btnStopServer.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_stopserver"));
        // btnClientTest
        this.btnClientTest.setAction((IItemAction)ActionProxyFactory.getProxy(clientTest, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClientTest.setText(resHelper.getString("btnClientTest.text"));		
        this.btnClientTest.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_testappserver"));		
        this.btnClientTest.setVisible(false);
        // btnClient
        this.btnClient.setAction((IItemAction)ActionProxyFactory.getProxy(connectServer, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnClient.setText(resHelper.getString("btnClient.text"));		
        this.btnClient.setVisible(false);
        // btnRefresh
        this.btnRefresh.setAction((IItemAction)ActionProxyFactory.getProxy(refresh, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRefresh.setText(resHelper.getString("btnRefresh.text"));		
        this.btnRefresh.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_refresh"));
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        kDTable1.setBounds(new Rectangle(9, 7, 1000, 617));
        this.add(kDTable1, new KDLayout.Constraints(9, 7, 1000, 617, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemCloudShare);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnStartServer);
        this.toolBar.add(btnStopServer);
        this.toolBar.add(btnClientTest);
        this.toolBar.add(btnClient);
        this.toolBar.add(btnRefresh);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.custom.socketjk.app.ServerStatusViewHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    	

    /**
     * output startServer_actionPerformed method
     */
    public void startServer_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output stopServer_actionPerformed method
     */
    public void stopServer_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output clientTest_actionPerformed method
     */
    public void clientTest_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output connectServer_actionPerformed method
     */
    public void connectServer_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output refresh_actionPerformed method
     */
    public void refresh_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareStartServer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareStartServer() {
    	return false;
    }
	public RequestContext prepareStopServer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareStopServer() {
    	return false;
    }
	public RequestContext prepareClientTest(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareClientTest() {
    	return false;
    }
	public RequestContext prepareConnectServer(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareConnectServer() {
    	return false;
    }
	public RequestContext prepareRefresh(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareRefresh() {
    	return false;
    }

    /**
     * output StartServer class
     */     
    protected class StartServer extends ItemAction {     
    
        public StartServer()
        {
            this(null);
        }

        public StartServer(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("StartServer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("StartServer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("StartServer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractServerStatusView.this, "StartServer", "startServer_actionPerformed", e);
        }
    }

    /**
     * output StopServer class
     */     
    protected class StopServer extends ItemAction {     
    
        public StopServer()
        {
            this(null);
        }

        public StopServer(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("StopServer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("StopServer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("StopServer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractServerStatusView.this, "StopServer", "stopServer_actionPerformed", e);
        }
    }

    /**
     * output ClientTest class
     */     
    protected class ClientTest extends ItemAction {     
    
        public ClientTest()
        {
            this(null);
        }

        public ClientTest(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ClientTest.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ClientTest.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ClientTest.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractServerStatusView.this, "ClientTest", "clientTest_actionPerformed", e);
        }
    }

    /**
     * output ConnectServer class
     */     
    protected class ConnectServer extends ItemAction {     
    
        public ConnectServer()
        {
            this(null);
        }

        public ConnectServer(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ConnectServer.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ConnectServer.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ConnectServer.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractServerStatusView.this, "ConnectServer", "connectServer_actionPerformed", e);
        }
    }

    /**
     * output Refresh class
     */     
    protected class Refresh extends ItemAction {     
    
        public Refresh()
        {
            this(null);
        }

        public Refresh(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("Refresh.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("Refresh.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("Refresh.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractServerStatusView.this, "Refresh", "refresh_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.custom.socketjk.client", "ServerStatusView");
    }




}