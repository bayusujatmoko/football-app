package match.football.submission2.submission2.app.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.team_fragment.*
import match.football.submission2.submission2.R
import match.football.submission2.submission2.app.api.ApiRepository
import match.football.submission2.submission2.app.model.Team
import match.football.submission2.submission2.app.teams.TeamsAdapter
import match.football.submission2.submission2.app.teams.TeamsPresenter
import match.football.submission2.submission2.app.teams.TeamsView
import match.football.submission2.submission2.app.teams.TeamsDetailActivity
import match.football.submission2.submission2.app.util.Constant
import match.football.submission2.submission2.app.util.invisible
import match.football.submission2.submission2.app.util.visible
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.startActivity

class TeamsFragment : Fragment(), TeamsView,
    SearchView.OnQueryTextListener,
    AdapterView.OnItemSelectedListener
{

    private var teamList : MutableList<Team> = mutableListOf()
    private lateinit var presenter : TeamsPresenter
    private lateinit var adapter: TeamsAdapter
    private lateinit var listTeam: RecyclerView
    private lateinit var spinner: Spinner
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var leagueName: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.team_fragment, container, false)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.search, menu)
        val searchItem = menu.findItem(R.id.search_menu)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            val editext: EditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)
            editext.hint = getString(R.string.search)
            searchView.setOnQueryTextListener(this)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        //load data
        val request = ApiRepository()
        val gson = Gson()

        presenter = TeamsPresenter(this, request, gson)

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter
        setAdapter(teamList)
        spinner.onItemSelectedListener = this

        swipeRefreshTeam.setOnRefreshListener {
            presenter.getTeamList(leagueName)
            hideLoading()
        }
    }

    private fun initData() {
        listTeam = find(R.id.rvTeam)
        progressBar = find(R.id.progressBarTeam)
        swipeRefresh = find(R.id.swipeRefreshTeam)
        spinner = find(R.id.spinnerTeam)
    }

    private fun setAdapter(data: List<Team>) {
        adapter = TeamsAdapter(data,requireContext()) {
            startActivity<TeamsDetailActivity>(Constant.DETAIL to it.idTeam)
        }
        rvTeam.adapter = adapter
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teamList.clear()
        teamList.addAll(data)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listTeam.layoutManager = layoutManager
        adapter.notifyDataSetChanged()
    }

    override fun onQueryTextSubmit(search: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(search: String): Boolean {
        if (search.isNotEmpty()) {
            val cari = search.toLowerCase()
            val data = teamList.filter {
                it.strTeam.toString().toLowerCase().contains(cari)
            }
            setAdapter(data)
        } else {
            setAdapter(teamList)
        }
        return true
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        leagueName = spinner.selectedItem.toString()
        val data = leagueName.split(' ')
        leagueName = data.joinToString("%20")
        presenter.getTeamList(leagueName)
    }
}


