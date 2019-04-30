import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Config.Versions.kotlin
    jacoco
}

group = "norangebit"
version = "0.0.1"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(Config.Libs.arrowCore)
    implementation(Config.Libs.koin)
    implementation(Config.Libs.gson)

    testImplementation(Config.Libs.junit)
    testImplementation(Config.Libs.kluent)
    testImplementation(Config.Libs.mockk)
    testImplementation(Config.Libs.jetbrainJunit)
    // testImplementation(Config.Libs.spekDsl)

    testRuntimeOnly(Config.Libs.junitEngine)
    // testRuntimeOnly(Config.Libs.kotlinReflect)
    // testRuntimeOnly(Config.Libs.spekRunner)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.register("ktlint", Ktlint::class)
