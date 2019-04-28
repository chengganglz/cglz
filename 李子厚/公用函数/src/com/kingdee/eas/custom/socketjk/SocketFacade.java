package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.Context;
import com.kingdee.eas.custom.socketjk.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import java.math.BigDecimal;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

public class SocketFacade extends AbstractBizCtrl implements ISocketFacade
{
    public SocketFacade()
    {
        super();
        registerInterface(ISocketFacade.class, this);
    }
    public SocketFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISocketFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("908D6A7C");
    }
    private SocketFacadeController getController() throws BOSException
    {
        return (SocketFacadeController)getBizController();
    }
    /**
     *����socket�����߳�-User defined method
     */
    public void RunService() throws BOSException
    {
        try {
            getController().RunService(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ֹͣ����-User defined method
     */
    public void StopService() throws BOSException
    {
        try {
            getController().StopService(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *1-User defined method
     *@return
     */
    public SocketServerStatusCollection getServerStatusList() throws BOSException
    {
        try {
            return getController().getServerStatusList(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ִ�нű�-User defined method
     *@return
     */
    public boolean executeScript() throws BOSException
    {
        try {
            return getController().executeScript(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��������ִ�нű�-User defined method
     */
    public void channelRead() throws BOSException
    {
        try {
            getController().channelRead(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Socket��־-User defined method
     *@param urlConfig ������Դ
     *@param socketMsg ��������
     *@param losMsg ��־��Ϣ
     */
    public void InsSocketLog(UrlConfigInfo urlConfig, String socketMsg, String losMsg) throws BOSException
    {
        try {
            getController().InsSocketLog(getContext(), urlConfig, socketMsg, losMsg);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���͵�������-User defined method
     *@param socketMsgSetNo �������õ��ݱ��
     *@param billId ����ID
     *@return
     */
    public SocketResult sendSocketMsg(String socketMsgSetNo, String billId) throws BOSException
    {
        try {
            return getController().sendSocketMsg(getContext(), socketMsgSetNo, billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *[description]�����ƺţ���ȣ���� �ж��Ƿ�������ϣ�������������{BR}���ϱ���������ϱ�ʶ�롢���Ϸ����롢�ƺ��롢������Ĳ��ֹ���14λ��ɣ��޼����־����ϸ˵�����£�{BR} ��һ�Σ����ϱ�ʶ�룬1λ�ֽڣ���5��ʶ��Ʒ��{BR} �ڶ��Σ����Ϸ����룬2λ�ֽڣ�09������ϴ�壬10������ϴ��ƽ�壬17�������ư壬18�������ƿ�ƽ�壬19�����п�壬20�����п��ƽ�塣{BR} �����Σ��ƺ��룬4λ�ֽڣ���ϸ������MES���ƺ���ˮ��Ϊ׼��������MES���ƺ���ˮ�Ų���4λ��ǰ��0���㡣{BR} ���ĶΣ�����룬7λ�ֽڣ������Ϻ��+��ȱ�ʶ���������Ϻ��3λ�����Ϻ��*100�������Ͽ��4λ��������Ӧλ����ǰ��0���㡣{BR} ���磺50900010801200��������MES�ƺ���ˮ��Ϊ0001������ͺ�Ϊ0.80*1200����ϴ�塣{BR}�������������淶{BR} �������ư������ࡢ�ƺš���������֣��ԡ�_���������ϸ˵�����£�{BR}��һ�Σ��������ƣ�������ϴ�塢��ϴ��ƽ�塢���ư塢���ƿ�ƽ�塢��п�塢��п��ƽ�����࣬�������ֽڳ��ȣ�{BR}�ڶ��Σ��ƺ����ƣ���ϸ������MES���ƺ�����Ϊ׼���������ֽڳ��ȣ�{BR}�����Σ���������Ϻ��*��ȱ�ʶ���������Ϻ��3λ�����Ϻ��*100�������Ͽ��4λ��������Ӧλ����ǰ��0���㣬8λ�ֽڡ�{BR}���磺��ϴ��_C600QK-CM_0.80*1200�����ƺ�ΪC600QK-CM������ͺ�Ϊ0.80*1200����ϴ�塣[%description][invokedemo][%invokedemo][returndesc]����info[%returndesc]-User defined method
     *@param zzbm ��˾����
     *@param bs ���ϱ�ʶ�룬1λ�ֽڣ���5��ʶ��Ʒ
     *@param fl ���Ϸ����룬2λ�ֽڣ�09������ϴ�壬10������ϴ��ƽ�壬17�������ư壬18�������ƿ�ƽ�壬19�����п�壬20�����п��ƽ�塣
     *@param ph �ƺ��룬4λ�ֽڣ���ϸ������MES���ƺ���ˮ��Ϊ׼��������MES���ƺ���ˮ�Ų���4λ��ǰ��0���㡣
     *@param phmc �ƺ�����
     *@param kd ����룬7λ�ֽڣ������Ϻ��+��ȱ�ʶ���������Ϻ��3λ�����Ϻ��*100�������Ͽ��4λ��������Ӧλ����ǰ��0���㡣
     *@param hd ���
     *@param dwmc ��λ����
     *@param dwzbm ��λ�����
     *@param isBatchNo ��������
     *@return
     */
    public MaterialInfo getMaterialInfo(String zzbm, String bs, String fl, String ph, String phmc, BigDecimal kd, BigDecimal hd, String dwmc, String dwzbm, boolean isBatchNo) throws BOSException, EASBizException
    {
        try {
            return getController().getMaterialInfo(getContext(), zzbm, bs, fl, ph, phmc, kd, hd, dwmc, dwzbm, isBatchNo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}