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
    // Provided at runtime by Typewriter's own QuestExtension. Used to find the waypoint belonging
    // to the player's currently tracked quest, instead of any quest's currently-active objective.
    implementation("com.typewritermc:QuestExtension:0.9.0")
}

group = "com.example"
version = "1.0.0"

typewriter {
    namespace = "example"

    extension {
        name = "InfluWaypoint"
        shortDescription = "Active player waypoint, exposed via PlaceholderAPI placeholders"
        description = """
            |Exposes %waypoint_compass% (ItemsAdder compass frame id) and %waypoint_distance% via
            |PlaceholderAPI for whichever waypoint_manifest entry the player is currently in the
            |audience of. No persisted state, resolved live on every placeholder request.
            """.trimMargin()
        engineVersion = "0.9.0-beta-174"
        channel = com.typewritermc.moduleplugin.ReleaseChannel.BETA

        dependencies {
            dependency("typewritermc", "Quest")
            paper {
                dependency("PlaceholderAPI")
            }
        }
    }
}

kotlin {
    jvmToolchain(21)
}

tasks.jar {
    archiveFileName.set("influ-waypoint.jar")
}
