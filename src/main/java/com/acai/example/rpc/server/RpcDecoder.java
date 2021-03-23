package com.acai.example.rpc.server;

import com.acai.example.rpc.RpcInvocation;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RpcDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("decode");
        int readable = byteBuf.readableBytes();
        if (readable < 4) {
            return;
        }

        int len = byteBuf.readInt();

        if (readable < len + 4) {
            return;
        }

        String methodName = new String(byteBuf.readBytes(len).toString(StandardCharsets.UTF_8));
        list.add(methodName);
    }
}
