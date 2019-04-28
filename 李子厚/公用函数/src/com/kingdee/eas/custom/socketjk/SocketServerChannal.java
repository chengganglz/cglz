package com.kingdee.eas.custom.socketjk;

import io.netty.channel.socket.SocketChannel;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SocketServerChannal {

	private SocketChannel clientChannal;
	private Date updateTime;
	private long CheckTime;
	private Timer timer;
	/**
	 * 
	 * @param time  最后收到报文时间
	 * @param ch	通道
	 * @param ChkTime 检测时间间隔
	 * @param isHeart 是否启用心跳电文检测
	 */
	public SocketServerChannal(Date time,SocketChannel ch,long ChkTime,boolean isHeart)
	{
		clientChannal=ch;
		updateTime=time;
		CheckTime=ChkTime;
		timer = new Timer();
		if (isHeart) {
	    	timer.schedule(new TimerTask() {
	    	        public void run() {
	    	        	Calendar cal = Calendar.getInstance(); 
                    	Date date = cal.getTime();
                    	if ((date.getTime()-updateTime.getTime())>=CheckTime){
                    		clientChannal.close();
                    	}
	    	        }
	    	}, ChkTime , ChkTime);
		}
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param clientChannal the clientChannal to set
	 */
	public void setClientChannal(SocketChannel clientChannal) {
		this.clientChannal = clientChannal;
	}
	/**
	 * @return the clientChannal
	 */
	public SocketChannel getClientChannal() {
		return clientChannal;
	}
	
}
