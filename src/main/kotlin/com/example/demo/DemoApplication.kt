package com.example.demo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.math.BigDecimal

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    SpringApplication.run(DemoApplication::class.java, *args)
}

@ConfigurationProperties(prefix = "message")
@Configuration
class MessageConfig {
    var messages: MutableList<Message> = mutableListOf()
}

class Message {
    lateinit var name: String
    lateinit var type: String
    lateinit var size: BigDecimal
}

@Component
class MessageService(private var messageConfig: MessageConfig) {
    fun createMessage() {
        val message = messageConfig.messages[0]
        println("${message.name}, ${message.type}, ${message.size}")
    }
}

@Component
class ApplicationStartup(val service: MessageService) : ApplicationListener<ApplicationReadyEvent> {
    override fun onApplicationEvent(event: ApplicationReadyEvent) = service.createMessage()
}
