package com.example.demo.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by GYH on 2018/12/3.
 */
public class MessageDecoder extends ByteToMessageDecoder {
    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private final int HEAD_LENGTH = 8;  //头的长度
    static final int BODY_INDEX = 6;    //包长度的位置
    private final byte BAG_HEAD = 0x02; //包头
    private final int CMD_INDEX = 2;    //CMD位置

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        System.out.println("查找 in :"+ in.readableBytes() +"  w:" +  in.writerIndex());
        int readable = in.readableBytes();
        if (in.isReadable() && readable >= HEAD_LENGTH) {
            int index = in.forEachByte(b -> b != BAG_HEAD);
            if (index != -1) {  //如果找到了
                in.readerIndex(index);
                int body = in.getShort(in.readerIndex() + BODY_INDEX);
                if (body >= 0) {
                    if (HEAD_LENGTH + body <= readable) {
                        Object request = getData(in, ctx, body);
                        if (request != null) {
                            out.add(request);
                        } else {
                            in.skipBytes(1);
                        }
                    }
                } else {
                    // 跳过一个字节
                    in.skipBytes(1);
                }
            } else { //否则跳过全部
                in.skipBytes(readable);
            }
        }
    }

    private Object getData(ByteBuf in, ChannelHandlerContext ctx, int body) {
        int cmd = in.getByte(in.readerIndex() + CMD_INDEX);
        switch (cmd) {
            case 1: {   //心跳
                /*Ping p = new Ping();
                p.setByteBuf(in.slice(in.readerIndex(), HEAD_LENGTH));
                in.retain();
                in.skipBytes(HEAD_LENGTH);
                logger.info("心跳");
                return p;*/
                break;
            }
            case 2: {    //消息
                String strBody = in.toString(in.readerIndex() + HEAD_LENGTH, body, Charset.forName("UTF-8"));
                logger.info("body.length: "+body+" req: "+in.getByte(2)+" 解码 :" + strBody);
                byte[] b = new byte[8];
                in.getBytes(in.readerIndex(), b);
                NettyServletRequest inputMessage = new NettyServletRequest(strBody, b, ctx);
                in.retain();        //TODO 抛异常的时候释放了吗？
                in.skipBytes(body + HEAD_LENGTH);
                return inputMessage;
            }
            case 3: {    //文件
                System.out.println("文件");
            }
        }
        return null;
    }

}

