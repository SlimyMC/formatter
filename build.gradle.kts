spotless {
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
        eclipse().configFile("eclipse-prefs.xml")
    }
}
