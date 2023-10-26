package net.slimymc.formatter

import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files

class FormatterPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val cachePath = ".gradle/caches/formatter/eclipse-prefs.xml"
        val eclipsePrefs = this.javaClass.classLoader.getResource("eclipse-prefs.xml")
            ?: throw FileNotFoundException("eclipse-prefs.xml")
        val file = target.file(cachePath)
        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.createNewFile()
        }
        file.writeText(eclipsePrefs.readText())

        target.plugins.apply(SpotlessPlugin::class.java)
        target.extensions.configure(SpotlessExtension::class.java) {
            lineEndings = com.diffplug.spotless.LineEnding.UNIX

            format("encoding") {
                target("*.*")
                encoding("UTF-8")
                endWithNewline()
                trimTrailingWhitespace()
            }

            yaml {
                target(
                    ".github/**/*.yml",
                    ".github/**/*.yaml",
                )
                endWithNewline()
                trimTrailingWhitespace()
                jackson()
                    .yamlFeature("LITERAL_BLOCK_STYLE", true)
                    .yamlFeature("MINIMIZE_QUOTES", true)
                    .yamlFeature("SPLIT_LINES", false)
            }

            kotlinGradle {
                target("**/*.gradle.kts")
                endWithNewline()
                trimTrailingWhitespace()
                diktat()
            }

            java {
                target("**/src/**/java/**/*.java")
                importOrder()
                removeUnusedImports()
                indentWithSpaces(2)
                endWithNewline()
                trimTrailingWhitespace()
                eclipse().configFile(cachePath)
            }
        }
    }
}
