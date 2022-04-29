import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    kotlin("jvm") version "1.6.20"
    id("com.google.protobuf") version "0.8.18"
    application
    idea
}

allprojects {
    group = "io.wisoft"
    version = "2022.17.0"

    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
        maven("https://oss.jfrog.org/artifactory/oss-release-local")
    }

    dependencies {
        testImplementation(kotlin("test"))
//        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    }
}
