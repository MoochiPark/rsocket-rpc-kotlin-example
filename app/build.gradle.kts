import com.google.protobuf.gradle.*

plugins {
    application
    idea
    kotlin("plugin.serialization") version "1.4.32"
}

apply(plugin = "com.google.protobuf")

dependencies {
    protobuf(project(":protos"))
    implementation("io.rsocket.rpc.kotlin:rsocket-rpc-core:0.2.13")
    api("com.google.protobuf:protobuf-java-util:3.20.1")

    implementation("io.rsocket.kotlin:rsocket-transport-netty:0.9.6")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")

    implementation("io.ktor:ktor-server-core:2.0.1")
    implementation("io.ktor:ktor-server-netty:2.0.1")
    implementation("io.ktor:ktor-server-content-negotiation:2.0.1")
    implementation("io.ktor:ktor-serialization:2.0.1")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.0.1")

    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation("org.jfree:jcommon:1.0.24")
    implementation("org.jfree:jfreechart:1.5.3")
    implementation("org.apache.commons:commons-math3:3.6")

    implementation("ch.qos.logback:logback-classic:1.2.11")
}

protobuf {
    generatedFilesBaseDir = "$projectDir/src/generated"

    protoc {
        artifact = "com.google.protobuf:protoc:3.17.0"
    }

    plugins {
        id("rsocketRpcKotlin") {
            artifact = "io.rsocket.rpc.kotlin:rsocket-rpc-protobuf:0.2.13"
//            artifact = "io.rsocket.rpc:rsocket-rpc-protobuf:0.3.0"
        }
    }

    generateProtoTasks {
        all().forEach { task ->
            task.inputs.file("${rootProject.projectDir}/build.gradle.kts")
            task.plugins {
                id("rsocketRpcKotlin")
            }
        }
    }
}

idea {
    module {
        sourceDirs.plusAssign(file("src/generated/main/java"))
        sourceDirs.plusAssign(file("src/generated/main/rsocketRpcKotlin"))

        generatedSourceDirs.plusAssign(file("src/generated/main/java"))
        generatedSourceDirs.plusAssign(file("src/generated/main/rsocketRpcKotlin"))
    }
}
