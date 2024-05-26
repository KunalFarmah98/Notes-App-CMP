package database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    val title: String,
    val text: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)