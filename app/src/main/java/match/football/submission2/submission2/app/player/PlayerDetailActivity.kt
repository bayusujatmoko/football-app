package match.football.submission2.submission2.app.player

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_player.*
import match.football.submission2.submission2.R
import match.football.submission2.submission2.app.api.ApiRepository
import match.football.submission2.submission2.app.model.PlayerDetail
import match.football.submission2.submission2.app.util.Constant
import match.football.submission2.submission2.app.util.invisible
import match.football.submission2.submission2.app.util.visible
import org.jetbrains.anko.find

class PlayerDetailActivity : AppCompatActivity(), PlayerViewDetail {

    private val player: MutableList<PlayerDetail> = mutableListOf()
    private lateinit var idPlayers: String
    private lateinit var presenter: PlayerPresenterDetail
    private lateinit var progressBar: ProgressBar
    private lateinit var imgPlayer: ImageView
    private lateinit var weight: TextView
    private lateinit var height: TextView
    private lateinit var position: TextView
    private lateinit var description: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)

        initData()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val intent = intent
        idPlayers = intent.getStringExtra(Constant.TAG_PLAYERS)
        val gson = Gson()
        val apiRepository = ApiRepository()
        presenter = PlayerPresenterDetail(this, apiRepository, gson)
        presenter.getPlayerDetail(idPlayers)
    }

    private fun initData() {
        progressBar = find(R.id.progressBarPlayerDetail)
        imgPlayer = find(R.id.img_player)
        weight = find(R.id.tvPlayerWeight)
        height = find(R.id.tvPlayerHeight)
        position = find(R.id.tvPlayerPosition)
        description = find(R.id.tvPlayerDescription)
    }

    override fun hideLoading() {
        progressBarPlayerDetail.invisible()
    }

    override fun showLoading() {
        progressBarPlayerDetail.visible()
    }

    override fun showPlayerDetail(data: List<PlayerDetail>) {
        player.clear()
        player.addAll(data)
        supportActionBar?.title = data[0].strPlayer
        Picasso.get().load(data[0].strFanart1).into(imgPlayer)
        weight.text = data[0].strWeight
        height.text = data[0].strheight
        position.text = data[0].strPosition
        description.text = data[0].strDescriptionEN
    }
}