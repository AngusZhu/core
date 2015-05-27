package com.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.netty.handler.DiscardServerHandler;

public class DiscardServer {
	private int port;
	
	public DiscardServer(){}
	public DiscardServer(int port){
		this.port=port;
	}
	
	private void run() {
		//开发服务端步骤：
		/**
		 * 1.新建两个事件驱动组EventLoopGroup，一个分发组，一个工作组。
		 * 2.新建服务启动类ServerBootstrap
		 * 3.把步骤1新建的时间驱动组注入到服务启动类中
		 * 4.声明服务器端使用的服务处理管道类
		 * 5.把服务端的handler加到childHandler中
		 * 6.设置分发组的可选配置
		 * 7.设置handler的可选配置。
		 * 8.监听端口
		 * 9.关闭管道
		 * 10.finally中，关闭事件驱动组。
		 * 	
		 */
		EventLoopGroup bossGroup =new NioEventLoopGroup();
		EventLoopGroup workerGroup =new NioEventLoopGroup();
		
		ServerBootstrap sbs=new ServerBootstrap();
		sbs.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new DiscardServerHandler());
			}
		})
		.option(ChannelOption.SO_BACKLOG, 128)
		.childOption(ChannelOption.SO_KEEPALIVE, true);
		 try {
			ChannelFuture f=sbs.bind(port).sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	
	}
	
	public static void main(String[] args) {
		new DiscardServer(8787).run();
	}

}
