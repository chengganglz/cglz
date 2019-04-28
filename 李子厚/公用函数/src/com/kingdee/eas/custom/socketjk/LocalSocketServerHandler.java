package com.kingdee.eas.custom.socketjk;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.mozilla.javascript.EvaluatorException;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.ep.app.BeanParam;
import com.kingdee.eas.ep.plugin.ExtendMethodInfo;
import com.kingdee.eas.ep.plugin.MethodParam;
import com.kingdee.eas.ep.plugin.ScriptExecuteException;
import com.kingdee.eas.ep.plugin.ScriptExecutorFactotry;
import com.kingdee.eas.ep.plugin.ScriptInfo;
import com.kingdee.util.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.CharsetUtil;

public class LocalSocketServerHandler extends ChannelInboundHandlerAdapter  {

	public SocketChannel myCH;
	public LocalSocketServer localServer;

	public LocalSocketServerHandler(LocalSocketServer localSe,SocketChannel ch) {
		// TODO Auto-generated constructor stub
		myCH=ch;
		localServer=localSe;
	}

	/*
     * channelAction
     *
     * channel 通道 action 活跃的
     *
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     *
     */
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       // System.out.println(ctx.channel().localAddress().toString() + " 通道已激活！");
    }

    /*
     * channelInactive
     *
     * channel 通道 Inactive 不活跃的
     *
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     *
     */
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //System.out.println(ctx.channel().localAddress().toString() + " 通道不活跃！");
        // 关闭流
        //ctx.writeAndFlush(paramObject)
        synchronized (PublicUtil.LocalServerList) {
        	if(PublicUtil.LocalServerList.size()>0){
        		for(int i=PublicUtil.LocalServerList.size()-1;i>-1;i--){
        			LocalSocketServer localServerTemp=PublicUtil.LocalServerList.get(i);
        			if (localServerTemp.equals(localServer)){
	        			for(int j=localServerTemp.getRemoteSocketChannelList().size()-1;j>-1;j--){
	        				if(localServerTemp.getRemoteSocketChannelList().get(j)!=null){
	        					SocketServerChannal chTemp=localServerTemp.getRemoteSocketChannelList().get(j);
		        				if(chTemp.getClientChannal().equals(myCH)){
		        					localServerTemp.getRemoteSocketChannelList().remove(chTemp);
		        					myCH.close();
		        				}
	        				}
	        				else
	        				{
	        					localServerTemp.getRemoteSocketChannelList().remove(j);
	        				}
	        			}
        			}
        		}
        	}
    		
        }

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
        	
            return new String(con, localServer.getConfigInfo().getCharset().getValue().toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 功能：读取服务器发送过来的信息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 第一种：接收字符串时的处理
        ByteBuf buf = (ByteBuf) msg;
        String rev = getMessage(buf);
//        System.out.println("服务器收到客户端数据:" + rev);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("客户端你好", CharsetUtil.UTF_8)); 
        for (int i=0;i<localServer.getRemoteSocketChannelList().size();i++){
        	SocketServerChannal servCh=localServer.getRemoteSocketChannelList().get(i);
        	if (servCh!=null){
	        	if(servCh.getClientChannal()!=null){
	        		if(servCh.getClientChannal().equals(myCH)){
	        			Calendar cal = Calendar.getInstance(); 
                    	Date date = cal.getTime();
	        			servCh.setUpdateTime(date);
	        		}
	        	}
        	}
        }
        BeanParam param = new BeanParam();
		param.setContext(localServer.getServerCtx());
		ExtendMethodInfo method = new ExtendMethodInfo();
		MethodParam socket=new MethodParam();
		socket.setType("socketMsg");
		socket.setValue(rev);
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
		String revStr=rev;
		SocketMsgSetInfo socketMsgInfo=PublicBaseUtil.getScriptByType(localServer.getServerCtx(), localServer.getConfigInfo(), revStr);
		if(socketMsgInfo!=null){
		String script=socketMsgInfo.getScript();
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
					SocketFacadeFactory.getLocalInstance(localServer.getServerCtx()).InsSocketLog(localServer.getConfigInfo(), revStr,e.getMessage());
				} catch (BOSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (ScriptExecuteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					SocketFacadeFactory.getLocalInstance(localServer.getServerCtx()).InsSocketLog(localServer.getConfigInfo(),  revStr,e.getMessage());
				} catch (BOSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}catch( EvaluatorException e){
				
				e.printStackTrace();
				try {
					SocketFacadeFactory.getLocalInstance(localServer.getServerCtx()).InsSocketLog(localServer.getConfigInfo(),  revStr,e.getMessage());
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
					SocketFacadeFactory.getLocalInstance(localServer.getServerCtx()).InsSocketLog(localServer.getConfigInfo(),  revStr,"错误："+strErr);
				}
			}
			if(!StringUtils.isEmpty( sendMsg.getValue().toString())){
				if(socketMsgInfo.isIsLog()){
					SocketFacadeFactory.getLocalInstance(localServer.getServerCtx()).InsSocketLog(localServer.getConfigInfo(),  revStr,"应答电文："+sendMsg.getValue().toString());
				}
        		if(myCH!=null){
        			myCH.writeAndFlush(Unpooled.copiedBuffer(sendMsg.getValue().toString(), Charset.forName(localServer.getConfigInfo().getCharset().getValue().toString())));
        		}else
        		{
        			SocketFacadeFactory.getLocalInstance(localServer.getServerCtx()).InsSocketLog(localServer.getConfigInfo(),  revStr,"通道已关闭，发送应答电文失败");
        		}
        	}
		}
		}
    	if (localServer.getConfigInfo().isIsWrite()){
    		SocketFacadeFactory.getLocalInstance(localServer.getServerCtx()).InsSocketLog(localServer.getConfigInfo(), rev, "");
        }
        //return false;
    }

    /**
     * 功能：读取完毕客户端发送过来的数据之后的操作
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("服务端接收数据完毕..");
        // 第一种方法：写一个空的buf，并刷新写出区域。完成后关闭sock channel连接。
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        // ctx.flush();
        // ctx.flush(); //
        // 第二种方法：在client端关闭channel连接，这样的话，会触发两次channelReadComplete方法。
        // ctx.flush().close().sync(); // 第三种：改成这种写法也可以，但是这中写法，没有第一种方法的好。
    }

    /**
     * 功能：服务端发生异常的操作
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println("异常信息：\r\n" + cause.getMessage());
    }

}
