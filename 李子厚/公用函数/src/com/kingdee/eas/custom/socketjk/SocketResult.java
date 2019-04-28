package com.kingdee.eas.custom.socketjk;

public class SocketResult implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2873515088600666373L;
	private boolean result;
	private String errMsg;
	private String returnSocketMsg;
	
	public SocketResult()
	{
		result=false;
		errMsg="";
		returnSocketMsg="";
	}
	/**
	 * @param 设置是否成功
	 */
	public void setResult(boolean result) {
		this.result = result;
	}
	/**
	 * @return 是否成功
	 */
	public boolean isResult() {
		return result;
	}
	/**
	 * @param 设置错误消息
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	/**
	 * @return 错误消息
	 */
	public String getErrMsg() {
		return errMsg;
	}
	/**
	 * @param 设置返回Socket电文
	 */
	public void setReturnSocketMsg(String returnSocketMsg) {
		this.returnSocketMsg = returnSocketMsg;
	}
	/**
	 * @return 返回Socket电文
	 */
	public String getReturnSocketMsg() {
		return returnSocketMsg;
	}
	
}
