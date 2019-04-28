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
	 * @param �����Ƿ�ɹ�
	 */
	public void setResult(boolean result) {
		this.result = result;
	}
	/**
	 * @return �Ƿ�ɹ�
	 */
	public boolean isResult() {
		return result;
	}
	/**
	 * @param ���ô�����Ϣ
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	/**
	 * @return ������Ϣ
	 */
	public String getErrMsg() {
		return errMsg;
	}
	/**
	 * @param ���÷���Socket����
	 */
	public void setReturnSocketMsg(String returnSocketMsg) {
		this.returnSocketMsg = returnSocketMsg;
	}
	/**
	 * @return ����Socket����
	 */
	public String getReturnSocketMsg() {
		return returnSocketMsg;
	}
	
}
