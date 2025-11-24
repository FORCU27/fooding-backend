description = "fooding-gateway"

dependencies {
	implementation("org.springframework.cloud:spring-cloud-starter-gateway")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("io.netty:netty-resolver-dns-native-macos:4.1.111.Final:osx-aarch_64")
	testImplementation("io.projectreactor:reactor-test")
}

val springCloudVersion by extra("2023.0.2")

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
	}
}
