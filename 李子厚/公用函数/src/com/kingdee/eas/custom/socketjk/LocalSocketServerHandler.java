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
     * channel ͨ�� action ��Ծ��
     *
     * ���ͻ����������ӷ���˵����Ӻ����ͨ�����ǻ�Ծ���ˡ�Ҳ���ǿͻ��������˽�����ͨ��ͨ�����ҿ��Դ�������
     *
     */
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       // System.out.println(ctx.channel().localAddress().toString() + " ͨ���Ѽ��");
    }

    /*
     * channelInactive
     *
     * channel ͨ�� Inactive ����Ծ��
     *
     * ���ͻ��������Ͽ�����˵����Ӻ����ͨ�����ǲ���Ծ�ġ�Ҳ����˵�ͻ��������˵Ĺر���ͨ��ͨ�����Ҳ����Դ�������
     *
     */
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //System.out.println(ctx.channel().localAddress().toString() + " ͨ������Ծ��");
        // �ر���
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
     * ���ܣ���ȡ���������͹�������Ϣ
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // ��һ�֣������ַ���ʱ�Ĵ���
        ByteBuf buf = (ByteBuf) msg;
        String rev = getMessage(buf);
//        System.out.println("�������յ��ͻ�������:" + rev);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("�ͻ������", CharsetUtil.UTF_8)); 
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
					SocketFacadeFactory.getLocalInstance(localServer.getServerCtx()).InsSocketLog(localServer.getConfigInfo(),  revStr,"����"+strErr);
				}
			}
			if(!StringUtils.isEmpty( sendMsg.getValue().toString())){
				if(socketMsgInfo.isIsLog()){
					SocketFacadeFactory.getLocalInstance(localServer.getServerCtx()).InsSocketLog(localServer.getConfigInfo(),  revStr,"Ӧ����ģ�"+sendMsg.getValue().toString());
				}
        		if(myCH!=null){
        			myCH.writeAndFlush(Unpooled.copiedBuffer(sendMsg.getValue().toString(), Charset.forName(localServer.getConfigInfo().getCharset().getValue().toString())));
        		}else
        		{
        			SocketFacadeFactory.getLocalInstance(localServer.getServerCtx()).InsSocketLog(localServer.getConfigInfo(),  revStr,"ͨ���ѹرգ�����Ӧ�����ʧ��");
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
     * ���ܣ���ȡ��Ͽͻ��˷��͹���������֮��Ĳ���
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("����˽����������..");
        // ��һ�ַ�����дһ���յ�buf����ˢ��д��������ɺ�ر�sock channel���ӡ�
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        // ctx.flush();
        // ctx.flush(); //
        // �ڶ��ַ�������client�˹ر�channel���ӣ������Ļ����ᴥ������channelReadComplete������
        // ctx.flush().close().sync(); // �����֣��ĳ�����д��Ҳ���ԣ���������д����û�е�һ�ַ����ĺá�
    }

    /**
     * ���ܣ�����˷����쳣�Ĳ���
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println("�쳣��Ϣ��\r\n" + cause.getMessage());
    }

}
