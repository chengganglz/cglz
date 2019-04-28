package com.kingdee.eas.custom.socketjk.client;

import com.kingdee.eas.base.uict.client.AbstractSelectBizUI;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.MetaDataTypeList;
import com.kingdee.bos.metadata.bizunit.BizUnitInfo;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.resource.BizEnumInfo;
import com.kingdee.bos.metadata.view.MetaDataBriefInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.uict.BizConfigureItemInfo;
import com.kingdee.eas.base.uict.client.util.UictUtils;
import com.kingdee.eas.common.SubSystemUtils;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;
import java.awt.event.ActionEvent;
import java.util.Map;
import org.apache.log4j.Logger;
public class NewSelectBizUI 
extends AbstractSelectBizUI {
	private static final Logger logger = CoreUIObject.getLogger(NewSelectBizUI.class);
	  private static final String BIZUNIT_VIEWNAME = "com_kingdee_eas_base_bizunit";
	  private static final String BIZENUM_VIEWNAME = "com_kingdee_eas_base_enum";
	  private static final String BIZENTITY_VIEWNAME = "com_kingdee_eas_base_subsystemEntity";
	  private static final String BIZUI_VIEWNAME = "com_kingdee_eas_base_ui";
	  private static final String BIZQUERY_VIEWNAME = "com_kingdee_eas_base_query";
	  private static final String BIZFUNCTION_VIEWNAME = "com_kingdee_eas_base_function";
	  private static final String BIZPERMISSION_VIEWNAME = "com_kingdee_eas_base_permission";
	  private static final String BIZFACADE_VIEWNAME = "";
	  public static final String SELECT_BIZ_TYPE = "selectBizType";
	  public static final String BASEDATA = "basedata";
	  public static final String BIZENUM = "bizenum";
	  public static final String BIZENTITY = "bizentity";
	  public static final String BIZUI = "bizui";
	  public static final String BIZFUNCTION = "bizfunction";
	  public static final String BIZQUERY = "bizquery";
	  public static final String BIZFACADE = "bizfacade";
	  public static final String BIZPERMISSION = "bizpermission";
	  private String CURRENT_TYPE = null;
	  private String EXIT_STATUS = null;
	  public static final String CONFIRM_EXIT = "confirm";
	  public static final String CANCEL_EXIT = "cancel";
	  private BizConfigureItemInfo item = new BizConfigureItemInfo();
	  private Object value = null;
	  
	  public NewSelectBizUI()
	    throws Exception
	  {}
	  
	  public void setItem(BizConfigureItemInfo item)
	  {
	    this.item = item;
	  }
	  
	  public BizConfigureItemInfo getItme()
	  {
	    return this.item;
	  }
	  
	  public void storeFields()
	  {
	    super.storeFields();
	  }
	  
	  public void onLoad()
	    throws Exception
	  {
	    super.onLoad();
	    setUITitle("选择业务单元");
	    

	    Object object = getUIContext().get("selectBizType");
	    if (object != null)
	    {
	      String type = (String)object;
	      DefaultKingdeeTreeNode root = null;
	      if ("basedata".equals(type))
	      {
	        root = getBaseData();this.CURRENT_TYPE = "basedata";
	      }
	      else if ("bizenum".equals(type))
	      {
	        root = getBizEnum();this.CURRENT_TYPE = "bizenum";
	      }
	      else if ("bizentity".equals(type))
	      {
	        root = getEntity();this.CURRENT_TYPE = "bizentity";
	      }
	      else if ("bizui".equals(type))
	      {
	        root = getUIObject();this.CURRENT_TYPE = "bizui";
	      }
	      else if ("bizquery".equals(type))
	      {
	        root = getQuery();this.CURRENT_TYPE = "bizquery";
	      }
	      else if ("bizfunction".equals(type))
	      {
	        root = getFunction();this.CURRENT_TYPE = "bizfunction";
	      }
	      else if ("bizfacade".equals(type))
	      {
	        root = getFacade();this.CURRENT_TYPE = "bizfacade";
	      }
	      else if ("bizpermission".equals(type))
	      {
	        root = getPermission();this.CURRENT_TYPE = "bizpermission";
	      }
	      if (root != null) {
	        ((KingdeeTreeModel)this.baseDataTree.getModel()).setRoot(root);
	      }
	    }
	  }
	  
	  private DefaultKingdeeTreeNode getBaseData()
	  {
	    return SubSystemUtils.getKDTreeNode(UictUtils.getBaseDataNode(SubSystemUtils.getSubSystemByName("com_kingdee_eas_base_bizunit", MetaDataTypeList.BIZUNIT)));
	  }
	  
	  public DefaultKingdeeTreeNode getBizEnum()
	  {
	    return SubSystemUtils.getKDTreeNode(SubSystemUtils.getSubSystemByName("com_kingdee_eas_base_enum", MetaDataTypeList.BIZENUM));
	  }
	  
	  public DefaultKingdeeTreeNode getEntity()
	  {
	    return SubSystemUtils.getKDTreeNode(SubSystemUtils.getSubSystemByName("com_kingdee_eas_base_subsystemEntity", MetaDataTypeList.ENTITY));
	  }
	  
	  public DefaultKingdeeTreeNode getUIObject()
	  {
	    return SubSystemUtils.getKDTreeNode(SubSystemUtils.getSubSystemByName("com_kingdee_eas_base_ui", MetaDataTypeList.UIOBJECT));
	  }
	  
	  public DefaultKingdeeTreeNode getQuery()
	  {
	    return SubSystemUtils.getKDTreeNode(SubSystemUtils.getSubSystemByName("com_kingdee_eas_base_query", MetaDataTypeList.JOINQUERY));
	  }
	  
	  public DefaultKingdeeTreeNode getFunction()
	  {
	    return SubSystemUtils.getKDTreeNode(SubSystemUtils.getSubSystemByName("com_kingdee_eas_base_function", MetaDataTypeList.FUNCTION));
	  }
	  
	  public DefaultKingdeeTreeNode getFacade()
	  {
	    return SubSystemUtils.getKDTreeNode(SubSystemUtils.getSubSystemByName("", MetaDataTypeList.FACADE));
	  }
	  
	  public DefaultKingdeeTreeNode getPermission()
	  {
	    return SubSystemUtils.getKDTreeNode(SubSystemUtils.getSubSystemByName("com_kingdee_eas_base_permission", MetaDataTypeList.PERMISSION));
	  }
	  
	  private MetaDataBriefInfo getMetaDataBrief()
	  {
	    DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)this.baseDataTree.getLastSelectedPathComponent();
	    if ((treeNode != null) && (treeNode.getUserObject() != null) && ((treeNode.getUserObject() instanceof MetaDataBriefInfo))) {
	      return (MetaDataBriefInfo)treeNode.getUserObject();
	    }
	    return null;
	  }
	  
	  private void setDataSource()
	  {
	    EntityObjectInfo entity = null;
	    MetaDataBriefInfo mdInfo = getMetaDataBrief();
	    if (mdInfo != null)
	    {
	      BizUnitInfo buInfo = UictUtils.loadBizUnit(mdInfo.getMetaDataPK());
	      if (buInfo != null)
	      {
	        String sEntityPK = buInfo.getString("entityPK");
	        if (!StringUtils.isEmpty(sEntityPK))
	        {
	          entity = UictUtils.loadEntity(new MetaDataPK(sEntityPK));
	          if (this.item != null) {
	            this.item.setDataSource(entity);
	          }
	        }
	        else
	        {
	          MsgBox.showInfo(this, "加载业务单元元数据失败 ， 请检查  " + mdInfo.getFullName() + " 是否存在 ， 或者是否子系统树未更新");
	        }
	      }
	    }
	  }
	  
	  private void setEnumType()
	  {
	    MetaDataBriefInfo mdInfo = getMetaDataBrief();
	    BizEnumInfo bizEnum = UictUtils.loadBizEnum(mdInfo.getMetaDataPK());
	    if (this.item != null) {
	      this.item.setEnumType(bizEnum);
	    }
	  }
	  
	  protected void okBtn_actionPerformed(ActionEvent e)
	    throws Exception
	  {
	    if (this.CURRENT_TYPE == "basedata")
	    {
	      setDataSource();
	    }
	    else if (this.CURRENT_TYPE == "bizenum")
	    {
	      setEnumType();
	    }
	    else if (this.CURRENT_TYPE == "bizentity")
	    {
	      MetaDataBriefInfo mdInfo = getMetaDataBrief();
	      if (mdInfo != null) {
	        this.value = UictUtils.loadEntity(mdInfo.getMetaDataPK());
	      }
	    }
	    else if (this.CURRENT_TYPE == "bizui")
	    {
	      MetaDataBriefInfo mdInfo = getMetaDataBrief();
	      if (mdInfo != null) {
	        this.value = UictUtils.loadUI(mdInfo.getMetaDataPK());
	      }
	    }
	    else if (this.CURRENT_TYPE == "bizquery")
	    {
	      MetaDataBriefInfo mdInfo = getMetaDataBrief();
	      if (mdInfo != null) {
	        this.value = UictUtils.loadQuery(mdInfo.getMetaDataPK());
	      }
	    }
	    else if (this.CURRENT_TYPE == "bizfunction")
	    {
	      MetaDataBriefInfo mdInfo = getMetaDataBrief();
	      if (mdInfo != null) {
	        this.value = UictUtils.loadFunction(mdInfo.getMetaDataPK());
	      }
	    }
	    else if (this.CURRENT_TYPE == "bizfacade")
	    {
	      MetaDataBriefInfo mdInfo = getMetaDataBrief();
	      if (mdInfo != null) {
	        this.value = UictUtils.loadFacade(mdInfo.getMetaDataPK());
	      }
	    }
	    else if (this.CURRENT_TYPE == "bizpermission")
	    {
	      MetaDataBriefInfo mdInfo = getMetaDataBrief();
	      if (mdInfo != null) {
	        this.value = UictUtils.loadPermission(mdInfo.getMetaDataPK());
	      }
	    }
	    this.EXIT_STATUS = "confirm";
	    disposeUIWindow();
	  }
	  
	  protected void cancelBtn_actionPerformed(ActionEvent e)
	    throws Exception
	  {
	    this.EXIT_STATUS = "cancel";
	    disposeUIWindow();
	  }
	  
	  public String getExitStatus()
	  {
	    return this.EXIT_STATUS;
	  }
	  
	  public Object getReturnValue()
	  {
	    return this.value;
	  }
}
