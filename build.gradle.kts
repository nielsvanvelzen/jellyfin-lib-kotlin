plugins {
	id("maven-publish")
}

allprojects {
	project.group = "org.jellyfin"
	project.version = "1.0.1"
}

buildscript {
	repositories {
		google()
		jcenter()
	}

	dependencies {
		classpath("com.android.tools.build:gradle:4.0.0")
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
	}
}

allprojects {
	apply(plugin = "maven-publish")

	publishing.repositories.maven {
		url = uri("https://api.bintray.com/maven/nielsvanvelzen/jellyfin-lib-kotlin/org.jellyfin;publish=1")

		credentials {
			username = project.findProperty("bintray.user") as String?
				?: System.getenv("BINTRAY_USER")
			password = project.findProperty("bintray.key") as String?
				?: System.getenv("BINTRAY_KEY")
		}
	}
}

tasks.register("clean", Delete::class) {
	delete(rootProject.buildDir)
}
