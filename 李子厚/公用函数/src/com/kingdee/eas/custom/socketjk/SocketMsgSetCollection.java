package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SocketMsgSetCollection extends AbstractObjectCollection 
{
    public SocketMsgSetCollection()
    {
        super(SocketMsgSetInfo.class);
    }
    public boolean add(SocketMsgSetInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SocketMsgSetCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SocketMsgSetInfo item)
    {
        return removeObject(item);
    }
    public SocketMsgSetInfo get(int index)
    {
        return(SocketMsgSetInfo)getObject(index);
    }
    public SocketMsgSetInfo get(Object key)
    {
        return(SocketMsgSetInfo)getObject(key);
    }
    public void set(int index, SocketMsgSetInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SocketMsgSetInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SocketMsgSetInfo item)
    {
        return super.indexOf(item);
    }
}