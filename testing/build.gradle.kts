plugins {
    id("java")
}

group = "be.ecam"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // Mockito core
    testImplementation("org.mockito:mockito-core:5.13.0")
// Optional: Mockito + JUnit 5 integration (recommended)
    testImplementation("org.mockito:mockito-junit-jupiter:5.13.0")
}

tasks.test {
    useJUnitPlatform()
}