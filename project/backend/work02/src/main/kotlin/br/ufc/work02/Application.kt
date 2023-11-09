package br.ufc.work02

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
//@EnableJpaRepositories(basePackages = ["br.ufc.work02.product.dao"])
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
