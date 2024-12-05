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
    implementation("com.google.dagger:dagger:2.44")
    annotationProcessor("com.google.dagger:dagger-compiler:2.44")
    implementation ("org.jetbrains:annotations:16.0.2")
}
application {
     mainClass = "be.ecam.trafficsim.Main"
}
tasks.test {
    useJUnitPlatform()
}