package com.itrexgroup.foosballratingsystem.db

import androidx.room.TypeConverter
import org.joda.time.DateTime

object DateTimeTypeConverter {

    @TypeConverter
    @JvmStatic
    fun toDateTime(value: Long?): DateTime {
        return DateTime(value)
    }

    @TypeConverter
    @JvmStatic
    fun fromDateTime(date: DateTime?): Long? = date?.millis
}