package br.com.forum

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@SpringBootApplication
class ForumApplication

fun main(args: Array<String>) {
	runApplication<ForumApplication>(*args)
}
