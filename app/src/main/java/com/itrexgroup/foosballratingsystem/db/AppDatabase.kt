package com.itrexgroup.foosballratingsystem.db

import androidx.lifecycle.LiveData
import androidx.room.*
import org.joda.time.DateTime

@Database(
    entities = [(Game::class)],
    version = 1
)
@TypeConverters(DateTimeTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getGameDao(): GameDao

}

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game: Game)

    @Delete
    fun delete(game: Game)

    @Query("DELETE FROM games")
    fun deleteAll()

    @Query("SELECT * FROM games ORDER by datetime DESC")
    fun getAllGames(): LiveData<List<Game>>

    @Query("SELECT * FROM games WHERE playerNameHome = :playerName OR playerNameAway = :playerName ORDER by datetime DESC")
    fun getAllGames(playerName: String): LiveData<List<Game>>

    @Query("SELECT * FROM games WHERE id = :gameId")
    fun getGame(gameId: Int): LiveData<Game>
}

@Entity(tableName = "games")
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val playerNameHome: String = "",
    val playerNameAway: String = "",
    val scoreHome: Int = 0,
    val scoreAway: Int = 0,
    val dateTime: DateTime = DateTime()
) {

    val isHomePlayerWins: Boolean get() = scoreHome >= WIN_CONDITION_POINTS && scoreHome != scoreAway
    val isAwayPlayerWins: Boolean get() = scoreAway >= WIN_CONDITION_POINTS && scoreHome != scoreAway

    companion object {
        const val WIN_CONDITION_POINTS = 5
    }
}