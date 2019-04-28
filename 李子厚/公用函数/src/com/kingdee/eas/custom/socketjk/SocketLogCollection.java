package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SocketLogCollection extends AbstractObjectCollection 
{
    public SocketLogCollection()
    {
        super(SocketLogInfo.class);
    }
    public boolean add(SocketLogInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SocketLogCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SocketLogInfo item)
    {
        return removeObject(item);
    }
    public SocketLogInfo get(int index)
    {
        return(SocketLogInfo)getObject(index);
    }
    public SocketLogInfo get(Object key)
    {
        return(SocketLogInfo)getObject(key);
    }
    public void set(int index, SocketLogInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SocketLogInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SocketLogInfo item)
    {
        return super.indexOf(item);
    }
}