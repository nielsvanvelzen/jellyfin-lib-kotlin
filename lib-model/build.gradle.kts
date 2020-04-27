plugins {
	id("kotlin")
}

repositories {
	jcenter()
}

dependencies {
	implementation(kotlin("stdlib-jdk8"))
	implementation("com.google.code.gson:gson:2.8.6")
}
