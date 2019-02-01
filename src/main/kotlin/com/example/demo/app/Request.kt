package com.example.demo.app

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*
import java.util.stream.Collectors
import kotlin.reflect.KClass
import kotlin.reflect.full.cast

/**
 * Created by gyh on 2019/1/26.
 */
class Request(ip:String = "127.0.0.1", port:Int = 8282, code: Int = 1, body: String = "{\n    \"code\":12\n}") {
    var ip: String by property()
    var port: Int by property()
    var code: Int by property()
    val heads: ObservableList<HeadField> = FXCollections.observableArrayList()
    var body: String = "{\n" +
            "    \"code\":12\n" +
            "}"
    init {
        this.body = body
        this.ip = ip
        this.port = port
        this.code = code
    }

    fun serialize(): Map<String, Any> {
        return mapOf(
                "ip" to ip,
                "port" to port,
                "code" to code,
                "body" to body,
                "heads" to heads.stream().map { it.serialize() }.collect(Collectors.toList())
        )
    }

}

fun deserializeToRequest(map: Map<String, Any>): Request {
    val req = Request(map["ip"] as String, map["port"] as Int, map["code"] as Int, map["body"] as String)
    (map["heads"] as List<Map<String, Any>>).forEach{
        req.heads.add(deserializeToHead(it))
    }
    return req
}
