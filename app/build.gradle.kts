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

	kotlinOptions {
		jvmTarget = JavaVersion.VERSION_1_8.toString()
	}

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
	implementation("androidx.core:core-ktx:1.3.2")
	implementation("androidx.core:core-role:1.0.0")
	implementation("androidx.leanback:leanback:1.0.0")
	implementation("androidx.fragment:fragment-ktx:1.3.3")
	implementation("androidx.cardview:cardview:1.0.0")
	implementation("androidx.constraintlayout:constraintlayout:2.0.4")
	implementation("androidx.tvprovider:tvprovider:1.0.0")
}
