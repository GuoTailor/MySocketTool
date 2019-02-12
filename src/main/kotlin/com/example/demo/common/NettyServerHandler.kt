package com.example.demo.common

import com.example.demo.app.printHexBinary
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import javafx.application.Platform
import javafx.scene.control.Button
import javafx.scene.web.HTMLEditor
import java.text.SimpleDateFormat

/**
 * Created by GYH on 2018/12/3.
 */
@ChannelHandler.Sharable
class NettyServerHandler : ChannelInboundHandlerAdapter() {
    var editor: HTMLEditor? = null
    var button: Button? = null
    var ctx: ChannelHandlerContext? = null

    // 有信号进来时
    @Throws(Exception::class)
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        println(msg)
        val nmka = msg as NettyServletRequest
        Platform.runLater{
            editor?.htmlText = editor?.htmlText + "<a style=\"color:#6d6d6d;font-size:12px;\">${printHexBinary(nmka.head)} <<" +
                    " ${SimpleDateFormat("HH:mm:ss SSS").format(System.currentTimeMillis())}</a> <br/> <a style=\"color:#008000;font-size:14px;\">${nmka.body}</a><p/>"
        }
        ctx.fireChannelRead(msg)
    }

    // 当连接打开时，这里表示有数据将要进站。
    @Throws(Exception::class)
    override fun channelActive(ctx: ChannelHandlerContext) {
        println("连接打开")
        Platform.runLater {
            button?.text = "已连接"
        }
        this.ctx = ctx
        super.channelActive(ctx)
    }

    // 当连接要关闭时
    @Throws(Exception::class)
    override fun channelInactive(ctx: ChannelHandlerContext) {
        println("连接关闭")
        Platform.runLater {
            button?.text = "连接"
        }
        super.channelInactive(ctx)
    }

    fun send(data: String, heads: List<Byte>) {
        ctx?.channel()?.writeAndFlush(NettyServletResponse(data, heads))
    }

}

