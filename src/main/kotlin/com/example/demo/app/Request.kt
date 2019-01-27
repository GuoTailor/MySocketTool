package com.example.demo.app

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*
import java.util.*
import java.util.stream.Collector
import java.util.stream.Collectors
import kotlin.reflect.KClass
import kotlin.reflect.full.cast

/**
 * Created by gyh on 2019/1/26.
 */
class Request(ip:String = "127.0.0.1", port:Int = 8282, code: Int = 1) {
    var ip: String by property()
    var port: Int by property()
    var code: Int by property()

    val heads: ObservableList<Head<*>> = FXCollections.observableArrayList()
    init {
        this.ip = ip
        this.port = port
        this.code = code
    }

}

class Head<T : Number>(chosen: Boolean = false, content: String, type: KClass<T>) {
    var chosen: Boolean by property()
    var content: String by property()
    var type: KClass<T> by property()
    fun getCon(): List<T> {
        val list = content.split(",")
        val n = list.stream().map {
            val i = it.trim().toInt(16)
            type.cast(i)
        }.collect(Collectors.toList())
        return n
    }

    /*fun getContent():List<T> {

    }*/

    init {
        this.chosen = chosen
        this.content = content
        this.type = type
    }
}

