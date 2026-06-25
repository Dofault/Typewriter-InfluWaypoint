plugins {
    kotlin("jvm") version "2.2.10"
    id("com.typewritermc.module-plugin") version "2.1.0"
}

repositories {
    mavenCentral()
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    implementation(kotlin("reflect"))
    // Provided at runtime by the PlaceholderAPI plugin already installed on the server.
    compileOnly("me.clip:placeholderapi:2.11.6")
}

group = "com.example"
version = "1.0.0"

typewriter {
    namespace = "example"

    extension {
        name = "Waypoint"
        shortDescription = "Single tracked waypoint per player, exposed as PlaceholderAPI placeholders"
        description = """
            |Lets Typewriter entries set/clear a single tracked waypoint per player, and exposes
            |%waypoint_compass% (ItemsAdder compass frame id) and %waypoint_distance% via PlaceholderAPI.
            """.trimMargin()
        engineVersion = "0.9.0-beta-174"
        channel = com.typewritermc.moduleplugin.ReleaseChannel.BETA

        dependencies {
            paper {
                dependency("PlaceholderAPI")
            }
        }
    }
}

kotlin {
    jvmToolchain(21)
}
