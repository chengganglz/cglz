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
public abstract class AbstractServerStatusViewHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleStartServer(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleStartServer(request,response,context);
	}
	protected void _handleStartServer(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleStopServer(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleStopServer(request,response,context);
	}
	protected void _handleStopServer(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleClientTest(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleClientTest(request,response,context);
	}
	protected void _handleClientTest(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleConnectServer(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleConnectServer(request,response,context);
	}
	protected void _handleConnectServer(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleRefresh(request,response,context);
	}
	protected void _handleRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}