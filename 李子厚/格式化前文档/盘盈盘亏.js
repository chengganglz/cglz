var imp = JavaImporter();  
imp.importPackage(Packages.com.kingdee.eas.util.app);  
imp.importPackage(Packages.org.apache.log4j); 
imp.importPackage(Packages.com.kingdee.bos); 
imp.importPackage(Packages.com.kingdee.bos.dao); 
imp.importPackage(Packages.com.kingdee.bos.dao.ormapping);
imp.importPackage(Packages.com.kingdee.eas.base.permission); 
imp.importPackage(Packages.com.kingdee.eas.basedata.assistant); 
imp.importPackage(Packages.com.kingdee.eas.basedata.master.material); 
imp.importPackage(Packages.com.kingdee.eas.basedata.org); 
imp.importPackage(Packages.com.kingdee.eas.basedata.scm.common); 
imp.importPackage(Packages.com.kingdee.eas.basedata.scm.im.inv); 
imp.importPackage(Packages.com.kingdee.eas.common); 
imp.importPackage(Packages.com.kingdee.eas.custom.socketjk); 
imp.importPackage(Packages.com.kingdee.eas.scm.common); 
imp.importPackage(Packages.com.kingdee.eas.scm.im.inv); 
imp.importPackage(Packages.com.kingdee.eas.util); 
imp.importPackage(Packages.java.lang); 
imp.importPackage(Packages.java.math); 
imp.importPackage(Packages.java.sql); 
imp.importPackage(Packages.java.text); 
imp.importPackage(Packages.java.util); 

with(imp){ 
	System.out.println("/******************************  CCCW11 ��Ϣ��¼��ʼ   **********************************/");
	var socketMsg = methodCtx.getParamValue(0);  //���ձ��� 
	// var socketMsg ="0156CCCW1120190424091613CCCWDL1613CCCWDL11920009300                                      L101                                                  0020201250000000000001 ";
	var billID = methodCtx.getParamValue(1); // ҵ��ԪID  
	var result = methodCtx.getParamValue(2); //�ɹ�ʧ��  true:false  �����з���ֵ  methodCtx.getParam(2).setValue("true") 
	var sendMsg = methodCtx.getParamValue(3); // ���ͱ���   �����Ҫ���͵���,������ƴ�÷ŵ����ﷵ��  methodCtx.getParam(3).setValue("")
  	var errMsg = methodCtx.getParamValue(4); //������Ϣ methodCtx.getParam(4).setValue("")  
	var ctx = pluginCtx.getContext(); //����������� 
	var isSucess=true;
	var errMsg="";
	var strCreatorNo="user"; //�����˱���
	var strCUNo="01";// ����Ԫ����
	//var strBizDate="20190418" ; //ҵ������  ��MES��ȡ
	var billStatus=BillBaseStatusEnum.ADD; //����״̬
	var purchaseType = PurchaseTypeEnum.PURCHASE;
	var strBizTypeNo="501";//ҵ�����ͱ���
	var strBillTypeNo="108";//�������ͱ���
	var strTranstionType="032";//��������
	var strNeedStockOrgNo="01";//�跽�����֯
	var  isInTax=true;
	var sdfDate = new SimpleDateFormat("yyyy-MM-dd");
	var dwh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,5,20);//���ĺ�  ��MES��ȡ
	//dwh="CCCW0120190418110558";
	System.out.println("���ĺ�"+dwh);
	var dwt=PublicBaseUtil.substringByte(socketMsg,"GBK" ,1,29);//����ͷ  ��MES��ȡ
	//dwt="0524CCCW2020190224040527CCCWD11203778";
	System.out.println("�������ϵ��ĺ�"+dwt);
	var flag=PublicBaseUtil.substringByte(socketMsg,"GBK",165,1);//������־  ��MES��ȡ
	if(flag!=null){
		flag = flag.trim();
	}
	System.out.println("������־"+flag);
	// 1����ӯ��2���̿�
	if("1".equals(flag)){
		System.out.println("/******************************  CCCW11-��ӯ��¼��ʼ   **********************************/");
		var strBizDate=PublicBaseUtil.substringByte(socketMsg,"GBK" ,11,8) ; //ҵ������  ��MES��ȡ
		if(strBizDate==null){
			isSucess=false;
			errMsg=errMsg + "ҵ�����ڲ���Ϊ��\r\n";
		}else{
			strBizDate=strBizDate.replace(" ", "");
		}
		if("".equals(strBizDate)){
			isSucess=false;
			errMsg=errMsg + "ҵ�����ڲ���Ϊ��\r\n";
		}else if(strBizDate!=null){
			var isdate=PublicBaseUtil.valiDateTimeWithLongFormat(strBizDate);
			if(!isdate){
				isSucess=false;
				errMsg=errMsg + "ҵ�����ڸ�ʽ���Ϸ�\r\n";
			}
		}
		
		System.out.println("ҵ������"+strBizDate);
		var  cu=PublicBaseUtil.getCU(ctx, strCUNo); 
      	if(cu==null){
      		isSucess=false;
  			errMsg=errMsg + "����Ԫ����Ϊ��\r\n";
      	}
		// ҵ������
  		var bizType=PublicBaseUtil.getBizTypeByNumber(ctx, strBizTypeNo);
  		if(bizType==null){
  			isSucess=false;
  			errMsg=errMsg + "ҵ�����Ͳ���Ϊ��\r\n";
  		}
  		// ��������
  		var  billType=PublicBaseUtil.getBillTypeByNumber(ctx, strBillTypeNo);
  		if(billType==null){
  			isSucess=false;
  			errMsg=errMsg + "�������Ͳ���Ϊ��\r\n";
  		}
  		var  trantypeInfo=PublicBaseUtil.getTransactionTypeInfoByNumber(ctx, strTranstionType);
  		if(trantypeInfo==null){
  			isSucess=false;
  			errMsg=errMsg + "�������Ͳ���Ϊ��\r\n";
  		}
  		//�ɱ�����
		var costCenter=PublicBaseUtil.getCostCenterOrgUnitInfoById(ctx,cu.getId().toString());
		if(costCenter==null){
			isSucess=false;
			errMsg=errMsg + "�ɱ����Ĳ���Ϊ��\r\n";
		}
  		//��ҵ����֯
  		var  storageInfo=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strCUNo);
  		if(storageInfo==null){
  			isSucess=false;
  			errMsg=errMsg + "��ҵ����֯����Ϊ��\r\n";
  		}
		var strMaterialNo="";
  		var strUnit="��";//��λĬ�϶�
		var strLot=PublicBaseUtil.substringByte(socketMsg,"GBK" ,30,20);//���κ�  ��MES��ȡ
		// ��ͬ��
		var hth=PublicBaseUtil.substringByte(socketMsg,"GBK" ,70,20);//  ��MES��ȡ
		System.out.println("��ͬ��-hth:"+hth);
		// ��Լ��
		var hyh=PublicBaseUtil.substringByte(socketMsg,"GBK" ,50,20);//  ��MES��ȡ
		System.out.println("��Լ��-hyh:"+hyh);
  		var strStockNo="001";//�ֿ����  ��MES��ȡ
  		var bgQty=new BigDecimal("0");
  		var bgPrice=new BigDecimal("10");
  		var bgTaxRate=new BigDecimal("0");
  		var bgAmount=new BigDecimal("1000");
      	
  		var bgTaxPrice=bgPrice.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
  		var bgTaxAmount=bgAmount.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
  		var bgTax=bgAmount.multiply(bgTaxRate).setScale(2, BigDecimal.ROUND_HALF_UP);
		if(strLot!=null){
			strLot = strLot.trim();
		}
		/*var ph="0418";//�ƺ�  ��MES��ȡ
		if(ph!=null){
			ph=ph.trim();
		}
		else
		{
			ph="";
		}
		if("".equals(ph))
		{
			isSucess=false;
			errMsg=errMsg + "�ƺŲ���Ϊ��\r\n";
		}
		var phmc="ABABA";//�ƺ�����  ��MES��ȡ
		if(phmc!=null){
			phmc=phmc.trim();
		}
		else
		{
			phmc="";
		}
		if("".equals(phmc))
		{
			isSucess=false;
			errMsg=errMsg + "�ƺ����Ʋ���Ϊ��\r\n";
		}*/
		var phmc=PublicBaseUtil.substringByte(socketMsg,"GBK",94,50); //�ƺ�����  ��MES��ȡ
		if(phmc!=null){
			phmc=phmc.trim();
		}else{
			phmc="";
		}
		if("".equals(phmc))
		{
			isSucess=false;
			errMsg=errMsg + "�ƺ����Ʋ���Ϊ��\r\n";
		}
		var ph="";
		var sql = "SELECT * FROM CT_CUS_Paihao where fname_l2 ='"+phmc+"'";
		var rows = DbUtil.executeQuery(ctx, sql);
		if(rows!= null&&rows.next() ) {
			ph=rows.getString("fnumber");
		}else{
			ph=phmc;
		}
		System.out.println("�ƺ�����"+phmc);
		System.out.println("�ƺ�"+ph);
		
		var kd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,150,5);//���  ��MES��ȡ
		if(kd!=null){
			kd=kd.trim();
		}else
		{
			kd="0";
		}
		try
		{
			bgKd=new BigDecimal(kd).divide(new BigDecimal("10"));;
			//bgKd=BigDecimal.valueOf(4160);
		}catch(e){
			
		}
		var hd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,144,6);//���  ��MES��ȡ
		if(hd!=null){
			hd=hd.trim();
		}else
		{
			hd="0";
		}
		try
		{
			bgHd=new BigDecimal(hd).divide(new BigDecimal("1000"));
			//bgHd=BigDecimal.valueOf(0.81);
		}catch(e){
			
		}
		var zl=PublicBaseUtil.substringByte(socketMsg,"GBK" ,155,10);//����  ��MES��ȡ
		if(zl!=null){
			zl=zl.trim();
		}else
		{
			zl="0";
		}
		//zl="20";		
		try{
			bgQty=new BigDecimal(zl).divide(new BigDecimal("1000"));
		}catch(e){
			
		}
		if (bgKd.compareTo(BigDecimal.ZERO)==0){
    		isSucess=false;
			errMsg=errMsg + "��Ȳ���Ϊ��\r\n";
    	}
    	if (bgHd.compareTo(BigDecimal.ZERO)==0){
    		isSucess=false;
			errMsg=errMsg + "��Ȳ���Ϊ��\r\n";
    	}
    	if (bgQty.compareTo(BigDecimal.ZERO)==0){
    		isSucess=false;
			errMsg=errMsg + "��������Ϊ��\r\n";
    	}
		System.out.println("����"+zl);
		System.out.println("���"+hd);
		System.out.println("���"+kd);
		//var materialGroupNum="09";//�������  ��MES��ȡ
		// ��ʱ������¼���,�ж��Ƿ���ڶ�Ӧֵ,��¼��־
		var temp_materialGroupNum = '';
		var materialGroupNum = PublicBaseUtil.substringByte(socketMsg,"GBK" ,90,4);
			materialGroupNum = materialGroupNum.trim();
		if("L101".equals(materialGroupNum)){
			materialGroupNum="09";
		}else if("L701".equals(materialGroupNum)){
			materialGroupNum="19";
		}
		temp_materialGroupNum = materialGroupNum;
		if("".equals(temp_materialGroupNum)){
			System.out.println("exception-��������û���ҵ���Ӧ���������");
		}
		System.out.println("�������"+materialGroupNum);		
		var  material= SocketFacadeFactory.getLocalInstance(ctx).getMaterialInfo("01", "5", materialGroupNum, ph, phmc, bgKd, bgHd, strUnit, "", true);
		if(material==null){
			isSucess=false;
			errMsg=errMsg + "���ϲ���Ϊ��\r\n";
		}
		// �ɱ����Ŀ�ʼ
		// �ɱ����ı���
		var cbzxNo = PublicBaseUtil.substringByte(socketMsg,"GBK" ,90,4);
		// �ɱ�����ʵ��
		var costcenter = null;
		System.out.println("�ɱ����ı���-cbzxNo:"+cbzxNo);
		if("".equals(cbzxNo))
		{
			isSucess=false;
			errMsg=errMsg + "�ɱ����Ĳ���Ϊ��\r\n";
		}else{
			// �ɱ����Ķ�Ӧ��ϵ�洢
			var temp_cbzx = '';
			cbzxNo = cbzxNo.trim()
			/**
			 * 
			 * ����ţ��ɱ����ı��� ��Ӧ��ϵ
			 * L101 ��ϴ 007
			 * L701 ��п 008
			 * L201 ��������
			 * L801 ��Ŀ�ƽ
			*/
			if('L101'.equals(cbzxNo)){
				temp_cbzx = '007'
			}
			if('L701'.equals(cbzxNo)){
				temp_cbzx = '008'
			}
			if("".equals(temp_cbzx)){
				isSucess=false;
				System.out.println("Error-��ǰ�����û���ҵ���Ӧ�ĳɱ�����,�޷�����,�ж�");
				errMsg=errMsg + "��ǰ�����û���ҵ���Ӧ�ĳɱ�����\r\n";
			}else{
				costcenter = PublicBaseUtil.getCostCenterOrgUnitInfoByNumber(ctx,temp_cbzx);//�ɱ����ı���  ��MES��ȡ
				if(costcenter==null){
					System.out.println("Error-�ɱ�����Ϊ��,�޷�����,�ж�");
					isSucess=false;
					errMsg=errMsg + "�ɱ����Ĳ���Ϊ��\r\n";
				}
			}
		}
		// �ɱ����Ľ���
		/****************************************************************************/
		/**
		 * ʱ��:2019-4-24 15:34
		 * �޸��ˣ�������
		 * �޸����ݣ����ӻ�ȡ����id
		 */
		// ����id
		var wlid = '';
		// ����ʵ��
		var material = null 
		if(isSucess){
			System.out.println("MSG-�����ȡ����");
			// ��ȡ����ʵ��
			material =SocketFacadeFactory.getLocalInstance(ctx).getMaterialInfo("01", "5", materialGroupNum, ph, phmc, bgKd, bgHd, strUnit, "", true);
			if(material==null){
				System.out.println("Error-��ȡ���ϲ����ڣ��޷����У��ж�");
				isSucess=false;
				errMsg=errMsg + "���ϲ���Ϊ��\r\n";
			}else{
				// ��ȡ����id
				wlid = material.getId().toString();
				// ������д����
				if(isSucess){
					System.out.println("ǰ����������-�����������������¼");
					// ��ȡBOS����				
					var bosid=com.kingdee.bos.util.BOSUuid.create("5E53BAD4");
					var id=bosid.toString();
					// ����ʱ��
					var format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					var fcreatetime = new java.util.Date();
					var fcreatetimestr=format.format(fcreatetime);
					// ����ʱ���ʽ��
					var sdf = new SimpleDateFormat("yyyyMMdd");
					var scsjDate=sdf.parse(strBizDate);
					var scsj = format.format(scsjDate); 
					// ����
					var fnumber = new java.util.Date().getTime().toString();
					// ���ݿ��ֶ� fid,FNUMBER,FNAME_L2,fcreatorid,fcreatetime,fcontrolunitid,CFCKJH,CFHTH,CFHYH,CFJZH,CFZL,CFSCSJ,CFWLID,CFSFRK
					// �ֶ����� Ψһ��ʶ,����,����,����id,����ʱ��,���Ƶ�Ԫ,���ھ��,��ͬ��,��Լ��,�����,����,����ʱ��,����id,�Ƿ����,���ĺ�,��������
					// ֵ id,rjh,hth,'256c221a-0106-1000-e000-10d7c0a813f413B7DE7F',fcreatetimestr,'00000000-0000-0000-0000-000000000000CCE7AED4',strLot,hth,hyh,cbzxNo,bgQty,scsj,wlid,0,dwh,2
					sql = "insert into CT_CUS_JZSCCCJL(fid,FNUMBER,FNAME_L2,fcreatorid,fcreatetime,fcontrolunitid,CFCKJH,CFHTH,CFHYH,CFJZH,CFZL,CFSCSJ,CFWLID,CFSFRK,CFDWH,CFDWLX) values('"+id+"','"+fnumber+"','"+fnumber+"','256c221a-0106-1000-e000-10d7c0a813f413B7DE7F',to_date('"+fcreatetimestr+"'),'00000000-0000-0000-0000-000000000000CCE7AED4','"+strLot+"','"+hth+"','"+hyh+"','"+cbzxNo+"','"+bgQty+"',to_date('"+scsj+"'),'"+wlid+"','0','"+dwh+"',2)"
					System.out.println("ִ��sql:"+sql);		
					try {
						DbUtil.execute(ctx, sql);
						System.out.println("�����ɹ�-��������������¼");
					} catch (error) {
						System.out.println("����ʧ��-��������������¼ʧ��");
					}		
					
				}
			}
		}
		System.out.println("/******************************  CCCW11-��ӯ��¼����   **********************************/");
		/**
		 * �޸�:�����ɵ��ݣ�ֱ��д��̨��
		 * ʱ�䣺2019-04-24 20:00
		 * �޸��ˣ�������
		 */		
		/*
		var  storOrgUnit=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
		if(storOrgUnit==null){
			isSucess=false;
			errMsg=errMsg + "�����֯����Ϊ��\r\n";
		}
		var  stock=PublicBaseUtil.getWarehouseInfoByNumber(ctx, strStockNo);
		if(stock==null){
			isSucess=false;
			errMsg=errMsg + "�ֿⲻ��Ϊ��\r\n";
		}
		var  invUpType=PublicBaseUtil.getInvUpdateTypeInfoByNumber(ctx, "001");
		if(invUpType==null){
			isSucess=false;
			errMsg=errMsg + "�������Ͳ���Ϊ��\r\n";
		}
		*/
		/*
		if(isSucess){
			//����ί������
			var info=new OtherInWarehsBillInfo();
			info.put("bxNumber","��ӯ����CCCW11");
			//�Ƶ���
			var creator=PublicBaseUtil.getUserByNumber(ctx, strCreatorNo);
			
			if(creator!=null){
				info.setCreator(creator);
			}else{
				info.setCreator(ContextUtil.getCurrentUserInfo(ctx));
			}
			//��ȡ����Ԫ
			if(cu!=null){
				info.setCU(cu); //���Ƶ�Ԫ
			}
			//����ʱ��
			try {
				info.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
			} catch (e) {
				
			}
			//ҵ������
			try
			{
				var bizDate;
				var  sdf = new SimpleDateFormat("yyyyMMdd");
				bizDate=sdf.parse(strBizDate);
				info.setBizDate(bizDate);
				var c = Calendar.getInstance();
				c.setTime(bizDate);
				var year=c.get(Calendar.YEAR);   //���
				var period=c.get(Calendar.MONTH);
				var month =c.get(Calendar.YEAR)*100+c.get(Calendar.MONTH) ; //�ڼ�
				var iday=(c.get(Calendar.YEAR)*100+c.get(Calendar.MONTH)+1)*100+c.get(Calendar.MONDAY);
				info.setYear(year);
				info.setPeriod(period);
				info.setMonth(month);
				info.setDay(iday);
			}catch(ex){
				isSucess=false;
				errMsg=errMsg + "���ڸ�ʽ����ȷ\r\n";
				//throw new BOSException("���ڸ�ʽ����ȷ");
				
			}
			//����״̬
			info.setBaseStatus(billStatus);
			//info.setPurchaseType(purchaseType);
			// ҵ������
			if(bizType!=null){
				info.setBizType(bizType);
			}
			// ��������
			if(billType!=null){
				info.setBillType(billType);
			}
			if(trantypeInfo!=null){
				info.setTransactionType(trantypeInfo);
				
			}
			
			//�ɱ�����
			if(costCenter!=null){
				info.setCostCenterOrgUnit(costCenter);
			}
			// ������֯
			var  company=PublicBaseUtil.getCompany(ctx, cu.getNumber());
			//��ҵ����֯
			if(storageInfo!=null){
				info.setStorageOrgUnit(storageInfo);
			}
			//��ӯ����¼ 
			
			//������¼ʵ��
			var entry=new OtherInWarehsBillEntryInfo();
			//��¼�к�
			entry.setSeq(1);
			entry.setSourceBillEntrySeq(0);
			//����
			
			if (material!=null){
				entry.setMaterial(material);
				entry.setBaseUnit(material.getBaseUnit());
				//��λ
				var  unit=PublicBaseUtil.getMeasureUnitInfoByName(ctx, material.getBaseUnit().getMeasureUnitGroup().getNumber(), strUnit);
				if (unit!=null){
					entry.setUnit(unit);
				}else
				{
					isSucess=false;
					errMsg=errMsg + "��λ����Ϊ��\r\n";
				}
				
			}
			
			entry.setBaseStatus(EntryBaseStatusEnum.ADD);//
			//
			entry.setAssCoefficient(BigDecimal.ZERO);
			//�����֯
			if(storOrgUnit!=null){
				entry.setStorageOrgUnit(storOrgUnit);
				//entry.setReceiveStorageOrgUnit(storOrgUnit);
				
			}
			//������֯
			entry.setCompanyOrgUnit(company);
			//�ֿ�
			if(stock!=null){
				entry.setWarehouse(stock);
				//entry.setOutWarehouse(stock);
			}
			
			entry.setQty(bgQty);
			entry.setBaseQty(bgQty);
			//entry.setPrice(bgPrice);
			//entry.setTaxPrice(bgTaxPrice);
			//entry.setTaxRate(bgTaxRate);
			//entry.setAmount(bgAmount);
			entry.setLot(strLot);
			
			//var invInfo = InvUpdateTypeFactory.getLocalInstance(ctx).getInvUpdateTypeInfo(new ObjectUuidPK("8r0AAAAEaOnC73rf"));
			
			entry.setInvUpdateType(invUpType);
			info.getEntries().addObject(entry);
      	}
      	//entry.getInvUpdateType().getStoreTypePre().getStoreFlag();
      	if(isSucess){
      		
      		//MaterialReqBillInfo info1 = MaterialReqBillFactory.getLocalInstance(ctx).getMaterialReqBillInfo(new ObjectUuidPK("23sE0JysRKmP7TP55oLn7VAKt14="));
      		
      		var  pk=OtherInWarehsBillFactory.getLocalInstance(ctx).save(info);  //����
  			if(pk!=null){
  				var  tempIno=OtherInWarehsBillFactory.getLocalInstance(ctx).getOtherInWarehsBillInfo(pk);
  				if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.TEMPORARILYSAVED)) {
  					
  					OtherInWarehsBillFactory.getLocalInstance(ctx).submit(tempIno);
  				}
  				tempIno=OtherInWarehsBillFactory.getLocalInstance(ctx).getOtherInWarehsBillInfo(pk);
  				if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
  					OtherInWarehsBillFactory.getLocalInstance(ctx).audit(pk);
  				}
				methodCtx.getParam(2).setValue("true") ;
				methodCtx.getParam(4).setValue("�����ɹ�");
  				
  			}
      	}else
      	{
      		methodCtx.getParam(2).setValue("false") ;
			methodCtx.getParam(4).setValue(errMsg);
		  }
		  */
	}
	
	else if("2".equals(flag)){
		
				System.out.println("isSucess1"+isSucess);
		var strBizDate=PublicBaseUtil.substringByte(socketMsg,"GBK" ,11,8) ; //ҵ������  ��MES��ȡ
			if(strBizDate==null){
				isSucess=false;
				errMsg=errMsg + "ҵ�����ڲ���Ϊ��\r\n";
			}else{
				strBizDate=strBizDate.replace(" ", "");
			}
			if("".equals(strBizDate)){
				isSucess=false;
				errMsg=errMsg + "ҵ�����ڲ���Ϊ��\r\n";
			}else if(strBizDate!=null){
				var isdate=PublicBaseUtil.valiDateTimeWithLongFormat(strBizDate);
				if(!isdate){
					isSucess=false;
					errMsg=errMsg + "ҵ�����ڸ�ʽ���Ϸ�\r\n";
				}
			}
			
			System.out.println("ҵ������"+strBizDate);
		var strLot=PublicBaseUtil.substringByte(socketMsg,"GBK" ,30,20);//���κ�  ��MES��ȡ
		var bgQty=new BigDecimal("0");
		
		errMsg="";
      	strCreatorNo="user"; //�����˱���
      	strCUNo="01";// ����Ԫ����
      	//strBizDate="20190418" ; //ҵ������  ��MES��ȡ
      	billStatus=BillBaseStatusEnum.ADD; //����״̬
      	purchaseType = PurchaseTypeEnum.PURCHASE;
      	strBizTypeNo="511";//ҵ�����ͱ���
		strBillTypeNo="108";//�������ͱ���
		strTranstionType="029";//��������
      	strNeedStockOrgNo="01";//�跽�����֯
      	isInTax=true;
      	sdfDate = new SimpleDateFormat("yyyyMMdd");
		if(strLot!=null){
			strLot = strLot.trim();
		}
		var zl=PublicBaseUtil.substringByte(socketMsg,"GBK" ,155,10);//����  ��MES��ȡ
		if(zl!=null){
			zl=zl.trim();
		}else
		{
			zl="0";
		}
		try
		{
			bgQty=new BigDecimal(zl).divide(new BigDecimal("1000"));
		}catch(e){
			
		}
		if (bgQty.compareTo(BigDecimal.ZERO)==0){
    		isSucess=false;
			errMsg=errMsg + "��������Ϊ��\r\n";
    	}
		var strSQLInv = "select * from T_IM_Inventory where flot='"+strLot+"'  and FCURSTOREQTY <> 0";
		System.out.println("���۳���ӿ�"+strSQLInv);
		var rsInv = DbUtil.executeQuery(ctx, strSQLInv);
		if (rsInv.next()) {
			var curStoreQty=rsInv.getBigDecimal("FCURSTOREQTY");
			if(bgQty.compareTo(curStoreQty)!=0){
				isSucess=false;
				errMsg=errMsg + "����������һ��\r\n";
			}
		}
		var  cu=PublicBaseUtil.getCU(ctx, strCUNo); 
		if(cu==null){
			isSucess=false;
			errMsg=errMsg + "����Ԫ����Ϊ��\r\n";
		}
				System.out.println("isSucess3"+isSucess);
		// ҵ������
		var bizType=PublicBaseUtil.getBizTypeByNumber(ctx, strBizTypeNo);
		if(bizType==null){
			isSucess=false;
			errMsg=errMsg + "ҵ�����Ͳ���Ϊ��\r\n";
		}
				System.out.println("isSucess4"+isSucess);
		// ��������
		var  billType=PublicBaseUtil.getBillTypeByNumber(ctx, strBillTypeNo);
		if(billType==null){
			isSucess=false;
			errMsg=errMsg + "�������Ͳ���Ϊ��\r\n";
		}
		var  trantypeInfo=PublicBaseUtil.getTransactionTypeInfoByNumber(ctx, strTranstionType);
		if(trantypeInfo==null){
			isSucess=false;
			errMsg=errMsg + "�������Ͳ���Ϊ��\r\n";
		}
		//�ɱ�����
		var costCenter=PublicBaseUtil.getCostCenterOrgUnitInfoById(ctx,cu.getId().toString());
		if(costCenter==null){
			isSucess=false;
			errMsg=errMsg + "�ɱ����Ĳ���Ϊ��\r\n";
		}
		var  storageInfo=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, strCUNo);
		if(storageInfo==null){
			isSucess=false;
			errMsg=errMsg + "��ҵ����֯����Ϊ��\r\n";
		}
		var strMaterialNo="";
		var strUnit="��";//��λĬ�϶�

		var sqlLot = "SELECT * FROM T_IM_Inventory where FLot ='"+strLot+"'";
		System.out.println("sqlLot"+sqlLot);
		var strStockNo="";
		var rows = DbUtil.executeQuery(ctx, sqlLot);
			if(rows!= null && rows.next()) {
				var FID=rows.getString("FWarehouseID");
				
				var strStockNosql = "SELECT * FROM T_DB_WAREHOUSE WHERE FID='"+FID+"'";
				var row1 = DbUtil.executeQuery(ctx, strStockNosql);
				if(row1!=null &&row1.next()){
					strStockNo = row1.getString("FNUMBER");
				}
			}else{
				isSucess=false;
				errMsg=errMsg + "�ֿ���û�д�����\r\n";
			}
			
		
		//var strStockNo="003";//�ֿ����  ��MES��ȡ
		System.out.println("�ֿ����"+strStockNo);
		if(strStockNo==null){
				isSucess=false;
				errMsg=errMsg + "�ֿ���벻��Ϊ��\r\n";
		}

		var bgPrice=new BigDecimal("10");
		var bgTaxRate=new BigDecimal("0");
		var bgAmount=new BigDecimal("1000");
		
		var bgTaxPrice=bgPrice.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
		var bgTaxAmount=bgAmount.multiply(bgTaxRate.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_UP);
		var bgTax=bgAmount.multiply(bgTaxRate).setScale(2, BigDecimal.ROUND_HALF_UP);
		var bgKd=new BigDecimal("0"); 
		var bgHd=new BigDecimal("0");
		var kd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,150,5);//���  ��MES��ȡ
		if(kd!=null){
			kd=kd.trim();
		}else
		{
			kd="0";
		}
		try
		{
			bgKd=new BigDecimal(kd).divide(new BigDecimal("10"));;
			//bgKd=new BigDecimal("1200");
		}catch(e){
			
		}
		var hd=PublicBaseUtil.substringByte(socketMsg,"GBK" ,144,6);//���  ��MES��ȡ
		if(hd!=null){
			hd=hd.trim();
		}else
		{
			hd="0";
		}
		try
		{
			bgHd=new BigDecimal(hd).divide(new BigDecimal("1000"));
			//bgHd=new BigDecimal("0.80");
		}catch(e){
			
		}
		if (bgKd.compareTo(BigDecimal.ZERO)==0){
			isSucess=false;
			errMsg=errMsg + "��Ȳ���Ϊ��\r\n";
		}
		if (bgHd.compareTo(BigDecimal.ZERO)==0){
			isSucess=false;
			errMsg=errMsg + "��Ȳ���Ϊ��\r\n";
		}
		System.out.println("����"+zl);
		System.out.println("���"+hd);
		System.out.println("���"+kd);
		/*var ph="0422"; //�ƺ� ��MES��ȡ
		if(ph!=null){
			ph=ph.trim();
		}
		else
		{
			ph="";
		}
		if("".equals(ph))
		{
			isSucess=false;
			errMsg=errMsg + "�ƺŲ���Ϊ��\r\n";
		}
		var phmc="0422"; //�ƺ�����  ��MES��ȡ
		if(phmc!=null){
			phmc=phmc.trim();
		}else{
			phmc="";
		}
		if("".equals(phmc))
		{
			isSucess=false;
			errMsg=errMsg + "�ƺ����Ʋ���Ϊ��\r\n";
		}*/
			var phmc=PublicBaseUtil.substringByte(socketMsg,"GBK",94,50); //�ƺ�����  ��MES��ȡ
			if(phmc!=null){
				phmc=phmc.trim();
			}else{
				phmc="";
			}
			if("".equals(phmc))
			{
				isSucess=false;
				errMsg=errMsg + "�ƺ����Ʋ���Ϊ��\r\n";
			}
			System.out.println("�ƺ�����"+phmc);
			var ph="";
			var sql = "SELECT * FROM CT_CUS_Paihao where fname_l2 ='"+phmc+"'";
			var rows = DbUtil.executeQuery(ctx, sql);
			if(rows!= null&&rows.next() ) {
				ph=rows.getString("fnumber");
			}else{
				ph=phmc;
			}
			System.out.println("�ƺ�����"+phmc);
			System.out.println("�ƺ�"+ph);
		var strUnit="��"; //��λ  Ĭ�϶�
		//var materialGroupNum="06";//�������  ��MES��ȡ
		var materialGroupNum = PublicBaseUtil.substringByte(socketMsg,"GBK" ,90,4);
			materialGroupNum = materialGroupNum.trim();
		if("L101".equals(materialGroupNum)){
			materialGroupNum="09";
		}else if("L701".equals(materialGroupNum)){
			materialGroupNum="19";
		}
		System.out.println("�������"+materialGroupNum);
		var material= SocketFacadeFactory.getLocalInstance(ctx).getMaterialInfo("01", "5", materialGroupNum, ph, phmc, bgKd, bgHd, strUnit, "", true);
		if(material==null){
			isSucess=false;
			errMsg=errMsg + "���ϲ���Ϊ��\r\n";
		}
		var  storOrgUnit=PublicBaseUtil.getStorageOrgUnitInfoByNumber(ctx, cu.getNumber());
		if(storOrgUnit==null){
			isSucess=false;
			errMsg=errMsg + "�����֯����Ϊ��\r\n";
		}
		var  stock=PublicBaseUtil.getWarehouseInfoByNumber(ctx, strStockNo);
		if(stock==null){
			isSucess=false;
			errMsg=errMsg + "�ֿⲻ��Ϊ��\r\n";
		}
		var  invUpType=PublicBaseUtil.getInvUpdateTypeInfoByNumber(ctx, "002");
		if(invUpType==null){
			isSucess=false;
			errMsg=errMsg + "�������Ͳ���Ϊ��\r\n";
		}
		
		System.out.println("isSucess"+isSucess);
		if(isSucess){
			//�����̿�
			var info=new OtherIssueBillInfo();
			info.put("bxNumber","�̿�����CCCW11");
			//�Ƶ���
			var creator=PublicBaseUtil.getUserByNumber(ctx, strCreatorNo);
			
			if(creator!=null){
				info.setCreator(creator);
			}else{
				info.setCreator(ContextUtil.getCurrentUserInfo(ctx));
			}
			//��ȡ����Ԫ
			if(cu!=null){
				info.setCU(cu); //���Ƶ�Ԫ
			}
			//����ʱ��
			try {
				info.setCreateTime(new Timestamp(SysUtil.getAppServerTime(ctx).getTime()));
			} catch (e) {
				
			}
			//ҵ������
			try
			{
				var bizDate;
				var  sdf = new SimpleDateFormat("yyyyMMdd");
				bizDate=sdf.parse(strBizDate);
				info.setBizDate(bizDate);
				var c = Calendar.getInstance();
				c.setTime(bizDate);
				var year=c.get(Calendar.YEAR);   //���
				var period=c.get(Calendar.MONTH);
				var month =c.get(Calendar.YEAR)*100+c.get(Calendar.MONTH) ; //�ڼ�
				var iday=(c.get(Calendar.YEAR)*100+c.get(Calendar.MONTH)+1)*100+c.get(Calendar.MONDAY);
				info.setYear(year);
				info.setPeriod(period);
				info.setMonth(month);
				info.setDay(iday);
			}catch(ex){
				isSucess=false;
				errMsg=errMsg + "���ڸ�ʽ����ȷ\r\n";
				//throw new BOSException("���ڸ�ʽ����ȷ");
				
			}
			//����״̬
			info.setBaseStatus(billStatus);
			//info.setPurchaseType(purchaseType);
			// ҵ������
			if(bizType!=null){
				info.setBizType(bizType);
			}
			// ��������
			if(billType!=null){
				info.setBillType(billType);
			}
			//�������� 
			if(trantypeInfo!=null){
				info.setTransactionType(trantypeInfo);
				
			}
			//�ɱ�����
			if(costCenter!=null){
				info.setCostCenterOrgUnit(costCenter);
			}
			// ������֯
			var  company=PublicBaseUtil.getCompany(ctx, cu.getNumber());
			//��ҵ����֯
			if(storageInfo!=null){
				info.setStorageOrgUnit(storageInfo);
			}
			
			//�̿�����¼ 
			//������¼ʵ��
			var entry=new OtherIssueBillEntryInfo();
			//��¼�к�
			entry.setSeq(1);
			entry.setSourceBillEntrySeq(0);
		  
			//����
			if (material!=null){
				entry.setMaterial(material);
				entry.setBaseUnit(material.getBaseUnit());
				//��λ
				var  unit=PublicBaseUtil.getMeasureUnitInfoByName(ctx, material.getBaseUnit().getMeasureUnitGroup().getNumber(), strUnit);
				if (unit!=null){
					entry.setUnit(unit);
				}else
				{
					isSucess=false;
					errMsg=errMsg + "��λ����Ϊ��\r\n";
				}
				
			}
			
			entry.setBaseStatus(EntryBaseStatusEnum.ADD);//
			//
			entry.setAssCoefficient(BigDecimal.ZERO);
			//�����֯
			if(storOrgUnit!=null){
				entry.setStorageOrgUnit(storOrgUnit);
				//entry.setReceiveStorageOrgUnit(storOrgUnit);
				
			}
			//������֯
			entry.setCompanyOrgUnit(company);
			//�ֿ�
			if(stock!=null){
				entry.setWarehouse(stock);
				//entry.setOutWarehouse(stock);
			}
			
			entry.setQty(bgQty);
			entry.setBaseQty(bgQty);
			//entry.setPrice(bgPrice);
			//entry.setTaxPrice(bgTaxPrice);
			//entry.setTaxRate(bgTaxRate);
			//entry.setAmount(bgAmount);
			entry.setLot(strLot);
			
			//InvUpdateTypeInfo invInfo = InvUpdateTypeFactory.getLocalInstance(ctx).getInvUpdateTypeInfo(new ObjectUuidPK("8r0AAAAEaOnC73rf"));
			
			entry.setInvUpdateType(invUpType);
			info.getEntries().addObject(entry);
		}
		//entry.getInvUpdateType().getStoreTypePre().getStoreFlag();

		if(isSucess){
			
			//MaterialReqBillInfo info1 = MaterialReqBillFactory.getLocalInstance(ctx).getMaterialReqBillInfo(new ObjectUuidPK("23sE0JysRKmP7TP55oLn7VAKt14="));
			System.out.println("���ɵ���");
			var  pk=OtherIssueBillFactory.getLocalInstance(ctx).save(info);  //����
			if(pk!=null){
				var  tempIno=OtherIssueBillFactory.getLocalInstance(ctx).getOtherIssueBillInfo(pk);
				if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.TEMPORARILYSAVED)) {
					
					OtherIssueBillFactory.getLocalInstance(ctx).submit(tempIno);
				}
				tempIno=OtherIssueBillFactory.getLocalInstance(ctx).getOtherIssueBillInfo(pk);
				if(tempIno.getBaseStatus().equals(BillBaseStatusEnum.SUBMITED)) {
					OtherIssueBillFactory.getLocalInstance(ctx).audit(pk);
				}
				methodCtx.getParam(2).setValue("true") ;
				methodCtx.getParam(4).setValue("�����ɹ�");
				
			}
		}else
		{
			methodCtx.getParam(2).setValue("false") ;
			methodCtx.getParam(4).setValue(errMsg);
		}
	}
	var strMsg="0110"+dwh+"CWCC";
	if(isSucess){
		methodCtx.getParam(2).setValue("true");
		var strData=String.format("%-81s", "A")+"\n" ;
		System.out.println("Ӧ���ģ�"+strMsg+strData);
		methodCtx.getParam(3).setValue(strMsg+strData);
	}else
	{
		methodCtx.getParam(2).setValue("false");
		methodCtx.getParam(4).setValue(dwt+errMsg);
		var strMsg="0110"+dwh+"CWCCB";
		
		var strData=String.format("%-80s", errMsg)+"\n" 
		System.out.println("Ӧ���ģ�"+strMsg+strData);
		methodCtx.getParam(3).setValue(strMsg+strData);
	}
	System.out.println("/******************************  CCCW11 ��Ϣ��¼����   **********************************/");
}