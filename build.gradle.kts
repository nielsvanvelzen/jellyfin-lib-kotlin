plugins {
	id("maven-publish")
}

buildscript {
	repositories {
		google()
		jcenter()
	}

	dependencies {
		classpath("com.android.tools.build:gradle:3.6.3")
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
	}
}


subprojects {
	apply(plugin = "maven-publish")

	group = "org.jellyfin"
	version = "1.0.0"

	publishing {
		repositories {
			maven {
				url = uri("https://api.bintray.com/maven/nielsvanvelzen/jellyfin-lib-kotlin/org.jellyfin;publish=0")

				credentials {
					username = project.findProperty("bintray.user") as String? ?: System.getenv("BINTRAY_USER")
					password = project.findProperty("bintray.key") as String? ?: System.getenv("BINTRAY_KEY")
				}
			}
		}

		publications {
			create<MavenPublication>("maven") {
				from(components.findByName("kotlin") ?: components.findByName("java"))
			}
		}
	}
}

tasks.register("clean", Delete::class) {
	delete(rootProject.buildDir)
}
