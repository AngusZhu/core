package com.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import com.netty.handler.HelloWorldServerHander;



public class HelleWorldServer {

	
	  public static void main(String[] args) {
		 new HelleWorldServer().helloWorld();
	 }

	private void helloWorld() {
		EventLoopGroup bossGroup=new NioEventLoopGroup();
		EventLoopGroup workerGroup=new NioEventLoopGroup();
		ServerBootstrap sb=new ServerBootstrap();
		sb.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
		//.childHandler(new HelloServerInitializer());
		.childHandler(new ChannelInitializer<ServerChannel>() {
			@Override
			protected void initChannel(ServerChannel ch) throws Exception {
				ch.pipeline().addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
				 			.addLast("decoder",new StringDecoder())
							.addLast("encoder",new StringEncoder())
							.addLast("handler",new  HelloWorldServerHander());
			}
		});
		try {
			ChannelFuture cf = sb.bind(8877).sync();
			cf.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
