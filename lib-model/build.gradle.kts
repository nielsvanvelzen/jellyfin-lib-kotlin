plugins {
	id("kotlin")
	id("maven-publish")
}

repositories {
	jcenter()
}

dependencies {
	implementation(kotlin("stdlib-jdk8"))
	implementation("com.google.code.gson:gson:2.8.6")
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
