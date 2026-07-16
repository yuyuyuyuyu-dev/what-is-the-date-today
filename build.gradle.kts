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
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
    // Version-catalog linter/formatter (sorts libs.versions.toml, flags unused entries).
    // Applied to the root project only. Auto-update is intentionally left unused so it
    // does not compete with Dependabot; use the `versionCatalogFormat` task for linting.
    alias(libs.plugins.versionCatalogUpdate)
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "io.gitlab.arturbosch.detekt")

    // ktlint (Kotlin) and Prettier (everything else) run at their defaults — the "Fit to
    // Standard" stance: ktlint's style comes from .editorconfig, Prettier's from package.json.
    ktlint {
        val buildDirPath =
            layout.buildDirectory
                .get()
                .asFile.invariantSeparatorsPath
        filter {
            // Compose Multiplatform registers its generated resource accessors (under the
            // build directory) as Kotlin source roots. That code is regenerated on every
            // build and is not ours to format, so ktlint must skip everything under build/.
            exclude { element -> element.file.invariantSeparatorsPath.startsWith(buildDirPath) }
        }
    }

    detekt {
        buildUponDefaultConfig = true
        source.setFrom(files("src"))
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    }
}

// Use the catalog plugin as a pure formatter (sort + normalize), not a pruner.
// versionCatalogFormat would otherwise delete version-only entries such as the
// android-* SDK levels: they are referenced from build scripts via libs.versions.*
// accessors, which the plugin's usage analysis cannot see. keepUnusedVersions guards them.
// (Library/plugin entries are detected via dependency resolution, so they are safe.)
versionCatalogUpdate {
    keep {
        keepUnusedVersions.set(true)
    }
}
