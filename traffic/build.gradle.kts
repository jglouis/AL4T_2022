plugins {
    application
}

group = "be.ecam"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("org.jetbrains:annotations:16.0.2")
}
application {
     mainClass = "trafficsim.Main"
}
tasks.test {
    useJUnitPlatform()
}