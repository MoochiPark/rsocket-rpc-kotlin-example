import com.google.protobuf.gradle.*

plugins {
    application
    idea
}

apply(plugin = "com.google.protobuf")

dependencies {
    compileOnly("javax.annotation:javax.annotation-api:1.3.2")

//    compileOnly("io.rsocket.rpc:rsocket-rpc-core:0.3.0")
}

protobuf {
    generateProtoTasks {
        all().forEach { task ->
            task.enabled = false
        }
    }
}

idea {
    module {
        sourceDirs.plusAssign(file("src/main/proto"))
    }
}
