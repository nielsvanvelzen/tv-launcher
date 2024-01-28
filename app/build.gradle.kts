plugins {
	alias(libs.plugins.android.app)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.sqldelight)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

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

	composeOptions {
		kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
}

sqldelight {
	databases {
		create("Database") {
			packageName.set("nl.ndat.tvlauncher.data.sqldelight")
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
	implementation(libs.androidx.compose.material3)
	implementation(libs.androidx.compose.ui.tooling.preview)
	debugImplementation(libs.androidx.compose.ui.tooling)
	implementation(libs.androidx.tv.foundation)
	implementation(libs.androidx.tv.material)
	implementation(libs.coil.compose)
}
