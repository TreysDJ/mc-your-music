plugins {
    id("java")
}

group = "bro.maks"
version = "0.0.1"

// Set Java compatibility. Must match your JDK and the server's requirements (JDK 17 for MC 1.17+)
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(24))
}

repositories {
    // Define where Gradle should look for your dependencies
    mavenCentral() // Standard Maven central repository
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    // Define your project's dependencies
    // 'compileOnly' is equivalent to Maven's 'provided' scope
    compileOnly("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT")
    // If your plugin uses other libraries, add them as 'implementation("groupId:artifactId:version")'
    // For example:
    // implementation("com.google.guava:guava:32.1.3-jre")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

// Task to copy the built JAR directly to your server's plugins folder
tasks.register<Copy>("deployPlugin") {
    from(tasks.jar.map { it.archiveFile.get().asFile })
    // IMPORTANT: Adjust this path to your local server's plugins folder
    destinationDir = file("C:/Dev/MinecraftServer/plugins") // Windows example
    // destinationDir = file("/Users/yourusername/MinecraftServer/plugins") // macOS/Linux example
    // Or set via environment variable/project property for flexibility
}

// Optional: Make 'build' also trigger the deployment
tasks.build {
    dependsOn("deployPlugin")
}

// Configuration for the JAR file output
tasks.jar {
    // Specify the name of your plugin JAR file.
    // By default, it's artifactId-version.jar, e.g., MyFirstPlugin-1.0-SNAPSHOT.jar
    // You can customize it if needed:
    // archiveFileName.set("${project.name}.jar")
    // Or to include version:
    // archiveFileName.set("${project.name}-${project.version}.jar")
}

tasks.test {
    useJUnitPlatform()
}