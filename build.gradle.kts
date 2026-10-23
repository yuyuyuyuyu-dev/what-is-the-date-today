plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt) apply false
    // Version-catalog linter/formatter (sorts libs.versions.toml, flags unused entries).
    // Applied to the root project only. Auto-update is intentionally left unused so it
    // does not compete with Dependabot; use the `versionCatalogFormat` task for linting.
    alias(libs.plugins.versionCatalogUpdate)
}

ktlint {
    version = libs.versions.ktlintCli.get()
}
