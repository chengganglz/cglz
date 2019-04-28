package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SocketMsgSetEntryInCollection extends AbstractObjectCollection 
{
    public SocketMsgSetEntryInCollection()
    {
        super(SocketMsgSetEntryInInfo.class);
    }
    public boolean add(SocketMsgSetEntryInInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SocketMsgSetEntryInCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SocketMsgSetEntryInInfo item)
    {
        return removeObject(item);
    }
    public SocketMsgSetEntryInInfo get(int index)
    {
        return(SocketMsgSetEntryInInfo)getObject(index);
    }
    public SocketMsgSetEntryInInfo get(Object key)
    {
        return(SocketMsgSetEntryInInfo)getObject(key);
    }
    public void set(int index, SocketMsgSetEntryInInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SocketMsgSetEntryInInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SocketMsgSetEntryInInfo item)
    {
        return super.indexOf(item);
    }
}