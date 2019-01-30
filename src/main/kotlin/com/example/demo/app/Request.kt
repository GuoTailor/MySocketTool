package com.example.demo.app

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*
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

    val heads: ObservableList<HeadField> = FXCollections.observableArrayList()
    init {
        this.ip = ip
        this.port = port
        this.code = code
    }

}

