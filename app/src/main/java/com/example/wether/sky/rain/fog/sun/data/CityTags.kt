package com.example.wether.sky.rain.fog.sun.data

enum class CityTags(val tag: String) {
    RU("RU"),
    EU("EU"),
    World("WORLD");

    companion object{
        fun getEnumByTag(tag: String): CityTags{
            return when (tag) {
                RU.tag -> RU
                World.tag -> World
                else -> World
            }
        }
    }


}