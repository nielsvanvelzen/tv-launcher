plugins {
	alias(libs.plugins.android.app)
	alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.sqldelight)
	alias(libs.plugins.kotlin.serialization)
}

kotlin {
	jvmToolchain(libs.versions.java.jdk.get().toInt())
}

android {
	namespace = "nl.ndat.tvlauncher"
	compileSdk = libs.versions.android.compileSdk.get().toInt()

	defaultConfig {
		minSdk = libs.versions.android.minSdk.get().toInt()
		targetSdk = libs.versions.android.targetSdk.get().toInt()

		applicationId = "nl.ndat.tvlauncher"
		versionCode = 1_00_00
		versionName = "1.0.0"
	}

	buildFeatures {
		buildConfig = true
		compose = true
	}
}

sqldelight {
	databases {
		create("Database") {
			packageName.set("nl.ndat.tvlauncher.data.sqldelight")
			generateAsync.set(true)
		}
	}
}

dependencies {
	// System
	implementation(libs.bundles.androidx.core)
	implementation(libs.bundles.koin)
	implementation(libs.androidx.tvprovider)
	implementation(libs.timber)

	// Data
	implementation(libs.bundles.sqldelight)
	implementation(libs.kotlinx.serialization.json)

	// UI
	implementation(libs.bundles.androidx.compose)
	implementation(libs.androidx.activity)
	implementation(libs.androidx.activity.compose)
	implementation(libs.androidx.appcompat)
	implementation(libs.androidx.navigation3.ui)
	implementation(libs.androidx.palette)
	implementation(libs.androidx.savedstate)
	implementation(libs.androidx.tv.material)
	implementation(libs.coil.compose)
	debugImplementation(libs.androidx.compose.ui.tooling)
}
