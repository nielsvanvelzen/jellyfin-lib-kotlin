plugins {
	id("com.android.library")
	id("kotlin-android")
	id("kotlin-android-extensions")
}

android {
	compileSdkVersion(29)
	buildToolsVersion = "29.0.2"

	defaultConfig {
		minSdkVersion(26)
		targetSdkVersion(29)
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}

	kotlinOptions {
		jvmTarget = JavaVersion.VERSION_1_8.toString()
	}
}

repositories {
	google()
	jcenter()
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib-jdk8"))

	implementation(project(":lib-core"))

	implementation("androidx.core:core-ktx:1.2.0")
	implementation("com.google.code.gson:gson:2.8.6")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.5")
}
