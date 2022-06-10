package com.example.sampleapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@SpringBootApplication
class SampleAppApplication

fun main(args: Array<String>) {
    runApplication<SampleAppApplication>(*args) {
        addInitializers(beans {
            bean {
                router {
                    GET("/echo") { r ->
                        ServerResponse.ok().body(
                            fromValue(
                                mapOf(
                                    "method" to r.methodName(),
                                    "path" to r.path()
                                )
                            )
                        )
                    }
                }
            }
        })
    }
}