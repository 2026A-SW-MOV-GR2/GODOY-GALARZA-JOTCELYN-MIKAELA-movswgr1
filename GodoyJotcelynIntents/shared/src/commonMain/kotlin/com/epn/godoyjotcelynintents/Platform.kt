package com.epn.godoyjotcelynintents

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform