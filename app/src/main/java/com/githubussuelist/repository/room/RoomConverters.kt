package com.githubussuelist.repository.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

object RoomConverters {
    @JvmField
    val mGson = Gson()

    @JvmStatic
    @TypeConverter
    fun dateFromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @JvmStatic
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @JvmStatic
    @TypeConverter
    fun labelMapFromString(string: String): Map<String, String> {
        return mGson.fromJson(string, Map::class.java) as Map<String, String>
    }

    @JvmStatic
    @TypeConverter
    fun labelMapToString(labelMap: Map<String, String>): String {
        return mGson.toJson(labelMap)
    }
}