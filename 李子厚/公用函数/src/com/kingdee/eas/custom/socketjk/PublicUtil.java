package com.kingdee.eas.custom.socketjk;

import java.util.ArrayList;
import java.util.List;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;

public  class PublicUtil {
//	public static EventLoopGroup ServerGroup = new NioEventLoopGroup();
//	public static EventLoopGroup ClientGroup = new NioEventLoopGroup();
	/**
	 * 记录当前启动的监听列表
	 */
	public static List<LocalSocketServer> LocalServerList= new ArrayList<LocalSocketServer>();
	/**
	 * 记录当前启动的远程服务连接列表
	 */
	public  static List<RemoteSocketClient> RemoteClientList=new ArrayList<RemoteSocketClient>(); 
	

	/**
	 * KDTable 增加列
	 * @param table KDtable
	 * @param colKey  列Key
	 * @param colName  列标题名
	 * @param width  列宽
	 */
	public static void addColumnToKDTable(KDTable table,String colKey, String colName,int width, boolean visible  ){
		if (table!=null){
			KDTable kt = new KDTable();
			kt =table;
			if(kt.getColumn(colKey)==null){
				IColumn col=kt.addColumn();
				col.setKey(colKey);
				col.getStyleAttributes().setHided(!visible);
			}
			if(kt.getHeadRowCount()<1){
				kt.addHeadRow();
			}
			kt.getHeadRow(0).getCell(colKey).setValue(colName);
			table =kt;
		}
	}
	
	/**
	 * 表格融合
	 * @param table
	 * @param columns
	 */
	public static void setMergeColumn(KDTable table, String columns[])
    {
        table.checkParsed();
        //table.getMergeManager().setMergeMode(KDTMergeManager.FREE_ROW_MERGE);
        table.gerGroupManager().setGroup(true);
        table.getMergeManager().setMergeMode(KDTMergeManager.GROUP_MERGE);
        table.getGroupManager().group();
        for(int i = 0; i < columns.length; i++)
        {
            table.getColumn(columns[i]).setGroup(true);
            table.getColumn(columns[i]).setMergeable(true);
        }
       
    }
}
