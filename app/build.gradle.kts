plugins {
	id("com.android.application")
	id("kotlin-android")
}

android {
	compileSdkVersion(30)
	buildToolsVersion("30.0.3")

	defaultConfig {
		minSdkVersion(21)
		targetSdkVersion(30)

		applicationId = "nl.ndat.tvlauncher"
		versionCode = 1
		versionName = "1.0"
	}

	sourceSets["main"].java.srcDirs("src/main/kotlin")
	sourceSets["test"].java.srcDirs("src/test/kotlin")

	buildFeatures {
		viewBinding = true
	}

	buildTypes {
		val release by getting {
			minifyEnabled(false)
		}
	}
}

dependencies {
	implementation(libs.androidx.core.core)
	implementation(libs.androidx.core.role)
	implementation(libs.androidx.leanback)
	implementation(libs.androidx.fragment)
	implementation(libs.androidx.cardview)
	implementation(libs.androidx.constraintlayout)
	implementation(libs.androidx.lifecycle.livedata)
	implementation(libs.androidx.tvprovider)
}
