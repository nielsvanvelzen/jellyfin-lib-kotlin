plugins {
	id("kotlin")
}

repositories {
	google()
	jcenter()
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib-jdk8"))
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")

	// HTTP
	implementation("io.ktor:ktor-client-android:1.3.2")
	implementation("io.ktor:ktor-client-gson:1.3.2")
}
