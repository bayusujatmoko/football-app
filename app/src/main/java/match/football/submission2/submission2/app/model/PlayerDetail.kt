package match.football.submission2.submission2.app.model

import com.google.gson.annotations.SerializedName

data class PlayerDetail(

    @SerializedName("idPlayer")
    val idPlayer: String? = null,

    @SerializedName("idTeam")
    var idTeam: String? = null,

    @SerializedName("strPlayer")
    val strPlayer: String? = null,

    @SerializedName("strFanart1")
    val strFanart1: String? = null,

    @SerializedName("strHeight")
    val strheight: String? = null,

    @SerializedName("strWeight")
    val strWeight: String? = null,

    @SerializedName("strPosition")
    val strPosition: String? = null,

    @SerializedName("strDescriptionEN")
    val strDescriptionEN: String? = null
)