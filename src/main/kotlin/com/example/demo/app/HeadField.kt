package com.example.demo.app

import tornadofx.*
import java.lang.IllegalStateException

/**
 * Created by gyh on 2019/1/28.
 */
class HeadField {
    var chosen: Boolean by property()
    var content: String by property()
    var type: String by property()

    fun getContent(): List<Byte> {
        val list = content.split(",")
        return list.stream().map {
            val i = it.trim().toInt(16)
            when(type) {
                "byte" -> listOf(i.toByte())
                "short" -> listOf(i.ushr(8).toByte(), i.toByte())
                "int" -> listOf(i.ushr(24).toByte(), i.ushr(16).toByte(), i.ushr(8).toByte(), i.toByte())
                else -> throw IllegalStateException("类型错误")
            }
        }.collect({ArrayList()}, { sb, s -> sb.addAll(s)}, { sb, sb2 -> sb.addAll(sb2)})
    }

}

fun main() {
    val i = 0x01_02_03_04
    println(i.ushr(8).toByte())
}
