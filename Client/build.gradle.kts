//TekitouVoxelGame - A voxel game that 1510ty is casually putting together.
//Copyright (C) 2023-2026 1510ty
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU Affero General Public License as published by
//the Free Software Foundation, either version 3 of the License, or any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU Affero General Public License for more details.
//
//You should have received a copy of the GNU Affero General Public License
//along with this program.  If not, see <https://www.gnu.org/licenses/>.

import org.gradle.internal.os.OperatingSystem
import org.gradle.api.tasks.Exec

plugins {
    id("java")
    id("com.gradleup.shadow") version "9.6.1"
}

tasks{
    shadowJar {
        archiveFileName.set("TekitouVoxelGame-Client-${project.version}.jar")
        manifest {
            attributes["Main-Class"] = "com.mc1510ty.TekitouVoxelGame.Client.Main"
        }
    }

}

tasks.register<Exec>("runClient") {
    dependsOn("shadowJar")

    val shadowJarTask = tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar")
    val jarFile = shadowJarTask.get().archiveFile.get().asFile.absolutePath

    if (System.getProperty("os.name").lowercase().contains("windows")) {
        // cmd /k に渡すコマンド全体をひとつの文字列にまとめる
        val command = "java -jar \"$jarFile\" --debug --enable_validation_layer && pause && exit"
        commandLine("cmd", "/c", "start", "cmd", "/k", command)
    } else {
        commandLine("java", "-jar", jarFile, "--debug", "--enable_validation_layer")
    }
}


val lwjglVersion = "3.4.3-SNAPSHOT"
val jomlVersion = "1.10.9"
val `joml-primitivesVersion` = "1.10.0"

val lwjglNatives = Pair(
    System.getProperty("os.name")!!,
    System.getProperty("os.arch")!!
).let { (name, arch) ->
    when {
        "FreeBSD".equals(name)                                    ->
            "natives-freebsd"
        arrayOf("Linux", "SunOS", "Unix").any { name.startsWith(it) } ->
            if (arrayOf("arm", "aarch64").any { arch.startsWith(it) })
                "natives-linux${if (arch.contains("64") || arch.startsWith("armv8")) "-arm64" else "-arm32"}"
            else if (arch.startsWith("ppc"))
                "natives-linux-ppc64le"
            else if (arch.startsWith("riscv"))
                "natives-linux-riscv64"
            else
                "natives-linux"
        arrayOf("Mac OS X", "Darwin").any { name.startsWith(it) }     ->
            "natives-macos${if (arch.startsWith("aarch64")) "-arm64" else ""}"
        arrayOf("Windows").any { name.startsWith(it) }                ->
            if (arch.contains("64"))
                "natives-windows${if (arch.startsWith("aarch64")) "-arm64" else ""}"
            else
                "natives-windows-x86"
        else                                                                            ->
            throw Error("Unrecognized or unsupported platform. Please set \"lwjglNatives\" manually")
    }
}


repositories {
    mavenCentral()
    maven("https://central.sonatype.com/repository/maven-snapshots")
}

dependencies {
    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))

    implementation("org.lwjgl:lwjgl")
    implementation("org.lwjgl:lwjgl-assimp")
    implementation("org.lwjgl:lwjgl-freetype")
    implementation("org.lwjgl:lwjgl-harfbuzz")
    implementation("org.lwjgl:lwjgl-openal")
    implementation("org.lwjgl:lwjgl-opengl")
    implementation("org.lwjgl:lwjgl-sdl")
    implementation("org.lwjgl:lwjgl-spng")
    implementation("org.lwjgl:lwjgl-vma")
    implementation("org.lwjgl:lwjgl-vulkan")
    implementation("org.lwjgl:lwjgl-xxhash")
    implementation("org.lwjgl:lwjgl-zstd")
    implementation("org.lwjgl:lwjgl::$lwjglNatives")
    implementation("org.lwjgl:lwjgl-assimp::$lwjglNatives")
    implementation("org.lwjgl:lwjgl-freetype::$lwjglNatives")
    implementation("org.lwjgl:lwjgl-harfbuzz::$lwjglNatives")
    implementation("org.lwjgl:lwjgl-openal::$lwjglNatives")
    implementation("org.lwjgl:lwjgl-opengl::$lwjglNatives")
    implementation("org.lwjgl:lwjgl-sdl::$lwjglNatives")
    implementation("org.lwjgl:lwjgl-spng::$lwjglNatives")
    implementation("org.lwjgl:lwjgl-vma::$lwjglNatives")
    if (lwjglNatives == "natives-macos" || lwjglNatives == "natives-macos-arm64") implementation("org.lwjgl:lwjgl-vulkan::$lwjglNatives")
    implementation("org.lwjgl:lwjgl-xxhash::$lwjglNatives")
    implementation("org.lwjgl:lwjgl-zstd::$lwjglNatives")
    implementation("org.joml:joml:$jomlVersion")
    implementation("org.joml:joml-primitives:${`joml-primitivesVersion`}")
}