import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.21"
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

    testImplementation(Config.Libs.junit)
    testImplementation(Config.Libs.kluent)
    // testImplementation(Config.Libs.spekDsl)

    // testRuntimeOnly(Config.Libs.kotlinReflect)
    // testRuntimeOnly(Config.Libs.spekRunner)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.register("ktlint", Ktlint::class)