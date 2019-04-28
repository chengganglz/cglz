package com.kingdee.eas.custom.socketjk;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.ep.app.BeanParam;
import com.kingdee.eas.ep.plugin.ExtendMethodInfo;
import com.kingdee.eas.ep.plugin.MethodParam;
import com.kingdee.eas.ep.plugin.ScriptExecuteException;
import com.kingdee.eas.ep.plugin.ScriptExecutorFactotry;
import com.kingdee.eas.ep.plugin.ScriptInfo;
import com.kingdee.util.StringUtils;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
public class RemoteSocketClient {


	private EventLoopGroup group = new NioEventLoopGroup();
	private  SocketChannel RomoteSocketChannel;
	private ChannelFuture cf;
	private UrlConfigInfo configInfo;
	private Context ServerCtx;
	private Timer timerChkHeart;
	private  Timer timerChkTimeOut;
	private List<SocketMsg> sendMsgList=new ArrayList<SocketMsg>();;
	/**
	 * 
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


    public RemoteSocketClient() {

    }

    public RemoteSocketClient getRemoteSocketClient()
    {
    	return this;
    }
  //增加发送消息列表
	public  void addsendMsgList( SocketMsg msg)
	{
		sendMsgList.add(msg);
	}
	  //移除发送消息列表
	public void removesendMsgList(SocketMsg msg){
		sendMsgList.remove(msg);
	}
	
	/**
	 * 获取发送消息列表
	 * @return
	 */
	public List<SocketMsg> getSendMsgList()
	{
		return sendMsgList;
	}
    /**
	 * @param group the group to set
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
    /**
	 * @param romoteSocketChannel the romoteSocketChannel to set
	 */
	public void setRomoteSocketChannel(SocketChannel romoteSocketChannel) {
		RomoteSocketChannel = romoteSocketChannel;
	}
	/**
	 * @return the romoteSocketChannel
	 */
	public SocketChannel getRomoteSocketChannel() {
		return RomoteSocketChannel;
	}
	/**
	 * @param cf the cf to set
	 */
	public void setCf(ChannelFuture cf) {
		this.cf = cf;
	}
	/**
	 * @return the cf
	 */
	public ChannelFuture getCf() {
		return cf;
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
	public void start() throws Exception {
		if(configInfo==null){
			throw new Exception("参数未初始化，不能启动");
		
		}
		
		if(configInfo.getPort()<0){
			throw new Exception("端口设置不合法，不能启动");
		
		}
		
		if (configInfo!=null){
//        EventLoopGroup group = new NioEventLoopGroup();
        try {
        	
            Bootstrap b = new Bootstrap();
           
            b.group(group) // 注册线程池
                    .channel(NioSocketChannel.class) // 使用NioSocketChannel来作为连接用的channel类
                    .remoteAddress(new InetSocketAddress(this.configInfo.getIp(), this.configInfo.getPort())) // 绑定连接端口和host信息
                    .handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                        	RomoteSocketChannel=ch;
//                            System.out.println("正在连接中...");
                            ch.pipeline().addLast(new StringEncoder(Charset.forName(configInfo.getCharset().getValue().toString())));
                            ch.pipeline().addLast(new RemoteSocketClientHandler(getRemoteSocketClient(),ch));
                            ch.pipeline().addLast(new ByteArrayEncoder());
                            ch.pipeline().addLast(new ChunkedWriteHandler());

                        }
                    });
            // System.out.println("服务端连接成功..");
        	
            cf = b.connect().sync(); // 异步连接服务器
//            System.out.println("服务端连接成功..."); // 连接完成
            if(!PublicUtil.RemoteClientList.contains(this))
            {
            	synchronized (PublicUtil.RemoteClientList) {
            		PublicUtil.RemoteClientList.add(this);
                }
            	if(this.getConfigInfo().isIsUseHeartbeatMsg())
            	{
            		timerChkHeart=new Timer();
            		timerChkHeart.schedule(new TimerTask() {
            	    	        public void run() {
//            	    	        	RomoteSocketChannel.writeAndFlush(Unpooled.copiedBuffer("心跳报文", Charset.forName(configInfo.getCharset().getValue().toString())));
//            	    	        	try {
////            	    	        		SocketFacadeFactory.getLocalInstance(ServerCtx).sendHeartMsg(getRemoteSocketClient());
//									} catch (BOSException e) {
//										// TODO Auto-generated catch block
//										e.printStackTrace();
//									}
            	    	        	BeanParam param = new BeanParam();
            	    	    		param.setContext(getRemoteSocketClient().getServerCtx());
            	    	    		ExtendMethodInfo method = new ExtendMethodInfo();
            	    	    		MethodParam socket=new MethodParam();
            	    	    		socket.setType("socketMsg");
            	    	    		socket.setValue("");
            	    	    		method.addParam(socket);
            	    	    		
            	    	    		MethodParam billid=new MethodParam();
            	    	    		billid.setType("billID");
            	    	    		billid.setValue("");
            	    	    		method.addParam(billid);
            	    	    		
            	    	    		MethodParam result=new MethodParam();
            	    	    		result.setType("result");
            	    	    		result.setValue("");
            	    	    		method.addParam(result);
            	    	    		
            	    	    		MethodParam sendMsg=new MethodParam();
            	    	    		sendMsg.setType("sendMsg");
            	    	    		sendMsg.setValue("");
            	    	    		method.addParam(sendMsg);
            	    	    		
            	    	    		
            	    	    		MethodParam errMsg=new MethodParam();
            	    	    		errMsg.setType("errMsg");
            	    	    		errMsg.setValue("");
            	    	    		method.addParam(errMsg);
            	    	    		method.setResult("String", "");
            	    	        	String script=PublicBaseUtil.getHeartMsgScriptByType(getRemoteSocketClient().getServerCtx(), getRemoteSocketClient().getConfigInfo());
            	    	        	if (script!=null && (!script.equals(""))) {
            	    	        		
            	    	    			try {
            	    	    				
            	    	    				ScriptExecutorFactotry.getServerInstance().execute(
            	    	    						param,
            	    	    						method,
            	    	    						new ScriptInfo(script, false, ""));
            	    	    			} catch (EASBizException e) {
            	    	    				// TODO Auto-generated catch block
            	    	    				e.printStackTrace();
            	    	    				try {
												SocketFacadeFactory.getLocalInstance(getRemoteSocketClient().getServerCtx()).InsSocketLog(getRemoteSocketClient().getConfigInfo(), e.getMessage(), "");
											} catch (BOSException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
            	    	    			} catch (ScriptExecuteException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
											try {
												SocketFacadeFactory.getLocalInstance(getRemoteSocketClient().getServerCtx()).InsSocketLog(getRemoteSocketClient().getConfigInfo(), e.getMessage(), "");
											} catch (BOSException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
										}
            	    	    			if (result!=null)
            	    	    			{
            	    	    				if(result.getValue().toString().toUpperCase().equals("FALSE")){
            	    	    					String strErr="";
            	    	    					if(errMsg!=null){
            	    	    						if(errMsg.getValue()!=null){
            	    	    							strErr=errMsg.getValue().toString();
            	    	    						}
            	    	    					}
            	    	    					try {
													SocketFacadeFactory.getLocalInstance(getRemoteSocketClient().getServerCtx()).InsSocketLog(getRemoteSocketClient().getConfigInfo(),  "","错误："+strErr);
												} catch (BOSException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
            	    	    				}
            	    	    			}
            	    	    			if(!StringUtils.isEmpty( sendMsg.getValue().toString())){
                	    	        		if(RomoteSocketChannel==null){
                	    	        			try {
    												getRemoteSocketClient().start();
    											} catch (Exception e) {
    												// TODO Auto-generated catch block
    												e.printStackTrace();
    												try {
    													SocketFacadeFactory.getLocalInstance(getRemoteSocketClient().getServerCtx()).InsSocketLog(getRemoteSocketClient().getConfigInfo(), e.getMessage(), "");
    												} catch (BOSException e1) {
    													// TODO Auto-generated catch block
    													e1.printStackTrace();
    												}
    											}
                	    	        		}
                	    	        		
                	    	        			RomoteSocketChannel.writeAndFlush(Unpooled.copiedBuffer(sendMsg.getValue().toString(), Charset.forName(configInfo.getCharset().getValue().toString())));
                	    	        			if(configInfo.isIsWrite()){	
                	    	        				try {
														SocketFacadeFactory.getLocalInstance(getRemoteSocketClient().getServerCtx()).InsSocketLog(getRemoteSocketClient().getConfigInfo(), sendMsg.getValue().toString(), "心跳电文");
													} catch (BOSException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
                	    	        			}
                	    	        	}
            	    	    		}
            	    	        	
            	    	        }
            	    	}, this.getConfigInfo().getHeartbeatMsgTime() , this.getConfigInfo().getHeartbeatMsgTime());
            		
            	}
            	
            } 

//            cf.channel().closeFuture().sync(); // 异步等待关闭连接channel
//            System.out.println("连接已关闭.."); // 关闭完成

        } finally {
//            group.shutdownGracefully().sync(); // 释放线程池资源
        }
		}
    }

}
