package com.nur_ikhsan.themoviedb.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.io.Serializable


@Entity(tableName = "favoritemovies")
@Parcelize
data class FavoriteMovie(

    val title: String,

    var id: String,

    val poster_path: String

) : Serializable, Parcelable{

    @IgnoredOnParcel

    @PrimaryKey(autoGenerate = true)
    var movieId : Int = 0
}