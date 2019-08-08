package match.football.submission2.submission2.app.player

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.player_fragment.*
import match.football.submission2.submission2.R
import match.football.submission2.submission2.app.api.ApiRepository
import match.football.submission2.submission2.app.model.Player
import match.football.submission2.submission2.app.util.Constant
import match.football.submission2.submission2.app.util.invisible
import match.football.submission2.submission2.app.util.visible
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class PlayerFragment : Fragment(), PlayerView{
    private var player: MutableList<Player> = mutableListOf()
    private var idTeam: String? = null
    private lateinit var presenter: PlayerPresenter
    private lateinit var adapter: PlayerAdapter
    private lateinit var listPlayer: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.player_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        val intent = activity?.intent
        idTeam = intent?.getStringExtra(Constant.DETAIL)
        val gson = Gson()
        val apiRepository = ApiRepository()
        setAdapter(player)
        presenter = PlayerPresenter(this, apiRepository, gson)
        presenter.getPlayerList(idTeam)

        swipeRefreshPlayer.onRefresh {
            presenter.getPlayerList(idTeam)
            hideLoading()
        }
    }

    private fun initData() {
        listPlayer = find(R.id.rvPlayer)
        swipeRefresh = find(R.id.swipeRefreshPlayer)
        progressBar = find(R.id.progressBarPlayer)
    }

    private fun setAdapter(data: List<Player>) {
        adapter = PlayerAdapter(data,requireContext()) {
            startActivity<PlayerDetailActivity>(Constant.TAG_PLAYERS to it.idPlayer)
        }
        rvPlayer.adapter = adapter
    }


    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun showPlayerList(data: List<Player>) {
        swipeRefresh.isRefreshing = false
        player.clear()
        player.addAll(data)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listPlayer.layoutManager = layoutManager
        adapter.notifyDataSetChanged()
    }
}