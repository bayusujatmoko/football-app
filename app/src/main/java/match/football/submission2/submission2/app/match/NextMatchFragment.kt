package match.football.submission2.submission2.app.match

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.next_match_fragment.*
import match.football.submission2.submission2.R
import match.football.submission2.submission2.R.id.search_menu
import match.football.submission2.submission2.app.api.ApiRepository
import match.football.submission2.submission2.app.detail.DetailActivity
import match.football.submission2.submission2.app.model.Match
import match.football.submission2.submission2.app.util.Constant
import match.football.submission2.submission2.app.util.Time
import match.football.submission2.submission2.app.util.invisible
import match.football.submission2.submission2.app.util.visible
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.startActivity

class NextMatchFragment : Fragment(), MatchView,
    SearchView.OnQueryTextListener,
    AdapterView.OnItemSelectedListener
{

    private var matchList : MutableList<Match> = mutableListOf()
    private lateinit var presenter : MatchPresenter
    private lateinit var adapter: NextMatchAdapter
    private lateinit var idLeague: String
    private lateinit var spinner: Spinner
    private lateinit var listMatch: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.next_match_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()

        //load data
        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchPresenter(this, request, gson)

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = this

        setAdapter(matchList)

        swipeRefreshNextMatch.setOnRefreshListener {
            presenter.getNextMatchData(idLeague)
            hideLoading()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        idLeague = resources.getStringArray(R.array.id_league)[position]
        presenter.getNextMatchData(idLeague)
    }

    private fun initData() {
        listMatch = find(R.id.rvNextMatch)
        progressBar = find(R.id.progressBarNextMatch)
        swipeRefresh = find(R.id.swipeRefreshNextMatch)
        spinner = find(R.id.spinnerNextMatch)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun setAdapter(data: List<Match>) {
        adapter = NextMatchAdapter(data, requireContext(),{
            startActivity<DetailActivity>(Constant.DETAIL to it.idEvent)
        }, {
            val intent = Intent(Intent.ACTION_EDIT)
            intent.type = getString(R.string.intent_type_calendar)
            val time = Time.formatUTCtoGMT(it.dateEvent.toString(), it.strTime.toString().substring(0, 5), Constant.DATE_TIME_FORMAT_UTC)
            val timestamp = Time.timeStamp(time)
            val title = "${it.strHomeTeam} VS ${it.strAwayTeam}"

            intent.putExtra(CalendarContract.Events.TITLE, title)
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, timestamp)
            intent.putExtra(CalendarContract.CalendarAlerts.ALARM_TIME, timestamp)
            startActivity(intent)
        })
        rvNextMatch.adapter = adapter
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showMatchList(data: List<Match>) {
        swipeRefresh.isRefreshing = false
        matchList.clear()
        matchList.addAll(data)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listMatch.layoutManager = layoutManager
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.search, menu)
        val searchItem = menu.findItem(search_menu)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            val editext: EditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)
            editext.hint = getString(R.string.search)
            searchView.setOnQueryTextListener(this)
        }
    }

    override fun onQueryTextSubmit(search: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(search: String): Boolean {
        if (search.isNotEmpty()) {
            val find = search.toLowerCase()
            val data = matchList.filter {
                it.strHomeTeam.toString().toLowerCase().contains(find) ||
                        it.strAwayTeam.toString().toLowerCase().contains(find)
            }
            setAdapter(data)
        } else {
            setAdapter(matchList)
        }
        return true
    }
}


