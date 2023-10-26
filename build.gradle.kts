plugins {
    java
    `kotlin-dsl`
    `maven-publish`
}

group = "net.slimymc"

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.22.0")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

gradlePlugin {
    plugins {
        create("formatter") {
            id = "net.slimymc.formatter"
            implementationClass = "net.slimymc.formatter.FormatterPlugin"
        }
    }
}
