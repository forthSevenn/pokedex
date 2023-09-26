package com.firstapp.pokedex.model.pokemon

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "pokemon", indices = [Index(value = ["name"], unique = true)])
@Parcelize

data class Pokemon (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int,

    @ColumnInfo(name = "name")
    val name: String?
):Parcelable