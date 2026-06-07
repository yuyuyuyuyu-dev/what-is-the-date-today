package dev.yuyuyuyuyu.whatisthedatetoday

class Greeting {
    private val platform = getPlatform()

    fun greet(): String = "Hello, ${platform.name}!"
}
