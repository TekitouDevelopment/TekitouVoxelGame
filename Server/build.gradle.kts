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
plugins {
    id("java")
    id("com.gradleup.shadow") version "9.6.1"
}

group = "com.mc1510ty"

repositories {
    mavenCentral()
}

dependencies {
//    implementation(project(":common"))

    implementation("org.yaml:snakeyaml:2.2")
}

tasks{
    shadowJar {
        archiveFileName.set("TekitouVoxelGame-Server-${project.version}.jar")
        manifest {
            attributes["Main-Class"] = "com.mc1510ty.TekitouVoxelGame.Server.Main"
        }
    }
}