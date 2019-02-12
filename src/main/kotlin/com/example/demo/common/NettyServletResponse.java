package com.example.demo.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.List;

/**
 * Created by GYH on 2018/12/13.
 */
public class NettyServletResponse {
    private String data;
    private List<Byte> heads;

    public NettyServletResponse(String data, List<Byte> heads) {
        this.data = data;
        this.heads = heads;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Byte> getHeads() {
        return heads;
    }

    public void setHeads(List<Byte> heads) {
        this.heads = heads;
    }
}
