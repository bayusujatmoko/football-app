package match.football.submission2.submission2.app.model

import com.google.gson.annotations.SerializedName

data class Match(
    @SerializedName("idEvent")
    var idEvent: String? = null,

    @SerializedName("idLeague")
    var idLeague: String? = null,

    @SerializedName("dateEvent")
    var dateEvent: String? = null,

    @SerializedName("intHomeScore")
    var intHomeScore: String? = null,

    @SerializedName("intAwayScore")
    var intAwayScore: String? = null,

    @SerializedName("strHomeTeam")
    var strHomeTeam: String? = null,

    @SerializedName("strAwayTeam")
    var strAwayTeam: String? = null,

    @SerializedName("strTime")
    var strTime: String? = null
)