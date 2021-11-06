enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("VERSION_CATALOGS")

pluginManagement {
	repositories {
		gradlePluginPortal()
		mavenCentral()
		google()
	}

	plugins {
		id("com.google.devtools.ksp") version "1.5.31-1.0.0"
	}
}

dependencyResolutionManagement.repositories {
	mavenCentral()
	google()
}

include(":app")
