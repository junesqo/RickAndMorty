package kg.junesqo.rickandmorty.data.characters.local

import androidx.room.TypeConverter
import kg.junesqo.rickandmorty.data.characters.local.local_models.DBLocation
import kg.junesqo.rickandmorty.data.characters.local.local_models.DBOrigin
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject

class Converters {

    @TypeConverter
    fun fromList(value : List<String>) = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<String>>(value)

    @TypeConverter
    fun fromLocation(dbLocation: DBLocation): String {
        return JSONObject().apply {
            put("name", dbLocation.name)
            put("url", dbLocation.url)
        }.toString()
    }

    @TypeConverter
    fun toLocation(dbLocation: String): DBLocation {
        val json = JSONObject(dbLocation)
        return DBLocation(json.getString("name"), json.getString("url"))
    }

    @TypeConverter
    fun fromOrigin(dbOrigin: DBOrigin): String {
        return JSONObject().apply {
            put("name", dbOrigin.name)
            put("url", dbOrigin.url)
        }.toString()
    }

    @TypeConverter
    fun toOrigin(dbOrigin: String): DBOrigin {
        val json = JSONObject(dbOrigin)
        return DBOrigin(json.getString("name"), json.getString("url"))
    }

}