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
     *建立socket链接线程-User defined method
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
     *停止服务-User defined method
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
     *执行脚本-User defined method
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
     *接收数据执行脚本-User defined method
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
     *插入Socket日志-User defined method
     *@param urlConfig 报文来源
     *@param socketMsg 报文内容
     *@param losMsg 日志消息
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
     *发送电文请求-User defined method
     *@param socketMsgSetNo 电文设置单据编号
     *@param billId 单据ID
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
     *[description]根据牌号，厚度，宽度 判断是否存在物料，不存在则新增{BR}物料编码包括物料标识码、物料分类码、牌号码、规格码四部分共计14位组成，无间隔标志，详细说明如下：{BR} 第一段：物料标识码，1位字节，以5标识成品。{BR} 第二段：物料分类码，2位字节，09代表酸洗板，10代表酸洗开平板，17代表轧制板，18代表轧制开平板，19代表镀锌板，20代表镀锌开平板。{BR} 第三段：牌号码，4位字节，详细以冷轧MES中牌号流水号为准，如冷轧MES中牌号流水号不足4位则前置0补足。{BR} 第四段：规格码，7位字节，以物料厚度+宽度标识，其中物料厚度3位（物料厚度*100），物料宽度4位，不足相应位数的前置0补足。{BR} 例如：50900010801200代表冷轧MES牌号流水号为0001，规格型号为0.80*1200的酸洗板。{BR}三、物料命名规范{BR} 物料名称包括分类、牌号、规格三部分，以“_”间隔，详细说明如下：{BR}第一段：分类名称，包括酸洗板、酸洗开平板、轧制板、轧制开平板、镀锌板、镀锌开平板六类，不限制字节长度；{BR}第二段：牌号名称，详细以冷轧MES中牌号名称为准，不限制字节长度；{BR}第三段：规格，以物料厚度*宽度标识，其中物料厚度3位（物料厚度*100），物料宽度4位，不足相应位数的前置0补足，8位字节。{BR}例如：酸洗板_C600QK-CM_0.80*1200代表牌号为C600QK-CM，规格型号为0.80*1200的酸洗板。[%description][invokedemo][%invokedemo][returndesc]物料info[%returndesc]-User defined method
     *@param zzbm 公司编码
     *@param bs 物料标识码，1位字节，以5标识成品
     *@param fl 物料分类码，2位字节，09代表酸洗板，10代表酸洗开平板，17代表轧制板，18代表轧制开平板，19代表镀锌板，20代表镀锌开平板。
     *@param ph 牌号码，4位字节，详细以冷轧MES中牌号流水号为准，如冷轧MES中牌号流水号不足4位则前置0补足。
     *@param phmc 牌号名称
     *@param kd 规格码，7位字节，以物料厚度+宽度标识，其中物料厚度3位（物料厚度*100），物料宽度4位，不足相应位数的前置0补足。
     *@param hd 厚度
     *@param dwmc 单位名称
     *@param dwzbm 单位组编码
     *@param isBatchNo 启用批次
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