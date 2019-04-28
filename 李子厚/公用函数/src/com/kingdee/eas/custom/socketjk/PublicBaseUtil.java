package com.kingdee.eas.custom.socketjk;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import oracle.jdbc.OracleResultSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.ejb.EJBFactory;
import com.kingdee.bos.metadata.bot.BOTMappingFactory;
import com.kingdee.bos.metadata.bot.BOTMappingInfo;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.bot.DefineSysEnum;
import com.kingdee.bos.metadata.bot.IBOTMapping;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.btp.BTPManagerFactory;
import com.kingdee.eas.base.btp.BTPTransformResult;
import com.kingdee.eas.base.btp.IBTPManager;
import com.kingdee.eas.base.codingrule.CodingRuleCollection;
import com.kingdee.eas.base.codingrule.CodingRuleFactory;
import com.kingdee.eas.base.codingrule.CodingRuleInfo;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRule;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.app.CodingRuleManagerControllerBean;
import com.kingdee.eas.base.permission.IUser;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.AccountBankCollection;
import com.kingdee.eas.basedata.assistant.AccountBankFactory;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.IAccountBank;
import com.kingdee.eas.basedata.assistant.ICurrency;
import com.kingdee.eas.basedata.assistant.IMeasureUnit;
import com.kingdee.eas.basedata.assistant.IMeasureUnitGroup;
import com.kingdee.eas.basedata.assistant.IPaymentType;
import com.kingdee.eas.basedata.assistant.IPeriod;
import com.kingdee.eas.basedata.assistant.ISettlementType;
import com.kingdee.eas.basedata.assistant.IVoucherType;
import com.kingdee.eas.basedata.assistant.MeasureUnitCollection;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.assistant.MeasureUnitGroupCollection;
import com.kingdee.eas.basedata.assistant.MeasureUnitGroupFactory;
import com.kingdee.eas.basedata.assistant.MeasureUnitGroupInfo;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.assistant.PaymentTypeCollection;
import com.kingdee.eas.basedata.assistant.PaymentTypeFactory;
import com.kingdee.eas.basedata.assistant.PaymentTypeInfo;
import com.kingdee.eas.basedata.assistant.PeriodCollection;
import com.kingdee.eas.basedata.assistant.PeriodFactory;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeCollection;
import com.kingdee.eas.basedata.assistant.SettlementTypeFactory;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.assistant.VoucherTypeCollection;
import com.kingdee.eas.basedata.assistant.VoucherTypeFactory;
import com.kingdee.eas.basedata.assistant.VoucherTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewCollection;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.IAccountView;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeFactory;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeGroupCollection;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeGroupFactory;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeGroupInfo;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo;
import com.kingdee.eas.basedata.master.auxacct.IGeneralAsstActType;
import com.kingdee.eas.basedata.master.auxacct.IGeneralAsstActTypeGroup;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupCollection;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardCollection;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerCollection;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoCollection;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.master.cssp.ICSSPGroup;
import com.kingdee.eas.basedata.master.cssp.ICSSPGroupStandard;
import com.kingdee.eas.basedata.master.cssp.ICustomer;
import com.kingdee.eas.basedata.master.cssp.ICustomerCompanyInfo;
import com.kingdee.eas.basedata.master.cssp.ISupplier;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.material.IMaterial;
import com.kingdee.eas.basedata.master.material.IMaterialGroup;
import com.kingdee.eas.basedata.master.material.IMaterialGroupStandard;
import com.kingdee.eas.basedata.master.material.IQuotaPolicy;
import com.kingdee.eas.basedata.master.material.MaterialCollection;
import com.kingdee.eas.basedata.master.material.MaterialFactory;
import com.kingdee.eas.basedata.master.material.MaterialGroupCollection;
import com.kingdee.eas.basedata.master.material.MaterialGroupFactory;
import com.kingdee.eas.basedata.master.material.MaterialGroupInfo;
import com.kingdee.eas.basedata.master.material.MaterialGroupStandardCollection;
import com.kingdee.eas.basedata.master.material.MaterialGroupStandardFactory;
import com.kingdee.eas.basedata.master.material.MaterialGroupStandardInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.master.material.QuotaPolicyCollection;
import com.kingdee.eas.basedata.master.material.QuotaPolicyFactory;
import com.kingdee.eas.basedata.master.material.QuotaPolicyInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitCollection;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.IAdminOrgUnit;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.basedata.org.ICostCenterOrgUnit;
import com.kingdee.eas.basedata.org.ICtrlUnit;
import com.kingdee.eas.basedata.org.IFullOrgUnit;
import com.kingdee.eas.basedata.org.IOrgUnit;
import com.kingdee.eas.basedata.org.ISaleOrgUnit;
import com.kingdee.eas.basedata.org.IStorageOrgUnit;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.basedata.org.OrgUnitFactory;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitCollection;
import com.kingdee.eas.basedata.org.SaleOrgUnitFactory;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.basedata.org.StorageOrgUnitCollection;
import com.kingdee.eas.basedata.org.StorageOrgUnitFactory;
import com.kingdee.eas.basedata.org.StorageOrgUnitInfo;
import com.kingdee.eas.basedata.person.IPerson;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.basedata.scm.common.BillTypeCollection;
import com.kingdee.eas.basedata.scm.common.BillTypeFactory;
import com.kingdee.eas.basedata.scm.common.BillTypeInfo;
import com.kingdee.eas.basedata.scm.common.BizTypeCollection;
import com.kingdee.eas.basedata.scm.common.BizTypeFactory;
import com.kingdee.eas.basedata.scm.common.BizTypeInfo;
import com.kingdee.eas.basedata.scm.common.DeliveryTypeCollection;
import com.kingdee.eas.basedata.scm.common.DeliveryTypeFactory;
import com.kingdee.eas.basedata.scm.common.DeliveryTypeInfo;
import com.kingdee.eas.basedata.scm.common.IBillType;
import com.kingdee.eas.basedata.scm.common.IBizType;
import com.kingdee.eas.basedata.scm.common.IDeliveryType;
import com.kingdee.eas.basedata.scm.common.ITransactionType;
import com.kingdee.eas.basedata.scm.common.TransactionTypeCollection;
import com.kingdee.eas.basedata.scm.common.TransactionTypeFactory;
import com.kingdee.eas.basedata.scm.common.TransactionTypeInfo;
import com.kingdee.eas.basedata.scm.im.inv.IInvUpdateType;
import com.kingdee.eas.basedata.scm.im.inv.IWarehouse;
import com.kingdee.eas.basedata.scm.im.inv.InvUpdateTypeCollection;
import com.kingdee.eas.basedata.scm.im.inv.InvUpdateTypeFactory;
import com.kingdee.eas.basedata.scm.im.inv.InvUpdateTypeInfo;
import com.kingdee.eas.basedata.scm.im.inv.WarehouseCollection;
import com.kingdee.eas.basedata.scm.im.inv.WarehouseFactory;
import com.kingdee.eas.basedata.scm.im.inv.WarehouseInfo;
import com.kingdee.eas.basedata.scm.sd.sale.ISaleGroup;
import com.kingdee.eas.basedata.scm.sd.sale.SaleGroupCollection;
import com.kingdee.eas.basedata.scm.sd.sale.SaleGroupFactory;
import com.kingdee.eas.basedata.scm.sd.sale.SaleGroupInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.ep.CoreBillBaseCustomFactory;
import com.kingdee.eas.fi.cas.IReceivingBillType;
import com.kingdee.eas.fi.cas.ReceivingBillTypeCollection;
import com.kingdee.eas.fi.cas.ReceivingBillTypeFactory;
import com.kingdee.eas.fi.cas.ReceivingBillTypeInfo;
import com.kingdee.eas.fi.cas.SourceTypeEnum;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.fm.common.FMException;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.report.util.DBUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
public class PublicBaseUtil {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.custom.socketjk.PublicBaseUtil");
	/**
	 * 判断是否存在编码规则
	 * @param ctx
	 * @param objValue 需要编码的实体
	 * @return boolean 
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static boolean isCodeRuleEnable(Context ctx,IObjectValue objValue) 

	    throws EASBizException, BOSException { 
	    	CompanyOrgUnitInfo  aCompanyOrgUnitInfo = ContextUtil.getCurrentFIUnit(ctx); 
		    String companyId = aCompanyOrgUnitInfo.getId().toString(); 
		
		    ICodingRuleManager codeRuleMgr = null;
		
		    codeRuleMgr = CodingRuleManagerFactory.getLocalInstance(ctx); 
		
		    return codeRuleMgr.isExist(objValue, companyId); 

	    } 

	/**
	 * 获取自动编码 
	 * @param ctx
	 * @param info 需要自动编码的实体
	 * @param orgUnitId 组织ID
	 * @return String 编码
	 * @throws Exception
	 */
	    public  static String getNumberByCodingRule(Context ctx, IObjectValue info, String orgUnitId)
	             throws Exception
	         {
	    		OrgUnitInfo orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
//	   		CodingRuleInfo a= CodingRuleFactory .getLocalInstance(ctx).getCodingRuleInfo("");
	             ICodingRuleManager iCodingRuleManager = null;
	             if(ctx == null)
	                iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
	             else
	                 iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
	            if(iCodingRuleManager.isExist(info, orgUnitId))
	                return iCodingRuleManager.getNumber(info, orgUnitId);
	            else
	            	
	                return null;
	           
	        }
	    
	    
	    /**
	     * 获取自定义核算项目自动编码
	     * @param ctx
	     * @param info 需要自动编码的实体
	     * @param orgUnitId 组织ID
	     * @return String 编码
	     * @throws Exception
	     */
	        public  static String getAssactNumberByCodingRule(Context ctx, IObjectValue info, String orgUnitId)
	                 throws Exception
	             {
//	        		OrgUnitInfo orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
	       		CodingRuleInfo ruleInfo=null;
	       		
	       		EntityViewInfo view = new EntityViewInfo();
	            SelectorItemCollection sic = view.getSelector();
	            sic.addObjectCollection(GlUtils.getCompanySic());
	            sic.add("*");
	 	        sic.add("codingRuleEntrys.*");
	            FilterInfo filter = new FilterInfo();
	            FilterItemCollection fic = filter.getFilterItems();
	            fic.add(new FilterItemInfo("businessObject", "com.kingdee.eas.basedata.master.auxacct.app.GeneralAsstActType"));
	            fic.add(new FilterItemInfo("bindingProperty.propertyName", "group.number"));
	            fic.add(new FilterItemInfo("bindingProperty.propertyValue", "02"));
	            fic.add(new FilterItemInfo("isEnabled", true));
	            fic.add(new FilterItemInfo("groupFirst", true));
	            view.setFilter(filter);
	            
	            ICodingRule iCodingRule = null;
	            if(ctx != null){
	            	iCodingRule = CodingRuleFactory.getLocalInstance(ctx);
	            }else{
	            	iCodingRule = CodingRuleFactory.getRemoteInstance();
	            }
	            CodingRuleCollection codingRuleCollection = iCodingRule.getCodingRuleCollection(view);
	            if (codingRuleCollection.size()>0){
	            	ruleInfo=codingRuleCollection.get(0);
	            }
	            
	            if(ruleInfo==null){
	            	view = new EntityViewInfo();
	                sic = view.getSelector();
	                sic.addObjectCollection(GlUtils.getCompanySic());
	     	        sic.add("*");
	     	        sic.add("codingRuleEntrys.*");
	                filter = new FilterInfo();
	                fic = filter.getFilterItems();
	                fic.add(new FilterItemInfo("businessObject", "com.kingdee.eas.basedata.master.auxacct.app.GeneralAsstActType"));
	                fic.add(new FilterItemInfo("bindingProperty.propertyName", "group.number"));
	                fic.add(new FilterItemInfo("bindingProperty.propertyValue", "02"));
	                fic.add(new FilterItemInfo("isEnabled", true));
	                fic.add(new FilterItemInfo("orgFirst", true));
	                fic.add(new FilterItemInfo("appOrgUnit.id", orgUnitId));
	                view.setFilter(filter);
	                codingRuleCollection = iCodingRule.getCodingRuleCollection(view);
	                if (codingRuleCollection.size()>0){
	                	ruleInfo=codingRuleCollection.get(0);
	                }
	            }
	             
	            if (ruleInfo!=null){
	            	ICodingRuleManager iCodingRuleManager = null;
	                if(ctx == null)
	                   iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
	                else
	                    iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
	            	return iCodingRuleManager.getNumber(info,ruleInfo);
	            }
	               
	            return null;
	        }
	    
	    /**
	     * 获取公司信息
	     * 
	     * 根据公司编码获取公司对象
	     * 
	     * @param ctx 服务器端上下文
	     * @param number 公司编码
	     * @return CompanyOrgUnitInfo 公司对象
	     * @throws BOSException 查询公司信息时可能用引发此异常
	     */
	    public static CompanyOrgUnitInfo getCompany(Context ctx, String number)  {
	    	CompanyOrgUnitInfo orgUnitInfo = null;
	    	try {
	    	EntityViewInfo view = new EntityViewInfo();
	        SelectorItemCollection sic = view.getSelector();
	        sic.addObjectCollection(GlUtils.getCompanySic());
	        FilterInfo filter = new FilterInfo();
	        FilterItemCollection fic = filter.getFilterItems();
	        fic.add(new FilterItemInfo("number", number));
//	        fic.add(new FilterItemInfo("isBizUnit", new Integer(1)));
	        view.setFilter(filter);
	        
	        ICompanyOrgUnit iCompany = null;
	        if(ctx != null){
	        	
					iCompany = CompanyOrgUnitFactory.getLocalInstance(ctx);
				
	        }else{
	        	iCompany = CompanyOrgUnitFactory.getRemoteInstance();
	        }
	        
	        CompanyOrgUnitCollection companyCollection = iCompany.getCompanyOrgUnitCollection(view);
	        
	        if(companyCollection != null && companyCollection.size() != 0){
	            orgUnitInfo = companyCollection.get(0);
	        }
	    	} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return orgUnitInfo;
	    }
	    
	    
	    
	    /**
	     * 获取公司信息
	     * 
	     * 根据公司编码获取公司对象
	     * 
	     * @param ctx 服务器端上下文
	     * @param id 公司Id
	     * @return CompanyOrgUnitInfo 公司对象
	     * @throws BOSException 查询公司信息时可能用引发此异常
	     */
	    public static CompanyOrgUnitInfo getCompanyById(Context ctx, String id)  {
	    	CompanyOrgUnitInfo orgUnitInfo = null;
	    	try {
	    	EntityViewInfo view = new EntityViewInfo();
	        SelectorItemCollection sic = view.getSelector();
	        sic.addObjectCollection(GlUtils.getCompanySic());
	        FilterInfo filter = new FilterInfo();
	        FilterItemCollection fic = filter.getFilterItems();
	        fic.add(new FilterItemInfo("id", id));
	        fic.add(new FilterItemInfo("isBizUnit", new Integer(1)));
	        view.setFilter(filter);
	        
	        ICompanyOrgUnit iCompany = null;
	        if(ctx != null){
	        	
					iCompany = CompanyOrgUnitFactory.getLocalInstance(ctx);
				
	        }else{
	        	iCompany = CompanyOrgUnitFactory.getRemoteInstance();
	        }
	        
	        CompanyOrgUnitCollection companyCollection = iCompany.getCompanyOrgUnitCollection(view);
	        
	        if(companyCollection != null && companyCollection.size() != 0){
	            orgUnitInfo = companyCollection.get(0);
	        }
	    	} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return orgUnitInfo;
	    }
	    
	    /**
	     * 获取成本中心
	     * 
	     * 根据成本中心编码获取成本中心对象
	     * 
	     * @param ctx 服务器端上下文
	     * @param id 成本中心Id
	     * @return CostCenterOrgUnitInfo 成本中心对象
	     * @throws BOSException 查询公司信息时可能用引发此异常
	     */
	    public static CostCenterOrgUnitInfo getCostCenterOrgUnitInfoById(Context ctx, String id)  {
	    	CostCenterOrgUnitInfo costCenterInfo = null;
	    	try {
	    	EntityViewInfo view = new EntityViewInfo();
	        SelectorItemCollection sic = view.getSelector();
	        sic.addObjectCollection(GlUtils.getCompanySic());
	        FilterInfo filter = new FilterInfo();
	        FilterItemCollection fic = filter.getFilterItems();
	        fic.add(new FilterItemInfo("id", id));
//	        fic.add(new FilterItemInfo("isBizUnit", new Integer(1)));
	        view.setFilter(filter);
	        
	        ICostCenterOrgUnit iCostCenterOrgUnit = null;
	        if(ctx != null){
	        	
					iCostCenterOrgUnit = CostCenterOrgUnitFactory.getLocalInstance(ctx);
				
	        }else{
	        	iCostCenterOrgUnit = CostCenterOrgUnitFactory.getRemoteInstance();
	        }
	        
	        CostCenterOrgUnitCollection costCollection = iCostCenterOrgUnit.getCostCenterOrgUnitCollection(view);
	        
	        if(costCollection != null && costCollection.size() != 0){
	        	costCenterInfo = costCollection.get(0);
	        }
	    	} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return costCenterInfo;
	    }
	    /**
	     * 获取行政组织
	     * @param ctx
	     * @param adminID 行政组织ID
	     * @return AdminOrgUnitInfo 行政组织实体
	     * @throws BOSException
	     */
	     public static  AdminOrgUnitInfo getAdminOrgUnitInfoByID(Context ctx, String adminID)  {
	     	
	     	AdminOrgUnitInfo adminOrgUnitInfo = null;
	     	try{
	     		IAdminOrgUnit  iAdminOrgUnit=null;
	     		if(ctx != null){
	     			iAdminOrgUnit=AdminOrgUnitFactory.getLocalInstance(ctx);
	 			}else{
	 				iAdminOrgUnit = AdminOrgUnitFactory.getRemoteInstance();
	 			}
	     		
	     		
	 	    	EntityViewInfo view = new EntityViewInfo();
	 	        SelectorItemCollection sic = view.getSelector();
	 	        sic.add("*");
	 	        sic.add("parent.*");
	 	        FilterInfo filter = new FilterInfo();
	 	        FilterItemCollection fic = filter.getFilterItems();
	 	        fic.add(new FilterItemInfo("id", adminID));
	 	        view.setFilter(filter);
	 	
	 	        AdminOrgUnitCollection adminOrgUnitCollection = iAdminOrgUnit.getAdminOrgUnitCollection(view);
	 	        
	 	        if(adminOrgUnitCollection != null && adminOrgUnitCollection.size() != 0){
	 	        	adminOrgUnitInfo = adminOrgUnitCollection.get(0);
	 	        }
	     	}
	     	catch(Exception ex){
	     		logger.error(ex);
	     	}
	         return adminOrgUnitInfo;
	     }
	     
	     /**
	      * 获取组织单元
	      * @param ctx
	      * @param orgID 组织单元ID
	      * @return FullOrgUnitInfo 组织单元对象
	      * @throws BOSException
	      */
	      public static  FullOrgUnitInfo getFullOrgUnitInfoByID(Context ctx, String orgID)  {
	      	
	      	FullOrgUnitInfo fullOrgUnitInfo = null;
	      	try{
	      		IFullOrgUnit  iFullOrgUnit=null;
	      		if(ctx != null){
	      			iFullOrgUnit=FullOrgUnitFactory.getLocalInstance(ctx);
	  			}else{
	  				iFullOrgUnit = FullOrgUnitFactory.getRemoteInstance();
	  			}
	      		
	      		
	  	    	EntityViewInfo view = new EntityViewInfo();
	  	        SelectorItemCollection sic = view.getSelector();
	  	        sic.add("*");
	  	        sic.add("parent.*");
	  	        FilterInfo filter = new FilterInfo();
	  	        FilterItemCollection fic = filter.getFilterItems();
	  	        fic.add(new FilterItemInfo("id", orgID));
	  	        view.setFilter(filter);
	  	
	  	       FullOrgUnitCollection fullOrgUnitCollection = iFullOrgUnit.getFullOrgUnitCollection(view);
	  	        
	  	        if(fullOrgUnitCollection != null && fullOrgUnitCollection.size() != 0){
	  	        	fullOrgUnitInfo = fullOrgUnitCollection.get(0);
	  	        }
	      	}
	      	catch(Exception ex){
	      		logger.error(ex);
	      	}
	          return fullOrgUnitInfo;
	      }
	     
	     
	     /**
	      * 获取行政组织
	      * @param ctx
	      * @param adminNumber 行政组织编码
	      * @return AdminOrgUnitInfo 行政组织实体
	      * @throws BOSException
	      */
	      public static  AdminOrgUnitInfo getAdminOrgUnitInfoByNumber(Context ctx, String adminNumber)  {
	      	
	      	AdminOrgUnitInfo adminOrgUnitInfo = null;
	      	try{
	      		IAdminOrgUnit  iAdminOrgUnit=null;
	      		if(ctx != null){
	      			iAdminOrgUnit=AdminOrgUnitFactory.getLocalInstance(ctx);
	  			}else{
	  				iAdminOrgUnit = AdminOrgUnitFactory.getRemoteInstance();
	  			}
	      		
	      		
	  	    	EntityViewInfo view = new EntityViewInfo();
	  	        SelectorItemCollection sic = view.getSelector();
	  	        sic.add("*");
	  	        sic.add("parent.*");
	  	        FilterInfo filter = new FilterInfo();
	  	        FilterItemCollection fic = filter.getFilterItems();
	  	        fic.add(new FilterItemInfo("number", adminNumber));
	  	        view.setFilter(filter);
	  	
	  	        AdminOrgUnitCollection adminOrgUnitCollection = iAdminOrgUnit.getAdminOrgUnitCollection(view);
	  	        
	  	        if(adminOrgUnitCollection != null && adminOrgUnitCollection.size() != 0){
	  	        	adminOrgUnitInfo = adminOrgUnitCollection.get(0);
	  	        }
	      	}
	      	catch(Exception ex){
	      		logger.error(ex);
	      	}
	          return adminOrgUnitInfo;
	      }
	    
	      /**
		      * 获取销售组织
		      * @param ctx
		      * @param number 销售组织编码
		      * @return SaleOrgUnitInfo 销售组织实体
		      * @throws BOSException
		      */
		      public static  SaleOrgUnitInfo getSaleOrgUnitInfoByNumber(Context ctx, String number)  {
		      	
		    	  SaleOrgUnitInfo info = null;
		      	try{
		      		ISaleOrgUnit  iSaleOrgUnit=null;
		      		if(ctx != null){
		      			iSaleOrgUnit=SaleOrgUnitFactory.getLocalInstance(ctx);
		  			}else{
		  				iSaleOrgUnit =SaleOrgUnitFactory.getRemoteInstance();
		  			}
		      		
		      		
		  	    	EntityViewInfo view = new EntityViewInfo();
		  	        SelectorItemCollection sic = view.getSelector();
		  	        sic.add("*");
		  	        sic.add("parent.*");
		  	        FilterInfo filter = new FilterInfo();
		  	        FilterItemCollection fic = filter.getFilterItems();
		  	        fic.add(new FilterItemInfo("number", number));
		  	        view.setFilter(filter);
		  	
		  	      SaleOrgUnitCollection infoCollection = iSaleOrgUnit.getSaleOrgUnitCollection(view);
		  	        
		  	        if(infoCollection != null && infoCollection.size() != 0){
		  	        	info = infoCollection.get(0);
		  	        }
		      	}
		      	catch(Exception ex){
		      		logger.error(ex);
		      	}
		          return info;
		      }
		      
		      /**
			      * 获取库存组织
			      * @param ctx
			      * @param number 库存组织编码
			      * @return  StorageOrgUnitInfo 库存组织实体
			      * @throws BOSException
			      */
			      public static  StorageOrgUnitInfo getStorageOrgUnitInfoByNumber(Context ctx, String number)  {
			      	
			    	  StorageOrgUnitInfo info = null;
			      	try{
			      		IStorageOrgUnit  iStorageOrgUnit=null;
			      		if(ctx != null){
			      			iStorageOrgUnit=StorageOrgUnitFactory.getLocalInstance(ctx);
			  			}else{
			  				iStorageOrgUnit =StorageOrgUnitFactory.getRemoteInstance();
			  			}
			      		
			      		
			  	    	EntityViewInfo view = new EntityViewInfo();
			  	        SelectorItemCollection sic = view.getSelector();
			  	        sic.add("*");
			  	        sic.add("parent.*");
			  	        FilterInfo filter = new FilterInfo();
			  	        FilterItemCollection fic = filter.getFilterItems();
			  	        fic.add(new FilterItemInfo("number", number));
			  	        view.setFilter(filter);
			  	
			  	      StorageOrgUnitCollection infoCollection = iStorageOrgUnit.getStorageOrgUnitCollection(view);
			  	        
			  	        if(infoCollection != null && infoCollection.size() != 0){
			  	        	info = infoCollection.get(0);
			  	        }
			      	}
			      	catch(Exception ex){
			      		logger.error(ex);
			      	}
			          return info;
			      }
			      
		      /**
			      * 获取销售组
			      * @param ctx
			      * @param number 销售组编码
			      * @return SaleGroupInfo  销售组实体
			      * @throws BOSException
			      */
			      public static  SaleGroupInfo getSaleGroupInfoByNumber(Context ctx, String number)  {
			      	
			    	  SaleGroupInfo info = null;
			      	try{
			      		ISaleGroup  iSaleGroup=null;
			      		if(ctx != null){
			      			iSaleGroup=SaleGroupFactory.getLocalInstance(ctx);
			  			}else{
			  				iSaleGroup =SaleGroupFactory.getRemoteInstance();
			  			}
			      		
			      		
			  	    	EntityViewInfo view = new EntityViewInfo();
			  	        SelectorItemCollection sic = view.getSelector();
			  	        sic.add("*");
			  	        FilterInfo filter = new FilterInfo();
			  	        FilterItemCollection fic = filter.getFilterItems();
			  	        fic.add(new FilterItemInfo("number", number));
			  	        view.setFilter(filter);
			  	
			  	      SaleGroupCollection infoCollection = iSaleGroup.getSaleGroupCollection(view);
			  	        
			  	        if(infoCollection != null && infoCollection.size() != 0){
			  	        	info = infoCollection.get(0);
			  	        }
			      	}
			      	catch(Exception ex){
			      		logger.error(ex);
			      	}
			          return info;
			      }
			      
			      /**
				      * 获取员工
				      * @param ctx
				      * @param number 员工编码
				      * @return PersonInfo 员工实体
				      * @throws BOSException
				      */
				      public static  PersonInfo getPersonInfoByNumber(Context ctx, String number)  {
				      	
				    	  PersonInfo info = null;
				      	try{
				      		IPerson  iPerson=null;
				      		if(ctx != null){
				      			iPerson=PersonFactory.getLocalInstance(ctx);
				  			}else{
				  				iPerson =PersonFactory.getRemoteInstance();
				  			}
				      		
				      		
				  	    	EntityViewInfo view = new EntityViewInfo();
				  	        SelectorItemCollection sic = view.getSelector();
				  	        sic.add("*");
				  	        FilterInfo filter = new FilterInfo();
				  	        FilterItemCollection fic = filter.getFilterItems();
				  	        fic.add(new FilterItemInfo("number", number));
				  	        view.setFilter(filter);
				  	
				  	      PersonCollection infoCollection = iPerson.getPersonCollection(view);
				  	        
				  	        if(infoCollection != null && infoCollection.size() != 0){
				  	        	info = infoCollection.get(0);
				  	        }
				      	}
				      	catch(Exception ex){
				      		logger.error(ex);
				      	}
				          return info;
				      }
	    /**
	     * 获取控制单元对象
	     * 
	     * 根据控制单元编码获取控制单元对象
	     * 
	     * @param ctx 服务器端上下文,客户端调用时传null值
	     * @param number 控制单元编码
	     * @return CtrlUnitInfo 控制单元对象
	     */
	    public static CtrlUnitInfo getCU(Context ctx, String number)
	    {
	    	CtrlUnitInfo currentUnit = null;
	    	try
	    	{
		    	EntityViewInfo evi = new EntityViewInfo();
		    	FilterInfo filter = new FilterInfo();
		    	filter.getFilterItems().add(new FilterItemInfo("number", number));
		    	evi.setFilter(filter);
		    	
		    	SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		evi.setSelector(sic);
		    	
	    		ICtrlUnit iCtrlUnit = null;
	    		if(ctx != null){
	    			iCtrlUnit = CtrlUnitFactory.getLocalInstance(ctx);
	    		}else{
	    			iCtrlUnit = CtrlUnitFactory.getRemoteInstance();
	    		}
	    		
		    	CtrlUnitCollection cus = iCtrlUnit.getCtrlUnitCollection(evi);
		    	
		    	if(cus != null && cus.size() != 0)
		    	{
		    		currentUnit = cus.get(0);
		    	}else{
//		    		currentUnit = iCtrlUnit.getCtrlUnitInfo(new ObjectUuidPK("00000000-0000-0000-0000-000000000000CCE7AED4"));
		    		
		    	}
	    	}catch(Exception ex){
	    		logger.error(ex);
	    	}
	    	
	    	return currentUnit;
	    }
	    
	    /**
	     * 根据助记码获取控制单元对象
	     * 
	     * 根据控制单元编码获取控制单元对象
	     * 
	     * @param ctx 服务器端上下文,客户端调用时传null值
	     * @param number 控制单元编码
	     * @return CtrlUnitInfo 控制单元对象
	     */
	    public static CtrlUnitInfo getCUByMnemonicCode(Context ctx, String number)
	    {
	    	CtrlUnitInfo currentUnit = null;
	    	try
	    	{
		    	EntityViewInfo evi = new EntityViewInfo();
		    	FilterInfo filter = new FilterInfo();
		    	filter.getFilterItems().add(new FilterItemInfo("code", number));
		    	evi.setFilter(filter);
		    	
		    	SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		evi.setSelector(sic);
		    	
	    		ICtrlUnit iCtrlUnit = null;
	    		if(ctx != null){
	    			iCtrlUnit = CtrlUnitFactory.getLocalInstance(ctx);
	    		}else{
	    			iCtrlUnit = CtrlUnitFactory.getRemoteInstance();
	    		}
	    		
		    	CtrlUnitCollection cus = iCtrlUnit.getCtrlUnitCollection(evi);
		    	
		    	if(cus != null && cus.size() != 0)
		    	{
		    		currentUnit = cus.get(0);
		    	}else{
//		    		currentUnit = iCtrlUnit.getCtrlUnitInfo(new ObjectUuidPK("00000000-0000-0000-0000-000000000000CCE7AED4"));
		    		
		    	}
	    	}catch(Exception ex){
	    		logger.error(ex);
	    	}
	    	
	    	return currentUnit;
	    }
	    
	    /**
	     * 获取客户分类
	     * 
	     * 
	     * @param ctx 服务器端上下文,客户端调用时传null值
	     * @param number 客户分类编码
	     * @param number 客商分类标准ID
	     * @return  CSSPGroupInfo 客户分类对象
	     */
	    public static CSSPGroupInfo getCSSPGroup(Context ctx, String number,String JBFL)
	    {
	    	CSSPGroupInfo currentUnit = null;
	    	try
	    	{
		    	EntityViewInfo evi = new EntityViewInfo();
		    	FilterInfo filter = new FilterInfo();
		    	filter.getFilterItems().add(new FilterItemInfo("number", number));
		    	filter.getFilterItems().add(new FilterItemInfo("groupStandard.id", JBFL));
		    	evi.setFilter(filter);
		    	SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		evi.setSelector(sic);
		    	
	    		ICSSPGroup iCSSPGroup = null;
	    		if(ctx != null){
	    			iCSSPGroup = CSSPGroupFactory.getLocalInstance(ctx);
	    		}else{
	    			iCSSPGroup = CSSPGroupFactory.getRemoteInstance();
	    		}
	    		
	    		CSSPGroupCollection cus = iCSSPGroup.getCSSPGroupCollection(evi);
		    	
		    	if(cus != null && cus.size() != 0)
		    	{
		    		currentUnit = cus.get(0);
		    	}else{
//		    		currentUnit = iCtrlUnit.getCtrlUnitInfo(new ObjectUuidPK("00000000-0000-0000-0000-000000000000CCE7AED4"));
		    		
		    	}
	    	}catch(Exception ex){
	    		logger.error(ex);
	    	}
	    	
	    	return currentUnit;
	    }
	    
	    
	    /**
	     * 获取客商分类标准
	     * 
	     * 
	     * @param ctx 服务器端上下文,客户端调用时传null值
	     * @param String 客商分类标准ID
	     * @return CSSPGroupStandardInfo 客商分类标准对象
	     */
	    public static CSSPGroupStandardInfo getCSSPGroupStandard(Context ctx, String JBFL)
	    {
	    	CSSPGroupStandardInfo currentUnit = null;
	    	try
	    	{
		    	EntityViewInfo evi = new EntityViewInfo();
		    	FilterInfo filter = new FilterInfo();
		    	filter.getFilterItems().add(new FilterItemInfo("id", JBFL));
		    	evi.setFilter(filter);
		    	SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		evi.setSelector(sic);
		    	
	    		ICSSPGroupStandard iCSSPGroupStandard = null;
	    		if(ctx != null){
	    			iCSSPGroupStandard = CSSPGroupStandardFactory.getLocalInstance(ctx);
	    		}else{
	    			iCSSPGroupStandard = CSSPGroupStandardFactory.getRemoteInstance();
	    		}
	    		
	    		CSSPGroupStandardCollection cus = iCSSPGroupStandard.getCSSPGroupStandardCollection(evi);
		    	
		    	if(cus != null && cus.size() != 0)
		    	{
		    		currentUnit = cus.get(0);
		    	}else{
//		    		currentUnit = iCtrlUnit.getCtrlUnitInfo(new ObjectUuidPK("00000000-0000-0000-0000-000000000000CCE7AED4"));
		    		
		    	}
	    	}catch(Exception ex){
	    		logger.error(ex);
	    	}
	    	
	    	return currentUnit;
	    }
	    
	    /**
	     * 获取自定义核算项目分类
	     * 
	     * 
	     * @param ctx 服务器端上下文,客户端调用时传null值
	     * @param number 自定义核算项目分类编码
	     * @return GeneralAsstActTypeGroupInfo 自定义核算项目分类对象
	     */
	    public static GeneralAsstActTypeGroupInfo getGeneralAsstActTypeGroupInfo(Context ctx, String number)
	    {
	    	GeneralAsstActTypeGroupInfo currentUnit = null;
	    	try
	    	{
		    	EntityViewInfo evi = new EntityViewInfo();
		    	FilterInfo filter = new FilterInfo();
		    	filter.getFilterItems().add(new FilterItemInfo("number", number));
		    	evi.setFilter(filter);
		    	SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		evi.setSelector(sic);
		    	
	    		IGeneralAsstActTypeGroup iGeneralAsstActTypeGroup = null;
	    		if(ctx != null){
	    			iGeneralAsstActTypeGroup = GeneralAsstActTypeGroupFactory.getLocalInstance(ctx);
	    		}else{
	    			iGeneralAsstActTypeGroup = GeneralAsstActTypeGroupFactory.getRemoteInstance();
	    		}
	    		
	    		GeneralAsstActTypeGroupCollection cus = iGeneralAsstActTypeGroup.getGeneralAsstActTypeGroupCollection(evi);
		    	
		    	if(cus != null && cus.size() != 0)
		    	{
		    		currentUnit = cus.get(0);
		    	}else{
//		    		currentUnit = iCtrlUnit.getCtrlUnitInfo(new ObjectUuidPK("00000000-0000-0000-0000-000000000000CCE7AED4"));
		    		
		    	}
	    	}catch(Exception ex){
	    		logger.error(ex);
	    	}
	    	
	    	return currentUnit;
	    }
	    
	    /**
	     * 获取计量单位组
	     * 
	     * 
	     * @param ctx 服务器端上下文,客户端调用时传null值
	     * @param number 计量单位组编码
	     * @return MeasureUnitGroupInfo 计量单位组对象
	     */
	    public static MeasureUnitGroupInfo getMeasureUnitGroupInfo(Context ctx, String number)
	    {
	    	MeasureUnitGroupInfo currentUnit = null;
	    	try
	    	{
		    	EntityViewInfo evi = new EntityViewInfo();
		    	FilterInfo filter = new FilterInfo();
		    	filter.getFilterItems().add(new FilterItemInfo("number", number));
		    	evi.setFilter(filter);
		    	SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		evi.setSelector(sic);
		    	
	    		IMeasureUnitGroup iMeasureUnitGroup = null;
	    		if(ctx != null){
	    			iMeasureUnitGroup = MeasureUnitGroupFactory.getLocalInstance(ctx);
	    		}else{
	    			iMeasureUnitGroup = MeasureUnitGroupFactory.getRemoteInstance();
	    		}
	    		
	    		MeasureUnitGroupCollection cus = iMeasureUnitGroup.getMeasureUnitGroupCollection(evi);
		    	
		    	if(cus != null && cus.size() != 0)
		    	{
		    		currentUnit = cus.get(0);
		    	}else{
//		    		currentUnit = iCtrlUnit.getCtrlUnitInfo(new ObjectUuidPK("00000000-0000-0000-0000-000000000000CCE7AED4"));
		    		
		    	}
	    	}catch(Exception ex){
	    		logger.error(ex);
	    	}
	    	
	    	return currentUnit;
	    }
	    
	    /**
	     * 获取计量单位
	     * 
	     * 
	     * @param ctx 服务器端上下文,客户端调用时传null值
	     * @param GroupNumber 计量单位组编码
	     * @param number 计量单位编码
	     * @return MeasureUnitInfo 计量单位对象
	     */
	    public static MeasureUnitInfo getMeasureUnitInfo(Context ctx, String GroupNumber ,String number)
	    {
	    	MeasureUnitInfo currentUnit = null;
	    	try
	    	{
		    	EntityViewInfo evi = new EntityViewInfo();
		    	FilterInfo filter = new FilterInfo();
		    	filter.getFilterItems().add(new FilterItemInfo("number", number));
		    	filter.getFilterItems().add(new FilterItemInfo("measureUnitGroup.number", GroupNumber));
		    	evi.setFilter(filter);
		    	SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		sic.add("measureUnitGroup.*");
	    		evi.setSelector(sic);
		    	
	    		IMeasureUnit iMeasureUnit = null;
	    		if(ctx != null){
	    			iMeasureUnit = MeasureUnitFactory.getLocalInstance(ctx);
	    		}else{
	    			iMeasureUnit = MeasureUnitFactory.getRemoteInstance();
	    		}
	    		
	    		MeasureUnitCollection cus = iMeasureUnit.getMeasureUnitCollection(evi);
		    	
		    	if(cus != null && cus.size() != 0)
		    	{
		    		currentUnit = cus.get(0);
		    	}else{
//		    		currentUnit = iCtrlUnit.getCtrlUnitInfo(new ObjectUuidPK("00000000-0000-0000-0000-000000000000CCE7AED4"));
		    		
		    	}
	    	}catch(Exception ex){
	    		logger.error(ex);
	    	}
	    	
	    	return currentUnit;
	    }
	    
	    /**
	     * 获取计量单位
	     * 
	     * 
	     * @param ctx 服务器端上下文,客户端调用时传null值
	     * @param GroupNumber 计量单位组编码
	     * @param number 计量单位编码
	     * @return MeasureUnitInfo 计量单位对象
	     */
	    public static MeasureUnitInfo getMeasureUnitInfoByName(Context ctx, String GroupNumber ,String name)
	    {
	    	MeasureUnitInfo currentUnit = null;
	    	try
	    	{
		    	EntityViewInfo evi = new EntityViewInfo();
		    	FilterInfo filter = new FilterInfo();
		    	filter.getFilterItems().add(new FilterItemInfo("name", name));
		    	if(!GroupNumber.equals("")){
		    		filter.getFilterItems().add(new FilterItemInfo("measureUnitGroup.number", GroupNumber));
		    	}
		    	evi.setFilter(filter);
		    	SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		sic.add("measureUnitGroup.*");
	    		evi.setSelector(sic);
		    	
	    		IMeasureUnit iMeasureUnit = null;
	    		if(ctx != null){
	    			iMeasureUnit = MeasureUnitFactory.getLocalInstance(ctx);
	    		}else{
	    			iMeasureUnit = MeasureUnitFactory.getRemoteInstance();
	    		}
	    		
	    		MeasureUnitCollection cus = iMeasureUnit.getMeasureUnitCollection(evi);
		    	
		    	if(cus != null && cus.size() != 0)
		    	{
		    		currentUnit = cus.get(0);
		    	}else{
//		    		currentUnit = iCtrlUnit.getCtrlUnitInfo(new ObjectUuidPK("00000000-0000-0000-0000-000000000000CCE7AED4"));
		    		
		    	}
	    	}catch(Exception ex){
	    		logger.error(ex);
	    	}
	    	
	    	return currentUnit;
	    }
	    
	    /**
	     * 获取用户对象
	     * 
	     * 根据用户名称获取用户对象
	     * 
	     * @param ctx 服务端上下文,客户端调用时传null值
	     * @param name 用户名称
	     * @return UserInfo 用户对象
	     */
	    public static UserInfo getUser(Context ctx, String name){
	    	UserInfo user = null;
	    	try{
	    		EntityViewInfo evi = new EntityViewInfo();
		    	FilterInfo filter = new FilterInfo();
		    	filter.getFilterItems().add(new FilterItemInfo("name", name));
		    	evi.setFilter(filter);
		    	
		    	SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		evi.setSelector(sic);
		    	
	    		IUser iUser = null;
	    		if(ctx != null){
	    			iUser = UserFactory.getLocalInstance(ctx);    			
	    		}else{
	    			iUser = UserFactory.getRemoteInstance();
	    		}
	    		
		    	UserCollection users = iUser.getUserCollection(evi);
		    	if(users != null && users.size() != 0){
		    		user = users.get(0);
		    	}
	    	}catch(Exception ex){
	    		logger.info("=====获取用户信息时发生错误=====");
	    		logger.error(ex);
	    	}
	    	
	    	return user;
	    }
	    /**
	     * 获取用户对象
	     * 
	     * 根据用户名称获取用户对象
	     * 
	     * @param ctx 服务端上下文,客户端调用时传null值
	     * @param id 用户ID
	     * @return UserInfo 用户对象
	     */
	    public static UserInfo getUserById(Context ctx, String id){
	    	UserInfo user = null;
	    	try{
	    		EntityViewInfo evi = new EntityViewInfo();
		    	FilterInfo filter = new FilterInfo();
		    	filter.getFilterItems().add(new FilterItemInfo("id", id));
		    	evi.setFilter(filter);
		    	
		    	SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		evi.setSelector(sic);
		    	
	    		IUser iUser = null;
	    		if(ctx != null){
	    			iUser = UserFactory.getLocalInstance(ctx);    			
	    		}else{
	    			iUser = UserFactory.getRemoteInstance();
	    		}
	    		
		    	UserCollection users = iUser.getUserCollection(evi);
		    	if(users != null && users.size() != 0){
		    		user = users.get(0);
		    	}
	    	}catch(Exception ex){
	    		logger.info("=====获取用户信息时发生错误=====");
	    		logger.error(ex);
	    	}
	    	
	    	return user;
	    }
	    /**
	     * 获取科目对象
	     * 
	     * 根据科目编码及公司信息获取科目对象
	     * 
	     * @param ctx 服务端上下文,客户端调用时传null值
	     * @param number 科目编码
	     * @param company 公司对象
	     * @return AccountViewInfo 科目对象
	     * @throws BOSException 执行科目查询时可能会引发此异常
	     */
	    public static AccountViewInfo getAccountView(Context ctx, String number, CompanyOrgUnitInfo company) throws BOSException
	    {
	    	if(StringUtil.isEmptyString(number)){
	            return null;
	    	}
	    	
	        EntityViewInfo view = new EntityViewInfo();
	        SelectorItemCollection sic = view.getSelector();
	        sic.add(new SelectorItemInfo("id"));
	        sic.add(new SelectorItemInfo("number"));
	        sic.add(new SelectorItemInfo("name"));
	        sic.add(new SelectorItemInfo("longNumber"));
	        sic.add(new SelectorItemInfo("longName"));
	        sic.add(new SelectorItemInfo("level"));
	        sic.add(new SelectorItemInfo("DC"));
	        sic.add(new SelectorItemInfo("AC"));
	        sic.add(new SelectorItemInfo("isQty"));
	        sic.add(new SelectorItemInfo("control"));
	        sic.add(new SelectorItemInfo("isCash"));
	        sic.add(new SelectorItemInfo("isBank"));
	        sic.add(new SelectorItemInfo("isCashEquivalent"));
	        sic.add(new SelectorItemInfo("hasUserProperty"));
	        sic.add(new SelectorItemInfo("isGFreeze"));
	        sic.add(new SelectorItemInfo("isCFreeze"));
	        sic.add(new SelectorItemInfo("isLeaf"));
	        sic.add(new SelectorItemInfo("PLType"));
	        sic.add(new SelectorItemInfo("measureUnitGroupID.id"));
	        sic.add(new SelectorItemInfo("accountTypeID.property"));
	        sic.add(new SelectorItemInfo("CAA.id"));
	        sic.add(new SelectorItemInfo("CAA.name"));
	        sic.add(new SelectorItemInfo("CAA.count"));
	        sic.add(new SelectorItemInfo("CAA.isQty"));
	        sic.add(new SelectorItemInfo("CAA.measureUnitGroup"));
	        sic.add(new SelectorItemInfo("CAA.measureUnitGroup.id"));
	        FilterInfo filter = new FilterInfo();
	        view.setFilter(filter);
	        FilterItemCollection fic = filter.getFilterItems();
	        fic.add(new FilterItemInfo("number", number.trim().toString()));
	        fic.add(new FilterItemInfo("companyId.id", company.getId().toString()));
	        fic.add(new FilterItemInfo("accountTableID.id", company.getAccountTable().getId().toString()));
	        filter.setMaskString("#0 and #1 and #2");
	        AccountViewInfo accountViewInfo = null;
	        IAccountView accountView = null;
	        if(ctx != null){
	        	accountView = AccountViewFactory.getLocalInstance(ctx);
	        }else{
	        	accountView = AccountViewFactory.getRemoteInstance();
	        }
	        
	        AccountViewCollection accountViewCollection = accountView.getAccountViewCollection(view);
	        if(accountViewCollection != null && accountViewCollection.size() != 0)
	            accountViewInfo = accountViewCollection.get(0);
	        return accountViewInfo;
	    }
	    
	    
	    /**
	     * 获取科目对象
	     * 
	     * 根据科目id及公司信息获取科目对象
	     * 
	     * @param ctx 服务端上下文,客户端调用时传null值
	     * @param id 科目ID
	     * @param company 公司对象
	     * @return AccountViewInfo 科目对象
	     * @throws BOSException 执行科目查询时可能会引发此异常
	     */
	    public static AccountViewInfo getAccountViewById(Context ctx, String id, CompanyOrgUnitInfo company) throws BOSException
	    {
	    	if(StringUtil.isEmptyString(id)){
	            return null;
	    	}
	    	
	        EntityViewInfo view = new EntityViewInfo();
	        SelectorItemCollection sic = view.getSelector();
	        sic.add(new SelectorItemInfo("id"));
	        sic.add(new SelectorItemInfo("number"));
	        sic.add(new SelectorItemInfo("name"));
	        sic.add(new SelectorItemInfo("longNumber"));
	        sic.add(new SelectorItemInfo("longName"));
	        sic.add(new SelectorItemInfo("level"));
	        sic.add(new SelectorItemInfo("DC"));
	        sic.add(new SelectorItemInfo("AC"));
	        sic.add(new SelectorItemInfo("isQty"));
	        sic.add(new SelectorItemInfo("control"));
	        sic.add(new SelectorItemInfo("isCash"));
	        sic.add(new SelectorItemInfo("isBank"));
	        sic.add(new SelectorItemInfo("isCashEquivalent"));
	        sic.add(new SelectorItemInfo("hasUserProperty"));
	        sic.add(new SelectorItemInfo("isGFreeze"));
	        sic.add(new SelectorItemInfo("isCFreeze"));
	        sic.add(new SelectorItemInfo("isLeaf"));
	        sic.add(new SelectorItemInfo("PLType"));
	        sic.add(new SelectorItemInfo("measureUnitGroupID.id"));
	        sic.add(new SelectorItemInfo("accountTypeID.property"));
	        sic.add(new SelectorItemInfo("CAA.id"));
	        sic.add(new SelectorItemInfo("CAA.name"));
	        sic.add(new SelectorItemInfo("CAA.count"));
	        sic.add(new SelectorItemInfo("CAA.isQty"));
	        sic.add(new SelectorItemInfo("CAA.measureUnitGroup"));
	        sic.add(new SelectorItemInfo("CAA.measureUnitGroup.id"));
	        FilterInfo filter = new FilterInfo();
	        view.setFilter(filter);
	        FilterItemCollection fic = filter.getFilterItems();
	        fic.add(new FilterItemInfo("id", id.trim().toString()));
	        fic.add(new FilterItemInfo("companyId.id", company.getId().toString()));
	        fic.add(new FilterItemInfo("accountTableID.id", company.getAccountTable().getId().toString()));
	        filter.setMaskString("#0 and #1 and #2");
	        AccountViewInfo accountViewInfo = null;
	        IAccountView accountView = null;
	        if(ctx != null){
	        	accountView = AccountViewFactory.getLocalInstance(ctx);
	        }else{
	        	accountView = AccountViewFactory.getRemoteInstance();
	        }
	        
	        AccountViewCollection accountViewCollection = accountView.getAccountViewCollection(view);
	        if(accountViewCollection != null && accountViewCollection.size() != 0)
	            accountViewInfo = accountViewCollection.get(0);
	        return accountViewInfo;
	    }
	    /**
	     * 获取辅助账类型
	     * 
	     * 根据科目辅助账类型Id获取辅助账类型集合
	     * 
	     * @param ctx 服务端上下文
	     * @param caa 科目辅助账类型ID
	     * @return AsstActTypeCollection 辅助账中核算项目类型集合
	     */
	    public static AsstActTypeCollection getAsstActType(Context ctx, String caa)
	    {
	    	AsstActTypeCollection asstTypes = new AsstActTypeCollection();
	    	try
	    	{
	    	
	    		String strSQL = "SELECT I.FID, I.FName_L2,I.FNumber,I.FAsstHGAttribute,I.FRealtionDataObject,I.FUseLongNumber, I.FGroupTableName, FGlAsstActTypeGrpID FROM T_BD_AsstActGroupDetail G INNER JOIN T_BD_AsstActType I ON G.FAsstActTypeID = I.FID WHERE G.FAsstAccountID = ? ORDER BY I.FAsstHGAttribute ASC";

	    		IRowSet rs = DbUtil.executeQuery(ctx, strSQL, new Object[]{caa});
	    		
	    		while(rs.next()){
	    			AsstActTypeInfo asstType = new AsstActTypeInfo();
	    			asstType.setId(BOSUuid.read(rs.getString("FID")));
	    			asstType.setNumber(rs.getString("FNumber"));
	    			asstType.setName(rs.getString("FName_L2"), ctx.getLocale());
	    			asstType.setAsstHGAttribute(rs.getString("FAsstHGAttribute"));
	    			asstType.setRealtionDataObject(rs.getString("FRealtionDataObject"));
	    			asstType.setUseLongNumber(rs.getBoolean("FUseLongNumber"));
	    			asstType.setGroupTableName(rs.getString("FGroupTableName"));
	    			GeneralAsstActTypeGroupInfo asstTypeGrp = new GeneralAsstActTypeGroupInfo();
	    			if(rs.getString("FGlAsstActTypeGrpID") != null){
	    				asstTypeGrp.setId(BOSUuid.read(rs.getString("FGlAsstActTypeGrpID")));    			
	    				asstType.setGlAsstActTypeGrp(asstTypeGrp);
	    			}
	    			
	    			asstTypes.add(asstType);
	    		}
	    		rs.close();
	    		rs = null;
	    	}catch(Exception ex){
	    		logger.info("=====获取科目核算项目时发生错误======");
	    		logger.error(ex);
	    	}
	    	return asstTypes;
	    }
	    
	    /**
	     * 获取客户对象
	     * 
	     * 根据客户编码获取客户对象
	     * 
	     * @param ctx 服务端上下文,客户端调用时传null值
	     * @param number 客户编码
	     * @return CustomerInfo 客户对象
	     */
	    public static CustomerInfo getCustomer(Context ctx, String number){
	    	CustomerInfo customer = null;
	    	
	    	try
	    	{
	    		EntityViewInfo evi = new EntityViewInfo();
	    		FilterInfo filter = new FilterInfo();
	    		filter.getFilterItems().add(new FilterItemInfo("number", number));
	    		evi.setFilter(filter);
	    		
	    		SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		evi.setSelector(sic);
	    		
	    		ICustomer iCustomer = null;
	    		if(ctx != null){
	    			iCustomer = CustomerFactory.getLocalInstance(ctx);
	    		}else{
	    			iCustomer = CustomerFactory.getRemoteInstance();
	    		}
	    		
	    		CustomerCollection customers = iCustomer.getCustomerCollection(evi);
	    		if(customers != null && customers.size() != 0){
	    			customer = customers.get(0);
	    		}
	    	}catch(Exception ex){
	    		logger.info("====获取客户信息时发生错误=====");
	    		logger.error(ex);
	    	}
	    	
	    	return customer;
	    }
	    
	    
	    /**
	     * 获取客户公司信息对象
	     * 
	     * 根据客户ID 获取客户公司信息对象
	     * 
	     * @param ctx 服务端上下文,客户端调用时传null值
	     * @param number 客户ID
	     * @return CustomerCompanyInfoInfo 客户公司信息对象
	     */
	    public static CustomerCompanyInfoInfo getCustomerCompanyInfo(Context ctx, String custID){
	    	CustomerCompanyInfoInfo info = null;
	    	
	    	try
	    	{
	    		EntityViewInfo evi = new EntityViewInfo();
	    		FilterInfo filter = new FilterInfo();
	    		filter.getFilterItems().add(new FilterItemInfo("customer.id", custID));
	    		evi.setFilter(filter);
	    		
	    		SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("customer.*");
	    		sic.add("*");
	    		evi.setSelector(sic);
	    		
	    		ICustomerCompanyInfo iCustomerCompany = null;
	    		if(ctx != null){
	    			iCustomerCompany = CustomerCompanyInfoFactory.getLocalInstance(ctx);
	    		}else{
	    			iCustomerCompany = CustomerCompanyInfoFactory.getRemoteInstance();
	    		}
	    		
	    		CustomerCompanyInfoCollection infoCols = iCustomerCompany.getCustomerCompanyInfoCollection(evi);
	    		if(infoCols != null && infoCols.size() != 0){
	    			info = infoCols.get(0);
	    		}
	    	}catch(Exception ex){
	    		logger.info("====获取客户信息时发生错误=====");
	    		logger.error(ex);
	    	}
	    	
	    	return info;
	    }
	    
	    /**
	     * 获取客户对象
	     * 
	     * 根据客户编码获取客户对象
	     * 
	     * @param ctx 服务端上下文,客户端调用时传null值
	     * @param id 客户id
	     * @return CustomerInfo 客户对象
	     */
	    public static CustomerInfo getCustomerByID(Context ctx, String id){
	    	CustomerInfo customer = null;
	    	
	    	try
	    	{
	    		EntityViewInfo evi = new EntityViewInfo();
	    		FilterInfo filter = new FilterInfo();
	    		filter.getFilterItems().add(new FilterItemInfo("id", id));
	    		evi.setFilter(filter);
	    		
	    		SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		evi.setSelector(sic);
	    		
	    		ICustomer iCustomer = null;
	    		if(ctx != null){
	    			iCustomer = CustomerFactory.getLocalInstance(ctx);
	    		}else{
	    			iCustomer = CustomerFactory.getRemoteInstance();
	    		}
	    		
	    		CustomerCollection customers = iCustomer.getCustomerCollection(evi);
	    		if(customers != null && customers.size() != 0){
	    			customer = customers.get(0);
	    		}
	    	}catch(Exception ex){
	    		logger.info("====获取客户信息时发生错误=====");
	    		logger.error(ex);
	    	}
	    	
	    	return customer;
	    }
	    /**
	     * 获取客户对象
	     * 
	     * 根据客户助记码获取客户对象
	     * 
	     * @param ctx 服务端上下文,客户端调用时传null值
	     * @param number 客户助记码
	     * @return CustomerInfo 客户对象
	     */
	    public static CustomerInfo getCustomerByMnemonicCode(Context ctx, String number){
	    	CustomerInfo customer = null;
	    	if(number.equals("")|| number==null){
	    		return null;
	    	}
	    	try
	    	{
	    		EntityViewInfo evi = new EntityViewInfo();
	    		FilterInfo filter = new FilterInfo();
	    		filter.getFilterItems().add(new FilterItemInfo("mnemonicCode", number+"%",CompareType.LIKE));
	    		evi.setFilter(filter);
	    		
	    		SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		evi.setSelector(sic);
	    		
	    		ICustomer iCustomer = null;
	    		if(ctx != null){
	    			iCustomer = CustomerFactory.getLocalInstance(ctx);
	    		}else{
	    			iCustomer = CustomerFactory.getRemoteInstance();
	    		}
	    		
	    		CustomerCollection customers = iCustomer.getCustomerCollection(evi);
	    		if(customers != null && customers.size() != 0){
	    			customer = customers.get(0);
	    		}
	    	}catch(Exception ex){
	    		logger.info("====获取客户信息时发生错误=====");
	    		logger.error(ex);
	    	}
	    	
	    	return customer;
	    }
	    /**
	     * 获取供应商对象
	     * 
	     * 根据供应商助记码获取供应商对象
	     * 
	     * @param ctx 服务端上下文,客户端调用时传null值
	     * @param number 供应商助记码
	     * @return SupplierInfo 供应商对象
	     */
	    public static SupplierInfo getSupplierByMnemonicCode(Context ctx, String number){
	    	SupplierInfo supplier = null;
	    	
	    	try
	    	{
	    		EntityViewInfo evi = new EntityViewInfo();
	    		FilterInfo filter = new FilterInfo();
	    		filter.getFilterItems().add(new FilterItemInfo("mnemonicCode", number));
	    		evi.setFilter(filter);
	    		
	    		SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		evi.setSelector(sic);
	    		
	    		ISupplier iSupplier = null;
	    		if(ctx != null){
	    			iSupplier = SupplierFactory.getLocalInstance(ctx);
	    		}else{
	    			iSupplier = SupplierFactory.getRemoteInstance();
	    		}
	    		
	    		SupplierCollection suppliers = iSupplier.getSupplierCollection(evi);
	    		if(suppliers != null && suppliers.size() != 0){
	    			supplier = suppliers.get(0);
	    		}
	    	}catch(Exception ex){
	    		logger.info("====获取供应商信息时发生错误=====");
	    		logger.error(ex);
	    	}
	    	
	    	return supplier;
	    }
	    
	    /**
	     * 获取供应商对象
	     * 
	     * 根据供应商编码获取供应商对象
	     * 
	     * @param ctx 服务端上下文,客户端调用时传null值
	     * @param number 供应商编码
	     * @return SupplierInfo 供应商对象
	     */
	    public static SupplierInfo getSupplier(Context ctx, String number){
	    	SupplierInfo supplier = null;
	    	
	    	try
	    	{
	    		EntityViewInfo evi = new EntityViewInfo();
	    		FilterInfo filter = new FilterInfo();
	    		filter.getFilterItems().add(new FilterItemInfo("number", number));
	    		evi.setFilter(filter);
	    		
	    		SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		evi.setSelector(sic);
	    		
	    		ISupplier iSupplier = null;
	    		if(ctx != null){
	    			iSupplier = SupplierFactory.getLocalInstance(ctx);
	    		}else{
	    			iSupplier = SupplierFactory.getRemoteInstance();
	    		}
	    		
	    		SupplierCollection suppliers = iSupplier.getSupplierCollection(evi);
	    		if(suppliers != null && suppliers.size() != 0){
	    			supplier = suppliers.get(0);
	    		}
	    	}catch(Exception ex){
	    		logger.info("====获取供应商信息时发生错误=====");
	    		logger.error(ex);
	    	}
	    	
	    	return supplier;
	    }
	    /**
	     * 获取供应商对象
	     * 
	     * 根据供应商编码获取供应商对象
	     * 
	     * @param ctx 服务端上下文,客户端调用时传null值
	     * @param id 供应商ID
	     * @return SupplierInfo 供应商对象
	     */
	    public static SupplierInfo getSupplierById(Context ctx, String id){
	    	SupplierInfo supplier = null;
	    	
	    	try
	    	{
	    		EntityViewInfo evi = new EntityViewInfo();
	    		FilterInfo filter = new FilterInfo();
	    		filter.getFilterItems().add(new FilterItemInfo("id", id));
	    		evi.setFilter(filter);
	    		
	    		SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		evi.setSelector(sic);
	    		
	    		ISupplier iSupplier = null;
	    		if(ctx != null){
	    			iSupplier = SupplierFactory.getLocalInstance(ctx);
	    		}else{
	    			iSupplier = SupplierFactory.getRemoteInstance();
	    		}
	    		
	    		SupplierCollection suppliers = iSupplier.getSupplierCollection(evi);
	    		if(suppliers != null && suppliers.size() != 0){
	    			supplier = suppliers.get(0);
	    		}
	    	}catch(Exception ex){
	    		logger.info("====获取供应商信息时发生错误=====");
	    		logger.error(ex);
	    	}
	    	
	    	return supplier;
	    }
	    /**
	     * 获取自定义核算项目对象
	     * 
	     * 根据描述信息获取自定义核算项目对象
	     * 
	     * @param ctx 服务端上下文
	     * @param companyId 公司ID
	     * @param groupId 自定义核算项目组别ID
	     * @param number 工作流自定义核算项目编码
	     * @return GeneralAsstActTypeInfo 自定义核算项目对象
	     */
	    public static GeneralAsstActTypeInfo getGLAsstActTypeByDesc(Context ctx, String companyId, String groupId, String number){
	    	GeneralAsstActTypeInfo typeInfo = null;
	    	try
	    	{
		    	String cmdText = "SELECT G.FID, G.FNumber, G.FName_L2, G.FIsLeaf FROM T_BD_GeneralAsstActType G WHERE G.FGroupID = ? AND G.FDescription_L2 = ? AND G.FControlUnitID = ?";
		         
		    	IRowSet rs = DbUtil.executeQuery(ctx, cmdText, new Object[]{groupId, number, companyId});
		        
		    	if(rs.next()){
	                typeInfo = new GeneralAsstActTypeInfo();
	                typeInfo.setId(BOSUuid.read(rs.getString("FID")));
	                typeInfo.setName(rs.getString("FName_L2"), ctx.getLocale());
	                typeInfo.setNumber(rs.getString("FNumber"));
	                typeInfo.setIsLeaf(rs.getBoolean("FIsLeaf"));
		        }
		        rs.close();
		        rs = null;
	    	}catch(Exception ex){
	    		logger.info("====获取自定义核算项目信息时发生错误=====");
	    		logger.error(ex);
	    	}
	        return typeInfo;
	    }
	    
	    /**
	     * 获取自定义核算项目对象
	     * 
	     * 根据长编码获取自定义核算项目对象
	     * 
	     * @param ctx 服务端上下文
	     * @param companyId 公司ID
	     * @param groupId 自定义核算项目组别ID
	     * @param number 自定义核算项目长编码
	     * @return GeneralAsstActTypeInfo 自定义核算项目对象
	     */
	    public static GeneralAsstActTypeInfo getGLAsstActTypeByLngNum(Context ctx, String companyId, String groupId, String number){
	    	GeneralAsstActTypeInfo typeInfo = null;
	    	try
	    	{
		    	String cmdText = "SELECT G.FID, G.FNumber, G.FName_L2, G.FIsLeaf FROM T_BD_GeneralAsstActType G WHERE G.FGroupID = ? AND G.FLongNumber = ? AND G.FControlUnitID = ?";
		         
		    	IRowSet rs = DbUtil.executeQuery(ctx, cmdText, new Object[]{groupId, number, companyId});
		        
		    	if(rs.next()){
	                typeInfo = new GeneralAsstActTypeInfo();
	                typeInfo.setId(BOSUuid.read(rs.getString("FID")));
	                typeInfo.setName(rs.getString("FName_L2"), ctx.getLocale());
	                typeInfo.setNumber(rs.getString("FNumber"));
	                typeInfo.setIsLeaf(rs.getBoolean("FIsLeaf"));
		        }
		        rs.close();
		        rs = null;
	    	}catch(Exception ex){
	    		logger.info("====获取自定义核算项目信息时发生错误=====");
	    		logger.error(ex);
	    	}
	        return typeInfo;
	    }
	    /**
	     * 获取凭证期间
	     * 
	     * 根据期间编码和期间年份获取凭证期间对象
	     * 
	     * @param ctx 服务端上下文,客户端调用时传null值
	     * @param periodNumber 期间编码 ，periodYear 期间年份YYYY
	     * @return PeriodInfo 凭证期间对象
	     * @author xjq
	     */
	    public static PeriodInfo getPeriodInfo(Context ctx, String periodNumber, String periodYear){
	    	PeriodInfo periodInfo = null;	
	    	try
	    	{
	    		EntityViewInfo evi = new EntityViewInfo();
	    		FilterInfo filter = new FilterInfo();
	    		filter.getFilterItems().add(new FilterItemInfo("periodNumber", periodNumber));
	    		filter.getFilterItems().add(new FilterItemInfo("periodYear", periodYear));
	    		evi.setFilter(filter);
	    		
	    		SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		evi.setSelector(sic);
	    		
	    		IPeriod iperiod = null;
	    		if(ctx != null){
	    			iperiod = PeriodFactory.getLocalInstance(ctx);
	    		}else{
	    			iperiod = PeriodFactory.getRemoteInstance();
	    		}
	    		
	    		PeriodCollection periods = iperiod.getPeriodCollection(evi);
	    		if(periods != null && periods.size() != 0){
	    			periodInfo = periods.get(0);
	    		}    		
	    	}catch(Exception ex){
	    		logger.info("====获取凭证期间信息时发生错误=====");
	    		logger.error(ex);
	    	}
	    	return periodInfo;
	    } 
	    /**
	     * 获取凭证类型对象
	     * 
	     * 根据凭证类型说明获取凭证类型对象
	     * 
	     * @param ctx 服务器端上下文,客户端调用时传null值
	     * @param description 凭证类型说明
	     * @return VoucherTypeInfo 凭证类型对象
	     * @author xjq
	     */
	    public static VoucherTypeInfo getVoucherTypeByDescription(Context ctx, String description)
	    {
	    	VoucherTypeInfo typeInfo = null;
	    	try
	    	{
	    		EntityViewInfo evi = new EntityViewInfo();
		    	FilterInfo filter = new FilterInfo();
		    	filter.getFilterItems().add(new FilterItemInfo("description", description));
		    	evi.setFilter(filter);
		    	
		    	SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		evi.setSelector(sic);
		    	
	    		IVoucherType iVoucherType = null;
	    		if(ctx != null){
	    			iVoucherType = VoucherTypeFactory.getLocalInstance(ctx);
	    		}else{
	    			iVoucherType = VoucherTypeFactory.getRemoteInstance();
	    		}
	    		
		    	VoucherTypeCollection types = iVoucherType.getVoucherTypeCollection(evi);
		    	if(types != null && types.size() != 0){
		    		typeInfo = types.get(0);
		    	}
	    	}catch(Exception ex){
	    		logger.info("=====获取凭证类型时发生错误====");
	    		logger.error(ex);
	    	}	
	    	return typeInfo;
	    }
	    
	    /**
	     * 获取用户对象
	     * 
	     * 根据用户编码获取用户对象
	     * 
	     * @param ctx 服务端上下文,客户端调用时传null值
	     * @param number 用户编码
	     * @return UserInfo 用户对象
	     * @author xjq
	     */
	    public static UserInfo getUserByNumber(Context ctx, String number){
	    	UserInfo user = null;
	    	try{
	    		EntityViewInfo evi = new EntityViewInfo();
		    	FilterInfo filter = new FilterInfo();
		    	filter.getFilterItems().add(new FilterItemInfo("number", number));
		    	evi.setFilter(filter);
		    	
		    	SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		evi.setSelector(sic);
		    	
	    		IUser iUser = null;
	    		if(ctx != null){
	    			iUser = UserFactory.getLocalInstance(ctx);    			
	    		}else{
	    			iUser = UserFactory.getRemoteInstance();
	    		}
	    		
		    	UserCollection users = iUser.getUserCollection(evi);
		    	if(users != null && users.size() != 0){
		    		user = users.get(0);
		    	}
	    	}catch(Exception ex){
	    		logger.info("=====获取用户信息时发生错误=====");
	    		logger.error(ex);
	    	}
	    	
	    	return user;
	    }  
	    
	    /**
	     * 获取凭证类型对象
	     * 
	     * 根据凭证类型名称获取凭证类型对象
	     * 
	     * @param ctx 服务器端上下文,客户端调用时传null值
	     * @param name 凭证类型名称
	     * @return VoucherTypeInfo 凭证类型对象
	     */
	    public static VoucherTypeInfo getVoucherType(Context ctx, String name)
	    {
	    	VoucherTypeInfo typeInfo = null;
	    	try
	    	{
	    		EntityViewInfo evi = new EntityViewInfo();
		    	FilterInfo filter = new FilterInfo();
		    	filter.getFilterItems().add(new FilterItemInfo("name", name));
		    	evi.setFilter(filter);
		    	
		    	SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		evi.setSelector(sic);
		    	
	    		IVoucherType iVoucherType = null;
	    		if(ctx != null){
	    			iVoucherType = VoucherTypeFactory.getLocalInstance(ctx);
	    		}else{
	    			iVoucherType = VoucherTypeFactory.getRemoteInstance();
	    		}
	    		
		    	VoucherTypeCollection types = iVoucherType.getVoucherTypeCollection(evi);
		    	if(types != null && types.size() != 0){
		    		typeInfo = types.get(0);
		    	}
	    	}catch(Exception ex){
	    		logger.info("=====获取凭证类型时发生错误====");
	    		logger.error(ex);
	    	}
	    	
	    	return typeInfo;
	    }
	    /**
	     * 获取用户对象
	     * @param ctx
	     * @param id 用户ID
	     * @return UserInfo
	     */
	    public static UserInfo getUserByID(Context ctx, String id){
	    	UserInfo user = null;
	    	try{
	    		IUser iUser = null;
	    		if(ctx != null){
	    			iUser = UserFactory.getLocalInstance(ctx);    			
	    		}else{
	    			iUser = UserFactory.getRemoteInstance();
	    		}
	    		
	    		SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		
		    	user = iUser.getUserInfo(new ObjectUuidPK(id), sic);	    	
	    	}catch(Exception ex){
	    		logger.info("=====获取用户信息时发生错误=====");
	    		logger.error(ex);
	    	}
	    	
	    	return user;
	    }
	    
	    /**
	     * 获取银行账户信息
	     * 
	     * 根据银行账户编码获取银行账户信息
	     * @param ctx 服务端上下文,客户端调用时传null值
	     * @param number 银行账户编码
	     * @return AccountBankInfo 银行账户对象
	     */
	    public static AccountBankInfo getAcctBank(Context ctx, String companyId, String number){
	    	AccountBankInfo acctBank = null;
	    	try{
	    		EntityViewInfo evi = new EntityViewInfo();
		    	FilterInfo filter = new FilterInfo();
		    	filter.getFilterItems().add(new FilterItemInfo("number", number));
		    	filter.getFilterItems().add(new FilterItemInfo("company.id", companyId));
		    	evi.setFilter(filter);
		    	
		    	SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		sic.add("bank.*");
	    		evi.setSelector(sic);
	    		
	    		IAccountBank iAcctBank = null;
	    		if(ctx != null){
	    			iAcctBank = AccountBankFactory.getLocalInstance(ctx);
	    		}else{
	    			iAcctBank = AccountBankFactory.getRemoteInstance();
	    		}
	    		
	    		AccountBankCollection acctBanks = iAcctBank.getAccountBankCollection(evi);
	    		if(acctBanks != null && acctBanks.size() != 0){
	    			acctBank = acctBanks.get(0);
	    		}
	    	}catch(Exception ex){
	    		logger.info("=====获取银行账户信息时发生错误=====");
	    		logger.error(ex);
	    	}
	    	
	    	return acctBank;
	    }
	    
	    /**
	     * 获取银行账户信息
	     * 
	     * 根据银行账户编码获取银行账户信息
	     * @param ctx 服务端上下文,客户端调用时传null值
	     * @param ID 银行账户ID
	     * @return AccountBankInfo 银行账户对象
	     */
	    public static AccountBankInfo getAcctBankById(Context ctx, String companyId, String id){
	    	AccountBankInfo acctBank = null;
	    	try{
	    		EntityViewInfo evi = new EntityViewInfo();
		    	FilterInfo filter = new FilterInfo();
		    	filter.getFilterItems().add(new FilterItemInfo("id", id));
		    	filter.getFilterItems().add(new FilterItemInfo("company.id", companyId));
		    	evi.setFilter(filter);
		    	
		    	SelectorItemCollection sic = new SelectorItemCollection();
	    		sic.add("*");
	    		sic.add("bank.*");
	    		evi.setSelector(sic);
	    		
	    		IAccountBank iAcctBank = null;
	    		if(ctx != null){
	    			iAcctBank = AccountBankFactory.getLocalInstance(ctx);
	    		}else{
	    			iAcctBank = AccountBankFactory.getRemoteInstance();
	    		}
	    		
	    		AccountBankCollection acctBanks = iAcctBank.getAccountBankCollection(evi);
	    		if(acctBanks != null && acctBanks.size() != 0){
	    			acctBank = acctBanks.get(0);
	    		}
	    	}catch(Exception ex){
	    		logger.info("=====获取银行账户信息时发生错误=====");
	    		logger.error(ex);
	    	}
	    	
	    	return acctBank;
	    }
	    /**
	     * 获取辅助核算项目对应的核算项目名称
	     * 
	     * 根据辅助核算项目编码获取对应的核算项目名称
	     * 
	     * @param ctx 服务端上下文
	     * @param asstActNumber 辅助核算项目编码
	     * @return String 核算项目名称
	     */
	    public static String getAsstHGAttribute(Context ctx,String asstActNumber)
	    {
	    	String value = null;
	    	try
	    	{
		        String cmdText = "SELECT FAsstHGAttribute FROM T_BD_AsstActType WHERE FNumber = ?";        
		        IRowSet rs = DbUtil.executeQuery(ctx, cmdText, new Object[]{ asstActNumber });
		        if(rs.next()){
		    		value = rs.getString("FAsstHGAttribute");
		    	}
		    	rs.close();
		    	rs = null;
	    	}catch(Exception ex){
	    		logger.error(ex);
	    	}
	        return value;
	    }
	    
	    /**
	     * 获取币别对象
	     * 
	     * 根据币别编码获取币别对象
	     * 
	     * @param ctx 服务器端上下文,客户端调用时传null值
	     * @param currency 币别编码
	     * @return CurrencyInfo 币别对象
	     */
	    public static CurrencyInfo getCurrencyInfo(Context ctx, String currency)
	    {
	    	CurrencyInfo currencyInfo = null;
	    	try
	    	{
	    		if(StringUtil.isEmptyString(currency)){
	    			return null;
	    		}
	    		
	    		ICurrency iCurrency = null;
	    		if(ctx != null){
	    			iCurrency = CurrencyFactory.getLocalInstance(ctx);
	    		}else{
	    			iCurrency = CurrencyFactory.getRemoteInstance();
	    		}
	    		
	            EntityViewInfo view = new EntityViewInfo();
	            SelectorItemCollection sic = view.getSelector();
	            sic.add(new SelectorItemInfo("id"));
	            sic.add(new SelectorItemInfo("number"));
	            sic.add(new SelectorItemInfo("name"));
	            sic.add(new SelectorItemInfo("precision"));
	            sic.add(new SelectorItemInfo("isoCode"));
	            sic.add(new SelectorItemInfo("deletedStatus"));
	            FilterInfo filter = new FilterInfo();
	            FilterItemCollection fic = filter.getFilterItems();
	            fic.add(new FilterItemInfo("number", currency.trim()));
	            view.setFilter(filter);
	            
	            CurrencyCollection currencyCollection = iCurrency.getCurrencyCollection(view);
	            if(currencyCollection != null && currencyCollection.size() != 0){
	                currencyInfo = currencyCollection.get(0);
	            }            
	    	}catch(Exception ex){
	    		logger.info("获取币别时发生错误");
	    		logger.error(ex);
	    	}
	        return currencyInfo;
	    }
	    
	    /**
	     * 获取币别对象
	     * 
	     * 根据币别ID获取币别对象
	     * 
	     * @param ctx 服务器端上下文,客户端调用时传null值
	     * @param currencyID 币别ID
	     * @return CurrencyInfo 币别对象
	     */
	    public static CurrencyInfo getCurrencyInfoByID(Context ctx, String currencyID)
	    {
	    	CurrencyInfo currencyInfo = null;
	    	try
	    	{
	    		if(StringUtil.isEmptyString(currencyID)){
	    			return null;
	    		}
	    		
	    		ICurrency iCurrency = null;
	    		if(ctx != null){
	    			iCurrency = CurrencyFactory.getLocalInstance(ctx);
	    		}else{
	    			iCurrency = CurrencyFactory.getRemoteInstance();
	    		}
	    		
	            EntityViewInfo view = new EntityViewInfo();
	            SelectorItemCollection sic = view.getSelector();
	            sic.add(new SelectorItemInfo("id"));
	            sic.add(new SelectorItemInfo("number"));
	            sic.add(new SelectorItemInfo("name"));
	            sic.add(new SelectorItemInfo("precision"));
	            sic.add(new SelectorItemInfo("isoCode"));
	            sic.add(new SelectorItemInfo("deletedStatus"));
	            FilterInfo filter = new FilterInfo();
	            FilterItemCollection fic = filter.getFilterItems();
	            fic.add(new FilterItemInfo("id", currencyID.trim()));
	            view.setFilter(filter);
	            
	            CurrencyCollection currencyCollection = iCurrency.getCurrencyCollection(view);
	            if(currencyCollection != null && currencyCollection.size() != 0){
	                currencyInfo = currencyCollection.get(0);
	            }            
	    	}catch(Exception ex){
	    		logger.info("获取币别时发生错误");
	    		logger.error(ex);
	    	}
	        return currencyInfo;
	    }
	    
	    
	    
	    /**
	     * 获取结算类型对象
	     * @param ctx  服务器端上下文,客户端调用时传null值
	     * @param id   结算类型ID
	     * @return  SettlementTypeInfo 结算类型对象
	     */
	    public static SettlementTypeInfo getSettlementTypeInfoByID(Context ctx, String id)
	    {
	    	SettlementTypeInfo settlementTypeInfo = null;
	    	try
	    	{
	    		if(StringUtil.isEmptyString(id)){
	    			return null;
	    		}
	    		
	    		ISettlementType iSettlementType = null;
	    		if(ctx != null){
	    			iSettlementType = SettlementTypeFactory.getLocalInstance(ctx);
	    		}else{
	    			iSettlementType = SettlementTypeFactory.getRemoteInstance();
	    		}
	    		
	            EntityViewInfo view = new EntityViewInfo();
	            SelectorItemCollection sic = view.getSelector();
	            sic.add(new SelectorItemInfo("id"));
	            sic.add(new SelectorItemInfo("number"));
	            sic.add(new SelectorItemInfo("name"));
	            FilterInfo filter = new FilterInfo();
	            FilterItemCollection fic = filter.getFilterItems();
	            fic.add(new FilterItemInfo("id", id.trim()));
	            view.setFilter(filter);
	            
	            SettlementTypeCollection paymentTypeCollection = iSettlementType.getSettlementTypeCollection(view);
	            if(paymentTypeCollection != null && paymentTypeCollection.size() != 0){
	            	settlementTypeInfo = paymentTypeCollection.get(0);
	            }            
	    	}catch(Exception ex){
	    		logger.info("获取结算类型时发生错误");
	    		logger.error(ex);
	    	}
	        return settlementTypeInfo;
	    }
	    
	    /**
	     * 获取收款类型对象
	     * @param ctx  服务器端上下文,客户端调用时传null值
	     * @param id   收款类型ID
	     * @return  ReceivingBillTypeInfo 收款类型对象
	     */
	    public static ReceivingBillTypeInfo getReceivingBillTypeInfoByID(Context ctx, String id)
	    {
	    	ReceivingBillTypeInfo receivingBillTypeInfo = null;
	    	try
	    	{
	    		if(StringUtil.isEmptyString(id)){
	    			return null;
	    		}
	    		
	    		IReceivingBillType iReceivingBillType = null;
	    		if(ctx != null){
	    			iReceivingBillType = ReceivingBillTypeFactory.getLocalInstance(ctx);
	    		}else{
	    			iReceivingBillType = ReceivingBillTypeFactory.getRemoteInstance();
	    		}
	    		
	            EntityViewInfo view = new EntityViewInfo();
	            SelectorItemCollection sic = view.getSelector();
	            sic.add(new SelectorItemInfo("id"));
	            sic.add(new SelectorItemInfo("number"));
	            sic.add(new SelectorItemInfo("name"));
	            FilterInfo filter = new FilterInfo();
	            FilterItemCollection fic = filter.getFilterItems();
	            fic.add(new FilterItemInfo("id", id.trim()));
	            view.setFilter(filter);
	            
	            ReceivingBillTypeCollection receivingBillTypeCollection = iReceivingBillType.getReceivingBillTypeCollection(view);
	            if(receivingBillTypeCollection != null && receivingBillTypeCollection.size() != 0){
	            	receivingBillTypeInfo = receivingBillTypeCollection.get(0);
	            }            
	    	}catch(Exception ex){
	    		logger.info("获取收款类型时发生错误");
	    		logger.error(ex);
	    	}
	        return receivingBillTypeInfo;
	    }
	    
	    /**
	     * 获取收付款类型对象
	     * @param ctx  服务器端上下文,客户端调用时传null值
	     * @param id   收付款类型ID
	     * @return  PaymentTypeInfo 收付款类型对象
	     */
	    public static PaymentTypeInfo getPaymentTypeInfoByID(Context ctx, String id)
	    {
	    	PaymentTypeInfo paymentTypeInfoInfo = null;
	    	try
	    	{
	    		if(StringUtil.isEmptyString(id)){
	    			return null;
	    		}
	    		
	    		IPaymentType iPaymentType = null;
	    		if(ctx != null){
	    			iPaymentType = PaymentTypeFactory.getLocalInstance(ctx);
	    		}else{
	    			iPaymentType = PaymentTypeFactory.getRemoteInstance();
	    		}
	    		
	            EntityViewInfo view = new EntityViewInfo();
	            SelectorItemCollection sic = view.getSelector();
	            sic.add(new SelectorItemInfo("id"));
	            sic.add(new SelectorItemInfo("number"));
	            sic.add(new SelectorItemInfo("name"));
	            FilterInfo filter = new FilterInfo();
	            FilterItemCollection fic = filter.getFilterItems();
	            fic.add(new FilterItemInfo("id", id.trim()));
	            view.setFilter(filter);
	            
	            PaymentTypeCollection paymentTypeCollection = iPaymentType.getPaymentTypeCollection(view);
	            if(paymentTypeCollection != null && paymentTypeCollection.size() != 0){
	            	paymentTypeInfoInfo = paymentTypeCollection.get(0);
	            }            
	    	}catch(Exception ex){
	    		logger.info("获取收付款类型时发生错误");
	    		logger.error(ex);
	    	}
	        return paymentTypeInfoInfo;
	    }
	    
	    
	    /**
	     * 获取收付款类型对象
	     * @param ctx  服务器端上下文,客户端调用时传null值
	     * @param id   收付款类型编码
	     * @return  PaymentTypeInfo 收付款类型对象
	     */
	    public static PaymentTypeInfo getPaymentTypeInfoByNumber(Context ctx, String number)
	    {
	    	PaymentTypeInfo paymentTypeInfoInfo = null;
	    	try
	    	{
	    		if(StringUtil.isEmptyString(number)){
	    			return null;
	    		}
	    		
	    		IPaymentType iPaymentType = null;
	    		if(ctx != null){
	    			iPaymentType = PaymentTypeFactory.getLocalInstance(ctx);
	    		}else{
	    			iPaymentType = PaymentTypeFactory.getRemoteInstance();
	    		}
	    		
	            EntityViewInfo view = new EntityViewInfo();
	            SelectorItemCollection sic = view.getSelector();
	            sic.add(new SelectorItemInfo("id"));
	            sic.add(new SelectorItemInfo("number"));
	            sic.add(new SelectorItemInfo("name"));
	            FilterInfo filter = new FilterInfo();
	            FilterItemCollection fic = filter.getFilterItems();
	            fic.add(new FilterItemInfo("number", number));
	            view.setFilter(filter);
	            
	            PaymentTypeCollection paymentTypeCollection = iPaymentType.getPaymentTypeCollection(view);
	            if(paymentTypeCollection != null && paymentTypeCollection.size() != 0){
	            	paymentTypeInfoInfo = paymentTypeCollection.get(0);
	            }            
	    	}catch(Exception ex){
	    		logger.info("获取收付款类型时发生错误");
	    		logger.error(ex);
	    	}
	        return paymentTypeInfoInfo;
	    }
	    
	/**
	 * 遍历实体类属性和类型，属性值
	 * @param model
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void testReflect(Object model) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
	    Field[] field = model.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组  
	    for(int j=0 ; j<field.length ; j++){     //遍历所有属性
	            String name = field[j].getName();    //获取属性的名字
	            
	            System.out.println("attribute name:"+name);     
	            name = name.substring(0,1).toUpperCase()+name.substring(1); //将属性的首字符大写，方便构造get，set方法
	            String type = field[j].getGenericType().toString();    //获取属性的类型
	            if(type.equals("class java.lang.String")){   //如果type是类类型，则前面包含"class "，后面跟类名
	                Method m = model.getClass().getMethod("get"+name);
	                String value = (String) m.invoke(model);    //调用getter方法获取属性值
	                if(value != null){

	                    System.out.println("attribute value:"+value);
	                }
	            }
	            if(type.equals("class java.lang.Integer")){     
	                Method m = model.getClass().getMethod("get"+name);
	                Integer value = (Integer) m.invoke(model);
	                if(value != null){
	                    System.out.println("attribute value:"+value);
	                }
	            }
	            if(type.equals("class java.lang.Short")){     
	                Method m = model.getClass().getMethod("get"+name);
	                Short value = (Short) m.invoke(model);
	                if(value != null){
	                    System.out.println("attribute value:"+value);                    }
	            }       
	            if(type.equals("class java.lang.Double")){     
	                Method m = model.getClass().getMethod("get"+name);
	                Double value = (Double) m.invoke(model);
	                if(value != null){                    
	                    System.out.println("attribute value:"+value);  
	                }
	            }                  
	            if(type.equals("class java.lang.Boolean")){
	                Method m = model.getClass().getMethod("get"+name);    
	                Boolean value = (Boolean) m.invoke(model);
	                if(value != null){                      
	                    System.out.println("attribute value:"+value);
	                }
	            }
	            if(type.equals("class java.util.Date")){
	                Method m = model.getClass().getMethod("get"+name);                    
	                Date value = (Date) m.invoke(model);
	                if(value != null){
	                    System.out.println("attribute value:"+value.toLocaleString());
	                }
	            }                
	        }
	}

	public static SocketMsgSetInfo getScriptByType(Context ctx,UrlConfigInfo configInfo,String socketMsg){
		
		SocketMsgSetInfo socketMsgSetInfo=null;
		try
    	{
    		if(StringUtil.isEmptyString(socketMsg)){
    			return null;
    		}
    		
    		ISocketMsgSet iSocketMsgSet  = null;
    		if(ctx != null){
    			iSocketMsgSet = SocketMsgSetFactory.getLocalInstance(ctx);
    		}else{
    			iSocketMsgSet = SocketMsgSetFactory.getRemoteInstance();
    		}
    		
            EntityViewInfo view = new EntityViewInfo();
            SelectorItemCollection sic = view.getSelector();
            sic.add(new SelectorItemInfo("*"));
            sic.add(new SelectorItemInfo("Channel.*"));
            FilterInfo filter = new FilterInfo();
            FilterItemCollection fic = filter.getFilterItems();
            fic.add(new FilterItemInfo("Channel.id", configInfo.getId().toString().trim()));
            view.setFilter(filter);
            
            SocketMsgSetCollection socketMsgSetCollection = iSocketMsgSet.getSocketMsgSetCollection(view);
            if(socketMsgSetCollection != null && socketMsgSetCollection.size() != 0){
            	for(int i=0;i< socketMsgSetCollection.size() ;i++){
            		SocketMsgSetInfo socketMsgSetInfoTemp = socketMsgSetCollection.get(i);
            		if(socketMsg.length()>=socketMsgSetInfoTemp.getMsgNoEnd()){
            			String msgNo=socketMsg.substring(socketMsgSetInfoTemp.getMsgNoSta(), socketMsgSetInfoTemp.getMsgNoEnd());
            			if(socketMsgSetInfoTemp.getMsgNo().equals(msgNo)){
            				socketMsgSetInfo=socketMsgSetInfoTemp;
            			}
            		}
            	}
            }            
    	}catch(Exception ex){
    		logger.info("获取执行脚本时发生错误");
    		logger.error(ex);
    	}
		return  socketMsgSetInfo;
	}
	
	public static String getHeartMsgScriptByType(Context ctx,UrlConfigInfo configInfo){
		
		String script="";
		try
    	{
    		
    		ISocketMsgSet iSocketMsgSet  = null;
    		if(ctx != null){
    			iSocketMsgSet = SocketMsgSetFactory.getLocalInstance(ctx);
    		}else{
    			iSocketMsgSet = SocketMsgSetFactory.getRemoteInstance();
    		}
    		
            EntityViewInfo view = new EntityViewInfo();
            SelectorItemCollection sic = view.getSelector();
            sic.add(new SelectorItemInfo("*"));
            sic.add(new SelectorItemInfo("Channel.*"));
            FilterInfo filter = new FilterInfo();
            FilterItemCollection fic = filter.getFilterItems();
            fic.add(new FilterItemInfo("Channel.id", configInfo.getId().toString().trim()));
            fic.add(new FilterItemInfo("MsgType", SocketMsgType.HeartBeat.getValue().toString()));
            
            view.setFilter(filter);
            
            SocketMsgSetCollection socketMsgSetCollection = iSocketMsgSet.getSocketMsgSetCollection(view);
            if(socketMsgSetCollection != null && socketMsgSetCollection.size() != 0){
            		SocketMsgSetInfo socketMsgSetInfo = socketMsgSetCollection.get(0);
            		script=socketMsgSetInfo.getScript();
            }            
    	}catch(Exception ex){
    		logger.info("获取心跳报文执行脚本时发生错误");
    		logger.error(ex);
    	}
		return  script;
	}
	/**
     * 获取业务类型对象
     * @param ctx  服务器端上下文,客户端调用时传null值
     * @param number    业务类型编码
     * @return  BizTypeInfo 业务类型对象
     */
    public static BizTypeInfo getBizTypeByNumber(Context ctx, String number)
    {
    	BizTypeInfo bizType = null;
    	try
    	{
    		if(StringUtil.isEmptyString(number)){
    			return null;
    		}
    		
    		IBizType iBizType = null;
    		if(ctx != null){
    			iBizType = BizTypeFactory.getLocalInstance(ctx);
    		}else{
    			iBizType = BizTypeFactory.getRemoteInstance();
    		}
    		
            EntityViewInfo view = new EntityViewInfo();
            SelectorItemCollection sic = view.getSelector();
            sic.add(new SelectorItemInfo("id"));
            sic.add(new SelectorItemInfo("number"));
            sic.add(new SelectorItemInfo("name"));
            FilterInfo filter = new FilterInfo();
            FilterItemCollection fic = filter.getFilterItems();
            fic.add(new FilterItemInfo("number", number));
            view.setFilter(filter);
            
            BizTypeCollection bizTypeCollection = iBizType.getBizTypeCollection(view);
            if(bizTypeCollection != null && bizTypeCollection.size() != 0){
            	bizType = bizTypeCollection.get(0);
            }            
    	}catch(Exception ex){
    		logger.info("获取业务类型时发生错误");
    		logger.error(ex);
    	}
        return bizType;
    }
    /**
     * 获取事务类型对象
     * @param ctx
     * @param number 事务类型编码
     * @return TransactionTypeInfo 事务类型对象
     */
    public static TransactionTypeInfo getTransactionTypeInfoByNumber(Context ctx, String number)
    {
    	TransactionTypeInfo transinfo=new com.kingdee.eas.basedata.scm.common.TransactionTypeInfo();

		try {
    	ITransactionType iTransactionType = null;
		if(ctx != null){
			iTransactionType = TransactionTypeFactory.getLocalInstance(ctx);
		}else{
			iTransactionType = TransactionTypeFactory.getRemoteInstance();
		}
		
		EntityViewInfo view = new com.kingdee.bos.metadata.entity.EntityViewInfo();
		SelectorItemCollection sic = view.getSelector();
		sic.add("id");
		sic.add("number");
		sic.add("name");
		FilterInfo filter = new com.kingdee.bos.metadata.entity.FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("number", number));
		view.setFilter(filter);
		TransactionTypeCollection transCollection =new com.kingdee.eas.basedata.scm.common.TransactionTypeCollection();
		
		transCollection = iTransactionType.getTransactionTypeCollection(view);
		
		
			if (transCollection != null && transCollection.size() != 0) {
				transinfo = transCollection.get(0);
			}
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transinfo;
    }
    /**
     * 获取单据类型对象
     * @param ctx  服务器端上下文,客户端调用时传null值
     * @param number    单据类型编码
     * @return  BillTypeInfo 单据类型对象
     */
    public static BillTypeInfo getBillTypeByNumber(Context ctx, String number)
    {
    	BillTypeInfo billType = null;
    	try
    	{
    		if(StringUtil.isEmptyString(number)){
    			return null;
    		}
    		
    		IBillType iBillType = null;
    		if(ctx != null){
    			iBillType = BillTypeFactory.getLocalInstance(ctx);
    		}else{
    			iBillType = BillTypeFactory.getRemoteInstance();
    		}
    		
            EntityViewInfo view = new EntityViewInfo();
            SelectorItemCollection sic = view.getSelector();
            sic.add(new SelectorItemInfo("id"));
            sic.add(new SelectorItemInfo("number"));
            sic.add(new SelectorItemInfo("name"));
            FilterInfo filter = new FilterInfo();
            FilterItemCollection fic = filter.getFilterItems();
            fic.add(new FilterItemInfo("number", number));
            view.setFilter(filter);
            
            BillTypeCollection billTypeCollection = iBillType.getBillTypeCollection(view);
            if(billTypeCollection != null && billTypeCollection.size() != 0){
            	billType = billTypeCollection.get(0);
            }            
    	}catch(Exception ex){
    		logger.info("获取单据类型时发生错误");
    		logger.error(ex);
    	}
        return billType;
    }
    
    /**
     * 获取交货方式对象
     * @param ctx  服务器端上下文,客户端调用时传null值
     * @param number    交货方式编码
     * @return  DeliveryTypeInfo 交货方式对象
     */
    public static DeliveryTypeInfo geDeliveryTypeByNumber(Context ctx, String number)
    {
    	DeliveryTypeInfo info = null;
    	try
    	{
    		if(StringUtil.isEmptyString(number)){
    			return null;
    		}
    		
    		IDeliveryType iDeliveryType = null;
    		if(ctx != null){
    			iDeliveryType =DeliveryTypeFactory.getLocalInstance(ctx);
    		}else{
    			iDeliveryType = DeliveryTypeFactory.getRemoteInstance();
    		}
    		
            EntityViewInfo view = new EntityViewInfo();
            SelectorItemCollection sic = view.getSelector();
            sic.add(new SelectorItemInfo("id"));
            sic.add(new SelectorItemInfo("number"));
            sic.add(new SelectorItemInfo("name"));
            FilterInfo filter = new FilterInfo();
            FilterItemCollection fic = filter.getFilterItems();
            fic.add(new FilterItemInfo("number", number));
            view.setFilter(filter);
            
            DeliveryTypeCollection infoCollection = iDeliveryType.getDeliveryTypeCollection(view);
            if(infoCollection != null && infoCollection.size() != 0){
            	info = infoCollection.get(0);
            }            
    	}catch(Exception ex){
    		logger.info("获取单据类型时发生错误");
    		logger.error(ex);
    	}
        return info;
    }
    
    
    
    
    /**
     * 获取配额方式实体
     * @param ctx
     * @param 配额方式编码
     * @return QuotaPolicyInfo 配额方式实体
     * @throws BOSException
     */
     public static   QuotaPolicyInfo getQuotaPolicyInfoByNumber(Context ctx, String number)  {
     	
    	 QuotaPolicyInfo info = null;
     	try{
     		IQuotaPolicy  iQuotaPolicy=null;
     		if(ctx != null){
     			iQuotaPolicy=QuotaPolicyFactory.getLocalInstance(ctx);
 			}else{
 				iQuotaPolicy =QuotaPolicyFactory.getRemoteInstance();
 			}
     		
     		
 	    	EntityViewInfo view = new EntityViewInfo();
 	        SelectorItemCollection sic = view.getSelector();
 	        sic.add("*");
 	        FilterInfo filter = new FilterInfo();
 	        FilterItemCollection fic = filter.getFilterItems();
 	        fic.add(new FilterItemInfo("number", number));
 	        view.setFilter(filter);
 	
 	       QuotaPolicyCollection infoCollection = iQuotaPolicy.getQuotaPolicyCollection(view);
 	        
 	        if(infoCollection != null && infoCollection.size() != 0){
 	        	info = infoCollection.get(0);
 	        }
     	}
     	catch(Exception ex){
     		logger.error(ex);
     	}
         return info;
     }
    
    /**
     * 获取物料实体
     * @param ctx
     * @param 物料编码
     * @return MaterialInfo 物料实体
     * @throws BOSException
     */
     public static   MaterialInfo getMaterialInfoByNumber(Context ctx, String companyID,String number)  {
     	
    	 MaterialInfo info = null;
     	try{
     		IMaterial  iMaterial=null;
     		if(ctx != null){
     			iMaterial=MaterialFactory.getLocalInstance(ctx);
 			}else{
 				iMaterial =MaterialFactory.getRemoteInstance();
 			}
     		
     		
 	    	EntityViewInfo view = new EntityViewInfo();
 	        SelectorItemCollection sic = view.getSelector();
 	        sic.add("*");
 	        sic.add("baseUnit.*");
 	       sic.add("materialGroup.*");
 	       sic.add("baseUnit.MeasureUnitGroup.*");
 	        FilterInfo filter = new FilterInfo();
 	        FilterItemCollection fic = filter.getFilterItems();
 	        fic.add(new FilterItemInfo("number", number));
 	       fic.add(new FilterItemInfo("adminCU.id", companyID));
 	        view.setFilter(filter);
 	
 	       MaterialCollection infoCollection = iMaterial.getMaterialCollection(view);
 	        
 	        if(infoCollection != null && infoCollection.size() != 0){
 	        	info = infoCollection.get(0);
 	        }
     	}
     	catch(Exception ex){
     		logger.error(ex);
     	}
         return info;
     }
     
     
     /**
      * 获取物料组实体
      * @param ctx
      * @param 物料组编码
      * @return MaterialGroupInfo 物料组实体
      * @throws BOSException
      */
      public static   MaterialGroupInfo getMaterialGroupInfoByNumber(Context ctx, String companyID,String number)  {
      	
     	 MaterialGroupInfo info = null;
      	try{
      		IMaterialGroup  iMaterialGroup=null;
      		if(ctx != null){
      			iMaterialGroup=MaterialGroupFactory.getLocalInstance(ctx);
  			}else{
  				iMaterialGroup =MaterialGroupFactory.getRemoteInstance();
  			}
      		
      		
  	    	EntityViewInfo view = new EntityViewInfo();
  	        SelectorItemCollection sic = view.getSelector();
  	        sic.add("*");
  	       sic.add("groupStandard.*");
  	        FilterInfo filter = new FilterInfo();
  	        FilterItemCollection fic = filter.getFilterItems();
  	        fic.add(new FilterItemInfo("number", number));
  	       fic.add(new FilterItemInfo("CU.id", companyID));
  	        view.setFilter(filter);
  	
  	      MaterialGroupCollection infoCollection = iMaterialGroup.getMaterialGroupCollection(view);
  	        
  	        if(infoCollection != null && infoCollection.size() != 0){
  	        	info = infoCollection.get(0);
  	        }else
  	        {
  	        	info=getMaterialGroupInfoByNumber(ctx,"00000000-0000-0000-0000-000000000000CCE7AED4",number);
  	        }
      	}
      	catch(Exception ex){
      		logger.error(ex);
      	}
          return info;
      }
      
      
      /**
       * 获取物料基本分类实体
       * @param ctx
       * @param 物料基本分类编码
       * @return MaterialGroupStandardInfo 物料基本分类实体
       * @throws BOSException
       */
       public static   MaterialGroupStandardInfo getMaterialGroupStandardInfoByNumber(Context ctx, String companyID,String number)  {
       	
    	   MaterialGroupStandardInfo info = null;
       	try{
       		IMaterialGroupStandard  iMaterialGroupStandard=null;
       		if(ctx != null){
       			iMaterialGroupStandard=MaterialGroupStandardFactory.getLocalInstance(ctx);
   			}else{
   				iMaterialGroupStandard =MaterialGroupStandardFactory.getRemoteInstance();
   			}
       		
       		
   	    	EntityViewInfo view = new EntityViewInfo();
   	        SelectorItemCollection sic = view.getSelector();
   	        sic.add("*");
   	       
   	        FilterInfo filter = new FilterInfo();
   	        FilterItemCollection fic = filter.getFilterItems();
   	        fic.add(new FilterItemInfo("number", number));
   	       fic.add(new FilterItemInfo("CU.id", companyID));
   	        view.setFilter(filter);
   	
   	     MaterialGroupStandardCollection infoCollection = iMaterialGroupStandard.getMaterialGroupStandardCollection(view);
   	        
   	        if(infoCollection != null && infoCollection.size() != 0){
   	        	info = infoCollection.get(0);
   	        }else
   	        {
   	        	info=getMaterialGroupStandardInfoByNumber(ctx,"00000000-0000-0000-0000-000000000000CCE7AED4",number);
   	        }
       	}
       	catch(Exception ex){
       		logger.error(ex);
       	}
           return info;
       }
       
      
     /**  
      * 判断是否是一个中文汉字  
      *   
      * @param c  字符  
      * @param charset  字符编码 GBK  
      * @return true表示是中文汉字，false表示是英文字母  
      * @throws UnsupportedEncodingException  
      *             使用了JAVA不支持的编码格式  
      */  
     public static boolean isChineseChar(char c,String charset)
             throws UnsupportedEncodingException {
     	
         // 如果字节数大于1，是汉字   
         // 以这种方式区别英文字母和中文汉字并不是十分严谨，但在这个题目中，这样判断已经足够了   
         return String.valueOf(c).getBytes(charset).length > 1;   
     }
     
     /**
      * 计算当前String字符串所占的总Byte长度
      * @param args 要截取的字符串
      * @param charset 要截取的字符串编码  GBK
      * @return 返回值int型，字符串所占的字节长度，如果args为空或者“”则返回0
      * @throws UnsupportedEncodingException
      */
     public static int getStringByteLenths(String args,String charset) throws UnsupportedEncodingException{
     	return args!=null&&args!=""? args.getBytes(charset).length:0;
     }
     
     /**
      * 获取与字符串每一个char对应的字节长度数组
      * @param  args  要计算的目标字符串
      * @param  charset  要计算的目标字符串编码
      * @return int[]数组类型，返回与字符串每一个char对应的字节长度数组
      * @throws UnsupportedEncodingException
      */
     public static int[] getByteLenArrays(String args,String charset) throws UnsupportedEncodingException{
     	char[] strlen=args.toCharArray();
     	int[] charlen=new int[strlen.length];
     	for (int i = 0; i < strlen.length; i++) {
     		charlen[i]=String.valueOf(strlen[i]).getBytes(charset).length;
 		}
     	return charlen;
     }

     /**  
      * 按字节截取字符串 ，指定截取起始字节位置与截取字节长度
      *   
      * @param orignal  要截取的字符串  
      * @param charset  字符串编码 GBK   
      * @param start 截取Byte起始位； 
      * @param count 截取Byte长度； 
      * @return 截取后的字符串  
      * @throws UnsupportedEncodingException  
      *              使用了JAVA不支持的编码格式  
      */  
     public static String substringByte(String orignal,String charset,int start, int count){
     	
     	//如果目标字符串为空，则直接返回，不进入截取逻辑；
     	if(orignal==null || "".equals(orignal))return orignal;
     	
     	//截取Byte长度必须>0
     	if (count <= 0) return orignal;
     	
     	//截取的起始字节数必须比
 	    if(start<0) start=0;
 	     
     	//目标char Pull buff缓存区间；
     	StringBuffer buff = new StringBuffer();
     	 
     	 try {
     		 
     		//截取字节起始字节位置大于目标String的Byte的length则返回空值
     		if (start >= getStringByteLenths(orignal,charset)) return null;
 			 
     		// int[] arrlen=getByteLenArrays(orignal);
     		int len=0;
     		
     		char c;
     		
     		//遍历String的每一个Char字符，计算当前总长度
     		//如果到当前Char的的字节长度大于要截取的字符总长度，则跳出循环返回截取的字符串。
     		 for (int i = 0; i < orignal.toCharArray().length; i++) {
     			 
     			 c=orignal.charAt(i);
     			 
     			 //当起始位置为0时候
     			 if(start==0){
     				 
     				 len+=String.valueOf(c).getBytes(charset).length;
         			 if(len<=count) buff.append(c);
         			 else break;
         			 
     			 }else{
     				 
     				 //截取字符串从非0位置开始
     				 len+=String.valueOf(c).getBytes(charset).length;
     				 if(len>=start&&len<start+count){
     					 buff.append(c);
     				 }
     				 if(len>start+count) break;
     				 
     			 }
 			 }
 	    	 
 		} catch (UnsupportedEncodingException e) {
 			e.printStackTrace();
 		}
     	 //返回最终截取的字符结果;
     	 //创建String对象，传入目标char Buff对象
     	 return new String(buff);
     }
   
     /**
      * 截取指定长度字符串
      * @param orignal要截取的目标字符串
      * @param charset要截取的目标字符串编码格式
      * @param count 指定截取长度
      * @return 返回截取后的字符串
      */
     public static String substringByte(String orignal,String charset, int count){
     	return substringByte(orignal,charset,0,count);
     }
    
     /**
      * 获取更新类型对象
      * @param ctx
      * @param number 更新类型编码
      * @return InvUpdateTypeInfo 更新类型对象
      */
     public static InvUpdateTypeInfo getInvUpdateTypeInfoByNumber(Context ctx, String number){
    	InvUpdateTypeInfo invtypeinfo=new InvUpdateTypeInfo();
    	try {
	    	IInvUpdateType iInvUpdateType = null;
			if(ctx != null){
				iInvUpdateType = InvUpdateTypeFactory.getLocalInstance(ctx);
			}else{
				iInvUpdateType = InvUpdateTypeFactory.getRemoteInstance();
			}
			
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = view.getSelector();
			sic.add("*");
			FilterInfo filter = new com.kingdee.bos.metadata.entity.FilterInfo();
			FilterItemCollection fic = filter.getFilterItems();
			fic.add(new FilterItemInfo("number", number));
			view.setFilter(filter);
			InvUpdateTypeCollection invtypeCollection =new com.kingdee.eas.basedata.scm.im.inv.InvUpdateTypeCollection();
			
			
				invtypeCollection = iInvUpdateType.getInvUpdateTypeCollection(view);
			
			
			if (invtypeCollection != null && invtypeCollection.size() != 0) {
				invtypeinfo = invtypeCollection.get(0);
			}
    	} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return invtypeinfo;
     }
     
     /**
      * 根据botp生成目标单据 并返回
      * @param ctx
      * @param sourceType BOSObjectType 源单
      * @param destType BOSObjectType 目标单
      * @param CoreBillBaseCollection  源单集合
      * @param botpId 单据转换规则ID
      * @return CoreBillBaseCollection 目标单据集合
      * @throws BOSException
      */
     public static CoreBillBaseCollection botp(Context ctx, BOSObjectType  sourceType, BOSObjectType destType, IObjectCollection sourceCollIn, String botpId) throws BOSException {  
    	 CoreBillBaseCollection destCollRe=new CoreBillBaseCollection();
    	 CoreBillBaseCollection sourceColl=(CoreBillBaseCollection)sourceCollIn;
    		IBOTMapping botMapping = BOTMappingFactory.getLocalInstance(ctx);
    	        CoreBillBaseInfo model = sourceColl.get(0); //源单对象
    		try {
    		    BOTMappingInfo botp = botMapping.getMapping(model, destType.toString(), DefineSysEnum.BTP);
    	            if (botp == null) {
    	                throw new FMException(FMException.NODESTBILL);
    	            }
    	            IBTPManager botpManager = BTPManagerFactory.getLocalInstance(ctx);
    	            BTPTransformResult btpResult = null;
    	            btpResult = botpManager.transformForBotp(sourceColl, destType.toString(), new ObjectUuidPK(botpId));
    	            IObjectCollection destColl = btpResult.getBills(); //目标单据集合
    	            BOTRelationCollection botRelations = btpResult.getBOTRelationCollection();  
    	            for (int i = 0; i < destColl.size(); i++) {
    	                CoreBillBaseInfo destBillInfo = (CoreBillBaseInfo) destColl.getObject(i); //目标单据
    	                botpManager.saveRelations(destBillInfo, botRelations);
    	                destCollRe.add(destBillInfo);
    	            }
    		} catch (Exception e) {
    		    throw new BOSException(e);
    		}
    	   return destCollRe;
    	}
     /**
      * 获取仓库对象
      * @param ctx
      * @param number 仓库编码
      * @return WarehouseInfo 仓库对象
      */
     public static WarehouseInfo getWarehouseInfoByNumber(Context ctx, String number ){
     WarehouseInfo wareinfo=new WarehouseInfo();
     try {
	     IWarehouse iWarehouse = null;
			if(ctx != null){
				iWarehouse = WarehouseFactory.getLocalInstance(ctx);
			}else{
				iWarehouse = WarehouseFactory.getRemoteInstance();
			}
			
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = view.getSelector();
			sic.add("*");
			FilterInfo filter = new com.kingdee.bos.metadata.entity.FilterInfo();
			FilterItemCollection fic = filter.getFilterItems();
			fic.add(new FilterItemInfo("number", number));
			view.setFilter(filter);
			WarehouseCollection wareCollection =new WarehouseCollection();
			
			
				wareCollection = iWarehouse.getWarehouseCollection(view);
			
			
			if (wareCollection != null && wareCollection.size() != 0) {
				wareinfo = wareCollection.get(0);
			}
     } catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wareinfo;
     }
	/**
	 * 16进制直接转换成为字符串(无需Unicode解码)
	 * @param hexStr
	 * @return
	 */
	public static String hexStr2Str(String hexStr) {
//	    String str = "0123456789ABCDEF";
//	    char[] hexs = hexStr.toCharArray();
//	    byte[] bytes = new byte[hexStr.length() / 2];
//	    int n;
//	    for (int i = 0; i < bytes.length; i++) {
//	        n = str.indexOf(hexs[2 * i]) * 16;
//	        n += str.indexOf(hexs[2 * i + 1]);
//	        bytes[i] = (byte) (n & 0xff);
//	    }
//	    return new String(bytes);
		String s=hexStr;
		String[] baKeyword = new String[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
		try {
		baKeyword[i] = String.valueOf(((byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16))));
		} catch (Exception e) {
		e.printStackTrace();
		return "";
		}
		}
		try {
//		s = new String(baKeyword, "UTF-8");// UTF-16le:Not
		} catch (Exception e1) {
		e1.printStackTrace();
		return "";
		}
		
		String[] ASCIIss = baKeyword;  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < ASCIIss.length; i++) {  
            sb.append((char) (Integer.parseInt(ASCIIss[i])));  
        }  
        return sb.toString();  
//		return s;
	}
	/**
	 * 字符串转换成为16进制(无需Unicode编码)
	 * @param str
	 * @return
	 */
	public static String str2HexStr(String str) {
	    char[] chars = "0123456789ABCDEF".toCharArray();
	    StringBuilder sb = new StringBuilder("");
	    byte[] bs = str.getBytes();
	    int bit;
	    for (int i = 0; i < bs.length; i++) {
	        bit = (bs[i] & 0x0f0) >> 4;
	        sb.append(chars[bit]);
	        bit = bs[i] & 0x0f;
	        sb.append(chars[bit]);
	        // sb.append(' ');
	    }
	    return sb.toString().trim();
	}
}
