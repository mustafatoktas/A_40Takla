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

rootProject.name = "40 Takla"
include(":app")
include(":core:core-domain")
include(":core:core-data")
include(":core:core-common")
include(":core:core-ui")
include(":usecase:usecase-app")
include(":usecase:usecase-firebase")
include(":feat:feat-about")
include(":feat:feat-list")
include(":feat:feat-main")
include(":feat:feat-congratulation")

