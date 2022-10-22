plugins {
	alias(libs.plugins.android.app)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.ksp)
}

android {
	namespace = "nl.ndat.tvlauncher"
	compileSdk = 33

	defaultConfig {
		minSdk = 21
		targetSdk = 33

		applicationId = "nl.ndat.tvlauncher"
		versionCode = 1_00_00
		versionName = "1.0.0"
	}

	buildFeatures {
		compose = true
	}

	composeOptions {
		kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
	}
}

dependencies {
	// System
	implementation(libs.androidx.core)
	implementation(libs.androidx.core.role)
	implementation(libs.androidx.tvprovider)
	implementation(libs.bundles.koin)
	implementation(libs.timber)

	// Data
	implementation(libs.androidx.room.ktx)
	ksp(libs.androidx.room.compiler)

	// UI
	implementation(libs.androidx.appcompat)
	implementation(libs.bundles.androidx.compose)
	implementation(libs.bundles.androidx.tv)
	implementation(libs.coil.compose)
}
