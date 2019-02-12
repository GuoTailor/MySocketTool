package com.example.demo.common

import io.netty.bootstrap.Bootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel

/**
 * Created by GYH on 2019/2/1.
 */
class NettyServer {

    fun run(host: String, port: Int): NettyServerHandler {
        val group = NioEventLoopGroup()
        val nmka = NettyServerInitializer()
        //是一个启动NIO服务的辅助启动类
        val bootstrap = Bootstrap()
        bootstrap.group(group)
                .channel(NioSocketChannel().javaClass)
                .handler(nmka)

        bootstrap.connect(host, port).sync().channel()

        return nmka.serverHandler
    }

}