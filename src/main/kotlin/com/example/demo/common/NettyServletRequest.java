package com.example.demo.common;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by GYH on 2018/12/4.
 */
public class NettyServletRequest {
    private String body;
    private byte[] head;
    private ChannelHandlerContext ctx;

    public NettyServletRequest(String body, byte[] head, ChannelHandlerContext ctx) {
        this.body = body;
        this.head = head;
        this.ctx = ctx;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public byte[] getHead() {
        return head;
    }

    public void setHead(byte[] head) {
        this.head = head;
    }
}
