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
 * ����socket������
 * @author xin
 *
 */
public class LocalSocketServer {

	

//	private int ClientCount ;//�ͻ���������
	private List<SocketServerChannal> RemoteSocketChannelList =new ArrayList<SocketServerChannal>();
	private ChannelFuture cf;
	private EventLoopGroup group=new NioEventLoopGroup();
	private UrlConfigInfo configInfo;
	private Context ServerCtx;

	/**
	 * ���ò�������
	 * @param configInfo the configInfo to set
	 */
	public void setConfigInfo(UrlConfigInfo configInfo) {
		this.configInfo = configInfo;
	}
	/**
	 * ��ȡ��������
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
//	 * @param clientCount ���ÿͻ���������
//	 */
//	public void setClientCount(int clientCount) {
//		ClientCount = clientCount;
//	}
//	/**
//	 * @return the ��ȡ�ͻ���������
//	 */
//	public int getClientCount() {
//		return ClientCount;
//	}
	/**
	 * @param remoteClientList ���ÿͻ��������б�
	 */
	public void setRemoteSocketChannelList(List<SocketServerChannal> remoteSocketChannelList) {
		RemoteSocketChannelList = remoteSocketChannelList;
	}
	/**
	 * @return the ��ȡ�ͻ��������б�
	 */
	public List<SocketServerChannal> getRemoteSocketChannelList() {
		return RemoteSocketChannelList;
	}
	//�ͻ��������б�����
	public  void addRemoteSocketChannelList( SocketServerChannal client)
	{
		RemoteSocketChannelList.add(client);
	}
	public void removeRemoteSocketChannelList(SocketServerChannal client){
		RemoteSocketChannelList.remove(client);
	}
	

	/**
	 * @param �������߳�ͨ��
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
	 * @param EventLoopGroup �����̳߳�
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
			throw new Exception("����δ��ʼ������������");
		
		}
		
		if(configInfo.getPort()<0){
			throw new Exception("�˿����ò��Ϸ�����������");
		
		}
		
		if (configInfo!=null){
	        EventLoopGroup bossGroup = new NioEventLoopGroup();
	      
	//        EventLoopGroup group = new NioEventLoopGroup();
	        try {
	        	
		            ServerBootstrap sb = new ServerBootstrap();
		            sb.option(ChannelOption.SO_BACKLOG, 1024);
		            sb.group(group, bossGroup) // ���̳߳�
		                    .channel(NioServerSocketChannel.class) // ָ��ʹ�õ�channel
		                    .localAddress(this.configInfo.getPort())// �󶨼����˿�
		                    .childHandler(new ChannelInitializer<SocketChannel>() { // �󶨿ͻ�������ʱ�򴥷�����
		                    	
		                        @Override
		                        protected void initChannel(SocketChannel ch) throws Exception {	  
		                        	Calendar cal = Calendar.getInstance(); 
		                        	Date date = cal.getTime();
		                        	SocketServerChannal chl=new SocketServerChannal(date, ch,getLocalSocketServer().getConfigInfo().getHeartbeatMsgTime()*2,getLocalSocketServer().getConfigInfo().isIsUseHeartbeatMsg());
		                        	addRemoteSocketChannelList(chl);
//		                            System.out.println("����");
//		                            System.out.println("��Ϣ����һ�ͻ������ӵ��������");
//		                            System.out.println("IP:" + ch.localAddress().getHostName());
//		                            System.out.println("Port:" + ch.localAddress().getPort());
//		                            System.out.println("�������");
		                           
		                            ch.pipeline().addLast(new StringEncoder(Charset.forName(configInfo.getCharset().getValue().toString())));
		                            ch.pipeline().addLast(new LocalSocketServerHandler(getLocalSocketServer(),ch)); // �ͻ��˴�������
		                            ch.pipeline().addLast(new ByteArrayEncoder());
		                            
		                        }
		                    });
		            cf = sb.bind().sync(); // �������첽������
		            System.out.println(LocalSocketServer.class + " �������ڼ����� " + cf.channel().localAddress());
	        	
	            if(!PublicUtil.LocalServerList.contains(this))
	            {
	            	synchronized (PublicUtil.LocalServerList) {
	            		PublicUtil.LocalServerList.add(this);
	                }
	            	
	            } 
	//            cf.channel().closeFuture().sync(); // �رշ�����ͨ��
	//            System.out.println("�����ѹر�.."); // �ر����
	        } finally {
	//            group.shutdownGracefully().sync(); // �ͷ��̳߳���Դ
	//            bossGroup.shutdownGracefully().sync();
	        }
		}
    }
	
	/*** 
     * ��������Host��port�˿��Ƿ�ʹ��
     * @param host 
     * @param port 
     * @throws UnknownHostException  
     */ 
    public static boolean isPortUsing(String host,int port) throws UnknownHostException{  
        boolean flag = false;  
        InetAddress Address = InetAddress.getByName(host);  
        try {  
            Socket socket = new Socket(Address,port);  //����һ��Socket����
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

