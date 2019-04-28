package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SocketMsgSetTreeCollection extends AbstractObjectCollection 
{
    public SocketMsgSetTreeCollection()
    {
        super(SocketMsgSetTreeInfo.class);
    }
    public boolean add(SocketMsgSetTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SocketMsgSetTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SocketMsgSetTreeInfo item)
    {
        return removeObject(item);
    }
    public SocketMsgSetTreeInfo get(int index)
    {
        return(SocketMsgSetTreeInfo)getObject(index);
    }
    public SocketMsgSetTreeInfo get(Object key)
    {
        return(SocketMsgSetTreeInfo)getObject(key);
    }
    public void set(int index, SocketMsgSetTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SocketMsgSetTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SocketMsgSetTreeInfo item)
    {
        return super.indexOf(item);
    }
}