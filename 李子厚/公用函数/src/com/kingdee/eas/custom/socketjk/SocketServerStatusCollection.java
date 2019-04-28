package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SocketServerStatusCollection extends AbstractObjectCollection 
{
    public SocketServerStatusCollection()
    {
        super(SocketServerStatusInfo.class);
    }
    public boolean add(SocketServerStatusInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SocketServerStatusCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SocketServerStatusInfo item)
    {
        return removeObject(item);
    }
    public SocketServerStatusInfo get(int index)
    {
        return(SocketServerStatusInfo)getObject(index);
    }
    public SocketServerStatusInfo get(Object key)
    {
        return(SocketServerStatusInfo)getObject(key);
    }
    public void set(int index, SocketServerStatusInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SocketServerStatusInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SocketServerStatusInfo item)
    {
        return super.indexOf(item);
    }
}