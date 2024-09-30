package netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

// 部署在服务器
public class ChatServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel channel) {
                            ChannelPipeline channelPipeline = channel.pipeline();
                            channelPipeline.addLast("decoder", new StringDecoder());
                            channelPipeline.addLast("encoder", new StringEncoder());
                            channelPipeline.addLast(new ChatServerHandler());
                        }
                    });
            System.out.println("Netty Server started.");
            ChannelFuture future = bootstrap.bind(8000).sync();
            // future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
