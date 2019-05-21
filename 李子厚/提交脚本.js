var easNames = JavaImporter();
easNames.importPackage(Packages.com.kingdee.eas.util);
easNames.importPackage(Packages.com.kingdee.eas.util.client);
easNames.importPackage(Packages.com.kingdee.bos.dao.query);
with(easNames){
    var kehu=pluginCtx.getKDBizPromptBox("prmtKH").getValue();
    if(kehu==null||"".equals(kehu)){
        MsgBox.showWarning("请录入客户!");
		SysUtil.abort();
    }
    var FTBZ=pluginCtx.getKDComboBox("FTBZ").getSelectedItem();
    if(FTBZ==null){
        MsgBox.showWarning("请录入分摊标准!");
		SysUtil.abort();
    }
    var table=pluginCtx.getKDTable("kdtEntrys");
    var assTable=pluginCtx.getKDTable("kdtAssEntrys");
    var tableRowCount=table.getRowCount();
    if(tableRowCount<=0){
        MsgBox.showWarning("请选择借贷项调整单!");
		SysUtil.abort();
    }
    var assTableRowCount=assTable.getRowCount();
    if(assTableRowCount<=0){
        MsgBox.showWarning("请选择销售出库单!");
		SysUtil.abort();
    }
    for(var i=0;i<tableRowCount;i++){
        var index=i+1;
        var tableRow=table.getRow(i);
        var jdxtzddjbmCell=tableRow.getCell("jdxtzddjbm");
        if(jdxtzddjbmCell!=null){
            var jdxtzddjbm=jdxtzddjbmCell.getValue();
            if(jdxtzddjbm==null||"".equals(jdxtzddjbm)){
                MsgBox.showWarning("请重新选择借贷项调整单，第"+index+"行借贷项调整单单据编码不能为空!");
		        SysUtil.abort();
            }
        }
        var jdfxCell=tableRow.getCell("jdfx");
        if(jdfxCell!=null){
            var jdfx=jdfxCell.getValue();
            if(jdfx==null){
                MsgBox.showWarning("请重新选择借贷项调整单，第"+index+"行借贷方向不能为空!");
		        SysUtil.abort();
            }
        }
        var jdyyCell=tableRow.getCell("jdyy");
        if(jdyyCell!=null){
            var jdyy=jdyyCell.getValue();
            if(jdyy==null||"".equals(jdyy)){
                MsgBox.showWarning("请重新选择借贷项调整单，第"+index+"行借贷原因不能为空!");
		        SysUtil.abort();
            }
        }
        var zjeCell=tableRow.getCell("zje");
        if(zjeCell!=null){
            var zje=zjeCell.getValue();
            if(zje==null||"".equals(zje)){
                MsgBox.showWarning("请重新选择借贷项调整单，第"+index+"行总金额不能为空!");
		        SysUtil.abort();
            }else{
                var zjeBigDecimal=new java.math.BigDecimal(zje);
                if(zjeBigDecimal.compareTo(java.math.BigDecimal.ZERO)==0){
                    MsgBox.showWarning("请重新选择借贷项调整单，第"+index+"行总金额不能为零!");
		            SysUtil.abort();
                }
            }
        }
        var kftjeCell=tableRow.getCell("kftje");
        if(kftjeCell!=null){
            var kftje=kftjeCell.getValue();
            if(kftje==null||"".equals(kftje)){
                MsgBox.showWarning("借贷项调整单分录第"+index+"行可分摊金额不能为空!");
		        SysUtil.abort();
            }else{
                var kftjeBigDecimal=new java.math.BigDecimal(kftje);
                if(kftjeBigDecimal.compareTo(java.math.BigDecimal.ZERO)==0){
                    MsgBox.showWarning("借贷项调整单分录第"+index+"行可分摊金额不能为零!");
		            SysUtil.abort();
                }
            }
        }
        var bcftjeCell=tableRow.getCell("bcftje");
        if(bcftjeCell!=null){
            var bcftje=bcftjeCell.getValue();
            if(bcftje==null||"".equals(bcftje)){
                MsgBox.showWarning("借贷项调整单分录第"+index+"行本次分摊金额不能为空!");
		        SysUtil.abort();
            }else{
                var bcftjeBigDecimal=new java.math.BigDecimal(bcftje);
                if(bcftjeBigDecimal.compareTo(java.math.BigDecimal.ZERO)==0){
                    MsgBox.showWarning("借贷项调整单分录第"+index+"行本次分摊金额不能为零!");
		            SysUtil.abort();
                }
            }
        }
        var kftje=kftjeCell.getValue();
        var bcftje=bcftjeCell.getValue();
        var kftjeBigDecimal=new java.math.BigDecimal(kftje);
        var bcftjeBigDecimal=new java.math.BigDecimal(bcftje);
        if(kftjeBigDecimal.compareTo(bcftjeBigDecimal)<0){
            MsgBox.showWarning("借贷项调整单分录第"+index+"行本次分摊金额不能大于可分摊金额!");
		    SysUtil.abort();
        }
    }
    for(var j=0;j<assTableRowCount;j++){
        var index=j+1;
        var tableRow=assTable.getRow(i);
        var ckdbmCell=tableRow.getCell("ckdbm");
        if(ckdbmCell!=null){
            var ckdbm=ckdbmCell.getValue();
            if(ckdbm==null||"".equals(ckdbm)){
                MsgBox.showWarning("请重新选择销售出库单，第"+index+"行出库单编码不能为空!");
		        SysUtil.abort();
            }
        }
        var ckdwlCell=tableRow.getCell("ckdwl");
        if(ckdwlCell!=null){
            var ckdwl=ckdwlCell.getValue();
            if(ckdwl==null||"".equals(ckdwl)){
                MsgBox.showWarning("请重新选择销售出库单，第"+index+"行物料不能为空!");
		        SysUtil.abort();
            }
        }
        var ftqdjCell=tableRow.getCell("ftqdj");
        if(ftqdjCell!=null){
            var ftqdj=ftqdjCell.getValue();
            if(ftqdj==null||"".equals(ftqdj)){
                MsgBox.showWarning("请重新选择销售出库单，分录第"+index+"行单价不能为空!");
		        SysUtil.abort();
            }else{
                var ftqdjBigDecimal=new java.math.BigDecimal(ftqdj);
                if(ftqdjBigDecimal.compareTo(java.math.BigDecimal.ZERO)==0){
                    MsgBox.showWarning("请重新选择销售出库单，分录第"+index+"行单价不能为零!");
		            SysUtil.abort();
                }
            }
        }
        var ftqhsdjCell=tableRow.getCell("ftqhsdj");
        if(ftqhsdjCell!=null){
            var ftqhsdj=ftqhsdjCell.getValue();
            if(ftqhsdj==null||"".equals(ftqhsdj)){
                MsgBox.showWarning("请重新选择销售出库单，分录第"+index+"行含税单价不能为空!");
		        SysUtil.abort();
            }else{
                var ftqhsdjBigDecimal=new java.math.BigDecimal(ftqhsdj);
                if(ftqhsdjBigDecimal.compareTo(java.math.BigDecimal.ZERO)==0){
                    MsgBox.showWarning("请重新选择销售出库单，分录第"+index+"行含税单价不能为零!");
		            SysUtil.abort();
                }
            }
        }
        var slCell=tableRow.getCell("sl");
        if(slCell!=null){
            var sl=slCell.getValue();
            if(sl==null||"".equals(sl)){
                MsgBox.showWarning("请重新选择销售出库单，分录第"+index+"行含税单价不能为空!");
		        SysUtil.abort();
            }else{
                var slBigDecimal=new java.math.BigDecimal(sl);
                if(slBigDecimal.compareTo(java.math.BigDecimal.ZERO)==0){
                    MsgBox.showWarning("请重新选择销售出库单，分录第"+index+"行含税单价不能为零!");
		            SysUtil.abort();
                }
            }
        }
        var ftqbhsjineCell=tableRow.getCell("ftqbhsjine");
        if(ftqbhsjineCell!=null){
            var ftqbhsjine=ftqbhsjineCell.getValue();
            if(ftqbhsjine==null||"".equals(ftqbhsjine)){
                MsgBox.showWarning("请重新选择销售出库单，分录第"+index+"行不含税金额不能为空!");
		        SysUtil.abort();
            }else{
                var ftqbhsjineBigDecimal=new java.math.BigDecimal(ftqbhsjine);
                if(ftqbhsjineBigDecimal.compareTo(java.math.BigDecimal.ZERO)==0){
                    MsgBox.showWarning("请重新选择销售出库单，分录第"+index+"行不含税金额不能为零!");
		            SysUtil.abort();
                }
            }
        }
        var ftqjeCell=tableRow.getCell("ftqje");
        if(ftqjeCell!=null){
            var ftqje=ftqjeCell.getValue();
            if(ftqje==null||"".equals(ftqje)){
                MsgBox.showWarning("请重新选择销售出库单，分录第"+index+"行金额不能为空!");
		        SysUtil.abort();
            }else{
                var ftqjeBigDecimal=new java.math.BigDecimal(ftqje);
                if(ftqjeBigDecimal.compareTo(java.math.BigDecimal.ZERO)==0){
                    MsgBox.showWarning("请重新选择销售出库单，分录第"+index+"行金额不能为零!");
		            SysUtil.abort();
                }
            }
        }
        var ftbzValue=pluginCtx.getKDComboBox("FTBZ").getSelectedItem().getValue();
        if("3".equals(ftbzValue)){
            var blCell=tableRow.getCell("bl");
            if(blCell!=null){
                var bl=blCell.getValue();
                if(bl==null||"".equals(bl)){
                    MsgBox.showWarning("请重新选择销售出库单，分录第"+index+"行比例不能为空!");
                    SysUtil.abort();
                }else{
                    var blBigDecimal=new java.math.BigDecimal(bl);
                    if(blBigDecimal.compareTo(java.math.BigDecimal.ZERO)==0){
                        MsgBox.showWarning("请重新选择销售出库单，分录第"+index+"行比例不能为零!");
                        SysUtil.abort();
                    }
                }
            }
        }
        var fthdjCell=tableRow.getCell("fthdj");
        if(fthdjCell!=null){
            var fthdj=fthdjCell.getValue();
            if(fthdj==null||"".equals(fthdj)){
                MsgBox.showWarning("销售出库单分录第"+index+"行分摊后单价不能为空!");
		        SysUtil.abort();
            }else{
                var fthdjBigDecimal=new java.math.BigDecimal(fthdj);
                if(fthdjBigDecimal.compareTo(java.math.BigDecimal.ZERO)==0){
                    MsgBox.showWarning("销售出库单分录第"+index+"行分摊后单价不能为零!");
		            SysUtil.abort();
                }
            }
        }
        var fthhsdjCell=tableRow.getCell("fthhsdj");
        if(fthhsdjCell!=null){
            var fthhsdj=fthhsdjCell.getValue();
            if(fthhsdj==null||"".equals(fthhsdj)){
                MsgBox.showWarning("销售出库单分录第"+index+"行分摊后含税单价不能为空!");
		        SysUtil.abort();
            }else{
                var fthhsdjBigDecimal=new java.math.BigDecimal(fthhsdj);
                if(fthhsdjBigDecimal.compareTo(java.math.BigDecimal.ZERO)==0){
                    MsgBox.showWarning("销售出库单分录第"+index+"行分摊后含税单价不能为零!");
		            SysUtil.abort();
                }
            }
        }
        var fthbhsjineCell=tableRow.getCell("fthbhsjine");
        if(fthbhsjineCell!=null){
            var fthbhsjine=fthbhsjineCell.getValue();
            if(fthbhsjine==null||"".equals(fthbhsjine)){
                MsgBox.showWarning("销售出库单分录第"+index+"行分摊后不含税金额不能为空!");
		        SysUtil.abort();
            }else{
                var fthbhsjineBigDecimal=new java.math.BigDecimal(fthbhsjine);
                if(fthbhsjineBigDecimal.compareTo(java.math.BigDecimal.ZERO)==0){
                    MsgBox.showWarning("销售出库单分录第"+index+"行分摊后不含税金额不能为零!");
		            SysUtil.abort();
                }
            }
        }
        var fthjeCell=tableRow.getCell("fthje");
        if(fthjeCell!=null){
            var fthje=fthjeCell.getValue();
            if(fthje==null||"".equals(fthje)){
                MsgBox.showWarning("销售出库单分录第"+index+"行分摊后金额不能为空!");
		        SysUtil.abort();
            }else{
                var fthjeBigDecimal=new java.math.BigDecimal(fthje);
                if(fthjeBigDecimal.compareTo(java.math.BigDecimal.ZERO)==0){
                    MsgBox.showWarning("销售出库单分录第"+index+"行分摊后金额不能为零!");
		            SysUtil.abort();
                }
            }
        }
    }

}