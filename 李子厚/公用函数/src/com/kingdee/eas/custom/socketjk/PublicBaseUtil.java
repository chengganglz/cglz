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
	 * �ж��Ƿ���ڱ������
	 * @param ctx
	 * @param objValue ��Ҫ�����ʵ��
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
	 * ��ȡ�Զ����� 
	 * @param ctx
	 * @param info ��Ҫ�Զ������ʵ��
	 * @param orgUnitId ��֯ID
	 * @return String ����
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
	     * ��ȡ�Զ��������Ŀ�Զ�����
	     * @param ctx
	     * @param info ��Ҫ�Զ������ʵ��
	     * @param orgUnitId ��֯ID
	     * @return String ����
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
	     * ��ȡ��˾��Ϣ
	     * 
	     * ���ݹ�˾�����ȡ��˾����
	     * 
	     * @param ctx ��������������
	     * @param number ��˾����
	     * @return CompanyOrgUnitInfo ��˾����
	     * @throws BOSException ��ѯ��˾��Ϣʱ�������������쳣
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
	     * ��ȡ��˾��Ϣ
	     * 
	     * ���ݹ�˾�����ȡ��˾����
	     * 
	     * @param ctx ��������������
	     * @param id ��˾Id
	     * @return CompanyOrgUnitInfo ��˾����
	     * @throws BOSException ��ѯ��˾��Ϣʱ�������������쳣
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
	     * ��ȡ�ɱ�����
	     * 
	     * ���ݳɱ����ı����ȡ�ɱ����Ķ���
	     * 
	     * @param ctx ��������������
	     * @param id �ɱ�����Id
	     * @return CostCenterOrgUnitInfo �ɱ����Ķ���
	     * @throws BOSException ��ѯ��˾��Ϣʱ�������������쳣
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
	     * ��ȡ������֯
	     * @param ctx
	     * @param adminID ������֯ID
	     * @return AdminOrgUnitInfo ������֯ʵ��
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
	      * ��ȡ��֯��Ԫ
	      * @param ctx
	      * @param orgID ��֯��ԪID
	      * @return FullOrgUnitInfo ��֯��Ԫ����
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
	      * ��ȡ������֯
	      * @param ctx
	      * @param adminNumber ������֯����
	      * @return AdminOrgUnitInfo ������֯ʵ��
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
		      * ��ȡ������֯
		      * @param ctx
		      * @param number ������֯����
		      * @return SaleOrgUnitInfo ������֯ʵ��
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
			      * ��ȡ�����֯
			      * @param ctx
			      * @param number �����֯����
			      * @return  StorageOrgUnitInfo �����֯ʵ��
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
			      * ��ȡ������
			      * @param ctx
			      * @param number ���������
			      * @return SaleGroupInfo  ������ʵ��
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
				      * ��ȡԱ��
				      * @param ctx
				      * @param number Ա������
				      * @return PersonInfo Ա��ʵ��
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
	     * ��ȡ���Ƶ�Ԫ����
	     * 
	     * ���ݿ��Ƶ�Ԫ�����ȡ���Ƶ�Ԫ����
	     * 
	     * @param ctx ��������������,�ͻ��˵���ʱ��nullֵ
	     * @param number ���Ƶ�Ԫ����
	     * @return CtrlUnitInfo ���Ƶ�Ԫ����
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
	     * �����������ȡ���Ƶ�Ԫ����
	     * 
	     * ���ݿ��Ƶ�Ԫ�����ȡ���Ƶ�Ԫ����
	     * 
	     * @param ctx ��������������,�ͻ��˵���ʱ��nullֵ
	     * @param number ���Ƶ�Ԫ����
	     * @return CtrlUnitInfo ���Ƶ�Ԫ����
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
	     * ��ȡ�ͻ�����
	     * 
	     * 
	     * @param ctx ��������������,�ͻ��˵���ʱ��nullֵ
	     * @param number �ͻ��������
	     * @param number ���̷����׼ID
	     * @return  CSSPGroupInfo �ͻ��������
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
	     * ��ȡ���̷����׼
	     * 
	     * 
	     * @param ctx ��������������,�ͻ��˵���ʱ��nullֵ
	     * @param String ���̷����׼ID
	     * @return CSSPGroupStandardInfo ���̷����׼����
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
	     * ��ȡ�Զ��������Ŀ����
	     * 
	     * 
	     * @param ctx ��������������,�ͻ��˵���ʱ��nullֵ
	     * @param number �Զ��������Ŀ�������
	     * @return GeneralAsstActTypeGroupInfo �Զ��������Ŀ�������
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
	     * ��ȡ������λ��
	     * 
	     * 
	     * @param ctx ��������������,�ͻ��˵���ʱ��nullֵ
	     * @param number ������λ�����
	     * @return MeasureUnitGroupInfo ������λ�����
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
	     * ��ȡ������λ
	     * 
	     * 
	     * @param ctx ��������������,�ͻ��˵���ʱ��nullֵ
	     * @param GroupNumber ������λ�����
	     * @param number ������λ����
	     * @return MeasureUnitInfo ������λ����
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
	     * ��ȡ������λ
	     * 
	     * 
	     * @param ctx ��������������,�ͻ��˵���ʱ��nullֵ
	     * @param GroupNumber ������λ�����
	     * @param number ������λ����
	     * @return MeasureUnitInfo ������λ����
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
	     * ��ȡ�û�����
	     * 
	     * �����û����ƻ�ȡ�û�����
	     * 
	     * @param ctx �����������,�ͻ��˵���ʱ��nullֵ
	     * @param name �û�����
	     * @return UserInfo �û�����
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
	    		logger.info("=====��ȡ�û���Ϣʱ��������=====");
	    		logger.error(ex);
	    	}
	    	
	    	return user;
	    }
	    /**
	     * ��ȡ�û�����
	     * 
	     * �����û����ƻ�ȡ�û�����
	     * 
	     * @param ctx �����������,�ͻ��˵���ʱ��nullֵ
	     * @param id �û�ID
	     * @return UserInfo �û�����
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
	    		logger.info("=====��ȡ�û���Ϣʱ��������=====");
	    		logger.error(ex);
	    	}
	    	
	    	return user;
	    }
	    /**
	     * ��ȡ��Ŀ����
	     * 
	     * ���ݿ�Ŀ���뼰��˾��Ϣ��ȡ��Ŀ����
	     * 
	     * @param ctx �����������,�ͻ��˵���ʱ��nullֵ
	     * @param number ��Ŀ����
	     * @param company ��˾����
	     * @return AccountViewInfo ��Ŀ����
	     * @throws BOSException ִ�п�Ŀ��ѯʱ���ܻ��������쳣
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
	     * ��ȡ��Ŀ����
	     * 
	     * ���ݿ�Ŀid����˾��Ϣ��ȡ��Ŀ����
	     * 
	     * @param ctx �����������,�ͻ��˵���ʱ��nullֵ
	     * @param id ��ĿID
	     * @param company ��˾����
	     * @return AccountViewInfo ��Ŀ����
	     * @throws BOSException ִ�п�Ŀ��ѯʱ���ܻ��������쳣
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
	     * ��ȡ����������
	     * 
	     * ���ݿ�Ŀ����������Id��ȡ���������ͼ���
	     * 
	     * @param ctx �����������
	     * @param caa ��Ŀ����������ID
	     * @return AsstActTypeCollection �������к�����Ŀ���ͼ���
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
	    		logger.info("=====��ȡ��Ŀ������Ŀʱ��������======");
	    		logger.error(ex);
	    	}
	    	return asstTypes;
	    }
	    
	    /**
	     * ��ȡ�ͻ�����
	     * 
	     * ���ݿͻ������ȡ�ͻ�����
	     * 
	     * @param ctx �����������,�ͻ��˵���ʱ��nullֵ
	     * @param number �ͻ�����
	     * @return CustomerInfo �ͻ�����
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
	    		logger.info("====��ȡ�ͻ���Ϣʱ��������=====");
	    		logger.error(ex);
	    	}
	    	
	    	return customer;
	    }
	    
	    
	    /**
	     * ��ȡ�ͻ���˾��Ϣ����
	     * 
	     * ���ݿͻ�ID ��ȡ�ͻ���˾��Ϣ����
	     * 
	     * @param ctx �����������,�ͻ��˵���ʱ��nullֵ
	     * @param number �ͻ�ID
	     * @return CustomerCompanyInfoInfo �ͻ���˾��Ϣ����
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
	    		logger.info("====��ȡ�ͻ���Ϣʱ��������=====");
	    		logger.error(ex);
	    	}
	    	
	    	return info;
	    }
	    
	    /**
	     * ��ȡ�ͻ�����
	     * 
	     * ���ݿͻ������ȡ�ͻ�����
	     * 
	     * @param ctx �����������,�ͻ��˵���ʱ��nullֵ
	     * @param id �ͻ�id
	     * @return CustomerInfo �ͻ�����
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
	    		logger.info("====��ȡ�ͻ���Ϣʱ��������=====");
	    		logger.error(ex);
	    	}
	    	
	    	return customer;
	    }
	    /**
	     * ��ȡ�ͻ�����
	     * 
	     * ���ݿͻ��������ȡ�ͻ�����
	     * 
	     * @param ctx �����������,�ͻ��˵���ʱ��nullֵ
	     * @param number �ͻ�������
	     * @return CustomerInfo �ͻ�����
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
	    		logger.info("====��ȡ�ͻ���Ϣʱ��������=====");
	    		logger.error(ex);
	    	}
	    	
	    	return customer;
	    }
	    /**
	     * ��ȡ��Ӧ�̶���
	     * 
	     * ���ݹ�Ӧ���������ȡ��Ӧ�̶���
	     * 
	     * @param ctx �����������,�ͻ��˵���ʱ��nullֵ
	     * @param number ��Ӧ��������
	     * @return SupplierInfo ��Ӧ�̶���
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
	    		logger.info("====��ȡ��Ӧ����Ϣʱ��������=====");
	    		logger.error(ex);
	    	}
	    	
	    	return supplier;
	    }
	    
	    /**
	     * ��ȡ��Ӧ�̶���
	     * 
	     * ���ݹ�Ӧ�̱����ȡ��Ӧ�̶���
	     * 
	     * @param ctx �����������,�ͻ��˵���ʱ��nullֵ
	     * @param number ��Ӧ�̱���
	     * @return SupplierInfo ��Ӧ�̶���
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
	    		logger.info("====��ȡ��Ӧ����Ϣʱ��������=====");
	    		logger.error(ex);
	    	}
	    	
	    	return supplier;
	    }
	    /**
	     * ��ȡ��Ӧ�̶���
	     * 
	     * ���ݹ�Ӧ�̱����ȡ��Ӧ�̶���
	     * 
	     * @param ctx �����������,�ͻ��˵���ʱ��nullֵ
	     * @param id ��Ӧ��ID
	     * @return SupplierInfo ��Ӧ�̶���
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
	    		logger.info("====��ȡ��Ӧ����Ϣʱ��������=====");
	    		logger.error(ex);
	    	}
	    	
	    	return supplier;
	    }
	    /**
	     * ��ȡ�Զ��������Ŀ����
	     * 
	     * ����������Ϣ��ȡ�Զ��������Ŀ����
	     * 
	     * @param ctx �����������
	     * @param companyId ��˾ID
	     * @param groupId �Զ��������Ŀ���ID
	     * @param number �������Զ��������Ŀ����
	     * @return GeneralAsstActTypeInfo �Զ��������Ŀ����
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
	    		logger.info("====��ȡ�Զ��������Ŀ��Ϣʱ��������=====");
	    		logger.error(ex);
	    	}
	        return typeInfo;
	    }
	    
	    /**
	     * ��ȡ�Զ��������Ŀ����
	     * 
	     * ���ݳ������ȡ�Զ��������Ŀ����
	     * 
	     * @param ctx �����������
	     * @param companyId ��˾ID
	     * @param groupId �Զ��������Ŀ���ID
	     * @param number �Զ��������Ŀ������
	     * @return GeneralAsstActTypeInfo �Զ��������Ŀ����
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
	    		logger.info("====��ȡ�Զ��������Ŀ��Ϣʱ��������=====");
	    		logger.error(ex);
	    	}
	        return typeInfo;
	    }
	    /**
	     * ��ȡƾ֤�ڼ�
	     * 
	     * �����ڼ������ڼ���ݻ�ȡƾ֤�ڼ����
	     * 
	     * @param ctx �����������,�ͻ��˵���ʱ��nullֵ
	     * @param periodNumber �ڼ���� ��periodYear �ڼ����YYYY
	     * @return PeriodInfo ƾ֤�ڼ����
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
	    		logger.info("====��ȡƾ֤�ڼ���Ϣʱ��������=====");
	    		logger.error(ex);
	    	}
	    	return periodInfo;
	    } 
	    /**
	     * ��ȡƾ֤���Ͷ���
	     * 
	     * ����ƾ֤����˵����ȡƾ֤���Ͷ���
	     * 
	     * @param ctx ��������������,�ͻ��˵���ʱ��nullֵ
	     * @param description ƾ֤����˵��
	     * @return VoucherTypeInfo ƾ֤���Ͷ���
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
	    		logger.info("=====��ȡƾ֤����ʱ��������====");
	    		logger.error(ex);
	    	}	
	    	return typeInfo;
	    }
	    
	    /**
	     * ��ȡ�û�����
	     * 
	     * �����û������ȡ�û�����
	     * 
	     * @param ctx �����������,�ͻ��˵���ʱ��nullֵ
	     * @param number �û�����
	     * @return UserInfo �û�����
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
	    		logger.info("=====��ȡ�û���Ϣʱ��������=====");
	    		logger.error(ex);
	    	}
	    	
	    	return user;
	    }  
	    
	    /**
	     * ��ȡƾ֤���Ͷ���
	     * 
	     * ����ƾ֤�������ƻ�ȡƾ֤���Ͷ���
	     * 
	     * @param ctx ��������������,�ͻ��˵���ʱ��nullֵ
	     * @param name ƾ֤��������
	     * @return VoucherTypeInfo ƾ֤���Ͷ���
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
	    		logger.info("=====��ȡƾ֤����ʱ��������====");
	    		logger.error(ex);
	    	}
	    	
	    	return typeInfo;
	    }
	    /**
	     * ��ȡ�û�����
	     * @param ctx
	     * @param id �û�ID
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
	    		logger.info("=====��ȡ�û���Ϣʱ��������=====");
	    		logger.error(ex);
	    	}
	    	
	    	return user;
	    }
	    
	    /**
	     * ��ȡ�����˻���Ϣ
	     * 
	     * ���������˻������ȡ�����˻���Ϣ
	     * @param ctx �����������,�ͻ��˵���ʱ��nullֵ
	     * @param number �����˻�����
	     * @return AccountBankInfo �����˻�����
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
	    		logger.info("=====��ȡ�����˻���Ϣʱ��������=====");
	    		logger.error(ex);
	    	}
	    	
	    	return acctBank;
	    }
	    
	    /**
	     * ��ȡ�����˻���Ϣ
	     * 
	     * ���������˻������ȡ�����˻���Ϣ
	     * @param ctx �����������,�ͻ��˵���ʱ��nullֵ
	     * @param ID �����˻�ID
	     * @return AccountBankInfo �����˻�����
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
	    		logger.info("=====��ȡ�����˻���Ϣʱ��������=====");
	    		logger.error(ex);
	    	}
	    	
	    	return acctBank;
	    }
	    /**
	     * ��ȡ����������Ŀ��Ӧ�ĺ�����Ŀ����
	     * 
	     * ���ݸ���������Ŀ�����ȡ��Ӧ�ĺ�����Ŀ����
	     * 
	     * @param ctx �����������
	     * @param asstActNumber ����������Ŀ����
	     * @return String ������Ŀ����
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
	     * ��ȡ�ұ����
	     * 
	     * ���ݱұ�����ȡ�ұ����
	     * 
	     * @param ctx ��������������,�ͻ��˵���ʱ��nullֵ
	     * @param currency �ұ����
	     * @return CurrencyInfo �ұ����
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
	    		logger.info("��ȡ�ұ�ʱ��������");
	    		logger.error(ex);
	    	}
	        return currencyInfo;
	    }
	    
	    /**
	     * ��ȡ�ұ����
	     * 
	     * ���ݱұ�ID��ȡ�ұ����
	     * 
	     * @param ctx ��������������,�ͻ��˵���ʱ��nullֵ
	     * @param currencyID �ұ�ID
	     * @return CurrencyInfo �ұ����
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
	    		logger.info("��ȡ�ұ�ʱ��������");
	    		logger.error(ex);
	    	}
	        return currencyInfo;
	    }
	    
	    
	    
	    /**
	     * ��ȡ�������Ͷ���
	     * @param ctx  ��������������,�ͻ��˵���ʱ��nullֵ
	     * @param id   ��������ID
	     * @return  SettlementTypeInfo �������Ͷ���
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
	    		logger.info("��ȡ��������ʱ��������");
	    		logger.error(ex);
	    	}
	        return settlementTypeInfo;
	    }
	    
	    /**
	     * ��ȡ�տ����Ͷ���
	     * @param ctx  ��������������,�ͻ��˵���ʱ��nullֵ
	     * @param id   �տ�����ID
	     * @return  ReceivingBillTypeInfo �տ����Ͷ���
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
	    		logger.info("��ȡ�տ�����ʱ��������");
	    		logger.error(ex);
	    	}
	        return receivingBillTypeInfo;
	    }
	    
	    /**
	     * ��ȡ�ո������Ͷ���
	     * @param ctx  ��������������,�ͻ��˵���ʱ��nullֵ
	     * @param id   �ո�������ID
	     * @return  PaymentTypeInfo �ո������Ͷ���
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
	    		logger.info("��ȡ�ո�������ʱ��������");
	    		logger.error(ex);
	    	}
	        return paymentTypeInfoInfo;
	    }
	    
	    
	    /**
	     * ��ȡ�ո������Ͷ���
	     * @param ctx  ��������������,�ͻ��˵���ʱ��nullֵ
	     * @param id   �ո������ͱ���
	     * @return  PaymentTypeInfo �ո������Ͷ���
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
	    		logger.info("��ȡ�ո�������ʱ��������");
	    		logger.error(ex);
	    	}
	        return paymentTypeInfoInfo;
	    }
	    
	/**
	 * ����ʵ�������Ժ����ͣ�����ֵ
	 * @param model
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void testReflect(Object model) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
	    Field[] field = model.getClass().getDeclaredFields();        //��ȡʵ������������ԣ�����Field����  
	    for(int j=0 ; j<field.length ; j++){     //������������
	            String name = field[j].getName();    //��ȡ���Ե�����
	            
	            System.out.println("attribute name:"+name);     
	            name = name.substring(0,1).toUpperCase()+name.substring(1); //�����Ե����ַ���д�����㹹��get��set����
	            String type = field[j].getGenericType().toString();    //��ȡ���Ե�����
	            if(type.equals("class java.lang.String")){   //���type�������ͣ���ǰ�����"class "�����������
	                Method m = model.getClass().getMethod("get"+name);
	                String value = (String) m.invoke(model);    //����getter������ȡ����ֵ
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
    		logger.info("��ȡִ�нű�ʱ��������");
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
    		logger.info("��ȡ��������ִ�нű�ʱ��������");
    		logger.error(ex);
    	}
		return  script;
	}
	/**
     * ��ȡҵ�����Ͷ���
     * @param ctx  ��������������,�ͻ��˵���ʱ��nullֵ
     * @param number    ҵ�����ͱ���
     * @return  BizTypeInfo ҵ�����Ͷ���
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
    		logger.info("��ȡҵ������ʱ��������");
    		logger.error(ex);
    	}
        return bizType;
    }
    /**
     * ��ȡ�������Ͷ���
     * @param ctx
     * @param number �������ͱ���
     * @return TransactionTypeInfo �������Ͷ���
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
     * ��ȡ�������Ͷ���
     * @param ctx  ��������������,�ͻ��˵���ʱ��nullֵ
     * @param number    �������ͱ���
     * @return  BillTypeInfo �������Ͷ���
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
    		logger.info("��ȡ��������ʱ��������");
    		logger.error(ex);
    	}
        return billType;
    }
    
    /**
     * ��ȡ������ʽ����
     * @param ctx  ��������������,�ͻ��˵���ʱ��nullֵ
     * @param number    ������ʽ����
     * @return  DeliveryTypeInfo ������ʽ����
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
    		logger.info("��ȡ��������ʱ��������");
    		logger.error(ex);
    	}
        return info;
    }
    
    
    
    
    /**
     * ��ȡ��ʽʵ��
     * @param ctx
     * @param ��ʽ����
     * @return QuotaPolicyInfo ��ʽʵ��
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
     * ��ȡ����ʵ��
     * @param ctx
     * @param ���ϱ���
     * @return MaterialInfo ����ʵ��
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
      * ��ȡ������ʵ��
      * @param ctx
      * @param ���������
      * @return MaterialGroupInfo ������ʵ��
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
       * ��ȡ���ϻ�������ʵ��
       * @param ctx
       * @param ���ϻ����������
       * @return MaterialGroupStandardInfo ���ϻ�������ʵ��
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
      * �ж��Ƿ���һ�����ĺ���  
      *   
      * @param c  �ַ�  
      * @param charset  �ַ����� GBK  
      * @return true��ʾ�����ĺ��֣�false��ʾ��Ӣ����ĸ  
      * @throws UnsupportedEncodingException  
      *             ʹ����JAVA��֧�ֵı����ʽ  
      */  
     public static boolean isChineseChar(char c,String charset)
             throws UnsupportedEncodingException {
     	
         // ����ֽ�������1���Ǻ���   
         // �����ַ�ʽ����Ӣ����ĸ�����ĺ��ֲ�����ʮ���Ͻ������������Ŀ�У������ж��Ѿ��㹻��   
         return String.valueOf(c).getBytes(charset).length > 1;   
     }
     
     /**
      * ���㵱ǰString�ַ�����ռ����Byte����
      * @param args Ҫ��ȡ���ַ���
      * @param charset Ҫ��ȡ���ַ�������  GBK
      * @return ����ֵint�ͣ��ַ�����ռ���ֽڳ��ȣ����argsΪ�ջ��ߡ����򷵻�0
      * @throws UnsupportedEncodingException
      */
     public static int getStringByteLenths(String args,String charset) throws UnsupportedEncodingException{
     	return args!=null&&args!=""? args.getBytes(charset).length:0;
     }
     
     /**
      * ��ȡ���ַ���ÿһ��char��Ӧ���ֽڳ�������
      * @param  args  Ҫ�����Ŀ���ַ���
      * @param  charset  Ҫ�����Ŀ���ַ�������
      * @return int[]�������ͣ��������ַ���ÿһ��char��Ӧ���ֽڳ�������
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
      * ���ֽڽ�ȡ�ַ��� ��ָ����ȡ��ʼ�ֽ�λ�����ȡ�ֽڳ���
      *   
      * @param orignal  Ҫ��ȡ���ַ���  
      * @param charset  �ַ������� GBK   
      * @param start ��ȡByte��ʼλ�� 
      * @param count ��ȡByte���ȣ� 
      * @return ��ȡ����ַ���  
      * @throws UnsupportedEncodingException  
      *              ʹ����JAVA��֧�ֵı����ʽ  
      */  
     public static String substringByte(String orignal,String charset,int start, int count){
     	
     	//���Ŀ���ַ���Ϊ�գ���ֱ�ӷ��أ��������ȡ�߼���
     	if(orignal==null || "".equals(orignal))return orignal;
     	
     	//��ȡByte���ȱ���>0
     	if (count <= 0) return orignal;
     	
     	//��ȡ����ʼ�ֽ��������
 	    if(start<0) start=0;
 	     
     	//Ŀ��char Pull buff�������䣻
     	StringBuffer buff = new StringBuffer();
     	 
     	 try {
     		 
     		//��ȡ�ֽ���ʼ�ֽ�λ�ô���Ŀ��String��Byte��length�򷵻ؿ�ֵ
     		if (start >= getStringByteLenths(orignal,charset)) return null;
 			 
     		// int[] arrlen=getByteLenArrays(orignal);
     		int len=0;
     		
     		char c;
     		
     		//����String��ÿһ��Char�ַ������㵱ǰ�ܳ���
     		//�������ǰChar�ĵ��ֽڳ��ȴ���Ҫ��ȡ���ַ��ܳ��ȣ�������ѭ�����ؽ�ȡ���ַ�����
     		 for (int i = 0; i < orignal.toCharArray().length; i++) {
     			 
     			 c=orignal.charAt(i);
     			 
     			 //����ʼλ��Ϊ0ʱ��
     			 if(start==0){
     				 
     				 len+=String.valueOf(c).getBytes(charset).length;
         			 if(len<=count) buff.append(c);
         			 else break;
         			 
     			 }else{
     				 
     				 //��ȡ�ַ����ӷ�0λ�ÿ�ʼ
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
     	 //�������ս�ȡ���ַ����;
     	 //����String���󣬴���Ŀ��char Buff����
     	 return new String(buff);
     }
   
     /**
      * ��ȡָ�������ַ���
      * @param orignalҪ��ȡ��Ŀ���ַ���
      * @param charsetҪ��ȡ��Ŀ���ַ��������ʽ
      * @param count ָ����ȡ����
      * @return ���ؽ�ȡ����ַ���
      */
     public static String substringByte(String orignal,String charset, int count){
     	return substringByte(orignal,charset,0,count);
     }
    
     /**
      * ��ȡ�������Ͷ���
      * @param ctx
      * @param number �������ͱ���
      * @return InvUpdateTypeInfo �������Ͷ���
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
      * ����botp����Ŀ�굥�� ������
      * @param ctx
      * @param sourceType BOSObjectType Դ��
      * @param destType BOSObjectType Ŀ�굥
      * @param CoreBillBaseCollection  Դ������
      * @param botpId ����ת������ID
      * @return CoreBillBaseCollection Ŀ�굥�ݼ���
      * @throws BOSException
      */
     public static CoreBillBaseCollection botp(Context ctx, BOSObjectType  sourceType, BOSObjectType destType, IObjectCollection sourceCollIn, String botpId) throws BOSException {  
    	 CoreBillBaseCollection destCollRe=new CoreBillBaseCollection();
    	 CoreBillBaseCollection sourceColl=(CoreBillBaseCollection)sourceCollIn;
    		IBOTMapping botMapping = BOTMappingFactory.getLocalInstance(ctx);
    	        CoreBillBaseInfo model = sourceColl.get(0); //Դ������
    		try {
    		    BOTMappingInfo botp = botMapping.getMapping(model, destType.toString(), DefineSysEnum.BTP);
    	            if (botp == null) {
    	                throw new FMException(FMException.NODESTBILL);
    	            }
    	            IBTPManager botpManager = BTPManagerFactory.getLocalInstance(ctx);
    	            BTPTransformResult btpResult = null;
    	            btpResult = botpManager.transformForBotp(sourceColl, destType.toString(), new ObjectUuidPK(botpId));
    	            IObjectCollection destColl = btpResult.getBills(); //Ŀ�굥�ݼ���
    	            BOTRelationCollection botRelations = btpResult.getBOTRelationCollection();  
    	            for (int i = 0; i < destColl.size(); i++) {
    	                CoreBillBaseInfo destBillInfo = (CoreBillBaseInfo) destColl.getObject(i); //Ŀ�굥��
    	                botpManager.saveRelations(destBillInfo, botRelations);
    	                destCollRe.add(destBillInfo);
    	            }
    		} catch (Exception e) {
    		    throw new BOSException(e);
    		}
    	   return destCollRe;
    	}
     /**
      * ��ȡ�ֿ����
      * @param ctx
      * @param number �ֿ����
      * @return WarehouseInfo �ֿ����
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
	 * 16����ֱ��ת����Ϊ�ַ���(����Unicode����)
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
	 * �ַ���ת����Ϊ16����(����Unicode����)
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
