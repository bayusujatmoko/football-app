package match.football.submission2.submission2.app.model

import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("idPlayer")
    val idPlayer: String? = null,

    @SerializedName("idTeam")
    var idTeam: String? = null,

    @SerializedName("strPlayer")
    val strPlayer: String? = null,

    @SerializedName("strPosition")
    val strPosition: String? = null,

    @SerializedName("strCutout")
    val strCutout: String? = null
)