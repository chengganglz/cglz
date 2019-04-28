package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.Context;
import com.kingdee.eas.custom.socketjk.app.*;
import com.kingdee.bos.BOSException;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

public class testFacade extends AbstractBizCtrl implements ItestFacade
{
    public testFacade()
    {
        super();
        registerInterface(ItestFacade.class, this);
    }
    public testFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ItestFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("49C3F7FB");
    }
    private testFacadeController getController() throws BOSException
    {
        return (testFacadeController)getBizController();
    }
    /**
     *创建单据-User defined method
     *@param strMsg 出入电文
     */
    public void CreateBill(String strMsg) throws BOSException, EASBizException
    {
        try {
            getController().CreateBill(getContext(), strMsg);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}