buildscript {
    dependencies {
        classpath("org.postgresql:postgresql:42.6.0")
    }
}

plugins {
    kotlin("jvm") version "1.8.0"
    application
    id("org.flywaydb.flyway") version "9.16.1"
    id("co.uzzu.dotenv.gradle") version "2.0.0"
    id("org.openjfx.javafxplugin") version "0.0.8"
}

javafx {
    version = "13"
    modules = mutableListOf("javafx.controls", "javafx.fxml", "javafx.base", "javafx.media")
}

group = "no.tornado"
//version = "1.7.20.1"
group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

//javafx {
//    version = "11.0.2"
//    modules = ['javafx.controls', 'javafx.graphics']
//}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    implementation("org.jetbrains.exposed:exposed-core:0.40.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.40.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.40.1")
    implementation("org.jetbrains.exposed:exposed-java-time:0.40.1")
    implementation("org.postgresql:postgresql:42.6.0")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    implementation("ch.qos.logback:logback-classic:1.4.6")
    implementation("org.slf4j:slf4j-nop:2.0.5")
    implementation("org.slf4j:slf4j-api:2.0.5")
    implementation("no.tornado:tornadofx:1.7.20")
//    implementation("ch.qos.logback:logback-classic:1.2.9")
//    implementation("ch.qos.logback:logback-core:1.2.9")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(19)
}

application {
    mainClass.set("MainKt")
}



flyway {
    url =
        "jdbc:" + env.fetch("DB_CONNECT") + "://" + env.fetch("DB_HOST") + ":" + env.fetch("DB_PORT") + "/" + env.fetch(
            "DB_NAME"
        )
    user = env.fetch("DB_USER")
    password = env.fetch("DB_PASSWORD")
    locations = arrayOf("filesystem:src/main/resources/db/migration/")
}

