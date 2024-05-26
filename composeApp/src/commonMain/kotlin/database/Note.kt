package database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes")
data class Note(
    val title: String,
    val content: String,
    val colorHex: Long,
    val created: Long,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
){
    companion object{
        private val colors = listOf(Colors.VioletHex, Colors.RedPinkHex, Colors.BabyBlueHex, Colors.LightGreenHex, Colors.RedOrangeHex)
        fun generateRandomColor() = colors.random()
    }
}