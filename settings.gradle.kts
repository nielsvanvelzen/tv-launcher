enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "tv-launcher"

include(":app")

pluginManagement {
	repositories {
		gradlePluginPortal()
		mavenCentral()
		google()
	}
}

dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

	repositories {
		mavenCentral()
		google()
	}
}
