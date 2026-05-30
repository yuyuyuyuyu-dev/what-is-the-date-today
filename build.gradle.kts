plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.detekt)
}

allprojects {
    apply(plugin = "com.diffplug.spotless")
    apply(plugin = "io.gitlab.arturbosch.detekt")

    spotless {
        kotlin {
            target("**/*.kt")
            targetExclude(
                "**/build/**/*.kt",
                "**/.idea/**/*.kt",
                "**/bin/**/*.kt",
                "**/generated/**/*.kt",
            )
            ktfmt().kotlinlangStyle()
        }
        kotlinGradle {
            target("**/*.gradle.kts")
            targetExclude("**/build/**/*.gradle.kts")
            ktfmt().kotlinlangStyle()
        }
        format("web") {
            target("**/*.html", "**/*.css", "**/*.js")
            targetExclude(
                "**/build/**",
                "**/.gradle/**",
                "**/.kotlin/**",
                "**/kotlin-js-store/**",
                "**/node_modules/**",
            )
            prettier()
        }
    }

    detekt {
        buildUponDefaultConfig = true
        source.setFrom(files("src"))
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    }
}
