package com.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.netty.handler.HelloClientInitializer;
import com.netty.handler.HelloWorldClientHandler;

public class HelloWorldClient {
	public static void main(String[] args) throws IOException {
		Bootstrap bs = new Bootstrap();
		EventLoopGroup group = new NioEventLoopGroup();
		bs.group(group).channel(NioSocketChannel.class)
//		.handler(new HelloClientInitializer());
		.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline=ch.pipeline();
				pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
				// 字符串解码 和 编码
				pipeline.addLast("decoder", new StringDecoder());
				pipeline.addLast("encoder", new StringEncoder());
				// 自己的逻辑Handler
				pipeline.addLast("handler", new HelloWorldClientHandler());
			}
		});

		try {
			Channel channel = bs.connect("localhost", 8877).sync().channel();
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				String line = in.readLine();
				if (line == null) {
					continue;
				}
				System.out.println("what's your input is :" + line);
				/*
				 * 向服务端发送在控制台输入的文本 并用"\r\n"结尾 之所以用\r\n结尾 是因为我们在handler中添加了
				 * DelimiterBasedFrameDecoder 帧解码。
				 * 这个解码器是一个根据\n符号位分隔符的解码器。所以每条消息的最后必须加上\n否则无法识别和解码
				 */
				channel.writeAndFlush(line + "\r\n");
				// fc.channel().closeFuture().sync();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}

	}
}
