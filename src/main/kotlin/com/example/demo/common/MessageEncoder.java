package com.example.demo.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.logging.Logger;

/**
 * Created by GYH on 2018/12/3.
 */
@ChannelHandler.Sharable
public class MessageEncoder extends MessageToByteEncoder<Object> {
    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private final ObjectMapper mapper = new ObjectMapper();


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws JsonProcessingException {
        logger.info("编码");
        NettyServletResponse response = (NettyServletResponse) msg;

        byte[] jsonByte = response.getData().getBytes();
        byte[] heads = new byte[8];
        for (int i = 0; i < 6; i++) {
            heads[i] = response.getHeads().get(i);
        }
        ByteBuf buf = Unpooled.copiedBuffer(heads);
        //response.getHead().body = jsonByte.length;
        buf.setShort(MessageDecoder.BODY_INDEX, jsonByte.length);
        out.writeBytes(buf);
        out.writeBytes(jsonByte);
        //0000001800000000000000C80000271100000000000000367B2273656E646F72223A31323336382C22636F6465223A313030332C2264617461223A7B226465766963655F6E616D65223A22227D7D0000001800000000000000C80000000200000002000002AA

    }

}
