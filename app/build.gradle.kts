plugins {
    application
    idea
}

dependencies {
    compileOnly(project(":protos"))

    implementation("io.ktor:ktor-client-cio:1.6.7")
    implementation("io.ktor:ktor-server-core:1.6.7")
    implementation("io.ktor:ktor-serialization:1.6.7")
    implementation("io.ktor:ktor-websockets:1.6.7")
    implementation("io.ktor:ktor-server-cio:1.6.7")
    implementation("io.ktor:ktor-server-netty:1.6.7")

//    implementation("io.rsocket.kotlin:rsocket-core:0.14.3")
    implementation("io.rsocket.kotlin:rsocket-transport-netty:0.9.6")
    implementation("io.rsocket.kotlin:rsocket-transport-okhttp:0.9.6")

    implementation("io.rsocket.kotlin:rsocket-transport-ktor:0.14.3")
    implementation("io.rsocket.kotlin:rsocket-transport-ktor-client:0.14.3")
    implementation("io.rsocket.kotlin:rsocket-transport-ktor-server:0.14.3")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
}
