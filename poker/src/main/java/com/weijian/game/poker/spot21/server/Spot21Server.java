package com.weijian.game.poker.spot21.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * <p> @Description
 *
 * @author weijian
 * @date 2020-07-15 17:38
 */
@Service
public class Spot21Server {

    @PostConstruct
    public void start() {
        try {
            new Spot21Server().bind(8899);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void bind(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childAttr(AttributeKey.newInstance("childAttr"), "childAttrValue")
//                    .handler(new SimpleServerHandler())
                    .childHandler(new ChildChannelHandler());

            ChannelFuture f = serverBootstrap.bind(port).sync();

            f.channel().closeFuture().sync();
        } catch (Exception e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

//    private static class SimpleServerHandler extends ChannelInboundHandlerAdapter {
//        @Override
//        public void channelActive(ChannelHandlerContext ctx) throws Exception {
//            ctx.channel().pipeline().fireChannelActive();
//            System.out.println("SimpleServerHandler channelActive");
//            ctx.channel().eventLoop().execute(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("test task ...");
//                }
//            });
//        }
//
//        @Override
//        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//            System.out.println("SimpleServerHandler channelRegistered");
//        }
//
//        @Override
//        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//            System.out.println("SimpleServerHandler handlerAdded");
//        }
//    }
}
