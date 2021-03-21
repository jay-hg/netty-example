package com.acai.example.discard;

import io.netty.buffer.ByteBuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        /*ByteBuf in = (ByteBuf) msg;
        // Discard the received data silently.
        try {
            // Do something with msg
            while (in.isReadable()) {
                System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII));
                in.clear();
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }*/

        ctx.write(msg);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}