package com.kingdee.eas.custom.socketjk;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.util.SysUtil;
/**
 * 本地socket服务类
 * @author xin
 *
 */
public class LocalSocketServer {

	

//	private int ClientCount ;//客户端连接数
	private List<SocketServerChannal> RemoteSocketChannelList =new ArrayList<SocketServerChannal>();
	private ChannelFuture cf;
	private EventLoopGroup group=new NioEventLoopGroup();
	private UrlConfigInfo configInfo;
	private Context ServerCtx;

	/**
	 * 设置参数配置
	 * @param configInfo the configInfo to set
	 */
	public void setConfigInfo(UrlConfigInfo configInfo) {
		this.configInfo = configInfo;
	}
	/**
	 * 获取参数配置
	 * @return the configInfo
	 */
	public UrlConfigInfo getConfigInfo() {
		return configInfo;
	}

	public LocalSocketServer getLocalSocketServer()
	{
		return this;
	}
//	/**
//	 * @param clientCount 设置客户端连接数
//	 */
//	public void setClientCount(int clientCount) {
//		ClientCount = clientCount;
//	}
//	/**
//	 * @return the 获取客户端连接数
//	 */
//	public int getClientCount() {
//		return ClientCount;
//	}
	/**
	 * @param remoteClientList 设置客户端连接列表
	 */
	public void setRemoteSocketChannelList(List<SocketServerChannal> remoteSocketChannelList) {
		RemoteSocketChannelList = remoteSocketChannelList;
	}
	/**
	 * @return the 获取客户端连接列表
	 */
	public List<SocketServerChannal> getRemoteSocketChannelList() {
		return RemoteSocketChannelList;
	}
	//客户端连接列表增加
	public  void addRemoteSocketChannelList( SocketServerChannal client)
	{
		RemoteSocketChannelList.add(client);
	}
	public void removeRemoteSocketChannelList(SocketServerChannal client){
		RemoteSocketChannelList.remove(client);
	}
	

	/**
	 * @param 设置主线程通道
	 */
	public void setCf(ChannelFuture cf) {
		this.cf = cf;
	}
	/**
	 * @return 
	 */
	public ChannelFuture getCf() {
		return cf;
	}
	

	
	
	/**
	 * @param EventLoopGroup 设置线程池
	 */
	public void setGroup(EventLoopGroup group) {
		this.group = group;
	}
	/**
	 * @return the group
	 */
	public EventLoopGroup getGroup() {
		return group;
	}
	
	public void start() throws Exception {
		if(configInfo==null){
			throw new Exception("参数未初始化，不能启动");
		
		}
		
		if(configInfo.getPort()<0){
			throw new Exception("端口设置不合法，不能启动");
		
		}
		
		if (configInfo!=null){
	        EventLoopGroup bossGroup = new NioEventLoopGroup();
	      
	//        EventLoopGroup group = new NioEventLoopGroup();
	        try {
	        	
		            ServerBootstrap sb = new ServerBootstrap();
		            sb.option(ChannelOption.SO_BACKLOG, 1024);
		            sb.group(group, bossGroup) // 绑定线程池
		                    .channel(NioServerSocketChannel.class) // 指定使用的channel
		                    .localAddress(this.configInfo.getPort())// 绑定监听端口
		                    .childHandler(new ChannelInitializer<SocketChannel>() { // 绑定客户端连接时候触发操作
		                    	
		                        @Override
		                        protected void initChannel(SocketChannel ch) throws Exception {	  
		                        	Calendar cal = Calendar.getInstance(); 
		                        	Date date = cal.getTime();
		                        	SocketServerChannal chl=new SocketServerChannal(date, ch,getLocalSocketServer().getConfigInfo().getHeartbeatMsgTime()*2,getLocalSocketServer().getConfigInfo().isIsUseHeartbeatMsg());
		                        	addRemoteSocketChannelList(chl);
//		                            System.out.println("报告");
//		                            System.out.println("信息：有一客户端链接到本服务端");
//		                            System.out.println("IP:" + ch.localAddress().getHostName());
//		                            System.out.println("Port:" + ch.localAddress().getPort());
//		                            System.out.println("报告完毕");
		                           
		                            ch.pipeline().addLast(new StringEncoder(Charset.forName(configInfo.getCharset().getValue().toString())));
		                            ch.pipeline().addLast(new LocalSocketServerHandler(getLocalSocketServer(),ch)); // 客户端触发操作
		                            ch.pipeline().addLast(new ByteArrayEncoder());
		                            
		                        }
		                    });
		            cf = sb.bind().sync(); // 服务器异步创建绑定
		            System.out.println(LocalSocketServer.class + " 启动正在监听： " + cf.channel().localAddress());
	        	
	            if(!PublicUtil.LocalServerList.contains(this))
	            {
	            	synchronized (PublicUtil.LocalServerList) {
	            		PublicUtil.LocalServerList.add(this);
	                }
	            	
	            } 
	//            cf.channel().closeFuture().sync(); // 关闭服务器通道
	//            System.out.println("连接已关闭.."); // 关闭完成
	        } finally {
	//            group.shutdownGracefully().sync(); // 释放线程池资源
	//            bossGroup.shutdownGracefully().sync();
	        }
		}
    }
	
	/*** 
     * 测试主机Host的port端口是否被使用
     * @param host 
     * @param port 
     * @throws UnknownHostException  
     */ 
    public static boolean isPortUsing(String host,int port) throws UnknownHostException{  
        boolean flag = false;  
        InetAddress Address = InetAddress.getByName(host);  
        try {  
            Socket socket = new Socket(Address,port);  //建立一个Socket连接
            flag = true;  
            socket.close();
        } catch (IOException e) {  
 
        }  
        return flag;  
    }
	/**
	 * @param serverCtx the serverCtx to set
	 */
	public void setServerCtx(Context serverCtx) {
		ServerCtx = serverCtx;
	}
	/**
	 * @return the serverCtx
	 */
	public Context getServerCtx() {
		return ServerCtx;
	}  

	 
}

