plugins {
    antlr
    java
    application
    kotlin("jvm") version "1.3.72"
}

repositories {
    mavenCentral()
}

application {
    mainClassName = "MainKt"

    val run: JavaExec by tasks
    run.standardInput = System.`in`
}

dependencies {
    antlr("org.antlr", "antlr4", "4.8")
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.ajalt:clikt:2.7.1")
    testImplementation("org.junit.jupiter", "junit-jupiter", "5.6.2")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    generateGrammarSource {
        outputs.cacheIf { true }
        inputs.dir("src/main/antlr")
        outputDirectory = File("src/main/java/antlr")
        arguments = arguments + listOf("-visitor", "-long-messages", "-package", "antlr")
    }
    test {
        useJUnitPlatform()
    }
    compileKotlin {
        dependsOn(generateGrammarSource)
    }
}

