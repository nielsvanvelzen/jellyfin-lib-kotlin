plugins {
	id("kotlin")
}

repositories {
	google()
	jcenter()
}

dependencies {
	implementation(kotlin("stdlib-jdk8"))
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")

	api(project(":lib-model"))

	// HTTP
	implementation("io.ktor:ktor-client-okhttp:1.3.2")
	implementation("io.ktor:ktor-client-gson:1.3.2")
	implementation("io.ktor:ktor-client-websockets:1.3.2")
}
