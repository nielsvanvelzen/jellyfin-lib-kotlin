plugins {
	id("kotlin")
	id("maven-publish")
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
}

val sourcesJar by tasks.creating(Jar::class) {
	archiveClassifier.set("sources")

	from(sourceSets.getByName("main").allSource)
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			from(components["kotlin"])

			artifact(sourcesJar)
		}
	}
}
