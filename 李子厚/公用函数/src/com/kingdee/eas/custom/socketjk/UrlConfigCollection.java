package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class UrlConfigCollection extends AbstractObjectCollection 
{
    public UrlConfigCollection()
    {
        super(UrlConfigInfo.class);
    }
    public boolean add(UrlConfigInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(UrlConfigCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(UrlConfigInfo item)
    {
        return removeObject(item);
    }
    public UrlConfigInfo get(int index)
    {
        return(UrlConfigInfo)getObject(index);
    }
    public UrlConfigInfo get(Object key)
    {
        return(UrlConfigInfo)getObject(key);
    }
    public void set(int index, UrlConfigInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(UrlConfigInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(UrlConfigInfo item)
    {
        return super.indexOf(item);
    }
}