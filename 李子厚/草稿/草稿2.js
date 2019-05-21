var imp = JavaImporter();
imp.importPackage(Packages.com.kingdee.bos.ctrl.kdf.table.util);
imp.importPackage(Packages.com.kingdee.bos.dao.query);
imp.importPackage(Packages.com.kingdee.jdbc.rowset);
imp.importPackage(Packages.com.kingdee.bos.metadata.query.util);
imp.importPackage(Packages.com.kingdee.bos.ctrl.extendcontrols);
imp.importPackage(Packages.com.kingdee.bos.util);
imp.importPackage(Packages.com.kingdee.bos.dao.ormapping);
imp.importPackage(Packages.com.kingdee.eas.basedata.master.account);
imp.importPackage(Packages.com.kingdee.eas.basedata.master.auxacct);
imp.importPackage(Packages.com.kingdee.eas.util.client);
imp.importPackage(Packages.com.kingdee.bos.ctrl.swing);
imp.importPackage(Packages.com.kingdee.bos.metadata.entity);
imp.importPackage(Packages.com.kingdee.eas.basedata.org);
imp.importPackage(Packages.java.lang);
imp.importPackage(Packages.com.kingdee.eas.basedata.master.material);
imp.importPackage(Packages.java.math);
with(imp) {
	var htgjnr = pluginCtx.getKDTextArea("txtkDTextArea").getText();
	if (htgjnr == null || htgjnr == "") {
		pluginCtx.getKDTextArea("txtkDTextArea").setText("请输入合同主要内容，包括但不限于：合同标的信息，合同执行期限等");
	}
	var fkjh = pluginCtx.getKDTextArea("txtkDTextArea1").getText();
	if (fkjh == null || fkjh == "") {
		pluginCtx.getKDTextArea("txtkDTextArea1").setText("请输入合同合同付款条件");
	}
	var prmthtlxval = pluginCtx.getKDBizPromptBox("prmthtlx").getValue();
	if (prmthtlxval == null) {
		var sql1 = " SELECT * FROM CT_CUS_HTLX where fnumber ='01'  ";
		var row2 = null;
		row2 = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(sql1).executeSQL();
		if (row2.next()) {
			var fid = row2.getString("fid");
			var fname_l2 = row2.getString("fname_l2");
			var fnumber = row2.getString("fnumber");
			var info = new com.kingdee.eas.custom.HTLXInfo();
			info.setId(BOSUuid.read(fid));
			info.setName(fname_l2);
			info.setNumber(fnumber);
			pluginCtx.getKDBizPromptBox("prmthtlx").setValue(info);
		}
	}
	prmthtlxval = pluginCtx.getKDBizPromptBox("prmthtlx").getValue();

	var btnReservationQuery = pluginCtx.getKDWorkButton("btnReservationQuery");
	btnReservationQuery.setVisible(false);
	var btnQueryGeneralInventory = pluginCtx.getKDWorkButton("btnQueryGeneralInventory");
	btnQueryGeneralInventory.setVisible(false);
	var btnRevoke = pluginCtx.getKDWorkButton("btnRevoke");
	btnRevoke.setVisible(false);
	var btnInventorySum = pluginCtx.getKDWorkButton("btnInventorySum");
	btnInventorySum.setVisible(false);
	var btnCreateFrom = pluginCtx.getKDWorkButton("btnCreateFrom");
	btnCreateFrom.setVisible(false);
	var table = pluginCtx.getKDTable("kdtEntries");
	table.getHeadRow(0).getCell("totalInvoicedAmount").setValue("累计发票金额");
	//	var httk =pluginCtx.getKDTable("httkentry");
	//	httk.setSize(2000,800);
	var editData = pluginCtx.getDataObject();
	var baseStatus = editData.getBaseStatus().toString();
	//com.kingdee.eas.util.client.MsgBox.showInfo(baseStatus);
	if ("新增".equals(baseStatus)) {
		pluginCtx.getKDCheckBox("chkkDCheckBox1").setSelected(true);
		pluginCtx.getKDCheckBox("chkkDCheckBox").setSelected(false);
		pluginCtx.getKDFormattedTextField("txtkDNumberTextField1").setValue(0);
		pluginCtx.getKDFormattedTextField("txtPrepaid").setValue(0);
		pluginCtx.getKDFormattedTextField("txtkDNumberTextField3").setValue(0);
		pluginCtx.getKDFormattedTextField("txtkDNumberTextField6").setValue(0);
	}
	pluginCtx.getKDFormattedTextField("txtfukuanbili").setHorizontalAlignment(javax.swing.JTextField.RIGHT);
	var termConColTxtArea = new com.kingdee.bos.ctrl.swing.KDTextArea(5, 80);
	termConColTxtArea.setMaxLength(2000);
	termConColTxtArea.setLineWrap(true);
	termConColTxtArea.setWrapStyleWord(true);
	pluginCtx.getKDFormattedTextField("txtPrepayment").setEnabled(false);
	pluginCtx.getKDTextField("txtDescription").setText("必录");
	//	httk.getColumn("tiaokuanneirong2").setEditor(new com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor(termConColTxtArea));
	//	httk.getColumn("tiaokuanneirong2").getStyleAttributes().setWrapText(true);
	//httk.getColumn("tiaokuanneirong2").getStyleAttributes().setVerticalAlign(org.apache.poi.ss.usermodel.VerticalAlignment.TOP);
	var rowcount = table.getRowCount();
	var xiangmubianma = table.getColumn("xiangmubianma").getEditor().getComponent();
	var company = pluginCtx.getUIContext().get("sysContext").getCurrentFIUnit();
	var CGXZ = pluginCtx.getKDTextField("txthtxz").getText();
	if (CGXZ == "") {
		pluginCtx.getKDTextField("txthtxz").setText("采购类合同");
	}
	CGXZ = pluginCtx.getKDTextField("txthtxz").getText();
	var selected = pluginCtx.getKDCheckBox("chkkDCheckBox").getSelected();
	//com.kingdee.eas.util.client.MsgBox.showInfo(selected);
	if (selected == 32) {
		table.getColumn("preReceived").getStyleAttributes().setLocked(false);
		table.getColumn("totalInvoicedAmount").getStyleAttributes().setLocked(false);
		table.getColumn("totalPaidAmount").getStyleAttributes().setLocked(false);
	} else {
		table.getColumn("preReceived").getStyleAttributes().setLocked(true);
		table.getColumn("totalInvoicedAmount").getStyleAttributes().setLocked(true);
		table.getColumn("totalPaidAmount").getStyleAttributes().setLocked(true);
	}
	var htlxname = prmthtlxval.getName().toString();
	if (CGXZ == "采购类合同" && htlxname != "工程设备采购类合同") {
		table.getColumn("wareHouse").setRequired(true);
	} else {
		table.getColumn("wareHouse").setRequired(false);
	}
	if (CGXZ == "采购类合同" || CGXZ == "工程类合同") {
		table.getColumn("xiangmubianma").setRequired(true);
	} else {
		table.getColumn("xiangmubianma").setRequired(false);
	}
	if (CGXZ == "采购类合同" && CGXZ != "工程设备采购类合同") {
		for (var i = 0; i < rowcount; i++) {
			table.getRow(i).getCell("materialNum").setValue(null);
			table.getRow(i).getCell("materialName").setValue("");
			table.getRow(i).getCell("unit").setValue(null);
		}
		var prmtPurchaseOrgUnit = pluginCtx.getKDBizPromptBox("prmtPurchaseOrgUnit").getValue();
		if (prmtPurchaseOrgUnit != null && prmtPurchaseOrgUnit.getId() != null) {
			var orgid = prmtPurchaseOrgUnit.getId().toString();
			var sql = " SELECT * FROM T_ORG_BaseUnit where fid  ='" + orgid + "'  ";

			//com.kingdee.eas.util.client.MsgBox.showInfo(sql);
			var row = null;
			row = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
			if (row.next()) {
				var cfbkid = row.getString("cfbkid");
				var sqlbk = " select * from CT_CUS_Bk where fid = '" + cfbkid + "'";

				//com.kingdee.eas.util.client.MsgBox.showInfo(sql);
				var rowbk = null;
				rowbk = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(sqlbk).executeSQL();
				if (rowbk.next()) {
					var fnamebk = rowbk.getString("fname_l2");
					if (fnamebk == "新能源板块") {
						var filterInfo = new com.kingdee.bos.metadata.entity.FilterInfo();
						var entityViewInfo = new com.kingdee.bos.metadata.entity.EntityViewInfo();
						var filterItemInfo = new com.kingdee.bos.metadata.entity.FilterItemInfo("name", "新能源采购", com.kingdee.bos.metadata.query.util.CompareType.EQUALS);
						filterInfo.getFilterItems().add(filterItemInfo);
						entityViewInfo.setFilter(filterInfo);

						var accountcol = com.kingdee.eas.basedata.master.material.MaterialFactory.getRemoteInstance().getMaterialCollection(entityViewInfo);
						var cur = new com.kingdee.eas.basedata.master.material.MaterialInfo();
						if (accountcol.size() > 0) {
							cur = accountcol.get(0);
							var FBaseUnit = cur.getBaseUnit();
							if (FBaseUnit != null) {
								var cur2 = new com.kingdee.eas.basedata.assistant.MeasureUnitInfo();
								var oql2 = "select where id='" + FBaseUnit.getId() + "'";
								var accountcol2 = com.kingdee.eas.basedata.assistant.MeasureUnitFactory.getRemoteInstance().getMeasureUnitCollection(oql2);
								if (accountcol2.size() > 0) {
									cur2 = accountcol2.get(0);
									for (var i = 0; i < rowcount; i++) {
										table.getRow(i).getCell("materialNum").setValue(cur);
										table.getRow(i).getCell("materialName").setValue(cur.getName());
										table.getRow(i).getCell("unit").setValue(cur2);
										table.getRow(i).getCell("baseUnit").setValue(cur2); //jerry 给基本计量单位赋值
									}
								}
							}
						}
						var curck = new com.kingdee.eas.basedata.scm.im.inv.WarehouseInfo();
						var oqlck = "select where number='0801'";
						var accountcolck = com.kingdee.eas.basedata.scm.im.inv.WarehouseFactory.getRemoteInstance().getWarehouseCollection(oqlck);
						if (accountcolck.size() > 0) {
							curck = accountcolck.get(0);
							for (var i = 0; i < rowcount; i++) {
								table.getRow(i).getCell("wareHouse").setValue(curck);
							}
						}
					}
				}
			}
		}
	}
	var fukuan = 0;
	var htongzongjine = pluginCtx.getKDFormattedTextField("txtTotalTaxAmount").getValue();
	if (htongzongjine != null) {
		htongzongjine = java.lang.Float.parseFloat(htongzongjine);
	}
	for (var i = 0; i < rowcount; i++) {
		var rownow = table.getRow(i);
		var totalPaidAmount = rownow.getCell("totalPaidAmount").getValue();
		if (totalPaidAmount != null) {
			totalPaidAmount = java.lang.Float.parseFloat(totalPaidAmount);
			fukuan += totalPaidAmount;
		}
	}
	var txtkDNumberTextField4 = pluginCtx.getKDFormattedTextField("txtkDNumberTextField4").getValue();
	var newleijifp = 0;
	for (var i = 0; i < rowcount; i++) {
		var rownow = table.getRow(i);
		var totalInvoicedAmount = rownow.getCell("totalInvoicedAmount").getValue();
		if (totalInvoicedAmount != null) {
			totalInvoicedAmount = java.lang.Float.parseFloat(totalInvoicedAmount);
			newleijifp += totalInvoicedAmount;
		}
	}
	/* if(txtkDNumberTextField4!=null){
		txtkDNumberTextField4=java.lang.Float.parseFloat(txtkDNumberTextField4);
		newleijifp+=txtkDNumberTextField4;
	} */
	pluginCtx.getKDFormattedTextField("txtkDNumberTextField3").setValue(newleijifp);
	if (table.getRow(0) != null && table.getRow(0).getCell("id") != null && table.getRow(0).getCell("id").getValue() != null) {
		var fenluid = table.getRow(0).getCell("id").getValue();
		var sql2 = "select * from T_SM_PurOrderEntry WHERE fid = '" + fenluid + "'";
		var sqlExecutor2 = new com.kingdee.bos.dao.query.SQLExecutor(sql2);
		var rowSet2 = sqlExecutor2.executeSQL();
		if (rowSet2.next()) {
			fparentid = rowSet2.getString("fparentid");
			if (fparentid != null) {
				var sqlfp = "UPDATE T_SM_PurOrder SET cfleijifapiao = " + newleijifp + " WHERE fid = '" + fparentid + "'";
				var pkfp = new com.kingdee.bos.metadata.MetaDataPK("com.kingdee.eas.fi.cas.app.PaymentBill");
				var namefp = "String update(String arg1)";
				var paramsfp = [sqlfp];
				var result = com.kingdee.bos.framework.BOClientTool.callCmethod(pkfp, namefp, paramsfp);
			}
		}
	}
	var txtNumber = pluginCtx.getKDTextField("txtNumber").getText();
	var sqlljfk = " SELECT * FROM T_SM_PurOrder where fnumber  ='" + txtNumber + "'  ";
	var zongfkjine = 0;
	//com.kingdee.eas.util.client.MsgBox.showInfo(sql);
	var rowljfk = null;
	rowljfk = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(sqlljfk).executeSQL();
	if (rowljfk.next()) {
		var cfleijifkjine = rowljfk.getFloat("cfleijifkjine");
		if (cfleijifkjine == null) {
			cfleijifkjine = 0;
		}
		zongfkjine += cfleijifkjine;
	}
	var sqlfkd = " SELECT * FROM T_CAS_PaymentBill where cfhtbianhao  ='" + txtNumber + "'  and  FBILLSTATUS =15";
	var rowfkd = null;
	rowfkd = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(sqlfkd).executeSQL();
	while (rowfkd.next()) {
		var FACTUALPAYAMOUNT = rowfkd.getFloat("FACTUALPAYAMOUNT");
		if (FACTUALPAYAMOUNT == null) {
			FACTUALPAYAMOUNT = 0;
		}
		zongfkjine += FACTUALPAYAMOUNT;
	}
	if (zongfkjine == null) {
		zongfkjine = 0;
	}
	var sqlleijifkfk = "UPDATE T_SM_PurOrder SET cfleijifkjine = " + zongfkjine + " WHERE fnumber = '" + txtNumber + "'";
	var pkljfk = new com.kingdee.bos.metadata.MetaDataPK("com.kingdee.eas.fi.cas.app.PaymentBill");
	var nameljfk = "String update(String arg1)";
	var paramsljfk = [sqlleijifkfk];
	var result = com.kingdee.bos.framework.BOClientTool.callCmethod(pkljfk, nameljfk, paramsljfk);

	var weidaopiao = htongzongjine - newleijifp;
	pluginCtx.getKDFormattedTextField("txtkDNumberTextField5").setValue(weidaopiao);
	if (table.getRow(0) != null && table.getRow(0).getCell("id") != null && table.getRow(0).getCell("id").getValue() != null) {
		var fenluid = table.getRow(0).getCell("id").getValue();
		var sql2 = "select * from T_SM_PurOrderEntry WHERE fid = '" + fenluid + "'";
		var sqlExecutor2 = new com.kingdee.bos.dao.query.SQLExecutor(sql2);
		var rowSet2 = sqlExecutor2.executeSQL();
		if (rowSet2.next()) {
			fparentid = rowSet2.getString("fparentid");
			if (fparentid != null) {
				var sqlfp = "UPDATE T_SM_PurOrder SET cfweidaopiao = " + weidaopiao + " WHERE fid = '" + fparentid + "'";
				var pkfp = new com.kingdee.bos.metadata.MetaDataPK("com.kingdee.eas.fi.cas.app.PaymentBill");
				var namefp = "String update(String arg1)";
				var paramsfp = [sqlfp];
				var result = com.kingdee.bos.framework.BOClientTool.callCmethod(pkfp, namefp, paramsfp);
			}
		}
	}
	var txtTotalTaxAmount = pluginCtx.getKDFormattedTextField("txtTotalTaxAmount").getValue();

	var txtkDNumberTextField6 = pluginCtx.getKDFormattedTextField("txtkDNumberTextField6").getValue();
	var ljweifukuan = 0;
	if (txtTotalTaxAmount != null) {
		txtTotalTaxAmount = java.lang.Float.parseFloat(txtTotalTaxAmount);
		if (txtkDNumberTextField6 != null) {
			txtkDNumberTextField6 = java.lang.Float.parseFloat(txtkDNumberTextField6);
			ljweifukuan = txtTotalTaxAmount - txtkDNumberTextField6;
		} else {
			ljweifukuan = txtTotalTaxAmount;
		}
	}
	if (table.getRow(0) != null && table.getRow(0).getCell("id") != null && table.getRow(0).getCell("id").getValue() != null) {
		var fenluid = table.getRow(0).getCell("id").getValue();
		var sql2 = "select * from T_SM_PurOrderEntry WHERE fid = '" + fenluid + "'";
		var sqlExecutor2 = new com.kingdee.bos.dao.query.SQLExecutor(sql2);
		var rowSet2 = sqlExecutor2.executeSQL();
		if (rowSet2.next()) {
			fparentid = rowSet2.getString("fparentid");
			pluginCtx.getKDFormattedTextField("txtkDNumberTextField1").setValue(ljweifukuan);
			if (fparentid != null) {
				var sqlfp = "UPDATE T_SM_PurOrder SET cfweifukuan = " + ljweifukuan + " WHERE fid = '" + fparentid + "'";
				var pkfp = new com.kingdee.bos.metadata.MetaDataPK("com.kingdee.eas.fi.cas.app.PaymentBill");
				var namefp = "String update(String arg1)";
				var paramsfp = [sqlfp];
				var result = com.kingdee.bos.framework.BOClientTool.callCmethod(pkfp, namefp, paramsfp);
			}
		}
	}
	var fparentid = "";
	if (fukuan != 0 && htongzongjine != 0 && table.getRow(0) != null && table.getRow(0).getCell("id") != null && table.getRow(0).getCell("id").getValue() != null) {
		var fenluid = table.getRow(0).getCell("id").getValue();
		var fukuanbili = fukuan * 100 / htongzongjine;
		pluginCtx.getKDFormattedTextField("txtfukuanbili").setValue(fukuanbili);
		var sql2 = "select * from T_SM_PurOrderEntry WHERE fid = '" + fenluid + "'";
		var sqlExecutor2 = new com.kingdee.bos.dao.query.SQLExecutor(sql2);
		var rowSet2 = sqlExecutor2.executeSQL();
		if (rowSet2.next()) {
			fparentid = rowSet2.getString("fparentid");
			if (fparentid != null) {
				var sql2 = "UPDATE T_SM_PurOrder SET cffukuanbili = " + fukuanbili + " WHERE fid = '" + fparentid + "'";
				var pk = new com.kingdee.bos.metadata.MetaDataPK("com.kingdee.eas.fi.cas.app.PaymentBill");
				var name = "String update(String arg1)";
				var params = [sql2];
				var result = com.kingdee.bos.framework.BOClientTool.callCmethod(pk, name, params);
			}
		}

		for (var i = 0; i < rowcount; i++) {
			var rownow = table.getRow(i);
			var qty = rownow.getCell("qty").getValue();
			var totalInvoicedQty = rownow.getCell("totalInvoicedQty").getValue();
			var bigqty = new java.math.BigDecimal(qty);
			var bigtotalInvoicedQty = new java.math.BigDecimal(totalInvoicedQty);
			var weidaopiao = new java.math.BigDecimal(0);

			weidaopiao = bigqty.subtract(bigtotalInvoicedQty);
			rownow.getCell("weidaopiao").setValue(weidaopiao);
			var id2 = rownow.getCell("id");
			if (id2 != null && id2.getValue() != null) {
				var idvalue = id2.getValue();
				var sql2 = "UPDATE T_SM_PurOrderEntry SET cfweidaopiao = " + weidaopiao.toString() + " WHERE fid = '" + idvalue + "'";
				var pk = new com.kingdee.bos.metadata.MetaDataPK("com.kingdee.eas.fi.cas.app.PaymentBill");
				var name = "String update(String arg1)";
				var params = [sql2];
				var result = com.kingdee.bos.framework.BOClientTool.callCmethod(pk, name, params);
			}
			var statestr = rownow.getCell("baseStatus").getValue().toString();
			//com.kingdee.eas.util.client.MsgBox.showInfo(rownow.getCell("baseStatus").getValue().toString());
			var totalPaidAmount = rownow.getCell("totalPaidAmount").getValue();
			var taxAmount = rownow.getCell("taxAmount").getValue();

			if (totalPaidAmount == taxAmount) {
				var id = rownow.getCell("id");
				rownow.getCell("baseStatus").setValue(com.kingdee.eas.scm.common.BillBaseStatusEnum.CLOSED);
				if (id != null && id.getValue() != null) {
					var idvalue = id.getValue();
					var sql2 = "UPDATE T_SM_PurOrderEntry SET FBaseStatus = 7 WHERE fid = '" + idvalue + "'";
					var pk = new com.kingdee.bos.metadata.MetaDataPK("com.kingdee.eas.fi.cas.app.PaymentBill");
					var name = "String update(String arg1)";
					var params = [sql2];
					var result = com.kingdee.bos.framework.BOClientTool.callCmethod(pk, name, params);
				}
			} else {
				if (statestr == "关闭") {
					var id = rownow.getCell("id");
					rownow.getCell("baseStatus").setValue(com.kingdee.eas.scm.common.BillBaseStatusEnum.AUDITED);
					if (id != null && id.getValue() != null) {
						var idvalue = id.getValue();
						var sql2 = "UPDATE T_SM_PurOrderEntry SET FBaseStatus = 4 WHERE fid = '" + idvalue + "'";
						var pk = new com.kingdee.bos.metadata.MetaDataPK("com.kingdee.eas.fi.cas.app.PaymentBill");
						var name = "String update(String arg1)";
						var params = [sql2];
						var result = com.kingdee.bos.framework.BOClientTool.callCmethod(pk, name, params);
					}
				}
			}
		}

		var sql2 = "select * from T_SM_PurOrderEntry WHERE FParentID = '" + fparentid + "'";
		var sqlExecutor2 = new com.kingdee.bos.dao.query.SQLExecutor(sql2);
		var rowSet2 = sqlExecutor2.executeSQL();
		var isguanbi = true;
		while (rowSet2.next()) {
			var fbasestatus = rowSet2.getInt("FBaseStatus");
			if (fbasestatus == 4) {
				isguanbi = false;
			}
		}

		var statusstr = pluginCtx.getKDComboBox("comboStatus").getSelectedItem().toString();
		if (isguanbi) {
			pluginCtx.getKDComboBox("comboStatus").setSelectedItem(com.kingdee.eas.scm.common.BillBaseStatusEnum.CLOSED);
			sql2 = "UPDATE T_SM_PurOrder SET fbasestatus = 7 WHERE fid = '" + fparentid + "'";
			var pk = new com.kingdee.bos.metadata.MetaDataPK("com.kingdee.eas.fi.cas.app.PaymentBill");
			var name = "String update(String arg1)";
			var params = [sql2];
			var result = com.kingdee.bos.framework.BOClientTool.callCmethod(pk, name, params);
		} else {
			if (statusstr == "关闭") {
				pluginCtx.getKDComboBox("comboStatus").setSelectedItem(com.kingdee.eas.scm.common.BillBaseStatusEnum.AUDITED);
				sql2 = "UPDATE T_SM_PurOrder SET fbasestatus = 4 WHERE fid = '" + fparentid + "'";
				var pk = new com.kingdee.bos.metadata.MetaDataPK("com.kingdee.eas.fi.cas.app.PaymentBill");
				var name = "String update(String arg1)";
				var params = [sql2];
				var result = com.kingdee.bos.framework.BOClientTool.callCmethod(pk, name, params);
			}
		}

	}

	var companyId = company.getId();
	if (xiangmubianma instanceof com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox) {
		var filterInfo2 = new FilterInfo();
		var entityViewInfo2 = new EntityViewInfo();

		filterInfo2.getFilterItems().add(new com.kingdee.bos.metadata.entity.FilterItemInfo("company.id", company.getId(), com.kingdee.bos.metadata.query.util.CompareType.EQUALS));
		entityViewInfo2.setFilter(filterInfo2);
		xiangmubianma.setEntityViewInfo(entityViewInfo2);
	}

	var shuilv = table.getColumn("shuilv").getEditor().getComponent();
	if (shuilv instanceof com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox) {
		var filterInfo = new FilterInfo();
		var entityViewInfo = new EntityViewInfo();
		filterInfo.getFilterItems().add(new com.kingdee.bos.metadata.entity.FilterItemInfo("creatorCompany.id", company.getId(), com.kingdee.bos.metadata.query.util.CompareType.EQUALS));
		filterInfo.getFilterItems().add(new com.kingdee.bos.metadata.entity.FilterItemInfo("creatorCompany.id", "00000000-0000-0000-0000-000000000000CCE7AED4", com.kingdee.bos.metadata.query.util.CompareType.EQUALS));
		filterInfo.getFilterItems().add(new com.kingdee.bos.metadata.entity.FilterItemInfo("group.number", "404", com.kingdee.bos.metadata.query.util.CompareType.EQUALS));
		filterInfo.setMaskString(" (#0 or #1) and #2 ");

		entityViewInfo.setFilter(filterInfo);
		shuilv.setEntityViewInfo(entityViewInfo);
	}



	var hetongleixing = pluginCtx.getKDBizPromptBox("prmthtlx").getValue();

	if (hetongleixing != null && hetongleixing.getId() != null) {
		var htlxid = hetongleixing.getId().toString();
		var sql = "select * from CT_CUS_HTLX WHERE fid = '" + htlxid + "'";

		var sqlExecutor = new com.kingdee.bos.dao.query.SQLExecutor(sql);
		var rowSet = sqlExecutor.executeSQL();
		if (rowSet.next()) {

			var Ftreeid = rowSet.getString("Ftreeid");

			var sql2 = "select * from T_CUS_HTLXTREE WHERE fid = '" + Ftreeid + "'";
			var sqlExecutor2 = new com.kingdee.bos.dao.query.SQLExecutor(sql2);
			var rowSet2 = sqlExecutor2.executeSQL();
			if (rowSet2.next()) {
				var fnumber = rowSet2.getString("fnumber");
				if (fnumber != "01" || htlxname == "工程设备采购类合同") {
					if (fnumber == "02") {
						pluginCtx.getKDLabelContainer("contkDNumberTextField2").setVisible(true);
					} else {
						pluginCtx.getKDLabelContainer("contkDNumberTextField2").setVisible(false);
					}
					table.getColumn("materialNum").getStyleAttributes().setHided(true);
					table.getColumn("materialName").getStyleAttributes().setHided(true);
					table.getColumn("materialModel").getStyleAttributes().setHided(true);
					table.getColumn("wareHouse").getStyleAttributes().setHided(true);
					table.getColumn("unit").getStyleAttributes().setHided(true);
					table.getColumn("taxPrice").getStyleAttributes().setHided(true);
					table.getColumn("price").getStyleAttributes().setHided(true);
					table.getColumn("taxAmount").getStyleAttributes().setHided(true);
					table.getColumn("amount").getStyleAttributes().setHided(true);
					table.getHeadRow(0).getCell("qty").setValue("价税合计");
				} else if (fnumber == "01") {
					pluginCtx.getKDLabelContainer("contkDNumberTextField2").setVisible(false);
					table.getColumn("materialNum").getStyleAttributes().setHided(false);
					table.getColumn("materialName").getStyleAttributes().setHided(false);
					table.getColumn("materialModel").getStyleAttributes().setHided(false);
					table.getColumn("wareHouse").getStyleAttributes().setHided(false);
					table.getColumn("unit").getStyleAttributes().setHided(false);
					table.getColumn("taxPrice").getStyleAttributes().setHided(false);
					table.getColumn("price").getStyleAttributes().setHided(false);
					table.getColumn("taxAmount").getStyleAttributes().setHided(false);
					table.getColumn("amount").getStyleAttributes().setHided(false);
					table.getHeadRow(0).getCell("qty").setValue("数量");
					var prmtPurchaseOrgUnit = pluginCtx.getKDBizPromptBox("prmtPurchaseOrgUnit").getValue();
					if (prmtPurchaseOrgUnit != null && prmtPurchaseOrgUnit.getId() != null) {
						var orgid = prmtPurchaseOrgUnit.getId().toString();
						var sqlorg = " SELECT * FROM T_ORG_BaseUnit where fid  ='" + orgid + "'  ";

						//com.kingdee.eas.util.client.MsgBox.showInfo(sql);
						var row = null;
						row = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(sqlorg).executeSQL();
						if (row.next()) {
							var cfbkid = row.getString("cfbkid");
							var sqlbk = " select * from CT_CUS_Bk where fid = '" + cfbkid + "'";

							//com.kingdee.eas.util.client.MsgBox.showInfo(sql);
							var rowbk = null;
							rowbk = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(sqlbk).executeSQL();
							if (rowbk.next()) {
								var fnamebk = rowbk.getString("fname_l2");
								if (fnamebk == "新能源板块") {
									table.getColumn("materialNum").getStyleAttributes().setHided(true);
									table.getColumn("materialName").getStyleAttributes().setHided(true);
									table.getColumn("materialModel").getStyleAttributes().setHided(true);
									table.getColumn("wareHouse").getStyleAttributes().setHided(true);
									table.getColumn("unit").getStyleAttributes().setHided(true);
								}
							}
						}
					}
				}
				var fname_l2 = rowSet2.getString("fname_l2");
				//com.kingdee.eas.util.client.MsgBox.showInfo(fname_l2);
				pluginCtx.getKDTextField("txthtxz").setText(fname_l2);
				if (fname_l2 == "采购类合同" && htlxname != "工程设备采购类合同") {
					table.getColumn("wareHouse").setRequired(true);
				} else {
					table.getColumn("wareHouse").setRequired(false);
				}
				if (fname_l2 == "采购类合同" || fname_l2 == "工程类合同") {
					table.getColumn("xiangmubianma").setRequired(true);
				} else {
					table.getColumn("xiangmubianma").setRequired(false);
				}
			}
		}
	}

	var prmthtlx = pluginCtx.getKDBizPromptBox("prmthtlx");

	if (prmthtlx instanceof com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox) {
		var filterInfo2 = new FilterInfo();
		var entityViewInfo2 = new EntityViewInfo();

		filterInfo2.getFilterItems().add(new com.kingdee.bos.metadata.entity.FilterItemInfo("number", "%trq%", com.kingdee.bos.metadata.query.util.CompareType.NOTLIKE));
		entityViewInfo2.setFilter(filterInfo2);
		prmthtlx.setEntityViewInfo(entityViewInfo2);
	}
	if (CGXZ != "采购类合同" || htlxname == "工程设备采购类合同") {
		for (var n = 0; n < rowcount; n++) {
			var rownow = table.getRow(n);
			var adminOrgUnit = table.getRow(n).getCell("adminOrgUnit");
			adminOrgUnit.setValue(company);
		}
	}
	//	pluginCtx.getKDTable("httkentry").addKDTEditListener(function(event,methodName){
	//		if(methodName == "editStopped"){
	//			var httk =pluginCtx.getKDTable("httkentry");
	//			var i = 0;
	//			for (var m = httk.getRowCount(); i < m; ++i)
	//				com.kingdee.bos.ctrl.kdf.table.KDTableHelper.autoFitRowHeight(httk, i);
	//			}
	//		if(methodName == "editStopping"){
	//			}
	//		if(methodName == "equals"){ return this == event;}
	//		});
	var qyrq = pluginCtx.getKDDatePicker("pkkDDatePicker").getValue();
	/* if(qyrq==null){
		pluginCtx.getKDDatePicker("pkkDDatePicker").setValue(new  java.util.Date());
	} */
	var userId = pluginCtx.getUIContext().get("sysContext").getCurrentUser().get("id").toString();
	var sql = " select * from T_PM_User where fid='" + userId + "' ";
	var row = null;
	row = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
	if (row.next()) {
		var personid = row.getString("FPersonId");
		var sql1 = " SELECT * FROM T_BD_Person where fid ='" + personid + "'  ";
		var row2 = null;
		row2 = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(sql1).executeSQL();
		if (row2.next()) {
			var fname_l2 = row2.getString("fname_l2");
			var fnumber = row2.getString("fnumber");
			var perinfo = new com.kingdee.eas.basedata.person.PersonInfo();
			perinfo.setId(BOSUuid.read(personid));
			perinfo.setName(fname_l2);
			perinfo.setNumber(fnumber);
			var fcell = row2.getString("fcell");
			pluginCtx.getKDTextField("txtkDTextField1").setText(fcell);
			pluginCtx.getKDBizPromptBox("prmtPurchasePerson").setValue(perinfo);
		}
		var personsql = "select * from T_ORG_PositionMember where FPersonID = '" + personid + "'  order by FISPRIMARY asc";
		var personrows = null;
		var admininfo = new com.kingdee.eas.basedata.org.AdminOrgUnitInfo();
		var CU = pluginCtx.getUIContext().get("sysContext").getCurrentCtrlUnit();
		var cuid = CU.getId().toString();
		personrows = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(personsql).executeSQL();
		while (personrows != null && personrows.next()) {
			var FPositionID = personrows.getString("FPositionID");
			var adminsql = "select * from T_ORG_Position where fid = '" + FPositionID + "'";
			var adminrows = null;
			adminrows = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(adminsql).executeSQL();
			if (adminrows != null && adminrows.next()) {
				var FControlUnitID = adminrows.getString("FControlUnitID");
				var FAdminOrgUnitID = adminrows.getString("FAdminOrgUnitID");
				if (cuid == FControlUnitID) {
					var bmsql = "select * from T_ORG_Admin where fid = '" + FAdminOrgUnitID + "'";
					var bmrows = null;
					bmrows = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(bmsql).executeSQL();
					while (bmrows != null && bmrows.next()) {
						var fname_l2 = bmrows.getString("fname_l2");
						var bmid = bmrows.getString("fid");
						var bmfnumber = bmrows.getString("fnumber");
						admininfo.setId(BOSUuid.read(bmid));
						admininfo.setName(fname_l2);
						admininfo.setNumber(bmfnumber);
					}
				}
			}
		}
		pluginCtx.getKDBizPromptBox("prmtAdminOrgUnit").setValue(admininfo);
	}
	var selected = pluginCtx.getKDCheckBox("chkkDCheckBox1").getSelected();

	//com.kingdee.eas.util.client.MsgBox.showInfo(selected);
	if (selected == 32) {
		table.getColumn("xiangmubianma").getStyleAttributes().setHided(false);
		table.getColumn("xiangmumingcheng").getStyleAttributes().setHided(false);
	} else {
		table.getColumn("xiangmubianma").getStyleAttributes().setHided(true);
		table.getColumn("xiangmumingcheng").getStyleAttributes().setHided(true);
	}
	pluginCtx.getKDBizPromptBox("prmtPurchasePerson").addDataChangeListener(function (e, methodName) {
		if (methodName == "dataChanged") {
			var prmtPurchasePerson = pluginCtx.getKDBizPromptBox("prmtPurchasePerson").getValue();
			if (prmtPurchasePerson != null) {

				var purpersonid = prmtPurchasePerson.getId().toString();
				var sql = " SELECT * FROM T_BD_Person where fid ='" + purpersonid + "'  ";
				var qyrq = pluginCtx.getKDDatePicker("pkkDDatePicker").getValue();

				//com.kingdee.eas.util.client.MsgBox.showInfo(sql);
				var row = null;
				row = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
				if (row.next()) {
					var fcell = row.getString("fcell");

					pluginCtx.getKDTextField("txtkDTextField1").setText(fcell);
				}
				var personsql = "select * from T_ORG_PositionMember where FPersonID = '" + purpersonid + "'  order by FISPRIMARY asc";
				var personrows = null;
				var admininfo = new com.kingdee.eas.basedata.org.AdminOrgUnitInfo();
				var CU = pluginCtx.getUIContext().get("sysContext").getCurrentCtrlUnit();
				var cuid = CU.getId().toString();
				personrows = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(personsql).executeSQL();
				while (personrows != null && personrows.next()) {
					var FPositionID = personrows.getString("FPositionID");
					var adminsql = "select * from T_ORG_Position where fid = '" + FPositionID + "'";
					var adminrows = null;
					adminrows = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(adminsql).executeSQL();
					if (adminrows != null && adminrows.next()) {
						var FControlUnitID = adminrows.getString("FControlUnitID");
						var FAdminOrgUnitID = adminrows.getString("FAdminOrgUnitID");
						if (cuid == FControlUnitID) {
							var bmsql = "select * from T_ORG_Admin where fid = '" + FAdminOrgUnitID + "'";
							var bmrows = null;
							bmrows = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(bmsql).executeSQL();
							while (bmrows != null && bmrows.next()) {
								var fname_l2 = bmrows.getString("fname_l2");
								var bmid = bmrows.getString("fid");
								var bmfnumber = bmrows.getString("fnumber");
								admininfo.setId(BOSUuid.read(bmid));
								admininfo.setName(fname_l2);
								admininfo.setNumber(bmfnumber);
							}
						}
					}
				}
				pluginCtx.getKDBizPromptBox("prmtAdminOrgUnit").setValue(admininfo);
			}
		}
		if (methodName == "equals") {
			return this == e;
		}
	});
	pluginCtx.getKDFormattedTextField("txtTotalTaxAmount").setPrecision(2);
	pluginCtx.getKDFormattedTextField("txtkDNumberTextField").setPrecision(2);
	pluginCtx.getKDFormattedTextField("txtPrepayment").setPrecision(2);
	pluginCtx.getKDFormattedTextField("txtkDNumberTextField6").setPrecision(2);
	pluginCtx.getKDFormattedTextField("txtPrepaid").setPrecision(2);
	pluginCtx.getKDFormattedTextField("txtzbjbili").setHorizontalAlignment(javax.swing.JTextField.RIGHT);
	pluginCtx.getKDFormattedTextField("txtzbjedu").setHorizontalAlignment(javax.swing.JTextField.RIGHT);
	pluginCtx.getKDFormattedTextField("txtkDNumberTextField5").setPrecision(2);
	pluginCtx.getKDFormattedTextField("txtkDNumberTextField5").setHorizontalAlignment(javax.swing.JTextField.RIGHT);
	pluginCtx.getKDFormattedTextField("txtkDNumberTextField").setPrecision(2);
	pluginCtx.getKDFormattedTextField("txtkDNumberTextField").setHorizontalAlignment(javax.swing.JTextField.RIGHT);
	pluginCtx.getKDFormattedTextField("txtkDNumberTextField1").setPrecision(2);
	pluginCtx.getKDFormattedTextField("txtkDNumberTextField1").setHorizontalAlignment(javax.swing.JTextField.RIGHT);
	pluginCtx.getKDFormattedTextField("txtkDNumberTextField3").setHorizontalAlignment(javax.swing.JTextField.RIGHT);
	pluginCtx.getKDFormattedTextField("txtkDNumberTextField3").setPrecision(2);
	pluginCtx.getKDFormattedTextField("txtkDNumberTextField2").setHorizontalAlignment(javax.swing.JTextField.RIGHT);
	pluginCtx.getKDFormattedTextField("txtkDNumberTextField2").setPrecision(2);
	pluginCtx.getKDFormattedTextField("txtExchangeRate").addDataChangeListener(function (e, methodName) {
		if (methodName == "dataChanged") {
			var exchangeRate = pluginCtx.getKDFormattedTextField("txtExchangeRate").getValue();

			//com.kingdee.eas.util.client.MsgBox.showInfo(exchangeRate);
			var txtTotalTaxAmount = pluginCtx.getKDFormattedTextField("txtTotalTaxAmount").getValue();
			//com.kingdee.eas.util.client.MsgBox.showInfo(txtTotalTaxAmount);
			if (exchangeRate != null && txtTotalTaxAmount != null) {
				exchangeRate = java.lang.Float.parseFloat(exchangeRate);
				txtTotalTaxAmount = java.lang.Float.parseFloat(txtTotalTaxAmount);
				var table = pluginCtx.getKDTable("kdtEntries");
				var rowcount = table.getRowCount();
				var zonge = 0;
				for (var line = 0; line < rowcount; line++) {
					var rownew = table.getRow(line);

					var quantity = rownew.getCell("qty").getValue();
					var taxprice = rownew.getCell("taxPrice").getValue();
					if (quantity != null && taxprice != null && quantity != "" && taxprice != "") {
						var floqua = java.lang.Float.parseFloat(quantity);
						var floprice = java.lang.Float.parseFloat(taxprice);
						zonge = zonge + floqua * floprice;
					}
				}
				var benweibi = exchangeRate * zonge;
				pluginCtx.getKDFormattedTextField("txtkDNumberTextField").setValue(benweibi);
			}
		}
		if (methodName == "equals") {
			return this == e;
		}
	});
	pluginCtx.getKDFormattedTextField("txtTotalTaxAmount").addDataChangeListener(function (e, methodName) {
		if (methodName == "dataChanged") {
			var htongzongjine = pluginCtx.getKDFormattedTextField("txtTotalTaxAmount").getValue();

			if (htongzongjine != null) {
				htongzongjine = java.lang.Float.parseFloat(htongzongjine);
				var zhibaojinBL = pluginCtx.getKDFormattedTextField("txtzbjbili").getValue();
				if (zhibaojinBL < 0 || zhibaojinBL > 100) {
					com.kingdee.eas.util.client.MsgBox.showInfo("质保金比例要在0-100之间");
					return;
				}
				if (zhibaojinBL != null) {
					var zhibaojinedu = zhibaojinBL * htongzongjine / 100;
					pluginCtx.getKDFormattedTextField("txtzbjedu").setValue(zhibaojinedu);
				} else {
					pluginCtx.getKDFormattedTextField("txtzbjedu").setValue(0);
				}
			} else {
				pluginCtx.getKDFormattedTextField("txtzbjedu").setValue(0);
			}
			//com.kingdee.eas.util.client.MsgBox.showInfo(htongzongjine==null);
		}
		if (methodName == "equals") {
			return this == e;
		}
	});

	pluginCtx.getKDFormattedTextField("txtzbjbili").addDataChangeListener(function (e, methodName) {
		if (methodName == "dataChanged") {
			var htongzongjine = pluginCtx.getKDFormattedTextField("txtTotalTaxAmount").getValue();
			if (htongzongjine != null) {
				var zhibaojinBL = pluginCtx.getKDFormattedTextField("txtzbjbili").getValue();
				if (zhibaojinBL < 0 || zhibaojinBL > 100) {
					com.kingdee.eas.util.client.MsgBox.showInfo("质保金比例要在0-100之间");
					pluginCtx.getKDFormattedTextField("txtzbjbili").setValue(0);
					pluginCtx.getKDFormattedTextField("txtzbjedu").setValue(0);
					return;
				}
				if (zhibaojinBL != null) {
					var zhibaojinedu = zhibaojinBL * htongzongjine / 100;
					pluginCtx.getKDFormattedTextField("txtzbjedu").setValue(zhibaojinedu);
				} else {
					pluginCtx.getKDFormattedTextField("txtzbjedu").setValue(0);
				}
			} else {
				pluginCtx.getKDFormattedTextField("txtzbjedu").setValue(0);
			}
			//com.kingdee.eas.util.client.MsgBox.showInfo(htongzongjine==null);
		}
		if (methodName == "equals") {
			return this == e;
		}
	});
	pluginCtx.getKDFormattedTextField("txtzbjedu").addDataChangeListener(function (e, methodName) {
		if (methodName == "dataChanged") {
			var htongzongjine = pluginCtx.getKDFormattedTextField("txtTotalTaxAmount").getValue();
			if (htongzongjine != null && htongzongjine != 0) {
				var txtzbjedu = pluginCtx.getKDFormattedTextField("txtzbjedu").getValue();

				if (txtzbjedu != null && txtzbjedu != 0) {
					var zhibaojinBL = txtzbjedu * 100 / htongzongjine;
					if (zhibaojinBL < 0 || zhibaojinBL > 100) {
						com.kingdee.eas.util.client.MsgBox.showInfo("质保金比例要在0-100之间");
						pluginCtx.getKDFormattedTextField("txtzbjbili").setValue(0);
						pluginCtx.getKDFormattedTextField("txtzbjedu").setValue(0);
						return;
					} else {
						pluginCtx.getKDFormattedTextField("txtzbjbili").setValue(zhibaojinBL);
					}

				} else {
					pluginCtx.getKDFormattedTextField("txtzbjbili").setValue(0);
				}
			} else {
				pluginCtx.getKDFormattedTextField("txtzbjbili").setValue(0);
			}
			//com.kingdee.eas.util.client.MsgBox.showInfo(htongzongjine==null);
		}
		if (methodName == "equals") {
			return this == e;
		}
	});
	pluginCtx.getKDTable("kdtEntries").addKDTEditListener(function (event, methodName) {


		if (methodName == "editStarting") {
			var table = pluginCtx.getKDTable("kdtEntries");
			var line = event.getRowIndex();
			var row1 = table.getRow(line);

			var colName1 = table.getColumnKey(event.getColIndex());
			var CGXZ = pluginCtx.getKDTextField("txthtxz").getText();
			var prmthtlxval = pluginCtx.getKDBizPromptBox("prmthtlx").getValue();
			var htlxname = "";
			if (prmthtlxval != null) {
				htlxname = prmthtlxval.getName().toString();
			}

			var rowcount = table.getRowCount();
			if (CGXZ != "采购类合同" || htlxname == "工程设备采购类合同") {
				//com.kingdee.eas.util.client.MsgBox.showInfo(CGXZ);
				for (var i = 0; i < rowcount; i++) {
					table.getRow(i).getCell("taxPrice").setValue(new java.math.BigDecimal(1));
				}
			} else {
				var prmtPurchaseOrgUnit = pluginCtx.getKDBizPromptBox("prmtPurchaseOrgUnit").getValue();
				if (prmtPurchaseOrgUnit != null && prmtPurchaseOrgUnit.getId() != null) {
					var orgid = prmtPurchaseOrgUnit.getId().toString();
					var sql = " SELECT * FROM T_ORG_BaseUnit where fid  ='" + orgid + "'  ";

					//com.kingdee.eas.util.client.MsgBox.showInfo(sql);
					var row = null;
					row = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
					if (row.next()) {
						var cfbkid = row.getString("cfbkid");
						var sqlbk = " select * from CT_CUS_Bk where fid = '" + cfbkid + "'";

						//com.kingdee.eas.util.client.MsgBox.showInfo(sql);
						var rowbk = null;
						rowbk = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(sqlbk).executeSQL();
						if (rowbk.next()) {
							var fnamebk = rowbk.getString("fname_l2");
							if (fnamebk == "新能源板块") {
								var filterInfo = new com.kingdee.bos.metadata.entity.FilterInfo();
								var entityViewInfo = new com.kingdee.bos.metadata.entity.EntityViewInfo();
								var filterItemInfo = new com.kingdee.bos.metadata.entity.FilterItemInfo("name", "新能源采购", com.kingdee.bos.metadata.query.util.CompareType.EQUALS);
								filterInfo.getFilterItems().add(filterItemInfo);
								entityViewInfo.setFilter(filterInfo);

								var accountcol = com.kingdee.eas.basedata.master.material.MaterialFactory.getRemoteInstance().getMaterialCollection(entityViewInfo);
								var cur = new com.kingdee.eas.basedata.master.material.MaterialInfo();
								if (accountcol.size() > 0) {
									cur = accountcol.get(0);
									var FBaseUnit = cur.getBaseUnit();
									if (FBaseUnit != null) {
										var cur2 = new com.kingdee.eas.basedata.assistant.MeasureUnitInfo();
										var oql2 = "select where id='" + FBaseUnit.getId() + "'";
										var accountcol2 = com.kingdee.eas.basedata.assistant.MeasureUnitFactory.getRemoteInstance().getMeasureUnitCollection(oql2);
										if (accountcol2.size() > 0) {
											cur2 = accountcol2.get(0);
											for (var i = 0; i < rowcount; i++) {
												table.getRow(i).getCell("materialNum").setValue(cur);
												table.getRow(i).getCell("materialName").setValue(cur.getName());
												table.getRow(i).getCell("unit").setValue(cur2);
												table.getRow(i).getCell("baseUnit").setValue(cur2); //jerry 给基本计量单位赋值
											}
										}
									}
								}
								var curck = new com.kingdee.eas.basedata.scm.im.inv.WarehouseInfo();
								var oqlck = "select where number='0801'";
								var accountcolck = com.kingdee.eas.basedata.scm.im.inv.WarehouseFactory.getRemoteInstance().getWarehouseCollection(oqlck);
								if (accountcolck.size() > 0) {
									curck = accountcolck.get(0);
									for (var i = 0; i < rowcount; i++) {
										table.getRow(i).getCell("wareHouse").setValue(curck);
									}
								}
							}
						}
					}
				}
			}


			if ("materialNum".equals(colName1)) {
				row1.getCell("shuilv").setValue(null);
				row1.getCell("taxRate").setValue(new java.math.BigDecimal(0));
				var amtax = row1.getCell("taxAmount").getValue();

				row1.getCell("amount").setValue(amtax);
				var localtaxam = row1.getCell("localTaxAmount").getValue();
				row1.getCell("localAmount").setValue(new java.math.BigDecimal(localtaxam));
				row1.getCell("localTax").setValue(new java.math.BigDecimal(0));
				row1.getCell("tax").setValue(new java.math.BigDecimal(0));
				var hetongleixing = pluginCtx.getKDBizPromptBox("prmthtlx").getValue();
				if (hetongleixing == null || hetongleixing.getId() == null) {
					com.kingdee.eas.util.client.MsgBox.showInfo("合同类型为空，请注意填写合同类型！");
				}
			}
		}
		if (methodName == "editStopped") {
			var table = pluginCtx.getKDTable("kdtEntries");
			var line = event.getRowIndex();
			var colName1 = table.getColumnKey(event.getColIndex());
			var rowcount = table.getRowCount();
			var CGXZ = pluginCtx.getKDTextField("txthtxz").getText();
			var rownew = table.getRow(line);
			if ("qty".equals(colName1) || "taxPrice".equals(colName1)) {
				var exchangeRate = pluginCtx.getKDFormattedTextField("txtExchangeRate").getValue();

				//com.kingdee.eas.util.client.MsgBox.showInfo(exchangeRate);
				var txtTotalTaxAmount = pluginCtx.getKDFormattedTextField("txtTotalTaxAmount").getValue();

				//com.kingdee.eas.util.client.MsgBox.showInfo(txtTotalTaxAmount);
				if (exchangeRate != null && txtTotalTaxAmount != null) {
					exchangeRate = java.lang.Float.parseFloat(exchangeRate);
					txtTotalTaxAmount = java.lang.Float.parseFloat(txtTotalTaxAmount);
					var zonge = 0;
					for (var line = 0; line < rowcount; line++) {
						var rownew = table.getRow(line);

						var quantity = rownew.getCell("qty").getValue();
						var taxprice = rownew.getCell("taxPrice").getValue();
						if (quantity != null && taxprice != null && quantity != "" && taxprice != "") {
							var floqua = java.lang.Float.parseFloat(quantity);
							var floprice = java.lang.Float.parseFloat(taxprice);
							zonge = zonge + floqua * floprice;
						}
					}
					var benweibi = exchangeRate * zonge;
					pluginCtx.getKDFormattedTextField("txtkDNumberTextField").setValue(benweibi);
				}
			}
			if ("qty".equals(colName1)) {
				var qty = rownew.getCell("qty").getValue();
				rownew.getCell("weidaopiao").setValue(qty);
			}
			if ("preReceived".equals(colName1)) {
				var zonginamount = 0;
				for (var n = 0; n < rowcount; n++) {
					var rownew = table.getRow(n);
					var preReceived = rownew.getCell("preReceived").getValue();
					if (preReceived != null && preReceived != 0) {
						var inamount = java.lang.Float.parseFloat(preReceived);
						zonginamount += inamount;
					}
				}
				pluginCtx.getKDFormattedTextField("txtPrepaid").setValue(zonginamount);
			}
			if ("totalInvoicedAmount".equals(colName1)) {
				var zonginamount = 0;
				for (var n = 0; n < rowcount; n++) {
					var rownew = table.getRow(n);
					var totalInvoicedAmount = rownew.getCell("totalInvoicedAmount").getValue();
					if (totalInvoicedAmount != null && totalInvoicedAmount != 0) {
						var inamount = java.lang.Float.parseFloat(totalInvoicedAmount);
						zonginamount += inamount;
					}
				}
				pluginCtx.getKDFormattedTextField("txtkDNumberTextField3").setValue(zonginamount);
			}
			if ("totalPaidAmount".equals(colName1)) {
				var zonginamount = 0;
				for (var n = 0; n < rowcount; n++) {
					var rownew = table.getRow(n);
					var totalPaidAmount = rownew.getCell("totalPaidAmount").getValue();
					if (totalPaidAmount != null && totalPaidAmount != 0) {
						var inamount = java.lang.Float.parseFloat(totalPaidAmount);
						zonginamount += inamount;
					}
				}
				pluginCtx.getKDFormattedTextField("txtkDNumberTextField6").setValue(zonginamount);
			}
			if ("shuilv".equals(colName1)) {
				var row1 = table.getRow(line);
				var taxRate1 = row1.getCell("shuilv");
				var taxvalue1 = null;
				if (taxRate1 != null && taxRate1.getValue() != null) {
					taxvalue1 = taxRate1.getValue();
				}
				for (var n = line; n < rowcount; n++) {
					var rownew = table.getRow(n);
					var materialNum = rownew.getCell("materialNum");
					if (materialNum != null && materialNum.getValue() != null) {

						if (n > line) {
							rownew.getCell("shuilv").setValue(taxvalue1);
						}
						var taxRate = rownew.getCell("shuilv");
						if (taxRate != null && taxRate.getValue() != null) {
							var taxvalue = taxRate.getValue();
							if (taxvalue.getDescription() != null) {
								var taxname = taxvalue.getDescription();
								var newStr = taxname.replace("%", "");

								var tax = java.lang.Float.parseFloat(newStr);
								var bigtax = new java.math.BigDecimal(tax);
								rownew.getCell("taxRate").setValue(bigtax);
								var taxpoint = bigtax.divide(new java.math.BigDecimal(100));

								var quantity = rownew.getCell("qty").getValue();
								if (quantity != null && quantity != 0) {
									var qua = new java.math.BigDecimal(quantity);
									var taxPrice = rownew.getCell("taxPrice").getValue();
									if (taxPrice != null && taxPrice != 0) {
										var pri = new java.math.BigDecimal(taxPrice);
										var amount = pri.multiply(qua);

										var zongtax = taxpoint.add(new java.math.BigDecimal(1));

										var amountouttax = amount.divide(zongtax, 2, java.math.BigDecimal.ROUND_HALF_UP);
										//com.kingdee.eas.util.client.MsgBox.showInfo(amountouttax);
										rownew.getCell("amount").setValue(amountouttax);
										rownew.getCell("localAmount").setValue(amountouttax);
										var taxam = amount.subtract(amountouttax);
										rownew.getCell("tax").setValue(taxam);
										var taxprice = amountouttax.divide(qua, 6, java.math.BigDecimal.ROUND_HALF_UP);
										rownew.getCell("price").setValue(taxprice);
									}
								} else {
									var taxPrice = rownew.getCell("taxPrice").getValue();
									if (taxPrice == null) {
										rownew.getCell("qty").setValue(new java.math.BigDecimal(0));
										rownew.getCell("assistQty").setValue(new java.math.BigDecimal(0));
										rownew.getCell("price").setValue(new java.math.BigDecimal(0));
										rownew.getCell("taxPrice").setValue(new java.math.BigDecimal(0));
										rownew.getCell("discountRate").setValue(new java.math.BigDecimal(0));
										rownew.getCell("discountAmount").setValue(new java.math.BigDecimal(0));
										rownew.getCell("actualTaxPrice").setValue(new java.math.BigDecimal(0));
										rownew.getCell("actualPrice").setValue(new java.math.BigDecimal(0));
										rownew.getCell("amount").setValue(new java.math.BigDecimal(0));
										rownew.getCell("localAmount").setValue(new java.math.BigDecimal(0));
										rownew.getCell("tax").setValue(new java.math.BigDecimal(0));
										rownew.getCell("taxAmount").setValue(new java.math.BigDecimal(0));
										rownew.getCell("totalReceiveQty").setValue(new java.math.BigDecimal(0));
										rownew.getCell("planReceiveQty").setValue(new java.math.BigDecimal(0));
										rownew.getCell("totalReceiptQty").setValue(new java.math.BigDecimal(0));
										rownew.getCell("totalReturnedQty").setValue(new java.math.BigDecimal(0));
										rownew.getCell("totalCancelledStockQty").setValue(new java.math.BigDecimal(0));
										rownew.getCell("totalInvoicedQty").setValue(new java.math.BigDecimal(0));
										rownew.getCell("totalInvoicedAmount").setValue(new java.math.BigDecimal(0));
										rownew.getCell("totalReqPayAmt").setValue(new java.math.BigDecimal(0));
										rownew.getCell("totalPaidAmount").setValue(new java.math.BigDecimal(0));
										rownew.getCell("localTax").setValue(new java.math.BigDecimal(0));
										rownew.getCell("localTaxAmount").setValue(new java.math.BigDecimal(0));
									}
								}
								//com.kingdee.eas.util.client.MsgBox.showInfo(materialNum.getValue());
							}
						} else {
							var bigtax = new java.math.BigDecimal(0);
							rownew.getCell("taxRate").setValue(bigtax);
							var taxpoint = bigtax.divide(new java.math.BigDecimal(100));

							var quantity = rownew.getCell("qty").getValue();
							if (quantity != null && quantity != 0) {
								var qua = new java.math.BigDecimal(quantity);
								var taxPrice = rownew.getCell("taxPrice").getValue();
								if (taxPrice != null && taxPrice != 0) {
									var pri = new java.math.BigDecimal(taxPrice);
									var amount = pri.multiply(qua);

									var zongtax = taxpoint.add(new java.math.BigDecimal(1));

									var amountouttax = amount.divide(zongtax, 2, java.math.BigDecimal.ROUND_HALF_UP);
									//com.kingdee.eas.util.client.MsgBox.showInfo(amountouttax);
									rownew.getCell("amount").setValue(amountouttax);
									rownew.getCell("localAmount").setValue(amountouttax);
									var taxam = amount.subtract(amountouttax);
									rownew.getCell("tax").setValue(taxam);
									var taxprice = amountouttax.divide(qua, 6, java.math.BigDecimal.ROUND_HALF_UP);
									rownew.getCell("price").setValue(taxprice);
								}
							} else {
								var taxPrice = rownew.getCell("taxPrice").getValue();
								if (taxPrice == null) {
									rownew.getCell("qty").setValue(new java.math.BigDecimal(0));
									rownew.getCell("assistQty").setValue(new java.math.BigDecimal(0));
									rownew.getCell("price").setValue(new java.math.BigDecimal(0));
									rownew.getCell("taxPrice").setValue(new java.math.BigDecimal(0));
									rownew.getCell("discountRate").setValue(new java.math.BigDecimal(0));
									rownew.getCell("discountAmount").setValue(new java.math.BigDecimal(0));
									rownew.getCell("actualTaxPrice").setValue(new java.math.BigDecimal(0));
									rownew.getCell("actualPrice").setValue(new java.math.BigDecimal(0));
									rownew.getCell("amount").setValue(new java.math.BigDecimal(0));
									rownew.getCell("localAmount").setValue(new java.math.BigDecimal(0));
									rownew.getCell("tax").setValue(new java.math.BigDecimal(0));
									rownew.getCell("taxAmount").setValue(new java.math.BigDecimal(0));
									rownew.getCell("totalReceiveQty").setValue(new java.math.BigDecimal(0));
									rownew.getCell("planReceiveQty").setValue(new java.math.BigDecimal(0));
									rownew.getCell("totalReceiptQty").setValue(new java.math.BigDecimal(0));
									rownew.getCell("totalReturnedQty").setValue(new java.math.BigDecimal(0));
									rownew.getCell("totalCancelledStockQty").setValue(new java.math.BigDecimal(0));
									rownew.getCell("totalInvoicedQty").setValue(new java.math.BigDecimal(0));
									rownew.getCell("totalInvoicedAmount").setValue(new java.math.BigDecimal(0));
									rownew.getCell("totalReqPayAmt").setValue(new java.math.BigDecimal(0));
									rownew.getCell("totalPaidAmount").setValue(new java.math.BigDecimal(0));
									rownew.getCell("localTax").setValue(new java.math.BigDecimal(0));
									rownew.getCell("localTaxAmount").setValue(new java.math.BigDecimal(0));
								}
							}
						}
						var maxtax = new java.math.BigDecimal(0);
						var maxamount = new java.math.BigDecimal(0);
						for (var i = 0; i < rowcount; i++) {
							var rownow = table.getRow(i);
							var materialNum = rownow.getCell("materialNum");
							if (materialNum != null && materialNum.getValue() != null) {
								var taxRate = rownow.getCell("tax").getValue();
								maxtax = maxtax.add(taxRate);
								var amountouttax = rownow.getCell("amount").getValue();
								maxamount = maxamount.add(amountouttax);
							}
						}
						pluginCtx.getKDFormattedTextField("txtTotalTax").setValue(maxtax);
						pluginCtx.getKDFormattedTextField("txtTotalAmount").setValue(maxamount);

					}
				}
			}
			var prmthtlxval = pluginCtx.getKDBizPromptBox("prmthtlx").getValue();
			var htlxname = "";
			if (prmthtlxval != null) {
				htlxname = prmthtlxval.getName().toString();
			}
			if ("xiangmubianma".equals(colName1)) {
				var rownew = table.getRow(line);
				var xiangmubianma = rownew.getCell("xiangmubianma");
				var info = new com.kingdee.eas.ep.DataBaseCustomInfo();
				if (xiangmubianma != null && xiangmubianma.getValue() != null) {
					var xmbmvalue = xiangmubianma.getValue();
					if (xmbmvalue.getName() != null) {
						var xiangmumingcheng = xmbmvalue.getName();
						rownew.getCell("xiangmumingcheng").setValue(xiangmumingcheng);
					}
					if (xmbmvalue.getId() != null) {
						var xiangmuid = xmbmvalue.getId().toString();
						var sql1 = " SELECT * FROM T_BD_Project where fid ='" + xiangmuid + "'  ";
						var row2 = null;
						row2 = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(sql1).executeSQL();

						if (row2.next()) {
							var CFXmqjID = row2.getString("CFXmqjID");
							var sql3 = " SELECT * FROM CT_CUS_Xmqj where fid ='" + CFXmqjID + "'  ";
							var row3 = null;
							row3 = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(sql3).executeSQL();

							if (row3.next()) {
								var fid = row3.getString("fid");
								var fname_l2 = row3.getString("fname_l2");
								var fnumber = row3.getString("fnumber");
								//com.kingdee.eas.util.client.MsgBox.showInfo(selected);
								info.put("id", BOSUuid.read(fid));
								info.put("name", fname_l2);
								info.put("number", fnumber);
								rownew.getCell("xmqijian").setValue(info);
							}
						}
					}
				}
			}
			if ("xiangmubianma".equals(colName1)) {
				var line = event.getRowIndex();
				//com.kingdee.eas.util.client.MsgBox.showInfo(line);

				var rownew = table.getRow(line);
				var xiangmubianma = rownew.getCell("xiangmubianma");
				if (xiangmubianma != null && xiangmubianma.getValue() != null) {
					var xmbmvalue = xiangmubianma.getValue();
					if (xmbmvalue.getName() != null) {
						var xiangmumingcheng = xmbmvalue.getName();
						rownew.getCell("xiangmumingcheng").setValue(xiangmumingcheng);

						if (CGXZ != "采购类合同" || htlxname == "工程设备采购类合同") {

							for (var n = line; n < rowcount; n++) {
								if (n > 0) {
									var rownow = table.getRow(n);
									var xiangmubianmacell = table.getRow(n).getCell("xiangmubianma");
									xiangmubianmacell.setValue(xmbmvalue);
									var xiangmumingchengcell = table.getRow(n).getCell("xiangmumingcheng");
									xiangmumingchengcell.setValue(xiangmumingcheng);
								}
							}

						} else {

							for (var n = line; n < rowcount; n++) {
								if (n > 0) {
									var rownow = table.getRow(n);
									var xiangmubianmacell = table.getRow(n).getCell("xiangmubianma");
									xiangmubianmacell.setValue(xmbmvalue);
									var xiangmumingchengcell = table.getRow(n).getCell("xiangmumingcheng");
									xiangmumingchengcell.setValue(xiangmumingcheng);
								}
							}
						}

					} else {
						rownew.getCell("xiangmumingcheng").setValue("");
						if (CGXZ != "采购类合同" || htlxname == "工程设备采购类合同") {

							for (var n = line; n < rowcount; n++) {
								if (n > 0) {
									var rownow = table.getRow(n);
									var xiangmubianmacell = table.getRow(n).getCell("xiangmubianma");
									xiangmubianmacell.setValue(null);
									var xiangmumingchengcell = table.getRow(n).getCell("xiangmumingcheng");
									xiangmumingchengcell.setValue("");
								}
							}

						} else {

							for (var n = line; n < rowcount; n++) {
								if (n > 0) {
									var rownow = table.getRow(n);
									var xiangmubianmacell = table.getRow(n).getCell("xiangmubianma");
									xiangmubianmacell.setValue(null);
									var xiangmumingchengcell = table.getRow(n).getCell("xiangmumingcheng");
									xiangmumingchengcell.setValue("");
								}
							}

						}

					}

				} else {
					rownew.getCell("xiangmumingcheng").setValue("");
					if (CGXZ != "采购类合同") {}
				}
			}
			if ("materialNum".equals(colName1)) {

				for (var line = 0; line < rowcount; line++) {
					var rownew = table.getRow(line);
					var taxRate = rownew.getCell("taxRate");

					rownew.getCell("shuilv").setValue(null);

					var bigtax = new java.math.BigDecimal(0);
					rownew.getCell("taxRate").setValue(bigtax);
					var taxpoint = bigtax.divide(new java.math.BigDecimal(100));

					var quantity = rownew.getCell("qty").getValue();
					if (quantity != null && quantity != 0) {
						var qua = new java.math.BigDecimal(quantity);
						var taxPrice = rownew.getCell("taxPrice").getValue();
						if (taxPrice != null && taxPrice != 0) {
							var pri = new java.math.BigDecimal(taxPrice);
							var amount = pri.multiply(qua);

							var zongtax = taxpoint.add(new java.math.BigDecimal(1));

							var amountouttax = amount.divide(zongtax, 2, java.math.BigDecimal.ROUND_HALF_UP);
							//com.kingdee.eas.util.client.MsgBox.showInfo(amountouttax);
							rownew.getCell("amount").setValue(amountouttax);
							rownew.getCell("localAmount").setValue(amountouttax);
							var taxam = amount.subtract(amountouttax);
							rownew.getCell("tax").setValue(taxam);
							var taxprice = amountouttax.divide(qua, 6, java.math.BigDecimal.ROUND_HALF_UP);
							rownew.getCell("price").setValue(taxprice);
						}
					} else {
						var taxPrice = rownew.getCell("taxPrice").getValue();
						if (taxPrice == null) {
							rownew.getCell("qty").setValue(new java.math.BigDecimal(0));
							rownew.getCell("assistQty").setValue(new java.math.BigDecimal(0));
							rownew.getCell("price").setValue(new java.math.BigDecimal(0));
							rownew.getCell("taxPrice").setValue(new java.math.BigDecimal(0));
							rownew.getCell("discountRate").setValue(new java.math.BigDecimal(0));
							rownew.getCell("discountAmount").setValue(new java.math.BigDecimal(0));
							rownew.getCell("actualTaxPrice").setValue(new java.math.BigDecimal(0));
							rownew.getCell("actualPrice").setValue(new java.math.BigDecimal(0));
							rownew.getCell("amount").setValue(new java.math.BigDecimal(0));
							rownew.getCell("localAmount").setValue(new java.math.BigDecimal(0));
							rownew.getCell("tax").setValue(new java.math.BigDecimal(0));
							rownew.getCell("taxAmount").setValue(new java.math.BigDecimal(0));
							rownew.getCell("totalReceiveQty").setValue(new java.math.BigDecimal(0));
							rownew.getCell("planReceiveQty").setValue(new java.math.BigDecimal(0));
							rownew.getCell("totalReceiptQty").setValue(new java.math.BigDecimal(0));
							rownew.getCell("totalReturnedQty").setValue(new java.math.BigDecimal(0));
							rownew.getCell("totalCancelledStockQty").setValue(new java.math.BigDecimal(0));
							rownew.getCell("totalInvoicedQty").setValue(new java.math.BigDecimal(0));
							rownew.getCell("totalInvoicedAmount").setValue(new java.math.BigDecimal(0));
							rownew.getCell("totalReqPayAmt").setValue(new java.math.BigDecimal(0));
							rownew.getCell("totalPaidAmount").setValue(new java.math.BigDecimal(0));
							rownew.getCell("localTax").setValue(new java.math.BigDecimal(0));
							rownew.getCell("localTaxAmount").setValue(new java.math.BigDecimal(0));
						}
					}
				}
			}
			if ("adminOrgUnit".equals(colName1)) {
				var line = event.getRowIndex();
				var rownew = table.getRow(line);
				var adminOrgUnit = rownew.getCell("adminOrgUnit");
				if (adminOrgUnit != null && adminOrgUnit.getValue() != null) {
					var adminvalue = adminOrgUnit.getValue();
					for (var n = line; n < rowcount; n++) {
						var rownow = table.getRow(n);
						rownow.getCell("adminOrgUnit").setValue(adminvalue);
					}
				}
			}
			if ("xiangmubianma".equals(colName1)) {
				var rownew = table.getRow(line);
				var xiangmuNum = rownew.getCell("xiangmubianma");
				if (xiangmuNum != null && xiangmuNum.getValue() != null) {
					var xmvalue = xiangmuNum.getValue();
					for (var n = line; n < rowcount; n++) {
						var rownow = table.getRow(n);
						var xiangmuNumcell = table.getRow(n).getCell("xiangmubianma");
						xiangmuNumcell.setValue(xmvalue);
						if (xmvalue.getName() != null) {
							var xiangmuName = xmvalue.getName();
							var xiangmuNameCell = table.getRow(n).getCell("xiangmumingcheng");
							xiangmuNameCell.setValue(xiangmuName);
						}
					}
				}
			}
			if ("yewuleibie".equals(colName1)) {
				var rownew = table.getRow(line);
				var yewuleibie = rownew.getCell("yewuleibie");
				if (yewuleibie != null && yewuleibie.getValue() != null) {
					var yewuvalue = yewuleibie.getValue();
					for (var n = line; n < rowcount; n++) {
						var rownow = table.getRow(n);
						var yewuleibiecell = table.getRow(n).getCell("yewuleibie");
						yewuleibiecell.setValue(yewuvalue);

					}
				}
			}
			if ("wareHouse".equals(colName1)) {
				var rownew = table.getRow(line);
				var wareHouse = rownew.getCell("wareHouse");
				if (wareHouse != null && wareHouse.getValue() != null) {
					var cangkuvalue = wareHouse.getValue();
					for (var n = line; n < rowcount; n++) {
						var rownow = table.getRow(n);
						var wareHousecell = table.getRow(n).getCell("wareHouse");
						wareHousecell.setValue(cangkuvalue);

					}
				}
			}
		}
		if (methodName == "editStopping") {}
		if (methodName == "equals") {
			return this == event;
		}
	});
	pluginCtx.getKDBizPromptBox("prmthtlx").addDataChangeListener(function (e, methodName) {
		if (methodName == "dataChanged") {
			var table = pluginCtx.getKDTable("kdtEntries");
			var hetongleixing = pluginCtx.getKDBizPromptBox("prmthtlx").getValue();
			var rowcount = table.getRowCount();


			if (hetongleixing != null && hetongleixing.getId() != null) {
				var htlxid = hetongleixing.getId().toString();
				var htlxname = hetongleixing.getName().toString();
				//com.kingdee.eas.util.client.MsgBox.showInfo(htlxname);
				var sql = "select * from CT_CUS_HTLX WHERE fid = '" + htlxid + "'";

				var sqlExecutor = new com.kingdee.bos.dao.query.SQLExecutor(sql);
				var rowSet = sqlExecutor.executeSQL();
				if (rowSet.next()) {

					var Ftreeid = rowSet.getString("Ftreeid");

					var sql2 = "select * from T_CUS_HTLXTREE WHERE fid = '" + Ftreeid + "'";
					var sqlExecutor2 = new com.kingdee.bos.dao.query.SQLExecutor(sql2);
					var rowSet2 = sqlExecutor2.executeSQL();
					if (rowSet2.next()) {
						var fnumber = rowSet2.getString("fnumber");
						if (fnumber != "01" || htlxname == "工程设备采购类合同") {
							if (fnumber == "02") {
								pluginCtx.getKDLabelContainer("contkDNumberTextField2").setVisible(true);
							} else {
								pluginCtx.getKDLabelContainer("contkDNumberTextField2").setVisible(false);
							}
							table.getColumn("materialNum").getStyleAttributes().setHided(true);
							table.getColumn("materialName").getStyleAttributes().setHided(true);
							table.getColumn("materialModel").getStyleAttributes().setHided(true);
							table.getColumn("wareHouse").getStyleAttributes().setHided(true);
							table.getColumn("unit").getStyleAttributes().setHided(true);
							table.getColumn("taxPrice").getStyleAttributes().setHided(true);
							table.getColumn("price").getStyleAttributes().setHided(true);
							table.getColumn("taxAmount").getStyleAttributes().setHided(true);
							table.getColumn("amount").getStyleAttributes().setHided(true);
							table.getHeadRow(0).getCell("qty").setValue("价税合计");
						} else if (fnumber == "01") {
							pluginCtx.getKDLabelContainer("contkDNumberTextField2").setVisible(false);
							table.getColumn("materialNum").getStyleAttributes().setHided(false);
							table.getColumn("materialName").getStyleAttributes().setHided(false);
							table.getColumn("materialModel").getStyleAttributes().setHided(false);
							table.getColumn("wareHouse").getStyleAttributes().setHided(false);
							table.getColumn("unit").getStyleAttributes().setHided(false);
							table.getColumn("taxPrice").getStyleAttributes().setHided(false);
							table.getColumn("price").getStyleAttributes().setHided(false);
							table.getColumn("taxAmount").getStyleAttributes().setHided(false);
							table.getColumn("amount").getStyleAttributes().setHided(false);
							table.getHeadRow(0).getCell("qty").setValue("数量");
							var prmtPurchaseOrgUnit = pluginCtx.getKDBizPromptBox("prmtPurchaseOrgUnit").getValue();
							if (prmtPurchaseOrgUnit != null && prmtPurchaseOrgUnit.getId() != null) {
								var orgid = prmtPurchaseOrgUnit.getId().toString();
								var sqlorg = " SELECT * FROM T_ORG_BaseUnit where fid  ='" + orgid + "'  ";

								//com.kingdee.eas.util.client.MsgBox.showInfo(sql);
								var row = null;
								row = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(sqlorg).executeSQL();
								if (row.next()) {
									var cfbkid = row.getString("cfbkid");
									var sqlbk = " select * from CT_CUS_Bk where fid = '" + cfbkid + "'";

									//com.kingdee.eas.util.client.MsgBox.showInfo(sql);
									var rowbk = null;
									rowbk = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(sqlbk).executeSQL();
									if (rowbk.next()) {
										var fnamebk = rowbk.getString("fname_l2");
										if (fnamebk == "新能源板块") {
											table.getColumn("materialNum").getStyleAttributes().setHided(true);
											table.getColumn("materialName").getStyleAttributes().setHided(true);
											table.getColumn("materialModel").getStyleAttributes().setHided(true);
											table.getColumn("wareHouse").getStyleAttributes().setHided(true);
											table.getColumn("unit").getStyleAttributes().setHided(true);
										}
									}
								}
							}
						}
						var fname_l2 = rowSet2.getString("fname_l2");
						//com.kingdee.eas.util.client.MsgBox.showInfo(fname_l2);
						pluginCtx.getKDTextField("txthtxz").setText(fname_l2);
						if (fname_l2 == "采购类合同" && htlxname != "工程设备采购类合同") {
							table.getColumn("wareHouse").setRequired(true);
						} else {
							table.getColumn("wareHouse").setRequired(false);
						}
						if (fname_l2 == "采购类合同" || fname_l2 == "工程类合同") {
							table.getColumn("xiangmubianma").setRequired(true);
						} else {
							table.getColumn("xiangmubianma").setRequired(false);
						}
						if (fname_l2 == "工程类合同" || htlxname == "工程设备采购类合同") {
							/*var sql = "select * from T_BD_Material where fnumber='9901'";							
							var sqlExecutor=new com.kingdee.bos.dao.query.SQLExecutor(sql);
							var rowSet=sqlExecutor.executeSQL();
							if(rowSet.next()){
								var fid=rowSet.getString("fid");
								var fnumber=rowSet.getString("fnumber");
								var fname_l2=rowSet.getString("fname_l2");
								var cur = new com.kingdee.eas.basedata.master.material.MaterialInfo();
								cur.setId(BOSUuid.read(fid));
								cur.setNumber(fnumber);
								cur.setName(fname_l2);
								var FBaseUnit=rowSet.getString("FBaseUnit");
								if(FBaseUnit!=null){
									var sql2 = "select * from T_BD_MeasureUnit where fid='"+FBaseUnit+"'";
									var sqlExecutor2=new com.kingdee.bos.dao.query.SQLExecutor(sql2);
									var rowSet2=sqlExecutor2.executeSQL();
									if(rowSet2.next()){
										var fid2=rowSet2.getString("fid");
										var fnumber2=rowSet2.getString("fnumber");
										var fname_l22=rowSet2.getString("fname_l2");
										var cur2 = new com.kingdee.eas.basedata.assistant.MeasureUnitInfo();
										cur2.setId(BOSUuid.read(fid2));
										cur2.setNumber(fnumber2);
										cur2.setName(fname_l22);
										for(var i=0;i<rowcount;i++){
											table.getRow(i).getCell("materialNum").setValue(cur);
											table.getRow(i).getCell("materialName").setValue(fname_l2);
											table.getRow(i).getCell("unit").setValue(cur2);
										}
									}
									
								}								
								
							}*/
							//jerry 使用全对象赋值
							//var oql ="select where number='9901'";

							var filterInfo = new com.kingdee.bos.metadata.entity.FilterInfo();
							var entityViewInfo = new com.kingdee.bos.metadata.entity.EntityViewInfo();
							var filterItemInfo = new com.kingdee.bos.metadata.entity.FilterItemInfo("number", "9901", com.kingdee.bos.metadata.query.util.CompareType.EQUALS);
							filterInfo.getFilterItems().add(filterItemInfo);
							entityViewInfo.setFilter(filterInfo);

							var accountcol = com.kingdee.eas.basedata.master.material.MaterialFactory.getRemoteInstance().getMaterialCollection(entityViewInfo);
							var cur = new com.kingdee.eas.basedata.master.material.MaterialInfo();
							if (accountcol.size() > 0) {
								cur = accountcol.get(0);
								var FBaseUnit = cur.getBaseUnit();
								if (FBaseUnit != null) {
									var cur2 = new com.kingdee.eas.basedata.assistant.MeasureUnitInfo();
									var oql2 = "select where id='" + FBaseUnit.getId() + "'";
									var accountcol2 = com.kingdee.eas.basedata.assistant.MeasureUnitFactory.getRemoteInstance().getMeasureUnitCollection(oql2);
									if (accountcol2.size() > 0) {
										cur2 = accountcol2.get(0);
										for (var i = 0; i < rowcount; i++) {
											table.getRow(i).getCell("materialNum").setValue(cur);
											table.getRow(i).getCell("materialName").setValue(cur.getName());
											table.getRow(i).getCell("unit").setValue(cur2);
											table.getRow(i).getCell("baseUnit").setValue(cur2); //jerry 给基本计量单位赋值
										}
									}
								}
							}
						}
						if (fname_l2 == "采购类合同" && htlxname != "工程设备采购类合同") {
							for (var i = 0; i < rowcount; i++) {
								table.getRow(i).getCell("materialNum").setValue(null);
								table.getRow(i).getCell("materialName").setValue("");
								table.getRow(i).getCell("unit").setValue(null);
							}
							var prmtPurchaseOrgUnit = pluginCtx.getKDBizPromptBox("prmtPurchaseOrgUnit").getValue();
							if (prmtPurchaseOrgUnit != null && prmtPurchaseOrgUnit.getId() != null) {
								var orgid = prmtPurchaseOrgUnit.getId().toString();
								var sql = " SELECT * FROM T_ORG_BaseUnit where fid  ='" + orgid + "'  ";

								//com.kingdee.eas.util.client.MsgBox.showInfo(sql);
								var row = null;
								row = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
								if (row.next()) {
									var cfbkid = row.getString("cfbkid");
									var sqlbk = " select * from CT_CUS_Bk where fid = '" + cfbkid + "'";

									//com.kingdee.eas.util.client.MsgBox.showInfo(sql);
									var rowbk = null;
									rowbk = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(sqlbk).executeSQL();
									if (rowbk.next()) {
										var fnamebk = rowbk.getString("fname_l2");
										if (fnamebk == "新能源板块") {
											var filterInfo = new com.kingdee.bos.metadata.entity.FilterInfo();
											var entityViewInfo = new com.kingdee.bos.metadata.entity.EntityViewInfo();
											var filterItemInfo = new com.kingdee.bos.metadata.entity.FilterItemInfo("name", "新能源采购", com.kingdee.bos.metadata.query.util.CompareType.EQUALS);
											filterInfo.getFilterItems().add(filterItemInfo);
											entityViewInfo.setFilter(filterInfo);

											var accountcol = com.kingdee.eas.basedata.master.material.MaterialFactory.getRemoteInstance().getMaterialCollection(entityViewInfo);
											var cur = new com.kingdee.eas.basedata.master.material.MaterialInfo();
											if (accountcol.size() > 0) {
												cur = accountcol.get(0);
												var FBaseUnit = cur.getBaseUnit();
												if (FBaseUnit != null) {
													var cur2 = new com.kingdee.eas.basedata.assistant.MeasureUnitInfo();
													var oql2 = "select where id='" + FBaseUnit.getId() + "'";
													var accountcol2 = com.kingdee.eas.basedata.assistant.MeasureUnitFactory.getRemoteInstance().getMeasureUnitCollection(oql2);
													if (accountcol2.size() > 0) {
														cur2 = accountcol2.get(0);
														for (var i = 0; i < rowcount; i++) {
															table.getRow(i).getCell("materialNum").setValue(cur);
															table.getRow(i).getCell("materialName").setValue(cur.getName());
															table.getRow(i).getCell("unit").setValue(cur2);
															table.getRow(i).getCell("baseUnit").setValue(cur2); //jerry 给基本计量单位赋值
														}
													}
												}
											}
											var curck = new com.kingdee.eas.basedata.scm.im.inv.WarehouseInfo();
											var oqlck = "select where number='0801'";
											var accountcolck = com.kingdee.eas.basedata.scm.im.inv.WarehouseFactory.getRemoteInstance().getWarehouseCollection(oqlck);
											if (accountcolck.size() > 0) {
												curck = accountcolck.get(0);
												for (var i = 0; i < rowcount; i++) {
													table.getRow(i).getCell("wareHouse").setValue(curck);
												}
											}
										}
									}
								}
							}
						}
						if (fname_l2 != "采购类合同" || htlxname == "工程设备采购类合同") {

							//com.kingdee.eas.util.client.MsgBox.showInfo(table.getColumn("qty").getKey());
							for (var i = 0; i < rowcount; i++) {
								table.getRow(i).getCell("taxPrice").setValue(new java.math.BigDecimal(1));
							}
						} else {
							for (var i = 0; i < rowcount; i++) {
								table.getRow(i).getCell("taxPrice").setValue(new java.math.BigDecimal(0));
								table.getRow(i).getCell("price").setValue(new java.math.BigDecimal(0));
								table.getRow(i).getCell("amount").setValue(new java.math.BigDecimal(0));
								table.getRow(i).getCell("taxAmount").setValue(new java.math.BigDecimal(0));
								table.getRow(i).getCell("tax").setValue(new java.math.BigDecimal(0));
								table.getRow(i).getCell("localAmount").setValue(new java.math.BigDecimal(0));
								table.getRow(i).getCell("localTax").setValue(new java.math.BigDecimal(0));
								table.getRow(i).getCell("localTaxAmount").setValue(new java.math.BigDecimal(0));
							}
						}
						if (fname_l2 == "服务费用类合同") {
							;
							/*var sql = "select * from T_BD_Material where fnumber='9902'";
														
														var sqlExecutor=new com.kingdee.bos.dao.query.SQLExecutor(sql);
														var rowSet=sqlExecutor.executeSQL();
														if(rowSet.next()){
															var fid=rowSet.getString("fid");
															var fnumber=rowSet.getString("fnumber");
															var fname_l2=rowSet.getString("fname_l2");
															var cur = new com.kingdee.eas.basedata.master.material.MaterialInfo();
															cur.setId(BOSUuid.read(fid));
															cur.setNumber(fnumber);
															cur.setName(fname_l2);
															var FBaseUnit=rowSet.getString("FBaseUnit");
															if(FBaseUnit!=null){
																var sql2 = "select * from T_BD_MeasureUnit where fid='"+FBaseUnit+"'";
																var sqlExecutor2=new com.kingdee.bos.dao.query.SQLExecutor(sql2);
																var rowSet2=sqlExecutor2.executeSQL();
																if(rowSet2.next()){
																	var fid2=rowSet2.getString("fid");
																	var fnumber2=rowSet2.getString("fnumber");
																	var fname_l22=rowSet2.getString("fname_l2");
																	var cur2 = new com.kingdee.eas.basedata.assistant.MeasureUnitInfo();
																	cur2.setId(BOSUuid.read(fid2));
																	cur2.setNumber(fnumber2);
																	cur2.setName(fname_l22);
																	for(var i=0;i<rowcount;i++){
																		table.getRow(i).getCell("materialNum").setValue(cur);
																		table.getRow(i).getCell("materialName").setValue(fname_l2);
																		table.getRow(i).getCell("unit").setValue(cur2);
																		}
																	}
																
																}								
															
															}*/
							//jerry 使用全对象赋值
							//var oql ="select where number='9902'";

							var filterInfo = new com.kingdee.bos.metadata.entity.FilterInfo();
							var entityViewInfo = new com.kingdee.bos.metadata.entity.EntityViewInfo();
							var filterItemInfo = new com.kingdee.bos.metadata.entity.FilterItemInfo("number", "9902", com.kingdee.bos.metadata.query.util.CompareType.EQUALS);
							filterInfo.getFilterItems().add(filterItemInfo);
							entityViewInfo.setFilter(filterInfo);

							var accountcol = com.kingdee.eas.basedata.master.material.MaterialFactory.getRemoteInstance().getMaterialCollection(entityViewInfo);
							var cur = new com.kingdee.eas.basedata.master.material.MaterialInfo();
							if (accountcol.size() > 0) {
								cur = accountcol.get(0);
								var FBaseUnit = cur.getBaseUnit();
								if (FBaseUnit != null) {
									var cur2 = new com.kingdee.eas.basedata.assistant.MeasureUnitInfo();
									var oql2 = "select where id='" + FBaseUnit.getId() + "'";
									var accountcol2 = com.kingdee.eas.basedata.assistant.MeasureUnitFactory.getRemoteInstance().getMeasureUnitCollection(oql2);
									if (accountcol2.size() > 0) {
										cur2 = accountcol2.get(0);
										for (var i = 0; i < rowcount; i++) {
											table.getRow(i).getCell("materialNum").setValue(cur);
											table.getRow(i).getCell("materialName").setValue(cur.getName());
											table.getRow(i).getCell("unit").setValue(cur2);
											table.getRow(i).getCell("baseUnit").setValue(cur2); //jerry 给基本计量单位赋值
										}
									}
								}
							}
						}
						//com.kingdee.eas.util.client.MsgBox.showInfo(CGXZ);
					}
				}
			}
		}
		if (methodName == "equals") {
			return this == e;
		}
	});
	pluginCtx.getKDCheckBox("chkkDCheckBox").addActionListener(function (e) {
		var selected = pluginCtx.getKDCheckBox("chkkDCheckBox").getSelected();
		var table = pluginCtx.getKDTable("kdtEntries");
		//com.kingdee.eas.util.client.MsgBox.showInfo(selected);
		if (selected == 32) {
			table.getColumn("preReceived").getStyleAttributes().setLocked(false);
			table.getColumn("totalInvoicedAmount").getStyleAttributes().setLocked(false);
			table.getColumn("totalPaidAmount").getStyleAttributes().setLocked(false);
		} else {
			table.getColumn("preReceived").getStyleAttributes().setLocked(true);
			table.getColumn("totalInvoicedAmount").getStyleAttributes().setLocked(true);
			table.getColumn("totalPaidAmount").getStyleAttributes().setLocked(true);
		}
	});
	pluginCtx.getKDTextArea("txtkDTextArea").addFocusListener(function (event, methodName) {
		//com.kingdee.eas.util.client.MsgBox.showInfo(methodName);
		if (methodName == "focusGained") {
			var htgjnr = pluginCtx.getKDTextArea("txtkDTextArea").getText();
			if (htgjnr == "请输入合同主要内容，包括但不限于：合同标的信息，合同执行期限等") {
				pluginCtx.getKDTextArea("txtkDTextArea").setText("");
			}
		}
		if (methodName == "focusLost") {
			var htgjnr = pluginCtx.getKDTextArea("txtkDTextArea").getText();
			if (htgjnr == "") {
				pluginCtx.getKDTextArea("txtkDTextArea").setText("请输入合同主要内容，包括但不限于：合同标的信息，合同执行期限等");
			}
		}
	});
	pluginCtx.getKDTextArea("txtkDTextArea1").addFocusListener(function (event, methodName) {
		if (methodName == "focusGained") {
			var fkjh = pluginCtx.getKDTextArea("txtkDTextArea1").getText();
			if (fkjh == "请输入合同合同付款条件") {
				pluginCtx.getKDTextArea("txtkDTextArea1").setText("");
			}
		}
		if (methodName == "focusLost") {
			var fkjh = pluginCtx.getKDTextArea("txtkDTextArea1").getText();
			if (fkjh == "") {
				pluginCtx.getKDTextArea("txtkDTextArea1").setText("请输入合同合同付款条件");
			}
		}
	});
	pluginCtx.getKDFormattedTextField("txtkDNumberTextField2").addDataChangeListener(function (e, methodName) {
		if (methodName == "dataChanged") {
			var txtkDNumberTextField2 = pluginCtx.getKDFormattedTextField("txtkDNumberTextField2").getValue();

			if (txtkDNumberTextField2 < 0 || txtkDNumberTextField2 > 100) {
				com.kingdee.eas.util.client.MsgBox.showInfo("结算提示比例要在0-100之间");
				pluginCtx.getKDFormattedTextField("txtkDNumberTextField2").setValue(0);
				return;
			}
		}
		if (methodName == "equals") {
			return this == e;
		}
	});
	pluginCtx.getKDFormattedTextField("txtPrepayment").addFocusListener(function (e, methodName) {
		if (methodName == "focusGained") {
			pluginCtx.getKDFormattedTextField("txtPrepayment").setEnabled(false);
		}
	});
	pluginCtx.getKDCheckBox("chkkDCheckBox1").addActionListener(function (e) {
		var selected = pluginCtx.getKDCheckBox("chkkDCheckBox1").getSelected();
		var table = pluginCtx.getKDTable("kdtEntries");
		//com.kingdee.eas.util.client.MsgBox.showInfo(selected);
		if (selected == 32) {
			table.getColumn("xiangmubianma").getStyleAttributes().setHided(false);
			table.getColumn("xiangmumingcheng").getStyleAttributes().setHided(false);
		} else {
			table.getColumn("xiangmubianma").getStyleAttributes().setHided(true);
			table.getColumn("xiangmumingcheng").getStyleAttributes().setHided(true);
		}
	});
}