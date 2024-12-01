plugins {
    kotlin("jvm") version "2.0.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin Standard Library for test
    testImplementation(kotlin("test"))

    // Selenide for browser automation
    implementation("com.codeborne:selenide:7.5.1")

    // JUnit 5 for testing
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")

    // Selenium WebDriver (used by Selenide internally)
    implementation("org.seleniumhq.selenium:selenium-java:4.13.0")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(20)
}
