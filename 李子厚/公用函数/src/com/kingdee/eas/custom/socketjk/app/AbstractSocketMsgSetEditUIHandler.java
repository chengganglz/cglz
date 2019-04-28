/**
 * output package name
 */
package com.kingdee.eas.custom.socketjk.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractSocketMsgSetEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleSelectEASBiz(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleSelectEASBiz(request,response,context);
	}
	protected void _handleSelectEASBiz(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}