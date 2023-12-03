package br.ufc.work02

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
@ComponentScan(basePackages = ["br.ufc.work02"])
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
