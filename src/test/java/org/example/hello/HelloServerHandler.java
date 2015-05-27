package org.example.hello;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloServerHandler extends SimpleChannelInboundHandler<String> {
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg)  {
        // 收到消息直接打印输出
		System.out.println(ctx.channel().remoteAddress() + " Say : " + msg);
		try {
			// 返回客户端消息 - 我已经接收到了你的消息 new
			// SimpleDateFormat("yyyy-MM-dd HH:mi:ss").format(new Date())+
			String abc = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			abc += "--Received your message !" + msg + "\n";
			ctx.writeAndFlush(abc);
        }catch(Exception e){
        	e.printStackTrace();
        }
    }
    
    /*
     * 
     * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
     * 
     * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
     * */
    @Override
    public void channelActive(ChannelHandlerContext ctx)  {
        try{
        System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");
        String abc=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        ctx.writeAndFlush( abc+"--Welcome to "+ InetAddress.getLocalHost().getHostName() + " service!\n");
        
        super.channelActive(ctx);
        }catch(Exception e){
        	e.printStackTrace();
        }
    }
}