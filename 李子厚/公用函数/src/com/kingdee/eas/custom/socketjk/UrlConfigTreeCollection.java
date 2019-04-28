package com.kingdee.eas.custom.socketjk;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class UrlConfigTreeCollection extends AbstractObjectCollection 
{
    public UrlConfigTreeCollection()
    {
        super(UrlConfigTreeInfo.class);
    }
    public boolean add(UrlConfigTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(UrlConfigTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(UrlConfigTreeInfo item)
    {
        return removeObject(item);
    }
    public UrlConfigTreeInfo get(int index)
    {
        return(UrlConfigTreeInfo)getObject(index);
    }
    public UrlConfigTreeInfo get(Object key)
    {
        return(UrlConfigTreeInfo)getObject(key);
    }
    public void set(int index, UrlConfigTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(UrlConfigTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(UrlConfigTreeInfo item)
    {
        return super.indexOf(item);
    }
}