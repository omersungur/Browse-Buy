pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Browse & Buy"
include(":app")
include(":domain")
include(":data")
include(":compose-ui")
include(":feature:splash")
include(":feature:onboarding")
include(":feature:favorite")
include(":feature:home")
include(":feature:auth")
include(":feature:setting")
include(":feature:search")
include(":feature:category")
include(":feature:cart")
include(":feature:profile")
include(":feature:detail")
