description = "fooding-realtime"

dependencies {
    implementation(project(":fooding-core"))

    // R2DBC ( JPA는 WebFlux랑 호환성이 좋지 않음 )
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")

    // Spring Boot WebFlux Starter
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.2.0")

    // WebFlux 실행환경 ( Netty 런타임 환경 )
    implementation("org.springframework.boot:spring-boot-starter-reactor-netty")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")

    // mariadb 드라이버 ( MySQL이랑 호환됨 )
    implementation("org.mariadb:r2dbc-mariadb:1.1.4")

    // lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}