package com.logix

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LogixApiApplication

fun main(args: Array<String>) {
  runApplication<LogixApiApplication>(*args)
}
