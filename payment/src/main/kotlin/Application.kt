import io.wisoft.test.Test
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication



fun main(args: Array<String>) {
    SpringApplication.run(args)
}


fun response(message: String) = Test.Response
    .newBuilder()
    .setMessage(message)
    .build()

fun request(message: String) = Test.Request
    .newBuilder()
    .setMessage(message)
    .build()
