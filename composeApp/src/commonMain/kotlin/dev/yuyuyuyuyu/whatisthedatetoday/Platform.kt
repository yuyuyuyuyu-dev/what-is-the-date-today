package dev.yuyuyuyuyu.whatisthedatetoday

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform