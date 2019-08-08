package match.football.submission2.submission2.app.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelperTeams(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteTeamDetail.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelperTeams? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelperTeams {
            if (instance == null) {
                instance = MyDatabaseOpenHelperTeams(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelperTeams
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true,
            FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
            FavoriteTeam.TEAM_NAME to TEXT,
            FavoriteTeam.TEAM_BADGE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true)
    }
}

val Context.databaseTeams: MyDatabaseOpenHelperTeams
    get() = MyDatabaseOpenHelperTeams.getInstance(applicationContext)