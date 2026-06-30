package org.jotcelyngodoy.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform