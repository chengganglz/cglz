package com.kingdee.eas.custom.socketjk;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import com.dynamicode.socket.tcp.SendMsg;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.CharsetUtil;

public class RemoteSocketClientHandler  extends SimpleChannelInboundHandler<ByteBuf>  {

	public SocketChannel myCH;
	public RemoteSocketClient remoteClient;
	 public RemoteSocketClientHandler(RemoteSocketClient remoteC,SocketChannel ch) {
		// TODO Auto-generated constructor stub
		 myCH=ch;
		 remoteClient=remoteC;
	}

	/**
     * 向服务端发送数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("客户端与服务端通道-开启：" + ctx.channel().localAddress() + "channelActive");
//
//        String sendInfo = "Hello 这里是客户端  你好啊！";
//        System.out.println("客户端准备发送的数据包：" + sendInfo);
//        ctx.writeAndFlush(Unpooled.copiedBuffer(sendInfo, CharsetUtil.UTF_8)); // 必须有flush

    }

    /**
     * channelInactive
     *
     * channel 通道 Inactive 不活跃的
     *
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     *
     */
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("客户端与服务端通道-关闭：" + ctx.channel().localAddress() + "channelInactive");
    	synchronized (PublicUtil.RemoteClientList) {
        	if(PublicUtil.RemoteClientList.size()>0){
        		for(int i=PublicUtil.RemoteClientList.size()-1;i>-1 ;i--){
        			RemoteSocketClient remoteClientTemp=PublicUtil.RemoteClientList.get(i);
        			if(remoteClientTemp.equals(remoteClient)){
	        			if(remoteClientTemp.getRomoteSocketChannel()!=null){
	        				if(remoteClientTemp.getRomoteSocketChannel().equals(myCH)){
	        					remoteClientTemp.setRomoteSocketChannel(null);
	        					myCH.close();
	        				}
	        			}
        			}
        		}
        	}
    		
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
//        System.out.println("读取客户端通道信息..");
        ByteBuf buf = msg.readBytes(msg.readableBytes());
        String rev = getMessage(buf);
        for( int i=remoteClient.getSendMsgList().size()-1 ;i>-1;i--){
        	SocketMsg sendMsg= remoteClient.getSendMsgList().get(i);
        	if(sendMsg!=null)
        	{
        		String sendMsgStr=sendMsg.getSendMsg();
        		int headLength=remoteClient.getConfigInfo().getHeadLength();
        		if (headLength==0){
        			headLength=29;
        		}
        		if(sendMsgStr.length()>headLength){
	        		if (sendMsgStr.substring(0,remoteClient.getConfigInfo().getHeadLength()).equals(rev.substring(0, remoteClient.getConfigInfo().getHeadLength()))){
	        			sendMsg.setReturnMsg(rev);
	        			break;
	        		}
        		}else
        		{
        			remoteClient.getSendMsgList().remove(i);
        		}
        	}
        }
//        System.out.println(
//                "客户端接收到的服务端信息:" + ByteBufUtil.hexDump(buf) + "; 数据包为:" + buf.toString(Charset.forName("utf-8")));
    }
    /**
     * 
     * @param buf
     * @return
     */
    private String getMessage(ByteBuf buf) {
        byte[] con = new byte[buf.readableBytes()];
        buf.readBytes(con);
        try {
            return new String(con, remoteClient.getConfigInfo().getCharset().getValue().toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println("异常退出:" + cause.getMessage());
    }

}
