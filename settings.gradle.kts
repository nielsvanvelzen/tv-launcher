enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("VERSION_CATALOGS")

pluginManagement {
	repositories {
		gradlePluginPortal()
		mavenCentral()
		google()
	}

	plugins {
		id("org.jetbrains.kotlin.android") version "1.5.31"
		id("com.google.devtools.ksp") version "1.5.31-1.0.0"
		id("com.android.application") version "7.0.3"
	}
}

dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

	repositories {
		mavenCentral()
		google()
	}
}

include(":app")
