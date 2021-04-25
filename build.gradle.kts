buildscript {
    repositories {
		mavenCentral()
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
    }
}

allprojects {
    repositories {
		mavenCentral()
        google()
        jcenter()
    }
}
