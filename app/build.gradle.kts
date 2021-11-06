plugins {
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.ksp)
	alias(libs.plugins.android.app)
}

android {
	compileSdk = 31

	defaultConfig {
		minSdk = 21
		targetSdk = 31

		applicationId = "nl.ndat.tvlauncher"
		versionCode = 1_00_00
		versionName = "1.0.0"
	}

	buildFeatures {
		viewBinding = true
	}

	sourceSets["main"].java.srcDirs("src/main/kotlin")
	sourceSets["test"].java.srcDirs("src/test/kotlin")
}

dependencies {
	// System
	implementation(libs.androidx.core)
	implementation(libs.androidx.core.role)
	implementation(libs.androidx.lifecycle.livedata)
	implementation(libs.androidx.tvprovider)
	implementation(libs.koin)

	// Data
	implementation(libs.androidx.room.ktx)
	ksp(libs.androidx.room.compiler)

	// UI
	implementation(libs.androidx.cardview)
	implementation(libs.androidx.constraintlayout)
	implementation(libs.androidx.fragment)
	implementation(libs.androidx.recyclerview)
	implementation(libs.androidx.appcompat)
}
