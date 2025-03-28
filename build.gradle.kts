plugins {
    kotlin("jvm") version "2.1.10"
}

group = "org.alexiscrack3"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.beam:beam-sdks-java-core:2.63.0")
    implementation("org.apache.beam:beam-runners-direct-java:2.63.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(22)
}