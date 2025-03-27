package org.penakelex.rating_physics

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform