package match.football.submission2.submission2.app.api

import android.net.Uri
import match.football.submission2.submission2.BuildConfig

object ApiInterface {
   fun getLastMatch(league: String?): String {
      return Uri.parse(BuildConfig.BASE_URL).buildUpon()
         .appendPath("api")
         .appendPath("v1")
         .appendPath("json")
         .appendPath(BuildConfig.TSDB_API_KEY)
         .appendPath("eventspastleague.php")
         .appendQueryParameter("id", league)
         .build()
         .toString()
   }

   fun getNextMatch(league: String?): String {
      return Uri.parse(BuildConfig.BASE_URL).buildUpon()
         .appendPath("api")
         .appendPath("v1")
         .appendPath("json")
         .appendPath(BuildConfig.TSDB_API_KEY)
         .appendPath("eventsnextleague.php")
         .appendQueryParameter("id", league)
         .build()
         .toString()
   }

   fun getEvent(league: String?): String {
      return Uri.parse(BuildConfig.BASE_URL).buildUpon()
         .appendPath("api")
         .appendPath("v1")
         .appendPath("json")
         .appendPath(BuildConfig.TSDB_API_KEY)
         .appendPath("lookupevent.php")
         .appendQueryParameter("id", league)
         .build()
         .toString()
   }

   fun getPlayerList(league: String?): String {
      return Uri.parse(BuildConfig.BASE_URL).buildUpon()
         .appendPath("api")
         .appendPath("v1")
         .appendPath("json")
         .appendPath(BuildConfig.TSDB_API_KEY)
         .appendPath("lookup_all_players.php")
         .appendQueryParameter("id", league)
         .build()
         .toString()
   }

   fun getPlayer(league: String?): String {
      return Uri.parse(BuildConfig.BASE_URL).buildUpon()
         .appendPath("api")
         .appendPath("v1")
         .appendPath("json")
         .appendPath(BuildConfig.TSDB_API_KEY)
         .appendPath("lookupplayer.php")
         .appendQueryParameter("id", league)
         .build()
         .toString()
   }

   fun getTeam(league: String?): String {
      return Uri.parse(BuildConfig.BASE_URL).buildUpon()
         .appendPath("api")
         .appendPath("v1")
         .appendPath("json")
         .appendPath(BuildConfig.TSDB_API_KEY)
         .appendPath("lookupteam.php")
         .appendQueryParameter("id", league)
         .build()
         .toString()
   }

   fun getTeamList(league: String?): String {
      return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/search_all_teams.php?l=" + league
   }
}