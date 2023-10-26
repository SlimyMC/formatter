plugins {
    java
    `kotlin-dsl`
    `maven-publish`
    id("com.gradle.plugin-publish") version("1.2.1")
}

group = "net.slimymc"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.22.0")
}

gradlePlugin {
    website.set("https://github.com/SlimyMC/formatter")
    vcsUrl.set("https://github.com/SlimyMC/formatter")
    plugins {
        create("formatter") {
            id = "net.slimymc.formatter"
            implementationClass = "net.slimymc.formatter.FormatterPlugin"
            displayName = "Formatter Plugin"
            description = "Formatter plugin to configure spotless easily."
            tags.set(listOf("formatter"))
        }
    }
}
