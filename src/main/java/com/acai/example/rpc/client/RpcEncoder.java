package com.acai.example.rpc.client;

import com.acai.example.rpc.RpcInvocation;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.nio.charset.StandardCharsets;

public class RpcEncoder extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        RpcInvocation invocation = (RpcInvocation) msg;

        ByteBuf encoded = ctx.alloc().buffer(1024);
        encoded.writeInt(invocation.getMethod().getName().length());
        encoded.writeBytes(invocation.getMethod().getName().getBytes(StandardCharsets.UTF_8));
        ctx.write(encoded, promise);
    }
}
