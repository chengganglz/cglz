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
	 * ��¼��ǰ�����ļ����б�
	 */
	public static List<LocalSocketServer> LocalServerList= new ArrayList<LocalSocketServer>();
	/**
	 * ��¼��ǰ������Զ�̷��������б�
	 */
	public  static List<RemoteSocketClient> RemoteClientList=new ArrayList<RemoteSocketClient>(); 
	

	/**
	 * KDTable ������
	 * @param table KDtable
	 * @param colKey  ��Key
	 * @param colName  �б�����
	 * @param width  �п�
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
	 * ����ں�
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
