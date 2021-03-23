package com.acai.example.rpc.client;

import com.acai.example.rpc.DemoService;
import com.acai.example.rpc.RpcInvocation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcClient {
    public static void main(String[] args) throws Exception {
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler((new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new RpcClientHandler(),new RpcEncoder());
                        }
                    }));

            ChannelFuture f = b.connect(host,port).sync();

            InvocationHandler invocationHandler = new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    RpcInvocation invocation = new RpcInvocation(method, args);
                    f.channel().writeAndFlush(invocation);
                    return null;
                }
            };

            DemoService demoService = (DemoService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[]{DemoService.class},invocationHandler);
            demoService.echo("chg");

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
