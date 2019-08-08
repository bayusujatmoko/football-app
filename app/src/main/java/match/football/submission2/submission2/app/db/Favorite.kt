package match.football.submission2.submission2.app.db

data class Favorite(
    val id: Long?,
    val idEvent: String?,
    val dateEvent: String?,
    val strTime: String?,
    val homeScore: String?,
    val awayScore: String?,
    val homeTeam: String?,
    val awayTeam: String?
) {
    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val IDS: String = "id"
        const val ID: String = "ID_"
        const val ID_EVENT: String = "ID_EVENT"
        const val DATE_EVENT : String = "DATE_EVENT"
        const val TIME : String = "TIME"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val AWAY_TEAM: String = "AWAY_TEAM"
    }
}