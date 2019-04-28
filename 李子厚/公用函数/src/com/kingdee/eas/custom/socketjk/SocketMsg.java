package com.kingdee.eas.custom.socketjk;

import java.util.Date;

public class SocketMsg {

	private String sendMsg;
	private String returnMsg;
	private Date SendTime;
	private int  SendTimes;
	/**
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(Date sendTime) {
		SendTime = sendTime;
	}
	/**
	 * @return the sendTime
	 */
	public Date getSendTime() {
		return SendTime;
	}
	
	/**
	 * @param sendTimes the sendTimes to set
	 */
	public void setSendTimes(int sendTimes) {
		SendTimes = sendTimes;
	}
	/**
	 * @return the sendTimes
	 */
	public int getSendTimes() {
		return SendTimes;
	}
	/**
	 * @param sendMsg the sendMsg to set
	 */
	public void setSendMsg(String sendMsg) {
		this.sendMsg = sendMsg;
	}
	/**
	 * @return the sendMsg
	 */
	public String getSendMsg() {
		return sendMsg;
	}
	/**
	 * @param returnMsg the returnMsg to set
	 */
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	/**
	 * @return the returnMsg
	 */
	public String getReturnMsg() {
		return returnMsg;
	}
	
}
