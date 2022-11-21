package com.nur_ikhsan.themoviedb.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite")
class TrailersFavorite (

    @field:ColumnInfo(name = "name")
    @field:PrimaryKey
    val name : String,

    @field:ColumnInfo(name = "id")
    val id : String,

    @field:ColumnInfo(name = "key")
    val key : String,

    @field:ColumnInfo(name = "bookmarked" )
    var isBookmarked : Boolean

)