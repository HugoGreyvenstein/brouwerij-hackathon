package com.example.brewery.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.example.brewery"])
class BrewingApplication

fun main(args: Array<String>) {
    runApplication<BrewingApplication>(*args)
}
