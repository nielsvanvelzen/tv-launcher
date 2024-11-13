plugins {
	alias(libs.plugins.android.app)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.sqldelight)
}

kotlin {
	jvmToolchain(21)
}

android {
	namespace = "nl.ndat.tvlauncher"
	compileSdk = 34

	defaultConfig {
		minSdk = 21
		targetSdk = 34

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
	implementation(libs.androidx.core)
	implementation(libs.androidx.core.role)
	implementation(libs.androidx.tvprovider)
	implementation(libs.koin.android)
	implementation(libs.koin.androidx.compose)
	implementation(libs.timber)

	// Data
	implementation(libs.sqldelight.android)
	implementation(libs.sqldelight.coroutines)

	// UI
	implementation(libs.androidx.appcompat)
	implementation(libs.androidx.activity.compose)
	implementation(libs.androidx.compose.foundation)
	implementation(libs.androidx.compose.ui.tooling.preview)
	debugImplementation(libs.androidx.compose.ui.tooling)
	implementation(libs.androidx.tv.material)
	implementation(libs.androidx.palette)
	implementation(libs.coil.compose)
}
