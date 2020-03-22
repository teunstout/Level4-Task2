package com.example.rockpaperscissors.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "HistoryGame")
data class PlayedGame(
//    @ColumnInfo(name = "date")
    val computerThrow: String,
//    @ColumnInfo(name = "date")
    val playerThrow: String,
//    @ColumnInfo(name = "date")
    val playedDate: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var gameId: Long? = null

) : Parcelable